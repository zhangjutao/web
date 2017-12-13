package com.gooalgene.iqgs.web;

import com.gooalgene.entity.Qtl;
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
     * {
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
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Phialophora gregata infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Fusarium infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Diaporthe phaseolorum var sojae Infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Diaporthe longicolla Infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Phytophthora sojae infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Fusarium solani f sp glycines infection"
     * },
     * {
     * "isNewRecord": false,
     * "qtlId": 16,
     * "traitName": "Reaction to Sclerotinia sclerotiorum infection"
     * }
     * ]
     * }
     */
    @RequestMapping(value = "/fetch-qtl-smarty")
    public String fetchQtlSmartyData() {
        return null;
    }
}