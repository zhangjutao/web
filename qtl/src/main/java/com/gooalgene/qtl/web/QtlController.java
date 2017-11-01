package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Chrlg;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.service.ChrlgService;
import com.gooalgene.qtl.service.QtlService;
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
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/qtl")
public class QtlController {
    Logger logger = LoggerFactory.getLogger(QtlController.class);

    @Autowired
    private QtlService qtlService;
    @Autowired
    private ChrlgService chrlgService;

    @RequestMapping("/list")
    public String index(Model model) {
        return "qtl/qtl";
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
        String type = "All";
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + "Type:" + type + ",search:" + sSearch);
        JSONArray result = qtlService.searchQtlsbyKeywords(type, sSearch, page);
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
        List<Chrlg> list = chrlgService.findAllList();
        model.addAttribute("list", list);
        return "qtl/add";
    }

    @RequestMapping("/saveadd")
    public String add(Qtl qtl, Model model) {
        logger.info(qtl.toString());
        qtlService.add(qtl);
        return "redirect:/qtl/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        Qtl qtl = qtlService.findById(id);
        List<Chrlg> list = chrlgService.findAllList();
        model.addAttribute("list", list);
        model.addAttribute("qtl", qtl);
        return "qtl/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(Qtl qtl, Model model) {
        qtlService.update(qtl);
        return "redirect:/qtl/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = qtlService.delete(Integer.parseInt(id));
        return flag;
    }
}