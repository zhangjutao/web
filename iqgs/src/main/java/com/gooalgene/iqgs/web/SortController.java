package com.gooalgene.iqgs.web;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.iqgs.entity.sort.SortRequestParam;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.service.sort.GeneSortViewService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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
