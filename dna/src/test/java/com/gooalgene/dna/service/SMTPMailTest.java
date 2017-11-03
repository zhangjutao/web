package com.gooalgene.dna.service;

import com.gooalgene.common.service.SMTPService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy(@ContextConfiguration(locations = "classpath:spring-context.xml"))
public class SMTPMailTest extends TestCase {

    @Autowired
    private SMTPService smtpService;

    @Test
    public void testSMTP(){
        org.springframework.util.Assert.isTrue(false, "异常");
    }

    @Test
    public void testSMTPTemplate() throws IOException, MessagingException {
        Resource resource = new ClassPathResource("template.html");
        File file = resource.getFile();
        List<String> list = new ArrayList<>();
        list.add("crabime@gmail.com");
        smtpService.send("songsx@gooalgene.com", list, "使用文件模板发送邮件", file, true);
    }
}
