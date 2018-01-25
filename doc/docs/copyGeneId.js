function copyOrderedGeneId() {
}

/**
 * @api {POST} /sort/copy-ordered-geneId 复制基因ID
 * @apiName copyOrderedGeneId
 * @apiGroup Sort
 * @apiDescription 复制经过排序的基因ID
 * @apiSampleRequest http://localhost:8080/iqgs/sort//copy-ordered-geneId
 * @apiDescription 排序结果列表,注意这里传入的参数。geneExpressionConditionEntities中是一个对象数组，
 * 用户选择的每一个二级组织需要给它一个不为null的值。
 * @apiParam {String} traitCategoryId 下拉框选中性状的ID值
 * @apiParam {String[]} underSortedGeneId 待排序的基因ID数组
 * @apiParam {Object[]} GeneExpressionConditionEntity 用户选中组织的组织集合，begin、end不传
 * @apiParam {Number} pageNo 1或者前端页码默认
 * @apiParam {Number} pageSize 基因总条数或者前端的当前页条数默认值
 * @apiParamExample {json} Request-Example:
 *{
 *	"geneIdList": ["Glyma.04G197300","Glyma.01G182600","Glyma.02G036200","Glyma.13G319500"],
 *	"tissue": {
 *		"pod": 20,
 *		"cotyledon": 11.5
 *	},
 *	"traitCategoryId": 19,
 *  "pageNo": 1,
 *  "pageSize": 10
 *}
 *
 * @apiSuccessExample Success-Response:
 * ["Glyma.01G182600","Glyma.04G197300","Glyma.13G319500","Glyma.02G036200"]
 */