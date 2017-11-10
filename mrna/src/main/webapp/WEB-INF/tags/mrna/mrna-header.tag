<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<header>
    <style type="text/css">
        img.back-index {
            width: 56px;
            height: 30px;
            margin-top: -10px;
            margin-right: 20px;
        }
    </style>
    <div class="container">
        <div class="logo">
            <a href="http://www.gooalgene.com" target="_blank" class="qtl-logo-pic"><img src="${ctxStatic}/images/logo.png"></a>
            <a href="${ctxroot}/mrna/index" class="qtl-data"><img class="back-index" src="${ctxStatic}/images/back-index.png">Gene Expression Database</a>
        </div>
        <div class="login-out">
            <%-- 如果从登录页面跳转到MRNAHomeController，然后跳转到index页面时，会携带userName参数
             否则通过spring security拦截或者其它方式跳转到其它页面，该页面又包含这个header，那么需要从
             全局中找到这个Role对象，有则已登录，否则未登录 --%>
            <c:choose>
                <c:when test="${not empty userName}">
                    你好,${userName}
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="${ctxroot}/managerPage" id="adminUser" style="display:none;">管理员</a>
                    </sec:authorize>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a href="${ctxroot}/logout" class="tc">退出登录</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
