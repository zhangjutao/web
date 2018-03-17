package com.gooalgene.dna.service;

import com.gooalgene.common.service.ConfigService;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.entity.Configuration;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration("classpath:spring-context.xml"))
public class ConfigServiceTest extends TestCase {
    @Autowired
    private ConfigService configService;
    @Autowired
    private DNAGensService dnaGensService;
    @Test
    public void testConfigService(){
        List<Configuration> list = configService.getAllConfig();
        assertEquals(2, list.size());
        assertEquals("songsx@gooalgene.com", list.get(0).getValue());
        assertEquals("Ssx123456", list.get(1).getValue());
    }

    @Test
    public void testFindValueByKey() {
        Configuration configuration = configService.findValueByKey("mail.administrator");
        assertEquals("songsx@gooalgene.com", configuration.getValue());
    }

    @Test
    public void testFindKeyByValue() {
        Configuration configuration = configService.findKeyByValue("songsx@gooalgene.com");
        assertEquals("mail.administrator", configuration.getKey());
    }
}
