<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>基因图谱</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <script src="http://cdn.bootcss.com/jquery/2.2.2/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/raphael/2.2.7/raphael.min.js"></script>
    <style>
        html {
            overflow-y: hidden;
            cursor: -webkit-grab;
            cursor: grab;
        }
        html, body {
            margin: 0;
            padding: 0;
        }
        body{
            position: absolute;
            width: 100%;
            height: 100%;
        }
        text, .qtl-line, .qtl-dot {
            cursor: pointer;
        }
        text.text {
            cursor: default;
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

        .map {
            display: inline-table;
            width: 140px;
            margin: 15px 10px 0;
            padding: 5px;
            text-align: left;
        }
        .map.phy {
            background: #e5edff;
            margin-left: 25px;
            text-align: center;
            color: #333333;
        }
        .map.gen {
            background: #fff8e5;
            text-align: center;
            color: #333333;
        }
        .phy-ver, .gen-ver {
            color: #999999;
            font-size: 10px;
            word-break: break-all;
            height: 32px;
            display: inline-block;
        }
        #illustrate {
            position: absolute;
            top: 1770px;
            left: 64px;
            font-size: 11px;
            color: #666;
            display: inline-block;
            width: 260px;
        }

    </style>
</head>

<body>
<div class="map phy">
    <div>PHYSICAL MAP</div>
    <div class="phy-ver"></div>
</div>

<div class="map gen">
    <div>GENETIC MAP</div>
    <div class="gen-ver">Soybean-GmComposite2003</div>
</div>

<div id="illustrate">
    单 Marker 定位的 QTL 以 Marker 为中心上下各 1cM 的区域作图，不代表此 QTL 的真实区域。
