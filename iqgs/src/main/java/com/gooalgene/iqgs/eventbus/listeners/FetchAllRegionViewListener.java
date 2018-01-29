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
        //这里后续取只会使用DNAGeneStructure,传入的View是需要被缓存的数据，与Key值无关
        String key = event.getClass().getSimpleName() + event.getGenStructure().hashCode();
        cache.put(key, transformViewToId(searchResultViews));
    }
}
