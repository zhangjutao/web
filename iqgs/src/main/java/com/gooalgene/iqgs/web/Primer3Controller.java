package com.gooalgene.iqgs.web;

import com.gooalgene.primer.bean.Primer;
import com.gooalgene.primer.interfaces.Primer3Service;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
     * @apiParam {String} primerSizeMin 引物序列范围下限 对应输入框 primerSize-min
     * @apiParam {String} primerSizeMax 引物序列范围上限 对应输入框 primerSize-max
     * @apiParam {String} primerGCMin         GC含量下限
     * @apiParam {String} primerGCgcMax         GC含量上限
     * @apiParam {String} primerTMMin         tm下限
     * @apiParam {String} primerTMMax         tm上限
     * @apiParam {String} productSizeMin  产物大小下限
     * @apiParam {String} productSizeMax  产物大小上限
     * @apiParam {String} primerFMin      primerF下限
     * @apiParam {String} primerFMax      primerF上限
     * @apiParam {String} primerRMin      primerR下限
     * @apiParam {String} primerRMax      primerR上限
     * @apiParam {String} sequence     输入的基因序列
     * @apiSuccessExample Success-Response:
     * [{"group":1,
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
     * "primerSizeMin":100
     * "primerSizeMax":1000
     * "gcMin":10
     * "gcMax":100
     * "tmMin":10
     * "tmMax":20
     * "productSizeMin":100
     * "productSizeMax":1000
     * "sequence":"AGAGTAGATAGTAGTATAGTAGATATG"
     **/
    @RequestMapping(value = "/getPrimer", method = RequestMethod.POST)
    public List<Primer> getPrimer(String primerSizeMin, String primerSizeMax, String primerGCMin, String primerGCMax, String primerTMMin, String primerTMMax, String productSizeMin, String productSizeMax, String sequence) {
        String param1 = "PRIMER_TASK=generic&PRIMER_MASK_KMERLIST_PREFIX=&PRIMER_MASK_FAILURE_RATE=0.1&PRIMER_MASK_5P_DIRECTION=1&PRIMER_MASK_3P_DIRECTION=1&PRIMER_MISPRIMING_LIBRARY=NONE&";
        String squenceTemplate = "SEQUENCE_TEMPLATE=" + sequence + "&";
        String param2 = "MUST_XLATE_PRIMER_PICK_LEFT_PRIMER=1&MUST_XLATE_PRIMER_PICK_RIGHT_PRIMER=1&SEQUENCE_PRIMER=&SEQUENCE_INTERNAL_OLIGO=&SEQUENCE_PRIMER_REVCOMP=&SEQUENCE_ID=&" +
                "SEQUENCE_TARGET=&SEQUENCE_OVERLAP_JUNCTION_LIST=&SEQUENCE_EXCLUDED_REGION=&SEQUENCE_PRIMER_PAIR_OK_REGION_LIST=&" +
                "SEQUENCE_TARGET=&SEQUENCE_OVERLAP_JUNCTION_LIST=&SEQUENCE_EXCLUDED_REGION=&SEQUENCE_PRIMER_PAIR_OK_REGION_LIST=&SEQUENCE_INCLUDED_REGION=&SEQUENCE_START_CODON_POSITION=&SEQUENCE_INTERNAL_EXCLUDED_REGION=&SEQUENCE_FORCE_LEFT_START=-1000000&SEQUENCE_FORCE_RIGHT_START=-1000000&SEQUENCE_FORCE_LEFT_END=-1000000&SEQUENCE_FORCE_RIGHT_END=-1000000&SEQUENCE_QUALITY=&PRIMER_MIN_QUALITY=0&PRIMER_MIN_END_QUALITY=0&PRIMER_QUALITY_RANGE_MIN=0&PRIMER_QUALITY_RANGE_MAX=100&Pick+Primers=Pick+Primers&Upload=&";
        String primerMinSize = "PRIMER_MIN_SIZE=" + primerSizeMin + "&";
        String primerOptSize = "PRIMER_OPT_SIZE=&";
        String primerMaxSize = "PRIMER_MAX_SIZE=" + primerSizeMax + "&";
        String primerMinTm = "PRIMER_MIN_TM=" + primerTMMin + "&";
        String primerOptTm = "PRIMER_OPT_TM=&";
        String primerMaxTm = "PRIMER_MAX_TM=" + primerTMMax + "&";
        String param3 = "&PRIMER_PAIR_MAX_DIFF_TM=5.0&PRIMER_TM_FORMULA=1&PRIMER_PRODUCT_MIN_TM=-1000000.0&PRIMER_PRODUCT_OPT_TM=0.0&PRIMER_PRODUCT_MAX_TM=1000000.0&";
        String primerMinGc = "PRIMER_MIN_GC=" + primerGCMin + "&";
        String param4 = "PRIMER_OPT_GC_PERCENT=50.0&";
        String primerMaxGc = "PRIMER_MAX_GC=" + primerGCMax + "&";
        String primerProductSizeRange = "PRIMER_PRODUCT_SIZE_RANGE=" + productSizeMin + "-" + productSizeMax+"&";
        String param5 = "PRIMER_NUM_RETURN=5&PRIMER_MAX_END_STABILITY=9.0&PRIMER_MAX_LIBRARY_MISPRIMING=12.00&PRIMER_PAIR_MAX_LIBRARY_MISPRIMING=20.00&MUST_XLATE_PRIMER_THERMODYNAMIC_OLIGO_ALIGNMENT=1&PRIMER_MAX_SELF_ANY_TH=45.0&PRIMER_MAX_SELF_END_TH=35.0&PRIMER_PAIR_MAX_COMPL_ANY_TH=45.0&PRIMER_PAIR_MAX_COMPL_END_TH=35.0&PRIMER_MAX_HAIRPIN_TH=24.0&PRIMER_MAX_SELF_ANY=8.00&PRIMER_MAX_SELF_END=3.00&PRIMER_PAIR_MAX_COMPL_ANY=8.00&PRIMER_PAIR_MAX_COMPL_END=3.00&PRIMER_MAX_TEMPLATE_MISPRIMING_TH=40.00&PRIMER_PAIR_MAX_TEMPLATE_MISPRIMING_TH=70.00&PRIMER_MAX_TEMPLATE_MISPRIMING=12.00&PRIMER_PAIR_MAX_TEMPLATE_MISPRIMING=24.00&PRIMER_MAX_NS_ACCEPTED=0&PRIMER_MAX_POLY_X=4&PRIMER_INSIDE_PENALTY=-1.0&PRIMER_OUTSIDE_PENALTY=0&PRIMER_FIRST_BASE_INDEX=1&PRIMER_GC_CLAMP=0&PRIMER_MAX_END_GC=5&PRIMER_MIN_LEFT_THREE_PRIME_DISTANCE=3&PRIMER_MIN_RIGHT_THREE_PRIME_DISTANCE=3&PRIMER_MIN_5_PRIME_OVERLAP_OF_JUNCTION=7&PRIMER_MIN_3_PRIME_OVERLAP_OF_JUNCTION=4&PRIMER_SALT_MONOVALENT=50.0&PRIMER_SALT_CORRECTIONS=1&PRIMER_SALT_DIVALENT=1.5&PRIMER_DNTP_CONC=0.6&PRIMER_DNA_CONC=50.0&PRIMER_SEQUENCING_SPACING=500&PRIMER_SEQUENCING_INTERVAL=250&PRIMER_SEQUENCING_LEAD=50&PRIMER_SEQUENCING_ACCURACY=20&MUST_XLATE_PRIMER_LIBERAL_BASE=1&MUST_XLATE_PRIMER_EXPLAIN_FLAG=1&PRIMER_WT_SIZE_LT=1.0&PRIMER_WT_SIZE_GT=1.0&PRIMER_WT_TM_LT=1.0&PRIMER_WT_TM_GT=1.0&PRIMER_WT_GC_PERCENT_LT=0.0&PRIMER_WT_GC_PERCENT_GT=0.0&PRIMER_WT_SELF_ANY_TH=0.0&PRIMER_WT_SELF_END_TH=0.0&PRIMER_WT_HAIRPIN_TH=0.0&PRIMER_WT_TEMPLATE_MISPRIMING_TH=0.0&PRIMER_WT_SELF_ANY=0.0&PRIMER_WT_SELF_END=0.0&PRIMER_WT_TEMPLATE_MISPRIMING=0.0&PRIMER_WT_NUM_NS=0.0&PRIMER_WT_LIBRARY_MISPRIMING=0.0&PRIMER_WT_SEQ_QUAL=0.0&PRIMER_WT_END_QUAL=0.0&PRIMER_WT_POS_PENALTY=0.0&PRIMER_WT_END_STABILITY=0.0&PRIMER_WT_MASK_FAILURE_RATE=0.0&PRIMER_PAIR_WT_PRODUCT_SIZE_LT=0.0&PRIMER_PAIR_WT_PRODUCT_SIZE_GT=0.0&PRIMER_PAIR_WT_PRODUCT_TM_LT=0.0&PRIMER_PAIR_WT_PRODUCT_TM_GT=0.0&PRIMER_PAIR_WT_COMPL_ANY_TH=0.0&PRIMER_PAIR_WT_COMPL_END_TH=0.0&PRIMER_PAIR_WT_TEMPLATE_MISPRIMING_TH=0.0&PRIMER_PAIR_WT_COMPL_ANY=0.0&PRIMER_PAIR_WT_COMPL_END=0.0&PRIMER_PAIR_WT_TEMPLATE_MISPRIMING=0.0&PRIMER_PAIR_WT_DIFF_TM=0.0&PRIMER_PAIR_WT_LIBRARY_MISPRIMING=0.0&PRIMER_PAIR_WT_PR_PENALTY=1.0&PRIMER_PAIR_WT_IO_PENALTY=0.0&PRIMER_INTERNAL_MIN_SIZE=18&PRIMER_INTERNAL_OPT_SIZE=20&PRIMER_INTERNAL_MAX_SIZE=27&PRIMER_INTERNAL_MIN_TM=57.0&PRIMER_INTERNAL_OPT_TM=60.0&PRIMER_INTERNAL_MAX_TM=63.0&PRIMER_INTERNAL_MIN_GC=20.0&PRIMER_INTERNAL_OPT_GC_PERCENT=50.0&PRIMER_INTERNAL_MAX_GC=80.0&PRIMER_INTERNAL_MAX_SELF_ANY_TH=47.00&PRIMER_INTERNAL_MAX_SELF_END_TH=47.00&PRIMER_INTERNAL_MAX_HAIRPIN_TH=47.00&PRIMER_INTERNAL_MAX_SELF_ANY=12.00&PRIMER_INTERNAL_MAX_SELF_END=12.00&PRIMER_INTERNAL_MIN_QUALITY=0&PRIMER_INTERNAL_MAX_NS_ACCEPTED=0&PRIMER_INTERNAL_MAX_POLY_X=5&PRIMER_INTERNAL_MISHYB_LIBRARY=NONE&PRIMER_INTERNAL_MAX_LIBRARY_MISHYB=12.00&PRIMER_INTERNAL_SALT_MONOVALENT=50.0&PRIMER_INTERNAL_DNA_CONC=50.0&PRIMER_INTERNAL_SALT_DIVALENT=1.5&PRIMER_INTERNAL_DNTP_CONC=0.0&PRIMER_INTERNAL_WT_SIZE_LT=1.0&PRIMER_INTERNAL_WT_SIZE_GT=1.0&PRIMER_INTERNAL_WT_TM_LT=1.0&PRIMER_INTERNAL_WT_TM_GT=1.0&PRIMER_INTERNAL_WT_GC_PERCENT_LT=0.0&PRIMER_INTERNAL_WT_GC_PERCENT_GT=0.0&PRIMER_INTERNAL_WT_SELF_ANY_TH=0.0&PRIMER_INTERNAL_WT_SELF_END_TH=0.0&PRIMER_INTERNAL_WT_HAIRPIN_TH=0.0&PRIMER_INTERNAL_WT_SELF_ANY=0.0&PRIMER_INTERNAL_WT_SELF_END=0.0&PRIMER_INTERNAL_WT_NUM_NS=0.0&PRIMER_INTERNAL_WT_LIBRARY_MISHYB=0.0&PRIMER_INTERNAL_WT_SEQ_QUAL=0.0&PRIMER_INTERNAL_WT_END_QUAL=0.0";

        String params = param1 + squenceTemplate + param2 + primerMinSize +
                primerOptSize + primerMaxSize + primerMinTm +
                primerOptTm + primerMaxTm + param3 + primerMinGc +
                param4 + primerMaxGc + primerProductSizeRange + param5;
        List<Primer> primerList = primer3Service.getPrimer("http://192.168.14.128/cgi-bin/primer3web_results.cgi?", params);
        return primerList;
    }
}
