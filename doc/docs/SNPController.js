
function fetchAllChromosome() {
}
/**
 * @api {get} /dna/fetch-all-chromosome 获取所有染色体
 * @apiName fetchAllChromosome
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA页面加载时获取所有染色体以及该染色体的最大长度
 * @apiSampleRequest http://localhost:8080/dna/dna/fetch-all-chromosome
 * @apiSuccessExample Success-Response:
 * [
 {
     "id": 1,
     "chromosome": "chr316",
     "length": 164327
 },
 {
     "id": 2,
     "chromosome": "chr317",
     "length": 51201
 }
 ]
 */

function queryForSNPTableByRegion() {
}
/**
 * @api {POST} /dna/queryForTable DNA数据库确认时下方表格数据(Search By Region)
 * @apiName queryForSNPTableByRegion
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA页面侧边栏点击确认时右侧下方表格数据
 * @apiSampleRequest http://localhost:8080/dna/dna/queryForTable
 * @apiParamExample {json} Request-Example:
 * {
  "type": "INDEL",
  "ctype": "all",
  "chromosome": "chr316",
  "start": 0,
  "end": 1000000,
  "pageNo": 1,
  "pageSize": 10,
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "data": [
        {
            "id": "PT1i316010534",
            "chr": "chr316",
            "pos": 10534,
            "ref": "GTA",
            "alt": "G",
            "qual": 194.75,
            "maf": 0.015625,
            "lenght": null,
            "type": "intergenic",
            "gene": "PT1_10000698,PT1_10000699",
            "genecontent": "PT1_10000698(dist=5466),PT1_10000699(dist=3788)",
            "effect": "---",
            "consequencetype": "intergenic",
            "samples": null,
            "majorallen": "GTA",
            "minorallen": "G",
            "major": 0.984375,
            "minor": 0.015625,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.984375,
                    "minor": 0.015625
                }
            ],
            "geneType": {
                "major": 98.44,
                "type": "INDEL"
            },
            "index": 0,
            "consequencetypeColor": 0
        }
    ],
    "total": 397,
    "offset": 0,
    "pageNo": 0
}
 */

function queryForSNPTableByGene() {
}
/**
 * @api {POST} /dna/queryForTable DNA数据库确认时下方表格数据(Search By Gene)
 * @apiName queryForSNPTableByGene
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA页面侧边栏点击确认时右侧下方表格数据,当用户选择的根据基因搜索，此时只需要传入基因与用户输入的上下游
 * 区间，如果用户不输入，默认上游减2000，下游加2000；如果用户输入该值，则以用户输入值为准
 * @apiSampleRequest http://localhost:8080/dna/dna/queryForTable
 * @apiParamExample {json} Request-Example:
 * {
  "type": "SNP",
  "ctype": "all",
  "gene": "PT1_10000698",
  "start": 1000,
  "end": 1000,
  "pageNo": 1,
  "pageSize": 10,
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "data": [
        {
            "id": "PT1s31605378",
            "chr": "chr316",
            "pos": 5378,
            "ref": "C",
            "alt": "T",
            "qual": 68.35,
            "maf": 0.015625,
            "lenght": null,
            "type": "downstream",
            "gene": "PT1_10000698",
            "genecontent": "PT1_10000698(dist=310)",
            "effect": "---",
            "consequencetype": "downstream",
            "samples": null,
            "majorallen": "C",
            "minorallen": "T",
            "major": 0.984375,
            "minor": 0.015625,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.984375,
                    "minor": 0.015625
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.984375,
                "totalRefAndAltPercent": 0,
                "major": 98.44,
                "totalAltAndAltPercent": 0.015625,
                "type": "SNP"
            },
            "index": 8,
            "consequencetypeColor": 0
        }
    ],
    "total": 3,
    "offset": 0,
    "pageNo": 0
}
 */

