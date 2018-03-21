package com.gooalgene.mrna.service;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Study;
import com.gooalgene.mrna.entity.*;
import com.gooalgene.entity.Genes;
import com.gooalgene.qtl.service.GenesService;
import com.gooalgene.utils.StringUtils;
import com.gooalgene.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/8/11 0011.
 */
@Service
public class GeneService {

    Logger logger = LoggerFactory.getLogger(GeneService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StudyService studyService;

    @Autowired
    private GenesService genesService;

    @Autowired
    private MongoService mongoService;

    /**
     * 对基因进行搜索查询
     *
     * @param gene
     * @param pageNo
     * @param pageSize
     * @return
     */
    public JSONObject queryGenes(String gene, String pageNo, String pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNo", pageNo);
        jsonObject.put("pageSize", pageSize);
        JSONArray data = new JSONArray();
        long total = 0;
        try {
            int pNo = Integer.valueOf(pageNo);
            int pSize = Integer.valueOf(pageSize);
            int skip = (pNo - 1) * pSize;
            if (skip < 0) {
                skip = 0;
            }
            Query query = new Query();
            if (StringUtils.isNotBlank(gene)) {
                if (gene.contains(",")) {//多个基因查询 使用 in
                    String[] gens = gene.split(",");
                    query.addCriteria(Criteria.where("gene").in(gens));
                    logger.info("Query count:" + query.toString());
                    total = mongoTemplate.count(query, "all_gens_fpkm");
                    query.skip(skip).limit(pSize);
                    logger.info("Query:" + query.toString() + ",total:" + total);
                    List<Expression> list = mongoTemplate.find(query, Expression.class, "all_gens_fpkm");
                    for (Expression expression : list) {
                        data.add(expression.getGene());
                    }
                } else {//单个基因 模糊匹配 速度太慢 故改为查询mysql
                    Genes genes = new Genes();//搜索关键字
//                  genes.setGene(gene);
                    genes.setKeywords(gene);
                    Page<Genes> page = new Page<Genes>();
                    page.setPageNo(pNo);
                    page.setPageSize(pSize);
                    genes.setPage(page);
                    List<Genes> list = genesService.query(genes);
                    total = page.getCount();
                    for (Genes genes1 : list) {
                        data.add(genes1.getGene());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("genes query error", e);
        }
        jsonObject.put("total", total);
        jsonObject.put("data", data);
        return jsonObject;
    }

    /**
     * 查询表达基因数据
     *
     * @param id
     * @param gene
     * @param expressionValue
     * @param pageNo
     * @param pageSize
     * @return
     */
    public JSONObject queryStudyExpression(String id, String gene, String expressionValue, String pageNo, String pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNo", pageNo);
        jsonObject.put("pageSize", pageSize);
        JSONArray data = new JSONArray();
        long total = 0;
        Double min = 0.0, max = 0.0;
        try {
            Study study = studyService.queryById(id);
            if (study != null) {
                String sraStudy = study.getSraStudy();
                String collection = "Expression_" + sraStudy;
                boolean flag = mongoTemplate.collectionExists(collection);
                System.out.println(flag + " exist:" + collection);
                if (flag) {
//                    String[] runs = Tools.queryExpressions(sraStudy);//表达基因表头
                    String[] runs = mongoService.queryTitlesBySraStudy(sraStudy);
                    if (runs != null) {
                        int pNo = Integer.valueOf(pageNo);
                        int pSize = Integer.valueOf(pageSize);
                        int skip = (pNo - 1) * pSize;
                        if (skip < 0) {
                            skip = 0;
                        }
                        Criteria criteria = new Criteria();
                        boolean isExistGene = true;
                        boolean needMatchGene = false;
                        if (StringUtils.isNotBlank(gene)) {
                            String[] strings = genesService.queryGensByKey(gene);
//                            criteria.and("gene").regex(Tools.getRegex(gene));
                            if (strings != null && strings.length != 0) {
                                criteria.and("gene").in(strings);
                                needMatchGene = true;
                                logger.info("queryStudyExpression match gene size:" + strings.length);
                            } else {
                                isExistGene = false;
                            }
                        }
                        if (StringUtils.isNoneBlank(expressionValue)) {
                            String[] ss = expressionValue.split(",");
                            if (ss.length == 2) {
                                if (Tools.isNumeric(ss[1])) {
                                    double value = Double.valueOf(ss[1]);
                                    if (ss[0].equals(">")) {
                                        criteria.and("samples.value").gt(value);
                                    } else if (ss[0].equals("=")) {
                                        criteria.and("samples.value").is(value);
                                    } else if (ss[0].equals("<")) {
                                        criteria.and("samples.value").lt(value);
                                    }
                                }
                            }
                        }
                        if (isExistGene) {
                            Query query = new Query();
                            query.addCriteria(criteria);
                            logger.info("Query count:" + query.toString());
                            Object json = query.getQueryObject().get("samples.value");
                            long a = System.currentTimeMillis();
                            if (needMatchGene || json != null) {
                                Aggregation a1 = Aggregation.newAggregation(
//                                        Aggregation.unwind("$samples"),
                                        Aggregation.match(criteria),
                                        Aggregation.group("gene")).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                                AggregationResults<Map> list1 = mongoTemplate.aggregate(a1, collection, Map.class);
                                JSONArray rest = JSONArray.fromObject(list1.getRawResults().get("result"));
                                total = rest.size();
                            } else {
                                total = 54174;//总基因数（没有匹配的话，不用去查了）
                                logger.info("Total 54174 with no query.");
                            }
                            logger.info("Total:" + total);
                            long b = System.currentTimeMillis();
                            Aggregation aggregation = Aggregation.newAggregation(
//                                    Aggregation.unwind("$samples"),
                                    Aggregation.match(criteria),
                                    Aggregation.group("gene").push("$samples").as("samples"),
                                    Aggregation.skip(Long.valueOf(skip)),
                                    Aggregation.limit(Long.valueOf(pageSize))).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                            logger.info("Query:" + aggregation.toString());
                            AggregationResults<SampleExpression> list2 = mongoTemplate.aggregate(aggregation, collection, SampleExpression.class);
                            long c = System.currentTimeMillis();
                            logger.info("Cost:" + (b - a) / 1000 + "s," + (c - b) / 1000 + "s");
                            Map<String, Study> allRuns = studyService.queryExpressionRuns(sraStudy);
                            logger.info("Total:" + total + ",samples.value json:" + json + ",size:" + total);
                            for (SampleExpression expression : list2) {
                                JSONObject genes = new JSONObject();
                                genes.put("geneName", expression.getId());//group之后id就是gene
                                List<Run> sampleRuns = expression.getSamples();
                                JSONArray array = new JSONArray();
                                for (Run sampleRun : sampleRuns) {
                                    JSONObject group = new JSONObject();
                                    String run = sampleRun.getStudy();
                                    Double value = sampleRun.getValue();
                                    if (allRuns.containsKey(run) && (json != null ? Tools.compareLog2AndQuery(value, json) : true)) {
                                        Study study1 = allRuns.get(run);
                                        group.put("sampleNo", run);
                                        group.put("sampleName", study1.getSampleName() != null ? study1.getSampleName() : "");
                                        if (min.compareTo(value) > 0) {
                                            min = value;
                                        }
                                        if (max.compareTo(value) < 0) {
                                            max = value;
                                        }
                                        group.put("value", value);
                                        group.put("type", study1.getType() == null ? "" : study1.getType());
                                        group.put("phenotype", study1.getPhenoType() == null ? "" : study1.getPhenoType());
                                        group.put("tissue", study1.getTissue() == null ? "" : study1.getTissue());
                                        group.put("stage", study1.getStage() == null ? "" : study1.getStage());
                                        array.add(group);
                                    }
                                }
                                genes.put("samples", array);
                                data.add(genes);
                            }
                        }
                        if (data.size() == 0) {
                            total = 0;
                            logger.info("query with no result:" + total);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("studyExpression query error.", e);
        }
        jsonObject.put("min", min);
        jsonObject.put("max", max);
        jsonObject.put("total", total);
        jsonObject.put("data", data);
        return jsonObject;
    }

    /**
     * 查询差异基因数据
     *
     * @param id
     * @param gene
     * @param logFoldChange
     * @param adjustedPValue
     * @param sort           up/dowan/up_or_down
     * @param pageNo
     * @param pageSize
     * @return
     */
    public JSONObject queryStudyComparison(String id, String gene, String logFoldChange, String adjustedPValue, String sort, String pageNo, String pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNo", pageNo);
        jsonObject.put("pageSize", pageSize);
        JSONArray data = new JSONArray();
        long total = 0;
        Double min = 0.0, max = 0.0;
        try {
            Study study = studyService.queryById(id);
            if (study != null) {
                String sraStudy = study.getSraStudy();
                String collections = "Comparison_" + sraStudy;
                boolean flag = mongoTemplate.collectionExists(collections);
                System.out.println(flag + " exist:" + collections);
                if (flag) {
                    int pNo = Integer.valueOf(pageNo);
                    int pSize = Integer.valueOf(pageSize);
                    int skip = (pNo - 1) * pSize;
                    if (skip < 0) {
                        skip = 0;
                    }
                    boolean isExistGene = true;
                    Criteria criteria = new Criteria();
                    if (StringUtils.isNotBlank(gene)) {
//                        criteria.and("gene").regex(Tools.getRegex(gene));
                        String[] strings = genesService.queryGensByKey(gene);
                        if (strings != null && strings.length != 0) {
                            criteria.and("gene").in(strings);
                            logger.info("queryStudyComparison match gene size:" + strings.length);
                        } else {
                            isExistGene = false;
                        }
                    }
                    if (StringUtils.isNoneBlank(logFoldChange)) {//you can't add a second 'diffs.log_value' criteria. Query already contains
                        Integer log2 = Integer.valueOf(logFoldChange);
                        if (log2 == 0) {//不筛选

                        } else {
                            Integer start = -log2;//负数
                            if ("up".equals(sort)) {
                                criteria.and("diffs.log_value").gt(log2);
                            } else if ("down".equals(sort)) {
                                criteria.and("diffs.log_value").lt(start);
                            } else {
                                criteria.orOperator(Criteria.where("diffs.log_value").gt(log2), (Criteria.where("diffs.log_value").lt(start)));
                            }
                        }
                    }
                    if (Tools.isNumeric(adjustedPValue)) {
                        //文档要求：默认小于
                        criteria.and("diffs.p_value").lt(Double.valueOf(adjustedPValue));
                    }
                    if (isExistGene) {
                        Query query = new Query();
                        query.addCriteria(criteria);
                        logger.info("======" + criteria.getCriteriaObject());
                        Aggregation aggregation1 = Aggregation.newAggregation(
//                                Aggregation.unwind("$diffs"),
                                Aggregation.match(criteria),
                                Aggregation.group("gene")
                        ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                        AggregationResults<Map> list1 = mongoTemplate.aggregate(aggregation1, collections, Map.class);
                        logger.info("Query count:" + aggregation1.toString());
                        JSONArray rest = JSONArray.fromObject(list1.getRawResults().get("result"));
                        total = rest.size();
                        Aggregation aggregation = Aggregation.newAggregation(
//                                Aggregation.unwind("$diffs"),
                                Aggregation.match(criteria),
                                Aggregation.group("gene").push("$diffs").as("diffs"),
                                Aggregation.skip(Long.valueOf(skip)),
                                Aggregation.limit(Long.valueOf(pageSize))
                        ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
                        logger.info("Query count:" + aggregation.toString() + ",total:" + total);
                        AggregationResults<Comparison> list = mongoTemplate.aggregate(aggregation, collections, Comparison.class);
                        logger.info("Total:" + total);
//                    db.ERP016957.aggregate([{"$unwind":"$diffs"},
//                    {"$match":{"diffs.log_value" : { "$gt" : -1 , "$lt" : 1} , "diffs.p_value" : { "$lt" : 0.05}}},
//                    {"$group": {"_id": "$gene","diffs":{'$push': "$diffs"}}}])
//                    List<Comparison> list = mongoTemplate.find(query, Comparison.class, sraStudy);
//                    Object json = query.getQueryObject().get("diffs.log_value");
//                    logger.info("Query:" + query.getQueryObject() + ",Json:" + json);
//                        String cultivar = (study.getCcultivar() == null ? "" : study.getCcultivar());
//                        String phenotype = (study.getPhenoType() == null ? "" : study.getPhenoType());
                        Map<String, Study> allRuns = studyService.queryRuns(sraStudy);
                        for (Comparison comparison : list) {
                            JSONObject genes = new JSONObject();
                            genes.put("geneName", comparison.getId());
                            List<Diff> diffs = comparison.getDiffs();
                            JSONArray array = new JSONArray();
                            for (Diff diff : diffs) {
                                Study study1 = allRuns.get(diff.getName());
                                JSONObject group = new JSONObject();
                                group.put("compareGroupName", Tools.changeDiff2SampleName(allRuns, diff.getName()));//显示sampleName
                                group.put("compareGroupNo", diff.getName());
                                group.put("value", diff.getQ_value());
                                group.put("type", (study1 == null || (study1.getType() == null) ? "" : study1.getType()));
                                group.put("phenotype", (study1 == null || (study1.getPhenoType() == null) ? "" : study1.getPhenoType()));
                                Double log2 = diff.getLog_value();
                                if (log2.compareTo(32.0) == 0) {
                                    group.put("logFoldChange", "inf");//不符合条件的页面无弹窗展示
//                                System.out.println("=====inf");
                                } else {
                                    if (min.compareTo(log2) > 0) {
                                        min = log2;
                                    }
                                    if (max.compareTo(log2) < 0) {
                                        max = log2;
                                    }
                                    group.put("logFoldChange", log2);//不符合条件的页面无弹窗展示
                                }
                                group.put("adjustedPValue", diff.getP_value());
                                array.add(group);
//                            System.out.println(diff.getLog_value() + "\t" + diff.getP_value() + "\t" + diff.getQ_value());
                            }
                            genes.put("compareGroups", array);
                            data.add(genes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("studyComparison query error.", e);
        }
        jsonObject.put("min", min);
        jsonObject.put("max", max);
        jsonObject.put("total", total);
        jsonObject.put("data", data);
        return jsonObject;
    }


    /**
     * 差异基因数据导出
     *
     * @param sraStudy
     * @param gene
     * @param logFoldChange
     * @param adjustedPValue
     * @param sort
     * @param titles
     * @return
     */
    public String queryStudyComparisonToExport(String sraStudy, String gene, String logFoldChange, String adjustedPValue, String sort, String[] titles) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (titles != null) {
            sb = new StringBuilder();
            sb.append("genid").append(",");
            int len = titles.length;
            Map<String, Study> allRuns = studyService.queryRuns(sraStudy);
            for (int i = 0; i < len; i++) {
                String ss = titles[i];
                sb.append(Tools.changeDiff2SampleName(allRuns, ss));
//                String[] sss = ss.split("_");
//                sb.append(allRuns.get(sss[0])).append(",").append(allRuns.get(sss[2]));
                if (i != (len - 1)) {
                    sb.append(",");
                } else {
                    sb.append("\n");
                }
//                map.put(sss[0], i);
//                map.put(sss[2], i);
                map.put(ss, i);
            }
            boolean flag = mongoTemplate.collectionExists(sraStudy);
            logger.info(flag + " exist:" + sraStudy);
            if (flag) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                if (StringUtils.isNotBlank(gene)) {
                    criteria.and("gene").regex(Tools.getRegex(gene));
                }
                if (StringUtils.isNoneBlank(logFoldChange)) {//you can't add a second 'diffs.log_value' criteria. Query already contains
                    Integer log2 = Integer.valueOf(logFoldChange);
                    if (log2 == 0) {//不筛选

                    } else {
                        Integer start = -log2;//负数
                        if ("up".equals(sort)) {
                            criteria.and("diffs.log_value").gt(log2);
                        } else if ("down".equals(sort)) {
                            criteria.and("diffs.log_value").lt(start);
                        } else {
                            criteria.orOperator(Criteria.where("diffs.log_value").gt(log2), (Criteria.where("diffs.log_value").lt(start)));
                        }
                    }
                }
                if (Tools.isNumeric(adjustedPValue)) {
                    //文档要求：默认小于
                    criteria.and("diffs.p_value").lt(Double.valueOf(adjustedPValue));
                }
                query.addCriteria(criteria);
                logger.info("Query count:" + query.toString());
                List<Comparison> list = mongoTemplate.find(query, Comparison.class, sraStudy);
                Object json = query.getQueryObject().get("diffs.log_value");
                logger.info("Query:" + query.toString() + ",Json:" + json);
                for (Comparison comparison : list) {
                    sb.append(comparison.getGene()).append(",");
                    List<Diff> diffs = comparison.getDiffs();
                    for (Diff diff : diffs) {
                        Double log2 = diff.getLog_value();
                        if (map.containsKey(diff.getName())) {
                            if (log2.compareTo(32.0) == 0) {
                                sb.append("inf").append(",");
                            } else {
                                sb.append((Tools.compareLog2AndQuery(log2, json) ? log2 : "")).append(",");
                            }
                        }
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    public List<Map> queryExpression(String sraStudy) {
        long a = System.currentTimeMillis();
        List<Map> mapList = mongoTemplate.find(new Query(), Map.class, "Expression_" + sraStudy);
        long b = System.currentTimeMillis();
        logger.info("Cost:" + (b - a) / 1000 + "s." + mapList.size());
        return mapList;
    }


    /**
     * 表达基因数据导出
     *
     * @param sraStudy
     * @param gene
     * @param expressionValue
     * @param titles
     * @return
     */
    public String queryStudyExpressionToExport(String sraStudy, String gene, String expressionValue, String[] titles) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (titles != null) {
            Map<String, Study> allRuns = studyService.queryExpressionRuns(sraStudy);
            sb = new StringBuilder();
            sb.append("genid").append(",");
            int len = titles.length;
            for (int i = 0; i < len; i++) {
                String run = titles[i];
                sb.append(allRuns.get(run) == null ? run : allRuns.get(run).getSampleName());
                if (i != (len - 1)) {
                    sb.append(",");
                } else {
                    sb.append("\n");
                }
                map.put(titles[i], i);
            }
            if (!allRuns.isEmpty()) {
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("samplerun.study").is(sraStudy);
                query.addCriteria(Criteria.where("samplerun.study").is(sraStudy));
                if (StringUtils.isNotBlank(gene)) {
                    query.addCriteria(Criteria.where("gene").regex(Tools.getRegex(gene)));
                    criteria.and("gene").regex(Tools.getRegex(gene));
                }
                Query query1 = new Query();
                if (StringUtils.isNoneBlank(expressionValue)) {
                    String[] ss = expressionValue.split(",");
                    if (ss.length == 2) {
                        if (Tools.isNumeric(ss[1])) {
                            double value = Double.valueOf(ss[1]);
                            if (ss[0].equals(">")) {
                                query.addCriteria(Criteria.where("samplerun.value").gt(value));
                                criteria.and("samplerun.value").gt(value);
                            } else if (ss[0].equals("=")) {
                                query.addCriteria(Criteria.where("samplerun.value").is(value));
                                criteria.and("samplerun.value").is(value);
                            } else if (ss[0].equals("<")) {
                                query.addCriteria(Criteria.where("samplerun.value").lt(value));
                                criteria.and("samplerun.value").lt(value);
                            }
                        }
                    }
                }
                logger.info("Query:" + query.toString() + ",Query1:" + query1.toString());
//            query.limit(100);
//            List<Expression> list = mongoTemplate.find(query, Expression.class, "all_gens_fpkm");
                Object json = query.getQueryObject().get("samplerun.value");
                List<Map> queryExpressionAll = queryExpression(sraStudy);
                List<String> runs = studyService.queryExpression(sraStudy);//表头
                for (Map map1 : queryExpressionAll) {
                    String ge = (String) map1.get("gene");
                    JSONArray values = JSONArray.fromObject(map1.get("samples"));
                    String content = Tools.getResult(values, json);
                    if (content != null) {
                        sb.append(ge).append(",").append(content).append("\n");
                    }
                }

//                Aggregation aggregation = Aggregation.newAggregation(Aggregation.unwind("$samplerun"),
//                        Aggregation.match(criteria),
//                        Aggregation.group("_id", "gene").push("$samplerun").as("samplerun"),
//                        Aggregation.limit(100)
//                ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
//                AggregationResults<Expression> list = mongoTemplate.aggregate(aggregation, "all_gens_fpkm", Expression.class);
//                long c = System.currentTimeMillis();
//                logger.info("Cost:" + (c - a) / 1000 + "s.");
//                for (Expression expression : list) {
//                    sb.append(expression.getGene()).append(",");
//                    List<SampleRun> sampleRuns = expression.getSamplerun();
//                    for (SampleRun sampleRun : sampleRuns) {
//                        String run = sampleRun.getName();
//                        Double value = sampleRun.getValue();
//                        if (map.containsKey(run)) {
//                            sb.append(value).append(",");
//                        }
//                    }
//                    sb.append("\n");
//                }
            }
        }
        return sb.toString();
    }
}
