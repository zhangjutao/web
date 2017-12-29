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

/**
 * @api {get} /advance-search/query-all-organic 查询所有组织及二级组织
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

/**
 * @api {post} advance-search/advanceSearch 高级搜索接口
 * @apiName advanceSearch
 * @apiGroup Search
 * @apiParam {String[]} childTissues[] 子组织名字数组
 * @apiParam {String[]} snpConsequenceType[] 选中的SNP序列类型名字集合
 * @apiParam {String[]} indelConsequenceType[] 选中的INDEL序列类型名字集合
 * @apiParam {int[]} qtlId[] 选中的所有QTL关联基因ID,对应fetch-qtl-smarty接口返回的associatedGenesId字段
 * @apiParam {int} begin 基因表达量最小FPKM值
 * @apiParam {int} end 基因表达量最大FPKM值
 * @apiParam {int} pageNo 页码
 * @apiParam {int} pageSize 每页数量
 * @apisamplerequest
 *  http://localhost:8081/iqgs/advance-search/advanceSearch?childTissues[]=stem internode&pageNo=1&pageSize=10&begin=5&snpConsequenceType[]=upstream,downstream&indelConsequenceType[]=downstream, upstream&qtlId[]=997,1952,33,39,186,195&end=10
 * @apidescription 高级搜索查询接口，结果数据与初次点击确认按钮相同，只是这里会增加更多的筛选条件，我的测试请求返回结果参见build/advanceSearch.json文件
 *  注意:该接口是post请求!
 */

/**
 * @api {get} /advance-search/fetch-network-genes 调控网络数据接口
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