<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>


<button id="back-btn" type="button" class="am-btn am-btn-default">返回</button>


<script>
    $(function() {

        $("#back-btn").click(function(){
            window.history.back();
        });

    })
</script>