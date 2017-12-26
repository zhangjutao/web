package com.gooalgene.mrna.service;

import com.gooalgene.common.handler.DocumentCallbackHandlerImpl;
import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.vo.GResultVo;
import com.gooalgene.mrna.vo.GVo;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.mrna.vo.GenVo;
import com.mongodb.WriteResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ShiYun on 2017/7/31 0031.
 * Modified By Crabime on 12/14/2017
 */
@Service
public class ClassifyService {
    private final static Logger logger = LoggerFactory.getLogger(ClassifyService.class);
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TService tService;

    private final static int TOTAL = 1000; //线程总个数

    private final static String ALL_GENE_FPKM = "all_gens_fpkm";

    private CopyOnWriteArrayList<String> eligibilities = new CopyOnWriteArrayList<>();  //符合条件基因存放位置

    public boolean insert(Classifys classifys) {
        mongoTemplate.insert(classifys);
        return true;
    }

    public boolean update(Classifys classifys) {
        Query query = new Query();
        if (classifys.getId() == null) {
            query.addCriteria(Criteria.where("name").is(classifys.getName()));
        } else {
            query.addCriteria(Criteria.where("id").is(classifys.getId()));
        }
        Update update = new Update();
        update.set("name", classifys.getName());
        update.set("chinese", classifys.getChinese());
        update.set("children", classifys.getChildren());
        WriteResult writeResult = mongoTemplate.upsert(query, update, Classifys.class);
        System.out.println(writeResult.toString());
        return true;
    }

