<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="true"%>
<html>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">

    <link rel="stylesheet" href="${ctxStatic}/js/jquery-validation/jquery.validate.min.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
</head>
<body >
<dna:dna-header />
<!--header-->
<div class="container">
    <div class="signup">
        <form method="POST" action="${pageContext.request.contextPath}/signup/action.do" class="form" id="signupForm">
            <c:if test="${not empty error}">
                <div class="er">${error}</div>
            </c:if>
            <div class="reg-u">
                <label>
                    <span><b>*</b>用户名:</span>
                    <input type="text" name="username" id="username" placeholder="请输入用户名" value="${username}"
                           onkeyup="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
                           onpaste="value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')"
                           oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')" required>
                </label>
                <span class="tips real">输入后不可更改,<br/>只支持2到14位的英文和数字</span>
                <span class="tips errorU" style="display:none;">该用户名已存在</span>
            </div>
            <div class="reg-mail">
                <label>
                    <span><b>*</b>邮箱:</span>
                    <input type="email" name="email" id="mail" placeholder="请输入用户邮箱" value="${email}" required>
                    <span class="tips errorM" style="display:none;">该用户名已存在</span>
                </label>
                <span class="tips mailTip" style="display:none;color:#ff0000;">邮箱格式不对</span>
            </div>
            </div>
            <div class="reg-pwd">
                <label>
                    <span><b>*</b>密码:</span>
                    <input type="password" name="password" min="5" id="pwd" placeholder="请输入用户密码" value="${password}" required>
                </label>
                <span class="tips pwdTip">密码长度不能少于5位,且只能包含字符和数字。</span>
            </div>
            <div class="reg-confirm-pwd">
                <label>
                    <span><b>*</b>确认密码:</span>
                    <input type="password" name="passwordVerify" id="confirmPwd" placeholder="请确认用户密码" value="${passwordVerify}" required>
                </label>
                <span class="tips confirmTip" style="display:none;color:#ff0000;">密码不一致</span>
            </div>
            <div class="reg-contact">
                <label>
                    <span>联系方式:</span>
                    <input type="tel" name="phone" id="contact" placeholder="请输入联系方式" value="${phone}">
                </label>
            </div>
            <div class="reg-industry">
                <label>
                    <span>从事行业:</span>
                    <input type="text" name="domains" id="industry" placeholder="请输入从事行业" value="${domains}">
                </label>
                <span class="tips industryTip" style="display:none;color:#ff0000;">不能包含特殊字符</span>
            </div>
            <div class="reg-Colleges">
                <label>
                    <span>所属院校:</span>
                    <input type="text" name="university" id="Colleges" placeholder="请输入所属院校" value="${university}">
                </label>
                <span class="tips CollegesTip" style="display:none;color:#ff0000;">不能包含特殊字符</span>
            </div>
            <div class="stars-tips">带<b>*</b>的选项为必填项</div>
            <button type="submit" class="js-ref" name="submit"  href="javascript:;">提交审核</button>
        </form>
    </div>
</div>
<!--container-->
<%--遮挡层--%>
<c:if test="${not empty user}">
<div id="mask" class=""></div>
<div id="waiting" class="waiting">
    <div class="waiting-h"><img src="${ctxStatic}/images/i-forget2.png"></div>
    <div class="waiting-b">
        <div class="waiting-txt">24小时内审核完成，等待管理员进行联系</div>
        <a class="btn b-index" href="${ctxroot}/dna/index">返回首页</a>
    </div>
</div>
</c:if>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<%--<script src="${ctxStatic}/js/jquery_validate.min.js"></script>--%>
<script>
    // 用户名和邮箱的验证==begin===
    $("#username").blur(function (){
        var val = $(this).val().toLowerCase().trim().toString();
        if(val.length<2 && val!=""){
            $("#username").css("border","1px solid #ff0000");
        }else {
            $("#username").css("border","1px solid #E6E6E6");
        }
    });
    $("#pwd").blur(function (){
        var val = $("#pwd").val();
        console.log(val);
        var reg = /^[0-9a-zA-Z]+$/;
        if(val.toString().length<6 || !reg.test(val.toString())){
            $("#pwd").css("border","1px solid #ff0000");
            $(".pwdTip").show();
        }else {
            $("#pwd").css("border","1px solid #E6E6E6");

        }
    })
    $("#confirmPwd").blur(function (){
       var confirmVal = $(this).val().toString().trim();
       console.log(confirmVal);
       if(confirmVal != $("#pwd").val().toString() && confirmVal !=""){
           $("#confirmPwd").css("border","1px solid #ff0000");
           $(".confirmTip").show();
       }else{
           $("#confirmPwd").css("border","1px solid #E6E6E6");
           $(".confirmTip").hide();
       }
    })
    $("#contact").blur(function (){
        var val = $(this).val().trim().toString();
        RegExp = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
        console.log(val)
        if(!RegExp.test(val) && val !=""){
            $("#contact").css("border","1px solid #ff0000");

        }else {
            $("#contact").css("border","1px solid #E6E6E6");

        }
    })
    $("#industry").blur(function (){
        var val = $(this).val().trim().toString();
        var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
            regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;

        if (regEn.test(val) || regCn.test(val)) {
            $("#industry").css("border","1px solid #ff0000");
            $(".industryTip").show();
        }else {
            $("#industry").css("border","1px solid #E6E6E6");
            $(".industryTip").hide();
        }

    })
    $("#Colleges").blur(function (){
        var val = $(this).val().trim().toString();
        var regEn = /[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im,
            regCn = /[·！#￥（——）：；“”‘、，|《。》？、【】[\]]/im;

        if (regEn.test(val) || regCn.test(val)) {
            $("#Colleges").css("border","1px solid #ff0000");
            $(".CollegesTip").show();
        }else {
            $("#Colleges").css("border","1px solid #E6E6E6");
            $(".CollegesTip").hide();
        }
    })
    $("#mail").blur(function (){
        var val = $(this).val().trim().toString();
        var reg = /^\w+[@][a-zA-Z]+[.]\w+([.]?[a-zA-Z])*$/;
        if(!reg.test(val) && val!=""){
            $("#mail").css("border","1px solid #ff0000");
            $(".mailTip").show();
        }else {
            $("#mail").css("border","1px solid #E6E6E6");
            $(".mailTip").hide();
        }
    })
</script>
</body>
</html>