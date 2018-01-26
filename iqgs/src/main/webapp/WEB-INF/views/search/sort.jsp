<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>排序</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/index.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/combo_select.css">--%>
    <link rel="stylesheet" href="${ctxStatic}/css/sort.css">
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <link rel="stylesheet" href="${ctxStatic}/css/newAdd.css">
    <link rel="stylesheet" href="${ctxStatic}/js/laypage/skin/laypage.css">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>

<body style="min-width:auto; width: 100%;  background-color: #fff;">
<div class="sort_top">
    <div class="sortText_main"><span class="sortText_tit">已选></span>
        <div class="sortText_div">

            <div class="sortZzText">
                <span class="sortText"><span class="sortText_conter"></span><i class="sortGb">X</i>
                </span>
                <%--<span class="sortZzText_conter"></span><i class="sortZzGb">X</i>--%>
            </div>
        </div>
    </div>

    <div class="sort_selectConter">
        <form name="forms">
        <div class="sort_xz">
            <label class="sort_lab">设置性状：</label>
            <select name="sortSelect" class="sortSelect">
                <%--<option value="">请选择</option>--%>
                <%--<option value="一月">一月</option>--%>
                <%--<option value="二月">二月</option>--%>
                <%--<option value="三月">三月</option>--%>
                <%--<option value="四月">四月</option>--%>
            </select>
        </div>
        </form>
        <form name="form1" class="sort_zz">
         <div class="sort_zz_lab">
             <label class="sort_lab">设置组织：</label>
             <select name="geneName" id="geneName">
                 <option>请选择</option>
             </select>
             <button class="sortInfo_btn">排序</button>
         </div>
            <ul class="select_con" id="geneList">
            </ul>
        </form>
    </div>

    <div class="sortMain">
        <div class="sortResult">
            <span class="sortTitle">排序结果</span>
            <span class="sortSpan">
            <button><img src='${ctxStatic}/images/copys.png'>复制</button>
            <button><img src='${ctxStatic}/images/doms.png'>下载</button>
        </span>
        </div>

        <div class="tab-detail-tbody">
            <table class="popu-table" style="width: 100%;">
                <thead>
                <tr>
                    <td class="species">基因ID</td>
                    <td class="locality">基因名</td>
                    <td class="sampleName">染色体</td>
                    <td class="cultivar">基因位置</td>
                    <td class="weightPer100seeds">基因注释</td>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="checkbox-item-tab" id="popu-paginate">
                <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
            </div>
        </div>
    </div>

