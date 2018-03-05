package com.gooalgene.iqgs.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.*;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.condition.RangeSearchResult;
import com.gooalgene.iqgs.eventbus.EventBusRegister;
import com.gooalgene.iqgs.eventbus.events.GenePreFetchEvent;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.qtl.dao.AssociatedgenesDao;
import com.gooalgene.utils.ConsequenceTypeUtils;
import com.google.common.eventbus.AsyncEventBus;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by sauldong on 2017/10/12.
 */
@Service
public class DNAGenBaseInfoService implements InitializingBean {
    private final String PREFIX = "MAX";

    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    @Autowired
    private FPKMService fpkmService;

    @Autowired
    private FPKMDao fpkmDao;

    @Autowired
    private CacheManager manager;

    @Autowired
    private EventBusRegister register;

    /**
     * controller层调用的服务层接口，用于查询单个搜索返回结果
     * @param conditionEnum 查询类型
     * @param keyword 输入框中用户输入的关键字
     */
    public PageInfo<DNAGeneSearchResult> queryDNAGenBaseSearchResult(SearchConditionEnum conditionEnum, String keyword, int pageNo, int pageSize) {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        if (conditionEnum.equals(SearchConditionEnum.ID)) {
            bean.setGeneId(keyword);
        }else if (conditionEnum.equals(SearchConditionEnum.FUNCTION)){
            bean.setFunctions(keyword);
        }
        //调用fpkm中针对基因功能、ID、NAME查询接口，获取到初步查询结果
        PageInfo<AdvanceSearchResultView> advanceSearchResultPage = fpkmService.searchByIdOrFunction(bean, pageNo, pageSize);
        if (advanceSearchResultPage == null)
            return new PageInfo<>();
        return convertSearchResultToSearchView(advanceSearchResultPage);
    }

    /**
     * QTL输入框搜索结果对应的查询服务
     * @param allQTLId 所有的QTL ID
     * @param firstHierarchyQtlId 一级搜索选中的QTL ID
     * @param pageNo 页码
     * @param pageSize 页数
     * @return 搜索结果列表
     */
    public PageInfo<RangeSearchResult> queryDNAGenBaseInfos(List<GeneExpressionConditionEntity> condition,
                                                              List<String> selectSnp,
                                                              List<String> selectIndel,
                                                              List<Integer> firstHierarchyQtlId,
                                                              List<Integer> allQTLId,
                                                              DNAGenBaseInfo baseInfo,
                                                              DNAGenStructure structure,
                                                              int pageNo, int pageSize) {
        if (selectSnp != null && selectSnp.size() > 0){
            selectSnp = ConsequenceTypeUtils.reverseReadableListValue(selectSnp);  //转换为数据库可读的序列类型
        }
        if (selectIndel != null && selectIndel.size() > 0){
            selectIndel = ConsequenceTypeUtils.reverseReadableListValue(selectIndel);
        }
        PageInfo<RangeSearchResult> properGene =
                fpkmService.findProperGeneUnderSampleRun(condition, selectSnp, selectIndel, firstHierarchyQtlId, allQTLId, baseInfo, structure, pageNo, pageSize);  //通过高级搜索接口查询
        return properGene;
    }

