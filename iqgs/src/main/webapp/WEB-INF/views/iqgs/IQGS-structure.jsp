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

</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
	<div class="detail-name">
		<p>${genId}</p>
	</div>
	<div class="detail-content">
		<iqgs:iqgs-nav focus="3" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="basic">
				<div class="explain-h">
					<p>基因结构</p>
				</div>
				<div class="explain-b">
                    <div class="color-example">
                        <label>
                            <span><i class="cor-pro"></i>Promoter</span>
                            <span><i class="cor-5utr"></i>5'UTR</span>
                            <span><i class="cor-cds"></i>CDS</span>
                            <span><i class="cor-3utr"></i>3'UTR</span>
                        </label>
                    </div>
					<div id="geneStructure">
                        <!--<div class="structure-list list-1">
                            <a href="javascript:;">Glyma.01G000300</a>
                            <div id="geneID01" >
                            </div>
                            <div class="show">
                                <p class="btn-download-more">
                                    <button class="btn js-btn-download">下载序列</button>
                                    <button class="btn js-more-txt">详细信息</button>
                                </p>
                                <div class="more-txt">
                                    <table>
                                        <tr>
                                            <td></td>
                                            <td>染色体</td>
                                            <td>起始位置</td>
                                            <td>终止位置</td>
                                            <td>长度</td>
                                        </tr>
                                        <tr>
                                            <td>Promoter</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>5'UTR</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>2.2</td>
                                        </tr>
                                        <tr>
                                            <td>CDS</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>CDS</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>3'UTR</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="structure-list list-2">
                            <a href="javascript:;">Glyma.01G000300</a>
						    <div id="geneID02" > </div>
                            <div class="show">
                                <p class="btn-download-more">
                                    <button class="btn js-btn-download">下载序列</button>
                                    <button class="btn js-more-txt">详细信息</button>
                                </p>
                                <div class="more-txt">
                                    <table>
                                        <tr>
                                            <td></td>
                                            <td>染色体</td>
                                            <td>起始位置</td>
                                            <td>终止位置</td>
                                            <td>长度</td>
                                        </tr>
                                        <tr>
                                            <td>Promoter</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>5'UTR</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>2.2</td>
                                        </tr>
                                        <tr>
                                            <td>CDS</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>CDS</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                        <tr>
                                            <td>3'UTR</td>
                                            <td>Chro1</td>
                                            <td>111222</td>
                                            <td>113333</td>
                                            <td>22</td>
                                        </tr>
                                    </table>
                                </div>
                            </div>
                        </div>-->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->

