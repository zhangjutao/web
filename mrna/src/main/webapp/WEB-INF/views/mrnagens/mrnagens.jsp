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
                            <div class="widget-title  am-cf">mRNA_genes</div>
                        </div>
                        <div class="widget-body  am-fr">

                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                <div class="am-form-group">
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="${ctxroot}/mrnagens/toadd">
                                                <button type="button" class="am-btn am-btn-default am-btn-success"><span
                                                        class="am-icon-plus"></span> 新增
                                                </button>
                                            </a>
                                        </div>
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="javascript:void(0);">
                                                <button type="button"
                                                        class="am-btn am-btn-default am-btn-success js-upload-modal">
                                                    <span class="am-icon-plus"></span> 导入
                                                </button>
                                            </a>
                                            <span>(注：导入时若基因存在，则更新name和function，若不存在，则添加新记录)</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="am-btn-group am-btn-group-xs">
                                <a href="${ctxroot}/static/example/mrna/gmx1.zip">
                                    <button type="button" class="am-btn am-btn-default am-btn-success"> 导入基因样例文件
                                    </button>
                                </a>
                            </div>

                            <div class="am-u-sm-12">
                                <table width="100%" class="am-table am-table-striped am-table-bordered am-table-compact"
                                       id="example-r">
                                    <thead>
                                    <tr>
                                        <th>id</th>
                                        <th>gene</th>
                                        <th>geneName</th>
                                        <th>functions</th>
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
            <sys:upload type="T11"/>
        </div>
    </div>
</div>
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
<sys:alert/>
<script src="${ctxStatic}/dashboard/js/amazeui.datatables.min.js"></script>
<script src="${ctxStatic}/dashboard/js/dataTables.responsive.min.js"></script>
<script>
    $(function () {
        $('#example-r').DataTable({
                    "scrollX": true,
                    "bFilter": true,//去掉搜索框
                    "bAutoWidth": true, //自适应宽度
                    "sPaginationType": "full_numbers",
                    "bDestroy": true,
                    "bProcessing": true,
                    "bServerSide": true,
                    "sAjaxDataProp": "aData",//是服务器分页的标志，必须有
                    "sAjaxSource": "${ctxroot}/mrnagens/index",//通过ajax实现分页的url路径。
                    "columns": [
                        {"data": "id"},
                        {'data': 'gene'},
                        {'data': 'geneName'},
                        {'data': 'functions'},
                        {
                            render: function (data, type, row) {
                                return "<div class='tpl-table-black-operation'><a href='${ctxroot}/mrnagens/toedit?id=" + row.id + "'><i class='am-icon-pencil'></i> 编辑</a>" + "<a class='tpl-table-black-operation-del del-btn' href='javascript:void(0)' data-id='" + row.id + "'><i class='am-icon-trash'></i> 删除</a></div>";
                            }
                        }
                    ],
                    "bAutoWidth": true, //自适应宽度
                    "sPaginationType": "full_numbers",
                    "columnDefs": [
                        {
                            "targets": [2, 3],
                            "render": function (data, type, full, meta) {
                                if (data == "") {
                                    return '<a class="js-modal-open" href="javascript:;" data-content="' + data + '"> </a>';
                                } else {
                                    return '<a class="js-modal-open" href="javascript:;" data-content="' + data + '"> ' + data.substr(0, 13) + '...</a>';
                                }
                            }
                        }]
                }
        );

    })

    $("body").on("click", ".js-modal-open", function () {
        var content = $(this).attr("data-content");
        var str = "";
        str += "<p style='text-align: left; word-break: break-all;'>"
        str += content;
        str += "</p>";
        $("#doc-modal .am-modal-bd").empty().append(str);
        $("#doc-modal").modal();
    });

    $(".js-upload-modal").click(function () {
        $("#upload-modal").modal();
    });

    $("body").on("click", ".del-btn", function() {
        var id = $(this).attr("data-id");

        $('#my-confirm').modal({
            relatedTarget: this,
            onConfirm: function(options) {
                // 删除
                $.ajax({
                    url: "${ctxroot}/mrnagens/delete",
                    data: {id: id},
                    type:"GET",
                    dataType: "text",
                    success: function(data) {
                        $('#my-confirm').modal("close");
                        if(data == "true") {
                            Alert.success();
                        } else {
                            Alert.fail();
                        }
                        setTimeout(function() {
                            window.location.reload();
                        }, 3000);
                    }
                }) ;
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
            _alert.modal({closeViaDimmer: false});
            setTimeout(function() {
                _alert.modal("close");
            }, 2000);
        },
        fail: function() {
            var content = '<p><a href="javascript:;" class="am-icon-btn am-danger am-icon-close"></a> 操作失败</p>'
            _alert.find(".am-modal-bd").empty().append(content);
            _alert.modal({closeViaDimmer: false});
            setTimeout(function() {
                _alert.modal("close");
            }, 2000);
        }
    }
</script>

</body>

</html>