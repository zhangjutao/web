package com.gooalgene.iqgs.web;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 排序接口层
 */
@Controller
@RequestMapping("/sort")
public class SortController implements InitializingBean {
    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    /**
     * 获取当前页面所有基因ID
     */
    @RequestMapping(value = "/fetch-all-geneId", method = RequestMethod.POST)
    @ResponseBody
    public List<String> fetchAllCurrentPageGeneId(){
        return null;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("advanceSearch");
    }
}
