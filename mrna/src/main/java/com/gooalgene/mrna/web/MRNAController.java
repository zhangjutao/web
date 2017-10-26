package com.gooalgene.mrna.web;

import com.gooalgene.common.Global;
import com.gooalgene.common.Page;
import com.gooalgene.common.service.IndexExplainService;
import com.gooalgene.entity.Study;
import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.service.MongoService;
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.utils.FileType;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.Tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.*;


/**
 * mRNA
 * Created by Shiyun on 2017/7/26 0010.
 */
@Controller
@RequestMapping("/mrna")
public class MRNAController {

    Logger logger = LoggerFactory.getLogger(MRNAController.class);

    @Autowired
    private IndexExplainService indexExplainService;

    @Autowired
    private StudyService studyService;

    @Autowired
    private TService tService;

    @RequestMapping("/login")
    public String login(HttpServletRequest request) {
        logger.info("后台登录.");
        return "dashboard/login";
    }

    @RequestMapping("/queryStudy")
    public String queryStudy(HttpServletRequest request) {
        return "mrna/study";
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request) {

        ModelAndView model = new ModelAndView("mRNA/mRNA-index");
        //如果用户登录首页，直接登录：先跳转到登录页面后来到首页，然后将userName放入到session中
//        String userName = "";
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null){
//            Object credentials = authentication.getCredentials();
//            if (credentials != null){
//                if (credentials instanceof UserDetails){
//                    UserDetails userDetails = (UserDetails) credentials;
//                    userName = userDetails.getUsername();
//                } else if (credentials instanceof Principal) {
//                    Principal principal = (Principal) credentials;
//                    userName = principal.getName();
//                }else {
//                    userName = String.valueOf(userName);
//                }
//            }
//            request.getSession().setAttribute("userName", userName);
//        }

        List<Classifys> tree = tService.getClassifyTree();
        model.addObject("tree", tree);
        String treejson = JsonUtils.Bean2Json(tree);
        model.addObject("treejs", treejson);

//        String[] gens = {"GLYMA04G38670", "GLYMA04G32251", "GLYMA16G25932", "GLYMA03G33200", "GLYMA08G22740"};
//        String[] gens = {"GLYMA17G35620","GLYMA13G34810","GLYMA02G13420","GLYMA06G45331","GLYMA13G39820"};
        String[] gens = studyService.queryGenesForFirst();
        GenResult genResult = tService.generateData(gens);
        String json = JsonUtils.Bean2Json(genResult);
//        System.out.println(json);

        model.addObject("data", json);
        model.addObject("mrnaDetail", indexExplainService.queryByType("mrna").getDetail());
        return model;
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("mRNA/mRNA-list");
        List<Classifys> tree = tService.getClassifyTree();
        modelAndView.addObject("tree", tree);
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String isIndex = request.getParameter("isIndex");//是否首页--此参数只在首页搜索和列表页搜索才有，和侧边栏搜索进行区分
        modelAndView.addObject("type", type == null ? "All" : type);
        modelAndView.addObject("keywords", (keywords == null ? "" : keywords));
        modelAndView.addObject("isIndex", isIndex == null ? "" : isIndex);
        logger.info("type:" + type + ",keywords:" + keywords + ",isIndex:" + isIndex);
        return modelAndView;
    }

    @RequestMapping("/genesdif")
    public ModelAndView genesdif(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("mRNA/mRNAgenes-dif");
        modelAndView.addObject("downloadUrl", Global.getConfig("downloadUrl"));
        return modelAndView;
    }

    @RequestMapping("/genesexpression")
    public ModelAndView genesexpression(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("mRNA/mRNAgenes-expression");
        modelAndView.addObject("downloadUrl", Global.getConfig("downloadUrl"));
        return modelAndView;
    }

    @RequestMapping("/highcharts")
    public String highcharts(HttpServletRequest request) {
        return "mRNA/highcharts";
    }

