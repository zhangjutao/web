package com.gooalgene.common.authority;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.common.dao.TokenDao;
import com.gooalgene.common.service.SMTPService;
import com.gooalgene.common.service.TokenService;
import com.gooalgene.common.service.UserService;
import com.gooalgene.utils.TokenUtils;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Crabime on 16/10/2017.
 * 注册controller
 */
@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final static Logger logger = LoggerFactory.getLogger(SignUpController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private SMTPService smtpService;

    @Autowired
    private GuavaCacheManager guavaCacheManager;
    private Cache author_cache;

    @Autowired
    private TokenService tokenService;

    @RequestMapping(value="/action", method = RequestMethod.GET)
    public String signupPage () {
        System.out.println(userService.queryAll());
        return "signup";
    }

    @RequestMapping(value="/action.do", method = RequestMethod.POST)
    public ModelAndView signupDo (
            @RequestParam(value = "username",required = false)String username,
            @RequestParam(value = "email",required = false)String email,
            @RequestParam(value = "password",required = false)String password,
            @RequestParam(value = "passwordVerify",required = false)String passwordVerify,
            @RequestParam(value = "phone",required = false)String phone,
            @RequestParam(value = "domains",required = false)String domains,
            @RequestParam(value = "university",required = false)String university
    ) {


        ModelAndView modelAndView = new ModelAndView("signup");
        modelAndView.addObject("username",username);
        modelAndView.addObject("email",email);
        modelAndView.addObject("password",password);
        modelAndView.addObject("passwordVerify",passwordVerify);
        modelAndView.addObject("phone",phone);
        modelAndView.addObject("domains",domains);
        modelAndView.addObject("university",university);
        boolean hasError = false;
        if((username==null) ||username.isEmpty()){
            modelAndView.addObject("error","用户名没有填写");
            return modelAndView;
        }

        if((email==null)||email.isEmpty()){
            modelAndView.addObject("error","电子邮件没有填写");
            return modelAndView;
        }

        if((password==null)||password.isEmpty()){
            modelAndView.addObject("error","密码没有填写");
            return modelAndView;
        }

        if((passwordVerify==null)||passwordVerify.isEmpty()){
            modelAndView.addObject("error","请补全密码确认信息");
            return modelAndView;
        }

        if(userService.exist(username)){
            System.out.println("exist user? ["+username+"]="+true);
            modelAndView.addObject("error","用户已经存在，请换一个用户名");
            return modelAndView;
        }else{
            System.out.println("exist user? ["+username+"]="+false);
        }

        if(!password.equals(passwordVerify)){
            modelAndView.addObject("error","两次密码不一致");
            return modelAndView;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        PasswordEncoder encoder = new Md5PasswordEncoder();
        System.out.println("p:"+password);
        password = encoder.encodePassword(password, null);
        System.out.println("ph:" + password);
//      //password = md5(password);
        user.setPassword(password);
        user.setPhone(phone);
        user.setDomains(domains);
        user.setUniversity(university);

        if(userService.createUser(user)){
            User_Role user_role=new User_Role(userService.findLastInsertId(),1);
            userService.setRole(user_role);
            System.out.println("userid:\t"+userService.findLastInsertId());
            modelAndView.addObject("user",user);
        }else {
            modelAndView.addObject("error", "创建失败");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/nameexists", method = RequestMethod.GET)
    @ResponseBody
    public String userNameExists(@RequestParam(value = "username", required = true)String username) throws JsonProcessingException {
        boolean exists = userService.exist(username);
        Map<String, Object> result = Collections.singletonMap("exists", exists);
        ObjectMapper objectMapper = new ObjectMapper(); //这里转成ajax更好读取的格式JSON
           String jsonResult = null;
           jsonResult = objectMapper.writeValueAsString(result);
        return jsonResult;
    }

    public String md5(String v){
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(v.getBytes(),0, v.length());
        String hashedPass = new BigInteger(1,messageDigest.digest()).toString(16);
        if (hashedPass.length() < 32) {
            hashedPass = "0" + hashedPass;
        }
        return hashedPass;
    }

    @RequestMapping(value = "/forget", method = RequestMethod.GET)
    public String toForget(Model model) {
        return "forget";
    }

    /*忘记密码  验证通过之后直接给相应的用户发送邮件*/
    @RequestMapping(value = "/forget", method = RequestMethod.POST)
    public ModelAndView forgetPwd(@RequestParam String username, @RequestParam String email, Model model) throws UnsupportedEncodingException, MessagingException {
        ModelAndView mv = new ModelAndView("forget");
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "用户不存在");
            return mv;
        }
        if (!email.equals(user.getEmail())) {
            model.addAttribute("error", "不是注册邮箱");
            return mv;
        }
        List<String> recevers=new ArrayList<String>();
        recevers.add(user.getEmail());
        HashMap<String,String> message=new HashMap<String,String>();
        message.put("subject", "DNA数据库用户确认");
        message.put("content", "忘记密码的确认邮件");
        Token token=new Token();
        token.setUserid(user.getId());
        System.out.println("用户的id" + user.getUid());
        token.setToken(TokenUtils.generateToken());

        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, 2);
        Date due_date=calendar.getTime();
        token.setDue_time(due_date);
        token.setToken_status(1);
        if(tokenService.getTokenByUserId(user.getId())!=null){
             tokenService.updateToken(token);
        }else {
            tokenService.insertToken(token);
        }
             author_cache=guavaCacheManager.getCache("config");
             String admin_email=author_cache.get("mail.administrator").get().toString();
             smtpService.send(admin_email,recevers,message.get("subject"),message.get("content"),true);
        return mv;
    }
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public String toModifyPassword(HttpServletRequest req, Model model) {
        String username = (String) req.getSession().getAttribute("userName");
        if (username == null) {
            return "redirect:/login";
        }
        return "modify-password";
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public String modifyPassword(String oldpwd, String password, String pwdverify, HttpServletRequest req, Model model) {
        if (oldpwd == null || oldpwd.isEmpty()) {
            model.addAttribute("error", "原密码未填写");
            return "modify-password";
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "新密码未填写");
            return "modify-password";
        }
        if (pwdverify == null || pwdverify.isEmpty()) {
            model.addAttribute("error", "密码确认未填写");
            return "modify-password";
        }
        if (!password.equals(pwdverify)) {
            model.addAttribute("error", "两次密码不一致");
            return "modify-password";
        }
        String username = (String) req.getSession().getAttribute("userName");
        if (username == null) {
            return "redirect:/login";
        }

        return "modify-password";
    }

    /**
     * 用户点击URL，修改密码生效
     * 用户点击URL需要与入库的token进行比较，false则进入验证错误页面，打印错误日志
     * @param token 唯一的UUID值
     * @return 重定向页面
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(@RequestParam(name = "token") String token,
                         @RequestParam(name = "id") int id){
        Token originToken = tokenService.getTokenByUserId(id);
        Date dueTime = originToken.getDue_time();
        Date currentTime = new Date();
        String oldToken = originToken.getToken();
        if (dueTime.before(currentTime)){
            logger.warn("token已失效");
            return "err403";
        }
        if (!oldToken.equals(token)){
            logger.warn("传入token有异常");
            return "err403";
        }
        tokenService.disableToken(id); //让当前token失效
        return "modify-password";
    }
}
