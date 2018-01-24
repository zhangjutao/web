package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("fetAllListener")
public class FetchAllAdvanceSearchViewListener implements EventBusListener, InitializingBean {

    @Autowired
    private CacheManager cacheManager;

    private Cache cache;

    @Autowired
    private FPKMDao fpkmDao;

    @Subscribe
    public void listen(AllAdvanceSearchViewEvent event){
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(),
                        event.getFirstHierarchyQtlId(), event.getSelectQTL(), event.getBaseInfo(), event.getStructure());
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
        cache.putIfAbsent(event.toString(), resultGeneCollection);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("advanceSearch");
    }
}
