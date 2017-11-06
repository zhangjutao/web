package com.gooalgene.common.service;

import com.gooalgene.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Crabime
 * �ʼ����͹�����
 * ������ģ�����õ�GuavaCacheManager
 */
public class SMTPService {
    private final static Logger logger = LoggerFactory.getLogger(SMTPService.class);
    @Autowired
    private GuavaCacheManager guavaCacheManager;
    private Cache cache;

    public void init(){
        cache = guavaCacheManager.getCache("config");
    }
    /**
     * ʹ����Ѷ��ҵ���䷢���ʼ�
     * @param from �ʼ�����������
     * @param receivers �ʼ����������䣬�����߱������һ��
     * @param subject �ʼ�����
     * @param sendMessage �ʼ�����
     * @param debug �Ƿ�ʹ��debugģʽ
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void send(String from, List<String> receivers, String subject, String sendMessage, boolean debug) throws MessagingException, UnsupportedEncodingException {
        Assert.isTrue(receivers != null && receivers.size() > 0, "�ʼ�������Ϊ��");
        Address[] addresses = new InternetAddress[receivers.size()];
        Properties properties = new Properties();
        if (debug){
            properties.setProperty("mail.debug", String.valueOf(debug));
        }
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.host", "smtp.exmail.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        if (StringUtils.isEmpty(subject)){
            logger.error("��������Ϊ�գ��ʼ�����ʧ��");
            return;
        }
        message.setSubject(subject);
        if (StringUtils.isEmpty(sendMessage)){
            logger.error("��������Ϊ�գ��ʼ�����ʧ��");
            return;
        }
        message.setText(sendMessage);
        message.setFrom(new InternetAddress(from, "������")); //�����ϣ��������ʱֱ�Ӵ����ݿ�����ֵ
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("crabime@gmail.com"));

        Transport transport = session.getTransport();
        String username = cache.get("mail.administrator").get().toString();
        String password = cache.get("mail.administrator.password").get().toString();
        transport.connect(username, password);
        for (int i = 0; i < receivers.size(); i++) {
            addresses[i] = new InternetAddress(receivers.get(i));
        }
        transport.sendMessage(message, addresses);
        transport.close();
    }



    public void send(String from, List<String> receivers, String subject, File template, boolean debug, String[] filePlaceHolder) throws MessagingException, IOException {
        Assert.isTrue(receivers != null && receivers.size() > 0, "�ʼ�������Ϊ��");
        Address[] addresses = new InternetAddress[receivers.size()];
        Properties properties = new Properties();
        if (debug){
            properties.setProperty("mail.debug", String.valueOf(debug));
        }
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.host", "smtp.exmail.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        if (StringUtils.isEmpty(subject)){
            logger.error("��������Ϊ�գ��ʼ�����ʧ��");
            return;
        }
        message.setSubject(subject);
        if (template == null){
            logger.error("�ʼ�ģ��Ϊ��");
            return;
        }
        String concreteContent = getMailContent(template, filePlaceHolder); //�õ��ļ���������
        BodyPart bodyPart = new MimeBodyPart(); //���ݳ����壬������ͼƬ
        bodyPart.setContent(concreteContent, "text/html;charset=UTF-8");
        Multipart multipart = new MimeMultipart(); //bodypart������
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        message.saveChanges();
        message.setFrom(new InternetAddress(from, "������")); //�����ϣ��������ʱֱ�Ӵ����ݿ�����ֵ
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("crabime@gmail.com"));

        Transport transport = session.getTransport();
        String username = cache.get("mail.administrator").get().toString();
        String password = cache.get("mail.administrator.password").get().toString();
        transport.connect(username, password);
        for (int i = 0; i < receivers.size(); i++) {
            addresses[i] = new InternetAddress(receivers.get(i));
        }
        transport.sendMessage(message, addresses);
        transport.close();
    }

    private String getMailContent(File template, Object[] params) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(template), "UTF-8"));
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null){
            sb.append(line);
            sb.append("\r\n");
        }
        MessageFormat messageFormat = new MessageFormat(sb.toString());
        String result = messageFormat.format(params);
        return result;
    }
}
