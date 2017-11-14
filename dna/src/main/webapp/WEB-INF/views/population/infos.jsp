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
    <script>
        var CTXROOT = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/jquery-ui.js"></script>
    <script src="${ctxStatic}/js/population.js"></script>
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
            <div class="selecting">
                <p>
                    表格内容：
                </p>
                <div id="selectedDetails">
                    <ul>
                        <li>
                            <input type="checkbox" name="cultivar" class="cultivar"> 品种名
                        </li>
                        <li>
                            <%--无--%>
                            <input type="checkbox" name="population" class="population"> 群体
                        </li>
                        <li>
                            <input type="checkbox" name="species" class="species"> 物种
                        </li>
                        <li>
                            <input type="checkbox" name="locality" class="locality"> 位置
                        </li>
                        <li>
                            <input type="checkbox" name="sampleName" class="sampleName"> 样品名
                        </li>
                        <li>
                            <input type="checkbox" name="weightPer100seeds" class="weightPer100seeds"> 百粒重
                        </li>
                        <li>
                            <input type="checkbox" name="protein" class="protein"> 蛋白质含量
                        </li>
                        <li>
                            <input type="checkbox" name="maturityDate" class="maturityDate"> 熟期
                        </li>
                        <li>
                            <input type="checkbox" name="height" class="height"> 株高
                        </li>
                        <li>
                            <input type="checkbox" name="seedCoatColor" class="seedCoatColor"> 种皮色
                        </li>
                        <li>
                            <input type="checkbox" name="hilumColor" class="hilumColor"> 种脐色
                        </li>
                        <li>
                            <input type="checkbox" name="cotyledonColor" class="cotyledonColor"> 子叶色
                        </li>
                        <li>
                            <input type="checkbox" name="flowerColor" class="flowerColor"> 花色
                        </li>
                        <li>
                            <input type="checkbox" name="podColor" class="podColor"> 荚色
                        </li>
                        <li>
                            <input type="checkbox" name="pubescenceColor" class="pubescenceColor"> 茸毛色
                        </li>
                        <li>
                            <%--无--%>
                            <input type="checkbox" name="yield" class="yield"> 产量
                        </li>
                        <li>
                            <input type="checkbox" name="upperLeafletLength" class="upperLeafletLength"> 顶端小叶长度
                        </li>
                        <li>
                            <input type="checkbox" name="linoleic" class="linoleic">亚油酸
                        </li>
                        <li>
                            <input type="checkbox" name="linolenic" class="linolenic"> 亚麻酸
                        </li>
                        <li>
                            <input type="checkbox" name="oleic" class="oleic"> 油酸
                        </li>
                        <li>
                            <input type="checkbox" name="palmitic" class="palmitic"> 软脂酸
                        </li>
                        <li>
                            <input type="checkbox" name="stearic" class="stearic"> 硬脂酸
                        </li>
                    </ul>
                    <div>
                        <input type="checkbox" id="SelectAllBox"> 全选
                    </div>
                </div>
            </div>
            <div class="changeStauts">
                <div class="sets">
                    <p id="tableSet">表格设置</p>
                    <p id="exportData">导出数据</p>
                </div>
                <div id="operate">
                    <p class="sure">确定</p>
                    <div class="opOthers">
                        <p class="selectedAll">清空</p>
                        <p class="packUp">收起
                            <img src="${ctxStatic}/images/packUp.png" alt="logo" style="width:12px;margin-left:5px;margin-top: -3px;">
                        </p>
                    </div>

                </div>
            </div>
        </div>
        <div id="tableShow">
            <table border="1" cellspacing="0" cellpadding="5">
                <thead style="overflow-x: scroll;">
                    <tr style="background: #F5F8FF;">
                        <th class="param cultivarT">品种名
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                            <div>
                                <input type="text" placeholder="请输入" class="cultivarI">
                                <p class="inputComponent">
                                    <a href="javascript:void(0);" class="btnCancel">取消</a>
                                    <a href="javascript:void(0);" class="btnConfirmInfo">确定</a>
                                </p>
                            </div>
                        </th>
                        <th class="param populationT">群体
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param speciesT">物种
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param localityT">位置
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param sampleNameT">样品名
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param weightPer100seedsT">百粒重
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param proteinT">蛋白质含量
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param maturityDateT">熟期
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param heightT">株高
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param seedCoatColorT">种皮色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param hilumColorT">种脐色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param cotyledonColorT">子叶色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param flowerColorT">花色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param podColorT">荚色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param pubescenceColorT">茸毛色
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param yieldT">产量
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param upperLeafletLengthT">顶端小叶长度
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param linoleicT">亚油酸
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param linolenicT">亚麻酸
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param oleicT">油酸
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param palmiticT">软脂酸
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                        <th class="param stearicT">硬脂酸
                            <img src="${ctxStatic}/images/arrow-drop-down.png" alt="logo">
                        </th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="param cultivarT">品种名
                    </th>
                    <th class="param populationT">群体
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
                <tr>
                    <th class="param cultivarT">品种名
                    </th>
                    <th class="param populationT">群体
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
                <tr>
                    <th class="param cultivarT">品种名
                    </th>
                    <th class="param populationT">群体
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
                <tr>
                    <th class="param cultivarT">品种名
                    </th>
                    <th class="param populationT">群体
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
                </tbody>
            </table>
        </div>
        <%--弹出框--%>
        <div id="popTips">
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

</body>
</html>
