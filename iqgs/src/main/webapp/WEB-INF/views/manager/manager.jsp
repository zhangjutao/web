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
    <script type="text/javascript">
        var ctxRoot = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/manager.js"></script>

</head>
<body onload='document.loginForm.username.focus();'>
<iqgs:iqgs-header />
<!--header-->
    <div id="containerAdmin">
        <p class="auditUser">待审核用户</p>
        <p class="auditUserInfo">待审核用户信息</p>
        <div id="userDetail">
            <div id="tblhead">
                <table border="1" cellspacing="0" cellpadding="0" bordercolor="#ccc">
                    <tr style="background:#F5F8FF;">
                        <td style="height:45px;">待审核用户</td>
                        <td style="height:45px;">注册邮箱</td>
                        <td style="height:45px;width:159px;" >从事行业</td>
                        <td style="height:45px;">所属院校</td>
                        <td style="height:45px;">状态</td>
                        <td style="height:45px;">剩余天数</td>
                        <td style="height:45px;">确认审核</td>
                    </tr>
                </table>
            </div>
            <div id="tblbody">
                <table border="1" cellspacing="0" cellpadding="0" bordercolor="#ccc">


                </table>
            </div>

        </div>
        <div id="errorImg">
            <img src="${ctxStatic}/images/430.jpg" alt="errorImg">
            <p id="errorDes">
                好像出现了一些小问题，请您返回重试
            </p>
            <p id="returnIndex">
                点此 <a href="${ctxroot}/login">返回首页</a>
            </p>
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
        </div>
        <div id="inputNums">
            <span>跳转到</span>
            <div>
            <input type="number" min="1" name="number" value="" id="inputNum" >
            </div>
            <span>页</span>
            <span>展示数量</span>
            <div id="selectedNum">
                <select name="selected" id="selectSize">
                    <option value="10" selected = "true">10</option>
                    <option value="10">20</option>
                    <option value="10">30</option>
                    <option value="10">40</option>
                </select>
            </div>

            <span>/页</span>
            <p style="margin:0px;margin-left:20px;">总数：<span id="totals"></span> 条</p>
        </div>
    </div>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

</body>
</html>