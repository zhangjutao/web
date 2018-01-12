package com.gooalgene.iqgs.others;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模糊搜索正则匹配
 */
public class SearchRegexpTest extends TestCase {
    @Test
    public void testDeleteSpecialCharacter(){
        String str = ";act3 &";
        String regex = "(^([^A-Za-z0-9])([a-zA-Z0-9.])*([^A-Za-z0-9])$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()){
            String result = matcher.group();
            System.out.println(result);
            String lastResult = matcher.group(1);
            System.out.println(lastResult);
        }else {
            System.out.println("不支持的类型");
        }
    }
}