    @RequestMapping("/specificity")
    public ModelAndView specificity(HttpServletRequest request) {

        ModelAndView model = new ModelAndView("mRNA/mRNAtissue-specificity");
        String[] gens = {"GLYMA04G38670", "GLYMA04G32251", "GLYMA16G25932", "GLYMA03G33200", "GLYMA08G22740"};
        GenResult genResult = tService.generateData(gens);
        String json = JsonUtils.Bean2Json(genResult);
        System.out.println(json);

        model.addObject("data", json);
        return model;
    }

    /**
     * ScientificName下拉列表
     *
     * @return
     */
    @RequestMapping("/scientificNames")
    @ResponseBody
    public JSONArray queryScientificNames() {
        return studyService.queryScientificNames();
    }


    /**
     * ScientificName下拉列表
     *
     * @return
     */
    @RequestMapping("/libraryLayouts")
    @ResponseBody
    public JSONArray queryLibraryLayouts() {
        return studyService.queryLibraryLayouts();
    }

    /**
     * 对搜索结果进行搜索
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/listByResult")
    @ResponseBody
    public Map list1(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        if (type == null) {
            return new HashMap();
        }
        String keywords = request.getParameter("keywords");
        String parameters = request.getParameter("conditions");
        Page<Study> page = new Page<Study>(request, response);
        return studyService.queryStudyByCondition(type, keywords, parameters, page);
    }

    @RequestMapping("/dataExport")
    @ResponseBody
    public void qtlSearchResultExport(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");
        String keywords = request.getParameter("keywords");
        String parameters = request.getParameter("condition");
        String columns = request.getParameter("choices");
        logger.info("type:" + type + ",keywords:" + keywords + ",condition:" + parameters + ",colums:" + columns);
        if (StringUtils.isNoneBlank(columns)) {
            List<Map> result = studyService.queryStudyToExport(type, keywords, parameters);
            String sb = serialList(result, columns.split(","));
            if (keywords.endsWith(".all")) {
                keywords = keywords.substring(0, keywords.lastIndexOf("."));
            }
            String fileName = "MRNA-" + type + "(" + keywords + ")";
            System.out.println(fileName);
            Tools.toDownload(fileName, sb, response);
        }
    }

    private String serialList(List<Map> result, String[] titles) {
        StringBuilder sb = new StringBuilder();
//        sb.append("Run").append(",");
//        sb.append("SampleName").append(",");
//        sb.append("Expression/Comparison").append(",");
//        sb.append("SRAStudy").append(",");
//        sb.append("Study").append(",");
//        sb.append("Tissue for Classification").append(",");
//        sb.append("Tissue").append(",");
//        sb.append("preservation").append(",");
//        sb.append("Treat").append(",");
//        sb.append("Stage").append(",");
//        sb.append("Genetype").append(",");
//        sb.append("Phenotype").append(",");
//        sb.append("Environment").append(",");
//        sb.append("Geo loc").append(",");
//        sb.append("ecotype").append(",");
//        sb.append("collection date").append(",");
//        sb.append("Coordinates").append(",");
//        sb.append("Cultivar").append(",");
//        sb.append("ScientificName").append(",");
//        sb.append("Pedigree").append(",");
//        sb.append("Reference").append(",");
//        sb.append("Institution").append(",");
//        sb.append("submissiontime").append(",");
//        sb.append("Instrument").append(",");
//        sb.append("LibraryStrategy").append(",");
//        sb.append("LibrarySource").append(",");
//        sb.append("LibraryLayout").append(",");
//        sb.append("insertsize").append(",");
//        sb.append("readlength").append(",");
//        sb.append("Spots").append(",");
//        sb.append("Experiment").append(",");
//        sb.append("links");
//        sb.append("\n");
        Map<String, Integer> map = new HashMap<String, Integer>();
        if (titles != null) {
            sb = new StringBuilder();
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
        }
        if (!result.isEmpty() && !map.isEmpty()) {
            Iterator<Map> it = result.iterator();
            while (it.hasNext()) {
                Map data = it.next();
                if (map.containsKey("Sample name")) {
                    String sampleName = (String) data.get("sampleName");
                    sb.append((sampleName != null ? sampleName : "")).append(",");
                }
                if (map.containsKey("Study")) {
                    String study = (String) data.get("study");
                    sb.append((study != null ? study : "")).append(",");
                }
                if (map.containsKey("Reference")) {
                    String reference = (String) data.get("reference");
                    sb.append((reference != null ? reference : "")).append(",");
                }
                if (map.containsKey("Tissue")) {
                    String tissue = (String) data.get("tissueForClassification");
                    sb.append((tissue != null ? tissue : "")).append(",");
                }
                if (map.containsKey("Stage")) {
                    String stage = (String) data.get("stage");
                    sb.append((stage != null ? stage : "")).append(",");
                }
                if (map.containsKey("Treat")) {
                    String treat = (String) data.get("treat");
                    sb.append((treat != null ? treat : "")).append(",");
                }
                if (map.containsKey("Genetype")) {
                    String geneType = (String) data.get("geneType");
                    sb.append((geneType != null ? geneType : "")).append(",");
                }
                if (map.containsKey("Preservation")) {
                    String preservation = (String) data.get("preservation");
                    sb.append((preservation != null ? preservation : "")).append(",");
                }
                if (map.containsKey("Phenotype")) {
                    String phenotype = (String) data.get("phenoType");
                    sb.append((phenotype != null ? phenotype : "")).append(",");
                }
                if (map.containsKey("Environment")) {
                    String environment = (String) data.get("environment");
                    sb.append((environment != null ? environment : "")).append(",");
                }
                if (map.containsKey("Cultivar")) {
                    String cultivar = (String) data.get("ccultivar");
                    sb.append((cultivar != null ? cultivar : "")).append(",");
                }
                if (map.containsKey("Scientific Name")) {
                    String scientificName = (String) data.get("scientificName");
                    sb.append((scientificName != null ? scientificName : "")).append(",");
                }
                if (map.containsKey("Library Layout")) {
                    String libraryLayout = (String) data.get("libraryLayout");
                    sb.append((libraryLayout != null ? libraryLayout : "")).append(",");
                }
                if (map.containsKey("Spots")) {
                    Integer spots = (Integer) data.get("spots");
                    sb.append((spots != null ? spots : "")).append(",");
                }
                if (map.containsKey("Run")) {
                    String run = (String) data.get("sampleRun");
                    sb.append((run != null ? run : "")).append(",");
                }
//                Integer isExpression = (Integer) data.get("isExpression");
//                if (isExpression == 0) {
//                    sb.append("comparison").append(",");
//                } else {
//                    sb.append("expression").append(",");
//                }
                if (map.containsKey("SRAStudy")) {
                    String sraStudy = (String) data.get("sraStudy");
                    sb.append((sraStudy != null ? sraStudy : "")).append(",");
                }
                if (map.containsKey("Experiment")) {
                    String experiment = (String) data.get("experiment");
                    sb.append((experiment != null ? experiment : "")).append(",");
                }
//                sb.append(data.get("tissueForClassification")).append(",");
//                sb.append(data.get("geoLoc")).append(",");
//                sb.append(data.get("ecoType")).append(",");
//                sb.append(data.get("collectionDate")).append(",");
//                sb.append(data.get("coordinates")).append(",");
//                sb.append(data.get("pedigree")).append(",");
//                sb.append(data.get("institution")).append(",");
//                sb.append(data.get("submissionTime")).append(",");
//                sb.append(data.get("instrument")).append(",");
//                sb.append(data.get("libraryStrategy")).append(",");
//                sb.append(data.get("librarySource")).append(",");
//                sb.append(data.get("insertSize")).append(",");
//                sb.append(data.get("readLength")).append(",");
//                sb.append(data.get("links")).append("\n");
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Autowired
    private MongoService mongoService;

    //文件上传的临时目录
    private String uploadedDir = "/static/tmp";

    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public String importRouter(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request, Model model) {
        boolean flag = false;
        String path = request.getSession().getServletContext().getRealPath(uploadedDir) + File.separator + "mrna";
        String type = request.getParameter("type");
        //T13---fpkm
        //T14---Expression
        //T15---Comparison
        String sraStudy = request.getParameter("sraStudy");
        String mode = request.getParameter("mode");//insert/update/delete
        String mes = "";
        String suffix = file.getOriginalFilename();
        suffix = suffix.substring(suffix.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + "-" + type + suffix;
        logger.info("上传表文件类型Type:" + type + ",sraStudy:" + sraStudy + ",mode:" + mode + ",path:" + path + ",fileName:" + fileName);
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
            String name = targetFile.getName().toLowerCase();
            String targetFilePath = targetFile.getAbsolutePath();
            logger.info("target file:" + targetFilePath);
            if (name.endsWith(".zip")) {
                String outPath = Tools.unzipFile(targetFile, "/tmp/" + name.replace(".zip", "") + "/");
                if (outPath != null) {
                    if (mode.equals("insert")) {
                        if (type.equals(FileType.TYPE_FPKM)) {
                            flag = mongoService.insertFPKMFile(outPath);
                        } else if (type.equals(FileType.TYPE_EXPRESSION)) {
                            flag = mongoService.insertExpressionFile(sraStudy, outPath);
                            if (flag) {
                                String mrnaMountPath = Global.getConfig("mrnaMountPath_expression") + sraStudy + ".zip";
                                Tools.zip(mrnaMountPath, new File(outPath));
//                              FileUtils.copyFile(targetFile, new File(mrnaMountPath));//移动文件到挂载路径供下载使用。
                            } else {
                                mes = "导入过程出错，请查询后台日志定位问题。";
                            }
                        } else if (type.equals(FileType.TYPE_COMPARISON)) {
                            flag = mongoService.insertComparisonFile(sraStudy, outPath);
                            if (flag) {
                                String mrnaMountPath = Global.getConfig("mrnaMountPath_comparison") + sraStudy + ".zip";
                                Tools.zip(mrnaMountPath, new File(outPath));
//                                FileUtils.copyFile(targetFile, new File(mrnaMountPath));//移动文件到挂载路径供下载使用。
                            } else {
                                mes = "导入过程出错，请查询后台日志定位问题。";
                            }
                        } else {
                            mes = "不支持的导入操作！";
                        }
                    } else if (mode.equals("update")) {
                        if (type.equals(FileType.TYPE_FPKM)) {
                            flag = mongoService.updateFPKMFile(outPath);
                        } else if (type.equals(FileType.TYPE_EXPRESSION)) {
//                            flag = mongoService.updateExpressionFile(sraStudy, outPath);
                        } else if (type.equals(FileType.TYPE_COMPARISON)) {
//                            flag = mongoService.updateComparisonFile(sraStudy, outPath);
                        } else {
                            mes = "不支持的导入操作！";
                        }
                    } else if (mode.equals("delete")) {
                        if (type.equals(FileType.TYPE_FPKM)) {
                            flag = mongoService.deleteFPKMFile(outPath);
                        } else if (type.equals(FileType.TYPE_EXPRESSION)) {
//                            flag = mongoService.deleteExpressionFile(sraStudy, outPath);
                        } else if (type.equals(FileType.TYPE_COMPARISON)) {
//                            flag = mongoService.deleteComparisonFile(sraStudy, outPath);
                        } else {
                            mes = "不支持的导入操作！";
                        }
                    } else {
                        mes = "不支持的导入操作！";
                    }
                } else {
                    mes = "压缩文件有误！";
                }
            } else {
                mes = "不支持的文件格式,请上传zip文件。";
            }
        } catch (IOException e) {
            e.printStackTrace();
            mes = "上报文件异常。";
        }
        JSONObject result = new JSONObject();
        result.put("result", flag);
        result.put("mes", mes);
        return result.toString();
    }
}
