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
    <title>IQGS details</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/d3.js"></script>
    <script src="${ctxStatic}/js/clipboard.min.js"></script>
    <script src="${ctxStatic}/js/layer/layer.js"></script>
    <style>
        .copy{width:120px;}

        .copy_p{  width: 573px;
            height: 210px;
            overflow-y: auto;
            text-align: left;
        }
        .upstream2k,.peptide,.gDNA{    width: 573px;
            word-break: break-all;
        }

        .foo_div {
            position: relative;
        }

        #input,#inputPeptide {
            position: absolute;
            top: 0;
            left: 0;
            opacity: 0;
            z-index: -10;
        }

        .order_bg{
            background:#5c8ce6;
            color:#fff;
        }
        .refseq{
            overflow: hidden;
            margin-bottom: 28px;
        }
        .refseq_ul{
            overflow: hidden;
            float: right;
            border: 1px solid #dedede;
            padding: 10px;
        }
        .refseq_ul li{
            float: left;
            display: flex;
            padding-right: 16px;
        }
        .refseq_ul li:last-child{
            padding-right: 0px;
        }
        .refseq_ul li i{
            width:12px; height: 12px; border-radius: 50%; display: block;    display: flex;
            position: relative;
            top:4px;
            margin-right: 4px;
        }
        .refseq_i1{background: #abbac3;
        }
        .refseq_i2{background: #f87481;
        }
        .refseq_i3{background: #0099bb;
        }
        .refseq_i4{background: #ffb902;
        }
        .upstream2k_bg{background: #abbac3;}
        .cds_bg{background: #0099bb;}
        .five_p{background: #f87481;}
        .three_p{background:#ffb902;}

    </style>
</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
    <div class="detail-name">
        <p>${genId}</p>
    </div>
    <div class="detail-content">
        <iqgs:iqgs-nav focus="2" genId="${genId}"></iqgs:iqgs-nav>
        <div class="explains">
            <div class="explain-list" id="basic" style="min-height:auto;">
                <div class="explain-h">
                    <p>参考序列</p>
                </div>
                <div class="explain-b">
                    <div class="refseq">
                        <ul class="refseq_ul">
                            <li><i class="refseq_i1"></i>Upstream2k</li>
                            <li><i class="refseq_i2"></i>5'UTR</li>
                            <li><i class="refseq_i3"></i>CDS</li>
                            <li><i class="refseq_i4"></i>3'UTR</li>

                        </ul>
                    </div>
                    <table>
                        <tbody>
                        <tr>
                            <td style="padding: 30px 0;">
                                <p class="se0quence-name"><b>Genomic sequence</b></p>
                                <button class="copy" onclick="copyConter()"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button><br />
                                <button class="copy" onclick="copyUpstream2k()">复制Upstream2k</button><br />
                                <button class="copy" onclick="copygDNA()">复制gDNA</button><br />
                                <button class="copy" onclick="copyCDS()">复制CDS</button>
                            </td>
                            <td>
                                <div class="foo_main">
                                    <div class="foo_div">
                                        <textarea id="input"></textarea>
                                        <div class="copy_p" id="copyConter">
                                        </div>
                                    </div>
                                </div>
                            </td>

                        </tr>
                        <tr>
                            <td style="padding: 30px 0;">
                                <p class="se0quence-name"><b>Peptide sequence</b></p>
                                <button class="copy" onclick="copyPeptide()"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button><br />
                            </td>
                            <td>
                                <div class="foo_main">
                                    <div class="foo_div">
                                        <textarea id="inputPeptide"></textarea>
                                        <div class="copy_p" id="copyPeptideConter">
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<script>
    $(document).ready(function () {
        var data=${data};
        var gDNAHtml = "";
        var peptideHtml="";
        for (var i = 0; i < data.length; i++) {
            //gDNA
            if (data[i].type == "gDNA") {
                gDNAHtml += '<span class="gDNA">' + data[i].sequence + '</span>';
            }
            $("#copyConter").html(gDNAHtml);
            //upstream2k
            if (data[i].type == "upstream2k") {
                gDNAHtml += '<span class="upstream2k upstream2k_bg">' + data[i].sequence + '</span>';
            }
            $("#copyConter").html(gDNAHtml);
            //判断给gDNA下为'-'时upstream2k位置在gDNA之上
            if (data[i].strand == "-") {
                $('.upstream2k').prependTo($("#copyConter"));
            }
            //peptide
            if (data[i].type == "peptide") {
                peptideHtml += '<span class="peptide">' + data[i].sequence + '</span>';
            }
            $("#copyPeptideConter").html(peptideHtml);
        }

        //CDS
        var str = $(".gDNA").text();
        for (var i = 0; i < data.length; i++) {
            if (data[i].feature == "CDS") {
                var reg = str.replace(/<.*?>/ig,"").substring(data[i].start-1, data[i].end);
                var reg_CDS="<span class='CDS cds_bg'>"+reg+"</span>"
                str = str.replace(reg, reg_CDS);
            }
            //three_prime_UTR
            if (data[i].feature == "three_prime_UTR") {
                var reg = str.replace(/<.*?>/ig,"").substring(data[i].start-1, data[i].end);
                var reg_three_prime_UTR = "<span class='three_prime_UTR three_p'>" + reg + "</span>"
                str = str.replace(reg, reg_three_prime_UTR);
            }
            //five_prime_UTR
            if (data[i].feature == "five_prime_UTR") {
                var reg2 = str.replace(/<.*?>/ig,"").substring(data[i].start-1, data[i].end);
                var reg_five_prime_UTR = "<span class='five_prime_UTR five_p'>" + reg2 + "</span>"
                str= str.replace(reg2, reg_five_prime_UTR);
            }
        }
        $(".gDNA").html(str)
    });

    //复制gDNA
    function copygDNA() {
        $(".upstream2k").addClass("upstream2k_bg");
        $(".three_prime_UTR").removeClass('three_p');
        $(".five_prime_UTR").removeClass('five_p');
        $(".CDS").removeClass('cds_bg');
        $(".CDS,.upstream2k,#copyConter,.peptide").removeClass("order_bg");
        var text = $("#copyConter .gDNA").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".gDNA").removeClass('cds_bg').addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制CDS
    function copyCDS(){
        $(".upstream2k").addClass("upstream2k_bg");
        $(".three_prime_UTR").addClass('three_p');
        $(".five_prime_UTR").addClass('five_p');
        $(".gDNA,.upstream2k,#copyConter,.peptide").removeClass("order_bg");
        var text = $("#copyConter .CDS").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".CDS").removeClass('cds_bg').addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制upstream2k
    function copyUpstream2k() {
        $(".three_prime_UTR").addClass('three_p');
        $(".five_prime_UTR").addClass('five_p');
        $(".CDS").addClass('cds_bg');
        $(".gDNA,.CDS,#copyConter,.peptide").removeClass("order_bg");
        var text = $("#copyConter .upstream2k").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".upstream2k").removeClass("upstream2k_bg").addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制全部序列
    function copyConter() {
        $(".CDS").removeClass('cds_bg');
        $(".upstream2k").removeClass("upstream2k_bg")
        $(".three_prime_UTR").removeClass('three_p');
        $(".five_prime_UTR").removeClass('five_p');
        $(".gDNA,.CDS,.upstream2k,.peptide").removeClass("order_bg");
        var text = $("#copyConter").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $("#copyConter").addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制Peptide全部序列
    function copyPeptide() {
        $(".three_prime_UTR").addClass('three_p');
        $(".five_prime_UTR").addClass('five_p');
        $(".CDS").addClass('cds_bg');
        $(".upstream2k").addClass("upstream2k_bg")
        $(".gDNA,.CDS,.upstream2k,#copyConter").removeClass("order_bg");
        var text = $(".peptide").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".peptide").addClass("order_bg");
        layer.msg("复制成功！");
    }
</script>
</body>
</html>