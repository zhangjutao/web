package com.gooalgene.iqgs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.dna.service.DNAGenStructureService;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.FpkmDto;
import com.gooalgene.iqgs.entity.FpkmDto;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.Tissue;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.sort.SortedSearchResultView;
import com.gooalgene.iqgs.eventbus.EventBusRegister;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.IDAndNameSearchViewEvent;
import com.gooalgene.iqgs.service.concurrent.ThreadManager;
import com.gooalgene.iqgs.service.concurrent.TimeConsumingJob;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.*;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
@DependsOn(value = {"configAfterTomcatStartup"})
public class FPKMService implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(FPKMService.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private DNAGenStructureService dnaGenStructureService;

    @Autowired
    private CacheManager manager;

    @Autowired
    private EventBusRegister register;

    @Autowired
    private ThreadManager threadManager;

    private Cache cache;

    private boolean globalCacheSign;

    private Thread initDataThread = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (context.getParent() != null) {
            cache = manager.getCache("advanceSearch");
            Cache configCache = manager.getCache("config");
            final boolean cacheChromosome = Integer.parseInt((String) configCache.get("initCache").get()) == 1;  //动态配置是否启动时缓存染色体
            globalCacheSign = cacheChromosome;
            initDataThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (cacheChromosome) {
                        initDataToCache();
                    } else {
                        logger.warn("不缓存目标染色体");
                    }
                }
            });
            initDataThread.start();
        }
    }

    private void initDataToCache(){
        //将基因机构数据加载到缓存中
        Map<String, List<DNAGenStructure>> allChromosomeAndId = dnaGenStructureService.fetchAllChromosomeAndID();
        Set<Map.Entry<String, List<DNAGenStructure>>> entries = allChromosomeAndId.entrySet();
        Iterator<Map.Entry<String, List<DNAGenStructure>>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<DNAGenStructure>> next = iterator.next();
            final String chromosome = next.getKey();
            final List<DNAGenStructure> includeGeneId = next.getValue();
            final List<AdvanceSearchResultView> advanceSearchResultViews = new ArrayList<>();
            LoadThreadCallable callable = new LoadThreadCallable(10, includeGeneId);  //优先级设置稍微低点
            logger.warn("执行" + chromosome + "写入缓存");
            try {
                Set<AdvanceSearchResultView> executeResult = threadManager.submitTask(callable);
                advanceSearchResultViews.addAll(executeResult);
                logger.warn(chromosome + "完成写入缓存");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            cache.put(chromosome, advanceSearchResultViews);
        }
    }

    private class LoadThreadCallable extends TimeConsumingJob<Set<AdvanceSearchResultView>> {
        private List<DNAGenStructure> includeGeneId;

        public LoadThreadCallable(int priority, List<DNAGenStructure> includeGeneId) {
            super(priority);
            this.includeGeneId = includeGeneId;
        }

        @Override
        public Set<AdvanceSearchResultView> call() throws Exception {
            int singleLoop = includeGeneId.size();
            logger.info(Thread.currentThread().getName() + "正在执行查找任务，查找总量为：" + singleLoop);
            Set<AdvanceSearchResultView> result = new HashSet<>();
            //对小于10000的数据总量不采用循环获取方式
            if (singleLoop > 10000){
                singleLoop = singleLoop / 30 + 1;
                int end = 0;
                for (int i = 0; i < 30; i++){
                    end = singleLoop*(i+1) > includeGeneId.size() ? includeGeneId.size() : singleLoop*(i+1);
                    //单次遍历可以保证返回的该次几百个基因数据的唯一性,该种唯一性可以通过SQL group操作完成
                    //但是由于这里为了减小数据库查询压力,使用遍历方式,逐步查询最后写入到一个大的集合中,
                    //那就破坏了最初数据唯一性,出现部分重复的基因ID,因为多次的group可没法保障,
                    //这样就只能通过程序数据结构来控制,因为要使用Set类型来限制数据的唯一性
                    //由于Set中元素的hash通过是来自父类的,那这里就是来自Tissue的,如果两个对象属性相同,
                    //那么它们的equals方法一定是相同的,所以尽可能放行的使用它们equals方法
                    List<AdvanceSearchResultView> singleLoopAdvanceSearchResultViews =
                            fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, includeGeneId.subList(singleLoop * i, end));
                    result.addAll(singleLoopAdvanceSearchResultViews);
                }
            }else {
                List<AdvanceSearchResultView> searchResultViews = fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, includeGeneId);
                result.addAll(searchResultViews);
            }
            return result;
        }

        public List<DNAGenStructure> getIncludeGeneId() {
            return includeGeneId;
        }

        public void setIncludeGeneId(List<DNAGenStructure> includeGeneId) {
            this.includeGeneId = includeGeneId;
        }
    }

    /**
     * Search By Name/ID OR Search By Function搜索接口
     * @param baseInfo 用户输入的基因基本信息实体
     */
    public PageInfo<AdvanceSearchResultView> searchByIdOrFunction(DNAGenBaseInfo baseInfo, int pageNo, int pageSize){
        checkNotNull(baseInfo);
        Page<AdvanceSearchResultView> page = new Page<>(pageNo, pageSize, false);
        List<Integer> properGeneIdList = null;
        String geneId = baseInfo.getGeneId();  //用户输入的geneId，这里需要调正则匹配服务
        //先判断是根据ID/name查询还是根据function查询，拿到基因ID集合
        if (geneId != null && !geneId.trim().equals("")){  //如果用户输入为geneFunction，这里不存在geneId
            List<String> matchedGene = GeneRegexpService.interpretGeneInput(geneId);  //基因匹配结果
            if (matchedGene != null && matchedGene.size() > 0) {
                //先取第一个匹配到的值
                baseInfo.setGeneId(matchedGene.get(0));
                baseInfo.setGeneOldId(matchedGene.get(0));
            }
        }
        //如果是function，那么ID/name均为null，直接拿到从前台传过来的DNAGenBaseInfo即可，这里获取到符合条件的基因ID集合
        properGeneIdList = dnaGenBaseInfoDao.findProperGeneId(baseInfo);
        if (properGeneIdList.size() == 0){
            logger.warn(baseInfo + "未找到合适的基因");
            return null;
        }
        IDAndNameSearchViewEvent event = new IDAndNameSearchViewEvent(properGeneIdList, baseInfo);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        eventBus.post(event);
        int total = properGeneIdList.size();
        page.setTotal(total);
        int end = pageNo * pageSize;
        end = end < total ? end : total;  //防止数组越界
        // todo 这里也可以根据ID来做IN查询，目前使用name，但速度过慢
        List<AdvanceSearchResultView> advanceSearchResultViews =
                fpkmDao.fetchFirstHundredGene(null, null, null, null, properGeneIdList.subList((pageNo - 1) * pageSize, end));
        page.addAll(advanceSearchResultViews);
        return new PageInfo<>(page);
    }

    /**
     * 根据染色体类型、长度搜索基因
     * @param genStructure 染色体实体
     */
    public PageInfo<AdvanceSearchResultView> searchByRegion(DNAGenStructure genStructure, int pageNo, int pageSize){
        checkNotNull(genStructure);
        Page<AdvanceSearchResultView> page = new Page<>(pageNo, pageSize, false);
        String chromosome = genStructure.getChromosome();
        //先从基因结构表中查找符合该种查询条件的基因总个数，返回基因结构ID，然后通过结构ID到高级搜索中搜索
        final List<DNAGenStructure> properGeneStructureIdList = dnaGenStructureService.getGeneStructureId(genStructure.getChromosome(), genStructure.getStart(), genStructure.getEnd());
        List<AdvanceSearchResultView> advanceSearchResultViews = new ArrayList<>();
        Cache.ValueWrapper valueWrapper = cache.get(chromosome);
        if (valueWrapper == null){
            //确保缓存数据线程是否还在运行中，防止启动时由于该线程还在跑，但是页面已经有人访问，但是从缓存中找不到值以为缓存值已经被清理了，又重新开始该线程
            if (!initDataThread.isAlive()){
                initDataThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (globalCacheSign) {
                            initDataToCache();
                        } else {
                            logger.warn("无需缓存染色体数据，仅在测试环境下使用!");
                        }
                    }
                });
                initDataThread.start();
            }
            if (properGeneStructureIdList.size() > 0) {
                //保持当前主线程进行数据搜索，如果传入的基因结构集合为空，不进行查询
                advanceSearchResultViews =
                        fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, properGeneStructureIdList);
            }
        }else {
            List<AdvanceSearchResultView> wholeChromosomeSearchResultView = (List<AdvanceSearchResultView>) valueWrapper.get();  //当前染色体查询总量
            Collection<AdvanceSearchResultView> searchResult = Collections2.filter(wholeChromosomeSearchResultView, new Predicate<AdvanceSearchResultView>() {
                @Override
                public boolean apply(AdvanceSearchResultView input) {
                    //通过重写DNAGenStructure类hashCode与equals方法判断该对象是否在搜索集合中
                    String geneId = input.getGeneId();
                    DNAGenStructure structure = new DNAGenStructure();
                    structure.setGeneId(geneId);
                    return properGeneStructureIdList.contains(structure);
                }
            });
            advanceSearchResultViews.addAll(searchResult);
        }
        int total = advanceSearchResultViews.size();
        //通过EventBus将查询结果封装，在另一个线程中处理该事件
        AllRegionSearchResultEvent event = new AllRegionSearchResultEvent(genStructure, advanceSearchResultViews);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        eventBus.post(event);
        page.setTotal(total);
        int end = pageNo*pageSize > total ? total : pageNo*pageSize;  //防止数组越界
        page.addAll(advanceSearchResultViews.subList((pageNo-1)*pageSize, end));
        return new PageInfo<>(page);
    }

    /**
     * 根据用户选择的基因表达量、SNP、INDEL，筛选出对应基因
     * @param condition 基因表达量
     * @param selectSnp 选择的SNP name集合
     * @param selectIndel 选择的INDEL name集合
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return 符合条件基因ID集合
     */
    public PageInfo<AdvanceSearchResultView> findProperGeneUnderSampleRun(List<GeneExpressionConditionEntity> condition,
                                                                      List<String> selectSnp,
                                                                      List<String> selectIndel,
                                                                      List<Integer> firstHierarchyQtlId,
                                                                      List<Integer> selectQTL,
                                                                      DNAGenBaseInfo baseInfo,
                                                                      DNAGenStructure structure,
                                                                      int pageNo,
                                                                      int pageSize){
        PageHelper.startPage(pageNo, pageSize, true);
        //QTL查询高级搜索
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, baseInfo, structure);
        //通过EventBus将该参数封装，发送一个异步事件，在该事件中执行读取所有符合条件基因实体
        AllAdvanceSearchViewEvent event = new AllAdvanceSearchViewEvent(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, baseInfo, structure);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        eventBus.post(event);
        return new PageInfo<>(searchResult);
    }

    public PageInfo<AdvanceSearchResultView> findViewByRange(String chromosome, int start, int end, int pageNo, int pageSize){
        PageHelper.startPage(pageNo, pageSize);
        List<AdvanceSearchResultView> result = fpkmDao.searchByRegion(chromosome, start, end);
        return new PageInfo<>(result);
    }

    public boolean checkExistSNP(String fpkmGeneId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmGeneId, snpConsequenceType);
    }

}
