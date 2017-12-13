package com.gooalgene.iqgs.web;

import com.gooalgene.common.Page;
import com.gooalgene.dna.entity.DNAGens;
import com.gooalgene.dna.service.SNPService;
import com.gooalgene.entity.Qtl;
import com.gooalgene.iqgs.entity.*;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.mrna.vo.GenResult;
import com.gooalgene.qtl.service.QueryService;
import com.gooalgene.utils.JsonUtils;
import com.gooalgene.utils.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 整合数据库主页,包含三个主要查询入口
 * @since 2017/10/12
 * @version 1.0
 * @author sauldong
 */
@Controller
@RequestMapping("/iqgs")
public class DNAGenBaseInfoController {

    Logger logger = LoggerFactory.getLogger(DNAGenBaseInfoController.class);

    @Autowired
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private StudyService studyService;

    /**
     * <span style="color:red;">请求URL</span>: http://host:port/contextPath/iqgs/index <br>
     * 请求方式: GET OR POST
     * @return 跳转到iqgs目录下IQGS-index.jsp页面
     */
    @RequestMapping("/index")
    public String toIndexPage() {
        return "iqgs/IQGS-index";
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
        }
        model.addAttribute("searchType", searchType);
        return "iqgs/IQGS-list";
    }

    /**
     * 根据基因id或者name进行模糊查询
     *
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping("/search/gene-id-name")
    @ResponseBody
    public Map searchForIdORName(HttpServletRequest req, HttpServletResponse resp) {
        Map rs = new HashMap();
        String idOrName = req.getParameter("keyword");
        Page<DNAGenBaseInfo> page = new Page<DNAGenBaseInfo>(req, resp);
        List<DNAGenBaseInfo> gens = dnaGenBaseInfoService.queryDNAGenBaseInfosByIdorName(idOrName, page);
        rs.put("total", page.getCount());
        rs.put("data", gens);
        return rs;
    }

    /**
     * 根据基因function字段进行模糊查询 <br>
     * <span style="color:red;">请求URL</span>: http://host:port/contextPath/iqgs/search/func <br>
     * 请求方式: GET OR POST
     * @param req http请求
     * @param resp http响应
     * @return total:搜索出来的总条数, data:搜索出来的数据,data中包含哪些数据...
     */
    @RequestMapping("/search/func")
    @ResponseBody
    public Map searchForFunction(HttpServletRequest req, HttpServletResponse resp) {
        Map rs = new HashMap();
        String func = req.getParameter("keyword");
        Page<DNAGenBaseInfo> page = new Page<DNAGenBaseInfo>(req, resp);
        List<DNAGenBaseInfo> gens = dnaGenBaseInfoService.queryDNAGenBaseInfosByFunc(func, page);
        rs.put("total", page.getCount());
        rs.put("data", gens);
        return rs;
    }

    @RequestMapping("/search/range")
    @ResponseBody
    public Map searchForRange(HttpServletRequest req, HttpServletResponse resp) {
        Map rs = new HashMap();
        String start = req.getParameter("begin");
        String end = req.getParameter("end");
        String chr = req.getParameter("chr");
        Page<DNAGenBaseInfo> page = new Page<DNAGenBaseInfo>(req, resp);
        List<DNAGenBaseInfo> gens = dnaGenBaseInfoService.queryDNAGenBaseInfosByRange(chr, start, end, page);
        rs.put("total", page.getCount());
        rs.put("data", gens);
        return rs;
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
	public void downloadSeq(HttpServletRequest request, HttpServletResponse response)  {
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

    /**
     * 根据基因id获取gain基因序列 <br>
     * <span style="color:red;">请求URL</span>: http://host:port/contextPath/iqgs/detail/sequence?gen_id=${genId} <br>
     * 请求方式: GET OR POST
     * @param req http请求
     * @param resp http响应
     * @return
     */
    @RequestMapping("/detail/sequence")
    public String detailForSequence(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        JSONArray json = new JSONArray();
        //获取gene序列
        String transcriptId="";
        List<DNAGenSequence> dnas = dnaGenBaseInfoService.getGenSequenceByGeneId(genId);
        for (DNAGenSequence a : dnas) {
            transcriptId=a.getTranscriptId();
            JSONObject jo = new JSONObject();
            jo.put("geneId",a.getGeneId());
            jo.put("transcriptId",a.getTranscriptId());
            jo.put("type",a.getType());
            jo.put("sequence",a.getSequence());
            json.add(jo);
        }
        //获取gene结构
        List<DNAGenStructure> dstu = dnaGenBaseInfoService.getGenStructureByTranscriptId(transcriptId);
        Long firstIndex = new Long(99999999);
        for (int i = 0; i < dstu.size(); i++) {
            DNAGenStructure dnaGenStructure =  dstu.get(i);
            if(firstIndex>dnaGenStructure.getStart()){
                firstIndex=dnaGenStructure.getStart();
            }
        }
        for (DNAGenStructure a : dstu) {
            JSONObject jo = new JSONObject();
            jo.put("geneId",a.getGeneId());
            jo.put("transcriptId",a.getTranscriptId());
            jo.put("strand",a.getStrand());
            jo.put("start",a.getStart()+1-firstIndex);
            jo.put("end",a.getEnd()+1-firstIndex);
            jo.put("firstIndex",firstIndex);
            jo.put("length",a.getLength());
            jo.put("feature",a.getFeature());
            json.add(jo);
        }
        System.out.println(json.toString());
        model.addAttribute("genId", genId);
        model.addAttribute("transcriptId", transcriptId);
        model.addAttribute("dnas", dnas);
        model.addAttribute("dstu", dstu);
        model.addAttribute("data", json.toString());
        return "iqgs/Ref-sequence";
    }

    /**
     *
     * @param req
     * @param resp
     * @return json数组
     */
    /*@RequestMapping("/detail/newsequence")
    @ResponseBody
    public Map detailForNewSequence(HttpServletRequest req, HttpServletResponse resp) {
        Map result = new HashMap();
        String genId = req.getParameter("gen_id");
        String transcriptId="";
        List<DNAGenSequence> dnas = dnaGenBaseInfoService.getGenSequenceByGeneId(genId);
        for (DNAGenSequence a : dnas) {
            transcriptId=a.getTranscriptId();
        }
        //获取gene结构
        List<DNAGenStructure> dstu = dnaGenBaseInfoService.getGenStructureByTranscriptId(transcriptId);
        result.put("dnas", dnas);
        result.put("dstu", dstu);
        return result;
    }*/

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

    /**
     * 同源基因
     *
     * @param req
     * @param resp
     * @param model
     * @return
     */
    @RequestMapping("/detail/origin")
    public String detailForOrigin(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        List<DNAGenHomologous> homologous = dnaGenBaseInfoService.getGenHomologousByGeneId(genId);
        model.addAttribute("genId", genId);
        model.addAttribute("homologous", homologous);
        return "iqgs/homologous-gene";
    }

    /**
     * 基因家族
     *
     * @param req
     * @param resp
     * @param model
     * @return
     */
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
     *
     * @param req
     * @param resp
     * @return
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
     *
     * @param req
     * @param resp
     * @param model
     * @return
     */
    @RequestMapping("/detail/expression")
    public String detailForExpresssion(HttpServletRequest req, HttpServletResponse resp, Model model) {
        String genId = req.getParameter("gen_id");
        model.addAttribute("genId", genId);
        return "iqgs/IQGS-expression";
    }

    @Autowired
    private QueryService queryService;


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

    /**
     * 按基因搜索差异变异数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/queryExpressionByGene")
    @ResponseBody
    public JSONArray queryExpressionByGene(HttpServletRequest request, HttpServletResponse response) {
        String gene = request.getParameter("gene");//具体的gene
        return studyService.queryStudyByGene(gene);
    }

    @Autowired
    private TService tService;

    /**
     * 大豆折线图数据接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/line")
    @ResponseBody
    public String line(HttpServletRequest request) {
        String genes = request.getParameter("genes");
        String json = "{}";
        logger.info("line:genes{" + genes + "}");
        if (org.apache.commons.lang.StringUtils.isNotBlank(genes)) {
            String[] gens = genes.split(",");
            GenResult genResult = tService.generateData(gens);
            json = JsonUtils.Bean2Json(genResult);
        }
        return json;
    }



    @Autowired
    private SNPService snpService;

    /**
     * 按基因搜索差异变异数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/searchDNAinGene")
    @ResponseBody
    public Map searchSNPinGene(HttpServletRequest request, HttpServletResponse response) {
        String type = request.getParameter("type");//区分snp和indel数据
        String gene = request.getParameter("gene");//具体的gene
        logger.info("queryBy " + type + " with gene:" + gene);
        Page<DNAGens> page = new Page<DNAGens>(request, response);
        return snpService.searchSNPByGene(type, gene, page);
    }
}
