package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.common.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllQTLSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("regionListener")
public class FetchAllRegionViewListener extends AbstractSearchViewListener implements EventBusListener {
    @Autowired
    private FPKMDao fpkmDao;

    @AllowConcurrentEvents
    @Subscribe
    public void listenRegionSearch(AllRegionSearchResultEvent event){
        String chromosome = event.getChromosome();
        int start = event.getStart();
        int end = event.getEnd();
        List<String> searchResult = fpkmDao.cacheSearchByRegion(chromosome, start, end);
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.put(key, searchResult);
    }

    /**
     * 监听所有QTL一级搜索结果列表获取事件
     */
    @AllowConcurrentEvents
    @Subscribe
    public void listenQTLSearch(AllQTLSearchResultEvent event){
        List<Integer> selectedQtl = event.getSelectedQtl();
        List<String> searchResult = fpkmDao.cacheFindViewByQtl(selectedQtl);
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.put(key, searchResult);
    }
}
