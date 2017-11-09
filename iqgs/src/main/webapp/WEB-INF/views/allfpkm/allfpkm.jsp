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
                            <div class="widget-title  am-cf">大表数据fpkm导入</div>
                        </div>
                        <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                            <div class="am-form-group">
                                <div class="am-btn-group am-btn-group-xs">
                                    <a href="${ctxroot}/static/example/mrna/fpkm.zip">
                                        <button type="button" class="am-btn am-btn-default am-btn-success js-upload-modal"> 导入FPKM样例文件
                                        </button>
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="widget-body  am-fr">
                            <sys:mrnaupload type="T13"/>
                        </div>

                        <div class="am-u-sm-12" style="overflow: auto">
                            <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact"
                                   id="example-r">
                                <thead>
                                <%--<tr>--%>
                                <%--<th>id</th>--%>
                                <%--<th>gens</th>--%>
                                <%--<th>操作</th>--%>
                                <%--</tr>--%>
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
<%--<div class="am-modal am-modal-no-btn" tabindex="-1" id="upload-modal">--%>
<%--<div class="am-modal-dialog">--%>
<%--<div class="am-modal-hd"> 上传--%>
<%--<a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>--%>
<%--</div>--%>
<%--<div class="am-modal-bd">--%>
<%--<sys:upload type="T11" />--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<sys:alert/>
<script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
<script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function () {
//        $('#example-r').DataTable(
        <%--{--%>
        <%--"bFilter": false,//去掉搜索框--%>
        <%--"bAutoWidth": true, //自适应宽度--%>
        <%--"sPaginationType": "full_numbers",--%>
        <%--"bDestroy": true,--%>
        <%--"bProcessing": true,--%>
        <%--"bServerSide": true,--%>
        <%--"sAjaxDataProp": "aData",//是服务器分页的标志，必须有--%>
        <%--"sAjaxSource": "${ctxroot}/firstgens/index",//通过ajax实现分页的url路径。--%>
        <%--"columns": [--%>
        <%--{"data": "id"},--%>
        <%--{'data': 'gene'},--%>
        <%--{render:function(data,type,row){--%>
        <%--return  "<div class='tpl-table-black-operation'><a href='${ctxroot}/firstgens/toedit?id="+row.id+"'><i class='am-icon-pencil'></i> 编辑</a></div>";--%>
        <%--}}--%>
        <%--],--%>
        <%--"bAutoWidth": true, //自适应宽度--%>
        <%--"sPaginationType": "full_numbers"--%>
        <%--}--%>
//        );

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

    $("#target").val("fpkm");
    $(".target-group").hide();
    $(".insert-radio label span").text("添加");


</script>

</body>

</html>