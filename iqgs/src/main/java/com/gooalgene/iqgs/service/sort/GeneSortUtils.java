package com.gooalgene.iqgs.service.sort;

import com.gooalgene.iqgs.dao.GeneSortDao;
import com.gooalgene.iqgs.entity.sort.CalculateScoreResult;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 基因排序工具类
 */
@Service
public class GeneSortUtils implements InitializingBean {

    @Autowired
    private GeneSortDao geneSortDao;

    @Autowired
    private CacheManager cacheManager;

    private Ordering<Comparable> comparableOrdering = Ordering.natural();

    /**
     * 排序规则, 0(false):降序(true)， 1:升序
     */
    private boolean sortedOrder;

    /**
     * 将从数据库中搜索的结果转换为前端页面视图
     * @param calculateSortedResult 数据库查询结果，包含geneId、score字段
     * @return 排完序后的结果视图
     */
    public synchronized List<SortedResult> convertSearchResultToView(final List<CalculateScoreResult> calculateSortedResult){
        Collection<String> transformResult = Collections2.transform(calculateSortedResult, new Function<CalculateScoreResult, String>() {
            @Override
            public String apply(CalculateScoreResult input) {
                return input.getGeneId();
            }
        });
        //获取基因基本信息，这里会输入集合的顺序，需要重新对输出到页面的基因进行重排序
        List<SortedResult> sortedResultWithNoScore = geneSortDao.findSortedResultThroughGeneId(new ArrayList<>(transformResult));
        Collection<SortedResult> finalSortedResult = Collections2.transform(sortedResultWithNoScore, new Function<SortedResult, SortedResult>() {
            @Override
            public SortedResult apply(SortedResult input) {
                String geneId = input.getGeneId();
                CalculateScoreResult result = getSortedResultIfExists(calculateSortedResult, geneId);
                input.setScore(result.getScore());
                return input;
            }
        });
        List<SortedResult> finalSortedResultList = new ArrayList<>(finalSortedResult);  //Collection无法被Collections使用，需转换为List
        Ordering<SortedResult> sortedResultOrdering = comparableOrdering.onResultOf(new Function<SortedResult, Double>() {
            @Override
            public Double apply(SortedResult input) {
                return input.getScore();
            }
        });
        Collections.sort(finalSortedResultList, sortedResultOrdering);
        return finalSortedResultList;
    }

    /**
     * 判断从数据库中搜索的结果中是否包含指定基因ID
     * @param originPageResult 数据库搜索结果
     * @param geneId 指定基因ID
     * @return 如果存在则返回原始搜索结果，否则返回null
     */
    private CalculateScoreResult getSortedResultIfExists(List<CalculateScoreResult> originPageResult, final String geneId){
        Collection<CalculateScoreResult> filterResult = Collections2.filter(originPageResult, new Predicate<CalculateScoreResult>() {
            @Override
            public boolean apply(CalculateScoreResult input) {
                return input.getGeneId().equals(geneId);
            }
        });
        return new ArrayList<>(filterResult).get(0);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Cache configCache = cacheManager.getCache("config");
        sortedOrder = configCache.get("sortedOrder") != null && Integer.parseInt((String) configCache.get("sortedOrder").get()) == 1;
        //确定排序升序或降序，动态配置
        if (!sortedOrder){
            comparableOrdering = comparableOrdering.reverse();
        }
    }
}
