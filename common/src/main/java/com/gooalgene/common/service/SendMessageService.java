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
import com.gooalgene.common.Global;
import com.gooalgene.entity.AliMessage;
import com.gooalgene.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class SendMessageService {
    //private static Logger logger= LoggerFactory.getLogger(SendMessageService.class);

    //String url = "http://gw.api.taobao.com/router/rest";
    private static final String url= Global.getConfig("alidy.url");
    private static final String accessKeyId= Global.getConfig("alidy.appKeyId");
    private static final String accessKeySecret= Global.getConfig("alidy.appKeySecret");
    private static final String signName= Global.getConfig("alidy.signName");
    private static final String templateCode= Global.getConfig("alidy.templateCode");

    //产品名称:云通信短信API产品,开发者无需替换
    private static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    private static final String domain = "dysmsapi.aliyuncs.com";


    public void sendMessage2(AliMessage aliMessage) {
        /*TaobaoClient client = new DefaultTaobaoClient(url,appId ,appSecret);
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend("123456");
        req.setSmsType("normal");
        req.setSmsFreeSignName("变更验证");
        //req.setSmsParamString(json);
        req.setSmsParamString(aliMessage.getContent());
        req.setRecNum(aliMessage.getManagerPhone());
        req.setSmsTemplateCode("SMS_4720619");
        try{
            AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
            logger.info(rsp.getBody());
        } catch(Exception e){
        }

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
        //替换成你的AK
        final String accessKeyId = "yourAccessKeyId";//你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "yourAccessKeySecret";//你的accessKeySecret，参考本文档步骤2
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
                accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers("1500000000");
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("云通信");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_1000000");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"123\"}");
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");
        //请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
        }*/
    }

    @EventListener
    @Async
    public void sendMessage(AliMessage aliMessage) {
        SendSmsResponse response = null;
        try {
            //发短信
            response = sendSms(aliMessage);
            log.info("短信接口返回对象：{}",response);
            if(response!=null){
                System.out.println("短信接口返回的数据----------------");
                System.out.println("Code=" + response.getCode());
                System.out.println("Message=" + response.getMessage());
                System.out.println("RequestId=" + response.getRequestId());
                System.out.println("BizId=" + response.getBizId());

                //Thread.sleep(3000L);

                //查明细
                if(response.getCode() != null && response.getCode().equals("OK")) {
                    QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId());
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
            log.error("短信发送异常，{}",e.getMessage());
        }

    }

    private SendSmsResponse sendSms(AliMessage aliMessage) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        //request.setPhoneNumbers(aliMessage.getManagerPhone());
        request.setPhoneNumbers("18171416480");
        //必填:短信签名-可在短信控制台中找到
        //request.setSignName("云通信");
        request.setSignName(signName);
        //必填:短信模板-可在短信控制台中找到
        //request.setTemplateCode("SMS_1000000");
        request.setTemplateCode(templateCode);
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

    private QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
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
