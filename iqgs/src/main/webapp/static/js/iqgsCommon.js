// $(function (){


// 定义全局变量
var globalObj = {}
globalObj.SleExpreDatas = [];  // 选中的大组织以及小组织
globalObj.SleSnpDatas = [];// 选中的snp 信息
globalObj.SleIndelDatas = [];// 选中的indel 信息
globalObj.qtlParams = [];  // 存放qtl 信息
// 存放高级搜索最后的参数集合
var dataParam;
// ajax 请求的代码封装
function SendAjaxRequest(method, url,data) {
    if (window.Promise) {//检查浏览器是否支持Promise
        var promise = new Promise(function (resolve, reject) {
            $.ajax({
                method: method,
                url: url,
                data:data,
                dataType: "json",
                contentType: "application/json;charset=UTF-8",
                success: function (result) {
                    resolve(result)
                },
                error: function (error) {
                    reject(error)
                }
            });
        });
        return promise;
    } else {
        alert("sorry,你的浏览器不支持Promise 对象")
    };
};

flag = 0;
// })
