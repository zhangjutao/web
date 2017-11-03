package com.gooalgene.mrna.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Qtl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
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
@RequestMapping("/fpkm")
public class FPKMController {

    Logger logger = LoggerFactory.getLogger(FPKMController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/list")
    public String index(Model model) {
        return "allfpkm/allfpkm";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<Qtl> page = new Page<Qtl>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
//        System.out.println("Type:" + type + ",search:" + sSearch);
//        JSONArray result = qtlService.searchQtlsbyKeywords(type, sSearch, page);
        JSONArray result = new JSONArray();
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
        return "allfpkm/add";
    }

    @RequestMapping("/saveadd")
    public String add(Qtl qtl, Model model) {
        boolean flage = true;
        return "redirect:/allfpkm/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        return "allfpkm/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(Qtl qtl, Model model) {
        int flage = 0;
        return "redirect:/allfpkm/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flage = true;
        if (flage == true) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }
}