<script>
    $(function(){
        $("#geneStructure").on('click', '.js-more-txt', function(){
            $(this).closest('div.show').find("div.more-txt").toggle();
            $(this).text()=="详细信息"?$(this).text("收起"):$(this).text("详细信息");
        })
    });
    $(function(){
        $("#geneStructure").on('click', '.js-btn-download', function(){
            window.location.href="${ctxroot}/iqgs/detail/structure/downloadSeq?transcript_id="+$(this).attr("id");
        })
    });
    var responseData = ${structJSON};/*{
        "max_length": 30000,
        "start": 1000,
        "data": [
            {
                "geneID": 'Glyma.11G109400',
                "structure": [
                    {
                        "type": "three_prime_UTR",
                        "start": 1000,
                        "end": 2980
                    },
                    {
                        "type": "CDS",
                        "start": 2990,
                        "end": 7590
                    },
                    {
                        "type": "promoter",
                        "start": 12900,
                        "end": 13400
                    },
                    {
                        "type": "five_prime_UTR",
                        "start": 20814,
                        "end": 22280
                    },
                ]
            },
            {
                "geneID": 'Glyma.11G109401',
                "structure": [
                    {
                        "type": "promoter",
                        "start": 1100,
                        "end": 5980
                    },
                    {
                        "type": "CDS",
                        "start": 8299,
                        "end": 9759
                    },
                    {
                        "type": "CDS",
                        "start": 12900,
                        "end": 13400
                    },
                    {
                        "type": "five_prime_UTR",
                        "start": 21814,
                        "end": 35228
                    },
                ]
            },
            {
                "geneID": 'Glyma.11G109402',
                "structure": [
                    {
                        "type": "three_prime_UTR",
                        "start": 3000,
                        "end": 6298
                    },
                    {
                        "type": "five_prime_UTR",
                        "start": 24814,
                        "end": 26000
                    },
                ]
            },
            {
                "geneID": 'Glyma.11G109403',
                "structure": [
                    {
                        "type": "three_prime_UTR",
                        "start": 3000,
                        "end": 4980
                    },
                    {
                        "type": "five_prime_UTR",
                        "start": 7814,
                        "end": 23228
                    },
                ]
            }
        ]
    }*/
    sortASC(responseData.data);

    d3.select("#geneStructure").selectAll("svg").remove();

    $.each(responseData.data, function(i, item){
        var genDomId = "geneId" + i;
        var html = [];
        html.push('<div class="structure-list">');
        html.push('    <a href="javascript:;">'+item.geneID+'</a>');
        html.push('    <div id="'+ genDomId +'" ></div>');
        html.push('    <div class="show">');
        html.push('        <p class="btn-download-more">');
        html.push('            <button id="'+item.geneID+'" class="btn js-btn-download">下载序列</button>');
        html.push('            <button class="btn js-more-txt">收起</button>');
        html.push('        </p>');
        html.push('        <div class="more-txt">');
        html.push('            <table>');
        html.push('                <tr><td></td><td>染色体</td><td>起始位置</td><td>终止位置</td><td>长度</td></tr>');
        $.each(item.structure, function(ii, sitem){
            html.push('                <tr><td>'+sitem.type+'</td><td>'+sitem.chromosome+'</td><td>'+sitem.start+'</td><td>'+sitem.end+'</td><td>'+sitem.length+'</td></tr>');
        });
        html.push('            </table>');
        html.push('        </div>');
        html.push('    </div>');
        html.push('</div>');
        $("#geneStructure").append($(html.join('\n')));
        drawGeneStructure(genDomId, responseData.max_length, responseData.start, item)
    });
//    drawGeneStructure("geneID01", responseData.max_length, responseData.start, responseData.data[0]);
//    drawGeneStructure("geneID02", responseData.max_length, responseData.start, responseData.data[0]);
    function drawGeneStructure(divId, maxLength, start, data) {

        var svg = d3.select("#" + divId).append("svg").attr("width", $("#" + divId).width()).attr("height", 40),
            svg_width = svg.attr("width"),
            svg_height = svg.attr("height"),
            svg_margin = 20

        var structureColor = {
            "promoter": "#C6E3FE",
            "three_prime_UTR": "#FFBA02",
            "CDS": "#009ABB",
            "five_prime_UTR": "#F87580",
            "line": "#D1D1D1"
        }

        //构建比例尺
        var Scale = d3.scaleLinear().domain([0, maxLength]).range([0, svg_width - svg_margin * 2])
        var g = svg.append("g")
            .attr("transform", "translate( "+svg_margin+" , 20)")

        // 背景线
        g.append("line").attr("x1", 0).attr("y1", 0).attr("x2", Scale(maxLength)).attr("y2", 0).attr("stroke-width", 4).attr("stroke", structureColor.line)
        // 方块
        for (var i = 0; i< data.structure.length; i++) {
            g.append("rect").attr("x", Scale(data.structure[i].start - start))
                .attr("y", function(){
                    if(data.structure[i].type === "promoter"){
                        return -6
                    }
                    return -15
                }).attr("width", Scale(data.structure[i].end - data.structure[i].start)).attr("height", function(){
                if(data.structure[i].type === "promoter"){
                    return 12
                }
                return 30
            })
                .attr("fill", structureColor[data.structure[i].type])
        }



    }
    function sortASC(data) {
        for (var i=0; i<data.length; i++) {
            var minIndex = i;
            for (var j=i+1; j<data.length; j++) {
                if (data[j].geneID < data[minIndex].geneID) {
                    minIndex = j;
                }
            }
            var tmp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = tmp;
        }
    }
</script>

</body>
</html>