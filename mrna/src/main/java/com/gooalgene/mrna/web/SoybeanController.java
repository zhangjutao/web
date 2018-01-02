package com.gooalgene.mrna.web;

import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.entity.Expression;
import com.gooalgene.mrna.entity.SampleRun;
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.utils.JsonUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOptions;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 大豆热图相关接口
 * Created by ShiYun on 2017/8/9 0009.
 */
@Controller
@RequestMapping("/specific")
public class SoybeanController {

    Logger logger = LoggerFactory.getLogger(SoybeanController.class);

    @Autowired
    private TService tService;

    @RequestMapping("/tree")
    @ResponseBody
    public String tree(HttpServletRequest request) {
        List<Classifys> tree = tService.getClassifyTree();
        String treejson = JsonUtils.Bean2Json(tree);
        return treejson;
    }


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("mRNA/mRNAtissue-specificity");
        String genes = request.getParameter("genes");
        modelAndView.addObject("genes", genes);
        return modelAndView;
    }

    /**
     * 大豆热图数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/hitmap")
    @ResponseBody
    public String hitmap(HttpServletRequest request) {
        String genes = request.getParameter("genes");
        String json = "{}";
        logger.info("hitmap:genes{" + genes + "}");
        if (StringUtils.isNotBlank(genes)) {
            String[] gens = genes.split(",");
            GenResult genResult = tService.generateData(gens);
            json = JsonUtils.Bean2Json(genResult);
//            System.out.println(json);
        }
        return json;
    }

    /**
     * 大豆折线图数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/line")
    @ResponseBody
    public String line(HttpServletRequest request) {
        String genes = request.getParameter("genes");
        String json = "{}";
        logger.info("line:genes{" + genes + "}");
        if (StringUtils.isNotBlank(genes)) {
            String[] gens = genes.split(",");
            GenResult genResult = tService.generateData(gens);
            json = JsonUtils.Bean2Json(genResult);
//            System.out.println(json);
        }
        return json;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StudyService studyService;

    /**
     * 大豆箱线图数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/boxplot")
    @ResponseBody
    public String boxplot(HttpServletRequest request) {
        String genes = request.getParameter("genes");
        logger.info("boxplot:genes{" + genes + "}");
        JSONArray data = new JSONArray();
        if (StringUtils.isNotBlank(genes)) {
            String[] gens = genes.split(",");
            Criteria criteria = new Criteria();
            criteria.and("gene").in(gens);
            long start = System.currentTimeMillis();
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("gene").push("$samplerun").as("samplerun")
            ).withOptions(new AggregationOptions.Builder().allowDiskUse(true).build());
            AggregationResults<Expression> list = mongoTemplate.aggregate(aggregation, "all_gens_fpkm", Expression.class);
            long end = System.currentTimeMillis();
            DecimalFormat df = new DecimalFormat("######0.00");
            Map<String, String> runAndclassify = studyService.queryRunsAndTissueForClassify();//run对应的分类
            Map<String, String> classifyAndFirst = tService.queryChildAndFirst();//所有分类对应第一级关系
            Map<String, String> classifyAndChinese = tService.queryClassifyAndChinese();//所有分类对应中文名
            Map<String, String> firstAndChinese = tService.queryFirstAndChinese();//第一级分类和对应中文
            logger.info("Query:" + aggregation.toString() + ",cost " + (end - start) / 1000 + "s.");
            for (Expression expression : list) {
//              System.out.println(expression.getId()+"\t"+expression.getSamplerun().size());
                JSONObject gene = new JSONObject();
                String gen = expression.getId();//group之后id字段就是gene
                List<SampleRun> sampleRuns = expression.getSamplerun();
                JSONObject jsonObject = new JSONObject();
                for (SampleRun sampleRun : sampleRuns) {
                    String name = sampleRun.getName();
                    Double value = sampleRun.getValue();
                    String type = classifyAndFirst.get(runAndclassify.get(name));
                    if (type != null) {
                        String chinese = classifyAndChinese.get(type);
                        jsonObject = query(jsonObject, type, chinese, df.format(value));
                    }
                }
                gene.put(gen, compare2AllFirstClassify(jsonObject, firstAndChinese));//新增分类可能不包含在内，但是页面需要展示全部分类，需返回
                data.add(gene);
            }
        }
        return data.toString();
    }

    private JSONObject query(JSONObject result, String key, String chinese, String value) {
        JSONObject jsonObject = new JSONObject();
        if (result.containsKey(key)) {
            jsonObject = result.getJSONObject(key);
            JSONArray array = jsonObject.getJSONArray("data");
            array.add(value);
            jsonObject.put("data", array);
        } else {
            JSONArray array = new JSONArray();
            array.add(value);
            jsonObject.put("chinese", chinese);
            jsonObject.put("data", array);
        }
        result.put(key, jsonObject);
        return result;
    }

    private JSONObject compare2AllFirstClassify(JSONObject jsonObject, Map<String, String> firstAndChinese) {
        Set<String> keys = jsonObject.keySet();
        Map<String, JSONObject> map = new HashMap<String, JSONObject>();
        for (String key : firstAndChinese.keySet()) {
            if (!keys.contains(key)) {
                String chinese = firstAndChinese.get(key);
                JSONObject one = new JSONObject();
                JSONArray array = new JSONArray();
                one.put("chinese", chinese);
                one.put("data", array);
                map.put(key, one);
            }
        }
        for (String k : map.keySet()) {
            JSONObject jsonObject1 = map.get(k);
            jsonObject.put(k, jsonObject1);
        }
        return jsonObject;
    }
}
