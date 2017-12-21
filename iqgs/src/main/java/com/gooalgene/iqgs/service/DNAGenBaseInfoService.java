package com.gooalgene.iqgs.service;

import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.dna.service.DNAMongoService;
import com.gooalgene.iqgs.dao.DNAGenBaseInfoDao;
import com.gooalgene.iqgs.entity.*;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.gooalgene.common.constant.CommonConstant.EXONIC_NONSYNONYMOUSE;

/**
 * Created by sauldong on 2017/10/12.
 */
@Service
public class DNAGenBaseInfoService {
    @Autowired
    private DNAGenBaseInfoDao dnaGenBaseInfoDao;

    @Autowired
    private DNAMongoService dnaMongoService;

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByIdorName(String keyword, Page<DNAGenBaseInfo> page) {
        List<DNAGenBaseInfo> result = null;
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneId(keyword);
        bean.setGeneOldId(keyword);
        bean.setGeneName(keyword);
        bean.setPage(page);
        result = dnaGenBaseInfoDao.findByConditions(bean);
        return result;
    }

    public List<DNAGeneSearchResult> queryDNAGenBaseInfos(String keyword, Page<DNAGenBaseInfo> page) {
        List<DNAGenBaseInfo> result = null;
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setGeneId(keyword);
        bean.setGeneOldId(keyword);
        bean.setGeneName(keyword);
        bean.setPage(page);
        result = dnaGenBaseInfoDao.findByConditions(bean);
        List<DNAGeneSearchResult> searchResultWithSNP = new ArrayList<>();
        DNAGeneSearchResult dnaGeneSearchResult = null;
        for (DNAGenBaseInfo gene : result){
            String geneId = gene.getGeneId();
            Set<String> allConsequenceType = dnaMongoService.getAllConsequenceTypeByGeneId(geneId, CommonConstant.SNP);
            dnaGeneSearchResult = new DNAGeneSearchResult();
            BeanUtils.copyProperties(gene, dnaGeneSearchResult); //将从MySQL中查询到的数据全部拷贝到返回值结果bean上
            boolean exists = result.contains(EXONIC_NONSYNONYMOUSE);
            if (exists){
                dnaGeneSearchResult.setExistsSNP(true);
            }
            searchResultWithSNP.add(dnaGeneSearchResult);
        }
        return searchResultWithSNP;
    }

    public List<DNAGenBaseInfo> queryDNAGenBaseInfosByFunc(String func, Page<DNAGenBaseInfo> page) {
        DNAGenBaseInfo bean = new DNAGenBaseInfo();
        bean.setFunctions(func);
        bean.setPage(page);
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
}
