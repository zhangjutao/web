<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>大豆QTL物理-遗传图谱</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/index.css">

	<script src="http://cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/raphael/2.2.7/raphael.min.js"></script>
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
	<style>
		text {
			cursor: pointer;
		}
		.circle {
			display: inline-block;
			width: 15px;
			height: 15px;
		}
		ul li {
			display: inline-block;
			margin: 5px 10px;
		}
        .search-section {
            padding: 20px 20px 0;
            border-bottom: 1px solid #e6e6e6;
        }
        .content-screening{
            padding-bottom:5px;
            border-bottom: 1px solid #e6e6e6;
            padding-bottom: 50px;
        }
        .content-screening .content-title{
            float: left;
            line-height: 40px;
        }
        .content-screening .checkbox-list{
            float: left;
            padding: 0;
        }
        .checkbox-qtl{
            float: left;
            width: 1089px;
            position: relative;
        }
        .checkbox-qtl ul{
            display: block;
        }
        .checkbox-qtl ul li{
            display: inline-block;
            padding: 0;
            margin: 0 9px 10px 0px;
            line-height: 35px;
            width: 190px;
        }
        .checkbox-qtl ul li span{
            width: 40px;
            height: 40px;
            display: inline-block;
            margin-right: 11px;
            float: left;;
        }
        .checkbox-qtl ul li:nth-child(1) span{
            background-image: url(${ctxStatic}/images/input-bg_03.png);
        }
        .checkbox-qtl ul li:nth-child(2) span{
            background-image: url(${ctxStatic}/images/input-bg_05.jpg);
        }
        .checkbox-qtl ul li:nth-child(3) span{
            background-image: url(${ctxStatic}/images/input-bg_07.png);
        }
        .checkbox-qtl ul li:nth-child(4) span{
            background-image: url(${ctxStatic}/images/input-bg_09.png);
        }
        .checkbox-qtl ul li:nth-child(5) span{
            background-image: url(${ctxStatic}/images/input-bg_11.png);
        }
        .checkbox-qtl ul li:nth-child(6) span{
            background-image: url(${ctxStatic}/images/input-bg_13.png);
        }
        .checkbox-qtl ul li:nth-child(7) span{
            background-image: url(${ctxStatic}/images/input-bg_21.png);
        }
        .checkbox-qtl ul li:nth-child(8) span{
            background-image: url(${ctxStatic}/images/input-bg_22.png);
        }
        .checkbox-qtl ul li:nth-child(9) span{
            background-image: url(${ctxStatic}/images/input-bg_23.png);
        }
        .checkbox-qtl ul li:nth-child(10) span{
            background-image: url(${ctxStatic}/images/input-bg_24.png);
        }
        .checkbox-qtl ul li:nth-child(11) span{
            background-image: url(${ctxStatic}/images/input-bg_25.png);
        }
        .checkbox-qtl ul li:nth-child(12) span{
            background-image: url(${ctxStatic}/images/input-bg_26.png);
        }
        .checkbox-qtl ul li:nth-child(13) span{
            background-image: url(${ctxStatic}/images/input-bg_33.png);
        }
        .checkbox-qtl ul li:nth-child(14) span{
            background-image: url(${ctxStatic}/images/input-bg_34.png);
        }
        .checkbox-qtl ul li:nth-child(15) span{
            background-image: url(${ctxStatic}/images/input-bg_35.png);
        }
        .checkbox-qtl ul li:nth-child(1).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_15.jpg);
        }
        .checkbox-qtl ul li:nth-child(2).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_17.jpg);
        }
        .checkbox-qtl ul li:nth-child(3).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_19.jpg);
        }
        .checkbox-qtl ul li:nth-child(4).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_21.jpg);
        }
        .checkbox-qtl ul li:nth-child(5).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_23.jpg);
        }
        .checkbox-qtl ul li:nth-child(6).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_25.jpg);
        }
        .checkbox-qtl ul li:nth-child(7).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_41.jpg);
        }
        .checkbox-qtl ul li:nth-child(8).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_43.jpg);
        }
        .checkbox-qtl ul li:nth-child(9).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_45.jpg);
        }
        .checkbox-qtl ul li:nth-child(10).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_47.jpg);
        }
        .checkbox-qtl ul li:nth-child(11).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_49.jpg);
        }
        .checkbox-qtl ul li:nth-child(12).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_51.jpg);
        }
        .checkbox-qtl ul li:nth-child(13).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_67.jpg);
        }
        .checkbox-qtl ul li:nth-child(14).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_69.jpg);
        }
        .checkbox-qtl ul li:nth-child(15).qtl-ac span{
            background-image: url(${ctxStatic}/images/checkbox-bg_71.jpg);
        }
        .checkbox-qtl .btn-group{
            top:160px;
            right:132px;
            position: absolute;
        }
        .checkbox-qtl .btn-group button:hover{
            cursor: pointer;
        }
        .checkbox-qtl .btn-chooseAll{
            padding: 0 3px;
        }
        .checkbox-qtl .btn-confirm{
            margin-right: 10px;
        }
        .btn-cancel{
            background-color: #ffffff;
            border: none;
            padding: 0 3px;
            color: #5c8ce6;
        }
        .search-change{
            padding: 8px 0;
        }
        .content-screening:after{
            content: "";
            display: block;
            clear: both;
        }
        .search-change .search-text{
            float: left;
            line-height: 35px;
        }
        .search-change input{
            outline: none;
            width: 480px;
            height: 33px;
            line-height: 33px;
            display: inline-block;
            border: 1px solid #cacaca;
            padding: 1px 10px;
            margin-right: -5px;
            float: left;
        }
        .search-change span:nth-child(1){
            display: inline-block;
            margin-right: 20px;
        }
        .search-change #btn-search{
            background-color: #5c8ce6;
            height: 37px;
            border: 1px solid #5c8ce6;
            width: 120px;
            display: inline-block;
            color: #fff;
            border-top-right-radius: 3px;
            border-bottom-right-radius: 3px;
            background-image: url("${ctxStatic}/images/search.png");
            background-repeat: no-repeat;
            background-size: 16px;
            background-position: 28px;
            padding-left: 15px;
            position: relative;
            top:0px;
        }
        .search-change #btn-search:hover{
            cursor: pointer;
        }
        #chromosome{
            width: 126px;
            height: 33px;
            border: 1px solid #5c8ce6;
            color: #999;
            border-right: none;
            margin-right: -5px;
            padding-left: 40px;;
        }
        #edition-num{
            width: 240px;
            height: 33px;
            border: 1px solid #5c8ce6;
            color: #999;
            /*padding-left: 55px;*/
        }
        .checkbox-qtl ul li:hover span{
            cursor: pointer;
        }
        .tab-detail-thead{cursor:move}
        .tab-detail-tbody table{
            width: 100%;
        }
        .tab-detail-tbody tr td{
            border:1px solid #e6e6e6;
            padding: 15px  10px;
        }
        .tab-detail-tbody tr td:nth-child(1){
            width: 200px;
            text-align: center;
        }

	</style>
