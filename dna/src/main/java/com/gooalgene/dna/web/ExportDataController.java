package com.gooalgene.dna.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DataExportCondition;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.service.DNAGensService;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.dna.service.SNPService;
import com.gooalgene.dna.util.JacksonUtils;
import com.gooalgene.utils.CommonUtil;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.Tools;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jayway.jsonpath.JsonPath;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liuyan on 2017/11/13
 */

@RestController
public class ExportDataController {

    private final static Logger logger = LoggerFactory.getLogger(ExportDataController.class);
    @Autowired
    private DNARunService dnaRunService;

    @Autowired
    private DNARunDao dnaRunDao;
    @Autowired
    private SNPService snpService;
    @Autowired
    private DNAGensService dnaGensService;

    //TODO
    private static List<String> dnaList = new ArrayList<String>();

    //用于群体信息数据导出

    @RequestMapping(value = "/export", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String exportData(HttpServletRequest request) throws IOException {
        //接收的参数 title 表头信息  condition 表头的筛选条件
        String choices = request.getParameter("titles");
        String temp = request.getParameter("condition");
        //dnaRunDto用来存储表头筛选的条件
        SampleInfoDto sampleInfoDto = null;
        if (temp != null && !temp.equals("")) {
            sampleInfoDto = new ObjectMapper().readValue(temp, SampleInfoDto.class);
        } else {
            sampleInfoDto = new SampleInfoDto();
        }

        String titles = "";
        //此处condition 用来表示表头信息
        List condition = new ArrayList();
        if (choices != null && !choices.equals("")) {
            //由于前端在字符串结尾传了一个逗号，故要截掉
            titles = choices.substring(0, choices.length() - 1);
            condition = Arrays.asList(titles.split(","));
        } else {
            condition = Arrays.asList(",".split(","));
        }

        String fileName = "";
        //生成文件名
        if (condition.size() > 5) {
            for (int i = 0; i < 5; i++) {
                fileName += condition.get(i) + "-";
            }
        } else {
            for (int j = 0; j < condition.size(); j++) {
                fileName += condition.get(j) + "-";
            }
        }

        fileName += UUID.randomUUID() + ".csv";
        String filePath = request.getSession().getServletContext().getRealPath("/") + "tempFile/";
        String csvStr = "";
        List<SampleInfoDto> result = dnaRunDao.getListByCondition(sampleInfoDto);
        //使用csv进行导出
        if (choices == null || choices.equals("")) {
            csvStr = "请正确选择表格内容";
        } else {
            csvStr = createCsvStr(result, condition);
        }


        File tempfile = new File(filePath + fileName);
        if (!tempfile.getParentFile().exists()) {
            if (!tempfile.getParentFile().mkdirs()) {
                return "文件目录创建失败";
            }
        }
        FileOutputStream tempFile = new FileOutputStream(tempfile);
        tempFile.write(csvStr.getBytes("gbk"));
        tempFile.flush();
        tempFile.close();

        String scheme = request.getScheme();                // http
        String serverName = request.getServerName();        // gooalgene.com
        int serverPort = request.getServerPort();           // 8080
        String contextPath = request.getContextPath();      // /dna
        //重构请求URL
        StringBuilder builder = new StringBuilder();
        builder.append(scheme).append("://").append(serverName);
        //针对nginx反向代理、https请求重定向，不需要加端口
        if (serverPort != 80 && serverPort != 443) {
            builder.append(":").append(serverPort);
        }
        builder.append(contextPath);
        String path = builder.toString() + "/tempFile/" + fileName;
        logger.info(path);
        deleteTempFile(filePath);
        return JsonUtils.Bean2Json(path);
    }


    /**
     * @param filePath 文件路径
     * @author 张衍平
     */
    public void deleteTempFile(String filePath) {
        //删除之前下载产生的临时文件 每次下载都删除一个小时之前的文件
        File[] fileList = null;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, -1000 * 3600);
        long time = calendar.getTime().getTime();
        File file = new File(filePath);
        if (file.exists()) {
            fileList = file.listFiles();
        }
        if (fileList != null && fileList.length > 0) {
            for (File f : fileList) {
                if (f.lastModified() < time) {
                    f.delete();
                }
            }
        }
    }