</div>
<script>

    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
    }
    var version = getUrlParam("version");
    $(".phy-ver").html(version);

    var RESOURCEURL = {
        markerLeft: "${ctxroot}/getMarkerLeft?chr=${chr}&version=${version}",
        markerRight: "${ctxroot}/getMarkerRight?markerlg=${markerlg}",
        getQtl: "${ctxroot}/getQtlByLg?lg=${lg}"
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
     *    物理标尺数据（markerLeft）来源： /getMarkerLeft?chr=${chr}&version=${version}
     *    基因标尺数据（markerRight）来源： /markerRight?cmarkerlg=${markerlg}
     *    qtl数据（getQtl）来源：/getQtlByLg?lg=${lg}
     *
     *  思路：
     *    1、数据整理和标识绘制
     *        a)    拉取标尺的数据，并计算相对坐标系，详见：promise_physics、promise_inheritance 方法
     *        b)    绘制左边标尺，详见：drawRuler 方法
     *    2、绘制右边QTL图
     *        a)    先抽象绘制一个 QTL group 的方法 （drawQtl），每一组包括 多个QTL竖线和名称的连线
     *        b)    可通过多种维度对 QTL 进行分组、排序
     * @type {number}
     */


    /**
     *  1、初始化画布 和 一些常量
     */

    // 设置画布大小和起始坐标
    // 设置左边标尺画布参数
    var PAPER_HEIGHT = 1900, PAPER_WIDTH = 360, PAPER_X = 0, PAPER_Y = 80;
    // 设置右边QTLS画布参数
    var PAPER_HEIGHT2 = 1800, PAPER_WIDTH2 = 500, PAPER_X2 = 360, PAPER_Y2 = 80;
    // 设置基因图大小和起始坐标，RULER_GAP:两个轴间隙，RULER_TIP_WIDTH:marker宽度
    var RULER_HEIGHT = PAPER_HEIGHT - 250, 		//标尺的高度
            RULER_WIDTH = 10, 						//标尺宽度
            RULER_X = 150, RULER_Y = 20, 			//标尺的起始位置
            RULER_GAP = 60, 						//左右标尺间距
            RULER_TIP_WIDTH = 30;					//marker文字宽度

    var COL_WIDTH = 10;							// QTL图中 多条QTL的间距

    var GLOW_COLOR = "red";						//选中高亮颜色

    var GROUP_NUM = 2; 							// 分组数
    var BASIC_WIDTH = 0; // 画qlt图的基础x坐标，可变的宽度

    var qtls;                                   // 保存所有qtl数据
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

    // 获取需要绘制的QTL类型，默认所有，在drawQtl 和 drawText 方法中使用
    var typesArray = [];
    for(t in COLORS) {
        typesArray.push(t);
    }
    var types = typesArray.join(",");

    // 初始化画布
    // 标尺画布
    var paper = Raphael(PAPER_X, PAPER_Y, PAPER_WIDTH, PAPER_HEIGHT);
    paper.clear();
    // QTL画布
    var paper2 = Raphael(PAPER_X2, PAPER_Y2, PAPER_WIDTH2, PAPER_HEIGHT2);
    paper2.clear();

    /**
     *  2、数据整理和标识绘制
     */

    // 物理标尺的数据整理和绘制
    var promise_physics = function () {
        return $.ajax({
            url: RESOURCEURL.markerLeft,
            type: "GET",
            dataType: "json",
            success: function (data) {
                var max = (data[0][3] * 1 / 1000 / 1000 + data[0][4] * 1 / 1000 / 1000) / 2;
                $.each(data, function (i, el) {
                    var yaxis = (el[3] * 1 / 1000 / 1000 + el[4] * 1 / 1000 / 1000) / 2;
                    if (yaxis > max) {
                        max = yaxis;
                    }
                    var tmp = {};
                    tmp.x = RULER_X;
                    tmp.y = yaxis;
                    tmp.marker = el[0];
                    markersLeft.push(tmp);     //左边标尺的坐标
                });
//                console.log("MAX:", max);
                var linerfunc = ((RULER_HEIGHT-1) / max).toFixed(2);    // 设置相对坐标系
//                console.log(linerfunc);
                // 计算每个 marker 的相对坐标
                $.each(markersLeft, function (i, el) {
                    el.y = linerfunc * el.y + RULER_Y;
                    coordinate[el.marker] = {x1: RULER_X * 1, y1: el.y * 1};
                });

                //按 Y 轴坐标排序
                markersLeft = markersLeft.sort(function (obj1, obj2) {
                    return obj1.y - obj2.y;
                });
                drawRuler(markersLeft, "left");

                var scale_num = Math.ceil( Math.floor(max/5));
                for (var index = 0; index < scale_num; index++) {
                    var scale_text = paper.text(RULER_X + RULER_GAP / 4 * 1, linerfunc * (5 * (index + 1) - 1) + RULER_Y, "< " + 5 * (index + 1) + "Mb >").attr({
                        "stroke": "#5c8ce5",
                        "font-size": 15,
                        "class": "text"
                    });
                    scale_text.transform("t5,0r90t0,0");
                }
            }
        });
    }

    var linear_num;
    // 基因标尺的数据整理和绘制
    var promise_inheritance = function () {
        return $.ajax({
            url: RESOURCEURL.markerRight,
            type: "GET",
            dataType: "json",
            success: function (data) {
                var max = data[0][3] * 1;
                $.each(data, function (i, el) {
                    if (el[3] * 1 > max) {
                        max = el[3] * 1;
                    }
                    var tmp = {};
                    tmp.x = RULER_X + RULER_GAP + 10;
                    tmp.y = el[3] * 1;
                    tmp.marker = el[0];
                    markersRight.push(tmp);
                });
//                 console.log("MAX:", max);
                var linerfunc = linear_num = ((RULER_HEIGHT-1) / max).toFixed(2);
//                 console.log(linerfunc);
                $.each(markersRight, function (i, el) {
                    el.y = linerfunc * el.y + RULER_Y;
                    if (coordinate[el.marker]) {
                        coordinate[el.marker].x2 = (RULER_X + RULER_GAP + 10) * 1;
                        coordinate[el.marker].y2 = el.y * 1;
                    } else {
                        coordinate[el.marker] = {x2: (RULER_X + RULER_GAP + 10) * 1, y2: el.y * 1}
                    }
                });
                markersRight = markersRight.sort(function (obj1, obj2) {
                    return obj1.y - obj2.y;
                });

                drawRuler(markersRight, "right");

                var scale_num = Math.ceil( Math.floor(max/10) * 10 / 10);
                for (var index = 0; index < scale_num; index++) {
                    var scale_text = paper.text(RULER_X + RULER_GAP / 4 * 3, linear_num * (10 * (index + 1) - 5) + RULER_Y, "< " + 10 * (index + 1) + " cM >").attr({
                        "stroke": "#5c8ce5",
                        "font-size": 15,
                        "class": "text"
                    });
                    scale_text.transform("t5,0r90t0,0");
                }
            }
        });
    }

    // 物理标尺和基因标尺的数据拉取完后，拉取QTL数据，并将坐标对应到标尺上
    promise_physics().then(function () {
        promise_inheritance().then(function () {
            //拉取QTL数据
            getQtl().then(function() {

                // 加入是从其他页面查询qtl跳转过来，则定位qtl到显示中央

                var QTLNameSearch = getUrlParam("qtl");
                if(QTLNameSearch) {
                    searchQtl(QTLNameSearch);
                    var h = Q[QTLNameSearch].lable.attrs.y;
                    var windowHeight = $(top.window).height();
                    if( (h + 370) > windowHeight) {
                        $(top.window).scrollTop((h + 370) - windowHeight + (windowHeight/2));
                    }
                }

            });
        });
    });

    //标尺绘制方法，在 promise_physics 和 promise_inheritance 方法中调用
    function drawRuler(data, align) {
        // 画两个竖着的矩形
        // 标尺长 RULER_HEIGHT
        if (align == "left") {
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


            if (align == "left") {
                M[markerName] = {};
                data[idx].lx = RULER_X - RULER_TIP_WIDTH;
                data[idx].ly = (textHeight / 2) + textHeight * idx + RULER_Y;
                // 左边引导线
                M[markerName]["left_guide"] = data[idx].lc = paper.path("M" + data[idx].lx + " " + data[idx].ly + "L" + data[idx].x + " " + data[idx].y).attr("stroke", "#BBB");
                // 左边短横线
                M[markerName]["left_line"] = paper.path("M" + data[idx].x + " " + data[idx].y + "L" + (data[idx].x + RULER_WIDTH) + " " + data[idx].y).attr("stroke", "#777");
                // 左边文字
                M[markerName]["left_label"] = data[idx].lt = paper.text(data[idx].lx - 5, data[idx].ly, markerName)
                        .attr({"fill": "#777", "font-size": 9, "font-weight": "bolder", "text-anchor": "end"})
                        .data("marker", markerName)
                        .click(function (e) {
                            e.stopPropagation();
                            var arr = [];
                            arr.push(this.data("marker"));
                            if(arr[0] == currentClickMarker[0] || arr[0] == currentClickMarker[1]) { // 高亮后点击
                                top.showMarkerInfo(this.data("marker"));
                            } else {
                                restoreStyle();
                                chooseQtl(arr, "");
                                currentClickMarker = arr;
                            }
                        });
            } else if (align == "right") {
                data[idx].rx = data[idx].x + RULER_TIP_WIDTH;
                data[idx].ry = (textHeight / 2) + textHeight * idx + RULER_Y;
                if(!M[markerName]) {
                    M[markerName] = {};
                }
                // 右边引导线
                M[markerName]["right_guide"] = data[idx].rc = paper.path("M" + data[idx].x + " " + data[idx].y + "L" + data[idx].rx + " " + data[idx].ry).attr("stroke", "#BBB");
                // 右边短横线
                M[markerName]["right_line"] = paper.path("M" + (data[idx].x - RULER_WIDTH) + " " + data[idx].y + "L" + data[idx].x + " " + data[idx].y).attr("stroke", "#777");
                // 右边文字
                M[markerName]["right_label"] = data[idx].rt = paper.text(data[idx].rx + 5, data[idx].ry, markerName)
                        .attr({"fill": "#777", "font-size": 9, "font-weight": "bolder", "text-anchor": "start"})
                        .data("marker", markerName)
                        .click(function (e) {
                            e.stopPropagation();
                            var arr = [];
                            arr.push(this.data("marker"));
                            if(arr[0] == currentClickMarker[0] || arr[0] == currentClickMarker[1]) { // 高亮后点击
                                top.showMarkerInfo(this.data("marker"));
                            } else {
                                restoreStyle();
                                chooseQtl(arr, "");
                                currentClickMarker = arr;
                            }

                        });


                var y1 = coordinate[markerName].y1;
                var y2 = data[idx].y;
                if ((y1 != "") && (y2 != "") && (y1 != undefined) && (y2 != undefined)) {
                    var str = "M" + (RULER_X + RULER_WIDTH) + " " + y1 + "L" + (RULER_X + RULER_GAP) + " " + y2;
                    // 两个marker连接线
                    M[markerName]["line"] = data[idx].el = paper.path(str).attr("stroke", "#666");
                }

            }

        }

    }

    //拉取QTL数据,计算QTL在标尺上的相对坐标
    function getQtl() {
        return $.ajax({
            url: RESOURCEURL.getQtl,
            type: "GET",
            dataType: "json",
            success: function (data) {
                for (var i = 0; i < data.length; i++) {
                    var marker1 = data[i].marker1;
                    var marker2 = data[i].marker2;
                    var start = coordinate[marker1] ? (coordinate[marker1].y2 ? Math.round(coordinate[marker1].y2 * 1) : -1 ) : -1;
                    // start 等于 -1 说明没有该坐标
//                    if (start == -1) {
//						console.log("----------", marker1);
//                    }
                    var end = marker2 ? (coordinate[marker2] ? (coordinate[marker2].y2 ? Math.round(coordinate[marker2].y2 * 1) : -1) : -1) : start;
                    if (start > end) {
                        var tmp = start;
                        start = end;
                        end = tmp;
                    }
                    // 同坐标(一个点时，实际值前后+1再换算成相对坐标)
                    if (start == end && (start != -1 || end != -1)) {
                        var y0 = (start - RULER_Y) / linear_num;
                        start = (y0 - 1) * linear_num + RULER_Y;
                        end = (y0 + 1) * linear_num + RULER_Y;
                        start = (start < RULER_Y) ? RULER_Y : start;
                        end = (end > RULER_HEIGHT) ? RULER_HEIGHT : end;
                    }
                    data[i].start = start;
                    data[i].end = end ;
                }

                var d = qtlSort(data);
                var dd = qtlSplit(d);
                qtls = data;
                drawQtls(dd);

            }
        });
    }


    /**
     *  3、对右边 QTL 进行排序、分组、绘制
     */

    // 排序
    function qtlSort(data) {
        data.sort(function (obj1, obj2) {
            return obj1.type - obj2.type;
        });
        return data;
    }

    // 分组
    function qtlSplit(data) {
        var len = data.length;
        var basic = Math.ceil(len / GROUP_NUM);
        var arr = [];
        for (var a = 0; a < GROUP_NUM; a++) {
            var tmp = [];
            tmp = data.concat([]);
            if(a != (GROUP_NUM-1)) {
                arr[a] = tmp.splice(basic*a , basic*(a+1));
            } else {
                arr[a] = tmp.splice(basic*a , len);
            }
        }
        return arr;
    }

    // 画多组qtls
    function drawQtls(data) {
        paper2.clear();
        for (var i = 0; i < data.length; i++) {
            qtl = data[i];
            qtl.sort(function (obj1, obj2) {
                return (obj1.start + obj1.end) - (obj2.start + obj2.end)
            });
            drawQtl(qtl);
        }
        paper2.setSize(BASIC_WIDTH + 50, PAPER_HEIGHT);
//        viewBox.width = basicWidth;
//        paper.setViewBox(0, 0, BASIC_WIDTH + 400 , PAPER_HEIGHT + 400);
    }

    // 画一组qtl
    function drawQtl(data) {
//        data.sort(function (obj1, obj2) {
//            return obj1.start - obj2.start
//        });

        // 假设绘制 n 列
        var columns = []; // 将已经绘制的坐标数组存到columns中

        var len = data.length;
        geneLoop:
                for (var i = 0; i < len; i++) {
                    // console.log(data[i]);
                    var type = data[i].type;
                    var color = COLORS[type];
                    if( types.indexOf(type) < 0) {
                        continue;
                    }
                    var start = data[i].start;
                    var end = data[i].end;
                    // 忽略异常坐标
                    if (start < 0 || end < 0 || data[i].type == "NEW TYPE") {
//						console.log(data[i]["chr"],"|",data[i]["lg"],"|",data[i]["qtlName"],"|",data[i]["type"],"|",data[i]["marker1"],"|",data[i]["marker2"]);
                        continue;
                    }

                    var clen = columns.length;
                    if (clen == 0) {
                        columns.push([]);
                        drawLine(0, data[i], color, i);
                        columns[0].push([start, end]);
                    } else {
                        colLoop:
                                for (var n = 0; n < clen; n++) {
                                    var nlen = columns[n].length;
                                    if (!nlen) { // n列中没有绘制坐标段
                                        drawLine(n, data[i], color, i);
                                        columns[n].push([start, end]);
                                        break;
                                    } else {
                                        var trueCount = 0;
                                        itemLoop:
                                                for (var nn = 0; nn < nlen; nn++) {
                                                    var a = columns[n][nn][0];
                                                    var b = columns[n][nn][1];
                                                    // 当前坐标不能在已绘制坐标段中
                                                    if ((start >= a && start <= b) || (end >= a && end <= b) || (start <= a && end >= b)) { // 判断在此范围里
                                                        if (nn + 1 == nlen) { // 当前坐标不能绘制在n列中，则要创建一个新列
                                                            if (!columns[n + 1]) {
                                                                columns.push([]);
                                                                clen = columns.length;
                                                            }
                                                            continue;
                                                        }
                                                        continue;
                                                    } else {
                                                        trueCount++;
                                                    }

                                                    if ((nn + 1 == nlen) && (trueCount == nlen)) { // 坐标不在范围里
                                                        drawLine(n, data[i], color, i);
                                                        columns[n].push([start, end]);
                                                        break colLoop;
                                                    } else if ((nn + 1 == nlen) && (trueCount != nlen)) {
                                                        if (!columns[n + 1]) {
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
        BASIC_WIDTH = BASIC_WIDTH + 240 + COL_WIDTH * col; // 画完当前内容后估算下一个图的起始x坐标
    }

    // 写qtl文字，在 drawQtl 方法中调用
    function drawText(data, col) {

        data.sort(function (obj1, obj2) {
            return obj1.mid - obj2.mid
        });


        var len = data.length;
        var textHeight = ((RULER_HEIGHT) / len);

        // 根据列数col计算文字x坐标
        var textX = COL_WIDTH * col + BASIC_WIDTH + 50;
        for (var i = 0; i < len; i++) {
            if( types.indexOf(data[i].type) < 0) {
                continue;
            }
            var key = data[i]["qtlName"];
            var color = data[i].color;

            var start = data[i].start * 1;
            var end = data[i].end * 1;
            // 忽略异常坐标
            if (start < 0 || end < 0 || data[i].type == "NEW TYPE") {
                // 文字
                <%--Q[key]["lable"] = paper2.text(textX, (textHeight * i + RULER_Y), data[i]["qtlName"])--%>
                        <%--.attr({--%>
                            <%--"fill": COLORS[data[i].type],--%>
                            <%--"text-anchor": "start",--%>
                            <%--"font-size": 9,--%>
                            <%--"font-weight": "bolder"--%>
                        <%--})--%>
                        <%--.data("marker", data[i].marker1 + (data[i].marker2 ? ("|" + data[i].marker2) : ""))--%>
                        <%--.data("key", key)--%>
                        <%--.click(function (e) {--%>
                            <%--e.stopPropagation();--%>
                            <%--restoreStyle();--%>
                            <%--var key = this.data("key");--%>
                            <%--var tmp = key;--%>
                            <%--if (tmp == currentClickQtl) {--%>
                                <%--window.top.location.href = '${ctxroot}/tmp/aboutus?name=' + currentClickQtl + '&version=${version}';--%>
                            <%--}--%>
                            <%--currentClickQtl = key;--%>

                            <%--chooseQtl([], key);--%>

                        <%--});--%>
                continue;
            }
            Q[key]["color"] = color;
            // 文字
            Q[key]["lable"] = paper2.text(textX, (textHeight * i + RULER_Y), data[i]["qtlName"])
                    .attr({"fill": color, "text-anchor": "start", "font-size": 9, "font-weight": "bolder"})
                    .data("marker", data[i].marker1 + (data[i].marker2 ? ("|" + data[i].marker2) : ""))
                    .data("key", key)
                    .click(function (e) {
                        e.stopPropagation();
                        restoreStyle();

                        var markers = this.data("marker").split("|");
                        currentClickMarker = markers;

                        var key = this.data("key");
                        var tmp = key;
                        if (tmp == currentClickQtl) {
                            <%--window.top.location.href = '${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}';--%>
                            window.open('${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}');
                        }
                        currentClickQtl = key;

                        chooseQtl(markers, key);
                    });

            // qtl引导线
            var my = start + Math.floor((end - start) / 2);
            var x = data[i].x * 1;
            Q[key]["guide"] = paper2.path("M" + x + " " + my + "L" + textX + " " + (textHeight * i + RULER_Y)).attr({
                "stroke": "#BBB"
            });
        }
    }
    // 画qtl竖线，在 drawQtl 方法中调用
    function drawLine(n, data, color, i) {
        var key = data["qtlName"];

        var start = data.start;
        var end = data.end;

        var x = BASIC_WIDTH + 50 + COL_WIDTH * n;
        qtl[i].x = x; // 竖线的x坐标，存起来供后面画文字使用
        qtl[i].color = color;
        qtl[i].mid = ((start + end) / 2).toFixed(2);

        var str = "M" + x + " " + start + "L" + x + " " + end;
        if(!Q[key]) {
            Q[key] = {};
        }
        // 竖线
        Q[key]["line"] = paper2.path(str).attr({
            "stroke": color,
            "stroke-width": 2,
            "class": "qtl-line"
        })
        .data("marker", data.marker1 + (data.marker2 ? ("|" + data.marker2) : ""))
        .data("key", key)
        .click(function (e) {
            e.stopPropagation();
            restoreStyle();

            var markers = this.data("marker").split("|");
            currentClickMarker = markers;

            var key = this.data("key");
            var tmp = key;
            if (tmp == currentClickQtl) {
                window.open('${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}');
                <%--window.top.location.href = '${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}';--%>
            }
            currentClickQtl = key;

            chooseQtl(markers, key);
//            top.showMarkerInfo(this.data("marker"));
        });

        // 上下圆点
        Q[key]["top_circle"] = paper2.circle(x, start, 2).attr({
            "stroke": color,
            "fill": color,
            "class": "qtl-dot"
        })
        .data("marker", data.marker1 + (data.marker2 ? ("|" + data.marker2) : ""))
        .data("key", key)
        .click(function (e) {
            e.stopPropagation();
            restoreStyle();

            var markers = this.data("marker").split("|");
            currentClickMarker = markers;

            var key = this.data("key");
            var tmp = key;
            if (tmp == currentClickQtl) {
                <%--window.top.location.href = '${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}';--%>
                window.open('${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}');
            }
            currentClickQtl = key;

            chooseQtl(markers, key);
//            top.showMarkerInfo(this.data("marker"));
        });
        Q[key]["bottom_circle"] = paper2.circle(x, end, 2).attr({
            "stroke": color,
            "fill": color,
            "class": "qtl-dot"
        })
        .data("marker", data.marker1 + (data.marker2 ? ("|" + data.marker2) : ""))
        .data("key", key)
        .click(function (e) {
            e.stopPropagation();
            restoreStyle();

            var markers = this.data("marker").split("|");
            currentClickMarker = markers;

            var key = this.data("key");
            var tmp = key;
            if (tmp == currentClickQtl) {
                <%--window.top.location.href = '${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}';--%>
                window.open('${ctxroot}/search/aboutus?name=' + currentClickQtl + '&version=${version}');
            }
            currentClickQtl = key;

            chooseQtl(markers, key);
//            top.showMarkerInfo(this.data("marker"));
        });

    }

    function restoreStyle() {
        if (currentClickQtl) {
            var key = currentClickQtl;
            var color = Q[key].color;
            Q[key]["line"] ?  Q[key]["line"].attr({"stroke-width": 2, "stroke": color }): "";
            Q[key]["guide"] ? Q[key]["guide"].attr({"stroke": "#BBB", "stroke-width": 1}) : "";
            Q[key]["lable"] ? Q[key]["lable"].attr({"font-size": 9, "fill": color }) : "";

        }

        if (currentClickMarker[0]) {
            for (var i = 0; i < currentClickMarker.length; i++) {
                var marker = currentClickMarker[i];
                if (marker != "undefined") {
                    M[marker]["line"] ? M[marker]["line"].attr({"stroke": "#666"}) : "";
                    M[marker]["left_label"] ? M[marker]["left_label"].attr({"fill": "#777", "font-size": 9}) : "";
                    M[marker]["right_label"] ? M[marker]["right_label"].attr({
                        "fill": "#777",
                        "font-size": 9
                    }) : "";
                    M[marker]["left_guide"] ? M[marker]["left_guide"].attr({"stroke": "#BBB"}) : "";
                    M[marker]["right_guide"] ? M[marker]["right_guide"].attr({"stroke": "#BBB"}) : "";
                    M[marker]["left_line"] ? M[marker]["left_line"].attr({"stroke": "#777"}) : "";
                    M[marker]["right_line"] ? M[marker]["right_line"].attr({"stroke": "#777"}) : "";
                }
            }

        }
    }

    // 点击高亮
    function chooseQtl(markers, key) {
        if(searchQTLName) {
            searchQtl(searchQTLName);
        }
        if(!!key) {
            Q[key]["line"] ? Q[key]["line"].attr({"stroke-width": 3, "stroke": GLOW_COLOR }) : "";
            Q[key]["guide"] ? Q[key]["guide"].attr({"stroke": GLOW_COLOR, "stroke-width": 2}) : "";
            Q[key]["lable"] ? (Q[key]["lable"].attr({"font-size": 12, "fill": GLOW_COLOR})) : "";
        }

        for (var i = 0; i < markers.length; i++) {
            var marker = markers[i];
            if (marker != "undefined") {
                if (M[marker]["line"]) {
                    M[marker]["line"].attr({"stroke": GLOW_COLOR});
                }
                if (M[marker]["left_label"]) {
                    M[marker]["left_label"].attr({"fill": GLOW_COLOR});
                }
                if (M[marker]["right_label"]) {
                    M[marker]["right_label"].attr({"fill": GLOW_COLOR});
                }
                if (M[marker]["left_guide"]) {
                     M[marker]["left_guide"].attr({"stroke": GLOW_COLOR});
                }
                if (M[marker]["right_guide"]) {
                    M[marker]["right_guide"].attr({"stroke": GLOW_COLOR});
                }
                if (M[marker]["left_line"]) {
                    M[marker]["left_line"].attr({"stroke": GLOW_COLOR});
                }
                if (M[marker]["right_line"]) {
                    M[marker]["right_line"].attr({"stroke": GLOW_COLOR});
                }

            }
        }
    }

    document.body.ondblclick = function (e) {
        e.stopPropagation();
        restoreStyle();
        restoreSearch();
        searchQTLName = "";
        currentClickQtl = "";
        currentClickMarker = [];
    }

    // 画布内文字搜索高亮
    var findQTL = [], findMarker = [];
    var searchQTLName;
    function searchQtl(searchValue) {
        searchQTLName = searchValue;
        restoreSearch();
        if(searchValue) {

            searchValue = searchValue.toLowerCase();
            for (var qtl in Q) {
                if (qtl.toLowerCase().indexOf(searchValue) > -1) {
                    findQTL.push(qtl);
                    Q[qtl]["lable"] ? (Q[qtl]["lable"].attr({"font-size": 12, "fill": GLOW_COLOR})) : "";
                }
            }

            for (var marker in M) {
                if(marker.toLowerCase().indexOf(searchValue) > -1) {
                    findMarker.push(marker);
                    if (M[marker]["left_label"]) {
                        M[marker]["left_label"].attr({"fill": GLOW_COLOR});
                    }
                    if (M[marker]["right_label"]) {
                        M[marker]["right_label"].attr({"fill": GLOW_COLOR});
                    }
                }
            }

        }
    }

    function restoreSearch() {
        for(var i in findQTL) {
            Q[findQTL[i]]["lable"] ? (Q[findQTL[i]]["lable"].attr({"font-size": 9, "fill": Q[findQTL[i]]["color"]})) : "";
        }
        findQTL = [];
        for(var i in findMarker) {
            var marker = findMarker[i];
            M[marker]["left_label"] ? M[marker]["left_label"].attr({"fill": "#777", "font-size": 9}) : "";
            M[marker]["right_label"] ? M[marker]["right_label"].attr({"fill": "#777", "font-size": 9}) : "";
        }
        findMarker = [];
    }

    // 重绘QTLs 供外部页面调用
    function redrawQtls(type) {
        BASIC_WIDTH = 0;
        types = type;
        var d = qtlSplit(qtlSort(qtls));
        drawQtls(d);
    }

    /*
     * 拖拽 Start
     */
    var isStart = false;
    var x1, x2;
    var offsetWidth = 0, lastLeft = 0;

    document.body.addEventListener("mouseleave", function(e) {
        e.stopPropagation();
        x2 = e.x;
        $("body").css("left", offsetWidth + lastLeft +"px");
        $("body").css("transform", "translateX(0px)");
        lastLeft = lastLeft + offsetWidth;
        offsetWidth = 0;
        isStart = false;
//        console.log("leave");
    });

    document.body.addEventListener("mousedown", function(e) {
        e.stopPropagation();
        if(!isStart) {
            isStart = true;
            x1 = e.x;
//            console.log("x1:", x1);
        }
    });
    document.body.addEventListener("mousemove", function(e) {
        e.stopPropagation();
        if (isStart) {
            x2 = e.x;
            offsetWidth = x2 - x1;
            console.log('offsetWidth : '+offsetWidth);
            $("body").css("transform", "translateX("+ offsetWidth +"px)");
        }
    });
    document.body.addEventListener("mouseup", function(e) {
        e.stopPropagation();
        x2 = e.x;
        $("body").css("left", offsetWidth + lastLeft +"px");
        $("body").css("transform", "translateX(0px)");
        lastLeft = lastLeft + offsetWidth;
        offsetWidth = 0;
//        console.log("x2:", x2);
//        console.log(offsetWidth);
        isStart = false;
    });
    /*
     * 拖拽 End
     */

</script>
</body>

</html>