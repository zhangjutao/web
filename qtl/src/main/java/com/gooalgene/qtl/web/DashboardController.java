package com.gooalgene.qtl.web;

import com.gooalgene.entity.User;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.qtl.service.QUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 此控制器用于后台管理登录
 * Created by Xiaoyao on 2017/7/11 0010.
 */
@Controller
@RequestMapping("/d")
public class DashboardController {

    Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private QUserService qUserService;

    @Autowired
    private QueryService queryService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        logger.info("后台登录.");
        return "dashboard/login";
    }

    @RequestMapping("/tologin")
    public String tologin(User user, Model model) {
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        List<User> userList = qUserService.findByUsername(user.getUsername());
        if (userList.size() > 0) {
            List<User> users = qUserService.findByUsernameAndPassword(user);
            if (users.size() > 0) {
                return "redirect:/traitcategory/list";
            } else {
                model.addAttribute("user", user);
                model.addAttribute("msg", "密码错误");
                return "/dashboard/login";
            }
        } else {
            model.addAttribute("user", user);
            model.addAttribute("msg", "用户名不存在");
            return "/dashboard/login";
        }
    }

    @RequestMapping("/table")
    public String table(HttpServletRequest request) {
        logger.info("列表样例.");
        return "dashboard/table-list";
    }

    @RequestMapping("/form")
    public String form(HttpServletRequest request) {
        logger.info("表单样例.");

        return "dashboard/form";
    }

    @RequestMapping("/soybean")
    @ResponseBody
    public String soybean(HttpServletRequest request) {
        logger.info("query soybean list.");
        String name = request.getParameter("name");
        return queryService.queryBySoybeanName(name).toString();
    }

    @RequestMapping("/logout")
    public String tologout(User user) {
        logger.info("logout:" + user.getUsername());
        return "/dashboard/login";
    }
}
