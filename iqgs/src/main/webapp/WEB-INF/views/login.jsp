<%--
  Created by IntelliJ IDEA.
  User: hedingwei
  Date: 16/10/2017
  Time: 12:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true"%>
<html>
<head>
    <title>Login Page</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body onload='document.loginForm.username.focus();'>
<iqgs:iqgs-header></iqgs:iqgs-header>
<!--header-->
<div class="container">
    <div class="loginC">
        <div class="login-h">
            <p>账号密码登录</p>
        </div>
        <div class="login-b">
            <form method="POST" action="<c:url value='/j_spring_security_check' />" name='loginForm' class="form">
                <c:if test="${not empty error}">
                    <div class="er">${error}</div>
                </c:if>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <label class="user" for="u-mail"><input type="text" id="u-mail" name="username" placeholder="用户名/邮箱"/><span class="clear js-clear-u">X</span></label>
                <label class="pwd"  for="pwd"><input type="password" id="pwd" name="password" placeholder="密码"/><span class="clear js-clear-p">X</span></label>
                <div class="m-unlogin">
                    <%--<div class="un-login">--%>
                        <%--<input type="checkbox" checked/>下次自动登录--%>
                    <%--</div>--%>
                    <a href="${ctxroot}/signup/forget" class="forgetpwd">忘记密码</a>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                <button type="submit" href="javascript:;" class="loginbox">登录</button>
                <div class="reg">
                    <p>还没有账号,<a href="${ctxroot}/signup/action">立即注册</a></p>
                </div>
            </form>
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<%--<div id="login-box">--%>

    <%--<h2>登录</h2>--%>

    <%--<c:if test="${not empty error}">--%>
        <%--<div class="error">${error}</div>--%>
    <%--</c:if>--%>
    <%--<c:if test="${not empty msg}">--%>
        <%--<div class="msg">${msg}</div>--%>
    <%--</c:if>--%>

    <%--<form name='loginForm'--%>
          <%--action="<c:url value='/j_spring_security_check' />" method='POST'>--%>

        <%--<table>--%>
            <%--<tr>--%>
                <%--<td>User:</td>--%>
                <%--<td><input type='text' name='username'></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>Password:</td>--%>
                <%--<td><input type='password' name='password' /></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td colspan='2'><input name="submit" type="submit"--%>
                                       <%--value="submit" /></td>--%>
            <%--</tr>--%>
        <%--</table>--%>


    <%--</form>--%>
<%--</div>--%>

</body>
</html>