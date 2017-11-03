<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
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
                                <div class="widget-title  am-cf">注册用户审核</div>


                            </div>
                            <div class="widget-body  am-fr">

                                <!--<div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-xs">
                                                <a href="${ctxroot}/traitcategory/toadd">
                                                <button type="button" class="am-btn am-btn-default am-btn-success"><span class="am-icon-plus"></span> 新增</button>
                                                </a>
                                            </div>
                                            <div class="am-btn-group am-btn-group-xs">
                                                <a href="javascript:void(0);">
                                                    <button type="button" class="am-btn am-btn-default am-btn-success js-upload-modal" ><span class="am-icon-plus"></span> 导入</button>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>-->

                                <div class="am-u-sm-12">
                                    <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact" id="example-r">
                                        <thead>
                                            <tr>
                                                <th>username</th>
                                                <th>email</th>
                                                <th>phone</th>
                                                <th>domains</th>
                                                <th>university</th>
                                                <th>create_time</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${users}" var="user">
                                            <tr>
                                                <td>${user.username}</td>
                                                <td>${user.email}</td>
                                                <td>${user.phone}</td>
                                                <td>${user.domains}</td>
                                                <td>${user.university}</td>
                                                <td><fmt:formatDate value="${user.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                                <td>
                                                    <div class="tpl-table-black-operation">
                                                        <a href="javascript:;" onclick="checkUser(this, ${user.id})">
                                                            <i class="am-icon-pencil"></i> 通过
                                                        </a>
                                                    </div>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                            <!-- more data -->
                                        </tbody>
                                    </table>
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
        $('#example-r').DataTable({
            "scrollX": true,
            "columns": [
                    null,
                { "width": "20%" },
                null,
                null,
                null,
                null
            ]
        });
    })

    function checkUser(dom, userId) {
        $.ajax({
            url: '${ctxroot}/d/user/enable',
            method: 'POST',
            data: {uid: userId},
            type: 'json',
            success:function(res){
                if (res.status == 0) {
                    $(dom).closest("tr").remove();
                }
            }
        })
    }
</script>

</body>

</html>