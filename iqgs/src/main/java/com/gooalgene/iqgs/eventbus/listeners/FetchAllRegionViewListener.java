package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("regionListener")
public class FetchAllRegionViewListener extends AbstractSearchViewListener implements EventBusListener {

    @AllowConcurrentEvents
    @Subscribe
    public void listenRegionSearch(AllRegionSearchResultEvent event){
        List<AdvanceSearchResultView> searchResultViews = event.getSearchResultViews();
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.put(key, transformViewToId(searchResultViews));
    }
}
