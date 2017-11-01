package com.gooalgene.mrna.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.MrnaGens;
import com.gooalgene.mrna.service.MrnaGensService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * Created by 陈冬 on 2017/8/22.
 */
@Controller
@RequestMapping(value = "/mrnagens")
public class MrnaGensController {
    Logger logger = LoggerFactory.getLogger(MrnaGensController.class);

    @Autowired
    private MrnaGensService mrnaGensService;

    @RequestMapping("/list")
    public String index(Model model) {
        return "mrnagens/mrnagens";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<MrnaGens> page = new Page<MrnaGens>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        System.out.println("Type:" + type + ",search:" + sSearch);
        JSONArray result = mrnaGensService.searchMrnaGensbyKeywords(type, sSearch, page);
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
        return "mrnagens/add";
    }

    @RequestMapping("/saveadd")
    public String add(MrnaGens mrnaGens, Model model) {
        mrnaGensService.add(mrnaGens);
        return "redirect:/mrnagens/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        MrnaGens mrnaGens = mrnaGensService.findById(id);
        model.addAttribute("mrnaGens", mrnaGens);
        return "mrnagens/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(MrnaGens mrnaGens, Model model) {
        mrnaGensService.update(mrnaGens);
        return "redirect:/mrnagens/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = mrnaGensService.delete(Integer.parseInt(id));
        return flag;
    }
}
