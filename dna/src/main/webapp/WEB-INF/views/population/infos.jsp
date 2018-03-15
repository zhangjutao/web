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
    <link rel="stylesheet" href="${ctxStatic}/css/population.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>

    <script>
        var CTXROOT = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/jquery-ui.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <script src="${ctxStatic}/js/population.js"></script>
    <style>
        #tableShow>table{
            border: none;
        }
        .total-page-count{
            position: relative;
            top:-4px;
        }
    </style>
</head>
<body>
<dna:dna-header />
    <div class="container" style="background: #fff;position:relative;">
        <div id="topTitle">
            <p>
                <img src="${ctxStatic}/images/systree.png" alt="logo" style="height:31px;">
            </p>
           <p class="systree">系统进化树</p>
        </div>
        <p class="popDes">
            利用了近1000个样本的大豆群体的SNP信息，使用STRUCTURE软件推断其亚群结构：假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群，在各亚群内的个体服从Hardy－Weinberg 定律；之后采用邻接法（Neighbor－Joining）计算亚群之间的距离，构建系统进化树（Phylogenetic Tree，又称Evolutionary Tree）展示了本数据库中大豆样本之间的进化历程和亲缘关系。
        </p>
        <div id="groups">
            <ul>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 1</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 2</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 3</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 4</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 5</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 6</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 7</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 8</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 9</p>
                </li>
                <li>
                    <img src="${ctxStatic}/images/Group.png" alt="logo">
                    <p class="groupDes">Group 10</p>
                </li>
            </ul>
        </div>
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
        <div id="tableShow">
            <table border="1" cellspacing="0" cellpadding="5" style="overflow: scroll; min-height:100px;">
                <thead style="overflow-x: scroll;width:730px;">
                <tr>
                    <th class="param" style="width:42px;"></th>
                    <th class="param">测序样品编号
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="runNo inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">物种名称
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="scientificName inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">编号
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sampleId inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌株名称
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="strainName inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">地理位置
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="locality inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">保藏地点
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入"
                                   class="preservationLocation inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">类型
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="type inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">培养环境
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="environment inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">材料
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="materials inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">处理
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="treat inputStyle">
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
                    <th class="param">时间
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="time inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">分类地位
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="taxonomy inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌丝形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaPhenotype inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌丝直径
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaDiameter inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌丝颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="myceliaColor inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">孢子颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sporesColor inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">孢子形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sporesShape inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">锁状联合
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="clampConnection inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌盖形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="pileusPhenotype inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌盖颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="pileusColor inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌柄形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="stipePhenotype inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌柄颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="stipeColor inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">子实体颜色
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="fruitbodyColor inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">子实体形态
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="fruitbodyType inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">光照
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="illumination inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌环
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="collarium inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌托
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="volva inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌幕
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="velum inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">菌核
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="sclerotium inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">菌种培养基
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="strainMedium inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">主要栽培基质
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="mainSubstrate inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">后熟期
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="afterRipeningStage inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">原基刺激&子实体
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入"
                                   class="primordialStimulationFruitbody inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">生殖方式
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="reproductiveMode inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">生活方式
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="lifestyle inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">保藏方法
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="preservation inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">驯化
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="domestication inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>

                    <th class="param">核相
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="nuclearPhase inputStyle">
                            <p>
                                <a href="javascript:void(0);" class="btnCancel">取消</a>
                                <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                            </p>
                        </div>
                    </th>
                    <th class="param">交配型
                        <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        <div class="inputComponent">
                            <input type="text" placeholder="请输入" class="matingType inputStyle">
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
        <div class="checkbox-item-tab" id="sysPopulations">
            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
        </div>
        <%--laypage 分页 end --%>
        <%--弹出框--%>
        <div id="popTips" style="display: none;">
            <div class="tipTop">
                <p class="tipTopCnt">Group 1</p>
                <p class="closeBtn"><img src="${ctxStatic}/images/close.png"></p>
            </div>
            <p class="titCnt">群体分类生物学意义</p>
            <p class="groupCnt">
                111假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的
            </p>
        </div>
    </div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
</body>
</html>
