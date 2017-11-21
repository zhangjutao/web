package com.gooalgene.dna.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.service.*;
import com.gooalgene.common.service.SMTPService;
import com.gooalgene.utils.ResultUtil;
import com.gooalgene.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

@RestController
@RequestMapping("/dna")
public class SNPController {

    Logger logger = LoggerFactory.getLogger(SNPController.class);

    @Autowired
    private DNARunService dnaRunService;

    @Autowired
    private IndexExplainService indexExplainService;

    @Autowired
    private DNAGensService dnaGensService;

    @Autowired
    private SNPService snpService;

    @Autowired
    private DNAMongoService dnaMongoService;

    @Autowired
    private DNAGroupsService dnaGroupsService;


    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("mDNA/dna-index");
        model.addObject("dnaDetail", indexExplainService.queryByType("dna").getDetail());
        return model;
    }
    @RequestMapping("/populationInfos")
    public ModelAndView populationInfos(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("population/infos");
        return model;
    }

    /**
     * 按基因条件搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/queryByGene")
    @ResponseBody
    public Map QueryByGene(HttpServletRequest request, HttpServletResponse response) {
        String gene = request.getParameter("gene");
        logger.info("Gene:" + gene);
        Page<DNAGens> page = new Page<DNAGens>(request, response);
        return dnaGensService.queryDNAGenesByGenes(gene, page);
    }

    /**
     * 按基因条件搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/queryByGroup")
    @ResponseBody
    public Map QueryByGroup(HttpServletRequest request, HttpServletResponse response) {
        String group = request.getParameter("group");
        logger.info("QueryByGroup:" + group);
        Page<DNARun> page = new Page<DNARun>(request, response);
        return dnaRunService.queryDNARunByGroup(group, page);
    }

    /**
     * 按基因条件搜索
     *
     * @return
     */
    @RequestMapping(value = "/condition",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getByExample(@RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                 @RequestParam(value = "isPage",required = false)String isPage,
                                 DnaRunDto dnaRunDto) {
            PageInfo<DNARun> dnaRunPageInfo=dnaRunService.getByCondition(dnaRunDto,pageNum,pageSize,isPage);
            return ResultUtil.success(dnaRunPageInfo);

    }

    /**
     * 查询默认群体
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/defaultGroup")
    @ResponseBody
    public JSONArray QueryDefaultGroup(HttpServletRequest request, HttpServletResponse response) {
        return dnaGroupsService.searchAll();
    }

    /**
     * 根据runNo查找dnaRun
     * @return
     */
    @RequestMapping(value = "/dnaRuns",method = RequestMethod.GET)
    @ResponseBody
    public ResultVO getByRunNos(@RequestParam("runNos") List<String> runNos) {
        return ResultUtil.success(dnaRunService.getByRunNos(runNos));
    }

    /**
     * 按群组条件搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchSNPinRegion")
    @ResponseBody
    public Map queryBySNP(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");//区分snp和indel数据
        String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
        String chr = request.getParameter("chromosome");
        String startPos = request.getParameter("start");
        String endPos = request.getParameter("end");
        String group = request.getParameter("group");
//      String conditions = request.getParameter("conditions");
        logger.info("queryBy " + type + " with ctype:" + ctype + ",chr:" + chr + ",startPos:" + startPos + ",endPos:" + endPos + ",group:" + group);
        Page<DNARun> page = new Page<DNARun>(request, response);
        Map result=snpService.searchSNPinRegion(type, ctype, chr, startPos, endPos, group, page);
        return result;
    }

    /**
     * 点选SNPId或INDELId时根据相应id进行样本相关信息查询
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/findSampleById")
    @ResponseBody
    public ResultVO genetypePercentById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        if (id == null) {
            return ResultUtil.error(200, "未拿到id的值");
        }
            Map result = snpService.findSampleById(id);
        return ResultUtil.success(result);
    }

    /**
     * 按群组条件搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchSNPinGene")
    @ResponseBody
    public Map queryByGene(HttpServletRequest request, HttpServletResponse response) {
        //todo
        String type = request.getParameter("type");//区分snp和indel数据
        String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
        String gene = request.getParameter("gene");
        String upstream = request.getParameter("upstream");
        String downstream = request.getParameter("downstream");
        String group = request.getParameter("group");
//      String conditions = request.getParameter("conditions");
        DNAGens dnaGens = dnaGensService.findByGene(gene);
        logger.info("queryBy " + type + " Gene with ctype:" + ctype + ",gene:" + gene + ",upstream:" + upstream + ",downstream:" + downstream + ",group:" + group);
        if (dnaGens != null) {
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
        return snpService.searchSNPinGene2(type, ctype, gene, upstream, downstream, group, page);
    }


    private static final Integer EXPORT_NUM = 10000;//默认最大导出10000条记录


    @RequestMapping("/dataExport")
    @ResponseBody
    public void searchResultExport(HttpServletRequest request, HttpServletResponse response) {
        String model = request.getParameter("model");//区分导出数据类型：regin、gene、samples
        String columns = request.getParameter("choices");//表头列
        logger.info("model:" + model + ",colums:" + columns);
        String content = "";
        String fileName = model;
        if (StringUtils.isNoneBlank(columns)) {
            if (StringUtils.isNoneBlank(model)) {
                Map result = new HashMap();
                if ("REGION".equals(model)) {
                    String type = request.getParameter("type");
                    fileName += "_" + type;
                    String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
                    String chr = request.getParameter("chromosome");
                    String startPos = request.getParameter("start");
                    String endPos = request.getParameter("end");
                    fileName += "_" + chr + "_Position:[" + startPos + "," + endPos + "]_" + ctype;
                    String group = request.getParameter("group");
//                    fileName += "_" + group;
                    Page<DNARun> page = new Page<DNARun>(request, response);
                    page.setPageSize(EXPORT_NUM);
                    result = snpService.searchSNPinRegion(type, ctype, chr, startPos, endPos, group, page);
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
//                    fileName += "_" + group;
                    DNAGens dnaGens = dnaGensService.findByGene(gene);
                    if (dnaGens != null) {
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
                    page.setPageSize(EXPORT_NUM);

                    //外包的原来是给出最大10000条数据   要不要改
                    result=snpService.searchSNPinGene2(type,ctype,gene,upstream,downstream,group,page);
                    //result = snpService.searchSNPinGene(type, ctype, gene, upstream, downstream, group, page);
                    content = serialList(type, result, columns.split(","));
                } else if ("SAMPLES".equals(model)) {
                    String group = request.getParameter("group");
//                    fileName += "_" + group;
                    Page<DNARun> page = new Page<DNARun>(request, response);
                    page.setPageSize(EXPORT_NUM);
                    result = dnaRunService.queryDNARunByGroup(group, page);
                    content = serialList(model, result, columns.split(","));
                } else {

                }
            } else {
                content = "请选择导出数据类型";
            }
        } else {
            content = "请选择正确的显示表头数据";
        }
        Tools.toDownload(fileName, content, response);
    }

    /**
     * 生成导出内容
     *
     * @param model  数据类型
     * @param result 导出内容
     * @param titles 导出表头
     * @return
     */
    private String serialList(String model, Map result, String[] titles) {
        StringBuilder sb = new StringBuilder();
        Map<String, Integer> map = new HashMap<String, Integer>();
        int len = titles.length;
        for (int i = 0; i < len; i++) {
            sb.append(titles[i]);
            if (i != (len - 1)) {
                sb.append(",");
            } else {
                sb.append("\n");
            }
            map.put(titles[i], i);
        }

       /*
       * 现在的sample和idel还是用的原来的接口  返回的是json类型的数据
       * SNP使用的是新的接口  返回的是对应SNPdto类型的数据
       * 把data 分别移动到判断条件内  根据不同类型 接收不同类型的参数
       * */

        if ("SAMPLES".equals(model)) {
            JSONArray data = (JSONArray) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {

                JSONObject one = data.getJSONObject(i);
                // SNPDto snpDto=data.get(i);
//                if (map.containsKey("ID")) {
//                    sb.append(one.getString("id")).append(",");
//                }
//                if (map.containsKey("runNo")) {
//                    sb.append(one.getString("runNo")).append(",");
//                }
                if (map.containsKey("species")) {
                    String species = one.getString("species");
                    sb.append((species != null ? species : "")).append(",");
                }
                if (map.containsKey("locality")) {
                    String locality = one.getString("locality");
                    sb.append((locality != null ? Tools.getRightContent(locality) : "")).append(",");
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
//                if (map.containsKey("plantName")) {
//                    String plantName = one.getString("plantName");
//                    sb.append((plantName != null ? plantName : "")).append(",");
//                }
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
                 SNPDto snpDto= (SNPDto) data.get(i);
                //JSONObject one = data.getJSONObject(i);
                if (map.containsKey("SNPID")) {
                    sb.append(snpDto.getId()).append(",");
                    //sb.append(one.getString("id")).append(",");
                }
                if (map.containsKey("consequenceType")) {
                    sb.append(snpDto.getConsequencetype()).append(",");
                    //sb.append(one.getString("consequencetype")).append(",");
                }
                if (map.containsKey("chromosome")) {
                     sb.append(snpDto.getChr()).append(",");
                    //sb.append(one.getString("chr")).append(",");
                }
                if (map.containsKey("position")) {
                    sb.append(snpDto.getPos()).append(",");
                    //sb.append(one.getString("pos")).append(",");
                }
                if (map.containsKey("reference")) {
                     sb.append(snpDto.getRef()).append(",");
                   // sb.append(one.getString("ref")).append(",");
                }
                if (map.containsKey("majorAllele")) {
                    sb.append(snpDto.getMajorallen()).append(",");
                    //sb.append(one.getString("majorallen")).append(",");
                }
                if (map.containsKey("minorAllele")) {
                    sb.append(snpDto.getMinorallen()).append(",");
                    //sb.append(one.getString("minorallen")).append(",");
                }
                 JSONArray freq= (JSONArray) snpDto.getFreq();
               // JSONArray freq = one.getJSONArray("freq");
                if (map.containsKey("frequencyOfMajorAllele")) {
                     sb.append(snpDto.getMajor()).append(",");
                     //sb.append(one.getString("major")).append(",");
                    for (int j = 0; j < freq.size(); j++) {
                        JSONObject groupFreq = freq.getJSONObject(j);
                        String name = "fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                        String major = groupFreq.getString("major");
                        if (map.containsKey(name)) {
                            sb.append(major).append(",");
                        }
                    }
                }
                if (map.containsKey("frequencyOfMinorAllele")) {
                    for (int j = 0; j < freq.size(); j++) {
                        JSONObject groupFreq = freq.getJSONObject(j);
                        String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                        String minor = groupFreq.getString("minor");
                        if (map.containsKey(name)) {
                            sb.append(minor).append(",");
                        }
                    }
                }
                sb.append("\n");
            }
        } else if ("INDEL".equals(model)) {
            List<SNPDto> data= (List<SNPDto>) result.get("data");
           // JSONArray data = (JSONArray) result.get("data");
            int size = data.size();
            for (int i = 0; i < size; i++) {
                  SNPDto snpDto=data.get(i);
                //JSONObject one = data.getJSONObject(i);
                if (map.containsKey("INDELID")) {
                      sb.append(snpDto.getId()).append(",");
                  //  sb.append(one.getString("id")).append(",");
                }
                if (map.containsKey("consequenceType")) {
                      sb.append(snpDto.getConsequencetype()).append(",");
                   // sb.append(one.getString("consequencetype")).append(",");
                }
                if (map.containsKey("chromosome")) {
                      sb.append(snpDto.getChr()).append(",");
                    //sb.append(one.getString("chr")).append(",");
                }
                if (map.containsKey("position")) {
                      sb.append(snpDto.getPos()).append(",");
                    //sb.append(one.getString("pos")).append(",");
                }
                if (map.containsKey("reference")) {
                      sb.append(snpDto.getRef()).append(",");
                    //sb.append(one.getString("ref")).append(",");
                }
                if (map.containsKey("majorAllele")) {
                      sb.append(snpDto.getMajorallen()).append(",");
                    //sb.append(one.getString("majorallen")).append(",");
                }
                if (map.containsKey("minorAllele")) {
                      sb.append(snpDto.getMinorallen()).append(",");
                    //sb.append(one.getString("minorallen")).append(",");
                }
                JSONArray freq= (JSONArray) snpDto.getFreq();
                //JSONArray freq = one.getJSONArray("freq");
                if (map.containsKey("frequencyOfMajorAllele")) {
                      sb.append(snpDto.getMajor()).append(",");
                    //sb.append(one.getString("major")).append(",");
                    for (int j = 0; j < freq.size(); j++) {
                        JSONObject groupFreq = freq.getJSONObject(j);
                        String name = "fmajorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                        String major = groupFreq.getString("major");
                        if (map.containsKey(name)) {
                            sb.append(major).append(",");
                        }
                    }
                }
                if (map.containsKey("frequencyOfMinorAllele")) {
                    for (int j = 0; j < freq.size(); j++) {
                        JSONObject groupFreq = freq.getJSONObject(j);
                        String name = "fminorAllelein" + groupFreq.getString("name").replaceAll(",", "_");
                        String minor = groupFreq.getString("minor");
                        if (map.containsKey(name)) {
                            sb.append(minor).append(",");
                        }
                    }
                }
                sb.append("\n");
            }
        } else {

        }
        return sb.toString();
    }


    //    @RequestMapping(value = "/insertSNP")
    @ResponseBody
    public String insertSNP(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "E:\\古奥科技资料\\DNA\\snp.test.tab";
        dnaMongoService.insertSNP(fileName);
        return "success";
    }


    //    @RequestMapping(value = "/insertDNAGenes")
    @ResponseBody
    public String insertDNAGene(HttpServletRequest request, HttpServletResponse response) {
        String efile = "E:\\古奥科技资料\\DNA\\gma2.0_ID_name_function_locus_noscaffold";
        try {
            List<String> list = FileUtils.readLines(new File(efile));
            int len = list.size();
            List<DNAGens> result = new ArrayList<DNAGens>();
            int num = 0;
            for (int i = 1; i < len; i++) {
                String line = list.get(i);
                String[] genes = line.split("\t");
                if (genes.length == 5) {
                    DNAGens dnaGens = new DNAGens();
                    dnaGens.setGeneId(genes[0]);
                    dnaGens.setGeneName(genes[1]);
                    dnaGens.setGeneFunction(genes[2]);
                    dnaGens.setGeneStart(Long.valueOf(genes[3]));
                    dnaGens.setGeneEnd(Long.valueOf(genes[4]));
                    result.add(dnaGens);
                } else {
                    System.out.println("A:" + line);
                }
                if (result.size() == 2000) {
                    dnaGensService.insertBatch(result);
                    num += result.size();
                    result = new ArrayList<DNAGens>();
                }
            }
            if (result.size() > 0) {
                num += result.size();
                dnaGensService.insertBatch(result);
            }
            System.out.println("len:" + len + ",insert size:" + num);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


//    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    @ResponseBody
    public String insert(HttpServletRequest request, HttpServletResponse response) {
        String efile = "F:\\古奥科技\\20171107soyDNA_sampleinfo_withgroupV1.1.xls";
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream(efile));
            //获得指定的表
            HSSFSheet xsheet = workbook.getSheetAt(0);
            //获得数据总行数
            int rowCount = xsheet.getLastRowNum();
            System.out.println("总行数:" + rowCount);
            //逐行读取数据
            int num = 0;
            List<DNARun> list = new ArrayList<DNARun>();
            //前2行不取
            for (int rowIndex = 2; rowIndex <= rowCount; rowIndex++) {
                //获得行对象
                HSSFRow xrow = xsheet.getRow(rowIndex);
                if (xrow != null) {
                    //获得本行中单元格的个数
                    int columnCount = xrow.getLastCellNum();
                    System.out.print(columnCount + "\t");
                    DNARun dnaRun = new DNARun();
                    for (int i = 0; i < 25; i++) {
                        HSSFCell value = xrow.getCell(i);
                        String content = null;
                        if (value != null) {
                            System.out.print("[" + rowIndex + "," + i + "]:" + value + "\t");
                            content = value.toString();
                        } else {
                            System.out.print("null\t");
                        }
                        switch (i) {
                            case 0:
                                dnaRun.setGroup(content);
                                break;
                            case 1:
                                if (content != null) {
                                    dnaRun.setRunNo(content);
                                }
                                break;
                            case 2:
                                if (content != null) {
                                    dnaRun.setSpecies(content);
                                }
                                break;
                            case 3:
                                if (content != null) {
                                    dnaRun.setSampleName(content);
                                }
                                break;
                            case 4:
                                if (content != null) {
                                    dnaRun.setCultivar(content);
                                }
                                break;
                            case 5:
                                if (content != null) {
                                    dnaRun.setPlantName(content);
                                }
                                break;
                            case 6:
                                if (content != null) {
                                    dnaRun.setLocality(content);
                                }
                                break;
                            case 7:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setProtein(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " protein content", e);
                                    }
                                }
                                break;
                            case 8:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setOil(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " oil content", e);
                                    }
                                }
                                break;
                            case 9:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setLinoleic(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " linoleic content", e);
                                    }
                                }
                                break;
                            case 10:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setLinolenic(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " linolenic content", e);
                                    }
                                }
                                break;
                            case 11:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setOleic(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " oleic content", e);
                                    }
                                }
                                break;
                            case 12:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setPalmitic(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " palmitic content", e);
                                    }
                                }
                                break;
                            case 13:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setStearic(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " stearic content", e);
                                    }
                                }
                                break;
                            case 14:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setHeight(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " height content", e);
                                    }
                                }
                                break;
                            case 15:
                                if (content != null) {
                                    dnaRun.setFlowerColor(content);
                                }
                                break;
                            case 16:
                                if (content != null) {
                                    dnaRun.setHilumColor(content);
                                }
                                break;
                            case 17:
                                if (content != null) {
                                    dnaRun.setPodColor(content);
                                }
                                break;
                            case 18:
                                if (content != null) {
                                    dnaRun.setPubescenceColor(content);
                                }
                                break;
                            case 19:
                                if (content != null) {
                                    dnaRun.setSeedCoatColor(content);
                                }
                                break;
                            case 20:
                                if (content != null) {
                                    dnaRun.setCotyledonColor(content);
                                }
                                break;
                            case 21:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setWeightPer100seeds(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " weightPer100seeds content", e);
                                    }
                                }
                                break;
                            case 22:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setUpperLeafletLength(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " UpperLeafletLength content", e);
                                    }
                                }
                                break;
                            case 23:
                                if (content != null) {
                                    dnaRun.setMaturityDate(content);
                                }
                                break;
                            case 24:
                                if (StringUtils.isNoneBlank(content)) {
                                    try {
                                        dnaRun.setYield(Float.parseFloat(content));
                                    } catch (NumberFormatException e) {
                                        logger.error(dnaRun.getRunNo() + " yield content", e);
                                    }
                                }
                                break;
                        }
                    }
                    list.add(dnaRun);
                    System.out.println("\n");
//                    break;
                } else {
                    System.out.println("empty line.");
                }
            }
            dnaRunService.insertBatch(list);
            System.out.println("list size:" + list.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "success";
    }

    /**
     * 进入snp详情页
     */
    @RequestMapping("/snp/info")
    //@ResponseBody
    public ModelAndView getSnpInfo(HttpServletRequest request, HttpServletResponse response,SNP snp) {
        /*List runNos = Arrays.asList(snp.getSamples());
        List<DNARun> dnaRuns=dnaRunService.getByRunNos(runNos);*/
        ModelAndView modelAndView=new ModelAndView("/snpinfo/snpinfo");
        modelAndView.addObject("snp",snp);
        return modelAndView;
    }
}