function fetchAllPointByGene() {
}
/**
 * @api {POST} /dna/fetch-point DNA数据库确认时下方图形数据(Search By Gene)
 * @apiName fetchAllPointByGene
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA页面侧边栏点击确认时右侧下方表格数据,当用户选择的根据基因搜索，此时只需要传入基因与用户输入的上下游
 * 区间，如果用户不输入，默认上游减2000，下游加2000；获取该基因区间上的所有SNP位点，返回给前端用户画图
 * @apiSampleRequest http://localhost:8080/dna/dna/fetch-point
 * @apiParamExample {json} Request-Example:
 * {
  "type": "SNP",
  "ctype": "all",
  "gene": "PT1_10000698",
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "snpList": [
        {
            "pos": 1280,
            "consequenceType": "intergenic",
            "consequenceTypeColor": 0,
            "index": 0
        },
        {
            "pos": 1284,
            "consequenceType": "intergenic",
            "consequenceTypeColor": 0,
            "index": 1
        }
    ],
    "structureList": [
        {
            "chromosome": "chr316",
            "geneId": "PT1_10000698",
            "transcriptId": "PT1_10000698",
            "feature": "CDS",
            "start": 4466,
            "end": 5068,
            "length": 603,
            "strand": "+",
            "maxLength": 0,
            "offset": 0
        },
        {
            "chromosome": "chr316",
            "geneId": "PT1_10000698",
            "transcriptId": "PT1_10000698",
            "feature": "CDS",
            "start": 4245,
            "end": 4419,
            "length": 175,
            "strand": "+",
            "maxLength": 0,
            "offset": 0
        }
    ],
    "geneInsideRegion": null,
    "geneId": "PT1_10000698",
    "upstream": 1175,
    "downstream": 7068
}
 */

function fetAllPointByRegion() {
}
/**
 * @api {POST} /dna/fetch-point DNA数据库确认时下方图形数据(Search By Region)
 * @apiName fetchAllPointByGene
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA页面侧边栏点击确认时右侧下方表格数据,当用户选择的根据染色体搜索；获取该染色体指定
 * 区间上的所有SNP位点，返回给前端用户画图（注意:这里geneInsideRegion是该区域内所包含的所有基因个数，后台会默认选择第一个基因
 * 并返回该基因的所有基因结构，这里的structureList就是第一个基因的基因结构，不是所有！！！）
 * @apiSampleRequest http://localhost:8080/dna/dna/fetch-point
 * @apiParamExample {json} Request-Example:
 * {
  "type": "SNP",
  "ctype": "all",
  "chromosome": "chr323",
  "start": 1000,
  "end": 200000,
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "snpList": [
        {
            "pos": 10937,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 869
        },
        {
            "pos": 10943,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 870
        },
        {
            "pos": 10997,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 871
        },
        {
            "pos": 10998,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 872
        },
        {
            "pos": 11005,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 873
        },
        {
            "pos": 11008,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 874
        },
        {
            "pos": 11012,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 875
        },
        {
            "pos": 11016,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 876
        },
        {
            "pos": 11018,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 877
        },
        {
            "pos": 11022,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 878
        },
        {
            "pos": 11058,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 879
        },
        {
            "pos": 11064,
            "consequenceType": "exonic_stopgain",
            "consequenceTypeColor": 0,
            "index": 880
        },
        {
            "pos": 11073,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 881
        },
        {
            "pos": 11112,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 882
        },
        {
            "pos": 11121,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 883
        },
        {
            "pos": 11125,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 884
        },
        {
            "pos": 11128,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 885
        },
        {
            "pos": 11130,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 886
        },
        {
            "pos": 11131,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 887
        },
        {
            "pos": 11144,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 888
        },
        {
            "pos": 11146,
            "consequenceType": "exonic_stopgain",
            "consequenceTypeColor": 0,
            "index": 889
        },
        {
            "pos": 11153,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 890
        },
        {
            "pos": 11156,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 891
        },
        {
            "pos": 11162,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 892
        },
        {
            "pos": 11170,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 893
        },
        {
            "pos": 11182,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 894
        },
        {
            "pos": 11201,
            "consequenceType": "exonic_synonymous SNV",
            "consequenceTypeColor": 0,
            "index": 895
        },
        {
            "pos": 11208,
            "consequenceType": "exonic_nonsynonymous SNV",
            "consequenceTypeColor": 1,
            "index": 896
        }
    ],
    "structureList": [
        {
            "chromosome": "chr323",
            "geneId": "PT1_10004043",
            "transcriptId": "PT1_10004043",
            "feature": "CDS",
            "start": 14647,
            "end": 14906,
            "length": 260,
            "strand": "-",
            "maxLength": 0,
            "offset": 0
        },
        {
            "chromosome": "chr323",
            "geneId": "PT1_10004043",
            "transcriptId": "PT1_10004043",
            "feature": "CDS",
            "start": 14550,
            "end": 14594,
            "length": 45,
            "strand": "-",
            "maxLength": 0,
            "offset": 0
        },
        {
            "chromosome": "chr323",
            "geneId": "PT1_10004043",
            "transcriptId": "PT1_10004043",
            "feature": "CDS",
            "start": 13724,
            "end": 14493,
            "length": 770,
            "strand": "-",
            "maxLength": 0,
            "offset": 0
        },
        {
            "chromosome": "chr323",
            "geneId": "PT1_10004043",
            "transcriptId": "PT1_10004043",
            "feature": "CDS",
            "start": 13463,
            "end": 13665,
            "length": 203,
            "strand": "-",
            "maxLength": 0,
            "offset": 0
        },
        {
            "chromosome": "chr323",
            "geneId": "PT1_10004043",
            "transcriptId": "PT1_10004043",
            "feature": "CDS",
            "start": 12936,
            "end": 13406,
            "length": 471,
            "strand": "-",
            "maxLength": 0,
            "offset": 0
        }
    ],
    "geneInsideRegion": [
        "PT1_10004043",
        "PT1_10004065",
        "PT1_10004066",
        "PT1_10004044",
        "PT1_10004067",
        "PT1_10004045",
        "PT1_10004068",
        "PT1_10004046",
        "PT1_10004061",
        "PT1_10004062",
        "PT1_10004040",
        "PT1_10004063",
        "PT1_10004041",
        "PT1_10004064",
        "PT1_10004042",
        "PT1_10004060",
        "PT1_10004047",
        "PT1_10004069",
        "PT1_10004048",
        "PT1_10004049",
        "PT1_10004054",
        "PT1_10004055",
        "PT1_10004056",
        "PT1_10004057",
        "PT1_10004050",
        "PT1_10004072",
        "PT1_10004073",
        "PT1_10004051",
        "PT1_10004052",
        "PT1_10004053",
        "PT1_10004070",
        "PT1_10004071",
        "PT1_10004058",
        "PT1_10004059",
        "PT1_10004039"
    ],
    "geneId": "PT1_10004043",
    "upstream": 10936,
    "downstream": 16906
}
 */

