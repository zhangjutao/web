package com.gooalgene.dna.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将mybatis从数据库中查询到的0转化为空字符串
 * @author Crabime
 */
@MappedJdbcTypes(JdbcType.DECIMAL)
@MappedTypes(String.class)
public class ZeroDecimalToNullHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setDouble(i, Double.parseDouble(parameter)); //将parameter值存入数据库中
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convertDoubleToString(rs.getDouble(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convertDoubleToString(rs.getDouble(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convertDoubleToString(cs.getDouble(columnIndex));
    }

    private String convertDoubleToString(double value){
        if (value == 0.00){
            return "-";
        }else {
            return String.valueOf(value);
        }
    }
}
