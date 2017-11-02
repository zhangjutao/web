package com.gooalgene.common.service;

import com.gooalgene.common.authority.User;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by liuyan on 2017/11/1.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    //通过用户ID查询用户信息
    public User getUserById(int id){
        return userDao.get(id);
    }

    //查询全部用户
    public List<User> queryAll() {
        return userDao.queryAll();
    }
    //判断用户名是否存在
    public boolean exist(String username){
         System.out.println(userDao.queryAll());
         System.out.println(userDao.findByUsername(username));
         return !userDao.findByUsername(username).isEmpty();
    }
    //通过用户名查询用户
    public User findByUsername(String username){
        List<User> list = userDao.findByUsername(username);
        System.out.println(list);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }
     //向user表中插入用户
    public boolean createUser(User user){
        user.setCreateTime(new Date());
        return userDao.insert(user);
    }

    /*得到最后插入数据的ID 即当前插入数据之后得到其ID*/
    public int findLastInsertId(){
        return userDao.findLastInsertId();
    }

    //查询所有未激活（审核）enable=0的用户
    public List<User> queryUserForCheck() {
        return userDao.findByEnable(0);
    }

    //更新用户enable的值
    public boolean enableUser(String id) {
        User user = new User();
        user.setId(id);
        user.setEnabled(1);
        return userDao.updateUserEnabled(user);
    }

    //设置密码重置标志位
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



}