</div>
<script src="${ctxStatic}/js/iqgsCommon.js"></script>
<script src="${ctxStatic}/js/sort.js"></script>
<script src="${ctxStatic}/js/jquery.combo.select.js"></script>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<script>
    $(function () {
        window.DOMAIN = "${ctxroot}/iqgs";
        window.ctxROOT = "${ctxroot}";
        window.ctxStatic = "${ctxStatic}";
        $('.sort_xz select').comboSelect();
    });

    //接收消息数据
    window.addEventListener("message", function( event ) {
        // 把父窗口发送过来的数据显示在子窗口中
        var sortConditionData=event.data;

        var geneIdList=[
            "Glyma.08G009000",
            "Glyma.08G007100",
            "Glyma.08G008600",
            "Glyma.08G009100",
            "Glyma.08G007900",
            "Glyma.08G003700",
            "Glyma.08G000700",
            "Glyma.08G003200",
            "Glyma.08G008200",
            "Glyma.08G001300",
            "Glyma.08G002400",
            "Glyma.08G009400",
            "Glyma.08G003000",
            "Glyma.08G005400",
            "Glyma.08G007800",
            "Glyma.08G006100",
            "Glyma.08G000600",
            "Glyma.08G006200",
            "Glyma.08G004300",
            "Glyma.08G008500",
            "Glyma.08G007500",
            "Glyma.08G001700",
            "Glyma.08G007200",
            "Glyma.08G008100",
            "Glyma.08G003800",
            "Glyma.08G006300",
            "Glyma.08G004200",
            "Glyma.08G001000",
            "Glyma.08G002300",
            "Glyma.08G000100",
            "Glyma.08G009200",
            "Glyma.08G009500",
            "Glyma.08G004800",
            "Glyma.08G001900",
            "Glyma.08G003600",
            "Glyma.08G003400",
            "Glyma.08G005800",
            "Glyma.08G003300",
            "Glyma.08G005600",
            "Glyma.08G006000",
            "Glyma.08G009600",
            "Glyma.08G006900",
            "Glyma.08G000500",
            "Glyma.08G008900",
            "Glyma.08G007300",
            "Glyma.08G008000",
            "Glyma.08G005200",
            "Glyma.08G003900",
            "Glyma.08G004900",
            "Glyma.08G002900",
            "Glyma.08G004500",
            "Glyma.08G001100",
            "Glyma.08G002200",
            "Glyma.08G005000",
            "Glyma.08G001800",
            "Glyma.08G009300",
            "Glyma.08G007600",
            "Glyma.08G008400",
            "Glyma.08G002600",
            "Glyma.08G006400",
            "Glyma.08G006500",
            "Glyma.08G009900",
            "Glyma.08G006600",
            "Glyma.08G000900",
            "Glyma.08G004600",
            "Glyma.08G007000",
            "Glyma.08G000200",
            "Glyma.08G005700",
            "Glyma.08G007700",
            "Glyma.08G002700",
            "Glyma.08G004700",
            "Glyma.08G009800",
            "Glyma.08G006700",
            "Glyma.08G003500",
            "Glyma.08G005900",
            "Glyma.08G001200",
            "Glyma.08G001500",
            "Glyma.08G009700",
            "Glyma.08G002000",
            "Glyma.08G008700",
            "Glyma.08G000300",
            "Glyma.08G000800",
            "Glyma.08G008800",
            "Glyma.08G002500",
            "Glyma.08G000400",
            "Glyma.08G007400",
            "Glyma.08G005300",
            "Glyma.08G002800",
            "Glyma.08G004400",
            "Glyma.08G001600",
            "Glyma.08G003100",
            "Glyma.08G002100",
            "Glyma.08G005500"

        ];
        var tissue={
            "pod": 20,
            "cotyledon": 11.5
        };
        dataParams = {};
        dataParams.geneIdList = geneIdList;
        dataParams.tissue = tissue;
        dataParams.traitCategoryId = 19;
//        dataParams.pageNo = 1;
//        dataParams.pageSize = 10;
        var dataParam = Object.assign(sortConditionData,dataParams);

        sortTable(1,dataParam);

    }, false );

    $(".sortGb").click(function () {
        $('.sortText_conter').text("");
        $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");

//        $('.sortSelect').children('option').eq(0);
//        $(".sort_xz .combo-dropdown li").eq(0).prop("selected", 'selected');
//        $(".sortSelect option").eq(0).prop("selected", 'selected');
        //    如果设置性状为空，则隐藏边框
        if($(".sortText_conter").text().length==0){
            $(".sortText").hide()
        }else{
            $(".sortText").show()
        }
    });
    function changeText() {

//        $('.sortSelect').children('option').eq(0).val("");
        var sortText = $(".sortSelect").val();
        if ($('.sortText_conter').text().length !== 0&&$(".sortSelect").val()=="") {
            $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
            $(".sortGb").hide();
            $(".sortSelect option:first").prop("selected", 'selected');
        } else {
            $('.sortText_conter').text(sortText);
            $(".sort_xz .combo-dropdown li").removeClass("option-item").addClass("option-disabled ");
            $(".sortGb").show();
        }

        //    如果设置性状为空，则隐藏边框
        if($(".sortText_conter").text().length==0){
            $(".sortText").hide()
        }else{
            $(".sortText").show()
        }
        //当下拉为“请选择”，恢复初始状态
        if($(".sortSelect").val()==""){
            $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
            $(".sortGb").hide();
        }

        if($(".sort_zz .geneName").val()=="请选择"){

            $(".sort_zz .combo-input").css("color","#ccc")
        }
//    .sort_zz .combo-input{color:#ccc;}

    }

    // 获取组织数据
    organizationData ();
    function organizationData (){
        var promise = SendAjaxRequest("GET", "${ctxroot}/advance-search/query-all-organic");
        promise.then(
            function (jsonStr){
                var arr_geneList = [""];
                var arr_geneName = ["请选择"];
                for (var i = 0; i < jsonStr.length; i++) {
                    //组织select
                    list = [jsonStr[i].chinese];
                    arr_geneName.push(list)
                    var arr_geneListName = [];
                    for (var j = 0; j < jsonStr[i].children.length; j++) {
                        geneList = jsonStr[i].children[j].name
                        arr_geneListName.push(geneList);
                    }
                    arr_geneList.push(arr_geneListName);
                }
                //初始化组织
                init(arr_geneName);
                $('.sort_zz select').comboSelect();
                //选择单个组织触发
                $("#geneName").change(function () {
                    changeSelect(this.selectedIndex, arr_geneList);
                    // 设置组织
                    $("#geneList li").off("click").on("click",function (){
                        if($(this).hasClass("checked")) {
                            $(this).removeClass("checked");
                            var _thisText=$(this).text();
                            var selTexts = $(".sortZzText").find("span");
                            for (var i=0;i<selTexts.length;i++){
                                var selTextsName = $(selTexts[i]).find(".sortZzText_b2").text();
                                if(_thisText==selTextsName){
                                    $(selTexts[i]).remove();
                                }
                            }
                        }else {
                            $(this).addClass("checked");
                            var sortzzTextConter=$(this).text();
                            var sortZzText_name=$(this).parent().parent().find("#geneName").val()
                            var str="<span class='sortZzText_conter'><b class='sortZzText_b1'>"+sortZzText_name+"</b><b class='sortZzText_b2'>"+sortzzTextConter+"</b><i class='sortZzGb'>X</i></span>";
                            $(".sortZzText").append(str);
                        }

                        // 选中的点击删除
                        $(".sortZzGb").on("click",function (){
                            $(this).parent().remove();
                            var _thisdelText=$(this).parent().find(".sortZzText_b2").text();
                            var sortInputs = $("#geneList").find("li");
                            for (var j=0;j<sortInputs.length;j++){
                                var tdParent = $(sortInputs[j]).find("label").text();
                                if(_thisdelText==tdParent){
                                    $(sortInputs[j]).removeClass("checked");
                                }
                            }
                        })
                    });
                    //调用重置保存状态
                    sortSaveStatus();
                    $('.sort_zz select').comboSelect();
                })
            },
            function (error){
                console.log(error);
            }
        )
    };


    // 组织重置保存状态
    function sortSaveStatus(){
        var sortInputs = $("#geneList").find("li");
        var selTexts = $(".sortZzText").find("span");
        for (var i=0;i<selTexts.length;i++){
            // var selTextsName = $(selTexts[i]).text().substring(3,$(selTexts[i]).text().length-1);
            var selTextsName = $(selTexts[i]).find(".sortZzText_b2").text();
            for (var j=0;j<sortInputs.length;j++){
                var tdParent = $(sortInputs[j]).find("label").text();
                if(tdParent==selTextsName){
                    $(sortInputs[j]).addClass("checked");
                }
            }
        }
    }


    // 获取性状数据
     characterData();
    function characterData (){
        var promise = SendAjaxRequest("POST", "${ctxroot}/sort/fetch-trait");
        promise.then(
            function (jsonStr){
                var arr_geneName = ["请选择"];
                var arr_geneId=[""];
                for (var i = 0; i < jsonStr.length; i++) {
                    //性状select
                    list = [jsonStr[i].qtlDesc];
                    arr_geneName.push(list);

                    listId=[jsonStr[i].traitCategoryId];
                    arr_geneId.push(listId);

                }
                //初始化组织
                initXZ(arr_geneName,arr_geneId);
                $('.sortSelect').children('option').eq(0).val("");
                $('.sort_xz select').comboSelect();
            },
            function (error){
                console.log(error);
            }
        )
    };

