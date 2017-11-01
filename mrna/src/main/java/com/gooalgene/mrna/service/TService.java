package com.gooalgene.mrna.service;

import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.vo.GResultVo;
import com.gooalgene.mrna.vo.GVo;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.mrna.vo.GenVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TService {

    @Autowired
    private MongoTemplate mongoTemplate;


    public GenResult generateData(String[] gens) {
        GenResult genResult = new GenResult();
        try {
            //定义所有sum个数集合
            Map<String, Map<String, Object>> sumcountMaps = new HashMap<String, Map<String, Object>>();

            //定义所有value值集合
            Map<String, Map<String, Object>> valuesMaps = new HashMap<String, Map<String, Object>>();
//			System.out.println("time1:" + System.currentTimeMillis());
            //循坏取出所有基因的sumcount与value的值
            for (String gen : gens) {
                Map<String, Map<String, Object>> allMap = gensData(gen);
                Map<String, Object> sumcountMap = allMap.get("count");
                Map<String, Object> valueMap = allMap.get("value");
                sumcountMaps.put(gen, sumcountMap);
                valuesMaps.put(gen, valueMap);
            }
//			System.out.println("time2:" + System.currentTimeMillis());
            //获取所有分类，分类存在多级子嵌套
            List<Classifys> all = mongoTemplate.findAll(Classifys.class);
//			System.out.println("time3:" + System.currentTimeMillis());
            //临时存放所有基因的对应的分类数据
            List<GVo> gvos = new ArrayList<GVo>();


            //循坏基因，取出对应分类值
            for (String gen : gens) {
                //sum 的集合
                Map<String, Object> sumMaps = sumcountMaps.get(gen);

                //value的集合
                Map<String, Object> valueMaps = valuesMaps.get(gen);
//				System.out.println("time4:" + System.currentTimeMillis());
                //Map<String, Map<String, Object>> tempMaps = new HashMap<String, Map<String,Object>>();
                for (Classifys c : all) {
                    //临时存储一种分类的基因
                    Map<String, GenVo> genvosMap = new HashMap<String, GenVo>();
                    int level = 0;
                    String name = c.getName();
//					System.out.println("name:" + name);
                    String chinese = (c.getChinese() == null ? "" : c.getChinese());
                    String countTemp = "";
                    String valueTemp = "";
                    if (null == sumMaps.get(name)) {
                        countTemp = "0";
                    } else {
                        countTemp = String.valueOf(sumMaps.get(name));
                    }
                    if (null == valueMaps.get(name)) {
                        valueTemp = "0";

                    } else {
                        valueTemp = String.valueOf(valueMaps.get(name));
                    }
                    double count = Double.parseDouble(countTemp);
                    double value = Double.parseDouble(valueTemp);

                    GenVo vo = new GenVo();
                    vo.setCount(count);
                    vo.setValue(value);
                    vo.setName(name);
                    vo.setLevel(level);
                    vo.setGen(gen);
                    vo.setPname("");
                    vo.setChinese(chinese);

                    genvosMap.put(gen + name + level, vo);

                    List<Map<String, Object>> childs = c.getChildren();
                    if (childs.size() > 0) {
                        childsGenerateGen(sumMaps, valueMaps, genvosMap, level, childs, gen, name);
                    }

//                    System.out.println("name: " + name + ",json:" + JsonUtils.Bean2Json(genvosMap));
                    for (Map.Entry<String, GenVo> entry : genvosMap.entrySet()) {
                        GenVo genvo = entry.getValue();
                        GVo gvo = new GVo();
                        if (0.0 == genvo.getValue() || 0.0 == genvo.getCount()) {
                            gvo.setValue(0.0);
                        } else {
                            double avg = genvo.getValue() / genvo.getCount();
                            gvo.setValue(avg);
                        }
                        gvo.setLevel(genvo.getLevel());
                        gvo.setName(genvo.getName());
                        gvo.setPname(genvo.getPname());
                        gvo.setState("close");
                        gvo.setGen(genvo.getGen());
                        gvo.setChinese(genvo.getChinese());
                        gvos.add(gvo);
                    }
                }
//				System.out.println("time5:" + System.currentTimeMillis());
            }
//			System.out.println("time6:" + System.currentTimeMillis());
            Map<String, List<Double>> dataMap = new HashMap<String, List<Double>>();
            Map<String, String> name_chinese = new HashMap<String, String>();
            List<Double> list = null;
            for (GVo gvo : gvos) {
//				System.out.println("gen:" + gvo.getGen() + ",name:" + gvo.getName()
//						+ ", pname:" + gvo.getPname() + ",level:" + gvo.getLevel()
//						+ ",value:" + gvo.getValue());
                String n = gvo.getName();

                int ev = gvo.getLevel();

                String pname = gvo.getPname();

                Double value = gvo.getValue();
                String chinese = gvo.getChinese();
                String key = "";
                if (StringUtils.isBlank(pname)) {
                    key = n + "," + ev + ",@";
                } else {
                    key = n + "," + ev + "," + pname;
                }
                name_chinese.put(n, chinese);
                if (dataMap.containsKey(key)) {
                    list = dataMap.get(key);
                } else {
                    list = new ArrayList<Double>();
                }
                list.add(value);
                dataMap.put(key, list);
            }
//			System.out.println("time7:" + System.currentTimeMillis());
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
                vo.setChinese(name_chinese.get(params[0]));
                vo.setState("close");
                List<Double> values = entry.getValue();
                vo.setValues(values);
                grvos.add(vo);
            }

            genResult.setCate(grvos);
            genResult.setGens(gens);

//			    System.out.println("time8:" + System.currentTimeMillis());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return genResult;

    }

    /**
     * @param sumMaps   sumcount数的集合
     * @param valueMaps value值的结合
     * @param genvosMap 临时存放的集合
     * @param level     当前级别
     * @param childs    需要处理的子集合
     */
    public void childsGenerateGen(Map<String, Object> sumMaps, Map<String, Object> valueMaps, Map<String, GenVo> genvosMap, int level, List<Map<String, Object>> childs, String gen, String xxname) {
        level++;
        for (int i = 0; i < childs.size(); i++) {
            Map<String, Object> map = childs.get(i);
            String name = map.get("name").toString();
//            System.out.println("child:" + name);
            String chinese = map.get("chinese").toString();
            String countTemp = "";
            String valueTemp = "";
            if (null == sumMaps.get(name)) {
                countTemp = "0";
            } else {
                countTemp = String.valueOf(sumMaps.get(name));
            }
            if (null == valueMaps.get(name)) {
                valueTemp = "0";

            } else {
                valueTemp = String.valueOf(valueMaps.get(name));
            }
            double count = Double.parseDouble(countTemp);
            double value = Double.parseDouble(valueTemp);

            //父的信息
            int pnum = level - 1;
            String parentKey = gen + xxname + pnum;
            //System.out.println("parentKey:" + parentKey);
            GenVo parentVo = genvosMap.get(parentKey);
            parentVo.setCount(parentVo.getCount() + count);
            parentVo.setValue(parentVo.getValue() + value);
            genvosMap.put(parentKey, parentVo);
            String nowPname = parentVo.getPname();

            for (int j = 0; j < pnum; j++) {
                if (StringUtils.isNotBlank(nowPname)) {
                    GenVo p = genvosMap.get(gen + nowPname + (pnum - (j + 1)));
                    p.setCount(p.getCount() + count);
                    p.setValue(p.getValue() + value);
                    nowPname = p.getPname();
                } else {
                    break;
                }
            }


            GenVo vo = new GenVo();
            vo.setCount(count);
            vo.setValue(value);
            vo.setName(name);
            vo.setLevel(level);
            vo.setGen(gen);
            vo.setPname(parentVo.getName());
            vo.setChinese(chinese);
            genvosMap.put(gen + name + level, vo);

            List<Map<String, Object>> alls = (List<Map<String, Object>>) map.get("children");
            int size = alls.size();
            if (size > 0) {
                childsGenerateGen(sumMaps, valueMaps, genvosMap, level, alls, gen, name);
            }
        }
    }

    public Map<String, Map<String, Object>> gensData(String gen) {
        Criteria c = Criteria.where("gene").is(gen);
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(c),
//              Aggregation.unwind("$samplerun"),
                Aggregation.group("$samplerun.type").count().as("count").sum("$samplerun.value").as("value")
        );
//        System.out.println(aggregation.toString());
        AggregationResults<Map> aggRes = mongoTemplate.aggregate(aggregation, "all_gens_fpkm", Map.class);

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
     * 获取大豆分类（多层级）
     *
     * @return
     */
    public List<Classifys> getClassifyTree() {
        List<Classifys> all = mongoTemplate.findAll(Classifys.class);
        return all;
    }

    /**
     * 根据分类查询子分类
     *
     * @param father
     * @return
     */
    public List<String> queryClassifyByFather(String father) {
        Map<String, List<String>> mapAll = new HashMap<String, List<String>>();
        List<Classifys> all = getClassifyTree();
        for (Classifys classifys : all) {
            String name = classifys.getName();//第一级
            List<String> first = new ArrayList<String>();
            first.add(name);
//            System.out.println("name:" + name);
            List<Map<String, Object>> children = classifys.getChildren();
            for (Map<String, Object> map : children) {
                String name1 = (String) map.get("name");//第二级
                first.add(name1);
//                System.out.println("name1:" + name1);
                List<String> second = new ArrayList<String>();
                second.add(name1);
                List<Map<String, Object>> child1 = (List<Map<String, Object>>) map.get("children");
                if (child1 != null) {
                    for (Map<String, Object> map1 : child1) {
                        String name2 = (String) map1.get("name");//第三级
//                        System.out.println("name2:" + name2);
                        first.add(name2);
                        second.add(name2);
                    }
                }
                mapAll.put(name1, second);
            }
            mapAll.put(name, first);
        }
        return mapAll.get(father);
    }

    /**
     * 查询子类和第一级分类的关系
     *
     * @return
     */
    public Map<String, String> queryChildAndFisrt() {
        Map<String, String> mapAll = new HashMap<String, String>();
        List<Classifys> all = getClassifyTree();
        for (Classifys classifys : all) {
            String name = classifys.getName();//第一级--包含后缀_all
            List<Map<String, Object>> children = classifys.getChildren();
            for (Map<String, Object> map : children) {
                String name1 = (String) map.get("name");//第二级
                List<Map<String, Object>> child1 = (List<Map<String, Object>>) map.get("children");
                if (child1 != null) {
                    for (Map<String, Object> map1 : child1) {
                        String name2 = (String) map1.get("name");//第三级
                        mapAll.put(name2, name);
                    }
                }
                mapAll.put(name1, name);
            }
        }
        return mapAll;
    }

    /**
     * 查询分类和中文名的关系
     *
     * @return
     */
    public Map<String, String> queryClassifyAndChinese() {
        Map<String, String> mapAll = new HashMap<String, String>();
        List<Classifys> all = getClassifyTree();
        for (Classifys classifys : all) {
            String name = classifys.getName();//第一级--包含后缀_all
            String chinese = classifys.getChinese();
            mapAll.put(name, chinese);
            List<Map<String, Object>> children = classifys.getChildren();
            for (Map<String, Object> map : children) {
                String name1 = (String) map.get("name");//第二级
                String chinese1 = (String) map.get("chinese");
                mapAll.put(name1, chinese1);
                List<Map<String, Object>> child1 = (List<Map<String, Object>>) map.get("children");
                if (child1 != null) {
                    for (Map<String, Object> map1 : child1) {
                        String name2 = (String) map1.get("name");//第三级
                        String chinese2 = (String) map1.get("chinese");
                        mapAll.put(name2, chinese2);
                    }
                }
            }
        }
        return mapAll;
    }

    /**
     * 查询一级分类和中文名的关系
     *
     * @return
     */
    public Map<String, String> queryFirstAndChinese() {
        Map<String, String> mapAll = new HashMap<String, String>();
        List<Classifys> all = getClassifyTree();
        for (Classifys classifys : all) {
            String name = classifys.getName();//第一级--包含后缀_all
            String chinese = classifys.getChinese();
            mapAll.put(name, chinese);
        }
        return mapAll;
    }

}
