package com.gooalgene.common.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gooalgene.entity.AliMessage;
import com.gooalgene.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class SendMessageService {
    private static Logger logger= LoggerFactory.getLogger(SendMessageService.class);
    @Autowired
    private CacheManager cacheManager;

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";


    @EventListener
    @Async
    public void sendMessage(AliMessage aliMessage) {
        if(aliMessage.getDev()){
            logger.info("注册成功，短信对象：{}",aliMessage);
            System.out.println("注册成功，短信对象："+aliMessage);
            return;
        }
        SendSmsResponse response = null;
        //从缓存中取参数
        Cache cache= cacheManager.getCache("config");
        try {
            //发短信
            response = sendSms(aliMessage,cache);
            logger.info("短信接口返回对象：{}",response);
            if(response!=null){
                System.out.println("短信接口返回的数据----------------");
                System.out.println("Code=" + response.getCode());
                System.out.println("Message=" + response.getMessage());
                System.out.println("RequestId=" + response.getRequestId());
                System.out.println("BizId=" + response.getBizId());

                //Thread.sleep(3000L);

                //查明细
                if(response.getCode() != null && response.getCode().equals("OK")) {
                    QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId(),cache);
                    System.out.println("短信明细查询接口返回数据----------------");
                    System.out.println("Code=" + querySendDetailsResponse.getCode());
                    System.out.println("Message=" + querySendDetailsResponse.getMessage());
                    int i = 0;
                    for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
                    {
                        System.out.println("SmsSendDetailDTO["+i+"]:");
                        System.out.println("Content=" + smsSendDetailDTO.getContent());
                        System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                        System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                        System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                        System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                        System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                        System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                        System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
                    }
                    System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
                    System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
                }else {
                    //todo 短信发送失败时的处理
                }
            }
        } catch (ClientException e) {
            //e.printStackTrace();
            logger.error("短信发送异常，{}",e.getMessage());
        }

    }

    private SendSmsResponse sendSms(AliMessage aliMessage, Cache cache) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", cache.get("alidy.appKeyId").get().toString(), cache.get("alidy.appKeySecret").get().toString());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        //request.setPhoneNumbers(aliMessage.getManagerPhone());
        request.setPhoneNumbers(aliMessage.getManagerPhone());
        //必填:短信签名-可在短信控制台中找到
        //request.setSignName("云通信");
        request.setSignName(cache.get("alidy.signName").get().toString());
        //必填:短信模板-可在短信控制台中找到
        //request.setTemplateCode("SMS_1000000");
        request.setTemplateCode(cache.get("alidy.templateCode").get().toString());
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //request.setTemplateParam("{\"username\":\"Tom\"}");
        request.setTemplateParam(JsonUtils.Bean2Json(aliMessage.getTemplateParam()));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        return sendSmsResponse;
    }

    private QuerySendDetailsResponse querySendDetails(String bizId,Cache cache) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", cache.get("alidy.appKeyId").get().toString(), cache.get("alidy.appKeySecret").get().toString());
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber("15000000000");
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }
}
