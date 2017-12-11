package com.gooalgene.qtl.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * qtl查询相关单元测试代码
 * @author crabime
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context.xml"}))
public class QtlDaoTest extends TestCase {
    @Autowired
    private QtlDao qtlDao;

    @Test
    public void testFindAllQtlList(){
        assertNotNull(qtlDao);
    }
}
