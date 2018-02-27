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
    <title>差异基因信息</title>
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
            background: #6288e6;
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
        }

        .heat-table {
            border-collapse: collapse;
            font-size: 12px;
            width: max-content;
        }
        .heat-table td, .heat-table th {
            font-weight: normal;
            box-sizing: border-box;
            width: 160px;
            word-break: break-all;
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
            width: 155px;
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
            display: inline;
            float: right;
        }

        .legend span {
            display: inline-block;
            width: 40px;
            height: 40px;
            vertical-align: text-top;
            line-height: 40px;
            text-align: center;
        }

        .legend .legend-item:nth-child(8) {
            background: #f1e7d9;
        }
        .legend .legend-item:nth-child(9) {
            background: #edd1b4;
        }
        .legend .legend-item:nth-child(10) {
            background: #ecbf90;
        }
        .legend .legend-item:nth-child(11) {
            background: #edac67;
        }
        .legend .legend-item:nth-child(12) {
            background: #ed902e;
        }

        .legend .legend-item:nth-child(6) {
            background: #dfe6f1;
        }
        .legend .legend-item:nth-child(5) {
            background: #C2D4F1;
        }
        .legend .legend-item:nth-child(4) {
            background: #89aae5;
        }
        .legend .legend-item:nth-child(3) {
            background: #5c8de5;
        }
        .legend .legend-item:nth-child(2) {
            background: #386cca;
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
        .ga-ctrl-footer {
            text-align: right;
        }
        #pagination {
            display: inline-block;
        }
        #per-page-count {
            display: inline-block;
            vertical-align: bottom;
        }
        #pagination .laypage_main * {
            font-size: 14px;
        }
        .lay-per-page-count-select {
            height: 33px;
            padding: 0 5px;
            box-sizing: border-box;
            border: 1px solid #DDD;
            margin: 0 5px;
        }
        #pagination .laypageskin_molv a, #pagination .laypageskin_molv span {
            border-radius: 0px;
        }
        #pagination .laypage_main a, #pagination .laypage_main span {
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
        #pagination .laypage_main a.laypage_next {
            border-right: 1px solid #ddd;
        }
        #pagination .laypage_main .laypage_total {
            width: 125px;
            border: none;
            margin: 0 20px;
        }
        #pagination .laypageskin_molv .laypage_curr {
            border-top: 1px solid #5c8de5;
            border-bottom: 1px solid #5c8de5;
        }
        #pagination .laypage_main input {
            height: 33px;
            line-height: 33px;
        }
        #pagination .laypage_main button {
            display: none;
        }
        .contrast-checkbox {
            display: inline-block;
            width: 880px;
        }
        .checkbox-list .js-compare-list {
            display: block;
            line-height: 30px;
        }
        .js-compare-list > label {
            cursor: pointer;
            display: inline-block;
        }
        .ga-gene-name {
            width: 120px;
            text-align: justify;
            display: inline-block;
            text-align-last: justify;
        }
        .change-select select {
            padding: 0 10px;
        }
        .change-select input, .search-input input {
            height: 36px;
        }
        .change-select input {
            padding: 0 10px;
            width: 35px;
            top: -15px;
            position: relative;
            border: 0;
            text-align: center;
        }
        .search-input input{
            padding: 0 10px;
            color: #444;
            width: 35px;
            top: -15px;
            position: relative;
            border: 0;
        }
        .add-genes button {
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
        .checkbox-list label.contrast-all{
            cursor: pointer;
            display: inline-block;
            width: 200px;
        }
        .change-select {
            width: 280px;
        }
        .change-select > span {
            vertical-align: top;
        }
        .search-input {
            width: 275px;
        }
        .search-input > span {
            vertical-align: top;
        }
        .ga-num-component {
            position: relative;
            float: none!important;
            display: inline-block;
            top: -10px;
            border: 1px solid #C2D4F1;
            box-sizing: border-box;
        }
        .ga-num-add {
            background: url("${ctxStatic}/images/add.png");
            -webkit-background-size: contain;
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
            width: 38px;
            height: 38px;
            /*margin-top: 2px;*/
            cursor: pointer;
        }
        .ga-num-minus {
            background: url("${ctxStatic}/images/minus.png");
            -webkit-background-size: contain;
            background-size: contain;
            background-repeat: no-repeat;
            display: inline-block;
            width: 38px;
            height: 38px;
            /*margin-top: 2px;*/
            cursor: pointer;
        }
	.tab-contrast-ac .contrast{
            display: none;
        }
        .zwsj{
            padding: 20px 0;
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
            <label class=""><span class="item-title">Duplicate:</span><span class="item-dec js-duplicate"></span></label>
            <label class=""><span class="item-title">Pedigree:</span><span class="item-dec js-pedigree"></span></label>
        </div>
        <div class="sample-contrast tab-contrast-ac">
            <div class="contrast">
                <div class="contrast-checkbox">
                    <p>比较组</p>
                    <div class="checkbox-list">
                        <span class="js-compare-list">
                            <%--<label for="1v2"><span id="1v2"></span>1v2</label>--%>
                            <%--<label for="1v3"><span id="1v3" type="checkbox" value="1v3" ></span>1v3</label>--%>
                            <%--<label for="1v4"><span id="1v4" type="checkbox" value="1v4" ></span>1v4</label>--%>
                            <%--<label for="2v3" class="contrast-ac"><span id="2v3" type="checkbox" value="2v3" ></span>2v3</label>--%>
                            <%--<label for="2v4" class="contrast-ac"><span id="2v4" type="checkbox" value="2v4" ></span>2v4</label>--%>
                        </span>

                        <label for="checkbox-all" class="contrast-all js-compare-all"><span id="checkbox-all" type="checkbox" value="checkbox-all" ></span>全选</label>

                  </div>
                </div>
                <div class="sample-tab">
                    <p>样本</p>
                    <table class="tab-item js-samples ga-samples-table">
                        <thead>
                            <tr class="choose-ac">
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
                        <button type="button" class="btn-fill btn-confirm js-confirm-btn">确认</button>
                        <button type="button" class="btn-chooseAll js-default-btn">默认</button>
                        <button type="button" class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                    </p>
                </div>
            </div>
            <div class="export-data">
                <p class="btn-export-set">
                    <button class="btn btn-export js-export"><img src="${ctxStatic}/images/export.png">导出数据</button>
                    <button class="btn btn-contrast"><img src="${ctxStatic}/images/set.png">比较组/样本</button>
                </p>
            </div>

        </div>

        <div class="genes-conditional-search">
            <div class="genes-search">
                <span>Genes:</span>
                <input type="text" class="js-search-gene-name" placeholder="请输入您要搜索的基因ID或名称功能">
                <button type="button" class="btn js-search-gene-button"><img src="${ctxStatic}/images/search.png">搜索</button>
            </div>
            <%--<div class="change-select">--%>
                <%--<span>Log2-flod change</span>--%>
                <%--<select class="js-log-fold-change-select">--%>
                    <%--<option value=">"> &gt;</option>--%>
                    <%--<option value="="> =</option>--%>
                    <%--<option value="<"> &lt;</option>--%>
                <%--</select>--%>
                <%--<input type="text" class="js-log-fold-change-val" value="1">--%>
            <%--</div>--%>
            <%--<div class="search-input">--%>
                <%--<span>Adjusted P-value</span>--%>
                <%--<input type="text" class="js-adjusted-pvalue" placeholder="请输入值" value="0.05">--%>
            <%--</div>--%>
            <div class="change-select" style="position:relative;">
                <span>Log<small style="top: 8px;position: absolute;">2</small>&nbsp;-fold change</span>
                <div class="ga-num-component">
                    <span class="ga-num-minus"></span>
                    <input type="text" class="ga-num-text js-log-fold-change-val" value="1">
                    <span class="ga-num-add"></span>
                </div>

            </div>
            <div class="search-input">
                <span>Adjusted P-value</span>
                <div class="ga-num-component">
                    <span class="ga-num-minus"></span>
                    <input type="text" class="js-adjusted-pvalue" placeholder="请输入值" value="0.05">
                    <span class="ga-num-add"></span>
                </div>

            </div>
        </div>

    </div>
    <aside style=" ">
        <div class="item-header">
            <div class="icon-left"><img src="${ctxStatic}/images/genes-pic.png">添加基因</div>
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
            <button type="button" class="btn js-specify">组织特异性表达</button>
        </div>

    </aside>

    <div class="genes-highcharts ga-heat-table">
        <div class="ga-ctrl">
            <select class="ga-select js-regulated" name="" id="">
                <option value="up_or_down">Up or down regulated </option>
                <option value="up">Up regulated </option>
                <option value="down">Down regulated </option>
            </select>
            <div class="legend">
                <span id="min">-4.9</span>
                <span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span>
                <span>0</span>
                <span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span><span class="legend-item"></span>
                <span id="max">4.9</span>
            </div>
        </div>
        <div id="mask-test">
            <div class="table-responsive">

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
                    <%--<tr>
                        <td>
                            <span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035
                        </td>
                        <td><span class="js-gene-value">2.2</span></td>
                        <td><span class="js-gene-value">0</span></td>
                        <td><span class="js-gene-value">-4.8</span></td>
                        <td><span class="js-gene-value">1</span></td>
                        <td><span class="js-gene-value">3</span></td>
                        <td><span class="js-gene-value">-2</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035
                        </td>
                        <td><span class="js-gene-value">3.1</span></td>
                        <td><span class="js-gene-value">3.6</span></td>
                        <td><span class="js-gene-value">1.1</span></td>
                        <td><span class="js-gene-value">-1</span></td>
                        <td><span class="js-gene-value">0</span></td>
                        <td><span class="js-gene-value">0.5</span></td>
                    </tr>
                    <tr>
                        <td>
                            <span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035
                        </td>
                        <td><span class="js-gene-value">4.8</span></td>
                        <td><span class="js-gene-value">3</span></td>
                        <td><span class="js-gene-value">2.1</span></td>
                        <td><span class="js-gene-value">3.3</span></td>
                        <td><span class="js-gene-value">-3.3</span></td>
                        <td><span class="js-gene-value">-1.3</span></td>
                        <!-- <td></td> -->
                    </tr>
                    <tr>
                        <td>
                            <span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518036
                        </td>
                        <td><span class="js-gene-value">-4.8</span></td>
                        <td><span class="js-gene-value">-3</span></td>
                        <td><span class="js-gene-value">-2.1</span></td>
                        <td><span class="js-gene-value">3.3</span></td>
                        <td><span class="js-gene-value">-0.3</span></td>
                        <td><span class="js-gene-value">1.3</span></td>
                        <!-- <td></td> -->
                    </tr>
                    <tr>
                        <td>
                            <span class="checkbox js-checkbox"><input type="checkbox" /></span>CMYLAC0518035
                        </td>
                        <td><span class="js-gene-value">-3.8</span></td>
                        <td><span class="js-gene-value">2.5</span></td>
                        <td><span class="js-gene-value">2.1</span></td>
                        <td><span class="js-gene-value">3.3</span></td>
                        <td><span class="js-gene-value">-3</span></td>
                        <td><span class="js-gene-value">-2.3</span></td>
                        <!-- <td></td> -->
                    </tr>--%>
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
<script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
<script>

    function loadMask (el) {
        $(el).css({"position": "relative"});
        var _mask = $('<div class="ga-mask"><div>数据加载中...</div></div>');
        $(el).append(_mask);
    }

    function maskClose() {
        $(".ga-mask").remove();
    }

    $("body").on("click", ".js-gene-info", function(e) {
        var version = 'gmx_ensembl_release23';
        var geneName = $(this).text();
        $(".js-gene-head-name").html(geneName);
        $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName="+ geneName + "&version=" + version);
        e.preventDefault();
        $(".genesInfo").show();

    });
    $(".genesInfo-head > a").click(function() {
        $(".genesInfo").hide();
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
                "type": 0
            };
            return $.getJSON("${ctxroot}/diffgene/studyBasicInfo", params, function(res){
                if(res) {
                    $('.js-study').html(res.study);
                    $('.js-sarstudy').html(res.sraStudy);
                    $(".js-sarstudy").attr('href', (res.links || '#') );
                    $(".js-instrument").html(res.instrument);
                    $(".js-library-strategy").html(res.libraryStrategy);
                    $(".js-duplicate").html();
                    $(".js-pedigree").html(res.pedigree);

                    var samples = res.samples;
                    var len = samples.length;
                    var str = '';
                    for(var i = 0; i < len; i++) {
                        str += '<tr class="cls_sm_'+ samples[i].sampleno +'">'
                        str += '<td  class="serial-number"><span class="js-sample-item" data-sample="'+ samples[i].sampleno +'"></span></td>'
                        str += '<td>'+ samples[i].samplename +'</td>'
                        str += '<td>'+ samples[i].tissue +'</td>'
                        str += '<td>' + samples[i].stage + '</td>'
                        var l = samples[i].treat.length;
                        str += '<td><p title="'+samples[i].treat.substr(1,l-2)+'">'+ samples[i].treat.substr(0, 10) +'...</td>'
                        str += '<td>'+ samples[i].genetype +'</td>'
                        str += '<td>'+ samples[i].cultivar +'</td>'
                        str += '</tr>'
                    }
                    $(".js-samples > tbody").empty().append(str);

                    var compares = res.Compares;
                    var len2 = compares.length;
                    var str2 = '';
                    var headStr = ''; // 表格头部
                    headStr += '<tr><th></th>';
                    for(var c = 0; c < len2; c++) {
                        var compare = compares[c];
                        str2 += '<label class="js-compare-item" for="'+ compare.runno +'"><span id="'+ compare.runno +'" type="checkbox" value="'+ compare.runno +'" ></span>' + compare.runname + '</label>'

                        headStr += '<th class="col_'+ compare.runno +'">'+ compare.runname +'</th>';
                        headerGroup.push(compare.runno);
                    }
                    $(".js-compare-list").empty().append(str2);

                    headStr += '</tr>';
                    $(".js-heat-table > thead").empty().append(headStr);


                }
            });
        }

        function getParams() {
            var geneName = $.trim($(".js-search-gene-name").val());
            var logFoldChange = $(".js-log-fold-change-val").val();
            var adjustedPValue = $(".js-adjusted-pvalue").val();
            var sort = $(".js-regulated").val();
            return {
                "id": studyId,
                "gene": geneName,
                "logFoldChange": logFoldChange,
                "adjustedPValue": adjustedPValue,
                "sort": sort
            }
        }

        function renderTable(data) {

            // 表格body
            var bodyStr = '';
            var len = data.length;
            for(var i = 0; i < len; i++) {
                bodyStr += '<tr>'
                if (addedGenesGroup.has(data[i].geneName)) {
                    bodyStr += '    <td><span class="checkbox js-checkbox cls_gene_'+ data[i].geneName +' checked" data-gene="'+ data[i].geneName +'"><input type="checkbox" checked/></span><span class="ga-gene-name js-gene-info">'+ data[i].geneName +'</span></td>'
                } else {
                    bodyStr += '    <td><span class="checkbox js-checkbox cls_gene_'+ data[i].geneName +'" data-gene="'+ data[i].geneName +'"><input type="checkbox" /></span><span class="ga-gene-name js-gene-info">'+ data[i].geneName +'</span></td>'
                }

                var glen = data[i].compareGroups.length;
                for(var idx in headerGroup) {
                    var notFound = 0;
                    for(var gv = 0; gv < glen; gv++) {
                        if(headerGroup[idx] ==  data[i].compareGroups[gv].compareGroupNo) {
                            var geneItem = data[i].compareGroups[gv];
                            if(geneItem.logFoldChange == "NA") {
                                bodyStr += '    <td data-gene="' + data[i].geneName + '" data-cultivar="' + geneItem.cultivar + '" data-phenotype="' + geneItem.phenotype + '" data-log-fold-change="' + geneItem.logFoldChange + '" data-adjusted-pvalue="' + geneItem.adjustedPValue + '" class="col_' + geneItem.compareGroupNo + '">'
                                bodyStr += '<span class="js-gene-value">0</span></td>';
                            } else {
                                bodyStr += '    <td data-gene="' + data[i].geneName + '" data-cultivar="' + geneItem.cultivar + '" data-phenotype="' + geneItem.phenotype + '" data-log-fold-change="' + geneItem.logFoldChange + '" data-adjusted-pvalue="' + geneItem.adjustedPValue + '" class="heat-hover col_' + geneItem.compareGroupNo + '">'
                                var lgcg = data[i].compareGroups[gv].logFoldChange;
                                if(lgcg == 'inf') {
                                    lgcg = $("#max").html()*1;
                                }
                                bodyStr += '<span class="js-gene-value">'+ lgcg +'</span></td>';
                            }

                            break;
                        }
                        notFound++;
//                        else {
//                            var geneItem = data[i].compareGroups[gv];
//                            bodyStr += '    <td class="col_' + geneItem.compareGroupNo + '">'
//                            bodyStr += '<span class="js-gene-value">0</span></td>';
//                            break;
//                        }
                    }
                    if(glen == notFound) {
                        bodyStr += '    <td class="col_' + headerGroup[idx] + '">'
                            bodyStr += '<span class="js-gene-value">-0</span></td>';
                    }
                }

                bodyStr += '</tr>'
            }
            $(".js-heat-table > tbody").empty().append(bodyStr);


            $(".js-compare-list").find(".js-compare-item").each(function(idx, el) {
                if(!$(el).hasClass("contrast-ac")) {
                    var cls = ".col_" + $(this).find("span").attr("value");
                    $(".js-heat-table").find(cls).hide();
                }
            });

        }

        var pageSize = 20;
        $(".lay-per-page-count-select").val(pageSize);

        function initTable(curr) {
            var max, min;

            var params = getParams();
            params['pageNo'] = curr || 1;
            params['pageSize'] = pageSize;

            loadMask ("#mask-test");

            return $.ajax({
                url: "${ctxroot}/diffgene/studyComparison",
                type: "get",
                dataType: "json",
                data: params,
                success: function(res) {
                    maskClose();

                    max = Math.abs(res.max);
                    $("#max").html(Math.ceil(res.max));
                    min = Math.abs(res.min);
                    $("#min").html("-" + Math.ceil(min));

                    if(res.data.length > 0) {
                        $(".js-heat-table > tbody, .js-heat-table > thead,.ga-ctrl-footer").show();
                        $('.zwsj').remove();
                        //显示表格内容
                        renderTable(res.data);

                        // 根据td里的基因值渲染颜色
                        $(".heat-table").find(".js-gene-value").each(function(idx, el) {
                            var geneValue = $(el).text() * 1;
                            $(el).hide();
                            var darkColor = "237, 144, 46"; // #ed902e
                            var lightColor = "56, 108, 202" // #386cca;
                            var alpha = 0;
                            if(geneValue > 0) {
                                alpha = (geneValue / max).toFixed(2);
                                $(el).parent("td").css({"background": "rgba(" + darkColor + "," + alpha + ")"});
                            } else {
                                alpha = ((0-geneValue) / min).toFixed(2);
                                $(el).parent("td").css({"background": "rgba(" + lightColor + "," + alpha + ")"});
                            }
                        });

                    } else {
                        $('.heat-table').append("<p class='zwsj'>暂无数据</p>")
                        $(".js-heat-table > tbody, .js-heat-table > thead,.ga-ctrl-footer").hide();
//                        alert("无数据");
                    }
                    //显示分页
                    laypage({
                        cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                        pages: Math.ceil(res.total / pageSize), //通过后台拿到的总页数
                        curr: curr || 1, //当前页
                        skin: '#5c8de5',
                        skip: true,
                        first: 1, //将首页显示为数字1,。若不显示，设置false即可
                        last: Math.ceil(res.total / pageSize), //将尾页显示为总页数。若不显示，设置false即可
                        prev: '<',
                        next: '>',
                        groups: 3, //连续显示分页数
                        jump: function (obj, first) { //触发分页后的回调
                            if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                initTable(obj.curr);
                            }
                        }
                    });
                    $("#total-page-count > span").html(res.total);
                }

            })
        }

        // 修改每页显示条数
        $(".ga-heat-table").on("change", ".lay-per-page-count-select", function() {
            pageSize = $(this).val();
            initTable(1);
        });

        // up or down regulated
        $(".js-regulated").change(function() {
           initTable(1);
        });

        // 搜索
        $(".js-search-gene-button").click(function() {
            initTable(1);
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
        $(".js-adjusted-pvalue").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $(".js-adjusted-pvalue").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(".js-log-fold-change-val").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $(".js-log-fold-change-val").on("blur", function() {
            $(this).removeClass("isFocus");
        });

        // 注册 enter 事件的元素
        document.onkeydown = function(e) {
            var _page_skip = $('.laypage_skip');
            var _search_gene_dom = $('.js-search-gene-name');
            var _log_fold_change_dom = $('.js-log-fold-change-val');
            var _adjusted_pvalue_dom = $('.js-adjusted-pvalue');
            if(e && e.keyCode==13){ // enter 键

                if( _page_skip.hasClass("isFocus") ) {
                    initTable(_page_skip.val() * 1);
                } else if (_search_gene_dom.hasClass("isFocus")) {
                    initTable(1);
                } else if (_adjusted_pvalue_dom.hasClass("isFocus")) {
                    initTable(1);
                } else if(_log_fold_change_dom.hasClass("isFocus")) {
                    initTable(1);
                } else {

                }

            }
        }


        // 收起面板
        $(".btn-toggle").unbind("click").bind("click",function(e){
//            $(".contrast-checkbox").hide();
            var _top=$(".sample-contrast").height();
            console.log(_top);
        });


        var addedGenesGroup = new Set(); // 手动添加的基因

        // 表格里的 checkbox 效果，手动添加基因
        $(".heat-table").on("click", ".js-checkbox", function() {
            var checked = $(this).children("input[type='checkbox']").prop("checked");
            var geneName = $(this).attr("data-gene");
            if(!checked) {
                $(this).children("input[type='checkbox']").prop("checked", true);
                $(this).addClass("checked");
                addedGenesGroup.add(geneName);
                console.log(addedGenesGroup)
            } else {
                $(this).children("input[type='checkbox']").prop("checked", false);
                $(this).removeClass("checked");
                addedGenesGroup.delete(geneName);
                //_.pull(addedGenesGroup, geneName);
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
            var str = '';
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

            // 基本信息加载完后初始化事件
            basic_promise.then(function() {
                table_promise = initTable();

                // 比较组 与 样本 交互
                $(".js-compare-list").on("click", ".js-compare-item", function(){
                    var checked = $(this).hasClass("contrast-ac");
                    var samplesInCompares = $(this).find("span").attr("value").split("_vs_");
                    var comparesDom = $(".js-compare-list").find(".js-compare-item");
                    var len = comparesDom.length;

                    if(checked) {
                        // 取消选中
                        $(this).removeClass("contrast-ac");
                        for(i in samplesInCompares) {
                            var cls = ".cls_sm_" + samplesInCompares[i];
                            $(".js-samples").find(cls).removeClass("choose-ac");
                        }
                    } else {
                        // 选中
                        $(this).addClass("contrast-ac");
                        for(i in samplesInCompares) {
                            var hasCount = 0, checkedCount = 0;
                            $.each(comparesDom, function(idx, el){
                                var samples = $(el).find("span").attr("value").split("_vs_");
                                for(s in samples) {
                                    if(samplesInCompares[i] == samples[s]) {
                                        hasCount++;
                                        if( $(el).hasClass("contrast-ac") ) {
                                            checkedCount++;
                                        }
                                    }
                                }
                            });
                            if(hasCount == checkedCount) {
                                var cls = ".cls_sm_" + samplesInCompares[i];
                                $(".js-samples").find(cls).addClass("choose-ac");
                            }
                        }

                    }

                    if( len == $(".js-compare-list").find(".contrast-ac").length ) {
                        var $that = $(".js-compare-all");
                        $that.addClass("contrast-ac");
                    } else {
                        var $that = $(".js-compare-all");
                        $that.removeClass("contrast-ac");
                    }
                    if( $(".js-samples > tbody").find("tr").length == $(".js-samples > tbody").find(".choose-ac").length ) {
                        $(".js-samples > thead").find("tr").addClass("choose-ac");
                    } else {
                        $(".js-samples > thead").find("tr").removeClass("choose-ac");
                    }
                });

                // 比较组 全选
                $(".js-compare-all").click(function() {
                    var hasChecked = $(this).hasClass("contrast-ac");
                    if(hasChecked) {
                        $(this).removeClass("contrast-ac");
                        $(".js-choose-all-tab").parents("tr").removeClass("choose-ac");
                    } else {
                        $(this).addClass("contrast-ac");
                        $(".js-choose-all-tab").parents("tr").addClass("choose-ac");
                    }
                    var comparesDom = $(".js-compare-list").find(".js-compare-item");

                    $.each(comparesDom, function(idx, el) {
                        var checked = $(el).hasClass("contrast-ac");
                        if(!hasChecked) { // 全选
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

                $(".js-compare-all").trigger("click");

                // 样本 与 比较组 交互
                $(".js-samples").on("click", ".js-sample-item", function() {
                    var checked = $(this).parents("tr").hasClass("choose-ac");
                    var sample = $(this).attr("data-sample");
                    var comparesDom = $(".js-compare-list").find(".js-compare-item");
                    if(checked) {
                        $(this).parents("tr").removeClass("choose-ac");
                        $.each(comparesDom, function(idx, el){
                            var samples = $(el).find("span").attr("value").split("_vs_");
                            for(s in samples) {
                                if(sample == samples[s]) {
                                    $(el).removeClass("contrast-ac");
                                }
                            }
                        });
                    } else {
                        $(this).parents("tr").addClass("choose-ac");
                        $.each(comparesDom, function(idx, el){
                            var samples = $(el).find("span").attr("value").split("_vs_");
                            for(s in samples) {
                                if(sample == samples[s]) {
                                     $(el).addClass("contrast-ac");
                                }
                            }
                        });
                    }
                    var allLen = $(".js-samples > tbody").find(".js-sample-item").length;
                    var checkedLen =  $(".js-samples > tbody").find(".choose-ac").length;
                    if(allLen == checkedLen) {
                        $(".js-choose-all-tab").parents("tr").addClass("choose-ac");
                    } else {
                        $(".js-choose-all-tab").parents("tr").removeClass("choose-ac");
                    }
                    if( $(".js-compare-list").find(".js-compare-item").length == $(".js-compare-list").find(".contrast-ac").length ) {
                        var $that = $(".js-compare-all");
                        $that.addClass("contrast-ac");
                    } else {
                        var $that = $(".js-compare-all");
                        $that.removeClass("contrast-ac");
                    }
                });

                // 样本 全选
                $(".js-choose-all-tab").click(function() {
                    var allChecked = $(this).parents("tr").hasClass("choose-ac");
                    if(allChecked) {
                        $(this).parents("tr").removeClass("choose-ac");
                        $(".js-compare-all").removeClass("contrast-ac");
                    } else {
                        $(this).parents("tr").addClass("choose-ac");
                        $(".js-compare-all").addClass("contrast-ac");
                    }
//                    $(".js-samples").find(".js-sample-item").trigger("click");
                    $.each($(".js-samples").find(".js-sample-item"), function(idx, el){
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


                table_promise.then(function() {
                    // 面板 默认
                    $(".js-default-btn").click(function() {
                        $(".js-compare-all").trigger("click");
                    });

                    // 面板确认
                    $(".js-confirm-btn").click(function(){
                        var compares = $(".js-compare-list").find(".js-compare-item");
                        $.each(compares, function(idx, el) {
                            var checked = $(el).hasClass("contrast-ac");
                            var cls = ".col_" + $(this).find("span").attr("value");
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
                        var logFoldChange = $(this).attr("data-log-fold-change");
                        var adjustedPValue = $(this).attr("data-adjusted-pvalue");
                        if(adjustedPValue.indexOf("e") > -1) {
                            var slices = adjustedPValue.split("e");
                            var before = (slices[0] * 1).toFixed(4);
                            var after = slices[1];
                            adjustedPValue = before + "e" + after + "";
                        } else {
                            adjustedPValue = (adjustedPValue * 1).toFixed(4);
                        }
                        var str = '';
                        str += '<div class="ga-tip">'
                        str += '<div class="tip-item"><span class="tip-label">Gene name: </span><span>'+ geneName +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Cultivar: </span><span>'+ cultivar +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label">Phenotype: </span><span>'+ phenotype +'</span></div>'
                        str += '<div class="hori-div"></div>'
                        str += '<div class="tip-item"><span class="tip-label2">Log<small style="top: 7px;position: relative;">2</small>-fold change: </span><span>'+ logFoldChange +'</span></div>'
                        str += '<div class="tip-item"><span class="tip-label2">Adjusted p-value: </span><span>'+ adjustedPValue +'</span></div>'
                        str += '</div>'
                        $.pt({
                            target: self,
                            position: "b",
                            width: 410,
                            height: 140,
                            content: str
                        });
                    });

                    $(".js-heat-table").on("mouseout", ".heat-hover", function() {
                        $(".pt").hide();
                    });
                });
            });



            $(".change-select").on("click", ".ga-num-add", function() {
                var num = $(this).siblings("input").val();
                $(this).siblings("input").val(num*1+1);
                initTable(1);
            });
            $(".change-select").on("click", ".ga-num-minus", function() {
                var num = $(this).siblings("input").val();
                num = num*1-1;
                if(num < 0) {
                    num = 0;
                }
                $(this).siblings("input").val(num);
                initTable(1);
            });

            $(".search-input").on("click", ".ga-num-add", function() {
                var num = $(this).siblings("input").val();
                $(this).siblings("input").val( ((num*100 + 1)/100).toFixed(2) );
                initTable(1);
            });
            $(".search-input").on("click", ".ga-num-minus", function() {
                var num = $(this).siblings("input").val();
                num = ((num*100 - 1)/100).toFixed(2);
                $(this).siblings("input").val(num);
                initTable(1);
            });

            $(".js-export").click(function() {
                var compares = [];
                $.each($(".js-compare-list").find(".contrast-ac"),function(idx, el) {
                    compares.push($(el).find("span").attr("value"));
                });
                var params = getParams();
                <%--var url = "${ctxroot}/diffgene/studyComparisonDataExport?id="+ params.id +"&gene=" + params.gene + "&logFoldChange=" + params.logFoldChange + "&adjustedPValue=" + params.adjustedPValue + "&sort=" + params.sort + "&choices=" + compares.join(",");--%>
                var url="${downloadUrl}"+"comparison_differ/"+$('.js-sarstudy').html()+".zip";
                window.open(url,"_self");
            });

        }();
        /*收起来*/
        $(".btn-contrast").click(function(){
            $(".sample-contrast").removeClass("tab-contrast-ac")
        })
        $(".btn-toggle").click(function(){
            $(".sample-contrast").addClass("tab-contrast-ac")
        })
    })
</script>
</body>
</html>