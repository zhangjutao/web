package com.gooalgene.qtl.web;

import com.gooalgene.entity.Chrlg;
import com.gooalgene.qtl.service.ChrlgService;
import com.gooalgene.qtl.service.QueryService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 此控制器和基因图谱绘画数据有关
 * Created by Administrator on 2017/07/08.
 */
@Controller
public class GeneViewController {

    @Autowired
    private QueryService queryService;

    @Autowired
    private ChrlgService chrlgService;

    @RequestMapping("/gene")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView test = new ModelAndView("search/gene");
        String chr = request.getParameter("chr");
        String version = request.getParameter("version");
        if (chr == null || version == null) {
            return new ModelAndView("redirect:/qtl/index");
        }
        String markerlg = request.getParameter("markerlg");
        if (StringUtils.isBlank(markerlg)) {//搜索页点击lg转跳基因图时,缺少markerlg参数
            if (chr.startsWith("Chr")) {
                version = "Gmax_275_v2.0";
            } else {
                version = "Glycine_max.V1.0.23.dna.genome";
            }
            Chrlg chrlg = chrlgService.queryByChrAndVersion(chr, version);
            if (chrlg != null) {
                if (chr.startsWith("Chr")) {
                    if (chr.startsWith("Chr0")) {
                        markerlg = chrlg.getLg() + "(" + chr.substring(4) + ")";
                    } else {
                        markerlg = chrlg.getLg() + "(" + chr.substring(3) + ")";
                    }
                } else {
                    markerlg = chrlg.getLg() + "(" + chr + ")";
                    if (chr.length() == 1) {
                        chr = "0" + chr;
                    }
                }
            }
            System.out.println(markerlg);
        }
        System.out.println("chr:" + chr + ",version:" + version + ",markerlg:" + markerlg);
        test.addObject("chr", chr);
        test.addObject("version", version);
        test.addObject("markerlg", markerlg);
        test.addObject("lg", markerlg.substring(0, markerlg.indexOf("(")));
        return test;
    }

    @RequestMapping("/innerGene")
    public ModelAndView innerGene(HttpServletRequest request) {
        ModelAndView test = new ModelAndView("search/inner-gene");
        String chr = request.getParameter("chr");
        String version = request.getParameter("version");
        if (chr == null || version == null) {
            return new ModelAndView("redirect:/qtl/index");
        }
        String markerlg = request.getParameter("markerlg");
        if (StringUtils.isBlank(markerlg)) {//搜索页点击lg转跳基因图时,缺少markerlg参数
            if (chr.startsWith("Chr")) {
                version = "Gmax_275_v2.0";
            } else {
                version = "Glycine_max.V1.0.23.dna.genome";
            }
            Chrlg chrlg = chrlgService.queryByChrAndVersion(chr, version);
            if (chrlg != null) {
                if (chr.startsWith("Chr")) {
                    if (chr.startsWith("Chr0")) {
                        markerlg = chrlg.getLg() + "(" + chr.substring(4) + ")";
                    } else {
                        markerlg = chrlg.getLg() + "(" + chr.substring(3) + ")";
                    }
                } else {
                    markerlg = chrlg.getLg() + "(" + chr + ")";
                    if (chr.length() == 1) {
                        chr = "0" + chr;
                    }
                }
            }
            System.out.println(markerlg);
        }
        test.addObject("chr", chr);
        test.addObject("version", version);
        test.addObject("markerlg", markerlg);
        test.addObject("lg", markerlg == null ? "" : markerlg.substring(0, markerlg.indexOf("(")));
        return test;
    }

    @RequestMapping("/getMarkerLeft")
    @ResponseBody
    public String data1(HttpServletRequest request) {
        String chr = request.getParameter("chr");
        String version = request.getParameter("version");
        return queryService.getMarkerLeftData(chr, version).toString();
    }


    @RequestMapping("/getMarkerRight")
    @ResponseBody
    public String data2(HttpServletRequest request) {
        System.out.println("getMarkerRight");
        String markerlg = request.getParameter("markerlg");
        return queryService.getMarkerRightData(markerlg).toString();
    }


    @RequestMapping("/getQtlByLg")
    @ResponseBody
    public String data3(HttpServletRequest request) {
        System.out.println("getQtl");
        String lg = request.getParameter("lg");
        return queryService.getQTLData(lg).toString();
    }

    @RequestMapping("/index")
    public String home(HttpServletRequest request) {
        return "redirect:/dna/index";
    }

    @RequestMapping("/geneInfo")
    public String geneInfo(HttpServletRequest request) {
        return "/qtl/genesInfo";
    }

}
