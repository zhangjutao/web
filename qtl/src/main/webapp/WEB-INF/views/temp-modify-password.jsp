<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true"%>
<html>
<head>
    <title>找回密码</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body >

<qtl:qtl-header />
<!--header-->
<div class="container">
    <div class="forget-h"><p>找回密码</p></div>
    <div class="forget modify">
        <form method="POST" action="${ctxroot}/signup/temp/modifyPassword">
            <input type="hidden" value="${userId}" name="userId">
            <c:if test="${not empty error}">
                <div class="er">${error}</div>
            </c:if>
            <div class="modify-pwd">
                <label>
                    <span>新密码:</span>
                    <input type="password" name="password" id="new-pwd" placeholder="请输入新密码">
                    <span class="tips"></span>
                </label>
            </div>
            <div class="confirm-modify-pwd">
                <label>
                    <span>确认新密码:</span>
                    <input type="password" name="pwdverify" id="confirm-new-pwd" placeholder="请确认与新密码保持一致">
                    <span class="tips"></span>
                </label>
            </div>
            <button type="submit" class="js-ref" name="submit"  href="javascript:;">确定</button>
        </form>
    </div>
</div>

<%--遮挡层--%>
<c:if test="${not empty user}">
    <div id="mask" class=""></div>
    <div id="waiting" class="waiting">
        <div class="waiting-h"><img src="${ctxStatic}/images/i-forget2.png"></div>
        <div class="waiting-b">
            <div class="waiting-txt">密码修改成功</div>
            <a class="btn b-index" href="${ctxroot}/iqgs/index">返回首页</a>
        </div>
    </div>
</c:if>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
</body>
</html>