package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("advanceListener")
public class FetchAllAdvanceSearchViewListener extends AbstractSearchViewListener implements EventBusListener {

    @Autowired
    private FPKMDao fpkmDao;

    @AllowConcurrentEvents
    @Subscribe
    public void listenAdvanceSearch(AllAdvanceSearchViewEvent event){
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(),
                        event.getFirstHierarchyQtlId(), event.getSelectQTL(), event.getBaseInfo(), event.getStructure());
        List<String> resultGeneCollection = transformViewToId(searchResult);
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.putIfAbsent(key, resultGeneCollection);
    }

}
