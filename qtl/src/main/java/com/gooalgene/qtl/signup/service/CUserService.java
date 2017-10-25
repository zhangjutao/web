//package com.gooalgene.qtl.signup.service;
//
//import com.gooalgene.qtl.signup.dao.CUserDao;
//import com.gooalgene.qtl.signup.entity.CUser;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.security.authentication.encoding.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//
///**
// * Created by Administrator on 2017/07/08.
// */
//@Service
//public class CUserService {
//
//    @Autowired
//    private CUserDao userDao;
//
//
//    public List<CUser> queryAll() {
//        return userDao.queryAll();
//    }
//
//    public boolean exist(String username){
//        System.out.println(userDao.queryAll());
//        System.out.println(userDao.findByUsername(username));
//        return !userDao.findByUsername(username).isEmpty();
//    }
//
//    public CUser findByUsername(String username){
//
//        List<CUser> list = userDao.findByUsername(username);
//        System.out.println(list);
//        if(list.isEmpty()){
//            return null;
//        }else{
//            return list.get(0);
//        }
//    }
//
//    public boolean createUser(CUser cUser){
//        cUser.setCreateTime(new Date());
//        return userDao.insert(cUser);
//    }
//
//    public List<CUser> queryUserForCheck() {
//        return userDao.findByEnable(0);
//    }
//
//    public boolean enableUser(String uid) {
//        CUser user = new CUser();
//        user.setId(uid);
//        user.setEnabled(1);
//        return userDao.updateUserEnabled(user);
//    }
//
//    public List<CUser> queryUserForReset() {
//        return userDao.findByReset(1);
//    }
//
//    public String resetUserPwd(String uid) throws Exception {
//        CUser user = userDao.get(uid);
//        String newPasswd = getRandomPwd();
//        sendMail(user.getEmail(), newPasswd);
//        PasswordEncoder encoder = new Md5PasswordEncoder();
//        user.setPassword(encoder.encodePassword(newPasswd, null));
//        user.setReset(0);
//        userDao.updateUserPassword(user);
//        return newPasswd;
//    }
//
//    private String getRandomPwd() {
//        String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//        Random random = new Random(System.currentTimeMillis());
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            int index = random.nextInt(code.length());
//            sb.append(code.charAt(index));
//        }
//        return sb.toString();
//    }
//
//    private void sendMail(String email, String newPasswd) {
//
//    }
//
//    public boolean applyPasswdRest(CUser user) {
//        user.setReset(1);
//        try {
//            userDao.updateUserForReset(user);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//}
