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
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/addTags.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
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
    <script src="${ctxStatic}/js/d3.js"></script>
    <script src="${ctxStatic}/js/svg-pan-zoom.js"></script>
    <style>
        .total-page-count {display: none!important;}
        /* master分支中无群体信息 */
        #populationInfos{
            padding:8px 20px;
            background:#5D8CE6;
            color:#fff;
            width:68px;
            float:right;
            cursor:pointer;
            font-size:16px;
            margin-bottom:16px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<dna:dna-header />
<!--header-->
<div class="container snp-content js-nav-ac">
    <%@ include file="/WEB-INF/views/include/sidebar.jsp" %>

    <div class="contant page-tables" style="display: none;">
        <div class="box-shadow resulting">
            <div class="item-header">
                <div class="icon-left">
                    <img src="${ctxStatic}/images/result.png">结果
                    <span>搜索条件: <span class="js-search-desc"></span></span>
                    <i>></i>
                </div>
                <div class="icon-right">
                    <p class="page-num-tab-snp">共<span class="total-page-count-snp">0</span>条结果</p>
                    <p class="page-num-tab-indel" style="display: none;">共<span class="total-page-count-indel">0</span>条结果</p>
                </div>
            </div>
            <%--基因ID 选择区 begin--%>
            <div id="GlyIds">
                    <ul>
                    </ul>
            </div>
            <%--基因ID 选择区 end--%>
            <div class="tab-item">
                <ul class="item">
                    <li class="item-ac">SNPs</li>
                    <li class="geneIndels">INDELs</li>
                </ul>
                <%--基因结构图 begin--%>
                <div id="geneConstruction">
                    <div class="geneLegend">

                        <p>
                            <span class="colorBlock" style="background: #ffb902;"></span>
                            <span class="legendCnt">3'UTR</span>
                        </p>
                        <p style="width:52px;">
                            <span class="colorBlock" style="background: #0099bb;"></span>
                            <span class="legendCnt">CDS</span>
                        </p>
                        <p>
                            <span class="colorBlock" style="background: #f76919;"></span>
                            <span class="legendCnt" style="margin-bottom:5px;">5'UTR</span>
                        </p>
                        <p style="width:183px;" class="snpTipE">
                            <span class="colorBlock" style="background: #02ccb1;"></span>
                            <span class="legendCnt" style="width:155px;">nonsynonymouse SNY</span>
                        </p>
                        <p style="width:208px;" class="indelTipE">
                            <span class="colorBlock" style="background: #0ccdf1;"></span>
                            <span class="legendCnt" style="width:180px;">Exonic_frameshift deletion</span>
                        </p>
                        <p style="width:208px;" class="indelTipE">
                            <span class="colorBlock" style="background:#df39e0;"></span>
                            <span class="legendCnt" style="width:180px;">Exonic_frameshift insertion</span>
                        </p>
                    </div>
                    <div class="geneError">
                        无数据
                    </div>
                    <div id="constructorPanel" onmousewheel="return false;">
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg">

                        </svg>
                    </div>
                    <div id="constructorPanel2" class="hiddeCurr" onmousewheel="return false;">
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg">

                        </svg>
                    </div>
                    <!--无数据时返回的页面-->
                    <%--<div id="errorShow">--%>
                        <%--&lt;%&ndash;<img src="${ctxStatic}" alt="errorLoge">&ndash;%&gt;--%>
                        <%--<p>暂无数据</p>--%>
                    <%--</div>--%>
                </div>
                <%--基因结构图 end--%>

                <!-- 导出数据 页面更换位置snp begin-->
                <div class="table-item box-shadow snp-checkbox" style="display: none">
                    <div class="checkbox-item">
                        <div class="che-list ">
                            <span class="tab-title">表格内容:</span>
                            <dl class="table_header_setting js-table-header-setting-snp">
                                <dd><label for="snpid" class="checkbox-ac" data-col-name="SNPID"><span id="snpid" data-value="snpid"></span>SNP ID</label></dd>
                                <dd><label for="consequenceType" class="checkbox-ac" data-col-name="consequenceType"><span id="consequenceType" data-value="consequenceType"></span>Consequence Type</label></dd>
                                <dd><label for="snpchromosome" class="checkbox-ac" data-col-name="chromosome"><span id="snpchromosome" data-value="snpchromosome"></span>Chromosome</label></dd>
                                <dd><label for="position" class="checkbox-ac" data-col-name="position"><span id="position" data-value="position"></span>Position</label></dd>
                                <dd><label for="snpreference" class="checkbox-ac" data-col-name="reference"><span id="snpreference" data-value="snpreference"></span>Reference</label></dd>
                                <dd><label for="majorAllele" class="checkbox-ac" data-col-name="majorAllele"><span id="majorAllele" data-value="majorAllele"></span>Major Allele</label></dd>
                                <dd><label for="minorAllele" class="checkbox-ac" data-col-name="minorAllele"><span id="minorAllele" data-value="minorAllele"></span>Minor Allele</label></dd>
                                <dd><label for="fmajorAllele" class="checkbox-ac" data-col-name="frequencyOfMajorAllele"><span id="fmajorAllele" data-value="fmajorAllele"></span>Frequency of Major Allele</label></dd>
                                <dd><label for="genoType" class="checkbox-ac" data-col-name="genoType"><span id="exGenoType" data-value="GenoType"></span>GenoType</label></dd>
                                <span></span>
                                <%--<dd><label for="fmajorAllelein" class="checkbox-ac"><span id="fmajorAllelein" data-value="fmajorAllelein"></span>Frequency of Major Allele in</label></dd>--%>
                            </dl>
                        </div>
                    </div>
                    <div class="export-data">
                        <p class="btn-export-set">
                            <button type="button" class="btn btn-export js-export">导出数据</button>
                        </p>
                    </div>
                    <div class="choose-default">
                        <div class="btn-default">
                            <label class="js-choose-all "><span id="js-choose-all"></span>全选</label>
                            <%--<label class="js-default js-default-ac"><span id="js-default" ></span>默认</label>--%>
                        </div>
                        <div class="btn-group" style="display: block;">
                            <button type="button" class="btn-fill btn-confirm js-snp-setting-btn">确认</button>
                            <button type="button" class="btn-chooseAll js-clear-btn" id="snp-clear-all">清空</button>
                            <button type="button" class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                        </div>
                    </div>
                </div>
                <!-- 导出数据 页面更换位置snp end-->
                <!-- 导出数据 页面更换位置indel begin-->
                <div class="table-item box-shadow indels-checkbox" style="display: none">
                    <div class="checkbox-item">
                        <div class="che-list">
                            <span class="tab-title">表格内容:</span>
                            <dl class="table_header_setting js-table-header-setting-indel">
                                <dd><label for="indels" class="checkbox-ac" data-col-name="INDELID"><span id="indels" data-value="indels"></span>INDEL ID</label></dd>
                                <dd><label for="iconsequenceType" class="checkbox-ac" data-col-name="consequenceType"><span id="iconsequenceType" data-value="consequenceType"></span>Consequence Type</label></dd>
                                <dd><label for="indelchromosome" class="checkbox-ac" data-col-name="chromosome"><span id="indelchromosome" data-value="snpchromosome"></span>Chromosome</label></dd>
                                <dd><label for="iposition" class="checkbox-ac" data-col-name="position"><span id="iposition" data-value="position"></span>Position</label></dd>
                                <dd><label for="indelreference" class="checkbox-ac" data-col-name="reference"><span id="indelreference" data-value="snpreference"></span>Reference</label></dd>
                                <dd><label for="imajorAllele" class="checkbox-ac" data-col-name="majorAllele"><span id="imajorAllele" data-value="majorAllele"></span>Major Allele</label></dd>
                                <dd><label for="iminorAllele" class="checkbox-ac" data-col-name="minorAllele"><span id="iminorAllele" data-value="minorAllele"></span>Minor Allele</label></dd>
                                <dd><label for="ifmajorAllele" class="checkbox-ac" data-col-name="frequencyOfMajorAllele"><span id="ifmajorAllele" data-value="fmajorAllele"></span>Frequency of Major Allele</label></dd>
                                <span></span>
                            </dl>
                        </div>
                    </div>
                    <div class="export-data">
                        <p class="btn-export-set">
                            <button type="button" class="btn btn-export js-export">导出数据</button>
                        </p>
                    </div>
                    <div class="choose-default">
                        <div class="btn-default">
                            <label class="js-choose-all"><span></span>全选</label>
                            <%--<label class="js-default js-default-ac"><span></span>默认</label>--%>
                        </div>
                        <div class="btn-group" style="display: block;">
                            <button type="button" class="btn-fill btn-confirm js-indel-setting-btn">确认</button>
                            <button type="button" class="btn-chooseAll js-clear-btn" id="indels-clear-all">清空</button>
                            <button type="button" class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                        </div>
                    </div>
                </div>
                <!-- 导出数据 页面更换位置indel end-->
                <div class="tab">
                    <%--snp 表格--%>
                    <div class="tab-txt tab-txt-ac js-snp-tab">
                        <div class="export-data">
                            <p class="btn-export-set">
                                <button type="button" class="btn snp-set-up">表格设置</button>
                                <button type="button" class="btn btn-export js-export">导出数据</button>
                            </p>
                        </div>
                        <div id="mask-test">
                            <div class="genes-tab tab-txt-snps" style="height: auto;">
                                <table class="js-snp-table" style="display:table;">
                                    <thead>
                                        <tr>
                                            <%--<td class="t_snpid">SNP ID</td>--%>
                                            <td class="param t_consequenceType">Consequence Type
                                                <img src="${ctxStatic}/images/down.png">
                                                <input type="hidden" class="js-consequence-type">
                                                <div class="input-component ">
                                                    <ul class="consequence-type ">
                                                        <li data-value="all">ALL</li>
                                                        <li data-type="type" data-value="downstream">Downstream11</li>
                                                        <li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>
                                                        <li data-type="effect" data-value="nonsynonymous SNV">Exonic_nonsynonymous SNV</li>
                                                        <li data-type="effect" data-value="stopgain">Exonic_stopgain</li>
                                                        <li data-type="effect" data-value="stoploss">Exonic_stoploss</li>
                                                        <li data-type="effect" data-value="synonymous SNV">Exonic_synonymous SNV</li>
                                                        <li data-type="type" data-value="intergenic">Intergenic</li>
                                                        <li data-type="type" data-value="intronic">Intronic</li>
                                                        <li data-type="type" data-value="splicing">Splicing</li>
                                                        <li data-type="type" data-value="upstream">Upstream</li>
                                                        <li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>
                                                        <li data-type="type" data-value="UTR3">3&acute;UTR</li>
                                                        <li data-type="type" data-value="UTR5">5&acute;UTR</li>
                                                        <li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>
                                                    </ul>
                                                </div>
                                            </td>
                                            <td class="param t_snpchromosome">Chromosome</td>
                                            <td class="param t_position">Position</td>
                                            <td class="param t_snpreference">Reference</td>
                                            <td class="param t_majorAllele">Major Allele</td>
                                            <td class="param t_minorAllele">Minor Allele</td>
                                            <td class="param t_fmajorAllele">
                                                <select class="f-ma">
                                                    <option value="major">Frequency of Major Allele</option>
                                                    <option value="minor">Frequency of Minor Allele</option>
                                                </select>
                                            </td>

                                        </tr>
                                    </thead>
                                    <tbody id="tableBody">
                                    </tbody>
                                </table>
                                <form id="exportForm" action="" method="get">
                                    <input id="search1" class="search-type" name="type" type="hidden">
                                    <input id="search2" name="keywords" type="hidden">
                                    <input id="search3" name="condition" type="hidden">
                                    <input id="search4" name="choices" type="hidden">
                                    <input class="total" name="total" type="hidden"/>
                                </form>
                            </div>
                            <%--<div id="tableErrorShow">--%>
                                    <%--<p class="photos">--%>
                                        <%--暂无数据--%>
                                    <%--</p>--%>
                            <%--</div>--%>
                        </div>
                        <div class="checkbox-item-tab" id="snp-paginate">
                            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                        </div>
                    </div>
                    <%--indel 表格、--%>
                    <div class="tab-txt js-indel-tab">
                        <div class="export-data">
                            <p class="btn-export-set">
                                <button type="button" class="btn indels-set-up">表格设置</button>
                                <button type="button" class="btn btn-export js-export">导出数据</button>
                            </p>
                        </div>
                        <div id="mask-test2">
                            <div class="tab-txt-indels genes-tab">
                            <table class="js-indel-table">
                                <thead>
                                    <tr>
                                        <td class="param t_indels">INDEL ID</td>
                                        <td class="param t_consequenceType">Consequence Type
                                            <img src="${ctxStatic}/images/down.png">
                                            <input type="hidden" class="js-consequence-type">
                                            <div class="input-component ">
                                                <ul class="consequence-type ">
                                                    <li data-value="all">ALL</li>
                                                    <li data-type="type" data-value="downstream">Downstream</li>
                                                    <li data-type="type" data-value="exonic;splicing">Exonic;Splicing</li>
                                                    <li data-type="effect" data-value="frameshift deletion">Exonic_frameshift deletion</li>
                                                    <li data-type="effect" data-value="frameshift insertion">Exonic_frameshift insertion</li>
                                                    <li data-type="effect" data-value="nonframeshift deletion">Exonic_nonframeshift deletion</li>
                                                    <li data-type="effect" data-value="nonframeshift insertion">Exonic_nonframeshift insertion</li>
                                                    <li data-type="effect" data-value="stopgain">Exonic_stopgain</li>
                                                    <li data-type="effect" data-value="stoploss">Exonic_stoploss</li>
                                                    <li data-type="type" data-value="intergenic">Intergenic</li>
                                                    <li data-type="type" data-value="intronic">Intronic</li>
                                                    <li data-type="type" data-value="splicing">Splicing</li>
                                                    <li data-type="type" data-value="upstream">Upstream</li>
                                                    <li data-type="type" data-value="upstream;downstream">Upstream;Downstream</li>
                                                    <li data-type="type" data-value="UTR3">3&acute;UTR</li>
                                                    <li data-type="type" data-value="UTR5">5&acute;UTR</li>
                                                    <li data-type="type" data-value="UTR5;UTR3">5&acute;UTR;3&acute;UTR</li>
                                                </ul>
                                            </div>
                                        </td>
                                        <td class="param t_snpchromosome">Chromosome</td>
                                        <td class="param t_position">Position</td>
                                        <td class="param t_snpreference">Reference</td>
                                        <td class="param t_majorAllele">Major Allele</td>
                                        <td class="param t_minorAllele">Minor Allele</td>
                                        <td class="param t_fmajorAllele">
                                            <select class="f-ma">
                                                <option value="major">Frequency of Major Allele</option>
                                                <option value="minor">Frequency of Minor Allele</option>
                                            </select>
                                        </td>
                                    </tr>
                                </thead>
                                <tbody  id="tableBody2">
                                </tbody>
                            </table>
                        </div>
                        </div>
                        <div class="checkbox-item-tab" id="indel-paginate">
                            <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="contant page-circle" id="systreeContainer">
        <div class="box-shadow">
            <div class="item-header">
                <div class="icon-left"><img src="${ctxStatic}/images/tag-name.png"> 树形图</div>
            </div>
            <div class="tab-item">
                <%--<ul class="item">--%>
                    <%--<li class="item-ac">标签1</li>--%>
                    <%--<!--<li>标签2</li>-->--%>
                <%--</ul>--%>
                <div class="tab">
                    <div class="tab-txt tab-txt-ac" style="overflow:hidden;">
                        <img src="${ctxStatic}/images/dnatree.png">
                        <p id="populationInfos"><a href="${ctxroot}/dna/populationInfos" style="color:#fff;" target="_blank">群体信息</a></p>
                            <%--<%@ include file="/WEB-INF/views/include/dna.jsp" %>--%>
                            <%--<jsp:include flush="true" page="/WEB-INF/views/include/dna.jsp"/>--%>
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
    </div>

    <form id="exportRegionForm" action="${ctxroot}/dna/dataExport" method="get">
        <input class="model" name="model" type="hidden" value="REGION"/>
        <input class="chromosome" name="chromosome" type="hidden" value=""/>
        <input class="start" name="start" type="hidden" value=""/>
        <input class="end" name="end" type="hidden" value=""/>
        <input class="ctype" name="ctype" type="hidden" value=""/>
        <input class="type" name="type" type="hidden" value=""/>
        <input class="choices" name="choices" type="hidden" value=""/>
        <input class="group" name="group" type="hidden" value=""/>
        <input class="total" name="total" type="hidden" value=""/>
    </form>

    <form id="exportGeneForm" action="${ctxroot}/dna/dataExport" method="get">
        <input class="model" name="model" type="hidden" value="GENE"/>
        <input class="gene" name="gene" type="hidden" value=""/>
        <input class="upstream" name="upstream" type="hidden" value=""/>
        <input class="downstream" name="downstream" type="hidden" value=""/>
        <input class="ctype" name="ctype" type="hidden" value=""/>
        <input class="type" name="type" type="hidden" value=""/>
        <input class="choices" name="choices" type="hidden" value=""/>
        <input class="group" name="group" type="hidden" value=""/>
        <input class="total" name="total" type="hidden" value=""/>
     </form>
</div>
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<%--// 新增基因结构信息 弹出框--%>
<div class="genesInfo" style="display: none">
    <div class="genesInfo-head">
        <p>基因<span class="js-gene-head-name"></span>信息</p>
        <a href="#">x</a>
    </div>
    <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
</div>
<%--// 新增基因结构信息 弹出框--%>

<script>
    var ctxRoot = '${ctxroot}';
</script>
<script src="${ctxStatic}/js/dna.js"></script>
<script src="${ctxStatic}/js/addTags.js"></script>


</body>
</html>