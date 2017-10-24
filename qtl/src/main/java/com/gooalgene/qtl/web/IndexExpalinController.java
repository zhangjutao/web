package com.gooalgene.qtl.web;

import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.entity.IndexExplain;
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
import java.util.List;
import java.util.Map;

/**
 * Created by ShiYun on 2017/8/8 0008.
 */
@Controller
@RequestMapping("/indexexplains")
public class IndexExpalinController {

    Logger logger = LoggerFactory.getLogger(IndexExpalinController.class);

    @Autowired
    private IndexExplainService indexExplainService;

    @RequestMapping("/list")
    public String index(Model model) {
        return "indexexplains/indexexplains";
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
        List<IndexExplain> list = indexExplainService.queryAll();
        JSONArray result = new JSONArray();
        for (IndexExplain indexExplain : list) {
            JSONObject one = new JSONObject();
            one.put("id", indexExplain.getId());
            one.put("type", indexExplain.getType());
            one.put("detail", indexExplain.getDetail());
            result.add(one);
        }
        jsonobj.put("aData", result);
        Map<String, Object> map = new HashMap<String, Object>();
        // 为操作次数加1，必须这样做
        int initEcho = sEcho + 1;
        map.put("sEcho", initEcho);
        map.put("iTotalRecords", result.size());
        map.put("iTotalDisplayRecords", result.size());
        map.put("aData", result);
        model.addAttribute("aData", result);
        return map;
    }

    @RequestMapping("/toadd")
    public String toadd(Model model) {
        return "indexexplains/add";
    }

    @RequestMapping("/saveadd")
    public String add(Model model) {
        boolean flage = true;
        System.out.println("flage:" + flage);
        return "redirect:/indexexplains/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(String type, Model model) {
        IndexExplain indexExplain = indexExplainService.queryByType(type);
        model.addAttribute("id", indexExplain.getId());
        model.addAttribute("type", indexExplain.getType());
        model.addAttribute("detail", indexExplain.getDetail());
        return "indexexplains/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String detail = request.getParameter("detail");
        System.out.println(id + "\t" + detail);
        if (StringUtils.isNotBlank(detail) && StringUtils.isNumeric(id)) {
            IndexExplain indexExplain = new IndexExplain();
            indexExplain.setId(id);
            indexExplain.setDetail(detail);
            indexExplainService.update(indexExplain);
        } else {
            logger.error("para error:{id=" + id + ",detail=" + detail + "}");
        }
        return "redirect:/indexexplains/list";
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
