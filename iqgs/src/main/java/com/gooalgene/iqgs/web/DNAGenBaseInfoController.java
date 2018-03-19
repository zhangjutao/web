package com.gooalgene.iqgs.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.service.SNPService;
import com.gooalgene.entity.Qtl;
import com.gooalgene.iqgs.entity.*;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.iqgs.entity.condition.RangeSearchResult;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.gooalgene.iqgs.service.FPKMService;
import com.gooalgene.mrna.entity.ExpressionStudy;
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.PropertiesLoader;
import com.gooalgene.utils.ResultUtil;
import com.gooalgene.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整合数据库主页,包含三个主要查询入口
 *
 * @author sauldong
 * @version 1.0
 * @since 2017/10/12
 */
@Controller
@RequestMapping("/iqgs")
public class DNAGenBaseInfoController implements InitializingBean {
    Logger logger = LoggerFactory.getLogger(DNAGenBaseInfoController.class);

    @Autowired
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private StudyService studyService;

    @Autowired
    private QueryService queryService;

    @Autowired
    private FPKMService fpkmService;

    @Autowired
    private CacheManager cacheManager;

    private PropertiesLoader loader;

    private Cache cache;

    @Override
    public void afterPropertiesSet() throws Exception {
        loader = new PropertiesLoader("classpath:qtldb.properties");
        cache = cacheManager.getCache("sortCache");
    }

    @RequestMapping("/index")
    public ModelAndView toIndexPage() {
        ModelAndView modelAndView = new ModelAndView("iqgs/IQGS-index");
        String mrnaIndex = loader.getProperty("mrna.index");
        String dnaIndex = loader.getProperty("dna.index");
        String qtlIndex = loader.getProperty("qtl.index");
        String qtlExample = loader.getProperty("qtl.example");
        int exampleQtlId = dnaGenBaseInfoService.getAssociateGeneIdByQtlAndVersion(qtlExample, CommonConstant.VERSIONTWO);
        modelAndView.addObject("qtlExample", qtlExample);
        modelAndView.addObject("exampleQtlId", exampleQtlId);
        modelAndView.addObject("mrnaIndex", mrnaIndex);
        modelAndView.addObject("dnaIndex", dnaIndex);
        modelAndView.addObject("qtlIndex", qtlIndex);

        return modelAndView;
    }

    @RequestMapping("/search/list")
    public String toSeachListPage(HttpServletRequest req, Model model) {
        String searchType = req.getParameter("searchType");
        if ("1".equals(searchType) || "2".equals(searchType)) {
            String keyword = req.getParameter("keyword");
            model.addAttribute("keyword", keyword);
        } else {
            String chr = req.getParameter("chr");
            String begin = req.getParameter("begin");
            String end = req.getParameter("end");
            model.addAttribute("chr", chr);
            model.addAttribute("rgBegin", begin);
            model.addAttribute("rgEnd", end);
            model.addAttribute("keyword", chr + "," + begin + "bp-" + end + "bp");
        }
        model.addAttribute("searchType", searchType);
        return "iqgs/IQGS-list";
    }

