package com.gooalgene.mrna.service;

import com.gooalgene.mrna.entity.Classifys;
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
public class ClassifyServiceTest extends TestCase {

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private TService tService;

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
        try {
            Thread.sleep(30*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
