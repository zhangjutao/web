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
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>

<body style="min-width:auto; width: 100%;  background-color: #fff;">
<div class="sort_top">
    <div class="sortText_main"><span>已选></span>
        <div class="sortText"><span class="sortText_conter"></span><i class="sortGb">X</i>
        </div>
        <div class="sortZzText">
            <%--<span class="sortZzText_conter"></span><i class="sortZzGb">X</i>--%>
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
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                <tr>
                    <td class="species">Domestic</td>
                    <td class="locality">Korea</td>
                    <td class="sampleName">KL7</td>
                    <td class="cultivar">Yorkshire</td>
                    <td class="weightPer100seeds">14.51</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>

</div>
<script src="${ctxStatic}/js/iqgsCommon.js"></script>
<script src="${ctxStatic}/js/sort.js"></script>
<script src="${ctxStatic}/js/jquery.combo.select.js"></script>

<script>
    $(function () {
        window.DOMAIN = "${ctxroot}/iqgs";
        window.ctxROOT = "${ctxroot}";
        window.ctxStatic = "${ctxStatic}";
        $('.sort_xz select').comboSelect();

    });
    //获取URL参数
    (function ($) {
        $.getUrlParam = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return unescape(r[2]); return null;
        }
    })(jQuery);
    var keyword = $.getUrlParam('id');
    console.log(keyword)


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
                            console.log(sortZzText_name)
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
                console.log(jsonStr)
                var arr_geneName = ["请选择"];
                for (var i = 0; i < jsonStr.length; i++) {
                    //性状select
                    list = [jsonStr[i].qtlDesc];
                    arr_geneName.push(list)
                }
                //初始化组织
                initXZ(arr_geneName);
                $('.sortSelect').children('option').eq(0).val("");
                $('.sort_xz select').comboSelect();
            },
            function (error){
                console.log(error);
            }
        )
    };

//    var obj = parent.window.document.getElementsByClassName('showClear');
//console.log(obj)
    var parentdb = parent.window.$(".result-title");
console.log(parentdb)
</script>
</body>
</html>