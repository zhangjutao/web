package com.gooalgene.qtl.signup.controller;

//import com.gooalgene.qtl.signup.entity.CUser;
//import com.gooalgene.qtl.signup.service.CUserService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by hedingwei on 16/10/2017.
 */
@Controller
@RequestMapping("/signup")
public class SignUpController {

//    @Autowired
//    CUserService cUserService;

    @RequestMapping(value="/action", method = RequestMethod.GET)
    public String signupPage () {
//        System.out.println(cUserService.queryAll());
        return "signup";
    }

//    @RequestMapping(value="/action", method = RequestMethod.POST)
//    public ModelAndView signupDo (
//                                      @RequestParam(value = "username",required = false)String username,
//                                      @RequestParam(value = "email",required = false)String email,
//                                      @RequestParam(value = "password",required = false)String password,
//                                      @RequestParam(value = "passwordVerify",required = false)String passwordVerify,
//                                      @RequestParam(value = "phone",required = false)String phone,
//                                      @RequestParam(value = "domains",required = false)String domains,
//                                      @RequestParam(value = "university",required = false)String university
//                                  ) {
//
//
//        ModelAndView modelAndView = new ModelAndView("signup");
//        modelAndView.addObject("username",username);
//        modelAndView.addObject("email",email);
//        modelAndView.addObject("password",password);
//        modelAndView.addObject("passwordVerify",passwordVerify);
//        modelAndView.addObject("phone",phone);
//        modelAndView.addObject("domains",domains);
//        modelAndView.addObject("university",university);
//        boolean hasError = false;
//        if((username==null) ||username.isEmpty()){
//            modelAndView.addObject("error","用户名没有填写");
//            return modelAndView;
//        }
//
//        if((email==null)||email.isEmpty()){
//            modelAndView.addObject("error","电子邮件没有填写");
//            return modelAndView;
//        }
//
//        if((password==null)||password.isEmpty()){
//            modelAndView.addObject("error","密码没有填写");
//            return modelAndView;
//        }
//
//        if((passwordVerify==null)||passwordVerify.isEmpty()){
//            modelAndView.addObject("error","请补全密码确认信息");
//            return modelAndView;
//        }
//
//        if(cUserService.exist(username)){
//            System.out.println("exist user? ["+username+"]="+true);
//            modelAndView.addObject("error","用户已经存在，请换一个用户名");
//            return modelAndView;
//        }else{
//            System.out.println("exist user? ["+username+"]="+false);
//        }
//
//        if(!password.equals(passwordVerify)){
//            modelAndView.addObject("error","两次密码不一致");
//            return modelAndView;
//        }
//
//        CUser cUser = new CUser();
//        cUser.setUsername(username);
//        cUser.setEmail(email);
//        PasswordEncoder encoder = new Md5PasswordEncoder();
//        System.out.println("p:"+password);
//        password = encoder.encodePassword(password, null);
//        System.out.println("ph:"+password);
////        password = md5(password);
//        cUser.setPassword(password);
//        cUser.setPhone(phone);
//        cUser.setDomains(domains);
//        cUser.setUniversity(university);
//        if(cUserService.createUser(cUser)){
//            modelAndView.addObject("user",cUser);
//        }else {
//            modelAndView.addObject("error", "创建失败");
//        }
//        return modelAndView;
//    }
//
//    public String md5(String v){
//        MessageDigest messageDigest = null;
//        try {
//            messageDigest = MessageDigest.getInstance("MD5");
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        messageDigest.update(v.getBytes(),0, v.length());
//        String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);
//        if (hashedPass.length() < 32) {
//            hashedPass = "0" + hashedPass;
//        }
//        return hashedPass;
//    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public String toForget(Model model) {
        return "forget";
    }
//
//    @RequestMapping(value = "/forget", method = RequestMethod.POST)
//    public ModelAndView forgetPwd(@RequestParam String username, @RequestParam String email, Model model) {
//        ModelAndView mv = new ModelAndView("forget");
//        model.addAttribute("username", username);
//        model.addAttribute("email", email);
//        CUser user = cUserService.findByUsername(username);
//        if (user == null) {
//            model.addAttribute("error", "用户不存在");
//            return mv;
//        }
//
//        if (!email.equals(user.getEmail())) {
//            model.addAttribute("error", "不是注册邮箱");
//            return mv;
//        }
//
//        if (cUserService.applyPasswdRest(user)) {
//            model.addAttribute("user", user);
//            return mv;
//        } else {
//            model.addAttribute("error", "申请重置密码失败");
//            return mv;
//        }
//    }
//    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
//    public String toModifyPassword(Model model) {
//        return "modify-password";
//    }
}
