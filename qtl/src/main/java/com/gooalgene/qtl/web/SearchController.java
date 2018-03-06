package com.gooalgene.qtl.web;

import com.gooalgene.common.Page;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.entity.Qtl;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 2017/07/08.
 */
@Controller
@RequestMapping("/search")
public class SearchController {
    private final static Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private QueryService queryService;
    @Autowired
    private IndexExplainService indexExplainService;

    @RequestMapping("/index")
    public ModelAndView login(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            // 拿到当前用户角色
            Collection<Role> authorities = (Collection<Role>) authentication.getAuthorities();
            for (Role authority : authorities) {
                logger.info("当前用户拥有的权限为：" + authority.getAuthority());
            }
        }
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
//        String keywords = request.getParameter("keywords");
//        String version = request.getParameter("version");
//        String parameters = request.getParameter("condition");
        if (type == null) {
            return new ModelAndView("redirect:/search/index");
        }
//        Page<Qtl> page = new Page<Qtl>(request, response);
        modelAndView.addObject("types", queryService.queryAll());  // 搜索结果侧边栏
        modelAndView.addObject("versions", queryService.queryVersions());  // 可选的所有基因版本

//        modelAndView.addAllObjects(queryService.qtlSearchByResult(version, type, keywords, parameters, page));
        modelAndView.addObject("condition", "{}");
//        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @RequestMapping(value = "/list/page",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<Map> lista(HttpServletRequest request) {
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String version = request.getParameter("version");
        String parameters = request.getParameter("condition");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Map result = queryService.qtlSearchByResult(version, type, keywords, parameters, pageNo, pageSize);
        return ResultUtil.success(result);
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
//        String keywords = request.getParameter("keywords");
//        String parameters = request.getParameter("condition");
//        Page<Qtl> page = new Page<Qtl>(request, response);
        modelAndView.addObject("versions", queryService.queryVersions());
        modelAndView.addObject("types", queryService.queryAll());
//        modelAndView.addAllObjects(queryService.qtlSearchByResult(version, type, keywords, parameters, page));
//        modelAndView.addObject("page", page);
        return modelAndView;
    }
}