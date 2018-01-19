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
    <title>QTL信息列表</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/index.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <style>
        .nav li {
            position: relative;
        }
        .nav .second-text a {
            display: block;
            margin-right: 15px;;
            float: left;
            padding: 5px;
        }

        .nav .second-text a:hover {
            background-color: #eff3fa;
            color: #5c8ce6;
            border-radius: 2px;
        }
        .nav li:nth-child(8) .second-text:before {
            height: 59px;
        }
        .checkbox-tab table .lg-item a, .checkbox-tab table .chr-item a {
            display: block;
            height: 30px;;
            line-height: 30px;;
            text-align: center;
            float: left;
            width: 80px;
            color: #666666
        }

        .lg-item a:hover, .chr-item a:hover {
            background-color: #e6edff;
        }

        .version-num {
            text-align: center;
            position: absolute;
            left: 77px;
        }

        .version-num span {
            height: 30px;
            min-width: 190px;
            display: inline-block;
            color: #999
        }

        .version-num span img {
            width: 15px;
            vertical-align: middle;
        }

        .version-num select {
            position: absolute;
            left: 0px;
            top: 1px;
            width: 100%;
            height: 30px;
            margin-left: 20px;
        }

        .item-ac .checkbox-list {
            display: none;
        }

        .item-ac .export-data .btn-group {
            display: none;
        }

        .navigation-toggle {

        }

        .navigation-toggle .tab-search {
            margin-bottom: 0;
        }

        .navigation-toggle .table-item {
            margin-bottom: 1px;
            float: right;
            width: 1125px;;
        }

        .navigation-toggle article {
            width: 1125px;
            min-height: 560px;
            position: relative;
        }

        .nav_ac {
            width: 70px;
            height: 689px;
            position: relative;
            float: left;
            background-color: #fff
        }

        .nav_ac .icon-right {
            float: none;
            width: 100%;
            padding-top: 15px;
            border-bottom: 1px solid #e6e6e6
        }

        .nav_ac .icon-right img {
            margin-left: 20px;
        }

        .author-pop-tab {
            width: 730px;
            background-color: #fff;
            position: fixed;
            top: 60px;
            left: 25%;
            z-index: 11;
            border: 1px solid #e6e6e6;
        }

        .author-pop-tab:before {
            /*content: "";*/
            /*display: block;*/
            /*position: absolute;*/
            /*top: -11px;*/
            /*right: 110px;*/
            /*width: 0;*/
            /*height: 0;*/
            /*border-bottom: 10px solid #d4d4d4;*/
            /*border-left: 10px solid transparent;*/
            /*border-right: 10px solid transparent;*/
        }

        .author-pop-tab .information-title {
            height: 50px;
            line-height: 50px;
            text-align: center;
        }

        .author-pop-tab .information-tab {
            padding: 20px;
        }

        .checkbox-tab .information-title button {
            float: right;
            color: #666;
            margin-right: 20px;
            margin-top: -38px;
            background-color: #f5f8ff;
            border: none;
            font-size: 20px;
            cursor: pointer;
        }
        .js-pop-head,.information-title,.tab-detail-thead{cursor: move;}
    </style>
