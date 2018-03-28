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
            <!--<a href="http://www.gooalgene.com" target="_blank" class="qtl-logo-pic"><img src="${ctxStatic}/images/logo.png"></a>
            <a href="${ctxroot}/dna/index" class="qtl-data"><img class="back-index" src="${ctxStatic}/images/back-index.png">SNP INDEL Database</a>-->
            <a href="${ctxroot}/dna/index" class="qtl-data"><img class="back-index" src="${ctxStatic}/images/mushroom.png">SNP INDEL Database</a>
        </div>
        <div class="login-out">
            <sec:authorize var="login" access="isAuthenticated()"/>
            <c:choose>
                <c:when test="${login}">
                    <sec:authentication property="name" var="authenticationName" />
                    你好, ${authenticationName}
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="${ctxroot}/managerPage">管理员</a>
                    </sec:authorize>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a href="${ctxroot}/logout" class="tc">退出登录</a>
                </c:when>
                <c:otherwise>
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active" style="background:#0F9145;color:#fff;">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
