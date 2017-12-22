package com.gooalgene.iqgs;

import com.gooalgene.iqgs.service.SearchService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class SearchServiceTest extends TestCase {

    @Autowired
    private SearchService searchService;

    @Test
    public void testFindAllDistinctSNP(){
        List<String> allDistinctSNP = searchService.findAllDistinctSNP();
        assertEquals(15, allDistinctSNP.size());
    }
}
