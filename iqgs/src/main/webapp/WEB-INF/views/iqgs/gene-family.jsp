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
    <link rel="stylesheet" href="${ctxStatic}/css/DNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">
    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js/d3.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>

</head>

<body>
<iqgs:iqgs-header></iqgs:iqgs-header>

<!--header-->
<div class="container">
    <div class="detail-name">
        <p>${genId}</p>
    </div>
    <div class="detail-content">
        <iqgs:iqgs-nav focus="5" genId="${genId}"></iqgs:iqgs-nav>
        <c:if test="${hasFamilyFlg == true}">
            <div class="explains">
                <div class="explain-list" id="gene-family">
                    <div class="page-tables">
                        <div class="box-shadow resulting">
                                <%--<div class="item-header">
                                    <div class="icon-left">基因家族</div>
                                </div>--%>
                            <div class="tab-item">
                                <ul class="item">
                                    <c:forEach items="${dnaGenFamilyRels}" var="item">
                                        <li class="${item.familyId == familyId ? 'item-ac' : ''} family-tab">${item.familyId}</li>
                                    </c:forEach>
                                </ul>
                                    <%--<div class="explain-h">
                                        <p>
                                            基因家族</p>
                                    </div>--%>
                                <div class="explain-b" style="padding-bottom: 5px;">
                                    <table>
                                        <thead>
                                        <tr><th>基因名</th><th>基因ID</th><th>物种</th><th>染色体</th><th>位置</th><th>注释</th></tr>
                                        </thead>
                                        <tbody id="gensTbody">
                                            <%--<tr><td>GLE1</td><td>Glyma.18G521600</td><td>大豆</td><td>Chr01</td><td>222-35450</td><td>功能注释</td></tr>
                                            <tr><td>GLE2</td><td>Glyma.18G521600</td><td>大豆</td><td>Chr01</td><td>222-35451</td><td>功能注释</td></tr>
                                            <tr><td>GLE3</td><td>Glyma.18G521600</td><td>大豆</td><td>Chr01</td><td>222-35452</td><td>功能注释</td></tr>
                                            <tr><td>GLE4</td><td>Glyma.18G521600</td><td>大豆</td><td>Chr01</td><td>222-35453</td><td>功能注释</td></tr>
                                            <tr><td>GLE4</td><td>Glyma.18G521600</td><td>大豆</td><td>Chr01</td><td>222-35455</td><td>功能注释</td></tr>--%>
                                        </tbody>
                                    </table>
                                    <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
                                </div>
                            </div>
                        </div>

                        <div id="clusterPic" style="min-width:800px">
                            <svg xmlns="http://www.w3.org/2000/svg" style="border:1px solid #f0f0f0;">
                            </svg>
                        </div>

                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${hasFamilyFlg == false}">
            <div class="explains">
                <div class="explain-list">
                    <div class="explain-h">
                        <p>基因家族</p>
                    </div>
                    <div class="explain-b" style="text-align: center">
                        <img src="${ctxStatic}/images/nodata.png">
                        <div style="padding-top: 10px">无基因家族信息</div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>
