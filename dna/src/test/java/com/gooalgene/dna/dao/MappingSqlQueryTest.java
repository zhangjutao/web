package com.gooalgene.dna.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context-test.xml"))
public class MappingSqlQueryTest extends TestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testMapRow(){
        ConfigMapping configMapping = new ConfigMapping(dataSource, "select * from db_config");
        List execute = configMapping.execute();
        System.out.println(execute.size());
    }

    private class ConfigMapping extends MappingSqlQuery {
        protected ConfigMapping(DataSource dataSource, String queryStr){
            super(dataSource, queryStr);
            compile();
        }

        @Override
        protected Object mapRow(ResultSet rs, int rowNum) throws SQLException {
            String key = rs.getString(1);
            String value = rs.getString(2);
            return new Configuration(key, value);
        }

        private class Configuration{
            private String key;
            private String value;

            public Configuration(String key, String value) {
                this.key = key;
                this.value = value;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
