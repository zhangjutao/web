package com.gooalgene.mrna.service;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gooalgene.mrna.entity.Classifys;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class ClassifyServiceTest extends TestCase {

    private ObjectMapper objectMapper = new ObjectMapper();

    private JsonGenerator jsonGenerator = null;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private TService tService;

    @Before
    public void setUp(){
        try {
            jsonGenerator = objectMapper.getFactory().createGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQuery(){
        assertNotNull(classifyService);
        classifyService.query();
    }

    /**
     * 测试获取所有大组织与小组织树
     */
    @Test
    public void testClassifyTree(){
        List<Classifys> classifyTree = tService.getClassifyTree();
        assertEquals(8, classifyTree.size());
        String chineseName = classifyTree.get(0).getChinese();
        assertEquals("豆荚", chineseName);
        try {
            jsonGenerator.writeObject(classifyTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试指定一个SampleRun，查询所有关联基因
     */
    @Test
    public void testFindAllAssociateGeneThroughSampleRun(){
        String sampleRunName = "SRR037383";
        List<String> allAssociateGene = null;
        try {
            allAssociateGene = classifyService.findAllAssociateGeneThroughSampleRun(sampleRunName);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(allAssociateGene.get(0));
    }
}
