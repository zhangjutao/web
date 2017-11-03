package com.gooalgene.mrna.service;

import com.gooalgene.entity.Study;
import com.gooalgene.mrna.entity.ExpressionTitles;
import com.mongodb.WriteResult;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by ShiYun on 2017/8/22 0022.
 */
@Service
public class MongoService {

    Logger logger = LoggerFactory.getLogger(MongoService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StudyService studyService;

    /**
     * 查询对应的列表
     *
     * @param type（1：表达 0：差异 -1：所有）
     * @return
     */
    public List<String> queryCollectionsByType(Integer type) {
        Set<String> set = mongoTemplate.getCollectionNames();
        List<String> result = new ArrayList<String>();
        for (String collection : set) {
            switch (type) {
                case 1:
                    if (collection.startsWith("Expression_")) {
                        result.add(collection);
                    }
                    break;
                case 0:
                    if (collection.startsWith("Comparison_")) {
                        result.add(collection);
                    }
                    break;
                case -1:
                    result.add(collection);
                    break;
            }
        }
        return result;
    }


    /**
     * 获取表达基因中出现的run和study对应关系
     *
     * @return
     */
    public Map<String, String> queryExpressionsRunAndStudy() {
        Map<String, String> map = new HashMap<String, String>();
        long st = System.currentTimeMillis();
        List<ExpressionTitles> expressionTitles = mongoTemplate.findAll(ExpressionTitles.class);
        for (ExpressionTitles expressionTitles1 : expressionTitles) {
            String sraStudy = expressionTitles1.getSraStudy();
            List<String> titles = expressionTitles1.getTitles();
            for (String study : titles) {
                map.put(study, sraStudy);
            }
        }
//        List<String> collections = queryCollectionsByType(1);
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.group("gene").push("$samples").as("samples"),
//                Aggregation.limit(1)
//        ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
//        logger.info("Query count:" + aggregation.toString());
//        for (String collection : collections) {
//            String sraStudy = collection.replace("Expression_", "");
//            long start = System.currentTimeMillis();
//            AggregationResults<SampleExpression> queryResult = mongoTemplate.aggregate(aggregation, collection, SampleExpression.class);
//            long end = System.currentTimeMillis();
//            logger.info(collection + " cost " + (end - start) / 1000 + "s.");
//            for (SampleExpression sampleExpression : queryResult) {
//                List<Run> runs = sampleExpression.getSamples();
//                if (!runs.isEmpty()) {
//                    for (Run run : runs) {
//                        map.put(run.getStudy(), sraStudy);
//                    }
//                    break;//每条数据对应的比较组都是一样的，所以直接取一个就好
//                }
//            }
//        }
        long en = System.currentTimeMillis();
        logger.info("queryExpressionsRunAndStudy total cost " + (en - st) / 1000 + "s.");
        return map;
    }

    /**
     * 变更Expression表头数据
     *
     * @param expressionTitles
     * @return
     */
    public boolean update(ExpressionTitles expressionTitles) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sraStudy").is(expressionTitles.getSraStudy()));
        Update update = new Update();
        update.set("sraStudy", expressionTitles.getSraStudy());
        update.set("titles", expressionTitles.getTitles());
        WriteResult writeResult = mongoTemplate.upsert(query, update, ExpressionTitles.class);
        System.out.println(writeResult.toString());
        return true;
    }

    /**
     * 根据sraStudy查询表头数据
     *
     * @param sraStudy
     * @return
     */
    public String[] queryTitlesBySraStudy(String sraStudy) {
        Query query = new Query();
        query.addCriteria(Criteria.where("sraStudy").is(sraStudy));
        ExpressionTitles expressionTitles = mongoTemplate.findOne(query, ExpressionTitles.class);
        if (expressionTitles != null) {
            List<String> titles = expressionTitles.getTitles();
            StringBuffer sb = new StringBuffer();
            int len = titles.size();
            for (int i = 0; i < len; i++) {
                sb.append(titles.get(i));
                if (i != (len - 1)) {
                    sb.append(",");
                }
            }
            return sb.toString().split(",");
        }
        return null;
    }

