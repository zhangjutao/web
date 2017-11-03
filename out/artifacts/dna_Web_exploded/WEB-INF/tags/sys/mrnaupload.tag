<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="type" type="java.lang.String" required="true" description="" %>
<style>
    .am-modal-bd {
        text-align: left;
    }
</style>
<form:form id="fileForm" action="${ctxroot}/upload/import?type=${type}" method="post" enctype="multipart/form-data">

    <div class="am-form-group target-group">
        <label>sraStudy</label>
        <input type="text" class="am-form-field am-input-sm" id="target" name="target" />
    </div>

    <div class="am-form-group">
        <label>选择导入模式</label>
        <div class="am-radio insert-radio">
            <label>
                <input type="radio" class="importMode" name="importMode" value="insert" checked>
                <span>添加 - 如果目标存在记录则删除全部记录，并从源重新导入</span>
            </label>
        </div>

        <div class="am-radio update-radio">
            <label>
                <input type="radio" class="importMode" name="importMode" value="update">
                更新
            </label>
        </div>

        <div class="am-radio delete-radio">
            <label>
                <input type="radio" class="importMode" name="importMode" value="delete">
                删除
            </label>
        </div>
    </div>


    <div class="am-form-group am-form-file">
        <button type="button" class="am-btn am-btn-default am-btn-sm" id="my-upload">
            <i class="am-icon-cloud-upload"></i> 选择要上传的文件</button>
        <input name="file" id="file" type="file" onchange="fileChange(this)">
    </div>

    <button type="button" class="am-btn am-btn-default am-btn-sm" id="upload-loading" disabled style="display: none;">导入中...</button>

    <input type="text" class="am-form-field am-input-sm" id="file-name" style="display: none;"/>


</form:form>

<script>


        var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
        function fileChange(target) {
            var fileSize = 0;
            var filetypes = [".zip"];
            var filepath = target.value;
            var fileName = filepath.substring(filepath.lastIndexOf("\\") + 1);
            var filemaxsize = 1024 * 10;
            if (filepath) {
                var isnext = false;
                var fileend = filepath.substring(filepath.lastIndexOf("."));
                if (filetypes && filetypes.length > 0) {
                    for (var i = 0; i < filetypes.length; i++) {
                        if (filetypes[i] == fileend) {
                            isnext = true;
                            break;
                        }
                    }
                }
                if (!isnext) {
                    alert("不接受此文件类型！");
                    target.value = "";
                    return false;
                }
            } else {
                return false;
            }
            //kfsjflsdklfjsdljfks
            if (isIE && target.files) {
                var filePath = target.value;
                var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
                if (!fileSystem.FileExists(filepath)) {
                    alert("附件不存在，请重新输入！");
                    return false;
                }
                var file = fileSystem.GetFile(filePath);
                fileSize = target.files.size;
            } else {
                fileSize = target.files[0].size;
            }
            var size = fileSize / 1024;
//            if (size > filemaxsize) {
//                alert("附件不能大于" + filemaxsize / 1024 + "M!");
//                target.value = "";
//                return false;
//            }
            $("#file-name").val(fileName);
//            document.getElementById("fileForm").submit();

            $("#my-upload").parent().hide();
            $("#upload-loading").show();

            var formData = new FormData();
            formData.append('file', $('#file')[0].files[0]);
            $.ajax({
                url: '${ctxroot}/mrna/import?type=${type}&sraStudy=' + $("#target").val() + "&mode=" + $("input[name='importMode']:checked").val(),
                type: 'POST',
                cache: false,
                data: formData,
                processData: false,
                contentType: false,
                dataType:"json"
            }).done(function(res) {
                $("#upload-modal").modal("close");
                if(res.result) {
                    var content = "导入成功";
                } else {
                    var content = res.mes + ",导入失败";
                }
                $("#my-alert").find(".am-modal-bd").empty().append(content);
                $("#my-alert").modal("open");

                $("#my-upload").parent().show();
                $("#upload-loading").hide();

                $("#file").val("");
                setTimeout(function() {
                    window.location.reload();
                }, 3000);

            }).fail(function(res) {
                $("#my-upload").parent().show();
                $("#upload-loading").hide();
                $("#file").val("");
                setTimeout(function() {
                    window.location.reload();
                }, 3000);
            });

        }

</script>
