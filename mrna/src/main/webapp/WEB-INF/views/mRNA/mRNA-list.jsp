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
    <title>mRNA 实验信息</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>

    <style>
        .che-list .checkbox-ac span{
            /*background-image: url(${ctxStatic}/images/contrast-ac.png);*/
            background-image: url(${ctxStatic}/images/checkbox.png);
        }
        .genes-tab tr td{
            position: relative;
        }
        .cont{height: auto!important;}
        .input-component {
            display: none;
            position: absolute;
            /*border: 1px solid #5c8ce6;*/
            border : 1px solid #0F9145;
            z-index: 1;
        }
        .input-component {
            position: absolute;
            top: 38px;
            padding: 15px 10px;
            width: 110px;
            /*border: 1px solid #5c8ce6;*/
            border : 1px solid #0F9145;
            background-color: #fff;
            left: 0;
        }
        .input-component input {
            display: block;
            width: 100px;
            border: 1px solid #e6e6e6;
            height: 24px;
            padding-left: 10px;
        }
        .input-component p {
            margin-top: 8px;
        }
        .input-component p a{
            /*color: #5c8ce6;*/
            color : #0F9145;
        }
        .input-component p a.btn-cancel {
            margin-right: 5px;
        }
        .input-component p a {
            padding: 2px 8px;
            display: inline-block;
            /*border: 1px solid #5c8ce6;*/
            border : 1px solid #0F9145;
            border-radius: 3px;
        }
        .input-component p a.btn-confirm-info {
            /*background-color: #5c8ce6;*/
            background-color: #0F9145;
            color: #fff;
        }
        td.param:hover >.input-component{
            display:block;
        }
        #tableBody .no-data{
            height: 100px;
            line-height: 100px;;
            padding-left: 15px;
        }
        .btn-default-ac span{
            /*background-image: url(${ctxStatic}/images/contrast-ac.png);*/
            background-image: url(${ctxStatic}/images/checkbox.png);
        }
        .js-r-ac dd:nth-child(15) label span,.js-r-ac  dd:nth-child(16) label span,.js-r-ac dd:nth-child(17) label span{
            background-image: url(${ctxStatic}/images/contrast.png);
        }
        .input-component optgroup{
            height: 30px;
        }
        .libraryLayout-item a,.scientificName-item a{
            display: block;
            height: 30px;
            line-height: 30px;
            text-align: center;
            float: left;
            width: 80px;
            color: #666666;
            padding: 0 15px;
        }
        .libraryLayout-item a:hover,.scientificName-item a:hover {
            background-color: #e6edff;
        }
        .genes-tab thead tr td:last-child .input-component{
            left: -10px;;
        }
        .navigation-toggle .tab-search {
            margin-bottom: 0;
        }

        .navigation-toggle .table-item {
            margin-bottom: 1px;
            float: right;
            width: 1065px;
        }

        .navigation-toggle article {
            width: 1124px;
            min-height: 560px;
            position: relative;
        }

        .nav_ac {
            width: 70px;
            height: 689px;
            position: relative;
            float: left;
            background-color: #fff
        }

        .nav_ac .icon-right {
            float: none;
            width: 100%;
            padding-top: 15px;
            border-bottom: 1px solid #e6e6e6
        }

        .nav_ac .icon-right img {
            margin-left: 20px;
        }
        .genes-tab tbody tr td.t_spots p{
            width: 130px;
        }
        .genes-tab thead tr td.t_spots{
            padding: 10px 35px;
        }
        #total-page-count{height: 28px!important;}

        #select{
            position: relative;
        }
        #select .select_default{
            position: relative;
            float: left;
            left: 234px;
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
            left: 233px;
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
<section class="container navigation-toggle">
    <div class="tab-search">
        <div class="search">
            <!--
            <select class="js-search-select">
                <option value="All" <c:if test="${type=='All'}">selected</c:if>>All</option>
                <option value="Study" <c:if test="${type=='Study'}">selected</c:if>>Study</option>
                <option value="Tissues" <c:if test="${type=='Tissues'}">selected</c:if>>Tissues</option>
                <option value="Stage" <c:if test="${type=='Stage'}">selected</c:if>>Stage</option>
                <option value="Treat" <c:if test="${type=='Treat'}">selected</c:if>>Treat</option>
                <option value="Reference" <c:if test="${type=='Reference'}">selected</c:if>>Reference</option>
            </select>
            -->
            <div id="select">
                <input type="text" class="select_default">
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
                <input type="text" name="search" class="js-search-text" placeholder="输入您要查找的关键字" value="${keywords}">
                <span class="clear-input" style="display:none ">
                    <img src="${ctxStatic}/images/clear-search.png">
                </span>
                <button type="button" class="js-search-btn"><img src="${ctxStatic}/images/search.png">搜索</button>
            </label>
        </div>

    </div>
    <div class="nav_ac" style="display: block;">
        <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
    </div>
    <div class="table-item box-shadow item-ac">
        <div class="checkbox-item">
            <div  class="che-list">
                <span class="tab-title">表格内容:</span>
                <dl id="table_header_setting">
                    <dd><label for="sampleName" class="checkbox-ac"><span id="sampleName" data-value="sampleName"></span>Sample name</label></dd>
                    <dd><label for="study" class="checkbox-ac"><span id="study" data-value="study"></span>Study</label></dd>
                    <dd><label for="reference" class="checkbox-ac"><span id="reference" data-value="reference"></span>Reference</label></dd>
                    <dd><label for="tissue" class="checkbox-ac"><span id="tissue" data-value="tissue"></span>Tissue</label></dd>
                    <dd><label for="stage"  class="checkbox-ac"><span id="stage" data-value="stage"></span>Stage</label></dd>
                    <dd><label for="treat" class="checkbox-ac"><span id="treat" data-value="treat"></span>Treat</label></dd>
                    <dd><label for="geneType" class="checkbox-ac"><span id="geneType" data-value="geneType"></span>Genetype</label></dd>
                    <dd><label for="preservation" class="checkbox-ac"><span id="preservation" data-value="preservation"></span>Preservation</label></dd>
                    <dd><label for="phenoType" class="checkbox-ac"><span id="phenoType" data-value="phenoType"></span>Phenotype</label></dd>
                    <dd><label for="environment" class="checkbox-ac"><span id="environment" data-value="environment"></span>Environment</label></dd>
                    <dd><label for="cultivar" class="checkbox-ac"><span id="cultivar" data-value="cultivar"></span>Type</label></dd>
                    <dd><label for="scientificName" class="checkbox-ac"><span id="scientificName" data-value="scientificName"></span>Scientific Name</label></dd>
                    <dd><label for="libraryLayout" class="checkbox-ac"><span id="libraryLayout" data-value="libraryLayout"></span>Library Layout</label></dd>
                    <dd><label for="spots" class="checkbox-ac"><span id="spots" data-value="spots"></span>Spots</label></dd>
                    <dd><label for="sampleRun" ><span id="sampleRun" data-value="sampleRun"></span>Run</label></dd>
                    <dd><label for="sraStudy"><span id="sraStudy" data-value="sraStudy"></span>SRAStudy</label></dd>
                    <dd><label for="experiment"><span id="experiment" data-value="experiment"></span>Experiment</label></dd>
                </dl>
            </div>
        </div>
        <div class="export-data">
            <p class="btn-export-set">
                <button class="btn btn-export">导出数据</button>
                <button class="btn set-up">表格设置</button>
            </p>
        </div>

        <div class="choose-default">
            <div class="btn-default">
                <label><span class="js-choose-all"></span>全选</label>
                <label class="js-btn-default btn-default-ac"><span></span>默认</label>
            </div>
            <div class="btn-group" style="display: block;">
                <button class="btn-fill btn-confirm">确认</button>
                <button class="btn-chooseAll" id="clear-all">清空</button>
                <button class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
            </div>
        </div>
    </div>
    <!--table-item-->
    <aside style="display: none">
        <div class="item-header">
            <div class="icon-left"><img src="${ctxStatic}/images/tissue.png">组织<i class="">TISSUE</i></div>
            <div class="icon-right"><img src="${ctxStatic}/images/Category.png"></div>
        </div>
        <%@ include file="/WEB-INF/views/include/nav-mnra.jsp" %>
    </aside>
    <!--aside-->
    <article>
        <div class="checkbox-item-tab">
            <p class="dif-col">
                <label><span class="gene-expression"></span>基因表达量</label>
                <!--<label><span class="dif-gene"></span>差异基因</label>-->
            </p>
            <div id="mask-test">
            <div class="genes-tab" style="height: auto;" >
                <table>
                    <thead>
                        <tr>
                            <td class="t_sampleName">Sample Name</td>
                            <td class="param t_study">Study<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-study">
                                    <p><a class=" btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_reference">Reference<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-reference">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_tissue">Tissue<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-tissue">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_stage">Stage<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-stage">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_treat">Treat<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-treat">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <!--<td class="param t_geneType">GeneType<img src="${ctxStatic}/images/down.png">修改名称为Genotype-->
                            <td class="param t_geneType">GenoType<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-genet-type">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_preservation">Preservation<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-preservation">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_phenoType">Phenotype<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-pheno-type">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_environment">Environment<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-environment">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_cultivar">Type<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-cultivar">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_scientificName">ScientificName<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-scientificName">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>

                                <!--<input type="hidden" class="js-scientificName">
                                <div class="input-component scientificName-item">
                                </div>-->
                            </td>
                            <td class="param t_libraryLayout">Library Layout<img src="${ctxStatic}/images/down.png">
                                <!--<input type="hidden" class="js-libraryLayout">
                                <div class="input-component libraryLayout-item">
                                </div>-->
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-libraryLayout">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_spots">Spots<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-spots">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_sampleRun">Run<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-sample-run">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_sraStudy">SRAStudy<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-srastudy">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                            <td class="param t_experiment">Experiment<img src="${ctxStatic}/images/down.png">
                                <div class="input-component ">
                                    <input type="text" placeholder="请输入" class="js-experiment">
                                    <p><a class="btn-cancel" href="javascript:void(0);">取消</a><a href="javascript:void(0);" class="btn-confirm-info">确定</a></p>
                                </div>
                            </td>
                        </tr>
                    </thead>
                    <tbody id="tableBody">

                    </tbody>
                </table>
                <form id="exportForm" action="${ctxroot}/mrna/dataExport" method="get">
                    <input id="search1" class="search-type" name="type" type="hidden"/>
                    <input id="search2" name="keywords" type="hidden"/>
                    <input id="search3" name="condition" type="hidden"/>
                    <input id="search4" name="choices" type="hidden"/>
                </form>
            </div>
            </div>
            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
        </div>
    </article>
    <!--article-->
