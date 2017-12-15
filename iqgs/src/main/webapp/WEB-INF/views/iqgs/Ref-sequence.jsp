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
			<div class="explain-list" id="basic">
                <div class="explain-h">
                    <p>参考序列</p>
                </div>
                ${data}
                <span style="background-color: red;color: white">红色背景  CDS</span>
                <span style="background-color: green;color: white">绿色背景 3-UTR</span>
                <span style="background-color: blue;color: white">蓝色背景 5-UTR</span>
                <span style="background-color: grey;color: white">灰色背景 upstream2k</span>

            <%--<c:forEach items="${maps}" var="v">
                    <div class="box">
                        <a href="${v.value.userName }/showUserbyJson">json格式数据<br></a>

                    </div>
                </c:forEach>--%>
                <div class="explain-b">
                    <table>
                        <tbody>
                        <tr>
                            <td>
                                <p class="sequence-name"><b>Genomic sequence</b></p>
                                <button class="copy" id="copyAll"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button>
                                <button class="copy" id="copyUpstream><img src="${ctxStatic}/images/i-1-ac.png">复制Upstream2k</button>
                                <button class="copy" id="copygDNA><img src="${ctxStatic}/images/i-1-ac.png">复制gDNA</button>
                                <button class="copy" id="copyCDS><img src="${ctxStatic}/images/i-1-ac.png">复制CDS</button>
                            </td>
                            <td>
                                <p class="gene-sequence" id="totalSequence" class="gene-sequence" ><%--gDNA+Upstream2k序列--%></p>
                                <p style="display: none" id="showUpstream" class="gene-sequence" ></p>
                                <p style="display: none" id="showgDNA" class="gene-sequence" ></p>
                                <p style="display: none" id="showCDS" class="gene-sequence" ></p>
                            </td>
                        </tr>
                        <%--peptide部分--%>
                        <tr>
                            <td>
                                <p class="sequence-name"><b>Peptide-sequence</b></p>
                                <button id="copypeptide" class="copy"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button>
                            </td>
                            <td>
                                <p class="gene-sequence" id="peptideSequence"></p>
                            </td>
                        </tr>
                        <%--${data}
                        <c:forEach items="${dnas}" var="dna">
                            <tr>
                                <td>
                                    <p class="sequence-name"><b>${dna.type}</b></p>
                                    <button class="copy"><img src="${ctxStatic}/images/i-1-ac.png">复制序列</button>
                                </td>
                                <td>
                                    <p class="gene-sequence">${dna.sequence}</p>
                                </td>
                            </tr>
                        </c:forEach>--%>
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
//    var t=document.getElementById("txt");
//    t.select();
//    window.clipboardData.setData('text',t.createTextRange().text);
    $(function(){
        var result=${data};
        var cdsSequence;
        var peptideSequence;
        var gdnaSequence;
        var upstream2kSequence;
        var totalSequence;
        var strand;
        var increasedIndex=0;
        var originalIndex=0;
        var gdnaSequenceLoad;
        for(var i in result){
            if(result[i].type=="CDS"){cdsSequence=result[i].sequence;console.log("cdsSequence",cdsSequence)}
            if(result[i].type=="peptide"){peptideSequence=result[i].sequence;console.log("peptideSequence",peptideSequence)}
            if(result[i].type=="gDNA"){gdnaSequenceLoad=gdnaSequence=result[i].sequence;console.log("gdnaSequence",gdnaSequence)}
            if(result[i].type=="upstream2k"){upstream2kSequence=result[i].sequence;console.log("upstream2kSequence",upstream2kSequence)}
            if(result[i].feature=="CDS"){
                originalIndex=gdnaSequenceLoad.length;
                strand=result[i].strand;
                var cdsPart=gdnaSequenceLoad.substring(result[i].start+increasedIndex-1,result[i].end+increasedIndex);
                console.log("cdsPart",cdsPart)
                var cdsNewpart="<span style='background-color:red'>"+cdsPart+"</span>";
                gdnaSequenceLoad=gdnaSequenceLoad.replace(cdsPart,cdsNewpart);
                increasedIndex=gdnaSequenceLoad.length-originalIndex;
            }
            if(result[i].feature=="three_prime_UTR"){
                originalIndex=gdnaSequenceLoad.length;
                strand=result[i].strand;
                var cdsPart=gdnaSequenceLoad.substring(result[i].start+increasedIndex-1,result[i].end+increasedIndex);
                var cdsNewpart="<span style='background-color:green'>"+cdsPart+"</span>";
                gdnaSequenceLoad=gdnaSequenceLoad.replace(cdsPart,cdsNewpart);
                increasedIndex=gdnaSequenceLoad.length-originalIndex;
                console.log(gdnaSequenceLoad)
            }
            if(result[i].feature=="five_prime_UTR"){
                originalIndex=gdnaSequenceLoad.length;
                strand=result[i].strand;
                var cdsPart=gdnaSequenceLoad.substring(result[i].start+increasedIndex-1,result[i].end+increasedIndex);
                var cdsNewpart="<span style='background-color:blue'>"+cdsPart+"</span>";
                gdnaSequenceLoad=gdnaSequenceLoad.replace(cdsPart,cdsNewpart);
                increasedIndex=gdnaSequenceLoad.length-originalIndex;
            }
        }
        var upstream2kSequenceLoad="<span style='background-color:grey'>"+upstream2kSequence+"</span>";
        if(strand=="+"){
            totalSequence=upstream2kSequenceLoad+gdnaSequenceLoad;
        }
        if(strand=="-"){
            totalSequence=gdnaSequenceLoad+upstream2kSequenceLoad;
        }
        $("#totalSequence").html(totalSequence);
        $("#peptideSequence").html(peptideSequence);
        $("#showUpstream").html(upstream2kSequence);
        $("#showgDNA").html(gdnaSequence);
        $("#showCDS").html(cdsSequence);
    })
    //复制CDS
    $("#copyCDS").click(function(){
        copy(cdsSequence);
    })
    //复制gDNA
    $("#copygDNA").click(function(){
        copy(gdnaSequence);
    })

    //复制Upstream2k
    $("#copyUpstream").click(function(){
        //copy(upstream2kSequence);
        $("#copyUpstream").removeClass("copy-ac")
        var obj=$("#showUpstream");
        obj.addClass("copy-ac");
        var t=obj.text();
        var clipboard = new Clipboard('.copy', {
            text: function() {
                return t;
            }
        });
        clipboard.on('success', function(e) {
            layer.msg("复制成功！")
            console.log(t);
        });
    })

    //复制Peptide
    $("#copypeptide").click(function(){
        $("#copypeptide").removeClass("copy-ac")
        var obj=$("#peptideSequence");
        obj.addClass("copy-ac");
        //copy(peptideSequence);
        var t=obj.text();
        var clipboard = new Clipboard('.copy', {
            text: function() {
                return t;
            }
        });
        clipboard.on('success', function(e) {
            layer.msg("复制成功！")
            console.log(t);
        });
    })

    function copy(text)
    {
        var t=text;
        var clipboard = new Clipboard('.copy', {
            text: function() {
                return t;
            }
        });
        clipboard.on('success', function(e) {
            layer.msg("复制成功！")
            console.log(t);
        });
    }

    /*$('.copy').each(function(i){
        $(this).click(function(){
            $(".gene-sequence").removeClass("copy-ac")
            var obj=$(this).parent().siblings().find(".gene-sequence");
            obj.addClass("copy-ac");
            var t=obj.text();
            var clipboard = new Clipboard('.copy', {
                text: function() {
                    return t;
                }
            });
            clipboard.on('success', function(e) {
               layer.msg("复制成功！")
                console.log(t);
            });


        });
    });*/


</script>
</body>
</html>