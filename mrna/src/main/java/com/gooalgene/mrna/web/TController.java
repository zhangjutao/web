package com.gooalgene.mrna.web;

import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.service.ClassifyService;
import com.gooalgene.mrna.service.MongoService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/t")
public class TController {

    Logger logger = LoggerFactory.getLogger(TController.class);

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private TService tService;

    @RequestMapping(value = "/all")
    public GenResult all1(HttpServletRequest request, HttpServletResponse response) {

        String gen = request.getParameter("gen");
        if (StringUtils.isNotBlank(gen)) {
            String[] gens = gen.split(",");
            return classifyService.findAllClassifys(gens);
        } else {
            return null;
        }

    }

    @RequestMapping(value = "/data")
    public GenResult data(HttpServletRequest request, HttpServletResponse response) {

        String gen = request.getParameter("gen");
        if (StringUtils.isNotBlank(gen)) {
            String[] gens = gen.split(",");
            GenResult genResult = tService.generateData(gens);
            return genResult;
        }
        return null;
    }

    @RequestMapping(value = "/insert")
    @ResponseBody
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        Classifys classifys = new Classifys();
        classifys.setName("test");
        classifys.setChinese("测试");
        classifys.setChildren(new ArrayList<Map<String, Object>>());
        boolean flag = classifyService.insert(classifys);
        return "success";
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Boolean update(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        String classify = request.getParameter("classify");
        System.out.println(classify);
        Classifys classifys = JsonUtils.Json2Bean(classify, Classifys.class);
        if (classifys != null) {
            flag = classifyService.update(classifys);
        }
        return flag;
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public Boolean delete(HttpServletRequest request, HttpServletResponse response) {
        boolean flag = false;
        String id = request.getParameter("id");
        if (StringUtils.isNoneBlank(id)) {
            flag = classifyService.delete(id);
        }
        return flag;
    }

    @RequestMapping(value = "/query")
    @ResponseBody
    public String query(HttpServletRequest request, HttpServletResponse response) {
        classifyService.query();
        return "success";
    }

    @Autowired
    private MongoService mongoService;

    @RequestMapping(value = "/query1")
    @ResponseBody
    public String queryMap(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = mongoService.queryExpressionsRunAndStudy();
        logger.info("expression titles:" + map.size());
        return "success";
    }
}
