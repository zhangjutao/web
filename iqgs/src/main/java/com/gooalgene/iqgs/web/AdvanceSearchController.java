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
     * @api {get} /advance-search/query-by-qtl-name 根据用户输入的qtl name进行模糊查询，返回模糊查询与之匹配的一组qtl name集合
     * @apiName queryByQTLName
     * @apiGroup Search
     * @apiParam {String} qtlName 用户输入的qtl name
     * @apiSuccessExample Success-Response:
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
     * }
     */
    @RequestMapping(value = "/query-by-qtl-name", method = RequestMethod.GET)
    public List<Qtl> queryByQTLName(String qtlName) {
        return null;
    }
}
