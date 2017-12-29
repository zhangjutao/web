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
	<title>调控网络</title>
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
		<iqgs:iqgs-nav focus="10" genId="${genId}"></iqgs:iqgs-nav>
        <div class="explains">
            <div class="explain-list">
                <div class="explain-h">
                    <p>调控网络</p>
                </div>
                <div id="netPic02">
                    <svg xmlns="http://www.w3.org/2000/svg"></svg>
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
	    var id = "${requestScope.genId}";
			// 获取调控网络的数据
		function getNetWordDatas (id){
		    $.ajax({
				type:
			})

		}

		var dataset = {
			nodes: [
				{ geneID: "Glyma.06G317500", type: 1 },
				// { geneID: "Glyma.15G272000", type: 1 },
				{ geneID: "Glyma.11G109400", type: 2 },
				{ geneID: "Glyma.12G015900", type: 2 },
				{ geneID: "Glyma.09G173500", type: 2 },
				{ geneID: "Glyma.12G207500", type: 2 },
				{ geneID: "Glyma.13G293600", type: 2 },
				{ geneID: "Glyma.13G064900", type: 2 },
				{ geneID: "Glyma.18G255700", type: 2 },
				// { geneID: "Glyma.19G020100", type: 2 },
				// { geneID: "Glyma.20G077000", type: 2 },
				// { geneID: "Glyma.16G075400", type: 2 },
				// { geneID: "Glyma.18G096900", type: 2 },
				// { geneID: "Glyma.17G064100", type: 2 },
				{ geneID: "Glyma.18G125500", type: 3 },
				// { geneID: "Glyma.18G157300", type: 3 },
				// { geneID: "Glyma.16G157900", type: 3 },
			],
			links: [
				{ source: "Glyma.06G317500", target: "Glyma.11G109400", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.12G015900", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.09G173500", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.12G207500", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.13G293600", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.13G064900", HasArrow: true },
				{ source: "Glyma.06G317500", target: "Glyma.18G255700", HasArrow: true },
				// { source: "Glyma.15G272000", target: "Glyma.19G020100", HasArrow: true },
				// { source: "Glyma.15G272000", target: "Glyma.20G077000", HasArrow: true },
				// { source: "Glyma.15G272000", target: "Glyma.16G075400", HasArrow: true },
				// { source: "Glyma.15G272000", target: "Glyma.18G096900", HasArrow: true },
				// { source: "Glyma.15G272000", target: "Glyma.17G064100", HasArrow: true },
				{ source: "Glyma.11G109400", target: "Glyma.18G125500", HasArrow: false },
				// { source: "Glyma.17G064100", target: "Glyma.18G157300", HasArrow: false },
				// { source: "Glyma.16G075400", target: "Glyma.16G157900", HasArrow: false },
			]
		};

		var svg = d3.select('#netPic02 svg').attr("width", $("#netPic02").width()).attr("height", 600)
		svg.selectAll("g").remove()
		var width = +svg.attr('width'),
				height = +svg.attr('height'),
				legendHeight = 60,
				colorsArray = ["#FF8B8B", "#167C80", "#005397", "#FACA0C", "#F3C9DD", "#0BBCD6", "#BFB5D7", "#BEA1A5", "#0E38B1", "#A6CFE2", "#371722", "#C7C6C4", "#DABAAE", "#DB9AAD", "#F1C3B8", "#EF3E4A", "#C0C2CE", "#EEC0DB", "#B6CAC0", "#C5BEAA", "#FDF06F", "#EDB5BD", "#17C37B", "#2C3979", "#1B1D1C", "#E88565", "#FFEFE5", "#F4C7EE", "#77EEDF", "#E57066", "#FBFE56", "#A7BBC3", "#3C485E", "#055A5B", "#178E96", "#D3E8E1", "#CBA0AA", "#9C9CDD", "#20AD65", "#E75153", "#4F3A4B", "#112378", "#A82B35", "#FEDCCC", "#00B28B", "#9357A9", "#C6D7C7", "#B1FDEB", "#BEF6E9", "#776EA7", "#EAEAEA", "#EF303B", "#1812D6", "#FFFDE7", "#D1E9E3", "#7DE0E6", "#3A745F", "#CE7182", "#340B0B", "#F8EBEE", "#FF9966", "#002CFC", "#75FFC0", "#FB9B2A", "#FF8FA4", "#000000", "#083EA7", "#674B7C", "#19AAD1", "#12162D", "#121738", "#0C485E", "#FC3C2D", "#864BFF", "#EF5B09", "#97B8A3", "#FFD101", "#C26B6A", "#E3E3E3", "#FF4C06", "#CDFF06", "#0C485E", "#1F3B34", "#384D9D", "#E10000", "#F64A00", "#89937A", "#C39D63", "#00FDFF", "#B18AE0", "#96D0FF", "#3C225F", "#FF6B61", "#EEB200", "#F9F7E8", "#EED974", "#F0CF61", "#B7E3E4"],
				type1Color = colorsArray[0];
		type2Color = colorsArray[1];
		type3Color = colorsArray[2];

		var defs = svg.append("defs"),
				arrowMarker = defs.append("marker")
						.attr("id", "arrow")
						.attr("markerUnits", "strokeWidth")
						.attr("markerWidth", 12)
						.attr("markerHeight", 12)
						.attr("viewBox", "0 0 10 10")
						.attr("refX", 18)
						.attr("refY", 6)
						.attr("orient", "auto"),
				arrow_path = "M2,2 L10,6 L2,10 L6,6 L2,2"

		arrowMarker.append("path")
				.attr("d", arrow_path)
				.attr("fill", "#000");


		var simulation = d3.forceSimulation(dataset.nodes)
				.force('charge', d3.forceManyBody().strength(-50))
				.force("link", d3.forceLink().id(function (d) { return d.geneID; }).distance(function () { return 100 }))
				.force('center', d3.forceCenter(width / 2, (height - legendHeight) / 2));


		var link = svg.append('g').attr("transform", "translate(0," + legendHeight + ")")
				.attr('class', 'links')
				.selectAll('line')
				.data(dataset.links)
				.enter().append('line')
				.attr('stroke', '#ccc')
				.attr('stroke-width', 1)
				.attr("marker-end", function (d) {
							if (d.HasArrow) { return "url(#arrow)" }
						}
				)

		var node = svg.append('g').attr("transform", "translate(0," + legendHeight + ")")
				.attr('class', 'nodes')
				.selectAll('circle')
				.data(dataset.nodes)
				.enter().append('circle')
				.attr("class", function (d) { return d.type })
				.attr('r', 10)
				.attr('fill', function (d) {
					switch (d.type) {
						case 1:
							return type1Color;
						case 2:
							return type2Color;
						case 3:
							return type3Color;
						default:
							return type1Color;
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
					if (d.type == 1) {
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

		var type1Legend = legend.append("g").attr("class", "type1Legend")
		var type2Legend = legend.append("g").attr("class", "type2Legend").attr("transform", "translate(300,0)")
		var type3Legend = legend.append("g").attr("class", "type3Legend").attr("transform", "translate(600,0)")

		type1Legend.append('circle').attr('r', 10).attr('fill', type1Color)
		type1Legend.append('text').attr("transform", "translate(-5,5)").attr("font-size",12).style("cursor", "default ").text("G")
		type1Legend.append('text').attr("transform", "translate(20,5)").text("type1文字描述")

		type2Legend.append('circle').attr('r', 10).attr('fill', type2Color)
		type2Legend.append('text').attr("transform", "translate(20,5)").text("type2文字描述")

		type3Legend.append('circle').attr('r', 10).attr('fill', type3Color)
		type3Legend.append('text').attr("transform", "translate(20,5)").text("type3文字描述")

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
			if (!d3.event.active) simulation.alphaTarget(0.3).restart();
			d.fx = d.x;
			d.fy = d.y;
		}

		function dragged(d) {
			d.fx = d3.event.x;
			d.fy = d3.event.y;
		}

		function dragended(d) {
			if (!d3.event.active) simulation.alphaTarget(0);
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