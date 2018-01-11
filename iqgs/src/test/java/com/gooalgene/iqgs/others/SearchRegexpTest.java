package com.gooalgene.iqgs.others;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * 模糊搜索正则匹配
 */
public class SearchRegexpTest extends TestCase {
    @Test
    public void testDeleteSpecialCharacter(){
        String str = ";act3 &";
        String regex = "^([^A-Za-z0-9])";
        Pattern pattern = Pattern.compile(regex);

    }
}
