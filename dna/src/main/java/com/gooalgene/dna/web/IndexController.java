package com.gooalgene.dna.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 解决用户在登录之后输入"/"时仍然显示登陆页问题
 * 这里无需对用户登录状态做判断,若未登录:spring security会直接弹出到登录页面
 * 若已登录:直接返回到首页
 *
 * Created by crabime on 3/15/18.
 */
@Controller
@RequestMapping(value = "/")
public class IndexController {

    @RequestMapping
    public String index() {
        return "redirect:/dna/index";
    }
}
