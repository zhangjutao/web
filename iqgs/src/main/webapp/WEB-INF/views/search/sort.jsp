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

<body style="min-width:auto;     width: 100%;  background-color: #fff;">
<div class="sort_top">
    <div class="sortText_main"><span>已选></span>
        <div class="sortText"><span class="sortText_conter"></span><i class="sortGb">X</i>
        </div>
        <div class="sortZzText">
            <%--<span class="sortZzText_conter"></span><i class="sortZzGb">X</i>--%>
        </div>
    </div>

    <div class="sort_selectConter">
        <div class="sort_xz">
            <label>设置性状：</label>
            <select class="sortSelect">
                <option value="">请选择</option>
                <option value="一月">一月</option>
                <option value="二月">二月</option>
                <option value="三月">三月</option>
                <option value="四月">四月</option>
            </select>
        </div>
        <form name="form1" class="sort_zz">
         <div class="sort_zz_lab">
             <label>设置组织：</label>
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
            <button>复制</button>
            <button>下载</button>
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
        $('.sort_zz select').comboSelect();
    });

    $(".sortGb").click(function () {
        $('.sortText_conter').text("");
        $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
//        $(".sortGb").hide();
        //    如果设置性状为空，则隐藏边框
        if($(".sortText_conter").text().length==0){
            $(".sortText").hide()
        }else{
            $(".sortText").show()
        }
    });
    function changeText() {
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
    }

    // 获取基因表达量的组织以及小组织
    getExpreData ();
    function getExpreData (){
        var promise = SendAjaxRequest("GET", "${ctxroot}/advance-search/query-all-organic");
        promise.then(
            function (result){
                console.log(result)
            },
            function (error){
                console.log(error);
            }
        )
    };
</script>
</body>
</html>