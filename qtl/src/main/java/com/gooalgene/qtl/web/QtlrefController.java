package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Qtlref;
import com.gooalgene.qtl.service.QtlrefService;
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
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/qtlref")
public class QtlrefController {
    Logger logger = LoggerFactory.getLogger(QtlrefController.class);

    @Autowired
    private QtlrefService qtlrefService;

    @RequestMapping("/list")
    public String list(Model model) {
        return "qtlref/qtlref";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<Qtlref> page = new Page<Qtlref>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + "Type:" + type + ",search:" + sSearch);
        JSONArray result = qtlrefService.searchQtlrefbyKeywords(type, sSearch, page);
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
        return "qtlref/add";
    }

    @RequestMapping("/saveadd")
    public String add(Qtlref qtlref, Model model) {
        boolean flag = qtlrefService.add(qtlref);
        logger.info("save:" + qtlref.toString() + ", excute:" + flag);
        return "redirect:/qtlref/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        Qtlref qtlref = qtlrefService.findById(id);
        model.addAttribute("qtlref", qtlref);
        return "qtlref/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(Qtlref qtlref, Model model) {
        System.out.println(qtlref.toString());
        int flag = qtlrefService.update(qtlref);
        logger.info("update:" + qtlref.toString() + ", excute:" + flag);
        return "redirect:/qtlref/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = qtlrefService.deleteById(Integer.parseInt(id));
        return flag;
    }
}