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

    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <script src="${ctxStatic}/js/highcharts/highcharts.js"></script>
    <script>
        var CTXROOT = "${ctxroot}";
    </script>

    <script src="${ctxStatic}/js/jquery-ui.js"></script>
</head>
<body>
<dna:dna-header />
    <div class="container" style="background: #fff;position:relative;">
        <div class="snpTop">
            <c:if test="${result.snpData!=null}">
             ${result.snpData.id}
            </c:if>
            <c:if test="${result.INDELData!=null}">
               ${result.INDELData.id}
            </c:if>
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
        <div id="errorBoxShow">
            没有找到您要查询的内容，请尝试其他搜索词
        </div>
        <div id="trInfos">
            <table cellspacing="0" cellpadding="0" >
                <tr>
                    <td class="trWidth">SNP ID:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpId">${result.snpData.id}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpId">${result.INDELData.id}</td>
                    </c:if>

                    <td class="trWidth">Consequence type:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpCon">${result.snpData.consequencetype}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpCon">${result.INDELData.consequencetype}</td>
                    </c:if>
                </tr>
                <tr>
                    <td class="trWidth">Chr:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpChr">${result.snpData.chr}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpChr">${result.INDELData.chr}</td>
                    </c:if>
                    <td class="trWidth">Position:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpPos">${result.snpData.pos}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpPos">${result.INDELData.pos}</td>
                    </c:if>
                </tr>
                <tr>
                    <td class="trWidth">Reference allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpRef">${result.snpData.ref}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpRef">${result.INDELData.ref}</td>
                    </c:if>

                    <td class="trWidth">Major allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpMaj">${result.snpData.majorallen}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpMaj">${result.INDELData.majorallen}</td>
                    </c:if>
                </tr>
                <tr>
                    <td class="trWidth">Minor allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpMio">${result.snpData.minorallen}</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpMio">${result.INDELData.minorallen}</td>
                    </c:if>
                    <td class="trWidth">Frequence of major allele:</td>
                    <%--<td class="trWidth2 snpQue">${frequence}%</td>--%>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpQue">${result.snpData.major*100}%</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpQue">${result.INDELData.major*100}%</td>
                    </c:if>
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
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="cultivarI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param genoTypeT">GenoType
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="speciesI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param speciesT">物种
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="speciesI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param localityT">位置
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="localityI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param sampleNameT">样品名
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sampleNameI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param weightPer100seedsT">百粒重(g)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="weightPer100seedsI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param proteinT">蛋白质含量(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="proteinI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param oilT">含油量(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="oilI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param maturityDateT">熟期
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="maturityDateI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param heightT">株高(cm)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="heightI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param seedCoatColorT">种皮色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="seedCoatColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param hilumColorT">种脐色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="hilumColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param cotyledonColorT">子叶色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="cotyledonColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param flowerColorT">花色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="flowerColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param podColorT">荚色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="podColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param pubescenceColorT">茸毛色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="pubescenceColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param yieldT">产量(Mg/ha)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="yieldI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param upperLeafletLengthT">顶端小叶长度
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="upperLeafletLengthI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param linoleicT">亚油酸(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="linoleicI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param linolenicT">亚麻酸(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="linolenicI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param oleicT">油酸(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="oleicI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param palmiticT">软脂酸(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="palmiticI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param stearicT">硬脂酸(%)
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <select class="selectOperate">
                                <option value="" selected="selected"></option>
                                <option value="<"> < </option>
                                <option value="="> = </option>
                                <option value=">"> > </option>
                            </select>
                            <br>
                            <input type="text" placeholder="请输入" class="stearicI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

        </div>
        <%--laypage 分页 插件  begin--%>
        <div class="checkbox-item-tab" id="snpInforsPage">
            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
        </div>
        <%--laypage 分页 end --%>
    </div>

</body>
<script>
    $(function (){
        var urlParmas = window.location.search;
        var stateType = urlParmas.substring(urlParmas.length-3);
        if(stateType == "ind"){
             $(".genoTypeT").hide();
        }
        var populVal;   // 点击每个群体信息值
        var ctxRoot = '${ctxroot}';
        if("${result.RefAndRefPercent}"!=""){
            var AA = "${result.RefAndRefPercent}";
        }
        if("${result.totalAltAndAltPercent}"!=""){
            var TT = "${result.totalAltAndAltPercent}";
        }
        if("${result.totalRefAndAltPercent}"!=""){
            var AT = "${result.totalRefAndAltPercent}";
        }

        var id="${snp.id}";
        if ("${result.snpData}"==""){
            var major = "${result.INDELData.ref}";
        }else {
            var major = "${result.snpData.ref}";
        }
        if ("${result.snpData}"==""){
            var mijor = "${result.INDELData.alt}";
        }else {
            var mijor = "${result.snpData.alt}";
        }

        <%--var mijor = "${result.snpData.alt}";--%>
        var name1 = major+major;
        var name2 = major+mijor;
        var name3 = mijor+mijor;
        // 初始化
        var changeParam = major;  // major 和 minor 页面切换
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
                tooltip: {
                    headerFormat:'',
                    pointFormat: '<b>GenoType 占比</b>: {point.percentage:.1f} %</b> '
                },

                plotOptions: {
                    series: {
                        dataLabels: {
                            enabled: true,
//                            color: '#98BEFF',
                            fontWeight:"5",
                            borderWidth: 0,
                            style: {
                                fontWeight:"10",
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
        if ("${result.snpData}"!=""){
            drawPie(AA,TT,AT,name1,name2,name3);
        }
        // 选择群体信息
        $(".moveOnPop").mouseover(function (){
             $(this).find("div.popNames").show();
        }).mouseleave(function (){
            $(this).find("div.popNames").hide();
        })
        //点击每个群体
        $(".popNames li").click(function (){
            populVal = $(this).text();
            $(this).parent().parent().hide();
        })
        // 获取表格数据
        $(".minor").click(function (){
            mijor = $(".snpMio").text();
            changeParam = mijor;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data,paramData.pageNum);
        });
        $(".major").click(function (){
            major = $(".snpMaj").text();
            changeParam = major;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data,paramData.pageNum);
        })
        window.onload = function (){
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            getData(data,paramData.pageNum);
        }
        // // pageSize 选择事件
        $("#snpInforsPage select").change(function (e){
            var currentSelected = $(this).find("option:selected").text();
            page.pageSize = currentSelected;
            paramData.pageSize = page.pageSize;
        });
        // 获取焦点添加样式：
        $("#snpInforsPage").on("focus", ".laypage_skip", function() {
            $(this).addClass("isFocus");
        });
        $("#snpInforsPage").on("blur", ".laypage_skip", function() {
            $(this).removeClass("isFocus");
        });

        document.onkeydown = function(e) {
            var _page_skip = $('#snpInforsPage .laypage_skip');
            if(e && e.keyCode==13){ // enter 键
                if( _page_skip.hasClass("isFocus") ) {
                    if(_page_skip.val() * 1 > Math.ceil(count/ paramData.pageSize)) {
                        return alert("输入页码不能大于总页数");
                    }
                    var selectedNum = $('#snpInforsPage .laypage_skip').val();
                    page.pageNum = selectedNum;
                    paramData.pageNum = page.pageNum;
                    var selectedDatas = snpGetParams(changeParam);
                    selectedDatas.pageNum = paramData.pageNum;
                    selectedDatas.pageSize = paramData.pageSize;
                    getData(selectedDatas,selectedDatas.pageNum);
                }
            }
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
        var curr = 1;
        //ajax 请求
        function getData(data,curr){
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/changeByProportion",
                data:data,
                success:function (result) {
                    if(result.code!=0){
//                        $("#paging").hide();
                    }else {
                        count = result.data.dnaRuns.total;
                        if(count == 0){
                            $("#errorImg").show();
                            $("#containerAdmin").css("height","754px");
                            $("#snpInforsPage").hide();
                            $("#snpinfoTable table tbody tr").remove();
                            return;
                        }else{
                            if($("#snpInforsPage").is(":hidden")){
                                $("#snpInforsPage").show();
                            }
                            totalDatas = result.data.dnaRuns.list;
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
                            if(stateType == "ind"){
                                var genos = $("#snpinfoTable table tbody tr");
                                $.each(genos,function (i,item){
                                    $(item).find("td.genoTypeT").hide();
                                })
                            }
                        }
                    };
                    // 分页
                    laypage({
                        cont: $('#snpInforsPage .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(count /  page.pageSize), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        skin: '#5c8de5',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(count /  page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                var tmp = snpGetParams(changeParam);

                                tmp.pageNum = obj.curr;
                                tmp.pageSize = paramData.pageSize;
                                getData(tmp,obj.curr);
                            }
                        }
                    });
                    $(".total-page-count-snp").html(count);


                },
                error:function (error){
                    console.log(error);
                }
            })
        }
        // 详情页 搜索按钮点击事件
        $(".searBtn").click(function (){
            var searchId = $(".searchBox").find("input").val();
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/findSampleById",
                data:{id:searchId},
                dataType:"json",
                success:function (result){
                    if(result.code !=0){
                        $("#paging").hide();
                        $(".searchBox input").addClass("inputError")
                        $("#errorBoxShow").show();
                    }else {
                        if($(".searchBox input").hasClass("inputError")){
                            $(".searchBox input").removeClass("inputError");
                        };
                        if( !$("#errorBoxShow").is(":hidden")){
                            $("#errorBoxShow").hide();
                        }
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
                        getData(data,paramData.pageNum);
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
        //表格筛选框显示隐藏
        $("#snpinfoTable thead th").mouseover(function (){
            $(this).find(".inputComponent").show();
        }).mouseleave(function (){
            $(this).find(".inputComponent").hide();
        })
        // 筛选取消按钮 样式
        $("#snpinfoTable .inputComponent .btnCancel").click(function (){
            $(this).parent().parent().find("input").val("");
            $(this).parent().parent().hide();
        })
        // 表格筛选功能  --》 获取数据

        // 获取参数
        function snpGetParams(type){
            var datas = {
                snpId: $(".snpId").text(),
                changeParam:type,
                cultivar:$(".cultivarI").val(),  // 品种名
//            group:popuSelectedVal, // 群体
                species:$(".speciesI").val(),// 物种
                locality:$(".localityI").val(), // 位置
                sampleName:$(".sampleNameI").val(), // 样品名
                // weightPer100seeds:$(".weightPer100seedsI").val(),//百粒重
                "weightPer100seeds.operation":$(".weightPer100seedsI").parent().find("option:selected").text().trim() == ">"?"gt":$(".weightPer100seedsI").parent().find("option:selected").text().trim()=="="?"eq":$(".weightPer100seedsI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "weightPer100seeds.value":$(".weightPer100seedsI").val(),
                // protein:$(".proteinI").val(), //蛋白质含量
                "protein.operation":$(".proteinI").parent().find("option:selected").text().trim() == ">"?"gt":$(".proteinI").parent().find("option:selected").text().trim()=="="?"eq":$(".proteinI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "protein.value":$(".proteinI").val(),
                // 含油量
                "oil.operation":$(".oilI").parent().find("option:selected").text().trim() == ">"?"gt":$(".oilI").parent().find("option:selected").text().trim()=="="?"eq":$(".oilI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "oil.value":$(".oilI").val(),
                maturityDate:$(".maturityDateI").val(), // 熟期
                // height:$(".heightI").val(),//株高
                "height.operation":$(".heightI").parent().find("option:selected").text().trim() == ">"?"gt":$(".heightI").parent().find("option:selected").text().trim()=="="?"eq":$(".heightI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "height.value":$(".heightI").val(),
                seedCoatColor:$(".seedCoatColorI").val(),//种皮色
                hilumColor:$(".hilumColorI").val(),//种脐色
                cotyledonColor:$(".cotyledonColorI").val(), //子叶色
                flowerColor:$(".flowerColorI").val(),//花色
                podColor:$(".podColorI").val(),//荚色
                pubescenceColor:$(".pubescenceColorI").val(),//茸毛色
                // yield:$(".yieldI").val(),// 产量
                "yield.operation":$(".yieldI").parent().find("option:selected").text().trim() == ">"?"gt":$(".yieldI").parent().find("option:selected").text().trim()=="="?"eq":$(".yieldI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "yield.value":$(".yieldI").val(),
                // upperLeafletLength:$(".upperLeaf
                // letLengthI").val(), //顶端小叶长度
                "upperLeafletLength.operation":$(".upperLeafletLengthI").parent().find("option:selected").text().trim() == ">"?"gt":$(".upperLeafletLengthI").parent().find("option:selected").text().trim()=="="?"eq":$(".upperLeafletLengthI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "upperLeafletLength.value":$(".upperLeafletLengthI").val(),
                // linoleic:$(".linoleicI").val(), //亚油酸
                "linoleic.operation":$(".linoleicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".linoleicI").parent().find("option:selected").text().trim()=="="?"eq":$(".linoleicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "linoleic.value":$(".linoleicI").val(),
                // linolenic:$(".linolenicI").val(), //亚麻酸
                "linolenic.operation":$(".linolenicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".linolenicI").parent().find("option:selected").text().trim()=="="?"eq":$(".linolenicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "linolenic.value":$(".linolenicI").val(),
                // oleic:$(".oleicI").val(), //油酸
                "oleic.operation":$(".oleicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".oleicI").parent().find("option:selected").text().trim()=="="?"eq":$(".oleicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "oleic.value":$(".oleicI").val(),
                // palmitic:$(".palmiticI").val(),  //软脂酸
                "palmitic.operation":$(".palmiticI").parent().find("option:selected").text().trim() == ">"?"gt":$(".palmiticI").parent().find("option:selected").text().trim()=="="?"eq":$(".palmiticI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "palmitic.value":$(".palmiticI").val(),
                // stearic:$(".stearicI").val(), //硬脂酸
                "stearic.operation":$(".stearicI").parent().find("option:selected").text().trim() == ">"?"gt":$(".stearicI").parent().find("option:selected").text().trim()=="="?"eq":$(".stearicI").parent().find("option:selected").text().trim()=="<"?"lt":"",
                "stearic.value":$(".stearicI").val(),
                isPage:1  // 是否分页
            };
            return datas;
        }
        // 获取表格数据
        $("#snpinfoTable .btnConfirmInfo").click(function (){
            var selectedDatas1 = snpGetParams(changeParam);
            selectedDatas1.pageNum = paramData.pageNum;
            selectedDatas1.pageSize = paramData.pageSize;
            getData(selectedDatas1,selectedDatas1.pageNum);
        })

    })

</script>
</html>
