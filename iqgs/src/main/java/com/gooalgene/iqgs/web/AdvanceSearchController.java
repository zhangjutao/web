package com.gooalgene.iqgs.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooalgene.common.Page;
import com.gooalgene.common.constant.CommonConstant;
import com.gooalgene.common.vo.ResultVO;
import com.gooalgene.dna.service.DNAMongoService;
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
import com.gooalgene.mrna.service.StudyService;
import com.gooalgene.mrna.service.TService;
import com.gooalgene.qtl.service.QtlService;
import com.gooalgene.qtl.service.TraitCategoryService;
import com.gooalgene.qtl.views.TraitCategoryWithinMultipleTraitList;
import com.gooalgene.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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

    /**
     * @api {get} /advance-search/query-by-qtl-name 主页qtl search
     * @apiName queryByQTLName
     * @apiGroup Search
     * @apiParam {String} qtlName 用户输入的qtl name
     * @apiSuccessExample Success-Response:
     * [{
     * "id": "12491",
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "cqSeed oil-001",
     * "trait": null,
     * "type": null,
     * "chrlgId": null,
     * "chr": null,
     * "lg": null,
     * "marker1": null,
     * "marker2": null,
     * "marker": null,
     * "associatedGenesId": null,
     * "version": null,
     * "method": null,
     * "genomeStart": null,
     * "genomeStartType": null,
     * "genomeEnd": null,
     * "genomeEndType": null,
     * "lod": null,
     * "lodType": null,
     * "parent1": null,
     * "parent2": null,
     * "parent": null,
     * "ref": null,
     * "author": null,
     * "keywords": null,
     * "createtime": null
     * },
     * {
     * "id": "12492",
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "cqSeed oil-002",
     * "trait": null,
     * "type": null,
     * "chrlgId": null,
     * "chr": null,
     * "lg": null,
     * "marker1": null,
     * "marker2": null,
     * "marker": null,
     * "associatedGenesId": null,
     * "version": null,
     * "method": null,
     * "genomeStart": null,
     * "genomeStartType": null,
     * "genomeEnd": null,
     * "genomeEndType": null,
     * "lod": null,
     * "lodType": null,
     * "parent1": null,
     * "parent2": null,
     * "parent": null,
     * "ref": null,
     * "author": null,
     * "keywords": null,
     * "createtime": null
     * }]
     */
    @RequestMapping(value = "/query-by-qtl-name", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Qtl> queryByQTLName(String qtlName) {
        return qtlService.findQtlsByName(qtlName);
    }

    /**
     * @api {get} /advance-search/query-all-organic 查询所有组织以及其小组织
     * @apiName getAllOrganicAndChildren
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/query-by-qtl-name
     * @apidescription 该方法在用户点击高级搜索时发起请求，所有的下拉组织、对应小组织都是动态的
     * @apiSuccessExample Success-Response:
     * [
     * {
     * "id": "59898cfc1d78c746c0df80d0",
     * "name": "seed_All",
     * "chinese": "种子 ",
     * "children": [
     * {
     * "name": "seed",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat",
     * "chinese": "",
     * "children": [
     * {
     * "name": "seed coat endothelium",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat epidermis",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat hilum",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat inner integument",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat outer integument",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat parenchyma compartment",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat hourglass",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat parenchyma",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "seed coat palisade",
     * "chinese": "",
     * "children": []
     * }
     * ]
     * },
     * {
     * "name": "embryo",
     * "chinese": "",
     * "children": [
     * {
     * "name": "embryo proper",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "suspensor",
     * "chinese": "",
     * "children": []
     * }
     * ]
     * },
     * {
     * "name": "cotyledon",
     * "chinese": "",
     * "children": [
     * {
     * "name": "cotyledon abaxial parenchyma",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "cotyledon adaxial epidermis",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "cotyledon adaxial parenchyma",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "cotyledon vasculature",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "cotyledon abaxial epidermis",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "germinated cotyledon",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "immature cotyledon",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "embryonic cotyledon",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "embryo proper cotyledon",
     * "chinese": "",
     * "children": []
     * }
     * ]
     * },
     * {
     * "name": "axis",
     * "chinese": "",
     * "children": [
     * {
     * "name": "embryo proper axis ",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "embryonic axis",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis epidermis",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis plumules",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis vasculature",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis parenchyma",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis root tip",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis shoot apical meristem",
     * "chinese": "",
     * "children": []
     * },
     * {
     * "name": "axis stele",
     * "chinese": "",
     * "children": []
     * }
     * ]
     * },
     * {
     * "name": "endosperm",
     * "chinese": "",
     * "children": []
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/query-all-organic")
    @ResponseBody
    public List<Classifys> getAllOrganicAndChildren() {
        List<Classifys> classify = tService.getClassifyTree();
        return classify;
    }

    /**
     * @api {post} /advance-search/query-snp 获取所有SNP类型
     * @apiName fetchALLSNPCheckbox
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/query-snp
     * @apidescription 高级搜索页面加载时，获取所有SNP类型
     * @apiSuccessExample Success-Response:
     * [
     * "UTR3",
     * "UTR5",
     * "UTR5;UTR3",
     * "downstream",
     * "exonic;splicing_nonsynonymous SNV",
     * "exonic;splicing_synonymous SNV",
     * "exonic_nonsynonymous SNV",
     * "exonic_stopgain",
     * "exonic_stoploss",
     * "exonic_synonymous SNV",
     * "intergenic",
     * "intronic",
     * "splicing",
     * "upstream",
     * "upstream;downstream"
     * ]
     */
    @RequestMapping(value = "/query-snp", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getAllSNPCheckbox() {
        return searchService.findAllDistinctSNP();
    }

    /**
     * @api {post} /advance-search/query-snp 获取所有INDEL类型
     * @apiName fetchALLINDELCheckbox
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/query-indel
     * @apidescription 高级搜索页面加载时，获取所有INDEL类型
     * @apiSuccessExample Success-Response:
     * [
     * "UTR3",
     * "UTR5",
     * "downstream",
     * "exonic;splicing_stopgain",
     * "exonic_frameshift deletion",
     * "exonic_frameshift insertion",
     * "exonic_nonframeshift deletion",
     * "exonic_nonframeshift insertion",
     * "exonic_stopgain",
     * "exonic_stoploss",
     * "intergenic",
     * "intronic",
     * "splicing",
     * "upstream",
     * "upstream;downstream"
     * ]
     */
    @RequestMapping(value = "/query-indel", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getALLDistinctINDEL() {
        return searchService.findAllDistinctINDEL();
    }

    /**
     * @api {get} /advance-search/fetch-qtl-smarty qtl搜索选项二级联动
     * @apiName fetchQtlSmartyData
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/fetch-qtl-smarty
     * @apidescription 点击高级搜索时，qtl选项二级联动列表数据，无需入参，页面载入时直接向后台取即可
     * @apiSuccessExample Success-Response:
     * [
     * {
     * "traitCategoryId": 23,
     * "qtlName": null,
     * "qtlDesc": "无机物耐性",
     * "qtlOthername": null,
     * "traitLists": [
     * {
     * "traitListId": 112,
     * "qtlId": 23,
     * "traitName": "Aluminum Tolerance",
     * "qtls": [
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Al tolerance 1-2",
     * "trait": null,
     * "type": null,
     * "chrlgId": null,
     * "chr": null,
     * "lg": null,
     * "marker1": null,
     * "marker2": null,
     * "marker": null,
     * "associatedGenesId": null,
     * "version": null,
     * "method": null,
     * "genomeStart": null,
     * "genomeStartType": null,
     * "genomeEnd": null,
     * "genomeEndType": null,
     * "lod": null,
     * "lodType": null,
     * "parent1": null,
     * "parent2": null,
     * "parent": null,
     * "ref": null,
     * "author": null,
     * "keywords": null,
     * "createtime": null
     * }
     * ]
     * }
     * ]
     */
    @RequestMapping(value = "/fetch-qtl-smarty")
    @ResponseBody
    public List<TraitCategoryWithinMultipleTraitList> fetchQtlSmartyData() {
        List<TraitCategoryWithinMultipleTraitList> allTraitCategoryAndItsTraitList = traitCategoryService.findAllTraitCategoryAndItsTraitList();
        return allTraitCategoryAndItsTraitList;
    }

    /**
     * @api {post} /advance-search/confirm 选中几个qtl点击确认
     * @apiName clickConfirm
     * @apiGroup Search
     * @apiParam {int[]} chosenQtl 以选中的QTL ID数组，如chosen:[1001, 1005]
     * @apiParam {int} pageNo 页码
     * @apiParam {int} pageSize 每页数量
     * @apisamplerequest http://localhost:8081/iqgs/advance-search/confirm?choseQTL[]=1003&pageNo=1&pageSize=10
     * @apidescription 用户经过初步筛选后选中几个qtl后，后台将会返回所有符合该qtl的基因，详情数据可以参见build/clickConfirm.json文件
     * @apiSuccessExample Success-Response:
     * [
     * {
     * "geneId": "Glyma.18G141100",
     * "geneOldId": null,
     * "geneName": "ATHST,HST,PDS2",
     * "geneType": "Protein_coding",
     * "locus": "Chr18:21768218bp-21777484bp:+",
     * "length": "9266bp",
     * "species": "Glycine max",
     * "functions": null,
     * "description": "homogentisate prenyltransferase",
     * "familyId": null,
     * "id": 48833,
     * "existsSNP": true,
     * "associateQTLs": [
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "cqSeed weight-001",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Flower number 1-8",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Phytoph 14-3",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "SCN 17-4",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "SCN 18-5",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "SCN 19-4",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "SCN 29-3",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Seed length 3-2",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Seed oil 42-34",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Seed protein 36-25",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Seed weight per plant 6-6",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "shoot weight, dry 1-2",
     * "version": null,
     * "associatedGenes": null
     * },
     * {
     * "id": null,
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": null,
     * "updateDate": null,
     * "qtlName": "Somatic emb per explant 2-3",
     * "version": null,
     * "associatedGenes": null
     * }
     * ],
     * "rootTissues": []
     * }
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    @ResponseBody
    public ResultVO<DNAGeneSearchResult> clickConfirm(@RequestParam(value = "choseQTL[]") Integer[] chosenQtl, HttpServletRequest request) {
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        PageHelper.startPage(pageNo, pageSize, true);
        PageInfo<DNAGeneSearchResult> genes = dnaGenBaseInfoService.queryDNAGenBaseInfos(Arrays.asList(chosenQtl), pageNo, pageSize);
        return ResultUtil.success(genes);
    }

    @RequestMapping(value = "/gene-expression", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<GeneFPKM> advanceSearchByGeneExpression(
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
        //对找到符合FPKM值要求的所有基因进行SNP筛选
        Iterator<GeneFPKM> iterator = properGene.iterator();
        while (iterator.hasNext()) {
            String geneId = iterator.next().getGeneId();
            // todo 如果用户不选择SNP、INDEL该怎么办（后面增加该段逻辑）
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.SNP, snpConsequenceType);
            if (!geneExists) {
                iterator.remove();
            }
        }
        Iterator<GeneFPKM> indelIterator = properGene.iterator();
        while (indelIterator.hasNext()) {
            String geneId = indelIterator.next().getGeneId();
            // todo 如果用户不选择SNP、INDEL该怎么办（后面增加该段逻辑）
            boolean geneExists = dnaMongoService.checkGeneConsequenceType(geneId, CommonConstant.INDEL, indelConsequenceType);
            if (!geneExists) {
                indelIterator.remove();
            }
        }
        // 最后筛选符合QTL条件的所有基因，判断该基因是否有QTL
        Iterator<GeneFPKM> qtlIterator = properGene.iterator();
        while (qtlIterator.hasNext()) {
            String geneId = indelIterator.next().getGeneId();
            boolean insideQtl = dnaGenBaseInfoService.checkGeneHasQTL(geneId, Arrays.asList(qtlId));  //该基因是否位于该QTL集合中
            if (!insideQtl) {
                qtlIterator.remove();
            }
        }
        PageInfo<GeneFPKM> pageInfo = new PageInfo<>();
        pageInfo.setPageNum(pageNo);
        pageInfo.setPageSize(pageSize);
        pageInfo.setList(properGene);
        return pageInfo;
    }

    /**
     * @apiParam {String} snpParams 选中的SNP筛选条件，各个值之间使用","号分开
     * @apiParam {String} indelParams 选中的INDEL筛选条件，各个值之间使用","分开
     * @apiParam {Object[]} qtlParams 高级搜索中选中的qtl查询条件对象集合
     * @apiParam {int} pageNo 页码
     * @apiParam {int} pageSize 每页数量
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/search
     * @apidescription 高级搜索查询接口，结果数据与初次点击确认按钮相同，只是这里会增加更多的筛选条件
     * @apiSuccessExample Success-Response:
     * {
     * pageNum:1,
     * pageSize:100,
     * total:1,
     * geneResult:
     * [{
     * "id" : null,
     * "isNewRecord" : false,
     * "geneId" : "Glyma.02G218700",
     * "geneName" : "GLY1,SFD1",
     * "geneType" : "Protein_coding",
     * "locus" : "Chr02:40667610bp-40671395bp:+",
     * "length" : "3785bp",
     * "species" : "Glycine max",
     * "functions" : "glycerol-3-phosphate dehydrogenase [NAD(+)] 2, chloroplastic",
     * "description" : "NAD-dependent glycerol-3-phosphate dehydrogenase family protein",
     * "familyId" : null
     * }, {
     * "id" : null,
     * "isNewRecord" : false,
     * "geneId" : "Glyma.02G220100",
     * "geneName" : "GLX2-2,GLY2",
     * "geneType" : "Protein_coding",
     * "locus" : "Chr02:40797403bp-40800820bp:+",
     * "length" : "3417bp",
     * "species" : "Glycine max",
     * "functions" : "glyoxalase GLYII-1",
     * "description" : "Metallo-hydrolase/oxidoreductase superfamily protein",
     * "familyId" : null
     * }, {
     * "id" : null,
     * "isNewRecord" : false,
     * "geneId" : "Glyma.04G224100",
     * "geneName" : "GLX2-2,GLY2",
     * "geneType" : "Protein_coding",
     * "locus" : "Chr04:49456049bp-49460172bp:+",
     * "length" : "4123bp",
     * "species" : "Glycine max",
     * "functions" : null,
     * "description" : "Metallo-hydrolase/oxidoreductase superfamily protein",
     * "familyId" : null
     * } ]
     * }
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Page<DNAGenBaseInfo> advanceSearch(GeneExpressionCondition geneExpression, String snpParams, String indelParams, QTLCondition qtlParams) {
        return null;
    }

    /**
     * @api {get} /advance-search/confirm 调控网络数据接口
     * @apiName fetchAllRegularityNetworkGenes
     * @apiGroup Search
     * @apiParam {String} geneId 当前基因ID
     * @apisamplerequest http://localhost:8081/iqgs/advance-search/fetch-network-genes?geneId=Glyma.04G131800
     * @apidescription 调控网络数据接口, 完整数据参见build/regularityNetwork.json文件
     * @apiSuccessExample Success-Response:
     * {
     * "links": [
     * {
     * "source": "Glyma.04G131800",
     * "target": "Glyma.11G109400"
     * },
     * {
     * "source": "Glyma.04G131800",
     * "target": "Glyma.12G015900"
     * }
     * ],
     * "nodes": [
     * {
     * "geneId": "Glyma.04G131800",
     * "hierarchy": 0
     * },
     * {
     * "geneId": "Glyma.11G109400",
     * "hierarchy": 1
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/fetch-network-genes", method = RequestMethod.GET)
    @ResponseBody
    public RegularityResult fetchAllRegularityNetworkGenes(@RequestParam("geneId") String geneId) {
        List<RegularityLink> links = regularityNetworkService.findRelateGene(geneId);  //拿到所有links
        List<RegularityNode> nodes = regularityNetworkService.getAllDistinctGeneId(links, geneId);//拿到所有nodes
        RegularityResult result = new RegularityResult(links, nodes);
        return result;
    }
}
