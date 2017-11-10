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
                                <div class="widget-title  am-cf">大豆图片信息</div>


                            </div>
                            <div class="widget-body  am-fr">

                                <form class="am-form tpl-form-border-form tpl-form-border-br" modelAttribute="soybean" method="post" action="${ctxroot}/soybean/saveedit">
                                    <div class="am-form-group" style="display:none">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">id</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="id" name="id" value="${soybean.id}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>

                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">category_name</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="categoryName" name="categoryName" value="${soybean.categoryName}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">category</span></label>
                                        <div class="am-u-sm-9">
                                            <input type="text" id="category" name="category" value="${soybean.category}" class="am-form-field tpl-form-no-bg" placeholder="" >
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">trait_list</span></label>
                                        <div class="am-u-sm-9">
                                            <textarea type="text" id="listName" name="listName" class="am-form-field tpl-form-no-bg" placeholder="" >${soybean.listName}</textarea>
                                            <small></small>
                                        </div>
                                    </div>
                                    <div class="am-form-group">
                                        <label for="" class="am-u-sm-3 am-form-label"> <span class="tpl-form-line-small-title">qtl_list</span></label>
                                        <div class="am-u-sm-9">
                                            <textarea type="text" id="qtlName" name="qtlName" class="am-form-field tpl-form-no-bg" placeholder="" >${soybean.qtlName}</textarea>
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
    <script src="/static/dashboard/js/amazeui.datatables.min.js"></script>
    <script src="/static/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function(){
        $('#example-r').DataTable();
    })
</script>

</body>

</html>