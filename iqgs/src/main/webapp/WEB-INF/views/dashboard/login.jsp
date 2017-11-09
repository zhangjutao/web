<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<%@ include file="/WEB-INF/views/include/dashboard/header.jsp" %>

<body data-type="login" class="theme-white">
    <div class="am-g tpl-g">

        <div class="tpl-login">
            <div class="tpl-login-content">
                <a href="${ctxroot}/index"><div class="tpl-login-logo">
                </div> </a>
                <form class="am-form tpl-form-line-form" modelAttribute="user" method="post" action="${ctxroot}/d/tologin">
                    <div class="am-form-group">
                        <input type="text" class="tpl-form-input" id="username" name="username" value="${user.username}" placeholder="请输入账号">

                    </div>

                    <div class="am-form-group">
                        <input type="password" class="tpl-form-input" id="password" name="password" value="${user.password}" placeholder="请输入密码">

                    </div>
                    <div class="am-form-group"  style="color:red">
                        ${msg}
                    </div>
                    <div class="am-form-group tpl-login-remember-me">
                        <input id="remember-me" type="checkbox">
                        <label for="remember-me">
       
                        记住密码
                         </label>

                    </div>
                    <div class="am-form-group">

                        <button type="submit" class="am-btn am-btn-primary  am-btn-block tpl-btn-bg-color-success  tpl-login-btn">提交</button>

                    </div>
                </form>
            </div>
        </div>
    </div>

</body>

</html>