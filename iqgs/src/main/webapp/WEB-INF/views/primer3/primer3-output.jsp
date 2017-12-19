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
    <title>SNP INDEL database</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <%--<link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/primer3.css">--%>
    <%--<link rel="stylesheet" href="${ctxStatic}/css/primer3-input.css">--%>
    <link rel="stylesheet" href="${ctxStatic}/css/primer3-output.css">
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
    <style>

    </style>
</head>
<body>
<iqgs:iqgs-header />
<!--header-->
<div class="container primer3-content js-nav-ac">
    <%--<%@ include file="/WEB-INF/views/include/sidebar.jsp" %>--%>
    <div class="peimer3-main">
        <div class="primer3-result-title">
            <div class="result-icon">
                <%--<img src="${ctxroot}/static/images/primer3/resultIcon.png" style="vertical-align:text-bottom"/>--%>
                <div class="result-title-icon1"></div>
                <div class="blue-result">结果</div>
                <%--<img src="${ctxroot}/static/images/primer3/resultIcon2.png" style="vertical-align:text-bottom"/>--%>
                <div class="result-title-icon2"></div>
                <div class="primer3-conditions">搜索条件：<span></span></div>
            </div>
            <div class="clear-fix"></div>
        </div>
        <div class="primer3-table">
            <table ><%--cellspacing="50%" cellpadding="80"--%>
                <thead>
                    <tr>
                        <td></td>
                        <td>Position</td>
                        <td>Length</td>
                        <td>Tm</td>
                        <td>GC%</td>
                        <td>any(Self Cornplementarity)</td>
                        <td>3'(Self Complementarity)</td>
                        <td>Hairpin</td>
                        <td>Sequence(5'-3')</td>
                        <td>引物检测</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Primer F</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddddddddddddddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                    </tr>
                    <tr>
                        <td>Primer R</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddddddddddddddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                        <td>dddddd</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="primer3-designed">
            <div class="primer3-designed-title">
                Primer designed in sequence
            </div>
            <div class="primer3-designed-content">
                <div class="primer3-designed-content-left">
                    <div class="primer3-designed-item">
                        Primer F : <span>ATATATATATATATATATATAT</span>
                    </div>
                    <div class="primer3-designed-item">
                        Sequence Size : <span>111110</span>
                    </div>

                </div>
                <div class="primer3-designed-content-right">
                    <div class="primer3-designed-item">
                        Primer R : <span>ATATATATATATATATATATAT</span>
                    </div>
                    <div class="primer3-designed-item">
                        lnclude Region Sequence Size : <span>2222220</span>
                    </div>
                </div>
                <div class="clear-fix"></div>
            </div>
        </div>
        <div class="sequence-list">
            <div class="sequence-list-cover">
                <div class="sequence-list-cover-left"></div>
                <div class="sequence-list-cover-right"></div>
                <div class="clear-fix"></div>
            </div>
            <ul>
                <li class="sequence-list-li-one">
                    <div class="line-number">1</div>
                    <div class="sequence-content">ATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATATTATATATATATATAT</div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">2</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">3</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
                <li>
                    <div class="line-number">4</div>
                    <div class="sequence-content"></div>
                    <div class="clear-fix"></div>
                </li>
            </ul>
        </div>
    </div>
