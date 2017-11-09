<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true"%>
<html>
<head>
    <title>忘记密码</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body >

<mrna:mrna-header />
<div class="container">
    <div class="forget-h"><p>忘记密码</p></div>
    <div class="forget">
        <form method="POST" action="${ctxroot}/signup/forget">
            <c:if test="${not empty error}">
                <div class="error">${error}</div>
            </c:if>
            <div class="forget-u">
                <label>
                    <span>用户姓名:</span>
                    <input type="text" name="username" id="username" placeholder="请输入您注册时用户姓名" value="${username}">
                </label>
            </div>
            <div class="forget-mail">
                <label>
                    <span>邮箱:</span>
                    <input type="email" name="email" id="mail" placeholder="请输入注册时用户邮箱" value="${email}">
                    <span class="tips"></span>
                </label>
            </div>
            <button type="submit" class="js-ref" name="submit"  href="javascript:;">联系管理员</button>
        </form>
    </div>
</div>
<!--container-->
<c:if test="${not empty user}">
    <div id="mask" class=""></div>
    <div id="waiting" class="waiting">
        <div class="waiting-h"><img src="${ctxStatic}/images/i-forget2.png"></div>
        <div class="waiting-b">
            <div class="waiting-txt">24小时内审核完成，等待管理员进行联系</div>
            <a class="btn b-index" href="${ctxroot}/iqgs/index">返回首页</a>
        </div>
    </div>
</c:if>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script type="text/javascript">
    $(function (){
        var user = '${user}';
        console.log(user);
        if(user!=""){
            var inputMail = $("#mail").val();
            var mailType = inputMail.split("@")[1].split(".")[0];
            if(mailType == "gmail"){
                console.log($("#mailAdress").get(0));
                $("#mailAdress").attr("href","https://mail.google.com");
            }else if (mailType == "qq"){
                $("#returnType>a").attr("href","https://mail.qq.com/")
            }else if (mailType == "163"){
                $("#returnType>a").attr("href","http://mail.163.com/")
            }else if (mailType == "sina"){
                $("#returnType>a").attr("href","http://mail.sina.com.cn/")
            }else {
                $("#returnType").hide().next().show();
            }
        }
    })
</script>
</body>
</html>