</head>
<body>
<qtl:qtl-header/>
<section class="container navigation-toggle">
    <div class="tab-search">
        <div class="search">
            <select class="js-search-select">
                <option value="All" <c:if test="${type=='All'}">selected</c:if>>All</option>
                <option value="Trait" <c:if test="${type=='Trait'}">selected</c:if>>Trait</option>
                <option value="QTL Name" <c:if test="${type=='QTL Name'}">selected</c:if>>QTL Name</option>
                <option value="marker" <c:if test="${type=='marker'}">selected</c:if>>Marker</option>
                <option value="parent" <c:if test="${type=='parent'}">selected</c:if>>Parent</option>
                <option value="reference" <c:if test="${type=='reference'}">selected</c:if>>Reference</option>
            </select>
            <label>
                <input type="text" name="search" class="js-search-text" placeholder="输入您要查找的关键字" value="${keywords}">
                <span class="clear-input" style="display:none ">
                    <img src="${ctxStatic}/images/clear-search.png">
                </span>
                <button type="button" class="js-search-btn"><img src="${ctxStatic}/images/search.png">搜索</button>
            </label>
        </div>

    </div>
    <div class="nav_ac">
        <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
    </div>
    <div class="contant">
        <div class="table-item box-shadow item-ac">
            <div class="checkbox-list">
                <div>
                    <span>表格内容:</span>
                    <dl>
                        <dd><label for="id"><input id="id" type="checkbox" name="" value="id" checked>ID</label></dd>
                        <dd><label for="qtlName"><input id="qtlName" type="checkbox" name="" value="qtlName" checked>QTL
                            Name</label></dd>
                        <dd><label for="trait"><input id="trait" type="checkbox" name="" value="trait"
                                                      checked>Trait</label></dd>
                        <dd><label for="type"><input id="type" type="checkbox" name="" value="type" checked>Type</label>
                        </dd>
                        <dd><label for="chr"><input id="chr" type="checkbox" name="" value="chr" checked>Chr</label>
                        </dd>
                        <dd><label for="lg"><input id="lg" type="checkbox" name="" value="lg" checked>LG</label></dd>
                        <%--<dd><label for="version"><input id="version" type="checkbox" name="" value="version" checked>version</label></dd>--%>
                        <dd><label for="method"><input id="method" type="checkbox" name="" value="method" checked>Method</label>
                        </dd>
                        <dd><label for="marker1"><input id="marker1" type="checkbox" name="" value="marker1" checked>Marker1</label>
                        </dd>
                        <dd><label for="marker2"><input id="marker2" type="checkbox" name="" value="marker2" checked>Marker2</label>
                        </dd>
                        <dd><label for="genesNum"><input id="genesNum" type="checkbox" name="" value="genesNum" checked>Genes</label>
                        </dd>
                        <dd><label for="lod"><input id="lod" type="checkbox" name="" value="lod" checked>LOD</label>
                        </dd>
                        <dd><label for="parent1"><input id="parent1" type="checkbox" name="" value="parent1" checked>Parent1</label>
                        </dd>
                        <dd><label for="parent2"><input id="parent2" type="checkbox" name="" value="parent2" checked>Parent2</label>
                        </dd>
                        <dd><label for="genomeStart"><input id="genomeStart" type="checkbox" name="" value="genomeStart"
                                                            checked>Genome start</label></dd>
                        <dd><label for="genomeEnd"><input id="genomeEnd" type="checkbox" name="" value="genomeEnd"
                                                          checked>Genome end</label></dd>
                        <dd><label for="author"><input id="author" type="checkbox" name="" value="author" checked>Reference</label>
                        </dd>
                    </dl>
                </div>
                <label class="more">
                    <span>其&nbsp;&nbsp;&nbsp;&nbsp;他:</span>

                    <div class="version-num" style="">
                        <span>版本号 <img src="${ctxStatic}/images/arrow-drop-down.png"></span>
                        <select class="js-version-select">
                            <c:forEach items="${versions}" var="item">
                                <option value="${item}">${item}</option>
                            </c:forEach>
                        </select>
                    </div>

                </label>
            </div>
            <div class="export-data">
                <p class="btn-export-set">
                    <button class="btn btn-export"><img src="${ctxStatic}/images/export.png">导出数据</button>
                    <button class="btn set-up"><img src="${ctxStatic}/images/set.png">表格设置</button>
                </p>
                <p class="btn-group">
                    <button class="btn-fill btn-confirm">确认</button>
                    <button class="btn-chooseAll">全选</button>
                    <button class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                </p>
            </div>
        </div>

        <aside style="display: none">
            <div class="item-header">
                <div class="icon-left"><img src="${ctxStatic}/images/bookmarks.png">性状<i class="">TRAITS</i></div>
                <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
            </div>

            <%@ include file="/WEB-INF/views/include/nav.jsp" %>

        </aside>
        <article class="">
            <div class="checkbox-tab">
                <table class="checkbox-list-item">
                    <thead>
                    <tr>
                        <td class="param t_id">ID</td>
                        <td class="param t_qtlName">QTL Name<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component qtl-name-component">
                                <input type="text" placeholder="请输入" class="js-qtl-name">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param trait t_trait">Trait<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component trait-component">
                                <input type="text" placeholder="请输入" class="js-trait">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_type">Type<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component type-component">
                                <input type="text" placeholder="请输入" class="js-type">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>

                        <td class="param chr t_chr">Chr<img src="${ctxStatic}/images/arrow-drop-down.png">
                            <input class="js-chr" type="hidden">

                            <div class="chr-item">
                                <c:forEach items="${chrs}" var="item">
                                    <a href="#">${item}</a>
                                </c:forEach>
                            </div>
                        </td>
                        <td class="param lg t_lg">LG<img src="${ctxStatic}/images/arrow-drop-down.png">
                            <input class="js-lg" type="hidden">

                            <div class="lg-item">
                                <a href="#">D1a</a>
                                <a href="#">D1b</a>
                                <a href="#">N</a>
                                <a href="#">C1</a>
                                <a href="#">A1</a>
                                <a href="#">C2</a>
                                <a href="#">M</a>
                                <a href="#">A2</a>
                                <a href="#">K</a>
                                <a href="#">O</a>
                                <a href="#">B1</a>
                                <a href="#">H</a>
                                <a href="#">F</a>
                                <a href="#">B2</a>
                                <a href="#">E</a>
                                <a href="#">J</a>
                                <a href="#">D2</a>
                                <a href="#">G</a>
                                <a href="#">L</a>
                                <a href="#">I</a>
                                <%--<c:forEach items="${lgs}" var="item">--%>
                                <%--<a href="#">${item}</a>--%>
                                <%--</c:forEach>--%>
                            </div>
                        </td>
                        <td class="param t_method">Method<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component method-component">
                                <input type="text" placeholder="请输入" class="js-method">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_marker1">Marker1<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component marker1-component">
                                <input type="text" placeholder="请输入" class="js-marker1">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_marker2">Marker2<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component marker2-component">
                                <input type="text" placeholder="请输入" class="js-marker2">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_genesNum">Genes
                        </td>
                        <td class="param t_lod">LOD<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component genome-start-component">
                                <select class="js-lod-select">
                                    <option value="<">&lt;</option>
                                    <option value="=">=</option>
                                    <option value=">">&gt;</option>
                                </select>
                                <input type="text" class="js-lod">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_parent1">Parent1<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component parent1-component">
                                <input type="text" placeholder="请输入" class="js-parent1">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_parent2">Parent2<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component parent2-component">
                                <input type="text" placeholder="请输入" class="js-parent2">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_genomeStart">Genome start(cM)<img
                                src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component genome-start-component">
                                <select class="js-genome-start-select">
                                    <option value="<">&lt;</option>
                                    <option value="=">=</option>
                                    <option value=">">&gt;</option>
                                </select>
                                <input type="text" class="js-genome-start">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_genomeEnd">Genome end(cM)<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component genome-start-component">
                                <select class="js-genome-end-select">
                                    <option value="<">&lt;</option>
                                    <option value="=">=</option>
                                    <option value=">">&gt;</option>
                                </select>
                                <input type="text" class="js-genome-end">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                        <td class="param t_author">Reference<img src="${ctxStatic}/images/arrow-drop-down.png">

                            <div class="input-component author-component">
                                <input type="text" placeholder="请输入" class="js-author">

                                <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);"
                                                                                             class="btn-confirm-info">确定</a>
                                </p>
                            </div>
                        </td>
                    </tr>
                    </thead>
                    <tbody class="item-tab">
                    <c:forEach items="${data}" var="item">
                        <tr>
                            <td class="t_id">${item.id}</td>
                            <td class="t_qtlName"><a class="qtlname"
                                                     href="${ctxroot}/search/aboutus?name=${item.qtlName}&version=${item.version}"> ${item.qtlName}</a>
                            </td>
                            <td class="t_trait">${item.trait}</td>
                            <td class="t_type">${item.type}</td>
                            <c:set var="chrStr" value="${item.chr}"></c:set>
                            <c:set var="num" value="${fn:split(chrStr,'Chr')[0]}"></c:set>
                            <c:choose>
                                <c:when test="${fn:startsWith(num, '0')}">
                                    <c:set var="lg" value="${fn:split(num,'0')[0]}"></c:set>
                                </c:when>
                                <c:when test="${fn:startsWith(num, '0') == false}">
                                    <c:set var="lg" value="${num}"></c:set>
                                </c:when>
                            </c:choose>
                            <td class="t_chr"><a
                                    href="${ctxroot}/gene?chr=${item.chr}&version=${item.version}&markerlg=${item.lg}(${lg})&qtl=${item.qtlName}">${item.chr}</a>
                            </td>
                            <td class="t_lg"><a
                                    href="${ctxroot}/gene?chr=${item.chr}&version=${item.version}&markerlg=${item.lg}(${lg})&qtl=${item.qtlName}">${item.lg}</a>
                            </td>
                                <%--<td class="t_version">${item.version}</td>--%>
                            <td class="t_method">${item.method}</td>
                            <td class="t_marker1"><a class="js-pop-marker1" href="javascript:;"
                                                     data-src="${ctxroot}/query/marker?markerName=${item.marker1}">${item.marker1}</a>
                            </td>
                            <td class="t_marker2"><a class="js-pop-marker2" href="javascript:;"
                                                     data-src="${ctxroot}/query/marker?markerName=${item.marker2}">${item.marker2}</a>
                            </td>
                            <td class="t_genesNum"><a class="js-pop-genes" href="javascript:;"
                                                      data-txt="${item.genes}">${item.genesNum}</a></td>
                            <td class="t_lod">${item.lod}</td>
                            <td class="t_parent1">${item.parent1}</td>
                            <td class="t_parent2">${item.parent2}</td>
                            <td class="t_genomeStart">${item.geneStart}</td>
                            <td class="t_genomeEnd">${item.geneEnd}</td>
                            <td class="t_author">
                                <a class="js-author-pop" href="javascript:;"
                                   data-src="${ctxroot}/query/reference?qtlName=${item.qtlName}">${item.author}</a>

                                <div class="author-pop-tab" style="display: none">
                                    <div class="information-title">
                                        <p>REFERENCE</p>
                                        <button class="close-pop">X</button>
                                    </div>
                                    <div class="information-tab">
                                        <table>
                                        </table>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <form id="searchForm" action="${ctxroot}/search/listByResult" method="get">
                    <input id="search1" class="search-type" name="type" type="hidden" value="${type}"/>
                    <input id="search2" name="keywords" type="hidden" value="${keywords}"/>
                    <input id="search3" name="condition" type="hidden" value='${condition}'/>
                    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
                    <input id="version" name="version" type="hidden" value="">
                </form>

                <form id="exportForm" action="${ctxroot}/query/dataExport" method="get">
                    <input id="search7" name="version" type="hidden" value="${version}"/>
                    <input id="search4" class="search-type" name="type" type="hidden" value="${type}"/>
                    <input id="search5" name="keywords" type="hidden" value="${keywords}"/>
                    <input id="search6" name="condition" type="hidden" value='${condition}'/>
                    <input id="search8" name="choices" type="hidden"/>
                </form>
            </div>
            <div class="ktPaginate">
                ${page}
            </div>
        </article>
    </div>
