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
    <%--<script src="${ctxStatic}/js/highcharts-code.js"></script>--%>
    <script src="${ctxStatic}/js/highcharts/highcharts.js"></script>
    <%--<script src="${ctxStatic}/js/highcharts/highcharts-more.js"></script>--%>
    <%--<script src="${ctxStatic}/js/highcharts/highcharts-zh_CN.js"></script>--%>
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
        <%--<hr style="border:1px solid #E4E4E4;">--%>
        <p style="width:1200px;height:2px;border-top:1px solid #E4E4E4;margin-top:16px;"></p>
        <div id="pieShow">
        </div>
        <div id="snpinfoTable">
            <div class="changeTab">
                <p class="changeTagColor major">Major Allele</p>
                <p class="minor">Minor Allele</p>
            </div>
            <table border="1" cellspacing="0" cellpadding="5" style="overflow: scroll; min-height:100px;">
                <thead style="overflow-x: scroll;">
                <tr style="background: #F5F8FF;">
                    <th class="param cultivarT">品种名

                    </th>
                    <th class="param cultivarT">GeneType

                    </th>

                    <th class="param genoTypeT">GenoType

                    </th>
                    <th class="param speciesT">物种

                    </th>
                    <th class="param localityT">位置

                    </th>
                    <th class="param sampleNameT">样品名

                    </th>
                    <th class="param weightPer100seedsT">百粒重

                    </th>
                    <th class="param proteinT">蛋白质含量

                    </th>
                    <th class="param oilT">含油量

                    </th>
                    <th class="param maturityDateT">熟期

                    </th>
                    <th class="param heightT">株高

                    </th>
                    <th class="param seedCoatColorT">种皮色

                    </th>
                    <th class="param hilumColorT">种脐色

                    </th>
                    <th class="param cotyledonColorT">子叶色

                    </th>
                    <th class="param flowerColorT">花色

                    </th>
                    <th class="param podColorT">荚色

                    </th>
                    <th class="param pubescenceColorT">茸毛色

                    </th>
                    <th class="param yieldT">产量

                    </th>
                    <th class="param upperLeafletLengthT">顶端小叶长度

                    </th>
                    <th class="param linoleicT">亚油酸

                    </th>
                    <th class="param linolenicT">亚麻酸

                    </th>
                    <th class="param oleicT">油酸

                    </th>
                    <th class="param palmiticT">软脂酸

                    </th>
                    <th class="param stearicT">硬脂酸

                    </th>
                </tr>
                </thead>
                <tbody>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                    <td class="param">

                    </td>
                </tbody>
            </table>
        </div>

    </div>

</body>
<script>
    $(function (){
        var changeParam;  // major 和 minor 页面切换
        var populVal;   // 点击每个群体信息值
        var ctxRoot = '${ctxroot}';
        var AA = "${result.RefAndRefPercent}";
        var TT = "${result.totalAltAndAltPercent}";
        var AT = "${result.totalRefAndAltPercent}";
        var id="${snp.id}";
        var major = "${snp.majorallen}";
        var mijor = "${snp.minorallen}";
        console.log(AA)
        console.log(TT)
        console.log(AT)
        // 花饼图
        function drawPie(ref,alt,refAlt){
            $('#pieShow').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: 'GenoType'
                },
                plotOptions: {
                    series: {
                        dataLabels: {
                            enabled: true,
                            color: '#98BEFF',
                            borderWidth: 0,
                            style: {
                                fontWeight:"50",
                                fontSize: '14',
                                fontFamily:"Microsoft Yahei",
                                strokeWidth:"0px"
                            }
                        }},
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                            style: {
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                            }
                        }
                    }
                },
                series: [{
                    type: 'pie',
                    name: 'GenoType 占比',
                    data: [
                        ['AA',ref*100],
                        ['TT', alt*100],
                        ['AT', refAlt*100]
                    ]
                }]
            })
        }
        drawPie(AA,TT,AT);
        // 选择群体信息
        $(".moveOnPop").mouseover(function (){
             $(this).find("div.popNames").show();
        }).mouseleave(function (){
            $(this).find("div.popNames").hide();
        })
        //点击每个群体
        $(".popNames li").click(function (){
            populVal = $(this).text();
            console.log(populVal);
            $(this).parent().parent().hide();
        })
        // 获取表格数据
        function getSnpData (data){
            debugger;
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/changeByProportion",
                data:data,
                success:function (result){

                    console.log(result);
                },
                error:function (error){
                    console.log(error);
                }
            })
        }
        $(".minor").click(function (){
            var data = {
                snpId:id,
                changeParam:mijor
            };
            getSnpData(data);
        });
    })
        // tag 切换
        $(".changeTab p").click(function (){
            if(!$(this).hasClass("changeTagColor")){
                $(this).addClass("changeTagColor");
                var others = $(this).siblings()[0];
                if($(others).hasClass("changeTagColor")){
                    $(others).removeClass("changeTagColor");
                }
            }else {
                var others = $(this).siblings()[0];
                if($(others).hasClass("changeTagColor")){
                    $(others).removeClass("changeTagColor");
                }
            }
        })

</script>
</html>
