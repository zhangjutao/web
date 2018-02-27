<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="data" type="java.lang.String" required="true" description="" %>
<%@ attribute name="isAjax" type="java.lang.String" required="false" description="" %>
<%@ attribute name="gaHeight" type="java.lang.String" required="false" description="" %>
<c:set var="id" value="${java.util.UUID.randomUUID()}"/>

<div id="heatmap_${id}" style=" margin: auto; min-width: 310px; width: 100%; height: ${gaHeight}px;"></div>

<div class="genesInfo" style="display: none;">
    <div class="genesInfo-head">
        <p>基因<span class="js-gene-head-name"></span>信息</p>
        <a href="javascript:void(0);">x</a>
    </div>
    <iframe id="geneIframe" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" src=""></iframe>
</div>

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
<script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
<script>

    $("body").on("click", ".js-gene-info", function(e) {
        var version = 'gmx_ensembl_release23';
        var geneName = $(this).text();
        $(".js-gene-head-name").html(geneName);
        $("#geneIframe").attr("src", "${ctxroot}/geneInfo?geneName="+ geneName + "&version=" + version);
        e.preventDefault();
        $(".genesInfo").show();

    });
    $(".genesInfo-head > a").click(function() {
        $(".genesInfo").hide();
    });

    var chart;
    (function(){


        var g_cate;

        var heatmapdata, heatmapcategory;

        // 分类英文转中文显示
        window.en2cn = function(str){
            var name = "";
            switch (str){
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

        if("${isAjax}" == "true") {
            window.pageSize = 20;
            window.getHeatMap = function (genes, curr) {
                var innergenes = genes.split(",");
                innergenes = innergenes.sort();
                curr = curr || 1;
                var len = innergenes.length;
                var pageTotal = Math.ceil(specificGenes.length / pageSize);

                $(".lay-per-page-count-select").val(window.pageSize);

                var start = 1 + (curr - 1) * pageSize - 1;
                var end = curr * pageSize;
                if(end > len) {
                    end = len;
                }
                innergenes = innergenes.slice(start, end);
                innergenesStr = innergenes.join(",");

                $.ajax({
                    url: "${ctxroot}/specific/hitmap",
                    type: "POST",
                    data: {genes: innergenesStr},
                    dataType: "json",
                    success: function(res) {
                        var dd = res;
                        heatmapdata = _.orderBy(dd.cate, ["name"]);
                        heatmapcategory =  dd.gens;
                        g_cate = initCategories();
                        $("#heatmap_${id}").css({"height": (70 * innergenes.length + 120) +"px"});
                        renderChart(heatmapcategory, g_cate, getChartData(g_cate));

                        // 显示分页
                        laypage({
                            cont: $('#pagination'), //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>
                            pages: pageTotal, //通过后台拿到的总页数
                            curr: curr || 1, //当前页
                            skin: '#5c8de5',
                            skip: true,
                            prev: '<',
                            next: '>',
                            groups: 3, //连续显示分页数
                            jump: function (obj, first) { //触发分页后的回调
                                $("#total-page-count > span").html(specificGenes.length);
                                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr
                                    getHeatMap(genes, obj.curr);
                                }
                            }
                        });
                    }
                });
            }
        } else {
            var dd = eval(${data});
            heatmapdata = _.orderBy(dd.cate, ["name"]);
            heatmapcategory =  dd.gens;
            g_cate = initCategories();
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }

        // 初始化显示的种类数据，默认显示第一级
        function initCategories(){
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
            for (var i = 0; i < heatmapdata.length; i++) {
                if(heatmapdata[i].pname == cate_name && heatmapdata[i].level == 1){
                    hasChild = true;
                    break;
                }
            }
            return hasChild;
        }


        // 根据基因种类数据产生图表数据
        function getChartData(categoriesT){
            var chartData = new Array();
            for (var i = 0; i < categoriesT.length; i++) {
                var values = categoriesT[i].values;
                for (var j = 0; j < values.length; j++) {
                    var arr = [i,j,values[j]];
                    chartData.push(arr);
                }
            }
            return chartData;
        }

        // 绘制热力图
        function renderChart(gene, cate, data) {
//            console.log("gene: ", gene);
//            console.log("cate: ", cate);
//            console.log("data: ", data);
            if(chart) {chart.destroy();}

            var filename = "heat map " + gene[0];

            // 热力图
            chart =  Highcharts.chart('heatmap_${id}', {
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
                        rotation: -70,
                        y: -10,
//                        y: -55,
                        formatter: function() {
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
                            switch(level){
                                case 0 :
                                    _label += "<div class='js-label "+hasChildClass+state+" ml-20 cateOnToggleClass' dname='"+name+"' cname='"+ chinese +"'>" + chinese + "</div>";
                                    break
                                case 1 :
                                    _label += "<div class='js-label "+hasChildClass+state+" ml-20 cateOnToggleClass' dname='"+name+"' cname='"+ chinese +"'>" + name + "</div>";
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
                        formatter: function() {
                            return '<span class="js-gene-info">'+ this.value +'</span>';
                        }
                    }
                },
                colorAxis: {
                    min: 0,
                    max: 100,
                    minColor: '#ffffff',
                    maxColor: '#386cca'
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
                            formatter: function() {
                                return ""
                            }
                        },
                        cursor: "pointer",
                        point: {
                            events: {
                                click: function() {
                                    if("${isAjax}" == "true") {

                                    } else {
                                        $("#specificForm").find("input").val(heatmapcategory.join(","));
                                        $("#specificForm").submit();
                                        console.log(this.value, this.series.xAxis.categories[this.x]["name"], this.series.yAxis.categories[this.y]);
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
                        str += '<div><span style="display: inline-block; background-color: #386cca; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 70px;">FPKM:</span>'+ (this.point.value*1).toFixed(4) +' </div>'
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

            if('${isAjax}' == "true") {
                chart.legend.update({x: -150});
            }
        }

        //鼠标移动上去事件
        function cateOnHover(){
            $(".cateOnToggleClass").hover( function() {
                var str = "";
                if($(this).attr("dname")){
                    str = $(this).attr("dname");
                }else{
                    str = $(this).html();
                }
                var chinese = $(this).attr("cname");
                var content = '';
                if(chinese != "") {
                    content += "<div class='title'>"+ chinese +"</div>";
                }
                else {
                    content += "<div class='title'>"+str+"</div>";
                }
                content += "<div class='sub-title'>"+str.split("_")[0]+"</div>";
                for(var i=0; i<heatmapdata.length; i++){
//                console.log(" str : "+str + " == "+heatmapdata[i].pname+"  == "+heatmapdata[i].name);
                    if(heatmapdata[i].level == 1 && str == heatmapdata[i].pname){
                        content += "<a class='tips-qtl' href='${ctxroot}/mrna/list?type=Tissues&keywords="+heatmapdata[i].name+"'>" + heatmapdata[i].name + "<\/a>";
                    }

                }
                $.pt({
                    time: 1000,
                    target: $(this),
                    height: "80",
                    autoClose: true,
                    content: content
                });
            }, function() {
                $(".pt").hide();
            });
        }


        // 种类节点点击事件
        function cateOnToggle(obj){
            if($(obj).hasClass("has-child")) {
                if ($(obj).hasClass("close")) {
                    openNode(obj);
                } else if ($(obj).hasClass("open")) {
                    closeNode(obj);
                }
            }
        }

        // 展开节点
        function openNode(obj){
            var str = "";
            if($(obj).attr("dname")){
                str = $(obj).attr("dname");
            }else{
                str = $(obj).html();
            }

            for (var i = 0; i < heatmapdata.length; i++) {
                if(heatmapdata[i].pname == str){
                    var openNode = heatmapdata[i];
                    openNode.hasChild = judgeChildren(openNode.name)
                    for(var j = 0; j < g_cate.length; j++){
                        if (g_cate[j].name == str){
                            g_cate[j].state = "open";
                            g_cate.splice(j+1,0,openNode);
                            break;
                        }
                    }
                }
            }
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }

        // 收缩节点
        function closeNode(obj){
            var str = "";
            if($(obj).attr("dname")){
                str = $(obj).attr("dname");
            }else{
                str = $(obj).html();
            }
            for (var i = 0; i < heatmapdata.length; i++) {
                if(heatmapdata[i].pname == str){
                    var subPname = "";
                    for(var j = 0; j < g_cate.length; j++){
                        if (g_cate[j].name == str){
                            g_cate[j].state = "close";
                        }
                        if(g_cate[j].pname == str){
                            subPname = g_cate[j].name;
                            g_cate.splice(j,1);
                        }
                    }
                    if (subPname != ""){
                        for(var k = 0; k < g_cate.length; k++){
                            if(g_cate[k].pname == subPname){
                                g_cate.splice(k,1);
                            }
                        }
                    }
                }
            }
            renderChart(heatmapcategory, g_cate, getChartData(g_cate));
        }


        $("body").on("click",".cateOnToggleClass",function(){
            cateOnToggle($(this));
            cateOnHover();
        });

        cateOnHover();


//        $("#export").click(function() {
//            chart.exportChart();
//        });


    })();

</script>
