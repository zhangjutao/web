<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set var="ctxroot" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
    <title>Cache Admin Management</title>
</head>
<body>
    <iqgs:iqgs-header />
    <h1>缓存个体数目：${keySize}</h1>
    <h1>系统目前CPU使用率：${processCpuLoad}</h1>
    <h1>目前系统中缓存的Key有：</h1>
    <c:forEach items="${cacheResult}" var="obj">
        <span>${obj.key}</span>
        <button id="remove">remove</button><br/>
    </c:forEach>
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script type="application/javascript">
        $("#remove").click(function(){
            var currentElement = $(this);
            console.log(currentElement.prev().text());
            var key = currentElement.prev().text();

        })
    </script>
</body>
</html>
