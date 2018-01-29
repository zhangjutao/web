function fetchTraits() {
}

/**
 * @api {post} /sort/fetch-trait  获取初始性状
 * @apiName fetchTrait
 * @apiGroup Sort
 * @apiDescription 排序初始化弹框时，获取性状下拉列表元素
 * @apiSampleRequest http://localhost:8080/iqgs/sort/fetch-trait
 * @apiSuccessExample Success-Response:
 * [{
 * "id": "14444",
 * "qtlDesc": "无机物耐性",
 * "traitCategoryId": "23"
 * },
 * {
 * "id": "14445",
 * "qtlDesc": "真菌抗性",
 * "traitCategoryId": "16"
 * }
 * ]
 */

function getSortList() {
}

/**
 * @api {post} /sort/fetch-sort-list  获取排序结果列表
 * @apiName fetchSortedList
 * @apiGroup Sort
 * @apiDescription 排序结果列表,注意这里传入的参数。geneExpressionConditionEntities中是一个对象数组，
 * 用户选择的每一个二级组织需要给它一个不为null的值。
 * @apiParam {String} traitCategoryId 下拉框选中性状的ID值
 * @apiParam {String[]} underSortedGeneId 待排序的基因ID数组
 * @apiParam {Object[]} GeneExpressionConditionEntity 用户选中组织的组织集合，begin、end不传
 * @apiParam {Number} pageNo 当前页
 * @apiParam {Number} pageSize 每页大小
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
 * @apiSampleRequest http://localhost:8080/iqgs/sort/fetch-sort-list
 * @apiSuccessExample Success-Response:
 * [
 * {
 * "geneId":"Glyma.01G000600",
 * "geneName":"FRS5",
 * "chromosome":"Chr01",
 * "locus":"27355bp-28320bp:-",
 * "description":"FAR1-related sequence 9"
 * },
 * {
 * "geneId":"Glyma.01G000400",
 * "geneName":"FRS9",
 * "chromosome":"Chr01",
 * "locus":"116094bp-127845bp:+",
 * "description":"FAR1-related sequence 5"
 * }
 * ]
 */