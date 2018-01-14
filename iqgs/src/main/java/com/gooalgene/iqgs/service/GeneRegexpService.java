package com.gooalgene.iqgs.service;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by crabime on 1/14/18.
 * 基因正则匹配服务类
 */
public class GeneRegexpService {

    private final static Logger logger = LoggerFactory.getLogger(GeneRegexpService.class);

    /**
     * 判断是否为基因ID
     * @param candidate 输入候选者,如果为geneId则返回true,如果输入geneName,肯定返回false
     */
    public static boolean isGeneId(String candidate){
        String regex = "(gly)|(ma)|(?<![a-zA-Z.])[0-9g]+$";
        candidate = candidate.toLowerCase();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(candidate);
        return matcher.find();
    }

    /**
     * 解析用户输入,拆分为逐个的基因
     * @param userInput 用户在searchByQtlName/ID中的输入
     * @return 拆分后的基因集合
     */
    public static List<String> interpretGeneInput(String userInput){
        //去除开头或结尾的特殊字符,针对中间出现的一些特殊字符(如;,\t\n等)予以保留
        String regex = "(^[^a-zA-Z0-9]*)([A-Za-z\\d\\s;,.]+)([^a-zA-Z0-9]*$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(userInput);
        String result = "";
        if (matcher.find()){
            result = matcher.group(2);
            //使用guava的split方法可以很方便去除中间的空格结果,效率更高一些,还有大保健Iterables服务
            Iterable<String> iterable = Splitter.onPattern("[\\s,;]").omitEmptyStrings().split(result);
            Iterator<String> iterator = iterable.iterator();
            List<String> resultList = new ArrayList<>();
            while (iterator.hasNext()){
                String nextGeneId = iterator.next();
                resultList.add(interpretSingleGeneId(nextGeneId));
            }
            resultList.removeAll(Arrays.asList("", null));  //去除结果集合中的空值和null值
            logger.debug("输入基因解析结果为:" + Iterables.toString(resultList));
            return resultList;
        }
        logger.warn(userInput + "无匹配结果");
        return null;
    }

    /**
     * 解析当个基因ID
     * @param geneId 基因ID
     * @return 解析后更容易被数据库所识别的格式
     */
    private static String interpretSingleGeneId(String geneId){
        String result = "";
        //前面有字母或者点好,后面为数字加G的或者前面为空,后面数字加G的格式,直接取后面数字加G部分匹配
        String regex = "((?<=[a-zA-Z.])[0-9Gg]+|(?<![a-zA-Z.])[0-9Gg]+$)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(geneId);
        if (matcher.find()){
            result = matcher.group();
        }
        return result;
    }
}
