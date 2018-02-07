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
    <style>
        body .tab-detail-tbody {
            width: 860px;
            margin: 0 auto;
        }

        .sortMain #total-page-count {
            position: relative;
            top: -4px;
        }

        .description {
            background: none
        }

        #popu-paginate {
            padding-right: 20px;
        }

        /*.soetTable .description{background-color: #f5f8ff;}*/
        body #layui-layer-shade1 {
            opacity: 0.2 !important;
        }
    </style>
<body style="min-width:auto; width: 100%;  background-color: #fff;">
<div class="sort_top">
    <div class="sortText_main">
        <div class="sortText_div">
            <span class="sortText_tit">已选></span>
            <div class="sortZzText">
                <span class="sortText"><span class="sortText_conter"></span><i class="sortGb">X</i>
                </span>
            </div>
        </div>
    </div>

    <div class="sort_selectConter">
        <form name="forms">
            <div class="sort_xz">
                <label class="sort_lab">设置性状：</label>
                <select name="sortSelect" class="sortSelect" >
                </select>
                <span class="tishi"><img src="${ctxStatic}/images/tishi.png" class="tishiImg">性状仅可选择一项</span>
            </div>
        </form>
        <form name="form1" class="sort_zz">
            <div class="sort_zz_lab">
                <label class="sort_lab">设置组织：</label>
                <select name="geneName" id="geneName">
                    <option>请选择</option>
                </select>
            </div>
            <ul class="select_con" id="geneList">
            </ul>
        </form>
        <button class="sortInfo_btn">排序</button>
    </div>
    <div class="sortMain sortMainImg">
        <img src="${ctxStatic}/images/ydy.jpg" class="ydy">
    </div>
    <div class="sortMain sortMainConter" style="display: none;">
        <div class="sortResult">
            <span class="sortTitle">排序结果</span>
            <span class="sortSpan">
            <button id="copyBtn" class="copyBtn"  data-clipboard-action="copy" data-clipboard-target="#inputText"><img src='${ctxStatic}/images/copys.png'>双击复制</button>
            <button id="exportData"><img src='${ctxStatic}/images/doms.png'>下载</button>
        </span>
        </div>
        <div class="copyHtml" style="display: none;"></div>
        <textarea id="inputText" style="position: absolute; top: 0; left: 0; opacnoneity: 0; z-index: -10;"></textarea>
        <div class="tab-detail-tbody">
            <table class="popu-table soetTable">
                <thead>
                <tr>
                    <td class="geneId">基因ID</td>
                    <td class="geneName">基因名</td>
                    <td class="chromosome">染色体</td>
                    <td class="location">基因位置</td>
                    <td class="">基因注释</td>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="checkbox-item-tab" id="popu-paginate">
            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
        </div>
    </div>
