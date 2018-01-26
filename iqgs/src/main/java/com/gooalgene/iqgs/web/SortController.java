package com.gooalgene.iqgs.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.sort.SortRequestParam;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.IDAndNameSearchViewEvent;
import com.gooalgene.iqgs.service.sort.GeneSortViewService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.gooalgene.utils.ConsequenceTypeUtils;
import com.gooalgene.utils.ResultUtil;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        PageInfo<SortedResult> resultPage = geneSortViewService.findViewByGeneId(sortRequestParam.getGeneIdList(), sortRequestParam.getTissue(),
                sortRequestParam.getTraitCategoryId(), sortRequestParam.getPageNo(), sortRequestParam.getPageSize());
        return ResultUtil.success(resultPage);
    }

    @RequestMapping(value = "/copy-ordered-geneId", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<List<String>> copyOrderedGeneId(@RequestBody SortRequestParam sortRequestParam) {
        PageInfo<SortedResult> allOrderedGene = geneSortViewService.findViewByGeneId(sortRequestParam.getGeneIdList(), sortRequestParam.getTissue(), sortRequestParam.getTraitCategoryId(), 1, sortRequestParam.getGeneIdList().size());
        List<String> geneIdList = new ArrayList<String>();
        for (SortedResult sortedResult : allOrderedGene.getList()) {
            geneIdList.add(sortedResult.getGeneId());
        }
        return ResultUtil.success(geneIdList);
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
        AllAdvanceSearchViewEvent event = new AllAdvanceSearchViewEvent(entities, selectSnp,
                selectIndel, firstHierarchyQtlId, associateGeneIdArray, baseInfo, geneStructure);
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
        DNAGenStructure dnaGenStructure = new DNAGenStructure();
        dnaGenStructure.setChromosome(chr);
        dnaGenStructure.setStart(Long.valueOf(start));
        dnaGenStructure.setEnd(Long.valueOf(end));
        AllRegionSearchResultEvent event = new AllRegionSearchResultEvent(dnaGenStructure, null);
        String key = event.getClass().getSimpleName() + event.getGenStructure().hashCode();
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
        if (searchType.equals("1")){
            bean.setGeneId(keyword);
            bean.setGeneOldId(keyword);
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
        AllAdvanceSearchViewEvent event = new AllAdvanceSearchViewEvent(null, null, null, null, Arrays.asList(chosenQtl), null, null);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("advanceSearch");
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
