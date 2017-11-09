<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<title>基因图谱</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="http://cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/raphael/2.2.7/raphael.min.js"></script>
	<!-- <script src="http://www.seabreezecomputers.com/tips/find5.js"></script> -->
	<style>
		text {
			cursor: pointer;
		}
		.circle {
			display: inline-block;
			width: 15px;
			height: 15px;
		}
		ul li {
			display: inline-block;
			margin: 5px 10px;
		}
	</style>
</head>

<body>
<div><ul id="qtlExample"></ul></div>
<div><button type="button" id="zoomin">放大</button> <button type="button" id="zoomout">缩小</button> <input type="text" id="search"><button type="button" id="btn-search">搜索</button></div>

<script>

	var RESOURCEURL = {
		markerLeft: "/getMarkerLeft?chr=${chr}&version=${version}",
		markerRight: "/getMarkerRight?markerlg=${markerlg}",
		getQtl: "/getQtlByLg?lg=${lg}"
	}


	// lowValue,hightValue 之间随机数
	function selectfrom(lowValue, highValue) {
		var choice = highValue - lowValue + 1;
		return Math.floor(Math.random() * choice + lowValue);
	}


    /**
	 *  基因图片的设计思路
	 *  左边标尺和右边QTL图谱分开画
	 *  目前由于数据来源可能有3个地方，如：
	 *  	物理标尺数据（markerLeft）来源： /getMarkerLeft?chr=${chr}&version=${version}
	 *  	基因标尺数据（markerRight）来源： /markerRight?cmarkerlg=${markerlg}
	 *  	qtl数据（getQtl）来源：/getQtlByLg?lg=${lg}
	 *
	 *  思路：
	 *  	1、数据整理和标识绘制
	 *  		a)	拉取标尺的数据，并计算相对坐标系，详见：promise_physics、promise_inheritance 方法
	 *  		b)	绘制左边标尺，详见：drawRuler 方法
	 *  	2、绘制右边QTL图
	 *  		a)	先抽象绘制一个 QTL group 的方法 （drawQtl），每一组包括 多个QTL竖线和名称的连线
	 *  		b)	可通过多种维度对 QTL 进行分组、排序
     * @type {number}
     */


    /**
     *  1、初始化画布 和 一些常量
     */

    // 设置画布大小和起始坐标
	var PAPER_HEIGHT = 3600, PAPER_WIDTH = 1900, PAPER_X = 0, PAPER_Y = 150;
	// 设置基因图大小和起始坐标，RULER_GAP:两个轴间隙，RULER_TIP_WIDTH:marker宽度
	var RULER_HEIGHT = PAPER_HEIGHT - 250, 		//标尺的高度
		RULER_WIDTH = 12, 						//标尺宽度
		RULER_X = 150, RULER_Y = 100, 			//标尺的起始位置
		RULER_GAP = 70, 						//左右标尺间距
		RULER_TIP_WIDTH = 30;					//marker文字宽度

	var COL_WIDTH = 23;							// QTL图中 多条QTL的间距

	var GLOW_COLOR = "red";						//选中高亮颜色

    var GROUP_NUM = 2; 							// 分组数
    var BASIC_WIDTH = RULER_WIDTH + RULER_GAP + RULER_X + RULER_TIP_WIDTH + 120 ; // 画qlt图的基础x坐标，可变的宽度

    var qtl; 									// 保存一个组的qtl数据
    var markersLeft = [], 						// 存储物理数据
		markersRight = []; 						// 存储遗传数据
    var M = {}; 								// 存储标尺 marker 相关线段元素（文字，横线，辅助线）
    var currentClickMarker = []; 				// 当前选中qtl对应的marker数组
    var Q = {}; 								// 存储qtl相关线段元素 （文字,辅助线,属性）
    var currentClickQtl = ""; 					// 当前选中的qtl名称
    var coordinate = {}; 						// 标尺上 marker 的相对坐标

	var COLORS = {								//QTL大类颜色
		"QTL_fungal": "#FFB902",
		"QTL_inorganic": "#da3b01",
		"QTL_insect": "#EF6950",
		"QTL_leaf-stem": "#c339b3",
		"QTL_misc": "#c40052",
		"QTL_nematode": "#017be8",
		"QTL_oil": "#f76201",
		"QTL_other-seed": "#8f8bd5",
		"QTL_pod": "#6b69d6",
		"QTL_protein": "#0099bb",
		"QTL_reprod-period": "#018675",
		"QTL_root": "#10883e",
		"QTL_viral": "#677689",
		"QTL_whole-plant": "#647C64",
		"QTL_yield": "#0063B1"
	}

	// 初始化画布
	var paper = Raphael(PAPER_X, PAPER_Y, PAPER_WIDTH, PAPER_HEIGHT);
	paper.clear();

    /**
     *  2、数据整理和标识绘制
     */

    // 物理标尺的数据整理和绘制
    var promise_physics = function () {
		return $.ajax({
			url: RESOURCEURL.markerLeft,
			type: "GET",
			dataType: "json",
			success: function(data) {
				var max = (data[0][3] * 1 / 1000 / 1000 + data[0][4] * 1 / 1000 / 1000) / 2;
				$.each(data, function(i, el) {
					var yaxis = (el[3] * 1 / 1000 / 1000 + el[4] * 1 / 1000 / 1000) / 2;
					if(yaxis > max) {
						max = yaxis;
					}
					var tmp = {};
					tmp.x = RULER_X;
					tmp.y = yaxis;
					tmp.marker = el[0] ;
					markersLeft.push(tmp);     //左边标尺的坐标
				});
				var linerfunc = (RULER_HEIGHT / max).toFixed(2);    // 设置相对坐标系

				// 计算每个 marker 的相对坐标
				$.each(markersLeft, function(i, el) {
					el.y = linerfunc * el.y + RULER_Y;
					coordinate[el.marker] = {x1: RULER_X * 1 , y1: el.y *1};
				});

				//按 Y 轴坐标排序
				markersLeft = markersLeft.sort(function (obj1, obj2) {
					return obj1.y - obj2.y;
				});
				drawRuler(markersLeft, "left");
			}
		});
	}

    // 基因标尺的数据整理和绘制
	var promise_inheritance = function () {
		return $.ajax({
			url: RESOURCEURL.markerRight,
			type: "GET",
			dataType: "json",
			success: function(data) {
				var max = data[0][3] * 1 / 1000 / 1000;
				$.each(data, function(i, el) {
					if(el[3] * 1 > max) {
						max = el[3] * 1;
					}
					var tmp = {};
					tmp.x = RULER_X + RULER_GAP + 10;
					tmp.y = el[3] * 1 ;
					tmp.marker = el[0] ;
					markersRight.push(tmp);

				});
				// console.log("MAX:", max);
				var linerfunc = (RULER_HEIGHT / max).toFixed(2);
				// console.log(linerfunc);
				$.each(markersRight, function(i, el) {
					el.y = linerfunc * el.y + RULER_Y;
					if(coordinate[el.marker]) {
						coordinate[el.marker].x2 = (RULER_X + RULER_GAP + 10)*1;
						coordinate[el.marker].y2 = el.y*1;
					} else {
						coordinate[el.marker] = {x2: (RULER_X + RULER_GAP + 10)*1, y2: el.y*1}
					}
				});
				markersRight = markersRight.sort(function (obj1, obj2) {
					var val1 = obj1.y;
					var val2 = obj2.y;
					if (val1 < val2) {
						return -1;
					} else if (val1 > val2) {
						return 1;
					} else {
						return 0;
					}
				});

				drawRuler(markersRight, "right");
			}
		});
	}

	// 物理标尺和基因标尺的数据拉取完后，拉取QTL数据，并将坐标对应到标尺上
	promise_physics().then(function() {
		promise_inheritance().then(function() {
			// 画第一张图两个举行图中的 单位文字
			for (var index = 0; index < 10; index++) {
				var y = (RULER_HEIGHT / 10) * (index + 1) - 45;
				var ruler1 = paper.text(RULER_X + RULER_GAP / 4 * 1, RULER_Y, "< " + 2 * (index + 1) + "Mb >").attr({
					"stroke": "#5c8ce5",
					"font-size": 15
				});
				// ruler1.transform("t0,0r90t" + y + ",0");
				ruler1.transform("t5,"+y+"r90t0,0");

				var ruler2 = paper.text(RULER_X + RULER_GAP / 4 * 3, RULER_Y, "<  " + 10 * (index + 1) + "  >").attr({
					"stroke": "#5c8ce5",
					"font-size": 15
				});
				ruler2.transform("t5,"+y+"r90t0,0");

			}

			//拉取QTL数据
			getQtl();
		});
	});

    //标尺绘制方法，在 promise_physics 和 promise_inheritance 方法中调用
	function drawRuler (data, align) {
		// console.log(data);
		// 画两个竖着的矩形
		// 标尺长 RULER_HEIGHT
		if(align == "left") {
			var c = paper.rect(RULER_X, RULER_Y, RULER_WIDTH, RULER_HEIGHT, 5);
			c.attr({
				"stroke": "#1049A9",
				"fill": "#DDF0ED"
			});
		} else if (align == "right") {
			var c2 = paper.rect(RULER_X + RULER_GAP, RULER_Y, RULER_WIDTH, RULER_HEIGHT, 5);
			c2.attr({
				"stroke": "#1049A9",
				"fill": "#DDF0ED"
			});
		}

		var len = data.length;
		var textHeight = Math.floor(RULER_HEIGHT / len); // 计算每个文字显示高度
		for (var idx = 0; idx < len; idx++) {

			var markerName = data[idx].marker;

			if(align == "left") {
				data[idx].lx = RULER_X - RULER_TIP_WIDTH;
				data[idx].ly = (textHeight / 2) + textHeight * idx + RULER_Y;
				// 左边引导线
				M[markerName+"_left_guide"] = data[idx].lc = paper.path("M" + data[idx].lx + " " + data[idx].ly + "L" + data[idx].x + " " + data[idx].y).attr("stroke", "#BBB");
				// 左边文字
				M[markerName+"_left_label"] = data[idx].lt = paper.text(data[idx].lx - 5, data[idx].ly, markerName)
						.attr({"fill": "#777", "font-size": 11, "font-weight": "bolder", "text-anchor": "end"})
						.data("marker", markerName)
						.click(function(e) {
							e.stopPropagation();
							alert(this.data("marker"));
							<%--window.location.href='${ctxroot}/qtldb/tmp/aboutus?name='+currentClickQtl+'&version=${version}';--%>
						});
			} else if (align == "right") {
				data[idx].rx = data[idx].x + RULER_TIP_WIDTH;
				data[idx].ry = (textHeight / 2) + textHeight * idx + RULER_Y;
				// 右边引导线
				M[markerName+"_right_guide"] = data[idx].rc = paper.path("M" + data[idx].x + " " + data[idx].y + "L" + data[idx].rx + " " + data[idx].ry).attr("stroke", "#BBB");
				// 右边文字
				M[markerName+"_right_label"] = data[idx].rt = paper.text(data[idx].rx + 5, data[idx].ry, markerName)
						.attr({"fill": "#777", "font-size": 11, "font-weight": "bolder","text-anchor": "start"})
						.data("marker", markerName)
						.click(function(e) {
							e.stopPropagation();
							alert(this.data("marker"));
							<%--window.location.href='${ctxroot}/qtldb/tmp/aboutus?name='+currentClickQtl+'&version=${version}';--%>
						});

				// console.log(coordinate);

				var y1 = coordinate[markerName].y1;
				var y2 = data[idx].y;
				if( (y1 != "") && (y2 != "") && (y1 != undefined) && (y2 != undefined)) {
					var str = "M"+ RULER_X +" " + y1 + "L"+ (RULER_X + RULER_GAP + RULER_WIDTH) +" " + y2;
					// 左图横线
					M[markerName+"_line"] = data[idx].el = paper.path(str).attr("stroke", "#666");
				}

			}

		}

	}

    //拉取QTL数据,计算QTL在标尺上的相对坐标
	function getQtl() {
		$.ajax({
			url: RESOURCEURL.getQtl,
			type: "GET",
			dataType: "json",
			success: function(data) {
				for (var i = 0; i < data.length; i++) {
					var marker1 = data[i].marker1;
					var marker2 = data[i].marker2;
					var start = coordinate[marker1] ? (coordinate[marker1].y1 ? Math.round(coordinate[marker1].y1 * 1) : -1 ): -1;
					// start 等于 -1 说明没有改坐标
					if(start == -1) {
						console.log("----------", marker1);
					}
					var end = marker2 ? (coordinate[marker2] ? (coordinate[marker2].y1 ? Math.round(coordinate[marker2].y1 * 1) : -1): -1) : start;
					if(start > end) {
						var tmp = start;
						start = end;
						end = tmp;
					}
					// 同坐标(一个点时，前后+3)
					if(start == end) {
						start = start - 3;
						end = end + 3;
					}
					data[i].start = start;
					data[i].end = end;
				}
				var d = qtlSort(data);
				var dd = qtlSplit(d);
				drawQtls(dd);
//				paper.setViewBox(0, 0, viewBox.width, viewBox.height);
			}
		});
	}


    /**
     *  3、对右边 QTL 进行排序、分组、绘制
     */

	// 排序
	function qtlSort(data) {
		data.sort(function(obj1, obj2) {
			var a = obj1.type;
			var b = obj2.type;
			if(a < b) {
				return -1;
			} else if (a > b) {
				return 1;
			} else {
				return 0;
			}
		});
		return data;
	}

	// 分组
	function qtlSplit(data) {
		var len = data.length;
		// var basic = Math.floor(len / GROUP_NUM);
		// var left = len % GROUP_NUM;

		var arr = [];
		for(var a = 0; a < GROUP_NUM; a++){
			arr[a] = [];
		}

		for (var i = 0; i < len; i++) {
			arr[selectfrom(0, GROUP_NUM-1)].push(data[i]);
		}
		return arr;
	}

	// 画多组qtls
	function drawQtls(data) {
		for (var i = 0; i < data.length; i++) {
			qtl = data[i];
			drawQtl(data[i]);
		}
	}

	// 画一组qtl
	function drawQtl(data) {
		// 假设绘制 n 列
		var columns = []; // 将已经绘制的坐标数组存到columns中

		var len = data.length;
		geneLoop:
				for (var i = 0; i < len; i++) {
					// console.log(data[i]);
					var color = COLORS[data[i].type];
					var start = data[i].start;
					var end = data[i].end;
//					 console.log(i,  "("+ start + "," + end + ")");
					// 忽略异常坐标
					if(start < 0 || end < 0 || data[i].type == "NEW TYPE") {
						console.log(data[i]);
						continue;
					}

					var clen = columns.length;
					if(clen == 0) {
						columns.push([]);
						drawLine(0, data[i], color, i);
						columns[0].push([start, end]);
					} else {
						colLoop:
								for (var n = 0; n < clen; n++) {
									var nlen = columns[n].length;
									if(!nlen) { // n列中没有绘制坐标段
										drawLine(n, data[i], color, i);
										columns[n].push([start, end]);
										break;
									} else {
										var trueCount = 0;
										itemLoop:
												for(var nn = 0; nn < nlen; nn++) {
													var a = columns[n][nn][0];
													var b = columns[n][nn][1];
													// 当前坐标不能在已绘制坐标段中
													if( (start >= a && start <= b) || (end >= a && end <= b) || (start <= a && end >= b)) { // 判断在此范围里
														if( nn + 1 == nlen) { // 当前坐标不能绘制在n列中，则要创建一个新列
															if(!columns[n+1]) {
																columns.push([]);
																clen = columns.length;
															}
															continue;
														}
														continue;
													} else {
														trueCount++;
													}

													if( (nn + 1 == nlen) && (trueCount == nlen)) { // 坐标不在范围里
														drawLine(n, data[i], color, i);
														columns[n].push([start, end]);
														break colLoop;
													} else if ((nn + 1 == nlen) && (trueCount != nlen)) {
														if(!columns[n+1]) {
															columns.push([]);
															clen = columns.length;
														} else {
															continue;
														}
													}

												}
									}
								}

					}

				}
		// 竖线画完后再画qtl文字
		var col = columns.length;
		drawText(data, col);
		BASIC_WIDTH = BASIC_WIDTH + 300 + COL_WIDTH * col; // 画完当前内容后估算下一个图的起始x坐标

		// console.log("================Total:", count, "============basic-width:", BASIC_WIDTH);
	}

	// 写qtl文字，在 drawQtl 方法中调用
	function drawText(data, col) {

        data.sort(function (obj1, obj2) {
            return obj1.start - obj2.start
        });


		var len = data.length;
		// console.log("Text Count:", len);
		var textHeight = Math.floor((RULER_HEIGHT) / len);

		// 根据列数col计算文字x坐标
		var textX = COL_WIDTH * col + BASIC_WIDTH + 20 ;
		for (var i = 0; i < len; i++) {
			var key = data[i]["qtlName"];
			var color = data[i].color;

			var start = data[i].start * 1;
			var end = data[i].end *1;
			console.log(i,  "("+ start + "," + end + ")");
			// 忽略异常坐标
			if(start < 0 || end < 0 || data[i].type == "NEW TYPE") {
				console.log(key, "qtl text");
				continue;
			}
			// 文字
			Q[key + "_lable"] = paper.text(textX, (textHeight*i + RULER_Y), data[i]["qtlName"])
					.attr({"fill": color, "text-anchor": "start", "font-size": 11, "font-weight": "bolder"})
					.data("marker", data[i].marker1 + (data[i].marker2 ? ("|" + data[i].marker2):""))
					.data("key", key)
					.click(function(e) {
						e.stopPropagation();
						restoreStyle();

						var markers = this.data("marker").split("|");
						currentClickMarker = markers;

						var key = this.data("key");
						var tmp = key;
						if(tmp == currentClickQtl) {
							window.location.href='${ctxroot}/search/aboutus?name='+currentClickQtl+'&version=${version}';
						}
						currentClickQtl = key;

						Q[key + "_line"].g = Q[key + "_line"].glow({color: GLOW_COLOR});
						Q[key + "_guide"].g = Q[key + "_guide"].glow({color: GLOW_COLOR});
						Q[key + "_top_circle"].g = Q[key + "_top_circle"].glow({color: GLOW_COLOR});
						Q[key + "_bottom_circle"].g = Q[key + "_bottom_circle"].glow({color: GLOW_COLOR});
						Q[key + "_lable"].attr({"font-size": 16});

						for (var i = 0; i < markers.length; i++) {
							var marker = markers[i];
							if(marker != "undefined") {
								console.log("click marker", marker);
								if(M[marker+"_line"]) {
									M[marker+"_line"].g = M[marker+"_line"].glow({color: GLOW_COLOR});
								}
								if(M[marker+"_left_label"]) {
									M[marker+"_left_label"].attr({"fill": GLOW_COLOR, "font-size": 16});
								}
								if(M[marker+"_right_label"]) {
									M[marker+"_right_label"].attr({"fill": GLOW_COLOR, "font-size": 16});
								}
								if(M[marker+"_left_guide"]) {
									M[marker+"_left_guide"].g = M[marker+"_left_guide"].glow({color: GLOW_COLOR});
								}
								if(M[marker+"_right_guide"]) {
									M[marker+"_right_guide"].g = M[marker+"_right_guide"].glow({color: GLOW_COLOR});
								}

							}
						}
					});

			// qtl引导线
			var my = start + Math.floor((end-start) / 2);
			var x = data[i].x * 1;
			Q[key + "_guide"] = paper.path("M" + x + " " + my + "L" + textX + " " + (textHeight*i + RULER_Y)).attr({
				"stroke": "#BBB"
			});
		}
	}
	// 画qtl竖线，在 drawQtl 方法中调用
	function drawLine(n, data, color, i) {
		var key = data["qtlName"];

		var start = data.start;
		var end = data.end;

		var x = BASIC_WIDTH + 50 + COL_WIDTH * n ;
		qtl[i].x = x; // 竖线的x坐标，存起来供后面画文字使用
		qtl[i].color = color;

		var str = "M" + x + " " + start + "L" + x + " " + end;
		// console.log(str);
		// 竖线
		Q[key + "_line"] = paper.path(str).attr({
			"stroke": color,
			"stroke-width": 2
		}).data("marker", data.marker1 + "|" + data.marker2)
				.click(function() {
					alert(this.data("marker"));
				});
		count++;

		// 上下圆点
		Q[key + "_top_circle"] = paper.circle(x, start, 3).attr({
			"stroke": color,
			"fill": color
		});
		Q[key + "_bottom_circle"] = paper.circle(x, end, 3).attr({
			"stroke": color,
			"fill": color
		});

	}

	function restoreStyle() {
		if(currentClickQtl) {
			var key = currentClickQtl;
			Q[key + "_line"].g ? Q[key + "_line"].g.remove() : "";
			Q[key + "_top_circle"].g ? Q[key + "_top_circle"].g.remove() : "";
			Q[key + "_bottom_circle"].g ? Q[key + "_bottom_circle"].g.remove() : "";
			Q[key + "_guide"].g ? Q[key + "_guide"].g.remove() : "";
			Q[key + "_lable"].attr({"font-size": 11});

		}

		if(currentClickMarker[0]) {
			for (var i = 0; i < currentClickMarker.length; i++) {
				var marker = currentClickMarker[i];
				if(marker != "undefined") {
					M[marker+"_line"] ? M[marker+"_line"].g.remove(): "";
					M[marker+"_left_label"] ? M[marker+"_left_label"].attr({"fill":"#777", "font-size": 11}): "";
					M[marker+"_right_label"] ? M[marker+"_right_label"].attr({"fill":"#777", "font-size": 11}): "";
					M[marker+"_left_guide"] ? M[marker+"_left_guide"].g.remove() : "";
					M[marker+"_right_guide"] ? M[marker+"_right_guide"].g.remove() : "";
				}
			}

		}
	}

	document.onclick = function() {
		restoreStyle();
		currentClickQtl = "";
		currentClickMarker = [];
	}

</script>
</body>

</html>