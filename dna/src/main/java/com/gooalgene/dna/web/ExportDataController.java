package com.gooalgene.dna.web;

import com.gooalgene.common.Page;
import com.gooalgene.dna.dao.DNARunDao;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.service.DNAGensService;
import com.gooalgene.dna.service.DNARunService;
import com.gooalgene.dna.service.SNPService;
import com.gooalgene.dna.util.Json2DnaRunDto;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.Tools;
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
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuyan on 2017/11/13
 *
 */

@RestController
public class ExportDataController {

    private final static Logger logger= LoggerFactory.getLogger(ExportDataController.class);
    @Autowired
    private DNARunService dnaRunService;

    @Autowired
    private DNARunDao dnaRunDao;
    @Autowired
    private SNPService snpService;
    @Autowired
    private DNAGensService dnaGensService;

    private static List<String> dnaList=new ArrayList<String>();

    //用于群体信息数据导出

    @RequestMapping(value = "/export", method = RequestMethod.GET,produces ="application/json")
    @ResponseBody
    public String exportData(HttpServletRequest request) throws IOException {
        //接收的参数 title 表头信息  condition 表头的筛选条件
        String choices=request.getParameter("titles");
        String temp=request.getParameter("condition");
        JSONObject object=null;
        //dnaRunDto用来存储表头筛选的条件
        DnaRunDto dnaRunDto = null;
        if(temp!=null&&!temp.equals("")){
            object=JSONObject.fromObject(temp);
            dnaRunDto = Json2DnaRunDto.json2DnaRunDto(object);
        }else {
            dnaRunDto=new DnaRunDto();
        }

        String titles="";
        //此处condition 用来表示表头信息
        String[] condition=null;

        if(choices!=null&&!choices.equals("")){
             titles=choices.substring(0, choices.length() - 1);
             condition=titles.split(",");
        }else{
             condition=",".split(",");
        }
        String fileName="";

        //生成文件名
        if(condition.length>5){
            for (int i=0;i<5;i++){
                fileName+=condition[i]+"-";
            }
        }else {
            for(int j=0;j<condition.length;j++){
                fileName+=condition[j]+"-";
            }
        }

        fileName+= UUID.randomUUID()+".csv";

        String filePath=request.getSession().getServletContext().getRealPath("/")+"tempFile\\";
        String csvStr="";
        List<DNARun> result=dnaRunDao.getListByCondition(dnaRunDto);

        //使用csv进行导出
        if(choices==null||choices.equals("")){
            csvStr="请正确选择表格内容";
        }else {
            csvStr=createCsvStr(result,condition);
        }
        File tempfile=new File(filePath+fileName);
        if (!tempfile.getParentFile().exists()){
                 if (!tempfile.getParentFile().mkdirs()){
                     return "文件目录创建失败";
                 }
        }
        FileOutputStream tempFile=new FileOutputStream(tempfile);
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
        if (serverPort != 80 && serverPort != 443){
            builder.append(":").append(serverPort);
        }
        builder.append(contextPath);
        String path=builder.toString()+"/tempFile/"+fileName;
        logger.info(path);
        return JsonUtils.Bean2Json(path);
    }
    //调整表头显示
    private static Map<String, String> changeCloumn2Web() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("species", "Species");
        map.put("sampleName", "Sample Name");
        map.put("cultivar", "Cultivar");
        map.put("locality", "Locality");
        map.put("protein", "Protein");
        map.put("oil", "Oil");
        map.put("linoleic", "Linoleic");
        map.put("linolenic", "Linolenic");
        map.put("oleic", "Oleic");
        map.put("palmitic", "Palmitic");
        map.put("stearic", "Stearic");
        map.put("height", "Height");
        map.put("flowerColor", "Flower Color");
        map.put("hilumColor", "Hilum Color");
        map.put("podColor",   "Pod Color");
        map.put("pubescenceColor", "Pubescence Color");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("cotyledonColor", "Cotyledon Color");
        map.put("weightPer100seeds", "Weight (g) per 100 Seeds");
        map.put("seedCoatColor", "Seed Coat Color");
        map.put("upperLeafletLength", "upper Leaflet Length");
        map.put("maturityDate", "Maturity Date");
        map.put("yield", "Yield(Mg/ha)");
        map.put("population","Group");
        return map;
    }
    //将列表中的数据  生成csv的格式
    /**
     *@param  result 要导出的内容
     *@param  titles  表头
     */
    public static String  createCsvStr(List<DNARun> result,String[] titles){
        StringBuilder sb=new StringBuilder();
        Map<String,Integer> map=new ConcurrentHashMap<>();
        Map<String,String> titleMap=changeCloumn2Web();
        int len=titles.length;
        for(int i=0;i<len;i++){
            String title=titles[i];
            sb.append(titleMap.get(title));
            if (i!=len-1){
                sb.append(",");
            }
            if (i==len-1){
                sb.append("\n");
            }
            map.put(titles[i],i);
        }

        int size=result.size();
        for(int j=0;j<size;j++){
            DNARun dnaRun=result.get(j);
            //品种名
            if(map.containsKey("cultivar")){
                String cultivar=dnaRun.getCultivar();
                dnaList.add(cultivar!=null?cultivar:"");
                sb.append(cultivar!=null?cultivar:" ").append(",");
            }
            //组别
            if(map.containsKey("population")){
                String group=dnaRun.getGroup();
                dnaList.add(group);
                sb.append(group!=null?group:"").append(",");
            }
            //物种
            if(map.containsKey("species")){
                String species=dnaRun.getSpecies();
                dnaList.add(species!=null?species:"");
                sb.append(species!=null?species:" ").append(",");
            }

            //地理位置
            if(map.containsKey("locality")){
                String locality=dnaRun.getLocality();
                dnaList.add(locality != null ? locality : "");
                if(locality!=null&&locality.contains(",")){
                    locality=locality.replaceAll(",","，");
                }
                sb.append(locality!=null?locality:" ").append(",");
            }

            //样本名
            if(map.containsKey("sampleName")){
                String sampleName=dnaRun.getSampleName();
                dnaList.add(sampleName!=null?sampleName:"");
                sb.append(sampleName!=null?sampleName:" ").append(",");
            }
            //百粒重
            if (map.containsKey("weightPer100seeds")){
                float weight=dnaRun.getWeightPer100seeds();
                dnaList.add(String.valueOf(weight)!=null?String.valueOf(weight):"");
                sb.append(weight!=0?String.valueOf(weight):" ").append(",");
            }
            //蛋白质含量
            if(map.containsKey("protein")){
                float protein=dnaRun.getProtein();
                dnaList.add(String.valueOf(protein)!=null?String.valueOf(protein):"");
                sb.append(protein!=0?String.valueOf(protein):"0").append(",");
            }
            //含油量
            if(map.containsKey("oil")){
                float oil=dnaRun.getOil();
                dnaList.add(String.valueOf(oil)!=null?String.valueOf(oil):"");
                sb.append(oil!=0?String.valueOf(oil):"0").append(",");
            }
            //成熟期
            if (map.containsKey("maturityDate")){
                String maturityGroup=dnaRun.getMaturityDate();
                dnaList.add(String.valueOf(maturityGroup)!=null?String.valueOf(maturityGroup):"");
                sb.append(maturityGroup!=null?maturityGroup:"").append(",");
            }
            //株高
            if (map.containsKey("height")){
                float height=dnaRun.getHeight();
                dnaList.add(String.valueOf(height)!=null?String.valueOf(height):"");
                sb.append(height!=0?String.valueOf(height):"").append(",");
            }

            //种皮色
            if (map.containsKey("seedCoatColor")){
                String seedCoatColor=dnaRun.getSeedCoatColor();
                dnaList.add(seedCoatColor!=null?seedCoatColor:"");
                sb.append(seedCoatColor!=null?seedCoatColor:"").append(",");
            }
            //种脐色
            if (map.containsKey("hilumColor")){
                String hilumColor=dnaRun.getHilumColor();
                dnaList.add(hilumColor!=null?hilumColor:"");
                sb.append(hilumColor!=null?hilumColor:"").append(",");
            }
            //子叶色
            if (map.containsKey("cotyledonColor")){
                String cotyledonColor=dnaRun.getCotyledonColor();
                dnaList.add(cotyledonColor!=null?cotyledonColor:"");
                sb.append(cotyledonColor!=null?cotyledonColor:"").append(",");
            }

            //花色
            if (map.containsKey("flowerColor")){
                String flowerColor= dnaRun.getFlowerColor();
                dnaList.add(flowerColor!=null?flowerColor:"");
                sb.append(flowerColor!=null?flowerColor:"").append(",");
            }
            //荚色
            if (map.containsKey("podColor")){
                String podColor=dnaRun.getPodColor();
                dnaList.add(podColor!=null?podColor:"");
                sb.append(podColor!=null?podColor:"").append(",");
            }
            //茸毛色
            if (map.containsKey("pubescenceColor")){
                String pubescenceColor=dnaRun.getPubescenceColor();
                dnaList.add(pubescenceColor!=null?pubescenceColor:"");
                sb.append(pubescenceColor!=null?pubescenceColor:"").append(",");
            }
            //产量
            if (map.containsKey("yield")){
                float yield=dnaRun.getYield();
                dnaList.add(String.valueOf(yield)!=null?String.valueOf(yield):"");
                sb.append(yield!=0?String.valueOf(yield):"").append(",");
            }
            //顶端小叶长度
            if (map.containsKey("upperLeafletLength")){
                float upperLeafletLength=dnaRun.getUpperLeafletLength();
                dnaList.add(String.valueOf(upperLeafletLength)!=null?String.valueOf(upperLeafletLength):"");
                sb.append(upperLeafletLength!=0?String.valueOf(upperLeafletLength):"").append(",");
            }
            //脂肪酸的内容
            //亚油酸
            if(map.containsKey("linoleic")){
                float linoleic=dnaRun.getLinoleic();
                dnaList.add(String.valueOf(linoleic)!=null?String.valueOf(linoleic):"");
                sb.append(linoleic!=0?String.valueOf(linoleic):"0").append(",");
            }
            //亚麻酸
            if (map.containsKey("linolenic")){
                float linolenic=dnaRun.getLinolenic();
                dnaList.add(String.valueOf(linolenic)!=null?String.valueOf(linolenic):"");
                sb.append(linolenic!=0?String.valueOf(linolenic):"").append(",");
            }
            //油酸
            if (map.containsKey("oleic")){
                float oleic=dnaRun.getOleic();
                dnaList.add(String.valueOf(oleic)!=null?String.valueOf(oleic):"");
                sb.append(oleic!=0?String.valueOf(oleic):"").append(",");
            }

            //软脂酸
            if (map.containsKey("palmitic")){
                float palmitic=dnaRun.getPalmitic();
                dnaList.add(String.valueOf(palmitic)!=null?String.valueOf(palmitic):"");
                sb.append(palmitic!=0?String.valueOf(palmitic):" ").append(",");
            }
            //硬脂酸
            if (map.containsKey("stearic")){
                float stearic=dnaRun.getStearic();
                dnaList.add(String.valueOf(stearic)!=null?String.valueOf(stearic):"");
                sb.append(stearic!=0?String.valueOf(stearic):" ").append(",");
            }





            //前端数据不包含部分
            //编号
            if(map.containsKey("run")){
                String run=dnaRun.getRunNo();
                dnaList.add(run);
                sb.append(run!=null?run:"").append(",");
            }

            //品种名称
            if(map.containsKey("plantName")){
                String plantName=dnaRun.getPlantName();
                dnaList.add(String.valueOf(plantName));
                sb.append(plantName!=null?plantName:"").append(",");
            }

            sb.append("\n");

        }
        return sb.toString();
    }


    //用于SNP result页面的信息  searchInRegion 所在页面

    private static final Integer EXPORT_NUM = 10000;//默认最大导出10000条记录


    @RequestMapping("/dna/dataExport")
    @ResponseBody
    public void searchResultExport(HttpServletRequest request, HttpServletResponse response) {
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
                    String temp=request.getParameter("total");       //获取数据的总数
                    Page<DNARun> page = new Page<DNARun>(request, response);
                    int total=0;
                    if(temp!=null&&!temp.equals("")) {
                        total = Integer.parseInt(temp);
                    }else{
                        total=EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    result = snpService.searchSNPinRegion(type, ctype, chr, startPos, endPos, group, page);
                    content = serialList(type,result,columns.split(","));

                } else if ("GENE".equals(model)) {
                    String type = request.getParameter("type");
                    fileName += "_" + type;
                    String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
                    String gene = request.getParameter("gene");
                    String upstream = request.getParameter("upstream");
                    String downstream = request.getParameter("downstream");
                    fileName += "_" + gene + "Stream:[" + upstream + "," + downstream + "]_" + ctype;
                    String group = request.getParameter("group");
                    String temp=request.getParameter("total");       //获取数据的总数
                    DNAGens dnaGens = dnaGensService.findByGene(gene);
                    if (dnaGens!= null) {
                        long start = dnaGens.getGeneStart();
                        long end = dnaGens.getGeneEnd();
                        logger.info("gene:" + gene + ",start:" + start + ",end:" + end);
                        if (StringUtils.isNoneBlank(upstream)) {
                            start = start - Long.valueOf(upstream);
                        }
                        if (StringUtils.isNoneBlank(downstream)) {
                            end = end + Long.valueOf(downstream);
                        }
                        upstream = String.valueOf(start);
                        downstream = String.valueOf(end);
                    }
                    logger.info("gene:" + gene + ",upstream:" + upstream + ",downstream:" + downstream);
                    Page<DNAGens> page = new Page<DNAGens>(request, response);
                    int total=0;
                    if(temp!=null&&!temp.equals("")) {
                        total = Integer.parseInt(temp);
                    }else{
                        total=EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    result=snpService.searchSNPinGene(type,ctype,gene,upstream,downstream,group,page);
                    content = serialList(type, result, columns.split(","));
                } else if ("SAMPLES".equals(model)) {
                    //增加两个字段  flag 和 cultivars
                    String group = request.getParameter("group");
                    Page<DNARun> page = new Page<DNARun>(request, response);
                    String temp=request.getParameter("total");       //获取数据的总数
                    //判断自定义群体使用的是按Group查询还是按品种名查询   group 表示按群体查询   cultivar表示按品种名查询
                    String flag=request.getParameter("flag");
                    int total=0;
                    if(temp!=null&&!temp.equals("")) {
                        total = Integer.parseInt(temp);
                    }else{
                        total=EXPORT_NUM;
                    }
                    page.setPageSize(total);
                    if(flag.equals("group")){
                        result = dnaRunService.queryDNARunByGroup(group, page);
                    } else{
                        String cultivars=request.getParameter("cultivar");
                        String[] culltivarsArray=cultivars.split(",");
                        Map tempResult=new HashMap();
                        List<String> cultivarList;
                        cultivarList=Arrays.asList(culltivarsArray);
                        List<DNARun> dnaRunList=dnaRunDao.getByCultivar(cultivarList);
                        JSONArray data=new JSONArray();
                        for(DNARun dnaRun:dnaRunList){
                            data.add(dnaRun.toJSON());
                        }
                        tempResult.put("data",data);
                        result=tempResult;
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
        DecimalFormat df=new DecimalFormat();
        df.applyPattern("#0.00%");
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        List<String> titleList=new ArrayList<String>();
        int len = titles.length;
        if("SNP".equals(model)) {
            for (int i = 0; i < len; i++) {
                if(titles[i].equals("weightPer100seeds")){
                    sb.append(titles[i]+"(g)");
                }
                else if(titles[i].equals("oil")||titles[i].equals("protein")||titles[i].equals("linolenic")||titles[i].equals("linoleic")||titles[i].equals("oleic")||titles[i].equals("palmitic")||titles[i].equals("stearic")){
                    sb.append(titles[i]+"(%)");
                }
                else if(titles[i].equals("floweringDate")||titles[i].equals("maturityDate")){
                    sb.append(titles[i]+"(month day)");
                }
                else if(titles[i].equals("height")){
                    sb.append(titles[i]+"(cm)");
                }
                else if(titles[i].equals("yield")){
                    sb.append(titles[i]+"(t/ha)");
                }
                else if(titles[i].equals("upperLeafletLength")){
                    sb.append(titles[i]+"(mm)");
                }else if(titles[i].equals("FrequencyofMajorAllele")){
                    titles[i]="frequencyOfMajorAllele";
                    sb.append("frequencyOfMajorAllele");
                }else if(titles[i].equals("FrequencyofMinorAllele")){
                    titles[i]="frequencyOfMinorAllele";
                    sb.append("frequencyOfMinorAllele");
                }
                else {
                    sb.append(titles[i]);
                }

                if (i != (len - 1)) {
                    sb.append(",");
                } else {
                    sb.append(",");
                    sb.append("GenoType");
                    sb.append("\n");
                }
                titleList.add(titles[i]);
                map.put(titles[i], i);
            }
            titleList.add("GenoType");
            map.put("GenoType",len);
        }else {
            for (int i = 0; i < len; i++) {
                if(titles[i].equals("weightPer100seeds")){
                    sb.append(titles[i]+"(g)");
                }
                else if(titles[i].equals("oil")||titles[i].equals("protein")||titles[i].equals("linolenic")||titles[i].equals("linoleic")||titles[i].equals("oleic")||titles[i].equals("palmitic")||titles[i].equals("stearic")){
                    sb.append(titles[i]+"(%)");
                }
                else if(titles[i].equals("floweringDate")||titles[i].equals("maturityDate")){
                    sb.append(titles[i]+"(month day)");
                }
                else if(titles[i].equals("height")){
                    sb.append(titles[i]+"(cm)");
                }
                else if(titles[i].equals("yield")){
                    sb.append(titles[i]+"(t/ha)");
                }
                else if(titles[i].equals("upperLeafletLength")){
                    sb.append(titles[i]+"(mm)");
                }else if(titles[i].equals("FrequencyofMajorAllele")){
                    titles[i]="frequencyOfMajorAllele";
                    sb.append("frequencyOfMajorAllele");
                }else if(titles[i].equals("FrequencyofMinorAllele")){
                    titles[i]="frequencyOfMinorAllele";
                    sb.append("frequencyOfMinorAllele");
                }else {
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
                if (map.containsKey("species")) {
                    String species = one.getString("species");
                    sb.append((species != null ? species : "")).append(",");
                }
                if (map.containsKey("locality")) {
                    String locality = one.getString("locality");
                    sb.append((locality != null ?locality.replace(",","，") : "")).append(",");
                }
                if (map.containsKey("sampleName")) {
                    String sampleName = one.getString("sampleName");
                    sb.append((sampleName != null ? sampleName : "")).append(",");
                }
                if (map.containsKey("cultivar")) {
                    String cultivar = one.getString("cultivar");
                    sb.append((cultivar != null ? cultivar : "")).append(",");
                }
                if (map.containsKey("weightPer100seeds")) {
                    Object weightPer100seeds = one.get("weightPer100seeds");
                    sb.append((weightPer100seeds != null ? weightPer100seeds : "")).append(",");
                }
                if (map.containsKey("oil")) {
                    Object oil = one.get("oil");
                    sb.append((oil != null ? oil : "")).append(",");
                }
                if (map.containsKey("protein")) {
                    Object protein = one.get("protein");
                    sb.append((protein != null ? protein : "")).append(",");
                }
                if (map.containsKey("floweringDate")) {
                    Object floweringDate = one.get("floweringDate");
                    sb.append((floweringDate != null ? floweringDate : "")).append(",");
                }
                if (map.containsKey("maturityDate")) {
                    Object maturityDate = one.get("maturityDate");
                    sb.append((maturityDate != null ? maturityDate : "")).append(",");
                }
                if (map.containsKey("height")) {
                    Object height = one.get("height");
                    sb.append((height != null ? height : "")).append(",");
                }
                if (map.containsKey("seedCoatColor")) {
                    Object seedCoatColor = one.get("seedCoatColor");
                    sb.append((seedCoatColor != null ? seedCoatColor : "")).append(",");
                }
                if (map.containsKey("hilumColor")) {
                    Object hilumColor = one.get("hilumColor");
                    sb.append((hilumColor != null ? hilumColor : "")).append(",");
                }
                if (map.containsKey("cotyledonColor")) {
                    Object cotyledonColor = one.get("cotyledonColor");
                    sb.append((cotyledonColor != null ? cotyledonColor : "")).append(",");
                }
                if (map.containsKey("flowerColor")) {
                    Object flowerColor = one.get("flowerColor");
                    sb.append((flowerColor != null ? flowerColor : "")).append(",");
                }
                if (map.containsKey("podColor")) {
                    Object podColor = one.get("podColor");
                    sb.append((podColor != null ? podColor : "")).append(",");
                }
                if (map.containsKey("pubescenceColor")) {
                    Object pubescenceColor = one.get("pubescenceColor");
                    sb.append((pubescenceColor != null ? pubescenceColor : "")).append(",");
                }
                if (map.containsKey("yield")) {
                    Object maturityDate = one.get("yield");
                    sb.append((maturityDate != null ? maturityDate : "")).append(",");
                }
                if (map.containsKey("upperLeafletLength")) {
                    Object upperLeafletLength = one.get("upperLeafletLength");
                    sb.append((upperLeafletLength != null ? upperLeafletLength : "")).append(",");
                }
                if (map.containsKey("linoleic")) {
                    Object linoleic = one.get("linoleic");
                    sb.append((linoleic != null ? linoleic : "")).append(",");
                }
                if (map.containsKey("linolenic")) {
                    Object linolenic = one.get("linolenic");
                    sb.append((linolenic != null ? linolenic : "")).append(",");
                }
                if (map.containsKey("oleic")) {
                    Object oleic = one.get("oleic");
                    sb.append((oleic != null ? oleic : "")).append(",");
                }
                if (map.containsKey("palmitic")) {
                    Object palmitic = one.get("palmitic");
                    sb.append((palmitic != null ? palmitic : "")).append(",");
                }
                if (map.containsKey("stearic")) {
                    Object stearic = one.get("stearic");
                    sb.append((stearic != null ? stearic : "")).append(",");
                }
                sb.append("\n");
            }
        } else if ("SNP".equals(model)) {
            List<SNPDto> data= (List<SNPDto>) result.get("data");
            int size=data.size();
            for (int i = 0; i < size; i++) {
                SNPDto snpDto= data.get(i);
                JSONArray freq= (JSONArray) snpDto.getFreq();
                for(String titleItem:titleList){
                    if(titleItem.equals("SNPID")){
                        sb.append(snpDto.getId()).append(",");
                    }else if(titleItem.equals("consequenceType")){
                        sb.append(snpDto.getConsequencetype()).append(",");
                    }else if(titleItem.equals("chromosome")){
                        sb.append(snpDto.getChr()).append(",");
                    }else if(titleItem.equals("position")){
                        sb.append(snpDto.getPos()).append(",");
                    }else if(titleItem.equals("reference")){
                        sb.append(snpDto.getRef()).append(",");
                    }else if(titleItem.equals("majorAllele")){
                        sb.append(snpDto.getMajorallen()).append(",");
                    }else if(titleItem.equals("minorAllele")){
                        sb.append(snpDto.getMinorallen()).append(",");
                    }else if(titleItem.equals("frequencyOfMajorAllele")){
                        sb.append(df.format(snpDto.getMajor())).append(",");
                    }else if(titleItem.equals("frequencyOfMinorAllele")){
                        sb.append(df.format(snpDto.getMinor())).append(",");
                    }else if(titleItem.equals("GenoType")){
                        Map hashMap;
                        hashMap=snpDto.getGeneType();
                        String RR=String.valueOf(df.format((double)hashMap.get("RefAndRefPercent")));
                        String tRA=String.valueOf(df.format((double)hashMap.get("totalRefAndAltPercent")));
                        String tAA=String.valueOf(df.format((double)hashMap.get("totalAltAndAltPercent")));
                        String ref=snpDto.getRef();
                        String alt=snpDto.getAlt();
                        String ra=ref+alt;
                        sb.append(ref+ref+":"+RR+"，"+alt+alt+":"+tAA+"，"+ra+":"+tRA);
                    }else{
                        for(int j=0;j<freq.size();j++){
                            JSONObject groupFreq=freq.getJSONObject(j);
                            String name="fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String major=groupFreq.getString("major");
                            float majorValue=Float.parseFloat(major);
                            if(titleItem.equals(name)){
                                sb.append(df.format(majorValue)).append(",");
                            }
                        }
                        for(int j=0;j<freq.size();j++){
                            JSONObject groupFreq=freq.getJSONObject(j);
                            String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String minor = groupFreq.getString("minor");
                            float minorValue=Float.parseFloat(minor);
                            if(titleItem.equals(name)){
                                sb.append(df.format(minorValue)).append(",");
                            }
                        }
                    }
                }
                sb.append("\n");
            }
        } else if ("INDEL".equals(model)) {
            List<SNPDto> data= (List<SNPDto>) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {
                SNPDto snpDto= data.get(i);
                JSONArray freq= (JSONArray) snpDto.getFreq();
                for(String titleItem:titleList){
                    if(titleItem.equals("INDELID")){
                        sb.append(snpDto.getId()).append(",");
                    }else if(titleItem.equals("consequenceType")){
                        sb.append(snpDto.getConsequencetype()).append(",");
                    }else if(titleItem.equals("chromosome")){
                        sb.append(snpDto.getChr()).append(",");
                    }else if(titleItem.equals("position")){
                        sb.append(snpDto.getPos()).append(",");
                    }else if(titleItem.equals("reference")){
                        sb.append(snpDto.getRef()).append(",");
                    }else if(titleItem.equals("majorAllele")){
                        sb.append(snpDto.getMajorallen()).append(",");
                    }else if(titleItem.equals("minorAllele")){
                        sb.append(snpDto.getMinorallen()).append(",");
                    }else if(titleItem.equals("frequencyOfMajorAllele")){
                        sb.append(df.format(snpDto.getMajor())).append(",");
                    }else if(titleItem.equals("frequencyOfMinorAllele")){
                        sb.append(df.format(snpDto.getMinor())).append(",");
                    }else{
                        for(int j=0;j<freq.size();j++){
                            JSONObject groupFreq=freq.getJSONObject(j);
                            String name="fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String major=groupFreq.getString("major");
                            float majorValue=Float.parseFloat(major);
                            if(titleItem.equals(name)){
                                sb.append(df.format(majorValue)).append(",");
                            }
                        }
                        for(int j=0;j<freq.size();j++){
                            JSONObject groupFreq=freq.getJSONObject(j);
                            String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                            String minor = groupFreq.getString("minor");
                            float minorValue=Float.parseFloat(minor);
                            if(titleItem.equals(name)){
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



}
