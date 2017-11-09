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
    <title>IQGS list</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>

</head>

<body>

<iqgs:iqgs-header></iqgs:iqgs-header>
<!--header-->
<div class="container">
    <div class=" tabs">
        <ul id="myTabs"  class="searchNav nav-tabs">
            <li class="active geneIdName"><a class="" >Search By Gene ID/Name</a></li>
            <li class=" geneFunction"><a class="" >Search By Gene Function</a></li>
            <li class="region" ><a class="">Search By Region</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div id="GeneIdName" class="tab-pane tab-pane-ac">
                <p class="search-title">Search By Gene ID/Name</p>
                <label>
                    <input class="search-input" id="key_name" type="text" name="search" placeholder="输入您要查找的关键字">
                    <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                    <button id="btn_name" class="search-btn" ><img src="${ctxStatic}/images/search.png">搜索</button>
                </label>
                <p class="search-tips">示例: <a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=Glyma.01G004900">Glyma.01G004900</a><b>;</b> <a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=Glyma.01G004900"> LOC778160</a></p>
            </div>
            <div id="GeneFunction" class="tab-pane">
                <p class="search-title">Search By Gene Function</p>
                <label>
                    <input id="key_func" class="search-input" type="text" name="search" placeholder="输入您要查找的关键字">
                    <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                    <button id="btn_func" class="search-btn"><img src="${ctxStatic}/images/search.png">搜索</button>
                </label>
                <p class="search-tips">示例: <a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=Glyma.01G004900">transcription factor MYBJ6</a></p>
            </div>
            <div id="Region" class="tab-pane">
                <p class="search-title">Search By Region</p>
                <select class="js-region">
                    <option value="Chr01" data-max="">Chr01</option>
                    <option value="Chr02" data-max="">Chr02</option>
                    <option value="Chr03" data-max="">Chr03</option>
                    <option value="Chr04" data-max="">Chr04</option>
                    <option value="Chr05" data-max="">Chr05</option>
                    <option value="Chr06" data-max="">Chr06</option>
                    <option value="Chr07" data-max="">Chr07</option>
                    <option value="Chr08" data-max="">Chr08</option>
                    <option value="Chr09" data-max="">Chr09</option>
                    <option value="Chr10" data-max="">Chr10</option>
                    <option value="Chr11" data-max="">Chr11</option>
                    <option value="Chr12" data-max="">Chr12</option>
                    <option value="Chr13" data-max="">Chr13</option>
                    <option value="Chr14" data-max="">Chr14</option>
                    <option value="Chr15" data-max="">Chr15</option>
                    <option value="Chr16" data-max="">Chr16</option>
                    <option value="Chr17" data-max="">Chr17</option>
                    <option value="Chr18" data-max="">Chr18</option>
                    <option value="Chr19" data-max="">Chr19</option>
                    <option value="Chr20" data-max="">Chr20</option>
                </select>
                <div>
                    <input id="rg_begin" class="region-input region-s" type="number" name="search" placeholder="输入您要查找的数值"><span class="s-line"></span>
                    <input id="rg_end" class="region-input region-e" type="number" name="search" placeholder="输入您要查找的数值">
                    <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                    <button id="btn_range" class="region-search"><img src="${ctxStatic}/images/search.png">搜索</button>
                </div>
                <p class="search-region-tips">示例: Chr01,0bp-10000bp</p>
            </div>
        </div>

    </div>
    <div class="search-result">
        <div class="search-result-h">
            <p class="result-title">搜索结果</p>
            <p class="result-text">您的搜索条件为:<span> ${keyword} </span>,共匹配到<span class="js-search-total"> 0 </span>条相关消息</p>
        </div>
        <div class="search-result-b">
            <div class="tab-list">

            </div>
            <div class="pagination-backtop">
                 <a id="goTopBtn" class="backtop" href="javascript:;">返回顶部</a>
                <div class="ga-ctrl-footer">
                    <div id="pagination1" class="pagination"></div>
                    <div id="per-page-count1" class="per-page-count lay-per-page-count per-page-count">
                        <span>展示数量</span>
                        <select name="" class="lay-per-page-count-select">
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="30">30</option>
                            <option value="50">50</option>
                        </select>
                        <span>条/页</span>
                    </div>
                    <div id="total-page-count1" class="total-page-count">总条数 <span>0</span></div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<script src="${ctxStatic}/js/iqgs.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<script src="${ctxStatic}/js/iqgs-list.js"></script>
