package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.IDAndNameSearchViewEvent;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("nameListener")
public class FetchIdAndFuncSearchViewListener extends AbstractSearchViewListener implements EventBusListener {
    @Autowired
    private DNAGenBaseInfoService genBaseInfoService;

    @AllowConcurrentEvents
    @Subscribe
    public void listenNameEvent(IDAndNameSearchViewEvent event){
        List<Integer> idList = event.getId();
        List<String> allGeneId = genBaseInfoService.findAllGeneId(idList);
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.put(key, allGeneId);
    }
}
