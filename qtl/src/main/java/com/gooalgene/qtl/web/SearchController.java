package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private QueryService queryService;
    @Autowired
    private IndexExplainService indexExplainService;

    @RequestMapping("/index")
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/search/index");
        String version = "Gmax_275_v2.0";
        modelAndView.addObject("types", queryService.queryAll());
        modelAndView.addAllObjects(queryService.queryChrsByVersion(version));
        modelAndView.addObject("version", version);
        modelAndView.addObject("qtlDetail", indexExplainService.queryByType("qtl").getDetail());
        return modelAndView;
    }

    @RequestMapping("/aboutus")
    public ModelAndView aboutus(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/search/about-us");
        String qtl_name = request.getParameter("name");
        String version = request.getParameter("version");
        if (qtl_name == null || version == null) {
            return new ModelAndView("redirect:/search/index");
        }
        modelAndView.addAllObjects(queryService.qtlDetailByName(qtl_name, version));
        return modelAndView;
    }

    /**
     * 首页搜索框调用
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {

        ModelAndView modelAndView = new ModelAndView("/search/list");
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String version = request.getParameter("version");
        if (type == null) {
            return new ModelAndView("redirect:/search/index");
        }
        Page<Qtl> page = new Page<Qtl>(request, response);
        modelAndView.addObject("types", queryService.queryAll());
        modelAndView.addObject("versions", queryService.queryVersions());
        modelAndView.addAllObjects(queryService.qtlSearchbyKeywords(version, type, keywords, page));
        modelAndView.addObject("condition", "{}");
        modelAndView.addObject("page", page);
        return modelAndView;
    }


    /**
     * 对搜索结果进行搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/listByResult")
    public ModelAndView list1(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("/search/list");
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        String type = request.getParameter("type");
        String version = request.getParameter("version");
        if (type == null || version == null) {
            return new ModelAndView("redirect:/search/index");
        }
        String keywords = request.getParameter("keywords");
        String parameters = request.getParameter("condition");
        Page<Qtl> page = new Page<Qtl>(request, response);
        modelAndView.addObject("versions", queryService.queryVersions());
        modelAndView.addObject("types", queryService.queryAll());
        modelAndView.addAllObjects(queryService.qtlSearchbyResult(version, type, keywords, parameters, page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }
}