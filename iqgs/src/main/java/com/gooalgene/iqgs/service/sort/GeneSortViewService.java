package com.gooalgene.iqgs.service.sort;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.authority.SecurityUser;
import com.gooalgene.iqgs.dao.GeneSortDao;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.sort.CalculateScoreResult;
import com.gooalgene.iqgs.entity.sort.SortedResult;
import com.gooalgene.iqgs.entity.sort.UserAssociateTraitFpkm;
import com.gooalgene.iqgs.eventbus.EventBusRegister;
import com.gooalgene.iqgs.eventbus.events.AllSortedResultEvent;
import com.gooalgene.iqgs.service.concurrent.ThreadManager;
import com.gooalgene.iqgs.service.concurrent.TimeConsumingJob;
import com.gooalgene.utils.CommonUtil;
import com.google.common.base.Function;
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import static com.gooalgene.common.constant.CommonConstant.SORTEDRESULT;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class GeneSortViewService implements InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(GeneSortViewService.class);

    @Autowired
    private GeneSortDao geneSortDao;

    @Autowired
    private EventBusRegister register;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private ThreadManager manager;

    private final int sortThreadNum = 3;

    @Autowired
    private GeneSortUtils geneSortUtils;

    private Cache cache;

    /**
     * 排序规则, 0(false):降序(true)， 1:升序
     */
    private boolean sortedOrder;

    private Ordering<Comparable> comparableOrdering = Ordering.natural();

    /**
     * 对传入的基因ID进行查询、排序，输出排序后的结果
     * @param geneIds 输入的基因ID
     * @param tissue 用户所选的组织，多种二级组织可以组成一个完整的Tissue对象
     * @param categoryId 用户所选性状对应ID值
     * @return 拍完序的基因基本信息集合
     */
    public PageInfo<SortedResult> findSortedView(List<String> geneIds, Tissue tissue, Integer categoryId, int pageNo, int pageSize){
        AllSortedResultEvent preSortedResult = new AllSortedResultEvent(geneIds, tissue, categoryId, null);
        String cachedSortedKey = preSortedResult.getClass().getSimpleName() + preSortedResult.hashCode();
        Cache.ValueWrapper cachedSortedResult = cache.get(cachedSortedKey);
        List<SortedResult> result = new ArrayList<>();
        List<SortedResult> finalSortedResultList = new ArrayList<>();
        int size;
        int start = (pageNo - 1) * pageSize;
        if (cachedSortedResult != null){  //从缓存中拿结果(已排好序)
            result = (List<SortedResult>) cachedSortedResult.get();
            size = result.size();
            int end = pageNo * pageSize > size ? size : pageNo * pageSize;
            finalSortedResultList = result.subList(start, end);
        } else {
            String fields = getAllValidTissueProperties(tissue);
            List<String> selectedTissue = Arrays.asList(fields.split(","));
            List<CalculateScoreResult> calculateSortedResult = geneSortDao.findCalculateSortedResult(geneIds, selectedTissue, categoryId, selectedTissue.size());
            //发布EventBus异步事件，将该条件的搜索结果缓存到内存中
            CopyOnWriteArrayList<CalculateScoreResult> destList = new CopyOnWriteArrayList<>(calculateSortedResult);
            AllSortedResultEvent param = new AllSortedResultEvent(geneIds, tissue, categoryId, destList);
            String key = param.getClass().getSimpleName() + param.hashCode() + SORTEDRESULT;
            Cache.ValueWrapper valueWrapper = cache.get(key);
            AsyncEventBus asyncEventBus = register.getAsyncEventBus();
            //防止事件重复提交
            if (valueWrapper == null) {
                asyncEventBus.post(param);
                cache.put(key, true);
            }
            size = calculateSortedResult.size();
            //确定排序升序或降序，动态配置
            Ordering<Comparable> comparableOrdering = Ordering.natural();
            if (!sortedOrder) {
                comparableOrdering = comparableOrdering.reverse();
            }
            Ordering<CalculateScoreResult> ordering = comparableOrdering.onResultOf(new Function<CalculateScoreResult, Double>() {
                @Override
                public Double apply(CalculateScoreResult input) {
                    return input.getScore();
                }
            });
            Collections.sort(calculateSortedResult, ordering);
            int end = pageNo * pageSize > size ? size : pageNo * pageSize;
            List<CalculateScoreResult> originPageResult = calculateSortedResult.subList(start, end);
            //转换为结果视图
            finalSortedResultList = geneSortUtils.convertSearchResultToView(originPageResult);
            //记录用户行为
            Integer tempId;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof String&& StringUtils.equals((String)principal,"anonymousUser")){
                tempId= RandomUtils.nextInt(1,1000); //TODO 用户未登录时用随机数生成一个假id，以后改进
            }else {
                tempId=((SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
            }
            UserAssociateTraitFpkm userAssociateTraitFpkm = new UserAssociateTraitFpkm(tempId , categoryId, fields, new Date());
            asyncEventBus.post(userAssociateTraitFpkm);
        }
        PageInfo<SortedResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setList(finalSortedResultList);
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        resultPageInfo.setTotal(size);
        return resultPageInfo;
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

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = cacheManager.getCache("sortCache");
        Cache configCache = cacheManager.getCache("config");
        sortedOrder = configCache.get("sortedOrder") != null && Integer.parseInt((String) configCache.get("sortedOrder").get()) == 1;
        //确定排序升序或降序，动态配置
        if (!sortedOrder){
            comparableOrdering = comparableOrdering.reverse();
        }
    }

    //这里使用多线程，反而因为线程启动和数据拷贝浪费更多时间
    private List<CalculateScoreResult> splitTimeConsumingJob(List<String> geneIds, int categoryId, List<String> fields){
        List<CalculateScoreResult> result = new ArrayList<>();
        if (geneIds == null) return null;
        int size = geneIds.size();
        int singleRun = size / sortThreadNum;
        int end;
        for (int i = 0; i < sortThreadNum; i++){
            end = singleRun * (i + 1) > size ? size : singleRun * (i + 1);
            int start = singleRun * i;
            if (start == end)  //防止出现3的整数倍情况
                continue;
            try {
                List<CalculateScoreResult> singleSortedResults = manager.submitTask(new SortedViewCallable(1, geneIds.subList(start, end), categoryId, fields));
                result.addAll(singleSortedResults);
            } catch (ExecutionException | InterruptedException e) {
                logger.error("执行数据库查询排序错误：", e.getCause());
            }
        }
        return result;
    }

    private class SortedViewCallable extends TimeConsumingJob<List<CalculateScoreResult>> {
        //基因ID集合
        private List<String> geneCollection;

        private int categoryId;

        private List<String> selectedTissue;

        public SortedViewCallable(int priority, List<String> collection, int categoryId, List<String> fields) {
            super(priority);
            this.geneCollection = collection;
            this.categoryId = categoryId;
            this.selectedTissue = fields;
        }

        @Override
        public List<CalculateScoreResult> call() throws Exception {
            List<CalculateScoreResult> views = new ArrayList<>();
            try {
                views = geneSortDao.findCalculateSortedResult(this.geneCollection, selectedTissue, categoryId, selectedTissue.size());
            }catch (Exception sqlSyntaxErrorException){
                logger.error("查询出错,基因集合[：" + Iterables.toString(this.geneCollection) + "]", sqlSyntaxErrorException.getCause());
            }
            return views;
        }
    }
}
