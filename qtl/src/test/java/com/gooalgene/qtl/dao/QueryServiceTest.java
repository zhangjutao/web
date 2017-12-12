package com.gooalgene.qtl.dao;

import com.gooalgene.qtl.service.QueryService;
import junit.framework.TestCase;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 查询所有qtl大类型
 * @author crabime
 * @since 12/12/2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class QueryServiceTest extends TestCase {

    @Autowired
    private QueryService queryService;

    @Test
    public void testQueryAllTrait(){
        JSONArray traitJsonArray = queryService.queryAll();
        int resultSize = traitJsonArray.size();
        assertEquals(15, resultSize);
    }
}
