/**
 * Created by admin on 2017/10/11.
 */
$(function(){

    $("#btn_name").on('click', function(){
        var key = $("#key_name").val();
        if (key && !/^\s+$/.test(key)) {
            window.location = DOMAIN + "/search/list?keyword=" + encodeURI(key) + "&searchType=1";
        }
    });

    $("#btn_func").on('click', function(){
        var key = $("#key_func").val();
        if (key && !/^\s+$/.test(key)) {
            window.location = DOMAIN + "/search/list?keyword=" + encodeURI(key) + "&searchType=2";
        }
    });

    $("#btn_range").on('click', function(){
        var serial = $(".js-region").val();
        var rg_begin = $("#rg_begin").val();
        var rg_end = $("#rg_end").val();
        if (rg_begin && !/^\s+$/.test(rg_begin) && rg_end && !/^\s+$/.test(rg_end)) {
            window.location = DOMAIN + "/search/list?chr=" + encodeURI(serial) + "&begin="  + encodeURI(rg_begin) + "&end="  + encodeURI(rg_end) + "&searchType=3";
        }
    })

    $("#myTabs li").each(function(i){
        $(this).click(function(){
            $(this).addClass("active").siblings().removeClass("active");
            var divs = $("#myTabContent>div");
            for(var k=0;k<divs.length;k++){
                if(k == i){
                    $(divs[k]).addClass("tab-pane-ac")
                }else {
                    $(divs[k]).removeClass("tab-pane-ac");
                }
            }
            // $("#myTabContent").find("div").eq(i).show().siblings().hide();
        })
    })

    /*2017 - 12 新增业务需求 add by jarry*/
    // ajax 请求的代码封装
    function SendAjaxRequest(method, url,data) {
        if (window.Promise) {//检查浏览器是否支持Promise
            var promise = new Promise(function (resolve, reject) {
                $.ajax({
                    method: method,
                    url: url,
                    data:data,
                    contentType: "application/json;charset=UTF-8;",
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

    // qtl 搜索 -- 》获取数据 -- >search
    // $("#QtlBtnName").click(function (){
    //     //Mock 拦截请求
    //     var number = Mock.mock({
    //         "total|1-100": 100
    //     });
    //     var arr = [];
    //     for (var i=0;i<number.total;i++){
    //         var id = Mock.mock({ "number|1-100000": 100 });
    //         var qtlName = Mock.Random.string("lower",7,7) +Mock.Random.string("lower",7,7) + Mock.Random.string("lower",7,7) ;
    //         arr.push({
    //             id:id.number,
    //             qtlName:qtlName
    //         });
    //     }
    //     var obj = {
    //         status:200,
    //         data:arr
    //     }
    //     Mock.mock(/(query-by-qtl-name){1}\w*/,obj);
    //     // debugger;
    //     var qtlSearchVal = $("#qtlName").val();
    //     var data ={
    //         qtlName:qtlSearchVal
    //     };
    //     // console.log(data)
    //     var promise = SendAjaxRequest("GET","query-by-qtl-name",data);
    //     promise.then(
    //         function (result){
    //             console.log(result)
    //             if(result.status!=200){
    //                 if($("#qtlErrorTip").is(":hidden")){
    //                     $("#qtlErrorTip").show();
    //                     $("#qtlName").addClass("inputErrorColor")
    //                 };
    //                 if(!$("#qtlAdd .sureBtn").is(":hidden")){
    //                     $("#qtlAdd .sureBtn").hide();
    //                 }
    //             }else {
    //                 if( $("#qtlName").hasClass("inputErrorColor")){
    //                     $("#qtlName").removeClass("inputErrorColor")
    //                 };
    //                 if($("#qtlAdd .fuzzySearch").is(":hidden")){
    //                     $("#qtlAdd .fuzzySearch").show();
    //                 };
    //                 if(!$("#qtlErrorTip").is(":hidden")){
    //                     $("#qtlErrorTip").hide();
    //                 };
    //                 if($("#qtlAdd .sureBtn").is(":hidden")){
    //                     $("#qtlAdd .sureBtn").show();
    //                 }
    //                 // 动态生成qtlname列表
    //                 var list = result.data;
    //                 var $ul = $("#qtlAdd .fuzzySearch ul");
    //                 $ul.empty();
    //                 for (var i = 0; i < list.length; i++) {
    //                     var id = list[i].id;
    //                     var name = list[i].qtlName;
    //                     var li = "<li>" + "<label for='" + name + "'><span id ='" + id + "' data-value='" + name + "'></span>" + name + "</label></li>";
    //                     $ul.append(li);
    //                 }
    //             }
    //         },
    //         function (error){
    //             console.log(error);
    //         }
    //     )
    // });
    // 每个qtlname列表的点击选中事件
    var globleObject = {};
    globleObject.selectedQtl = [];
    $("#qtlAdd .fuzzySearch").on("click","li",function (){
        // var list =  $("#qtlAdd .fuzzySearch li");
        if($(this).hasClass("checked")) {
            $(this).removeClass("checked");
            for(var i=0;i<globleObject.selectedQtl.length;i++){
                if(globleObject.selectedQtl[i] == $(this).find("span").attr("id")){
                    globleObject.selectedQtl.splice(i,1);
                }
            }
        }
        else {
            $(this).addClass("checked")
            globleObject.selectedQtl.push($(this).find("span").attr("id"));
        }
    });
    // 根据选择的qtl 搜索 -- > sureBtn
    $("#qtlAdd .sureBtn").click(function (){
        var num = globleObject.selectedQtl.length;
        if(num>5){
            alert("最多只能选择 5 个");
            return;
        }else {
           var qtlVal =globleObject.selectedQtl;
            // val = [1001, 1005];
            var data ={
                chosenQtl:qtlVal,
                pageNo:1,
                pageSize:20
            };
            // 发送请求
            var promise = SendAjaxRequest("GET","/iqgs/advance-search/confirm", data);
            promise.then(
                function (result){
                    // console.warn(result);
                    var key = $("#qtlName").val();
                    var chosenQtl=globleObject.selectedQtl;
                    if (key && !/^\s+$/.test(key)) {
                        window.location = DOMAIN + "/search/list?keyword=" + encodeURI(key) + "&chosenQtl=" + encodeURI(chosenQtl) + "&qtlVal&searchType=4";
                    }
                },
                function (error){
                    console.log(error);
                }
            )
        }
    })

    $("#QtlBtnNames").click(function (){
    var qtlSearchVal = $("#qtlName").val();
    var data ={
        qtlName:qtlSearchVal
    };
        $.ajax({
            url: '/iqgs/advance-search/query-by-qtl-name',
            data: data,
            type: "get",
            dataType: "json",
            success: function(res) {
                if(res.length==0){
                alert('暂无数据')
                }else{
                    // 动态生成qtlname列表
                    $('#qtlAdd .fuzzySearch').show();
                    var $ul = $("#qtlAdd .fuzzySearch ul");
                    $ul.empty();
                    for (var i = 0; i < res.length; i++) {
                        var id = res[i].associatedGenesId;
                        var name = res[i].qtlName;
                        var li = "<li>" + "<label for='" + name + "'><span id ='" + id + "' data-value='" + name + "'></span>" + name + "</label></li>";
                        $ul.append(li);
                    }
                }

            }
        });
    });


})


