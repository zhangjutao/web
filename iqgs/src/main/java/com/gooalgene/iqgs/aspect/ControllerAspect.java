package com.gooalgene.iqgs.aspect;

import com.gooalgene.utils.ResultUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * Created by crabime on 2/7/18.
 * 对整合数据库中Controller接口返回的任何异常导致前台500错误,统一返回
 * 采用AOP机制
 */
//@Aspect
//@Controller
public class ControllerAspect {
    private final static Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

    //@Around("execution(* com.gooalgene.iqgs.web.*.*(..)) && @annotation(org.springframework.web.bind.annotation.ResponseBody)")
    /*public Object catchException(ProceedingJoinPoint call){
        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();
        Object[] args = call.getArgs();
        Object result = null;
        try {
            result = call.proceed(args);  //Controller接口返回值
        } catch (Throwable throwable) {
            logger.error("执行" + method.getName() + "出错!", throwable.getCause());
            result = ResultUtil.error(500, "服务器出错");
        }
        return result;
    }*/
}
