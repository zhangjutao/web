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
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body onload='document.loginForm.username.focus();'>
<dna:dna-header />
<div class="container">
    <div class="loginC">
        <div class="login-h">
            <p>账号密码登录</p>
        </div>
        <div class="login-b">
            <form method="POST"  name='loginForm' class="form">
                <c:if test="${not empty error}">
                    <div class="er">${error}</div>
                </c:if>
                <div class="er"></div>
                <c:if test="${not empty msg}">
                    <div class="msg">${msg}</div>
                </c:if>
                <label class="user" for="u-mail"><input type="text" id="u-mail" name="username" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" placeholder="用户名/邮箱" style="line-height:36px;"/><span class="clear js-clear-u">X</span></label>
                <label class="pwd"  for="pwd"><input type="password" id="pwd" name="password" placeholder="密码" style="line-height:36px;"/><span class="clear js-clear-p">X</span></label>
                <div class="m-unlogin">
                    <a href="${ctxroot}/signup/forget" class="forgetpwd" style="color:#5C8CE6;">忘记密码</a>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"  style="line-height:36px;"/>
                <input type="hidden" name="grant_type" value="password">
                <input type="hidden" name="authorization" value="ZG5hOmRuYQ==">
                <button type="button" href="javascript:;" class="loginbox">登录</button>
                <div class="reg">
                    <p>还没有账号,<a href="${ctxroot}/signup/action"> 立即注册</a></p>
                </div>
            </form>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script src="${ctxStatic}/js/webSocket.js"></script>
<script>
    $.fn.serializeObject = function(){
        var o = {};
        var a = this.serializeArray();
        $.each(a, function() {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    function enableLogin() {
        $('.loginbox').on('click',function () {
            console.log($('.form').serializeObject());
            var data=$('.form').serializeObject();
            $.post("${ctxroot}/sso/token",data,function (result) {
                console.log(JSON.stringify(result))
                if(result.code!=0){
                    var msg=result.msg;
                    $('.er').text(msg);
                }else {
                    var token=result.data;
                    //sessionStorage.setItem("access_token",token);
                    localStorage.setItem("access_token",token);
                    window.location.href="${ctxroot}/dna/index?access_token="+token;
                }
            });
        })
    }

    function setTokenInHeader() {
        var token=sessionStorage.getItem("access_token");
        if(token){
            $.ajaxSetup( {
                headers: { // 默认添加请求头
                    "Authorization": "bearer "+token
                } ,
            });
        }
    }
    function init() {
        $('.er').text("");
        $('.msg').text("");
    }
    $(function () {
        init();
        enableLogin();
    })
</script>
</body>
</html>