package com.gooalgene.utils;

import com.gooalgene.entity.Study;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 一些公共方法
 * Created by ShiYun on 2017/8/11 0011.
 */
public class Tools {

    static Logger logger = LoggerFactory.getLogger(Tools.class);

    /**
     * 模糊匹配查询条件
     *
     * @param keywords
     * @return
     */
    public static Pattern getRegex(String keywords) {
        //完全匹配
//        Pattern pattern = Pattern.compile("^王$", Pattern.CASE_INSENSITIVE);
        //右匹配
//        Pattern pattern = Pattern.compile("^.*王$", Pattern.CASE_INSENSITIVE);
        //左匹配
//        Pattern pattern = Pattern.compile("^王.*$", Pattern.CASE_INSENSITIVE);
        //模糊匹配
//        Pattern pattern = Pattern.compile("^.*王.*$", Pattern.CASE_INSENSITIVE);
        Pattern pattern = Pattern.compile("^.*" + keywords + ".*$", Pattern.CASE_INSENSITIVE);
        return pattern;
    }

    public static boolean isNumeric(String adjustedPValue) {
        try {
            Double.parseDouble(adjustedPValue);
            return true;
        } catch (NumberFormatException e) {
            logger.error("异常：\"" + adjustedPValue + "\"不是数字...");
            return false;
        }
    }

    public static String changeDiff2SampleName(Map<String, Study> allRuns, String diffName) {
//      SRR037375_vs_SRR037374
        String[] diffs = diffName.split("_");
        if (diffs.length == 3) {
            String s1 = diffs[0];
            String runName1 = allRuns.get(s1).getSampleName();
            String s2 = diffs[2];
            String runName2 = allRuns.get(s2).getSampleName();
            if (runName1 != null && runName2 != null) {
                return runName1 + "_vs_" + runName2;
            }
        }
        return diffName;
    }

    /**
     * @param logValue
     * @param json
     * @return
     */
    public static Boolean compareLog2AndQuery(Double logValue, Object json) {
//        $eq    =               "="
//        $gt   (greater than )  >
//        $gte                   >=  (equal)
//        $lt   (less than)      <
//        $lte                   <=  (equal)
        boolean flag = false;
        if (json != null) {
            JSONObject jsonObject = JSONObject.fromObject(json);
            for (Object key : jsonObject.keySet()) {
                Double va = jsonObject.getDouble((String) key);
                if (key.equals("$eq")) {
                    flag = (logValue.compareTo(va) == 0);
                } else if (key.equals("$gt")) {
                    flag = (logValue.compareTo(va) > 0);
                } else if (key.equals("$gte")) {
                    flag = (logValue.compareTo(va) >= 0);
                } else if (key.equals("$lt")) {
                    flag = (logValue.compareTo(va) < 0);
                } else if (key.equals("$lte")) {
                    flag = (logValue.compareTo(va) <= 0);
                } else {

                }
                if (!flag) {//第一个条件不符合，直接跳出
                    break;
                }
//                System.out.println(logValue + ",key:" + key + ",va:" + va + ",flag:" + flag);
            }
        }
        return flag;
    }