</div>
<!--container-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>
<!--footer-->
<c:if test="${hasFamilyFlg == true}">
    <script>
        var page = {curr: 1, pageSize:10};

        $(".family-tab").on('click', function(){
            window.location = "${ctxroot}/iqgs/detail/family?gen_id=${genId}&family_id="+$(this).text();
        });

        function requestPageData(currNum,pageSizeNum){
            $.getJSON('${ctxroot}/iqgs/detail/family/page', {
                pageNo: currNum || 1,
                pageSize: pageSizeNum || 10,
                family_id: '${familyId}'
            }, resultCallback);
        }

        function resultCallback(res,currNum) {
            // 关闭遮罩层
            renderList(res.data);
            laypage({
                cont: $('.ga-ctrl-footer .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                pages: Math.ceil(res.total / page.pageSize), //通过后台拿到的总页数
                curr: page.curr || 1, //当前页
                skin: '#5c8de5',
                skip: true,
                first: 1, //将首页显示为数字1,。若不显示，设置false即可
                last: Math.ceil(res.total / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                prev: '<',
                next: '>',
                groups: 3, //连续显示分页数
                jump: function (obj, first) { //触发分页后的回调
                    if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                        var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                        page.curr = obj.curr;
                        var currNum=obj.curr;
                        requestPageData(currNum,pageSizeNum);
                    }
                }
            });
            $(".total-page-count span").html(res.total);
        }

        // 修改每页显示条数
        $("body").on("change",".lay-per-page-count-select", function() {
            var curr = Number($(".laypage_curr").text());
            var pageSize = Number($(this).val());
            var total= $("#total-page-count span").text();

            var mathCeil=  Math.ceil(total/curr);
            page.pageSize = $(this).val();
            if(pageSize>mathCeil){
                var pageSizeNum=$(this).val();
                page.curr = 1;
                requestPageData(1,pageSizeNum)
            }else{
                var pageSizeNum=$(this).val();
                requestPageData(curr,pageSizeNum)
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
                        requestPageData(1,pageSizeNum)
                    }else{
                        page.curr = curr;
                        requestPageData(curr,pageSizeNum)
                    }
                }
            }
        });


        /*列表初始化*/
        requestPageData(1);

        function renderList(listdata) {
            if (listdata && listdata.length > 0) {
                var html = [];
                $.each(listdata, function(i, item){
                    var locus =  item.locus;
                    var arr = locus.split(":");
                    html.push('<tr>');
                    html.push('<td>' + item.geneName + '</td>');
                    html.push('<td>' + item.geneId + '</td>');
                    html.push('<td>' + item.species + '</td>');
                    html.push('<td>' + arr[0] + '</td>');
                    html.push('<td>' + arr[1].replace("bp","").replace("bp","") + '</td>');
                    html.push('<td>' + item.description + '</td>');
                    html.push('</tr>');
                });
                $("#gensTbody").html(html.join('\n'));
            }
        }

        requestPageData();

        var cluster_json = ${dnaGenFamily.treeJson};
        var structure_data = ${structureData};

        var responseData = {
            "cluster_json": cluster_json,
            "structure_data": structure_data
        }

        drawGeneFamily(responseData)

        function drawGeneFamily(Data) {
            // 查询的基因数组
            var targetGeneArray = [];
            // 定义图片结构
            var svg_legend_height = 80,
                svg_main_height = structure_data.data.length * 36,
                svg_bottom_height = 40,
                svg_height = svg_legend_height + svg_main_height + svg_bottom_height,
                main_margin = 30


            var svg = d3.select("#clusterPic svg").attr("width", $('#clusterPic').width()).attr("height", svg_height),
                svg_width = svg.attr("width")

            // 聚类折线图的node颜色
            var colorArray = ["#4575B4", "#FEF6B2", "#D9352A"];
            var colorScale = d3.scaleLinear().domain([0, 50, 100]).range(colorArray).interpolate(d3.interpolateRgb)
            //基因结构图的颜色
            var structureColor = {
                "three_prime_UTR": "#FFBA02",
                "CDS": "#009ABB",
                "five_prime_UTR": "#F87580",
                "line": "#000000"
            }


            svg.selectAll("g").remove()
            drawLegend();
            drawClusterPic(Data.cluster_json);
            drawAxis(Data.structure_data.max_length);
            drawStructurePic(Data.structure_data);


            //画图例
            function drawLegend() {
                var svg_legend_g = svg.append("g").attr("class", "legend")
                var cluster_legend = svg_legend_g.append("g").attr("class", "cluster_legend").attr("transform", "translate(" + main_margin + ",30)")
                var structure_legend = svg_legend_g.append("g").attr("class", "cluster_legend").attr("transform", "translate(" + (svg_width - 300 - main_margin) + ",30)")
                var main_height = 10
                //画左侧图例
                var cluster_legend_main = cluster_legend.append("g").attr("transform", "translate(96,-4)")
                var cluster_legend_main_width = 100


                var linearGradient = cluster_legend_main.append("defs").append("linearGradient")
                    .attr("id", "linearColor")
                    .attr("x1", "0%")
                    .attr("y1", "0%")
                    .attr("x2", "100%")
                    .attr("y2", "0%");

                var stop1 = linearGradient.append("stop")
                    .attr("offset", "0%")
                    .style("stop-color", colorArray[2]);

                var stop2 = linearGradient.append("stop")
                    .attr("offset", "50%")
                    .style("stop-color", colorArray[1]);

                var stop3 = linearGradient.append("stop")
                    .attr("offset", "100%")
                    .style("stop-color", colorArray[0]);


                var xScale = d3.scaleLinear().range([cluster_legend_main_width - 1, 0]).clamp(true).domain([0, 100])
                var xAxis = d3.axisBottom(xScale).tickSizeOuter(0).ticks(3)

                cluster_legend.append("text").text("bootstrap:").attr("dominant-baseline", "middle").attr("transform", "translate(0," + main_height / 2 + ")").attr("font-size", "16px")
                cluster_legend_main.append("g").attr("transform", "translate(0," + main_height + ")").call(xAxis)
                cluster_legend_main.append("rect").attr("width", cluster_legend_main_width).attr("height", main_height).attr("fill", "url(#" + linearGradient.attr("id") + ")")
                cluster_legend_main.selectAll("line").attr("stroke", "#CCC")
                cluster_legend_main.selectAll("path").attr("stroke", "#CCC")

                //画右侧图例
                var rect_width = 10
                structure_legend.append("text").text("gene structure:").attr("dominant-baseline", "middle").attr("transform", "translate(0," + main_height / 2 + ")").attr("font-size", "16px")
                var structure_legend_main = structure_legend.append("g").attr("transform", "translate(130,0)")

                structure_legend_main.append("rect").attr("x", 0).attr("y", 0).attr("width", rect_width).attr("height", rect_width).attr("fill", structureColor.five_prime_UTR)
                structure_legend_main.append("text").text("5'UTR").attr("dominant-baseline", "middle").attr("transform", "translate(16," + (main_height / 2 + 1) + ")").attr("font-size", "12px")

                structure_legend_main.append("rect").attr("x", 66).attr("y", 0).attr("width", rect_width).attr("height", rect_width).attr("fill", structureColor.CDS)
                structure_legend_main.append("text").text("CDS").attr("dominant-baseline", "middle").attr("transform", "translate(82," + (main_height / 2 + 1) + ")").attr("font-size", "12px")

                structure_legend_main.append("rect").attr("x", 120).attr("y", 0).attr("width", rect_width).attr("height", rect_width).attr("fill", structureColor.three_prime_UTR)
                structure_legend_main.append("text").text("3'UTR").attr("dominant-baseline", "middle").attr("transform", "translate(136," + (main_height / 2 + 1) + ")").attr("font-size", "12px")
                svg_legend_g.selectAll("text").attr("fill", "#666666")
            }

            //画聚类折线图
            function drawClusterPic(json) {
                //定义聚类折线图高度
                var cluster_height = svg_main_height;
                //定义聚类折线图宽度
                var cluster_width = svg_width * 0.3;

                var cluster = d3.cluster()
                    .size([cluster_height, cluster_width])
                    .separation(function () { return 1; });

                var svg_cluster_g = svg.append("g").attr("class", "cluster")
                    .attr("transform", "translate(" + main_margin + "," + svg_legend_height + ")");

                //根据数据建立模型
                var root = d3.hierarchy(json);
                cluster(root)

                var link = svg_cluster_g.selectAll(".clusterlink")
                    .data(root.links())
                    .enter().append("path")
                    .attr("class", "clusterlink").attr("fill", "none").attr("stroke", "#000000").attr("stroke-width", "1px")
                    .attr("d", elbow);

                var node = svg_cluster_g.selectAll(".node")
                    .data(root.descendants())
                    .enter().append("g")
                    .attr("class", "node")
                    .attr("transform", function (d) { return "translate(" + d.y + "," + d.x + ")"; })

                //填充node
                node.append("circle")
                    .attr("r", function (d) {
                        if (d.children && d.parent) {
                            return 6
                        }
                    })
                    .attr("fill", function (d) {
                        if (d.data.b_value) {
                            return colorScale(d.data.b_value)
                        }
                    })
                    .on("click", function (d) {
                        targetGeneArray = [];
                        getLeaves(d);
                        SearchGeneArray(targetGeneArray)
                    })
                    .on("mouseover", function (d) {
                        d3.select(this).style("cursor", "pointer").attr("stroke", "black")

                    })
                    .on("mouseout", function () {
                        d3.select(this).style("cursor", "default").attr("stroke", "");
                    })

                function elbow(d, i) {
                    return "M" + d.source.y + "," + d.source.x
                        + "V" + d.target.x + "H" + d.target.y;
                }
                //获取所有叶子
                function getLeaves(d) {
                    if (d.children) {
                        d.children.forEach(function (child) {
                            getLeaves(child)
                        });
                    }
                    else {
                        targetGeneArray.push(d.data.name);
                    }
                }
            }
            //画基因结构图
            function drawStructurePic(json) {
                //定义基因结构图高度
                var cluster_height = svg_main_height;
                //定义基因结构图宽度
                var cluster_width = svg_width * 0.7 - main_margin * 2;
                var svg_structure_g = svg.append("g").attr("class", "structure")
                    .attr("transform", "translate(" + (svg_width * 0.3 + main_margin) + "," + svg_legend_height + ")");

                //构建比例尺
                var Scale = d3.scaleLinear().domain([0, json.max_length]).range([0, cluster_width - 140])
                for (let i = 0; i < json.data.length; i++) {
//                    svg.append("defs").append("Path").attr("id", "myTextPath"+i).attr("d", "M0,10a1,0 0 0,0 120,0");
                    var single_gene_g = svg_structure_g.append("g").attr("id", "structure_" + json.data[i].geneID).attr("class", "structure_name")
                        .attr("transform", "translate( 0 ," + (i *36) + ")")
                    // label文字
                    var gene_name = single_gene_g.append("g").attr("class", "gene_name");

                    var gene_id= gene_name.append("text").attr("class", "labelGeneID").attr("transform", "translate( 10 ,10 )")
                        .text(json.data[i].geneID).attr("dominant-baseline", "middle").style("font-size", "12px");
                    var labelGeneName = gene_name.append("text").attr("class", "labelGeneName").attr("transform", "translate( 10 ,26)").style("cursor", "pointer")
                        .attr('title',json.data[i].geneName).text(json.data[i].geneName).attr("dominant-baseline", "middle").style("font-size", "12px");
                    gene_name.on("click", function () {
                        targetGeneArray = [];
                        targetGeneArray.push(d3.select(this).select('.labelGeneID').text())
                        SearchGeneArray(targetGeneArray)
                    })
                        .on("mouseover", function (d) {
                            d3.select(this).style("cursor", "pointer").attr("fill", "#5C8CE6")
                        })
                        .on("mouseout", function () {
                            d3.select(this).style("cursor", "default").attr("fill", "#000000");
                        })

                    // 结构图
                    var single_gene_structure = single_gene_g.append("g").attr("class", "gene_structure").attr("transform", "translate( 140 , 16)")
                    // 背景线
                    single_gene_structure.append("line").attr("x1", 0).attr("y1", 0).attr("x2", Scale(json.data[i].length)).attr("y2", 0).attr("stroke-width", 0.5).attr("stroke", structureColor.line)
                    // 方块
                    for (var j = 0; j < json.data[i].structure.length; j++) {
                        single_gene_structure.append("rect").attr("x", Scale(json.data[i].structure[j].start))
                            .attr("y", -5).attr("width", Scale(json.data[i].structure[j].end - json.data[i].structure[j].start)).attr("height", 10)
                            .attr("fill", structureColor[json.data[i].structure[j].type])
                    }

                    //给超长的基因名称添加省略号
                    var maxwidth = 15;//显示多少字符
                    for(var j=0;j<$(".labelGeneName").length;j++){
                        var cur = $(".labelGeneName").eq(j);
                        if(cur.text().length>maxwidth){
                            cur.text(cur.text().substring(0,maxwidth)+'...')
                        }
                    }
                }
            }
            function drawAxis(length) {
                var svg_Axis_g = svg.append("g").attr("class", "geneStructureAxis")
                    .attr("transform", "translate(" + (140 + main_margin + svg_width * 0.3) + "," + (svg_legend_height + svg_main_height) + ")");
                var xScale = d3.scaleLinear().range([svg_width * 0.7 - 140 - main_margin * 2, 0]).clamp(true).domain([length, 0])
                var xAxis = d3.axisBottom(xScale).tickSizeOuter(0).ticks(5).tickSize(-svg_main_height).tickPadding([10])
                svg_Axis_g.append("g").call(xAxis)
                svg_Axis_g.selectAll("line").attr("stroke", "#CCCCCC").attr("stroke-dasharray", 1)
                svg_Axis_g.selectAll("text").attr("fill", "#666666")
                svg_Axis_g.selectAll("path").remove()
            }
            //对目标基因数组的处理函数
            function SearchGeneArray(geneArray) {
//                console.log(geneArray);
                window.open("${ctxroot}/specific/index?genes="+geneArray.join(","));
            }
        }
        //基因名称悬浮框显示
        $('.gene_name .labelGeneName').hover(function () {
//                    $(this).attr("fill", "#5C8CE6")
            var self = this;
            var cont = $(this).attr("title");
            if($(this).text()!==""&&cont.length>15){
                $.pt({
                    target: self,
                    content: cont
                })
            }
        },function(){
//                    $(this).attr("fill", "#000000");
            if( $(".pt").css("display")=='block' ){
                $("body .pt").css("display","none");
            }
        })
    </script>
</c:if>

</body>
</html>