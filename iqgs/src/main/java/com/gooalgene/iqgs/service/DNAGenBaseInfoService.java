package com.gooalgene.iqgs.service;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.entity.*;
import com.gooalgene.iqgs.entity.condition.AdvanceSearchResultView;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.qtl.dao.AssociatedgenesDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by sauldong on 2017/10/12.
 */
@Service
public class DNAGenBaseInfoService {
    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private AssociatedgenesDao associatedgenesDao;

    @Autowired
    private FPKMService fpkmService;

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByIdorName(String keyword, Page<DNAGenBaseInfo> page) {
        List<DNAGenBaseInfo> result = null;
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneId(keyword);
        bean.setGeneOldId(keyword);
        bean.setGeneName(keyword);
        result = dnaGenBaseInfoDao.findByConditions(bean);
        return result;
    }

    /**
     * QTL输入框搜索结果对应的查询服务
     * @param allQTLId 所有的QTL ID
     * @param pageNo 页码
     * @param pageSize 页数
     * @return 搜索结果列表
     */
    public PageInfo<DNAGeneSearchResult> queryDNAGenBaseInfos(List<Integer> allQTLId, int pageNo, int pageSize) {
        PageInfo<AdvanceSearchResultView> properGene = fpkmService.findProperGeneUnderSampleRun(null, null, null, allQTLId, pageNo, pageSize);  //通过高级搜索接口查询
        List<DNAGeneSearchResult> searchResultWithSNP = new ArrayList<>();
        DNAGeneSearchResult dnaGeneSearchResult = null;
        for (AdvanceSearchResultView geneView : properGene.getList()){
            dnaGeneSearchResult = new DNAGeneSearchResult();
            int id = geneView.getId(); //拿到基因查询结果，根据ID查询与之关联的SNP_NAME
            List<Associatedgenes> associatedQTLs = associatedgenesDao.findAssociatedGeneByGeneId(id);
            dnaGeneSearchResult.setGeneId(geneView.getGeneId());
            dnaGeneSearchResult.setGeneOldId(geneView.getGeneOldId());
            dnaGeneSearchResult.setGeneName(geneView.getGeneName());
            dnaGeneSearchResult.setFunction(geneView.getFunctions());
            dnaGeneSearchResult.setExistsSNP(geneView.existSNP());
            dnaGeneSearchResult.setRootTissues(geneView.getLargerThanThirtyTissue());
            dnaGeneSearchResult.setAssociateQTLs(associatedQTLs); //将查询出来的AssociateQTL关联到搜索结果上
            searchResultWithSNP.add(dnaGeneSearchResult);
        }
        return new PageInfo<>(searchResultWithSNP);
    }

    public List<Associatedgenes> findAllQTLNamesByGeneId(String geneId){
        Assert.notNull(geneId, "传入的geneId为空");
        return dnaGenBaseInfoDao.findAllAssociatedQTLByGeneId(geneId);
    }

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByFunc(String func, Page<DNAGenBaseInfo> page) {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setFunctions(func);
//        bean.setPage(page);
        return dnaGenBaseInfoDao.findByConditions(bean);
    }

    public DNAGenBaseInfo getGenBaseInfoByGeneId(String genId) {
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

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByRange(String chr, String start, String end, Page<DNAGenBaseInfo> page) {
        DNAGenStructure dnaGenStructure = new DNAGenStructure();
        dnaGenStructure.setChromosome(chr);
        dnaGenStructure.setStart(Long.valueOf(start));
        dnaGenStructure.setEnd(Long.valueOf(end));
        Page<DNAGenStructure> page1 = new Page<DNAGenStructure>();
        page1.setPageSize(page.getPageSize());
        page1.setPageNo(page.getPageNo());
        dnaGenStructure.setPage(page1);
        List<DNAGenStructure> dnaGenBaseInfoList = dnaGenBaseInfoDao.findGenByChr(dnaGenStructure);
        List<DNAGenBaseInfo> result = new ArrayList<DNAGenBaseInfo>();
        for (DNAGenStructure dnaGenStructure1 : dnaGenBaseInfoList) {
            DNAGenBaseInfo dnaGenBaseInfo1 = new DNAGenBaseInfo();
            dnaGenBaseInfo1.setGeneId(dnaGenStructure1.getGeneId());
            DNAGenBaseInfo dnaGenBaseInfo2 = dnaGenBaseInfoDao.findByGeneId(dnaGenBaseInfo1);
            result.add(dnaGenBaseInfo2);
        }
//        System.out.println(page1.getMaxResults() + "," + page1.getCount());
        page.setCount(page1.getCount());
        page.setList(result);
        return result;
    }

    public List<DNAGenHomologous> getGenHomologousByGeneId(String genId) {
        DNAGenHomologous bean = new DNAGenHomologous();
        bean.setGeneId(genId);
        return dnaGenBaseInfoDao.findGenHomologousByGeneId(bean);
    }

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByFamilyId(String familyId, Page<DNAGenBaseInfo> page) {
        DNAGenBaseInfo dnaGenBaseInfo = new DNAGenBaseInfo();
        dnaGenBaseInfo.setFamilyId(familyId);
//        dnaGenBaseInfo.setPage(page);
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
}
