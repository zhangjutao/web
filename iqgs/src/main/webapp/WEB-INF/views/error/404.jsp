<%@ page import="com.gooalgene.common.Servlets" %><%
response.setStatus(404);

// 如果是异步请求或是手机端，则直接返回信息
if (Servlets.isAjaxRequest(request)) {
	out.print("页面不存在.");
}

//输出异常信息页面
else {
%>
<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>404 - 页面不存在</title>
	<%--<%@include file="/WEB-INF/views/include/header-bar.jsp" %>--%>
</head>
<body>
	<%--<div class="container-fluid">--%>
		<%--<div class="page-header"><h1>页面不存在.</h1></div>--%>
		<%--<div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>--%>
		<%--<script>try{top.$.jBox.closeTip();}catch(e){}</script>--%>
	<%--</div>--%>

	<div style="width:1200px;text-align:center;margin:60px auto;">
		<%@include file="/WEB-INF/views/include/header-bar.jsp" %>

	</div>
	<div class="container-fluid" style="width:1200px;text-align:center;margin:0 auto;">
		<div class="page-header" style="padding-bottom:30px;">
			<img id="errorPage" src="${ctxStatic}/images/430.jpg"/>
			<%--<img style="width:600px;height:300px;" id="errorPage" src="${ctxStatic}/images/430.jpg"/>--%>

		</div>
		<div style="margin-bottom:20px;">您访问的页面不存在</div>
		<div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>
<%
out.print("<!--"+request.getAttribute("javax.servlet.forward.request_uri")+"-->");
} out = pageContext.pushBody();
%>