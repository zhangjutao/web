<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>高级搜索Test页面</title>
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body>
<button id="advance">高级搜索</button>
<script type="text/javascript">
    $("#advance").click(function () {
        var geneExpressions = new Array();
        var singleGeneExpression = {
            tissue: {
                pod:0.0,
                endosperm:0.0
            },
            begin:5.0,
            end:10.0
        };
        geneExpressions.push(singleGeneExpression);
        var condition = {
            geneExpressionConditionEntities:geneExpressions,
            snpConsequenceType:['5UTR', '3UTR'],
            indelConsequenceType:['5UTR'],
            qtlId:[1003, 1005]
        };
        console.log(JSON.stringify(condition));
        $.ajax({
            type: "POST",
            url: '${ctxroot}/advance-search/advanceSearch',
            data: JSON.stringify(condition),
            contentType: "application/json;charset=UTF-8;",
            success: function (result) {
                console.log(请求成功)
            },
            error: function (error) {
                console.log(error)
            }
        });
    })
</script>
</body>
</html>