    /**
     * 统一转换层
     * 将从数据库中搜索处理的结果转换为前台搜索结果列表
     * @param properGene DAO层搜索结果
     * @return 高级搜索搜索列表分页显示
     */
    private PageInfo<DNAGeneSearchResult> convertSearchResultToSearchView(PageInfo<AdvanceSearchResultView> properGene){
        checkNotNull(properGene);
        List<DNAGeneSearchResult> searchResultWithSNP = new ArrayList<>();
        DNAGeneSearchResult dnaGeneSearchResult = null;
        for (AdvanceSearchResultView geneView : properGene.getList()){
            dnaGeneSearchResult = new DNAGeneSearchResult();
            int id = geneView.getId(); //拿到基因查询结果，根据ID查询与之关联的SNP_NAME
            Set<Associatedgenes> associatedQTLs = associatedgenesDao.findAssociatedGeneByGeneId(id);
            dnaGeneSearchResult.setGeneId(geneView.getGeneId());
            dnaGeneSearchResult.setGeneOldId(geneView.getGeneOldId());
            dnaGeneSearchResult.setGeneName(geneView.getGeneName());
            dnaGeneSearchResult.setDescription(geneView.getFunctions());
            dnaGeneSearchResult.setExistsSNP(fpkmService.checkExistSNP(geneView.getGeneId(), CommonConstant.EXONIC_NONSYNONYMOUSE));
            dnaGeneSearchResult.setRootTissues(geneView.getLargerThanThirtyTissue());
            dnaGeneSearchResult.setAssociateQTLs(associatedQTLs); //将查询出来的AssociateQTL关联到搜索结果上
            searchResultWithSNP.add(dnaGeneSearchResult);
        }
        PageInfo<DNAGeneSearchResult> resultPageInfo = new PageInfo<>();
        try {
            //将使用PageHelper查询的结果设值到DNAGeneSearchResult这个PageInfo中
            BeanUtils.copyProperties(resultPageInfo, properGene);
            resultPageInfo.setList(searchResultWithSNP);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return resultPageInfo;
    }

    /**
     * 获取染色体的最大长度(接口预留)
     * @param chromosome 染色体名
     * @return 该染色体中最长的基因长度
     */
    public int getLongestLength(String chromosome){
        int length = 0;
        Cache chromosomeCache = manager.getCache("config");
        String key = PREFIX + chromosome;
        Cache.ValueWrapper valueWrapper = chromosomeCache.get(key);
        if (valueWrapper == null){
            length = fpkmDao.getLongestLength(chromosome);
            chromosomeCache.putIfAbsent(key, length);
        } else {
            length = (int) chromosomeCache.get(key).get();
        }
        return length;
    }

    /**
     * 获取示例QTL ID
     */
    public int getAssociateGeneIdByQtlAndVersion(String qtlName, String version){
        Associatedgenes associateGene = associatedgenesDao.getByNameAndVersion(qtlName, version);
        if (associateGene == null){
            throw new IllegalArgumentException(qtlName + "对应的版本" + version + "不存在，请重新选择示例QTL");
        }
        return Integer.parseInt(associateGene.getId());
    }

    public List<Associatedgenes> findAllQTLNamesByGeneId(String geneId){
        Assert.notNull(geneId, "传入的geneId为空");
        return dnaGenBaseInfoDao.findAllAssociatedQTLByGeneId(geneId);
    }

    public List<String> findAllGeneId(List<Integer> idList){
        return dnaGenBaseInfoDao.findAllGeneId(idList);
    }

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByFunc(String func, Page<DNAGenBaseInfo> page) {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setFunctions(func);
//        bean.setPage(page);
        return dnaGenBaseInfoDao.findByConditions(bean);
    }

    public DNAGenBaseInfo getGenBaseInfoByGeneId(String genId) {
        //发布请求该基因相关的MongoDB数据事件
        GenePreFetchEvent<GenResult> event = new GenePreFetchEvent<>(genId, GenResult.class);
        AsyncEventBus asyncEventBus = register.getAsyncEventBus();
        asyncEventBus.post(event);
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findByGeneId(bean);
    }

    public List<DNAGenSequence> getGenSequenceByGeneId(String genId) {
        DNAGenSequence bean = new DNAGenSequence();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenSequenceByGeneId(bean);
    }

    public List<DNAGenStructure> getGenStructureByTranscriptId(String TranscriptId) {
        DNAGenStructure bean = new DNAGenStructure();
        bean.setTranscriptId(TranscriptId);
        return dnaGenBaseInfoDao.findGenStructureByTranscriptId(bean);
    }

    public List<DNAGenAnnoGo> getGenAnnoGoByGeneId(String genId) {
        DNAGenAnnoGo bean = new DNAGenAnnoGo();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenAnnoGoByGeneId(bean);
    }

    public List<DNAGenAnnoIpr> getGenAnnoIprByGeneId(String genId) {
        DNAGenAnnoIpr bean = new DNAGenAnnoIpr();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenAnnoIprByGeneId(bean);
    }

    public List<DNAGenAnnoKegg> getGenAnnoKeggByGeneId(String genId) {
        DNAGenAnnoKegg bean = new DNAGenAnnoKegg();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenAnnoKeggByGeneId(bean);
    }

    public Map generateGenStructure(String genId) {
        DNAGenStructure bean = new DNAGenStructure();
        bean.setGeneId(genId);
        List<DNAGenStructure> gss = dnaGenBaseInfoDao.findGenStructureByGeneId(bean);
        Map stct = buildStructure(gss);
        return stct;
    }

    public List<DNAGenStructure> getGenStructureByGeneId(String genId) {
        DNAGenStructure bean = new DNAGenStructure();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenStructureByGeneId(bean);
    }

    private Map buildStructure(List<DNAGenStructure> gss) {
        Map rs = new HashMap();
        if (gss != null && gss.size() > 0) {
            DNAGenStructure genStru = gss.get(0);
            rs.put("id", genStru.getGeneId());
            rs.put("max_length", genStru.getMaxLength());
            rs.put("start", genStru.getOffset());

            Map<String, List<DNAGenStructure>> tmpMap = new HashMap();
            for (DNAGenStructure struct : gss) {
                List<DNAGenStructure> tmpss = tmpMap.get(struct.getTranscriptId());
                if (tmpss == null) {
                    tmpss = new ArrayList();
                    tmpMap.put(struct.getTranscriptId(), tmpss);
                }
                tmpss.add(struct);
            }
            Iterator<Map.Entry<String, List<DNAGenStructure>>> it = tmpMap.entrySet().iterator();
            List<Map> data = new ArrayList();
            while (it.hasNext()) {
                Map.Entry<String, List<DNAGenStructure>> entry = it.next();
                Map stt = new HashMap();
                stt.put("geneID", entry.getKey());

                List<DNAGenStructure> dgss = entry.getValue();
                List<Map> sts = new ArrayList();
                for (DNAGenStructure dnaGS : dgss) {
                    Map dgs = new HashMap();
                    dgs.put("type", dnaGS.getFeature());
                    dgs.put("start", dnaGS.getStart());
                    dgs.put("end", dnaGS.getEnd());
                    dgs.put("length", dnaGS.getLength());
                    dgs.put("chromosome", dnaGS.getChromosome());
                    sts.add(dgs);
                }
                stt.put("structure", sts);
                data.add(stt);
            }
            rs.put("data", data);
        }
        return rs;
    }

    public List<DNAGenHomologous> getGenHomologousPageByGeneId(String genId) {
        DNAGenHomologous bean = new DNAGenHomologous();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenHomologousByGeneId(bean);
    }

    public PageInfo<DNAGenHomologous> getGenHomologousByGeneId(String genId, int pageNo, int pageSize) {
        DNAGenHomologous bean = new DNAGenHomologous();
        bean.setGeneId(genId);
        PageHelper.startPage(pageNo, pageSize);
        List<DNAGenHomologous> homologousList = dnaGenBaseInfoDao.findGenHomologousByGeneId(bean);
        PageInfo<DNAGenHomologous> homologousPageInfo = new PageInfo<>(homologousList);
        return homologousPageInfo;
    }

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByFamilyId(String familyId, Page<DNAGenBaseInfo> page) {
        DNAGenBaseInfo dnaGenBaseInfo = new DNAGenBaseInfo();
        dnaGenBaseInfo.setFamilyId(familyId);
        dnaGenBaseInfo.setPage(page);
        return dnaGenBaseInfoDao.findBaseInfoByFamilyId(dnaGenBaseInfo);
    }

    public List<DNAGenFamilyRel> findFamilyByGeneId(String genId) {
        return dnaGenBaseInfoDao.findFamilyByGeneId(genId);
    }

    public DNAGenFamily findFamilyByFamilyId(String familyId) {
        return dnaGenBaseInfoDao.findFamilyByFamilyId(familyId);
    }

    public JSONObject findStructureByFamilyId(String familyId) {
        // 根据基因家族id查询基因offset信息
        List<DNAGenOffset> list1 = dnaGenBaseInfoDao.findOffsetByFamilyId(familyId);

        // 根据基因家族id查询基因结构信息
        List<DNAGenFamilyStructure> list2 = dnaGenBaseInfoDao.findStructureByFamilyId(familyId);

        // 循环offset中基因列表设置基因结构信息
        JSONArray jsonArray1 = new JSONArray();
        for (DNAGenOffset dnaGenOffset : list1) {
            JSONObject json1 = new JSONObject();
            json1.put("geneName", dnaGenOffset.getGeneName());
            json1.put("geneID", dnaGenOffset.getGeneId());
            json1.put("length", dnaGenOffset.getLength());

            JSONArray jsonArray2 = new JSONArray();
            for (DNAGenFamilyStructure dnaGenFamilyStructure : list2) {
                // 获取某个基因的结构信息
                if (dnaGenOffset.getGeneId().equals(dnaGenFamilyStructure.getGeneId())) {
                    JSONObject json2 = new JSONObject();
                    json2.put("type", dnaGenFamilyStructure.getFeature());
                    json2.put("start", dnaGenFamilyStructure.getStart());
                    json2.put("end", dnaGenFamilyStructure.getEnd());
                    jsonArray2.add(json2);
                }
            }
            json1.put("structure", jsonArray2);

            jsonArray1.add(json1);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("max_length", dnaGenBaseInfoDao.findMaxLengthByFamilyId(familyId));
        jsonObject.put("data", jsonArray1);
        return jsonObject;
    }

    public String findSequenceByTranscriptId(String transcriptId) {
        return dnaGenBaseInfoDao.findSequenceByTranscriptId(transcriptId);
    }

    /**
     * 检查该基因是否位于筛选的QTL列表中
     * @param geneId 基因ID
     * @param qtlList 高级搜索中选中的QTL列表，这里是QTL ID的集合
     * @return 如果存在其中某一种QTL则返回true，否则返回false
     */
    public boolean checkGeneHasQTL(String geneId, List<Integer> qtlList){
        boolean result = false;
        Integer genePrimaryKey = dnaGenBaseInfoDao.checkGeneExists(geneId);  //检查该基因是否存在，拿到基因ID
        if (genePrimaryKey == null || qtlList == null || qtlList.size() == 0){
            return result;
        }
        result = dnaGenBaseInfoDao.checkGeneExistsInQtlList(genePrimaryKey, qtlList);
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
