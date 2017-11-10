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
    #admin{
        display:none;
    }
    #general{
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
                <div id="admin">
                    你好, <span class="username"></span>
                    <a href="${ctxroot}/managerPage" id="adminUser" style="display:none;">管理员</a>
                    <a href="${ctxroot}/signup/modifyPassword" class="modifyPassword">修改密码</a>
                    <a  href="${ctxroot}/logout" class="tc">退出登录</a>
                </div>
                <div id="general">
                    <a href="${ctxroot}/login" class="login">登录</a>
                    <a href="${ctxroot}/signup/action" class="register active">注册</a>
                </div>
        </div>
    </div>
</header>
<script>
   var ctxRoot = '${ctxroot}';
   //    判断当前用户是否是管理员
   window.onload = function (){
       $.ajax({
           type:"GET",
           url:ctxRoot+ "/user",
           success:function(result){
               console.log(result);
               if(result.data ==null){
                   $("#general").show();
               }else {
                   $("#admin").show();
                   var name = result.data.name;
                   $(".username").text(name);
                   var roles = result.data.authorities;
                   for (var i=0;i<roles.length;i++){
                       if(roles[i].name == "ROLE_ADMIN"){
                           $("#adminUser").show();

                       }
                   }
               }

           },
           error:function (error){
               console.log(error);
           }
       });
//        console.log( $("#general").get(0));
//       $("#general").on("click",function (e){
//           var $self = $(e.target());
//           console.log($self);
//                debugger;
//           if(!$self.hasClass("active")) {
//               console.log($self.siblings())
//               $self.addClass("active");
//               $($self.siblings()[0]).removeClass("active");
//           }
//
//       })
   }
</script>
