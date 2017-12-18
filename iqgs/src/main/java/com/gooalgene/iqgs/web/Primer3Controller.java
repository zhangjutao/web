package com.gooalgene.iqgs.web;

import com.gooalgene.primer.interfaces.Primer3Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * primer3 引物设计相关接口
 *
 * @author zhangyanping
 * @since 2017/12/15
 */
@RestController
@RequestMapping(value = "/primer")
public class Primer3Controller {
    @Autowired
    private Primer3Service primer3Service;

    /**
     * @api {post} /primer/getPrimer
     * @apiName getPrimer
     * @apiGroup Primer
     * @apiParam {int} primerSizeMin 引物序列范围下限 对应输入框 primerSize-min
     * @apiParam {int} primerSizeMax 引物序列范围上限 对应输入框 primerSize-max
     * @apiParam {int} gcMin         GC含量下限
     * @apiParam {int} gcMax         GC含量上限
     * @apiParam {int} tmMin         tm下限
     * @apiParam {int} tmMax         tm上限
     * @apiParam {int} productSizeMin  产物大小下限
     * @apiParam {int} productSizeMax  产物大小上限
     * @apiParam {int} primerFMin      primerF下限
     * @apiParam {int} primerFMax      primerF上限
     * @apiParam {int} primerRMin      primerR下限
     * @apiParam {int} primerRMax      primerR上限
     * @apiParam {String} sequence     输入的基因序列
     * @apiSuccessExample Success-Response:
     *[{"group":1,
     * "type":"primerF",
     * "position":"154",
     * "length":"20",
     * "tm":"58.98",
     * "gc":"55.00",
     * "any":"0.00",
     * "three":"0.00",
     * "hairpin":"0.00",
     * "sequence":"CTTTGTCATCTACGGCTGGC",
     * "link":"www.baidu.com"},
     * {"group":2,
     * "type":"primerR",
     * "position":"375",
     * "length":"20",
     * "tm":"58.98",
     * "gc":"55.00",
     * "any":"0.00",
     * "three":"0.00",
     * "hairpin":"0.00",
     * "sequence":"CTCCAGATTAACTTGGCGGC",
     * "link":"www.baidu.com"}
     * ]
     * @apiSuccessExample Success-Request:
     * {"primerSizeMin":100,
     *  "primerSizeMax":1000,
     *  "gcMin":10,
     *  "gcMax":100,
     *  "tmMin":10,
     *  "tmMax":20,
     *  "productSizeMin":100,
     *  "productSizeMax":1000,
     *  "primerFMin":100,
     *  "primerFMax":1000,
     *  "primerRMin":10,
     *  "primerRMax":1000,
     *  "sequence":"AGAGTAGATAGTAGTATAGTAGATATG"
     * }
     **/
    @RequestMapping(value = "/getPrimer")
    public void getPrimer(int primerSizeMin, int primerSizeMax, int gcMin, int gcMax, int tmMin, int tmMax, int productSizeMin, int productSizeMax, int primerFMin, int primerFMax, int primerRMin, int primerRMax,String sequence) {


    }
}
