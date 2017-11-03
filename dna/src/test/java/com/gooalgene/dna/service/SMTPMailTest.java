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
import java.io.*;
import java.text.MessageFormat;
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

    @Test
    public void replaceMessageFromHtml() throws IOException {
        String[] params = new String[]{"crabime", "2017", "11", "3", "15", "20", "http://www.gooalgene.com", "2017-11-3 15:31:20"};
        Resource resource = new ClassPathResource("verifyPassword.html");
        File file = resource.getFile();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null){
            sb.append(line);
            sb.append("\r\n");
        }
        MessageFormat messageFormat = new MessageFormat(sb.toString());
        String result = messageFormat.format(params);
        System.out.println(result);
    }
}
