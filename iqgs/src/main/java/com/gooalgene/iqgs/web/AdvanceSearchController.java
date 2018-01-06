package com.gooalgene.iqgs.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.service.DNAMongoService;
import com.gooalgene.entity.Associatedgenes;
import com.gooalgene.entity.MrnaGens;
import com.gooalgene.entity.Qtl;
import com.gooalgene.entity.Study;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import com.gooalgene.iqgs.entity.GeneFPKM;
import com.gooalgene.iqgs.entity.RegularityLink;
import com.gooalgene.iqgs.entity.RegularityNode;
import com.gooalgene.iqgs.entity.condition.*;
import com.gooalgene.iqgs.service.DNAGenBaseInfoService;
import com.gooalgene.iqgs.service.FPKMService;
import com.gooalgene.iqgs.service.RegularityNetworkService;
import com.gooalgene.iqgs.service.SearchService;
import com.gooalgene.mrna.entity.Classifys;
import com.gooalgene.mrna.service.ClassifyService;
import com.gooalgene.mrna.service.MrnaGensService;
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.qtl.service.QtlService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.gooalgene.utils.ResultUtil;
import com.google.common.base.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.gooalgene.common.constant.CommonConstant.EXONIC_NONSYNONYMOUSE;

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
    private StudyService studyService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private FPKMService fpkmService;

    @Autowired
    private TraitCategoryService traitCategoryService;

    @Autowired
    private DNAGenBaseInfoService dnaGenBaseInfoService;

    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private RegularityNetworkService regularityNetworkService;

    @Autowired
    private DNAMongoService dnaMongoService;

    @Autowired
    private MrnaGensService mrnaGensService;

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
        PageHelper.startPage(pageNo, pageSize, true);
        PageInfo<DNAGeneSearchResult> genes = dnaGenBaseInfoService.queryDNAGenBaseInfos(Arrays.asList(chosenQtl), pageNo, pageSize);
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
        // 需要在这个地方分页,现在为存入SNP、INDEL,仍然需要跨库查询
        PageInfo<AdvanceSearchResultView> properGene =
                fpkmService.findProperGeneUnderSampleRun(entities, selectSnpConsequenceType, selectIndelConsequenceType, associateGeneIdArray, pageNo, pageSize);
        logger.debug("Gene Expression筛选出来基因总量为:" + properGene.getList().size());
        //最后的loop，将GeneFPKM转换为想要的搜索结果
        Iterator<AdvanceSearchResultView> convertIterator = properGene.getList().iterator();
        //存放所有搜索结果的集合
        List<DNAGeneSearchResult> searchResultList = new ArrayList<>();
        DNAGeneSearchResult searchResult = null;
        AdvanceSearchResultView advanceSearchResultView = null;
        //知道基因ID后，可以查询包含该基因的所有QTL
        while (convertIterator.hasNext()){
            searchResult = new DNAGeneSearchResult();
            advanceSearchResultView = convertIterator.next();
            String geneId = advanceSearchResultView.getGeneId();
            searchResult.setGeneId(geneId);
            searchResult.setGeneOldId(advanceSearchResultView.getGeneOldId());
            MrnaGens mrnaGene = mrnaGensService.findMRNAGeneByGeneId(geneId);
            //使用Guava Optional防止空指针异常
            MrnaGens optional = Optional.<MrnaGens>fromNullable(mrnaGene).or(new MrnaGens());
            searchResult.setGeneName(optional.getGeneName());
            searchResult.setFunction(optional.getFunctions());
            //allAssociateGenes中包含QTL_NAME
            List<Associatedgenes> allAssociateGenes = dnaGenBaseInfoService.findAllQTLNamesByGeneId(geneId);
            searchResult.setAssociateQTLs(allAssociateGenes);
            //判断该基因是否存在SNP(直接从查询结果中拿)
            searchResult.setExistsSNP(advanceSearchResultView.existSNP());
            //获取所有FPKM大于30的root组织(直接从查询结果中拿)
            searchResult.setRootTissues(advanceSearchResultView.getLargerThanThirtyTissue());
            searchResultList.add(searchResult);
        }
        return ResultUtil.success(searchResultList);
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
