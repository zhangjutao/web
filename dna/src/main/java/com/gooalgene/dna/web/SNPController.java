package com.gooalgene.dna.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.authority.Role;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.dto.DNAGenStructureDto;
import com.gooalgene.dna.dto.DnaRunDto;
import com.gooalgene.dna.dto.SNPDto;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.entity.DNARun;
import com.gooalgene.dna.entity.SNP;
import com.gooalgene.dna.entity.result.DNARunSearchResult;
import com.gooalgene.dna.service.*;
import com.gooalgene.common.service.SMTPService;
import com.gooalgene.utils.ResultUtil;
import com.gooalgene.utils.Tools;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

@RestController
@RequestMapping("/dna")
public class SNPController {

    Logger logger = LoggerFactory.getLogger(SNPController.class);

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
    @Autowired
    private DNARunService dnaRunService;
    @Autowired
    private DNAGenStructureService dnaGenStructureService;


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
    @RequestMapping(value = "/queryByGroup", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map QueryByGroup(HttpServletRequest request, HttpServletResponse response) {
        String group = request.getParameter("group");
        logger.info("QueryByGroup:" + group);
        Page<DNARunSearchResult> page = new Page<>(request, response);
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
            logger.info(dnaRunDto.getGroup());
            PageInfo<DNARunSearchResult> dnaRunPageInfo=dnaRunService.getListByConditionWithTypeHandler(dnaRunDto,pageNum,pageSize,isPage);
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
                start = start - Long.valueOf(upstream)-2000<0?0:start - Long.valueOf(upstream)-2000;
            }else {
                start=start-2000<0?0:start-2000;
            }
            if (StringUtils.isNoneBlank(downstream)) {
                end = end + Long.valueOf(downstream)+2000;
            }else {
                end = end +2000;
            }
            upstream = String.valueOf(start);
            downstream = String.valueOf(end);
        }
        logger.info("gene:" + gene + ",upstream:" + upstream + ",downstream:" + downstream);
        Page<DNAGens> page = new Page<DNAGens>(request, response);
        return snpService.searchSNPinGene(type, ctype, gene, upstream, downstream, group, page);
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
     * 在范围中查询所有位点
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchIdAndPosInRegion")
    @ResponseBody
    public ResultVO searchIdAndPosInRegion(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");//区分snp和indel数据
        String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
        String chr = request.getParameter("chromosome");
        String startPos = request.getParameter("start");
        String endPos = request.getParameter("end");
        String group = request.getParameter("group");
//      String conditions = request.getParameter("conditions");
        logger.info("queryBy " + type + " with ctype:" + ctype + ",chr:" + chr + ",startPos:" + startPos + ",endPos:" + endPos + ",group:" + group);
        //Page<DNARun> page = new Page<DNARun>(request, response);
        Map result=Maps.newHashMap();
        List<SNP> snps=dnaMongoService.searchIdAndPosInRegion(type, ctype, chr, startPos, endPos, null);
        List<SNPDto> snpDtos=Lists.newArrayList();
        for (int i=0;i<snps.size();i++){
            SNP snp=snps.get(i);
            SNPDto snpDto=new SNPDto();
            BeanUtils.copyProperties(snp,snpDto);
            snpDto.setIndex(i);
            snpDtos.add(snpDto);
        }
        result.put("snps",snpDtos);
        List<String> geneIds=dnaGensService.getByRegionNoCompare(chr,startPos,endPos);
        List<DNAGenStructureDto> dnaGenStructures=dnaGenStructureService.getByStartEnd(chr,Integer.valueOf(startPos),Integer.valueOf(endPos),geneIds);
        result.put("dnaGenStructures",dnaGenStructures);
        if(CollectionUtils.isNotEmpty(dnaGenStructures)){
            result.put("bps",dnaGenStructures.get(0).getBps());
        }
        result.put("conditions", chr + "," + startPos + "," + endPos);
        return ResultUtil.success(result);
    }

