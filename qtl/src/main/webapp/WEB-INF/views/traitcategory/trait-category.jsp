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
                                <div class="widget-title  am-cf">qtl类别</div>


                            </div>
                            <div class="widget-body  am-fr">

                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
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
                                </div>

                                <div class="am-u-sm-12">
                                    <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact" id="example-r">
                                        <thead>
                                            <tr>
                                                <th>type_id</th>
                                                <th>qtl_type</th>
                                                <th>type_desc</th>
                                                <th>type_abbreviation</th>
                                                <%--<th>qtl_createtime</th>--%>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${list}" var="item">
                                            <tr>
                                                <td>${item.id}</td>
                                                <td>${item.qtlName}</td>
                                                <td>${item.qtlDesc}</td>
                                                <td>${item.qtlOthername}</td>
                                                <%--<td>${item.qtlCreatetime}</td>--%>
                                                <td>
                                                    <div class="tpl-table-black-operation">
                                                        <a href="${ctxroot}/traitcategory/toedit?id=${item.id}">
                                                            <i class="am-icon-pencil"></i> 编辑
                                                        </a>
                                                        <%--<a href="${ctxroot}/traitcategory/delete?id=${item.id}" class="tpl-table-black-operation-del">--%>
                                                            <%--<i class="am-icon-trash"></i> 删除--%>
                                                        <%--</a>--%>
                                                        <a href="javascript:;" class="tpl-table-black-operation-del btn-refuse"
                                                           data-src="${ctxroot}/traitcategory/delete?id=${item.id}">
                                                            <i class="am-icon-trash"></i> 删除
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
    </div>
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="upload-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd"> 上传
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <sys:upload type="T01" />
            </div>
        </div>
    </div>
    <sys:alert />
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

    $(".btn-refuse").click(function () {
        var url = $(this).attr("data-src");
        layer.confirm('确认删除吗？', {
            btn: ['是', '否'] //按钮
        }, function () {
            $.ajax({
                url: url,
                type: 'GET',
                contentType: "application/json;charset=utf-8",
//                    data:JSON.stringify(sendData),
                dataType:"text",
                success: function (data) {
                    layer.msg(data);
                    window.location.href = "${ctxroot}/traitcategory/list";
                }
            });
        }, function () {

        });
    })

    $(".js-upload-modal").click(function () {
        $("#upload-modal").modal();
    });
</script>

</body>

</html>