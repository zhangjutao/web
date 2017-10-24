package com.gooalgene.common.security;

import org.springframework.jdbc.object.MappingSqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将用户与资源文件之间的关系存入数据库中
 * 这里查找资源映射，这里使用 MappingSqlQuery只要是为了复用JDBC查询
 */
class ResourceMapping extends MappingSqlQuery {

    protected ResourceMapping(DataSource source, String resourceQuery){
        super(source, resourceQuery);
        compile();
    }

    /**
     * 获取Resource对象
     */
    protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        String url = rs.getString(1);
        String role = rs.getString(2);
        RoleUrlResource resource = new RoleUrlResource(url, role);
        return resource;
    }
}
