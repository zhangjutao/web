<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<style>
    .ga-ctrl-footer-snp {
        text-align: right;
        padding: 0 0 20px 0;
    }
    .pagination-snp {
        display: inline-block;
    }
    .per-page-count-snp {
        display: inline-block;
        vertical-align: bottom;
    }
    .pagination-snp .laypage_main * {
        font-size: 14px;
    }
    .lay-per-page-count-select-snp {
        height: 33px;
        padding: 0 0 0 5px;
        box-sizing: border-box;
        border: 1px solid #DDD;
        margin: 0 5px;
    }
    .pagination-snp .laypageskin_molv a, .pagination-snp .laypageskin_molv span {
        border-radius: 0px;
    }
    .pagination-snp .laypage_main a, .pagination-snp .laypage_main span {
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
    .pagination-snp .laypage_main a.laypage_next {
        border-right: 1px solid #ddd;
    }
    .pagination-snp .laypage_main .laypage_total {
        width: 125px;
        border: none;
        margin: 0 20px;
    }
    .pagination-snp .laypageskin_molv .laypage_curr {
        /*border-top: 1px solid #5c8de5;
        border-bottom: 1px solid #5c8de5;*/
        border-top: 1px solid #0F9145;
        border-bottom: 1px solid #0F9145;
    }
    .pagination-snp .laypage_main input {
        height: 33px;
        line-height: 33px;
    }
    .pagination-snp .laypage_main button {
        display: none;
    }
    .total-page-count-snp {
        display: inline-block;
        vertical-align: bottom;
        margin-left: 15px;
        height: 28px;
    }
    #select_page_snp{
        position: relative;
    }
    #select_page_snp .select_default_page_snp{
        position: relative;
        float: left;
        left: 0px;
        top: 0px;
        padding: 0 0 0 0px;
        width: 55px;
        color: #9a9a9a;
        background: #fff;
        height: 35px;
        line-height: 35px;
        border: none;
        margin-right: 5px;
        margin-left: 5px;
        border: 1px solid #ccc;
        text-transform: capitalize;
        text-align: center;
    }
    .select_default_page_snp:after{
        content:"";
        border-left:5px solid transparent;
        border-right:5px solid transparent;
        border-bottom:5px solid #999;
        -webkit-transform-origin:5px 2.5px;
        -moz-transform-origin:5px 2.5px;
        -ms-transform-origin:5px 2.5px;
        -o-transform-origin:5px 2.5px;
        transform-origin:5px 2.5px;
        -webkit-transition: all .5s ease;
        -moz-transition: all .5s ease;
        -ms-transition: all .5s ease;
        -o-transition: all .5s ease;
        transition: all .5s ease;
        position:absolute;
        right:5px;
        top:14px;
    }
    .select_default_page_snp .rotate:after{
        -webkit-transform:rotate(180deg);
        -moz-transform:rotate(180deg);
        -ms-transform:rotate(180deg);
        -o-transform:rotate(180deg);
        transform:rotate(180deg);
    }
    .select_item_page_snp{
        display: none;
        position: absolute;
        top: 35px;
        left: 61px;
        width: 55px;
        float: left;
        z-index: 100;
        margin: 0;
        padding: 0;
        list-style: none;
        background-color: white;
    }
    .select_item_page_snp li{
        width: 53px;
        height: 28px;
        line-height: 28px;
        border: solid 1px #0F9145;
        border-top: none;
        text-align: center;
        border-bottom: none;
    }
    .select_item_page_snp li:hover{
        background:#0F9145;
        color:#fff;
        cursor: pointer;
    }
</style>

<div class="ga-ctrl-footer-snp">
    <div id="pagination-snp" class="pagination-snp"></div>
    <div id="per-page-count-snp" class="per-page-count-snp lay-per-page-count-snp per-page-count-snp" style="width: 170px;">
        <span style="float: left; margin-top: 8px;">展示数量</span>
        <!--
        <select name="" class="lay-per-page-count-select">
            <option value="10">10</option>
            <option value="20">20</option>
            <option value="30">30</option>
            <option value="50">50</option>
        </select>
        -->
        <div id="select_page_snp">
            <input type="text" class="select_default_page_snp lay-per-page-count-select_snp" disabled="disabled" style="background:white">
            <ul class="select_item_page_snp">
                <li style="border-top:1px solid #0F9145;">10</li>
                <li>20</li>
                <li>30</li>
                <li style="border-bottom:1px solid #0F9145;">50</li>
            </ul>
        </div>
        <span style="float: left; margin-top: 8px;  margin-left: 5px;">条/页</span>
    </div>
    <div id="total-page-count-snp" class="total-page-count-snp">总条数<span>0</span></div>
</div>

<script>
    $(document).ready(function(){
        var    $sel_page = $("#select_page"),
            $sel_default_page = $(".select_default_page"),
            $sel_item_page = $(".select_item_page"),
            $sel_item_li_page = $(".select_item_page li");
        $sel_default_page.val($(".select_item_page li:first").text());
        //alert();
        $sel_page.hover(function(){
            $sel_item_page.show();
            console.log('hahaha');
            $sel_default_page.addClass("rotate");
            $sel_item_li_page.hover(function(){
                $index_page = $sel_item_li_page.index(this);
                //alert($index)
                $sel_item_li_page.eq($index_page).addClass("hover");
            },function(){
                $sel_item_li_page.removeClass("hover");
            })
        }, function(){
            $sel_item_page.hide();
            $sel_default_page.removeClass("rotate");
        });
        $sel_item_li_page.click(function(){
            $sel_default_page.val($(this).text());
            //alert($sel_default.val());
            $sel_item_page.hide();
        });
    });
</script>
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