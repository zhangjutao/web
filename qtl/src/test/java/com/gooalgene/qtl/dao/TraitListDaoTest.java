package com.gooalgene.qtl.dao;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.entity.TraitList;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * 跟trait_list相关接口单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class TraitListDaoTest extends TestCase {
    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonGenerator jsonGenerator = null;

    @Autowired
    private TraitListDao traitListDao;

    @Before
    public void setUp(){
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindById(String qtlId){
        int id = 16;
        TraitList traitList = traitListDao.findById(id);

    }
}
