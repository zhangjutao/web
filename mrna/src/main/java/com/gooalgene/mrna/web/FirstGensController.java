package com.gooalgene.mrna.web;

import com.gooalgene.mrna.service.StudyService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShiYun on 2017/8/8 0008.
 */
@Controller
@RequestMapping("/firstgens")
public class FirstGensController {

    Logger logger = LoggerFactory.getLogger(FirstGensController.class);

    @Autowired
    private StudyService studyService;

    @RequestMapping("/list")
    public String index(Model model) {
        return "firstgens/firstgens";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        System.out.println("Type:" + type + ",search:" + sSearch);
        JSONArray result = studyService.queryAllGenesForFirst();
        jsonobj.put("aData", result);
        Map<String, Object> map = new HashMap<String, Object>();
        // 为操作次数加1，必须这样做
        int initEcho = sEcho + 1;
        map.put("sEcho", initEcho);
        map.put("iTotalRecords", 5);
        map.put("iTotalDisplayRecords", 5);
        map.put("aData", result);
        model.addAttribute("aData", result);
        return map;
    }

    @RequestMapping("/toadd")
    public String toadd(Model model) {
        return "firstgens/add";
    }

    @RequestMapping("/saveadd")
    public String add(Model model) {
        boolean flage = true;
        System.out.println("flage:" + flage);
        return "redirect:/firstgens/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        Map map = studyService.findGenesForFirstById(id);
        model.addAttribute("id", map.get("id"));
        model.addAttribute("gene", map.get("gene"));
        return "firstgens/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String gene = request.getParameter("gene");
        System.out.println(id + "\t" + gene);
        if (StringUtils.isNotBlank(gene) && StringUtils.isNumeric(id)) {
            studyService.updateGenesForFirstById(Integer.valueOf(id), gene);
        } else {
            logger.error("para error:{id=" + id + ",gene=" + gene + "}");
        }
        return "redirect:/firstgens/list";
    }

//    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
//        boolean flage = qtlService.delete(Integer.parseInt(id));
//        System.out.println("flage:" + flage);
        boolean flage = true;
        if (flage == true) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


}
