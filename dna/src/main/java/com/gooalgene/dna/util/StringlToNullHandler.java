package com.gooalgene.dna.util;

import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将mybatis从数据库中查询到的0转化为空字符串
 * @author Crabime
 */
public class StringlToNullHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        return (rs.getString(columnName) == null)? "-" : rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        return (rs.getString(columnIndex) == null)? "-" : rs.getString(columnIndex);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (cs.getString(columnIndex) == null)? "-" : cs.getString(columnIndex);
    }
}