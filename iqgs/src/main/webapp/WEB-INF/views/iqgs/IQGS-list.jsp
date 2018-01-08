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
    <link rel="stylesheet" href="${ctxStatic}/css/newAdd.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/laypage.css">--%>
    <link rel="stylesheet" href="${ctxStatic}/js/laypage/skin/laypage.css">

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
            <li class="geneIdName"><a class="" >Search By Gene ID/Name</a></li>
            <li class="geneFunction"><a class="" >Search By Gene Function</a></li>
            <li class="region" ><a class="">Search By Region</a></li>
            <li class="qtl active" ><a class="">Search By QTL</a></li>
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
            <%--modify by jarry --%>
            <div id="qtlAdd" class="tab-pane">
                <p class="search-title">Search By QTL</p>
                <label>
                    <input class="search-input" id="qtlName" type="text" name="search" placeholder="输入您要查找的关键字">
                    <button id="QtlBtnName" class="search-btn" ><img src="${ctxStatic}/images/search.png">搜索</button>
                </label>
                <div id="qtlErrorTip">
                    根据输入的关键字查询的结果为: 0 条
                </div>
                <div class="fuzzySearch">
                    <ul>
                        <%--<li>--%>
                        <%--<label for="name1">--%>
                        <%--<span id ="name1" data-value="name11"></span>--%>
                        <%--Fusarium lesion length 1-1--%>
                        <%--</label>--%>
                        <%--</li>--%>
                    </ul>
                </div>
                <%--<p class="search-tips">示例: <a href="javascript:void(0);">Seed N at R5 1-1</a></p>--%>
                <p class="search-tips">示例: <a class="qtlExample" href="javascript:void(0);">Seed N at R5 1-1</a></p>
                <%--<p class="search-tips">示例: <a href="${ctxroot}/search/list?keyword=Seed N at R5 1-1&searchType=4">Seed N at R5 1-1</a>--%>
                <div class="sureBtn">
                    <p>确定</p>
                </div>
            </div>
        </div>

    </div>
    <%--modify by jarry at 2018-01-04--%>
    <%--// 高级搜索  按钮--%>
    <div id="advancedSearch">
        <div class="advanceBtn">
            <p>Advanced search
                <img src="${ctxStatic}/images/downtou.png" alt="down" ></p>
        </div>
    </div>

    <%--// 高级搜索  内容筛选区--%>
    <div id="advanceSelect">
        <div class="showSelected">
            <p class="showTitle">已选&gt</p>
            <div class="showContainer">
                <div id="expreDetail">
                    <%--<span class="expreSigle">基因表达量:种子23-45,seed,fjkldjfs</span><span class="deleteIconP">X</span>--%>
                </div>
                <%--<div id="snpDetail">--%>
                <%--</div>--%>
                <%--<div id="indelDetail">--%>
                <%--</div>--%>
                <%--<div id="qtlDetail">--%>
                <%--</div>--%>
            </div>
            <p class="showClear">清空</p>
        </div>
        <div class="selectContainer">
            <div class="geneExpression">
                <p>基因表达量:&emsp;</p>
                <div id="expreKinds">
                    <div class="inputBox">
                        <input type="text" placeholder="请选择">
                        <img src="${ctxStatic}/images/todown.png" alt="^">
                        <img src="${ctxStatic}/images/totop.png" alt="^" class="imgChange">
                    </div>
                    <div class="expreList">
                        <ul>
                            <%--<li>--%>
                            <%--花--%>
                            <%--</li>--%>
                            <
                        </ul>
                    </div>
                </div>
                <div class="selectedKinds" id="selectedKinds">
                    <ul class="lists">
                        <%--<li class="checked">--%>
                        <%--<label for="name1">--%>
                        <%--<span id ="name1"></span>--%>
                        <%--seed--%>
                        <%--</label>--%>
                        <%--</li>--%>
                    </ul>
                    <div class="expreRegin">
                        <p class="fromTO">FPKM:&nbsp;&nbsp;
                            <input type="number" id="expreStart" min="0">
                            -
                            <input type="number" id="expreEnd" min="0">
                        </p>
                        <%--<p class="expreTip">(min=0,max=100)</p>--%>
                        <p class="expreConfirm backColorBtn" >确定</p>
                    </div>
                </div>
            </div>
            <div class="geneSnp" style="min-height:83px;">
                <p class="titleStyle">SNP:&nbsp;&nbsp;</p>
                <div class="snpList">
                    <ul>
                        <li>
                            <%--<label for="snp1">--%>
                            <%--<span id ="snp1" data-value=""></span>--%>
                            <%--Downstream--%>
                            <%--</label>--%>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="geneIndel" style="min-height:83px;">
                <p class="titleStyle">INDEL:&nbsp;&nbsp;</p>
                <div class="indelList" style="margin-left: 42px;">
                    <ul>
                        <li>
                            <%--<label for="indel1">--%>
                            <%--<span id ="indel1" data-value=""></span>--%>
                            <%--indel3--%>
                            <%--</label>--%>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="geneQtl" style="min-height:56px;position:relative;border-bottom: 1px dashed #ffffff;">
                <p class="titleStyle">QTL:&nbsp;&nbsp;</p>
                <div class="qtlInput1" id="qtlKinds">
                    <div class="inputBox">
                        <input type="text" id="qtlBox1" placeholder="请选择">
                        <img src="${ctxStatic}/images/todown.png" alt="^">
                        <img src="${ctxStatic}/images/totop.png" alt="^" class="imgChange">
                    </div>
                    <div class="qtlList imgChange">
                        <ul>
                            <%--<li>--%>
                            <%--种子相关--%>
                            <%--</li> --%>
                        </ul>
                    </div>
                </div>
                <div class="qtlInput2" id="qtlKinds2">
                    <div class="inputBox">
                        <input type="text" id="qtlBox2" placeholder="请选择">
                        <img src="${ctxStatic}/images/todown.png" alt="^">
                        <img src="${ctxStatic}/images/totop.png" alt="^" class="imgChange">
                    </div>
                    <div class="qtlList" style="width:410px;">
                        <ul>
                        </ul>
                    </div>
                </div>
                <div class="qtlSelectList">
                    <ul>
                        <%--<li>--%>
                        <%--<label for="qtl1">--%>
                        <%--<span id ="qtl1"></span>--%>
                        <%--Seed N at R5 1-1--%>
                        <%--</label>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label for="qtl2">--%>
                        <%--<span id ="qtl2" data-value=""></span>--%>
                        <%--Seed N at R5 1-2--%>
                        <%--</label>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label for="qtl3">--%>
                        <%--<span id ="qtl3" data-value=""></span>--%>
                        <%--Seed N at R5 1-3--%>
                        <%--</label>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label for="qtl4">--%>
                        <%--<span id ="qtl4" data-value=""></span>--%>
                        <%--Seed N at R5 1-4--%>
                        <%--</label>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label for="qtl5">--%>
                        <%--<span id ="qtl5" data-value=""></span>--%>
                        <%--Seed N at R5 1-4--%>
                        <%--</label>--%>
                        <%--</li>--%>
                        <%--<li>--%>
                        <%--<label for="qtl6">--%>
                        <%--<span id ="qtl6" data-value=""></span>--%>
                        <%--Seed N at R5 1-4--%>
                        <%--</label>--%>
                        <%--</li>--%>
                    </ul>
                </div>

            </div>
            <div id="iqgsSearch">
                <p>搜索</p>
            </div>
            <div style="clear:both;"></div>
        </div>

    </div>

    <div class="search-result" style="margin-top:66px;">
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
                    <div id="paginationCnt" class="pagination"></div>
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
<script>
    window.DOMAIN = "${ctxroot}/iqgs";
    window.ctxROOT = "${ctxroot}";
    window.ctxStatic = "${ctxStatic}"