    /**
     * 通过geneId搜索位点
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchIdAndPosInGene")
    @ResponseBody
    public ResultVO searchIdAndPosInGene(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");//区分snp和indel数据
        String ctype = request.getParameter("ctype");//list里面的Consequence Type下拉列表 和前端约定 --若为type：后缀下划线，若为effect：前缀下划线
        String gene = request.getParameter("gene");
        String upstream = request.getParameter("upstream");
        String downstream = request.getParameter("downstream");
        String group = request.getParameter("group");
        DNAGens dnaGens = dnaGensService.findByGene(gene);
        logger.info("queryBy " + type + " Gene with ctype:" + ctype + ",gene:" + gene + ",upstream:" + upstream + ",downstream:" + downstream + ",group:" + group);
        if (dnaGens != null) {
            long start = dnaGens.getGeneStart();
            long end = dnaGens.getGeneEnd();
            if (StringUtils.isNoneBlank(upstream)) {
                start = start - Long.valueOf(upstream)-2000<0?0:start - Long.valueOf(upstream)-2000;
            }else {
                start=start-2000<0?0:start-2000;
            }
            if (StringUtils.isNoneBlank(downstream)) {
                end = end + Long.valueOf(downstream)+2000;
            }else {
                end=end+2000;
            }
            upstream = String.valueOf(start);
            downstream = String.valueOf(end);
        }
        Map result=Maps.newHashMap();
        List<SNP> snps=dnaMongoService.searchIdAndPosInGene(type,ctype,gene,upstream,downstream,null);
        List<SNPDto> snpDtos=Lists.newArrayList();
        for (int i=0;i<snps.size();i++){
            SNP snp=snps.get(i);
            SNPDto snpDto=new SNPDto();
            BeanUtils.copyProperties(snp,snpDto);
            snpDto.setIndex(i);
            snpDtos.add(snpDto);
        }
        result.put("snps",snpDtos);
        List<DNAGenStructureDto> dnaGenStructures=dnaGenStructureService.getByGeneId(gene);
        result.put("dnaGenStructures",dnaGenStructures);
        if(CollectionUtils.isNotEmpty(dnaGenStructures)){
            result.put("bps",dnaGenStructures.get(0).getBps());
        }
        result.put("conditions", gene + "," + upstream + "," + downstream);
        return ResultUtil.success(result);
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
            return ResultUtil.error(-1, "未拿到id的值");
        }
        Map result = snpService.findSampleById(id);
        if (result.size() == 0) {
            return ResultUtil.error(-1, "无对应id");
        }
        return ResultUtil.success(result);
    }


    //此方法为原来 导入数据的方法  目前未使用
    @RequestMapping(value = "/insertSNP")
    @ResponseBody
    public String insertSNP(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "E:\\古奥科技资料\\DNA\\snp.test.tab";
        dnaMongoService.insertSNP(fileName);
        return "success";
    }

    //此方法为原来 导入数据的方法  目前未使用
    @RequestMapping(value = "/insertDNAGenes")
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

    //此方法为原来 导入数据的方法  目前未使用
    @RequestMapping(value = "/insert", method = RequestMethod.GET)
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
    @RequestMapping(value = "/snp/info",method = RequestMethod.GET)
    public ModelAndView getSnpInfo(HttpServletRequest request, @RequestParam("frequence")String frequence,SNP snp) {
        ModelAndView modelAndView=new ModelAndView("snpinfo/snpinfo");
        Map result = snpService.findSampleById(snp.getId());
        SNP snpFormatMajorFreq = new SNP();
        if (result.containsKey("snpData")) {
            snpFormatMajorFreq = (SNP) result.get("snpData");
        }else {
            snpFormatMajorFreq = (SNP) result.get("INDELData");
        }
        double major = Double.parseDouble(new DecimalFormat("###0.0000").format(snpFormatMajorFreq.getMajor()));
        snpFormatMajorFreq.setMajor(major); //将转换后的值反设值到SNP对象中
        modelAndView.addObject("snp",snp);
        modelAndView.addObject("result",result);
        SNP snpTemp=(SNP)result.get("snpData");
        if(snpTemp==null){
            snpTemp=(SNP)result.get("INDELData");
        }
        Map map=(Map)snpTemp.getSamples();
        Set<Map.Entry<String, String>> entrySet=map.entrySet();
        List<String> runNos= Lists.newArrayList();
        for(Map.Entry entry:entrySet){
            if(((String)entry.getValue()).contains(snp.getMajorallen())){
                runNos.add((String) entry.getKey());
            }
        }
        //todo 此dnaruns可能重复
        //PageInfo<DNARun> dnaRuns=dnaRunService.getByRunNos(runNos,1,10);
        //modelAndView.addObject("dnaRuns",dnaRuns);
        modelAndView.addObject("frequence",frequence);
        return modelAndView;
    }

    /**
     * 区分mainor和majora
     */
    @RequestMapping(value = "/changeByProportion",method = RequestMethod.GET)
    public ResultVO changeByProportion(@RequestParam("snpId")String snpId,@RequestParam("changeParam") String changeParam,
                                       @RequestParam(value = "pageNum",defaultValue = "1",required = false) Integer pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                       @RequestParam(value = "pageSize",required = false)String isPage,
                                       DnaRunDto dnaRunDto) {
        Map result = snpService.findSampleById(snpId);

        SNP snpTemp=(SNP)result.get("snpData");
        if(snpTemp==null){
            snpTemp=(SNP)result.get("INDELData");
        }
        Map map=(Map)snpTemp.getSamples();
        Set<Map.Entry<String, String>> entrySet=map.entrySet();
        List<String> runNos= Lists.newArrayList();
        Map samples=Maps.newHashMap();
        for(Map.Entry entry:entrySet){
            String value=(String)entry.getValue();
            if(StringUtils.isNotBlank(changeParam)){
                if(value.contains(changeParam)){
                    runNos.add((String) entry.getKey());
                    samples.put(entry.getKey(),entry.getValue());
                }
            }
        }
        if(dnaRunDto==null){
            dnaRunDto=new DnaRunDto();
        }
        dnaRunDto.setRunNos(runNos);
        //PageInfo<DNARunSearchResult> dnaRuns=dnaRunService.getByRunNos(runNos,pageNum,pageSize);
        PageInfo<DNARunSearchResult> dnaRuns=dnaRunService.getListByConditionWithTypeHandler(dnaRunDto,pageNum,pageSize,isPage);
        Map response= Maps.newHashMap();
        response.put("dnaRuns",dnaRuns);
        response.put("samples",samples);
        return ResultUtil.success(response);
    }

