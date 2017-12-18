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
 *
 * @author sauldong
 * @version 1.0
 * @since 2017/10/12
 */
@Controller
@RequestMapping("/iqgs")
public class DNAGenBaseInfoController {

    Logger logger = LoggerFactory.getLogger(DNAGenBaseInfoController.class);

    @Autowired
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private StudyService studyService;

    @Autowired
    private QueryService queryService;

    /**
     * <span style="color:red;">请求URL</span>: http://host:port/contextPath/iqgs/index <br>
     * 请求方式: GET OR POST
     *
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
     *
     * @param req  http请求
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

    /**
     * @api {get} /iqgs/detail/sequence
     * @apiName detailForSequence
     * @apiGroup detail
     * @apiDescription
     * 前端采用 ${data} 接收数据
     * @apiParam {String} gen_id
     * @apiSuccessExample {json}Example data on success:
     * data：
     * [{"type":"CDS","sequence":"ATGGAGTCTGGAGCCCTAGTGTCGTCCGAGAAAGAAGGGCAGCAAGGAGCCTCGTATACGTACTGGGTTAGGAAAATAACGGAAGATGCAGCGCCTTTGCCTGTGCCTCGTAAGCTCAACCCAGAAGATGTTCCCCCTTGTCATTCTCAATCTCAATCTCGGTCTGCCACGCTTGGCTCAGCTTGGAATCGCGCTGGGACATGGGAGGAGAAAAGTCTAAACAATTGGGCAACTCCGAGAATTAAGGAGTTGCTTATCTCATTAGGCTCCATACAGTTCTCCTTTGGCAGAGCAGAAGTAGAAGATGTAACAAAATGTGTTGGCGATGCATTCATGGTGATAGTTCGGAACAAGAAACGTGTTGGTTACACATATGAGTTGAGCTTAAAAGTCAAAGGGGAATGGATCATACAAGGAGAGAAGAAGTTCGTTGGGGGTCATATAGATGTCCCAGAATTCTCATTTGGTGAACTAGATGAATTGCAGGTTGAAGTGAGACTGAGTGAAGCAAGGGATATCTTGCATCAAGACAAGACACAGATTTGCAACGACTTGAAGCTATTTTTACAGCCTGTTCGGGAAAAGTTGCTTCAATTTGAACAGGAACTCAAAGATAGATAG"},
     * {"type":"peptide","sequence":"MESGALVSSEKEGQQGASYTYWVRKITEDAAPLPVPRKLNPEDVPPCHSQSQSRSATLGSAWNRAGTWEEKSLNNWATPRIKELLISLGSIQFSFGRAEVEDVTKCVGDAFMVIVRNKKRVGYTYELSLKVKGEWIIQGEKKFVGGHIDVPEFSFGELDELQVEVRLSEARDILHQDKTQICNDLKLFLQPVREKLLQFEQELKDR*"},
     * {"type":"gDNA","sequence":"GACTTTGGACATTTTGAGTTTCCAATTCCTTCTTCTTCGATCTACGGGAAACTTCCGGAAGACCAATTATTAATTGCATCCCGCTGTCCATTGTTTTGTCAGTTGAAGGGCTTTTGTTTCTATTCCCCTGCGGTTGGCTGCTGACGAATCTCTTATCTGCGTCCTTAAATGTGAGTTCATTTTTGTTTTAATTTGATGGTGCAACTTAAAATTTTGTTTTCTTTGAAATTGAAAATCTGTTATTTGCTGGGAGAAGTAGTAAAATGGAGTCTGGAGCCCTAGTGTCGTCCGAGAAAGAAGGGCAGCAAGGAGCCTCGTATACGTACTGGGTTAGGAAAATAACGGAAGATGCAGCGCCTTTGCCTGTGCCTCGTAAGCTCAACCCAGAAGATGTTCCCCCTTGTCATTCTCAATCTCAATCTCGGTCTGCCACGCTTGGCTCAGCTTGGAATCGCGTAAGTATGACCTTACTTGTTGCTCCCTCTCTTTTGAAAAGAAAAATCAGTGATTTGGAATCGCGTATGTATTTTTTCTAATATACCCATTAATAACAATTTTGGTTATTTTATTCCTGGGAATCTAATTATATCCCTTGAAACAAACACCACCCAAAGGCTAAAGGGCTGTTGTTTTTTTCTTGTATCAACGAATCTGAATGGTGCAAAATTAATACAGGCTGGGACATGGGAGGAGAAAAGTCTAAACAATTGGGCAACTCCGAGAATTAAGGTTTGTATCTCATATCCATTTTGTCTTTTTTCTTCTTCCCTTTATGTTTGTCTTTGTGTATCTATGTCTATGTTGTGAATCTTGTATGTGATTGTGTTCAACTATTAGATTAGTAGAGTAGAATTACTCTTTTTTTATTATTATTTCGCACAGTTAGCAATTATAGTTAACTCAACTAATCTTGGGTTAGTTGACAGTTGTAATGGCAGCTGTGTACAGCTGTCCTGACTCTAGTATAAAACTAGAGTTATAACTGTTTTTCATTTGGGTTGAAGTTATCAACTTCTTCAATTTCTGTTCAGAGTTTTCTCTCTTTCGTTTATACAAAGCTTTTATCAACTATCTTAGAAAATGTCTGATTTCATCTCGCAATCTTACATAAAATGTCATTAACTTGTTGCAGCTAATCATATTTAATTTGTTTGTTTCTTGAAGCTGTGGTTAGTTATTCCTTGCATTTTTTTCAGGAGTTGCTTATCTCATTAGGCTCCATACAGTTCTCCTTTGGCAGAGCAGAAGTAGAAGATGTAACAAAATGTGTTGGCGATGTGAGTGTATTATATTTATATGATTTACTTTTAACTTTGTCTATTCCTGTATTACAGGACATTCACATATAAAGCTTTGGGTCAGTTTGGTGGCCCCTTTGAAAATAAGAGTGAAAAATTATGGGATGAATTGTATATGAAAACATTATCTTTGAAGCTTTTATCCCCAATTTTACTAGCATCCAAACAGACTACTAGAGCTTCCAATTTAAGTCTTGGATTGGATGATTTAATTTTTTTTAATAGAATTTTCTTATCCAATGATTGAATGTATGTTTATATTGCCATTCATCTATTTTTTAATGTTAATGATGCGCTTCAAATTGGTGGAGTCTAGAGAATCAAGTTTTCTTTCAGCTAAATTTTTTCAAGGTACTAGTTAGGTTCAGCTTTATAGGAAAATGTTGTAAGTCTCACATTGGTTGCATTTATAATTATTGTTACTCGTGTAGATTATGAGTTACCGAAGCTTTAGCTTTAGTTTGGACAAAATTATTTAGTTTTGTATATTTTAATGTTGTGCACAGTTTCTATCATTCAGAAATAGTATCAAAGCTCTTGATCTGGGAGACATTGCTTCTGCAAATCACTGTTGGACAGCTGCCTCCGTTGCTTTTTTTTTTTCCTTTTCCTTTTTGCACTTTCATATTGCTCAATTCTCTTTGTGGAAATCAAGGTCACCACTGCTTTTATGATTGGAGATATCATTAAGCAAACCACCATCCAATGTTAGGCCAAACCACTTGCAACCTATCTCTGGCAACCTGCTTATGCATTGGTGAACCCCTGCATGTATCAGTATACACCCCCCCCCCCCCCTCTTCTGACTGCCTGGGGCAGTGTGTCTGGCCACTATTCATCCCACTGCCAGTCCCAGTTGTCTCATCTCTGACTCCTCTACCAGTTTCTGTCCTCAGACTCACCTTGTACAATTGGTACCAACCTGCTAGCCATCCTTCTAAGCATAACCATTTTACTATCTGACTCGGTCTCTACCACCTTGTCTAATACAATCAACCATGACTCAACCACCACCGAATGTACCTTGCTGGAAATGAGACTTTTACTTATTGGGAATTGCTGTGTTAAAGTTTAGGATATATGAATGTTCAGATTTGTGCATAGTACGTATCTTGAGTACTTAGGGAATTGCATTTATAAATATATGGCTAATTTGATCTCTATTCAATTTAGTACCTTTATTTTTAAAAAGATCAATTTGGTCCCATAATTTTCAAAACTGATGCAATGTTATCCTTTGCGTTAGCTCTATGACGGAAGTGTCCTACACGACAACTACATGTCTTTTTTTATTGTGTCACGTACACAATAGTGTCGTTTCTCACAATTGGAAAGAAAAGGAAACATTGCATCAATTTTGAAAATTGTGGGATCAAATTGAACTAGTTAACAATAGAGGGACCAAATTGAACCAATTAAATAAATAAAAAGACCAAATTATTAATTTAGCCACTTTTAGTCATGATGAGGGATTAATAATGCTTTGTTATTGTTTAATGTAAGCACTAAAAACAAATAAAATTTAAATGGGGACCAAAACTTACAAATCATCAGATTTTAAAGACCAAAAATATATTTAAACTTTCATTTTTAATGTGCTCTTCCTTTTTACTTACCAAAGTTATATATATATATATATATATATATATATATATGAACTTCTTCAATAACAAATTTTAATAATTTGCGCTCTCCCCCTATTTGTGGTGAGTTACATACTTGATACATGTGATTCACTTGAGTTTATATTTCAAAGAAGAAACAAAAGGAAAGGAAAATTTTGAATATTGTTGCCCAAAATAGTTATATATATTTACGATGGACTAATATTTCTTGATTGTACATTTCTTAGGCATTCATGGTGATAGTTCGGAACAAGAAACGTGTTGGTTACACATATGAGTTGAGCTTAAAAGTCAAAGGTGAGGTACTATCAACCATTCTTTTTAACGATTTCAATCTAGCTGTTCTTTATTCTTTTTTATTTGTTTCAATCTAATTACTTTATAAGCCATGCTGATTTGCAAAGGGATGTAGGGGAATGGATCATACAAGGAGAGAAGAAGTTCGTTGGGGGTCATATAGATGTCCCAGAATTCTCATTTGGTGAACTAGATGAATTGCAGGTACCTCTTAATGTTGATTTTGATTTAAACACTTTTGAGCTACTAAAAAATTGAGAAGGGTCATAAGCTGTTTGGAAAAGTGCATGGTCAGAAGAGTTGACTTTAATTATCCACAATTGAGGAAGTAGAGACAATGCTATAAAAATGTGTAGGAATTTGATGGATGCGCCCTTACTCTCCTTCACAGTATTAGTAAAATAATGTGCATATGTACCATCTTCCACTAGTTTTTGTTAAATTTCATCTTAAAACTAATTGGCATTAAGTGAAGTTGTTCAAGAGATATATTCGCATCCCAAGAATTAAGGCAGGGGATGTGAGACCTTCCAGCACCCGTGTTCCACAGATGCCTGTCAGAACAGGCTAACTTATCAGAATAGGCAATTTACCAAGATGGATTATAGGCTTTGATACCATGTTAGCTTTCATCTTAAAACCAATTGGTGTTAAGTAAAATTGATCAACAAATACCCCAAGAATTGAGGCTAGCAATGTGGTACCTTCCAACAGTTTTGTGGGAAGCTCCACAATCGACCAATACCACAACCTTTCTGCCTGCTATGGATCTTCAAACCTTAAATGATTTCTTTGTATCCTCTTTGATTAATTGGCCTTTCCTACTCTCATTTCTTTCCCAACCTCCCCTAAAACCATTACTTAGTATTGTTTATTCTTACAAACATGACCTAGACCATACTTTTTCACTTTGTGTGCTGTTAGAAAATTTTGATATAGCCCAATCATGTTTGATCAATTTAGCTGACCTCACCTTGTACAATAAAGTGCGATTGTCATTTGTTTGTGTGATTATCTTGTTGCTGTATACAATACAGCAAGCCTAGGATCTTGACTTGGCTGGAATAGCTGATCAATACCTAAAGCTTGAGCTAAGAACCAGTTTTGCATATGAAGGGCATTTACTCATACGGGCAGCTGGAAGTGTTGGATTTGTGCTTCCTGTGATAAATTATATGATGCATATTTTGTTCTTTTTAGCTACTAGTCAGTTATTTCTAATTTTCTATTTTCTTCATACCTGACTGCTCTTAGACATCATTAGCAGTTTATACCGTCACAATCAAAGTGGTATTAATCTTGCAGTTTTCCTGAGAACCCATCCAACCATGTACTAATCCCACAACCATATCTCACCAATTGACCCTTTCAAACACATTATCTTAATTTTAAACCCCTTAAAATCCATAATCTGCCTAATACAAGACAACTTTAAGTCTTACTTCTGGAACACTTACCCTTTTGATGTCCTACGTCACTGTTCTTGATTTAAGATGTAATTTTTAAACCCACGTGCATAAGCATTTACCTTTTTTTCTTCGGAAGAGTCATTGCAATGCTGACTGCTCTCAGTAAATGTAGAATATCTGCTCTTGTTTAAGCATTGGGTTTGCGGGTGTTCCTTGATCCTGGGAATGGATTCTCTACTGGTTGCATTGCTCCTCACTAGAAAAGTGGTGAGCATCTATAAAAGGCACTTTGCCCTAGTTAGTGGATGTCCAAAGTGGAAGGAATACCCCATCTACTATCTAGTATGTGTATGTGGCTATGTGCTGCCTCTGCTTACGTGTGTCCTCTCTTTGTACGGCTACGTGGGTGAGAACACACTCATTTTCTTAGTACAATTGTTAATACCCATATAACTAGTTTTGGCATTATAGGCTTAGGCACGGTAATGCGTGTGCTTCTAGGAAGTATATGTTGTGGGACCCGCAGCGAGAGACCTATAACCTATTGGTTTGCATATATAAACCTCGCTGCTTCGATTAAATGACTAGCTATTTGCTTATATGGTTTGTTTGCTTCTTCCGGCAAAAAAATTTCAACAGGTTGAAGTGAGACTGAGTGAAGCAAGGGATATCTTGCATCAAGACAAGACACAGATTTGCAACGACTTGAAGCTATTTTTACAGCCTGTTCGGGAAAAGTTGCTTCAATTTGAACAGGAACTCAAAGATAGATAGATAGAAAGAGGTAGTTTTCGTATTTGTGACAAAAACCATGCAGTTTTTGTAATTTGAGTCGGTACTTGTATTTCTAGTACAATCTCTTCCCGAAATAAAGTTATGTTAATGTCTGGACCAAAACTACAAATATGGAAGTTCTCTTGGGTAAATTCGAACATGAGGCCTAAGCATTTCAATCATCATACATTAAATTAAATTAAATATAATATATGGCGTGGTTTTGCTCCTGAATCT"},
     * {"type":"upstream2k","sequence":"ATTTAACTTCATGGATTAGATATTCATCTAAACTGGTCAAAATCAGTAAAATAGACTTTAAATCGGTTAAATCCGGTTCATTTGATTGTTGTCTAACCGGTTTTTTATCCTTTGTCACTCACTTTTTCCCATCGATTCTACAACTTTTGGGTGGTCCCCATAGGCCCTATCCTTTACCATGGTCTAGTTATCTAGCCATTCAAGGGGTCCCTTGAGTTGATCCTATGAAGGTAGTGTGATTTTATCGAAGTTAGACAAATATGGGTAATGACTTATCTCTGTAAGGTTTGCATGGCCTTAGATCTATAGGATTCCACTCGCTCATCTAGTTTATATGTCCATTCTCGTTGGACACCACGAATACACCCAACACTAGGTTGAGAGCCCACCAAATGCTCTTAACTTACATGCCTATCCATATGCATATGACATGTATATGCAAACCAAACATGTTTTACGACTCCATTCCCATTCATAACATATACTTAGGAATCCATCTAGCAATTCCATAAGCTCAACAACACCATGATAATTGTACAAAATAAAATCATAGATCAAACCGCGCATTATGAATATACATCACTCTTCATTTCCTACTATACGTCTATGACCTTAATGACTTATAAACATGATAATTAGAGCTATACTAACACAATTAAGTTTATTCTTGTCTCATTTGAAATATAAGAAGATTATCTTCAACTTTACCCCAATACCTAGTTTTGAATTATATTGTGTCTAAAATTACAGTCTATATCAAGACTTTACTTATTACTGACAAGTTGACCTTTATTCACTAATTTGATCATTTCTAGAGTTATACGCAATATTTTTAAGTAAGATCGAAGTCATTAGTTAGCTAACATTCAGGTCTACAACTTTCATGAGGATCACTTTTCCTAACTCGGTCATCTAAGTCACCTACAGAATCTTTCAATTCGCTAAGCGAGTTCTGCTCACCCAGCGAGTAAAACTCTTTTTTCTCTCTCTTAGAATTATCTCGAGGCAGTGAGGTTTGTTCGCCCAACGAGACGGTAGAATTCTACGATTCACGATTTTGCCTCAAAATAGAACATCCCAACCCCCAAATCCGTACCAAACCAAATTTTGATCAATTCAACATATATATTCGTCATTCAACAACCAAAAGTCACAAAATAAACATCAAAACTCAGCACCCACAATAATTTTCACATTAAAAGTTTCTCTTACCTAAATAGGACCTTAGGTTTTCAACCTTTTGAAGAAGGATGAAGCAAAGAGGAACTTAGTGAATTTCTAAGTGAACTTCCTCCACTTGTGATCTCTAACAGGGTAGTAGACCTTAAGCCAACATCAAAACAACTTCAAGCTAGGTTTTACCACCAAATTCATTCAAGAGCTCATGCAAGAAAAATAGGAATTGAGCTTAAGAAAAATAAGAGAAGAAGGAATTATTGTTTACCAATGAAATCAAGAAATGAACTAAGGAACCAAGATGTCTTTGGATAGTTTCATCATGTAAGCTCTCAAGAAAAAGAGATTTTGAGCACAAACTGGGGAATGGAGAGAAAACGTGGAAGGGGTTGAAATTTTACAAGCTTAGAAATGGATTTGTGTATCCAACAAATTTCTAATTGATTACTTGATTTCTTCTATTTTCACTCTTCATCAACATATTTTTGTGATAAGATATTTGGACCCAACAATATCTAAGTCTATTAAAACTCATAGACCACTCAAGCAATCATATAAATATAAAGTCAACTACTACATCAATAACAAAATAACAGTCAAATTTTCATCTTACAAAATAAAATAAATTGAATGTTTGATAACATCAAAGAATAAAGTAAATAAGCATGATATTATAATATGCAACTTTCAAGCTAAATTTATTTTAAAACTAATCATATTTTAAAATTAATTTTAAATTTTTTTAAAGTTAAAACTAAAAACTAATTATTATTTTTAAAATTTTCGAAATTCAACCCTAAAAATTACCGAGACCTAAAATAGTT"},
     * {"strand":"+","start":1,"end":170,"length":170,"feature":"five_prime_UTR"},
     * {"strand":"+","start":257,"end":263,"length":7,"feature":"five_prime_UTR"},
     * {"strand":"+","start":264,"end":455,"length":192,"feature":"CDS"},
     * {"strand":"+","start":676,"end":729,"length":54,"feature":"CDS"},
     * {"strand":"+","start":1197,"end":1277,"length":81,"feature":"CDS"},
     * {"strand":"+","start":3151,"end":3220,"length":70,"feature":"CDS"},
     * {"strand":"+","start":3336,"end":3424,"length":89,"feature":"CDS"},
     * {"strand":"+","start":5269,"end":5403,"length":135,"feature":"CDS"},
     * {"strand":"+","start":5404,"end":5640,"length":237,"feature":"three_prime_UTR"}]
     * @apiErrorExample {json} Error-Response:
     *{"error": "no data"}
     */
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
        if(json.toString()!=null){
            model.addAttribute("data", json.toString());
        }else{
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

    /**
     * @api {get} /iqgs/detail/origin 基因的同源基因信息获取
     * @apiName detailForOrigin
     * @apiGroup DNAGeneBaseInfo
     * @apiParam {String} gen_id 基因详情页对应的基因id
     * @apidescription 返回页面转发（到homologous-gene.jsp），通过EL表达式取到后台查询的值。
     * @apiSuccessExample model structure:
     * {
     * "geneId": "Glyma.01G004900",
     * "homologous": [
     * {
     * "isNewRecord": false,
     * "orthologSpecies": "Arabidopsis thaliana",
     * "geneId": "Glyma.01G004900",
     * "OrthologGeneId": "AT5G65790.1",
     * "orthologGeneDescription": "myb domain protein 68",
     * "relationship":"many-to-one"
     * }
     * ]
     * }
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
     * @api {get} /iqgs/detail/family 基因的基因家族信息获取
     * @apiName detailForFamily
     * @apiGroup DNAGeneBaseInfo
     * @apiParam {String} gen_id 基因详情页对应的基因id
     * @apidescription 返回页面转发（到gene-family.jsp），通过EL表达式取到后台查询的值。
     * @apiSuccessExample model structure:
     * {
     * "hasFamilyFlg":true,
     * "dnaGenFamilyRels":{"geneId":"Glyma.04G202000","familyId":"LFY"},
     * "familyId":"LFY",
     * "dnaGenFamily":{
     * "isNewRecord":false,
     * "familyId":"LFY",
     * "treeJson": {
     * "b_value": 0,
     * "branch": "0.0186335",
     * "children": [
     * {
     * "branch": "0.0186335",
     * "name": "Glyma.04G202000",
     * "node_id": 1
     * },
     * {
     * "branch": "0.0186335",
     * "name": "Glyma.06G163600",
     * "node_id": 2
     * }
     * ],
     * "name": "TN3",
     * "node_id": 3
     * }
     * },
     * "structureData":{
     * "max_length": 3078,
     * "data": [
     * {
     * "geneName": "LFY,LFY3",
     * "geneID": "Glyma.04G202000",
     * "length": 3079,
     * "structure": [
     * {
     * "type": "three_prime_UTR",
     * "start": 0,
     * "end": 141
     * },
     * {
     * "type": "CDS",
     * "start": 142,
     * "end": 504
     * },
     * {
     * "type": "CDS",
     * "start": 1392,
     * "end": 1522
     * },
     * {
     * "type": "CDS",
     * "start": 2601,
     * "end": 3078
     * }
     * ]
     * },
     * {
     * "geneName": "LFY,LFY3",
     * "geneID": "Glyma.06G163600",
     * "length": 2931,
     * "structure": [
     * {
     * "type": "CDS",
     * "start": 0,
     * "end": 477
     * },
     * {
     * "type": "CDS",
     * "start": 976,
     * "end": 1364
     * },
     * {
     * "type": "CDS",
     * "start": 2334,
     * "end": 2696
     * },
     * {
     * "type": "three_prime_UTR",
     * "start": 2697,
     * "end": 2930
     * }
     * ]
     * }
     * ]
     * },
     *"geneId":"Glyma.04G202000"
     * }
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
        System.out.println(model);
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
     * @api {post} /iqgs/queryExpressionByGene
     * @apiGroup detail
     * @apiName queryExpressionByGene
     * @apiParam {String} gen_id
     * @apiSuccessExample [
     * {
     * "expressionValue": 0,
     * "study": "A new strategy to identify the long-distance mobile peptides from xylem sap",
     * "treat": "grown under 16-h light/8-h dark cycles at 24℃ without inoculation of rhizobia",
     * "isExpression": 0,
     * "scientificName": "Glycine max",
     * "tissue": "stem internode",
     * "instrument": "Illumina Genome Analyzer IIx",
     * "tissueForClassification": "stem internode",
     * "insertSize": "",
     * "submissionTime": "2015/8/26",
     * "sampleName": "stem internode",
     * "reference": "Okamoto S, Suzuki T, Kawaguchi M, et al. A comprehensive strategy for identifying long‐distance mobile peptides in xylem sap[J]. Plant Journal, 2015, 84(3):611-620.",
     * "preservation": "",
     * "institution": "ERATO Higashiyama Live-Holonics Project",
     * "experiment": "DRX026629",
     * "geneId": "Glyma.01G004900",
     * "sampleRun": "DRR029571",
     * "ccultivar": "Enrei",
     * "links": "https://trace.ncbi.nlm.nih.gov/Traces/sra?study=DRP002726",
     * "id": 1,
     * "ecoType": "",
     * "libraryStrategy": "RNA-Seq",
     * "coordinates": "",
     * "geoLoc": "",
     * "librarySource": "TRANSCRIPTOMIC",
     * "collectionDate": "",
     * "libraryLayout": "SINGLE",
     * "pedigree": "",
     * "phenoType": "",
     * "environment": "",
     * "stage": "18 day-after-germination",
     * "readLength": "",
     * "geneType": "",
     * "createTime": "2017-09-28 00:14:05",
     * "spots": 12346873,
     * "sraStudy": "DRP002726"
     * },
     * {…}.
     * ]
     */
    @RequestMapping("/queryExpressionByGene")
    @ResponseBody
    public JSONArray queryExpressionByGene(HttpServletRequest request, HttpServletResponse response) {
        String gene = request.getParameter("gene");
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