</div>
<script src="${ctxStatic}/js/iqgsCommon.js"></script>
<script src="${ctxStatic}/js/sort.js"></script>
<script src="${ctxStatic}/js/jquery.combo.select.js"></script>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/newAddNeed.js"></script>
<script src="${ctxStatic}/js/clipboard.min.js"></script>
<script>
    $(function () {
        window.DOMAIN = "${ctxroot}/iqgs";
        window.ctxROOT = "${ctxroot}";
        window.ctxStatic = "${ctxStatic}";
        $('.sort_xz select').comboSelect();
    });

    //接收消息数据
    window.addEventListener("message", function (event) {
        // 把父窗口发送过来的数据显示在子窗口中
        var sortConditionData = event.data;
        if (sortConditionData == null) {
            layer.msg("无基因名")
            return false;
        } else if (sortConditionData.type == "geneList") {
            //外部数据库访问传来的postmessage数据
            var geneIdLists = sortConditionData.data.geneList;
            //获取外部数据进行表格排序
            if (geneIdLists.length !== 0) {
                setTimeout(sortCurrencyTable(geneIdLists), 1000);
            }
        } else {
            //性状初始化
            characterData();
            //组织初始化
            organizationData();
            //点击排序获取排序表格数据
            setTimeout(sortCurrencyTable(sortConditionData), 200);
        }
    }, false);

    //处理发送过来的数据
    function sortCurrencyTable(sortConditionData) {
        $(".sortInfo_btn").off("click").click(function () {
//        var geneIdList = sortConditionData;
            var sortXzId = $(".sortSelect option:selected").attr("id");
            var organization = $(".sortZzText").find(".sortZzText_conter");
            var tissue = {};
            for (var i = 0; i < organization.length; i++) {
                var organizationName = deleteSpace($(organization[i]).find(".sortZzText_b2").text());
                tissue[organizationName] = i;
            }
            dataParam = {};
            dataParam.geneIdList = sortConditionData;
            dataParam.tissue = tissue;
            dataParam.traitCategoryId = sortXzId;
            dataParam.pageNo = 1;


            var characterLength = $(".sortText .sortText_conter").text().length;
            var tissueLength = JSON.stringify(tissue) == '{}';
            if (characterLength == 0) {
                layer.msg('请选择性状');
                return false;
            }
            else if (tissueLength == true) {
                layer.msg('请选择组织');
                return false;
            }
            else {
                sortTable(1, dataParam);
            }
        });
    }

    $(".sortGb").click(function () {
        $('.sortText_conter').text("");
        $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
        //    如果设置性状为空，则隐藏边框
        if ($(".sortText_conter").text().length == 0) {
            $(".sortText").hide()
        } else {
            $(".sortText").show()
        }
        $('.sort_xz select').val('');
        $('.sort_xz select').comboSelect();
    });

    function changeText() {
        var sortText = $(".sortSelect").val();
        if ($('.sortText_conter').text().length !== 0 && $(".sortSelect").val() == "") {
            $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
            $(".sortGb").hide();
            $(".sortSelect option:first").prop("selected", 'selected');
        } else {
            $('.sortText_conter').text(sortText);
            $(".sort_xz .combo-dropdown li").removeClass("option-item").addClass("option-disabled ");
            $(".sortGb").show();
        }

        //    如果设置性状为空，则隐藏边框
        if ($(".sortText_conter").text().length == 0) {
            $(".sortText").hide()
        } else {
            $(".sortText").show()
        }
        //当下拉为“请选择”，恢复初始状态
        if ($(".sortSelect").val() == "") {
            $(".sort_xz .combo-dropdown li").removeClass("option-disabled").addClass("option-item");
            $(".sortGb").hide();
        }

        if ($(".sort_zz .geneName").val() == "请选择") {

            $(".sort_zz .combo-input").css("color", "#ccc")
        }
    }

    // 获取组织数据
    function organizationData() {
        var promise = SendAjaxRequest("GET", "${ctxroot}/advance-search/query-all-organic");
        promise.then(
            function (jsonStr) {
                var arr_geneList = [""];
                var arr_geneName = ["请选择"];
                for (var i = 0; i < jsonStr.length; i++) {
                    //组织select
                    list = [jsonStr[i].chinese];
                    arr_geneName.push(list)
                    var arr_geneListName = [];
                    for (var j = 0; j < jsonStr[i].children.length; j++) {
                        geneList = jsonStr[i].children[j].name;
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
                    $("#geneList li").off("click").on("click", function () {
                        if ($(this).hasClass("checked")) {
                            $(this).removeClass("checked");
                            var _thisText = $(this).text();
                            var selTexts = $(".sortZzText").find("span");
                            for (var i = 0; i < selTexts.length; i++) {
                                var selTextsName = $(selTexts[i]).find(".sortZzText_b2").text();
                                if (_thisText == selTextsName) {
                                    $(selTexts[i]).remove();
                                }
                            }
                        } else {
                            $(this).addClass("checked");
                            var sortzzTextConter = $(this).text();
                            var sortZzText_name = $(this).parent().parent().find("#geneName").val()
                            var str = "<span class='sortZzText_conter'><b class='sortZzText_b1'>" + sortZzText_name + "</b><b class='sortZzText_b2'>" + sortzzTextConter + "</b><i class='sortZzGb'>X</i></span>";
                            $(".sortZzText").append(str);
                        }

                        // 选中的点击删除
                        $(".sortZzGb").on("click", function () {
                            $(this).parent().remove();
                            var _thisdelText = $(this).parent().find(".sortZzText_b2").text();
                            var sortInputs = $("#geneList").find("li");
                            for (var j = 0; j < sortInputs.length; j++) {
                                var tdParent = $(sortInputs[j]).find("label").text();
                                if (_thisdelText == tdParent) {
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
            function (error) {
                console.log(error);
            }
        )
    };

    // 组织重置保存状态
    function sortSaveStatus() {
        var sortInputs = $("#geneList").find("li");
        var selTexts = $(".sortZzText").find("span");
        for (var i = 0; i < selTexts.length; i++) {
            // var selTextsName = $(selTexts[i]).text().substring(3,$(selTexts[i]).text().length-1);
            var selTextsName = $(selTexts[i]).find(".sortZzText_b2").text();
            for (var j = 0; j < sortInputs.length; j++) {
                var tdParent = $(sortInputs[j]).find("label").text();
                if (tdParent == selTextsName) {
                    $(sortInputs[j]).addClass("checked");
                }
            }
        }
    }

    // 获取性状数据
    function characterData() {
        var promise = SendAjaxRequest("POST", "${ctxroot}/sort/fetch-trait");
        promise.then(
            function (jsonStr) {
                var arr_geneName = ["请选择"];
                var arr_geneId = [""];
                for (var i = 0; i < jsonStr.length; i++) {
                    //性状select
                    list = [jsonStr[i].qtlDesc];
                    arr_geneName.push(list);
                    listId = [jsonStr[i].traitCategoryId];
                    arr_geneId.push(listId);
                }
                //初始化组织
                initXZ(arr_geneName, arr_geneId);
                $('.sortSelect').children('option').eq(0).val("");
                $('.sort_xz select').comboSelect();
            },
            function (error) {
                console.log(error);
            }
        )
    }

    //排序获取列表数据
    var paseSize=10;
    function sortTable(curr, dataParam,pageSize) {
        dataParam.pageSize = pageSize||10;
        var load = layer.load(1);
        // 开启遮罩层
//        layer.msg('数据加载中!', {
//            shade: [0.5, '#393D49']
//        });
        var promise = SendAjaxRequest("POST", "${ctxroot}/sort/fetch-sort-result", JSON.stringify(dataParam));
        promise.then(
            function (result) {

                popCount = result.total;
                sortPopuTable(result)
                $(".ydy").hide();
                $(".sortMain").show();
                laypage({
                    cont: $('#popu-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(result.data.total / dataParam.pageSize), //通过后台拿到的总页数
                    curr: curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(result.data.total / dataParam.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function (obj, first) { //触发分页后的回调
                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                            dataParam.pageNo = obj.curr;
                            dataParam.pageSize = dataParam.pageSize;
                            sortTable(obj.curr, dataParam,pageSize);
                        }
                    }
                });
                $("#popu-paginate .total-page-count > span").html(result.data.total);
                $("#total-page-count1 span").text(result.data.total);
                $(".js-search-total").text(result.data.total);

                if (result.data.list.length !== 0) {
                    //复制方法调用
//                    document.getElementById("copyBtn").click();
                    $("#copyBtn").click(function () {
                        sortCopy(dataParam);
                    });

                    //导出方法调用
                    $("#exportData").off('click').click(function () {
                        exportData(dataParam);
                    })
                } else {
                    $(".soetTable tbody").html("<p class='dataNo'>暂无数据！</p>");
                }
//
                // 关闭遮罩层
                layer.close(load);
//                layer.closeAll();
            }, function (error) {
                console.log(error);
            }
        )
    }

    //复制数据获取
    function sortCopy(dataParam) {
        var promise = SendAjaxRequest("POST", "${ctxroot}/sort/copy-ordered-geneId", JSON.stringify(dataParam));
        promise.then(
            function (result) {
                var str = "";
                for (var i = 0; i < result.data.length; i++) {
                    str += '<span class="geneId">' + result.data[i] + ',</span>'
                }
                $(".copyHtml").append(str);

                if (result.data.length !== 0) {
//                        copyText()
                    var text = $(".copyHtml").text();
                    var inputs = document.getElementById("inputText");
                    inputs.value = text.substring(0, text.length - 1); // 修改文本框的内容
                    inputs.select(); // 选中文本

                    var clipboard = new Clipboard('#copyBtn');

                    clipboard.on('success', function(e) {
                        console.log(e);
                        layer.msg("复制成功！");
                    });

                    clipboard.on('error', function(e) {
                        console.log(e);
                    });

                }
            }, function (error) {
                console.log(error);
            }
        )
    }

    //复制
//    function copyText() {
//        var text = $(".copyHtml").text();
//        var inputs = document.getElementById("inputText");
//        inputs.value = text.substring(0, text.length - 1); // 修改文本框的内容
//        inputs.select(); // 选中文本
//        document.execCommand("Copy"); // 执行浏览器复制命令
//        layer.msg("复制成功！");
//    }

    //排序表格生成
    function sortPopuTable(data) {
        $(".js-table-header-setting-popu").find("label").addClass("checkbox-ac");
        var str = '';
        var jsonStr = data.data;
        for (var i = 0; i < jsonStr.list.length; i++) {
            str += '<tr>'
            str += '<td class="geneId"><a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=' + jsonStr.list[i].geneId + '">' + jsonStr.list[i].geneId + '</a></td><td class="geneName">' + jsonStr.list[i].geneName + '</td><td class="chromosome">' + jsonStr.list[i].chromosome + '</td><td class="location">' + jsonStr.list[i].location + '</td><td class="description">' + jsonStr.list[i].description + '</td>'
            str += '</tr>'
        }
        $(".popu-table > tbody").empty().append(str);
    }

    var popPageNum = 1;
    var popCount;
//    var pageSizePopu = 10;
    // 获取焦点添加样式：
    $("#popu-paginate").on("focus", ".laypage_skip", function () {
        $(this).addClass("isFocus");
    });
    $("#popu-paginate").on("blur", ".laypage_skip", function () {
        $(this).removeClass("isFocus");
    });
    // 分页跳转
    $("#paginationCnt").on("focus", ".laypage_total .laypage_skip", function () {
        $(this).addClass("isFocus");
    });
    $("#paginationCnt").on("blur", ".laypage_total .laypage_skip", function () {
        $(this).removeClass("isFocus");
    });
    // 注册 enter 事件的元素
    $(document).keyup(function (event) {
        var _page_skip = $('#pagination .laypage_skip');
        if (_page_skip.hasClass("isFocus")) {
            if (event.keyCode == 13) {
                var _page_skip = $('#pagination .laypage_skip');
                var curr = Number(_page_skip.val());
                var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                if (_page_skip.val() * 1 > Math.ceil($("#total-page-count span").text() / curr)) {
//                    layer.msg("输入页码不能大于总页数");
//                    return false;
                    dataParam.pageNo = 1;
                    dataParam.pageSize = pageSizeNum
                    sortTable(1, dataParam,pageSizeNum);
                }else{
                    dataParam.pageNo = curr;
                    sortTable(curr, dataParam,pageSizeNum);
                }

            }
        }
    });

    //    document.onkeydown = function (e) {
    //        var _page_skip = $('#pagination .laypage_skip');
    ////            if( _page_skip.hasClass("isFocus") ) {
    ////
    ////                if(_page_skip.val() * 1 >Math.ceil( $("#total-page-count1 span").text() / page.pageSize)) {
    ////                    return alert("输入页码不能大于总页数");
    ////                }
    ////                curr = Number(_page_skip.val());
    ////                    dataParam.pageNo = curr;
    ////                sortTable(curr,dataParam);
    ////            }
    //        if (_page_skip.hasClass("isFocus")) {
    //            var _page_skip = $('#pagination .laypage_skip');
    //            var curr = Number(_page_skip.val());
    //            dataParam.pageNo = curr;
    //            sortTable(curr, dataParam);
    //        }
    //    };
    // 修改每页显示条数
    $("#per-page-count").on("change", ".lay-per-page-count-select", function () {
//        var _page_skip = $('#pagination .laypage_skip');
        var currs = $(".laypage_curr").text();
        var pageSize = Number($(this).val());
//            dataParam.pageNo = curr;

      var total= $("#total-page-count span").text();
        if(currs*pageSize>total) {
//             layer.msg("切换条数不能大于总页数");
//             return false;
            dataParam.pageNo = 1;
            sortTable(dataParam.pageNo, dataParam,pageSize);
       }else{
            dataParam.pageSize = pageSize;
            sortTable(currs, dataParam,pageSize);
        }

    });

    // 表格导出
    function exportData(dataParam) {
        // 修复tomcat8无法识别的JSON格式问题
        $.ajax({
            type: "POST",
            url: "${ctxroot}/sort/download-sort",
            data: JSON.stringify(dataParam),
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var path = 'http://' + extractHostname(window.location.href) + ':' + window.location.port;
                window.location.href = path + result.data;

            },
            error: function (error) {
                console.log(error);
            }
        })

    }

    function extractHostname(url) {
        var hostname;
        //find & remove protocol (http, ftp, etc.) and get hostname

        if (url.indexOf("://") > -1) {
            hostname = url.split('/')[2];
        }
        else {
            hostname = url.split('/')[0];
        }

        //find & remove port number
        hostname = hostname.split(':')[0];
        //find & remove "?"
        hostname = hostname.split('?')[0];

        return hostname;
    }

</script>
</body>
</html>