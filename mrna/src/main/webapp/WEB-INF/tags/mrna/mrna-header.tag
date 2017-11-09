<%@ tag import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ tag import="org.springframework.security.core.Authentication" %>
<%@ tag import="java.security.Principal" %>
<%@ tag import="org.springframework.security.core.userdetails.UserDetails" %>
<%@ tag import="org.springframework.security.core.authority.SimpleGrantedAuthority" %>
<%@ tag import="java.util.Collection" %>
<%@ tag import="com.gooalgene.common.authority.Role" %>
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
                <div id="admin" style="display:none;">
                    你好, <span id="username"></span>
                    <a href="${ctxroot}/managerPage" id="adminUser" style="display:none;">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a  href="${ctxroot}/logout" class="tc">退出登录</a>
                </div>
                <div id="general" style="display:none;">
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active">注册</a>
                </div>
        </div>
    </div>
</header>
<script>
    var ctxRoot = '${ctxroot}';
    //判断当前用户是否是管理员
    window.onload = function (){
        $.ajax({
            type:"GET",
            url:ctxRoot+ "/user",
            success:function(result){
                console.log(result);
                if(result.data == null){
                    $("#general").show();
                }else {
                    $("#admin").show();
                    var name = result.data.name;
                    $("#username").text(name);
                    var roles = result.data.authorities;
                    for (var i=0;i<roles.length;i++){
                        if(roles[i].name == "ROLE_ADMIN"){
                            $("#adminUser").show();
                        }
                    }
                }
            }
        })
    }
</script>
