<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <%--<script src="https://cdn.hcharts.cn/jquery/jquery-2.1.1.min.js"></script>--%>
    <script src="http://cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/highcharts-more.js"></script>
    <title>QTL genesInfo</title>
    <style>
        body,div,h1,p,a,table,tbody,tr,td,img{
            padding: 0;
            margin: 0;
        }
        body{
            background: #fff;
            color: #4f4f4f;
        }
        a{
            text-decoration: none;
            color: #5c8ce6;
        }
        table{
            border-collapse: collapse;
            text-align: left;
        }
        .genesInfo{
            margin: 0 auto;
            background-color: #FFFFFF;
        }
        .genesInfo-head{
            background-color: #F7F7F9;
            height: 35px;
            line-height: 35px;
            padding: 0 10px;;
        }
        .genesInfo-head p{
            float: left;
            font-size: 18px;
        }
        .genesInfo-head a{
            float: right;
            color:#BFBFBF;
            font-size: 25px;;
        }
        .genesInfo-contant{
            padding-top: 20px;;
        }
        .genesInfo-contant>div{
            border-bottom: 1px solid #E6E6E6;
            padding-bottom: 25px;
            margin-bottom: 35px;
        }
        .basic-information .basic-information-tab{
            height: 468px;
            overflow: auto;
        }
        .basic-information p,.gens-structure p,.domain-structure p,.go-annotation p{
            font-weight: bolder;
            font-size: 18px;
            padding-bottom: 15px ;
        }
        .basic-information table{
            padding-bottom: 20px;
        }
        .basic-information table tr td,  .go-annotation table tr td{
            border: 1px solid #e6e6e6;
            padding: 8px 14px;;
        }
        .basic-information table tr td:nth-child(1){
            padding-right: 40px;
            white-space: nowrap;;
        }
        .domain-structure tbody tr td{
            border:1px solid #e6e6e6;
            padding: 5px 10px;
        }
        .basic-information tbody tr:nth-child(odd){
            background-color: #F9F9F9;
        }
        .domain-structure tbody tr:nth-child(1){
            background-color: #F7F7F7
        }
        .domain-structure tbody tr:nth-child(2){
            background-color: #F9F9F9
        }
        .go-annotation tbody tr td:nth-child(2){
            width: 200px;
        }
        .go-annotation-tab{

        }
        .basic-information .basic-information-tab::-webkit-scrollbar,.go-annotation::-webkit-scrollbar{
            width: 10px;
            height: 10px;
            background-color: #fff;
        }
        /*定义滚动条轨道 内阴影+圆角*/
        .basic-information .basic-information-tab::-webkit-scrollbar-track,.go-annotation::-webkit-scrollbar-track{
            -webkit-box-shadow:  0 0 2px rgba(0,0,0,0.3);
            border-radius: 5px;
            background-color: #F5F5F5;
        }
        /*定义滑块 内阴影+圆角*/
        .basic-information .basic-information-tab::-webkit-scrollbar-thumb,.go-annotation::-webkit-scrollbar-thumb {

            -webkit-box-shadow:  0 0 2px rgba(0,0,0,.3);
            border-radius: 5px;
            background-color: #ccc;
        }

        .ga-mask {
            position: absolute;
            background: rgba(255,255,255,0.6);
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
        }
        .ga-mask > div {
            text-align: center;
            margin-top: 30%;
        }
    </style>
