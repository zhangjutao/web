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
                <input type="text" placeholder="请输入关键词" />
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
            <table cellspacing="0" cellpadding="0" style="table-layout:fixed; ">
                <tr>
                    <td class="trWidth">SNP/INDEL ID:</td>
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
                        <td class="trWidth2 snpRef">
                            <div>
                                ${result.snpData.ref}
                            </div>
                        </td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpRef">
                            <div>
                                    ${result.INDELData.ref}
                            </div>
                        </td>
                    </c:if>

                    <td class="trWidth">Major allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpMaj">
                            <div>
                                    ${result.snpData.majorallen}
                            </div>

                        </td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpMaj">
                            <div>
                                    ${result.INDELData.majorallen}
                            </div>
                        </td>
                    </c:if>
                </tr>
                <tr>
                    <td class="trWidth">Minor allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpMio">
                            <div>
                                    ${result.snpData.minorallen}
                            </div>
                        </td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpMio">
                            <div>
                                    ${result.INDELData.minorallen}
                            </div>
                        </td>
                    </c:if>
                    <td class="trWidth">Frequence of major allele:</td>
                    <c:if test="${result.snpData!=null}">
                        <td class="trWidth2 snpQue">${major}%</td>
                    </c:if>
                    <c:if test="${result.INDELData!=null}">
                        <td class="trWidth2 snpQue">${major}%</td>
                    </c:if>
                </tr>
            </table>
        </div>
        <p class="pieShowTop"></p>
        <div id="pieShow">
        </div>
        <div id="snpinfoTable">
            <div id="snpSetPanel">
                <div id="tableCnt">
                    <div class="selecting" >
                        <p>
                            表格内容：
                        </p>
                        <div id="selectedDetails">
                            <ul>
                                <li>
                                    <input type="checkbox" name="runNo" class="runNo" checked="checked"> 测序样品编号
                                </li>
                                <li>
                                    <input type="checkbox" name="genoType" class="genoType" checked="checked"> Genotype
                                </li>
                                <li>
                                    <%--无--%>
                                    <input type="checkbox" name="scientificName" class="scientificName" checked="checked"> 物种名称
                                </li>
                                <li>
                                    <input type="checkbox" name="sampleId" class="sampleId" checked="checked"> 编号
                                </li>
                                <li>
                                    <input type="checkbox" name="strainName" class="strainName" checked="checked"> 菌株名称
                                </li>
                                <li>
                                    <input type="checkbox" name="locality" class="locality" checked="checked"> 地理位置
                                </li>
                                <li>
                                    <input type="checkbox" name="preservationLocation" class="preservationLocation" checked="checked">保藏地点
                                </li>
                                <li>
                                    <input type="checkbox" name="type" class="type" checked="checked"> 类型
                                </li>
                                <li>
                                    <input type="checkbox" name="environment" class="environment" checked="checked"> 培养环境
                                </li>
                                <li>
                                    <input type="checkbox" name="materials" class="materials" checked="checked"> 材料
                                </li>
                                <li>
                                    <input type="checkbox" name="treat" class="treat" checked="checked"> 处理
                                </li>
                                <li>
                                    <input type="checkbox" name="time" class="time" checked="checked"> 时间
                                </li>
                                <li>
                                    <input type="checkbox" name="taxonomy" class="taxonomy" checked="checked"> 分类地位
                                </li>
                                <li>
                                    <input type="checkbox" name="myceliaPhenotype" class="myceliaPhenotype" checked="checked"> 菌丝形态
                                </li>
                                <li>
                                    <input type="checkbox" name="myceliaDiameter" class="myceliaDiameter" checked="checked"> 菌丝直径
                                </li>
                                <li>
                                    <input type="checkbox" name="myceliaColor" class="myceliaColor" checked="checked"> 菌丝颜色
                                </li>
                                <li>
                                    <input type="checkbox" name="sporesColor" class="sporesColor" checked="checked"> 孢子颜色
                                </li>
                                <li>
                                    <%--无--%>
                                    <input type="checkbox" name="sporesShape" class="sporesShape" checked="checked"> 孢子形态
                                </li>
                                <li>
                                    <input type="checkbox" name="clampConnection" class="clampConnection" checked="checked"> 锁状联合
                                </li>
                                <li>
                                    <input type="checkbox" name="pileusPhenotype" class="pileusPhenotype" checked="checked">菌盖形态
                                </li>
                                <li>
                                    <input type="checkbox" name="pileusColor" class="pileusColor" checked="checked"> 菌盖颜色
                                </li>
                                <li>
                                    <input type="checkbox" name="stipePhenotype" class="stipePhenotype" checked="checked"> 菌柄形态
                                </li>
                                <li>
                                    <input type="checkbox" name="stipeColor" class="stipeColor" checked="checked"> 菌柄颜色
                                </li>
                                <li>
                                    <input type="checkbox" name="fruitbodyColor" class="fruitbodyColor" checked="checked"> 子实体颜色
                                </li>
                                <li>
                                    <input type="checkbox" name="fruitbodyType" class="fruitbodyType" checked="checked"> 子实体形态
                                </li>
                                <li>
                                    <input type="checkbox" name="illumination" class="illumination" checked="checked"> 光照
                                </li>
                                <li>
                                    <input type="checkbox" name="collarium" class="collarium" checked="checked">菌环
                                </li>
                                <li>
                                    <input type="checkbox" name="volva" class="volva" checked="checked">菌托
                                </li>
                                <li>
                                    <input type="checkbox" name="velum" class="velum" checked="checked">菌幕
                                </li>
                                <li>
                                    <input type="checkbox" name="sclerotium" class="sclerotium" checked="checked">菌核
                                </li>
                                <li>
                                    <input type="checkbox" name="strainMedium" class="strainMedium" checked="checked">菌种培养基
                                </li>
                                <li>
                                    <input type="checkbox" name="mainSubstrate" class="mainSubstrate" checked="checked">主要栽培基质
                                </li>
                                <li>
                                    <input type="checkbox" name="afterRipeningStage" class="afterRipeningStage" checked="checked">后熟期
                                </li>
                                <li>
                                    <input type="checkbox" name="primordialStimulationFruitbody" class="primordialStimulationFruitbody" checked="checked">原基刺激&子实体
                                </li>
                                <li>
                                    <input type="checkbox" name="reproductiveMode" class="reproductiveMode" checked="checked">生殖方式
                                </li>
                                <li>
                                    <input type="checkbox" name="lifestyle" class="lifestyle" checked="checked">生活方式
                                </li>
                                <li>
                                    <input type="checkbox" name="preservation" class="preservation" checked="checked">保藏方法
                                </li>
                                <li>
                                    <input type="checkbox" name="domestication" class="domestication" checked="checked">驯化
                                </li>
                                <li>
                                    <input type="checkbox" name="nuclearPhase" class="nuclearPhase" checked="checked">核相
                                </li>
                                <li>
                                    <input type="checkbox" name="matingType" class="matingType" checked="checked">交配型
                                </li>
                            </ul>
                            <div>
                                <input type="checkbox" id="SelectAllBox" checked="checked"> 全选
                            </div>
                        </div>
                    </div>
                    <div class="changeStauts">
                        <div class="sets">
                            <span id="exportData">导出数据</span>
                            <span id="tableSet">表格设置</span>
                        </div>
                        <div id="operate">
                            <p class="sure">确定</p>
                            <div class="opOthers">
                                <p class="selectedAll">清空</p>
                                <p class="packUp">收起
                                    <img src="${ctxStatic}/images/down.png" alt="logo" style="width:12px;margin-left:5px;margin-top: -3px;">
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="changeTab">
                <p class="changeTagColor major">Major Allele</p>
                <p class="minor">Minor Allele</p>
            </div>
            <table border="1" cellspacing="0" cellpadding="5" style="overflow-x: scroll;overflow-y: hidden; min-height:100px;margin-top:20px;">
                <thead style="overflow-x: scroll;">
                <tr style="background: #F5F8FF;">
                <tr>
                    <th class="param runNoT">测序样品编号
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="runNoI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param genoTypeT">Genotype
                        <img src="/dna/static/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="genoTypeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param scientificNameT">物种名称
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="scientificNameI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param sampleIdT">编号
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sampleIdI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param strainNameT">菌株名称
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="strainNameI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param localityT">地理位置
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="localityI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param preservationLocationT">保藏地点
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入"
                                   class="preservationLocationI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param typeT">类型
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="typeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param environmentT">培养环境
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="environmentI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param materialsT">材料
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="materialsI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param treatT">处理
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="treatI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <%--<th class="paramTag popMoveOn" style="position:relative;">时间--%>
                    <%--<img src="/dna/static/images/arrow-drop-down.png" alt="logo"--%>
                    <%--style="width: 15px;vertical-align: middle;">--%>
                    <%--<div class="popNames">--%>
                    <%--<ul>--%>
                    <%--<li>Q1</li>--%>
                    <%--<li>Q2</li>--%>
                    <%--<li>Q3</li>--%>
                    <%--<li>Q4</li>--%>
                    <%--<li>Q5</li>--%>
                    <%--<li>Q6</li>--%>
                    <%--<li>Q7</li>--%>
                    <%--<li>Q8</li>--%>
                    <%--<li>Q9</li>--%>
                    <%--<li>Q10</li>--%>
                    <%--</ul>--%>
                    <%--</div>--%>
                    <%--</th>--%>
                    <th class="param timeT">时间
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="timeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param taxonomyT">分类地位
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="taxonomyI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param myceliaPhenotypeT">菌丝形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaPhenotypeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param myceliaDiameterT">菌丝直径
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaDiameterI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param myceliaColorT">菌丝颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param sporesColorT">孢子颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sporesColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param sporesShapeT">孢子形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sporesShapeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param clampConnectionT">锁状联合
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="clampConnectionI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param pileusPhenotypeT">菌盖形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="pileusPhenotypeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param pileusColorT">菌盖颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="pileusColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param stipePhenotypeT">菌柄形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="stipePhenotypeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param stipeColorT">菌柄颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="stipeColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param fruitbodyColorT">子实体颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="fruitbodyColorI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param fruitbodyTypeT">子实体形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="fruitbodyTypeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param illuminationT">光照
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="illuminationI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param collariumT">菌环
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="collariumI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param volvaT">菌托
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="volvaI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param velumT">菌幕
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="velumI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param sclerotiumT">菌核
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sclerotiumI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param strainMediumT">菌种培养基
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="strainMediumI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param mainSubstrateT">主要栽培基质
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="mainSubstrateI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param afterRipeningStageT">后熟期
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="afterRipeningStageI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param primordialStimulationFruitbodyT">原基刺激&子实体
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入"
                                   class="primordialStimulationFruitbodyI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param reproductiveModeT">生殖方式
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="reproductiveModeI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param lifestyleT">生活方式
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="lifestyleI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param preservationT">保藏方法
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="preservationI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param domesticationT">驯化
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="domesticationI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param nuclearPhaseT">核相
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="nuclearPhaseI inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param matingTypeT">交配型
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="matingTypeI inputStyle">
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
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
<script>
    $(function (){
        var urlParmas = window.location.search;
        var stateType = urlParmas.substring(urlParmas.length-3);
        if(stateType == "ind"){
             $("#snpinfoTable table th.genoTypeT").remove();
             $("#selectedDetails .genoType").parent().remove();
             $("#pieShow").css("border-bottom","1px solid #ffffff");
             $(".pieShowTop").css("border-top","1px solid #ffffff");
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
                    text: 'Genotype'
                },
                credits: {
                    enabled: false
                },
                tooltip: {
                    headerFormat:'',
                    pointFormat: '<b>GenoType 占比</b>: {point.percentage:.4f} %</b> '
                },

                plotOptions: {
                    pie: {
                        allowPointSelect: true,
                        cursor: 'pointer',
                        dataLabels: {
                            enabled: true,
                            format: '<b>{point.name}</b>: {point.percentage:.4f} %',
                            style: {
                                fontWeight:"10",
                                fontSize:"14px",
                                color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black',
//                                color:"green"
//                                color:'PaleTurquoise'
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
            mijor = $(".snpMio").text().trim();
            changeParam = mijor;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            data.judgeAllele = $(this).text().split(" ")[0];
            getData(data,paramData.pageNum);
        });
        $(".major").click(function (){
            major = $(".snpMaj").text().trim();
            changeParam = major;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            data.judgeAllele = $(this).text().split(" ")[0];
            getData(data,paramData.pageNum,filterParamer);
        })
        window.onload = function (){
            debugger;
            var data = snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
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
                    selectedDatas.judgeAllele = $(".changeTagColor").text().split(" ")[0];
                    getData(selectedDatas,selectedDatas.pageNum,filterParamer);
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
        var currPageNumber = 1;
        //ajax 请求
        function getData(data,curr,fn){
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/changeByProportion",
                data:data,
                success:function (result) {
                    if(result.code!=0){
                      $('#snpinfoTable table tbody').empty();
                      $("#snpInforsPage .total-page-count span").text(0);
                    }else {
                        count = result.data.dnaRuns.total;
                        $("#total-page-count span").html(count)
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
                            $("#snpinfoTable table tbody").empty();

                            nums = Math.ceil(count / page.pageSize);
                            //舍弃小数之后的取整
                            intNums = parseInt(count / page.pageSize);
                            var dnaSamples = result.data.samples;
                            for (var i=0;i<totalDatas.length;i++){

                                var runNoT = totalDatas[i].runNo==null?"":totalDatas[i].runNo;  // 测序样品编号
                                var genoTypeT = dnaSamples[totalDatas[i].runNo];
                                var scientificNameT = totalDatas[i].scientificName==null?"":totalDatas[i].scientificName;  // 物种名称
                                var sampleIdT = totalDatas[i].sampleId==null?"":totalDatas[i].sampleId;  // 编号
                                var strainNameT = totalDatas[i].strainName==null?"":totalDatas[i].strainName; // 菌株名称
                                var localityT = totalDatas[i].locality==null?"":totalDatas[i].locality;  //地理位置
                                var preservationLocationT = totalDatas[i].preservationLocation==null?"":totalDatas[i].preservationLocation;  //保藏地点
                                var typeT = totalDatas[i].type==null?"":totalDatas[i].type;  //类型
                                var environmentT = totalDatas[i].environment==null?"":totalDatas[i].environment;  //培养环境
                                var materialsT = totalDatas[i].materials==null?"":totalDatas[i].materials;  //材料
                                var treatT = totalDatas[i].treat==null?"":totalDatas[i].treat  //处理
                                var timeT = totalDatas[i].definitionTime==null?"":totalDatas[i].definitionTime;  //采集时间
                                var taxonomyT  = totalDatas[i].taxonomy==null?"":totalDatas[i].taxonomy;  //分类地位
                                var myceliaPhenotypeT = totalDatas[i].myceliaPhenotype==null?"":totalDatas[i].myceliaPhenotype;  //菌丝形态
                                var myceliaDiameterT = totalDatas[i].myceliaDiameter==null?"":totalDatas[i].myceliaDiameter;  //菌丝直径
                                var myceliaColorT = totalDatas[i].myceliaColor==null?"":totalDatas[i].myceliaColor;  //菌丝颜色
                                var sporesColorT = totalDatas[i].sporesColor ==null?"":totalDatas[i].sporesColor;  //孢子颜色
                                var sporesShapeT = totalDatas[i].sporesShape==null?"":totalDatas[i].sporesShape;  //孢子形态
                                var clampConnectionT = totalDatas[i].clampConnection==null?"":totalDatas[i].clampConnection;   //锁状联合
                                var pileusPhenotypeT = totalDatas[i].pileusPhenotype==null?"":totalDatas[i].pileusPhenotype;  //菌盖形态
                                var pileusColorT = totalDatas[i].pileusColor==null?"":totalDatas[i].pileusColor;  //菌盖颜色
                                var stipePhenotypeT = totalDatas[i].stipePhenotype==null?"":totalDatas[i].stipePhenotype;  //菌柄形态
                                var stipeColorT = totalDatas[i].stipeColor==null?"":totalDatas[i].stipeColor;  //菌柄颜色
                                var fruitbodyColorT = totalDatas[i].fruitbodyColor==null?"":totalDatas[i].fruitbodyColor;  //子实体颜色
                                var fruitbodyTypeT = totalDatas[i].fruitbodyType==null?"":totalDatas[i].fruitbodyType;  //子实体形态
                                var illuminationT = totalDatas[i].illumination==null?"":totalDatas[i].illumination;  //光照
                                var collariumT = totalDatas[i].collarium==null?"":totalDatas[i].collarium;  //菌环
                                var volvaT = totalDatas[i].volva==null?"":totalDatas[i].volva;  //菌托
                                var velumT = totalDatas[i].velum==null?"":totalDatas[i].velum;  //菌幕
                                var sclerotiumT = totalDatas[i].sclerotium==null?"":totalDatas[i].sclerotium;  //菌核
                                var strainMediumT = totalDatas[i].strainMedium==null?"":totalDatas[i].strainMedium;  //菌种培养基
                                var mainSubstrateT = totalDatas[i].mainSubstrate==null?"":totalDatas[i].mainSubstrate;  //主要栽培基质
                                var afterRipeningStageT = totalDatas[i].afterRipeningStage==null?"":totalDatas[i].afterRipeningStage;  //后熟期
                                var primordialStimulationFruitbodyT = totalDatas[i].primordialStimulationFruitbody==null?"":totalDatas[i].primordialStimulationFruitbody;  //原基刺激&子实体
                                var reproductiveModeT = totalDatas[i].reproductiveMode==null?"":totalDatas[i].reproductiveMode;  //生殖方式
                                var lifestyleT = totalDatas[i].lifestyle==null?"":totalDatas[i].lifestyle;  //生活方式
                                var preservationT = totalDatas[i].preservation==null?"":totalDatas[i].preservation;  //保藏方法
                                var domesticationT = totalDatas[i].domestication==null?"":totalDatas[i].domestication;  //驯化
                                var nuclearPhaseT = totalDatas[i].nuclearPhase==null?"":totalDatas[i].nuclearPhase;  //核相
                                var matingTypeT = totalDatas[i].matingType==null?"":totalDatas[i].matingType;  //交配型

                                var tr = "<tr>" +
                                    "<td class='paramTag runNoT'>" + runNoT+
                                    "</td><td class='paramTag genoTypeT'>" + genoTypeT+
                                    "</td><td class='paramTag scientificNameT'>" + scientificNameT+
                                    "</td><td class='paramTag sampleIdT'>" + sampleIdT+
                                    "</td><td class='paramTag strainNameT'>" + strainNameT +
                                    "</td><td class='paramTag localityT'>" + localityT +
                                    "</td><td class='paramTag preservationLocationT'>" + preservationLocationT +
                                    "</td><td class='paramTag typeT'>" + typeT +
                                    "</td><td class='paramTag environmentT'>" + environmentT +
                                    "</td><td class='paramTag materialsT'>" + materialsT +
                                    "</td><td class='paramTag treatT'>" + treatT +
                                    "</td><td class='paramTag timeT'>" + timeT +
                                    "</td><td class='paramTag taxonomyT'>" + taxonomyT +
                                    "</td><td class='paramTag myceliaPhenotypeT'>" + myceliaPhenotypeT +
                                    "</td><td class='paramTag myceliaDiameterT'>" +myceliaDiameterT +
                                    "</td><td class='paramTag myceliaColorT'>" + myceliaColorT +
                                    "</td><td class='paramTag sporesColorT'>" + sporesColorT +
                                    "</td><td class='paramTag sporesShapeT'>" + sporesShapeT +
                                    "</td><td class='paramTag clampConnectionT'>" + clampConnectionT +
                                    "</td><td class='paramTag pileusPhenotypeT'>" + pileusPhenotypeT +
                                    "</td><td class='paramTag pileusColorT'>" + pileusColorT +
                                    "</td><td class='paramTag stipePhenotypeT'>" + stipePhenotypeT +
                                    "</td><td class='paramTag stipeColorT'>" + stipeColorT +
                                    "</td><td class='paramTag fruitbodyColorT'>" + fruitbodyColorT +"</td>" +
                                    "<td class='paramTag fruitbodyTypeT'>" + fruitbodyTypeT +"</td>"+
                                    "<td class='paramTag illuminationT'>" + illuminationT +"</td>"+
                                    "<td class='paramTag collariumT'>" + collariumT +"</td>"+
                                    "<td class='paramTag volvaT'>" + volvaT +"</td>"+
                                    "<td class='paramTag velumT'>" + velumT +"</td>"+
                                    "<td class='paramTag sclerotiumT'>" + sclerotiumT +"</td>"+
                                    "<td class='paramTag strainMediumT'>" + strainMediumT +"</td>"+
                                    "<td class='paramTag mainSubstrateT'>" + mainSubstrateT +"</td>"+
                                    "<td class='paramTag afterRipeningStageT'>" + afterRipeningStageT +"</td>"+
                                    "<td class='paramTag primordialStimulationFruitbodyT'>" + primordialStimulationFruitbodyT +"</td>"+
                                    "<td class='paramTag reproductiveModeT'>" + reproductiveModeT +"</td>"+
                                    "<td class='paramTag lifestyleT'>" + lifestyleT +"</td>"+
                                    "<td class='paramTag domesticationT'>" + domesticationT +"</td>"+
                                    "<td class='paramTag nuclearPhaseT'>" + nuclearPhaseT +"</td>"+
                                    "<td class='paramTag preservationT'>" + preservationT +"</td>"+
                                    "<td class='paramTag matingTypeT'>" + matingTypeT +"</td>"+
                                    "</tr>"
                                var $tbody =  $("#snpinfoTable table tbody")
                                $tbody.append(tr);
                            }
                            if(stateType == "ind"){
                                var genos = $("#snpinfoTable table tbody tr");
                                $.each(genos,function (i,item){
                                    var deleteEle = $(item).find("td.genoTypeT").attr("class").split(" ")[1];
//                                    $(item).find("td.genoTypeT").hide();
                                    if(deleteEle == "genoTypeT"){
                                        $(item).find("td.genoTypeT").remove();
                                    }
                                })
                            }else{

                            }
                        }
                    };
                    fn&&fn();
                    // 分页
                    laypage({
                        cont: $('#snpInforsPage .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(count /  page.pageSize), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        /*skin: '#5c8de5',*/
                        skin: '#0f9145',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(count /  page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                var tmp = snpGetParams(changeParam);
                                currPageNumber = obj.curr;
                                tmp.pageNum = obj.curr;
                                tmp.pageSize = paramData.pageSize;
                                tmp.judgeAllele = $(".changeTagColor").text().split(" ")[0];
                                getData(tmp,obj.curr,filterParamer);
                            }
                        }
                    });
//                    $("#total-page-count span").html(count);
                },
                error:function (error){
                    console.log(error);
                }
            })
        }
        // 详情页 搜索按钮点击事件
        $(".searBtn").click(function (){
            var searchId = $(".searchBox").find("input").val().trim();
            $.ajax({
                type:"GET",
                url:ctxRoot + "/dna/findSampleById",
                data:{id:searchId},
                dataType:"json",
                success:function (result){
                    if(result.code !=0){
                        $(".searchBox input").addClass("inputError")
                        $("#errorBoxShow").show();
                    }else {
                        if($(".searchBox input").hasClass("inputError")){
                            $(".searchBox input").removeClass("inputError");
                        };
                        if(!$("#errorBoxShow").is(":hidden")){
                            $("#errorBoxShow").hide();
                        }
                        // indel 隐藏 饼图和genoType;
                        if(result.data.type=="INDEL"){
                            stateType = "ind";
                            if(($("#snpinfoTable table th.genoTypeT").get(0))){
                                $("#snpinfoTable table th.genoTypeT").remove();
                            };
                            if(($("#selectedDetails .genoType").get(0))){
                                $("#selectedDetails .genoType").parent().remove();
                            };
                            if(!$("#pieShow").is(":hidden")){
                                $("#pieShow").css("border-bottom","1px solid #ffffff").hide();
                            }
                            $(".pieShowTop").css("border-top","1px solid #ffffff");


                            //  =======
                            id = result.data.INDELData.id;
                            major =result.data.INDELData.majorallen;
                            minor =result.data.INDELData.minorallen;
                            $(".snpTop").text(result.data.INDELData.id);
                            $(".snpId").text(result.data.INDELData.id);
                            $(".snpCon").text(result.data.INDELData.consequencetype);
                            $(".snpChr").text(result.data.INDELData.chr);
                            $(".snpPos").text(result.data.INDELData.pos);
                            $(".snpRef").text(result.data.INDELData.ref);
                            $(".snpMaj").text(result.data.INDELData.majorallen);
                            $(".snpMio").text(result.data.INDELData.minorallen);
                            $(".snpQue").text(result.data.major + "%");
                            changeParam = major;
                            var data = snpGetParams(changeParam);
                            data.pageNum = paramData.pageNum;
                            data.pageSize = paramData.pageSize;
                            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
                            getData(data,paramData.pageNum,filterParamer);
//
                        }else {
                            stateType = "snp";
                            if($("#pieShow").is(":hidden")){
                                $("#pieShow").css("border-bottom","1px solid #E4E4E4").show();
                            };
                            if(!$("#snpinfoTable table th.genoTypeT").get(0)){
                                var th ="<th class='param genoTypeT'> GenoType  <img src='${ctxStatic}/images/arrow-drop-down.png' alt='logo'> <div class='inputComponent'>"+
                                    "                            <input type='text' placeholder='请输入' class='pubescenceColorI inputStyle'>" +
                                    "                            <p>" +
                                    "                                <a href='javascript:void(0);' class='btnCancel'>取消</a>" +
                                    "                                <a href='javascript:void(0);' class='btnConfirmInfo'>确定</a>" +
                                    "                            </p>" +
                                    "                        </div>";
                                $("#snpinfoTable table th.cultivarT").after(th);
                            };
                            if(!$("#selectedDetails .genoType").get(0)){
                                var li = "<li>" +
                                            "<input type='checkbox' name='genoType' class='genoType' checked='checked'> GenoType" +
                                   " </li>";
                                $("#selectedDetails li input.cultivar").parent().after(li);
                            };
                            $(".pieShowTop").css("border-top","1px solid #E4E4E4");

                            ///=====
                            id = result.data.snpData.id;
                            major =result.data.snpData.majorallen;
                            minor =result.data.snpData.minorallen;
                            $(".snpTop").text(result.data.snpData.id);
                            $(".snpId").text(result.data.snpData.id);
                            $(".snpCon").text(result.data.snpData.consequencetype);
                            $(".snpChr").text(result.data.snpData.chr);
                            $(".snpPos").text(result.data.snpData.pos);
                            $(".snpRef").text(result.data.snpData.ref);
                            $(".snpMaj").text(result.data.snpData.majorallen);
                            $(".snpMio").text(result.data.snpData.minorallen);
                            $(".snpQue").text(result.data.major + "%");
                            changeParam = major;
                            var data = snpGetParams(changeParam);
                            data.pageNum = paramData.pageNum;
                            data.pageSize = paramData.pageSize;
                            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
                            getData(data,paramData.pageNum,filterParamer);
                            //重新画图
                            // 这里要判断是indel 进来的还是snp 进来的
                            AA = result.data.RefAndRefPercent;
                            TT = result.data.totalAltAndAltPercent;
                            AT = result.data.totalRefAndAltPercent;
                            var n1 =result.data.snpData.ref + result.data.snpData.ref;
                            var n2 =result.data.snpData.ref + result.data.snpData.alt;
                            var n3 =result.data.snpData.alt +result.data.snpData.alt;
                            drawPie(AA,TT,AT,n1,n2,n3);
                        }

                    }
                },
                error:function (error){
                    console.log(error);
                }
            });
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
        $("#snpinfoTable thead").on("mouseover"," th",function (){
            $(this).find(".inputComponent").show();
        }).on("mouseleave"," th",function (){
//        }).mouseleave(function (){
            $(this).find(".inputComponent").hide();
        })
        // 筛选取消按钮 样式
        $("#snpinfoTable .inputComponent").on("click",".btnCancel",function (){
//        $("#snpinfoTable .inputComponent .btnCancel").click(function (){
            $(this).parent().parent().find("input").val("");
            $(this).parent().parent().hide();
        })
        // 群体信息
        var popuSelectedVal="";
        // 获取参数
        function snpGetParams(type){
            var datas={
                changeParam:type,
                snpId: $("#trInfos td.snpId").text(),
                runNo:$(".runNoI").val(),  // 测序样品编号
                scientificName:$(".scientificNameI").val(),// 物种名称
                sampleId:$(".sampleIdI").val(), // 编号
                strainName:$(".strainNameI").val(), // 菌株名称
                locality:$(".localityI").val(), // 地理位置
                preservationLocation:$(".preservationLocationI").val(),//保藏地点
                type:$(".typeI").val(),//类型
                environment:$(".environmentI").val(),//培养环境
                materials:$(".materialsI").val(), //材料
                treat:$(".treatI").val(),//处理
                definitionTime:$(".timeI").val(),//采集时间
                taxonomy:$(".taxonomyI").val(),//分类地位
                myceliaPhenotype:$(".myceliaPhenotypeI").val(),//菌丝形态
                myceliaDiameter:$(".myceliaDiameterI").val(),//菌丝直径
                myceliaColor:$(".myceliaColorI").val(),//菌丝颜色
                sporesColor:$(".sporesColorI").val(),//孢子颜色
                sporesShape:$(".sporesShapeI").val(),//孢子形态
                clampConnection:$(".clampConnectionI").val(),//锁状联合
                pileusPhenotype:$(".pileusPhenotypeI").val(),//菌盖形态
                pileusColor:$(".pileusColorI").val(),//菌盖颜色
                stipePhenotype:$(".stipePhenotypeI").val(),//菌柄形态
                stipeColor:$(".stipeColorI").val(),//菌柄颜色
                fruitbodyColor:$(".fruitbodyColorI").val(),//子实体颜色
                fruitbodyType:$(".fruitbodyTypeI").val(),//子实体形态
                illumination:$(".illuminationI").val(),//光照
                collarium:$(".collariumI").val(),//菌环
                volva:$(".volvaI").val(),//菌托
                velum:$(".velumI").val(),//菌幕
                sclerotium:$(".sclerotiumI").val(),//菌核
                strainMedium:$(".strainMediumI").val(),//菌种培养基
                mainSubstrate:$(".mainSubstrateI").val(),//主要栽培基质
                afterRipeningStage:$(".afterRipeningStageI").val(),//后熟期
                primordialStimulationFruitbody:$(".primordialStimulationFruitbodyI").val(),//原基刺激&子实体
                reproductiveMode:$(".reproductiveModeI").val(),//生殖方式
                lifestyle:$(".lifestyleI").val(),//生活方式
                preservation:$(".preservationI").val(),//保藏方法
                domestication:$(".domesticationI").val(),//驯化
                nuclearPhase:$(".nuclearPhaseI").val(),//核相
                matingType:$(".matingTypeI").val(),//交配型
                isPage:1
            };
            return datas;
        }
        // 获取表格数据
        $("#snpinfoTable").on("click",".btnConfirmInfo",function (){
//        $("#snpinfoTable .btnConfirmInfo").click(function (){
            if($("#snpinfoTable .genoTypeT input").val()){
                changeParam = $("#snpinfoTable .genoTypeT input").val();
            };
            var selectedDatas1 = snpGetParams(changeParam);

            selectedDatas1.pageNum = paramData.pageNum;
            selectedDatas1.pageSize = paramData.pageSize;
            selectedDatas1.judgeAllele = $(".changeTagColor").text().split(" ")[0];
            console.log(selectedDatas1)
            getData(selectedDatas1,selectedDatas1.pageNum,filterParamer);
        })
        // 导出数据部分
        $('#tableSet').click(function (){
            $(".selecting").show();
            $("#operate").show();
            $(this).hide();
            $(".changeTab").addClass("changeTabStyle")
            $("#snpinfoTable table").addClass("changeTableStyle")
        })
        $(".packUp").click(function (){
            $(".selecting").hide();
            $("#operate").hide();
            $("#tableSet").show();
            $("#tableSet").css("margin-right","10px");
            if($(".changeTab").hasClass("changeTabStyle")){
                $(".changeTab").removeClass("changeTabStyle")
            };
            if($("#snpinfoTable table").hasClass("changeTableStyle")){
                $("#snpinfoTable table").removeClass("changeTableStyle")

            }
        })
        // 清空所有选中的
        $(".selectedAll").click(function  (){
            var lists = $("#selectedDetails li");
            var status = $("#SelectAllBox").prop("checked");
            if(status){
                $("#SelectAllBox").removeAttr("checked");
            }
            for(var i=0;i<lists.length;i++){
                var $input = $(lists[i]).find("input");
                if($input.is(":checked")){
                    $input.removeAttr("checked");
                }
            }
        })
        var exportTitles = [];
        function initExportTitles (){
            var lists = $("#selectedDetails li");
            $.each(lists,function (i,item){
                var $input = $(item).find("input");
                var classVal = $input.attr("name");
                exportTitles.push(classVal);
            })
        };
        initExportTitles();
        // 确定按钮（过滤条件）
        $("#operate .sure").click(function (){
            filterParamer();
//            exportTitles = [];
//            initExportTitles();
//            var lists = $("#selectedDetails li");
//            for(var i=0;i<lists.length;i++){
//                var $input = $(lists[i]).find("input");
//                var classVal = $input.attr("name");
//                if(!$input.is(":checked")){
//                    var idx = exportTitles.indexOf(classVal);
//                    exportTitles.splice(idx,1);
//                    var newClassVal = "." + classVal + "T";
//                    $("#snpinfoTable thead").find(newClassVal).hide();
//                    $("#snpinfoTable tbody").find(newClassVal).hide();
//                }
//                else {
//                    var newClassVal = "." + classVal + "T";
//                    if($("#snpinfoTable thead").find(newClassVal).is(":hidden")){
//                        $("#snpinfoTable thead").find(newClassVal).show();
//                        $("#snpinfoTable tbody").find(newClassVal).show();
//                    }
//                }
//            }
        });
        // 每次请求数据都要检查 当前表格设置的筛选条件
        function filterParamer(){
            exportTitles = [];
            initExportTitles();
            var lists = $("#selectedDetails li");
            for(var i=0;i<lists.length;i++){
                var $input = $(lists[i]).find("input");
                var classVal = $input.attr("name");
                if(!$input.is(":checked")){
                    var idx = exportTitles.indexOf(classVal);
                    exportTitles.splice(idx,1);
                    var newClassVal = "." + classVal + "T";
                    $("#snpinfoTable thead").find(newClassVal).hide();
                    $("#snpinfoTable tbody").find(newClassVal).hide();
                }
                else {
                    var newClassVal = "." + classVal + "T";
                    if($("#snpinfoTable thead").find(newClassVal).is(":hidden")){
                        $("#snpinfoTable thead").find(newClassVal).show();
                        $("#snpinfoTable tbody").find(newClassVal).show();
                    }
                }
            }
        }
        //选中状态代码封装
        function checkStatus(bool){
            var lists = $("#selectedDetails li");
            if(bool){
                for(var i=0;i<lists.length;i++){
                    var $input = $(lists[i]).find("input");
                    if(!$input.is(":checked")){
                        $input.get(0).checked = true;
                    }
                }
            }else {
                for(var i=0;i<lists.length;i++){
                    var $input = $(lists[i]).find("input");
                    if($input.is(":checked")){
                        $input.removeAttr("checked");
                    }
                }
            }
        }
        // 全选

        $("#SelectAllBox").click(function (){
            var status = $(this).prop("checked");
            checkStatus(status);
        })
        // 新增group 表
        $(".popMoveOnNewAdd").mouseover(function (){
            $(".popNamesNewAdd").show();
        }).mouseleave(function (){
            $(".popNamesNewAdd").hide()
        })
        // 点击群体时触发请求，跟后端协调字段名成是否正确
        $(".popNamesNewAdd li").click(function (){
            $(".popNamesNewAdd").hide();
            popuSelectedVal = $(this).text();
            var data =snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = paramData.pageSize;
            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
            getData(data,paramData.pageNum,filterParamer);
        });

        // pageSize 事件
//        $("#snpInforsPage select").change(function (e){
//            var val = $(this).val();
//            var data =snpGetParams(changeParam);
//            data.pageNum = paramData.pageNum;
//            data.pageSize = val;
//            data.pageNum = currPageNumber;
//            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
//            getData(data,data.pageNum);
//
//        });

        // 改写 pageSize事件
        $("#snpInforsPage ul li").click(function (){
            debugger;
            var val = $(this).text();
            var data =snpGetParams(changeParam);
            data.pageNum = paramData.pageNum;
            data.pageSize = val;
            data.pageNum = currPageNumber;
            data.judgeAllele = $(".changeTagColor").text().split(" ")[0];
            getData(data,data.pageNum);
        })
        // 表格导出
        $("#exportData").click(function (){
            debugger;
            filterParamer()
            console.log(exportTitles);
            var titleData = snpGetParams(changeParam);
            titleData.isPage = 0;
            var datas = JSON.stringify({
                    "titles":exportTitles.join(","),
                    "condition":titleData,
                    "judgeAllele":$(".changeTagColor").text().split(" ")[0]
                })
                console.log(datas);
            $.ajax({
                type:"POST",
                url:CTXROOT + "/dna/IdDetailExport",
                data:datas,
                dataType: "json",
                contentType: "application/json",
                success:function (result){
                    window.location.href = result;
                },
                error:function (error){
                    console.log(error);
                }
            })
        })
    })
</script>
</html>
