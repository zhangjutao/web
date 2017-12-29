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

</head>

<body>

<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
	<div class="detail-name">
		<p>${dna.geneId}</p>
	</div>
	<div class="detail-content">
		<iqgs:iqgs-nav focus="1" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="basic">
				<div class="explain-h">
					<p>基本信息</p>
				</div>
				<div class="explain-b">
					<table>
						<tbody>
							<tr><td>基因ID(V2.0)</td><td>${dna.geneId}</td></tr>
							<tr><td>基因ID(V1.1)</td><td>${dna.geneOldId}</td></tr>
							<tr><td>基因名</td><td>${dna.geneName}</td></tr>
							<tr><td>基因类型</td><td>${dna.geneType}</td></tr>
							<tr><td>基因位置</td><td>${dna.locus}</td></tr>
							<tr><td>基因长度</td><td>${dna.length}</td></tr>
							<tr><td>所属物种</td><td>${dna.species}</td></tr>
							<tr><td>基因描述</td><td>${dna.description}</td></tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->




</body>
</html>