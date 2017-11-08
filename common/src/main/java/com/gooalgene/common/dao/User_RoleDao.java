package com.gooalgene.common.dao;

import com.gooalgene.common.CrudDao;
import com.gooalgene.common.authority.User_Role;
import com.gooalgene.common.persistence.MyBatisDao;
import org.apache.ibatis.annotations.Insert;

@MyBatisDao
public interface User_RoleDao extends CrudDao<User_Role> {

    @Insert("insert into user_role(user_id, role_id) values(#{user_id},#{role_id}) ")
    Integer create(User_Role user_role);

}
