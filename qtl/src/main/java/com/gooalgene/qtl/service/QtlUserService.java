package com.gooalgene.qtl.service;

import com.gooalgene.common.Page;
import com.gooalgene.qtl.dao.ChrlgDao;
import com.gooalgene.qtl.dao.QUserDao;
import com.gooalgene.entity.Chrlg;
import com.gooalgene.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/07/08.
 */
@Service
public class QtlUserService {

    @Autowired
    private QUserDao userDao;

    @Autowired
    private ChrlgDao chrlgDao;

    public boolean addUsers() {
        User user = null;
        for (int i = 0; i < 100; i++) {
            user = new User();
            user.setUsername("shiyun" + i);
            user.setPassword("12345_" + i);
            userDao.insert(user);
        }
        return true;
    }

    public List<User> queryAll() {
        return userDao.queryAll();
    }

    public Page<User> query(User user, Page<User> page) {
        user.setPage(page);
        List<User> list = userDao.findList(user);
        System.out.println(list.size());
        page.setList(list);
        return page;
    }

    public List<Chrlg> queryChrlgs() {
        Chrlg chrlg = new Chrlg();
        List<Chrlg> chrlgs = chrlgDao.findList(chrlg);
        for (Chrlg chrlg1:chrlgs){
            System.out.println(chrlg1.toString());
        }
        return chrlgs;
    }

    public List<User> findByUsername(String username){
        return userDao.findByUsername(username);
    }

    public List<User> findByUsernameAndPassword(User user){
        return userDao.findByUsernameAndPassword(user);
    }
}
