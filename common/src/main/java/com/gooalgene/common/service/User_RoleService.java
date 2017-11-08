package com.gooalgene.common.service;

import com.gooalgene.common.authority.Role;
import com.gooalgene.common.authority.User;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.dao.UserDao;
import com.gooalgene.common.dao.User_RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class User_RoleService {
    @Autowired
    private User_RoleDao user_roleDao;

    public Integer create(User_Role user_role){
        return user_roleDao.create(user_role);
    }
}
