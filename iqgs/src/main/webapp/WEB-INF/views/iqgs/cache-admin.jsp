<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctxroot" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
    <title>Cache Admin Management</title>
    <style type="text/css">
        .key {
            padding: 10px;
            font-size: 16px;
            background-color: aliceblue;
            margin-right: 10px;
        }
        .remove {
            margin-left: 10px;
            font-size: 16px;
            padding: 4px;
            border-radius: 4px;
            cursor: pointer;
        }
        table {
            border: 1px solid black;
            padding: 10px;
        }
        table td {
            border-left: 1px solid gray;
        }
    </style>
</head>
<body>
    <iqgs:iqgs-header />
    <h1>缓存个体数目：${keySize}</h1>
    <h1>系统目前CPU使用率：${processCpuLoad}</h1>
    <h1>目前系统中缓存的Key有：</h1>
    <div class="ele">
        <table>
            <c:forEach items="${cacheResult}" var="obj">
                <tr>
                    <td><span class="key">${obj.key}</span></td>
                    <td><button class="remove" onclick="evictKey(this)">remove</button></td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script type="application/javascript">
        function evictKey(item) {
            var currentElement = $(item);
            console.log(currentElement.prev().text());
            var key = currentElement.prev().text();
            $.ajax({
                method: 'GET',
                url: '${ctxroot}/cache/delete',
                data: {
                    key: key
                }
            }).done(function(data){
                console.log("请求成功：" + data);
            }).error(function(){
                console.log("后台报错");
            });
        }
    </script>
</body>
</html>
