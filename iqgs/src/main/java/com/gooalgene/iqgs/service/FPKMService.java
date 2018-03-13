package com.gooalgene.iqgs.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.AdvanceSearchType;
import com.gooalgene.iqgs.entity.ConsequenceEntity;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.condition.RangeSearchResult;
import com.gooalgene.common.eventbus.EventBusRegister;
import com.gooalgene.iqgs.eventbus.events.AllAdvanceSearchViewEvent;
import com.gooalgene.iqgs.eventbus.events.AllQTLSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.AllRegionSearchResultEvent;
import com.gooalgene.iqgs.eventbus.events.IDAndNameSearchViewEvent;
import com.google.common.eventbus.AsyncEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private CacheManager manager;

    @Autowired
    private EventBusRegister register;

    private Cache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (context.getParent() != null) {
            cache = manager.getCache("config");
            cacheAllSNPAndINDEL();
        }
    }

    private void cacheAllSNPAndINDEL(){
        List<ConsequenceEntity> allSNPConsequenceType = fpkmDao.getAllConsequenceTypeAndItsId("SNP");
        List<ConsequenceEntity> allINDELConsequenceType = fpkmDao.getAllConsequenceTypeAndItsId("INDEL");
        cache.putIfAbsent(CommonConstant.CACHEDSNP, convertConsequenceEntityToMap(allSNPConsequenceType));
        cache.putIfAbsent(CommonConstant.CACHEDINDEL, convertConsequenceEntityToMap(allINDELConsequenceType));
    }

    public Map<String, Integer> convertConsequenceEntityToMap(List<ConsequenceEntity> entities){
        Map<String, Integer> result = new HashMap<>();
        for (ConsequenceEntity entity : entities){
            result.put(entity.getConsequenceType(), entity.getId());
        }
        return result;
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
     * 根据用户选择的基因表达量、SNP、INDEL，筛选出对应基因,
     * 目前使用入参中firstHierarchyQtlId、baseinfo、structure做查询分发,也就是传入的三个参数中另外两个肯定为null
     * @param condition 基因表达量
     * @param selectSnp 选择的SNP name集合
     * @param selectIndel 选择的INDEL name集合
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @return 符合条件基因ID集合
     */
    public PageInfo<RangeSearchResult> findProperGeneUnderSampleRun(List<GeneExpressionConditionEntity> condition,
                                                                      List<String> selectSnp,
                                                                      List<String> selectIndel,
                                                                      List<Integer> firstHierarchyQtlId,
                                                                      List<Integer> selectQTL,
                                                                      DNAGenBaseInfo baseInfo,
                                                                      DNAGenStructure structure,
                                                                      int pageNo,
                                                                      int pageSize){
        List<Integer> allSNPId = getAllSelectedConsequenceTypeId("SNP", selectSnp);
        List<Integer> allINDELId = getAllSelectedConsequenceTypeId("INDEL", selectIndel);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        if (firstHierarchyQtlId != null && firstHierarchyQtlId.size() > 0){
            AllAdvanceSearchViewEvent<List<Integer>> advanceSearchQTLEvent = new AllAdvanceSearchViewEvent<>(condition, allSNPId, allINDELId, selectQTL, firstHierarchyQtlId, AdvanceSearchType.QTL);
            eventBus.post(advanceSearchQTLEvent);
            return advanceSearchByQtl(condition, allSNPId, allINDELId, firstHierarchyQtlId, selectQTL, pageNo, pageSize);
        } else if (structure != null){
            String chromosome = structure.getChromosome();
            int start = (int)(long)structure.getStart();
            int end = (int)(long)structure.getEnd();
            AllAdvanceSearchViewEvent<DNAGenStructure> advanceSearchRegionEvent = new AllAdvanceSearchViewEvent<>(condition, allSNPId, allINDELId, selectQTL, structure, AdvanceSearchType.REGION);
            eventBus.post(advanceSearchRegionEvent);
            return advanceSearchByRegion(condition, allSNPId, allINDELId, selectQTL, chromosome, start, end, pageNo, pageSize);
        } else if (baseInfo != null && baseInfo.getGeneId() != null){
            String geneId = baseInfo.getGeneId();
            AllAdvanceSearchViewEvent<DNAGenBaseInfo> advanceSearchIdEvent = new AllAdvanceSearchViewEvent<>(condition, allSNPId, allINDELId, selectQTL, baseInfo, AdvanceSearchType.ID);
            eventBus.post(advanceSearchIdEvent);
            return advanceSearchByGeneId(condition, allSNPId, allINDELId, selectQTL, geneId, pageNo, pageSize);
        } else if (baseInfo != null && (baseInfo.getDescription() != null || baseInfo.getFunctions() != null)){  //接受description、function任意参数
            AllAdvanceSearchViewEvent<DNAGenBaseInfo> advanceSearchIdEvent = new AllAdvanceSearchViewEvent<>(condition, allSNPId, allINDELId, selectQTL, baseInfo, AdvanceSearchType.NAME);
            eventBus.post(advanceSearchIdEvent);
            String function = baseInfo.getFunctions() != null ? baseInfo.getFunctions() : baseInfo.getDescription();
            return advanceSearchByFunction(condition, allSNPId, allINDELId, selectQTL, function, pageNo, pageSize);
        } else {
            throw new IllegalArgumentException("请求参数不正确，高级搜索必须包含或GeneId，或QTL，或GeneFunction、或染色体区间查询条件");
        }
    }

    private PageInfo<RangeSearchResult> advanceSearchByFunction(List<GeneExpressionConditionEntity> condition,
                                                              List<Integer> selectSnp,
                                                              List<Integer> selectIndel,
                                                              List<Integer> selectQTL,
                                                              String function, int pageNo, int pageSize){
        int start = (pageNo - 1) * pageSize;
        int total = fpkmDao.countAdvanceSearchByFunction(condition, selectSnp, selectIndel, selectQTL, function);
        List<RangeSearchResult> searchResult = fpkmDao.advanceSearchByFunction(condition, selectSnp, selectIndel, selectQTL, function, start, pageSize);
        PageInfo<RangeSearchResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setTotal(total);
        resultPageInfo.setList(searchResult);
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        return resultPageInfo;
    }

      private PageInfo<RangeSearchResult> advanceSearchByRegion(List<GeneExpressionConditionEntity> condition,
      List<Integer> selectSnp,
      List<Integer> selectIndel,
      List<Integer> selectQTL,
      String chromosome,
      int geneStart, int geneEnd,
      int pageNo, int pageSize){
        int start = (pageNo - 1) * pageSize;
        int total = fpkmDao.countAdvanceSearchByRegion(condition, selectSnp, selectIndel, selectQTL, chromosome, geneStart, geneEnd);
        List<RangeSearchResult> searchResult = fpkmDao.advanceSearchByRegion(condition, selectSnp, selectIndel, selectQTL, chromosome, geneStart, geneEnd, start, pageSize);
        PageInfo<RangeSearchResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setTotal(total);
        resultPageInfo.setList(searchResult);
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        return resultPageInfo;
    }


    private PageInfo<RangeSearchResult> advanceSearchByGeneId(List<GeneExpressionConditionEntity> condition,
                                                              List<Integer> selectSnp,
                                                              List<Integer> selectIndel,
                                                              List<Integer> selectQTL,
                                                              String geneId,
                                                              int pageNo, int pageSize){
        int start = (pageNo - 1) * pageSize;
        int total = fpkmDao.countAdvanceSearchByGeneId(condition, selectSnp, selectIndel, selectQTL, geneId);
        List<RangeSearchResult> searchResult = fpkmDao.advanceSearchByGeneId(condition, selectSnp, selectIndel, selectQTL, geneId, start, pageSize);
        PageInfo<RangeSearchResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setTotal(total);
        resultPageInfo.setList(searchResult);
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        return resultPageInfo;
    }

    /**
     * QTL一级搜索对应的高级搜索功能
     */
    private PageInfo<RangeSearchResult> advanceSearchByQtl(List<GeneExpressionConditionEntity> condition,
                                                           List<Integer> selectSnp,
                                                           List<Integer> selectIndel,
                                                           List<Integer> firstHierarchyQtlId,
                                                           List<Integer> selectQTL, int pageNo, int pageSize){
        int start = (pageNo - 1) * pageSize;
        int total = fpkmDao.countAdvanceSearchByQtl(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL);
        List<RangeSearchResult> searchResult = fpkmDao.advanceSearchByQtl(condition, selectSnp, selectIndel, firstHierarchyQtlId, selectQTL, start, pageSize);
        PageInfo<RangeSearchResult> resultPageInfo = new PageInfo<>();
        resultPageInfo.setTotal(total);
        resultPageInfo.setList(searchResult);
        resultPageInfo.setPageNum(pageNo);
        resultPageInfo.setPageSize(pageSize);
        return resultPageInfo;
    }

    public PageInfo<RangeSearchResult> findViewByRange(String chromosome, int start, int end, int pageNo, int pageSize){
        //mybatis对连表查询无能为力,这里需要手动查询分页
        int total = fpkmDao.countBySearchRegion(chromosome, start, end);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        AllRegionSearchResultEvent event = new AllRegionSearchResultEvent(chromosome, start, end);
        eventBus.post(event);
        int pageStart = (pageNo - 1) * pageSize;
        List<RangeSearchResult> result = fpkmDao.searchByRegion(chromosome, start, end, pageStart, pageSize);
        PageInfo<RangeSearchResult> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(result);
        return pageInfo;
    }

    public PageInfo<RangeSearchResult> findViewByQtl(List<Integer> allQTLId, int pageNo, int pageSize){
        if (allQTLId == null || allQTLId.size() == 0) return null;
        int total = fpkmDao.countBySearchQtl(allQTLId);  //先计算选中QTL对应的基因总量
        int pageStart = (pageNo - 1) * pageSize;
        List<RangeSearchResult> result = fpkmDao.findViewByQtl(allQTLId, pageStart, pageSize);
        //发布QTL一级搜索结果列表事件
        AllQTLSearchResultEvent qtlSearchResultEvent = new AllQTLSearchResultEvent(allQTLId);
        AsyncEventBus eventBus = register.getAsyncEventBus();
        eventBus.post(qtlSearchResultEvent);
        PageInfo<RangeSearchResult> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(result);
        return pageInfo;
    }

    public boolean checkExistSNP(String fpkmGeneId, String snpConsequenceType){
        return fpkmDao.checkExistSNP(fpkmGeneId, snpConsequenceType);
    }

    private PageInfo<AdvanceSearchResultView> advanceSearchForGeneId(){
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getAllSelectedConsequenceTypeId(String type, List<String> consequenceType){
        List<Integer> result = new ArrayList<>();
        //判断缓存是否存在
        if (cache.get(CommonConstant.CACHEDSNP) == null){
            cacheAllSNPAndINDEL();
        }
        if (consequenceType != null && consequenceType.size() > 0){
            Map<String, Integer> cachedMap = new HashMap<>();
            if (type.equals("SNP")){
                cachedMap = (Map<String, Integer>) cache.get(CommonConstant.CACHEDSNP).get();
            } else if (type.equals("INDEL")){
                cachedMap = (Map<String, Integer>) cache.get(CommonConstant.CACHEDINDEL).get();
            } else {
                throw new IllegalArgumentException("传入的Type参数必须为:SNP、INDEL");
            }
            for (String con : consequenceType){
                result.add(cachedMap.get(con));
            }
        }
        return result;
    }
}
