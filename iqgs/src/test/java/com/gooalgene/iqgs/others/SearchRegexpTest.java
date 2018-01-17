package com.gooalgene.iqgs.others;

import com.gooalgene.iqgs.service.GeneRegexpService;
import com.gooalgene.utils.StringUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 模糊搜索正则匹配
 */
public class SearchRegexpTest extends TestCase {
    /**
     * 去除非字母和数字
     */
    @Test
    public void testRemoveNonNum(){
        //去除开头或结尾的特殊字符,针对中间出现的一些特殊字符(如;,\t\n等)予以保留
        String regex = "(^[^a-zA-Z0-9]*)([A-Za-z\\d\\s;,.]+)([^a-zA-Z0-9]*$)";
        String str = "  .01G0049000;01G0049000 ,01G0049000 ";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        String result = "";
        if (matcher.find()){
            result = matcher.group(2);
            System.out.println(result);
            //使用guava的split方法可以很方便去除中间的空格结果,效率更高一些,还有大保健Iterables服务
            Iterable<String> iterable = Splitter.onPattern("[\\s,;]").omitEmptyStrings().split(result);
            System.out.println(Iterables.toString(iterable));
        }
        str = "ha.01G001200";
        matcher = pattern.matcher(str);
        result = "";
        if (matcher.find()) {
            result = matcher.group(2);
            System.out.println(result);
        }
        str = ".01G001200";
        matcher = pattern.matcher(str);
        result = "";
        if (matcher.find()) {
            result = matcher.group(2);
            System.out.println(result);
        }
    }

    @Test
    public void testMatchGene(){
        String deviation = "Glyma01G00100";
        //使用多个零宽度正先行断言作用会重叠,没能达到想要的作用
        String regex = "[([0-9]*(?<=G)[0-9]*)|((?=[0-9])*G(?<=[0-9]))]";
        //前面有字母或者点好,后面为数字加G的或者前面为空,后面数字加G的格式,直接取后面数字加G部分匹配
        String regex2 = "((?<=[a-zA-Z.])[0-9Gg]+|(?<![a-zA-Z.])[0-9Gg]+$)";
        Pattern pattern = Pattern.compile(regex2);
        Matcher matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "01G00100";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "01g00100";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "ha.01G001200";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        deviation = "Glyma.01G00100";
        matcher = pattern.matcher(deviation);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testMatchCommonString(){
        String regex = "([Gglyma]+)|(0010)+";
        String str = "gm";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        str = "0010";
        matcher = compile.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        str = "01G0010";
        matcher = compile.matcher(str);
        if (matcher.find()){
            System.out.println(matcher.group());
        }
        assertTrue(GeneRegexpService.isCommonGeneIdOrName("0000"));
        assertFalse(GeneRegexpService.isCommonGeneIdOrName("ma0010"));
        assertTrue(GeneRegexpService.isCommonGeneIdOrName("ma"));
    }

    /**
     * 判断为geneName还是geneId
     */
    @Test
    public void testIsGeneIdOrName(){
        String regex = "(gly)|(ma)|(?<![a-zA-Z.])[0-9Gg]+$";
        String geneName = "ATMYB68";
        String geneId = "Glyma.01G00100";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(geneId.toLowerCase());
        while (matcher.find()){
            System.out.println(matcher.group());
        }
        matcher = pattern.matcher(geneName);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }

    @Test
    public void testMatchConsequenceType(){
        String str = "exonic;splicing_nonsynonymous SNV";
        str = "UTR5;UTR3";
        str = "upstream;downstream";
        StringBuilder resultStr = new StringBuilder();
        if (str.contains(";")){
            String[] result = str.split(";");
            for (int i = 0; i < result.length; i++){
                resultStr.append(reverseNumber(result[i]));
                if (i != result.length - 1){
                    resultStr.append(";");
                }
            }
        }else {
            resultStr.append(reverseNumber(str));
        }
        System.out.println(resultStr.toString());
    }

    @Test
    public void testReverseMatchSequenceType(){
        String str = "5'UTR;3'UTR";
        str = "Exonic;Splicing_nonsynonymous SNV";
        str = "5'UTR";
        str = "Downstream";
        StringBuilder builder = new StringBuilder();
        if (str.contains(";")){
            String[] split = str.split(";");
            for (int i = 0; i < split.length; i++){
                builder.append(resetNumber(split[i]));
                if (i != split.length - 1){
                    builder.append(";");
                }
            }
        }else {
            builder.append(resetNumber(str));
        }
        System.out.println(builder.toString());
    }

    /**
     * 数字复位
     */
    private String resetNumber(String str){
        String regex = "'{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            int start = matcher.start();
            str = str.substring(start+1) + str.substring(0, start);
        } else {
            str = StringUtils.uncapitalize(str);
        }
        return str;
    }

    private String reverseNumber(String str){
        str = StringUtils.capitalize(str);
        String regex = "[0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()){
            int start = matcher.start();
            str = str.substring(start) + "'" + str.substring(0, start);
        }
        return str;
    }

    @Test
    public void testIsGeneId(){
        String geneId = "Glyma.01G00100";
        assertTrue(GeneRegexpService.isGeneId(geneId));
        String geneName = "ATMYB68";
        assertFalse(GeneRegexpService.isGeneId(geneName));
    }

    @Test
    public void testInterpretGene(){
        String userInput = "  .01G0049000;01G0049000 ,01G0049000 ";
        List<String> list = GeneRegexpService.interpretGeneInput(userInput);
        assertEquals("01G0049000", list.get(2));
    }
}
