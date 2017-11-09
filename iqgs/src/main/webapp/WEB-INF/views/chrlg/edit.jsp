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
                                <div class="widget-title  am-cf">chrlg</div>


                            </div>
                            <div class="widget-body  am-fr">

                                <form class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="chrlg" method="post" action="${ctxroot}/chrlg/saveedit">
                                    <div class="am-form-group" style="display:none">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">id</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="id" name="id" value="${chrlg.id}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>

                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">chr</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="chr" name="chr" value="${chrlg.chr}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">lg</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="lg" name="lg" value="${chrlg.lg}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">version</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="version" name="version" value="${chrlg.version}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">bps</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="bps" name="bps" value="${chrlg.bps}" class="am-form-field tpl-form-no-bg" placeholder="请输入物理距离总长度（碱基个数）" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">cMs</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="cMs" name="cMs" value="${chrlg.cMs}" class="am-form-field tpl-form-no-bg" placeholder="请输入遗传距离总长度（cMs）" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">cm_bp</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="cmBp" name="cmBp" value="${chrlg.cmBp}" class="am-form-field tpl-form-no-bg" placeholder="请输入每单位遗传距离（cM）的碱基个数" >
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