    //调整表头显示

    //将列表中的数据  生成csv的格式

    /**
     * @param result 要导出的内容
     * @param titles 表头
     */
    public static String createCsvStr(List<SampleInfoDto> result, List<String> titles) throws JsonProcessingException {
        StringBuilder sb = new StringBuilder();
        CommonUtil commonUtil = new CommonUtil();
        String formTitles = commonUtil.camelListToTitle(titles);
        sb.append(formTitles).append("\n");
        ObjectMapper objectMapper = new ObjectMapper();
        Field[] fields = SampleInfoDto.class.getDeclaredFields();
        String fieldsString = ",";
        for (Field field : fields) {
            fieldsString += field.getName();
            fieldsString += ",";
        }
        for (SampleInfoDto sampleInfoDto : result) {
            String sampleInfoDtoJson = objectMapper.writeValueAsString(sampleInfoDto);

            //由前端给的titles（表头）顺序去导出数据
            for (String oneTitle : titles) {
                if (fieldsString.contains("," + oneTitle + ",")) {
                    sb.append("\"").append(JsonPath.read(sampleInfoDtoJson, "$." + oneTitle)).append("\",");

                    //表头的一个字段和数据库及实体的相应字段不一致，在dao层中取别名的话mysql语句太长（表属性过多）
                } else if (oneTitle.equals("time")) {
                    sb.append("\"").append(JsonPath.read(sampleInfoDtoJson, "$.definitionTime")).append("\",");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    //用于SNP result页面的信息  searchInRegion 所在页面 以及样本导出
    private static final Integer EXPORT_NUM = 10000;//默认最大导出10000条记录
    private static final Integer DEFAULT_PAGE_NUM = 1;//默认导出的页数

    @RequestMapping("/dna/dataExport")
    @ResponseBody
    public void searchResultExport(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String model = request.getParameter("model");//区分导出数据类型：regin、gene、samples
        String columns = request.getParameter("choices");//表头列
        logger.info("model:" + model + ",colums:" + columns);
        String content = "";
        String fileName = model;
        if (StringUtils.isNoneBlank(columns)) {
            if (StringUtils.isNoneBlank(model)) {
                Map result;
                if ("REGION".equals(model)) {
                    String type = request.getParameter("type");
                    fileName += "_" + type;
                    String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
                    String chr = request.getParameter("chromosome");
                    String startPos = request.getParameter("start");
                    String endPos = request.getParameter("end");
                    fileName += "_" + chr + "_Position:[" + startPos + "," + endPos + "]_" + ctype;
                    String group = request.getParameter("group");
                    String temp = request.getParameter("total");       //获取数据的总数
                    Page<DNARun> page = new Page<DNARun>(request, response);
                    int total = 0;
                    if (temp != null && !temp.equals("")) {
                        total = Integer.parseInt(temp);
                    } else {
                        total = EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    result = snpService.searchSNPinRegionForExport(type, ctype, chr, startPos, endPos, group, page);
                    content = serialList(type, result, columns.split(","));
                } else if ("GENE".equals(model)) {
                    String type = request.getParameter("type");
                    fileName += "_" + type;
                    String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
                    String gene = request.getParameter("gene");
                    String upstream = request.getParameter("upstream");
                    String downstream = request.getParameter("downstream");
                    fileName += "_" + gene + "Stream:[" + upstream + "," + downstream + "]_" + ctype;
                    String group = request.getParameter("group");
                    String temp = request.getParameter("total");       //获取数据的总数
                    DNAGens dnaGens = dnaGensService.findByGene(gene);
                    if (dnaGens != null) {
                        long start = dnaGens.getStart();
                        long end = dnaGens.getEnd();
                        logger.info("gene:" + gene + ",start:" + start + ",end:" + end);
                        if (StringUtils.isNoneBlank(upstream)) {
                            start = start - Long.valueOf(upstream) < 0 ? 0 : start - Long.valueOf(upstream);
                        } else {
                            start = start - 2000 < 0 ? 0 : start - 2000;
                        }
                        if (StringUtils.isNoneBlank(downstream)) {
                            end = end + Long.valueOf(downstream);
                        } else {
                            end = end + 2000;
                        }
                        upstream = String.valueOf(start);
                        downstream = String.valueOf(end);
                    }
                    logger.info("gene:" + gene + ",upstream:" + upstream + ",downstream:" + downstream);
                    Page<DNAGens> page = new Page<DNAGens>(request, response);
                    int total = 0;
                    if (temp != null && !temp.equals("")) {
                        total = Integer.parseInt(temp);
                    } else {
                        total = EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    result = snpService.searchSNPinGene(type, ctype, gene, upstream, downstream, group, page);
                    content = serialList(type, result, columns.split(","));
                } else if ("SAMPLES".equals(model)) {
                    //增加两个字段  flag 和 cultivars
                    String group = request.getParameter("group");
                    Page<SampleInfoDto> page = new Page<>(request, response);
                    String temp = request.getParameter("total");       //获取数据的总数
                    //判断自定义群体使用的是按Group查询还是按品种名查询   group 表示按群体查询   cultivar表示按品种名查询
                    String flag = request.getParameter("flag");
                    int total = 0;
                    if (temp != null && !temp.equals("")) {
                        total = Integer.parseInt(temp);
                    } else {
                        total = EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    if (flag.equals("group")) {
                        content = createCsvStr(dnaRunService.queryDNARunByGroup(group, 1, total).getList(), Arrays.asList(columns.split(",")));
                    } else {
                        String ids = request.getParameter("cultivar");
                        String[] idArray = ids.split(",");
                        List<String> idList;
                        idList = Arrays.asList(idArray);
                        List<SampleInfoDto> dnaRunList = dnaRunDao.getByCultivarForExport(idList, false);
                        content = createCsvStr(dnaRunList, Arrays.asList(columns.split(",")));
                    }
                }
            } else {
                content = "请选择导出数据类型";
            }
        } else {
            content = "请选择正确的显示表头数据";
        }
        Tools.toDownload(fileName, content, response);
    }

    /*
     * 生成导出内容
     *
     * @param model  数据类型
     * @param result 导出内容
     * @param titles 导出表头
     * @return
     */
    private String serialList(String model, Map result, String[] titles) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.00%");
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> titleList = new ArrayList<String>();
        int len = titles.length;
        if ("SNP".equals(model)) {
            for (int i = 0; i < len; i++) {
                if (titles[i].equals("weightPer100seeds")) {
                    sb.append(titles[i] + "(g)");
                } else if (titles[i].equals("oil") || titles[i].equals("protein") || titles[i].equals("linolenic") || titles[i].equals("linoleic") || titles[i].equals("oleic") || titles[i].equals("palmitic") || titles[i].equals("stearic")) {
                    sb.append(titles[i] + "(%)");
                } else if (titles[i].equals("floweringDate") || titles[i].equals("maturityDate")) {
                    sb.append(titles[i] + "(month day)");
                } else if (titles[i].equals("height")) {
                    sb.append(titles[i] + "(cm)");
                } else if (titles[i].equals("yield")) {
                    sb.append(titles[i] + "(t/ha)");
                } else if (titles[i].equals("upperLeafletLength")) {
                    sb.append(titles[i] + "(mm)");
                } else if (titles[i].equals("FrequencyofMajorAllele")) {
                    titles[i] = "frequencyOfMajorAllele";
                    sb.append("frequencyOfMajorAllele");
                } else if (titles[i].equals("FrequencyofMinorAllele")) {
                    titles[i] = "frequencyOfMinorAllele";
                    sb.append("frequencyOfMinorAllele");
                } else {
                    sb.append(titles[i]);
                }
                if (i != (len - 1)) {
                    sb.append(",");
                } else {
                    sb.append("\n");
                }
                titleList.add(titles[i]);
                map.put(titles[i], i);
            }
        } else {
            for (int i = 0; i < len; i++) {
                if (titles[i].equals("weightPer100seeds")) {
                    sb.append(titles[i] + "(g)");
                } else if (titles[i].equals("oil") || titles[i].equals("protein") || titles[i].equals("linolenic") || titles[i].equals("linoleic") || titles[i].equals("oleic") || titles[i].equals("palmitic") || titles[i].equals("stearic")) {
                    sb.append(titles[i] + "(%)");
                } else if (titles[i].equals("floweringDate") || titles[i].equals("maturityDate")) {
                    sb.append(titles[i] + "(month day)");
                } else if (titles[i].equals("height")) {
                    sb.append(titles[i] + "(cm)");
                } else if (titles[i].equals("yield")) {
                    sb.append(titles[i] + "(t/ha)");
                } else if (titles[i].equals("upperLeafletLength")) {
                    sb.append(titles[i] + "(mm)");
                } else if (titles[i].equals("FrequencyofMajorAllele")) {
                    titles[i] = "frequencyOfMajorAllele";
                    sb.append("frequencyOfMajorAllele");
                } else if (titles[i].equals("FrequencyofMinorAllele")) {
                    titles[i] = "frequencyOfMinorAllele";
                    sb.append("frequencyOfMinorAllele");
                } else {
                    if(StringUtils.equals(titles[i],"definitionTime")){
                        sb.append("time");
                    }else {
                        sb.append(titles[i]);
                    }
                }
                if (i != (len - 1)) {
                    sb.append(",");
                } else {
                    sb.append("\n");
                }
                titleList.add(titles[i]);
                map.put(titles[i], i);
            }
        }

     /*  * 现在的sample和idel还是用的原来的接口  返回的是json类型的数据
       * SNP使用的是新的接口  返回的是对应SNPdto类型的数据
       * 把data 分别移动到判断条件内  根据不同类型 接收不同类型的参数
       * */

        if ("SAMPLES".equals(model)) {
            JSONArray data = (JSONArray) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {
                JSONObject one = data.getJSONObject(i);
                Field[] fields = SampleInfo.class.getDeclaredFields();
                for (Field field:fields){
                    String name = field.getName();
                    if(map.containsKey(name)){
                        String str = one.getString(name);
                        sb.append((StringUtils.isNotEmpty(str)? str.replace(",", "，") : "-")).append(",");
                    }
                }
                sb.append("\n");
            }
        } else if ("SNP".equals(model)) {
            List<SNPDto> data = (List<SNPDto>) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {
                SNPDto snpDto = data.get(i);
                JSONArray freq = (JSONArray) snpDto.getFreq();
                for (String titleItem : titleList) {
                    if (titleItem.equals("SNPID")) {
                        sb.append(snpDto.getId()).append(",");
                    } else if (titleItem.equals("consequenceType")) {
                        sb.append(snpDto.getConsequencetype()).append(",");
                    } else if (titleItem.equals("chromosome")) {
                        sb.append(snpDto.getChr()).append(",");
                    } else if (titleItem.equals("position")) {
                        sb.append(snpDto.getPos()).append(",");
                    } else if (titleItem.equals("reference")) {
                        sb.append(snpDto.getRef()).append(",");
                    } else if (titleItem.equals("majorAllele")) {
                        sb.append(snpDto.getMajorallen()).append(",");
                    } else if (titleItem.equals("minorAllele")) {
                        sb.append(snpDto.getMinorallen()).append(",");
                    } else if (titleItem.equals("frequencyOfMajorAllele")) {
                        sb.append(df.format(snpDto.getMajor())).append(",");
                    } else if (titleItem.equals("frequencyOfMinorAllele")) {
                        sb.append(df.format(snpDto.getMinor())).append(",");
                    } else if (titleItem.equals("genoType")) {
                        Map hashMap;
                        hashMap = snpDto.getGeneType();
                        String RR = String.valueOf(df.format((double) hashMap.get("RefAndRefPercent")));
                        String tRA = String.valueOf(df.format((double) hashMap.get("totalRefAndAltPercent")));
                        String tAA = String.valueOf(df.format((double) hashMap.get("totalAltAndAltPercent")));
                        String ref = snpDto.getRef();
                        String alt = snpDto.getAlt();
                        String ra = ref + alt;
                        sb.append(ref + ref + ":" + RR + "，" + alt + alt + ":" + tAA + "，" + ra + ":" + tRA).append(",");
                    } else {
                        if (freq.size() == 0) {
                            sb.append(",");
                        }
                        for (int j = 0; j < freq.size(); j++) {
                            JSONObject groupFreq = freq.getJSONObject(j);
                            String name = "fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String major = groupFreq.getString("major");
                            float majorValue = Float.parseFloat(major);
                            if (titleItem.equals(name)) {
                                sb.append(df.format(majorValue)).append(",");
                            }
                        }
                        for (int j = 0; j < freq.size(); j++) {
                            JSONObject groupFreq = freq.getJSONObject(j);
                            String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String minor = groupFreq.getString("minor");
                            float minorValue = Float.parseFloat(minor);
                            if (titleItem.equals(name)) {
                                sb.append(df.format(minorValue)).append(",");
                            }
                        }
                    }
                }
                sb.append("\n");
            }
        } else if ("INDEL".equals(model)) {
            List<SNPDto> data = (List<SNPDto>) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {
                SNPDto snpDto = data.get(i);
                JSONArray freq = (JSONArray) snpDto.getFreq();
                for (String titleItem : titleList) {
                    if (titleItem.equals("INDELID")) {
                        sb.append(snpDto.getId()).append(",");
                    } else if (titleItem.equals("consequenceType")) {
                        sb.append(snpDto.getConsequencetype()).append(",");
                    } else if (titleItem.equals("chromosome")) {
                        sb.append(snpDto.getChr()).append(",");
                    } else if (titleItem.equals("position")) {
                        sb.append(snpDto.getPos()).append(",");
                    } else if (titleItem.equals("reference")) {
                        sb.append(snpDto.getRef()).append(",");
                    } else if (titleItem.equals("majorAllele")) {
                        sb.append(snpDto.getMajorallen()).append(",");
                    } else if (titleItem.equals("minorAllele")) {
                        sb.append(snpDto.getMinorallen()).append(",");
                    } else if (titleItem.equals("frequencyOfMajorAllele")) {
                        sb.append(df.format(snpDto.getMajor())).append(",");
                    } else if (titleItem.equals("frequencyOfMinorAllele")) {
                        sb.append(df.format(snpDto.getMinor())).append(",");
                    } else {
                        for (int j = 0; j < freq.size(); j++) {
                            JSONObject groupFreq = freq.getJSONObject(j);
                            String name = "fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String major = groupFreq.getString("major");
                            float majorValue = Float.parseFloat(major);
                            if (titleItem.equals(name)) {
                                sb.append(df.format(majorValue)).append(",");
                            }
                        }
                        for (int j = 0; j < freq.size(); j++) {
                            JSONObject groupFreq = freq.getJSONObject(j);
                            String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String minor = groupFreq.getString("minor");
                            float minorValue = Float.parseFloat(minor);
                            if (titleItem.equals(name)) {
                                sb.append(df.format(minorValue)).append(",");
                            }
                        }
                    }
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }


    /*
    * @param  snpId      snpId
    * @param  changeParam
    * @param  condition  筛选条件
    * @param  titles     表头信息
    * @author 张衍平
    * @return filePath
    * */
    @RequestMapping(value = "/dna/IdDetailExport", method = RequestMethod.POST)
    @ResponseBody
    public String idDetailPageExport(@RequestBody DataExportCondition dataExportCondition, HttpServletRequest request) throws IOException {

        SampleInfoDto sampleInfoDto = dataExportCondition.getCondition();
        String titles = dataExportCondition.getTitles();
        String judgeAllele = dataExportCondition.getJudgeAllele();
//        JSONObject object = null;
        boolean isINDEL = false;
        //dnaRunDto用来存储表头筛选的条件
        Map result = snpService.findSampleById(sampleInfoDto.getSnpId());
        SNP snpTemp = (SNP) result.get("snpData");
        if (snpTemp == null) {
            snpTemp = (SNP) result.get("INDELData");
            isINDEL = true;
        }
        if (isINDEL) {
            if (titles.contains(",genoType")) {
                titles = titles.replaceAll(",genoType", "");
            }
        }
        String[] title = titles.split(",");
        Map map = snpTemp.getSamples();
        Set<Map.Entry<String, String>> entrySet = map.entrySet();
        List<String> runNos = Lists.newArrayList();
        Map samples = Maps.newHashMap();
        String changeParam = sampleInfoDto.getChangeParam();

        /*拿到 major/minor 变异对应的sample*/
        for (Map.Entry entry : entrySet) {
            String value = (String) entry.getValue();

            /*SNP和INDEL变异的碱基变异方式不一样，在选择 major allele 和 minor allele
            时的匹配规则不一样.
            对于SNP的单个碱基变异来说，只要判断样本的 genotype（样本的
            majorAllele+majorAllele/minor 或 minorAllele+minorAllele 字符串拼接，即下方代
            码使用到的 value）中是否包含judgeAllele (前端标识 major 和 minor 的符号，为
            majorAllele/minorAllele) 即可.
            而在INDEL中，变异均为缺失/增添的碱基段（如 ATTATCGCCGTA），非变异为单个碱基（如“A”），
            如果还用包含关系，则会会同时包含 majorAllele 和 minorAllele，就无法筛选出只包含
             majorAllele/minorAllele 的样本*/
         if (StringUtils.isNotBlank(changeParam)) {
                if (isINDEL) {
                    String majAndchangePa = snpTemp.getMajorallen() + changeParam;
                    String changePaAndMin = changeParam + snpTemp.getMinorallen();
                    if (value.equalsIgnoreCase(majAndchangePa) || value.equalsIgnoreCase(changePaAndMin)) {
                        String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                        Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                        Matcher matcher = regexp.matcher(singleRunNo);
                        if (!matcher.find()) {
                            singleRunNo = singleRunNo + ".0";
                        }
                        runNos.add(singleRunNo);
                        samples.put(entry.getKey(), entry.getValue());
                    }
                } else {
                    if (StringUtils.equalsIgnoreCase(judgeAllele, "major")) {
                        if (value.contains(snpTemp.getMajorallen())) {
                            if (StringUtils.containsIgnoreCase(value, changeParam)) {
                                String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                                Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                                Matcher matcher = regexp.matcher(singleRunNo);
                                if (!matcher.find()) {
                                    singleRunNo = singleRunNo + ".0";
                                }
                                runNos.add(singleRunNo);
                                samples.put(entry.getKey(), entry.getValue());
                            }
                        }
                    } else {
                        if (value.contains(snpTemp.getMinorallen())) {
                            if (StringUtils.containsIgnoreCase(value, changeParam)) {
                                String singleRunNo = (String) entry.getKey(); // 从966sample中拿到每个runNo
                                Pattern regexp = Pattern.compile("[a-zA-Z]"); // 匹配是否含有字母
                                Matcher matcher = regexp.matcher(singleRunNo);
                                if (!matcher.find()) {
                                    singleRunNo = singleRunNo + ".0";
                                }
                                runNos.add(singleRunNo);
                                samples.put(entry.getKey(), entry.getValue());
                            }
                        }
                    }
                }
            }
        }
        String csvStr = "";
        StringBuilder stringBuilder = new StringBuilder();
        List<String> conditionTitles = new ArrayList();
        if (titles != null && !titles.equals("")) {
            conditionTitles = Arrays.asList(titles.split(","));
        } else {
            conditionTitles = Arrays.asList(",".split(","));
        }
        CommonUtil commonUtil = new CommonUtil();
        String formTitles = commonUtil.camelListToTitle(conditionTitles);
        stringBuilder.append(formTitles).append("\n");
        //查询结果集
        PageInfo<SampleInfoDto> dnaRuns = null;
        if (sampleInfoDto != null && runNos.size() > 0) {
            sampleInfoDto.setRunNos(runNos);
            dnaRuns = dnaRunService.getListByConditionWithTypeHandler(sampleInfoDto, DEFAULT_PAGE_NUM, EXPORT_NUM, null);
        }
        if(dnaRuns!=null){
            List<SampleInfoDto> sampleInfoDtoList= dnaRuns.getList();
            if (titles.contains("genotype")) {
                for (SampleInfoDto oneSampleInfoDto : sampleInfoDtoList) {
                    oneSampleInfoDto.setGenotype((String) samples.get(oneSampleInfoDto.getRunNo()));
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            Field[] fields = SampleInfoDto.class.getDeclaredFields();
            String fieldsString = ",";

            //在一个属性前后加逗号是确保有abc和bc这种一个字段被另一个字段包含的情况不会影响结果
            for (Field field : fields) {
                fieldsString += field.getName();
                fieldsString += ",";
            }
            for (SampleInfoDto oneSampleInfoDto : sampleInfoDtoList) {
                String sampleInfoDtoJson = objectMapper.writeValueAsString(oneSampleInfoDto);

                //由前端给的titles（表头）顺序去导出数据
                for (String oneTitle : conditionTitles) {
                    if (fieldsString.contains("," + oneTitle + ",")) {
                        stringBuilder.append("\"").append(JsonPath.read(sampleInfoDtoJson, "$." + oneTitle)).append("\",");

                        //表头的一个字段和数据库及实体的相应字段不一致，在dao层中取别名的话mysql语句太长（表属性过多）
                    } else if (oneTitle.equals("time")) {
                        stringBuilder.append("\"").append(JsonPath.read(sampleInfoDtoJson, "$.definitionTime")).append("\",");
                    }
                }
                stringBuilder.append("\n");
            }
        } else {
            stringBuilder.append("");
        }
        csvStr = stringBuilder.toString();
        if (title.length == 0) {
            csvStr = "请正确选择表头信息";
        }
        String fileName = "snpinfo_detail" + UUID.randomUUID() + ".csv";
        String filePath = request.getSession().getServletContext().getRealPath("/") + "tempFile/";
        File tempfile = new File(filePath + fileName);
        if (!tempfile.getParentFile().exists()) {
            if (!tempfile.getParentFile().mkdirs()) {
                return "文件目录创建失败";
            }
        }
        FileOutputStream tempFile = null;
        try {
            tempFile = new FileOutputStream(tempfile);
            tempFile.write(csvStr.getBytes("gbk"));
            tempFile.flush();
            tempFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String scheme = request.getScheme();                // http
        String serverName = request.getServerName();        // gooalgene.com
        int serverPort = request.getServerPort();           // 8080
        String contextPath = request.getContextPath();      // /dna
        //重构请求URL
        StringBuilder builder = new StringBuilder();
        builder.append(scheme).append("://").append(serverName);
        //针对nginx反向代理、https请求重定向，不需要加端口
        if (serverPort != 80 && serverPort != 443) {
            builder.append(":").append(serverPort);
        }
        builder.append(contextPath);
        String path = builder.toString() + "/tempFile/" + fileName;
        logger.info(path);
        return JsonUtils.Bean2Json(path);
    }
}