//点击排序获取排序表格数据

//    $(".sortInfo_btn").click(function() {
//      $(".sortMain").show();
//    });




//   var objData = Object.assign(sortConditionData,dataParam);


    function sortTable(curr,dataParam){
        var promise = SendAjaxRequest("POST","${ctxroot}/sort/fetch-sort-result",JSON.stringify(dataParam));
        promise.then(
            function (result){
                popCount = result.total;
                sortPopuTable(result)

                laypage({
                    cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(result.data.total / pageSizePopu), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(result.data.total / pageSizePopu), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr

                            dataParam.pageNo = obj.curr;
                            dataParam.pageSize = 10;
                            sortTable(obj.curr,dataParam);
                        }
                    }
                });
                $("#popu-paginate .total-page-count > span").html(result.data.total);
                    $("#total-page-count1 span").text(result.data.total);
                    $(".js-search-total").text(result.data.total);

            },function (error){
                console.log(error);
            }
        )
    }

    function sortPopuTable(data) {
        $(".js-table-header-setting-popu").find("label").addClass("checkbox-ac");
        var str = '';
        var jsonStr=data.data;
        for(var i=0;i<jsonStr.list.length;i++){
            str += '<tr>'
            str += '<td class="geneId">'+jsonStr.list[i].geneId+'</td><td class="geneName">'+jsonStr.list[i].geneName+'</td><td class="chromosome">'+jsonStr.list[i].chromosome+'</td><td class="description">'+jsonStr.list[i].description+'</td><td class="location">'+jsonStr.list[i].location+'</td>'
            str += '</tr>'
        }
        $(".popu-table > tbody").empty().append(str);
    }

    var popPageNum = 1;
    var popCount;
    var pageSizePopu = 10;
    // 获取焦点添加样式：
    $("#popu-paginate").on("focus", ".laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $("#popu-paginate").on("blur", ".laypage_skip", function() {
        $(this).removeClass("isFocus");
    });
    // 分页跳转
    $("#paginationCnt").on("focus", ".laypage_total .laypage_skip", function() {
        $(this).addClass("isFocus");
    });
    $("#paginationCnt").on("blur", ".laypage_total .laypage_skip", function() {
        $(this).removeClass("isFocus");
    });
    // 注册 enter 事件的元素
    document.onkeydown = function(e) {
        var _page_skip = $('#pagination .laypage_skip');
//            if( _page_skip.hasClass("isFocus") ) {
//
//                if(_page_skip.val() * 1 >Math.ceil( $("#total-page-count1 span").text() / page.pageSize)) {
//                    return alert("输入页码不能大于总页数");
//                }
//                curr = Number(_page_skip.val());
//                    dataParam.pageNo = curr;
//                sortTable(curr,dataParam);
//            }
        if( _page_skip.hasClass("isFocus") ) {
            var _page_skip = $('#pagination .laypage_skip');
            var curr = Number(_page_skip.val());
            dataParam.pageNo = curr;
            sortTable(curr,dataParam);
        }
    };
    // 修改每页显示条数
    $("#per-page-count").on("change", ".lay-per-page-count-select", function() {
//        var _page_skip = $('#pagination .laypage_skip');
        var currs=$(".laypage_curr").text();
       var pageSize = Number($(this).val());
//            dataParam.pageNo = curr;
            dataParam.pageSize = pageSize;
        sortTable(currs,dataParam);
    });



</script>
</body>
</html>