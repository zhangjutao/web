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
    <title>SNP INDEL database</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/primer3.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/primer3-input.css">--%>
    <link rel="stylesheet" href="${ctxStatic}/css/primer3-output.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/addTags.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">--%>
    <link href="https://cdn.bootcss.com/normalize/7.0.0/normalize.min.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script>
        var CTXROOT = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/jquery-ui.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <style>

    </style>
</head>
<body>
<iqgs:iqgs-header/>
<!--header-->
<div class="container primer3-content js-nav-ac">
    <%--<%@ include file="/WEB-INF/views/include/sidebar.jsp" %>--%>
    <div class="peimer3-main">
        <div class="primer3-result-title">
            <div class="result-icon">
                <%--<img src="${ctxroot}/static/images/primer3/resultIcon.png" style="vertical-align:text-bottom"/>--%>
                <div class="result-title-icon1"></div>
                <div class="blue-result">结果</div>
                <%--<img src="${ctxroot}/static/images/primer3/resultIcon2.png" style="vertical-align:text-bottom"/>--%>
                <div class="result-title-icon2"></div>
                <div class="primer3-conditions">搜索条件：<span></span></div>
            </div>
            <div class="clear-fix"></div>
        </div>
        <div class="primer3-table">
            <table><%--cellspacing="50%" cellpadding="80"--%>
                <thead>
                <tr>
                    <td></td>
                    <td>Position</td>
                    <td>Length</td>
                    <td>Tm</td>
                    <td>GC%</td>
                    <td>any(Self Cornplementarity)</td>
                    <td>3'(Self Complementarity)</td>
                    <td>Hairpin</td>
                    <td>Sequence(5'-3')</td>
                    <td>引物检测</td>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Primer F</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>ddddddddddddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td class="primer3-link" rowspan="2"><a href="www.baidu.com">可跳转的网址地址</a></td>
                </tr>
                <tr>
                    <td>Primer R</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddddddddddddddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                    <td>dddddd</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="primer3-designed">
            <div class="primer3-designed-title">
                Primer designed in sequence
            </div>
            <div class="primer3-designed-content">
                <div class="primer3-designed-content-left">
                    <div class="primer3-designed-item">
                        Primer F : <span>ATATATATATATATATATATAT</span>
                    </div>
                    <div class="primer3-designed-item">
                        Sequence Size : <span>111110</span>
                    </div>

                </div>
                <div class="primer3-designed-content-right">
                    <div class="primer3-designed-item">
                        Primer R : <span>ATATATATATATATATATATAT</span>
                    </div>
                    <div class="primer3-designed-item">
                        lnclude Region Sequence Size : <span>2222220</span>
                    </div>
                </div>
                <div class="clear-fix"></div>
            </div>
        </div>
        <div class="sequence-list">
            <div class="sequence-list-cover">
                <div class="sequence-list-cover-left"></div>
                <div class="sequence-list-cover-right"></div>
                <div class="clear-fix"></div>
            </div>
            <ul>
                <li class="sequence-list-li-one">
                    <div class="line-number">1</div>
                    <div class="sequence-content">
                        ATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATTATATATATATATAT
                    </div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">2</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">3</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">4</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--footer-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
    var ctxRoot = '${ctxroot}';
    var templet = {
        primer3Tr: '<tr>\n' +
            '<td>Primer F</td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td class="primer3-link" rowspan="2"><a href="www.baidu.com">可跳转的网址地址</a></td>\n' +
            '</tr>\n' +
            '<tr>\n' +
            '<td>Primer R</td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '<td></td>\n' +
            '</tr>',
    }

    $(function () {
        var primer3List = localStorage.getItem("primer3List");
        var param=localStorage.getItem('param');
        primer3List = JSON.parse(primer3List);
        param=JSON.parse(param);
        console.log(primer3List);
        console.log(param);

        /*if(primer3List.length>0){
            for(var i=0;i<primer3List.length;i++){
                var primer3=primer3List[i];
                console.log(primer3.type)
                if(primer3.group==1){

                }
                if(primer3.type=='primerF'){

                }else{

                }
                var trs=$('.primer3-table tbody>tr');
                for(var j=0;j<trs.length;j++){
                    var tr=trs.get(j).css('background','red');
                    $(tr).append('<td>'+primer3.position+'</td>')
                }
            }
        }*/

    })


</script>
<%--<script src="${ctxStatic}/js/primer3.js"></script>--%>
</body>
</html>