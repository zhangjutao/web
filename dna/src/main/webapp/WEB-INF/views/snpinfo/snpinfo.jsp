<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <title>群体信息</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/snpinfo.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script>
        var CTXROOT = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/jquery-ui.js"></script>
    <script src="${ctxStatic}/js/snpinfo.js"></script>
</head>
<body>
<dna:dna-header />
    <div class="container" style="background: #fff;position:relative;">
        <div class="snpTop">
            GlyS020044582527
        </div>
        <div id="snpSearch">
            <p class="tipTil">SNP/INDEL ID:</p>
            <p class="searchBox">
                <input type="text" placeholder="请输入你要进行搜索的内容进行搜索"/>
            </p>
            <p class="searBtn">
                搜索
            </p>
        </div>
        <div id="trInfos">
            <table cellspacing="0" cellpadding="0" >
                <tr>
                    <td class="trWidth">SNP ID:</td>
                    <td class="trWidth2">${snp.id}</td>
                    <td class="trWidth">Consequence type:</td>
                    <td class="trWidth2">${snp.consequencetype}</td>
                </tr>
                <tr>
                    <td class="trWidth">Chr:</td>
                    <td class="trWidth2">${snp.chr}</td>
                    <td class="trWidth">Position:</td>
                    <td class="trWidth2">${snp.pos}</td>
                </tr>
                <tr>
                    <td class="trWidth">Reference allele:</td>
                    <td class="trWidth2">${snp.ref}</td>
                    <td class="trWidth">Major allele:</td>
                    <td class="trWidth2">${snp.majorallen}</td>
                </tr>
                <tr>
                    <td class="trWidth">Minor allele:</td>
                    <td class="trWidth2">${snp.minorallen}</td>
                    <td class="trWidth">Frequence of major allele:</td>
                    <td class="trWidth2">${frequence}%</td>
                </tr>
            </table>
        </div>

    </div>

</body>
</html>
