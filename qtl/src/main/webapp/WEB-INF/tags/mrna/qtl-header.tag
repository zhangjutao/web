<%@ tag import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ tag import="org.springframework.security.core.Authentication" %>
<%@ tag import="java.security.Principal" %>
<%@ tag import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<header>
    <div class="container">
        <div class="logo">
            <a href="http://www.gooalgene.com" target="_blank" class="qtl-logo-pic"><img src="${ctxStatic}/images/logo.png"></a>
            <a href="${ctxroot}/search/index" class="qtl-data"><img class="back-index" src="${ctxStatic}/images/back-index.png">QTL Database</a>
        </div>
        <div class="login-out">
            <c:choose>
                <c:when test="${not empty userName}">
                    你好,${userName}
                    <a href="${ctxroot}/managerPage">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a href="${ctxroot}/logout" class="tc">退出登录</a>
                </c:when>
                <c:otherwise>
                    <%
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String name = "";
                        if (authentication != null) {
                            Object principal = authentication.getPrincipal();
                            if (principal instanceof UserDetails) {
                                name =  ((UserDetails) principal).getUsername();
                            }else if (principal instanceof Principal) {
                                name =  ((Principal) principal).getName();
                            } else {
                                name =  String.valueOf(principal);
                            }
                        }
                        if (name != null && !"".equals(name) && !"anonymousUser".equals(name)) {
                    %>

                    你好,<sec:authentication property="name"/>
                    <a href="${ctxroot}/managerPage">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a  href="${ctxroot}/logout" class="tc">退出登录</a>
                    <%
                    } else {
                    %>
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active">注册</a>
                    <%
                        }
                    %>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>
<script>
    $(function (){
        console.log(333333);
        var username = '${username}';
        console.log(username);
    })
</script>
