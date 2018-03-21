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
    <title>基因表达量信息</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <style>
        .ga-tip {
            padding: 0px 10px;
        }
        .hori-div {
            border-bottom: 1px solid #DDD;
            margin-top: 6px;
            margin-bottom: 9px;
        }
        .tip-item {
            line-height: 24px;
            font-size: 12px;
        }
        .tip-label {
            width: 81px;
            display: inline-block;
        }
        .tip-label2 {
            width: 133px;
            display: inline-block;
            position: relative;
            margin-left: 20px;
        }
        .tip-label2:before{
            content: '';
            display: inline-block;
            width: 12px;
            height: 12px;
            position: absolute;
            /*background: #6288e6;*/
            background: #07B34F;
            top: 7px;
            left: -20px;
        }

        .ga-heat-table {
            float: none;
            margin-left: 310px;
            padding: 20px 30px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
            width: 890px;
            height: auto;
        }

        .table-responsive {
            /*width: 900px;*/
            overflow: auto;
            /*min-height: 665px;*/
        }

        .heat-table {
            border-collapse: collapse;
            font-size: 12px;
            width: max-content;
        }
        .heat-table td, .heat-table th {
            font-weight: normal;
            box-sizing: border-box;
            width: 140px;
        }
        .heat-table th {
            padding: 20px 10px;
        }
        .heat-table td {
            height: 30px;
            line-height: 30px;
            border-top: 1px solid #dcdcdc;
            border-left: 1px solid #dcdcdc;
        }
        .heat-table tr td:last-child, .heat-table tr:last-child td  {
            border-bottom: 1px solid #dcdcdc;
            border-right: 1px solid #dcdcdc;
        }
        .heat-table tr td:first-child, .heat-table th:first-child {
            width: 135px;
            border-top: none;
            border-left: none;
            border-bottom: none;
            text-align: right;
            display: inline-block;
        }
        .heat-table tr td:first-child {
            padding-right: 5px;
            border-right: none;
        }

        .checkbox {
            display: inline-block;
            width: 14px;
            height: 14px;
            background-image: url("${ctxStatic}/images/unchecked.png");
            background-size: contain;
            background-repeat: no-repeat;
            cursor: pointer;
            position: relative;
            top: 3px;
            margin-right: 5px;
        }
        .checkbox.checked {
            background-image: url("${ctxStatic}/images/checked.png");
        }
        .checkbox > input[type='checkbox'] {
            display: none;
            position: absolute;
            left: -2px;
            top: -3px;
        }
        .legend {
            height: 40px;
            text-align: right;
            /*display: inline;*/
            /*float: right;*/
        }

        .legend span {
            display: inline-block;
            width: 40px;
            height: 40px;
            vertical-align: text-top;
            line-height: 40px;
            text-align: center;
        }

        .legend .legend-item:nth-child(2) {
            /*background: #dfe6f1;*/
            background: #DAF2E4;
        }
        .legend .legend-item:nth-child(3) {
            /*background: #C2D4F1;*/
            background: #C2F3D6;
        }
        .legend .legend-item:nth-child(4) {
            /*background: #89aae5;*/
            background: #89E6AF;
        }
        .legend .legend-item:nth-child(5) {
            /*background: #5c8de5;*/
            background: #15CC60;
        }
        .legend .legend-item:nth-child(6) {
            /*background: #386cca;*/
            background: #07B34F;
        }


        .ga-ctrl {
            padding-bottom: 20px;
            border-bottom: 1px solid #DDD;
        }
        .ga-ctrl .ga-select {
            border: 1px solid #ddd;
            width: 240px;
            height: 40px;
            line-height: 40px;
            color: #757575;
            padding: 0 20px;
        }
        .add-genes dl {
            overflow: auto;
        }

        .genes-conditional-search div {
            margin-right: 0;
            padding-left: 26px;
        }
        .change-select select {
            padding: 0 10px;
            margin-left: 20px;
        }
        .change-select input {
            padding: 0 10px;
            width: 300px;
        }

        .js-checkbox {
            cursor: pointer;
        }
        .ga-samples-table {
            width: 880px;
        }
        .ga-samples-table tr td {
            /*padding: 0;*/
            text-align: center;
            padding: 5px;
            box-sizing: border-box;
        }
        .ga-samples-table tr td:nth-child(1) {
            width: 55px;
            padding: 0;
            text-align: center;
        }
        .ga-samples-table tr td:nth-child(1) span {
            cursor: pointer;
        }
        .ga-samples-table tr td:nth-child(2),
        .ga-samples-table tr td:nth-child(7){
            width: 150px;
        }
        .ga-samples-table tr td:nth-child(3),
        .ga-samples-table tr td:nth-child(4),
        .ga-samples-table tr td:nth-child(5),
        .ga-samples-table tr td:nth-child(6) {
            width: 128px;
        }

        .ga-samples-table tr td span {
            display: inline-block;
        }
        .ga-gene-name {
            width: 110px;
            text-align: justify;
            display: inline-block;
            text-align-last: justify;
        }
        .tab-contrast-ac .contrast{
            display: none;
        }
        .zwsj{
            padding: 15px 0;
            font-size: 14px;
        }
        .btn-export-set button img{
            position: relative;
            top: -2px;
            padding-right: 2px;
        }
        body .genesInfo{
            left: 50%;
            margin-left: -515px;
            border: none;
        }
        body .genesInfo-head{
            position: relative;
            height: 40px;
            line-height: 40px;
            /*background: #386cca;*/
            background: #0F9145;

            cursor: move;
        }
        .genesInfo .genesInfo-head p{
            width: 100%;
            text-align:center;
            color: #fff;
        }
        .genesInfo .genesInfo-head a{
            float: right;
            position: absolute;
            right: 10px;
            color:#fff;
            font-size: 20px;
        }

        #js-expression-select input{
            padding: 0 10px;
            margin-left: 20px;
            margin-top: -6px;
            float:left;
            width: 40px;
            border: 1px solid #0F9145;
            height: 10px;
            margin-right: 0px;
            border-right: none;
            color: #999;
        }

        #selection{
            display: none;
            position: absolute;
            left:141px;
            top:30px;
            width:35px;
            height: 115px;
            border: 1px solid #0F9145;
            background-color: white;
        }
        /*.select{
            position:relative;
            padding: 0 10px;
            margin-left: 10px;
            cursor:pointer;
            width:44px;
            height: 10px;
            border: 1px solid #0F9145;
            height: 10px;
        }*/
        /*.select_default{

            position:absolute;
            top:-11px;
            left:-1px;
            width:40px;
            line-height:40px;
            padding:0 10px;
            border:solid 1px #0F9145;
        }*/
        #select{
            position: relative;
        }
        #select .select_default{
            width:59px;
            height:38px;
            position: relative;
            top: -10px;
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
            display: none;
            position:absolute;
            top: 31px;
            left: 26px;
            margin:0;
            padding:0;
            list-style:none;
        }
        .select_item li{
             width:80px;
             height:28px;
             line-height:28px;
             /*padding:0 -20px;*/
             border:solid 1px #0F9145;
             border-top:none;
             text-align: center;
             border-bottom:none;
         }
        .select_item li:hover{
            background:#0F9145;
            color:#fff;
            cursor: pointer;
        }

    </style>
