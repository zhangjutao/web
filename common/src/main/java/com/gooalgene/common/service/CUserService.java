package com.gooalgene.common.service;

import com.gooalgene.common.authority.CUser;
import com.gooalgene.common.dao.CUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Modified by Crabime on 2017/10/50.
 */
@Service
public class CUserService {

    @Autowired
    private CUserDao userDao;


    public List<CUser> queryAll() {
        return userDao.queryAll();
    }

    public boolean exist(String username){
        System.out.println(userDao.queryAll());
        System.out.println(userDao.findByUsername(username));
        return !userDao.findByUsername(username).isEmpty();
    }

    public CUser findByUsername(String username){

        List<CUser> list = userDao.findByUsername(username);
        System.out.println(list);
        if(list.isEmpty()){
            return null;
        }else{
            return list.get(0);
        }
    }

    public boolean createUser(CUser cUser){
        cUser.setCreateTime(new Date());
        return userDao.insert(cUser);
    }

    public List<CUser> queryUserForCheck() {
        return userDao.findByEnable(0);
    }

    public boolean enableUser(String uid) {
        CUser user = new CUser();
        user.setId(uid);
        user.setEnabled(1);
        return userDao.updateUserEnabled(user);
    }

    public List<CUser> queryUserForReset() {
        return userDao.findByReset(1);
    }

    public String resetUserPwd(String uid) throws Exception {
        CUser user = userDao.get(uid);
        String newPasswd = getRandomPwd();
        sendMail(user.getEmail(), newPasswd);
        PasswordEncoder encoder = new Md5PasswordEncoder();
        user.setPassword(encoder.encodePassword(newPasswd, null));
        user.setReset(0);
        userDao.updateUserPassword(user);
        return newPasswd;
    }

    private String getRandomPwd() {
        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(code.length());
            sb.append(code.charAt(index));
        }
        return sb.toString();
    }

    private void sendMail(String email, String newPasswd) {

    }

    public boolean applyPasswdRest(CUser user) {
        user.setReset(1);
        try {
            userDao.updateUserForReset(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void modifyUserPassword(String oldpwd, String password, String username) throws Exception {
        List<CUser> users = userDao.findByUsername(username);
        if (users == null || users.size() == 0) {
            throw new Exception("当前用户不存在！");
        }
        CUser u  = users.get(0);
        PasswordEncoder encoder = new Md5PasswordEncoder();
        if (!u.getPassword().equals(encoder.encodePassword(oldpwd,null))) {
            throw new Exception("原始密码不正确");
        }
        u.setPassword(encoder.encodePassword(password, null));
        userDao.updateUserPassword(u);
    }
}
