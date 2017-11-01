<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">


<%@ include file="/WEB-INF/views/include/dashboard/header.jsp" %>

<link rel="stylesheet" href="${ctxStatic}/css/ztree/css/metroStyle/metroStyle.css">
<style>
    .am-modal-bd {
        text-align: initial;
    }
    #my-alert .am-modal-bd,
    #my-confirm .am-modal-bd {
        text-align: center;
    }
</style>

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
                            <div class="widget-title  am-cf">mRNA 分类管理</div>


                        </div>
                        <div class="widget-body  am-fr">

                            <div class="am-u-sm-12 am-u-md-6 am-u-lg-6">
                                <div class="am-form-group">
                                    <div class="am-btn-toolbar">
                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="javascript:void(0);">
                                                <button type="button" class="am-btn am-btn-default am-btn-success js-modal-new">
                                                    <span class="am-icon-plus"></span> 新增
                                                </button>
                                            </a>

                                        </div>

                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="javascript:void(0);">
                                                <button type="button" class="am-btn am-btn-default js-modal-edit" >
                                                    <span class="am-icon-edit"></span> 编辑
                                                </button>
                                            </a>
                                        </div>

                                        <div class="am-btn-group am-btn-group-xs">
                                            <a href="javascript:void(0);">
                                                <button type="button" class="am-btn am-btn-default delete-btn" >
                                                    <span class="am-icon-times"></span> 删除
                                                </button>
                                            </a>
                                        </div>
                                    </div>

                                </div>
                                <div>注： 当未选中节点"新增"时，则新增一个根节点</div>
                            </div>
                            <div class="am-u-sm-12">
                                <div id="tree" class="ztree" style="width:200px;"></div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="new-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd"> 新建子元素
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <form class="am-form am-form-horizontal new-form">
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">名称</label>
                    <div class="am-u-sm-10">
                        <input type="text" class="name" placeholder="">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">中文名称</label>
                    <div class="am-u-sm-10">
                        <input type="text" class="chinese-name" placeholder="">
                    </div>
                </div>

                <div class="am-form-group">
                    <div class="am-u-sm-10 am-u-sm-offset-2">
                        <button type="button" class="am-btn am-btn-default new-btn">提交</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<div class="am-modal am-modal-no-btn" tabindex="-1" id="edit-modal">
    <div class="am-modal-dialog">
        <div class="am-modal-hd"> 编辑元素
            <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
        </div>
        <div class="am-modal-bd">
            <form class="am-form am-form-horizontal edit-form">
                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">名称</label>
                    <div class="am-u-sm-10">
                        <input type="text" class="name" placeholder="">
                    </div>
                </div>

                <div class="am-form-group">
                    <label class="am-u-sm-2 am-form-label">中文名称</label>
                    <div class="am-u-sm-10">
                        <input type="text" class="chinese-name" placeholder="">
                    </div>
                </div>

                <div class="am-form-group">
                    <div class="am-u-sm-10 am-u-sm-offset-2">
                        <button type="button" class="am-btn am-btn-default edit-btn">提交</button>
                    </div>
                </div>
            </form>
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
<sys:alert />

