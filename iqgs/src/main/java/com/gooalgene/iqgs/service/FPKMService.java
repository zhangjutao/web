package com.gooalgene.iqgs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.dna.service.DNAGenStructureService;
import com.gooalgene.entity.Configuration;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.DNAGenStructure;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

import static com.gooalgene.common.constant.CommonConstant.DEFAULTRESULTVIEW;
import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class FPKMService implements InitializingBean, DisposableBean {
    private final static Logger logger = LoggerFactory.getLogger(FPKMService.class);


    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private DNAGenStructureService dnaGenStructureService;

    @Autowired
    private CacheManager manager;

    private Cache cache;

    private ExecutorService threadPool;

    private Thread initDataThread = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = manager.getCache("advanceSearch");
        threadPool = Executors.newFixedThreadPool(10);
        initDataThread = new Thread(new Runnable() {
            @Override
            public void run() {
                initDataToCache();
            }
        });
        initDataThread.start();
    }

    private void initDataToCache(){
        //将基因机构数据加载到缓存中
        Map<String, List<Integer>> allChromosomeAndId = dnaGenStructureService.fetchAllChromosomeAndID();
        Set<Map.Entry<String, List<Integer>>> entries = allChromosomeAndId.entrySet();
        Iterator<Map.Entry<String, List<Integer>>> iterator = entries.iterator();
        while (iterator.hasNext()){
            Map.Entry<String, List<Integer>> next = iterator.next();
            final String chromosome = next.getKey();
            final List<Integer> includeGeneId = next.getValue();
            final List<AdvanceSearchResultView> advanceSearchResultViews = new ArrayList<>();
            LoadThreadCallable callable = new LoadThreadCallable(includeGeneId);
            logger.debug("执行" + chromosome + "写入缓存");
            Future<List<AdvanceSearchResultView>> futureResult = threadPool.submit(callable);
            try {
                List<AdvanceSearchResultView> executeResult = futureResult.get();
                advanceSearchResultViews.addAll(executeResult);
                logger.debug(chromosome + "完成写入缓存");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    List<AdvanceSearchResultView> result = fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, includeGeneId);
//                    advanceSearchResultViews.addAll(result);
//                    logger.info(Thread.currentThread().getName() + "已完成" + chromosome + "数据查询并装入缓存内");
//                }
//            }).start();
            cache.putIfAbsent(chromosome, advanceSearchResultViews);  //将该染色体中所有基因对应的高级搜索结果放入到缓存中
        }
    }

    @Override
    public void destroy() throws Exception {
        threadPool.shutdown();
    }

    private class LoadThreadCallable implements Callable<List<AdvanceSearchResultView>>{
        private List<Integer> includeGeneId;

        public LoadThreadCallable(List<Integer> includeGeneId) {
            this.includeGeneId = includeGeneId;
        }

        @Override
        public List<AdvanceSearchResultView> call() throws Exception {
            int singleLoop = includeGeneId.size();
            logger.info(Thread.currentThread().getName() + "正在执行查找任务，查找总量为：" + singleLoop);
            List<AdvanceSearchResultView> result = new ArrayList<>();
            //对小于10000的数据总量不采用循环获取方式
            if (singleLoop > 10000){
                singleLoop = singleLoop / 30 + 1;
                int end = 0;
                for (int i = 0; i < 30; i++){
                    end = singleLoop*(i+1) > includeGeneId.size() ? includeGeneId.size() : singleLoop*(i+1);
                    //单次遍历结果
                    List<AdvanceSearchResultView> singleLoopAdvanceSearchResultViews =
                            fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, includeGeneId.subList(singleLoop * i, end));
                    result.addAll(singleLoopAdvanceSearchResultViews);
                }
            }else {
                result = fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, includeGeneId);
            }
            return result;
        }

        public List<Integer> getIncludeGeneId() {
            return includeGeneId;
        }

        public void setIncludeGeneId(List<Integer> includeGeneId) {
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
        String geneIdOrName = baseInfo.getGeneId();  //用户输入的geneId，这里需要调正则匹配服务
        //先判断是根据ID/name查询还是根据function查询，拿到基因ID集合
        if (geneIdOrName != null && !geneIdOrName.trim().equals("")){  //如果用户输入为geneFunction，这里不存在geneId
            boolean isGeneId = GeneRegexpService.isGeneId(geneIdOrName);
            if (isGeneId) {
                List<String> matchedGene = GeneRegexpService.interpretGeneInput(geneIdOrName);  //基因匹配结果
                if (matchedGene != null && matchedGene.size() > 0) {
                    //先取第一个匹配到的值
                    baseInfo.setGeneId(matchedGene.get(0));
                    baseInfo.setGeneOldId(matchedGene.get(0));
                }
            } else {
                baseInfo.setGeneId(null);
                baseInfo.setGeneName(geneIdOrName);
            }
        }
        //如果是function，那么ID/name均为null，直接拿到从前台传过来的DNAGenBaseInfo即可，这里获取到符合条件的基因ID集合
        properGeneIdList = dnaGenBaseInfoDao.findProperGeneId(baseInfo);
        int total = properGeneIdList.size();
        page.setTotal(total);
        int end = pageNo * pageSize;
        end = end < total ? end : total;  //防止数组越界
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
        final List<Integer> properGeneStructureIdList = dnaGenStructureService.getGeneStructureId(genStructure.getChromosome(), genStructure.getStart(), genStructure.getEnd());
        List<AdvanceSearchResultView> advanceSearchResultViews = new ArrayList<>();
        Cache.ValueWrapper valueWrapper = cache.get(chromosome);
        if (valueWrapper == null){
            //确保缓存数据线程是否还在运行中，防止启动时由于该线程还在跑，但是页面已经有人访问，但是从缓存中找不到值以为缓存值已经被清理了，又重新开始该线程
            if (!initDataThread.isAlive()){
                initDataThread.start();
            }
            //保持当前主线程进行数据搜索
            advanceSearchResultViews =
                    fpkmDao.fetchFirstHundredGeneInGeneStructure(null, null, null, null, properGeneStructureIdList);
        }else {
            List<AdvanceSearchResultView> wholeChromosomeSearchResultView = (List<AdvanceSearchResultView>) valueWrapper.get();  //当前染色体查询总量
            Collection<AdvanceSearchResultView> searchResult = Collections2.filter(wholeChromosomeSearchResultView, new Predicate<AdvanceSearchResultView>() {
                @Override
                public boolean apply(AdvanceSearchResultView input) {
                    Integer structureId = input.getStructureId();
                    return properGeneStructureIdList.contains(structureId);
                }
            });
            advanceSearchResultViews.addAll(searchResult);
        }
        int total = advanceSearchResultViews.size();
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
                                                                      int pageNo,
                                                                      int pageSize){
        //QTL查询高级搜索
        List<AdvanceSearchResultView> searchResult =
                fpkmDao.findGeneThroughGeneExpressionCondition(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, null, null);
        return new PageInfo<>(searchResult);
    }

    public boolean checkExistSNP(int fpkmId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmId, snpConsequenceType);
    }
}
