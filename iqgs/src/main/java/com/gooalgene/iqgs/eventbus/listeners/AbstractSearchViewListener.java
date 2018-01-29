package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import javafx.application.Application;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class AbstractSearchViewListener implements InitializingBean, Transformer {
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ApplicationContext context;

    protected Cache cache;

    @Override
    public List<String> transformViewToId(List<AdvanceSearchResultView> searchResult){
        List<String> resultGeneCollection = new ArrayList<>();
        if (searchResult != null && searchResult.size() > 0){
            Collection<String> geneIdCollection = Collections2.transform(searchResult, new Function<AdvanceSearchResultView, String>() {
                @Override
                public String apply(AdvanceSearchResultView input) {
                    return input.getGeneId();
                }
            });
            resultGeneCollection.addAll(geneIdCollection);
        }
        return resultGeneCollection;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (context.getParent() != null) {
            cache = cacheManager.getCache("sortCache");
        }
    }
}
