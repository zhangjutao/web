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
    <title>IQGS details</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <style>
        body #homologous-gene tbody tr td {
            text-align: center;
            white-space: nowrap;
            position: relative;
        }
        body .ga-ctrl-footer {
            text-align: right;
            padding: 0px 28px !important;
        }
        .homTable {
            width: 853px;
            overflow-x: auto;
        }
        .explain-b table {
            width: auto;
        }
    </style>
</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
    <div class="detail-name">
        <p>${genId}</p>
    </div>
    <div class="detail-content">
        <iqgs:iqgs-nav focus="6" genId="${genId}"></iqgs:iqgs-nav>
        <div class="explains">
            <div class="explain-list" id="homologous-gene" style="min-height:600px;">
                <div class="explain-h">
                    <p>同源基因</p>
                </div>
                <div class="explain-b">
                    <div class="homTable">
                        <table id="tableBody">
                            <thead>
                            <tr>
                                <th>Gene ID</th>
                                <th>Ortholog Species</th>
                                <th>Ortholog Gene ID</th>
                                <th>Ortholog Gene Description</th>
                                <th>Relationship</th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <div class="checkbox-item-tab" id="tyjy-paginate">
                        <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
<script>
    //	$(function(){
    //		$(".item li").each(function(i){
    //			$(this).click(function(){
    //				$(this).addClass("item-ac").siblings().removeClass("item-ac");
    //				$(".tab > div").eq(i).show().siblings().hide();
    //			})
    //		})
    //	})
    initTables();
    var page = {curr: 1, pageSize: 10};
    $(".lay-per-page-count-select").val(page.pageSize);

    // 获取表格数据+分页
    function initTables(currNum, pageSizeNum) {
        $.ajax({
            url: "/iqgs/iqgs/detail/origin/page",
            type: "GET",
            data: {
                pageNo: currNum || 1,
                pageSize: pageSizeNum || 10,
                gene_id: "${genId}"
            },
            dataType: "json",
            success: function (res) {
                //显示表格内容
                if (res.data.list.length == 0) {
                    $('#tyjy-paginate').hide();
                    $("#tableBody").html("<div class='explain-b' style='text-align: center'><img src='${ctxStatic}/images/nodata.png'><div style='padding-top: 10px'>无同源基因信息</div></div>")
                } else {
                    renderTable(res);
                    $('#tyjy-paginate').show();
                    $("#total-page-count > span").html(res.data.total);

                    //显示分页
                    laypage({
                        cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.data.total / page.pageSize), //通过后台拿到的总页数
                        curr: currNum || 1, //当前页
                        skin: '#5c8de5',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(res.data.total / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                var currNum = obj.curr;
                                initTables(currNum, pageSizeNum);
                            }
                        }
                    });
                }
            }
        });
    }

    // 修改每页显示条数
    $("#tyjy-paginate").on("change", ".lay-per-page-count-select", function () {
        var curr = Number($(".laypage_curr").text());
        var pageSize = Number($(this).val());
        var total = $("#total-page-count span").text();
        var mathCeil = Math.ceil(total / curr);
        page.pageSize = Number($(this).val());
        if (pageSize > mathCeil) {
            page.curr = 1;
            initTables(1, pageSize)
        } else {
            initTables(curr, pageSize)
        }
    });

    // 分页跳转
    $("#pagination").on("focus", ".laypage_total .laypage_skip", function () {
        $(this).addClass("isFocus");
    });
    $("#pagination").on("blur", ".laypage_total .laypage_skip", function () {
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
                var total = $("#total-page-count span").text();
                var mathCeil = Math.ceil(total / pageSizeNum);
                if (curr > mathCeil) {
                    page.curr = 1;
                    initTables(1, pageSizeNum)
                } else {
                    page.curr = curr;
                    initTables(curr, pageSizeNum)
                }
            }
        }
    });

    function renderTable(data) {
        var eleData = data.data.list;
        var str = '';
        for (var i = 0; i < eleData.length; i++) {
            str += '<tr>'
            str += '<td>' + eleData[i].geneId + '</td>';
            str += '<td>' + eleData[i].orthologSpecies + '</td>';
            str += '<td>' + eleData[i].orthologGeneId + '</td>';
            str += '<td><p class="js-tipes-show">' + eleData[i].orthologGeneDescription + '</p></td>';
            str += '<td>' + eleData[i].relationship + '</td>';
            str += '</tr>';
        }
        $("#tableBody > tbody").empty().append(str);

        $(".js-tipes-show").hover(
            function () {
                if ($(this).text() !== "") {
                    var _text = $(this).text()
                    var self = this;
                    var content = "";
                    content += "<div>" + _text + "</div>";
                    $.pt({
                        target: self,
                        position: 't',
                        align: 'l',
                        autoClose: false,
                        content: content
                    });
                } else {
                    $(".pt").remove();
                }
            },
            function () {
                $(".pt").remove();
            }
        )
    }
</script>

</body>
</html>