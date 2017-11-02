package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.Page;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.persistence.MyBatisDao;

import java.util.List;

/**
 * Created by liuyan on 2017/11/1.
 */
@MyBatisDao
public interface UserDao extends CrudDao<User>{

    User get(int id);
    List<User> queryAll();

    Long getCount();

    List<User> findByUsername(String username);

    List<User> queryByPage(Page<User> page);

    List<User> findByEnable(int enable);

    boolean insert(User user);
    /*得到当前插入数据的id*/
    int findLastInsertId();

    User findByUserName(String username);

    boolean updateUserEnabled(User user);

    List<User> findByReset(int reset);

    boolean updateUserPassword(User user);

    boolean updateUserForReset(User user);

    /*此处新加了设置用户角色的接口*/
    boolean setRole(User_Role user_role);
}
