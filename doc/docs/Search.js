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
 * @api {get} advance-search/query-all-organic 查询所有组织及二级组织
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
     * }
 * ]
 */

/**
 * @api {get} advance-search/confirm 选中几个qtl点击确认
 * @apiName clickConfirm
 * @apiGroup Search
 * @apiParam {int[]} chosenQtl 以选中的QTL ID数组，chosenQtl:[1001, 1005]
 * @apiParam {int} pageNo 页码
 * @apiParam {int} pageSize 每页数量
 * @apiSamplerequest http://localhost:8081/iqgs/advance-search/confirm?choseQTL[]=1003&pageNo=1&pageSize=10
 * @apidescription 用户经过初步筛选后选中几个qtl后，后台将会返回所有符合该qtl的基因，详情数据可以参见build/clickConfirm.json文件
 * @apiSuccessExample Success-Response:
 * {
  "msg": "成功",
  "code": 0,
  "status": null,
  "data": {
    "pageNum": 1,
    "pageSize": 10,
    "size": 10,
    "orderBy": null,
    "startRow": 0,
    "endRow": 9,
    "total": 10,
    "pages": 1,
    "list": [
      {
        "geneId": "Glyma.18G141100",
        "geneOldId": null,
        "geneName": "ATHST,HST,PDS2",
        "geneType": "Protein_coding",
        "locus": "Chr18:21768218bp-21777484bp:+",
        "length": "9266bp",
        "species": "Glycine max",
        "functions": null,
        "description": "homogentisate prenyltransferase",
        "familyId": null,
        "id": 48833,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Flower number 1-8",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed length 3-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": null
      },
      {
        "geneId": "Glyma.18G141200",
        "geneOldId": null,
        "geneName": "KNAT6,KNAT6L,KNAT6S",
        "geneType": "Protein_coding",
        "locus": "Chr18:21901016bp-21913843bp:+",
        "length": "12827bp",
        "species": "Glycine max",
        "functions": null,
        "description": "KNOTTED1-like homeobox gene 6",
        "familyId": null,
        "id": 48834,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": null
      },
      {
        "geneId": "Glyma.18G141300",
        "geneOldId": null,
        "geneName": "",
        "geneType": "Protein_coding",
        "locus": "Chr18:21903989bp-21904174bp:-",
        "length": "185bp",
        "species": "Glycine max",
        "functions": null,
        "description": "",
        "familyId": null,
        "id": 48835,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Flower number 1-8",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed length 3-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": null
      },
      {
        "geneId": "Glyma.18G141400",
        "geneOldId": null,
        "geneName": "",
        "geneType": "Protein_coding",
        "locus": "Chr18:21906249bp-21909563bp:-",
        "length": "3314bp",
        "species": "Glycine max",
        "functions": null,
        "description": "",
        "familyId": null,
        "id": 48836,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "uncharacterized LOC102663265"
      },
      {
        "geneId": "Glyma.18G141500",
        "geneOldId": null,
        "geneName": "CRK2",
        "geneType": "Protein_coding",
        "locus": "Chr18:21917438bp-21922118bp:-",
        "length": "4680bp",
        "species": "Glycine max",
        "functions": null,
        "description": "cysteine-rich RLK (RECEPTOR-like protein kinase) 2",
        "familyId": null,
        "id": 48837,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "cysteine-rich receptor-like protein kinase 2"
      },
      {
        "geneId": "Glyma.18G141600",
        "geneOldId": null,
        "geneName": "",
        "geneType": "Protein_coding",
        "locus": "Chr18:22021723bp-22022700bp:-",
        "length": "977bp",
        "species": "Glycine max",
        "functions": null,
        "description": "",
        "familyId": null,
        "id": 48838,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "uncharacterized LOC102664078"
      },
      {
        "geneId": "Glyma.18G141700",
        "geneOldId": null,
        "geneName": "CRK3",
        "geneType": "Protein_coding",
        "locus": "Chr18:22029730bp-22033939bp:-",
        "length": "4209bp",
        "species": "Glycine max",
        "functions": null,
        "description": "cysteine-rich RLK (RECEPTOR-like protein kinase) 3",
        "familyId": null,
        "id": 48839,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "cysteine-rich receptor-like protein kinase 3"
      },
      {
        "geneId": "Glyma.18G141800",
        "geneOldId": null,
        "geneName": "",
        "geneType": "Protein_coding",
        "locus": "Chr18:22036546bp-22036837bp:+",
        "length": "291bp",
        "species": "Glycine max",
        "functions": null,
        "description": "",
        "familyId": null,
        "id": 48840,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": null
      },
      {
        "geneId": "Glyma.18G141900",
        "geneOldId": null,
        "geneName": "ATNRT2.5,NRT2.5",
        "geneType": "Protein_coding",
        "locus": "Chr18:22053882bp-22057005bp:-",
        "length": "3123bp",
        "species": "Glycine max",
        "functions": null,
        "description": "nitrate transporter2.5",
        "familyId": null,
        "id": 48841,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "high affinity nitrate transporter 2.5-like"
      },
      {
        "geneId": "Glyma.18G142000",
        "geneOldId": null,
        "geneName": "",
        "geneType": "Protein_coding",
        "locus": "Chr18:22213209bp-22215641bp:-",
        "length": "2432bp",
        "species": "Glycine max",
        "functions": null,
        "description": "hAT transposon superfamily",
        "familyId": null,
        "id": 48842,
        "existsSNP": true,
        "associateQTLs": [
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "cqSeed weight-001",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Foxglove aphid, primary damage, no choice 1-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Internode length 1-9",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet area 2-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Leaflet ash 1-11",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Phytoph 14-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 17-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 18-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 19-4",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "SCN 29-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed glycinin to beta-conglycinin ratio 1-5",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed hardness 2-10",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-33",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed oil 42-34",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed protein 36-25",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight 27-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed weight per plant 6-6",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Seed width 3-3",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "shoot weight, dry 1-2",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-1",
            "version": null,
            "associatedGenes": null
          },
          {
            "id": null,
            "isNewRecord": false,
            "remarks": null,
            "createTime": null,
            "updateDate": null,
            "qtlName": "Somatic emb per explant 2-3",
            "version": null,
            "associatedGenes": null
          }
        ],
        "rootTissues": [],
        "function": "uncharacterized LOC102669844"
      }
    ],
    "prePage": 0,
    "nextPage": 0,
    "isFirstPage": true,
    "isLastPage": true,
    "hasPreviousPage": false,
    "hasNextPage": false,
    "navigatePages": 8,
    "navigatepageNums": [
      1
    ],
    "navigateFirstPage": 1,
    "navigateLastPage": 1,
    "lastPage": 1,
    "firstPage": 1
  }
}
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