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
                            <input type="checkbox" name="cultivar" class="cultivar" checked="checked"> 品种名
                        </li>
                        <li>
                            <%--无--%>
                            <input type="checkbox" name="population" class="population" checked="checked"> 群体
                        </li>
                        <li>
                            <input type="checkbox" name="species" class="species" checked="checked"> 物种
                        </li>
                        <li>
                            <input type="checkbox" name="locality" class="locality" checked="checked"> 位置
                        </li>
                        <li>
                            <input type="checkbox" name="sampleName" class="sampleName" checked="checked"> 样品名
                        </li>
                        <li>
                            <input type="checkbox" name="weightPer100seeds" class="weightPer100seeds" checked="checked"> 百粒重(g)
                        </li>
                        <li>
                            <input type="checkbox" name="protein" class="protein" checked="checked"> 蛋白质含量
                        </li>
                        <li>
                            <input type="checkbox" name="oil" class="oil" checked="checked"> 含油量
                        </li>
                        <li>
                            <input type="checkbox" name="maturityDate" class="maturityDate" checked="checked"> 熟期组
                        </li>
                        <li>
                            <input type="checkbox" name="height" class="height" checked="checked"> 株高
                        </li>
                        <li>
                            <input type="checkbox" name="seedCoatColor" class="seedCoatColor" checked="checked"> 种皮色
                        </li>
                        <li>
                            <input type="checkbox" name="hilumColor" class="hilumColor" checked="checked"> 种脐色
                        </li>
                        <li>
                            <input type="checkbox" name="cotyledonColor" class="cotyledonColor" checked="checked"> 子叶色
                        </li>
                        <li>
                            <input type="checkbox" name="flowerColor" class="flowerColor" checked="checked"> 花色
                        </li>
                        <li>
                            <input type="checkbox" name="podColor" class="podColor" checked="checked"> 荚色
                        </li>
                        <li>
                            <input type="checkbox" name="pubescenceColor" class="pubescenceColor" checked="checked"> 茸毛色
                        </li>
                        <li>
                            <%--无--%>
                            <input type="checkbox" name="yield" class="yield" checked="checked"> 茸毛色
                        </li>
                        <li>
                            <input type="checkbox" name="upperLeafletLength" class="upperLeafletLength" checked="checked"> 顶端小叶长度
                        </li>
                        <li>
                            <input type="checkbox" name="linoleic" class="linoleic" checked="checked">亚油酸
                        </li>
                        <li>
                            <input type="checkbox" name="linolenic" class="linolenic" checked="checked"> 亚麻酸
                        </li>
                        <li>
                            <input type="checkbox" name="oleic" class="oleic" checked="checked"> 油酸
                        </li>
                        <li>
                            <input type="checkbox" name="palmitic" class="palmitic" checked="checked"> 软脂酸
                        </li>
                        <li>
                            <input type="checkbox" name="stearic" class="stearic" checked="checked"> 硬脂酸
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
                        <th class="param populationT popMoveOnNewAdd" style="position:relative;">群体
                            <img src="/dna/static/images/arrow-drop-down.png" alt="logo" style="width: 15px;vertical-align: middle;">
                            <div class="popNamesNewAdd">
                                <ul style="margin-top:10px;">
                                    <li>Q1</li>
                                    <li>Q2</li>
                                    <li>Q3</li>
                                    <li>Q4</li>
                                    <li>Q5</li>
                                    <li>Q6</li>
                                    <li>Q7</li>
                                    <li>Q8</li>
                                    <li>Q9</li>
                                    <li>Q10</li>
                                </ul>
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
                        <th class="param maturityDateT">熟期组
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
        <div class="checkbox-item-tab" id="sysPopulations">
            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
        </div>
        <%--laypage 分页 end --%>
        <%--弹出框--%>
        <div id="popTips" style="display: none;">
            <div class="tipTop">
                <p class="tipTopCnt">Group 1</p>
                <%--<p class="closeBtn"><img src="${ctxStatic}/images/close.png"></p>--%>
            </div>
            <p class="titCnt">群体分类生物学意义</p>
            <p class="groupCnt">
                111假设所有的个体来源于n个祖先，使用贝叶斯模型的计算方法依次模拟在K = 1~n 的情况下，推算每个个体的血统构成以及群体的分群情况，得到最大似然值（likelihood）最大并且亚群数量最少的模拟结果，这时K值往往最接近群体真实的亚群分布。分析得出：全部大豆样本来自10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而不同的亚群之间则亲缘关系稍远，在图中相同颜色的
            </p>
        </div>
    </div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>
