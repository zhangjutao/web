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
import com.gooalgene.iqgs.entity.condition.DNAGeneSearchResult;
import com.gooalgene.iqgs.entity.condition.GeneExpressionCondition;
import com.gooalgene.iqgs.entity.condition.QTLCondition;
import com.gooalgene.iqgs.entity.condition.RegularityResult;
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
            @RequestParam(value = "childTissues[]") String[] childTissues,
            @RequestParam(value = "snpConsequenceType[]") String[] snpConsequenceType,
            @RequestParam(value = "indelConsequenceType[]") String[] indelConsequenceType,
            @RequestParam(value = "qtlId[]") Integer[] qtlId,
            @RequestParam(value = "begin") int begin,
            @RequestParam(value = "end") int end,
            HttpServletRequest request) throws InterruptedException {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        List<String> totalClassify = new ArrayList<>(); //所有分类总类
        for (int i = 0; i < childTissues.length; i++) {
            List<String> classifyAndItsChildren = classifyService.findClassifyAndItsChildren(childTissues[i]);
            totalClassify.addAll(classifyAndItsChildren);
        }
        List<Study> allSampleRun = studyService.querySampleRunByTissueForClassification(totalClassify);  //从MySQL中查询所有的sampleRun
        List<GeneFPKM> properGene = new ArrayList<>();
        for (int i = 0; i < allSampleRun.size(); i++) {
            //筛选出该sample下满足FPKM条件的基因
            int sampleId = Integer.valueOf(allSampleRun.get(i).getId());
            List<GeneFPKM> allProperGene = fpkmService.findProperGeneUnderSampleRun(sampleId, begin, end);
            properGene.addAll(allProperGene);
        }
        properGene = properGene.subList(0, 20);
        logger.info(Arrays.toString(properGene.toArray()));
        //对找到符合FPKM值要求的所有基因进行SNP筛选
        Iterator<GeneFPKM> iterator = properGene.iterator();
        while (iterator.hasNext()) {
            String geneId = iterator.next().getGeneId();
            // todo 如果用户不选择SNP、INDEL该怎么办（后面增加该段逻辑）
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.SNP, Arrays.asList(snpConsequenceType));
            if (!geneExists) {
                iterator.remove();
            }
        }
        Iterator<GeneFPKM> indelIterator = properGene.iterator();
        while (indelIterator.hasNext()) {
            String geneId = indelIterator.next().getGeneId();
            // todo 如果用户不选择SNP、INDEL该怎么办（后面增加该段逻辑）
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.INDEL, Arrays.asList(indelConsequenceType));
            if (!geneExists) {
                indelIterator.remove();
            }
        }
        // 最后筛选符合QTL条件的所有基因，判断该基因是否有QTL
        Iterator<GeneFPKM> qtlIterator = properGene.iterator();
        while (qtlIterator.hasNext()) {
            String geneId = qtlIterator.next().getGeneId();
            //根据基因ID找到associateGeneId,该associateGeneId对应QTL表中associateGene
            //最初页面加载时QTL查询二级联动接口：traitCategoryService.findAllTraitCategoryAndItsTraitList已返回该字段
            boolean insideQtl = dnaGenBaseInfoService.checkGeneHasQTL(geneId, Arrays.asList(qtlId));  //该基因是否位于该QTL集合中
            if (!insideQtl) {
                qtlIterator.remove();
            }
        }
        //最后的loop，将GeneFPKM转换为想要的搜索结果
        Iterator<GeneFPKM> convertIterator = properGene.iterator();
        //存放所有搜索结果的集合
        List<DNAGeneSearchResult> searchResultList = new ArrayList<>();
        DNAGeneSearchResult searchResult = null;
        //知道基因ID后，可以查询包含该基因的所有QTL
        while (convertIterator.hasNext()){
            searchResult = new DNAGeneSearchResult();
            GeneFPKM geneFPKM = convertIterator.next();
            String geneId = geneFPKM.getGeneId();
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
}
