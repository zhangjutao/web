package com.gooalgene.dna.web;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNAGroups;
import com.gooalgene.dna.service.DNAGroupsService;
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
@RequestMapping(value = "/dnagroups")
public class DNAGroupsController {
    Logger logger = LoggerFactory.getLogger(DNAGroupsController.class);

    @Autowired
    private DNAGroupsService dnaGroupsService;

    @RequestMapping("/list")
    public String index() {
        return "dnagroups/dnagroups";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<DNAGroups> page = new Page<DNAGroups>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        System.out.println("Search:" + sSearch);
        JSONArray result = dnaGroupsService.searchDNAGroupsbyKeywords(sSearch, page);
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
    public String toadd() {
        return "dnagroups/add";
    }

    @RequestMapping("/saveadd")
    public String add(DNAGroups dnaGroups) {
        logger.info("toADD:" + dnaGroups.toString());
        dnaGroupsService.add(dnaGroups);
        return "redirect:/dnagroups/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        DNAGroups dnaGroups = dnaGroupsService.findById(id);
        model.addAttribute("dnaGroups", dnaGroups.toJSON());
        return "dnagroups/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(DNAGroups dnaGroups) {
        logger.info("toEdit:" + dnaGroups.toString());
        dnaGroupsService.update(dnaGroups);
        return "redirect:/dnagroups/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = false;
        try {
            flag = dnaGroupsService.delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
