package com.gooalgene.iqgs.eventbus.listeners;

import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.AdvanceSearchType;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.eventbus.EventBusListener;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.GenePreFetchEvent;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final static Logger logger = LoggerFactory.getLogger(FetchAllAdvanceSearchViewListener.class);

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private TService tService;

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

    @AllowConcurrentEvents
    @Subscribe
    public void listenPreFetchEvent(GenePreFetchEvent<?> event){
        Class<?> type = event.getType();
        String geneId = event.getGeneId();
        String key = geneId + "_" + type.getSimpleName();
        //防止无谓的性能消耗
        if (cache.get(key) == null){
            if (type.equals(GenResult.class)) {
                logger.debug("正在获取" + geneId + " GenResult结果");
                GenResult result = getGeneResult(geneId, 0);
                if (result != null) {
                    cache.put(key, result);
                }
            }
        }
    }

    //独立方法方便递归调用
    private GenResult getGeneResult(String geneId, int times){
        GenResult result = null;
        times++;
        try {
            result = tService.generateData(new String[]{geneId});
        } catch (Exception e){
            logger.error("获取基因FPKM数值报错，3s后重新尝试中!", e.getCause());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();  //该异常无需捕获
            }
            result = getGeneResult(geneId, times);
            if (times > 3){
                return result;
            }
        }
        return result;
    }
}
