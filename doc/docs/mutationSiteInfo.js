/**
 * @api {POST} /dna/IdDetailExport 变异位点详情页，表格信息
 * @apiName IdDetailExport
 * @apiGroup dataExport
 * @apiParam {DataExportCondition} dataExportCondition DataExportCondition对象,titles为表格设置内的勾选项，用户点确定之后有多少个勾就将相应字段拼成以逗号隔开的字符串；condition为SampleInfoDto对象，包含snpId、changeParam（major或minor）及所有表头（为key）和表头筛选值（key对应的value）；judgeAllele为majorALlele/minorAllele
 * @apiParamExample {json} Request-Example（judgeAllele必定有value，condition内的snpId、changeParam必定传value；isPage必须不穿值、不传值、不传值）:
 * {
  "titles": "runNo,genotype,scientificName,sampleId,strainName,locality,preservationLocation,type,environment,materials,treat,time,taxonomy,myceliaPhenotype,myceliaDiameter,myceliaColor,sporesColor,sporesShape,clampConnection,pileusPhenotype,pileusColor,stipePhenotype,stipeColor,fruitbodyColor,fruitbodyType,illumination,collarium,volva,velum,sclerotium,strainMedium,mainSubstrate,afterRipeningStage,primordialStimulationFruitbody,reproductiveMode,lifestyle,preservation,domestication,nuclearPhase,matingType",
  "condition": {
  	"snpId":"PT1s31601280",
  	"changeParam":"C",
    "runNo": "",
    "scientificName": "",
    "sampleId": "",
    "strainName": "",
    "locality": "",
    "preservationLocation": "",
    "type": "",
    "environment": "",
    "materials": "",
    "treat": "",
    "definitionTime": "",
    "taxonomy": "",
    "myceliaPhenotype": "",
    "myceliaDiameter": "",
    "myceliaColor": "",
    "sporesColor": "",
    "sporesShape": "",
    "clampConnection": "",
    "pileusPhenotype": "",
    "pileusColor": "",
    "stipePhenotype": "",
    "stipeColor": "",
    "fruitbodyColor": "",
    "fruitbodyType": "",
    "illumination": "",
    "collarium": "",
    "volva": "",
    "velum": "",
    "sclerotium": "",
    "strainMedium": "",
    "mainSubstrate": "",
    "afterRipeningStage": "",
    "primordialStimulationFruitbody": "",
    "reproductiveMode": "",
    "lifestyle": "",
    "preservation": "",
    "domestication": "",
    "nuclearPhase": "",
    "matingType": "",
    "isPage":
  },
  "judgeAllele": "Major"
}
 * * @apiSuccessExample {document} success return:
 * 如果接口没问题，会弹出下载框
 */

