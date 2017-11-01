package com.gooalgene.mrna.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Qtl;
import com.gooalgene.mrna.service.MongoService;
import com.gooalgene.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/expression")
public class ExpressionController {

    Logger logger = LoggerFactory.getLogger(ExpressionController.class);

    @Autowired
    private MongoService mongoService;

    @RequestMapping("/list")
    public String index(Model model) {
        return "expression/expression";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<Qtl> page = new Page<Qtl>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + ",condition:" + sSearch);
        List<String> all = mongoService.queryCollectionsByType(1);
        List<String> query = new ArrayList<String>();
        if (StringUtils.isNotBlank(sSearch)) {
            for (String line : all) {
                if (line.contains(sSearch)) {
                    query.add(line);
                }
            }
        } else {
            query = all;
        }
        int count = query.size();
        List<JSONObject> toQuery = new ArrayList<JSONObject>();
        for (int i = 0; i < count; i++) {
            String sraStudy = query.get(i).replace("Expression_", "");
            JSONObject one = new JSONObject();
            one.put("id", i + 1);
            one.put("name", sraStudy);
            toQuery.add(one);
        }
        Collections.reverse(toQuery);
        page.setCount(count);
        toQuery = Tools.queryByPage(toQuery, page.getPageNo(), page.getPageSize());
        JSONArray result = new JSONArray();
        for (JSONObject jsonObject : toQuery) {
            result.add(jsonObject);
        }
        jsonobj.put("aData", result);
        Map<String, Object> map = new HashMap<String, Object>();
        // 为操作次数加1，必须这样做
        int initEcho = sEcho + 1;
        map.put("sEcho", initEcho);
        map.put("iTotalRecords", page.getCount());
        map.put("iTotalDisplayRecords", page.getCount());
        map.put("aData", result);
        model.addAttribute("aData", result);
        return map;
    }

    @RequestMapping("/toadd")
    public String toadd(Model model) {
        return "expression/add";
    }

    @RequestMapping("/saveadd")
    public String add(Qtl qtl, Model model) {
        boolean flage = true;
        return "redirect:/expression/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        return "expression/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(Qtl qtl, Model model) {
        int flage = 0;
        return "redirect:/expression/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = false;
        String mes = "";
        if (StringUtils.isNotBlank(id)) {
            String collection = "Expression_" + id;
            if (mongoService.isCollectionExist(collection)) {
                mongoService.dropCollection(collection);
                flag = true;
                mes = "删除成功！";
            } else {
                mes = "不存在的库。";
            }
        } else {
            mes = "参数不正确。";
        }
        JSONObject result = new JSONObject();
        result.put("result", flag);
        result.put("mes", mes);
        logger.info("delete:Expression_" + id + " with result:" + result);
        return result.toString();
    }
}