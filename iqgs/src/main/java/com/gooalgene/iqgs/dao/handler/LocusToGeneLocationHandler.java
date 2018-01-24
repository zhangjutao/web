package com.gooalgene.iqgs.dao.handler;

import com.gooalgene.utils.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 将数据库中gens_baseinfo表中locus字段转换为GeneLocation两个属性值
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String.class)
public class LocusToGeneLocationHandler implements TypeHandler<String> {

    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        String locus = rs.getString(columnName);
        return interpretChromosomeFromLocus(locus);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        String locus = rs.getString(columnIndex);
        return interpretChromosomeFromLocus(locus);
    }

    @Override
    public String getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String locus = cs.getString(columnIndex);
        return interpretChromosomeFromLocus(locus);
    }

    private String interpretChromosomeFromLocus(String locus){
        if (StringUtils.isNotBlank(locus)){
            int i = locus.indexOf(":");
            if (i != -1){
                locus = locus.substring(i+1, locus.length());
            }
            return locus;
        }
        return null;
    }
}
