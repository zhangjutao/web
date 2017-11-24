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
                <input type="text" placeholder="请输入你要进行搜索的内容进行搜索" />
            </p>
            <p class="searBtn">
                <span>
                    <img src="${ctxStatic}/images/search.png" alt="">
                </span>
                搜索
            </p>
        </div>
        <div id="trInfos">
            <table cellspacing="0" cellpadding="0" >
                <tr>
                    <td class="trWidth">SNP ID:</td>
                    <td class="trWidth2 snpId">${snp.id}</td>
                    <td class="trWidth">Consequence type:</td>
                    <td class="trWidth2 snpCon">${snp.consequencetype}</td>
                </tr>
                <tr>
                    <td class="trWidth">Chr:</td>
                    <td class="trWidth2 snpChr">${snp.chr}</td>
                    <td class="trWidth">Position:</td>
                    <td class="trWidth2 snpPos">${snp.pos}</td>
                </tr>
                <tr>
                    <td class="trWidth">Reference allele:</td>
                    <td class="trWidth2 snpRef">${snp.ref}</td>
                    <td class="trWidth">Major allele:</td>
                    <td class="trWidth2 snpMaj">${snp.majorallen}</td>
                </tr>
                <tr>
                    <td class="trWidth">Minor allele:</td>
                    <td class="trWidth2 snpMio">${snp.minorallen}</td>
                    <td class="trWidth">Frequence of major allele:</td>
                    <td class="trWidth2 snpQue">${frequence}%</td>
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

                </tbody>
            </table>

        </div>
        <%--// 分页显示 begin--%>
        <div id="paging">

            <div id="inputNums">
                <span>跳转到</span>
                <div>
                    <input type="number" min="1" name="number" value="" id="inputNum" >
                </div>
                <span>页</span>
                <span>展示数量</span>
                <div id="selectedNum">
                    <select name="selected" id="selectSize" style="width:40px;">
                        <option value="10" selected = "true">10</option>
                        <option value="10">20</option>
                        <option value="10">30</option>
                        <option value="10">40</option>
                    </select>
                </div>
                <span>/页</span>
                <p style="margin:0px;">总数：<span id="totals"></span> 条</p>
            </div>
            <div id="page">
                <b class="first">&lt;</b>
                <p class="two"></p>
                <b class="three">...</b>
                <p class="four"></p>
                <p class="five"></p>
                <p class="six"></p>
                <b class="seven">...</b>
                <p class="eight"></p>
                <b class="last">&gt;</b>
            </div>
        </div>
        <%--// 分页显示 end--%>

    </div>

