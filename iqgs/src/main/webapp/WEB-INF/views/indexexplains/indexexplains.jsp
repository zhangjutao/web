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
                            <div class="widget-title  am-cf">首页数据库概况设置</div>


                        </div>
                        <div class="widget-body  am-fr">

                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                <%--<div class="am-form-group">--%>
                                    <%--<div class="am-btn-toolbar">--%>
                                        <%--<div class="am-btn-group am-btn-group-xs">--%>
                                            <%--<a href="${ctxroot}/qtl/toadd">--%>
                                                <%--<button type="button" class="am-btn am-btn-default am-btn-success"><span--%>
                                                        <%--class="am-icon-plus"></span> 新增--%>
                                                <%--</button>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                        <%--<div class="am-btn-group am-btn-group-xs">--%>
                                            <%--<a href="javascript:void(0);">--%>
                                                <%--<button type="button" class="am-btn am-btn-default am-btn-success js-upload-modal" ><span class="am-icon-plus"></span> 导入</button>--%>
                                            <%--</a>--%>
                                        <%--</div>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                            </div>

                            <div class="am-u-sm-12">
                                <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact"
                                       id="example-r">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>type</th>
                                        <th>detail</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>
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
<sys:alert />
<script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
<script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function () {
        $('#example-r').DataTable({
                    "scrollX": true,
                    "bFilter": false,//去掉搜索框
                    "bAutoWidth": true, //自适应宽度
                    "sPaginationType": "full_numbers",
                    "bDestroy": true,
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxDataProp": "aData",//是服务器分页的标志，必须有
                    "sAjaxSource": "${ctxroot}/indexexplains/index",//通过ajax实现分页的url路径。
                    "columns": [
                        {"data": "id"},
                        {'data': 'type'},
                        {'data': 'detail'},
                        {render:function(data,type,row){
                            return  "<div class='tpl-table-black-operation'><a href='${ctxroot}/indexexplains/toedit?type="+row.type+"'><i class='am-icon-pencil'></i> 编辑</a></div>";
                        }}
                    ],
                    "bAutoWidth": true, //自适应宽度
                    "sPaginationType": "full_numbers"
                }
        );

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
                dataType: "text",
                success: function (data) {
                    layer.msg(data);
                    window.location.href = "${ctxroot}/firstgens/list";
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