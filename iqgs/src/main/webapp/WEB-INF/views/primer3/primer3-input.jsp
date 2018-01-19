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
    <title>IQGS PRIMER3</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/primer3.css">--%>
    <link rel="stylesheet" href="${ctxStatic}/css/primer3-input.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/addTags.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">--%>
    <link href="https://cdn.bootcss.com/normalize/7.0.0/normalize.min.css" rel="stylesheet">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script>
        var CTXROOT = "${ctxroot}";
    </script>
    <script src="${ctxStatic}/js/jquery-ui.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <%--<script src="${ctxStatic}/js/d3.js"></script>
    <script src="${ctxStatic}/js/svg-pan-zoom.js"></script>--%>
    <style>
        .total-page-count {
            display: none !important;
        }

        /* master分支中无群体信息 */
        #populationInfos {
            padding: 8px 20px;
            background: #5D8CE6;
            color: #fff;
            width: 68px;
            float: right;
            cursor: pointer;
            font-size: 16px;
            margin-bottom: 16px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<iqgs:primer3-header />
<!--header-->
<div class="container primer3-content js-nav-ac">
    <%--<%@ include file="/WEB-INF/views/include/sidebar.jsp" %>--%>
    <div class="peimer3-main">
        <div class="primer3-top">
            <div class="paste-explain">
                Paste sequence below(5'~3')
            </div>
            <div class="sequence-length">
                Length:<span class="length-value">0</span>bp
            </div>
            <div class="clear-fix"></div>
        </div>
        <div class="primer3-middle">
            <textarea class="sequence-block"></textarea>
        </div>
        <div class="error-message"></div>
        <div class="primer3-bottom">
            <div class="primer3-bottom-top">
                <div class="setting-title">Genaral Primer Condition Settings</div>
                <div class="reset-btn">默认设置</div>
                <div class="clear-fix"></div>
            </div>
            <div class="primer3-input">
                <div class="primer3-input-left">
                    <div class="peimer3-input-item primer-size">
                        <div class="peimer3-input-item-title input-item-common">Primer Size(nt)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="primerSizeMin" value="18" placeholder="18"/>
                        <div class="input-item-common">-</div>
                        <div class="input-opt input-item-common">Opt</div>
                        <input type="number" class="input-content input-item-common" name="primerSizeOpt" value="20" placeholder="20"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerSizeMax" value="23" placeholder="23"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                    <div class="peimer3-input-item primer-GC">
                        <div class="peimer3-input-item-title input-item-common">Primer GC(%)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="primerGCMin" value="30.0" placeholder="30.0"/>
                        <div class="input-item-common">-</div>
                        <div class="input-opt input-item-common">Opt</div>
                        <input type="number" class="input-content input-item-common" name="primerGCOpt" value="50.0" placeholder="50.0"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerGCMax" value="70.0" placeholder="70.0"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                    <div class="peimer3-input-item primer-Tm"><%--Primer3 Tm--%>
                        <div class="peimer3-input-item-title input-item-common">Primer Tm(℃)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="primerTmMin" value="57.0" placeholder="57.0"/>
                        <div class="input-item-common">-</div>
                        <div class="input-opt input-item-common">Opt</div>
                        <input type="number" class="input-content input-item-common" name="primerTmOpt" value="59.0" placeholder="59.0"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerTmMax" value="62.0" placeholder="62.0"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                </div>
                <div class="primer3-input-right">
                    <div class="peimer3-input-item primer-f"><%----%>
                        <div class="peimer3-input-item-title input-item-common">Primer F(bp)</div>
                        <div class="input-min input-item-common">Start(5')</div>
                        <input type="number" name="primerFStart" class="input-content input-item-common"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Length</div>
                        <input type="number" name="primerFLength" class="input-content input-item-common"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                    <div class="peimer3-input-item primer-r"><%--Primer3 R--%>
                        <div class="peimer3-input-item-title input-item-common">Primer R(bp)</div>
                        <div class="input-min input-item-common">Start(5')</div>
                        <input type="number" name="primerRStart" class="input-content input-item-common"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Length</div>
                        <input type="number" name="primerRLength" class="input-content input-item-common"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                    <div class="peimer3-input-item product-size"><%--Product Size--%>
                        <div class="peimer3-input-item-title input-item-common">Product Size(bp)</div>
                        <div class="input-min input-item-common">Min</div> <%--todo 加入placeholder--%>
                        <input type="number" class="input-content input-item-common" name="productSizeMin"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" type="text" class="input-content input-item-common" name="productSizeMax"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="error-message"></div>
                    <div class="primer3-submit-btn">Search</div>
                </div>
                <div class="clear-fix"></div>
            </div>
        </div>
    </div>


</div>
<!--footer-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
    var ctxRoot = '${ctxroot}';
    var primer3 = {
        isFirefox:navigator.userAgent.toUpperCase().indexOf("FIREFOX")==-1?false:true,
        seqLength:0,
        errorMessageMap:{
            primerSizeMin:"Primer size不能低于15",
            primerSizeMax:"Primer size不能高于30",
            primerTmMin:"Primer Tm不能低于55℃",
            primerTmMax:"Primer Tm不能高于80℃",
            primerGCMin:"Primer GC不能低于20%",
            primerGCMax:"Primer GC不能高于80%",
            productSizeMin:"Product size不能小于序列长度减200",
            productSizeMax:"Product size不能大于序列长度",
            errorScope: "Max必须大于Min"
        },
        defaultValue: {
            primerSizeMin: 18,
            primerSizeMax: 23,
            primerSizeOpt:20,
            primerTmMin: 57.0,
            primerTmMax: 62.0,
            primerTmOpt:59.0,
            primerGCMin: 30.0,
            primerGCMax: 70.0,
            primerGCOpt:50.0,
            /*primerFStart:0,
            primerFLength:0.1,*/
        },
        valueScope: {
            primerSizeMin: 15,
            primerSizeMax: 30,
            primerTmMin: 55,
            primerTmMax: 80,
            primerGCMin: 20,
            primerGCMax: 80,
            productSizeMin: 0.8,
            productSizeMax: 1,
            /*primerFStart:0,
            primerFLength:0.5,*/
        },
        changeLengthAndProductSize:function () {
            //clearTimeout(primer3.time);
            //primer3.time = setTimeout("primer3.changeLength()", 500);
            /*$('input[name="productSizeMin"]').val('');
            $('input[name="productSizeMax"]').val('');*/
            $('.sequence-block').removeClass('red-border');
            $('.sequence-block').parent().next().hide();
            primer3.changeLength();
            //if($('input[name="productSizeMin"]').val()==''&&$('input[name="productSizeMax"]').val()==''){
                $('input[name="productSizeMin"]').val(parseInt(primer3.seqLength*0.5));
                $('input[name="productSizeMax"]').val(primer3.seqLength);
            //}
            $('input[name="productSizeMin"]').prop('placeholder',parseInt(primer3.seqLength*0.5));
            $('input[name="productSizeMax"]').prop('placeholder',primer3.seqLength);
        },
        changeLength: function () {
            var val = $('.sequence-block').val();
            var seqLength = val.replace(/[\r\n]/g, "").length;
            primer3.seqLength=seqLength;
            console.log("primer3.seqLength: "+primer3.seqLength);
            $('.length-value').html(seqLength);
        },
        checkPrimerFAndRAndProdSuctizeInput:function () {
            if(($('input[name="primerFStart"]').val()!=''&&$('input[name="primerFLength"]').val()=='')||
                ($('input[name="primerFStart"]').val()==''&&$('input[name="primerFLength"]').val()!='')){
                primer3.errorTip($('input[name="primerFStart"]'),"Primer F和Primer R需要全输入或全不输入");
                return false;
            }
            if(($('input[name="primerRStart"]').val()!=''&&$('input[name="primerRLength"]').val()=='')||
                ($('input[name="primerRStart"]').val()==''&&$('input[name="primerRLength"]').val()!='')){
                primer3.errorTip($('input[name="primerRStart"]'),"Primer R和Primer F需要全输入或全不输入");
                return false;
            }
            if(($('input[name="primerFStart"]').val()!=''&&$('input[name="primerFLength"]').val()!='')){
                if($('input[name="primerRStart"]').val()==''||$('input[name="primerRLength"]').val()==''){
                    primer3.errorTip($('input[name="primerRStart"]'),"Primer R和Primer F需要全输入或全不输入");
                    return false;
                }
            }
            if(($('input[name="primerRStart"]').val()!=''&&$('input[name="primerRLength"]').val()!='')){
                if($('input[name="primerFStart"]').val()==''||$('input[name="primerFLength"]').val()==''){
                    primer3.errorTip($('input[name="primerFStart"]'),"Primer F和Primer R需要全输入或全不输入");
                    return false;
                }
            }
            if(($('input[name="productSizeMin"]').val()!=''&&$('input[name="productSizeMax"]').val()=='')||
                ($('input[name="productSizeMin"]').val()==''&&$('input[name="productSizeMax"]').val()!='')){
                primer3.errorTip($('input[name="productSizeMin"]'),"Product Size需要全输入或全不输入");
                return false;
            }
            return true;
        },
        checkProductSizeAndPrimer:function () {
            if(primer3.checkPrimerFAndRAndProdSuctizeInput()){

                var length=parseInt($('.length-value').text());
                if(length==0&&$('.sequence-block').val()!=''){
                    length=primer3.seqLength;
                }
                //检查并获取productSize的值
                if($('input[name="productSizeMin"]').val()==''&&$('input[name="productSizeMax"]').val()==''){
                    var productSizeMin=parseInt(0.5*length);
                    var productSizeMax=length;
                }else {
                    var productSizeMin=parseInt($('input[name="productSizeMin"]').val());
                    var productSizeMax=parseInt($('input[name="productSizeMax"]').val());
                }
                if(productSizeMax>length){  //todo
                    primer3.errorTip($('input[name="productSizeMax"]'),'Product Size必须小于序列的长度');
                    return false;
                }else {
                    if(productSizeMin<2*parseInt($('input[name="primerSizeMin"]').val())){
                        primer3.errorTip($('input[name="productSizeMin"]'),'Product Size必须大于等于2倍Primer Size Min长度');
                        return false;
                    }
                }

                //检查并获取primerFStart和primerFLength值
                if($('input[name="primerFStart"]').val()==''&&$('input[name="primerFLength"]').val()==''){
                    var primerFStart='';
                    var primerFLength='';
                }else {
                    var primerFStart=parseInt($('input[name="primerFStart"]').val());
                    var primerFLength=parseInt($('input[name="primerFLength"]').val());
                    if(primerFLength<productSizeMin){
                        primer3.errorTip($('input[name="primerFLength"]'),'Primer F的Length必须大于Product Size Min长度');
                        return false;
                    }
                    if(primerFStart+primerFLength>length){
                        primer3.errorTip($('input[name="primerFLength"]'),'Primer F的Start加Primer F的Length必须小于等于序列的长度');
                        return false;
                    }
                }
                //检查并获取primerRStart和primerRLength值
                if($('input[name="primerRStart"]').val()==''&&$('input[name="primerRLength"]').val()==''){
                    var primerRStart='';
                    var primerRLength='';
                }else {
                    var primerRStart=parseInt($('input[name="primerRStart"]').val());
                    var primerRLength=parseInt($('input[name="primerRLength"]').val());
                    if(primerRLength<productSizeMin){
                        primer3.errorTip($('input[name="primerRLength"]'),'Primer R的Length必须大于Product Size Min长度');
                        return false;
                    }
                    if(primerRStart+primerRLength>length){
                        primer3.errorTip($('input[name="primerRLength"]'),'Primer R的Start加Primer R的Length必须小于等于序列的长度');
                        return false;
                    }
                }

                //返回primer f、r和product size的值
                return {
                    productSizeMin:productSizeMin,
                    productSizeMax:productSizeMax,
                    primerFLength:primerFLength,
                    primerFStart:primerFStart,
                    primerRLength:primerRLength,
                    primerRStart:primerRStart,
                }

            }
        },
        errorTip:function (ele,errorMessage) {
            $('.error-message').hide();
            $('.peimer3-input-item>input').removeClass('red-border');
            $(ele).addClass('red-border');
            //$(ele).parent().next().html(primer3.errorMessageMap[key]).show()
            $(ele).parent().next().html(errorMessage).show();
        },
        checkPrimer3:function () {
            var primerSizeMin=parseInt($('input[name="primerSizeMin"]').val());
            var primerSizeMax=parseInt($('input[name="primerSizeMax"]').val());
            var primerSizeOpt=parseInt($('input[name="primerSizeOpt"]').val());
            var primerGCMin=parseInt($('input[name="primerGCMin"]').val());
            var primerGCMax=parseInt($('input[name="primerGCMax"]').val());
            var primerGCOpt=parseInt($('input[name="primerGCOpt"]').val());
            var primerTmMin=parseInt($('input[name="primerTmMin"]').val());
            var primerTmMax=parseInt($('input[name="primerTmMax"]').val());
            var primerTmOpt=parseInt($('input[name="primerTmOpt"]').val());
            if(primerSizeMin>=primerSizeMax){
                primer3.errorTip($('input[name="primerSizeMin"]'),'primerSize的Max必须大于Min');
                return false;
            }
            if(primerGCMin>=primerGCMax){
                primer3.errorTip($('input[name="primerGCMin"]'),'primerGC的Max必须大于Min');
                return false;
            }
            if(primerTmMin>=primerTmMax){
                primer3.errorTip($('input[name="primerTmMin"]'),'primerTm的Max必须大于Min');
                return false;
            }
            if(primerSizeMin<15){
                primer3.errorTip($('input[name="primerSizeMin"]'),'primerSize的Min必须大于等于15');
                return false;
            }
            if(primerSizeMax>30){
                primer3.errorTip($('input[name="primerSizeMax"]'),'primerSize的Max必须小于等于30');
                return false;
            }
            if(primerSizeOpt>primerSizeMax||primerSizeOpt<primerSizeMin){
                primer3.errorTip($('input[name="primerSizeOpt"]'),'opt需在min到max之间');
                return false;
            }
            if(primerGCMin<20){
                primer3.errorTip($('input[name="primerGCMin"]'),'primerGC的Min必须大于等于20');
                return false;
            }
            if(primerGCMax>80){
                primer3.errorTip($('input[name="primerGCMax"]'),'primerGC的Max必须小于等于80');
                return false;
            }
            if(primerGCOpt>primerGCMax||primerGCOpt<primerGCMin){
                primer3.errorTip($('input[name="primerGCOpt"]'),'opt需在min到max之间');
                return false;
            }
            if(primerTmMin<55){
                primer3.errorTip($('input[name="primerTmMin"]'),'primerTm的Min必须大于等于55');
                return false;
            }
            if(primerTmMax>80){
                primer3.errorTip($('input[name="primerTmMax"]'),'primerTm的Max必须小于等于80');
                return false;
            }
            if(primerTmOpt>primerTmMax||primerTmOpt<primerTmMin){
                primer3.errorTip($('input[name="primerTmOpt"]'),'opt需在min到max之间');
                return false;
            }
            return true;
        },
        checkSeq:function (seq) {
            var seqs=seq.split('');
            for(var i=0;i<seqs.length;i++){
                if(seqs[i]!='A'&&seqs[i]!='T'&&seqs[i].toUpperCase()!='C'&&seqs[i]!='G'&&seqs[i]!='N'){
                    return false;
                }
            }
            return true;
        },
        time: '',
        utils: {
            sendAjaxRequest:function (method, url,data) {
                if (window.Promise) {//检查浏览器是否支持Promise
                    var promise = new Promise(function (resolve, reject) {
                        $.ajax({
                            method: method,
                            url: url,
                            data:data,
                            dataType: "json",
                            //contentType: "application/json,charset=UTF-8;",
                            success: function (result) {
                                resolve(result)
                            },
                            error: function (error) {
                                reject(error)
                            }
                        });
                    });
                    return promise;
                } else {
                    alert("sorry,你的浏览器不支持Promise 对象")
                };
            }
        }

    };

    $(function () {
        if(primer3.isFirefox){
            $('input[name="primerRStart"]').val('');
            $('input[name="primerRLength"]').val('');
            $('input[name="primerFStart"]').val('');
            $('input[name="primerFLength"]').val('');
            $('.sequence-block').val('');
            $('input[name="productSizeMin"]').val('');
            $('input[name="productSizeMax"]').val('');
        }
        $('.product-size>.input-min').css({'width':parseInt($('.primer-r>.input-min').css('width'))+'px'});
        $('.product-size>.input-max').css({'width':parseInt($('.primer-r>.input-max').css('width'))+'px'});

        //记录用户输入序列的长度
        $('.sequence-block').on({
            keyup:primer3.changeLengthAndProductSize,
            change:primer3.changeLengthAndProductSize
        });

        //重置用户输入
        $('.reset-btn').click(function () {
            $('.error-message').hide();
            $('.peimer3-input-item>input').removeClass('red-border');
            $('.primer3-input  .primer-size>input[name="primerSizeMin"]').val(primer3.defaultValue.primerSizeMin);
            $('.primer3-input  .primer-size>input[name="primerSizeMax"]').val(primer3.defaultValue.primerSizeMax);
            $('input[name="primerSizeOpt"]').val(primer3.defaultValue.primerSizeOpt);
            $('.primer3-input  .primer-GC>input[name="primerGCMin"]').val(primer3.defaultValue.primerGCMin);
            $('.primer3-input  .primer-GC>input[name="primerGCMax"]').val(primer3.defaultValue.primerGCMax);
            $('input[name="primerGCOpt"]').val(primer3.defaultValue.primerGCOpt);
            $('.primer3-input  .primer-Tm>input[name="primerTmMin"]').val(primer3.defaultValue.primerTmMin);
            $('.primer3-input  .primer-Tm>input[name="primerTmMax"]').val(primer3.defaultValue.primerTmMax);
            $('input[name="primerTmOpt"]').val(primer3.defaultValue.primerTmOpt);
            var length=$('.sequence-block').val().replace(/[\r\n]/g, "").length;
            $('input[name="productSizeMin"]').val(parseInt(length*0.5));
            $('input[name="productSizeMax"]').val(length);
            $('input[name="primerRStart"]').val('');
            $('input[name="primerRLength"]').val('');
            $('input[name="primerFStart"]').val('');
            $('input[name="primerFLength"]').val('');
        });


        $('.primer3-submit-btn').on('click', function () {
            if(!primer3.checkPrimer3()){
                return;
            }
            if(primer3.seqLength==0){
                primer3.errorTip($('.sequence-block'),"未输入序列");
                return;
            }else{
                if(!primer3.checkSeq($('.sequence-block').val().replace(/[\r\n]/g, "").toUpperCase())){

                    primer3.errorTip($('.sequence-block'),"序列错误");
                    return;
                }
                var obj=primer3.checkProductSizeAndPrimer();

                if(!obj){
                    return;
                }

                var primerSizeMin=$('input[name="primerSizeMin"]').val();
                var primerSizeMax=$('input[name="primerSizeMax"]').val();
                var primerSizeOpt=$('input[name="primerSizeOpt"]').val();
                var primerGCMin=$('input[name="primerGCMin"]').val();
                var primerGCMax=$('input[name="primerGCMax"]').val();
                var primerGCOpt=$('input[name="primerGCOpt"]').val();
                var primerTmMin=$('input[name="primerTmMin"]').val();
                var primerTmMax=$('input[name="primerTmMax"]').val();
                var primerTmOpt=$('input[name="primerTmOpt"]').val();
                var data={
                    primerSizeMin:primerSizeMin==''?primer3.defaultValue.primerSizeMin:primerSizeMin,
                    primerSizeMax:primerSizeMax==''?primer3.defaultValue.primerSizeMax:primerSizeMax,
                    primerSizeOpt:primerSizeOpt==''?primer3.defaultValue.primerSizeOpt:primerSizeOpt,
                    primerGCMin:primerGCMin==''?primer3.defaultValue.primerGCMin:primerGCMin,
                    primerGCMax:primerGCMax==''?primer3.defaultValue.primerGCMax:primerGCMax,
                    primerGCOpt:primerGCOpt==''?primer3.defaultValue.primerGCOpt:primerGCOpt,
                    primerTmMin:primerTmMin==''?primer3.defaultValue.primerTmMin:primerTmMin,
                    primerTmMax:primerTmMax==''?primer3.defaultValue.primerTmMax:primerTmMax,
                    primerTmOpt:primerTmOpt==''?primer3.defaultValue.primerTmOpt:primerTmOpt,
                    productSizeMin:obj['productSizeMin'],
                    productSizeMax:obj['productSizeMax'],
                    /*primerFStart:obj['primerFStart'],
                    primerFLength:obj['primerFLength'],
                    primerRStart:obj['primerRStart'],
                    primerRLength:obj['primerRLength'],*/
                    sequence:$('.sequence-block').val().replace(/[\r\n]/g, "").toUpperCase(),
                    seqLength:primer3.seqLength,
                    primerPairOKRegionList:obj['primerFStart']+','+obj['primerFLength']+','+obj['primerRStart']+','+obj['primerRLength'],/**/
                };

                var promise = primer3.utils.sendAjaxRequest("POST", ctxRoot+"/primer/getPrimer", data);
                promise.then(
                    function (result) {
                        console.log(result);
                        //result.param=data;
                        result=JSON.stringify(result);
                        localStorage.setItem('primer3List',result);
                        localStorage.setItem('param',JSON.stringify(data));
                        $('.error-message').hide();
                        $('.peimer3-input-item>input').removeClass('red-border');
                        //window.open(ctxRoot+"/primer3out");
                        var a = $('<a href="${ctxroot}/primer3out" class="to-primer3-out" target="_blank">Welcome</a>').get(0);
                        var e = document.createEvent('MouseEvents');
                        e.initEvent('click', true, true);
                        a.dispatchEvent(e);
                        /*var form = document.createElement('form');
                        form.action = ctxRoot+"/primer3out";
                        form.target = '_blank';
                        form.method = 'GET';
                        document.body.appendChild(form);
                        form.submit();*/
                    },
                    function (error) {
                        console.log(error);
                        alert("序列输入错误！(￣ε(#￣)")
                    }
                );
            }
        });
    });



</script>
</body>
</html>