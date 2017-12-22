package com.gooalgene.mrna.service;

import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.vo.GResultVo;
import com.gooalgene.mrna.vo.GVo;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.mrna.vo.GenVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(TService.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    public GenResult generateData(String[] gens) {
        GenResult genResult = new GenResult();
        try {
            //定义所有sum个数集合
            Map<String, Map<String, Object>> sumcountMaps = new HashMap<String, Map<String, Object>>();
            //定义所有value值集合
            Map<String, Map<String, Object>> valuesMaps = new HashMap<String, Map<String, Object>>();
            //循坏取出所有基因的sumcount与value的值
            for (String gen : gens) {
                Map<String, Map<String, Object>> allMap = gensData(gen);
                Map<String, Object> sumcountMap = allMap.get("count");
                Map<String, Object> valueMap = allMap.get("value");
                sumcountMaps.put(gen, sumcountMap);
                valuesMaps.put(gen, valueMap);
            }
            //获取所有分类，分类存在多级子嵌套
            List<Classifys> all = mongoTemplate.findAll(Classifys.class);
            //临时存放所有基因的对应的分类数据
            List<GVo> gvos = new ArrayList<GVo>();
            //循坏基因，取出对应分类值
            for (String gen : gens) {
                //所有样本type-->count的集合
                Map<String, Object> sumMaps = sumcountMaps.get(gen);
                //所有样本FPKM总值的集合
                Map<String, Object> valueMaps = valuesMaps.get(gen);
                //获取每一个大组织在该基因中的总个数与总FPKM值
                for (Classifys c : all) {
                    //临时存储一种分类的基因
                    Map<String, GenVo> genvosMap = new HashMap<String, GenVo>();
                    int level = 0;
                    String name = c.getName();
                    String chinese = (c.getChinese() == null ? "" : c.getChinese());
                    String countTemp = "";
                    String valueTemp = "";
                    // todo 这里Classifys中存的都包含_all后缀，而all_gens_fpkm中搜索出来的type不包含该后缀
                    //先跟一级root组织比,找到该组织个数与总的FPKM值
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
                    vo.setCount(count); //设置该种组织总个数
                    vo.setValue(value); //设置该种组织总FPKM值
                    vo.setName(name); //所属组织
                    vo.setLevel(level); //一级组织(root)
                    vo.setGen(gen);
                    vo.setPname("");
                    vo.setChinese(chinese);
                    //基因名+组织名+层级
                    genvosMap.put(gen + name + level, vo);
                    // 获取root组织分类下的小组织
                    List<Map<String, Object>> childs = c.getChildren();
                    if (childs.size() > 0) {
                        //拿到该大组织下所有小组织的FPKM等相关信息
                        childsGenerateGen(sumMaps, valueMaps, genvosMap, level, childs, gen, name);
                    }
                    for (Map.Entry<String, GenVo> entry : genvosMap.entrySet()) {
                        GenVo genvo = entry.getValue();
                        GVo gvo = new GVo();
                        if (0.0 == genvo.getValue() || 0.0 == genvo.getCount()) {
                            gvo.setValue(0.0);
                        } else {
                            double avg = genvo.getValue() / genvo.getCount();  // 算平均值
                            gvo.setValue(avg);
                        }
                        gvo.setLevel(genvo.getLevel());
                        gvo.setName(genvo.getName());
                        gvo.setPname(genvo.getPname());
                        gvo.setState("close");  // 修改基因状态为close
                        gvo.setGen(genvo.getGen());
                        gvo.setChinese(genvo.getChinese());
                        //将已经计算出FPKM结果的GenVO对象放入到这里临时集合中
                        gvos.add(gvo);
                    }
                }
            }
            Map<String, List<Double>> dataMap = new HashMap<String, List<Double>>();
            //基因名-->中文名
            Map<String, String> name_chinese = new HashMap<String, String>();
            List<Double> list = null;
            for (GVo gvo : gvos) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return genResult;

    }

    /**
     * 将子类组织信息也放入到genvosMap中
     *
     * @param sumMaps      samplerun对应总数集合
     * @param valueMaps    samplerun对应FPKM值的集合
     * @param genvosMap    临时存放的集合(基因名+组织+层级与GenVo之间关系集合)
     * @param level        当前级别
     * @param childs       Classify中需要处理的子集合
     * @param gen          基因ID
     * @param classifyName 组织名字
     */
    public void childsGenerateGen(Map<String, Object> sumMaps, Map<String, Object> valueMaps, Map<String, GenVo> genvosMap, int level, List<Map<String, Object>> childs, String gen, String classifyName) {
        level++; //组织层级增加
        for (int i = 0; i < childs.size(); i++) {
            Map<String, Object> map = childs.get(i);
            String name = map.get("name").toString(); //小组织名
            String chinese = map.get("chinese").toString(); //小组织对应中文名
            String countTemp = "";
            String valueTemp = "";
            //获取小组织在该基因查询结果集中的type个数和FPKM总值
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
            //该key对应genvosMap中的key,拼接方法也一样
            String parentKey = gen + classifyName + pnum;
            GenVo parentVo = genvosMap.get(parentKey);
            parentVo.setCount(parentVo.getCount() + count);
            parentVo.setValue(parentVo.getValue() + value);
            genvosMap.put(parentKey, parentVo);
            String nowPname = parentVo.getPname(); //获取父组织名字

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
            vo.setPname(parentVo.getName()); //重新设置父组织名字
            vo.setChinese(chinese);
            //把子类也放到该临时基因集合中，父类子类key命名约束也相同
            genvosMap.put(gen + name + level, vo);
            //如果存在子类的子类，则进行递归操作
            List<Map<String, Object>> alls = (List<Map<String, Object>>) map.get("children");
            int size = alls.size();
            if (size > 0) {
                childsGenerateGen(sumMaps, valueMaps, genvosMap, level, alls, gen, name);
            }
        }
    }

    /**
     * 获取某种基因对应的所有样本的总个数以及每种样本总FPKM
     *
     * @param gen Gene ID
     * @return 该种基因对应的所有样本的总个数以及每种样本总FPKM
     */
    public Map<String, Map<String, Object>> gensData(String gen) {
        Criteria c = Criteria.where("gene").is(gen);
        // 获取某一个基因下有多少个样本类型和某一个样本类型所对应的相应组织的FPKM
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(c),
                Aggregation.group("$samplerun.type").count().as("count").sum("$samplerun.value").as("value")
        );
        AggregationResults<Map> aggRes = mongoTemplate.aggregate(aggregation, "all_gens_fpkm", Map.class);
        List<Map> listRes = aggRes.getMappedResults();
        Map<String, Object> countMap = new HashMap<String, Object>(); //组织-->group个数
        Map<String, Object> valueMap = new HashMap<String, Object>(); //组织-->FPKM总值
        for (Map map : listRes) {
            String key = String.valueOf(map.get("_id"));
            Object count = map.get("count");  //该种样本的总数
            Object value = map.get("value");  //该种样本FPKM总值

            countMap.put(key, count);
            valueMap.put(key, value);
        }
        //将组织-->个数与组织-->FPKM总值对应关系汇总，存入Map中
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
     * 拿到每一个分类包括该分类下的所有name，有一个逐渐递减的关系
     * first: root tissue name + second children name + third children name
     * second: second children name + third children name
     * mapAll为包含上面两个key-value的集合
     *
     * @param father 大组织名字(去all后缀)
     * @return 某种组织下所包含的所有小组织名
     * 如传入的时seed_All，则返回：
     * seed_All
     *      seed
     *          seed coat
     *          seed coat endothelium
     *          ...
     */
    public List<String> queryClassifyByFather(String father) {
        Map<String, List<String>> mapAll = new HashMap<String, List<String>>();
        List<Classifys> all = getClassifyTree(); //拿到所有大组织
        for (Classifys classifys : all) {
            String name = classifys.getName(); //第一级
            List<String> first = new ArrayList<String>();
            first.add(name);
            List<Map<String, Object>> children = classifys.getChildren();
            for (Map<String, Object> map : children) {
                String name1 = (String) map.get("name"); //第二级
                first.add(name1);
                List<String> second = new ArrayList<String>();
                second.add(name1);
                List<Map<String, Object>> child1 = (List<Map<String, Object>>) map.get("children");
                if (child1 != null) {
                    for (Map<String, Object> map1 : child1) {
                        String name2 = (String) map1.get("name"); //第三级
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
