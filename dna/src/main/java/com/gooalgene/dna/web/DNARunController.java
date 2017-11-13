package com.gooalgene.dna.web;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.service.DNARunService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/23.
 */
@Controller
@RequestMapping(value = "/dnarun")
public class DNARunController {

    Logger logger = LoggerFactory.getLogger(DNARunController.class);

    @Autowired
    private DNARunService dnaRunService;

    @RequestMapping("/list")
    public String index() {
        return "dnarun/dnarun";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<DNARun> page = new Page<DNARun>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        System.out.println("Type:" + type + ",search:" + sSearch);
        JSONArray result = dnaRunService.searchStudybyKeywords(type, sSearch, page);
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
        return "dnarun/add";
    }

    @RequestMapping("/saveadd")
    public String add(DNARun dnaRun) {
        System.out.println(dnaRun.toString());
        logger.info("toAdd:" + dnaRun.toString());
        boolean flag = dnaRunService.add(dnaRun);
        logger.info("insert:" + flag);
        return "redirect:/dnarun/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        DNARun dnaRun = dnaRunService.findById(id);
        model.addAttribute("dnaRun", dnaRun);
        return "dnarun/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(DNARun dnaRun) {
        int flag = dnaRunService.update(dnaRun);
        logger.info("update:" + flag);
        return "redirect:/dnarun/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = false;
        try {
            flag = dnaRunService.delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    @ResponseBody
    public List<DNARun> getDunRuns(HttpServletRequest request, DNARun dnaRun,
                                   @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize) {
        return dnaRunService.queryByondition(dnaRun,pageNum,pageSize);
    }
}