</head>

<body>

<qtl:qtl-header />
<section class="container" style="background: #FFF;">

    <div class="search-section">
        <%--<button type="button" id="zoomin">放大</button> --%>
        <%--<button type="button" id="zoomout">缩小</button> --%>
        <div class="content-screening">
            <label class="content-title" >内容筛选：</label>
            <div class="checkbox-qtl">
                <ul>
                    <li class="qtl-ac"><span value="QTL_fungal"></span> <label>QTL_fungal</label></li>
                    <li class="qtl-ac"><span value="QTL_inorganic"></span> <label>QTL_inorganic</label></li>
                    <li class="qtl-ac"><span value="QTL_insect"></span> <label>QTL_insect</label></li>
                    <li class="qtl-ac"><span value="QTL_leaf-stem"></span> <label>QTL_leaf-stem</label></li>
                    <li class="qtl-ac"><span value="QTL_misc"></span> <label>QTL_misc</label></li>
                    <li class="qtl-ac"><span value="QTL_nematode"></span> <label>QTL_nematode</label></li>
                    <li class="qtl-ac"><span value="QTL_oil"></span> <label>QTL_oil</label></li>
                    <li class="qtl-ac"><span value="QTL_other-seed"></span> <label>QTL_other-seed</label></li>
                    <li class="qtl-ac"><span value="QTL_pod"></span> <label>QTL_pod</label></li>
                    <li class="qtl-ac"><span value="QTL_protein"></span> <label>QTL_protein</label></li>
                    <li class="qtl-ac"><span value="QTL_reprod-period"></span> <label>QTL_reprod-period</label></li>
                    <li class="qtl-ac"><span value="QTL_root"></span> <label>QTL_root</label></li>
                    <li class="qtl-ac"><span value="QTL_viral"></span> <label>QTL_viral</label></li>
                    <li class="qtl-ac"><span value="QTL_whole-plant"></span> <label>QTL_whole-plant</label></li>
                    <li class="qtl-ac"><span value="QTL_yield"></span> <label>QTL_yield</label></li>
                </ul>
                <p class="btn-group">
                    <button type="button" class="btn-fill btn-confirm">确定</button>
                    <button type="button" class="btn-chooseAll">默认</button>
                    <button type="button" class="btn-cancel">取消</button>
                </p>
            </div>
        </div>
         <div class="search-change">
            <span>
                <label class="search-text">搜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;索：</label>
                <input type="text" id="search" placeholder="请输入您要搜索的页面元素名称进行搜索">
                <button type="button" class="btn" id="btn-search">搜索</button>
            </span>
            <span>
                <label>切&nbsp;&nbsp;&nbsp;&nbsp;换：</label>
                <select name="" id="chromosome">
                    <option value="D1a(1)">D1a 1</option>
                    <option value="D1b(2)">D1b 2</option>
                    <option value="N(3)">N&nbsp;&nbsp;&nbsp;&nbsp; 3</option>
                    <option value="C1(4)">C1&nbsp;&nbsp; 4</option>
                    <option value="A1(5)">A1&nbsp;&nbsp; 5</option>
                    <option value="C2(6)">C2&nbsp;&nbsp; 6</option>
                    <option value="M(7)">M&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;7</option>
                    <option value="A2(8)">A2&nbsp;&nbsp; 8</option>
                    <option value="K(9)">K&nbsp;&nbsp;&nbsp;&nbsp; 9</option>
                    <option value="O(10)">O&nbsp;&nbsp;&nbsp;&nbsp;10</option>
                    <option value="B1(11)">B1&nbsp;&nbsp; 11</option>
                    <option value="H(12)">H&nbsp;&nbsp;&nbsp;&nbsp; 12</option>
                    <option value="F(13)">F&nbsp;&nbsp;&nbsp;&nbsp; 13</option>
                    <option value="B2(14)">B2&nbsp;&nbsp; 14</option>
                    <option value="E(15)">E&nbsp;&nbsp;&nbsp;&nbsp; 15</option>
                    <option value="J(16)">J&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 16</option>
                    <option value="D2(17)">D2&nbsp;&nbsp; 17</option>
                    <option value="G(18)">G&nbsp;&nbsp;&nbsp;&nbsp; 18</option>
                    <option value="L(19)">L&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 19</option>
                    <option value="I(20)">I&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 20</option>
                </select>
                <select name="" id="edition-num">
                    <option value="Glycine_max.V1.0.23.dna.genome" data-chr="">Glycine_max.V1.0.23.dna.genome</option>
                    <option value="Gmax_275_v2.0" data-chr="Chr">Gmax_275_v2.0</option>
                </select>
            </span>
         </div>
    </div>

    <div id="gene" style="overflow: hidden;">
        <%--<iframe name="geneFrame" id="geneFrame" src="${ctxroot}/innerGene" onload="initSearch()" frameborder="0" height="1840" width="1200" scrolling="no"></iframe>--%>
        <iframe name="geneFrame" id="geneFrame" src="${ctxroot}/innerGene" frameborder="0" height="1840" width="1200" scrolling="no"></iframe>
    </div>

