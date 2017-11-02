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
    <link rel="stylesheet" href="${ctxStatic}/css/manager.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">--%>
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/manager.js"></script>
</head>
<body onload='document.loginForm.username.focus();'>
<dna:dna-header />
<!--header-->
    <div id="containerAdmin">
        <p class="auditUser">待审核用户</p>
        <p class="auditUserInfo">待审核用户信息</p>
        <div id="userDetail">
            <div id="tblhead">
                <table border="1" cellspacing="0" cellpadding="0">
                    <tr style="background:#F5F8FF;">
                        <td style="height:45px;">待审核用户</td>
                        <td style="height:45px;">注册邮箱</td>
                        <td style="height:45px;">状态</td>
                        <td style="height:45px;">确认审核</td>
                    </tr>
                </table>
            </div>
            <div id="tblbody">
                <table border="1" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>fjasdfjfsl333</td>
                        <td>fjasdfjfsl333@163.com</td>
                        <td>待审核</td>
                        <td>
                            <p class="btnAudited btnCommon">已通过</p>
                        </td>
                    </tr>
                    <tr>
                        <td>fjasdfjfs34545</td>
                        <td>fjasdfjfsl333@163.com</td>
                        <td>待审核</td>
                        <td>
                            <p class="btnAudited btnCommon">已通过</p>
                        </td>
                    </tr>
                    <tr>
                        <td>fjasd365456</td>
                        <td>fjasdfjfsl333@163.com</td>
                        <td>待审核</td>
                        <td>
                            <p class="btnAudit btnCommon">待审核</p>
                        </td>
                    </tr>

                </table>
            </div>

        </div>
    </div>
    <div id="paging">
        <div id="page">
            <b class="first">&lt;</b>
            <p class="two"></p>
            <b class="three">...</b>
            <p class="four"></p>
            <p class="five"></p>
            <p class="six"></p>
            <b class="seven">...</b>
            <p class="eight"></p>
            <b class="last">&gt;</b>
            <div id="inputNums">
                <span>跳转到</span>
                <div>
                    <input type="number" name="number" value="" style="width:30px;">
                </div>
                <span>页</span>
                <p style="margin:0px;margin-left:20px;">总数：<span id="totals"></span> 条</p>
            </div>
        </div>
    </div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
</body>
</html>