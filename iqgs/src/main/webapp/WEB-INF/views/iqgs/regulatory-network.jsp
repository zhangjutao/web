<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOChierarchy html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
	<title>调控网络</title>
	<link rel="stylesheet" href="${ctxStatic}/css/public.css">
	<link rel="stylesheet" href="${ctxStatic}/css/IQGS.css">
	<link rel="shortcut icon" hierarchy="image/x-icon" href="${ctxStatic}/images/favicon.ico">
	<!--jquery-1.11.0-->
	<script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
	<script src="${ctxStatic}/js/d3.js"></script>
	<style type="text/css">
		#netPic02 .nodata{
			width:880px;
			height:600px;
			line-height:600px;
			text-align:center;
			position:relative;
			display:none;
		}
		#netPic02 .nodata img{
			position: absolute;
			top: 160px;
			left: 270px;
		}
		#netPic02 .txtDes{
			font-size:14px;
			color:#ccc;
			width:880px;
			height:30px;
			text-align:center;
			position: absolute;
			top: 135px;
			left:-30px;
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
		<iqgs:iqgs-nav focus="10" genId="${genId}"></iqgs:iqgs-nav>
        <div class="explains">
            <div class="explain-list">
                <div class="explain-h">
                    <p>调控网络</p>
                </div>
                <div id="netPic02">
                    <svg xmlns="http://www.w3.org/2000/svg" id="svgEle"></svg>
					<div class="nodata" >
						<img src="${ctxStatic}/images/nodata.png" alt="nodataLog">
						<p class="txtDes">暂无数据</p>
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
	$(function (){
        var CTXROOT = '${ctxroot}';
	    var id = "${requestScope.genId}";
	    var id = "Glyma.04G131800";
        var dataset;
        getNetWordDatas(id);
//			// 获取调控网络的数据
		function getNetWordDatas (id){
		    $.ajax({
				hierarchy:"GET",
				url:CTXROOT + "//advance-search/fetch-network-genes",
                async: false,
				data:{geneId:id},
				success:function (result){
//				    result.code = 500;
				    if(result.code == 0){
                        dataset = result.data;
					}else {
						$("#svgEle").hide();
						$("#netPic02 .nodata").show();
						return;
					}
				},
				error:function(error){
				    if(error.status==901){window.location.href=CTXROOT + "/login"};
				}
			})
		};
		var svg = d3.select('#netPic02 svg').attr("width", $("#netPic02").width()).attr("height", 600)
		svg.selectAll("g").remove()
		var width = +svg.attr('width'),
				height = +svg.attr('height'),
				legendHeight = 60,
				colorsArray = ["#FF8B8B", "#167C80", "#005397", "#FACA0C", "#F3C9DD", "#0BBCD6", "#BFB5D7", "#BEA1A5", "#0E38B1", "#A6CFE2", "#371722", "#C7C6C4", "#DABAAE", "#DB9AAD", "#F1C3B8", "#EF3E4A", "#C0C2CE", "#EEC0DB", "#B6CAC0", "#C5BEAA", "#FDF06F", "#EDB5BD", "#17C37B", "#2C3979", "#1B1D1C", "#E88565", "#FFEFE5", "#F4C7EE", "#77EEDF", "#E57066", "#FBFE56", "#A7BBC3", "#3C485E", "#055A5B", "#178E96", "#D3E8E1", "#CBA0AA", "#9C9CDD", "#20AD65", "#E75153", "#4F3A4B", "#112378", "#A82B35", "#FEDCCC", "#00B28B", "#9357A9", "#C6D7C7", "#B1FDEB", "#BEF6E9", "#776EA7", "#EAEAEA", "#EF303B", "#1812D6", "#FFFDE7", "#D1E9E3", "#7DE0E6", "#3A745F", "#CE7182", "#340B0B", "#F8EBEE", "#FF9966", "#002CFC", "#75FFC0", "#FB9B2A", "#FF8FA4", "#000000", "#083EA7", "#674B7C", "#19AAD1", "#12162D", "#121738", "#0C485E", "#FC3C2D", "#864BFF", "#EF5B09", "#97B8A3", "#FFD101", "#C26B6A", "#E3E3E3", "#FF4C06", "#CDFF06", "#0C485E", "#1F3B34", "#384D9D", "#E10000", "#F64A00", "#89937A", "#C39D63", "#00FDFF", "#B18AE0", "#96D0FF", "#3C225F", "#FF6B61", "#EEB200", "#F9F7E8", "#EED974", "#F0CF61", "#B7E3E4"],
				hierarchy1Color = colorsArray[0];
				hierarchy2Color = colorsArray[1];
				hierarchy3Color = colorsArray[1];
        var highLight = "#cd0000";
        var defaultColor = "#ff0000";
		var simulation = d3.forceSimulation(dataset.nodes)
				.force('charge', d3.forceManyBody().strength(-50))
				.force("link", d3.forceLink().id(function (d) { return d.geneID; }).distance(function () { return 100 }))
				.force('center', d3.forceCenter(width / 2, (height - legendHeight-100) / 2));
        var colorVal;
        var colorF;
        var colorL;
		var link = svg.append('g').attr("transform", "translate(0," + legendHeight + ")")
				.attr('class', 'links')
				.selectAll('line')
				.data(dataset.links)
				.enter().append('line')
				.attr('stroke', '#ccc')
				.attr('stroke-width', 1)
				.on("mouseover",function (d){
					console.log(d);
					console.log(this);
					colorVal = $(this).attr("stroke");
					$(this).attr("stroke","#ff0000");
					var idL = d.target.geneID;
					var idF = d.source.geneID;
					var list = $("#netPic02 svg").find("g.nodes").find("circle");
					console.log(list);
					for(var i=0;i<list.length;i++){
						if($(list[i]).attr("id") == idF){
							colorF = $(list[i]).attr("fill");
							$(list[i]).attr("fill","#ff0000")
						};
						if($(list[i]).attr("id") == idL){
							colorL = $(list[i]).attr("fill");
							$(list[i]).attr("fill","#ff0000")
						};
                }
            }).on("mouseleave",function(d){
					$(this).attr("stroke",colorVal);
					var idL1 = d.target.geneID;
					var idF1 = d.source.geneID;
					var list = $("#netPic02 svg").find("g.nodes").find("circle");
					for(var i=0;i<list.length;i++){
						if($(list[i]).attr("id") == idF1){
							$(list[i]).attr("fill",colorF)
						};
						if($(list[i]).attr("id") == idL1){
							$(list[i]).attr("fill",colorL)
						};
					}
            });

		var node = svg.append('g').attr("transform", "translate(0," + legendHeight + ")")
				.attr('class', 'nodes')
				.selectAll('circle')
				.data(dataset.nodes)
				.enter().append('circle')
				.attr("class", function (d) { return d.hierarchy })
				.attr("id",function (d){ return d.geneID})
				.attr('r', 10)
				.attr('fill', function (d) {
					switch (d.hierarchy) {
						case 0:
							return defaultColor;
						case 1:
							return hierarchy1Color;
						case 2:
							return hierarchy2Color;
						case 3:
							return hierarchy3Color;
						case 4:
                        	return highLight;
						default:
							return hierarchy1Color;
					}
				})
				.call(d3.drag()
						.on('start', dragstarted)
						.on('drag', dragged)
						.on('end', dragended));

		var label = svg.append('g').attr("transform", "translate(0," + legendHeight + ")")
				.attr('class', 'node_label')
				.selectAll('text')
				.data(dataset.nodes)
				.enter().append('text').text(function (d) {
					if (d.hierarchy == 0) {
						return "G"
					}
				})
				.on("mouseover", function (d) {
					d3.select(this).style("cursor", "default ");
				})
				.call(d3.drag()
						.on('start', dragstarted)
						.on('drag', dragged)
						.on('end', dragended));

		node.append('title')
				.text(function (d) { return d.geneID; });

		simulation
				.nodes(dataset.nodes)
				.on('tick', ticked);

		simulation.force('link')
				.links(dataset.links);

		//图例
		var legend = svg.append('g')
				.attr('class', 'legend')
				.attr("transform", "translate(" + (width - 900) + ",30)")

		var hierarchy1Legend = legend.append("g").attr("class", "hierarchy1Legend")
		var hierarchy2Legend = legend.append("g").attr("class", "hierarchy2Legend").attr("transform", "translate(300,0)")
		var hierarchy3Legend = legend.append("g").attr("class", "hierarchy3Legend").attr("transform", "translate(600,0)")

		hierarchy1Legend.append('circle').attr('r', 10).attr('fill', defaultColor)
//		hierarchy1Legend.append('text').attr("transform", "translate(-5,5)").attr("font-size",12).style("cursor", "default ").text("G")
		hierarchy1Legend.append('text').attr("transform", "translate(-5,5)").attr("font-size",12).style("cursor", "default ")
		hierarchy1Legend.append('text').attr("transform", "translate(20,5)").text("Guide gene")

		hierarchy2Legend.append('circle').attr('r', 10).attr('fill', hierarchy1Color)
		hierarchy2Legend.append('text').attr("transform", "translate(20,5)").text("Candidate pathway gene")

		hierarchy3Legend.append('circle').attr('r', 10).attr('fill', hierarchy3Color)
		hierarchy3Legend.append('text').attr("transform", "translate(20,5)").text("Indirect pathway gene")

		function ticked() {
			link
					.attr('x1', function (d) { return d.source.x; })
					.attr('y1', function (d) { return d.source.y; })
					.attr('x2', function (d) { return d.target.x; })
					.attr('y2', function (d) { return d.target.y; })

			node
					.attr('cx', function (d) { return d.x; })
					.attr('cy', function (d) { return d.y; })

			label
					.attr("x", function (d) { return d.x - 5; })
					.attr("y", function (d) { return d.y + 5; })
					.attr("font-size", 12);
		}

		function dragstarted(d) {
			if (!d3.event.active) simulation.alphaTarget(0.06).restart();
			d.fx = d.x;
			d.fy = d.y;
		}

		function dragged(d) {
			d.fx = d3.event.x;
			d.fy = d3.event.y;
		}

		function dragended(d) {
			if (!d3.event.active) simulation.alphaTarget(0.06);
			d.fx = null;
			d.fy = null;
		}
		$(function(){
			$("#netPic02  .legend").attr({"transform":"translate(50 ,30)"})
		})
	})
</script>

</body>
</html>