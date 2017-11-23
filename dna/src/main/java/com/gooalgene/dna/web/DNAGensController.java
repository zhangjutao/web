package com.gooalgene.dna.web;

import com.gooalgene.common.Page;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.service.DNAGensService;
import com.gooalgene.utils.ResultUtil;
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
import java.util.Map;

/**
 * Created by 陈冬 on 2017/8/22.
 */
@Controller
@RequestMapping(value = "/dnagens")
public class DNAGensController {
    Logger logger = LoggerFactory.getLogger(DNAGensController.class);

    @Autowired
    private DNAGensService dnaGensService;

    @RequestMapping("/list")
    public String index() {
        return "dnagens/dnagens";
    }

    @RequestMapping("/index")
    @ResponseBody
    public Map<String, Object> list(HttpServletRequest request, Model model, HttpServletResponse response) {
        Integer sEcho = Integer.valueOf(request.getParameter("sEcho"));// 记录操作的次数 每次加1
        JSONObject jsonobj = new JSONObject();
        Page<DNAGens> page = new Page<DNAGens>(request, response);
        Integer start = Integer.valueOf(request.getParameter("iDisplayStart"));
        Integer length = Integer.valueOf(request.getParameter("iDisplayLength"));
        System.out.println(start + "\t" + length + "\t" + (start / length + 1));
        page.setPageNo(start / length + 1);
        page.setPageSize(length);
        String sSearch = request.getParameter("sSearch");
        String type = "All";
        System.out.println("Type:" + type + ",search:" + sSearch);
        JSONArray result = dnaGensService.searchDNAGensbyKeywords(type, sSearch, page);
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
        return "dnagens/add";
    }

    @RequestMapping("/saveadd")
    public String add(DNAGens dnaGens) {
        logger.info("toADD:" + dnaGens.toString());
        dnaGensService.add(dnaGens);
        return "redirect:/dnagens/list";
    }

    @RequestMapping("/toedit")
    public String tiedit(int id, Model model) {
        DNAGens dnaGens = dnaGensService.findById(id);
        model.addAttribute("dnaGens", dnaGens.toJSON());
        return "dnagens/edit";
    }

    @RequestMapping("/saveedit")
    public String saveedit(DNAGens dnaGens) {
        logger.info("toEdit:" + dnaGens.toString());
        dnaGensService.update(dnaGens);
        return "redirect:/dnagens/list";
    }

    @RequestMapping("/delete")
    @ResponseBody
    public boolean delete(HttpServletRequest request) {
        String id = request.getParameter("id");
        boolean flag = false;
        try {
            flag = dnaGensService.delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @RequestMapping(value = "/geneIds",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO geneIds(@RequestParam("start") String start, @RequestParam("end") String end) {
        return ResultUtil.success(dnaGensService.getByRegion(start,end));
    }
}