</section>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<div id="mid"></div>
<div class="tab-detail">
    <div class="tab-detail-thead">
        <p>MARKER NAME: <span class="js-marker-name"></span>
            <a href="javascript:void(0)">X</a>
        </p>
    </div>
    <div class="tab-detail-tbody">
        <table>
            <tr>
                <td>Name</td> <td class="js-marker-name"></td>
            </tr>
            <tr>
                <td>Type</td> <td class="js-marker-type"></td>
            </tr>
            <tr>
                <td>LG(Chr)</td> <td class="js-marker-lg"></td>
            </tr>
            <tr>
                <td>Position</td> <td class="js-marker-position"></td>
            </tr>
            <tr>
                <td>Amplification Info</td> <td class="js-marker-info"></td>
            </tr>
            <tr>
                <td>Providers</td> <td class="js-marker-provider"></td>
            </tr>
            <tr>
                <td>References</td> <td class="js-marker-references"></td>
            </tr>
            <%--<tr>--%>
                <%--<td>Associated QTLs</td> <td class="js-marker-associate"></td>--%>
            <%--</tr>--%>
        </table>
    </div>
</div>
<script src="${ctxStatic}/js/layout.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
    /*拖动弹框*/
    $(function (){


    $(".tab-detail").draggable({ containment: "body" });
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }

    var initChrVersion = function() {
        var chr = getUrlParam("chr");
        var version = getUrlParam("version");
        var lg = getUrlParam("markerlg");
        $("#chromosome").val(lg);
        $("#edition-num").val(version);
    }();

    $("#chromosome").change(function() {
        chrVersion();
    });
    $("#edition-num").change(function() {
        chrVersion();
    });
    function chrVersion() {
        var lg = $("#chromosome").val();
        var version = $("#edition-num").val();
        var chrN = (lg.split("(")[1]).split(")")[0];
        if(chrN.length == 1) {
            chrN = "0" + chrN;
        }
        var chr = $("#edition-num option:selected").attr("data-chr") + chrN;
        window.location.href = "${ctxroot}/gene?chr="+chr+"&version="+version+"&markerlg="+lg;
    }


    $("#gene").find("iframe").attr("src", "${ctxroot}/innerGene" + location.search);

    // 搜索
    $("#btn-search").click(function() {
        geneFrame.window.searchQtl($("#search").val());
    });

    // 内容筛选
    function filterQtlType(typeString) {
        geneFrame.window.redrawQtls(typeString);
    }

    // 显示marker详情
    function showMarkerInfo(name) {
        $.ajax({
            url: "${ctxroot}/query/marker",
            data: {markerName: name},
            type:"GET",
            dataType: "json",
            success: function(data) {
                $(".js-marker-name").html(data.name ? data.name : "");
                $(".js-marker-info").html(data.amplificationInfo ? data.amplificationInfo: "");
                $(".js-marker-lg").html(data.lg ? data.lg : "");
                $(".js-marker-position").html(data.position ? data.position : "");
                $(".js-marker-provider").html(data.provider ? data.provider : "");
                $(".js-marker-references").html(data.refference ? data.refference : "");
                $(".js-marker-type").html(data.type ? data.type : "");
            }
        });
        $("#mid").show();
        $(".tab-detail").show();
    }

    // QTL type 切换
    $(".checkbox-qtl ul li span ").click(function(){
        if($(this).parent().hasClass("qtl-ac")){
            $(this).parent().removeClass("qtl-ac");
        }else{
            $(this).parent().addClass("qtl-ac");
        }
    });
    var chooseTypes = [];
    // 确定按Type过滤数据
    $(".btn-confirm").click(function() {
        var _spans = $(".checkbox-qtl ul li span");
        chooseTypes = [];
        for(var i =0; i < _spans.length; i++) {
            if($(_spans[i]).parent().hasClass("qtl-ac")) {
                chooseTypes.push($(_spans[i]).attr("value"));
            }
        }
        filterQtlType(chooseTypes.join(","));
    });
    // 默认
    $(".btn-chooseAll").click(function() {
        $(".checkbox-qtl ul li").addClass("qtl-ac");
    });
    // 取消
    $(".btn-cancel").click(function() {
        var _spans = $(".checkbox-qtl ul li span");
        $(".checkbox-qtl ul li").removeClass("qtl-ac");
        for(var i =0; i < _spans.length; i++) {
            for(var j = 0; j < chooseTypes.length; j++) {
                if($(_spans[i]).attr("value") == chooseTypes[j]) {
                    $(_spans[i]).parent().addClass("qtl-ac");
                    break;
                }
            }
        }
    });

    // 参数带入搜索qtlname
    var qtlName = getUrlParam("qtl");
    $("#search").val(qtlName);
    function initSearch() {
//        setTimeout(function(){
//            if(qtlName) {
//                $("#btn-search").trigger("click");
//            }
//        },2800);
    };

    })
</script>

</body>

</html>