function jumpPageByRegion() {
}
/**
 * @api {POST} /dna/jump-page DNA数据库中图标联动接口(Search By Region)
 * @apiName jumpPageByRegion
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA当右侧出现图表时，用户点击图中某一个SNP位点，后台根据前台传入的index值以及查询条件，计算
 * 该点在下方表中的页码和当前页的偏移量，返回前端pageNo、offset值，前端根据该值去渲染页面
 * @apiSampleRequest http://localhost:8080/dna/dna/jump-page
 * @apiParamExample {json} Request-Example:
 * {
  "type": "SNP",
  "ctype": "all",
  "chromosome": "chr323",
  "index": 957,
  "start": 1000,
  "end": 200000,
  "pageSize": 20,
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "data": [
        {
            "id": "PT1s323011870",
            "chr": "chr323",
            "pos": 11870,
            "ref": "T",
            "alt": "C",
            "qual": 379.28,
            "maf": 0.046875,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=200);PT1_10004042(dist=200)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "T",
            "minorallen": "C",
            "major": 0.9765625,
            "minor": 0.0234375,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.9765625,
                    "minor": 0.0234375
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.953125,
                "totalRefAndAltPercent": 0.046875,
                "major": 97.66,
                "totalAltAndAltPercent": 0,
                "type": "SNP"
            },
            "index": 950,
            "consequencetypeColor": 0
        },
        {
            "id": "PT1s323011877",
            "chr": "chr323",
            "pos": 11877,
            "ref": "T",
            "alt": "C",
            "qual": 6373.68,
            "maf": 0.1875,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=207);PT1_10004042(dist=207)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "T",
            "minorallen": "C",
            "major": 0.90625,
            "minor": 0.09375,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.90625,
                    "minor": 0.09375
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.8125,
                "totalRefAndAltPercent": 0.1875,
                "major": 90.62,
                "totalAltAndAltPercent": 0,
                "type": "SNP"
            },
            "index": 951,
            "consequencetypeColor": 0
        },
        {
            "id": "PT1s323011900",
            "chr": "chr323",
            "pos": 11900,
            "ref": "A",
            "alt": "C,T",
            "qual": 14675.55,
            "maf": 0.015625,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=230);PT1_10004042(dist=230)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "A",
            "minorallen": "C,T",
            "major": 0.8113207547169812,
            "minor": 0.18867924528301888,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.671875,
                    "minor": 0.328125
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.53125,
                "totalRefAndAltPercent": 0.28125,
                "major": 81.13,
                "totalAltAndAltPercent": 0.1875,
                "type": "SNP"
            },
            "index": 952,
            "consequencetypeColor": 0
        }
    ],
    "total": 15335,
    "offset": 8,
    "pageNo": 46
}
 */

