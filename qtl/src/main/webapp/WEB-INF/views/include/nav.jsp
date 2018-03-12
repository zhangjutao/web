<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<link href="https://cdn.bootcss.com/normalize/7.0.0/normalize.min.css" rel="stylesheet">
<style>
    .nav li:nth-child(8) .nav-right-border{
        height: 65px;;
    }
</style>
<ul class="nav">
    <c:forEach items="${types}" var="item">
        <li>
            <a href="javascript:void(0);">
                <div class="text">
                    <p class="n-text">${item.desc}</p>
                    <p class="s-text">${item.name}</p>

                </div>
                <div class="arrow-right"><img src="${ctxStatic}/images/arrow-right.png"></div>
            </a>
            <div class="nav-right-border" style=" "></div>
            <div class="second-text" style="display:none ">
                <c:forEach items="${item.data}" var="item1">
                    <a href="${ctxroot}/search/list?type=Trait&keywords=${item1.traitName}&version=Gmax_275_v2.0">${item1.traitName}</a>
                </c:forEach>
            </div>
        </li>
    </c:forEach>
</ul>

<script>
    $(function(){
        $(".nav").find("li").hover(
            function (e) {
                $(this).find(".nav-right-border").css({"display":"block"});
                $(this).css({"border":"1px solid #386cca","border-right":"1px solid #fff"});
                $(this).find(".second-text").css({"display":"block"});
                $(this).find("img").attr("src","${ctxStatic}/images/ac-arrow-right.png")
            },
            function (e) {
                $(this).find(".nav-right-border").css({"display":"none"})
                $(this).css({"border":"1px solid #fff","border-bottom":"1px solid #e6e6e6"});
                $(this).find(".second-text").css({"display":"none"});
                $(this).find("img").attr("src","${ctxStatic}/images/arrow-right.png")
            }
        );
    })

</script>