</head>
<body id="mask-test">
    <%--<div class="genesInfo">--%>
        <%--<div class="genesInfo-head">--%>
            <%--<p>基因LOC_Os01g01010</p>--%>
            <%--<a href="#">x</a>--%>
        <%--</div>--%>
        <%--<div class="genesInfo-body">--%>
        <div class="genesInfo-contant">
            <div class="basic-information">
                <div class="basic-information-tab">
                    <p>Basic Information</p>
                    <table class="js-RapeExondbInfo">
                    <tbody>
                        <tr>
                            <td>ChromID</td>
                            <td class="chromId"></td>
                        </tr>
                        <tr>
                            <td>Gene Name</td>
                            <td class="geneName"></td>
                        </tr>
                        <tr>
                            <td>Orientation</td>
                            <td class="oreitation"></td>
                        </tr>
                        <tr>
                            <td>Locus Start</td>
                            <td class="locusStart"></td>
                        </tr>
                        <tr>
                            <td>Locus End</td>
                            <td class="locusEnd"></td>
                        </tr>
                        <tr>
                            <td>Exon Number</td>
                            <td class="exonNumber"></td>
                        </tr>
                        <tr>
                            <td>Cds Length</td>
                            <td class="cdsLength"></td>
                        </tr>
                        <tr>
                            <td>Protein Length</td>
                            <td class="proteinLength"></td>
                        </tr>
                        <tr>
                            <td>Exon Region</td>
                            <td class="exonRegion">
                                <%--<span>1-366,</span><span>425-714,</span><span>1455-1553,</span>--%>
                                <%--<span>1-366,</span><span>425-714,</span><span>1455-1553,</span>--%>
                                <%--<span>1-366,</span><span>425-714,</span><span>1455-1553,</span>--%>
                            </td>
                        </tr>
                        <tr>
                            <td>Exon Position</td>
                            <td class="exonPosition">
                                <%--<span>2903,</span><span>3268</span><span>3354,</span>--%>
                                <%--<span>3616,</span><span>4352,</span><span>4555,</span>--%>
                                <%--<span>2903,</span><span>3268</span><span>3354,</span>--%>
                                <%--<span>3616,</span><span>4352,</span><span>4555,</span>--%>
                                <%--<span>2903,</span><span>3268</span><span>3354,</span>--%>
                                <%--<span>3616,</span><span>4352,</span><span>4555,</span>--%>
                            </td>
                        </tr>
                        <tr>
                            <td>Gene Group</td>
                            <td class="geneGroup">

                            </td>
                        </tr>
                    </tbody>
                </table>
                </div>
            </div>
            <div class="gens-structure">
                <p>Genes Structure</p>
                <div id="geneStructureGraph"></div>
            </div>
            <div class="domain-structure">
                <p>Domain Structure</p>
                <table class="js-returnPfamdbDT">
                    <tbody>
                        <tr>
                            <td>Protein Domain</td><td>Desc</td><td>Start</td><td>End</td><td>E_value</td>
                        </tr>
                        <tr>
                            <td class="PFamId"></td><td class="anno"></td><td class="start"></td><td class="end"></td><td class="evalue"></td>
                        </tr>
                    </tbody>
                </table>
                <div id="domainStructureGraph"></div>
            </div>
            <div class="go-annotation">
                <p>Go Annotation</p>
                <div class="go-annotation-tab"></div>
                <table class="js-go-anno">
                    <tbody>
                        <%--<tr><td><a href="#">GO:0030234</a></td><td></td></tr>--%>
                    </tbody>
                </table>
            </div>

            <div class="go-annotation">
                <p>IPR Annotation</p>
                <div class="go-annotation-tab"></div>
                <table class="js-ipr-anno">
                    <tbody>
                    <%--<tr><td><a href="#">GO:0030234</a></td><td></td></tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
        <%--</div>--%>
    <%--</div>--%>

    <script>

        function loadMask (el) {
            $(el).css({"position": "relative"});
            var _mask = $('<div class="ga-mask"><div>数据加载中...</div></div>');
            $(el).append(_mask);
        }

        function maskClose() {
            $(".ga-mask").remove();
        }

        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg);  //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }

        function renderGeneInfo(response) {

            if (response.RapeExondbInfo.length === 0) {
                alert("无数据");
            }

            var string = response.RapeExondbInfo[0].ExonRegion;
            var array1 = string.split('-').join(',').split(','),
                    array2 = response.returnPfamdbDT;
            if (array1[array1.length - 1] == '') {
                array1.pop();
            }
            var series1 = [],
                    series2 = {
                        name: 'title',
                        data: []
                    };
            for (var i = 0; i < array1.length / 2; i++) {
                series1[i] = {
                    data: [
                        [Number(array1[2 * i]), Number(array1[2 * i + 1])]
                    ],
                    color: '#7cb5ec'
                }
            }
            for (var i = 0; i < array2.length; i++) {
                series2.data[i] = [array2[i].Start, array2[i].End];
                series2.color = '#7cb5ec';
            }
            series2.data.push([-10, -10]);
            drawGeneStructureGraph(series1);
            drawDomainStructureGraph(series2);
            window.onresize = function () {
                if (document.getElementById('geneStructureGraph') != null) {
                    drawGeneStructureGraph(series1);
                    drawDomainStructureGraph(series2);
                }
            }
            // $scope.isGeneInfoLoaded = true;

            function drawGeneStructureGraph(series) {
                var width = 590,
                        height = 100;
                Highcharts.chart('geneStructureGraph', {
                    // 图表类型，以及反转等信息
                    chart: {
                        type: 'columnrange',
                        inverted: true,
                        plotBorderColor: '#FFFFFF',
                        width: width,
                        height: height // 图形的宽度和高度
                    },
                    // 图表标题
                    title: {
                        text: ''
                    },
                    // x轴设置
                    xAxis: {
                        labels: {
                            enabled: false // 不显示标签
                        },
                        tickColor: '#FFFFFF', // 刻度为白色，即不显示刻度
                        lineColor: '#FFFFFF',
                    },
                    // y轴设置
                    yAxis: {
                        title: '', // 无标题
                        lineColor: '#C0D0E0',
                        lineWidth: '1',
                        gridLineColor: '#FFFFFF', // 不显示网格线
                        min: 0, // y轴从0开始，而不是负数
                        tickPixelInterval: 60, // 50px为一格
                        minorTickInterval: 'auto',
                        minorGridLineColor: '#FFFFFF',
                        minorTickLength: 5,
                        minorTickWidth: 1,
                        minorTickColor: '#C0D0E0',
                        minorTickPosition: 'inside',
                        tickColor: '#C0D0E0', // 刻度的颜色
                        tickWidth: 1, // 刻度的宽度
                        tickPosition: 'inside', // 刻度的位置（default outside）
                        className: 'highcharts-axis-line',
                        labels: { // 设置坐标轴上的label
                            y: -10,
                            reserveSpace: true,
                        },
                        opposite: true,
                        id: 'yAxis' // 设置ID，用于后面操作此轴
                    },
                    // 提示框
                    tooltip: {
                        // enabled: false                  // 不显示提示框
                        positioner: function (w, h, point) {
                            return {
                                x: point.plotX,
                                y: 60
                            } // 提示框的显示位置
                        },
                        formatter: function () { // 提示框显示的内容
                            return this.point.low + '-' + this.point.high;
                        }
                    },
                    // 版权信息
                    credits: {
                        enabled: false,
                        text: 'Wutbiolab', // 版权信息的内容
                        href: 'http://www.wutbiolab.cn/index' // 版权信息的链接
                    },
                    plotOptions: {
                        columnrange: {
                            dataLabels: {
                                enabled: false,
                                formatter: function () {
                                    return this.value;
                                }
                            }
                        }
                    },

                    legend: {
                        enabled: false
                    },

                    series: series
                }, function (chart) {
                    $('#geneStructureGraph g rect').attr('x', height - 70).attr('width',
                            '10'); // 图中元素位置固定为同一水平线
                });
            }

            function drawDomainStructureGraph(series) {
                var width = 590,
                        height = (series.data.length - 1) * 30 + 80; // 高度跟随数组长度改变
                Highcharts.chart('domainStructureGraph', {
                    chart: {
                        type: 'columnrange',
                        inverted: true,
                        plotBorderColor: '#FFFFFF',
                        width: width,
                        height: height
                    },
                    title: {
                        text: ''
                    },
                    xAxis: {
                        labels: {
                            enabled: false // 不显示标签
                        },
                        tickColor: '#FFFFFF', // 不显示刻度颜色
                        lineColor: '#FFFFFF',
                        opposite: true
                    },
                    yAxis: {
                        title: '', // 无标题
                        min: 0,
                        lineColor: '#C0D0E0',
                        lineWidth: '1',
                        gridLineColor: '#FFFFFF', // 不显示网格线
                        tickPixelInterval: 50, // 50px为一格
                        minorTickInterval: 'auto',
                        minorGridLineColor: '#FFFFFF',
                        minorTickLength: 5,
                        minorTickWidth: 1,
                        minorTickColor: '#C0D0E0',
                        minorTickPosition: 'inside',
                        tickColor: '#C0D0E0', // 刻度的颜色
                        tickWidth: 1, // 刻度的宽度
                        tickPosition: 'inside', // 刻度的位置（default outside）
                        className: 'highcharts-axis-line',
                        labels: {
                            y: -10,
                            reserveSpace: true,
                        },
                        opposite: true,
                        id: 'yAxis' // 设置ID，用于后面操作此轴
                    },
                    plotOptions: {
                        columnrange: {
                            dataLabels: {
                                enabled: false,
                                formatter: function () {
                                    return this.y;
                                }
                            }
                        }
                    },
                    tooltip: {
                        // enabled: false                  // 不显示提示框
                        positioner: function (w, h, point) {
                            return {
                                x: point.plotX,
                                y: point.plotY + 48
                            } // 提示框显示的位置
                        },
                        formatter: function () { // 提示框显示的内容
                            return this.point.low + '-' + this.point.high;
                        }
                    },
                    credits: {
                        enabled: false,
                        text: 'Wutbiolab', // 版权信息的内容
                        href: 'http://www.wutbiolab.cn/index' // 版权信息的链接
                    },
                    legend: {
                        enabled: false
                    },
                    series: [series]
                }, function (chart) {
                    $('#domainStructureGraph .highcharts-series-group rect').attr('width',
                            '10');
                });
            }
        }

        function renderTable(response) {
            if(response.RapeExondbInfo.length > 0) {
            var RapeExondbInfo = response.RapeExondbInfo[0];
            $(".js-RapeExondbInfo").find(".chromId").html(RapeExondbInfo["ChromID"]);
            $(".js-RapeExondbInfo").find(".geneName").html(RapeExondbInfo["Gene_Name"]);
            $(".js-RapeExondbInfo").find(".oreitation").html(RapeExondbInfo["Oreitation"]);
            $(".js-RapeExondbInfo").find(".locusStart").html(RapeExondbInfo["LocusStart"]);
            $(".js-RapeExondbInfo").find(".locusEnd").html(RapeExondbInfo["LocusEnd"]);
            $(".js-RapeExondbInfo").find(".exonNumber").html(RapeExondbInfo["ExonNumber"]);
            $(".js-RapeExondbInfo").find(".cdsLength").html(RapeExondbInfo["CdsLength"]);
            $(".js-RapeExondbInfo").find(".proteinLength").html(RapeExondbInfo["ProteinLength"]);
            $(".js-RapeExondbInfo").find(".exonRegion").html(RapeExondbInfo["ExonRegion"]);
            $(".js-RapeExondbInfo").find(".exonPosition").html(RapeExondbInfo["ExonPosition"]);
            $(".js-RapeExondbInfo").find(".geneGroup").html(RapeExondbInfo["Gene_Group"]);
            }

            if(response.returnPfamdbDT.length > 0) {
            var returnPfamdbDT = response.returnPfamdbDT[0];
            $(".js-returnPfamdbDT").find(".PFamId").html(returnPfamdbDT["PfamID"]);
            $(".js-returnPfamdbDT").find(".anno").html(returnPfamdbDT["Anno"]);
            $(".js-returnPfamdbDT").find(".start").html(returnPfamdbDT["Start"]);
            $(".js-returnPfamdbDT").find(".end").html(returnPfamdbDT["End"]);
            $(".js-returnPfamdbDT").find(".evalue").html(returnPfamdbDT["Evalue"]);
            }

            var returnGoannoDT = response.returnGoannoDT;
            var gLen = returnGoannoDT.length;
            var gStr = '';
            for(var i = 0; i < gLen; i++) {
                gStr += '<tr><td><a target="_blank" href="http://amigo.geneontology.org/amigo/medial_search?q=' + returnGoannoDT[i].GOanno + '">' + returnGoannoDT[i].GOanno + '</a></td><td>'+ returnGoannoDT[i].Anno +'</td></tr>'
            }
            $(".js-go-anno > tbody").empty().append(gStr);

            var returnIprannoDT = response.returnIprannoDT;
            var rLen = returnIprannoDT.length;
            var rStr = '';
            for(var r = 0; r < rLen; r++) {
                rStr += '<tr><td><a target="_blank" href="http://www.ebi.ac.uk/interpro/entry/'+ returnIprannoDT[r].IPRanno +'">'+ returnIprannoDT[r].IPRanno +'</a></td><td>'+ returnIprannoDT[r].Anno +'</td></tr>'
            }
            $(".js-ipr-anno > tbody").empty().append(rStr);
        }


        $(function() {

            var geneName = getUrlParam("geneName");
            var version = getUrlParam("version");
            if(version=='Gmax_275_v2.0'){
                 version='gmx_phytozome_v11';
            }else{
                version='gmx_phytozome_v11';
            }
            var data = {
                "genename": geneName,
                "genegroup": version
            }
//            var data = {
//                "genename": "LOC_Os01g01010",
//                "genegroup": "osa_msu_7.0"
//            }
            loadMask('#mask-test');
            $.ajax({
                url: "http://47.96.185.131:82/api/CYJYFX/GetRapeInfoByGNAndGG",
                type: "POST",
                data: JSON.stringify(data),
                dataType: "json",
                headers: {
                  "Content-Type": "application/json"
                },
                success: function(data) {
                    maskClose();
                    if (data.Result === 'Error') {
                        alert(data.ErrorMessage);
                    } else {
                        renderTable(data);
                        renderGeneInfo(data);
                    }

                }
            });

        })
    </script>
</body>
</html>