</head>

<body>

<%@ include file="/WEB-INF/views/include/mrna-header.jsp"%>
<!--header-->
<section class="container mRMA-dif">
    <div class="contant">
        <div class="change-resulting box-shadow">
            <p class="js-study"></p>
            <label><span>SARStduy : </span><a href="#" target="_blank" class="js-sarstudy"></a></label>
        </div>
        <div class="resulting-item">
            <label class=""><span class="item-title">Instrument:</span><span class="item-dec js-instrument"></span></label>
            <label class=""><span class="item-title">Library strategy:</span><span class="item-dec js-library-strategy"></span></label>
            <%--<label class=""><span class="item-title">Duplicate:</span><span class="item-dec">3</span></label>--%>
            <label class=""><span class="item-title">Pedigree:</span><span class="item-dec js-pedigree"></span></label>
        </div>
        <div class="sample-contrast tab-contrast-ac">
            <div class="contrast">

                <div class="sample-tab">
                    <p>样本</p>
                    <table  class="tab-item js-samples ga-samples-table">
                        <thead>
                            <tr>
                                <td class="serial-number" ><span class="js-choose-all-tab"></span></td>
                                <td>样本编号/样本</td>
                                <td>组织</td>
                                <td>时期</td>
                                <td>处理</td>
                                <td>基因型</td>
                                <td>品种</td>
                            </tr>
                        </thead>
                        <tbody>
                            <%--<tr class="choose-ac">--%>
                                <%--<td  class="serial-number"><span></span></td>--%>
                                <%--<td>1</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                            <%--</tr>--%>
                            <%--<tr>--%>
                                <%--<td  class="serial-number"><span></span></td>--%>
                                <%--<td>2</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                                <%--<td>数据</td>--%>
                            <%--</tr>--%>

                        </tbody>
                    </table>
                    <p class="btn-group">
                        <button type="button" class="btn-confirm js-confirm-btn">确认</button>
                        <button type="button" class="btn-chooseAll js-default-btn">默认</button>
                        <button type="button" class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                    </p>
                </div>
            </div>
            <div class="export-data">
                <p class="btn-export-set"><!--<button type="button" class="btn-export js-export"><img src="${ctxStatic}/images/export.png">导出数据</button>--><button class="btn-contrast"><img src="${ctxStatic}/images/set.png">样本</button></p>

            </div>
        </div>
        <div class="genes-conditional-search">
            <div class="genes-search">
                <span>Genes:</span>
                <input type="text" class="js-search-gene-name" placeholder="请输入您要搜索的基因ID或名称功能">
                <button type="button" class="js-search-gene-button"><img src="${ctxStatic}/images/search.png">搜索</button>
            </div>
            <div class="change-select">
                <span style="float:left">Expression value</span>
                <!--
                <select class="js-expression-select">
                    <option value=">"> &gt;</option>
                    <option value="="> =</option>
                    <option value="<"> &lt;</option>
                </select>
                <input class="js-expression-value" type="text" value="">
                -->
                <div id="select">
                    <input type="text" class="select_default" value=">">
                    <ul class="select_item">
                        <li>&gt;</li>
                        <li>=</li>
                        <li style="border-bottom:1px solid #0F9145;">&lt;</li>
                    </ul>
                </div>
                <input class="js-expression-value" type="text" value="">
            </div>
        </div>
    </div>
    <aside style=" ">
        <div class="item-header">
            <!--<div class="icon-left"><img src="${ctxStatic}/images/genes-pic.png">添加基因</div>-->
            <div class="icon-left"><img src="${ctxStatic}/images/geneselect.png">添加基因</div>
        </div>
        <div class="add-genes">
            <p>可以手动添加基因</p>
            <dl class="js-added-genes-group">
                <%--<dd>
                    <a href="#">
                    <div class="gense-list">Glyma17G84900</div>
                    <div class="close-pic">
                        <img src="${ctxStatic}/images/genes-close.png">
                    </div>
                    </a>
                </dd>
                <dd class="genes_ac">
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>
                <dd>
                    <a href="#">
                        <div class="gense-list">Glyma17G84900</div>
                        <div class="close-pic">
                            <img src="${ctxStatic}/images/genes-close.png">
                        </div>
                    </a>
                </dd>--%>
            </dl>
            <button type="button" class="js-specify">组织特异性表达</button>
        </div>

    </aside>

    <div class="genes-highcharts ga-heat-table">
        <div class="ga-ctrl">
            <%--<select class="ga-select" name="" id="">--%>
                <%--<option value="">Up-or down regulated </option>--%>
            <%--</select>--%>
            <div class="legend">
                <span>0</span>
                <span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span>
                <span id="max"></span>
            </div>
        </div>

        <div id="mask-test">
            <div class="table-responsive" >

                <table class="heat-table js-heat-table">
                    <thead>
                    <%--<tr>--%>
                        <%--<th></th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                        <%--<th>oihgoerhgoieh v ierjhfwehg</th>--%>
                    <%--</tr>--%>
                    </thead>
                    <tbody>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035--%>
                        <%--</td>--%>
                        <%--<td><span class="js-gene-value">2.2</span></td>--%>
                        <%--<td><span class="js-gene-value">0</span></td>--%>
                        <%--<td><span class="js-gene-value">-4.8</span></td>--%>
                        <%--<td><span class="js-gene-value">1</span></td>--%>
                        <%--<td><span class="js-gene-value">3</span></td>--%>
                        <%--<td><span class="js-gene-value">-2</span></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035--%>
                        <%--</td>--%>
                        <%--<td><span class="js-gene-value">3.1</span></td>--%>
                        <%--<td><span class="js-gene-value">3.6</span></td>--%>
                        <%--<td><span class="js-gene-value">1.1</span></td>--%>
                        <%--<td><span class="js-gene-value">-1</span></td>--%>
                        <%--<td><span class="js-gene-value">0</span></td>--%>
                        <%--<td><span class="js-gene-value">0.5</span></td>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035--%>
                        <%--</td>--%>
                        <%--<td><span class="js-gene-value">4.8</span></td>--%>
                        <%--<td><span class="js-gene-value">3</span></td>--%>
                        <%--<td><span class="js-gene-value">2.1</span></td>--%>
                        <%--<td><span class="js-gene-value">3.3</span></td>--%>
                        <%--<td><span class="js-gene-value">-3.3</span></td>--%>
                        <%--<td><span class="js-gene-value">-1.3</span></td>--%>
                        <%--<!-- <td></td> -->--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518036--%>
                        <%--</td>--%>
                        <%--<td><span class="js-gene-value">-4.8</span></td>--%>
                        <%--<td><span class="js-gene-value">-3</span></td>--%>
                        <%--<td><span class="js-gene-value">-2.1</span></td>--%>
                        <%--<td><span class="js-gene-value">3.3</span></td>--%>
                        <%--<td><span class="js-gene-value">-0.3</span></td>--%>
                        <%--<td><span class="js-gene-value">1.3</span></td>--%>
                        <%--<!-- <td></td> -->--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td>--%>
                            <%--<span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035--%>
                        <%--</td>--%>
                        <%--<td><span class="js-gene-value">-3.8</span></td>--%>
                        <%--<td><span class="js-gene-value">2.5</span></td>--%>
                        <%--<td><span class="js-gene-value">2.1</span></td>--%>
                        <%--<td><span class="js-gene-value">3.3</span></td>--%>
                        <%--<td><span class="js-gene-value">-3</span></td>--%>
                        <%--<td><span class="js-gene-value">-2.3</span></td>--%>
                        <%--<!-- <td></td> -->--%>
                    <%--</tr>--%>
                    </tbody>
                </table>
            </div>
        </div>
        <form action="${ctxroot}/specific/index" method="POST" id="specificForm" style="display: none;">
            <input type="text" name="genes">
        </form>

        <%@ include file="/WEB-INF/views/include/pagination.jsp" %>

    </div>

    <div class="genesInfo" style="display: none;">
        <div class="genesInfo-head">
            <p>基因<span class="js-gene-head-name"></span>信息</p>
            <a href="javascript:void(0);">X</a>
        </div>
        <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
    </div>