</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<%--弹出框--%>
<div id="mid"></div>
<div class="tab-detail">
    <div class="tab-detail-thead">
        <p><span>genes</span>
            <a href="javascript:void(0)">X</a></p>
    </div>
    <div class="tab-detail-tbody">
        <div class="tab-category">Genes</div>
        <div class="tab-category-list">

        </div>
    </div>
</div>
<div class="links-pop">
    <div class="tab-detail-thead">
        <p>abstract</span>
            <a href="javascript:void(0)">X</a>
        </p>
    </div>
    <div class="links-text">

    </div>
</div>
<div class="js-pop">
    <div class="js-pop-head">
        <p><span class="tname"></span>
            <a href="javascript:void(0)">X</a></p>
    </div>
    <div class="js-pop-body">
        <table>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
<div class="genesInfo" style="display: none">
    <div class="genesInfo-head">
        <p>基因<span class="js-gene-head-name"></span>信息</p>
        <a href="#">x</a>
    </div>
    <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
</div>
<script src="${ctxStatic}/js/layout.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
    /*清除搜索框内容*/
    $(".clear-input").click(function () {
        $(".js-search-text").val("");
        return false
    })
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }
    $(".js-search-text").bind({
        focus: function () {
            $(".clear-input").css({"display": "inline-block"});
        },
        blur: function () {
            if ($(this).val() == "") {
                $(".clear-input").css({"display": "none"});
            } else {
                $(".clear-input").css({"display": "inline-block"});
            }
        }
    });
    /**
     *  初始化时读取 cookie 还原需要显示的列表项
     */
    function setCookie(name, value) {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
        document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
    }
    function getCookie(name) {
        var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
        if (arr = document.cookie.match(reg))
            return unescape(arr[2]);
        else
            return null;
    }

    console.log(getCookie('showedCols'));
    $(function () {
//        var currentPage = getParamsString("pageNo");
//        if(!currentPage && currentPage*1 > 1) {
//
//        }
        /*拖动弹框*/
        $(".js-pop").draggable({ containment: "body" });
        $( ".author-pop-tab" ).draggable({ containment: "body" });
        $( ".tab-detail" ).draggable({ containment: "body" });
        if (getCookie('showedCols')) {
            var cols = getCookie('showedCols').split("-");
            $("input[type='checkbox']").each(function (index, el) {
                console.log($(this).val());
                for (var i = 0; i < cols.length; i++) {
                    if ($(this).val() == cols[i]) {
                        $(this).prop("checked", false);
                        $(".t_" + $(this).val()).hide();
                        break;
                    }
                }
            });

        }


        /* 设置表头 */
        $(".btn-confirm").click(function () {
            var colArr = [];
            $("input[type='checkbox']").each(function (index) {
                // 没有选中，则隐藏列
                if (!$(this).is(":checked")) {
                    $(".t_" + $(this).val()).hide();
                    var colName = $(this).val();
                    colArr.push(colName);
                } else { // 选中，显示列
                    $(".t_" + $(this).val()).show();
                }
            });

            setCookie('showedCols', colArr.join("-"));
        });

        $(".js-search-select").change(function () {
            $(".search-type").val($(this).val());
        });

        // 版本选择
        $(".js-version-select").val(getUrlParam("version"));
        $("#version").val(getUrlParam("version"));
        $(".js-version-select").change(function () {
            $("#version").val($(this).val());
            $("#pageNo").val(1);
            $("#searchForm").submit();
        });
    });

    function page(n, s) {
        if (n) $("#pageNo").val(n);
        if (s) $("#pageSize").val(s);
        $("#searchForm").submit();
        return false;
    }
    function PressPage(e, n, s, p) {
        var c = e.keyCode || e.which;
        if (c == 13) {
            page(n, s);
        }
    }
    function SelectPage(e, n, s, p) {
        if ($("#pageSize").val() != s) {
            page(n, s);
        }
    }
    /*版本号选择*/
    $(".version-num select").change(function () {
        var option = $(".version-num option:selected").text();

        $(".version-num span").text(option);
    })
    /*全选*/
    function checkall() {
        $(".btn-chooseAll").click(function () {
            $("input[type='checkbox']").prop("checked", true);
        });
    }
    checkall()
    $(".set-up").click(function () {
        $(".checkbox-list").show();
        $(".btn-group").show();
        $(".set-up").hide();
    })
    $(".lg").hover(
            function (e) {
                $(".lg-item").show();
                $(this).find("img").finish().animate({"transform": "rotate(180deg)"}, 100)
            },
            function (e) {
                $(".lg-item").hide();
                $(this).find("img").finish().animate({"transform": "rotate(0deg)"}, 100)

            }
    );
    $(".chr").hover(
            function (e) {
                $(".chr-item").show();
                $(this).find("img").finish().animate({"transform": "rotate(180deg)"}, 100)
            },
            function (e) {
                $(".chr-item").hide();
                $(this).find("img").finish().animate({"transform": "rotate(0deg)"}, 100)

            }
    );
    var search_select = '${type}';
    if (search_select) {
        $(".js-search").val(search_select);
    }

    $(".js-search").change(function () {
        $("#search1").val($(this).val());
    });

    $(".js-search-btn").click(function () {
        $("#search2").val($(".js-search-text").val());
        $("#search3").val("{}");
        $("#search6").val("{}");
        $("#searchForm").submit();
    });

    var restoreParamString = function () {
        if ($("#search3").val()) {
            var obj = JSON.parse($("#search3").val());
            obj.qtlName ? $(".js-qtl-name").val(obj.qtlName) : "";
            obj.trait ? $(".js-trait").val(obj.trait) : "";
            obj.type ? $(".js-type").val(obj.type) : "";
            obj.chr ? $(".js-chr").val(obj.chr) : "";
            obj.lg ? $(".js-lg").val(obj.lg) : "";
            obj.version ? $(".js-version").val(obj.version) : "";
            obj.method ? $(".js-method").val(obj.method) : "";
            obj.marker1 ? $(".js-marker1").val(obj.marker1) : "";
            obj.marker2 ? $(".js-marker2").val(obj.marker2) : "";
            if (obj.lod) {
                if (obj.lod.indexOf(">") > -1) {
                    $(".js-lod-select").val(">");
                    $(".js-lod").val(obj.lod.split(">")[1]);
                }
                if (obj.lod.indexOf("=") > -1) {
                    $(".js-lod-select").val("=");
                    $(".js-lod").val(obj.lod.split("=")[1]);
                }
                if (obj.lod.indexOf("<") > -1) {
                    $(".js-lod-select").val("<");
                    $(".js-lod").val(obj.lod.split("<")[1]);
                }
            }
            obj.parent1 ? $(".js-parent1").val(obj.parent1) : "";
            obj.parent2 ? $(".js-parent2").val(obj.parent2) : "";
            if (obj.geneStart) {
                if (obj.geneStart.indexOf(">") > -1) {
                    $(".js-genome-start-select").val(">");
                    $(".js-genome-start").val(obj.geneStart.split(">")[1]);
                }
                if (obj.geneStart.indexOf("=") > -1) {
                    $(".js-genome-start-select").val("=");
                    $(".js-genome-start").val(obj.geneStart.split("=")[1]);
                }
                if (obj.geneStart.indexOf("<") > -1) {
                    $(".js-genome-start-select").val("<");
                    $(".js-genome-start").val(obj.geneStart.split("<")[1]);
                }
            }
            if (obj.geneEnd) {
                if (obj.geneEnd.indexOf(">") > -1) {
                    $(".js-genome-end-select").val(">");
                    $(".js-genome-end").val(obj.geneEnd.split(">")[1]);
                }
                if (obj.geneEnd.indexOf("=") > -1) {
                    $(".js-genome-end-select").val("=");
                    $(".js-genome-end").val(obj.geneEnd.split("=")[1]);
                }
                if (obj.geneEnd.indexOf("<") > -1) {
                    $(".js-genome-end-select").val("<");
                    $(".js-genome-end").val(obj.geneEnd.split("<")[1]);
                }
            }
            obj.ref ? $(".js-ref").val(obj.ref) : "";
            obj.author ? $(".js-author").val(obj.author) : "";
        }
    }();
    $(".chr-item a").click(function () {
        $(".js-chr").val($(this).html());
        $(".js-lg").val("");
        $("#pageNo").val(1);
        $("#search3").val(getParamsString());
        $("#searchForm").submit();
    });
    $(".lg-item a").click(function () {
        $(".js-lg").val($(this).html());
        $(".js-chr").val("");
        $("#pageNo").val(1);
        $("#search3").val(getParamsString());
        $("#searchForm").submit();
    });

    $(".btn-confirm-info").click(function () {
        $("#pageNo").val(1);
        $("#search3").val(getParamsString());
        $("#searchForm").submit();
    });

    $(".btn-cancel").click(function () {
        $(this).parent("p").siblings("input").val("");
        $(this).siblings(".btn-confirm-info").trigger("click");
    });

    $(".btn-export").click(function () {
        var option = $(".version-num option:selected").text();
        $("#search7").val(option);
        $("#search6").val(getParamsString());
        var colArr = [];
        $("input[type='checkbox']").each(function (index) {
            if ($(this).is(":checked")) {
                var colName = $(this).val();
                colArr.push(colName);
            }
        });
        var choices = colArr.join(",");
        console.log(choices)
        $("#search8").val(choices);
        $("#exportForm").submit();
    });

    function getParamsString() {
        var tmp = {};
        $(".js-qtl-name").val() ? tmp.qtlName = $(".js-qtl-name").val() : "";
        $(".js-trait").val() ? tmp.trait = $(".js-trait").val() : "";
        $(".js-type").val() ? tmp.type = $(".js-type").val() : "";
        $(".js-chr").val() ? tmp.chr = $(".js-chr").val() : "";
        $(".js-lg").val() ? tmp.lg = $(".js-lg").val() : "";
        $(".js-version").val() ? tmp.version = $(".js-version").val() : "";
        $(".js-method").val() ? tmp.method = $(".js-method").val() : "";
        $(".js-marker1").val() ? tmp.marker1 = $(".js-marker1").val() : "";
        $('.js-marker2').val() ? tmp.marker2 = $('.js-marker2').val() : "";
        if ($(".js-lod").val()) {
            tmp.lod = $(".js-lod-select").val() + $(".js-lod").val();
        }
        $(".js-parent1").val() ? tmp.parent1 = $(".js-parent1").val() : "";
        $(".js-parent2").val() ? tmp.parent2 = $(".js-parent2").val() : "";
        if ($(".js-genome-start").val()) {
            tmp.geneStart = $(".js-genome-start-select").val() + $(".js-genome-start").val();
        }
        if ($(".js-genome-end").val()) {
            tmp.geneEnd = $(".js-genome-end-select").val() + $(".js-genome-end").val();
        }

        $(".js-ref").val() ? tmp.ref = $(".js-ref").val() : "";
        $(".js-author").val() ? tmp.author = $(".js-author").val() : "";
        return JSON.stringify(tmp);
    }

    $(".js-search-text").keydown(function (event) {
        if (event.keyCode == 13) {
            console.log(event.keyCode)
        }
    })
    $(".nav_ac .icon-right").click(function () {
        $(".nav_ac").hide();
        $("article").css({"width": "895px"})
        $(".table-item").css({"width": "1200px", "margin-bottom": "5px"})
        $("aside").show()
    })
    $(".item-header .icon-right").click(function () {
        $("aside").hide();
        $("article").css({"width": "1125px"});
        $(".table-item").css({"width": "1125px", "margin-bottom": "1px"});
        $(".nav_ac").show();
    })

    $(".js-author-pop").click(function (e) {

        var url = $(this).attr("data-src");
        $("#mid").show()
        $(this).siblings().show();
        var _qtl = $("a.qtlName").html();
        $.ajax({
            url: url,
            type: "get",
            dataType: "json",
            success: function (data) {
                console.log(data)
                var tbody = "";
                tbody += "<tbody>"
                tbody += "   <tr>"
                tbody += "       <td>Title:</td>"
                tbody += "       <td>" + data.title + "</td>"
                tbody += "   </tr>"
                tbody += "   <tr>"
                tbody += "       <td>Authors:</td>"
                tbody += "       <td>" + data.authors + "</td>"
                tbody += "   </tr>"
                tbody += "   <tr>"
                tbody += "       <td>Source:</td>"
                tbody += "       <td>" + data.source + "</td>"
                tbody += "   </tr>"
                tbody += "   <tr>"
                tbody += "       <td>Abstract:</td>"
                tbody += "       <td>"
                tbody += "               <p>" + data.abs + "</a>"
                tbody += "       </td>"
                tbody += "   </tr>"
                tbody += "</tbody>"
                $(".information-tab table").html(tbody);
                /*data.abs*/
                $(".abstract").click(function (e) {
                    e.preventDefault();
                    $("#mid").show();
                    $(".links-pop").show();
                    var txt = $(this).attr("data-txt");
                    console.log(txt + "----");
                    $(".links-text").html("<p>" + txt + "</p>")


                })
            }
        });

        var xx = e.pageX;
        var yy = e.pageY

    })
    $(".close-pop").click(function (e) {
        $(".author-pop-tab").hide();
        $("#mid").hide();
    })


    /*表头*/
    $(".btn-confirm").click(function () {
        var str = "";
        $("input[type='checkbox']").each(function (index) {
            if (!$(this).is(":checked")) {
                $(".t_" + $(this).val()).hide();
            } else {
                $(".t_" + $(this).val()).show();
                str += $(this).val() + "-";
            }
        })

        setCookie('showedCols', str);
    })
    function pop(id, name) {
        $(id).click(function (e) {
            e.preventDefault();
            $("#mid").show();
            $(".js-pop").show();
            $(".tname").text(name)
            var url = $(this).attr("data-src");
            $.ajax({
                url: url,
                type: "get",
                dataType: "json",
                success: function (data) {
                    console.log(data);
                    var pop = "";
                    pop += "<tr>"
                    pop += "  <td>Name</td>"
                    pop += "  <td>" + data.name + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    pop += "  <td>Type</td>"
                    pop += "  <td>" + data.type + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    pop += "  <td>LG(Chr)</td>"
                    pop += "  <td>" + data.lg + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    pop += "  <td>Position</td>"
                    pop += "  <td>" + data.position + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    pop += "  <td>Amplification Info</td>"
                    pop += "  <td>" + data.amplificationInfo + "</td>"
                    pop += "</tr>"
                    pop += "  <td>Providers</td>"
                    pop += "  <td>" + data.provider + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    pop += "  <td>References</td>"
                    pop += "  <td>" + data.refference + "</td>"
                    pop += "</tr>"
                    pop += "<tr>"
                    $(".js-pop-body tbody").html(pop)
                }
            })
        })
    }
    pop(".js-pop-marker1", "marker1")
    pop(".js-pop-marker2", "marker2")
    /*genes 弹框*/
    $(".js-pop-genes").click(function (e) {
        e.preventDefault();
        $("#mid").show();
        $(".tab-detail").show();
        $(".tab-category-list").html("");
        var text = $(this).attr("data-txt");
        var t = text.split(",")

        for (var i = 0; i < t.length; i++) {
            var span = "";
            span += "<span><a class='js-gene-info' data-gene-name='" + t[i] + "' href='javascript:void(0);'>" + t[i] + "</a></span>"
            $(".tab-category-list").append(span)
        }
    });

    $("body").on("click", ".js-gene-info", function (e) {
        var version = getUrlParam("version");
        var geneName = $(this).attr("data-gene-name");
        $(".js-gene-head-name").html(geneName);
        $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName=" + geneName + "&version=" + version);
        e.preventDefault();
//        console.log($(this).html())
        $(".genesInfo").show();

    });
    $(".genesInfo a").click(function (e) {
        e.preventDefault();
        $(".genesInfo").hide();
    })
    $(".js-search-text").on("focus", function() {
       $(this).addClass("isFocus");
    });
    $(".js-search-text").on("blur", function() {
        $(this).removeClass("isFocus");
    });
    $(".input-component input").on("focus", function() {
        $(this).addClass("isFocus");
    });
    $(".input-component input").on("blur", function() {
        $(this).removeClass("isFocus");
    });
    $(document).keyup(function(event){
        var _searchDom = $(".js-search-text");
        var _conditionDoms = $(".input-component input");
        var e=e||event
        var keycode = e.which;
        if(keycode==13){
            if(_searchDom.hasClass("isFocus")) {
                $(".js-search-btn").trigger("click");
            }
            $.each(_conditionDoms, function(idx, item) {
                if($(item).hasClass("isFocus") ) {
                    $(this).siblings("p").find(".btn-confirm-info").trigger("click");
                }
            });

        }
    });

</script>
</body>
</html>