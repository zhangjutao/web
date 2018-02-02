package com.gooalgene.common.service;

import com.gooalgene.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.security.Security;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Crabime
 * 邮件发送工具类
 * 依赖子模块配置的GuavaCacheManager
 */
@Service
public class SMTPService implements InitializingBean{
    private final static Logger logger = LoggerFactory.getLogger(SMTPService.class);
    @Autowired
    private CacheManager guavaCacheManager;
    private Cache cache;

    @Autowired
    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (context.getParent() != null) {
            cache = guavaCacheManager.getCache("config");
        }
    }

    /**
     * 使用腾讯企业邮箱发送邮件
     * @param from 邮件发送者邮箱
     * @param receivers 邮件接收者邮箱，接收者必须多于一个
     * @param subject 邮件主题
     * @param sendMessage 邮件正文
     * @param debug 是否使用debug模式
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @decription 由于生产环境布在阿里云上，阿里云封禁了25端口，故本方法采用ssl协议，指定使用465端口发送邮件
     */
    public void send(String from, List<String> receivers, String subject, String sendMessage, boolean debug) throws MessagingException, UnsupportedEncodingException {
        Assert.isTrue(receivers != null && receivers.size() > 0, "邮件接收者为空");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Address[] addresses = new InternetAddress[receivers.size()];
        Properties properties = new Properties();
        if (debug){
            properties.setProperty("mail.debug", String.valueOf(debug));
        }
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.host", "smtp.exmail.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        if (StringUtils.isEmpty(subject)){
            logger.error("发送主题为空，邮件发送失败");
            return;
        }
        message.setSubject(subject);
        if (StringUtils.isEmpty(sendMessage)){
            logger.error("发送正文为空，邮件发送失败");
            return;
        }
        message.setText(sendMessage);
        message.setFrom(new InternetAddress(from, "古奥基因")); //这里更希望是启动时直接从数据库中拿值

        Transport transport = session.getTransport();
        String username = cache.get("mail.administrator").get().toString();
        String password = cache.get("mail.administrator.password").get().toString();
        transport.connect(username, password);
        for (int i = 0; i < receivers.size(); i++) {
            addresses[i] = new InternetAddress(receivers.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, addresses);
        transport.sendMessage(message, addresses);
        transport.close();
    }

    /**
     * @param from
     * @param receivers
     * @param subject
     * @param template
     * @param debug
     * @param filePlaceHolder
     * @throws MessagingException
     * @throws IOException
     * @decription 由于生产环境布在阿里云上，阿里云封禁了25端口，故本方法采用ssl协议，指定使用465端口发送邮件
     */
    public void send(String from, List<String> receivers, String subject, File template, boolean debug, String[] filePlaceHolder) throws MessagingException, IOException {
        Assert.isTrue(receivers != null && receivers.size() > 0, "邮件接收者为空");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Address[] addresses = new InternetAddress[receivers.size()];
        Properties properties = new Properties();
        if (debug){
            properties.setProperty("mail.debug", String.valueOf(debug));
        }
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        properties.setProperty("mail.host", "smtp.exmail.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.mime.charset", "UTF-8");

        Session session = Session.getInstance(properties);
        Message message = new MimeMessage(session);
        if (StringUtils.isEmpty(subject)){
            logger.error("发送主题为空，邮件发送失败");
            return;
        }
        message.setSubject(subject);
        if (template == null){
            logger.error("邮件模板为空");
            return;
        }
        String concreteContent = getMailContent(template, filePlaceHolder); //拿到文件具体内容
        BodyPart bodyPart = new MimeBodyPart(); //内容承载体，可以是图片
        bodyPart.setContent(concreteContent, "text/html;charset=UTF-8");
        Multipart multipart = new MimeMultipart(); //bodypart承载体
        multipart.addBodyPart(bodyPart);
        message.setContent(multipart);
        message.saveChanges();
        message.setFrom(new InternetAddress(from, "古奥基因")); //这里更希望是启动时直接从数据库中拿值

        Transport transport = session.getTransport();
        String username = cache.get("mail.administrator").get().toString();
        String password = cache.get("mail.administrator.password").get().toString();
        transport.connect(username, password);
        for (int i = 0; i < receivers.size(); i++) {
            addresses[i] = new InternetAddress(receivers.get(i));
        }
        message.setRecipients(Message.RecipientType.TO, addresses);
        transport.sendMessage(message, addresses);
        transport.close();
    }

    private String getMailContent(File template, Object[] params) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(template),"UTF-8"));
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