</div>
<!--footer-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>
    var ctxRoot = '${ctxroot}';
    var primer3 = {
        errorMessageMap:{
            primerSizeMin:"Primer size不能低于15",
            primerSizeMax:"Primer size不能高于30",
            primerTmMin:"Primer Tm不能低于55",
            primerTmMax:"Primer Tm不能高于80",
            primerGCMin:"Primer GC不能低于20",
            primerGCMax:"Primer GC不能高于80",
            productSizeMin:"Product size不能小于0",
            productSizeMax:"Product size不能大于序列长度",
            errorScope: "Max必须大于Min"

        },
        defaultValue: {
            primerSizeMin: 18,
            primerSizeMax: 23,
            primerTmMin: 57,
            primerTmMax: 62,
            primerGCMin: 30,
            primerGCMax: 70
            /*primerFMin:0,
            primerFMax:0.1,*/
        },
        valueScope: {
            primerSizeMin: 15,
            primerSizeMax: 30,
            primerTmMin: 55,
            primerTmMax: 80,
            primerGCMin: 20,
            primerGCMax: 80,
            productSizeMin: 0.8,
            productSizeMax: 1
            /*primerFMin:0,
            primerFMax:0.5,*/
        },
        changeLengthAndProductSize:function () {
            //clearTimeout(primer3.time);
            //primer3.time = setTimeout("primer3.changeLength()", 500);
            primer3.changeLength();
            var length=$('.sequence-block').val().length;
            $('input[name="productSizeMin"]').val(parseInt(length*0.8));
            $('input[name="productSizeMax"]').val(length);
        },
        changeLength: function () {
            var val = $('.sequence-block').val();
            var seqLength = val.length;
            $('.length-value').html(seqLength);
        },
        errorTip:function (ele,key) {
            if(ele!=null){
                $(ele).addClass('red-border');
            }
            alert(primer3.errorMessageMap[key]);
        },
        checkPrimer3:function () {
            var primerSizeMin=parseInt($('input[name="primerSizeMin"]').val());
            var primerSizeMax=parseInt($('input[name="primerSizeMax"]').val());
            var primerGCMin=parseInt($('input[name="primerGCMin"]').val());
            var primerGCMax=parseInt($('input[name="primerGCMax"]').val());
            var primerTmMin=parseInt($('input[name="primerTmMin"]').val());
            var primerTmMax=parseInt($('input[name="primerTmMax"]').val());
            if(primerSizeMin>=primerSizeMax||primerGCMin>=primerGCMax||primerTmMin>=primerTmMax){
                primer3.errorTip(null,'errorScope');
                return false;
            }
            if(primerSizeMin<15){
                primer3.errorTip($('input[name="primerSizeMin"]'),'primerSizeMin');
                return false;
            }
            if(primerSizeMax>30){
                primer3.errorTip($('input[name="primerSizeMax"]'),'primerSizeMax');
                return false;
            }
            if(primerGCMin<20){
                primer3.errorTip($('input[name="primerGCMin"]'),'primerGCMin');
                return false;
            }
            if(primerGCMax>80){
                primer3.errorTip($('input[name="primerGCMax"]'),'primerGCMax');
                return false;
            }
            if(primerTmMin<55){
                primer3.errorTip($('input[name="primerTmMin"]'),'primerTmMin');
                return false;
            }
            if(primerTmMax>80){
                primer3.errorTip($('input[name="primerTmMax"]'),'primerTmMax');
                return false;
            }
            return true;
        },
        checkProductSize:function () {
            var length=$('.length-value').text();
            var productSizeMin=$('.primer3-input  .product-size>input[name="productSizeMin"]').val();
            var productSizeMax=$('.primer3-input  .product-size>input[name="productSizeMax"]').val();
            if(productSizeMin==''){
                productSizeMin=parseInt(parseInt(length)*0.8);
                console.log('productSizeMin: '+productSizeMin)
            }else {
                productSizeMin=parseInt(productSizeMin);
                if(productSizeMin<0){
                    primer3.errorTip(null,'productSizeMin');
                    return false;
                }
                console.log('productSizeMin: '+productSizeMin)
            }
            if(productSizeMax==''){
                productSizeMax=parseInt(length);
                console.log('productSizeMax: '+productSizeMax)
            }else {
                productSizeMax=parseInt(productSizeMax);
                if(productSizeMax>length){
                    primer3.errorTip(null,'productSizeMax');
                    return false;
                }
                console.log('productSizeMax: '+productSizeMax)
            }
            if(productSizeMin>=productSizeMax){
                primer3.errorTip(null,'errorScope');
                return;
            }
            return [productSizeMin,productSizeMax];
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
                            //dataType: "json",
                            contentType: "application/json,charset=UTF-8;",
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
        //记录用户输入序列的长度
        $('.sequence-block').on({
            keyup:primer3.changeLengthAndProductSize,
            change:primer3.changeLengthAndProductSize
        })

        //重置用户输入
        $('.reset-btn').click(function () {
            $('.primer3-input  .primer-size>input[name="primerSizeMin"]').val(primer3.defaultValue.primerSizeMin);
            $('.primer3-input  .primer-size>input[name="primerSizeMax"]').val(primer3.defaultValue.primerSizeMax);
            $('.primer3-input  .primer-GC>input[name="primerGCMin"]').val(primer3.defaultValue.primerGCMin);
            $('.primer3-input  .primer-GC>input[name="primerGCMax"]').val(primer3.defaultValue.primerGCMax);
            $('.primer3-input  .primer-Tm>input[name="primerTmMin"]').val(primer3.defaultValue.primerTmMin);
            $('.primer3-input  .primer-Tm>input[name="primerTmMax"]').val(primer3.defaultValue.primerTmMax);
            var length=$('.sequence-block').val().length;
            if(length>0){
                $('input[name="productSizeMin"]').val(parseInt(length*0.8));
                $('input[name="productSizeMax"]').val(length);
            }
        });

        $('.primer3-submit-btn').on('click', function () {
            var flag=primer3.checkPrimer3();
            if(!flag){
                return;
            }
            var productSizes=primer3.checkProductSize();
            if(!productSizes){
                return;
            }
            var productSizeMin=productSizes[0];
            var productSizeMax=productSizes[1];

            var primerSizeMin=$('input[name="primerSizeMin"]').val();
            var primerSizeMax=$('input[name="primerSizeMax"]').val();
            var primerGCMin=$('input[name="primerGCMin"]').val();
            var primerGCMax=$('input[name="primerGCMax"]').val();
            var primerTmMin=$('input[name="primerTmMin"]').val();
            var primerTmMax=$('input[name="primerTmMax"]').val();
            var data={
                primerSizeMin:primerSizeMin==''?primer3.defaultValue.primerSizeMin:primerSizeMin,
                primerSizeMax:primerSizeMax==''?primer3.defaultValue.primerSizeMax:primerSizeMax,
                primerGCMin:primerGCMin==''?primer3.defaultValue.primerGCMin:primerGCMin,
                primerGCMax:primerGCMax==''?primer3.defaultValue.primerGCMax:primerGCMax,
                primerTmMin:primerTmMin==''?primer3.defaultValue.primerTmMin:primerTmMin,
                primerTmMax:primerTmMax==''?primer3.defaultValue.primerTmMax:primerTmMax,
                productSizeMin:productSizeMin,
                productSizeMax:productSizeMax,
                sequence:$('.sequence-block').val().toUpperCase()
            };
            console.log(data)
            var promise = primer3.utils.sendAjaxRequest("POST", ctxRoot+"/primer/getPrimer", data);
            promise.then(
                function (result) {
                    console.log(result);
                },
                function (error) {
                    console.log(error);
                }
            );
        });
    })


</script>
<%--<script src="${ctxStatic}/js/primer3.js"></script>--%>
</body>
</html>