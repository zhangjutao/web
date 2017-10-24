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
                            <div class="widget-title  am-cf">表达基因导入</div>
                        </div>
                        <div class="widget-body  am-fr">

                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                <div class="am-form-group">
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="javascript:void(0);">
                                                <button type="button"
                                                        class="am-btn am-btn-default am-btn-success js-upload-modal">
                                                    <span class="am-icon-plus"></span> 新建
                                                </button>
                                            </a>
                                        </div>
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="${ctxroot}/static/example/mrna/SRP002082.zip">
                                                <button type="button" class="am-btn am-btn-default am-btn-success"> 导入表达基因样例文件
                                                </button>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="am-u-sm-12" >
                                <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact"
                                       id="example-r">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>sraStudy</th>
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
<div class="am-modal am-modal-no-btn" tabindex="-1" id="upload-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd"> <span class="form-title">上传</span>
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <sys:mrnaupload type="T14"/>
        </div>
    </div>
</div>
<sys:alert/>
<div class="am-modal am-modal-confirm" tabindex="-1" id="my-confirm">
    <div class="am-modal-dialog">
        <div class="am-modal-hd">提示</div>
        <div class="am-modal-bd">
            <a href="javascript:;" class="am-icon-btn am-warning am-icon-warning"></a>
            你确定要删除吗？
        </div>
        <div class="am-modal-footer">
            <span class="am-modal-btn" data-am-modal-cancel>取消</span>
            <span class="am-modal-btn" data-am-modal-confirm>确定</span>
        </div>
    </div>
</div>
<script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
<script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function () {
        $('#example-r').DataTable(
                {
                    "scrollX": true,
                    "bFilter": true,//去掉搜索框
                    "bAutoWidth": true, //自适应宽度
                    "sPaginationType": "full_numbers",
                    "bDestroy": true,
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxDataProp": "aData",//是服务器分页的标志，必须有
                    "sAjaxSource": "${ctxroot}/expression/index",//通过ajax实现分页的url路径。
                    "columns": [
                        {"data": "id"},
                        {'data': 'name'},
                        {
                            render: function (data, type, row) {
                                return "<div class='tpl-table-black-operation'><a class='js-delete-modal' href='javascript:void(0)' data-id='" + row.name + "'><i class='am-icon-pencil'></i> 删除</a>" +
                                        " <a class='js-edit-modal' data-sra-study='"+ row.name +"' href='javascript:void(0);'><i class='am-icon-upload'></i> 导入</a></div>";
                            }
                        }
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

    $("#target").keyup(function() {
        var sraStudy = $(this).val();
        if(sraStudy != "") {
            $("#my-upload, #file").prop("disabled", false);
        } else {
            $("#my-upload, #file").prop("disabled", true);
        }
    });
    $("#fileForm .update-radio, #fileForm .delete-radio").hide();

    // 新建
    $(".js-upload-modal").click(function () {
        $(".form-title").html("新建");
        $("#fileForm").find(".target-group input").val("").prop("disabled", false);
        var sraStudy = $(this).val();
        if(sraStudy == "") {
            $("#my-upload, #file").prop("disabled", true);
        } else {
            $("#my-upload, #file").prop("disabled", false);
        }
        $("#upload-modal").modal({closeViaDimmer: false});
    });

    // 编辑
    $("body").on("click",".js-edit-modal", function () {
        $(".form-title").html("编辑");
        var sraStudy = $(this).attr("data-sra-study");
        $("#fileForm").find(".target-group input").val(sraStudy).prop("disabled", true);
        if(sraStudy == "") {
            $("#my-upload, #file").prop("disabled", true);
        } else {
            $("#my-upload, #file").prop("disabled", false);
        }
        $("#upload-modal").modal({closeViaDimmer: false});
    });

    $("body").on("click", ".js-delete-modal", function() {
        var id = $(this).attr("data-id");
        $('#my-confirm').modal({
            closeViaDimmer: false,
            relatedTarget: this,
            onConfirm: function(options) {
                $.ajax({
                    url: "${ctxroot}/expression/delete",
                    data: {id: id},
                    type: "GET",
                    dataType: "json",
                    success: function(data) {
                        $('#my-confirm').modal("close");
                        if(data.result) {
                            Alert.success();
                        } else {
                            Alert.fail();
                        }
                        setTimeout(function() {
                            window.location.reload();
                        }, 3000);
                    }
                });
            },
            closeOnConfirm: false
        });
    });

    var _alert = $("#my-alert");
    _alert.find(".am-modal-footer").remove();
    var Alert = {
        success: function() {
            var content = '<p><a href="javascript:;" class="am-icon-btn am-success am-icon-check"></a> 操作成功</p>'
            _alert.find(".am-modal-bd").empty().append(content);
            _alert.modal();
            setTimeout(function() {
                _alert.modal("close");
            }, 2000);
        },
        fail: function() {
            var content = '<p><a href="javascript:;" class="am-icon-btn am-danger am-icon-close"></a> 操作失败</p>'
            _alert.find(".am-modal-bd").empty().append(content);
            _alert.modal();
            setTimeout(function() {
                _alert.modal("close");
            }, 2000);
        }
    }
</script>

</body>

</html>