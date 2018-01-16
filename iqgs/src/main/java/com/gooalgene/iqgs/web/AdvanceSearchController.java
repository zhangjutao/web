package com.gooalgene.iqgs.web;

import com.github.pagehelper.PageInfo;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.entity.DNAGenStructure;
import com.gooalgene.entity.Qtl;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.RegularityLink;
import com.gooalgene.iqgs.entity.RegularityNode;
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.GeneExpressionConditionEntity;
import com.gooalgene.iqgs.entity.condition.RegularityResult;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.gooalgene.iqgs.service.RegularityNetworkService;
import com.gooalgene.iqgs.service.SearchService;
import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.qtl.service.QtlService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.gooalgene.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 高级搜索相关接口层
 *
 * @author crabime
 * @version 2.0
 * @since 12/11/2017
 */
@Controller
@RequestMapping("/advance-search")
public class AdvanceSearchController {

    private final static Logger logger = LoggerFactory.getLogger(AdvanceSearchController.class);

    @Autowired
    private QtlService qtlService;

    @Autowired
    private TService tService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private TraitCategoryService traitCategoryService;

    @Autowired
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private RegularityNetworkService regularityNetworkService;

    @RequestMapping(value = "/query-by-qtl-name", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Qtl> queryByQTLName(String qtlName) {
        return qtlService.findQtlsByName(qtlName);
    }

    @RequestMapping(value = "/query-all-organic", method = RequestMethod.GET)
    @ResponseBody
    public List<Classifys> getAllOrganicAndChildren() {
        List<Classifys> classify = tService.getClassifyTree();
        return classify;
    }

    @RequestMapping(value = "/query-snp", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAllSNPCheckbox() {
        return searchService.findAllDistinctSNP();
    }

    @RequestMapping(value = "/query-indel", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getALLDistinctINDEL() {
        return searchService.findAllDistinctINDEL();
    }

    @RequestMapping(value = "/fetch-qtl-smarty")
    @ResponseBody
    public List<TraitCategoryWithinMultipleTraitList> fetchQtlSmartyData() {
        List<TraitCategoryWithinMultipleTraitList> allTraitCategoryAndItsTraitList = traitCategoryService.findAllTraitCategoryAndItsTraitList();
        return allTraitCategoryAndItsTraitList;
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<DNAGeneSearchResult> clickConfirm(@RequestParam(value = "chosenQtl[]") Integer[] chosenQtl, HttpServletRequest request) {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        PageInfo<DNAGeneSearchResult> genes = dnaGenBaseInfoService.queryDNAGenBaseInfos(null, null, null, null, Arrays.asList(chosenQtl), null, null, pageNo, pageSize);
        return ResultUtil.success(genes);
    }

    @RequestMapping(value = "/advanceSearch", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<DNAGeneSearchResult> advanceSearch(
            @RequestBody GeneExpressionCondition geneExpressionCondition) throws InterruptedException {
        int pageNo = geneExpressionCondition.getPageNo();
        int pageSize = geneExpressionCondition.getPageSize();
        List<GeneExpressionConditionEntity> entities = geneExpressionCondition.getGeneExpressionConditionEntities();
        List<String> selectSnpConsequenceType = geneExpressionCondition.getSnpConsequenceType();  //已选SNP集合
        List<String> selectIndelConsequenceType = geneExpressionCondition.getIndelConsequenceType();  //已选INDEL集合
        List<Integer> associateGeneIdArray = geneExpressionCondition.getQtlId();  //已选qtl集合
        List<Integer> firstHierarchyQtlId = geneExpressionCondition.getFirstHierarchyQtlId();  //一级搜索选中的QTL ID集合
        DNAGenBaseInfo baseInfo = geneExpressionCondition.getGeneInfo();  //高级搜索中根据基因名字查询传入的基因名或ID
        DNAGenStructure geneStructure = geneExpressionCondition.getGeneStructure();  //高级搜索中根据基因结构搜索相应基因
        // 需要在这个地方分页,现在为存入SNP、INDEL,仍然需要跨库查询
        PageInfo<DNAGeneSearchResult> properGene =
                dnaGenBaseInfoService.queryDNAGenBaseInfos(entities, selectSnpConsequenceType,
                        selectIndelConsequenceType, firstHierarchyQtlId, associateGeneIdArray, baseInfo, geneStructure, pageNo, pageSize);
        return ResultUtil.success(properGene);
    }

    @RequestMapping(value = "/fetch-network-genes", method = RequestMethod.GET)
    @ResponseBody
    public ResultVO<RegularityResult> fetchAllRegularityNetworkGenes(@RequestParam("geneId") String geneId) {
        List<RegularityLink> links = regularityNetworkService.findRelateGene(geneId);  //拿到所有links
        Set<RegularityNode> nodes = regularityNetworkService.getAllDistinctGeneId(links, geneId);//拿到所有nodes
        RegularityResult result = new RegularityResult(links, nodes);
        if (links.isEmpty()){
            ResultUtil.error(500, "无可调控基因");
        }
        return ResultUtil.success(result);
    }

    @RequestMapping(value = "/jumpTest", method = RequestMethod.GET)
    public String jumpTest(){
        return "iqgs/AdvanceSearchTest";
    }
}
