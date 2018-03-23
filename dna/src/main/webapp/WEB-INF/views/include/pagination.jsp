<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<style>
    .ga-ctrl-footer {
        text-align: right;
        position: relative;
        padding: 0 0 20px 0;
    }
    .pagination {
        display: inline-block;
    }
    .per-page-count {
        display: inline-block;
        vertical-align: bottom;
    }
    .pagination .laypage_main * {
        font-size: 14px;
    }
    .lay-per-page-count-select {
        height: 33px;
        padding: 0 0 0 5px;
        box-sizing: border-box;
        border: 1px solid #DDD;
        margin: 0 5px;
    }
    .pagination .laypageskin_molv a, .pagination .laypageskin_molv span {
        border-radius: 0px;
    }
    .pagination .laypage_main a, .pagination .laypage_main span {
        margin: 0;
        height: 33px;
        width: 33px;
        padding: 0;
        text-align: center;
        line-height: 33px;
        border-bottom: 1px solid #ddd;
        border-left: 1px solid #ddd;
        border-top: 1px solid #ddd;
        background: #fff;
    }
    .pagination .laypage_main a.laypage_next {
        border-right: 1px solid #ddd;
    }
    .pagination .laypage_main .laypage_total {
        width: 125px;
        border: none;
        margin: 0 20px;
    }
    .pagination .laypageskin_molv .laypage_curr {
        border-top: 1px solid #5c8de5;
        border-bottom: 1px solid #5c8de5;
    }
    .pagination .laypage_main input {
        height: 33px;
        line-height: 33px;
    }
    .pagination .laypage_main button {
        display: none;
    }
    .total-page-count {
        display: inline-block;
        vertical-align: bottom;
        margin-left: 15px;
        height: 28px;
    }
</style>

<div class="ga-ctrl-footer">
    <div id="pagination" class="pagination"></div>
    <div id="per-page-count" class="per-page-count lay-per-page-count per-page-count">
        <span>展示数量</span>
        <select name="" class="lay-per-page-count-select">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
            <option value="50">50</option>
        </select>
        <span>条/页</span>
    </div>
    <div id="total-page-count" class="total-page-count">总条数 <span>0</span></div>
</div>


<%--<script>--%>
    <%--$(function() {--%>

        <%--laypage({--%>
            <%--cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象,--%>
            <%--pages: 11, //总页数--%>
            <%--skip: true, //是否开启跳页--%>
            <%--skin: '#5c8de5',--%>
            <%--first: 1, //将首页显示为数字1,。若不显示，设置false即可--%>
            <%--last: 11, //将尾页显示为总页数。若不显示，设置false即可--%>
            <%--prev: '<', //若不显示，设置false即可--%>
            <%--next: '>', //若不显示，设置false即可--%>
            <%--groups: 3 //连续显示分页数--%>
        <%--});--%>

    <%--})--%>
<%--</script>--%>