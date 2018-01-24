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