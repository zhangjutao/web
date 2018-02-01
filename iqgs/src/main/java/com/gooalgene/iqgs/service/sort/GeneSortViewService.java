package com.gooalgene.iqgs.service.sort;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.iqgs.dao.GeneSortDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.sort.*;
import com.gooalgene.iqgs.eventbus.EventBusRegister;
import com.gooalgene.iqgs.eventbus.events.AllSortedResultEvent;
import com.gooalgene.iqgs.service.concurrent.ThreadManager;
import com.gooalgene.iqgs.service.concurrent.TimeConsumingJob;
import com.gooalgene.utils.CommonUtil;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.eventbus.AsyncEventBus;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class GeneSortViewService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(GeneSortViewService.class);

    public final static String CATEGORY = "category";

    @Autowired
    private GeneSortDao geneSortDao;

    @Autowired
    private SortService sortService;

    @Autowired
    private EventBusRegister register;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ThreadManager manager;

    private Cache cache;

    /**
     * 是否返回分数
     */
    private boolean showScore;

    /**
     * 排序规则, 0(false):降序(true)， 1:升序
     */
    private boolean sortedOrder;

    public PageInfo<SortedResult> findSortedView(List<String> geneIds, Tissue tissue, Integer categoryId, int pageNo, int pageSize){
        String fields = getAllValidTissueProperties(tissue);
        List<String> selectedTissue = Arrays.asList(fields.split(","));
        List<CalculateScoreResult> calculateSortedResult = geneSortDao.findCalculateSortedResult(geneIds, selectedTissue, categoryId);
        int size = calculateSortedResult.size();
        //确定排序升序或降序，动态配置
        Ordering<Comparable> comparableOrdering = Ordering.natural();
        if (!sortedOrder){
            comparableOrdering = comparableOrdering.reverse();
        }
        Ordering<CalculateScoreResult> ordering = comparableOrdering.onResultOf(new Function<CalculateScoreResult, Double>() {
            @Override
            public Double apply(CalculateScoreResult input) {
                return input.getScore();
            }
        });
        Collections.sort(calculateSortedResult, ordering);
        int start = (pageNo - 1) * pageSize;
        int end = (pageNo+1)*pageSize > size ? size : (pageNo+1)*pageSize;
        final List<CalculateScoreResult> originPageResult = calculateSortedResult.subList(start, end);
        Collection<String> transformResult = Collections2.transform(originPageResult, new Function<CalculateScoreResult, String>() {
            @Override
            public String apply(CalculateScoreResult input) {
                return input.getGeneId();
            }
        });
        //获取基因基本信息
        List<SortedResult> sortedResultWithNoScore = geneSortDao.findSortedResultThroughGeneId(new ArrayList<>(transformResult));
        Collection<SortedResult> finalSortedResult = Collections2.transform(sortedResultWithNoScore, new Function<SortedResult, SortedResult>() {
            @Override
            public SortedResult apply(SortedResult input) {
                String geneId = input.getGeneId();
                CalculateScoreResult result = getSortedResultIfExists(originPageResult, geneId);
                input.setScore(result.getScore());
                return input;
            }
        });
        PageInfo<SortedResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setList(new ArrayList<>(finalSortedResult));
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        resultPageInfo.setTotal(size);
        return resultPageInfo;
    }

    /**
     * 对传入的基因ID进行查询、排序，输出排序后的结果
     * @param geneIds 输入的基因ID
     * @param tissue 用户所选的组织，多种二级组织可以组成一个完整的Tissue对象
     * @param categoryId 用户所选性状对应ID值
     * @return 拍完序的基因基本信息集合
     */
    public PageInfo<SortedResult> findViewByGeneId(List<String> geneIds, Tissue tissue, Integer categoryId, int pageNo, int pageSize){
        String fields = getAllValidTissueProperties(tissue);
        //从缓存中拿性状对应的QTL
        Cache.ValueWrapper categoryCachedValue = cache.get(CATEGORY + categoryId);
        List<String> qtlNames = new ArrayList<>();
        if (categoryCachedValue == null){
            qtlNames = geneSortDao.getQtlNamesByTrait(categoryId);
        } else {
            qtlNames = (List<String>) categoryCachedValue.get();
        }
        AllSortedResultEvent preSortedResult = new AllSortedResultEvent(geneIds, tissue, categoryId, null);
        String cachedSortedKey = preSortedResult.getClass().getSimpleName() + preSortedResult.hashCode();
        Cache.ValueWrapper cachedSortedResult = cache.get(cachedSortedKey);
        List<SortedResult> result = new ArrayList<>();
        if (cachedSortedResult != null){
            result = (List<SortedResult>) cachedSortedResult.get();
        } else {
            List<SortedSearchResultView> views = splitTimeConsumingJob(geneIds, fields);  //大任务拆分
            try {
                List<SortedSearchResultView> sortResult = sortService.sort(views, qtlNames);
                //确定排序升序或降序，动态配置
                Ordering<Comparable> comparableOrdering = Ordering.natural();
                if (!sortedOrder){
                    comparableOrdering = comparableOrdering.reverse();
                }
                Ordering<SortedSearchResultView> ordering = comparableOrdering.onResultOf(new Function<SortedSearchResultView, Double>() {
                    @Override
                    public Double apply(SortedSearchResultView input) {
                        return input.getScore();
                    }
                });
                Collections.sort(sortResult, ordering);
                Collection<SortedResult> transform = Collections2.transform(sortResult, new Function<SortedSearchResultView, SortedResult>() {
                    @Override
                    public SortedResult apply(SortedSearchResultView input) {
                        DNAGenBaseInfo genBaseInfo = input.getBaseInfo();
                        Double score = null;
                        if (showScore){
                            score = input.getScore();
                        }
                        return new SortedResult(genBaseInfo.getGeneId(), genBaseInfo.getGeneName(),
                                genBaseInfo.getDescription(), input.getChromosome(), input.getLocation(), score);
                    }
                });
                result.addAll(transform);
            } catch (IllegalAccessException e) {
                logger.error("排序过程中出错", e.getCause());
            }
            //发布EventBus异步事件，将该条件的搜索结果缓存到内存中
            AllSortedResultEvent param = new AllSortedResultEvent(geneIds, tissue, categoryId, result);
            AsyncEventBus asyncEventBus = register.getAsyncEventBus();
            asyncEventBus.post(param);
            //记录用户行为
            Integer tempId;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof String&& StringUtils.equals((String)principal,"anonymousUser")){
                tempId= RandomUtils.nextInt(1,1000); //TODO 用户未登录时用随机数生成一个假id，以后改进
            }else {
                tempId=((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            }
            UserAssociateTraitFpkm userAssociateTraitFpkm=new UserAssociateTraitFpkm(
                    tempId
                    ,categoryId,fields,new Date());

            asyncEventBus.post(userAssociateTraitFpkm);
        }
        int size = result.size();
        int end = pageNo*pageSize > size ? size : pageNo*pageSize;
        Page<SortedResult> page = new Page<>(pageNo, pageSize, false);
        page.setTotal(size);
        page.addAll(result.subList((pageNo-1)*pageSize, end));
        return new PageInfo<SortedResult>(page);
    }

    /**
     * 通过反射获取组织中所有非空的属性值
     */
    private String getAllValidTissueProperties(Tissue tissue){
        checkNotNull(tissue);
        Class<? extends Tissue> tissueClass = tissue.getClass();
        Field[] declaredFields = tissueClass.getDeclaredFields();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < declaredFields.length; i++){
            declaredFields[i].setAccessible(true);
            try {
                Object fieldValue = declaredFields[i].get(tissue);
                if (fieldValue != null){
                    String name = declaredFields[i].getName();
                    name= CommonUtil.camelToUnderline(name);
                    builder.append(name);
                    builder.append(",");
                }
            } catch (IllegalAccessException e) {
                logger.error("执行" + declaredFields[i].getName() + "出错", e.getCause());
            }
        }
        if (builder.length() == 0) {
            throw new IllegalArgumentException("传入的Tissue对象中必须包含二级组织");
        }
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }

    private List<SortedSearchResultView> splitTimeConsumingJob(List<String> geneIds, String fields){
        if (geneIds == null) return null;
        List<SortedSearchResultView> result = new ArrayList<>();
        int size = geneIds.size();
        //小数据量直接使用一次查询
        int loopTimes = size / 100 + 1;  //每次100条数据限制
        logger.warn("分解的任务个数为：" + loopTimes);
        int singleRun = 100;
        int end = 0;
        for (int i = 0; i < loopTimes; i++) {
            end = singleRun * (i + 1) > size ? size : singleRun * (i + 1);
            int start = singleRun * i;
            if (start == end)  //防止1000条数据循环到10次时出现start==end情况
                continue;
            try {
                List<SortedSearchResultView> singleSortedSearchResultViews = manager.submitTask(new SortedViewCallable(1, geneIds.subList(singleRun * i, end), fields));
                result.addAll(singleSortedSearchResultViews);
            } catch (InterruptedException | ExecutionException e) {
                logger.error("执行数据库查询排序基因错误", e.getCause());
            }
        }
        return result;
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
        cache = cacheManager.getCache("sortCache");
        Cache configCache = cacheManager.getCache("config");
        showScore = configCache.get("showScore") != null && Integer.parseInt((String) configCache.get("showScore").get()) == 1;
        sortedOrder = configCache.get("sortedOrder") != null && Integer.parseInt((String) configCache.get("sortedOrder").get()) == 1;
    }

    private class SortedViewCallable extends TimeConsumingJob<List<SortedSearchResultView>> {
        //基因ID集合
        private List<String> geneCollection;

        private String fields;

        public SortedViewCallable(int priority, List<String> collection, String fields) {
            super(priority);
            this.geneCollection = collection;
            this.fields = fields;
        }

        @Override
        public List<SortedSearchResultView> call() throws Exception {
            List<SortedSearchResultView> views = new ArrayList<>();
            try {
                views = geneSortDao.findViewByGeneId(this.geneCollection, this.fields);
            }catch (Exception sqlSyntaxErrorException){
                logger.error("查询出错,基因集合[：" + Iterables.toString(views) + "], fields:[" + this.fields + "]", sqlSyntaxErrorException.getCause());
            }
            return views;
        }
    }
}
