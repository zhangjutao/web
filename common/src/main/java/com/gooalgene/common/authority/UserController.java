package com.gooalgene.common.authority;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.constant.ResultEnum;
import com.gooalgene.common.service.SMTPService;
import com.gooalgene.common.service.UserService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.utils.ResultUtil;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class UserController {

    private final static  Logger logger= org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @Autowired
    private SMTPService smtpService;

    @Autowired
    private GuavaCacheManager guavaCacheManager;

    private  Cache author_cache;


    @RequestMapping(value = "/users",method = RequestMethod.GET)
    public ResultVO findAll(@RequestParam(value = "pageNum",defaultValue = "0",required = false) Integer pageNum,
                                  @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize){
        PageInfo<User> users=userService.queryByPage(pageNum,pageSize);
        return ResultUtil.success(users);
    }

    @RequestMapping(value = "/change/enable",method = RequestMethod.POST)
    public ResultVO changeEnable(@RequestParam String id) throws IOException, MessagingException {
        if(userService.enableUser(Integer.valueOf(id))){
            int userid=Integer.parseInt(id);

            User user=userService.getUserById(userid);  //传入的用户信息

            Resource resource=new ClassPathResource("notification.html");
            File file=resource.getFile();
            List<String> receivers=new ArrayList<String>();
            receivers.add(user.getEmail());

            //需要传入模板的参数
            Calendar calendar=Calendar.getInstance();
            Date registerTime=user.getCreate_time();
            calendar.setTime(registerTime);
            String[] args=new String[6];
            args[0]=user.getUsername();
            args[1]=String.valueOf(calendar.get(Calendar.YEAR));
            args[2]=String.valueOf(calendar.get(Calendar.MONTH));
            args[3]=String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
            args[4]=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
            args[5]=String.valueOf(calendar.get(Calendar.MINUTE));

            author_cache=guavaCacheManager.getCache("config");
            String adminEmail=author_cache.get("mail.administrator").get().toString();
            smtpService.send(adminEmail,receivers,"账号审核状态通知",file,true,args);

            return ResultUtil.success();
        }
        return ResultUtil.error(ResultEnum.ENABLE_FAILED);
    }
}
