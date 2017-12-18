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
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">

	<link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
	<!--jquery-1.11.0-->
	<script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
	<script src="${ctxStatic}/js//highcharts/highcharts.js"></script>
	<script src="${ctxStatic}/js/highcharts/highcharts-more.js"></script>
	<script src="${ctxStatic}/js/highcharts/exporting.js"></script>
	<script src="${ctxStatic}/js/highcharts/highcharts-zh_CN.js"></script>
	<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
	<script src="${ctxStatic}/js/laypage/laypage.js"></script>
	<script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
	<script src="https://cdn.bootcss.com/echarts/3.6.2/echarts.min.js"></script>
	<script src="${ctxStatic}/js/dataTool.min.js"></script>


	<link href="https://cdn.bootcss.com/datatables/1.10.16/css/jquery.dataTables.min.css" rel="stylesheet">
	<script src="https://cdn.bootcss.com/datatables/1.10.16/js/jquery.dataTables.min.js"></script>

	<style>
		.open {
			color: #688be2;
			font-weight: bold;
		}
		.js-label{
			padding-left: 20px;
			height: 20px;
			line-height: 20px;
			/*transform: rotate(-70deg);*/
		}
		.js-label.has-child {

		}
		.js-label.has-child::before {
			content: "+";
			position: relative;
			color: #688be2;
			display: inline-block;
			width: 10px;
			height: 10px;
			font-weight: bold;
		}
		.js-label.has-child.open::before {
			content: "-";
			position: relative;
			color: #688be2;
			display: inline-block;
			width: 10px;
			height: 10px;
			font-weight: bold;
		}
		.js-label.has-child.ml-20 {
			margin-left: -20px;
		}
		.js-label.has-child.ml-10 {
			margin-left: -10px;
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
		<iqgs:iqgs-nav focus="7" genId="${genId}"></iqgs:iqgs-nav>
		<div class="explains">
			<div class="explain-list" id="expression-data">
				<div class="explain-h">
					<p>表达数据</p>
				</div>
				<div class="explain-b">
					<div class="line-chart">
						<div class="line-head">
							<div class="item-header">
								<div class="icon-left"><img src="${ctxStatic}/images/box-line-chart.png">折线图</div>
							</div>
							<div class="genes-choose-export">
								<button type="button" class="btn js-export-line">导出图表</button>
							</div>
						</div>
						<div class="box-contant">
							<%--折线图--%>
							<div id="line" style="height: 400px; margin: 20px 0; min-width: 310px; width: 100%; "></div>
						</div>
					</div>
                    <div >
                    <table id="expression-table">
                        <thead>
                        <tr>
                            <td>Gene ID</td>
							<td class="param t_experiment">Expression value</td>
                            <td>Experiment ID</td>
                            <td class="t_sampleName">Sample Name</td>
                            <td class="param t_study">Study</td>
                            <td class="param t_tissue">Tissue</td>
                            <td class="param t_stage">Stage</td>
                            <td class="param t_treat">Treat</td>
                            <td class="param t_geneType">GeneType</td>
                            <td class="param t_cultivar">Cultivar</td>
                            <td class="param t_scientificName">ScientificName</td>
                            <td class="param t_sampleRun">Run</td>
                            <td class="param t_sraStudy">SRAStudy</td>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                    </div>
				</div>


			</div>
		</div>
	</div>
</div>
<form action="${ctxroot}/specific/index" method="POST" id="specificForm" style="display: none;">
	<input type="text" name="genes" value="${genId}">
</form>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
<!--footer-->
<script>

    $(function () {

        var LineMapDt, LineCate, prevData;
        function getLine(genes) {
//            alert(111);
            $.ajax({
                url: "${ctxroot}/iqgs/line",
                type: "POST",
                data: {genes: genes},
                dataType: "json",
                success: function(res) {
                    // .. res 数据处理
                    LineMapDt = _.orderBy(res.cate, ["name"]);
                    LineCate = res.gens;
                    prevData = getLevel0Data(initCategories(LineMapDt));

                    var src = getLineFormatDt(prevData);
                    renderLine(src);
                }
            });
        }

        function getLineFormatDt(cate) {
            var categories = [];
            var data = [];
            for(var c = 0; c < LineCate.length; c++) {
                var tmp = {};
                tmp.name = LineCate[c];
                tmp.data = [];
                $.each(cate, function(idx, el) {
                    var obj = {};
                    obj.name = el.name;
                    obj.level = el.level;
                    obj.state = el.state;
                    obj.hasChild = el.hasChild;
                    obj.chinese = el.chinese;
                    categories.push(obj);
                    tmp.data.push(el.values[c]);
                });
                data.push(tmp);
            }

            data = _.orderBy(data, ['name']); // 按基因名称排序
            console.log(data);
            var src = {};
            src.cate = categories;
            src.data = data;
            return src;
        }

        function getLevel0Data(data) {
            var tmp = [];
            $.each(data, function(idx, el) {
                if ( el.level * 1 == 0 ) {
                    tmp.push(el);
                }
            });
            return tmp;
        }

        var chartLine;

        function renderLine(data) {
            if(chartLine) {chartLine.destroy();}

            var names = [];
            $.each(data.data, function(idx, el) {
                names.push(el["name"]);
                el["marker"] = {
                    enabled: false,
                    symbol: "circle"
                };
                el["lineWidth"] = 1;
            });
            var filename = "line map " + names[0];

            // 折线图
            chartLine = new Highcharts.Chart('line', {
                chart: {
                    type: "line",
                    marginTop: 80
                },
                title: {
                    text: null
                },
                credits: {
                    enabled: false
                },
                exporting: {
                    buttons: {
                        contextButton: {
                            enabled: false,
                            menuItems: null,
                            onclick: function () {
                                console.log(this);
                                this.exportChart();
                            }
                        }

                    },
                    filename: filename,
                    sourceHeight: 400,
                    sourceWidth: 1000,
                    scale: 2
                },
				plotOptions: {
                    series: {
                        cursor: 'pointer',
						events: {
                            click: function(event) {
                                $("#specificForm").submit();
							}
						}
					}
				},
                tooltip: {
                    shared: true,
                    formatter: function() {
//                        console.log(this);
                        var chinese = this.x.chinese;
                        if(chinese!= ""){
                            var s = '<b>' + chinese + '</b>';
                        } else {
                            var s = '<b>' + this.x.name + '</b>';
                        }

                        $.each(this.points, function () {
                            s += '<br/><b style="color:' + this.color + '">' + this.series.name + '</b>: ' +
                                this.y.toFixed(2);
                        });
                        return s;
                    }
                },
                colors: ["#386cca", "#ef6062", "#ffba02", "#009cbc", "#6a6ad5"] ,
                legend: {
                    enabled: true,
                    align: "right",
                    useHTML: true,
                    verticalAlign: 'top',
                    squareSymbol: true,
                    symbolHeight: 20,
                    symbolWidth: 20,
                    symbolRadius: 0,
                    // itemMarginBottom: 20,
                    itemStyle: {"line-height": "12px","font-size": "12px", "font-weight": "200" , "color": "#898989"}
                },
                xAxis: {
                    categories: data.cate,
                    title: {
                        text: null
                    },
                    lineWidth: 1,
                    lineColor: "#000",
                    tickColor: "#fff",
                    labels: {
                        useHTML: true,
                        style: {
                            "fontSize": "14px"
                        },
                        rotation: -70,
                        formatter: function() {
//                            console.log(this);
                            var name = this.value["name"];
                            var chinese = this.value["chinese"];
                            var level = this.value["level"];
                            var state = this.value["state"];
                            var hasChild = this.value["hasChild"];
                            var hasChildClass = "";
                            if (hasChild) {
                                hasChildClass = "has-child ";
                            }
                            var _label = "";
//                            return '<span class="">' + this.value["name"] + '</span>';
                            switch(level){
                                case 0 :
                                    _label += "<div class='js-label "+hasChildClass+state+" ml-20 cateOnToggleLineClass' dname='"+name+"' >" + chinese + "</div>";
                                    break
                                case 1 :
                                    _label += "<div class='js-label "+hasChildClass+state+" ml-20 cateOnToggleLineClass' dname='"+name+"' >" + name + "</div>";
                                    break
                                case 2 :
                                    _label += "<div class='js-label' style='margin-left:10px'>" + name + "</div>";
                                    break
                            }

                            return _label;
                        }
                    }
                },
                yAxis: {
                    title: {
                        text: 'FPKM',
                        align: "high",
                        rotation: 0,
                        offset: 0,
                        y: -30
                    },
                    lineWidth: 1,
                    lineColor: "#000"
                },
                series: data.data
            });
        }

        // 初始化显示的种类数据，默认显示第一级
        function initCategories(heatmapdata){
            var d = new Array();
            for (var i = 0; i < heatmapdata.length; i++) {
                if(heatmapdata[i].level == 0){
                    heatmapdata[i].hasChild = judgeChildren(heatmapdata[i].name);
                    d.push(heatmapdata[i]);
                }
            }
            return d;
        }

        // 判断是否有子节点
        function judgeChildren(cate_name){
            var hasChild = false;
            for (var i = 0; i < LineMapDt.length; i++) {
                if(LineMapDt[i].pname == cate_name && LineMapDt[i].level == 1){
                    hasChild = true;
                    break;
                }
            }
            return hasChild;
        }

        // 种类节点点击事件
        function cateOnToggleLine(obj){
            if($(obj).hasClass("has-child")) {
                if ($(obj).hasClass("close")) {
                    openNodeLine(obj);
                } else if ($(obj).hasClass("open")) {
                    closeNodeLine(obj);
                }
            }
        }

        // 展开节点
        function openNodeLine(obj){
            var str = "";
            if($(obj).attr("dname")){
                str = $(obj).attr("dname");
            }else{
                str = $(obj).html();
            }

            for (var i = 0; i < LineMapDt.length; i++) {
                if(LineMapDt[i].pname == str){
                    var openNode = LineMapDt[i];
                    openNode.hasChild = judgeChildren(openNode.name);
                    for(var j = 0; j < prevData.length; j++){
                        if (prevData[j].name == str){
                            prevData[j].state = "open";
                            prevData.splice(j+1,0,openNode); // 添加数据
                            break;
                        }
                    }
                }
            }
//            console.log(prevData);
            var src = getLineFormatDt(prevData);
            renderLine(src);
        }

        // 收缩节点
        function closeNodeLine(obj){
            var str = "";
            if($(obj).attr("dname")){
                str = $(obj).attr("dname");
            }else{
                str = $(obj).html();
            }
            for (var i = 0; i < LineMapDt.length; i++) {
                if(LineMapDt[i].pname == str){
                    var subPname = "";
                    for(var j = 0; j < prevData.length; j++){
                        if (prevData[j].name == str){
                            prevData[j].state = "close";
                        }
                        if(prevData[j].pname == str){
                            subPname = prevData[j].name;
                            prevData.splice(j,1);
                        }
                    }
                    if (subPname != ""){
                        for(var k = 0; k < prevData.length; k++){
                            if(prevData[k].pname == subPname){
                                prevData.splice(k,1);
                            }
                        }
                    }
                }
            }
            var src = getLineFormatDt(prevData);
            renderLine(src);

        }


        // 获取表格数据
		function getTable(gene) {
			$.ajax({
				url: "${ctxroot}/iqgs/queryExpressionByGene",
				type:"POST",
				dataType:"json",
				data: {gene: gene},
				success: function(res) {
                    renderTable(res);
                }
			});
        }

        function renderTable(data) {
            var str = '';
            $.each(data, function(idx, ele) {
                str += '<tr>'
                str += '<td>'+ ele.geneId +'</td>';
                str += '<td>'+ ele.expressionValue +'</td>';
                str += '<td>'+ ele.experiment +'</td>';
                str += '<td>'+ ele.sampleName +'</td>';
                str += '<td><p class="js-tipes-show"><a href="'+ ele.links +'" target="_blank">'+ ele.study +'</a></p></td>';
                str += '<td>'+ ele.tissue +'</td>';
                str += '<td>'+ ele.stage +'</td>';
                str += '<td><p class="js-tipes-show">'+ ele.treat +'</p></td>';
                str += '<td><p class="js-tipes-show">'+ ele.geneType +'</p></td>';
                str += '<td>'+ ele.ccultivar +'</td>';
                str += '<td>'+ ele.scientificName +'</td>';
                str += '<td>'+ ele.sampleRun +'</td>';
                str += '<td>'+ ele.sraStudy +'</td>';
                str += '</tr>';

            });
            $("#expression-table > tbody").empty().append(str);

            $(".js-tipes-show").hover(
                    function(){
                        if($(this).text()!==""){
                            var _text=$(this).text()
                            var self = this;
                            var content = "";
                            content += "<div>"+_text+"</div>";
                            $.pt({
                                target: self,
                                position: 't',
                                align: 'l',
                                autoClose: false,
                                content: content
                            });
//                            $(".pt").css("left", $(".pt").position().left-15);
                        }else{
                            $(".pt").remove();
                        }

                    },
                    function(){
                        $(".pt").remove();
                    }
            )

			$("#expression-table").DataTable({
                "bFilter": false,
                "bSort": false,
                "scrollX": true,
                "language": {
                    "sProcessing": "处理中...",
                    "sLengthMenu": "显示 _MENU_ 项结果",
                    "sZeroRecords": "没有匹配结果",
                    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
                    "sInfoPostFix": "",
                    "sSearch": "搜索:",
                    "sUrl": "",
                    "sEmptyTable": "表中数据为空",
                    "sLoadingRecords": "载入中...",
                    "sInfoThousands": ",",
                    "oPaginate": {
                        "sFirst": "首页",
                        "sPrevious": "上页",
                        "sNext": "下页",
                        "sLast": "末页"
                    },
                    "oAria": {
                        "sSortAscending": ": 以升序排列此列",
                        "sSortDescending": ": 以降序排列此列"
                    }
                }
            });
        }

        getLine('${genId}');

        getTable('${genId}');

        $("body").on("click",".cateOnToggleLineClass",function(){
            cateOnToggleLine($(this));
        });

        $(".js-export-line").click(function() {
            chartLine.exportChart();
        });

    });

</script>
</body>
</html>