</script>
<%--<script src="${ctxStatic}/js/mock/mock.js"></script>--%>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<%--<script src="${ctxStatic}/js/newAddNeed.js"></script>--%>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<%--<script src="${ctxStatic}/js/laypage/laypage.js"></script>--%>
<script src="${ctxStatic}/js/iqgs-list.js"></script>
<script>
    // sessionStorage
    if(window.sessionStorage){
        var storage = window.sessionStorage;
    }else{
        alert('This browser does NOT support localStorage');
    };
    window.DOMAIN = "${ctxroot}/iqgs";
    var searchType = '${searchType}';
    var param = window.location.search;
    var nums = [];
    if(param.split("&")[0].split("=")[1].indexOf("*") !=-1){
        var currParam = param.split("&")[0].split("=")[1].split("*");
        // 把字符串转换为数字
        for(var i=0;i<currParam.length;i++){
            nums.push(Number(currParam[i]));
        };
    }else {
        nums.push(Number(param.split("&")[0].split("=")[1]));
    }
    var qtlSearchNames = JSON.parse(storage.getItem("qtlSearchNames"));
    var page = {curr: 1, pageSize:10};
    function initSearchTab() {
        if (searchType == 1) {
            $("#key_name").val('${keyword}');
            $($("#myTabs li")[0]).trigger('click');
            activeItem(searchType);
        }else if (searchType == 2) {
            $("#key_func").val('${keyword}');
            $($("#myTabs li")[1]).trigger('click');
            activeItem(searchType);
        }else if(searchType == 3){
            $(".js-region").val('${chr}');
            $("#rg_begin").val('${rgBegin}');
            $("#rg_end").val('${rgEnd}');
            $($("#myTabs li")[2]).trigger('click');
            activeItem(searchType);
        }else {
            $($("#myTabs li")[3]).trigger('click');
            if(qtlSearchNames.length!=1){
                $("#qtlName").val(qtlSearchNames.join(","));
            }else{
                $("#qtlName").val(qtlSearchNames[0])
            };
            activeItem(searchType);
            $("#advancedSearch").show();
        }
    }
    // 切换到列表页之后searchtype 为多少就默认显示到第几个
    function activeItem(searchtype){
        var list = $("#myTabs li");
        for (var i=0;i<list.length;i++){
            if($(list[i]).hasClass("active")){
                $(list[i]).removeClass("active");
            }
        };
        $(list[searchtype-1]).addClass("active");
        var cntList = $("#myTabContent>div");
        for(var i=0;i<cntList.length;i++){
            if($(cntList[i]).hasClass("tab-pane-ac")){
                $(cntList[i]).removeClass("tab-pane-ac")
            }
        };
        $(cntList[searchtype-1]).addClass("tab-pane-ac");
    };
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
        } else if(searchType == 3){
            $.getJSON('${ctxroot}/iqgs/search/range', {
                pageNo: page.curr || 1,
                pageSize: page.pageSize || 10,
                begin : $("#rg_begin").val(),
                end : $("#rg_end").val(),
                chr : $(".js-region").val()
            }, resultCallback);
        }else {
            getQtlNameData(page.curr,page.pageSize);
            $(".result-text>span:first").text(qtlSearchNames.join(","));
            <%--$.getJSON('${ctxroot}/advance-search/confirm', {--%>
                <%--pageNo: page.curr || 1,--%>
                <%--pageSize: page.pageSize || 10,--%>
                <%--chosenQtl : nums--%>
            <%--}, resultCallback);--%>

        }
    }
    // 根据qtlName 获取数据
    function getQtlNameData(curr,pageSize){
        var data = {
            pageNo: curr || 1,
            pageSize: pageSize || 10,
            chosenQtl : nums
        };
        $.ajax({
            type:"GET",
            url:"${ctxroot}/advance-search/confirm",
            data:data,
            success:function (result){
                // 关闭遮罩层
                layer.closeAll();
                    var data = result.data.list;
                    var total = result.data.total;
                    var res = {};
                    res.data = data;
                    res.total = total;
                    if(result.code == 0 && data.length!=0){
                        resultCallback(res)
                    }
            },
            error:function (error){
                console.log(error);
            }

        })
    }
    function resultCallback(res) {
        $("span.js-search-total").text(res.total);
        $("#total-page-count1 span").text(res.total);

        renderList(res.data);
        laypage({
            cont: 'paginationCnt',//容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
            pages: Math.ceil(res.total / page.pageSize), //通过后台拿到的总页数 (坑坑坑：这个框架默认是如果只有一页的话就不显示)
//            pages: 100, //通过后台拿到的总页数 (坑坑坑：这个框架默认是如果只有一页的话就不显示)
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
    };

    function renderList(listdata) {
        // 关闭遮罩层
        layer.closeAll();
        if (listdata && listdata.length > 0) {
            var html = [];
            $.each(listdata, function(i, item){
                var geneName = item.geneName?item.geneName:"-";
                var description =  item.description?item.description:"-";
                html.push('<div class="list">');
                html.push('    <div class="tab-index">' + (page.pageSize * (page.curr-1) + i+1) + '.</div>');
                html.push('    <div class="list-content">');
                html.push('        <p class="content-h"><a target="_blank" href="${ctxroot}/iqgs/detail/basic?gen_id=' + item.geneId + '">' + item.geneId + '</a></p>');
                html.push('        <p class="h-tips">基因名:<span>' + geneName + '</span></p>');
//                modify by jarry
                if(searchType == 4){
                    var qtls= item.associateQTLs;
                    var qtlNames = "";
                    for(var k=0;k<qtls.length;k++){
                        qtlNames +=qtls[k].qtlName +" ";
                    };
                    var snp = item.existsSNP?"存在Exonic_nonsynonymouse SNV":"-";
                    var expreTissues = item.rootTissues.length?item.rootTissues.join(","):"-";
                    var description = item.description?item.description:"-";
                    html.push('        <p class="h-snp qltlistSty">QTL:<span>' + item.associateQTLs.length + '个 （' + qtlNames +')</span></p>');
                    html.push('        <p class="h-qtl qltlistSty">SNP:<span>' + snp + '</span></p>');
                    html.push('        <p class="h-qtl qltlistSty">基因表达量(FPKM>30):<span>' + expreTissues + '</span></p>');
                };
                html.push('        <p class="content-b">基因注释:<span>' + description + '</span></p>');
                html.push('    </div>');
                html.push('</div>');
            });
            $(".search-result-b .tab-list").html(html.join('\n'));
        }
    }
    // 修改每页显示条数
    $("#per-page-count1").on("change", ".lay-per-page-count-select", function() {
        // 开户遮罩层
        layer.msg('数据加载中!', {
            shade: [0.5, '#393D49']
        });
        page.pageSize = Number($(this).val());
        getQtlNameData(page.curr,page.pageSize);
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
        var _page_skip = $('#paginationCnt .laypage_skip');
        if(e && e.keyCode==13){ // enter 键
            // 开户遮罩层
            layer.msg('数据加载中!', {
                shade: [0.5, '#393D49']
            });
            if( _page_skip.hasClass("isFocus") ) {

                if(_page_skip.val() * 1 >Math.ceil( $("#total-page-count1 span").text() / page.pageSize)) {
                    return alert("输入页码不能大于总页数");
                }
              page.curr = _page_skip.val();
                getQtlNameData(page.curr,page.pageSize);
            }
        }
    };

    function viewBaseInfo(geneId) {
        var url = '${ctxroot}/iqgs/detail/basic?gen_id=' + geneId;
        window.location = url;
    }

    $(function(){
        initSearchTab();
        requestSearchData();
    });
</script>
<script src="${ctxStatic}/js/iqgs.js"></script>
<script src="${ctxStatic}/js/newAddNeed.js"></script>
</body>
</html>