    /**
     * 批量写入数据到某个collection
     *
     * @param batchTosave
     * @param collectionName
     */
    public void insertBatch(List<Object> batchTosave, String collectionName, IndexDefinition indexDefinition) {
        long start = System.currentTimeMillis();
        mongoTemplate.indexOps(collectionName).ensureIndex(indexDefinition);
        mongoTemplate.insert(batchTosave, collectionName);
        long end = System.currentTimeMillis();
        logger.info("insert " + batchTosave.size() + " records costs " + (end - start) / 1000 + " s.");
    }

    /**
     * 写入一条记录到某个collection
     *
     * @param object
     * @param collectionName
     */
    public void insert(Object object, String collectionName) {
        long start = System.currentTimeMillis();
        mongoTemplate.insert(object, collectionName);
        long end = System.currentTimeMillis();
        logger.info("insert 1 record costs " + (end - start) / 1000 + " s.");
    }


    /**
     * 清空某个collection数据，不会删除索引
     *
     * @param collectionName
     */
    public void clearCollection(String collectionName) {
        mongoTemplate.remove(new Query(), collectionName);
        logger.info("remove all documents from collection " + collectionName + " .");
    }


    /**
     * 删除某个collection
     *
     * @param collectionName
     */
    public void dropCollection(String collectionName) {
        mongoTemplate.dropCollection(collectionName);
        logger.info("drop collection " + collectionName + " .");
    }

    /**
     * 变更collectionName
     *
     * @param oldName
     * @param newName
     */
    public void renameCollectionName(String oldName, String newName) {
        if (isCollectionExist(newName)) {
            dropCollection(newName);
            logger.info("drop collection " + newName);
        }
        mongoTemplate.getCollection(oldName).rename(newName);
        logger.info("remane collection " + oldName + " to " + newName);
    }

    /**
     * 判断某个collection是否存在
     *
     * @param collectionName
     * @return
     */
    public boolean isCollectionExist(String collectionName) {
        return mongoTemplate.collectionExists(collectionName);
    }


