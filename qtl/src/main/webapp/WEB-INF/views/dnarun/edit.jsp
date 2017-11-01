<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<%@ include file="/WEB-INF/views/include/dashboard/header.jsp" %>

<body data-type="widgets" class="theme-white">
    <div class="am-g tpl-g">
        <!-- 头部 -->
        <%@ include file="/WEB-INF/views/include/dashboard/inside-header.jsp" %>

        <!-- 侧边导航栏 -->
        <div class="left-sidebar">

            <%@ include file="/WEB-INF/views/include/dashboard/nav.jsp" %>

        </div>


        <!-- 内容区域 -->
        <div class="tpl-content-wrapper">
            <div class="row-content am-cf">
                <div class="row">
                    <div class="am-u-sm-12 am-u-md-12 am-u-lg-12">
                        <div class="widget am-cf">
                            <div class="widget-head am-cf">
                                <div class="widget-title  am-cf">DNA_samples</div>
                            </div>
                            <div class="widget-body  am-fr">

                                <form class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="dnaRun" method="post" action="${ctxroot}/dnarun/saveedit">
                                    <div class="am-form-group" style="display:none">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">id</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="id" name="id" value="${dnaRun.id}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">runNo</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="runNo" name="runNo" value="${dnaRun.runNo}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">species</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="species" name="species" value='${dnaRun.species}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">sampleName</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="sampleName" name="sampleName" value="${dnaRun.sampleName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">cultivar</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="cultivar" name="cultivar" value="${dnaRun.cultivar}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">plantName</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="plantName" name="plantName" value="${dnaRun.plantName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">locality</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="locality" name="locality" value="${dnaRun.locality}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">protein</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="protein" name="protein" value="${dnaRun.protein}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">oil</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="oil" name="oil" value="${dnaRun.oil}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">linoleic</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="linoleic" name="linoleic" value='${dnaRun.linoleic}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">linolenic</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="linolenic" name="linolenic" value='${dnaRun.linolenic}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">oleic</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="oleic" name="oleic" value='${dnaRun.oleic}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">palmitic</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="palmitic" name="palmitic" value='${dnaRun.palmitic}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">stearic</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="stearic" name="stearic" value='${dnaRun.stearic}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">height</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="height" name="height" value='${dnaRun.height}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">flowerColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="flowerColor" name="flowerColor" value="${dnaRun.flowerColor}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">hilumColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="hilumColor" name="hilumColor" value="${dnaRun.hilumColor}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">podColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="podColor" name="podColor" value="${dnaRun.podColor}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">pubescenceColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="pubescenceColor" name="pubescenceColor" value='${dnaRun.pubescenceColor}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">seedCoatColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="seedCoatColor" name="seedCoatColor" value="${dnaRun.seedCoatColor}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">cotyledonColor</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="cotyledonColor" name="cotyledonColor" value="${dnaRun.cotyledonColor}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">weightPer100seeds</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="weightPer100seeds" name="weightPer100seeds" value='${dnaRun.weightPer100seeds}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">upperLeafletLength</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="upperLeafletLength" name="upperLeafletLength" value='${dnaRun.upperLeafletLength}' class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">floweringDate</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="floweringDate" name="floweringDate" value="${dnaRun.floweringDate}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">maturityDate</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="maturityDate" name="maturityDate" value="${dnaRun.maturityDate}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">yield</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="number" step="0.01" id="yield" name="yield" value="${dnaRun.yield}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <div class="am-u-sm-9 am-u-sm-push-3">
                                            <button type="submit" class="am-btn am-btn-primary tpl-btn-bg-color-success ">提交</button>
                                            <%@ include file="/WEB-INF/views/include/backbutton.jsp" %>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </div>
    <script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
    <script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function(){
        $('#example-r').DataTable();
    })
</script>

</body>

</html>