<script>
    window.DOMAIN = "${ctxroot}/iqgs";
    var searchType = '${searchType}';
    var page = {curr: 1, pageSize:10};
    function initSearchTab() {
        if (searchType == 1) {
            $("#key_name").val('${keyword}');
            $($("#myTabs li")[0]).trigger('click');
        }else if (searchType == 2) {
            $("#key_func").val('${keyword}');
            $($("#myTabs li")[1]).trigger('click');
        }else{
            $(".js-region").val('${chr}');
            $("#rg_begin").val('${rgBegin}');
            $("#rg_end").val('${rgEnd}');
            $($("#myTabs li")[2]).trigger('click');
        }
    }

    function requestSearchData(){
        // 开户遮罩层
        layer.msg('数据加载中!', {
            shade: [0.5, '#393D49']
        });
        if (searchType == 1) {
            $.getJSON('${ctxroot}/iqgs/search/gene-id-name', {
                pageNo: page.curr || 1,
                pageSize: page.pageSize || 10,
                keyword : $("#key_name").val()
            }, resultCallback);
        } else if (searchType == 2) {
            $.getJSON('${ctxroot}/iqgs/search/func', {
                pageNo: page.curr || 1,
                pageSize: page.pageSize || 10,
                keyword : $("#key_func").val()
            }, resultCallback);
        } else {
            $.getJSON('${ctxroot}/iqgs/search/range', {
                pageNo: page.curr || 1,
                pageSize: page.pageSize || 10,
                begin : $("#rg_begin").val(),
                end : $("#rg_end").val(),
                chr : $(".js-region").val()
            }, resultCallback);
        }
    }

    function resultCallback(res) {
        $("span.js-search-total").text(res.total);
        $("#total-page-count1 span").text(res.total);
        // 关闭遮罩层
        layer.closeAll();
        renderList(res.data);
        laypage({
            cont: $('.ga-ctrl-footer .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res.total / page.pageSize), //通过后台拿到的总页数
            curr: page.curr || 1, //当前页
            skin: '#5c8de5',
            skip: true,
            first: 1, //将首页显示为数字1,。若不显示，设置false即可
            last: Math.ceil(res.total / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
            prev: '<',
            next: '>',
            groups: 3, //连续显示分页数
            jump: function (obj, first) { //触发分页后的回调
                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                    page.curr = obj.curr;
                    requestSearchData();
                }
            }
        });
    }

    function renderList(listdata) {
        if (listdata && listdata.length > 0) {
            var html = [];
            $.each(listdata, function(i, item){
                html.push('<div class="list">');
                html.push('    <div class="tab-index">' + (page.pageSize * (page.curr-1) + i+1) + '.</div>');
                html.push('    <div class="list-content">');
                html.push('        <p class="content-h"><a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=' + item.geneId + '">' + item.geneId + '</a></p>');
                html.push('        <p class="h-tips">基因名:<span>' + item.geneName + '</span></p>');
                html.push('        <p class="content-b">基因注释:<span>' + item.description + '</span></p>');
                html.push('    </div>');
                html.push('</div>');
            });
            $(".search-result-b .tab-list").html(html.join('\n'));
        }
    }

    function viewBaseInfo(geneId) {
        var url = '${ctxroot}/iqgs/detail/basic?gen_id=' + geneId;
        window.location = url;
    }

    $(function(){
        initSearchTab();
        requestSearchData();
    });
</script>
</body>
</html>