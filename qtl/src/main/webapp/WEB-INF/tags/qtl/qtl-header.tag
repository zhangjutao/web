<%@ tag import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ tag import="org.springframework.security.core.Authentication" %>
<%@ tag import="java.security.Principal" %>
<%@ tag import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ tag import="com.gooalgene.common.authority.Role" %>
<%@ tag import="java.util.Collection" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<style>
    /*#general{*/
        /*display:none;*/
    /*}*/
    #admin{
        display:none;
    }
</style>
<header>
    <div class="container">
        <div class="logo">
            <a href="http://www.gooalgene.com" target="_blank" class="qtl-logo-pic"><img src="${ctxStatic}/images/logo.png"></a>
            <a href="${ctxroot}/search/index" class="qtl-data"><img class="back-index" src="${ctxStatic}/images/back-index.png">QTL Database</a>
        </div>
        <div class="login-out">
            <%--<c:choose>--%>
                <%--<c:when test="${not empty userName}">--%>
                    <%--你好,<span class="username"></span>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a href="${ctxroot}/logout" class="tc">退出登录</a>--%>
                <%--</c:when>--%>
                <%--<c:otherwise>--%>
                    <%--<%
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
                            // 拿到当前用户角色
                            Collection<Role> authorities = (Collection<Role>) authentication.getAuthorities();
                            for (Role role : authorities){
                                if (role.getAuthority().equals("ROLE_ADMIN")){
                    %>--%>
                    <%--你好,&lt;%&ndash;<sec:authentication property="name"/>&ndash;%&gt;<span class="username"></span>
                    <a href="${ctxroot}/managerPage" id="adminUser" style="display:none;">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a  href="${ctxroot}/logout" class="tc">退出登录</a>--%>
                    <%--<%}else {%>--%>
                <div id="admin">
                    你好, <span class="username"></span>
                    <a href="${ctxroot}/managerPage" id="adminUser" style="display:none;">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a  href="${ctxroot}/logout" class="tc">退出登录</a>
                </div>

                    <%--<%}
                    }
                    } else {
                    %>--%>
                <div id="general">
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active">注册</a>
                </div>

                    <%--<%
                        }
                    %>--%>
                    <%--</c:otherwise>
                </c:choose>--%>
        </div>
    </div>
</header>
<script>
   var ctxRoot = '${ctxroot}';
</script>
