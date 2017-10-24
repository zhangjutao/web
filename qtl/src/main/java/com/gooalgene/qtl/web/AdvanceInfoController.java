package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.AdvanceInfo;
import com.gooalgene.qtl.service.AdvanceInfoService;
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
 * Created by 陈冬 on 2017/7/14.
 */
@Controller
@RequestMapping("/advanceinfo")
public class AdvanceInfoController {

    Logger logger = LoggerFactory.getLogger(AdvanceInfoController.class);

    @Autowired
    private AdvanceInfoService advanceInfoService;

    @RequestMapping(value = "/list")
    public String advanceInfoList(Model model) {
        return "advanceinfo/advanceinfo-list";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<AdvanceInfo> page = new Page<AdvanceInfo>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        logger.info(start + "\t" + length + "\t" + (start / length + 1) + ",Type:" + type + ",search:" + sSearch);
        JSONArray result = advanceInfoService.searchAdvanceInfosbyKeywords(type, sSearch, page);
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

    @RequestMapping(value = "/toaddadvanceinfo")
    public String toAddAdvanceInfo() {
        return "advanceinfo/add";
    }

    @RequestMapping(value = "/savenewadvanceinfo")
    public String saveNewAdvanceinfo(AdvanceInfo advanceInfo) {
        advanceInfoService.saveAdvanceinfo(advanceInfo);
        return "redirect:/advanceinfo/list";
    }

    @RequestMapping(value = "/toedit")
    public String toEdit(int id, Model model) {
        AdvanceInfo advanceInfo = advanceInfoService.findAdvanceInfoById(id);
        model.addAttribute("advanceInfo", advanceInfo);
        return "advanceinfo/edit";
    }

    @RequestMapping(value = "/saveedit")
    public String saveEdit(AdvanceInfo advanceInfo) {
        advanceInfoService.updateAdvanceInfo(advanceInfo);
        return "redirect:/advanceinfo/list";
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public boolean deleteAdvanceInfo(int id) {
        boolean flag = false;
        try {
            advanceInfoService.deleteAdvanceInfo(id);
            flag = true;
        } catch (Exception e) {

        }
        return flag;
    }
}