</section>
<!--section-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
<script src="${ctxStatic}/js/jquery-ui.js"></script>
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
            //alert($sel_default.val());
            $sel_item.hide();
        });
    });


    function loadMask (el) {
        $(el).css({"position": "relative"});
        var _mask = $('<div class="ga-mask"><div>数据加载中...</div></div>');
        $(el).append(_mask);
    }

    function maskClose() {
        $(".ga-mask").remove();
    }

    var index;
    $("body").on("click", ".js-gene-info", function(e) {
        var version = 'gmx_ensembl_release23';
        var geneName = $(this).text();
        $(".js-gene-head-name").html(geneName);
        $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName="+ geneName + "&version=" + version);
        e.preventDefault();
        //修改弹窗及拖拽样式
        /*$(".genesInfo").show();*/
        index = layer.open({
            title: "",
            type: 1,
            content: $(".genesInfo"),
            area: ['980px', '640px'],
            shadeClose: true,
            scrollbar: false,
            move: '.genesInfo-head',
            closeBtn: 0,
            //offset: ['135px', '320px']
        });

    });
    $(".genesInfo-head > a").click(function() {
        /*$(".genesInfo").hide();*/
        layer.close(index);
    });

    $(function() {

        var basic_promise, table_promise;

        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
            var r = window.location.search.substr(1).match(reg);
            if (r!=null) return (r[2]); return null;
        }

        var studyId = getUrlParam('study');
        var headerGroup = [];
        function initBasicInfo() {
            var params = {
                "id": studyId,
                "type": 1
            };
            return $.getJSON("${ctxroot}/diffgene/studyBasicInfo", params, function(res){
                if(res) {
                    $('.js-study').html(res.study);
                    $('.js-sarstudy').html(res.sraStudy);
                    $(".js-sarstudy").attr('href', (res.links || '#') );
                    $(".js-instrument").html(res.instrument);
                    $(".js-library-strategy").html(res.libraryStrategy);
                    $(".js-pedigree").html(res.pedigree);

                    var samples = res.samples;
                    var len = samples.length;
                    var str = '';
                    var headStr = ''; // 表格头部
                    headStr += '<tr><th></th>';

                    for(var i = 0; i < len; i++) {
                        str += '<tr class="">'
                        str += '<td  class="serial-number"><span data-sample="'+ samples[i].sampleno +'" class="js-checkbox"></span></td>'
                        str += '<td>'+ samples[i].samplename +'</td>'
                        str += '<td>'+ samples[i].tissue +'</td>'
                        str += '<td>' + samples[i].stage + '</td>'
                        str += '<td>'+ samples[i].treat +'</td>'
                        str += '<td>'+ samples[i].genetype +'</td>'
                        str += '<td>'+ samples[i].cultivar +'</td>'
                        str += '</tr>'

                        headStr += '<th class="col_'+ samples[i].sampleno +'">'+ samples[i].samplename +'</th>';
                        headerGroup.push(samples[i].sampleno);
                    }
                    headStr += '</tr>';
                    $(".js-heat-table > thead").empty().append(headStr);

                    $(".js-samples > tbody").empty().append(str);

                }
            });
        }

        function getParams() {
            var geneName = $.trim($(".js-search-gene-name").val());
            /*var expressionValue = $(".js-expression-select").val() + "," + $(".js-expression-value").val();*/
            var expressionValue = $(".select_default").val() + "," + $(".js-expression-value").val();
            return {
                "id": studyId,
                "gene": geneName,
                "expressionValue": expressionValue
            }
        }

        function renderTable(data) {

            // 表格body
            var bodyStr = '';
            var len = data.length;
            for(var i = 0; i < len; i++) {
                bodyStr += '<tr>'
                if (addedGenesGroup.has(data[i].geneName)) {
                    bodyStr += '    <td><span class="checkbox js-checkbox cls_gene_' + data[i].geneName + ' checked" data-gene="' + data[i].geneName + '"><input type="checkbox" checked /></span><span class="ga-gene-name js-gene-info">' + data[i].geneName + '</span></td>'
                }else {
                    bodyStr += '    <td><span class="checkbox js-checkbox cls_gene_'+ data[i].geneName +'" data-gene="'+ data[i].geneName +'"><input type="checkbox" /></span><span class="ga-gene-name js-gene-info">'+ data[i].geneName +'</span></td>'
                }
                var glen = data[i].samples.length;
                for(var idx in headerGroup) {
                    var notFound = 0;
                    for(var gv = 0; gv < glen; gv++) {
                        if(headerGroup[idx] ==  data[i].samples[gv].sampleNo) {
                            var geneItem = data[i].samples[gv];
                            bodyStr += '    <td data-gene="'+ data[i].geneName +'" data-cultivar="'+ geneItem.cultivar +'" data-phenotype="'+ geneItem.phenotype +'" data-tissue="'+ geneItem.tissue +'" data-stage="'+ geneItem.stage +'" class="heat-hover col_'+ geneItem.sampleNo +'"><span class="js-gene-value">'+ data[i].samples[gv].value +'</span></td>';
                            break;
                        }
                        notFound++;
                    }
                    if(notFound == glen) {
                            bodyStr += '    <td class="col_'+ headerGroup[idx] +'"><span class="js-gene-value">-0</span></td>';
                    }
                }

                bodyStr += '</tr>'
            }
            $(".js-heat-table > tbody").empty().append(bodyStr);

            $(".js-samples > tbody").find(".js-checkbox").each(function(idx, el) {
                if(!$(el).parents("tr").hasClass("choose-ac")) {
                    var cls = ".col_" + $(el).attr("data-sample");
                    $(".js-heat-table").find(cls).hide();
                }
            });

        }

        var page = {curr: 1, pageSize:20};
        $(".lay-per-page-count-select").val(page.pageSize);

        function initTable(curr,pageSizeNum) {
            console.log(page.pageSize)
            var max;
            var params = getParams();
            params['pageNo'] = curr;
            params['pageSize'] = pageSizeNum;
//           loadMask ("#mask-test");
            return $.ajax({
                url: "${ctxroot}/diffgene/studyExpression",
                type: "get",
                dataType: "json",
                data: params,
                success: function(res) {
//                    maskClose();
                    if(res.data.length > 0) {
                        $(".js-heat-table > thead,.ga-ctrl-footer").show();
                        //显示表格内容
                        renderTable(res.data);

                        max = Math.abs(res.max);
                        $("#max").html('100');

                        // 根据td里的基因值渲染颜色
                        $(".heat-table").find(".js-gene-value").each(function(idx, el) {
                            var geneValue = $(el).text() * 1;
                            $(el).hide();
                            /*var lightColor = "56, 108, 202" // #386cca;*/
                            var lightColor = "7,179,79" //#07B34F;
                            var alpha = 0;
                            geneValue = Math.abs(geneValue);
                            alpha = (geneValue / 100).toFixed(2);
                            $(el).parent("td").css({"background": "rgba(" + lightColor + "," + alpha + ")"});
                        });

                    } else {
//                        $(".js-heat-table > thead").empty();
//                        $(".js-heat-table > tbody").empty();
//                        alert("无数据");
                        $(".js-heat-table > thead,.ga-ctrl-footer").hide();
                        $(".js-heat-table > tbody").empty();
                        $('.js-heat-table').append("<p class='zwsj'>暂无数据</p>")
                    }
                    $("#total-page-count > span").html(res.total);
                    //显示分页
                    laypage({
                        cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.total / page.pageSize), //通过后台拿到的总页数
                        curr: curr|| 1, //当前页
                        /*skin: '#5c8de5',*/
                        skin : '#0F9145',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(res.total / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
//                                initTable(obj.curr);
                                var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                                page.curr = obj.curr;
                                var currNum=obj.curr;
                                initTable(currNum,pageSizeNum);
                            }
                        }
                    });
                }

            })
        }

        // 修改每页显示条数
