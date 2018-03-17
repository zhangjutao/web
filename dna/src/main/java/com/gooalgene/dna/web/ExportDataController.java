package com.gooalgene.dna.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.dto.SampleInfoDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.SampleInfo;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.gooalgene.dna.service.DNAGensService;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.dna.service.SNPService;
import com.gooalgene.dna.util.Json2DnaRunDto;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.Tools;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
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

    private static List<String> dnaList = new ArrayList<String>();

    //用于群体信息数据导出

    @RequestMapping(value = "/export", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String exportData(HttpServletRequest request) throws IOException {
        //接收的参数 title 表头信息  condition 表头的筛选条件
        String choices = request.getParameter("titles");
        String temp = request.getParameter("condition");
        JSONObject object = null;
        //dnaRunDto用来存储表头筛选的条件
        DnaRunDto dnaRunDto = null;
        if (temp != null && !temp.equals("")) {
            object = JSONObject.fromObject(temp);
            dnaRunDto = Json2DnaRunDto.json2DnaRunDto(object);
        } else {
            dnaRunDto = new DnaRunDto();
        }

        String titles = "";
        //此处condition 用来表示表头信息
        String[] condition = null;
        if (choices != null && !choices.equals("")) {
            titles = choices.substring(0, choices.length() - 1);
            condition = titles.split(",");
        } else {
            condition = ",".split(",");
        }

        String fileName = "";
        //生成文件名
        if (condition.length > 5) {
            for (int i = 0; i < 5; i++) {
                fileName += condition[i] + "-";
            }
        } else {
            for (int j = 0; j < condition.length; j++) {
                fileName += condition[j] + "-";
            }
        }

        fileName += UUID.randomUUID() + ".csv";
        String filePath = request.getSession().getServletContext().getRealPath("/") + "tempFile/";
        String csvStr = "";
        List<DNARun> result = dnaRunDao.getListByCondition(dnaRunDto);
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
    private static Map<String, String> changeCloumn2Web() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("species", "Species");
        map.put("sampleName", "Sample Name");
        map.put("cultivar", "Cultivar");
        map.put("locality", "Locality");
        map.put("protein", "Protein(%)");
        map.put("oil", "Oil(%)");
        map.put("linoleic", "Linoleic(%)");
        map.put("linolenic", "Linolenic(%)");
        map.put("oleic", "Oleic(%)");
        map.put("palmitic", "Palmitic(%)");
        map.put("stearic", "Stearic(%)");
        map.put("height", "Height(cm)");
        map.put("flowerColor", "Flower Color");
        map.put("hilumColor", "Hilum Color");
        map.put("podColor", "Pod Color");
        map.put("pubescenceColor", "Pubescence Color");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("cotyledonColor", "Cotyledon Color");
        map.put("weightPer100seeds", "Weight (g) per 100 Seeds");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("upperLeafletLength", "upper Leaflet Length");
        map.put("maturityDate", "Maturity Date");
        map.put("yield", "Yield(Mg/ha)");
        map.put("population", "Group");
        map.put("genoType", "GenoType");
        map.put("group", "Group");
        return map;
    }

    //将列表中的数据  生成csv的格式

    /**
     * @param result 要导出的内容
     * @param titles 表头
     */
    public static String createCsvStr(List<DNARun> result, String[] titles) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new ConcurrentHashMap<>();
        Map<String, String> titleMap = changeCloumn2Web();
        int len = titles.length;
        for (int i = 0; i < len; i++) {
            String title = titles[i];
            sb.append(titleMap.get(title));
            if (i != len - 1) {
                sb.append(",");
            }
            if (i == len - 1) {
                sb.append("\n");
            }
            map.put(titles[i], i);
        }

        int size = result.size();
        for (int j = 0; j < size; j++) {
            DNARun dnaRun = result.get(j);
            //品种名
            if (map.containsKey("cultivar")) {
                String cultivar = dnaRun.getCultivar();
                dnaList.add(cultivar != null && !cultivar.equals("") ? cultivar : "-");
                sb.append(cultivar != null && !cultivar.equals("") ? cultivar : "-").append(",");
            }
            //组别
            if (map.containsKey("population")) {
                String group = dnaRun.getGroup();
                dnaList.add(group != null && !group.equals("") ? group : "-");
                sb.append(group != null && !group.equals("") ? group : "-").append(",");
            }
            //物种
            if (map.containsKey("species")) {
                String species = dnaRun.getSpecies();
                dnaList.add(species != null && !species.equals("") ? species : "-");
                sb.append(species != null && !species.equals("") ? species : "-").append(",");
            }

            //地理位置
            if (map.containsKey("locality")) {
                String locality = dnaRun.getLocality();
                if (locality != null && locality.contains(",")) {
                    locality = locality.replaceAll(",", "，");
                }
                dnaList.add(locality != null && !locality.equals("") ? locality : "-");
                sb.append(locality != null && !locality.equals("") ? locality : "-").append(",");
            }

            //样本名
            if (map.containsKey("sampleName")) {
                String sampleName = dnaRun.getSampleName();
                dnaList.add(sampleName != null && !sampleName.equals("") ? sampleName : "-");
                sb.append(sampleName != null && !sampleName.equals("") ? sampleName : "-").append(",");
            }
            //百粒重
            if (map.containsKey("weightPer100seeds")) {
                float weight = dnaRun.getWeightPer100seeds();
                dnaList.add(!String.valueOf(weight).equals("") ? String.valueOf(weight) : "-");
                sb.append(weight != 0 ? String.valueOf(weight) : "-").append(",");
            }
            //蛋白质含量
            if (map.containsKey("protein")) {
                float protein = dnaRun.getProtein();
                dnaList.add(!String.valueOf(protein).equals("") ? String.valueOf(protein) : "-");
                sb.append(protein != 0 ? String.valueOf(protein) : "-").append(",");
            }
            //含油量
            if (map.containsKey("oil")) {
                float oil = dnaRun.getOil();
                dnaList.add(!String.valueOf(oil).equals("0") ? String.valueOf(oil) : "-");
                sb.append(oil != 0 ? String.valueOf(oil) : "-").append(",");
            }
            //成熟期
            if (map.containsKey("maturityDate")) {
                String maturityGroup = dnaRun.getMaturityDate();
                dnaList.add(!String.valueOf(maturityGroup).equals("") ? String.valueOf(maturityGroup) : "-");
                sb.append(maturityGroup != null && !maturityGroup.equals("") ? maturityGroup : "-").append(",");
            }
            //株高
            if (map.containsKey("height")) {
                float height = dnaRun.getHeight();
                dnaList.add(!String.valueOf(height).equals("0") ? String.valueOf(height) : "-");
                sb.append(height != 0 ? String.valueOf(height) : "-").append(",");
            }

            //种皮色
            if (map.containsKey("seedCoatColor")) {
                String seedCoatColor = dnaRun.getSeedCoatColor();
                dnaList.add(seedCoatColor != null && !seedCoatColor.equals("") ? seedCoatColor : "-");
                sb.append(seedCoatColor != null && !seedCoatColor.equals("") ? seedCoatColor : "-").append(",");
            }
            //种脐色
            if (map.containsKey("hilumColor")) {
                String hilumColor = dnaRun.getHilumColor();
                dnaList.add(hilumColor != null && !hilumColor.equals("") ? hilumColor : "-");
                sb.append(hilumColor != null && !hilumColor.equals("") ? hilumColor : "-").append(",");
            }
            //子叶色
            if (map.containsKey("cotyledonColor")) {
                String cotyledonColor = dnaRun.getCotyledonColor();
                dnaList.add(cotyledonColor != null && !cotyledonColor.equals("") ? cotyledonColor : "-");
                sb.append(cotyledonColor != null && !cotyledonColor.equals("") ? cotyledonColor : "-").append(",");
            }

            //花色
            if (map.containsKey("flowerColor")) {
                String flowerColor = dnaRun.getFlowerColor();
                dnaList.add(flowerColor != null && !flowerColor.equals("") ? flowerColor : "-");
                sb.append(flowerColor != null && !flowerColor.equals("") ? flowerColor : "-").append(",");
            }
            //荚色
            if (map.containsKey("podColor")) {
                String podColor = dnaRun.getPodColor();
                dnaList.add(podColor != null && !podColor.equals("") ? podColor : "-");
                sb.append(podColor != null && !podColor.equals("") ? podColor : "-").append(",");
            }
            //茸毛色
            if (map.containsKey("pubescenceColor")) {
                String pubescenceColor = dnaRun.getPubescenceColor();
                dnaList.add(pubescenceColor != null && !pubescenceColor.equals("") ? pubescenceColor : "-");
                sb.append(pubescenceColor != null && !pubescenceColor.equals("") ? pubescenceColor : "-").append(",");
            }
            //产量
            if (map.containsKey("yield")) {
                float yield = dnaRun.getYield();
                dnaList.add(!String.valueOf(yield).equals("0") ? String.valueOf(yield) : "-");
                sb.append(yield != 0 ? String.valueOf(yield) : "-").append(",");
            }
            //顶端小叶长度
            if (map.containsKey("upperLeafletLength")) {
                float upperLeafletLength = dnaRun.getUpperLeafletLength();
                dnaList.add(!String.valueOf(upperLeafletLength).equals("") ? String.valueOf(upperLeafletLength) : "-");
                sb.append(upperLeafletLength != 0 ? String.valueOf(upperLeafletLength) : "-").append(",");
            }
            //脂肪酸的内容
            //亚油酸
            if (map.containsKey("linoleic")) {
                float linoleic = dnaRun.getLinoleic();
                dnaList.add(!String.valueOf(linoleic).equals("") ? String.valueOf(linoleic) : "-");
                sb.append(linoleic != 0 ? String.valueOf(linoleic) : "-").append(",");
            }
            //亚麻酸
            if (map.containsKey("linolenic")) {
                float linolenic = dnaRun.getLinolenic();
                dnaList.add(!String.valueOf(linolenic).equals("") ? String.valueOf(linolenic) : "-");
                sb.append(linolenic != 0 ? String.valueOf(linolenic) : "-").append(",");
            }
            //油酸
            if (map.containsKey("oleic")) {
                float oleic = dnaRun.getOleic();
                dnaList.add(!String.valueOf(oleic).equals("") ? String.valueOf(oleic) : "-");
                sb.append(oleic != 0 ? String.valueOf(oleic) : "-").append(",");
            }

            //软脂酸
            if (map.containsKey("palmitic")) {
                float palmitic = dnaRun.getPalmitic();
                dnaList.add(!String.valueOf(palmitic).equals("") ? String.valueOf(palmitic) : "-");
                sb.append(palmitic != 0 ? String.valueOf(palmitic) : "-").append(",");
            }
            //硬脂酸
            if (map.containsKey("stearic")) {
                float stearic = dnaRun.getStearic();
                dnaList.add(!String.valueOf(stearic).equals("") ? String.valueOf(stearic) : "-");
                sb.append(stearic != 0 ? String.valueOf(stearic) : "-").append(",");
            }

            //前端数据不包含部分
            //编号
            if (map.containsKey("run")) {
                String run = dnaRun.getRunNo();
                dnaList.add(run);
                sb.append(run != null ? run : "-").append(",");
            }

            //品种名称
            if (map.containsKey("plantName")) {
                String plantName = dnaRun.getPlantName();
                dnaList.add(String.valueOf(plantName));
                sb.append(plantName != null ? plantName : "-").append(",");
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
                        long start = dnaGens.getGeneStart();
                        long end = dnaGens.getGeneEnd();
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
                        result = dnaRunService.queryDNARunByGroup(group, page);
                    } else {
                        String ids = request.getParameter("cultivar");
                        String[] idArray = ids.split(",");
                        Map tempResult = new HashMap();
                        List<String> idList;
                        idList = Arrays.asList(idArray);
                        List<DNARun> dnaRunList = dnaRunDao.getByCultivarForExport(idList);
                        JSONArray data = new JSONArray();
                        for (DNARun dnaRun : dnaRunList) {
                            data.add(dnaRun.toJSON());
                        }
                        tempResult.put("data", data);
                        result = tempResult;
                    }
                    content = serialList(model, result, columns.split(","));
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
                /*if (map.containsKey("scientificName")) {
                    String scientificName = one.getString("scientificName");
                    sb.append((scientificName != null && !scientificName.toString().equals("") ? scientificName : "-")).append(",");
                }
                if (map.containsKey("locality")) {
                    String locality = one.getString("locality");
                    sb.append((locality != null && !locality.toString().equals("") ? locality.replace(",", "，") : "-")).append(",");
                }
                if (map.containsKey("sampleId")) {
                    String sampleId = one.getString("sampleId");
                    sb.append((sampleId != null && !sampleId.toString().equals("") ? sampleId : "-")).append(",");
                }
                if (map.containsKey("strainName")) {
                    String strainName = one.getString("strainName");
                    sb.append((strainName != null && !strainName.toString().equals("") ? strainName : "-")).append(",");
                }
                if (map.containsKey("preservationLocation")) {
                    Object preservationLocation = one.get("preservationLocation");
                    sb.append((preservationLocation != null && !preservationLocation.toString().equals("") ? preservationLocation : "-")).append(",");
                }
                if (map.containsKey("type")) {
                    Object type = one.get("type");
                    sb.append((type != null && !type.toString().equals("") ? type : "-")).append(",");
                }
                if (map.containsKey("environment")) {
                    Object environment = one.get("environment");
                    sb.append((environment != null && !environment.toString().equals("") ? environment : "-")).append(",");
                }
                if (map.containsKey("materials")) {
                    Object materials = one.get("materials");
                    sb.append((materials != null && !materials.toString().equals("") ? materials : "-")).append(",");
                }
                if (map.containsKey("treat")) {
                    Object treat = one.get("treat");
                    sb.append((treat != null && !treat.toString().equals("") ? treat : "-")).append(",");
                }
                if (map.containsKey("definitionTime")) {
                    Object definitionTime = one.get("definitionTime");
                    sb.append((definitionTime != null && !definitionTime.toString().equals("") ? definitionTime : "-")).append(",");
                }
                if (map.containsKey("taxonomy")) {
                    Object taxonomy = one.get("taxonomy");
                    sb.append((taxonomy != null && !taxonomy.toString().equals("") ? taxonomy : "-")).append(",");
                }
                if (map.containsKey("myceliaPhenotype")) {
                    Object myceliaPhenotype = one.get("myceliaPhenotype");
                    sb.append((myceliaPhenotype != null && !myceliaPhenotype.toString().equals("") ? myceliaPhenotype : "-")).append(",");
                }
                if (map.containsKey("myceliaDiameter")) {
                    Object myceliaDiameter = one.get("myceliaDiameter");
                    sb.append((myceliaDiameter != null && !myceliaDiameter.toString().equals("") ? myceliaDiameter : "-")).append(",");
                }
                if (map.containsKey("myceliaColor")) {
                    Object myceliaColor = one.get("myceliaColor");
                    sb.append((myceliaColor != null && !myceliaColor.toString().equals("") ? myceliaColor : "-")).append(",");
                }
                if (map.containsKey("sporesColor")) {
                    Object sporesColor = one.get("sporesColor");
                    sb.append((sporesColor != null && !sporesColor.toString().equals("") ? sporesColor : "-")).append(",");
                }
                if (map.containsKey("sporesShape")) {
                    Object sporesShape = one.get("sporesShape");
                    sb.append((sporesShape != null && !sporesShape.toString().equals("") ? sporesShape : "-")).append(",");
                }
                if (map.containsKey("clampConnection")) {
                    Object clampConnection = one.get("clampConnection");
                    sb.append((clampConnection != null && !clampConnection.toString().equals("") ? clampConnection : "-")).append(",");
                }
                if (map.containsKey("pileusPhenotype")) {
                    Object pileusPhenotype = one.get("pileusPhenotype");
                    sb.append((pileusPhenotype != null && !pileusPhenotype.toString().equals("") ? pileusPhenotype : "-")).append(",");
                }
                if (map.containsKey("pileusColor")) {
                    Object pileusColor = one.get("pileusColor");
                    sb.append((pileusColor != null && !pileusColor.toString().equals("") ? pileusColor : "-")).append(",");
                }
                if (map.containsKey("stipePhenotype")) {
                    Object stipePhenotype = one.get("stipePhenotype");
                    sb.append((stipePhenotype != null && !stipePhenotype.toString().equals("") ? stipePhenotype : "-")).append(",");
                }
                if (map.containsKey("stipeColor")) {
                    Object stipeColor = one.get("stipeColor");
                    sb.append((stipeColor != null && !stipeColor.toString().equals("") ? stipeColor : "-")).append(",");
                }
                if (map.containsKey("fruitbodyColor")) {
                    Object fruitbodyColor = one.get("fruitbodyColor");
                    sb.append((fruitbodyColor != null && !fruitbodyColor.toString().equals("") ? fruitbodyColor : "-")).append(",");
                }
                if (map.containsKey("fruitbodyType")) {
                    Object fruitbodyType = one.get("fruitbodyType");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("illumination")) {
                    Object illumination = one.get("illumination");
                    sb.append((illumination != null && !illumination.toString().equals("") ? illumination : "-")).append(",");
                }
                if (map.containsKey("collarium")) {
                    Object collarium = one.get("collarium");
                    sb.append((collarium != null && !collarium.toString().equals("") ? collarium : "-")).append(",");
                }
                if (map.containsKey("volva")) {
                    Object volva = one.get("volva");
                    sb.append((volva != null && !volva.toString().equals("") ? volva : "-")).append(",");
                }
                if (map.containsKey("velum")) {
                    Object velum = one.get("velum");
                    sb.append((velum != null && !velum.toString().equals("") ? velum : "-")).append(",");
                }
                if (map.containsKey("sclerotium")) {
                    Object fruitbodyType = one.get("sclerotium");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("strainMedium")) {
                    Object fruitbodyType = one.get("strainMedium");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("mainSubstrate")) {
                    Object fruitbodyType = one.get("mainSubstrate");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("afterRipeningStage")) {
                    Object fruitbodyType = one.get("afterRipeningStage");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("primordialStimulationFruitbody")) {
                    Object fruitbodyType = one.get("primordialStimulationFruitbody");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("reproductiveMode")) {
                    Object fruitbodyType = one.get("reproductiveMode");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("lifestyle")) {
                    Object fruitbodyType = one.get("lifestyle");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("preservation")) {
                    Object fruitbodyType = one.get("preservation");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("domestication")) {
                    Object fruitbodyType = one.get("domestication");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("nuclearPhase")) {
                    Object fruitbodyType = one.get("nuclearPhase");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }
                if (map.containsKey("matingType")) {
                    Object fruitbodyType = one.get("matingType");
                    sb.append((fruitbodyType != null && !fruitbodyType.toString().equals("") ? fruitbodyType : "-")).append(",");
                }*/

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
    @RequestMapping("/dna/IdDetailExport")
    @ResponseBody
    public String idDetailPageExport(@RequestParam("titles") String titles, @RequestParam("judgeAllele") String judgeAllele, HttpServletRequest request) {

        String condition = request.getParameter("condition");
//        JSONObject object = null;
        boolean isINDEL = false;
        //dnaRunDto用来存储表头筛选的条件
        SampleInfoDto sampleInfoDto = null;
        if (condition != null && !condition.equals("")) {
//            object = JSONObject.fromObject(condition);
            sampleInfoDto = new Gson().fromJson(condition,SampleInfoDto.class);
        } else {
            sampleInfoDto = new SampleInfoDto();
        }
        String snpId = "";
        if (JsonPath.read(condition,"$.snpId")!="") {
            snpId = JsonPath.read(condition,"$.snpId");
        }
        String changeParam = "";
        if (JsonPath.read(condition,"changeParam")!="") {
            changeParam = JsonPath.read(condition,"changeParam");
        }
        Map result = snpService.findSampleById(snpId);
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
        for (Map.Entry entry : entrySet) {
            String value = (String) entry.getValue();
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
        Map<String, String> titleMap = changeCloumn2Web();
        int size = title.length;
        //生成表头
        for (int j = 0; j < size; j++) {
            stringBuilder.append(titleMap.get(title[j]));
            if (j != size - 1) {
                stringBuilder.append(",");
            } else {
                stringBuilder.append("\n");
            }
        }
        //查询结果集
        PageInfo<SampleInfoDto> dnaRuns = null;
        if (sampleInfoDto != null && runNos.size() > 0) {
            sampleInfoDto.setRunNos(runNos);
            dnaRuns = dnaRunService.getListByConditionWithTypeHandler(sampleInfoDto, DEFAULT_PAGE_NUM, EXPORT_NUM, null);
        }
        if(dnaRuns!=null){
            List<SampleInfoDto> sampleInfoDtoList= dnaRuns.getList();
            for (SampleInfoDto sampleInfoDto1 : sampleInfoDtoList) {
                for (String titleItem : title) {
                    if (titleItem.equals("id")) {
                        String id = sampleInfoDto1.getId();
                        stringBuilder.append(!id.equals("") ? id : "-");
                    } else if (titleItem.equals("runNo")) {
                        String runNo = sampleInfoDto1.getRunNo();
                        stringBuilder.append(!runNo.equals("") ? runNo : "-");
                    } else if (titleItem.equals("scientificName")) {
                        String scientificName = sampleInfoDto1.getScientificName();
                        stringBuilder.append(scientificName != null && !scientificName.equals("") ? scientificName : "-");
                    } else if (titleItem.equals("sampleId")) {
                        String sampleId = sampleInfoDto1.getSampleId();
                        stringBuilder.append(!sampleId.equals("") ? sampleId : "-");
                    } else if (titleItem.equals("strainName")) {
                        String strainName = sampleInfoDto1.getStrainName();
                        stringBuilder.append(!strainName.equals("") ? strainName : "-");
                    } else if (titleItem.equals("locality")) {
                        String locality = sampleInfoDto1.getLocality();
                        if (locality != null && locality.contains(",")) {
                            locality = locality.replaceAll(",", "，");
                        }
                        stringBuilder.append(locality != null && !locality.equals("") ? locality : "-");
                    } else if (titleItem.equals("preservationLocation")) {
                        String preservationLocation = sampleInfoDto1.getPreservationLocation();
                        stringBuilder.append(!preservationLocation.equals("") ? preservationLocation : "-");
                    } else if (titleItem.equals("type")) {
                        String type = sampleInfoDto1.getType();
                        stringBuilder.append(type != null && !type.equals("") ? type : "-");
                    } else if (titleItem.equals("environment")) {
                        String environment = sampleInfoDto1.getEnvironment();
                        stringBuilder.append(!environment.equals("") ? environment : "-");
                    } else if (titleItem.equals("materials")) {
                        String materials = sampleInfoDto1.getMaterials();
                        stringBuilder.append(!materials.equals("") ? materials : "-");
                    } else if (titleItem.equals("treat")) {
                        String treat = sampleInfoDto1.getTreat();
                        stringBuilder.append(!treat.equals("") ? treat : "-");
                    } else if (titleItem.equals("definitionTime")) {
                        String definitionTime = sampleInfoDto1.getDefinitionTime();
                        stringBuilder.append(!definitionTime.equals("") ? definitionTime : "-");
                    } else if (titleItem.equals("taxonomy")) {
                        String taxonomy = sampleInfoDto1.getTaxonomy();
                        stringBuilder.append(!taxonomy.equals("") ? taxonomy : "-");
                    } else if (titleItem.equals("myceliaPhenotype")) {
                        String myceliaPhenotype = sampleInfoDto1.getMyceliaPhenotype();
                        stringBuilder.append(!myceliaPhenotype.equals("") ? myceliaPhenotype : "-");
                    } else if (titleItem.equals("myceliaDiameter")) {
                        String myceliaDiameter = sampleInfoDto1.getMyceliaDiameter();
                        stringBuilder.append(!myceliaDiameter.equals("") ? myceliaDiameter : "-");
                    } else if (titleItem.equals("myceliaColor")) {
                        String myceliaColor = sampleInfoDto1.getMyceliaColor();
                        stringBuilder.append(!myceliaColor.equals("") ? myceliaColor : "-");
                    } else if (titleItem.equals("sporesColor")) {
                        String sporesColor = sampleInfoDto1.getSporesColor();
                        stringBuilder.append(!sporesColor.equals("") ? sporesColor : "-");
                    } else if (titleItem.equals("sporesShape")) {
                        String sporesShape = sampleInfoDto1.getSporesShape();
                        stringBuilder.append(!sporesShape.equals("") ? sporesShape : "-");
                    } else if (titleItem.equals("clampConnection")) {
                        String clampConnection = sampleInfoDto1.getClampConnection();
                        stringBuilder.append(!clampConnection.equals("") ? clampConnection : "-");
                    } else if (titleItem.equals("pileusPhenotype")) {
                        String pileusPhenotype = sampleInfoDto1.getPileusPhenotype();
                        stringBuilder.append(!pileusPhenotype.equals("") ? pileusPhenotype : "-");
                    } else if (titleItem.equals("pileusColor")) {
                        String pileusColor = sampleInfoDto1.getPileusColor();
                        stringBuilder.append(!pileusColor.equals("") ? pileusColor : "-");
                    } else if (titleItem.equals("stipePhenotype")) {
                        String stipePhenotype = sampleInfoDto1.getStipePhenotype();
                        stringBuilder.append(!stipePhenotype.equals("") ? stipePhenotype : "-");
                    } else if (titleItem.equals("stipeColor")) {
                        String stipeColor = sampleInfoDto1.getStipeColor();
                        stringBuilder.append(!stipeColor.equals("") ? stipeColor : "-");
                    } else if (titleItem.equals("fruitbodyColor")) {
                        String fruitbodyColor = sampleInfoDto1.getFruitbodyColor();
                        stringBuilder.append(!fruitbodyColor.equals("") ? fruitbodyColor : "-");
                    } else if (titleItem.equals("fruitbodyType")) {
                        String fruitbodyType = sampleInfoDto1.getFruitbodyType();
                        stringBuilder.append(!fruitbodyType.equals("") ? fruitbodyType : "-");
                    } else if (titleItem.equals("illumination")) {
                        String illumination = sampleInfoDto1.getIllumination();
                        stringBuilder.append(!illumination.equals("") ? illumination : "-");
                    } else if (titleItem.equals("collarium")) {
                        String collarium = sampleInfoDto1.getCollarium();
                        stringBuilder.append(!collarium.equals("") ? collarium : "-");
                    } else if (titleItem.equals("volva")) {
                        String volva = sampleInfoDto1.getVolva();
                        stringBuilder.append(!volva.equals("") ? volva : "-");
                    } else if (titleItem.equals("velum")) {
                        String velum = sampleInfoDto1.getVelum();
                        stringBuilder.append(!velum.equals("") ? velum : "-");
                    } else if (titleItem.equals("sclerotium")) {
                        String sclerotium = sampleInfoDto1.getSclerotium();
                        stringBuilder.append(!sclerotium.equals("") ? sclerotium : "-");
                    } else if (titleItem.equals("strainMedium")) {
                        String strainMedium = sampleInfoDto1.getStrainMedium();
                        stringBuilder.append(!strainMedium.equals("") ? strainMedium : "-");
                    } else if (titleItem.equals("mainSubstrate")) {
                        String mainSubstrate = sampleInfoDto1.getMainSubstrate();
                        stringBuilder.append(!mainSubstrate.equals("") ? mainSubstrate : "-");
                    } else if (titleItem.equals("afterRipeningStage")) {
                        String afterRipeningStage = sampleInfoDto1.getAfterRipeningStage();
                        stringBuilder.append(!afterRipeningStage.equals("") ? afterRipeningStage : "-");
                    } else if (titleItem.equals("primordialStimulationFruitbody")) {
                        String primordialStimulationFruitbody = sampleInfoDto1.getPrimordialStimulationFruitbody();
                        stringBuilder.append(!primordialStimulationFruitbody.equals("") ? primordialStimulationFruitbody : "-");
                    } else if (titleItem.equals("reproductiveMode")) {
                        String reproductiveMode = sampleInfoDto1.getReproductiveMode();
                        stringBuilder.append(!reproductiveMode.equals("") ? reproductiveMode : "-");
                    } else if (titleItem.equals("lifestyle")) {
                        String lifestyle = sampleInfoDto1.getLifestyle();
                        stringBuilder.append(!lifestyle.equals("") ? lifestyle : "-");
                    } else if (titleItem.equals("preservation")) {
                        String preservation = sampleInfoDto1.getPreservation();
                        stringBuilder.append(!preservation.equals("") ? preservation : "-");
                    } else if (titleItem.equals("domestication")) {
                        String domestication = sampleInfoDto1.getDomestication();
                        stringBuilder.append(!domestication.equals("") ? domestication : "-");
                    } else if (titleItem.equals("nuclearPhase")) {
                        String nuclearPhase = sampleInfoDto1.getNuclearPhase();
                        stringBuilder.append(!nuclearPhase.equals("") ? nuclearPhase : "-");
                    } else if (titleItem.equals("matingType")) {
                        String matingType = sampleInfoDto1.getMatingType();
                        stringBuilder.append(!matingType.equals("") ? matingType : "-");
                    }
                    stringBuilder.append(",");
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
