package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllSortedResultEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取所有排序完成后的基因基本信息
 */
@Service("sortedResultListener")
public class FetchAllSortedResultListener extends AbstractSearchViewListener implements EventBusListener {

    @AllowConcurrentEvents
    @Subscribe
    public void listenSortedResult(AllSortedResultEvent event){
        List<SortedResult> sortedResult = event.getSortedResult();
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.put(key, sortedResult);
    }
}
