package com.gooalgene.qtl.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/home")
public class QtlHomeController {

    @RequestMapping("/index")
    public String home(HttpServletRequest request){
        String userName = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal != null) {
            if (principal instanceof UserDetails) {
                userName =  ((UserDetails) principal).getUsername();
            }else if (principal instanceof Principal) {
                userName =  ((Principal) principal).getName();
            } else {
                userName =  String.valueOf(principal);
            }
            if ("anonymousUser".equals(userName)) {
                userName = "";
            }
        }
        request.getSession().setAttribute("userName", userName);

        return "redirect:/search/index";
    }
}
