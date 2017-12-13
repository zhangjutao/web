package com.gooalgene.iqgs.web;

import com.gooalgene.common.Page;
import com.gooalgene.entity.Qtl;
import com.gooalgene.iqgs.entity.DNAGenBaseInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    /**
     * @api {get} /advance-search/query-by-qtl-name 主页qtl search
     * @apiName queryByQTLName
     * @apiGroup Search
     * @apiParam {String} qtlName 用户输入的qtl name
     * @apiSuccessExample Success-Response:
     * [{
     * "id": "12329",
     * "isNewRecord": false,
     * "createTime": "2017-07-11 03:45:18",
     * "qtlName": "Al tolerance 1-2",
     * "trait": "Aluminum Tolerance",
     * "type": "QTL_inorganic",
     * "chrlgId": 51,
     * "marker1": "cr207_1",
     * "marker2": "",
     * "associatedGenesId": 3861,
     * "version": "Glycine_max.V1.0.23.dna.genome",
     * "method": "ANOVA",
     * "genomeStart": 19.0,
     * "genomeEnd": 21.0,
     * "parent1": "Young",
     * "parent2": "PI229358",
     * "ref": "Aluminum tolerance associated with quantitative trait loci derived from soybean PI 416937 in hydroponics\u0026nbsp;Crop Sci. 2000, 40(2):538-545",
     * "author": "Bianchi-Hall et al. 2000"
     * },
     * {
     * "id": "12329",
     * "isNewRecord": false,
     * "createTime": "2017-07-11 03:45:18",
     * "qtlName": "Al tolerance 1-2",
     * "trait": "Aluminum Tolerance",
     * "type": "QTL_inorganic",
     * "chrlgId": 51,
     * "marker1": "cr207_1",
     * "marker2": "",
     * "associatedGenesId": 3861,
     * "version": "Glycine_max.V1.0.23.dna.genome",
     * "method": "ANOVA",
     * "genomeStart": 19.0,
     * "genomeEnd": 21.0,
     * "parent1": "Young",
     * "parent2": "PI229358",
     * "ref": "Aluminum tolerance associated with quantitative trait loci derived from soybean PI 416937 in hydroponics\u0026nbsp;Crop Sci. 2000, 40(2):538-545",
     * "author": "Bianchi-Hall et al. 2000"
     * }]
     */
    @RequestMapping(value = "/query-by-qtl-name", method = RequestMethod.GET)
    public List<Qtl> queryByQTLName(String qtlName) {
        return null;
    }

    /**
     * @api {get} /advance-search/query-all-organic 查询所有组织以及其小组织
     * @apiName getAllOrganicAndChildren
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/query-by-qtl-name
     * @apidescription 该方法在用户点击高级搜索时发起请求，所有的下拉组织、对应小组织都是动态的
     * @apiSuccessExample Success-Response:
     * {
     * "seed" : [ "seed", "seed coat", "embryo" ],
     * "pod" : [ "seed", "dogTail" ]
     * }
     */
    @RequestMapping(value = "/query-all-organic")
    public String getAllOrganicAndChildren() {
        return null;
    }

    /**
     * @api {get} /advance-search/fetch-qtl-smarty qtl搜索选项二级联动
     * @apiName fetchQtlSmartyData
     * @apiGroup Search
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/fetch-qtl-smarty
     * @apidescription 点击高级搜索时，qtl选项二级联动列表数据，无需入参，页面载入时直接向后台取即可
     * @apiSuccessExample Success-Response:
     * [{
     * "qtlName": "Fungal resistance QTL",
     * "qtlDesc": "真菌抗性",
     * "qtlId": 16,
     * "qtlOthername": "QTL_fungal",
     * "traitLists": [
     * {
     * "traitName": "Reaction to Phakopsora pachyrhizi infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Phialophora gregata infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Fusarium infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Diaporthe phaseolorum var sojae Infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Diaporthe longicolla Infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * }
     * ]
     * },
     * {
     * "qtlName": "Insect resistance QTL",
     * "qtlDesc": "抗虫性",
     * "qtlId": 17,
     * "qtlOthername": "QTL_insectv",
     * "traitLists": [
     * {
     * "traitName": "Reaction to Aulacorthum solani, choice",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Aulacorthum solani, no choice",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Fusarium infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Diaporthe phaseolorum var sojae Infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * },
     * {
     * "traitName": "Reaction to Diaporthe longicolla Infection",
     * "includeQtlNames": ["Asian Soybean Rust 2-1", "Asian Soybean Rust 2-2"]
     * }
     * ]
     * }]
     */
    @RequestMapping(value = "/fetch-qtl-smarty")
    public String fetchQtlSmartyData() {
        return null;
    }

    /**
     * @api {post} /advance-search/confirm 选中几个qtl点击确认
     * @apiName clickConfirm
     * @apiGroup Search
     * @apiParam {String} chosenQtl 以选中的qtl字符串拼接，如"Al tolerance 1-2&Asian Soybean Rust 2-1",使用&符号拼接
     * @apiParam {int} pageNo 页码
     * @apiParam {int} pageSize 每页数量
     * @apisamplerequest http://localhost:8080/iqgs/advance-search/confirm
     * @apidescription 用户经过初步筛选后选中几个qtl后，后台将会返回所有符合该qtl的基因
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
    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public Page<DNAGenBaseInfo> clickConfirm(String chosenQtl) {
        return null;
    }

    /**
     * @api {post} /advance-search/search 高级搜索查询接口
     * @apiName advanceSearch
     * @apiGroup Search
     * @apiParam {String} geneExpression 已选中的基因表达量字符串拼接，
     * 如"Al tolerance 1-2&Asian Soybean Rust 2-1",使用&符号拼接，后台将按照该符号解析产生一个集合
     * @apiParam {String} snpParams 选中的SNP筛选条件，各个值之间使用","号分开
     * @apiParam {String} indelParams 选中的INDEL筛选条件，各个值之间使用","分开
     * @apiParam {String} qtlParams 高级搜索中选中的qtl查询条件字符串拼接，
     * 如"Seed&Asian Soybean Rust 2-1",使用&符号拼接，后台将按照该符号解析产生一个集合
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
    public Page<DNAGenBaseInfo> advanceSearch(String geneExpression, String snpParams, String indelParams, String qtlParams){
        return null;
    }
}
