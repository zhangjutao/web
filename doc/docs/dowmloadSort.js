function copyOrderedGeneId() {
}

/**
 * @api {POST} /sort/download-sort 下载排序表格
 * @apiName downloadSortResult
 * @apiGroup Sort
 * @apiDescription 下载排序后表格（全部）内容
 * @apiSampleRequest http://localhost:8080/iqgs/sort/download-sort
 * @apiDescription 排序结果列表,注意这里传入的参数。geneExpressionConditionEntities中是一个对象数组，
 * 用户选择的每一个二级组织需要给它一个不为null的值。
 * @apiParam {String} traitCategoryId 下拉框选中性状的ID值
 * @apiParam {String[]} underSortedGeneId 待排序的基因ID数组
 * @apiParam {Object[]} GeneExpressionConditionEntity 用户选中组织的组织集合，begin、end不传
 * @apiParamExample {json} Request-Example:
 *{
 *	"geneIdList": ["Glyma.04G197300","Glyma.01G182600","Glyma.02G036200","Glyma.13G319500"],
 *	"tissue": {
 *		"pod": 20,
 *		"cotyledon": 11.5
 *	},
 *	"traitCategoryId": 19
 *}
 *
 * @apiSuccessExample Success-Response:
 * {code:0,msg:"成功"}
 */