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
    <title>IQGS index</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="stylesheet" href="${ctxStatic}/css/newAdd.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>

<body>

<iqgs:iqgs-header />

<div class="container">
    <div class="banner">
        <div class="plant-pic">
            <img src="${ctxStatic}/images/banner-soybean.png">
        </div>
        <div class=" tabs">
            <ul id="myTabs"  class="searchNav nav-tabs">
                <li class="active geneIdName"><a class="" >Search By Gene ID/Name</a></li>
                <li class=" geneFunction"><a class="" >Search By Gene Function</a></li>
                <li class="region" ><a class="">Search By Region</a></li>
                <li class="qtl" ><a class="">Search By QTL</a></li>
            </ul>
            <div id="myTabContent" class="tab-content">
                <div id="GeneIdName" class="tab-pane tab-pane-ac">
                    <p class="search-title">Search By Gene ID/Name</p>
                    <label>
                        <input class="search-input" id="key_name" type="text" name="search" placeholder="输入您要查找的关键字">
                        <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                        <button id="btn_name" class="search-btn" ><img src="${ctxStatic}/images/search.png">搜索</button>
                    </label>
                    <p class="search-tips">示例: <a target="_blank" href="${ctxroot}/iqgs/search/list?keyword=Glyma.01G004900&searchType=1">Glyma.01G004900</a><b>;</b> <a target="_blank" href="${ctxroot}/iqgs/search/list?keyword=Glyma.01G004900&searchType=1"> LOC778160</a></p>
                </div>
                <div id="GeneFunction" class="tab-pane">
                    <p class="search-title">Search By Gene Function</p>
                    <label>
                        <input id="key_func" class="search-input" type="text" name="search" placeholder="输入您要查找的关键字">
                        <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                        <button id="btn_func" class="search-btn"><img src="${ctxStatic}/images/search.png">搜索</button>
                    </label>
                    <p class="search-tips">示例: <a target="_blank" href="${ctxroot}/iqgs/search/list?keyword=Glyma.01G004900&searchType=1">transcription factor MYBJ6</a></p>
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
                        <input onkeyup="this.value=this.value.replace(/\D/g,'')" min="0" id="rg_begin" class="region-input region-s" type="number" name="search" placeholder="输入您要查找的数值"><span class="s-line"></span>
                        <input onkeyup="this.value=this.value.replace(/\D/g,'')" min="0"  id="rg_end" class="region-input region-e" type="number" name="search" placeholder="输入您要查找的数值">
                        <span class="clear-input" style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                        <button id="btn_range" class="btn region-search"><img src="${ctxStatic}/images/search.png">搜索</button>
                    </div>
                    <p class="search-region-tips">示例: Chr01,0bp-10000bp</p>
                </div>

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
                    <p class="search-tips">示例: <a target="_blank" href="${ctxroot}/iqgs/search/list?keyword=Glyma.01G004900&searchType=1"> Seed N at R5 1-1</a></p>
                    <div class="sureBtn">
                        <p>确定</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="advancedSearch">
        <div class="advanceBtn">
            <p>Advanced search</p>
            <img src="${ctxStatic}/images/downtou.png" alt="down" >
        </div>
    </div>
    <div class="database-link">
        <div class="qlt-link">
            <a target="_blank" href="${ctxroot}/search/index">
                <div class="line-h">
                    <p>大豆QTL数据库</p>
                </div>
                <div class="link-b">
                    <img src="${ctxStatic}/images/qtl.png">
                </div>
            </a>
        </div>
        <div class="mrna-link">
            <a  target="_blank" href="${ctxroot}/mrna/index">
                <div class="line-h">
                    <p>大豆基因表达量数据库</p>
                </div>
                <div class="link-b">
                    <img src="${ctxStatic}/images/mrna.png">
                </div>
            </a>
        </div>
        <div class="snp-link">
            <a target="_blank"  href="${ctxroot}/dna/index">
                <div class="line-h">
                    <p>大豆SNP/INDEL数据库</p>
                </div>
                <div class="link-b">
                    <img src="${ctxStatic}/images/SNP_INDEL.png">
                </div>
            </a>
        </div>
    </div>
    <div class="database-overview ">
        <div class="explain-title">
            <img src="${ctxStatic}/images/explain.png">数据库概况
        </div>
        <div class="explain-text">
            大豆整合数据库是大豆基因组学、转录组学及相关数据资源的综合数据库，为多层面的生命科学研究提供了基础数据查询和数据可视化服务。本数据库整合了大豆基因序列、基因结构、基因家族、同源基因、QTL、基因表达、基因变异、共表达网络、调控网络等信息。组学数据丰富，基因信息全面，能辅助科研人员便捷地查询目标基因的整合信息、精准地预测候选基因。
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<script type="text/javascript">
    window.DOMAIN = "${ctxroot}/iqgs";
</script>

<script src="${ctxStatic}/js/mock/mock.js"></script>
<script src="${ctxStatic}/js/iqgs.js"></script>
<script src="${ctxStatic}/js/newAddNeed.js"></script>
</body>
</html>