<script src="${ctxStatic}/js/jquery.ztree.all.min.js"></script>
<script>
    $(function () {

        var treeObj, currentNode;

        var _newModal = $("#new-modal");
        var _editModal = $("#edit-modal");

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
            },
            attention: function() {
                var content = '<p><a href="javascript:;" class="am-icon-btn am-warning am-icon-exclamation"></a> 请选择一个组织再操作</p>'
                _alert.find(".am-modal-bd").empty().append(content);
                _alert.modal({closeViaDimmer: false});
                setTimeout(function() {
                    _alert.modal("close");
                }, 2000);
            },
            notNull: function(content) {
                var content = '<p><a href="javascript:;" class="am-icon-btn am-warning am-icon-exclamation"></a>'+  ((content!="") ? content : "输入不能为空") +'</p>'
                _alert.find(".am-modal-bd").empty().append(content);
                _alert.modal({closeViaDimmer: false});
                setTimeout(function() {
                    _alert.modal("close");
                }, 2000);
            }
        }

        $(".js-modal-new").click(function() {
//            if(typeof currentNode === 'object') {
               return  _newModal.modal({closeViaDimmer: false});
//            }
//            return Alert.attention();

        });

        $(".js-modal-edit").click(function() {
            if(typeof currentNode === 'object') {
                var _editForm = $(".edit-form");
                _editForm.find(".name").val(currentNode.name);
                _editForm.find(".chinese-name").val(currentNode.chinese);
                return _editModal.modal({closeViaDimmer: false});
            }
            return Alert.attention();
        });

        $(".new-btn").click(function() {
            var _newForm = $(".new-form");
            var newNode = {
                name: _newForm.find(".name").val(),
                chinese: _newForm.find(".chinese-name").val(),
                children: []
            }
            // 新增节点
            if(typeof currentNode === "object") {
                if(newNode.name == "") {
                    return Alert.notNull("名称 不能为空");
                }
                treeObj.addNodes(currentNode, newNode);
                var root = getThinRoot(getCurrentRoot(currentNode));
                var prom = updateTree(root);
                prom.done(function(data) {
                    if(data == "true") {
                        return Alert.success();
                    }
                    return Alert.fail();
                });
                _newModal.modal('close');

            } else {
                if(newNode.name == "" || newNode.chinese == "") {
                    return Alert.notNull("名称或中文名称不能为空");
                }
                treeObj.addNodes(null, newNode);
                var root = getThinRoot(getCurrentRoot(newNode));
                var prom = updateTree(root);
                prom.done(function(data) {
                    if(data == "true") {
                        return Alert.success();
                    }
                    return Alert.fail();
                });
                _newModal.modal('close');
                setTimeout(function() {
                    window.location.reload();
                },3000);
            }

        });

        $(".edit-btn").click(function() {
            var _editForm = $(".edit-form");
            currentNode.name = _editForm.find(".name").val();
            currentNode.chinese = _editForm.find(".chinese-name").val();
            // 编辑节点
            treeObj.updateNode(currentNode);
            var root = getThinRoot(getCurrentRoot(currentNode));
            var prom = updateTree(root);
            prom.done(function(data) {
                if(data == "true") {
                    return Alert.success();
                }
                return Alert.fail();
            });
            _editModal.modal('close');
        });

        $(".delete-btn").click(function() {
            if(typeof currentNode === 'object') {
                return $('#my-confirm').modal({
                    relatedTarget: this,
                    onConfirm: function(options) {
                        // 删除节点
                        treeObj.removeNode(currentNode);
                        var root = getThinRoot(getCurrentRoot(currentNode));
                        if(currentNode.level == 0) {
                            var prom = deleteRootNode(root);
                        } else {
                            var prom = updateTree(root);
                        }
                        prom.done(function(data) {
                            if(data == "true") {
                                return Alert.success();
                            }
                            return Alert.fail();
                        });
                        $('#my-confirm').modal("close");
                        currentNode = undefined;
                    },
                    closeOnConfirm: false
                });
            }
            return Alert.attention();

        });

        _newModal.on('closed.modal.amui', function(){
            var _newForm = $(".new-form");
            _newForm.find(".name").val('');
            _newForm.find(".chinese-name").val('');
        });

        function getCurrentRoot(treeNode) {
            var root = treeNode;
            if(treeNode.parentTId) {
                var node = treeObj.getNodeByTId(treeNode.parentTId);
                if(node.parentTId) {
                    root = treeObj.getNodeByTId(node.parentTId);
                } else {
                    root = node;
                }
            } else {
                root = treeNode;
            }
//            console.log("---------------------");
//            console.log("root:", root);
            return root;
        }

        function getThinRoot(root) {
            var obj1 = {
                "name": root["name"],
                "id": root["id"],
                "chinese": root["chinese"],
                "children": []
            }
            var level2 = root["children"];
            for(var r in level2) {
                var obj2 = {
                    "name": level2[r]["name"],
//                    "id": level2[r]["id"],
                    "chinese": level2[r]["chinese"],
                    "children": []
                }
                var level3 = level2[r]["children"];
                for(var r2 in level3) {
                    var obj3 = {
                        "name": level3[r2]["name"],
//                        "id": level3[r2]["id"],
                        "chinese": level3[r2]["chinese"],
                        "children": []
                    }
                    obj2["children"].push(obj3);
                }
                obj1["children"].push(obj2);
            }
            console.log(JSON.stringify(obj1));
            return obj1;
        }

        function updateTree(json) {
            json.name = json.name + "_All";
            var jsonstr = JSON.stringify(json);
            return $.ajax({
                url: "${ctxroot}/t/update",
                data: {classify: jsonstr},
                type: "post",
                dataType: "text"
            });
        }

        function deleteRootNode(json) {
            return $.ajax({
                url: "${ctxroot}/t/delete",
                data: {id: json.id},
                type: "POST",
                dataType: "text"
            });
        }

        function zTreeOnClick(event, treeId, treeNode) {
//            console.log("treeNode: " , treeNode);
            event.stopPropagation();
            var level = treeNode.level;
            if(level == 2) {
                $(".js-modal-new").prop("disabled", true);
            } else {
                $(".js-modal-new").prop("disabled", false);
            }

            var hasChildren = (treeNode.children.length > 0 ) ? true: false;
            if( hasChildren) {
                $(".delete-btn").prop("disabled", true);
            } else {
                $(".delete-btn").prop("disabled", false);
            }

            currentNode = treeNode;

        }

        $(".ztree").parent().click( function(e) {
            treeObj.cancelSelectedNode();
            $(".js-modal-new").prop("disabled", false);
            $(".delete-btn").prop("disabled", false);
            currentNode = undefined;
//            console.log("current node", currentNode);
        });

        $.ajax({
            url: "${ctxroot}/specific/tree",
            dataType: "json",
            type: "GET",
            success: function(data) {
                var setting = {
                    callback: {
                        onClick: zTreeOnClick
                    }
                };
//                var zNodes = [{
//                    name: "0",
//                    open: true,
//                    children: data
//                }];
                $.each(data, function(idx, item) {
                    item.name = item.name.substring(0, (item.name.length - 4));
                });
                var zNodes = data;
                treeObj = $.fn.zTree.init($("#tree"), setting, zNodes);
            }
        });
    })

</script>

</body>

</html>