/**
 * 按基因搜索差异变异数据
 *
 * @api {GET} /iqgs/searchDNAinGene 按基因搜索差异变异数据
 * @apiName searchSNPinGene
 * @apiGroup DNAGeneBaseInfo
 * @apiParam {String} type 区分SNP和INDEL数据,默认传SNP
 * @apiParam {String} gene 基因详情页对应的基因id
 * @apiParam {list} ctype 默认为all，用户进行consequencetype筛选时将用户输入的所有类型以“"ctype":"ctype1,ctype2,..."形式返回
 * @apiParam {int} pageNo 页码
 * @apiParam {int} pageSize 每页包含的条数
 * @apiParamExample 参数请求实例1（用户不进行consequencetype筛选时）:
 * {
     * "gene":"Glyma.01G004900",
     * "type":"SNP",
     * "ctype":"all",
     * "pageNo":2,
     * "pageSize":10
     * }
 * @apiParamExample 参数请求实例2（用户筛选consequencetype时）:
 * {
     * "gene":"Glyma.01G004900",
     * "type":"SNP",
     * "ctype":"upstream,downstream,intronic",
     * "pageNo":2,
     * "pageSize":10
     * }
 * @apiParamExample 参数请求实例3（用户点选INDEL时）:
 * {
     * "gene":"Glyma.01G004900",
     * "type":"INDEL",
     * "ctype":"all",
     * "pageNo":2,
     * "pageSize":10
     * }
 * @apiSuccessExample 成功返回，有数据时的格式:
 * {
     * "total": 148,
     * "data":[{
     * "id": "GlyS0010456979",
     * "chr": "Chr01",
     * "pos": 456979,
     * "ref": "T",
     * "alt": "G",
     * "qual": 733.77,
     * "maf": 0.001,
     * "type": "downstream",
     * "gene": "Glyma.01G004900",
     * "effect": "---",
     * "consequencetype": "downstream",
     * "majorallen": "T",
     * "minorallen": "G",
     * "major": 0.9974120082815735,
     * "minor": 0.002587991718426501
     * },
     * {
     * "id": "GlyS0010457020",
     * "chr": "Chr01",
     * "pos": 457020,
     * "ref": "T",
     * "alt": "C",
     * "qual": 908.77,
     * "maf": 0.001,
     * "type": "downstream",
     * "gene": "Glyma.01G004900",
     * "effect": "---",
     * "consequencetype": "downstream",
     * "majorallen": "T",
     * "minorallen": "C",
     * "major": 0.9989648033126294,
     * "minor": 0.0010351966873706005
     * }
     * ],
     * "pageNo": 2,
     * "pageSize": 10
     * }
 * @apiSuccessExample 成功返回，查无数据时的格式：
 * {
     * "total": 0,
     * "data": [],
     * "pageNo": 1,
     * "pageSize": 10
     * }
 * @apiErrorExample 传回参数名称不对或缺少时：
 * {
     * "msg": "系统异常",
     * "code": -1,
     * "status": null,
     * "data": null
     * }
 */

/**
 * 按基因搜索差异变异数据,按照value倒叙排列
 *
 *
 * @api {post} /iqgs/queryExpressionByGene
 * @apiGroup DNAGeneBaseInfo
 * @apiName queryExpressionByGene
 * @apiParam {String} gene
 * @apiSuccessExample [
 * {
     * "total": 531,
     * "data": [
     * {
     * "id": "59cc4d41921d173eab533743",
     * "isNewRecord": false,
     * "gene": "Glyma.01G004900",
     * "samplerun": {
     * "name": "SRR352327",
     * "type": "leaf",
     * "study": "SRP008837",
     * "value": 10.1823
     * },
     * "study": {
     * "id": "314",
     * "isNewRecord": false,
     * "remarks": null,
     * "createTime": "2017-09-27 16:14:05",
     * "updateDate": null,
     * "sraStudy": "SRP008837",
     * "study": "Transcriptome analysis of Williams 82 Glycine max (Soybean) 10 days after infection (dai) v.s. 0 dai (control)",
     * "sampleName": "W82_10dai_MS06-1",
     * "isExpression": 1,
     * "sampleRun": "SRR352327",
     * "tissue": "leaf",
     * "tissueForClassification": "leaf",
     * "preservation": "",
     * "treat": "inoculated with a Mississippi isolate of Phakopsora pachyrhizi (MS06-1)",
     * "stage": "three-weeks-old",
     * "geneType": "",
     * "phenoType": "",
     * "environment": "",
     * "geoLoc": "",
     * "ecoType": "",
     * "collectionDate": "",
     * "coordinates": "",
     * "ccultivar": "Williams 82",
     * "scientificName": "Glycine max",
     * "pedigree": "",
     * "reference": "",
     * "institution": "USDA, Parsa Hosseini",
     * "submissionTime": "2013/5/20",
     * "instrument": "Illumina Genome Analyzer IIx",
     * "libraryStrategy": "RNA-Seq",
     * "librarySource": "TRANSCRIPTOMIC",
     * "libraryLayout": "SINGLE",
     * "insertSize": "",
     * "readLength": "",
     * "spots": 3510311,
     * "experiment": "SRX100853",
     * "links": "https://trace.ncbi.nlm.nih.gov/Traces/sra?study=SRP008837",
     * "keywords": null,
     * "tissueKeywords": null,
     * "tissues": null
     * }
     * },
     * {…}.
     * ]
     */