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
    <style type="text/css">
       .confirmTip,.pwdTip{
           color:#ff0000;
           display:none;
       }
    </style>
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
                           oncontextmenu = "value=value.replace(/[^\a-\z\A-\Z0-9]/g,'')">
                </label>
                <span class="tips real">输入后不可更改,<br/>只支持2到14位的英文和数字</span>
                <span class="tips errorU" style="display:none;">该用户名已存在</span>
            </div>
            <div class="reg-mail">
                <label>
                    <span><b>*</b>邮箱:</span>
                    <input type="email" name="email" id="mail" placeholder="请输入用户邮箱" value="${email}">
                    <span class="tips errorM" style="display:none;">该用户名已存在</span>
                </label>
            </div>
            <div class="reg-pwd">
                <label>
                    <span><b>*</b>密码:</span>
                    <input type="password" name="password" min="5" id="pwd" placeholder="请输入用户密码" value="${password}">
                </label>
                <span class="tips pwdTip">密码长度不能少于5位,且只能包含字符和数字。</span>
            </div>
            <div class="reg-confirm-pwd">
                <label>
                    <span><b>*</b>确认密码:</span>
                    <input type="password" name="passwordVerify" id="confirmPwd" placeholder="请确认用户密码" value="${passwordVerify}">
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
                    <input type="tel" name="domains" id="industry" placeholder="请输入从事行业" value="${domains}">
                </label>
            </div>
            <div class="reg-Colleges">
                <label>
                    <span>所属院校:</span>
                    <input type="tel" name="university" id="Colleges" placeholder="请输入所属院校" value="${university}">

                </label>
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

<script src="${ctxStatic}/js/jquery_validate.min.js"></script>
<script>
    // 用户名和邮箱的验证==begin===
    $("#username").blur(function (){
        var val = $(this).val().toLowerCase().trim().toString();
        if(val.length<2){
            $("#username").css("border","1px solid #ff0000");
        }else {
            $("#username").css("border","1px solid #E6E6E6");
        }

    });
    $("#mail").blur(function (){
        var val = $("#mail").val();
        console.log(val);
        $.ajax({
            type:"POST",
            url:"",
            data:val,
            success:function (result){
                //定义返回字段为boolean 类型；
                if(result == true){
                    $(".errorM").show().css("color","#ff0000");
                };
            },
            error:function (error){
                console.log(error)
            }
        })
    })
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
        var reg1 = /0?(13|14|15|17|18)[0-9]{9}/;
        var reg2 = /^0[0-9]{2,3}-[0-9]{8}/;
        console.log(val)
        console.log(!reg1.test(val));
//        console.log(!reg2.test(val));
        if(!reg1.test(val)){
            console.log(333)
            $("#contact").css("border","1px solid #ff0000");
        }else {
            $("#contact").css("border","1px solid #E6E6E6");
        }
    })
//    =======end======
    $("#signupForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 2,
                maxlength:14
            },
            password: {
                required: true,
                minlength: 5
            },
            confirm_password: {
                required: true,
                minlength: 5,
                equalTo: "#pwd"
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            username: {
                required: "请输入用户名",
                minlength: "用户名长度不能小于2个字符",
                maxlength: "用户名长度不能大于14个字符"
            },
            password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字符"
            },
            confirm_password: {
                required: "请输入密码",
                minlength: "密码长度不能小于 5 个字符",
                equalTo: "两次密码输入不一致"
            },
            email: "请输入一个正确的邮箱"
        },
        errorPlacement: function(error, element) {
            error.appendTo(element.parent().parent());
        }
    });
</script>
</body>
</html>