package com.gooalgene.mrna.web;

import com.gooalgene.mrna.service.GeneService;
import com.gooalgene.mrna.service.StudyService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * mRNA
 * Created by Shiyun on 2017/7/26 0010.
 */
@Controller
@RequestMapping("/diffgene")
public class GeneController {

    Logger logger = LoggerFactory.getLogger(GeneController.class);

    @Autowired
    private StudyService studyService;

    @Autowired
    private GeneService geneService;


    /**
     * 查询基础信息接口
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/studyBasicInfo")
    @ResponseBody
    public JSONObject studyBasicInfo(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        logger.info("studyBasicInfo id:" + id + ",type:" + type);
        return studyService.queryStudyBasicInfo(id, type);
    }


    /**
     * 查询差异基因列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/studyComparison")
    @ResponseBody
    public JSONObject studyComparison(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String gene = request.getParameter("gene");
        String logFoldChange = request.getParameter("logFoldChange");
        String adjustedPValue = request.getParameter("adjustedPValue");
        String sort = request.getParameter("sort");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        logger.info("studyComparison id:" + id + ",gene:" + gene + ",logFoldChange:" + logFoldChange +
                ",adjustedPValue:" + adjustedPValue + ",sort:" + sort + ",pageNo:" + pageNo + ",pageSize:" + pageSize);
        return geneService.queryStudyComparison(id, gene, logFoldChange, adjustedPValue, sort, pageNo, pageSize);
    }

    /**
     * 查询表达基因列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/studyExpression")
    @ResponseBody
    public JSONObject studyExpression(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        String gene = request.getParameter("gene");
        String expressionValue = request.getParameter("expressionValue");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        logger.info("studyExpression id:" + id + ",gene:" + gene + ",expressionValue:" + expressionValue +
                ",pageNo:" + pageNo + ",pageSize:" + pageSize);
        return geneService.queryStudyExpression(id, gene, expressionValue, pageNo, pageSize);
    }

    /**
     * 查询基因列表
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/genes")
    @ResponseBody
    public JSONObject genes(HttpServletRequest request, HttpServletResponse response) {
        String gene = request.getParameter("gene");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        logger.info("studyComparison gene:" + gene + ",pageNo:" + pageNo + ",pageSize:" + pageSize);
        return geneService.queryGenes(gene, pageNo, pageSize);
    }


//    @RequestMapping("/studyComparisonDataExport")
//    @ResponseBody
//    public void studyComparisonDataExport(HttpServletRequest request, HttpServletResponse response) {
//        String id = request.getParameter("id");
//        String gene = request.getParameter("gene");
//        String logFoldChange = request.getParameter("logFoldChange");
//        String adjustedPValue = request.getParameter("adjustedPValue");
//        String sort = request.getParameter("sort");
//        String columns = request.getParameter("choices");
//        logger.info("studyComparisonDataExport id:" + id + ",gene:" + gene + ",logFoldChange:" + logFoldChange +
//                ",adjustedPValue:" + adjustedPValue + ",sort:" + sort + ",choices:" + columns);
//        if (StringUtils.isNoneBlank(columns)) {
//            Study study = studyService.queryById(id);
//            if (study != null) {
//                String fileName = study.getStudy();
//                String content = geneService.queryStudyComparisonToExport(study.getSraStudy(), gene, logFoldChange, adjustedPValue, sort, columns.split(","));
//                Tools.toDownload(fileName, content, response);
//            }
//        }
//    }

//    @RequestMapping("/studyExpressionDataExport")
//    @ResponseBody
//    public void studyExpressionDataExport(HttpServletRequest request, HttpServletResponse response) {
//        String id = request.getParameter("id");
//        String gene = request.getParameter("gene");
//        String expressionValue = request.getParameter("expressionValue");
//        String columns = request.getParameter("choices");
//        logger.info("studyExpressionDataExport id:" + id + ",gene:" + gene + ",expressionValue:" + expressionValue +
//                ",choices:" + columns);
//        if (StringUtils.isNoneBlank(columns)) {
//            Study study = studyService.queryById(id);
//            if (study != null) {
//                String fileName = study.getStudy();
//                String content = geneService.queryStudyExpressionToExport(study.getSraStudy(), gene, expressionValue, columns.split(","));
//                Tools.toDownload(fileName, content, response);
//            }
//        }
//    }
}
