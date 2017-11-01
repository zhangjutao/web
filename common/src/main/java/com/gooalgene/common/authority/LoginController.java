package com.gooalgene.common.authority;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hedingwei on 16/10/2017.
 * dna数据库启动页面直接进登录页
 */
@Controller
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "无效的用户名或密码!");
    }
        if (logout != null) {
            model.addObject("msg", "登出成功.");
        }
        model.setViewName("login");

        return model;

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    @RequestMapping(value="/signup", method = RequestMethod.GET)
    public String signupPage () {
        return "signup";
    }

    @RequestMapping(value="/signup.do", method = RequestMethod.POST)
    public ModelAndView signupDo (
                                      @RequestParam(value = "username")String username,
                                      @RequestParam("email")String email,
                                      @RequestParam("password")String password,
                                      @RequestParam("passwordVeriry")String passwordVerify,
                                      @RequestParam(value = "phone",required = false)String phone,
                                      @RequestParam(value = "domains",required = false)String domains,
                                      @RequestParam(value = "university",required = false)String university
                                  ) {
        ModelAndView modelAndView = new ModelAndView("signupDone");



        return modelAndView;
    }

    @RequestMapping("/403")
    public String err403(HttpServletRequest request) {
        return "err403";
    }
}
