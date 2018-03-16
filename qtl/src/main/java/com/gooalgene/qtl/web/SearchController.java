package com.gooalgene.qtl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.qtl.entity.QtlTableEntity;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.qtl.service.handler.NullSerializerImpl;
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
        if (type == null) {
            return new ModelAndView("redirect:/search/index");
        }
        modelAndView.addObject("types", queryService.queryAll());  // 搜索结果侧边栏
        modelAndView.addObject("versions", queryService.queryVersions());  // 可选的所有基因版本
        modelAndView.addObject("condition", "{}");
        return modelAndView;
    }

    @RequestMapping(value = "/list/page",method = RequestMethod.POST)
    @ResponseBody
    public String searchForQtl(HttpServletRequest request) throws JsonProcessingException {
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String version = request.getParameter("version");
        String parameters = request.getParameter("condition");
        // 表格设置中用户选择的check box
        String checkedOption = request.getParameter("choices");
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        QtlTableEntity result =
                queryService.qtlSearchByResult(version, type, keywords, parameters, pageNo, pageSize, checkedOption);
        ObjectMapper mapper = new ObjectMapper();
        DefaultSerializerProvider.Impl provider = new DefaultSerializerProvider.Impl();
        provider.setNullValueSerializer(new NullSerializerImpl());
        mapper.setSerializerProvider(provider);
        String jsonOutputResult = mapper.writeValueAsString(result);
        return jsonOutputResult;
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
        modelAndView.addObject("versions", queryService.queryVersions());
        modelAndView.addObject("types", queryService.queryAll());
        return modelAndView;
    }
}