    public boolean delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        WriteResult writeResult = mongoTemplate.remove(query, Classifys.class);
        System.out.println(writeResult.toString());
        return true;
    }

    public boolean query() {
        List<Classifys> all = mongoTemplate.findAll(Classifys.class);
        for (Classifys classifys : all) {
            logger.info(classifys.getId() + "\t" + classifys.getName()
                    + "\t" + classifys.getChinese() + "\t" + classifys.getChildren().size());
        }
        return true;
    }

    public GenResult findAllClassifys(String[] gens) {

        List<Classifys> all = mongoTemplate.findAll(Classifys.class);

        GenResult genResult = new GenResult();

//		 List<GResultVo> grvos = new ArrayList<GResultVo>();

        List<GVo> gvos = new ArrayList<GVo>();

        // String[] gens = { "GLYMA02G13420","Glyma7G849002","Glyma7G849003","Glyma7G849004"};
        for (Classifys c : all) {

            String name = c.getName();

            Map<String, Map<String, GenVo>> genvoMap = new HashMap<String, Map<String, GenVo>>();

            Map<String, Integer> levelMap = new HashMap<String, Integer>();

            int level = 0;
            // 循环基因，取出基因对应的count，value
            for (String gen : gens) {
                GenVo vo = new GenVo();
                vo.setName(name);
                vo.setLevel(level);
                Map<String, Map<String, Object>> allMap = gens(gen);
                Map<String, Object> countMap = allMap.get("count");
                Map<String, Object> valueMap = allMap.get("value");
                String countTemp = "";
                String valueTemp = "";
                if (null == countMap.get(name)) {
                    countTemp = "0";
                } else {
                    countTemp = String.valueOf(countMap.get(name));
                }
                if (null == valueMap.get(name)) {
                    valueTemp = "0";

                } else {
                    valueTemp = String.valueOf(valueMap.get(name));
                }
                double count = Double.parseDouble(countTemp);
                double value = Double.parseDouble(valueTemp);
                vo.setCount(count);
                vo.setValue(value);
                Map<String, GenVo> genMap = new HashMap<String, GenVo>();
                genMap.put(gen, vo);
                genvoMap.put(gen + level, genMap);
                levelMap.put(gen, level);
            }

            List<Map<String, Object>> childs = c.getChildren();
            if (childs.size() > 0) {
                generateData(childs, genvoMap, level + 1, levelMap, gens);
            }


            for (String g : gens) {
                int index = levelMap.get(g);
                String pname = "";
                for (int i = 0; i < index; i++) {
                    Map<String, GenVo> genvo = genvoMap.get(g + i);

                    if (null == genvo) {
                        continue;
                    } else {
                        GenVo xx = genvo.get(g);

                        GVo gvo = new GVo();
                        if (0.0 == xx.getValue() || 0.0 == xx.getCount()) {
                            gvo.setValue(0.0);
                        } else {
                            double avg = xx.getValue() / xx.getCount();
                            gvo.setValue(avg);
                        }

                        gvo.setLevel(i);
                        gvo.setName(xx.getName());
                        gvo.setPname(pname);
                        pname = xx.getName();

                        gvo.setState("close");
                        gvo.setGen(g);
                        gvos.add(gvo);
                    }
                }
            }

//		    genResult.setCate(grvos);
//		    genResult.setGens(gens);
        }

        Map<String, List<Double>> dataMap = new HashMap<String, List<Double>>();

        List<Double> list = null;

        for (GVo gvo : gvos) {

            System.out.println("gen:" + gvo.getGen() + ",name:" + gvo.getName() + ", pname:" + gvo.getPname() + ",level:" + gvo.getLevel() + ",value:" + gvo.getValue());
            String n = gvo.getName();

            int ev = gvo.getLevel();

            String pname = gvo.getPname();

            Double value = gvo.getValue();
            String key = "";
            if (StringUtils.isBlank(pname)) {
                key = n + "," + ev + ",@";
            } else {
                key = n + "," + ev + "," + pname;
            }


            if (dataMap.containsKey(key)) {
                list = dataMap.get(key);
            } else {
                list = new ArrayList<Double>();
            }
            list.add(value);
            dataMap.put(key, list);
        }

        //生成策略
        List<GResultVo> grvos = new ArrayList<GResultVo>();
        for (Map.Entry<String, List<Double>> entry : dataMap.entrySet()) {
            GResultVo vo = new GResultVo();
            String key = entry.getKey();
            String[] params = key.split(",");
            vo.setName(params[0]);
            vo.setLevel(Integer.parseInt(params[1]));
            if ("@".equals(params[2])) {
                vo.setPname("");
            } else {
                vo.setPname(params[2]);
            }

            vo.setState("close");
            List<Double> values = entry.getValue();
            vo.setValues(values);
            grvos.add(vo);
        }

        genResult.setCate(grvos);
        genResult.setGens(gens);

        return genResult;
    }


    public void generateData(List<Map<String, Object>> childs, Map<String, Map<String, GenVo>> genvoMap, int level, Map<String, Integer> levelMap, String[] gens) {

        for (Map<String, Object> map : childs) {

            String name = map.get("name").toString();

            // 循环基因，取出基因对应的count，value
            for (String gen : gens) {
                GenVo vo = new GenVo();
                vo.setName(name);
                vo.setLevel(level);

                Map<String, Map<String, Object>> allMap = gens(gen);
                Map<String, Object> countMap = allMap.get("count");
                Map<String, Object> valueMap = allMap.get("value");
                String countTemp = "";
                String valueTemp = "";
                if (null == countMap.get(name)) {
                    countTemp = "0";
                } else {
                    countTemp = String.valueOf(countMap.get(name));
                }
                if (null == valueMap.get(name)) {
                    valueTemp = "0";

                } else {
                    valueTemp = String.valueOf(valueMap.get(name));
                }
                double count = Double.parseDouble(countTemp);
                double value = Double.parseDouble(valueTemp);
                vo.setCount(count);
                vo.setValue(value);
                Map<String, GenVo> genMap = new HashMap<String, GenVo>();
                genMap.put(gen, vo);
                genvoMap.put(gen + level, genMap);

                levelMap.put(gen, level + 1);

                String parentKey = gen + (level - 1);
                Map<String, GenVo> parentGenMap = genvoMap.get(parentKey);
                GenVo parentVo = parentGenMap.get(gen);
                parentVo.setCount(parentVo.getCount() + count);
                parentVo.setValue(parentVo.getValue() + value);
                parentGenMap.put(gen, parentVo);
                genvoMap.put(parentKey, parentGenMap);

            }
            List<Map<String, Object>> alls = (List<Map<String, Object>>) map.get("children");
            int size = alls.size();
            if (size > 0) {
                generateData(alls, genvoMap, level + 1, levelMap, gens);
            }

        }

    }


    public Map<String, Map<String, Object>> gens(String gen) {
        Criteria c = Criteria.where("gene").is(gen);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(c),
                Aggregation.unwind("$samplerun"),
                Aggregation.group("$samplerun.type").count().as("count").sum("$samplerun.value").as("value")
        );
        AggregationResults<Map> aggRes = mongoTemplate.aggregate(aggregation, "all_gens_1", Map.class);

        List<Map> listRes = aggRes.getMappedResults();
        Map<String, Object> countMap = new HashMap<String, Object>();
        Map<String, Object> valueMap = new HashMap<String, Object>();
        for (Map map : listRes) {
            String key = String.valueOf(map.get("_id"));
            Object count = map.get("count");
            Object value = map.get("value");

//    		System.out.println("key: " + key +", count: " + count+", value: " + value );
            countMap.put(key, count);
            valueMap.put(key, value);
        }


        Map<String, Map<String, Object>> allMap = new HashMap<String, Map<String, Object>>();
        allMap.put("count", countMap);
        allMap.put("value", valueMap);
        return allMap;
    }

    /**
     * 查找某一个组织及其下面的小组织名字
     * @param classify 组织名称
     * @return 组织分类集合
     */
    public List<String> findClassifyAndItsChildren(String classify){
        return tService.queryClassifyByFather(classify);
    }

    /**
     * 在all_gens_fpkm集合中根据sampleRun.name查询gene属性
     * @param sampleRun 集合元素的sampleRun.name字段
     * @return 所有匹配的基因名
     */
    public List<String> findAllAssociateGeneThroughSampleRun(String sampleRun) throws InterruptedException {
        Criteria criteria = new Criteria("samplerun.name");
        criteria.is(sampleRun);
        Query query = new Query(criteria);
        query.fields().include("gene");
        long currentGeneTotal = mongoTemplate.count(query, ALL_GENE_FPKM);
        int singlePageNum = (int) (currentGeneTotal / TOTAL + 1);
        logger.info("当前基因总量为：" + currentGeneTotal);
        CaculateFPKMThread caculateThreads[] = new CaculateFPKMThread[TOTAL];
        for (int i = 0; i < TOTAL; i++){
            Pageable singleThreadPage = new PageRequest(i, singlePageNum);  //注意：这里使用的是mongodb pageable对象
            query.with(singleThreadPage);
            caculateThreads[i] = new CaculateFPKMThread(query, "线程" + i);
            caculateThreads[i].start();
            caculateThreads[i].join();
        }
        return eligibilities;
    }

    private class CaculateFPKMThread extends Thread{
        private final String name;
        private Query query;

        /**
         * 用于查询并计算每一个GENE的FPKM值线程
         * @param query Spring data mongodb查询对象
         * @param name 线程名字
         */
        public CaculateFPKMThread(Query query, String name) {
            this.query = query;
            this.name = name;
        }

        @Override
        public void run() {
            List<String> geneResult = execute();
            if (geneResult.size() == 0){
                logger.warn(this.name + "查询返回的基因总数为0");
                return;
            }
            String[] singleGene = new String[1];
            for (int i = 0; i < geneResult.size(); i++) {
                singleGene[0] = geneResult.get(i);
                GenResult genResult = tService.generateData(singleGene);  //计算它的FPKM值
                Double fpkmValue = genResult.getCate().get(0).getValues().get(0);
                logger.info(this.getName() + ": 当前基因名： " + singleGene[0] + " ,FPKM值为：" + fpkmValue);
                if (fpkmValue > 30){
                    eligibilities.add(singleGene[0]);
                }
            }
        }

        /**
         * 执行MongoDB查询
         */
        private List<String> execute(){
            List<String> searchGeneResult = new ArrayList<>();
            mongoTemplate.executeQuery(this.query, ALL_GENE_FPKM, new DocumentCallbackHandlerImpl<String>("gene", searchGeneResult));
            return searchGeneResult;
        }
    }
}
