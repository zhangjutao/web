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
    <title>蘑菇基因表达量数据库</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js//highcharts/highcharts.js"></script>
    <script src="${ctxStatic}/js/highcharts/highcharts-more.js"></script>
    <script src="${ctxStatic}/js/highcharts/exporting.js"></script>
    <!--<script src="${ctxStatic}/js/highcharts/heatmap.js"></script>-->
    <script src="${ctxStatic}/js/highcharts/heatmap1.js"></script>
    <script src="${ctxStatic}/js/highcharts/highcharts-zh_CN.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <style>
        .index-highcharts {
            padding: 20px;
            box-sizing: border-box;
        }
        #select{
            position: relative;
        }
        #select .select_default{
            position: relative;
            float: left;
            left: 159px;
            top: 2px;
            padding: 0 0 0 0px;
            width: 120px;
            color: #9a9a9a;
            background: #fff;
            border-top-left-radius: 15px;
            border-bottom-left-radius: 15px;
            height: 35px;
            line-height: 35px;
            border: none;
            margin-right: 0px;
            border-right: 0px solid black;
            text-transform: capitalize;
            text-align: center;
        }
        .select_default:after{
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
        .select_default .rotate:after{
            -webkit-transform:rotate(180deg);
            -moz-transform:rotate(180deg);
            -ms-transform:rotate(180deg);
            -o-transform:rotate(180deg);
            transform:rotate(180deg);
        }
        .select_item{
            display:none;
            position: absolute;
            top: 37px;
            left: 158px;
            width: 120px;
            float: left;
            z-index: 100;
            margin: 0;
            padding: 0;
            list-style: none;
            background-color: white;
        }
        .select_item li{
            width: 120px;
            height: 28px;
            line-height: 28px;
            border: solid 1px #0F9145;
            border-top: none;
            text-align: center;
            border-bottom: none;
        }
        .select_item li:hover{
            background:#0F9145;
            color:#fff;
            cursor: pointer;
        }
    </style>
</head>

<body>

<mrna:mrna-header />
<!--header-->
<section class="container">
    <div class="banner">
        <div class="plant-pic">
            <img src="${ctxStatic}/images/mRNA-banner-mogu-bg.jpg">
        </div>
        <div class="search">
            <!--
            <select class="js-search-select">
                <option>All</option>
                <option>Study</option>
                <option>Tissues</option>
                <option>Stage</option>
                <option>Treat</option>
                <option>Reference</option>
            </select>
            -->
            <div id="select">
                <input type="text" class="select_default" value="All" id="selectValue">
                <ul class="select_item">
                    <li style="border-top:1px solid #0F9145;">All</li>
                    <li>Study</li>
                    <li>Tissues</li>
                    <li>Stage</li>
                    <li>Treat</li>
                    <li style="border-bottom:1px solid #0F9145;">Reference</li>
                </ul>
            </div>
            <label>
                <input id="search-input" class="js-search-text" type="text" name="search" placeholder="输入您要查找的关键字">
                <span class="clear-input " style="display: none"><img src="${ctxStatic}/images/clear-search.png"></span>
                <button id="search-btn"><img src="${ctxStatic}/images/search.png">搜索</button>
            </label>
        </div>
    </div>
    <aside>
        <div class="item-header">
            <div class="icon-left"><img src="${ctxStatic}/images/tissue.png">组织<i class="">TISSUE</i></div>
        </div>
        <%@ include file="/WEB-INF/views/include/nav-mnra.jsp" %>
    </aside>
    <article style="height: 503px;">
        <div class="item-header">
            <div class="icon-left"><img src="${ctxStatic}/images/heatmap.png">组织特异性表达热图</div>
        </div>
        <div class="index-highcharts heatmapHeigth" style="height: 400px;" >
            <mrna:chart-heatmap data="${data}" gaHeight="400"></mrna:chart-heatmap>
        </div>
    </article>
</section>
<!--section-->
<div class="container explain">
    <div class="explain-title">
        <img src="${ctxStatic}/images/explain.png">数据库概况
    </div>
    <div class="explain-text">
        ${mrnaDetail}
        <%--本数据库提供了510个大豆样本的基因表达数据，涉及大豆的不同发育时期、不同组织、真菌侵染、无机物耐性等条件下大豆的基因表达情况。所有数据均来源于NCBI SRA数据库的相关公共数据，在复现大豆转录组测序实验分析结果的同时，结合基因注释信息为研究人员提供新的参考及数据关联信息。--%>
    </div>
</div>
<!--explain-->
<form action="${ctxroot}/specific/index" method="POST" id="specificForm" style="display: none;">
    <input type="text" name="genes">
</form>

<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
    $(document).ready(function(){
        var    $sel = $("#select"),
            $sel_default = $(".select_default"),
            $sel_item = $(".select_item"),
            $sel_item_li = $(".select_item li")
        $sel_default.text($(".select_item li:first").text());
        //alert();
        $sel.hover(function(){
            $sel_item.show();
            $sel_default.addClass("rotate");
            $sel_item_li.hover(function(){
                $index = $sel_item_li.index(this);
                //alert($index)
                $sel_item_li.eq($index).addClass("hover");
            },function(){
                $sel_item_li.removeClass("hover");
            })
        },function(){
            $sel_item.hide();
            $sel_default.removeClass("rotate");
        });
        $sel_item_li.click(function(){
            /*$sel_default.text($(this).text());*/
            $sel_default.val($(this).text());
            var select=$(this).text();
            $(".js-search-text").attr("placeholder","");
            console.log(select);
            switch (select){
                case "Study":
                    $(".js-search-text").attr("placeholder","输入您要查找的关键字");
                    break;
                case "Tissues":
                    $(".js-search-text").attr("placeholder","输入您要查找的关键字");
                    break;
                case "Stage":
                    $(".js-search-text").attr("placeholder","输入您要查找的关键字");
                    break;
                case "Treat":
                    $(".js-search-text").attr("placeholder","输入您要查找的关键字");
                    break;
                case "Reference":
                    $(".js-search-text").attr("placeholder","输入您要查找的关键字");
                    break;
            }
            //test
            $sel_item.hide();
        });
    });

    /*$(function () {
        $(".js-search-select").change(function(){
            var select=$(this).val();
            $(".js-search-text").attr("placeholder","");
            console.log(select);
            switch (select){
                case "Study":
                    $(".js-search-text").attr("placeholder","RNA-seq of soybean");
                    break;
                case "Tissues":
                    $(".js-search-text").attr("placeholder","root leaf");
                    break;
                case "Stage":
                    $(".js-search-text").attr("placeholder","16 days v1 stage");
                    break;
                case "Treat":
                    $(".js-search-text").attr("placeholder","water_deficit_12hr");
                    break;
                case "Reference":
                    $(".js-search-text").attr("placeholder","Ling H, John S. Conserved Gene Expression Programs in Developing Roots from Diverse Plants[J]. Plant Cell, 2015, 27(8):2119-32");
                    break;
            }
        })
        $("#search-btn").click(function(){
            var s_option=$(".search select").val();
            var i_input=$.trim($("#search-input").val());
            window.location.href='${ctxroot}/mrna/list?type='+s_option+"&keywords="+i_input+"&isIndex=1";
        })
        $("#search-input").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $("#search-input").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(document).keyup(function(event){
            var _searchDom = $("#search-input");
            var e=e||event
            var keycode = e.which;
            if(keycode==13){

                if(_searchDom.hasClass("isFocus")) {
                    $("#search-btn").trigger("click");
                }
            }
        });
    });*/

    $(document).ready(function () {
        $("#search-btn").click(function(){
            var s_option=$(".select_default").val();
            var i_input=$.trim($("#search-input").val());
            window.location.href='${ctxroot}/mrna/list?type='+s_option+"&keywords="+i_input+"&isIndex=1";
        });
        $("#search-input").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $("#search-input").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(document).keyup(function(event){
            var _searchDom = $("#search-input");
            var e=e||event
            var keycode = e.which;
            if(keycode==13){
                if(_searchDom.hasClass("isFocus")) {
                    $("#search-btn").trigger("click");
                }
            }
        });
    });


</script>

</body>
</html>