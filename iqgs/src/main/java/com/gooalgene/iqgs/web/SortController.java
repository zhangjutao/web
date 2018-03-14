package com.gooalgene.iqgs.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.AdvanceSearchType;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.sort.SortRequestParam;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.eventbus.events.*;
import com.gooalgene.iqgs.service.FPKMService;
import com.gooalgene.iqgs.service.GeneRegexpService;
import com.gooalgene.iqgs.service.sort.GeneSortUtils;
import com.gooalgene.iqgs.service.sort.GeneSortViewService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.gooalgene.utils.ConsequenceTypeUtils;
import com.gooalgene.utils.ResultUtil;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * 排序接口层
 */
@Controller
@RequestMapping("/sort")
public class SortController implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(SortController.class);

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TraitCategoryService traitCategoryService;

    @Autowired
    private GeneSortViewService geneSortViewService;

    @Autowired
    private FPKMService fpkmService;

    @Autowired
    private GeneSortUtils geneSortUtils;

    private Cache cache;

    private ObjectMapper mapper;

    /**
     * 获取当前页面所有基因ID
     */
    @RequestMapping(value = "/fetch-all-geneId", method = RequestMethod.POST)
    @ResponseBody
    public List<String> fetchAllCurrentPageGeneId(){
        return null;
    }

    @RequestMapping("/dispatch")
    public String dispatchToIFrame(){
        return "search/sort";
    }

    /**
     * 排序接口
     * @return 排序后的基因信息集合
     */
    @RequestMapping(value = "/fetch-sort-result", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<SortedResult> fetchSortedResult(@RequestBody SortRequestParam sortRequestParam){
        PageInfo<SortedResult> resultPage = geneSortViewService.findSortedView(sortRequestParam.getGeneIdList(), sortRequestParam.getTissue(),
                sortRequestParam.getTraitCategoryId(), sortRequestParam.getPageNo(), sortRequestParam.getPageSize());
        return ResultUtil.success(resultPage);
    }

    @RequestMapping(value = "/copy-ordered-geneId", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<List<String>> copyOrderedGeneId(@RequestBody SortRequestParam sortRequestParam) {
        AllSortedResultEvent cacheOrderedResult = new AllSortedResultEvent(sortRequestParam.getGeneIdList(), sortRequestParam.getTissue(), sortRequestParam.getTraitCategoryId(), null);
        String OrderedGeneIdkey = cacheOrderedResult.getClass().getSimpleName() + cacheOrderedResult.hashCode();
        Cache.ValueWrapper cacheOrderedGeneIdList = cache.get(OrderedGeneIdkey);
        if (cacheOrderedGeneIdList != null) {
            List<SortedResult> orderedGeneList = (List<SortedResult>) cacheOrderedGeneIdList.get();
            Collection<String> orderedGeneIdList = Collections2.transform(orderedGeneList, new Function<SortedResult, String>() {
                @Override
                public String apply(SortedResult sortedResult) {
                    return sortedResult.getGeneId();
                }
            });
            return ResultUtil.success(orderedGeneIdList);
        } else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    /**
     * 页面初始化时性状接口
     */
    @RequestMapping(value = "/fetch-trait", method = RequestMethod.POST)
    @ResponseBody
    public String fetchTrait(){
        String result = "";
        List<TraitCategoryWithinMultipleTraitList> allTraitCategory = traitCategoryService.findAllTraitCategoryAndItsTraitList();
        ImmutableList<TraitCategoryWithinMultipleTraitList> immutableList = ImmutableList.copyOf(allTraitCategory);
        Collection<SimpleTraitCategory> transformResult = Collections2.transform(immutableList, new Function<TraitCategoryWithinMultipleTraitList, SimpleTraitCategory>() {
            @Override
            public SimpleTraitCategory apply(TraitCategoryWithinMultipleTraitList input) {
                SimpleTraitCategory category = null;
                if (input != null) {
                    category = new SimpleTraitCategory(input.getTraitCategoryId(), input.getQtlDesc());
                }
                return category;
            }
        });
        try {
            result = mapper.writeValueAsString(transformResult);
        } catch (JsonProcessingException e) {
            logger.error("序列化错误", e.getCause());
        }
        return result;
    }

    /**
     * 排序第一屏获取所有基因ID数据
     * @return 搜索页面结果列表基因ID集合
     */
    @RequestMapping(value = "/fetch-first-screen", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<String> fetchFirstScreenData(@RequestBody GeneExpressionCondition geneExpressionCondition){
        List<GeneExpressionConditionEntity> entities = geneExpressionCondition.getGeneExpressionConditionEntities();
        List<String> selectSnp = geneExpressionCondition.getSnpConsequenceType();  //已选SNP集合
        List<String> selectIndel = geneExpressionCondition.getIndelConsequenceType();  //已选INDEL集合
        List<Integer> associateGeneIdArray = geneExpressionCondition.getQtlId();  //已选qtl集合
        List<Integer> firstHierarchyQtlId = geneExpressionCondition.getFirstHierarchyQtlId();  //一级搜索选中的QTL ID集合
        DNAGenBaseInfo baseInfo = geneExpressionCondition.getGeneInfo();  //高级搜索中根据基因名字查询传入的基因名或ID
        DNAGenStructure geneStructure = geneExpressionCondition.getGeneStructure();  //高级搜索中根据基因结构搜索相应基因
        if (selectSnp != null && selectSnp.size() > 0){
            selectSnp = ConsequenceTypeUtils.reverseReadableListValue(selectSnp);  //转换为数据库可读的序列类型
        }
        if (selectIndel != null && selectIndel.size() > 0){
            selectIndel = ConsequenceTypeUtils.reverseReadableListValue(selectIndel);
        }
        List<Integer> selectSNP = fpkmService.getAllSelectedConsequenceTypeId("SNP", selectSnp);
        List<Integer> selectINDEL = fpkmService.getAllSelectedConsequenceTypeId("INDEL", selectIndel);
        AllAdvanceSearchViewEvent event = null;
        if (baseInfo != null && baseInfo.getGeneId() != null){
            event = new AllAdvanceSearchViewEvent(entities, selectSNP, selectINDEL, associateGeneIdArray, baseInfo, AdvanceSearchType.ID);
        } else if (baseInfo != null && (baseInfo.getDescription() != null || baseInfo.getFunctions() != null)){
            event = new AllAdvanceSearchViewEvent(entities, selectSNP, selectINDEL, associateGeneIdArray, baseInfo, AdvanceSearchType.NAME);
        } else if (geneStructure != null){
            event = new AllAdvanceSearchViewEvent(entities, selectSNP, selectINDEL, associateGeneIdArray, geneStructure, AdvanceSearchType.REGION);
        } else if (firstHierarchyQtlId != null && firstHierarchyQtlId.size() > 0){
            event = new AllAdvanceSearchViewEvent(entities, selectSNP, selectINDEL, associateGeneIdArray, firstHierarchyQtlId, AdvanceSearchType.QTL);
        } else {
            logger.warn("传入数据有问题，请确定传入参数");
            return ResultUtil.error(-1, "传入参数有问题");
        }
        String key = event.getClass().getSimpleName() + event.hashCode();
        //数据缓存一小时，若一小时无操作，清空该数据
        Cache.ValueWrapper cachedGeneId = cache.get(key);
        if (cachedGeneId != null){
            List<String> resultGeneCollection = (List<String>) cachedGeneId.get();
            return ResultUtil.success(resultGeneCollection);
        } else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    /**
     * 获取范围搜索排序弹框首屏数据
     * @return 排序弹框中需要的首屏基因ID
     */
    @RequestMapping(value = "/fetch-range-data", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<String> fetchRangeSearchData(HttpServletRequest req){
        String start = req.getParameter("begin");
        String end = req.getParameter("end");
        String chr = req.getParameter("chr");
        AllRegionSearchResultEvent event = new AllRegionSearchResultEvent(chr, Integer.valueOf(start), Integer.valueOf(end));
        String key = event.getClass().getSimpleName() + event.hashCode();
        Cache.ValueWrapper cachedGeneId = cache.get(key);
        if (cachedGeneId != null){
            List<String> resultGeneCollection = (List<String>) cachedGeneId.get();
            return ResultUtil.success(resultGeneCollection);
        } else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    /**
     * 获取根据ID/NAME/FUNCTION查询的结果，作为排序的首屏数据
     * @return 排序弹框中需要的首屏基因ID
     */
    @RequestMapping(value = "/fetch-multi-data", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<String> fetchIdAndFunctionData(HttpServletRequest req){
        String keyword = req.getParameter("keyword");
        String searchType = req.getParameter("searchType");
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        // todo 增加判断：与第一次查询的条件保持一致，如果第一次未匹配，该次仍然无结果
        if (searchType.equals("1")){
            List<String> matchedGene = GeneRegexpService.interpretGeneInput(keyword);  //基因匹配结果
            if (matchedGene != null && matchedGene.size() > 0) {
                //先取第一个匹配到的值
                bean.setGeneId(matchedGene.get(0));
                bean.setGeneOldId(matchedGene.get(0));
            }
        } else {
            bean.setFunctions(keyword);
        }
        IDAndNameSearchViewEvent event = new IDAndNameSearchViewEvent(null, bean);
        String key = event.getClass().getSimpleName() + bean.hashCode();
        Cache.ValueWrapper cachedGeneId = cache.get(key);
        if (cachedGeneId != null){
            List<String> resultGeneCollection = (List<String>) cachedGeneId.get();
            return ResultUtil.success(resultGeneCollection);
        } else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    /**
     * 获取QTL查询结果，作为排序的首屏数据
     */
    @RequestMapping(value = "/fetch-qtl-data", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<String> fetchQtlData(@RequestParam(value = "chosenQtl[]") Integer[] chosenQtl){
        AllQTLSearchResultEvent event = new AllQTLSearchResultEvent(Arrays.asList(chosenQtl));
        String key = event.getClass().getSimpleName() + event.hashCode();
        //数据缓存一小时，若一小时无操作，清空该数据
        Cache.ValueWrapper cachedGeneId = cache.get(key);
        if (cachedGeneId != null){
            List<String> resultGeneCollection = (List<String>) cachedGeneId.get();
            return ResultUtil.success(resultGeneCollection);
        } else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    /**
     * 下载排序的结果
     */
    @RequestMapping(value = "/download-sort", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<String> downloadSortResult(@RequestBody SortRequestParam sortRequestParam, HttpServletRequest request){
        AllSortedResultEvent event=new AllSortedResultEvent(sortRequestParam.getGeneIdList(),sortRequestParam.getTissue(),sortRequestParam.getTraitCategoryId(),null);
        String key = event.getClass().getSimpleName() + event.hashCode();
        Cache.ValueWrapper cachedGeneId = cache.get(key);
        if (cachedGeneId != null){
            List<SortedResult> sortedResults=(List<SortedResult>) cachedGeneId.get();
            String content=getExportContent(sortedResults);
            String fileName=System.currentTimeMillis()+"_"+ UUID.randomUUID().toString()+".csv";
            String filePath = request.getSession().getServletContext().getRealPath("/") + "tempFile/";
            // 通过IO，将输出内容写入到文件中
            File tempFile = new File(filePath + fileName);
            try {
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(content.getBytes("gbk"));
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String contextPath = request.getContextPath();
            //重构请求URL
            StringBuilder builder = new StringBuilder();
            builder.append(contextPath);
            String path = builder.toString() + "/tempFile/" + fileName;
            logger.info(path);
            return ResultUtil.success(path);
        }else {
            logger.warn("缓存数据已清空，请重新查询后排序");
            return ResultUtil.error(-1, "数据已过期，请重新搜索获取数据");
        }
    }

    private static String getExportContent(List<SortedResult> sortedResults){
        String[] titles=new String[]{"geneId","geneName","description","chromosome","location"};
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<titles.length;i++){
            if(i==titles.length-1){
                sb.append(titles[i]).append("\n");
            }else {
                sb.append(titles[i]).append(",");
            }
        }
        for(int i=0;i<sortedResults.size();i++){
            SortedResult sortedResult = sortedResults.get(i);
            String geneName = sortedResult.getGeneName();
            if(geneName!=null&&geneName.contains(",")){
                geneName=StringUtils.replace(geneName,",",";");
            }
            String description = sortedResult.getDescription();
            if(description!=null&&description.contains(",")){
                description=StringUtils.replace(description,",",";");
            }
            sb.append(sortedResult.getGeneId()).append(",").append(geneName).append(",").append(description).append(",")
                    .append(sortedResult.getChromosome()).append(",").append(sortedResult.getLocation()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 在MVC容器中拿Root容器中注册的Bean
        cache = geneSortUtils.getCacheInsideContextContainer();
        mapper = new ObjectMapper();
        //使私有变量或私有类对JsonMapper可见
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    private class SimpleTraitCategory{
        /**
         * 组织类型ID(主键)
         */
        private Integer traitCategoryId;
        /**
         * 中文描述
         */
        private String qtlDesc;

        public SimpleTraitCategory(Integer traitCategoryId, String qtlDesc) {
            this.traitCategoryId = traitCategoryId;
            this.qtlDesc = qtlDesc;
        }
    }
}