    @RequestMapping(value = "/drawSNPTableInRegion",method = RequestMethod.GET)
    public ResultVO drawSNPTableInRegion(@RequestParam("id")String snpId,@RequestParam("index") Integer index,
                                 @RequestParam("chr")String chr,@RequestParam("type") String type,
                                 @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                         @RequestParam("start") String start,@RequestParam("end") String end,
                                 @RequestParam("ctype") String ctype,
                                         @RequestParam(value = "group",required = false,defaultValue = "[]") String group) {
        List<SNP> snps=dnaMongoService.findDataByIndexInRegion(type,chr,snpId,index,pageSize,start,end,ctype);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        List<SNPDto> data = Lists.newArrayList();
        for (SNP snp:snps){
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map map = snpService.findSampleById(snp.getId());
            JSONArray freqData;
            if(StringUtils.equals(type,"SNP")){
                freqData = snpService.getFrequencyInSnp((SNP) map.get("snpData"), group_runNos);
            }else {
                freqData = snpService.getFrequencyInSnp((SNP) map.get("INDELData"), group_runNos);
            }
            snpDto.setFreq(freqData);
            SNP snpData = (SNP) map.get("snpData");
            if(snpData==null){
                snpData = (SNP) map.get("INDELData");
            }
            snpData.setSamples(null);
            snpDto.setGeneType(map);
            data.add(snpDto);
        }
        return ResultUtil.success(data);
    }

    @RequestMapping(value = "/drawSNPTableInGene",method = RequestMethod.GET)
    public ResultVO drawSNPTableInGene(@RequestParam(value = "id",required = false)String snpId,@RequestParam("index") Integer index,
                                         @RequestParam("gene")String gene,@RequestParam("type") String type,
                                         @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                         @RequestParam(value = "upstream",required = false) String upstream,
                                       @RequestParam(value = "downstream",required = false) String downstream,
                                         @RequestParam("ctype") String ctype,
                                       @RequestParam(value = "group",required = false,defaultValue = "[]") String group) {
        DNAGens dnaGens = dnaGensService.findByGene(gene);
        if (dnaGens != null) {
            long start = dnaGens.getGeneStart();
            long end = dnaGens.getGeneEnd();
            if (StringUtils.isNoneBlank(upstream)) {
                start = start - Long.valueOf(upstream)-2000<0?0:start - Long.valueOf(upstream)-2000;
            }else {
                start=start-2000<0?0:start-2000;
            }
            if (StringUtils.isNoneBlank(downstream)) {
                end = end + Long.valueOf(downstream)+2000;
            }else {
                end=end+2000;
            }
            upstream = String.valueOf(start);
            downstream = String.valueOf(end);
        }
        List<SNP> snps=dnaMongoService.findDataByIndexInGene(type,gene,snpId,index,pageSize,upstream,downstream,ctype);
        Map<String, List<String>> group_runNos = dnaRunService.queryDNARunByCondition(group);
        List<SNPDto> data = Lists.newArrayList();
        for (SNP snp:snps){
            SNPDto snpDto = new SNPDto();
            BeanUtils.copyProperties(snp, snpDto);
            Map map = snpService.findSampleById(snp.getId());
            SNP snpData = (SNP) map.get("snpData");
            JSONArray freqData = snpService.getFrequencyInSnp(snpData, group_runNos);
            snpDto.setFreq(freqData);
            if(snpData!=null){
                snpData.setSamples(null);
            }
            snpDto.setGeneType(map);
            data.add(snpDto);
        }
        return ResultUtil.success(data);
    }
}
