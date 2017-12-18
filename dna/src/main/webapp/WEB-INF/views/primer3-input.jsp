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
<dna:dna-header/>
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
                        <input type="number" class="input-content input-item-common" name="primerSizeMin" value="18"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerSizeMax" value="23"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="peimer3-input-item primer-GC">
                        <div class="peimer3-input-item-title input-item-common">Primer GC(%)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="primerGCMin" value="30"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerGCMax" value="70"/>
                        <div class="clear-fix"></div>
                    </div>
                    <%--<div class="peimer3-input-item">&lt;%&ndash;&ndash;%&gt;
                        <div class="peimer3-input-item-title input-item-common">Primer F(bp)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input class="input-content input-item-common"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input class="input-content input-item-common"/>
                        <div class="clear-fix"></div>
                    </div>--%>
                </div>
                <div class="primer3-input-right">
                    <div class="peimer3-input-item primer-Tm"><%--Primer3 Tm--%>
                        <div class="peimer3-input-item-title input-item-common">Primer Tm(℃)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="primerTmMin" value="57"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" class="input-content input-item-common" name="primerTmMax" value="62"/>
                        <div class="clear-fix"></div>
                    </div>
                    <div class="peimer3-input-item product-size"><%--Product Size--%>
                        <div class="peimer3-input-item-title input-item-common">Product Size(bp)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="number" class="input-content input-item-common" name="productSizeMin"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="number" type="text" class="input-content input-item-common" name="productSizeMax"/>
                        <div class="clear-fix"></div>
                    </div>
                    <%--<div class="peimer3-input-item">&lt;%&ndash;Primer3 R&ndash;%&gt;
                        <div class="peimer3-input-item-title input-item-common">Primer R(bp)</div>
                        <div class="input-min input-item-common">Min</div>
                        <input type="text" name="primer-r-min" class="input-content input-item-common"/>
                        <div class="input-item-common">-</div>
                        <div class="input-max input-item-common">Max</div>
                        <input type="text" name="primer-r-max" class="input-content input-item-common"/>
                        <div class="clear-fix"></div>
                    </div>--%>
                    <div class="primer3-submit-btn">Search</div>
                </div>
                <div class="clear-fix"></div>
            </div>
        </div>
    </div>

    <%--<div class="contant page-circle" id="systreeContainer">
        <div class="box-shadow">
            <div class="item-header">
                <div class="icon-left"><img src="${ctxStatic}/images/tag-name.png"> 树形图</div>
            </div>
            <div class="tab-item">
                &lt;%&ndash;<ul class="item">&ndash;%&gt;
                    &lt;%&ndash;<li class="item-ac">标签1</li>&ndash;%&gt;
                    &lt;%&ndash;<!--<li>标签2</li>-->&ndash;%&gt;
                &lt;%&ndash;</ul>&ndash;%&gt;
                <div class="tab">
                    <div class="tab-txt tab-txt-ac" style="overflow:hidden;">
                        <img src="${ctxStatic}/images/dnatree.png">
                        <p id="populationInfos"><a href="${ctxroot}/dna/populationInfos" style="color:#fff;" target="_blank">群体信息</a></p>
                            &lt;%&ndash;<%@ include file="/WEB-INF/views/include/dna.jsp" %>&ndash;%&gt;
                            &lt;%&ndash;<jsp:include flush="true" page="/WEB-INF/views/include/dna.jsp"/>&ndash;%&gt;
                    </div>
                    <div class="tab-txt tree-explain">
                        <div class="txt-title explain-title">
                            <img src="${ctxStatic}/images/tree-img.png">
                            树形图说明
                        </div>
                        <div class="txt-content explain-text">
                            上图利用大豆群体近1000个样本的SNP信息，使用structure软件计算种群内的亚群结构，将群体分为10个亚群，其中处于同一亚群内的不同个体亲缘关系较高，而亚群与亚群之间则亲缘关系稍远，在图中相同颜色的节点表示来自同一亚群；同时，采用邻接法（neighbor-joining）计算种群之间的距离，构建系统进化树（phylogenetic tree，又称evolutionary tree）展示了本数据库中大豆样本之间的进化历程和亲缘关系。
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="database-overview box-shadow">
            <div class="explain-title">
                <img src="${ctxStatic}/images/explain.png">数据库概况
            </div>
            <div class="explain-text">
                SNP/INDEL数据库提供了大豆相关的21,657,613条SNPs及2,329,103条InDels变异信息。这些变异信息由收集NCBI公共网站上近1000个大豆重测序数据，与Glycine max Wm82.a2.v1基因组进行比对后获得。该数据库收录了丰富的样本变异信息，用户可以根据属性自定义组合群体，查询SNP、INDEL变异在不同群体中的出现频率。同时，亦可以根据基因及感兴趣位点对特定区域上的变异位点进行查看。通过该变异数据库，快速、直观地了解群体中变异发生比例，辅助群体遗传变异研究。
            </div>
        </div>
    </div>--%>

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
        $('.sequence-block').keyup(function () {
            //clearTimeout(primer3.time);
            //primer3.time = setTimeout("primer3.changeLength()", 500);
            primer3.changeLength();
        });

        //重置用户输入
        $('.reset-btn').click(function () {
            $('.primer3-input  .primer-size>input[name="primerSizeMin"]').val(primer3.defaultValue.primerSizeMin);
            $('.primer3-input  .primer-size>input[name="primerSizeMax"]').val(primer3.defaultValue.primerSizeMax);
            $('.primer3-input  .primer-GC>input[name="primerGCMin"]').val(primer3.defaultValue.primerGCMin);
            $('.primer3-input  .primer-GC>input[name="primerGCMax"]').val(primer3.defaultValue.primerGCMax);
            $('.primer3-input  .primer-Tm>input[name="primerTmMin"]').val(primer3.defaultValue.primerTmMin);
            $('.primer3-input  .primer-Tm>input[name="primerTmMax"]').val(primer3.defaultValue.primerTmMax);
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
            var promise = primer3.utils.sendAjaxRequest("GET", "http://192.168.0.123:8080/iqgs/primer/getPrimer", data);
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