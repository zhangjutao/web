package com.gooalgene.mrna.service;

import com.gooalgene.common.Page;
import com.gooalgene.common.dao.StudyDao;
import com.gooalgene.entity.Study;
import com.gooalgene.mrna.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ShiYun on 2017/8/2 0002.
 */
@Service
public class StudyService {

    @Autowired
    private StudyDao studyDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoService mongoService;

    public Study queryById(String id) {
        return studyDao.get(id);
    }


    public Study queryByRun(String run) {
        return studyDao.findBySampleRun(run);
    }

    /**
     * 后台管理查询Study分页处理
     *
     * @param type
     * @param keywords
     * @param page
     * @return
     */
    public JSONArray searchStudybyKeywords(String type, String keywords, Page<Study> page) {
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索
        JSONArray data = new JSONArray();
        Study study = new Study();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setKeywords(keywords);
            }//空白查询所有
        }
        study.setPage(page);
        List<Map> list = studyDao.findStudyMap(study);
        for (Map m : list) {
            String sraStudy = (String) m.get("sraStudy");
            if (null == sraStudy) {
                m.put("sraStudy", "");
            }
            String study1 = (String) m.get("study");
            if (null == study1) {
                m.put("study", "");
            }
            String sampleName = (String) m.get("sampleName");
            if (null == sampleName) {
                m.put("sampleName", "");
            }
            Integer isExpression = (Integer) m.get("isExpression");
            if (null == isExpression) {
                m.put("isExpression", "");
            }
            String sampleRun = (String) m.get("sampleRun");
            if (null == sampleRun) {
                m.put("sampleRun", "");
            }
            String tissue = (String) m.get("tissue");
            if (null == tissue) {
                m.put("tissue", "");
            }
            String tissueForClassification = (String) m.get("tissueForClassification");
            if (null == tissueForClassification) {
                m.put("tissueForClassification", "");
            }
            String preservation = (String) m.get("preservation");
            if (null == preservation) {
                m.put("preservation", "");
            }
            String treat = (String) m.get("treat");
            if (null == treat) {
                m.put("treat", "");
            }
            String stage = (String) m.get("stage");
            if (null == stage) {
                m.put("stage", "");
            }
            String geneType = (String) m.get("geneType");
            if (null == geneType) {
                m.put("geneType", "");
            }
            String phenoType = (String) m.get("phenoType");
            if (null == phenoType) {
                m.put("phenoType", "");
            }
            String environment = (String) m.get("environment");
            if (null == environment) {
                m.put("environment", "");
            }
            String geoLoc = (String) m.get("geoLoc");
            if (null == geoLoc) {
                m.put("geoLoc", "");
            }
            String ecoType = (String) m.get("ecoType");
            if (null == ecoType) {
                m.put("ecoType", "");
            }
            String collectionDate = (String) m.get("collectionDate");
            if (null == collectionDate) {
                m.put("collectionDate", "");
            }
            String coordinates = (String) m.get("coordinates");
            if (null == coordinates) {
                m.put("coordinates", "");
            }
            String ccultivar = (String) m.get("ccultivar");
            if (null == ccultivar) {
                m.put("ccultivar", "");
            }
            String scientificName = (String) m.get("scientificName");
            if (null == scientificName) {
                m.put("scientificName", "");
            }
            String pedigree = (String) m.get("pedigree");
            if (null == pedigree) {
                m.put("pedigree", "");
            }
            String reference = (String) m.get("reference");
            if (null == reference) {
                m.put("reference", "");
            }
            String institution = (String) m.get("institution");
            if (null == institution) {
                m.put("institution", "");
            }
            String submissionTime = (String) m.get("submissionTime");
            if (null == submissionTime) {
                m.put("submissionTime", "");
            }
            String instrument = (String) m.get("instrument");
            if (null == instrument) {
                m.put("instrument", "");
            }
            String libraryStrategy = (String) m.get("libraryStrategy");
            if (null == libraryStrategy) {
                m.put("libraryStrategy", "");
            }
            String librarySource = (String) m.get("librarySource");
            if (null == librarySource) {
                m.put("librarySource", "");
            }
            String libraryLayout = (String) m.get("libraryLayout");
            if (null == libraryLayout) {
                m.put("libraryLayout", "");
            }
            String insertSize = (String) m.get("insertSize");
            if (null == insertSize) {
                m.put("insertSize", "");
            }
            String readLength = (String) m.get("readLength");
            if (null == readLength) {
                m.put("readLength", "");
            }
            Integer spots = (Integer) m.get("spots");
            if (null == spots) {
                m.put("spots", "");
            }
            String experiment = (String) m.get("experiment");
            if (null == experiment) {
                m.put("experiment", "");
            }
            String links = (String) m.get("links");
            if (null == links) {
                m.put("links", "");
            }
//            System.out.println(m.toString());
            data.add(m);
        }
        List<Study> list1 = studyDao.findStudyList(study);
        page.setList(list1);
        return data;
    }

    /**
     * 查询全部run对应分类关系
     *
     * @return
     */
    public Map<String, String> queryRunsAndTissueForClassify() {
        Map<String, String> map = new HashMap<String, String>();
        List<Study> list = studyDao.findList(new Study());
        for (Study study : list) {
            map.put(study.getSampleRun().trim(), study.getTissueForClassification().toLowerCase().trim());
        }
        return map;
    }

    /**
     * 根据study编号查询对应的run-sampleName
     *
     * @param sraStudy
     * @return
     */
    public Map<String, Study> queryRuns(String sraStudy) {
        Map<String, Study> map = new HashMap<String, Study>();
        List<Study> list = studyDao.findBySID(sraStudy);
        for (Study study : list) {
            map.put(study.getSampleRun(), study);
        }
        return map;
    }

    /**
     * 表达基因表头run数据查询
     *
     * @param sraStudy
     * @return
     */
    public Map<String, Study> queryExpressionRuns(String sraStudy) {
        Map<String, Study> map = new HashMap<String, Study>();
//        String[] strings = Tools.queryExpressions(sraStudy);
        String[] strings = mongoService.queryTitlesBySraStudy(sraStudy);
        if (strings != null) {
            List<Study> list = studyDao.findBySampleRuns(strings);
            for (Study study : list) {
                map.put(study.getSampleRun(), study);
            }
        }
        return map;
    }

    public List<String> queryExpression(String sraStudy) {
        List<Study> list = studyDao.findBySID(sraStudy);
        List<String> runs = new ArrayList<String>();
        for (Study study : list) {
            runs.add(study.getSampleRun());
        }
        return runs;
    }

    /**
     * 查询study基础信息接口
     *
     * @param id
     * @param type 区分差异和表达
     * @return
     */
    public JSONObject queryStudyBasicInfo(String id, String type) {
        JSONObject jsonObject = new JSONObject();
        Study study = studyDao.get(id);
        if (study != null) {
            jsonObject.put("experiment", getStringNotNull(study.getExperiment()));
            jsonObject.put("instrument", getStringNotNull(study.getInstrument()));
            jsonObject.put("sraStudy", study.getSraStudy());
            jsonObject.put("study", study.getStudy());
            jsonObject.put("links", getStringNotNull(study.getLinks()));
            jsonObject.put("libraryStrategy", getStringNotNull(study.getLibraryStrategy()));
            jsonObject.put("pedigree", getStringNotNull(study.getPedigree()));
            String sraStudy = study.getSraStudy();
            JSONArray array = new JSONArray();
            if (type.equals("0")) {
                String collection = "Comparison_" + sraStudy;
                boolean flag = mongoTemplate.collectionExists(collection);
                System.out.println(flag + " exist:" + collection);
                if (flag) {
                    List<Study> list = studyDao.findBySID(sraStudy);
                    Aggregation aggregation = Aggregation.newAggregation(
                            Aggregation.group("gene").push("$diffs").as("diffs"),
                            Aggregation.limit(1)
                    ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                    System.out.println("Query count:" + aggregation.toString());
                    AggregationResults<Comparison> queryResult = mongoTemplate.aggregate(aggregation, collection, Comparison.class);
                    List<String> diffs = new ArrayList<String>();
                    for (Comparison comparison : queryResult) {
                        List<Diff> diffs1 = comparison.getDiffs();
                        if (!diffs1.isEmpty()) {
                            for (Diff diff : diffs1) {
                                diffs.add(diff.getName());
                            }
                            break;//每条数据对应的比较组都是一样的，所以直接取一个就好
                        }
                    }
                    Map<String, Study> allRuns = queryRuns(sraStudy);
                    Set hasIn = queryDiffRuns(diffs);
                    for (Study s : list) {//只有出现在比较组里的样本数据才显示
                        if (hasIn.contains(s.getSampleRun())) {
                            JSONObject context = new JSONObject();
                            context.put("sampleno", s.getSampleRun());
                            context.put("samplename", s.getSampleName());
                            context.put("tissue", getStringNotNull(s.getTissueForClassification()));
                            context.put("stage", getStringNotNull(s.getStage()));
                            context.put("treat", getStringNotNull(s.getTreat()));
                            context.put("genetype", getStringNotNull(s.getGeneType()));
                            context.put("cultivar", getStringNotNull(s.getCcultivar()));
                            array.add(context);
                        }
                    }
                    jsonObject.put("Compares", changeDiff2SampleName(allRuns, diffs));
                } else {
                    jsonObject.put("Compares", new JSONArray());
                }
            } else {
                String collection = "Expression_" + sraStudy;
                boolean flag = mongoTemplate.collectionExists(collection);
                System.out.println(flag + " exist:" + collection);
                StringBuffer stringBuffer = new StringBuffer();
                if (flag) {
                    Aggregation aggregation = Aggregation.newAggregation(
                            Aggregation.group("gene").push("$samples").as("samples"),
                            Aggregation.limit(1)
                    ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                    System.out.println("Query count:" + aggregation.toString());
                    AggregationResults<SampleExpression> queryResult = mongoTemplate.aggregate(aggregation, collection, SampleExpression.class);
                    for (SampleExpression sampleExpression : queryResult) {
                        List<Run> runs = sampleExpression.getSamples();
                        if (!runs.isEmpty()) {
                            for (Run run : runs) {
                                stringBuffer.append(run.getStudy()).append(",");
                            }
                            break;//每条数据对应的比较组都是一样的，所以直接取一个就好
                        }
                    }
                }
                System.out.println("Title:" + stringBuffer.toString());
                String[] runs = stringBuffer.toString().split(",");//获取基因表达表头数据
                if (runs != null) {
                    List<Study> list = studyDao.findBySampleRuns(runs);
                    for (Study s : list) {
                        JSONObject context = new JSONObject();
                        context.put("sampleno", s.getSampleRun());
                        context.put("samplename", s.getSampleName());
                        context.put("tissue", getStringNotNull(s.getTissueForClassification()));
                        context.put("stage", getStringNotNull(s.getStage()));
                        context.put("treat", getStringNotNull(s.getTreat()));
                        context.put("genetype", getStringNotNull(s.getGeneType()));
                        context.put("cultivar", getStringNotNull(s.getCcultivar()));
                        array.add(context);
                    }
                }
            }
            jsonObject.put("samples", array);
        } else {
            jsonObject.put("experiment", "");
            jsonObject.put("instrument", "");
            jsonObject.put("sraStudy", id);
            jsonObject.put("study", "");
            jsonObject.put("links", "");
            jsonObject.put("libraryStrategy", "");
            jsonObject.put("pedigree", "");
            jsonObject.put("samples", new JSONArray());
            if (type.equals("0")) {
                jsonObject.put("Compares", new JSONArray());
            }
        }
        return jsonObject;
    }

    private Set queryDiffRuns(List<String> diffName) {
        Set set = new HashSet();
        for (String ss : diffName) {
//          SRR037375_vs_SRR037374
            String[] diffs = ss.split("_");
            if (diffs.length == 3) {
                String s1 = diffs[0];
                String s2 = diffs[2];
                set.add(s1);
                set.add(s2);
            }
        }
        return set;
    }

    private JSONArray changeDiff2SampleName(Map<String, Study> allRuns, List<String> diffName) {
        JSONArray jsonArray = new JSONArray();
        for (String ss : diffName) {
//          SRR037375_vs_SRR037374
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("runno", ss);
            String[] diffs = ss.split("_");
            if (diffs.length == 3) {
                String s1 = diffs[0];
                String runName1 = allRuns.get(s1).getSampleName();
                String s2 = diffs[2];
                String runName2 = allRuns.get(s2).getSampleName();
                if (runName1 != null && runName2 != null) {
                    jsonObject.put("runname", runName1 + "_vs_" + runName2);
                }
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * @param type     下拉列表选项
     * @param keywords 搜索框值
     * @param param    列表表头搜索值（json格式）
     * @param page
     * @return
     */
    public Map queryStudyByCondition(String type, String keywords, String param, Page<Study> page) {
        Map result = new HashMap();
        result.put("type", type);
        result.put("keywords", (keywords == null ?
                "" : (keywords.endsWith(".all") ? keywords.substring(0, keywords.lastIndexOf(".")) : keywords))); //拿到所属大组织
        result.put("condition", "{}");
        result.put("pageNo", page.getPageNo());
        result.put("pageSize", page.getPageSize());
        JSONArray data = new JSONArray();
        Study study = getQuery(type, keywords, param); //查询参数封装到study对象中
        study.setPage(page);
        List<Map> list = null;
        List<Study> list1 = null;
        list = studyDao.findByCondition(study);
        list1 = studyDao.findList(study);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map m : list) {
            if (m.get("tissueForClassification") != null) {
                m.put("tissue", m.get("tissueForClassification"));
            }
            if (m.get("preservation") == null) {
                m.put("preservation", "");
            }
            if (m.get("treat") == null) {
                m.put("treat", "");
            }
            if (m.get("stage") == null) {
                m.put("stage", "");
            }
            if (m.get("geneType") == null) {
                m.put("geneType", "");
            }
            if (m.get("phenoType") == null) {
                m.put("phenoType", "");
            }
            if (m.get("environment") == null) {
                m.put("environment", "");
            }
            if (m.get("geoLoc") == null) {
                m.put("geoLoc", "");
            }
            if (m.get("ecoType") == null) {
                m.put("ecoType", "");
            }
            if (m.get("collectionDate") == null) {
                m.put("collectionDate", "");
            }
            if (m.get("coordinates") == null) {
                m.put("coordinates", "");
            }
            if (m.get("ccultivar") == null) {
                m.put("ccultivar", "");
            }
            if (m.get("scientificName") == null) {
                m.put("scientificName", "");
            }
            if (m.get("pedigree") == null) {
                m.put("pedigree", "");
            }
            if (m.get("reference") == null) {
                m.put("reference", "");
            }
            if (m.get("institution") == null) {
                m.put("institution", "");
            }
            if (m.get("submissionTime") == null) {
                m.put("submissionTime", "");
            }
            if (m.get("instrument") == null) {
                m.put("instrument", "");
            }
            if (m.get("libraryStrategy") == null) {
                m.put("libraryStrategy", "");
            }
            if (m.get("librarySource") == null) {
                m.put("librarySource", "");
            }
            if (m.get("libraryLayout") == null) {
                m.put("libraryLayout", "");
            }
            if (m.get("insertSize") == null) {
                m.put("insertSize", "");
            }
            if (m.get("readLength") == null) {
                m.put("readLength", "");
            }
            if (m.get("spots") == null) {
                m.put("spots", "");
            }
            if (m.get("experiment") == null) {
                m.put("experiment", "");
            }
            if (m.get("links") == null) {
                m.put("links", "");
            }
            Date time = (Date) m.get("createTime");
            m.put("createTime", simpleDateFormat.format(time));
            data.add(m);
        }
        page.setList(list1);
        result.put("total", page.getCount());
        result.put("data", data);
        return result;
    }

    /**
     * 页面导出数据功能
     *
     * @param type
     * @param keywords
     * @param parameters
     * @return
     */
    public List<Map> queryStudyToExport(String type, String keywords, String parameters) {
        Study study = getQuery(type, keywords, parameters);
        List<Map> list = studyDao.findByCondition(study);
        return list;
    }

    @Autowired
    private TService tService;

    private Study getQuery(String type, String keywords, String param) {
        Study study = new Study();
        if ("all".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setKeywords(keywords);
            }
        } else if ("Study".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setStudy(keywords);
            }
        } else if ("Tissues".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                if (keywords.endsWith(".all")) {
                    study.setTissueKeywords(keywords.substring(0, keywords.lastIndexOf(".")));
                } else {
                    List<String> list = tService.queryClassifyByFather(keywords); //查询子分类
                    if (list != null) {
                        study.setTissues(list); //设值该组织下所有子分类
                    }
                }
            }
        } else if ("Stage".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setStage(keywords);
            }
        } else if ("Treat".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setTreat(keywords);
            }
        } else if ("Reference".equalsIgnoreCase(type)) {
            if (!StringUtils.isBlank(keywords)) {
                study.setReference(keywords);
            }
        }
        try {
            JSONObject jsonObject = JSONObject.fromObject(param);
            if (hasValue(jsonObject, "SampleName")) {
                study.setSampleName(jsonObject.getString("SampleName"));
            }
            if (hasValue(jsonObject, "Study")) {
                study.setStudy(jsonObject.getString("Study"));
            }
            if (hasValue(jsonObject, "Tissue")) {
                study.setTissueKeywords(jsonObject.getString("Tissue"));
            }
            if (hasValue(jsonObject, "Reference")) {
                study.setReference(jsonObject.getString("Reference"));
            }
            if (hasValue(jsonObject, "Stage")) {
                study.setStage(jsonObject.getString("Stage"));
            }
            if (hasValue(jsonObject, "Treat")) {
                study.setTreat(jsonObject.getString("Treat"));
            }
            if (hasValue(jsonObject, "Genetype")) {
                study.setGeneType(jsonObject.getString("Genetype"));
            }
            if (hasValue(jsonObject, "Preservation")) {
                study.setPreservation(jsonObject.getString("Preservation"));
            }
            if (hasValue(jsonObject, "Phenotype")) {
                study.setPhenoType(jsonObject.getString("Phenotype"));
            }
            if (hasValue(jsonObject, "Environment")) {
                study.setEnvironment(jsonObject.getString("Environment"));
            }
            if (hasValue(jsonObject, "Cultivar")) {
                study.setCcultivar(jsonObject.getString("Cultivar"));
            }
            if (hasValue(jsonObject, "ScientificName")) {
                study.setScientificName(jsonObject.getString("ScientificName"));
            }
            if (hasValue(jsonObject, "LibraryLayout")) {
                study.setLibraryLayout(jsonObject.getString("LibraryLayout"));
            }
            if (hasValue(jsonObject, "Spots")) {
                study.setSpots(jsonObject.getInt("Spots"));
            }
            if (hasValue(jsonObject, "Run")) {
                study.setSampleRun(jsonObject.getString("Run"));
            }
            if (hasValue(jsonObject, "SRAStudy")) {
                study.setSraStudy(jsonObject.getString("SRAStudy"));
            }
            if (hasValue(jsonObject, "Experiment")) {
                study.setExperiment(jsonObject.getString("Experiment"));
            }
        } catch (JSONException e) {

        }
        return study;
    }

    private boolean hasValue(JSONObject jsonObject, String key) {
        try {
            String value = (String) jsonObject.get(key);
            return StringUtils.isNotBlank(value);
        } catch (JSONException e) {
            return false;
        }
    }

    private String getStringNotNull(String str) {
        return str == null ? "" : str;
    }

    /**
     * 查询首页设置的5个基因
     *
     * @return
     */
    public String[] queryGenesForFirst() {
        List<Map> result = studyDao.findGenesForFirst();
        if (!result.isEmpty()) {
            int len = result.size();
            String[] genes = new String[len];
            for (int i = 0; i < len; i++) {
                String gene = (String) result.get(i).get("gene");
                if (gene == null) {
                    gene = "";
                }
                genes[i] = gene;
            }
            return genes;
        } else {
            return new String[]{};
        }
    }

    /**
     * @return
     */
    public JSONArray queryAllGenesForFirst() {
        JSONArray jsonArray = new JSONArray();
        List<Map> result = studyDao.findGenesForFirst();
        for (Map m : result) {
            jsonArray.add(m);
        }
        return jsonArray;
    }

    public Map findGenesForFirstById(Integer id) {
        return studyDao.findGenesForFirstById(id);
    }

    public int updateGenesForFirstById(int id, String gene) {
        return studyDao.updateGenesForFirstById(id, gene);
    }

    public JSONArray queryScientificNames() {
        List<Map> list = studyDao.queryScientificNames();
        JSONArray jsonArray = new JSONArray();
        for (Map map : list) {
            jsonArray.add(map.get("scientificName"));
        }
        return jsonArray;
    }

    public JSONArray queryLibraryLayouts() {
        List<Map> list = studyDao.queryLibraryLayouts();
        JSONArray jsonArray = new JSONArray();
        for (Map map : list) {
            jsonArray.add(map.get("libraryLayout"));
        }
        return jsonArray;
    }

    @Transactional(readOnly = false)
    public boolean add(Study study) {
        return studyDao.add(study);
    }

    public Study findById(int id) {
        return studyDao.findById(id);
    }

    @Transactional(readOnly = false)
    public int update(Study study) {
        return studyDao.update(study);
    }

    @Transactional(readOnly = false)
    public boolean delete(int id) {
        return studyDao.deleteById(id);
    }

    public List<ExpressionStudy> getStudyByGene(String gene, Page<ExpressionStudy> page){
        List<String> run = studyDao.findSampleruns();//查询所有的run
        List<ExpressionStudy> data = new ArrayList<ExpressionStudy>();
        String collectionName="all_gens_fpkm";
        long total = 0;
        List<ExpressionVo> result = new ArrayList<ExpressionVo>();
        if (mongoTemplate.collectionExists(collectionName)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("gene").is(gene));
            query.addCriteria(Criteria.where("samplerun.name").in(run));
            query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "samplerun.value")));//降序
            total = mongoTemplate.count(query, ExpressionVo.class, collectionName);//总记录数
            Integer pageNo = page.getPageNo();
            Integer pageSize = page.getPageSize();
            int skip = (pageNo - 1) * pageSize;
            if (skip < 0) {
                skip = 0;
            }
            query.skip(skip);
            query.limit(pageSize);
            result = mongoTemplate.find(query, ExpressionVo.class, "all_gens_fpkm");
            for (int i = 0; i < result.size(); i++) {
                Study study=null;
                ExpressionVo expressionVo =  result.get(i);
                String samplerunName=expressionVo.getSamplerun().getName();
                study=studyDao.findBySampleRun(samplerunName);
                ExpressionStudy expressionStudy=new ExpressionStudy();
                expressionStudy.setId(expressionVo.getId());
                expressionStudy.setGene(expressionVo.getGene());
                expressionStudy.setSamplerun(expressionVo.getSamplerun());
                expressionStudy.setStudy(study);
                data.add(expressionStudy);
            }
        }else {
            System.out.println(" collectionName is not exist.");
            /*logger.info(collectionName + " is not exist.");*/
        }
        page.setCount(total);
        return data;
    }

    public JSONArray queryStudyByGene(String gene) {
        List<String> run = studyDao.findSampleruns(); //查询所有的run
        //SRR129864456c 换成 SRR12986456c
        run.add("SRR12986456c");
        Query query = new Query();
        query.addCriteria(Criteria.where("gene").is(gene));
        query.addCriteria(Criteria.where("samplerun.name").in(run));
        List<ExpressionVo> runs = mongoTemplate.find(query, ExpressionVo.class, "all_gens_fpkm");

        Map<String, Double> run_value = new HashMap<String, Double>();

        StringBuffer samplerun = new StringBuffer();

        for (ExpressionVo expression : runs) {
            SampleRun sampleRuns = expression.getSamplerun();
            String key = sampleRuns.getName();
            Double value = sampleRuns.getValue();
            run_value.put(key, value);
            samplerun.append(key).append(",");
        }
        samplerun.append("SRR129864456c");

        JSONArray data = new JSONArray();
        List<Map> list = studyDao.findByRuns(samplerun.toString().split(","));
        System.out.println("mysql size:" + list.size());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map m : list) {
            m.put("geneId", gene);
            String sampleRun = (String) m.get("sampleRun");
            if (sampleRun.equals("SRR129864456c")) {
                sampleRun = "SRR12986456c";
            }
            m.put("expressionValue", run_value.get(sampleRun));
            if (m.get("tissueForClassification") != null) {
                m.put("tissue", m.get("tissueForClassification"));
            }
            if (m.get("preservation") == null) {
                m.put("preservation", "");
            }
            if (m.get("treat") == null) {
                m.put("treat", "");
            }
            if (m.get("stage") == null) {
                m.put("stage", "");
            }
            if (m.get("geneType") == null) {
                m.put("geneType", "");
            }
            if (m.get("phenoType") == null) {
                m.put("phenoType", "");
            }
            if (m.get("environment") == null) {
                m.put("environment", "");
            }
            if (m.get("geoLoc") == null) {
                m.put("geoLoc", "");
            }
            if (m.get("ecoType") == null) {
                m.put("ecoType", "");
            }
            if (m.get("collectionDate") == null) {
                m.put("collectionDate", "");
            }
            if (m.get("coordinates") == null) {
                m.put("coordinates", "");
            }
            if (m.get("ccultivar") == null) {
                m.put("ccultivar", "");
            }
            if (m.get("scientificName") == null) {
                m.put("scientificName", "");
            }
            if (m.get("pedigree") == null) {
                m.put("pedigree", "");
            }
            if (m.get("reference") == null) {
                m.put("reference", "");
            }
            if (m.get("institution") == null) {
                m.put("institution", "");
            }
            if (m.get("submissionTime") == null) {
                m.put("submissionTime", "");
            }
            if (m.get("instrument") == null) {
                m.put("instrument", "");
            }
            if (m.get("libraryStrategy") == null) {
                m.put("libraryStrategy", "");
            }
            if (m.get("librarySource") == null) {
                m.put("librarySource", "");
            }
            if (m.get("libraryLayout") == null) {
                m.put("libraryLayout", "");
            }
            if (m.get("insertSize") == null) {
                m.put("insertSize", "");
            }
            if (m.get("readLength") == null) {
                m.put("readLength", "");
            }
            if (m.get("spots") == null) {
                m.put("spots", "");
            }
            if (m.get("experiment") == null) {
                m.put("experiment", "");
            }
            if (m.get("links") == null) {
                m.put("links", "");
            }
            Date time = (Date) m.get("createTime");
            m.put("createTime", simpleDateFormat.format(time));
            data.add(m);
        }
        return data;
    }
}
