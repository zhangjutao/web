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
    <script src="${ctxStatic}/js/clipboard.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>

</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
	<div class="detail-name">
		<p>${genId}</p>
	</div>
	<div class="detail-content">
		<iqgs:iqgs-nav focus="2" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="basic">
                <div class="explain-h">
                    <p>参考序列</p>
                </div>
                <div class="explain-b">
                    <table>
                        <tbody>
                        <c:forEach items="${dnas}" var="dna">
                            <tr>
                                <td>
                                    <p class="sequence-name"><b>${dna.type}</b></p>
                                    <button class="copy"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button>
                                </td>
                                <td>
                                    <p class="gene-sequence">${dna.sequence}</p>
                                </td>
                            </tr>
                        </c:forEach>
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
<script>
//    var t=document.getElementById("txt");
//    t.select();
//    window.clipboardData.setData('text',t.createTextRange().text);

    $('.copy').each(function(i){
        $(this).click(function(){
            $(".gene-sequence").removeClass("copy-ac")
            var obj=$(this).parent().siblings().find(".gene-sequence");
            obj.addClass("copy-ac");
            var t=obj.text();
            var clipboard = new Clipboard('.copy', {
                text: function() {
                    return t;
                }
            });
            clipboard.on('success', function(e) {
               layer.msg("复制成功！")
                console.log(t);
            });

            <%--$(this).zclip({--%>
                <%--path: "${ctxStatic}/js/zclip/ZeroClipboard.swf",--%>
                <%--copy: function(){--%>
                    <%--return $(this).parent().siblings().find(".gene-sequence").text();--%>
                <%--},--%>
                <%--afterCopy:function(){/* 复制成功后的操作 */--%>
                    <%--console.log($(this).parent().siblings().find(".gene-sequence").text());--%>
                    <%--$(this).parent().siblings().find(".gene-sequence").css({"background-color":"#5c8ce6","color":"#fff"});--%>

                <%--}--%>
            <%--});--%>

        });
    });

</script>
</body>
</html>