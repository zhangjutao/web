function fetchTraits() {
}
/**
 * @api {post} /sort/fetch-trait  获取初始性状
 * @apiName fetchTrait
 * @apiGroup Sort
 * @apidescription 排序初始化弹框时，获取性状下拉列表元素
 * @apisamplerequest http://localhost:8080/iqgs/sort/fetch-trait
 * @apiSuccessExample Success-Response:
 * [{
 * "id": "14444",
 * "qtlDesc": "无机物耐性"
 * },
 * {
 * "id": "14445",
 * "qtlDesc": "真菌抗性"
 * }
 * ]
 */

function getSortList() {
}
/**
 * @api {post} /sort/fetch-sort-list  获取排序结果列表
 * @apiName fetchSortedList
 * @apiGroup Sort
 * @apidescription 排序结果列表
 * @apisamplerequest http://localhost:8080/iqgs/sort/fetch-sort-list
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