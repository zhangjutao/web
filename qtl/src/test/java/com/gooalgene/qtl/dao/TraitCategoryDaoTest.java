package com.gooalgene.qtl.dao;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

/**
 * 跟trait category相关接口单元测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class TraitCategoryDaoTest extends TestCase {

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonGenerator jsonGenerator = null;

    @Autowired
    private TraitCategoryDao traitCategoryDao;

    @Before
    public void setUp(){
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试mybatis一对多以及查询所有trait category下所有trait list
     */
    @Test
    public void testFindAllTraitCategoryAndItsTraitList() throws JsonProcessingException {
        List<TraitCategoryWithinMultipleTraitList> allTraitCategoryAndItsTraitLists = traitCategoryDao.findAllTraitCategoryAndItsTraitList();
        assertNotNull(allTraitCategoryAndItsTraitLists);
        TraitCategoryWithinMultipleTraitList firstTraitCategory = allTraitCategoryAndItsTraitLists.get(0);
        assertEquals("真菌抗性", firstTraitCategory.getQtlDesc());
        String result = objectMapper.writeValueAsString(firstTraitCategory);
        System.out.println(result);
    }
}
