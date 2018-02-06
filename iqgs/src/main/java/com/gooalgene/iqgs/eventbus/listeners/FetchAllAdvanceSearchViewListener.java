package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.AdvanceSearchType;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
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
        List<String> searchResult = null;
        AdvanceSearchType type = event.getType();
        if (type.equals(AdvanceSearchType.ID)){
            DNAGenBaseInfo baseInfo = (DNAGenBaseInfo) event.getT();
            String geneId = baseInfo.getGeneId();
            searchResult = fpkmDao.cacheAdvanceSearchByGeneId(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(), event.getFirstHierarchyQtlId(), geneId);
        } else if (type.equals(AdvanceSearchType.NAME)){
            DNAGenBaseInfo baseInfo = (DNAGenBaseInfo) event.getT();
            String function = baseInfo.getFunctions() != null ? baseInfo.getFunctions() : baseInfo.getDescription();
            searchResult = fpkmDao.cacheAdvanceSearchByFunction(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(), event.getFirstHierarchyQtlId(), function);
        } else if (type.equals(AdvanceSearchType.REGION)){
            DNAGenStructure structure = (DNAGenStructure) event.getT();
            String chromosome = structure.getChromosome();
            int start = (int)(long)structure.getStart();
            int end = (int)(long)structure.getEnd();
            searchResult = fpkmDao.cacheAdvanceSearchByRegion(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(), event.getFirstHierarchyQtlId(), chromosome, start, end);
        } else if (type.equals(AdvanceSearchType.QTL)){
            List<Integer> firstHierarchyId = (List<Integer>) event.getT();
            searchResult = fpkmDao.cacheAdvanceSearchByQtl(event.getCondition(), event.getSelectSnp(), event.getSelectIndel(), event.getFirstHierarchyQtlId(), firstHierarchyId);
        } else {
            throw new IllegalArgumentException("请传入合理的事件类型");
        }
        String key = event.getClass().getSimpleName() + event.hashCode();
        cache.putIfAbsent(key, searchResult);
    }

}
