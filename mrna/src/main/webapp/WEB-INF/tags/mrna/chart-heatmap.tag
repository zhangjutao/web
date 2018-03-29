<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ attribute name="data" type="java.lang.String" required="true" description="" %>
<%@ attribute name="isAjax" type="java.lang.String" required="false" description="" %>
<%@ attribute name="gaHeight" type="java.lang.String" required="false" description="" %>
<c:set var="id" value="${java.util.UUID.randomUUID()}"/>

<div id="heatmap_${id}" style=" margin: auto; min-width: 310px; width: 100%;"></div>

<div class="genesInfo" style="display: none;">
    <div class="genesInfo-head">
        <p>基因<span class="js-gene-head-name"></span>信息</p>
        <a href="javascript:void(0);">X</a>
    </div>
    <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
</div>

<style>
    .open {
        /*color: #688be2;*/
        color: #0F9145;
        font-weight: bold;
    }

    .js-label {
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
        /*color: #688be2;*/
        color: #0F9145;
        display: inline-block;
        width: 10px;
        height: 10px;
        font-weight: bold;
    }

    .js-label.has-child.open::before {
        content: "-";
        position: relative;
        /*color: #688be2;*/
        color:
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

    body .genesInfo {
        left: 50%;
        margin-left: -515px;
        border: none;
    }

    body .genesInfo-head {
        position: relative;
        height: 40px;
        line-height: 40px;
        /*background: #386cca;*/
        background: #0F9145;
        cursor: move;
    }

    .genesInfo .genesInfo-head p {
        width: 100%;
        text-align: center;
        color: #fff;
    }

    .genesInfo .genesInfo-head a {
        float: none;
        position: absolute;
        right: 15px;
        color: #fff;
        font-size: 20px;
    }

    .zwsj {
        width: 200px;
        padding: 20px 10px;
        text-align: left;
    }
</style>
<script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
<script src="${ctxStatic}/js/jquery-ui.js"></script>
<script src="${ctxStatic}/js/layer/layer.js"></script>
<script>
    var index;
    $("body").on("click", ".js-gene-info", function (e) {
        var version = 'gmx_ensembl_release23';
        var geneName = $(this).text();
        $(".js-gene-head-name").html(geneName);
        $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName=" + geneName + "&version=" + version);
        e.preventDefault();
        //修改拖拽样式
        /*$(".genesInfo").show();*/
        index = layer.open({
            title: "",
            type: 1,
            content: $(".genesInfo"),
            area: ['980px', '640px'],
            shadeClose: true,
            scrollbar: false,
            move: '.genesInfo-head',
            closeBtn: 0,
            //offset: ['135px', '320px']
        });


    });
    $(".genesInfo-head > a").click(function () {
        /*$(".genesInfo").hide();*/
        layer.close(index);
    });

    var chart;
    (function () {
        var g_cate;
        var heatmapdata, heatmapcategory;
        // 分类英文转中文显示
        window.en2cn = function (str) {
            var name = "";
            switch (str) {
                case "pod_All" :
                    name = "豆荚";
                    break;
                case "seed_All" :
                    name = "种子";
                    break;
                case "root_All" :
                    name = "根";
                    break;
                case "shoot_All" :
                    name = "芽";
                    break;
                case "leaf_All" :
                    name = "叶子";
                    break;
                case "seedling_All" :
                    name = "幼苗";
                    break;
                case "flower_All" :
                    name = "花";
                    break;
                case "stem_All" :
                    name = "茎";
                    break;
                default:
                    name = str;
            }
            return name;
        }

        if ("${isAjax}" == "true") {
//            window.pageSize = 10;
            window.getHeatMap = function (genes, curr) {
                var innergenes = genes.split(",");
                innergenes = innergenes.sort();
//                curr = curr || 1;
//                var len = innergenes.length;

//                var pageTotal = Math.ceil(specificGenes.length / pageSize);

//                $(".lay-per-page-count-select").val(window.pageSize);

//                var start = 1 + (curr - 1) * pageSize - 1;
//                var end = curr * pageSize;
//                if (end > len) {
//                    end = len;
//                }
//                innergenes = innergenes.slice(start, end);
                innergenesStr = innergenes.join(",");0
//                热图数据获取
                initHeatmap(1,10);
                var page = {curr: 1, pageSize: 10};
                $(".lay-per-page-count-select").val(page.pageSize);
                function initHeatmap(currNum, pageSizeNum) {
                    $.ajax({
                        url: "${ctxroot}/specific/hitmap",
                        type: "POST",
                        data: {
                            genes: innergenesStr,
                            pageNo: currNum || 1,
                            pageSize: pageSizeNum || 10
                        },
                        dataType: "json",
                        success: function (res) {
                            if (res.gens.length == 0) {
                                $('#heatmap_').empty().html("<p class='zwsj'>暂无数据</p>");
                            } else {
//                                if(res.gens.length>30){
//                                    var heatmapHeigth= 900;
//                                }else if(res.gens.length<5){
//                                    var heatmapHeigth= 300;
//                                }else{
//                                    var heatmapHeigth= 600;
//                                }
                                var dd = res;
                                heatmapdata = _.orderBy(dd.cate, ["name"]);
                                heatmapcategory = dd.gens;
                                g_cate = initCategories();
                                if(res.gens.length<=2){
                                    $("#heatmap_${id}").css({"height": (150 + res.gens.length*50) + "px"});
                               }else if(res.gens.length<=5){
                                    $("#heatmap_${id}").css({"height": (50 * 5+100) + "px"});
                                } else {
                                    $("#heatmap_${id}").css({"height": (50 * pageSizeNum+100) + "px"});
                                }
                                <%--$("#heatmap_${id}").css({"height": (50 * pageSizeNum+100) + "px"});--%>
                                <%--$("#heatmap_${id}").css({"height": heatmapHeigth + "px"});--%>
                                $('#heatmap_').empty();
                                renderChart(heatmapcategory, g_cate, getChartData(g_cate));
                                $("#total-page-count span").html(res.gensTotal);
                                // 显示分页
                                laypage({
                                    cont: $('.ga-ctrl-footer .pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                                    pages: Math.ceil(res.gensTotal / page.pageSize), //通过后台拿到的总页数
                                    curr: currNum || 1, //当前页
                                    /*skin: '#5c8de5',*/
                                    skin: '#0F9145',
                                    skip: true,
                                    first: 1, //将首页显示为数字1,。若不显示，设置false即可
                                    last: Math.ceil(res.gensTotal / page.pageSize), //将尾页显示为总页数。若不显示，设置false即可
                                    prev: '<',
                                    next: '>',
                                    groups: 3, //连续显示分页数
                                    jump: function (obj, first) { //触发分页后的回调
                                        if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                            var currNum = obj.curr;
                                            initHeatmap(currNum, pageSizeNum);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }

                // 修改每页显示条数
                // $(".ga-ctrl-footer").on("change", ".lay-per-count-select", function () {
                //    var currNum = Number($(".ga-ctrl-footer .laypage_curr").text());
                $(".ga-ctrl-footer").on("click", ".select_item_page li", function () {
                    //var currNum = Number($(".ga-ctrl-footer .lay-per-page-count-select").val());
                    /*var currNum = Number($(".ga-ctrl-footer .lay-per-page-count-select").val());
                    var pageSizeNum = Number($(this).text());
                    var totalNum = $("#total-page-count span").text();
                    var mathCeilNum = Math.ceil(totalNum / currNum);
                    page.pageSize = Number($(this).text());
                    if (pageSizeNum > mathCeilNum) {
                        page.curr = 1;
                        initHeatmap(1, pageSizeNum)
                    } else {
                        initHeatmap(currNum, pageSizeNum)
                    }
                    */
                    var pageSize = Number($(this).text());
                    page.pageSize = $(this).text();
                    initHeatmap(1,pageSize);
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
                    var _page_skip = $('.ga-ctrl-footer #pagination .laypage_skip');
                    if (_page_skip.hasClass("isFocus")) {
                        if (event.keyCode == 13) {
                            var _page_skip = $('#pagination .laypage_skip');
                            var curr = Number(_page_skip.val());
                            var pageSizeNum = Number($('#per-page-count .lay-per-page-count-select').val());
                            var total = $("#total-page-count span").text();
                            var mathCeil = Math.ceil(total / pageSizeNum);
                            if (curr > mathCeil) {
                                page.curr = 1;
                                initHeatmap(1, pageSizeNum)
                            } else {
                                page.curr = curr;
                                initHeatmap(curr, pageSizeNum)
                            }
                        }
                    }
                });

            }
        } else {
            var dd = eval(${data});
            heatmapdata = _.orderBy(dd.cate, ["name"]);
            heatmapcategory = dd.gens;
            g_cate = initCategories();
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }



        // 初始化显示的种类数据，默认显示第一级
        function initCategories() {
            var d = new Array();
            for (var i = 0; i < heatmapdata.length; i++) {
                if (heatmapdata[i].level == 0) {
                    heatmapdata[i].hasChild = judgeChildren(heatmapdata[i].name);
                    d.push(heatmapdata[i]);
                }
            }
            return d;
        }

        // 判断是否有子节点
        function judgeChildren(cate_name) {
            var hasChild = false;
            for (var i = 0; i < heatmapdata.length; i++) {
                if (heatmapdata[i].pname == cate_name && heatmapdata[i].level == 1) {
                    hasChild = true;
                    break;
                }
            }
            return hasChild;
        }


        // 根据基因种类数据产生图表数据
        function getChartData(categoriesT) {
            var chartData = new Array();
            for (var i = 0; i < categoriesT.length; i++) {
                var values = categoriesT[i].values;
                for (var j = 0; j < values.length; j++) {
                    var arr = [i, j, values[j]];
                    chartData.push(arr);
                }
            }
            return chartData;
        }

        // 绘制热力图
        function renderChart(gene, cate, data) {
            if (chart) {
                chart.destroy();
            }

            var filename = "heat map " + gene[0];

            // 热力图
            chart = Highcharts.chart('heatmap_${id}', {
                chart: {
                    type: 'heatmap',
//                    marginTop: 140
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
                                this.exportChart();
                            }
                        }

                    },
                    filename: filename,
                    sourceHeight: 400,
                    sourceWidth: 1000,
                    scale: 2
                },
                title: {
                    text: null
                },
                xAxis: {
                    categories: cate,
                    title: {
                        text: null
                    },
                    opposite: true,
                    tickColor: "#fff",
                    labels: {
                        useHTML: true,
                        style: {
                            "fontSize": "12px",
                        },
                        rotation: -80,
                        y: -10,
//                        y: -55,
                        formatter: function () {
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

//                            switch(level){
//                                case 0 :
//                                    _label += "<div class='js-label "+hasChildClass+state+" ml-20 cateOnToggleClass' dname='"+name+"' >" + en2cn(name) + "</div><div class='js-label'>&nbsp</div><div class='js-label'>&nbsp</div>";
//                                    break
//                                case 1 :
//                                    _label += "<div class='js-label'>&nbsp</div><div class='js-label "+hasChildClass+state+" ml-10 cateOnToggleClass' >" + name + "</div><div class='js-label'>&nbsp</div>";
//                                    break
//                                case 2 :
//                                    _label += "<div class='js-label'>&nbsp</div><div class='js-label'>&nbsp</div><div class='js-label' style='margin-left:10px'>" + name + "</div>";
//                                    break
//                            }
                            switch (level) {
                                case 0 :
                                    _label += "<div class='js-label " + hasChildClass + state + " ml-20 cateOnToggleClass' dname='" + name + "' cname='" + chinese + "'>" + chinese + "</div>";
                                    break
                                case 1 :
                                    _label += "<div class='js-label " + hasChildClass + state + " ml-20 cateOnToggleClass' dname='" + name + "' cname='" + chinese + "'>" + name + "</div>";
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
                    categories: gene,
                    title: null,
                    reversed: true,
                    labels: {
                        useHTML: true,
                        formatter: function () {
                            return '<span class="js-gene-info">' + this.value + '</span>';
                        }
                    }
                },
                colorAxis: {
                    min: 0,
                    max: 100,
                    minColor: '#ffffff',
                    /*maxColor: '#386cca'*/
                    maxColor: '#0F9145'
                },
                legend: {
                    align: 'right',
                    verticalAlign: 'top',
                    symbolHeight: 30
                },
                plotOptions: {
                    series: {
                        borderColor: "#DDD",
                        borderWidth: 1,
                        dataLabels: {
                            enabled: true,
                            formatter: function () {
                                return ""
                            }
                        },
                        cursor: "pointer",
                        point: {
                            events: {
                                click: function () {
                                    if ("${isAjax}" == "true") {

                                    } else {
                                        $("#specificForm").find("input").val(heatmapcategory.join(","));
                                        $("#specificForm").submit();
//                                        console.log(this.value, this.series.xAxis.categories[this.x]["name"], this.series.yAxis.categories[this.y]);
                                    }
                                }
                            }
                        }

                    }

                },
                tooltip: {
                    enabled: true,
//                    formatter: function () {
//                        return '<b>' + en2cn(this.series.xAxis.categories[this.point.x].name) + '</b><b>【' +
//                            this.series.yAxis.categories[this.point.y] + '】</b>:<b>' +  this.point.value + '</b>';
//                    }
                    backgroundColor: '#FFF',
                    borderColor: '#DDD',
                    borderWidth: 1,
                    borderRadius: 5,
                    useHTML: true,
                    style: {
                        "fontSize": "12px"
                    },
                    padding: 0,
                    formatter: function () {
                        var str = '';
                        str += '<div style="background-color:#fff; padding: 8px; box-shadow: 0 0 10px #AAA; border-radius: 5px;">'
                        str += '    <div ><span style="display:inline-block; height:22px; width: 90px;">Gene name :</span>' + this.series.yAxis.categories[this.point.y] + '</div>'
//                        str += '    <div ><span style="display:inline-block; height:22px; width: 90px;">Cultivar :</span>AC colombe vs pagoda </div>'
//                        str += '    <div ><span style="display:inline-block; height:22px; width: 90px;">Phenotype :</span>High isoflavonaid content vs law isoflavonoid content. </div>'
                        str += '<div style="border-top: 1px solid #DDD; margin: 10px 0;"></div>'
//                        str += '<div><span style="display: inline-block; background-color: #386cca; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 110px;">Log-fold change:</span> -5.5</div>'
                        /*str += '<div><span style="display: inline-block; background-color: #386cca; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 70px;">FPKM:</span>' + (this.point.value * 1).toFixed(4) + ' </div>'*/
                        str += '<div><span style="display: inline-block; background-color: #0F9145; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 70px;">FPKM:</span>' + (this.point.value * 1).toFixed(4) + ' </div>'
                        str += '</div>'

                        return str;
//                         return '<b>' + this.series.xAxis.categories[this.point.x]["name"] + '</b> sold <br><b>' +
//                         this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
                    }

                },
                series: [{
                    name: '',
                    borderWidth: 1,
                    data: data,
                    dataLabels: {
                        enabled: false,
                        color: '#000000'
                    }
                }]
            });

            if ('${isAjax}' == "true") {
                chart.legend.update({x: -150});
            }
        }

        //鼠标移动上去事件
        function cateOnHover() {
            $(".cateOnToggleClass").hover(function () {
                var str = "";
                if ($(this).attr("dname")) {
                    str = $(this).attr("dname");
                } else {
                    str = $(this).html();
                }
                var chinese = $(this).attr("cname");
                var content = '';
                if (chinese != "") {
                    content += "<div class='title'>" + chinese + "</div>";
                }
                else {
                    content += "<div class='title'>" + str + "</div>";
                }
                content += "<div class='sub-title'>" + str.split("_")[0] + "</div>";
                for (var i = 0; i < heatmapdata.length; i++) {
//                console.log(" str : "+str + " == "+heatmapdata[i].pname+"  == "+heatmapdata[i].name);
                    if (heatmapdata[i].level == 1 && str == heatmapdata[i].pname) {
                        content += "<a class='tips-qtl' href='${ctxroot}/mrna/list?type=Tissues&keywords=" + heatmapdata[i].name + "'>" + heatmapdata[i].name + "<\/a>";
                    }

                }
                $.pt({
                    time: 1000,
                    target: $(this),
                    height: "80",
                    autoClose: true,
                    content: content
                });
            }, function () {
                $(".pt").hide();
            });
        }


        // 种类节点点击事件
        function cateOnToggle(obj) {
            if ($(obj).hasClass("has-child")) {
                if ($(obj).hasClass("close")) {
                    openNode(obj);
                } else if ($(obj).hasClass("open")) {
                    closeNode(obj);
                }
            }
        }

        // 展开节点
        function openNode(obj) {
            var str = "";
            if ($(obj).attr("dname")) {
                str = $(obj).attr("dname");
            } else {
                str = $(obj).html();
            }

            for (var i = 0; i < heatmapdata.length; i++) {
                if (heatmapdata[i].pname == str) {
                    var openNode = heatmapdata[i];
                    openNode.hasChild = judgeChildren(openNode.name)
                    for (var j = 0; j < g_cate.length; j++) {
                        if (g_cate[j].name == str) {
                            g_cate[j].state = "open";
                            g_cate.splice(j + 1, 0, openNode);
                            break;
                        }
                    }
                }
            }
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }

        // 收缩节点
        function closeNode(obj) {
            var str = "";
            if ($(obj).attr("dname")) {
                str = $(obj).attr("dname");
            } else {
                str = $(obj).html();
            }
            for (var i = 0; i < heatmapdata.length; i++) {
                if (heatmapdata[i].pname == str) {
                    var subPname = "";
                    for (var j = 0; j < g_cate.length; j++) {
                        if (g_cate[j].name == str) {
                            g_cate[j].state = "close";
                        }
                        if (g_cate[j].pname == str) {
                            subPname = g_cate[j].name;
                            g_cate.splice(j, 1);
                        }
                    }
                    if (subPname != "") {
                        for (var k = 0; k < g_cate.length; k++) {
                            if (g_cate[k].pname == subPname) {
                                g_cate.splice(k, 1);
                            }
                        }
                    }
                }
            }
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }


        $("body").on("click", ".cateOnToggleClass", function () {
            cateOnToggle($(this));
            cateOnHover();
        });

        cateOnHover();


//        $("#export").click(function() {
//            chart.exportChart();
//        });


    })();


    /*基因详情拖动弹框*/
    /*$(".genesInfo").draggable({containment: "body"});*/
</script>
