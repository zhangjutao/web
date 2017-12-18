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
                                            ${data}
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
                        <%--<c:forEach items="${dnas}" var="dna">--%>
                        <%--<tr>--%>
                        <%--<td>--%>
                        <%--<p class="sequence-name"><b>${dna.type}</b></p>--%>
                        <%--<button class="copy"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button>--%>
                        <%--</td>--%>
                        <%--<td>--%>
                        <%--<p class="gene-sequence">${dna.sequence}</p>--%>
                        <%--</td>--%>
                        <%--</tr>--%>
                        <%--</c:forEach>--%>
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
        var xfbRightHtml = "";
        var peptideHtml="";
        var str = $(".gDNA").html();
        for (var i = 0; i < data.length; i++) {
            //gDNA
            if (data[i].type == "gDNA") {
                xfbRightHtml += '<span class="gDNA">' + data[i].sequence + '</span>';
            }

            $("#copyConter").html(xfbRightHtml);
            //upstream2k
            if (data[i].type == "upstream2k") {
                xfbRightHtml += '<span class="upstream2k">' + data[i].sequence + '</span>';
            }
            $("#copyConter").html(xfbRightHtml);
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
        var str = $(".gDNA").html();
        for (var i = 0; i < data.length; i++) {
            if (data[i].feature == "CDS") {
                var reg2 = str.substring(data[i].start, data[i].end+1);
                var reg_test="<span class='CDS'>"+reg2+"</span>"
                str = str.replace(reg2, reg_test);
            }
            if (data[i].feature == "three_prime_UTR") {
                var reg2 = str.substring(data[i].start, data[i].end + 1);
                var reg_test = "<span class='three_prime_UTR'>" + reg2 + "</span>"
                str = str.replace(reg2, reg_test);
            }
        }
        $(".gDNA").html(str)
    });


    //复制gDNA
    function copygDNA() {
        $(".CDS").removeClass("order_bg");
        $(".upstream2k").removeClass("order_bg");
        $("#copyConter").removeClass("order_bg");
        $(".peptide").removeClass("order_bg");
        var text = $("#copyConter .gDNA").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".gDNA").addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制CDS
    function copyCDS(){
        $(".gDNA").removeClass("order_bg");
        $(".upstream2k").removeClass("order_bg");
        $("#copyConter").removeClass("order_bg");
        $(".peptide").removeClass("order_bg");
        var text = $("#copyConter .CDS").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".CDS").addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制upstream2k
    function copyUpstream2k() {
        $(".gDNA").removeClass("order_bg");
        $(".CDS").removeClass("order_bg");
        $("#copyConter").removeClass("order_bg");
        $(".peptide").removeClass("order_bg");
        var text = $("#copyConter .upstream2k").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".upstream2k").addClass("order_bg");
        layer.msg("复制成功！");
    }

    //复制全部序列
    function copyConter() {
        $(".gDNA").removeClass("order_bg");
        $(".CDS").removeClass("order_bg");
        $(".upstream2k").removeClass("order_bg");
        $(".peptide").removeClass("order_bg");
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
        $(".gDNA").removeClass("order_bg");
        $(".CDS").removeClass("order_bg");
        $(".upstream2k").removeClass("order_bg");
        $("#copyConter").removeClass("order_bg");
        var text = $(".peptide").text();
        var input = document.getElementById("input");
        input.value = text; // 修改文本框的内容
        input.select(); // 选中文本
        document.execCommand("copy"); // 执行浏览器复制命令
        $(".peptide").addClass("order_bg");
        layer.msg("复制成功！");
    }
    //    var t=document.getElementById("txt");
    //    t.select();
    //    window.clipboardData.setData('text',t.createTextRange().text);

    <%--$('.copy').each(function(i){--%>
    <%--$(this).click(function(){--%>
    <%--$(".gene-sequence").removeClass("copy-ac")--%>
    <%--var obj=$(this).parent().siblings().find(".gene-sequence");--%>
    <%--obj.addClass("copy-ac");--%>
    <%--var t=obj.text();--%>
    <%--var clipboard = new Clipboard('.copy', {--%>
    <%--text: function() {--%>
    <%--return t;--%>
    <%--}--%>
    <%--});--%>
    <%--clipboard.on('success', function(e) {--%>
    <%--layer.msg("复制成功！")--%>
    <%--console.log(t);--%>
    <%--});--%>

    <%--&lt;%&ndash;$(this).zclip({&ndash;%&gt;--%>
    <%--&lt;%&ndash;path: "${ctxStatic}/js/zclip/ZeroClipboard.swf",&ndash;%&gt;--%>
    <%--&lt;%&ndash;copy: function(){&ndash;%&gt;--%>
    <%--&lt;%&ndash;return $(this).parent().siblings().find(".gene-sequence").text();&ndash;%&gt;--%>
    <%--&lt;%&ndash;},&ndash;%&gt;--%>
    <%--&lt;%&ndash;afterCopy:function(){/* 复制成功后的操作 */&ndash;%&gt;--%>
    <%--&lt;%&ndash;console.log($(this).parent().siblings().find(".gene-sequence").text());&ndash;%&gt;--%>
    <%--&lt;%&ndash;$(this).parent().siblings().find(".gene-sequence").css({"background-color":"#5c8ce6","color":"#fff"});&ndash;%&gt;--%>

    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;});&ndash;%&gt;--%>

    <%--});--%>
    <%--});--%>

</script>
</body>
</html>