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
                                <div class="widget-title  am-cf">大豆图片信息</div>
                            </div>
                            <div class="widget-body  am-fr">

                                <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                    <div class="am-form-group">
                                        <div class="am-btn-toolbar">
                                            <div class="am-btn-group am-btn-group-xs">
                                                <a href="${ctxroot}/soybean/toaddsoybean">
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
                                                <th>id</th>
                                                <th>category_name</th>
                                                <th>category</th>
                                                <th>trait_list</th>
                                                <th>qtl_list</th>
                                                <th>操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${soybeanList}" var="item">
                                            <tr>
                                                <td>${item.id}</td>
                                                <td>${item.categoryName}</td>
                                                <td>${item.category}</td>
                                                <td>${item.listName}</td>
                                                <td>${item.qtlName}</td>
                                                <td>
                                                    <div class="tpl-table-black-operation">
                                                        <a href="${ctxroot}/soybean/toedit?id=${item.id}">
                                                            <i class="am-icon-pencil"></i> 编辑
                                                        </a>
                                                        <a href="${ctxroot}/soybean/delete?id=${item.id}" class="tpl-table-black-operation-del">
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

    <div class="am-modal am-modal-no-btn" tabindex="-1" id="doc-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd"> 详情
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">

            </div>
        </div>
    </div>
    <div class="am-modal am-modal-no-btn" tabindex="-1" id="upload-modal">
        <div class="am-modal-dialog">
            <div class="am-modal-hd"> 上传
                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
            </div>
            <div class="am-modal-bd">
                <sys:upload type="T10" />
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
            "columnDefs": [ {
                "targets": 3,
                "render": function ( data, type, full, meta ) {
                    return '<a class="js-modal-open" href="javascript:;" data-list="'+ data +'"> ' + data.split("|")[0] + '...</a>';
                }
            }, {
                "targets": 4,
                "render": function ( data, type, full, meta ) {
                    return '<a class="js-modal-open" href="javascript:;" data-list="'+ data +'">' + data.split("|")[0] + ' ...</a>';
                }
            } ]
        });

        $("body").on("click", ".js-modal-open", function() {
            var list = $(this).attr("data-list").split("|");
            var str = "";
            str += "<ul class='am-list' style='text-align: left;'>"
            for(var i = 0; i< list.length; i++) {
                str += "<li>" + list[i] + "</li>";
            }
            str += "</ul>";
            $("#doc-modal .am-modal-bd").empty().append(str);
            $("#doc-modal").modal();
        })
    })

    $(".js-upload-modal").click(function () {
        $("#upload-modal").modal();
    });
</script>

</body>

</html>