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
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>

</head>

<body>

<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
    <div class="detail-name">
        <p>${genId}</p>
    </div>
    <div class="detail-content">
        <iqgs:iqgs-nav focus="9" genId="${genId}"></iqgs:iqgs-nav>
        <div class="explains">
            <div class="explain-list" id="basic">
                <div class="page-tables" style="display: block;">
                        <div class="box-shadow resulting">
                            <div class="item-header">
                                <div class="icon-left">
                                    <img src="${ctxStatic}/images/result.png">结果
                                    <span>搜索条件: <span class="js-search-desc"></span></span>
                                    <i>></i>
                                </div>
                                <div class="icon-right">
                                    <p class="page-num-tab-snp">共<span class="total-page-count-snp">0</span>条结果</p>
                                    <p class="page-num-tab-indel" style="display: none;">共<span class="total-page-count-indel">0</span>条结果</p>
                                </div>
                            </div>
                            <div class="tab-item">
                                <ul class="item">
                                    <li class="item-ac">SNPs</li>
                                    <li class="">INDELs</li>
                                </ul>
                                <div class="tab">
                                    <div class="tab-txt tab-txt-ac js-snp-tab">
                                        <div id="mask-test">
                                            <div class="genes-tab tab-txt-snps" style="height: auto;">
                                                <table class="js-snp-table">
                                                    <thead>
                                                    <tr>
                                                        <td class="t_snpid">SNP ID</td>
                                                        <td class="param t_consequenceType">Consequence Type</td>
                                                        <td class="param t_snpchromosome">Chromosome</td>
                                                        <td class="param t_position">Position</td>
                                                        <td class="param t_snpreference">Reference</td>
                                                        <td class="param t_majorAllele">Major Allele</td>
                                                        <td class="param t_minorAllele">Minor Allele</td>
                                                        <td class="param t_fmajorAllele">
                                                            <select class="f-ma">
                                                                <option value="major">Frequence of Major Allele</option>
                                                                <option value="minor">Frequence of Minor Allele</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    </thead>
                                                    <tbody id="tableBody">
                                                    <%--<tr>--%>
                                                    <%--<td class="t_snpid">sf1111111</td>--%>
                                                    <%--<td class="t_consequenceType"><p class="js-tipes-show">"Okamoto S</p></td>--%>
                                                    <%--<td class="t_snpchromosome"><p>stem internode</p></td>--%>
                                                    <%--<td class="t_position"><p class="js-tipes-show"></p></td>--%>
                                                    <%--<td class="t_snpreference"><p>Enrei</p></td>--%>
                                                    <%--<td class="t_majorAllele"><p>12346873</p></td>--%>
                                                    <%--<td class="t_minorAllele"><p>数值</p></td>--%>
                                                    <%--<td class="t_fmajorAllele"><p>Enrei</p></td>--%>
                                                    <%--<td class="t_fmajorAllelein"><p>12346873</p></td>--%>
                                                    <%--</tr>--%>

                                                    </tbody>
                                                </table>
                                                <form id="exportForm" action="" method="get">
                                                    <input id="search1" class="search-type" name="type" type="hidden">
                                                    <input id="search2" name="keywords" type="hidden">
                                                    <input id="search3" name="condition" type="hidden">
                                                    <input id="search4" name="choices" type="hidden">
                                                </form>
                                            </div>
                                        </div>
                                        <div class="checkbox-item-tab" id="snp-paginate">
                                            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                                        </div>
                                    </div>
                                    <div class="tab-txt js-indel-tab">
                                        <div id="mask-test2">
                                            <div class="tab-txt-indels genes-tab">
                                                <table class="js-indel-table">
                                                    <thead>
                                                    <tr>
                                                        <td class="param t_indels">INDEL ID</td>
                                                        <td class="param t_consequenceType">Consequence Type</td>
                                                        <td class="param t_snpchromosome">Chromosome</td>
                                                        <td class="param t_position">Position</td>
                                                        <td class="param t_snpreference">Reference</td>
                                                        <td class="param t_majorAllele">Major Allele</td>
                                                        <td class="param t_minorAllele">Minor Allele</td>
                                                        <td class="param t_fmajorAllele">
                                                            <select class="f-ma">
                                                                <option value="major">Frequence of Major Allele</option>
                                                                <option value="minor">Frequence of Minor Allele</option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <div class="checkbox-item-tab" id="indel-paginate">
                                            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </div>
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<!--footer-->
<script>
    var iGENE = "${genId}";
    var CTXROOT = '${ctxroot}';
    var ctxStatic='${ctxStatic}'
</script>
<script src="${ctxStatic}/js/iqgs-table.js"></script>
<%--<script>--%>
    <%--$(".item li").each(function(i){--%>
        <%--$(this).click(function(){--%>
            <%--$(this).addClass("item-ac").siblings().removeClass("item-ac");--%>
            <%--$(".tab > div").eq(i).show().siblings().hide();--%>
        <%--})--%>
    <%--})--%>
<%--</script>--%>
</body>
</html>