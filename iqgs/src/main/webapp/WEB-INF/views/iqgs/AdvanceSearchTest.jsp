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
            },
            begin:10.0,
            end:20.0
        };
        geneExpressions.push(singleGeneExpression);
        singleGeneExpression = {
            tissue: {
                embryo:0.0,
            },
            begin:40.0,
            end:50.0
        };
        geneExpressions.push(singleGeneExpression);
        var condition = {
            geneExpressionConditionEntities:geneExpressions,
            snpConsequenceType:['upstream;downstream', 'UTR5'],
            indelConsequenceType:['exonic_frameshift deletion', 'splicing'],
            qtlId:[1453, 1941, 2089],
            pageNo: 1,
            pageSize: 10
        };
        console.log(JSON.stringify(condition));
        $.ajax({
            type: "POST",
            url: '${ctxroot}/advance-search/advanceSearch',
            data: JSON.stringify(condition),
            contentType: "application/json;charset=UTF-8;",
            success: function (result) {
                console.log(result)
            },
            error: function (error) {
                console.log(error)
            }
        });
    })
</script>
</body>
</html>
