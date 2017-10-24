package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.qtl.service.AssociatedgenesService;
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
 * Created by 陈冬 on 2017/7/17.
 */
@Controller
@RequestMapping(value = "/associatedgenes")
public class AssociatedgenesController {

    Logger logger = LoggerFactory.getLogger(AssociatedgenesController.class);

    @Autowired
    private AssociatedgenesService associatedgenesService;

    @RequestMapping(value = "/list")
    public String associatedgenesList(Model model) {
        return "associatedgenes/associatedgenes-list";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<Associatedgenes> page = new Page<Associatedgenes>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + ",Type:" + type + ",search:" + sSearch);
        JSONArray result = associatedgenesService.searchAssociatedgenessbyKeywords(type, sSearch, page);
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

    @RequestMapping(value = "/toaddassociatedgenes")
    public String toAddAssociatedgenes() {
        return "associatedgenes/add";
    }

    @RequestMapping(value = "/savenewassociatedgenes")
    public String saveNewAssociatedgenes(Associatedgenes associatedgenes) {
        associatedgenesService.saveAssociatedgenes(associatedgenes);
        return "redirect:/associatedgenes/list";
    }

    @RequestMapping(value = "/toedit")
    public String toEditAssociatedgenes(int id, Model model) {
        Associatedgenes associatedgenes = associatedgenesService.findAssociatedgenesById(id);
        model.addAttribute("associatedgenes", associatedgenes);
        return "associatedgenes/edit";
    }

    @RequestMapping(value = "/saveedit")
    public String saveAssociatedgenesEdit(Associatedgenes associatedgenes) {
        associatedgenesService.saveAssociatedgenesEdit(associatedgenes);
        return "redirect:/associatedgenes/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public boolean deleteAssociatedgenes(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = associatedgenesService.deleteAssociatedgenes(Integer.parseInt(id));
        return flag;
    }

}
