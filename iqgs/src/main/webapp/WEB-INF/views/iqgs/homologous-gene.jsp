<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<title>IQGS details</title>
	<link rel="stylesheet" href="${ctxStatic}/css/public.css">
	<link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
	<link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
	<!--jquery-1.11.0-->
	<script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
	<script src="${ctxStatic}/js/d3.js"></script>
<style>
	.explain-list .explain-b tbody tr td:first-child{
		width:auto;
	}
</style>
</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
	<div class="detail-name">
		<p>${genId}</p>
	</div>
	<div class="detail-content">
		<iqgs:iqgs-nav focus="6" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="homologous-gene">
				<div class="explain-h">
					<p>同源基因</p>
				</div>
				<c:if test="${not empty homologous}">
				<div class="explain-b">
                    <table>
                        <thead>
                            <tr>
								<th>Gene ID</th>
								<th>Ortholog Species</th>
								<th>Ortholog Gene ID</th>
								<th>Ortholog Gene Description</th>
								<th>Relationship</th>
							</tr>
                        </thead>
                        <tbody>
						<c:forEach items="${homologous}" var="homo">
                            <tr>
								<td>${homo.geneId}</td>
								<%--<td><a class="arabi-link" target="_blank" href="http://www.arabidopsis.org/servlets/Search?type=general&search_action=detail&method=1&show_obsolete=F&name=${homo.arabiId}&sub_type=gene&SEARCH_EXACT=4&SEARCH_CONTAINS=1">${homo.arabiId}</a></td>--%>
								<td>${homo.orthologSpecies}</td>
								<td>${homo.OrthologGeneId}</td>
								<td>${homo.orthologGeneDescription}</td>
								<td>${homo.relationship}</td>
							</tr>
						</c:forEach>
                        </tbody>
                    </table>
				</div>
				</c:if>
				<c:if test="${empty homologous}">
				<div class="explain-b" style="text-align: center">
					<img src="${ctxStatic}/images/nodata.png">
					<div style="padding-top: 10px">无同源基因信息</div>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->

<script>
	$(function(){

		$(".item li").each(function(i){
			$(this).click(function(){
				$(this).addClass("item-ac").siblings().removeClass("item-ac");
				$(".tab > div").eq(i).show().siblings().hide();
			})
		})
	})

</script>

</body>
</html>