    @RequestMapping("/me")
    public Authentication auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }


    /**
     * 根据基因id进行模糊查询
     */
    @RequestMapping("/search/gene-id-name")
    @ResponseBody
    public ResultVO<DNAGeneSearchResult> searchForIdORName(HttpServletRequest req) {
        String idOrName = req.getParameter("keyword");
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        ResultVO resultVO = new ResultVO();
        PageInfo<DNAGeneSearchResult> resultPageInfo = dnaGenBaseInfoService.queryDNAGenBaseSearchResult(SearchConditionEnum.ID, idOrName, pageNo, pageSize);
        return ResultUtil.success(resultPageInfo);
    }

    /**
     * 根据基因function或者name字段进行模糊查询
     *
     * @return 搜索出来的总条数, data:搜索出来的数据,data中包含哪些数据...
     */
    @RequestMapping("/search/func")
    @ResponseBody
    public ResultVO<DNAGeneSearchResult> searchForFunction(HttpServletRequest req) {
        String func = req.getParameter("keyword");
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        PageInfo<DNAGeneSearchResult> resultPageInfo = dnaGenBaseInfoService.queryDNAGenBaseSearchResult(SearchConditionEnum.FUNCTION, func, pageNo, pageSize);
        return ResultUtil.success(resultPageInfo);
    }

    @RequestMapping("/search/range")
    @ResponseBody
    public ResultVO<RangeSearchResult> searchForRange(HttpServletRequest req) {
        int start = Integer.parseInt(req.getParameter("begin"));
        int end = Integer.parseInt(req.getParameter("end"));
        String chr = req.getParameter("chr");
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        PageInfo<RangeSearchResult> resultPageInfo = fpkmService.findViewByRange(chr, start, end, pageNo, pageSize);
        return ResultUtil.success(resultPageInfo);
    }

    @RequestMapping("/detail/basic")
    public String detailForGene(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        DNAGenBaseInfo dna = dnaGenBaseInfoService.getGenBaseInfoByGeneId(genId);
        model.addAttribute("genId", genId);
        model.addAttribute("dna", dna);
        return "iqgs/IQGS-details";
    }

    @RequestMapping("/detail/structure")
    public String detailForStructure(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        Map structure = dnaGenBaseInfoService.generateGenStructure(genId);
        model.addAttribute("genId", genId);
        model.addAttribute("structJSON", JSONObject.fromObject(structure).toString());
        return "iqgs/IQGS-structure";
    }

    @RequestMapping("/detail/structure/downloadSeq")
    public void downloadSeq(HttpServletRequest request, HttpServletResponse response) {
        try {
            String transcriptId = request.getParameter("transcript_id");
            String fileName = transcriptId + "_Sequence.txt";
            String sequence = dnaGenBaseInfoService.findSequenceByTranscriptId(transcriptId);

            // response.setContentType("multipart/form-data");
            // response.setContentType("application/OCTET-STREAM;charset=UTF-8");
            response.setContentType("text/plain;charset=UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            os.write(sequence.getBytes());
            os.flush();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @RequestMapping("/detail/sequence")
    public String detailForSequence(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        logger.info("getSequence:" + genId);
        JSONArray json = new JSONArray();
        String transcriptId = "";
        List<DNAGenSequence> dnas = dnaGenBaseInfoService.getGenSequenceByGeneId(genId);
        for (DNAGenSequence a : dnas) {
            transcriptId = a.getTranscriptId();
            JSONObject jo = new JSONObject();
            jo.put("genId", a.getGeneId());
            jo.put("type", a.getType());
            jo.put("sequence", a.getSequence());
            json.add(jo);
        }
        List<DNAGenStructure> dstu = dnaGenBaseInfoService.getGenStructureByTranscriptId(transcriptId);
        Long firstIndex = new Long(99999999);
        for (int i = 0; i < dstu.size(); i++) {
            DNAGenStructure dnaGenStructure = dstu.get(i);
            if (firstIndex > dnaGenStructure.getStart()) {
                firstIndex = dnaGenStructure.getStart();
            }
        }
        for (DNAGenStructure a : dstu) {
            JSONObject jo = new JSONObject();
            jo.put("strand", a.getStrand());
            jo.put("start", a.getStart() + 1 - firstIndex);
            jo.put("end", a.getEnd() + 1 - firstIndex);
            jo.put("length", a.getLength());
            jo.put("feature", a.getFeature());
            json.add(jo);
        }
        if (json.toString() != null) {
            model.addAttribute("data", json.toString());
        } else {
            JSONObject jo = new JSONObject();
            jo.put("error", "no data");
            model.addAttribute("data", jo.toString());
        }
        model.addAttribute("genId", genId);
        return "iqgs/Ref-sequence";
    }

    @RequestMapping("/detail/dnalist")
    public String detailForDnalist(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
//        DNAGenSequence dna = dnaGenBaseInfoService.getGenSequenceByGeneId(genId);
//        model.addAttribute("genId", genId);
//        model.addAttribute("dna", dna);
        return "iqgs/IQGS-dnalist";
    }

    @RequestMapping("/detail/anno")
    public String detailForAnno(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        List<DNAGenAnnoGo> gos = dnaGenBaseInfoService.getGenAnnoGoByGeneId(genId);
        List<DNAGenAnnoIpr> iprs = dnaGenBaseInfoService.getGenAnnoIprByGeneId(genId);
        List<DNAGenAnnoKegg> keggs = dnaGenBaseInfoService.getGenAnnoKeggByGeneId(genId);
        model.addAttribute("genId", genId);
        model.addAttribute("gos", gos);
        model.addAttribute("iprs", iprs);
        model.addAttribute("keggs", keggs);
        return "iqgs/gene-annotation";
    }

    @RequestMapping("/detail/origin")
    public String detailForOrigin(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/homologous-gene";
    }

    @RequestMapping("/detail/origin/page")
    @ResponseBody
    public ResultVO<DNAGenHomologous> detailForOrigin(HttpServletRequest req) {
        String genId = req.getParameter("gene_id");
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        PageInfo<DNAGenHomologous> homologousPageInfo = dnaGenBaseInfoService.getGenHomologousByGeneId(genId, pageNo, pageSize);
        return ResultUtil.success(homologousPageInfo);
    }

    @RequestMapping("/detail/family")
    public String detailForFamily(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        String familyId = req.getParameter("family_id");

        // 根据基因id查询基因家族
        List<DNAGenFamilyRel> dnaGenFamilyRels = dnaGenBaseInfoService.findFamilyByGeneId(genId);
        if (dnaGenFamilyRels != null && dnaGenFamilyRels.size() > 0) {
            model.addAttribute("hasFamilyFlg", true);
            model.addAttribute("dnaGenFamilyRels", dnaGenFamilyRels);

            // 首次查询默认显示第一个基因家族数据
            if (StringUtils.isBlank(familyId)) {
                familyId = dnaGenFamilyRels.get(0).getFamilyId();
            }
            model.addAttribute("familyId", familyId);

            // 根据基因家族id查询基因家族信息
            DNAGenFamily dnaGenFamily = dnaGenBaseInfoService.findFamilyByFamilyId(familyId);
            model.addAttribute("dnaGenFamily", dnaGenFamily);

            // 根据基因家族id查询家族下所有基因结构信息
            JSONObject structureData = dnaGenBaseInfoService.findStructureByFamilyId(familyId);
            model.addAttribute("structureData", structureData);
        } else {
            model.addAttribute("hasFamilyFlg", false);
        }

        model.addAttribute("genId", genId);
        return "iqgs/gene-family";
    }

    /**
     * 根据基因家族id查询基因列表
     */
    @RequestMapping("/detail/family/page")
    @ResponseBody
    public Map getGensByFamily(HttpServletRequest req, HttpServletResponse resp) {
        Map result = new HashMap();
        String familyId = req.getParameter("family_id");
        // 查询基因家族下的基因列表分页显示
        Page<DNAGenBaseInfo> page = new Page<DNAGenBaseInfo>(req, resp);
        List<DNAGenBaseInfo> gens = dnaGenBaseInfoService.queryDNAGenBaseInfosByFamilyId(familyId, page);
        result.put("total", page.getCount());
        result.put("data", gens);
        return result;
    }

    /**
     * 表达数据
     */
    @RequestMapping("/detail/expression")
    public String detailForExpresssion(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/IQGS-expression";
    }

    @RequestMapping("/detail/qtl")
    public ModelAndView detailForQTL(HttpServletRequest req, HttpServletResponse resp) {
        ModelAndView modelAndView = new ModelAndView("/iqgs/IQGS-qtllist");
        String gene = req.getParameter("gen_id");
        //搜索框：包含ALL、Trait、QTL Name、marker、parent、reference，ALL是全局搜索，
        modelAndView.addObject("genId", gene);
        logger.info("queryQTLByGene:" + gene);
        Page<Qtl> page = new Page<Qtl>(req, resp);
        modelAndView.addObject("versions", queryService.queryVersions());
        modelAndView.addObject("types", queryService.queryAll());
        modelAndView.addAllObjects(queryService.qtlSearchbyGene(gene, page));
        modelAndView.addObject("page", page);
        return modelAndView;
    }

    @RequestMapping("/getQtlByVersionAndGene")
    @ResponseBody
    public ResultVO getQtlByVersionAndGene(HttpServletRequest req, HttpServletResponse resp) {
        String gene = req.getParameter("gen_id");
        Page<Qtl> page = new Page<Qtl>(req, resp);
        Map<String, ?> stringMap = queryService.qtlSearchbyGene(gene, page);
        ResultVO success = ResultUtil.success(stringMap);
        return success;
    }

    @RequestMapping("/detail/variation")
    public String detailForVariation(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/IQGS-dnalist";
    }

    @RequestMapping("/detail/regulatory")
    public String detailForRequlatory(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/regulatory-network";
    }

    @RequestMapping("/detail/coexpression")
    public String detailForCoexpression(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/co-expression-network";
    }

    @RequestMapping("/queryExpressionByGene")
    @ResponseBody
    public Map queryExpressionByGene(HttpServletRequest req, HttpServletResponse resp) {
        String gene = req.getParameter("gene");
        logger.info(gene + "Expression");
        Map rs = new HashMap();
        Page<ExpressionStudy> page = new Page<ExpressionStudy>(req, resp);
        List<ExpressionStudy> gens = studyService.getStudyByGene(gene, page);
        rs.put("total", page.getCount());
        rs.put("data", gens);
        return rs;
    }

    @Autowired
    private TService tService;

    /**
     * 大豆折线图数据接口
     */
    @RequestMapping("/line")
    @ResponseBody
    public String line(HttpServletRequest request) {
        String genes = request.getParameter("genes");
        String json = "{}";
        logger.info("line:genes{" + genes + "}");
        if (org.apache.commons.lang.StringUtils.isNotBlank(genes)) {
            String[] gens = genes.split(",");
            GenResult genResult = new GenResult();
            if (gens.length == 1) {
                String key = gens[0] + "_" + genResult.getClass().getSimpleName();
                Cache.ValueWrapper valueWrapper = cache.get(key);
                if (valueWrapper != null) {
                    genResult = (GenResult) valueWrapper.get();
                }
            } else {
                genResult = tService.generateData(gens);
            }
            json = JsonUtils.Bean2Json(genResult);
        }
        return json;
    }


    @Autowired
    private SNPService snpService;

    @RequestMapping("/searchDNAinGene")
    @ResponseBody
    public Map searchSNPByGene(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");//区分snp和indel数据
        String gene = request.getParameter("gene");//具体的gene
        String ctype = request.getParameter("ctype");
        if (ctype.startsWith("_")) {
            ctype = ctype.substring(1);
        }
        logger.info("queryBy " + type + "and ctype" + ctype + " with gene:" + gene);
        String[] ctypeList = ctype.split(",");
        Page<DNAGens> page = new Page<DNAGens>(request, response);
        return snpService.searchSNPByGene(type, ctypeList, gene, page);
    }
}
