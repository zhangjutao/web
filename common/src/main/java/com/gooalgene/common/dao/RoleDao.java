package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.persistence.MyBatisDao;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@MyBatisDao
public interface RoleDao  extends CrudDao<Role> {

    @Select("SELECT r.name from role r,user u,user_role ur where r.id=ur.role_id and u.id=ur.user_id and u.id=#{id}")
    List<Role> getByUserId(Integer id);
}
