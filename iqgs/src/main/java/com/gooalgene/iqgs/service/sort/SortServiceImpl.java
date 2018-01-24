package com.gooalgene.iqgs.service.sort;

import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortServiceImpl implements SortService, InitializingBean {

    @Autowired
    private CacheManager cacheManager;

    private Cache snpCache;

    private Cache indelCache;

    public int calculateSNPConsequenceTypeScore(List<String> consequenceTypes){
        int snpConsequenceTypeResult = 0;
        if (consequenceTypes == null || consequenceTypes.size() == 0){
            return 0;
        }
        for (int i = 0; i < consequenceTypes.size(); i++){
            Cache.ValueWrapper num = snpCache.get(consequenceTypes.get(i));
            int currentScore = (int) num.get();
            snpConsequenceTypeResult += currentScore;
        }
        return snpConsequenceTypeResult;
    }

    public int calculateINDELConsequenceTypeScore(List<String> consequenceTypes){
        int snpConsequenceTypeResult = 0;
        if (consequenceTypes == null || consequenceTypes.size() == 0){
            return 0;
        }
        for (int i = 0; i < consequenceTypes.size(); i++){
            Cache.ValueWrapper num = indelCache.get(consequenceTypes.get(i));
            int currentScore = (int) num.get();
            snpConsequenceTypeResult += currentScore;
        }
        return snpConsequenceTypeResult;
    }

    @Override
    public int sort(SortedSearchResultView view) {
        List<String> indelConsequenceTypes = view.getIndelConsequenceType();
        List<String> snpConsequenceTypes = view.getSnpConsequenceType();
        int total = calculateINDELConsequenceTypeScore(indelConsequenceTypes) + calculateSNPConsequenceTypeScore(snpConsequenceTypes);

        return 0;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        snpCache = cacheManager.getCache("snpCache");
        indelCache = cacheManager.getCache("indelCache");
    }
}