/**
 * @api {GET} /dna/changeByProportion 变异位点详情页，样本数据下载
 * @apiName changeByProportion
 * @apiGroup sampleInfo
 * @apiParam {String} judgeAllele value不为空
 * @apiParam {String} snpId value不为空
 * @apiParam {String} changeParam value不为空
 * @apiParam {String} runNo
 * @apiParam {String} scientificName
 * @apiParam {String} sampleId
 * @apiParam {String} strainName
 * @apiParam {String} locality
 * @apiParam {String} preservationLocation
 * @apiParam {String} type
 * @apiParam {String} environment
 * @apiParam {String} materials
 * @apiParam {String} treat
 * @apiParam {String} definitionTime
 * @apiParam {String} taxonomy
 * @apiParam {String} myceliaPhenotype
 * @apiParam {String} myceliaDiameter
 * @apiParam {String} myceliaColor
 * @apiParam {String} sporesColor
 * @apiParam {String} sporesShape
 * @apiParam {String} clampConnection
 * @apiParam {String} pileusPhenotype
 * @apiParam {String} pileusColor
 * @apiParam {String} stipePhenotype
 * @apiParam {String} stipeColor
 * @apiParam {String} fruitbodyColor
 * @apiParam {String} fruitbodyType
 * @apiParam {String} illumination
 * @apiParam {String} collarium
 * @apiParam {String} volva
 * @apiParam {String} velum
 * @apiParam {String} sclerotium
 * @apiParam {String} strainMedium
 * @apiParam {String} mainSubstrate
 * @apiParam {String} afterRipeningStage
 * @apiParam {String} primordialStimulationFruitbody
 * @apiParam {String} reproductiveMode
 * @apiParam {String} lifestyle
 * @apiParam {String} preservation
 * @apiParam {String} domestication
 * @apiParam {String} nuclearPhase
 * @apiParam {String} matingType
 * @apiParam {String} group
 * @apiParam {int} pageSize value不为空
 * @apiParam {int} pageNum value不为空
 * @apiParam {int} isPage value不为空
 * @apiParamExample {json} Request-Example:
 * judgeAllele:major
 snpId:PT1s31601280
 changeParam:C
 runNo:
 scientificName:
 sampleId:965
 strainName:
 locality:
 preservationLocation:
 type:
 environment:
 materials:
 treat:
 definitionTime:
 taxonomy:
 myceliaPhenotype:
 myceliaDiameter:
 myceliaColor:
 sporesColor:
 sporesShape:
 clampConnection:
 pileusPhenotype:
 pileusColor:
 stipePhenotype:
 stipeColor:
 fruitbodyColor:
 fruitbodyType:
 illumination:
 collarium:
 volva:
 velum:
 sclerotium:
 strainMedium:
 mainSubstrate:
 afterRipeningStage:
 primordialStimulationFruitbody:
 reproductiveMode:
 lifestyle:
 preservation:
 domestication:
 nuclearPhase:
 matingType:
 group:
 pageSize:10
 pageNum:1
 isPage:1
 *
 * @apiSuccessExample {json} success return:
 * {
    "msg": "成功",
    "code": 0,
    "status": null,
    "data": {
        "dnaRuns": {
            "pageNum": 1,
            "pageSize": 10,
            "size": 1,
            "orderBy": null,
            "startRow": 1,
            "endRow": 1,
            "total": 1,
            "pages": 1,
            "list": [
                {
                    "id": "11",
                    "runNo": "B25",
                    "runNos": null,
                    "scientificName": "Pleurotus tuoliensis",
                    "sampleId": "CCMJ965",
                    "strainName": "CCMJ965",
                    "preservationLocation": "Jilin agricultural university",
                    "locality": "Yumin,China",
                    "type": "wild ",
                    "environment": "",
                    "materials": "mycelium",
                    "treat": "",
                    "definitionTime": "2012",
                    "taxonomy": "Basidiomycetes,wood-rottingfungi,White rot fungus",
                    "myceliaPhenotype": "stolonmycelium",
                    "myceliaDiameter": "",
                    "myceliaColor": "white",
                    "keywords": null,
                    "sporesColor": "white",
                    "sporesShape": "",
                    "clampConnection": "Y",
                    "pileusPhenotype": "",
                    "pileusColor": "white",
                    "stipePhenotype": "solid ",
                    "stipeColor": "white",
                    "fruitbodyColor": "white",
                    "fruitbodyType": "cluster",
                    "illumination": "nature light",
                    "collarium": "NONE",
                    "volva": "NONE",
                    "velum": "NONE",
                    "sclerotium": "NONE",
                    "strainMedium": "PDA",
                    "mainSubstrate": "sawdust",
                    "afterRipeningStage": "Y",
                    "primordialStimulationFruitbody": "low temperature,few fruitbody",
                    "reproductiveMode": "sporogony",
                    "lifestyle": "parasitic ",
                    "preservation": "liquid nitrogen",
                    "domestication": "Difficulty",
                    "nuclearPhase": "diplophasenucleus",
                    "matingType": "tetrapolarity,heterothallism"
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
            "firstPage": 1,
            "lastPage": 1
        },
        "samples": {
            "B64": "CC",
            "B21": "CC",
            "B22": "CC",
            "B24": "CC",
            "B68": "CC",
            "B25": "CC",
            "B26": "CC",
            "B27": "CC",
            "B118": "CC",
            "B28": "CC",
            "B29": "CC",
            "B116": "CC",
            "B117": "CC",
            "B75": "CC",
            "B76": "CC",
            "B33": "CC",
            "B34": "CC",
            "B35": "CC",
            "B36": "CC",
            "B37": "CC",
            "B38": "CC",
            "B129": "CC",
            "B39": "CC",
            "B126": "CC",
            "B123": "CC",
            "B3": "CC",
            "B5": "CC",
            "B8": "CC",
            "B80": "CC",
            "B81": "CC",
            "B82": "CC",
            "B83": "CC",
            "B40": "CC",
            "B84": "CC",
            "B41": "CC",
            "B85": "CC",
            "B42": "CC",
            "B86": "CC",
            "B43": "CC",
            "B87": "CC",
            "B88": "CC",
            "B89": "CC",
            "B47": "CC",
            "B132": "CC",
            "B130": "CC",
            "B90": "CC",
            "B91": "CC",
            "B92": "CC",
            "B93": "CC",
            "B94": "CC",
            "B96": "CC",
            "B53": "CC",
            "B97": "CC",
            "B54": "CC",
            "B11": "CC",
            "B55": "CC",
            "B56": "CC",
            "B57": "CC",
            "B58": "CC",
            "B59": "CC",
            "B61": "CC",
            "B62": "CC",
            "B63": "CC"
        }
    }
}
 */