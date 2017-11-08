package com.gooalgene.common.service;

import com.gooalgene.common.authority.Role;
import com.gooalgene.common.dao.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role findByName(String name){
        return roleDao.findByName(name);
    }
}
