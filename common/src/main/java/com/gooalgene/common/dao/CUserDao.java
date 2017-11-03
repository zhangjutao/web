package com.gooalgene.common.dao;


import com.gooalgene.common.CrudDao;
import com.gooalgene.common.authority.CUser;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.persistence.MyBatisDao;

import java.util.List;

/**
 * Modified by Crabime on 2017/10/25.
 */
@MyBatisDao
public interface CUserDao extends CrudDao<CUser> {

    List<CUser> queryAll();

    List<CUser> findByUsername(String username);

    List<CUser> findByEnable(int enable);

    boolean updateUserEnabled(CUser user);

    List<CUser> findByReset(int reset);

    boolean updateUserPassword(CUser user);

    boolean updateUserForReset(CUser user);


    /*此处新加了设置用户角色的接口*/
    boolean setRole(User_Role user_role);

}
