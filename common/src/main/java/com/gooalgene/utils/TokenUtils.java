package com.gooalgene.utils;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by liuyan on 2017/11/2.
 */
public class TokenUtils {
    /*产生128位随机的token*/
    public static String generateToken(){
        String temp2=getUuid()+getUuid();
        return Base64.encode(temp2.getBytes());
    }
    public static String getUuid(){
        UUID uuid=UUID.randomUUID();
        String temp=uuid.toString();
        StringBuilder builder=new StringBuilder();
        builder.append(temp.substring(0,8));
        builder.append(temp.substring(9,13));
        builder.append(temp.substring(14,18));
        builder.append(temp.substring(19,23));
        builder.append(temp.substring(24));
        System.out.println(builder.toString());
        return builder.toString();
    }
}