function jumpPageByGene() {
}
/**
 * @api {POST} /dna/jump-page DNA数据库中图标联动接口(Search By Gene)
 * @apiName jumpPageByGene
 * @apiGroup DNA
 * @apiDescription 蘑菇数据库DNA当右侧出现图表时，用户点击图中某一个SNP位点，后台根据前台传入的index值以及查询条件
 * (这里查询条件是用户点击侧边栏确认时传入的同样参数，只是这里还需要一个额外的参数，index值，作为后台计算偏移量的标准)，
 * 计算该点在下方表中的页码和当前页的偏移量，返回前端pageNo、offset值，前端根据该值去渲染页面
 * @apiSampleRequest http://localhost:8080/dna/dna/jump-page
 * @apiParamExample {json} Request-Example:
 * {
  "type": "SNP",
  "ctype": "all",
  "gene": "PT1_10000698",
  "index": 957,
  "start": 1000,
  "end": 1000,
  "pageSize": 10,
  "group": [
    {
      "name": "sample1",
      "id": 1,
      "condition": {
        "strainName": "Zhongnongduanqi1hao",
        "locality": "Beijing,China",
        "preservationLocation": "Jilin agricultural university"
      }
    },
    {
      "name": "sample2",
      "id": 2,
      "condition": {
        "myceliaColor": "white"
      }
    }
  ]
}
 * @apiSuccessExample Success-Response:
 * {
    "data": [
        {
            "id": "PT1s323011870",
            "chr": "chr323",
            "pos": 11870,
            "ref": "T",
            "alt": "C",
            "qual": 379.28,
            "maf": 0.046875,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=200);PT1_10004042(dist=200)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "T",
            "minorallen": "C",
            "major": 0.9765625,
            "minor": 0.0234375,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.9765625,
                    "minor": 0.0234375
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.953125,
                "totalRefAndAltPercent": 0.046875,
                "major": 97.66,
                "totalAltAndAltPercent": 0,
                "type": "SNP"
            },
            "index": 950,
            "consequencetypeColor": 0
        },
        {
            "id": "PT1s323011877",
            "chr": "chr323",
            "pos": 11877,
            "ref": "T",
            "alt": "C",
            "qual": 6373.68,
            "maf": 0.1875,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=207);PT1_10004042(dist=207)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "T",
            "minorallen": "C",
            "major": 0.90625,
            "minor": 0.09375,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.90625,
                    "minor": 0.09375
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.8125,
                "totalRefAndAltPercent": 0.1875,
                "major": 90.62,
                "totalAltAndAltPercent": 0,
                "type": "SNP"
            },
            "index": 951,
            "consequencetypeColor": 0
        },
        {
            "id": "PT1s323011900",
            "chr": "chr323",
            "pos": 11900,
            "ref": "A",
            "alt": "C,T",
            "qual": 14675.55,
            "maf": 0.015625,
            "lenght": null,
            "type": "upstream;downstream",
            "gene": "PT1_10004040,PT1_10004042",
            "genecontent": "PT1_10004040(dist=230);PT1_10004042(dist=230)",
            "effect": "---",
            "consequencetype": "upstream;downstream",
            "samples": null,
            "majorallen": "A",
            "minorallen": "C,T",
            "major": 0.8113207547169812,
            "minor": 0.18867924528301888,
            "freq": [
                {
                    "name": "sample1",
                    "size": 1,
                    "major": 1,
                    "minor": 0
                },
                {
                    "name": "sample2",
                    "size": 64,
                    "major": 0.671875,
                    "minor": 0.328125
                }
            ],
            "geneType": {
                "RefAndRefPercent": 0.53125,
                "totalRefAndAltPercent": 0.28125,
                "major": 81.13,
                "totalAltAndAltPercent": 0.1875,
                "type": "SNP"
            },
            "index": 952,
            "consequencetypeColor": 0
        }
    ],
    "total": 15335,
    "offset": 8,
    "pageNo": 46
}
 */