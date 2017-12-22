package com.gooalgene.mrna.service;

import com.gooalgene.common.dao.StudyDao;
import junit.framework.TestCase;
import net.sf.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(value = {"classpath:spring-context-test.xml"}))
public class StudyServiceTest extends TestCase {
    @Autowired
    private StudyDao studyDao;

    @Autowired
    private StudyService studyService;

    @Test
    public void testFindSampleRunByTissueForClassification(){
        List<String> sampleRuns = studyDao.findSampleRunByTissueForClassification("Pod");
        assertEquals(18, sampleRuns.size());
        List<String> samples = studyDao.findSampleRunByTissueForClassification("5th trifoliate leaf");
        assertEquals(16, samples.size());

    }

    @Test
    public void testQueryStudyByGene(){
        JSONArray jsonArray = studyService.queryStudyByGene("Glyma.04G197300");
        System.out.println(jsonArray.size());
    }
}