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
		#expression-data thead td {
			border: 1px solid #e6e6e6;
			white-space: nowrap;
			height: auto;
			padding: 15px 10px;
			background-color: #f5f8ff;
			border-bottom: none;
			/*border-right: none;*/
		}
		#expression-table tbody td {
			padding: 0 10px;
			white-space: nowrap;
			position: relative;
		}
		body #bdsj-paginate{padding-right: 20px;}
		body #expression-data .explain-b{overflow-x:inherit;}
		.expressionTable{width:860px;overflow-x:scroll;}
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
					<div class="expressionTable">
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
				<div class="checkbox-item-tab" id="bdsj-paginate">
					<%@ include file="/WEB-INF/views/include/pagination.jsp" %>
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

        function loadMask (el) {
            $(el).css({"position": "relative"});
            var _mask = $('<div class="ga-mask"><div>数据加载中...</div></div>');
            $(el).append(_mask);
        }

        function maskClose(el) {
            $(el).find(".ga-mask").remove();
        }

        var LineMapDt, LineCate, prevData;
        function getLine(genes) {
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
//            console.log(data);
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

//        var pageSize = 10;
        var page = {curr: 1, pageSize:10};

        $(".lay-per-page-count-select").val(page.pageSize);
        // 获取表格数据+分页
        function initTables(currNum,pageSizeNum){
            loadMask ("#mask-test");
            $.getJSON('${ctxroot}/iqgs/queryExpressionByGene', {
                pageNo: currNum || 1,
                pageSize: pageSizeNum|| 10,
                gene: "${genId}"
            }, function(res,currNum){
                maskClose();
                //显示表格内容
                if(res.data.length==0){
                    console.log("表格无数据显示!")
                    $("#tableBody").html("<p>表格暂无数据显示!</p>")
                }
                renderTable(res);

                $("#total-page-count > span").html(res.total);

                //显示分页
                laypage({
                    cont: $('#bdsj-paginate .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                    pages: Math.ceil(res.total / page.pageSize), //通过后台拿到的总页数
                    curr: page.curr || 1, //当前页
                    skin: '#5c8de5',
                    skip: true,
                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                    last: Math.ceil(res.total / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                    prev: '<',
                    next: '>',
                    groups: 3, //连续显示分页数
                    jump: function(obj, first){ //触发分页后的回调
                        if(!first){ //点击跳页触发函数自身，并传递当前页：obj.curr
                            var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
//                            initTables(obj.curr);
                            page.curr = obj.curr;
                            var currNum=obj.curr;
                            initTables(currNum,pageSizeNum);
                        }
                        if(res.data.length==0){
                            $("#tableBody").html("<p class='no-data'>无数据显示!</p>")
                        }
                    }
                });
            });
        }

        function renderTable(data) {
            var eleData=data.data;
            var str = '';
            for(var i=0;i<eleData.length; i++){
                str += '<tr>'
                str += '<td>'+ eleData[i].gene +'</td>';
                str += '<td>'+ eleData[i].samplerun.value +'</td>';
                str += '<td>'+ eleData[i].study.experiment+'</td>';
                str += '<td>'+ eleData[i].samplerun.name +'</td>';
                str += '<td><p class="js-tipes-show"><a href="'+ eleData[i].study.links +'" target="_blank">'+ eleData[i].study.study +'</a></p></td>';
                str += '<td>'+ eleData[i].study.tissue +'</td>';
                str += '<td>'+ eleData[i].study.stage +'</td>';
                str += '<td><p class="js-tipes-show">'+ eleData[i].study.treat +'</p></td>';
                str += '<td><p class="js-tipes-show">'+ eleData[i].study.geneType +'</p></td>';
                str += '<td>'+ eleData[i].study.ccultivar +'</td>';
                str += '<td>'+ eleData[i].study.scientificName +'</td>';
                str += '<td>'+ eleData[i].study.sampleRun +'</td>';
                str += '<td>'+ eleData[i].study.sraStudy +'</td>';
                str += '</tr>';
            }
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
        }

        getLine('${genId}');

        <%--getTable('${genId}');--%>

        // 修改每页显示条数
//        $("body").on("change",".lay-per-page-count-select", function() {
//            pageSize = $(this).val();
//            initTables(1)
//        });
        $("body").on("change",".lay-per-page-count-select", function() {
            var curr = Number($(".laypage_curr").text());
            var pageSize = Number($(this).val());
            var total= $("#total-page-count span").text();

            var mathCeil=  Math.ceil(total/curr);
            page.pageSize = $(this).val();
            if(pageSize>mathCeil){
                var pageSizeNum=$(this).val();
                page.curr = 1;
                initTables(1,pageSizeNum)
            }else{
                var pageSizeNum=$(this).val();
                initTables(curr,pageSizeNum)
            }
        });

        // 分页跳转
        $("#pagination").on("focus", ".laypage_total .laypage_skip", function () {
            $(this).addClass("isFocus");
        });
        $("#pagination").on("blur", ".laypage_total .laypage_skip", function () {
            $(this).removeClass("isFocus");
        });
        // 注册 enter 事件的元素
        $(document).keyup(function (event) {
            var _page_skip = $('#pagination .laypage_skip');
            if (_page_skip.hasClass("isFocus")) {
                if (event.keyCode == 13) {
                    var _page_skip = $('#pagination .laypage_skip');
                    var curr = Number(_page_skip.val());
                    var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                    var total= $("#total-page-count span").text();
                    var mathCeil=  Math.ceil(total/pageSizeNum);
                    if (curr>mathCeil) {
                        page.curr = 1;
                        initTables(1,pageSizeNum)
                    }else{
                        page.curr = curr;
                        initTables(curr,pageSizeNum)
                    }
                }
            }
        });



        /*列表初始化*/
        initTables(1);

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