</body>
<script>
    $(function (){
        var populVal;   // 点击每个群体信息值
        var ctxRoot = '${ctxroot}';
        console.log('${ctxroot}');
        console.log("${snp.id}")
        var AA = "${result.RefAndRefPercent}";
        var TT = "${result.totalAltAndAltPercent}";
        var AT = "${result.totalRefAndAltPercent}";
        var id="${snp.id}";
        var major = "${result.snpData.ref}";
        var mijor = "${result.snpData.alt}";
        var name1 = major+major;
        var name2 = major+mijor;
        var name3 = mijor+mijor;

        // 初始化
        var changeParam = major;  // major 和 minor 页面切换
        console.log(AA)
        console.log(TT)
        console.log(AT)
        // 花饼图
        function drawPie(ref,alt,refAlt,name1,name2,name3){
            $('#pieShow').highcharts({
                chart: {
                    plotBackgroundColor: null,
                    plotBorderWidth: null,
                    plotShadow: false
                },
                title: {
                    text: 'GenoType'
                },
                credits: {
                    enabled: false
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
                        [name1,ref*100],
                        [name3, alt*100],
                        [name2, refAlt*100]
                    ]
                }]
            })
        }
        drawPie(AA,TT,AT,name1,name2,name3);
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
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/changeByProportion",
                data:data,
                success:function (result){
                    var GenoType;
                    var dnaDatas = result.data.dnaRuns;
                    var dnaSamples = result.data.samples;
                    $("#snpinfoTable table tbody tr").remove();
                    for (var i=0;i<dnaDatas.length;i++){
                       GenoType = dnaSamples[dnaDatas[i].runNo];
                        var cultivarTV = dnaDatas[i].cultivar==null?"":dnaDatas[i].cultivar;
                        var genoTypeTV = GenoType==null?"":GenoType;
                        var speciesTV = dnaDatas[i].species==null?"":dnaDatas[i].species;
                        var localityTV = dnaDatas[i].locality==null?"":dnaDatas[i].locality;
                        var sampleNameTV = dnaDatas[i].sampleName==null?"":dnaDatas[i].sampleName;
                        var weightPer100seedsTV = dnaDatas[i].weightPer100seeds==null?"":dnaDatas[i].weightPer100seeds;
                        var proteinTV = dnaDatas[i].protein==null?"":dnaDatas[i].protein;
                        var oilTV = dnaDatas[i].oil==null?"":dnaDatas[i].oil;
                        var maturityDateTV = dnaDatas[i].maturityDate==null?"":dnaDatas[i].maturityDate;
                        var heightTV = dnaDatas[i].height==null?"":dnaDatas[i].height
                        var seedCoatColorTV = dnaDatas[i].seedCoatColor==null?"":dnaDatas[i].seedCoatColor;
                        var hilumColorTV  = dnaDatas[i].hilumColor==null?"":dnaDatas[i].hilumColor==null;
                        var cotyledonColorTV = dnaDatas[i].cotyledonColor==null?"":dnaDatas[i].cotyledonColor;
                        var flowerColorTV = dnaDatas[i].flowerColor==null?"":dnaDatas[i].flowerColor;
                        var podColorTV = dnaDatas[i].podColor==null?"":dnaDatas[i].podColor;
                        var pubescenceColorTV = dnaDatas[i].pubescenceColor ==null?"":dnaDatas[i].pubescenceColor;
                        var yieldTV = dnaDatas[i].yield==null?"":dnaDatas[i].yield;
                        var upperLeafletLengthTV = dnaDatas[i].upperLeafletLength==null?"":dnaDatas[i].upperLeafletLength;
                        var linoleicTV = dnaDatas[i].linoleic==null?"":dnaDatas[i].linoleic;
                        var linolenicTV = dnaDatas[i].linolenic==null?"":dnaDatas[i].linolenic;
                        var oleicTV = dnaDatas[i].oleic==null?"":dnaDatas[i].oleic;
                        var palmiticTV = dnaDatas[i].palmitic==null?"":dnaDatas[i].palmitic;
                        var stearicTV = dnaDatas[i].stearic==null?"":dnaDatas[i].stearic;

                        var tr = "<tr><td class='param cultivarT'>" + cultivarTV +
                            "</td><td class='param genoTypeT'>" + genoTypeTV +
                            "</td><td class='param speciesT'>" + speciesTV+
                            "</td><td class='param localityT'>" + localityTV +
                            "</td><td class='param sampleNameT'>" + sampleNameTV +
                            "</td><td class='param weightPer100seedsT'>" + weightPer100seedsTV +
                            "</td><td class='param proteinT'>" + proteinTV +
                            "</td><td class='param oilT'>" + oilTV +
                            "</td><td class='param maturityDateT'>" + maturityDateTV +
                            "</td><td class='param heightT'>" + heightTV +
                            "</td><td class='param seedCoatColorT'>" + seedCoatColorTV +
                            "</td><td class='param hilumColorT'>" + hilumColorTV +
                            "</td><td class='param cotyledonColorT'>" + cotyledonColorTV +
                            "</td><td class='param flowerColorT'>" +flowerColorTV +
                            "</td><td class='param podColorT'>" + podColorTV +
                            "</td><td class='param pubescenceColorT'>" + pubescenceColorTV +
                            "</td><td class='param yieldT'>" + yieldTV +
                            "</td><td class='param upperLeafletLengthT'>" + upperLeafletLengthTV +
                            "</td><td class='param linoleicT'>" + linoleicTV +
                            "</td><td class='param linolenicT'>" + linolenicTV +
                            "</td><td class='param oleicT'>" + oleicTV +
                            "</td><td class='param palmiticT'>" + palmiticTV +
                            "</td><td class='param stearicT'>" + stearicTV +"</td></tr>"
                        var $tbody = $("#snpinfoTable table tbody");
                        $tbody.append(tr);
                    }
                    var tr =
                    console.log(result);
                },
                error:function (error){
                    console.log(error);
                }
            })
        }
        $(".minor").click(function (){
            mijor = $(".snpMio").text();
            changeParam = mijor;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data);
        });
        $(".major").click(function (){
            major = $(".snpMaj").text();
            changeParam = major;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data);
        })
        window.onload = function (){
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data);
        }
        // 分页
        var nums;
        var totalDatas;
        var intNums;
        var count;
        var page = {
            pageNum:1,
            pageSize:10
        }
        //每页展示的数量
        var paramData = {
            pageNum:page.pageNum,
            pageSize:page.pageSize
        };
        // 获取参数
        function snpGetParams(type){
            var data = {
                snpId:id,
                changeParam:type
            };
            return data;
        }
        //ajax 请求
        function getData(data){
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/changeByProportion",
                data:data,
                success:function (result) {
                    console.log(result);
                    if(result.code!=0){
                        $("#paging").hide();
                    }else {
                        count = result.data.dnaRuns.total;
                        if(count <40){
                            $("#page").css({"padding-left":"186px"});
                        }else {
                            $("#page").css({"padding-left":"10px"});
                        };
                        if(count == 0){
                            $("#paging").hide();
                            $("#errorImg").show();
                            $("#containerAdmin").css("height","754px");
                        }else{
                            totalDatas = result.data.dnaRuns.list;
                            console.log(totalDatas);
                            $("#snpinfoTable table tbody tr").remove();

                            nums = Math.ceil(count / page.pageSize);
                            //舍弃小数之后的取整
                            intNums = parseInt(count / page.pageSize);
                            var dnaSamples = result.data.samples;
                            for (var i=0;i<totalDatas.length;i++){
                                GenoType = dnaSamples[totalDatas[i].runNo];
                                var cultivarTV = totalDatas[i].cultivar==null?"":totalDatas[i].cultivar;
                                var genoTypeTV = GenoType==null?"":GenoType;
                                var speciesTV = totalDatas[i].species==null?"":totalDatas[i].species;
                                var localityTV = totalDatas[i].locality==null?"":totalDatas[i].locality;
                                var sampleNameTV = totalDatas[i].sampleName==null?"":totalDatas[i].sampleName;
                                var weightPer100seedsTV = totalDatas[i].weightPer100seeds==null?"":totalDatas[i].weightPer100seeds;
                                var proteinTV = totalDatas[i].protein==null?"":totalDatas[i].protein;
                                var oilTV = totalDatas[i].oil==null?"":totalDatas[i].oil;
                                var maturityDateTV = totalDatas[i].maturityDate==null?"":totalDatas[i].maturityDate;
                                var heightTV = totalDatas[i].height==null?"":totalDatas[i].height
                                var seedCoatColorTV = totalDatas[i].seedCoatColor==null?"":totalDatas[i].seedCoatColor;
                                var hilumColorTV  = totalDatas[i].hilumColor==null?"":totalDatas[i].hilumColor==null;
                                var cotyledonColorTV = totalDatas[i].cotyledonColor==null?"":totalDatas[i].cotyledonColor;
                                var flowerColorTV = totalDatas[i].flowerColor==null?"":totalDatas[i].flowerColor;
                                var podColorTV = totalDatas[i].podColor==null?"":totalDatas[i].podColor;
                                var pubescenceColorTV = totalDatas[i].pubescenceColor ==null?"":totalDatas[i].pubescenceColor;
                                var yieldTV = totalDatas[i].yield==null?"":totalDatas[i].yield;
                                var upperLeafletLengthTV = totalDatas[i].upperLeafletLength==null?"":totalDatas[i].upperLeafletLength;
                                var linoleicTV = totalDatas[i].linoleic==null?"":totalDatas[i].linoleic;
                                var linolenicTV = totalDatas[i].linolenic==null?"":totalDatas[i].linolenic;
                                var oleicTV = totalDatas[i].oleic==null?"":totalDatas[i].oleic;
                                var palmiticTV = totalDatas[i].palmitic==null?"":totalDatas[i].palmitic;
                                var stearicTV = totalDatas[i].stearic==null?"":totalDatas[i].stearic;

                                var tr = "<tr><td class='param cultivarT'>" + cultivarTV +
                                    "</td><td class='param genoTypeT'>" + genoTypeTV +
                                    "</td><td class='param speciesT'>" + speciesTV+
                                    "</td><td class='param localityT'>" + localityTV +
                                    "</td><td class='param sampleNameT'>" + sampleNameTV +
                                    "</td><td class='param weightPer100seedsT'>" + weightPer100seedsTV +
                                    "</td><td class='param proteinT'>" + proteinTV +
                                    "</td><td class='param oilT'>" + oilTV +
                                    "</td><td class='param maturityDateT'>" + maturityDateTV +
                                    "</td><td class='param heightT'>" + heightTV +
                                    "</td><td class='param seedCoatColorT'>" + seedCoatColorTV +
                                    "</td><td class='param hilumColorT'>" + hilumColorTV +
                                    "</td><td class='param cotyledonColorT'>" + cotyledonColorTV +
                                    "</td><td class='param flowerColorT'>" +flowerColorTV +
                                    "</td><td class='param podColorT'>" + podColorTV +
                                    "</td><td class='param pubescenceColorT'>" + pubescenceColorTV +
                                    "</td><td class='param yieldT'>" + yieldTV +
                                    "</td><td class='param upperLeafletLengthT'>" + upperLeafletLengthTV +
                                    "</td><td class='param linoleicT'>" + linoleicTV +
                                    "</td><td class='param linolenicT'>" + linolenicTV +
                                    "</td><td class='param oleicT'>" + oleicTV +
                                    "</td><td class='param palmiticT'>" + palmiticTV +
                                    "</td><td class='param stearicT'>" + stearicTV +"</td></tr>"
                                var $tbody = $("#snpinfoTable table tbody");
                                $tbody.append(tr);
                            }
                            pageStyle(nums,intNums);
                            $("#totals").text(count);
                        }

                    }


                },
                error:function (error){
                    console.log(error);
                }
            })
        }
        // 样式调整方法
        function pageStyle(nums,intNums){
            if (nums > 4) {
                // $(".first").hide().next().text(1).next().hide();
                $(".first").next().text(1);
                $(".four").text(2).next().text(3).next().text(4);
                $(".eight").text(nums);
                $(".seven").show();
                $(".last").show();
            };
            if (intNums == 0) {
                styleChange();
                $(".two").text(1);
                $(".four").hide();
                $(".five").hide();
                $(".six").hide();
            }
            switch (nums) {
                case 1:
                    styleChange();
                    $(".two").text(1);
                    $(".four").hide();
                    $(".five").hide();
                    $(".six").hide();
                    break;
                case 2:
                    styleChange();
                    $(".two").text(1);
                    $(".four").text(2);
                    $(".five").hide();
                    $(".six").hide();
                    break;
                case 3:
                    styleChange();
                    $(".two").text(1);
                    $(".four").text(2);
                    $(".five").text(3);
                    $(".six").hide();
                    break;
                case 4:
                    styleChange();
                    $(".two").text(1);
                    $(".four").text(2);
                    $(".five").text(3);
                    $(".six").text(4);
                    break;
            }
        }
        // 显示隐藏样式封装
        function styleChange() {
            $(".three").hide();
            $(".first").hide();
            $(".seven").hide();
            $(".eight").hide();
            $(".last").hide();
        };

        //每个页码的点击事件
        $("#page>p").click(function (e) {
            //样式
            if (nums > 4) {
                $(".first").show();
                $(".three").show();
                $(".eight").text(nums);
            };
            var $p = $(e.target);

            page.pageNum = parseInt($p.text());
            paramData.pageNum = page.pageNum;

            var selectedDatas = snpGetParams(changeParam);
            selectedDatas.pageNum = paramData.pageNum;
            selectedDatas.pageSize = paramData.pageSize;
            console.log(selectedDatas);
            getData(selectedDatas);
            var plists = $p.siblings();
            for (var i = 0; i < plists.length; i++) {
                if ($(plists[i]).hasClass("pageColor")) {
                    $(plists[i]).removeClass("pageColor");
                }
            }
            $p.addClass("pageColor");

        });
        // pageSize 选择事件
        $("#selectedNum").change(function (e){
            var currentSelected = $("#selectedNum option:selected").text();
            page.pageSize = currentSelected;
            paramData.pageSize = page.pageSize;
            var selectedDatas =snpGetParams(changeParam);
            selectedDatas.pageNum = paramData.pageNum;
            selectedDatas.pageSize = paramData.pageSize;
            getData(selectedDatas);
        })
        // "<" 点击事件
        $(".first").click(function () {
            var content6 = Number($(".six").text());
            var content4 = Number($(".four").text());
            var content5 = Number($(".five").text());
            if (content6 < nums) {
                $(".last").show();
                $(".seven").show();
            }
            if (content4 <= 2) {
                $(".first").hide().next().next().hide();
            } else {
                $(".six").text(content6 - 1);
                $(".four").text(content4 - 1);
                $(".five").text(content5 - 1);
            }
        })
        // enter 键盘事件
        $("#inputNum").keydown(function(event){
            event=document.all?window.event:event;
            if((event.keyCode || event.which)==13){
                var selectedNum = $(this).val();
                page.pageNum = pageNum = selectedNum;
                paramData.pageNum = page.pageNum;
                var selectedDatas = snpGetParams(changeParam);
                selectedDatas.pageNum = paramData.pageNum;
                selectedDatas.pageSize = paramData.pageSize;
                getData(selectedDatas);
            }
        });
        // ">" 点击事件
        $(".last").click(function () {
            var content6 = Number($(".six").text());
            var content4 = Number($(".four").text());
            var content5 = Number($(".five").text());
            var content2 = Number($(".two").text());
            if (content2 == 1) {
                $(".first").show();
                $(".three").show();
            }

            if (content6 >= nums - 1) {
                $(".seven").hide();
                $(this).hide();
            } else {
                $(".six").text(content6 + 1);
                $(".four").text(content4 + 1);
                $(".five").text(content5 + 1);
            }
        })

        // 分页end

        // 详情页 搜索按钮点击事件
        $(".searBtn").click(function (){
            var searchId = $(".searchBox").find("input").val();

            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/findSampleById",
                data:{id:searchId},
                dataType:"json",
                success:function (result){
                    console.log(result);
                    if(result.code !=0){
                        $("#paging").hide();
                    }else {
                        id = result.data.snpData.id;
                        major =result.data.snpData.majorallen;
                        minor =result.data.snpData.minorallen;
                        $(".snpId").text(result.data.snpData.id);
                        $(".snpCon").text(result.data.snpData.consequencetype);
                        $(".snpChr").text(result.data.snpData.chr);
                        $(".snpPos").text(result.data.snpData.pos);
                        $(".snpRef").text(result.data.snpData.ref);
                        $(".snpMaj").text(result.data.snpData.majorallen);
                        $(".snpMio").text(result.data.snpData.minorallen);
                        $(".snpQue").text((result.data.snpData.major*100).toFixed(2) + "%");
                        changeParam = major;
                        var data = snpGetParams(changeParam);
                        data.pageNum = paramData.pageNum;
                        data.pageSize = paramData.pageSize;
                        getData(data);
                        //重新画图
                        AA = result.data.RefAndRefPercent;
                        TT = result.data.totalAltAndAltPercent;
                        AT = result.data.totalRefAndAltPercent;
                        var n1 =result.data.snpData.ref + result.data.snpData.ref;
                        var n2 =result.data.snpData.ref + result.data.snpData.alt;
                        var n3 =result.data.snpData.alt +result.data.snpData.alt;
                        drawPie(AA,TT,AT,n1,n2,n3);
                    }
                },
                error:function (error){
                    console.log(error);
                }
            })
        })

    })
        // tag 样式切换
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
