package com.gooalgene.qtl.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.persistence.MyBatisDao;
import com.gooalgene.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2017/07/08.
 */
@MyBatisDao
public interface QUserDao extends CrudDao<User> {

    List<User> queryAll();

    boolean addUser(User user);

    boolean delUserById(int id);

    List<User> findByUsername(String username);

    List<User> findByUsernameAndPassword(User user);
}