//        $(".ga-heat-table").on("change", ".lay-per-page-count-select", function() {
//            pageSize = $(this).val();
//            initTable(1);
//        });
        // 修改每页显示条数
        $(".ga-heat-table").on("change", ".lay-per-page-count-select", function() {
//            pageSize = $(this).val();
//            initTable(1);
            var curr = Number($(".ga-heat-table .laypage_curr").text());
            var pageSize = Number($(this).val());
            var total= Number($(".ga-heat-table #total-page-count span").text());
            var mathCeil=  Math.ceil(total/curr);
            page.pageSize = $(this).val();
            if(pageSize>mathCeil){
                page.curr = 1;
                initTable(1,pageSize);
            }else{
                initTable(curr,pageSize);
            }
        });

        // 搜索
        $(".js-search-gene-button").click(function() {
            page.curr=1;
            page.pageSize=20;
            $(".lay-per-page-count-select option:nth-child(2)").prop("selected", 'selected');
            initTable(1,20);
            $('.zwsj').remove();
        });

        $(".ga-heat-table").on("focus", ".laypage_skip", function() {
            $(this).addClass("isFocus");
        });
        $(".ga-heat-table").on("blur", ".laypage_skip", function() {
            $(this).removeClass("isFocus");
        });
        $(".js-search-gene-name").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $(".js-search-gene-name").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(".js-expression-value").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $(".js-expression-value").on("blur", function() {
            $(this).removeClass("isFocus");
        });

        // 注册 enter 事件
        document.onkeydown = function(e) {
            var _page_skip = $('.laypage_skip');
            var _search_gene_dom = $('.js-search-gene-name');
            var _expression_dom = $('.js-expression-value');
            if(e && e.keyCode==13){ // enter 键
                var currNum = Number(_page_skip.val());
                var pageSizeNum = Number($('.ga-ctrl-footer #per-page-count .lay-per-page-count-select').val());
                var total= Number($(".ga-ctrl-footer #total-page-count span").text());
                var mathCeil=  Math.ceil(total/pageSizeNum);
                if( _page_skip.hasClass("isFocus") ) {
                    if(currNum>mathCeil){
                        page.curr = 1;
                        initTable(1,pageSizeNum);
                    }else{
                        page.curr = currNum;
                        initTable(currNum,pageSizeNum);
                    }
                } else if (_search_gene_dom.hasClass("isFocus")) {
                    $('.zwsj').remove();
                    page.pageSize=20;
                    $(".lay-per-page-count-select option:nth-child(2)").prop("selected", 'selected');
                    if(currNum>mathCeil){
                        page.curr = 1;
                        initTable(1,pageSizeNum);
                    }else{
                        page.curr = currNum;
                        initTable(currNum,pageSizeNum);
                    }
                } else if(_expression_dom.hasClass("isFocus")) {
                    $('.zwsj').remove();
                    page.pageSize=20;
                    $(".lay-per-page-count-select option:nth-child(2)").prop("selected", 'selected');
                    if(currNum>mathCeil){
                        page.curr = 1;
                        initTable(1,pageSizeNum);
                    }else{
                        page.curr = currNum;
                        initTable(currNum,pageSizeNum);
                    }
                }
            }
        }


        // 收起面板
        $(".btn-toggle").unbind("click").bind("click",function(e){
//            $(".contrast-checkbox").hide();
            var _top=$(".sample-contrast").height();
            $(".sample-contrast").addClass("tab-contrast-ac")
            console.log(_top)
        })
        $(".btn-contrast").click(function(){
            $(".sample-contrast").removeClass("tab-contrast-ac")
        })


        var addedGenesGroup = new Set(); // 手动添加的基因

        // 表格里的 checkbox 效果
        $(".heat-table").on("click", ".js-checkbox", function() {
            var checked = $(this).children("input[type='checkbox']").prop("checked");
            var geneName = $(this).attr("data-gene");
            if(!checked) {
                $(this).children("input[type='checkbox']").prop("checked", true);
                $(this).addClass("checked");
                addedGenesGroup.add(geneName);
//                addedGenesGroup.push(geneName);
            } else {
                $(this).children("input[type='checkbox']").prop("checked", false);
                $(this).removeClass("checked");
                addedGenesGroup.delete(geneName);
//                _.pull(addedGenesGroup, geneName);
            }
            renderGenesGroup();
        });

        // 删除手动添加基因
        $(".js-added-genes-group").on("click", ".close-pic", function() {
            var geneName = $(this).parent("a").find(".gense-list").text();
            var cls = ".cls_gene_" + geneName;
            $(cls).removeClass("checked").children("input[type='checkbox']").prop("checked", false);
            _.pull(addedGenesGroup, geneName);
            addedGenesGroup.delete(geneName);
            renderGenesGroup();
        });

        function renderGenesGroup() {
//            var len = addedGenesGroup.length;
            var str = '';
            <%--for(var i = 0; i < len; i++) {--%>
                <%--str += '<dd>'--%>
                <%--str += '    <a href="javascript:void(0);">'--%>
                <%--str += '        <div class="gense-list">'+ addedGenesGroup[i] +'</div>'--%>
                <%--str += '        <div class="close-pic">'--%>
                <%--str += '            <img src="${ctxStatic}/images/genes-close.png">'--%>
                <%--str += '        </div>'--%>
                <%--str += '    </a>'--%>
                <%--str += '</dd>'--%>
            <%--}--%>
            addedGenesGroup.forEach(function(item){
                str += '<dd>'
                str += '    <a href="javascript:void(0);">'
                str += '        <div class="gense-list">'+ item +'</div>'
                str += '        <div class="close-pic">'
                str += '            <img src="${ctxStatic}/images/genes-close.png">'
                str += '        </div>'
                str += '    </a>'
                str += '</dd>'
            });
            $(".js-added-genes-group").empty().append(str);
        }

        // 组织特异表达
        $(".js-specify").click(function(){
            var temp = [];
            addedGenesGroup.forEach(function(value, idx, ower) {
                temp.push(value);
            });
            if(temp.length != 0) {
                $("#specificForm").find("input").val(temp.join(","));
                $("#specificForm").submit();
            }

        });

        var initPage = function () {
            basic_promise = initBasicInfo();

            basic_promise.then(function() {
                table_promise = initTable(1,20);

                // 样本 多选
                $(".js-samples").on("click", ".js-checkbox", function() {
                    var checked = $(this).parents("tr").hasClass("choose-ac");
                    if(!checked) {
                        $(this).parents("tr").addClass("choose-ac");
                    } else {
                        $(this).parents("tr").removeClass("choose-ac");
                    }
                    var samples = $(".js-samples > tbody").find(".js-checkbox");
                    var checkedSamples = $(".js-samples > tbody").find(".choose-ac");
                    if(samples.length == checkedSamples.length) {
                        $(".js-choose-all-tab").parents("tr").addClass("choose-ac");
                    } else {
                        $(".js-choose-all-tab").parents("tr").removeClass("choose-ac");
                    }

                });

                // 全选
                $(".js-choose-all-tab").click(function() {
                    var samples = $(".js-samples").find(".js-checkbox");
                    var allChecked = $(this).parents("tr").hasClass("choose-ac");
                    if(allChecked) {
                        $(this).parents("tr").removeClass("choose-ac");
                    } else {
                        $(this).parents("tr").addClass("choose-ac");
                    }
                    $.each($(".js-samples").find(".js-checkbox"), function(idx, el){
                        var checked = $(el).parents("tr").hasClass("choose-ac");
                        if(!allChecked) {
                            if(!checked) {
                                $(el).trigger("click");
                            }
                        } else {
                            if(checked) {
                                $(el).trigger("click");
                            }
                        }
                    });
                });

                $(".js-choose-all-tab").trigger("click");


                table_promise.then(function() {
                    // 面板 默认
                    $(".js-default-btn").click(function() {
                        $(".js-choose-all-tab").trigger("click");
                    });

                    // 面板 确认
                    $(".js-confirm-btn").click(function(){
                        var samples = $(".js-samples").find(".js-checkbox");
                        $.each(samples, function(idx, el) {
                            var sample = $(el).attr("data-sample");
                            var cls = ".col_" + sample;
                            var checked = $(el).parents("tr").hasClass("choose-ac");
                            if(!checked) {
                                $(".js-heat-table").find(cls).hide();
                            } else {
                                $(".js-heat-table").find(cls).show();
                            }
                        });
                    });

                    $(".js-heat-table").on("mouseover", ".heat-hover", function() {
                        var self = this;
                        var geneName = $(this).attr("data-gene");
                        var cultivar = $(this).attr("data-cultivar");
                        var phenotype = $(this).attr("data-phenotype");
                        var tissue = $(this).attr("data-tissue");
                        var stage = $(this).attr("data-stage");
                        var value = $(this).text();
                        if(value.indexOf("e") > -1) {
                            var slices = value.split("e");
                            var before = (slices[0] * 1).toFixed(4);
                            var after = slices[1];
                            value = before + "e" + after + "";
                        } else {
                            value = (value * 1).toFixed(4);
                        }
                        var str = '';
                        str += '<div class="ga-tip">'
                        str += '<div class="tip-item"><span class="tip-label">Gene name: </span><span>'+ geneName +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Cultivar: </span><span>'+ cultivar +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Phenotype: </span><span>'+ phenotype +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Tissue: </span><span>'+ tissue +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Stage: </span><span>'+ stage +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">FPKM: </span><span>'+ value +'</span></div>'
                        str += '</div>'
                        $.pt({
                            target: self,
                            position: "b",
                            width: 410,
                            height: 150,
                            content: str
                        });
                    });

                    $(".js-heat-table").on("mouseout", ".heat-hover", function() {
                        $(".pt").hide();
                    });

                });
            });



            $(".js-export").click(function() {
                var samples = [];
                $.each($(".js-samples > tbody").find(".choose-ac"), function(idx, el) {
                    samples.push($(el).find(".js-checkbox").attr("data-sample"));
                });
                var params = getParams();
                <%--var url = "${ctxroot}/diffgene/studyExpressionDataExport?id=" + params.id + "&gene=" + params.gene + "&expressionValue=" + params.expressionValue + "&choices=" + samples.join(",");--%>
                var url= "${downloadUrl}"+"expression/"+$('.js-sarstudy').html()+".zip";
//                alert(url);
                window.open(url,"_self");
            });

        }();

    })

    /*基因详情拖动弹框*/
    /*$(".genesInfo").draggable({ containment: "body" });*/
</script>
</body>
</html>