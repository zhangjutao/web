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

</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
	<div class="detail-name">
		<p>${genId}</p>
	</div>
	<div class="detail-content">
		<iqgs:iqgs-nav focus="4" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="gene-annotation">
                <div class="explain-h">
                    <p>基因注释</p>
                </div>
                <div class="explain-b">
                    <table id="go-gene-annotation">
                        <tbody>
                        <tr><td colspan="3">GO注释</td></tr>
                        <tr><td>GO_Entry</td><td>Anno</td><td>Type</td></tr>
                        <c:forEach items="${gos}" var="go">
                        <tr><td>${go.goEntry}</td><td>${go.anno}</td><td>${go.type}</td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table id="kegg-gene-annotation">
                        <tbody>
                        <tr><td colspan="3">KEGG注释</td></tr>
                        <tr><td>Target_ID</td><td>KO_Entry</td><td>KO_Difinition</td></tr>
                        <c:forEach items="${keggs}" var="kegg">
                            <tr><td>${kegg.targetId}</td><td>${kegg.koEntry}</td><td>${kegg.koDefinition}</td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table id="IPR-gene-annotation">
                        <tbody>
                        <tr><td colspan="3">IPR注释</td></tr>
                        <tr><td>IPR_Entry</td><td>IPR_Anno</td></tr>
                        <c:forEach items="${iprs}" var="ipr">
                        <tr><td>${ipr.iprEntry}</td><td>${ipr.iprAnno}</td></tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
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