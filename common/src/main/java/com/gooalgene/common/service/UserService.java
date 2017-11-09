package com.gooalgene.common.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.dao.UserDao;
import com.gooalgene.entity.AliMessage;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyan on 2017/11/1.
 */
@Service
public class UserService implements ApplicationContextAware {

    private static final Logger logger= LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDao userDao;

    private ApplicationContext applicationContext;
    @Autowired
    private GuavaCacheManager cacheManager;

    //通过用户ID查询用户信息
    public User getUserById(int id){
        return userDao.get(id);
    }

    //查询全部用户
    public List<User> queryAll() {
        return userDao.queryAll();
    }
    //查询全部用户分页
    public PageInfo<User> queryByPage(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> list=userDao.queryAll();
        return new PageInfo<User>(list);
    }
    //判断用户名是否存在
    public boolean exist(String username){
        return !userDao.findByUsername(username).isEmpty();
    }
    //通过用户名查询用户
    public User findByUsername(String username){
        List<User> list = userDao.findByUsername(username);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
     //向user表中插入用户
    public boolean createUser(User user){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        logger.debug("添加之前的时间:", date);
        user.setCreate_time(date);
        logger.debug("添加之后的时间:",date);
        calendar.add(Calendar.MONTH, 2);
        Date due_date=calendar.getTime();
        logger.debug("due_time:",due_date);
        user.setDue_time(due_date);
        Boolean flag=userDao.insert(user);
        if(flag){
            successPublish(user);
        }
        return flag;
    }
    //插入临时用户
    public boolean createTempUser(User user){
        Date date=new Date();
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        logger.debug("添加之前的时间:", date);
        user.setCreate_time(date);
        logger.debug("添加之后的时间:",date);
        calendar.add(Calendar.HOUR, 2);
        Date due_date=calendar.getTime();
        logger.debug("due_time:",due_date);
        user.setDue_time(due_date);
        Boolean flag=userDao.insertTemp(user);
        if(flag){
            successPublish(user);
        }
        return flag;
    }

    /*得到最后插入数据的ID 即当前插入数据之后得到其ID*/
    public int findLastInsertId(){
        return userDao.findLastInsertId();
    }

    //查询所有未激活（审核）enable=0的用户
    public List<User> queryUserForCheck() {
        return userDao.findByEnable(0);
    }

    public Boolean deleteUser(Integer id){
        if(userDao.deleteUser(id)>0){
            return true;
        }
        return false;
    }

    //更新用户enable的值
    public boolean enableUser(int id) {
        User user = new User();
        user.setId(id);
        user.setEnabled(1);
        return userDao.updateUserEnabled(user);
    }


    public boolean applyPasswdRest(User user) {
        user.setReset(1);
        try {
            userDao.updateUserForReset(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean setRole(User_Role user_role){
         try{
             userDao.setRole(user_role);
             return true;
         }catch (Exception e){
             return false;
         }
    }

  //更新用户密码
    public boolean updateUserPassword(User user){
        return  userDao.updateUserPassword(user);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    /*注册完成时向管理员发送短信*/
    private void successPublish(User user){
        Cache cache=cacheManager.getCache("config");
        AliMessage aliMessage=new AliMessage();
        aliMessage.setDev(cache.get("isDev").get().toString().equals("1")?true:false);
        aliMessage.setManagerPhone(cache.get("admin.phone").get().toString());
        Map map= Maps.newHashMap();
        map.put("customer",user.getUsername());//
        aliMessage.setTemplateParam(map);
        applicationContext.publishEvent(aliMessage);
    }

    /*更新用户的登录次数*/
    public boolean updateUserLoginCount(User user){
        return  userDao.updateUserLoginCount(user);
    }
}
