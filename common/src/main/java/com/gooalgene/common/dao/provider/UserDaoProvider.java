package com.gooalgene.common.dao.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserDaoProvider {
    public String getByUserId(){
        return new SQL(){{
            SELECT("*");
            FROM("user");
            WHERE("id=#{userId}");
            AND();
            WHERE("username=#{username}");
        }}.toString();
    }
}
