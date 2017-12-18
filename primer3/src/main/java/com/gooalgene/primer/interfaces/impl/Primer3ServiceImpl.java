package com.gooalgene.primer.interfaces.impl;

import com.gooalgene.primer.bean.Primer;
import com.gooalgene.primer.interfaces.Primer3Service;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by liuyan on 2017/12/15.
 */
public class Primer3ServiceImpl implements Primer3Service {


    @Override
    public List<Primer> getPrimer(String url,String param) {
        List<Primer> primerList;
        //String params = "PRIMER_TASK=generic&PRIMER_MASK_KMERLIST_PREFIX=&PRIMER_MASK_FAILURE_RATE=0.1&PRIMER_MASK_5P_DIRECTION=1&PRIMER_MASK_3P_DIRECTION=1&PRIMER_MISPRIMING_LIBRARY=NONE&SEQUENCE_TEMPLATE=TCTCTGACTGACACCAAACAGATCGACTGCAGCTGGACATTCAAAGCTAAAAATGAGCAGAAACTACTTGTCCAACAACGACGAGCTCCGCAGGGGAGATTATCTGTTGTCCAACAACGGAGAATGGAAGGCAGTTTTCCAGAATGATGGAAACTTTGTCATCTACGGCTGGCAACCGACCTGGTCCTCTGACACCTACCAATCTGATGTGGTCCGCCTGATCATGCAGGCAGATCGCAACCTGGTGATGTACAACGACGACAACAAGCCTAGATGGCACACCGGCACCTACCTGAAGGATTCTCATGTTGGCCGTGTTCAGCTGACTGATGACGGCAAACTGTTGGTGTACAGCGCCGCCAAGTTAATCTGGAGCTCTGCCGATTCTAAAGGCATGAGGGTGTAGCATTAAAAATCTCACTAGTTTGAGTTCTCTGCATTTAAAATTACTCTTTCAAAGGATCTTCTAGTGTTAGCTTAGCTGCTTGGTCCAAAATGGTAAGAGACAACATGTCAAATCCAATGCCATGGACACCAGACGGGAG&MUST_XLATE_PRIMER_PICK_LEFT_PRIMER=1&MUST_XLATE_PRIMER_PICK_RIGHT_PRIMER=1&SEQUENCE_PRIMER=&SEQUENCE_INTERNAL_OLIGO=&SEQUENCE_PRIMER_REVCOMP=&Pick+Primers=Pick+Primers&SEQUENCE_ID=&SEQUENCE_TARGET=&SEQUENCE_OVERLAP_JUNCTION_LIST=&SEQUENCE_EXCLUDED_REGION=&SEQUENCE_PRIMER_PAIR_OK_REGION_LIST=&SEQUENCE_INCLUDED_REGION=&SEQUENCE_START_CODON_POSITION=&SEQUENCE_INTERNAL_EXCLUDED_REGION=&SEQUENCE_FORCE_LEFT_START=-1000000&SEQUENCE_FORCE_RIGHT_START=-1000000&SEQUENCE_FORCE_LEFT_END=-1000000&SEQUENCE_FORCE_RIGHT_END=-1000000&SEQUENCE_QUALITY=&PRIMER_MIN_QUALITY=0&PRIMER_MIN_END_QUALITY=0&PRIMER_QUALITY_RANGE_MIN=0&PRIMER_QUALITY_RANGE_MAX=100&Upload=&PRIMER_MIN_SIZE=18&PRIMER_OPT_SIZE=20&PRIMER_MAX_SIZE=23&PRIMER_MIN_TM=57.0&PRIMER_OPT_TM=59.0&PRIMER_MAX_TM=62.0&PRIMER_PAIR_MAX_DIFF_TM=5.0&PRIMER_TM_FORMULA=1&PRIMER_PRODUCT_MIN_TM=-1000000.0&PRIMER_PRODUCT_OPT_TM=0.0&PRIMER_PRODUCT_MAX_TM=1000000.0&PRIMER_MIN_GC=30.0&PRIMER_OPT_GC_PERCENT=50.0&PRIMER_MAX_GC=70.0&PRIMER_PRODUCT_SIZE_RANGE=150-250+100-300+301-400+401-500+501-600+601-700+701-850+851-1000&PRIMER_NUM_RETURN=5&PRIMER_MAX_END_STABILITY=9.0&PRIMER_MAX_LIBRARY_MISPRIMING=12.00&PRIMER_PAIR_MAX_LIBRARY_MISPRIMING=20.00&MUST_XLATE_PRIMER_THERMODYNAMIC_OLIGO_ALIGNMENT=1&PRIMER_MAX_SELF_ANY_TH=45.0&PRIMER_MAX_SELF_END_TH=35.0&PRIMER_PAIR_MAX_COMPL_ANY_TH=45.0&PRIMER_PAIR_MAX_COMPL_END_TH=35.0&PRIMER_MAX_HAIRPIN_TH=24.0&PRIMER_MAX_SELF_ANY=8.00&PRIMER_MAX_SELF_END=3.00&PRIMER_PAIR_MAX_COMPL_ANY=8.00&PRIMER_PAIR_MAX_COMPL_END=3.00&PRIMER_MAX_TEMPLATE_MISPRIMING_TH=40.00&PRIMER_PAIR_MAX_TEMPLATE_MISPRIMING_TH=70.00&PRIMER_MAX_TEMPLATE_MISPRIMING=12.00&PRIMER_PAIR_MAX_TEMPLATE_MISPRIMING=24.00&PRIMER_MAX_NS_ACCEPTED=0&PRIMER_MAX_POLY_X=4&PRIMER_INSIDE_PENALTY=-1.0&PRIMER_OUTSIDE_PENALTY=0&PRIMER_FIRST_BASE_INDEX=1&PRIMER_GC_CLAMP=0&PRIMER_MAX_END_GC=5&PRIMER_MIN_LEFT_THREE_PRIME_DISTANCE=3&PRIMER_MIN_RIGHT_THREE_PRIME_DISTANCE=3&PRIMER_MIN_5_PRIME_OVERLAP_OF_JUNCTION=7&PRIMER_MIN_3_PRIME_OVERLAP_OF_JUNCTION=4&PRIMER_SALT_MONOVALENT=50.0&PRIMER_SALT_CORRECTIONS=1&PRIMER_SALT_DIVALENT=1.5&PRIMER_DNTP_CONC=0.6&PRIMER_DNA_CONC=50.0&PRIMER_SEQUENCING_SPACING=500&PRIMER_SEQUENCING_INTERVAL=250&PRIMER_SEQUENCING_LEAD=50&PRIMER_SEQUENCING_ACCURACY=20&MUST_XLATE_PRIMER_LIBERAL_BASE=1&MUST_XLATE_PRIMER_EXPLAIN_FLAG=1&PRIMER_WT_SIZE_LT=1.0&PRIMER_WT_SIZE_GT=1.0&PRIMER_WT_TM_LT=1.0&PRIMER_WT_TM_GT=1.0&PRIMER_WT_GC_PERCENT_LT=0.0&PRIMER_WT_GC_PERCENT_GT=0.0&PRIMER_WT_SELF_ANY_TH=0.0&PRIMER_WT_SELF_END_TH=0.0&PRIMER_WT_HAIRPIN_TH=0.0&PRIMER_WT_TEMPLATE_MISPRIMING_TH=0.0&PRIMER_WT_SELF_ANY=0.0&PRIMER_WT_SELF_END=0.0&PRIMER_WT_TEMPLATE_MISPRIMING=0.0&PRIMER_WT_NUM_NS=0.0&PRIMER_WT_LIBRARY_MISPRIMING=0.0&PRIMER_WT_SEQ_QUAL=0.0&PRIMER_WT_END_QUAL=0.0&PRIMER_WT_POS_PENALTY=0.0&PRIMER_WT_END_STABILITY=0.0&PRIMER_WT_MASK_FAILURE_RATE=0.0&PRIMER_PAIR_WT_PRODUCT_SIZE_LT=0.0&PRIMER_PAIR_WT_PRODUCT_SIZE_GT=0.0&PRIMER_PAIR_WT_PRODUCT_TM_LT=0.0&PRIMER_PAIR_WT_PRODUCT_TM_GT=0.0&PRIMER_PAIR_WT_COMPL_ANY_TH=0.0&PRIMER_PAIR_WT_COMPL_END_TH=0.0&PRIMER_PAIR_WT_TEMPLATE_MISPRIMING_TH=0.0&PRIMER_PAIR_WT_COMPL_ANY=0.0&PRIMER_PAIR_WT_COMPL_END=0.0&PRIMER_PAIR_WT_TEMPLATE_MISPRIMING=0.0&PRIMER_PAIR_WT_DIFF_TM=0.0&PRIMER_PAIR_WT_LIBRARY_MISPRIMING=0.0&PRIMER_PAIR_WT_PR_PENALTY=1.0&PRIMER_PAIR_WT_IO_PENALTY=0.0&PRIMER_INTERNAL_MIN_SIZE=18&PRIMER_INTERNAL_OPT_SIZE=20&PRIMER_INTERNAL_MAX_SIZE=27&PRIMER_INTERNAL_MIN_TM=57.0&PRIMER_INTERNAL_OPT_TM=60.0&PRIMER_INTERNAL_MAX_TM=63.0&PRIMER_INTERNAL_MIN_GC=20.0&PRIMER_INTERNAL_OPT_GC_PERCENT=50.0&PRIMER_INTERNAL_MAX_GC=80.0&PRIMER_INTERNAL_MAX_SELF_ANY_TH=47.00&PRIMER_INTERNAL_MAX_SELF_END_TH=47.00&PRIMER_INTERNAL_MAX_HAIRPIN_TH=47.00&PRIMER_INTERNAL_MAX_SELF_ANY=12.00&PRIMER_INTERNAL_MAX_SELF_END=12.00&PRIMER_INTERNAL_MIN_QUALITY=0&PRIMER_INTERNAL_MAX_NS_ACCEPTED=0&PRIMER_INTERNAL_MAX_POLY_X=5&PRIMER_INTERNAL_MISHYB_LIBRARY=NONE&PRIMER_INTERNAL_MAX_LIBRARY_MISHYB=12.00&PRIMER_INTERNAL_SALT_MONOVALENT=50.0&PRIMER_INTERNAL_DNA_CONC=50.0&PRIMER_INTERNAL_SALT_DIVALENT=1.5&PRIMER_INTERNAL_DNTP_CONC=0.0&PRIMER_INTERNAL_WT_SIZE_LT=1.0&PRIMER_INTERNAL_WT_SIZE_GT=1.0&PRIMER_INTERNAL_WT_TM_LT=1.0&PRIMER_INTERNAL_WT_TM_GT=1.0&PRIMER_INTERNAL_WT_GC_PERCENT_LT=0.0&PRIMER_INTERNAL_WT_GC_PERCENT_GT=0.0&PRIMER_INTERNAL_WT_SELF_ANY_TH=0.0&PRIMER_INTERNAL_WT_SELF_END_TH=0.0&PRIMER_INTERNAL_WT_HAIRPIN_TH=0.0&PRIMER_INTERNAL_WT_SELF_ANY=0.0&PRIMER_INTERNAL_WT_SELF_END=0.0&PRIMER_INTERNAL_WT_NUM_NS=0.0&PRIMER_INTERNAL_WT_LIBRARY_MISHYB=0.0&PRIMER_INTERNAL_WT_SEQ_QUAL=0.0&PRIMER_INTERNAL_WT_END_QUAL=0.0";
        // String content = sendPost("http://192.168.14.128/cgi-bin/primer3web_results.cgi?", params);
        String content=sendPost(url,param);
        Document document = Jsoup.parse(content);
        Elements pre = document.getElementsByTag("pre");
        String element = pre.text();

        //从返回的页面中提取出每一组表格  每一组表格作为一个字符串存放到列表中  等待下一步处理
        List<String> dataList = new ArrayList<String>();
        int startPosition1 = element.indexOf("LEFT");
        int endPosition1 = element.indexOf("SEQUENCE SIZE");
        String table1 = element.substring(startPosition1, endPosition1);
        dataList.add(table1);
        int additonalPosition = element.indexOf("ADDITIONAL OLIGOS");
        element = element.substring(additonalPosition, element.length());
        while (true) {
            int startPosition = element.indexOf("LEFT");
            int endPosition = element.indexOf("PRODUCT SIZE");
            if (startPosition == -1 || endPosition == -1) {
                break;
            } else {
                String table = element.substring(startPosition, endPosition);
                dataList.add(table);
                element = element.substring(endPosition + 10, element.length());
            }
        }
        //对提取到的每一组表格进行下一步处理   提取出每一条数据  并封装成对象
        primerList= parseData(dataList);
        if(primerList==null||primerList.size()==0){
            primerList=new ArrayList<Primer>();
        }
        return primerList;
    }

