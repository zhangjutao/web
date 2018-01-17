package com.gooalgene.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库中SNP、INDEL界面与数据库值之间转换器
 */
public class ConsequenceTypeUtils {

    /**
     * 将数据库中consequence type值转换为前端页面需要的值
     */
    public static String convertDBToFrontStyle(String str){
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
        return resultStr.toString();
    }

    /**
     * 将前端发送过来的SNP、INDEL转换为数据库可识别的值
     */
    public static String reverseFrontStyleToDB(String str){
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
        return builder.toString();
    }

    /**
     * 将集合中数据库原始值转换为一个前端可识别的值集合
     * @param listValue 数据库中原始的序列类型集合
     */
    public static List<String> convertReadableListValue(List<String> listValue){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listValue.size(); i++){
            list.add(convertDBToFrontStyle(listValue.get(i)));
        }
        return list;
    }

    /**
     * 将前台传过来的SNP、INDEL集合转换为数据库可识别的集合
     * @param listValue 前端高级搜索传过来的SNP、INDEL集合
     */
    public static List<String> reverseReadableListValue(List<String> listValue){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < listValue.size(); i++){
            list.add(reverseFrontStyleToDB(listValue.get(i)));
        }
        return list;
    }

    /**
     * 将包含数字的序列类型转换为上撇号显示，如UTR5转换为5'UTR
     * 没有包含数字的字符串首个字母大写
     */
    private static String reverseNumber(String str){
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

    /**
     * 数字复位,如5'UTR转换为UTR5
     * 不包含上撇号的字符串首个字母小写
     */
    private static String resetNumber(String str){
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
}
