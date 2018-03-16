<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctxroot" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
    <title>Cache Admin Management</title>
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <style type="text/css">
        body {
            font-size: 14px;
            background-color: #f5f5f5;
            font-family: Microsoft YaHei, Heiti SC;
            color: #5a5a5a;
            min-width: 1200px;
        }
        .key {
            padding: 10px;
            font-size: 16px;
            margin-right: 10px;
        }
        .remove {
            margin-left: 10px;
            font-size: 16px;
            padding: 5px;
            border-radius: 4px;
            cursor: pointer;
        }
        .container {
            width: 1200px;
            margin: 0 auto;
        }
        .ele {
            background-color: #fff;
        }
        .t {
            width: 100%;
        }
        td.single {
            width: 80%;
            padding-left: 20px;
        }
        td.even {
            padding-left: 80px;
        }
        tr:nth-child(odd) {
            background-color: aliceblue;
        }
    </style>

</head>
<body>
    <div class="container">
        <iqgs:iqgs-header />
        <h1>缓存个体数目：${keySize}</h1>
        <h1>系统目前CPU使用率：${processCpuLoad}</h1>
        <h1>目前系统中缓存的Key有：</h1>
        <div class="ele">
            <table class="t">
                <c:forEach items="${cacheResult}" var="obj">
                    <tr>
                        <td class="single"><span class="key">${obj.key}</span></td>
                        <td class="even"><button class="remove">remove</button></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <script type="application/javascript">
        $(".remove").click(function () {
            var $currentElement = $(this);
            var key = $currentElement.parent().prev().children(".key").text();
            console.log("key: " + key);
            $.ajax({
                method: 'GET',
                url: '${ctxroot}/cache/delete',
                data: {
                    key: key
                }
            }).done(function(data){
                console.log("请求成功：" + data);
                location.reload();
            }).error(function(){
                console.log("后台报错");
            });
        });
        $(".key").click(function(){
            var hoverElement = $(this);
            var key = hoverElement.text();
            var searchResult;
            $.ajax({
                method: 'GET',
                url: '${ctxroot}/cache/search',
                data: {
                    key: key
                }
            }).done(function (data) {
                searchResult = data;
                layer.open({
                    title: hoverElement.text(),
                    content: searchResult
                });
            }).error(function () {
                console.log("无法查询");
            });
        })
    </script>
</body>
</html>
