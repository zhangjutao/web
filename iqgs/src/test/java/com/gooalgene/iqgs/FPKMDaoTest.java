package com.gooalgene.iqgs;

import com.gooalgene.iqgs.dao.FPKMDao;
import com.gooalgene.iqgs.entity.GeneFPKM;
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
public class FPKMDaoTest extends TestCase {

    @Autowired
    private FPKMDao fpkmDao;

    @Test
    public void testFindProperGeneUnderSampleRun(){
        int sampleRunId = 1;
        List<GeneFPKM> properGenes = fpkmDao.findProperGeneUnderSampleRun(sampleRunId, 5.0, 10.0);
        assertEquals(6739, properGenes.size());
    }
}