</section>
<!--section-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
<script src="${ctxStatic}/js/laypage/laypage.js"></script>

<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
<script>
    $(document).ready(function(){
        var    $sel = $("#select"),
            $sel_default = $(".select_default"),
            $sel_item = $(".select_item"),
            $sel_item_li = $(".select_item li")
        $sel_default.text($(".select_item li:first").text());
        //修改候选框里面的值
        var currTypes1 = window.location.search;
        var searchList1 = currTypes1.split("&");
        var currentType1 = searchList1[0].split("=")[1];

        $sel_default.val(currentType1);
        //修改候选框里面的值
        //alert();
        $sel.hover(function(){
            $sel_item.show();
            $sel_default.addClass("rotate");
            $sel_item_li.hover(function(){
                $index = $sel_item_li.index(this);
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
            //test
            var select=$(this).text();
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
            //test
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

    $(function(){

        /*show-hidden-tab*/
        function showtab(){
            var colArr = [];
            $("#table_header_setting span").each(function(){
                if(!$(this).parent().hasClass("checkbox-ac")){
                    $(".t_"+$(this).attr("data-value")).hide()
                    var colName = $(this).attr("data-value");
                    colArr.push(colName);
                }else{
                    $(".t_"+$(this).attr("data-value")).show()
                }
            })
        }
        /*确认*/
        $(".btn-confirm").click(function(){
            showtab()
        })

        var pageSize = 10;
        $(".lay-per-page-count-select").val(pageSize);
        function initTables(curr,type_select,key_input,cdt){

            loadMask ("#mask-test");

            var type=type_select;
            var key=key_input;
            var keywords=key;
            if(type=="Tissues"){
                if(${isIndex==1}){
                    keywords=keywords+".all";
                }
            }
            var currTypes = window.location.search;
            var searchList = currTypes.split("&");
            var currentType = searchList[0].split("=")[1];
            //console.log(currentType)

            $.getJSON('${ctxroot}/mrna/listByResult', {
                pageNo: curr || 1,
                pageSize: pageSize,
                // type:type ,
                type:currentType ,
                keywords: keywords,
                conditions: cdt
            }, function(res){
                maskClose();
//                console.log(res);
                //显示表格内容
                if(res.list.length==0){
                    console.log("表格无数据显示!")
                    $("#tableBody").html("<p>表格暂无数据显示!</p>")
                }
                renderTable(res);
                showtab();

                $("#total-page-count > span").html(res.total);

                //显示分页
                laypage({
                   //cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    cont: $('.ga-ctrl-footer .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    /*pages: Math.ceil(res.total / pageSize), //通过后台拿到的总页数*/
                    pages: parseInt(res.total / pageSize) + 1, //通过后台拿到的总页数*/
                    curr: curr || 1, //当前页
                    /*skin: '#5c8de5',*/
                    skin: '#0F9145',
                    prev: '<',
                    next: '>',
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    /*last: Math.ceil(res.total / pageSize), //将尾页显示为总页数。若不显示，设置false即可*/
                    last: parseInt(res.total / pageSize) + 1, //将尾页显示为总页数。若不显示，设置false即可
                    groups: 3, //连续显示分页数
                    jump: function(obj, first){ //触发分页后的回调
                        if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                            initTables(obj.curr,type,key, getParamsString());
                        }
                        if(res.list.length==0){
                            $("#tableBody").html("<p class='no-data'>无数据显示!</p>")
                        }
                    }
                });
            });
        }

        function renderTable(data){
            var arr = data.list;
            var strVar = "";
            for(var i in arr){

                strVar += '<tr>\n';
                strVar += '    <td class="t_sampleName">'+arr[i].sampleName+'</td>';
                if(arr[i].isExpression == 1){
                    strVar += '    <td class="t_study gene-expression"><p class="js-tipes-show"><a target="_blank" href="genesexpression?study='+arr[i].id+'">'+arr[i].study+'</a></td>';
                }else{
                    strVar += '    <td class="t_study dif-gene"><p class="js-tipes-show"><a target="_blank" href="genesdif?study='+arr[i].id+'">'+arr[i].study+'</a></p></td>';
                }
                strVar += '    <td class="t_reference"><p class="js-tipes-show">'+arr[i].reference+'</p></td>';
                strVar += '    <td class="t_tissue"><p>'+arr[i].tissue+'</p></td>';
                strVar += '    <td class="t_stage"><p class="js-tipes-show">'+arr[i].stage+'</p></td>';
                strVar += '    <td class="t_treat"><p class="js-tipes-show">'+arr[i].treat+'</p></td>';
                strVar += '    <td class="t_geneType"><p class="js-tipes-show">'+arr[i].geneType+'</p></td>';
                strVar += '    <td class="t_preservation"><p>'+arr[i].preservation+'</p></td>';
                strVar += '    <td class="t_phenoType"><p class="js-tipes-show">'+arr[i].phenoType+'</p></td>';
                strVar += '    <td class="t_environment"><p  class="js-tipes-show">'+arr[i].environment+'</p></td>';
                strVar += '    <td class="t_cultivar"><p>'+arr[i].type+'</p></td>';
                strVar += '    <td class="t_scientificName"><p>'+arr[i].scientificName+'</p></td>';
                strVar += '    <td class="t_libraryLayout"><p>'+arr[i].libraryLayout+'</p></td>';
                strVar += '    <td class="t_spots"><p>'+arr[i].spots+'</p></td>';
                strVar += '    <td class="t_sampleRun"><p><a target="_blank" href="'+arr[i].links+'">'+arr[i].sampleRun+'</a></p></td>';
                strVar += '    <td class="t_sraStudy"><p><a target="_blank" href="'+arr[i].links+'">'+arr[i].sraStudy+'</a></p></td>';
                strVar += '    <td class="t_experiment"><p><a target="_blank" href="'+arr[i].links+'">'+arr[i].experiment+'</a></p></td>';
                strVar += '</tr>\n';
            }

            $("#tableBody").html(strVar);

            $(".js-tipes-show").hover(
                    function(){
                        if($(this).text()!==""){
                            var _text=$(this).text()
                            var self = this;
                            var content = "";
                            content += "<div>"+_text+"</div>";
                            $.pt({
                                target: self,
                                position: 't',
                                align: 'l',
                                autoClose: false,
                                content: content,
                                width: 260,
                                height: 120
                            });
                            $(".pt").css("left", $(".pt").position().left-15);
                        }else{
                            $(".pt").remove();
                        }

                    },
                    function(){
                        $(".pt").remove();
                    }
            )

        }


        // 修改每页显示条数
        /*$("body").on("change", ".lay-per-page-count-select", function() {*/
        $("body").on("click", ".select_item_page li", function() {
            //pageSize = $(this).val();
            /*var type_select=$(".js-search-select").val();*/
            pageSize = $(".ga-ctrl-footer .lay-per-page-count-select").val();
            /*var type_select=$(".select_default").val();
            var key_input=$(".js-search-text").val();
            var cdt=getParamsString();
            initTables(1,type_select,key_input,cdt)*/
            var total= Number($("#total-page-count span").text());
            var curr = Number($(".ga-ctrl-footer .laypage_curr").text());
            var mathCeil = parseInt(total/pageSize) + 1;
            var type_select=$(".select_default").val();
            var key_input=$(".js-search-text").val();
            var cdt=getParamsString();
            if(curr > mathCeil){
                initTables(mathCeil,type_select,key_input,cdt);
            }else{
                initTables(curr,type_select,key_input,cdt);
            }

        });

        /*列表初始化*/
        /*var type=$(".js-search-select").val();*/
        var type=$(".select_default").val();
        var key= $.trim($(".js-search-text").val());
        initTables(1,type,key,getParamsString());

        /*列表选项*/
        function sellist(name1,name2,data){
            /*var type=$(".js-search-select").val();*/
            var type=$(".select_default").val();
            var key= $.trim($(".js-search-text").val());
           //console.log(type)
            var sc_str=""
            for(var i=0;i<data.length;i++){
                sc_str+="<a href='#'>"+data[i]+"</a>"
            }
            $(name1).html(sc_str);
//            $(name1).find("a").click(function(e){
//                var e=e || event;
//                e.preventDefault();
//                $(name2).val($(this).text());
//                var cdt=getParamsString();
//                initTables(1,type,key,cdt);
//            })
        }
        $("body").on("click",".scientificName-item a",function(e){
            var e=e || event;
            e.preventDefault();
            $(".js-scientificName").val($(this).text());
            var cdt=getParamsString();
            /*var type=$(".js-search-select").val();*/
            var type=$(".select_default").val();
            var key= $.trim($(".js-search-text").val());
            initTables(1,type,key,cdt);
        })
        $("body").on("click",".libraryLayout-item a",function(e){
            var e=e || event;
            e.preventDefault();
            $(".js-libraryLayout").val($(this).text());
            var cdt=getParamsString();
            /*var type=$(".js-search-select").val();*/
            var type=$(".select_default").val();
            var key= $.trim($(".js-search-text").val());
            initTables(1,type,key,cdt);
        })
        $.getJSON("scientificNames",function(data){
            var name1=$(".scientificName-item");
            var name2=$(".js-scientificName");
            sellist(name1,name2,data)
        })
        $.getJSON("libraryLayouts",function(data){
            var name1=$(".libraryLayout-item");
            var name2=$(".js-libraryLayout");
            sellist(name1,name2,data)
        })

        $(".set-up").click(function(e){
            $(this).hide();
            $(".table-item").removeClass("item-ac")
        })
        $(".btn-toggle").click(function(){
            $(".set-up").show();
            $(".table-item").addClass("item-ac")
        })
        /* 默认勾选*/
        $(".js-btn-default").click(function(){
            $("#table_header_setting label").addClass("checkbox-ac")
            $(".js-choose-all").css({"background-image":"url(${ctxStatic}/images/contrast.png)"})
            if($(this).hasClass("btn-default-ac")){
                $(this).removeClass("btn-default-ac")
                $("#table_header_setting").removeClass("js-r-ac")
                //($("#experiment").parent().html())
            }else{

                $(this).addClass("btn-default-ac");
                $("#table_header_setting").addClass("js-r-ac")
                $("#sampleRun").parent().removeClass("checkbox-ac")
                $("#sraStudy").parent().removeClass("checkbox-ac")
                $("#experiment").parent().removeClass("checkbox-ac")
            }
        })
        /*搜索*/
        $(".js-search-btn").click(function(){
            //console.log($(".genes-tab thead td input").val(""));
            /*var type_select=$(".js-search-select").val();*/
            var type_select=$(".select_default").val();
            var key_input= $.trim($(".js-search-text").val());
            window.location.href = window.location.origin + "${ctxroot}/mrna/list?type="+type_select+"&keywords=" + key_input+"&isIndex=1";
        })
        /*全选*/
        $(".js-choose-all").click(function(){
            $(".js-btn-default").removeClass("btn-default-ac");
            $("#table_header_setting").removeClass("js-r-ac");
            /*$(this).css({"background-image":"url(${ctxStatic}/images/contrast-ac.png)"})*/
            $(this).css({"background-image":"url(${ctxStatic}/images/checkbox.png)"})
            $("#table_header_setting dd label").each(function(index){
               $(this).addClass("checkbox-ac");
            })
        })
        /*切换*/
        $(".che-list label").click(function(){
            $(".js-btn-default").removeClass("btn-default-ac")
            $("#table_header_setting").removeClass("js-r-ac")
            if($(this).hasClass("checkbox-ac")){
                $(this).removeClass("checkbox-ac");
                $(".js-choose-all").css({"background-image":"url(${ctxStatic}/images/contrast.png)"})
            }else{
                $(this).addClass("checkbox-ac");
            }
        })
        /*清空*/
        $("#clear-all").click(function(){
            $("#table_header_setting").removeClass("js-r-ac");
            $(".js-choose-all").css({"background-image":"url(${ctxStatic}/images/contrast.png)"})
            $(".js-btn-default").removeClass("btn-default-ac");
            $(".che-list span").each(function(){
                $("#table_header_setting dd label").removeClass("checkbox-ac");
            })
        })

        /*选项示例*/
        /*$(".js-search-select").change(function(){
            var select=$(this).val();
            $(".js-search-text").attr("placeholder","");
            console.log(select);
            switch (select){
                case "All":
                    $(".js-search-text").val("");
                    break;
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
        })*/
        /*表头条件搜索*/
        function thSearch(){
            $(".btn-toggle").trigger("click");
            /*var type=$(".js-search-select").val();*/
            var type=$(".select_dafault").val();
            var key=$(".js-search-text").val();
            var cdt=getParamsString()
            initTables(1,type,key,cdt)
        }
        $(".btn-confirm-info").click(function(){
            thSearch();
        })
        /*筛选取消*/
        $(".btn-cancel").click(function() {
            $(this).parent("p").siblings("input").val("");
            $(this).siblings(".btn-confirm-info").trigger("click");
        });
         function getParamsString() {
            var tmp = {};
            $(".js-study").val() ? tmp.Study = $(".js-study").val() : "";
            $(".js-reference").val() ? tmp.Reference = $(".js-reference").val() : "";
            $(".js-tissue").val() ? tmp.Tissue = $(".js-tissue").val() : "";
            $(".js-stage").val() ? tmp.Stage = $(".js-stage").val() : "";
            $(".js-treat").val() ? tmp.Treat = $(".js-treat").val() : "";
            $(".js-genet-type").val() ? tmp.Genetype = $(".js-genet-type").val() : "";
            $(".js-preservation").val() ? tmp.Preservation = $(".js-preservation").val() : "";
            $(".js-pheno-type").val() ? tmp.Phenotype = $(".js-pheno-type").val() : "";
            $('.js-environment').val() ? tmp.Environment = $('.js-environment').val() : "";
            $(".js-cultivar").val() ? tmp.Cultivar = $(".js-cultivar").val() : "";
            $(".js-scientificName").val() ? tmp.ScientificName = $(".js-scientificName").val() : "";
            $(".js-libraryLayout").val() ? tmp.LibraryLayout = $(".js-libraryLayout").val() : "";
            $(".js-spots").val() ? tmp.Spots = $(".js-spots").val() : "";
            $(".js-sample-run").val() ? tmp.Run = $(".js-sample-run").val() : "";
            $(".js-srastudy").val() ? tmp.SRAStudy = $(".js-srastudy").val() : "";
            $(".js-experiment").val() ? tmp.Experiment = $(".js-experiment").val() : "";
            return JSON.stringify(tmp);
        }
        /*导出数据*/
        $(".btn-export").click(function(){
            /*var type=$(".js-search-select").val();*/
            //var type=$(".select_dafault").val();
            var currTypes = window.location.search;
            var searchList = currTypes.split("&");
            var type = searchList[0].split("=")[1];
            var key=$(".js-search-text").val();
            var keywords=key;
            if(type=="Tissues"){
                if(${isIndex==1}){
                    keywords=keywords+".all";
                }
            }
            $("#search1").val(type);
            $("#search2").val(keywords);
            $("#search3").val(getParamsString());
            var span=[];
            $("#table_header_setting label").each(function(){
                if($(this).hasClass("checkbox-ac")){
                    span.push($(this).text());
                }
            })
            var choices=span.join(",");
            //console.log(choices)
            $("#search4").val(choices);
            //console.log('search1' + $("#search1").val());
            //console.log('search2' + $("#search2").val());
            //console.log('search3' + $("#search3").val());
            //console.log('search4' + $("#search4").val());
            $("#exportForm").submit();
        })

        /*侧边栏收起来*/
        $(".nav_ac .icon-right").click(function () {
            $(".nav_ac").hide();
            $("article").css({"width": "895px"})
            $(".table-item").css({"width": "1140px", "margin-bottom": "5px"})
            $("aside").show()
        })
        $(".item-header .icon-right").click(function () {
            $("aside").hide();
            $("article").css({"width": "1125px"});
            $(".table-item").css({"width": "1065px", "margin-bottom": "1px"});
            $(".nav_ac").show();
        })
        /*注释的悬浮窗*/
        $("#table_header_setting dd label").hover( function(){
                    var _text=$(this).text()
                    var self = this;
                    var content = "";
                    switch (_text){
                        case "Sample name":
                            content += "<div>样品名称</div>";
                            break;
                        case "Study":
                            content += "<div>研究课题</div>";
                            break;
                        case "Reference":
                            content += "<div>参考文献</div>";
                            break;
                        case "Tissue":
                            content += "<div>组织</div>";
                            break;
                        case "Stage":
                            content += "<div>生长阶段</div>";
                            break;
                        case "Treat":
                            content += "<div>实验处理</div>";
                            break;
                        case "Genetype":
                            content += "<div>基因型</div>";
                            break;
                        case "Preservation":
                            content += "<div>保存条件</div>";
                            break;
                        case "Phenotype":
                            content += "<div>表型</div>";
                            break;
                        case "Environment":
                            content += "<div>生长环境</div>";
                            break;
                        case "Type":
                            content += "<div>品种</div>";
                            break;
                        case "Scientific Name":
                            content += "<div>拉丁名</div>";
                            break;
                        case "Library Layout":
                            content += "<div>建库方式</div>";
                            break;
                        case "Spots":
                            content += "<div>读段数据</div>";
                            break;
                        case "Run":
                            content += "<div>测序数据检索号</div>";
                            break;
                        case "SRAStudy":
                            content += "<div>研究课题信息检索号</div>";
                            break;
                        case "Experiment":
                            content += "<div>研究课题信息检索号</div>";
                            break;
                    }

                    $.pt({
                        target: self,
                        position: 't',
                        align: 'l',
                        autoClose: false,
                        content: content
                    });
                    $(".pt").css("left", $(".pt").position().left+15);

                },
                function(){
                    $(".pt").remove();
                })

        $(".param").hover(
            function(){
                $(this).find(".input-component").show()
            },
            function(){
                $(this).find(".input-component").hide()
            }
        )

        $(".js-search-text").on("focus", function() {
           $(this).addClass("isFocus");
        });
        $(".js-search-text").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(".input-component input").on("focus", function() {
            $(this).addClass("isFocus");
        });
        $(".input-component input").on("blur", function() {
            $(this).removeClass("isFocus");
        });
        $(document).keyup(function(event){
            var _searchDom = $(".js-search-text");
            var _conditionDoms = $(".input-component input")
            var e=e||event
            var keycode = e.which;
            if(keycode==13){
                if(_searchDom.hasClass("isFocus")) {
                    $(".js-search-btn").trigger("click");
                }
                $.each(_conditionDoms, function(idx, item) {
                    if($(item).hasClass("isFocus") ) {
                        $(this).siblings("p").find(".btn-confirm-info").trigger("click");
                    }
                });
//                thSearch();
            }
        });

    })
</script>
</body>
</html>