    ;

    /*
* @param List<String> originData    提取出来的待处理的表格
* @return List<Primer> parsedData   格式化之后的数据
* */
    public static List<Primer> parseData(List<String> originData) {
        List<Primer> primerList = new ArrayList<Primer>();
        for (int i = 0; i < originData.size(); i++) {
            String tempStr = originData.get(i);
            if (tempStr != null && tempStr.length() > 0) {
                int slicePosition = tempStr.indexOf("RIGHT");
                if (slicePosition != -1) {
                    String primerF = tempStr.substring(0, slicePosition);
                    primerList.add(parseDataToPrimer(primerF, i));
                    String primerR = tempStr.substring(slicePosition, tempStr.length());
                    primerList.add(parseDataToPrimer(primerR, i));
                    /* System.out.println("primerF:"+primerF);
                     System.out.println("primerR:"+primerR);*/
                }
            }
        }
        return primerList;
    }

    /*
* @param String strData 前一步处理得到的每一条primer的字符串
* @param int index  确定是第几组的数据
* @return Primer primer 经过格式化处理  封装成primer对象
*
* */
    public static Primer parseDataToPrimer(String strData, int index) {
        Primer primer = new Primer();
        primer.setGroup(index);
        if (!strData.equals("")) {
            if (strData.contains("LEFT")) {
                primer.setType("primerF");
            } else {
                primer.setType("primerR");
            }
            String splitRex = "\\s+";
            String numberRex = "\\s+(\\d)+\\s+";
            String seqRex = "\\s[ACTG]+[ACTG]$";
            Pattern pattern = Pattern.compile(splitRex);
            String[] str = pattern.split(strData);
            if (str.length >= 10) {
                primer.setPosition(str[2]);
                primer.setLength(str[3]);
                primer.setTm(str[4]);
                primer.setGc(str[5]);
                primer.setAny(str[6]);
                primer.setThree(str[7]);
                primer.setHairpin((str[8]));
                primer.setSequence(str[9]);
                primer.setLink("www.baidu.com");
            }
        }
        return primer;
    }

    /*
 * @param String url  发起请求的连接地址
 * @param String param  请求参数
 * @return String  result  返回的页面
 * */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }


}
