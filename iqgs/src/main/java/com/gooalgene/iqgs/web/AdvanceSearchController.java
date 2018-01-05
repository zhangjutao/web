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
    public PageInfo<DNAGeneSearchResult> advanceSearch(
            @RequestBody GeneExpressionCondition geneExpressionCondition,
            HttpServletRequest request) throws InterruptedException {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        List<GeneExpressionConditionEntity> entities = geneExpressionCondition.getGeneExpressionConditionEntities();
        // 需要在这个地方分页,现在为存入SNP、INDEL,仍然需要跨库查询
        List<String> properGene = fpkmService.findProperGeneUnderSampleRun(entities);
        logger.debug("Gene Expression筛选出来基因总量为:" + properGene.size());
        //对找到符合FPKM值要求的所有基因进行SNP筛选
        Iterator<String> iterator = properGene.iterator();
        while (iterator.hasNext()) {
            String geneId = iterator.next();
            // todo 如果用户不选择SNP,对以上集合不修改
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.SNP, geneExpressionCondition.getSnpConsequenceType());
            if (!geneExists) {
                iterator.remove();
            }
        }
        Iterator<String> indelIterator = properGene.iterator();
        while (indelIterator.hasNext()) {
            String geneId = indelIterator.next();
            // todo 如果用户不选择INDEL该怎么办,对以上集合不修改
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.INDEL, geneExpressionCondition.getIndelConsequenceType());
            if (!geneExists) {
                indelIterator.remove();
            }
        }
        // 最后筛选符合QTL条件的所有基因，判断该基因是否有QTL
        Iterator<String> qtlIterator = properGene.iterator();
        while (qtlIterator.hasNext()) {
            String geneId = qtlIterator.next();
            //根据基因ID找到associateGeneId,该associateGeneId对应QTL表中associateGene
            //最初页面加载时QTL查询二级联动接口：traitCategoryService.findAllTraitCategoryAndItsTraitList已返回该字段
            boolean insideQtl = dnaGenBaseInfoService.checkGeneHasQTL(geneId, geneExpressionCondition.getQtlId());  //该基因是否位于该QTL集合中
            if (!insideQtl) {
                qtlIterator.remove();
            }
        }
        //最后的loop，将GeneFPKM转换为想要的搜索结果
        Iterator<String> convertIterator = properGene.iterator();
        //存放所有搜索结果的集合
        List<DNAGeneSearchResult> searchResultList = new ArrayList<>();
        DNAGeneSearchResult searchResult = null;
        //知道基因ID后，可以查询包含该基因的所有QTL
        while (convertIterator.hasNext()){
            searchResult = new DNAGeneSearchResult();
            String geneId = convertIterator.next();
            MrnaGens mrnaGene = mrnaGensService.findMRNAGeneByGeneId(geneId);
            //使用Guava Optional防止空指针异常
            MrnaGens optional = Optional.<MrnaGens>fromNullable(mrnaGene).or(new MrnaGens());
            searchResult.setGeneName(optional.getGeneName());
            searchResult.setFunction(optional.getFunctions());
            //allAssociateGenes中包含QTL_NAME
            List<Associatedgenes> allAssociateGenes = dnaGenBaseInfoService.findAllQTLNamesByGeneId(geneId);
            searchResult.setAssociateQTLs(allAssociateGenes);
            //拿到该基因在SNP上所有consequenceType
            Set<String> allConsequenceType = dnaMongoService.getAllConsequenceTypeByGeneId(geneId, CommonConstant.SNP);
            boolean exists = allConsequenceType.contains(EXONIC_NONSYNONYMOUSE);
            if (exists){
                searchResult.setExistsSNP(true);
            }
            //获取所有FPKM大于30的root组织
            List<String> rootTissues = dnaGenBaseInfoService.getFPKMLargerThanThirty(geneId);
            searchResult.setRootTissues(rootTissues);
            searchResultList.add(searchResult);
        }
        PageInfo<DNAGeneSearchResult> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(searchResultList);
        return pageInfo;
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
