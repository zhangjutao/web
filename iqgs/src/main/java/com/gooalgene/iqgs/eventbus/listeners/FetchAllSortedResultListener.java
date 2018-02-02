package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.iqgs.dao.UserAssociateTraitFpkmDao;
import com.gooalgene.iqgs.entity.sort.CalculateScoreResult;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.entity.sort.UserAssociateTraitFpkm;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllSortedResultEvent;
import com.gooalgene.iqgs.service.sort.GeneSortUtils;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取所有排序完成后的基因基本信息
 */
@Service("sortedResultListener")
public class FetchAllSortedResultListener extends AbstractSearchViewListener implements EventBusListener {

    @Autowired
    private UserAssociateTraitFpkmDao userAssociateTraitFpkmDao;

    @Autowired
    private GeneSortUtils geneSortUtils;

    @AllowConcurrentEvents
    @Subscribe
    public void listenSortedResult(AllSortedResultEvent event){
        List<CalculateScoreResult> sortedResult = event.getSortedResult();
        String key = event.getClass().getSimpleName() + event.hashCode();
        List<SortedResult> finalSortedResult = geneSortUtils.convertSearchResultToView(sortedResult);
        cache.put(key, finalSortedResult);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void listenUserResult(UserAssociateTraitFpkm event){
        userAssociateTraitFpkmDao.insertSelective(event);
    }
}