    /**
     * 过滤表达数据
     *
     * @param values
     * @param json
     * @return
     */
    public static String getResult(JSONArray values, Object json) {
//        System.out.println(values + "\t" + json);
        StringBuffer sb = new StringBuffer();
        int len = values.size();
        boolean flag = false;
        for (int i = 0; i < len; i++) {
            Double value = values.getDouble(i);
            if (json != null) {
                boolean flag1 = Tools.compareLog2AndQuery(value, json);
                flag = flag || flag1;
                sb.append(flag1 ? value : "");
            } else {
                flag = true;
                sb.append(value);
            }
            if (i != (len - 1)) {
                sb.append(",");
            } else {

            }
        }
//        System.out.println(flag + "\t" + sb.toString());
        if (flag) {
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * 数据导出功能
     *
     * @param fileName
     * @param content
     * @param response
     */
    public static void toDownload(String fileName, String content, HttpServletResponse response) {
        try {
            String sb = content;
            System.out.println(fileName);
            if (sb != null) {
                byte[] buffer = sb.getBytes("gbk");
                // 清空response
                response.reset();
                // 设置response的Header
                String filename = java.net.URLEncoder.encode(fileName, "UTF-8") + ".csv";
                System.out.println("f:" + filename);
                filename = filename.replace("+", "%20");//空格会被转义为+
                response.addHeader("Content-Disposition", "attachment; filename=" + filename + "; filename*=utf-8''" + filename);
                response.addHeader("Content-Length", "" + buffer.length);
                OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
                response.setContentType("application/octet-stream");
                toClient.write(buffer);
                toClient.flush();
                toClient.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取表达基因表头
     *
     * @param sraStudy
     * @return
     */
    public static String[] queryExpressions(String sraStudy) {
        Map<String, String> map = new HashMap<String, String>();
        String SRP002082 = "SRR037381,SRR037382,SRR037383,SRR037384,SRR037385,SRR037386,SRR037387";
        map.put("SRP002082", SRP002082);
        String SRP006750 = "SRR203031,SRR203034,SRR203036,SRR203038";
        map.put("SRP006750", SRP006750);
        String SRP006766 = "SRR203340,SRR203343,SRR203346";
        map.put("SRP006766", SRP006766);
        String SRP006767 = "SRR203353,SRR203354,SRR203357,SRR203359,SRR203362,SRR203363,SRR203364,SRR203365,SRR203367,SRR203369";
        map.put("SRP006767", SRP006767);
        String SRP008837 = "SRR352327,SRR352328,SRR863029,SRR863032";
        map.put("SRP008837", SRP008837);
        String SRP010036 = "SRR392836,SRR392837";
        map.put("SRP010036", SRP010036);
        String SRP011586 = "SRR446583,SRR446584";
        map.put("SRP011586", SRP011586);
        String SRP016593 = "SRR605683,SRR605686,SRR605689,SRR605694";
        map.put("SRP016593", SRP016593);
        String SRP017324 = "SRR620376,SRR620378,SRR620380,SRR620382,SRR620384,SRR620386,SRR620389";
        map.put("SRP017324", SRP017324);
        String SRP017638 = "SRR639165,SRR639167,SRR639173,SRR639176";
        map.put("SRP017638", SRP017638);
        String SRP021098 = "SRR1298602,SRR12986171c,SRR12986226c,SRR12986312c,SRR129864456c,SRR827660,SRR12986501c,SRR12986578c,SRR12986735c,SRR12986767c,SRR1298681,SRR12986846c,SRR827656,SRR8276689c,SRR827672,SRR8276756c,SRR827680,SRR827685,SRR8276912c,SRR8276956c,SRR8276978c";
        map.put("SRP021098", SRP021098);
        String SRP024277 = "SRR887342,SRR891238,SRR923884,SRR923894,SRR923897,SRR923898,SRR923900";
        map.put("SRP024277", SRP024277);
        String SRP025919 = "SRR899430,SRR899431,SRR899432,SRR899433,SRR899438,SRR899439,SRR899440,SRR899498,SRR899507,SRR899508,SRR899509,SRR899510,SRR899511,SRR899512";
        map.put("SRP025919", SRP025919);
        String SRP029897 = "SRR976388,SRR976392,SRR976393,SRR976396";
        map.put("SRP029897", SRP029897);
        String SRP034934 = "SRR1185321,SRR1185322,SRR1185323";
        map.put("SRP034934", SRP034934);
        String SRP038111 = "SRR1174205,SRR1174206,SRR1174207,SRR1174208,SRR1174209,SRR1174211,SRR1174212,SRR1174213,SRR1174214,SRR1174215,SRR1174216,SRR1174217,SRR1174218,SRR1174219,SRR1174220,SRR1174221,SRR1174222,SRR1174223,SRR1174224,SRR1174225,SRR1174226,SRR1174227,SRR1174228,SRR1174229,SRR1174230,SRR1174231,SRR1174232,SRR1174233";
        map.put("SRP038111", SRP038111);
        String SRP040660 = "SRR1206481,SRR1206482,SRR1206483,SRR1206484,SRR1206485,SRR1206486,SRR1206487,SRR1206488,SRR1206489";
        map.put("SRP040660", SRP040660);
        String SRP041709 = "SRR1272329,SRR12723367c,SRR12723401c,SRR12723478c,SRR1272353,SRR12723567c,SRR12723656c,SRR12723689c";
        map.put("SRP041709", SRP041709);
        String SRP041710 = "SRR1272372,SRR12723778c,SRR1272387,SRR12723889c,SRR12723978c,SRR12724009c,SRR12724067c,SRR1272412";
        map.put("SRP041710", SRP041710);
        String SRP041932 = "SRR12845023c,SRR12845101c,SRR1284518,SRR1284524,SRR1284530,SRR1284531,SRR12845378c,SRR1284541,SRR1284545,SRR1284550";
        map.put("SRP041932", SRP041932);
        String SRP045061 = "SRR1531418,SRR1531419";
        map.put("SRP045061", SRP045061);
        String SRP045669 = "SRR1555242,SRR1555243,SRR1555244,SRR1555245";
        map.put("SRP045669", SRP045669);
        String SRP051653 = "SRR1737745,SRR1737746,SRR1737747";
        map.put("SRP051653", SRP051653);
        String SRP059860 = "SRR2079326,SRR2079327,SRR2079328,SRR2079329";
        map.put("SRP059860", SRP059860);
        String SRP007610 = "SRR324698,SRR324699,SRR324700,SRR324701";//2017-08-14 此处SRP066454重命名为SRP007610，然后里面的SRR2937360删除
        map.put("SRP007610", SRP007610);
        String SRP073278 = "SRR3382391,SRR3382392,SRR3382393,SRR3382394,SRR3382395";
        map.put("SRP073278", SRP073278);
        String SRP074365 = "SRR3475896,SRR3475897,SRR3475898,SRR3475904,SRR3475906,SRR3475907,SRR3475914,SRR3475915,SRR3475916,SRR3475918,SRR3475923,SRR3475925";
        map.put("SRP074365", SRP074365);
        String SRP082550 = "SRR4053698,SRR4053700,SRR4053702,SRR4053705";
        map.put("SRP082550", SRP082550);
        String SRP091708 = "SRR4428969,SRR4428970,SRR4428974,SRR4428975,SRR4428981,SRR4428982,SRR4428995,SRR4428998";
        map.put("SRP091708", SRP091708);
        String SRP095280 = "SRR5117918,SRR5117919,SRR5117920,SRR5117921,SRR5117922,SRR5117925,SRR5117926,SRR5117927,SRR5117929,SRR5117932,SRR5117933,SRR5117939,SRR5117940,SRR5117941,SRR5117942";
        map.put("SRP095280", SRP095280);
        String SRP100669 = "SRR5288603,SRR5288605,SRR5288608";
        map.put("SRP100669", SRP100669);
        if (map.containsKey(sraStudy)) {
            return map.get(sraStudy).split(",");
        }
        return null;
    }

    /**
     * 导出csv文件时是以逗号进行分割的，字段内容包含逗号时易出错，此方法将有逗号的字段用引号包含
     *
     * @param content
     * @return
     */
    public static String getRightContent(String content) {
        if (content.contains(",")) {
            return "\"" + content + "\"";
        } else {
            return content;
        }
    }

    /**
     * 获取表达基因中出现的run和study对应关系
     *
     * @return
     */
    public static Map<String, String> queryExpressionsRunAndStudy() {
        Map<String, String> map = new HashMap<String, String>();
        String SRP002082 = "SRR037381,SRR037382,SRR037383,SRR037384,SRR037385,SRR037386,SRR037387";
        putData("SRP002082", SRP002082, map);
        String SRP006750 = "SRR203031,SRR203034,SRR203036,SRR203038";
        putData("SRP006750", SRP006750, map);
        String SRP006766 = "SRR203340,SRR203343,SRR203346";
        putData("SRP006766", SRP006766, map);
        String SRP006767 = "SRR203353,SRR203354,SRR203357,SRR203359,SRR203362,SRR203363,SRR203364,SRR203365,SRR203367,SRR203369";
        putData("SRP006767", SRP006767, map);
        String SRP008837 = "SRR352327,SRR352328,SRR863029,SRR863032";
        putData("SRP008837", SRP008837, map);
        String SRP010036 = "SRR392836,SRR392837";
        putData("SRP010036", SRP010036, map);
        String SRP011586 = "SRR446583,SRR446584";
        putData("SRP011586", SRP011586, map);
        String SRP016593 = "SRR605683,SRR605686,SRR605689,SRR605694";
        putData("SRP016593", SRP016593, map);
        String SRP017324 = "SRR620376,SRR620378,SRR620380,SRR620382,SRR620384,SRR620386,SRR620389";
        putData("SRP017324", SRP017324, map);
        String SRP017638 = "SRR639165,SRR639167,SRR639173,SRR639176";
        putData("SRP017638", SRP017638, map);
        String SRP021098 = "SRR1298602,SRR12986171c,SRR12986226c,SRR12986312c,SRR129864456c,SRR827660,SRR12986501c,SRR12986578c,SRR12986735c,SRR12986767c,SRR1298681,SRR12986846c,SRR827656,SRR8276689c,SRR827672,SRR8276756c,SRR827680,SRR827685,SRR8276912c,SRR8276956c,SRR8276978c";
        putData("SRP021098", SRP021098, map);
        String SRP024277 = "SRR887342,SRR891238,SRR923884,SRR923894,SRR923897,SRR923898,SRR923900";
        putData("SRP024277", SRP024277, map);
        String SRP025919 = "SRR899430,SRR899431,SRR899432,SRR899433,SRR899438,SRR899439,SRR899440,SRR899498,SRR899507,SRR899508,SRR899509,SRR899510,SRR899511,SRR899512";
        putData("SRP025919", SRP025919, map);
        String SRP029897 = "SRR976388,SRR976392,SRR976393,SRR976396";
        putData("SRP029897", SRP029897, map);
        String SRP034934 = "SRR1185321,SRR1185322,SRR1185323";
        putData("SRP034934", SRP034934, map);
        String SRP038111 = "SRR1174205,SRR1174206,SRR1174207,SRR1174208,SRR1174209,SRR1174211,SRR1174212,SRR1174213,SRR1174214,SRR1174215,SRR1174216,SRR1174217,SRR1174218,SRR1174219,SRR1174220,SRR1174221,SRR1174222,SRR1174223,SRR1174224,SRR1174225,SRR1174226,SRR1174227,SRR1174228,SRR1174229,SRR1174230,SRR1174231,SRR1174232,SRR1174233";
        putData("SRP038111", SRP038111, map);
        String SRP040660 = "SRR1206481,SRR1206482,SRR1206483,SRR1206484,SRR1206485,SRR1206486,SRR1206487,SRR1206488,SRR1206489";
        putData("SRP040660", SRP040660, map);
        String SRP041709 = "SRR1272329,SRR12723367c,SRR12723401c,SRR12723478c,SRR1272353,SRR12723567c,SRR12723656c,SRR12723689c";
        putData("SRP041709", SRP041709, map);
        String SRP041710 = "SRR1272372,SRR12723778c,SRR1272387,SRR12723889c,SRR12723978c,SRR12724009c,SRR12724067c,SRR1272412";
        putData("SRP041710", SRP041710, map);
        String SRP041932 = "SRR12845023c,SRR12845101c,SRR1284518,SRR1284524,SRR1284530,SRR1284531,SRR12845378c,SRR1284541,SRR1284545,SRR1284550";
        putData("SRP041932", SRP041932, map);
        String SRP045061 = "SRR1531418,SRR1531419";
        putData("SRP045061", SRP045061, map);
        String SRP045669 = "SRR1555242,SRR1555243,SRR1555244,SRR1555245";
        putData("SRP045669", SRP045669, map);
        String SRP051653 = "SRR1737745,SRR1737746,SRR1737747";
        putData("SRP051653", SRP051653, map);
        String SRP059860 = "SRR2079326,SRR2079327,SRR2079328,SRR2079329";
        putData("SRP059860", SRP059860, map);
        String SRP007610 = "SRR324698,SRR324699,SRR324700,SRR324701";//2017-08-14 此处SRP066454重命名为SRP007610，然后里面的SRR2937360删除
        putData("SRP007610", SRP007610, map);
        String SRP073278 = "SRR3382391,SRR3382392,SRR3382393,SRR3382394,SRR3382395";
        putData("SRP073278", SRP073278, map);
        String SRP074365 = "SRR3475896,SRR3475897,SRR3475898,SRR3475904,SRR3475906,SRR3475907,SRR3475914,SRR3475915,SRR3475916,SRR3475918,SRR3475923,SRR3475925";
        putData("SRP074365", SRP074365, map);
        String SRP082550 = "SRR4053698,SRR4053700,SRR4053702,SRR4053705";
        putData("SRP082550", SRP082550, map);
        String SRP091708 = "SRR4428969,SRR4428970,SRR4428974,SRR4428975,SRR4428981,SRR4428982,SRR4428995,SRR4428998";
        putData("SRP091708", SRP091708, map);
        String SRP095280 = "SRR5117918,SRR5117919,SRR5117920,SRR5117921,SRR5117922,SRR5117925,SRR5117926,SRR5117927,SRR5117929,SRR5117932,SRR5117933,SRR5117939,SRR5117940,SRR5117941,SRR5117942";
        putData("SRP095280", SRP095280, map);
        String SRP100669 = "SRR5288603,SRR5288605,SRR5288608";
        putData("SRP100669", SRP100669, map);
        return map;
    }

    public static void putData(String value, String keys, Map<String, String> map) {
        String[] key = keys.split(",");
        for (String k : key) {
            map.put(k, value);
        }
    }

    /**
     * 对表达基因和差异基因库列表进行分页
     *
     * @param list
     * @param pageNo
     * @param pageSize
     * @return
     */
    public static List<JSONObject> queryByPage(List<JSONObject> list, Integer pageNo, Integer pageSize) {
        int totalSize = list.size();
        int totalPage = (totalSize + pageSize - 1) / pageSize;
        int startIdx = 0;
        int endIdx = 0;
        startIdx = (pageNo - 1) * pageSize;
        if (pageNo < totalPage) {
            endIdx = pageNo * pageSize;
        } else {
            endIdx = totalSize;
        }
        if (startIdx > endIdx) {
            return new ArrayList<JSONObject>();
        }
//      logger.info("Total:" + totalSize + ",from " + startIdx + " to " + endIdx);
        return list.subList(startIdx, endIdx);
    }

    /**
     * 对上传的zip压缩文件进行解压
     *
     * @param zipFile
     * @param descDir
     * @return
     */
    public static String unzipFile(File zipFile, String descDir) {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        ZipFile zip = null;
        boolean hasDir = false;
        String outdir = "";
        String outFile = "";
        try {
            zip = new ZipFile(zipFile);
            for (Enumeration entries = zip.entries(); entries.hasMoreElements(); ) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                if (entry.isDirectory()) {
                    String tmp = descDir + zipEntryName;
                    File dir = new File(tmp);
                    dir.mkdirs();
                    hasDir = true;
                    outdir = tmp;
                    continue;
                } else {
                    //读写文件
                    InputStream in = zip.getInputStream(entry);
                    String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
                    //判断路径是否存在,不存在则创建文件路径
                    File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    //输出文件路径信息
                    System.out.println(outPath);
                    outFile = outPath;
                    OutputStream out = new FileOutputStream(outPath);
                    byte[] buf1 = new byte[1024];
                    int len;
                    while ((len = in.read(buf1)) > 0) {
                        out.write(buf1, 0, len);
                    }
                    in.close();
                    out.close();
                }
            }
            System.out.println("******************解压完毕********************");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (hasDir) {
            return outdir;
        } else {
            return outFile;
        }
    }

    /**
     * 将解压后的文件加上对应的sampName再进行压缩。
     *
     * @param zipFileName
     * @param inputFile
     * @throws Exception
     */
    public static void zip(String zipFileName, File inputFile) {
        System.out.println("压缩中...");
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(
                    zipFileName));
            BufferedOutputStream bo = new BufferedOutputStream(out);
            zip(out, inputFile, inputFile.getName(), bo);
            bo.close();
            out.close(); // 输出流关闭
            System.out.println("压缩完成");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int k = 1;

    private static void zip(ZipOutputStream out, File f, String base,
                            BufferedOutputStream bo) throws Exception { // 方法重载
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
                System.out.println(base + "/");
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
            System.out.println("第" + k + "次递归");
            k++;
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            System.out.println(base);
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            bi.close();
            in.close(); // 输入流关闭
        }
    }

    public static void main(String[] args) {
        unzipFile(new File("C:\\Users\\Administrator\\Desktop\\SRP017638.zip"), "E:\\tmp");
    }

}
