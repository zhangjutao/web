package com.gooalgene.common.authority;

import com.gooalgene.common.service.*;
import com.gooalgene.utils.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Calendar;
import java.util.regex.*;
import java.util.regex.Matcher;

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
    private RoleService roleService;
    @Autowired
    private User_RoleService user_roleService;

    @Autowired
    private GuavaCacheManager guavaCacheManager;
    private Cache author_cache;

    /*@Autowired
    private AuthenticationManager authenticationManager;*/

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
        modelAndView.addObject("username", username);
        modelAndView.addObject("email", email);
        modelAndView.addObject("password", password);
        modelAndView.addObject("passwordVerify", passwordVerify);
        modelAndView.addObject("phone", phone);
        modelAndView.addObject("domains", domains);
        modelAndView.addObject("university", university);
        boolean hasError = false;
        if ((username == null) || username.isEmpty()) {
            modelAndView.addObject("error", "用户名没有填写");
            return modelAndView;
        }

        if ((email == null) || email.isEmpty()) {
            modelAndView.addObject("error", "电子邮件没有填写");
            return modelAndView;
        }else {
            if(userService.getEmailCount(email)>0){
                modelAndView.addObject("error","电子邮件已被使用");
                return modelAndView;
        }
        }

        if ((password == null) || password.isEmpty()) {
            modelAndView.addObject("error", "密码没有填写");
            return modelAndView;
        }

        if ((passwordVerify == null) || passwordVerify.isEmpty()) {
            modelAndView.addObject("error", "请补全密码确认信息");
            return modelAndView;
        }

        if (userService.exist(username)) {
            System.out.println("exist user? [" + username + "]=" + true);
            modelAndView.addObject("error", "用户已经存在，请换一个用户名");
            return modelAndView;
        } else {
            System.out.println("exist user? [" + username + "]=" + false);
        }

        if (!password.equals(passwordVerify)) {
            modelAndView.addObject("error", "两次密码不一致");
            return modelAndView;
        }

        //判断输入是否合法
        //判断用户名
        String usernameRex = "[a-zA-Z0-9]{2,14}";
        Pattern pattern = Pattern.compile(usernameRex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            modelAndView.addObject("error", "用户名输入不合法");
            return modelAndView;
        }
        //判断邮箱
        String passwordRex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        Pattern emaliPatten = Pattern.compile(passwordRex);
        Matcher emailMatcher = emaliPatten.matcher(email);
        if (!emailMatcher.matches()) {
            modelAndView.addObject("error", "邮箱输入不合法");
            return modelAndView;
        }
        //判断密码
        String passWordRex = "[a-zA-Z0-9]{5,}";
        Pattern passwordPatten = Pattern.compile(passWordRex);
        Matcher passwordMatcher = passwordPatten.matcher(password);
        if (!passwordMatcher.matches()) {
            modelAndView.addObject("error", "密码输入不合法");
            return modelAndView;
        }
        //判断联系方式
        if(!phone.equals("")){
            String phoneRex = "\\d{3}-\\d{8}|\\d{4}-\\d{7,8}|\\d{11}";
            Pattern phonePatten = Pattern.compile(phoneRex);
            Matcher phoneMatcher = phonePatten.matcher(phone);
            if (!phoneMatcher.matches() && phone != null) {
//            modelAndView.addObject("error","联系方式输入不合法");
                return modelAndView;
            }

        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        PasswordEncoder encoder = new Md5PasswordEncoder();
        password = encoder.encodePassword(password, null);
        user.setPassword(password);
        user.setPhone(phone);
        user.setDomains(domains);
        user.setUniversity(university);

        if(userService.createUser(user)){
            User_Role user_role=new User_Role(userService.findLastInsertId(),2);
            userService.setRole(user_role);
            modelAndView.addObject("user",user);
        }else {
            modelAndView.addObject("error", "创建失败");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/nameexists", method = RequestMethod.GET)
    @ResponseBody
    public String userNameExists(@RequestParam(value = "username", required = true)String username) {
        boolean exists = userService.exist(username);
        return String.valueOf(exists);
    }


    //好像没有用到这个方法
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
    public ModelAndView forgetPwd(@RequestParam String username, @RequestParam String email, Model model,HttpServletRequest request) throws IOException, MessagingException {
        ModelAndView mv = new ModelAndView("forget");
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        User user = userService.findByUsername(username);
        if (user == null) {
            model.addAttribute("error", "用户名不存在");
            return mv;
        }
        if (!email.equals(user.getEmail())) {
            model.addAttribute("error", "邮箱和用户名不匹配");
            return mv;
        }
        mv.addObject("user", user);
        List<String> recevers=new ArrayList<String>();
        recevers.add(user.getEmail());
        HashMap<String,String> message=new HashMap<String,String>();
        message.put("subject", "找回密码状态通知");
        Token token=new Token();
        token.setUserid(user.getId());
        Resource resource=new ClassPathResource("findBackPwd.html");
        File file=resource.getFile();
        String[] args = new String[7];
        args[0]=user.getUsername();

        Date updateDate=Calendar.getInstance().getTime();
        //给用户一个token值
        token.setToken(TokenUtils.generateToken());
        Calendar calendar1=Calendar.getInstance();

        calendar1.setTime(updateDate);
        args[1]= String.valueOf(calendar1.get(Calendar.YEAR));
        args[2]= String.valueOf(calendar1.get(Calendar.MONTH)+1);
        args[3]= String.valueOf(calendar1.get(Calendar.DAY_OF_MONTH));
        args[4]= String.valueOf(calendar1.get(Calendar.HOUR_OF_DAY));
        args[5]= String.valueOf(calendar1.get(Calendar.MINUTE));


        String scheme = request.getScheme();                // http
        String serverName = request.getServerName();        // gooalgene.com
        int serverPort = request.getServerPort();           // 8080
        String contextPath = request.getContextPath();      // /dna
        //重构请求URL
        StringBuilder builder = new StringBuilder();
        builder.append(scheme).append("://").append(serverName);
        //针对nginx反向代理、https请求重定向，不需要加端口
        if (serverPort != 80 && serverPort != 443){
            builder.append(":").append(serverPort);
        }
        builder.append(contextPath);
        args[6]= builder.append("/signup/verify?id=").append(user.getId()).append("&token=").append(token.getToken()).toString();

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(updateDate);
        calendar.add(Calendar.HOUR, 2);
        Date due_date=calendar.getTime();
        token.setDue_time(due_date);
        token.setToken_status(1);
        token.setUpdateTime(updateDate);

        if(tokenService.getTokenByUserId(user.getId())!=null){
             tokenService.updateToken(token);
        }else {
            tokenService.insertToken(token);
        }
        author_cache = guavaCacheManager.getCache("config");
        String admin_email=author_cache.get("mail.administrator").get().toString();
        smtpService.send(admin_email,recevers,message.get("subject"),file,false, args);
        return mv;
    }
    @RequestMapping(value = "/modifyPassword", method = RequestMethod.GET)
    public String toModifyPassword(HttpServletRequest req, Model model ) {
        return "modify-password";
    }

    @RequestMapping(value = "/temp/modifyPassword", method = RequestMethod.GET)
    public String tempModifyPassword(HttpServletRequest req, Model model ) {
        String username = (String) req.getSession().getAttribute("userName");
        return "temp-modify-password";
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
    public ModelAndView modifyPassword(String oldpwd, String password, String pwdverify,Model model) {
         Object principal=SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         String username=null;
         ModelAndView modelAndView=new ModelAndView("/modify-password");
         model.addAttribute("oldpwd",oldpwd);
         model.addAttribute("password",password);
         model.addAttribute("pwdverify",pwdverify);
         String pwdRex="[a-zA-Z0-9]{5,}";
         Pattern pattern=Pattern.compile(pwdRex);
         Matcher oldPwdMatcher=pattern.matcher(oldpwd);
         Matcher newPwdMatcher=pattern.matcher(password);
        if (!oldPwdMatcher.matches()&&!oldpwd.equals("")){
            model.addAttribute("error","原密码输入不符合要求，请重新输入");
            return modelAndView;
        }
        if(!newPwdMatcher.matches()&&!password.equals("")){
            model.addAttribute("error","新密码输入不符合要求，请重新输入");
            return modelAndView;
        }
        if (pwdverify.equals("")){
            model.addAttribute("error","确认密码未填写");
        }

        if(principal instanceof UserDetails){
            username=((UserDetails) principal).getUsername();
        }else {
            username=principal.toString();
        }
        if (oldpwd == null || oldpwd.isEmpty()) {
            model.addAttribute("error", "原密码未填写");
            return modelAndView;
        }
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "新密码未填写");
            return modelAndView;
        }
        if (pwdverify == null || pwdverify.isEmpty()) {
            model.addAttribute("error", "确认新密码未填写");
            return modelAndView;
        }
        if (!password.equals(pwdverify)) {
            model.addAttribute("error", "两次密码不一致");
            return modelAndView;
        }

        User user=userService.findByUsername(username);
        if(user==null){
            model.addAttribute("error","登录信息错误，用户不存在，请重新登录");
            return modelAndView;
        }else {
            PasswordEncoder encoder1=new Md5PasswordEncoder();
            String tempOldPwd=encoder1.encodePassword(oldpwd,null);
            if(!tempOldPwd.equals(user.getPassword())){
                model.addAttribute("error","原密码错误");
                return modelAndView;
            }

            PasswordEncoder encoder=new Md5PasswordEncoder();
            String newPwd=encoder.encodePassword(password,null);
            user.setPassword(newPwd);
            user.setReset(1);
            userService.updateUserPassword(user);
            model.addAttribute("user", user);
            model.addAttribute("error","密码修改完成");
        }

        return modelAndView;
    }

    @RequestMapping(value = "/temp/modifyPassword", method = RequestMethod.POST)
    public String tempModifyPassword(@RequestParam("userId") Integer id,
                                     String password, String pwdverify, HttpServletRequest request, Model model,Authentication authentication) {
        if (password == null || password.isEmpty()) {
            model.addAttribute("error", "新密码未填写");
            return "modify-password";
        }
        if (pwdverify == null || pwdverify.isEmpty()) {
            model.addAttribute("error", "确认新密码未填写");
            return "modify-password";
        }
        if (!password.equals(pwdverify)) {
            model.addAttribute("error", "两次密码不一致");
            return "modify-password";
        }
        User user=userService.getUserById(id);
        if(user==null){
            model.addAttribute("error","登录信息错误，用户不存在，请重新登录");
            return "modify-password";
        }else {
            PasswordEncoder encoder=new Md5PasswordEncoder();
            String newPwd=encoder.encodePassword(password,null);
            user.setPassword(newPwd);
            user.setReset(1);
            userService.updateUserPassword(user);
            //SecurityUser tempUser=(SecurityUser)authentication.getPrincipal();
            //userService.deleteUser(tempUser.getId());
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "/login";
    }
    /**
     * 用户点击URL，修改密码生效
     * 用户点击URL需要与入库的token进行比较，false则进入验证错误页面，打印错误日志
     * @param token 唯一的UUID值
     * @return 重定向页面
     */
    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public String verify(@RequestParam(name = "token") String token,
                         @RequestParam(name = "id") int id, HttpServletRequest request,
                         RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
        Token originToken = tokenService.getTokenByUserId(id);
        Date dueTime = originToken.getDue_time();
        Date currentTime = new Date();
        String oldToken = originToken.getToken();
        tokenService.disableToken(id);
        User user=userService.getUserById(id);
        if (dueTime.before(currentTime)){
            logger.warn("token已失效");
            return "linkError";
        }
        if (!oldToken.equals(token)){
            logger.warn("传入token有异常");
            return "linkError";
        }
        redirectAttributes.addFlashAttribute("userId",id);

        //创建临时用户
        String username=user.getUsername();
        String password=UUID.randomUUID().toString();
        //if(userService.createTempUser(user)){
            Role role=roleService.findByName("ROLE_TEMP");
            List<Role> roles=new ArrayList<>();
            roles.add(role);
            UsernamePasswordAuthenticationToken tempToken = new UsernamePasswordAuthenticationToken(
                    username, password,roles);
            /*UsernamePasswordAuthenticationToken tempToken = new UsernamePasswordAuthenticationToken(
                    username, password);*/
            try{
                tempToken.setDetails(new WebAuthenticationDetails(request));
                //Authentication authenticatedUser = authenticationManager.authenticate(tempToken);
                //SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                SecurityContextHolder.getContext().setAuthentication(tempToken);
                request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            }
            catch( AuthenticationException e ){
                System.out.println("Authentication failed: " + e.getMessage());
                //return new ModelAndView(new RedirectView("register"));
            }
        //}
        //重定向到当前controller忘记密码的GET请求中
        return "redirect:/signup/temp/modifyPassword";
    }
}