    /**
     * 写入大表数据
     *
     * @param fileName
     * @return
     */
    public boolean insertFPKMFile(String fileName) {
        boolean flag = false;
        Map<String, String> map = studyService.queryRunsAndTissueForClassify();//查询run和分类关系
//      Map<String, String> map1 = Tools.queryExpressionsRunAndStudy();//查询expression里面出现的run和study对应关系
        Map<String, String> map1 = queryExpressionsRunAndStudy();
        Index index = new Index();
        index.on("gene", Sort.Direction.ASC);
        index.on("samplerun", Sort.Direction.ASC);
        index.on("samplerun.name", Sort.Direction.ASC);
        index.on("samplerun.type", Sort.Direction.ASC);
        index.on("samplerun.study", Sort.Direction.ASC);
        index.unique();
        try {
            List<String> result = FileUtils.readLines(new File(fileName));
            int total = result.size();
            String[] runs = result.get(0).split("\t");//第一列为run列表
            int num = 0;
            long start = System.currentTimeMillis();
            List<Object> list = new ArrayList<Object>();
            for (int i = 1; i < total; i++) {
                String[] ss = result.get(i).split("\t");
                int len = ss.length;
                if (len >= 2) { //第一列为gene，后面的列对应run
                    String gen = ss[0];
                    for (int j = 1; j < len; j++) {
                        num++;
                        JSONObject one = new JSONObject();
                        one.put("gene", gen);
                        JSONObject two = new JSONObject();
                        String value = ss[j];
                        String run = runs[j];
                        two.put("name", run);
                        two.put("type", map.get(run) == null ? "" : map.get(run).toLowerCase());
                        two.put("study", (map1.get(run) == null ? "" : map1.get(run)));
                        logger.info(run + "\t" + map.get(run) + "\t" + map1.get(run));
                        two.put("value", Float.valueOf(value));
                        one.put("samplerun", two);
                        list.add(one);
                    }
                    if (list.size() >= 1000) {
                        insertBatch(list, "all_gens_fpkm", index);
                        list = new ArrayList<Object>();
                    }
                }
            }
            if (list.size() > 0) {
                insertBatch(list, "all_gens_fpkm", index);
            }
            long end = System.currentTimeMillis();
            logger.info("FPKMFile total:" + result.size() + ",title size:" + runs.length + ",num:" +
                    num + ",list size:" + list.size() + ",cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 更新大表数据
     *
     * @param fileName
     * @return
     */
    public boolean updateFPKMFile(String fileName) {
        boolean flag = false;
        Map<String, String> map = studyService.queryRunsAndTissueForClassify();//查询run和分类关系
//      Map<String, String> map1 = Tools.queryExpressionsRunAndStudy();//查询expression里面出现的run和study对应关系
        Map<String, String> map1 = queryExpressionsRunAndStudy();
        try {
            List<String> result = FileUtils.readLines(new File(fileName));
            int total = result.size();
            String[] runs = result.get(0).split("\t");//第一列为run列表
            int num = 0;
            long start = System.currentTimeMillis();
            for (int i = 1; i < total; i++) {
                String[] ss = result.get(i).split("\t");
                int len = ss.length;
                if (len >= 2) { //第一列为gene，后面的列对应run
                    String gen = ss[0];
                    for (int j = 1; j < len; j++) {
                        num++;
                        JSONObject two = new JSONObject();
                        String value = ss[j];
                        String run = runs[j];
                        two.put("name", run);
                        two.put("type", map.get(run) == null ? "" : map.get(run).toLowerCase());
                        two.put("study", (map1.get(run) == null ? "" : map1.get(run)));
                        two.put("value", Float.valueOf(value));
                        Update update = new Update();
                        update.set("gene", gen);
                        update.set("samplerun", two);
                        Query query = new Query();
                        query.addCriteria(Criteria.where("gene").is(gen).and("samplerun.name").is(run));
                        //此处有则更新、无则添加（可能存在旧数据没删掉，因为是按照name和run去匹配，上传新文件时，如果name变更了，则匹配不到）
                        WriteResult writeResult = mongoTemplate.upsert(query, update, "all_gens_fpkm");
                        logger.info("Update:[" + gen + "," + run + "]:" + writeResult.toString());
                    }
                }
            }
            long end = System.currentTimeMillis();
            logger.info("updateFPKMFile total:" + result.size() + ",title size:" + runs.length + ",num:" +
                    num + ",cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除大表数据
     *
     * @param fileName
     * @return
     */
    public boolean deleteFPKMFile(String fileName) {
        boolean flag = false;
        try {
            List<String> result = FileUtils.readLines(new File(fileName));
            int total = result.size();
            String[] runs = result.get(0).split("\t");//第一列为run列表
            int num = 0;
            long start = System.currentTimeMillis();
            for (int i = 1; i < total; i++) {
                String[] ss = result.get(i).split("\t");
                int len = ss.length;
                if (len >= 2) { //第一列为gene，后面的列对应run
                    String gen = ss[0];
                    for (int j = 1; j < len; j++) {
                        num++;
                        String run = runs[j];
                        Query query = new Query();
                        query.addCriteria(Criteria.where("gene").is(gen).and("samplerun.name").is(run));
                        WriteResult writeResult = mongoTemplate.remove(query, "all_gens_fpkm");
                        logger.info("Delete:[" + gen + "," + run + "]:" + writeResult.toString());
                    }
                }
            }
            long end = System.currentTimeMillis();
            logger.info("deleteFPKMFile total:" + result.size() + ",title size:" + runs.length + ",num:" +
                    num + ",cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 写入表达文件数据(先写入临时中间表，之后重命名（先删除旧表）)
     *
     * @param sraStudy
     * @param fileName
     * @return
     */
    public boolean insertExpressionFile(String sraStudy, String fileName) {
        boolean flag = false;
        File file = new File(fileName);
        String collections = "Expression_" + sraStudy;
        String collections_temp = "Expression_" + sraStudy + "_temp";
        long start = System.currentTimeMillis();
        Index index = new Index();
        index.on("gene", Sort.Direction.ASC);
        index.on("samples", Sort.Direction.ASC);
        index.on("samples.study", Sort.Direction.ASC);
        index.unique();
        List<String> extitles = new ArrayList<String>();
        Map<String, Study> runAndName = studyService.queryRuns(sraStudy);
        List<String> result = new ArrayList<String>();
        try {
            List<String> contexts = FileUtils.readLines(file);
            String title = contexts.get(0);
            String[] titles = title.split("\t");
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("geneid").append("\t");
            for (int i = 1; i < titles.length; i++) {//第一个为geneId 去掉
                String run = titles[i];
                extitles.add(run);
                if (runAndName.containsKey(run)) {
                    stringBuffer.append(runAndName.get(run).getSampleName());
                } else {
                    stringBuffer.append(run);
                }
                stringBuffer.append("\t");
            }
            result.add(stringBuffer.toString());//sampleName标题行
            result.add(title);//第一行
            int len = contexts.size();
            List<Object> list = new ArrayList<Object>();
            for (int i = 1; i < len; i++) {
                String line = contexts.get(i);
                result.add(line);
                String[] one = line.split("\t");
                int length = one.length;
                if (length == titles.length) {
                    String gen = one[0];
                    for (int j = 1; j < length; j++) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("gene", gen);
                        JSONObject jsonObject1 = new JSONObject();
                        String name = titles[j];
                        String value = one[j];
                        jsonObject1.put("study", name);
                        jsonObject1.put("value", Double.valueOf(value));
                        jsonObject.put("samples", jsonObject1);
                        list.add(Document.parse(jsonObject.toString()));
                    }
                    if (list.size() >= 1000) {
                        insertBatch(list, collections_temp, index);
                        list = new ArrayList<Object>();
                    }
                }
            }
            if (list.size() > 0) {
                insertBatch(list, collections_temp, index);
            }
            flag = true;
        } catch (IOException e) {
            flag = false;
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        logger.info("ExpressionFile " + sraStudy + " all insert cost:" + (end - start) / 1000 + "s");
        if (flag) {//成功，重命名
            renameCollectionName(collections_temp, collections);
            //更新表头数据表
            ExpressionTitles expressionTitles = new ExpressionTitles();
            expressionTitles.setSraStudy(sraStudy);
            expressionTitles.setTitles(extitles);
            update(expressionTitles);
            //重写文件内容
            try {
                FileUtils.writeLines(new File(fileName), result, "\n", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {//失败，删除中间表
            dropCollection(collections_temp);
        }
        return flag;
    }

    /**
     * 更新表达文件数据 --页面已经取消（如果修改，需要考虑表头数据的修改）
     *
     * @param sraStudy
     * @param fileName
     * @return
     */
    public boolean updateExpressionFile(String sraStudy, String fileName) {
        boolean flag = false;
        File file = new File(fileName);
        try {
            List<String> contexts = FileUtils.readLines(file);
            String studyNo = sraStudy;
            String[] titles = contexts.get(0).split("\t");
            int len = contexts.size();
            long start = System.currentTimeMillis();
            for (int i = 1; i < len; i++) {
                String[] one = contexts.get(i).split("\t");
                int length = one.length;
                if (length == titles.length) {
                    String gen = one[0];
                    for (int j = 1; j < length; j++) {
                        Update update = new Update();
                        update.set("gene", gen);
                        JSONObject jsonObject1 = new JSONObject();
                        String name = titles[j];
                        String value = one[j];
                        jsonObject1.put("study", name);
                        jsonObject1.put("value", Double.valueOf(value));
                        update.set("samples", jsonObject1);
                        Query query = new Query();
                        query.addCriteria(Criteria.where("gene").is(gen).and("samples.study").is(name));
                        WriteResult writeResult = mongoTemplate.updateMulti(query, update, "Expression_" + sraStudy);
                        logger.info("Update:[" + gen + "," + name + "]:" + writeResult.toString());
                    }
                }
            }
            long end = System.currentTimeMillis();
            logger.info("updateExpression to " + studyNo + " all cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除表达文件数据 --页面已经取消
     *
     * @param sraStudy
     * @param fileName
     * @return
     */
    public boolean deleteExpressionFile(String sraStudy, String fileName) {
        boolean flag = false;
        File file = new File(fileName);
        try {
            List<String> contexts = FileUtils.readLines(file);
            String studyNo = sraStudy;
            String[] titles = contexts.get(0).split("\t");
            int len = contexts.size();
            long start = System.currentTimeMillis();
            for (int i = 1; i < len; i++) {
                String[] one = contexts.get(i).split("\t");
                int length = one.length;
                if (length == titles.length) {
                    String gen = one[0];
                    for (int j = 1; j < length; j++) {
                        String name = titles[j];
                        Query query = new Query();
                        query.addCriteria(Criteria.where("gene").is(gen).and("samples.study").is(name));
                        WriteResult writeResult = mongoTemplate.remove(query, "Expression_" + sraStudy);
                        logger.info("Delete:[" + gen + "," + name + "]:" + writeResult.toString());
                    }
                }
            }
            long end = System.currentTimeMillis();
            logger.info("deleteExpression from " + studyNo + " all cost:" + (end - start) / 1000 + "s");
            flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 写入差异基因数据(先写入临时中间表，再重命名（先删除旧表）)
     *
     * @param sraStudy
     * @param dir
     * @return
     */
    public boolean insertComparisonFile(String sraStudy, String dir) {
        boolean flag = false;
        File file = new File(dir);
        String collections = "Comparison_" + sraStudy;
        String collections_temp = "Comparison_" + sraStudy + "_temp";
        long start = System.currentTimeMillis();
        Index index = new Index();
        index.on("gene", Sort.Direction.ASC);
        index.on("diffs", Sort.Direction.ASC);
        index.on("diffs.name", Sort.Direction.ASC);
        index.unique();
        if (file.exists()) {
            File[] files = file.listFiles();
            Map<String, Study> runAndName = studyService.queryRuns(sraStudy);
            logger.info(dir + "has file num " + files.length);
            for (File f : files) {
                String type = f.getName().substring(0, f.getName().indexOf("."));
                List<Object> list = new ArrayList<Object>();
                long a = System.currentTimeMillis();
                List<String> result = new ArrayList<String>();
                try {
                    List<String> contexts = FileUtils.readLines(f);
                    String title = contexts.get(0);
                    String[] titles = title.split("\t");
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int i = 0; i < titles.length; i++) {
                        String s = titles[i];
                        if ((i == 1 || i == 2) && runAndName.containsKey(s)) {
                            stringBuffer.append(runAndName.get(s).getSampleName());
                        } else {
                            stringBuffer.append(s);
                        }
                        stringBuffer.append("\t");
                    }
                    result.add(stringBuffer.toString());//替换run为sampleName的首行
                    int total = contexts.size();
                    for (int i = 1; i < total; i++) {
                        String line = contexts.get(i);
                        result.add(line);
                        String[] ss = line.split("\t");
                        if (ss.length == 6) {
                            String gene = ss[0];
                            String v1 = ss[1];
                            String v2 = ss[2];
                            String log2 = ss[3];
                            if (log2.equals("inf")) {
                                log2 = "32";
                            }
                            String pValue = ss[4];
                            if (pValue.equals("NA")) {
                                pValue = "0";
                            }
                            String qValue = ss[5];
                            if (qValue.equals("NA")) {
                                qValue = "0";
                            }
                            JSONObject one = new JSONObject();
                            one.put("name", type);
                            one.put("log_value", Double.valueOf(log2));
                            one.put("p_value", Double.valueOf(pValue));
                            one.put("q_value", Double.valueOf(qValue));
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("gene", gene);
                            jsonObject.put("diffs", one);
                            list.add(jsonObject);
                            if (list.size() >= 1000) {
                                insertBatch(list, collections_temp, index);
                                list = new ArrayList<Object>();
                            }
                        }
                    }
                    if (list.size() > 0) {
                        insertBatch(list, collections_temp, index);
                    }
                    long b = System.currentTimeMillis();
                    flag = true;
                    //重写文件内容
                    try {
                        FileUtils.writeLines(new File(f.getAbsolutePath()), result, "\n", false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.info(f.getName() + ",size:" + contexts.size() + ", insert cost " + (b - a) / 1000 + "s.");
                } catch (IOException e) {
                    flag = false;
                    e.printStackTrace();
                }
            }
            if (flag) {//写入成功，重命名
                renameCollectionName(collections_temp, collections);
            } else {//写入失败，删除中间表
                dropCollection(collections_temp);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("insert " + collections + " total cost:" + (end - start) / 1000 + "s.");
        return flag;
    }

    /**
     * 更新差异基因数据--页面已取消
     *
     * @param sraStudy
     * @param dir
     * @return
     */
    public boolean updateComparisonFile(String sraStudy, String dir) {
        boolean flag = false;
        File file = new File(dir);
        long start = System.currentTimeMillis();
        String collections = "Comparison_" + sraStudy;
        if (file.exists()) {
            File[] files = file.listFiles();
            logger.info(dir + "has file num " + files.length);
            for (File f : files) {
                String type = f.getName().substring(0, f.getName().indexOf("."));
                long a = System.currentTimeMillis();
                try {
                    List<String> contexts = FileUtils.readLines(f);
                    int total = contexts.size();
                    for (int i = 1; i < total; i++) {
                        String[] ss = contexts.get(i).split("\t");
                        if (ss.length == 6) {
                            String gene = ss[0];
                            String v1 = ss[1];
                            String v2 = ss[2];
                            String log2 = ss[3];
                            if (log2.equals("inf")) {
                                log2 = "32";
                            }
                            String pValue = ss[4];
                            if (pValue.equals("NA")) {
                                pValue = "0";
                            }
                            String qValue = ss[5];
                            if (qValue.equals("NA")) {
                                qValue = "0";
                            }
                            JSONObject one = new JSONObject();
                            one.put("name", type);
                            one.put("log_value", Double.valueOf(log2));
                            one.put("p_value", Double.valueOf(pValue));
                            one.put("q_value", Double.valueOf(qValue));
                            Update update = new Update();
                            update.set("gene", gene);
                            update.set("diffs", one);
                            Query query = new Query();
                            query.addCriteria(Criteria.where("gene").is(gene).and("diffs.name").is(type));
                            WriteResult writeResult = mongoTemplate.updateMulti(query, update, collections);
                            logger.info("Update:[" + gene + "," + type + "]:" + writeResult.toString());
                        }
                    }
                    long b = System.currentTimeMillis();
                    logger.info(f.getName() + ",size:" + contexts.size() + ", update cost " + (b - a) / 1000 + "s.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                flag = true;
            }
        }
        long end = System.currentTimeMillis();
        logger.info("update " + collections + " total cost:" + (end - start) / 1000 + "s.");
        return flag;
    }

    /**
     * 删除差异基因数据 ---也面已经取消
     *
     * @param sraStudy
     * @param dir
     * @return
     */
    public boolean deleteComparisonFile(String sraStudy, String dir) {
        boolean flag = false;
        File file = new File(dir);
        long start = System.currentTimeMillis();
        String collections = "Comparison_" + sraStudy;
        if (file.exists()) {
            File[] files = file.listFiles();
            logger.info(dir + "has file num " + files.length);
            for (File f : files) {
                String type = f.getName().substring(0, f.getName().indexOf("."));
                try {
                    List<String> contexts = FileUtils.readLines(f);
                    logger.info(type + ",size:" + contexts.size());
                    int total = contexts.size();
                    for (int i = 1; i < total; i++) {
                        String[] ss = contexts.get(i).split("\t");
                        if (ss.length == 6) {
                            String gene = ss[0];
                            Query query = new Query();
                            query.addCriteria(Criteria.where("gene").is(gene).and("diffs.name").is(type));
                            WriteResult writeResult = mongoTemplate.remove(query, collections);
                            logger.info("Delete:[" + gene + "," + type + "]:" + writeResult.toString());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            flag = true;
        }
        long end = System.currentTimeMillis();
        logger.info("delete " + collections + " total cost:" + (end - start) / 1000 + "s.");
        return flag;
    }
}
