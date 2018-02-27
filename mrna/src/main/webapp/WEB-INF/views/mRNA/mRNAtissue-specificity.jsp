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
    <title>组织特异性表达</title>
    <link rel="stylesheet" href="${ctxStatic}/css/public.css">
    <link rel="stylesheet" href="${ctxStatic}/css/mRNA.css">
    <link rel="stylesheet" href="${ctxStatic}/css/tooltips.css">
    <link rel="shortcut icon" type="image/x-icon" href="${ctxStatic}/images/favicon.ico">

    <!--jquery-1.11.0-->
    <script src="${ctxStatic}/js/jquery-1.11.0.js"></script>
    <script src="${ctxStatic}/js//highcharts/highcharts.js"></script>
    <script src="${ctxStatic}/js/highcharts/highcharts-more.js"></script>
    <script src="${ctxStatic}/js/highcharts/exporting.js"></script>
    <script src="${ctxStatic}/js/highcharts/heatmap.js"></script>
    <script src="${ctxStatic}/js/highcharts/highcharts-zh_CN.js"></script>
    <script src="${ctxStatic}/js/jquery.pure.tooltips.js"></script>
    <script src="${ctxStatic}/js/laypage/laypage.js"></script>
    <script src="https://cdn.bootcss.com/lodash.js/4.17.4/lodash.min.js"></script>
    <script src="https://cdn.bootcss.com/echarts/3.6.2/echarts.min.js"></script>
    <script src="${ctxStatic}/js/dataTool.min.js"></script>

    <style>
        .ga-checkbox {
            cursor: pointer;
        }
        .ga-2nd-gene-block {
            display: none;
        }
        .ga-2nd-gene-block.toggle {
            display: block;
        }
        #heatmap_ .highcharts-container {
            top: -40px!important;
        }
    </style>
</head>

<body>

<%@ include file="/WEB-INF/views/include/mrna-header.jsp"%>
<!--header-->
<section class="container">
    <div class="contant">
        <div class="tissue-specificity-charts">
            <div class="item-header">
                <div class="icon-left"><img src="${ctxStatic}/images/Linkage-group.png">组织特异性表达热图</div>
            </div>
            <div class="tissue-specificity-chartstab">
                <div class="genes-search">
                    <span>Genes:</span>
                    <input class="js-search-gene-value" type="text" placeholder="请输入您要搜索的基因ID或名称功能">
                    <button class="btn js-search-gene-btn" type="button"><img src="${ctxStatic}/images/search.png">搜索</button>
                </div>
                <div class="genes-result">
                    <div class="genes-result-left js-search-gene-result">
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                    </div>
                    <div  class="genes-result-mid">
                        <p class="genes-result-chooseAll"><button type="button" class="btn-fill js-choose-gene-all"><span> > </span>全部选中</button></p>
                        <p class="genes-result-clearAll"><button type="button" class="btn-fill js-remove-gene-all"><span> < </span>全部清除</button></p>
                    </div>
                    <div class="genes-result-right js-choose-gene-result">
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                        <%--<p><a href="#">Glyma11G11100(1代ID)基因名</a></p>--%>
                    </div>

                    <div class="genes-result-confirm"><button type="button" class="btn js-specific-gene">确定</button></div>
                </div>
                <div class="genes-pic" style="position: relative;">
                    <div class="genes-choose-export" style="position: relative; top: 15px; right: 0; z-index: 9;">
                        <button type="button" class="btn  js-export-heat">导出图表</button>
                    </div>
                    <mrna:chart-heatmap data="${data}" isAjax="true" gaHeight="600"></mrna:chart-heatmap>
                </div>
                <%@ include file="/WEB-INF/views/include/pagination.jsp" %>
             </div>
        </div>
        <div class="box-line-chart">
            <div class="box-chart">
                <div class="box-header">
                    <div class="item-header">
                        <div class="icon-left">
                            <img src="${ctxStatic}/images/box-line-chart.png">箱线图
                        </div>
                    </div>
                    <div class="genes-choose-export">
                        <button type="button" class="btn js-2nd-gene-toggle">基因选择</button>
                        <button type="button" class="btn js-export-box">导出图表</button>
                    </div>
                </div>
                <div class="box-contant js-2nd-gene-block ga-2nd-gene-block">
                    <div class="che-list js-checkbox-list">
                        <span class="tab-title">选择基因:</span>
                        <div class="set-genes-tab">
                            <div class="have-chosen">
                                <label class="have-chosen-num">(已选择基因<span class="js-2nd-choose-text">0/5</span>)</label>
                                <div class="have-chosen-tab js-2nd-choose-gene">
                                    <%--<label class="choose-tab-ac js-2nd-choose-item"><span></span>基因2345687</label>--%>
                                    <%--<label><span></span>基因23</label>--%>
                                    <%--<label><span></span>基因12345</label>--%>
                                    <%--<label><span></span>基因123</label>--%>
                                </div>
                                <div class="box-genes-tab js-checkbox-gene">
                                    <dl>
                                        <%--<dd><label for="sampleName"><span id="sampleName"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="study"><span id="study"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="reference"><span id="reference"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="tissue"><span id="tissue"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="stage"><span id="stage"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="treat"><span id="treat"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="genetype"><span id="genetype"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="preservation"><span id="preservation"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="phenotype"><span id="phenotype"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="environment"><span id="environment"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="cultivar"><span id="cultivar"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="scientificName"><span id="scientificName"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="libraryLayout"><span id="libraryLayout"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="spots"><span id="spots"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="run"><span id="run"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="sarstudy"><span id="sarstudy"></span>基因23456</label></dd>--%>
                                        <%--<dd><label for="experimant"><span id="experimant"></span>基因23456</label></dd>--%>
                                    </dl>
                                </div>
                            </div>
                        </div>
                        <p class="btn-group">
                            <button class="btn-fill btn-confirm js-2nd-specific-gene">确认</button>
                            <button class="btn-chooseAll js-btn-clear">清空</button>
                            <button class="btn-toggle">收起<img src="${ctxStatic}/images/down.png"></button>
                        </p>
                    </div>
                </div>
                <div class="box-chart-pic">
                    <%--相线图--%>
                    <div id="container" style="height: 400px; margin: 20px 0; min-width: 310px; width: 100%; "></div>
                </div>
            </div>
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
        </div>
        </div>
    </div>
</section>
<!--section-->
<%@ include file="/WEB-INF/views/include/footer.jsp" %>

<script>

    $(function () {

        var GENES = '${genes}';

        window.specificGenes = []; // 特异表达的基因组
        var secondSpecificGenes = []; // 最多选5个基因组

        /*
         * 搜索基因 Start
         */
        $(".js-search-gene-value").on("keyup", function() {
//            _.debounce(function() {
                searchGene();
//            }, 500);
        });

        $(".js-search-gene-btn").click(function() {
            var gene = $(".js-search-gene-value").val();
            if(gene == "") {
                return alert("输入不能为空");
            }
            searchGene();
        });

        function searchGene() {
            var gene = $(".js-search-gene-value").val();
            $.ajax({
                url: "${ctxroot}/diffgene/genes",
                data: {gene: gene, pageNo: 1, pageSize: 100},
                type: "GET",
                dataType: "json",
                timeout: 10000,
                success: function(res) {
                    if(res.data.length > 0) {
                        var len = res.data.length;
                        var str = '';
                        for(var i = 0; i < len; i++) {
                            str += '<p><a class="js-gene-item" href="javascript:void(0);">'+ res.data[i] +'</a></p>';
                        }
                        $(".js-search-gene-result").empty().append(str);
                    } else {
                        $(".js-search-gene-result").empty();
//                        alert("没有搜索结果");
                    }
                }
            });
        }
        /*
         * 搜索基因 End
         */

        /*
         * 搜索基因 全部选中
         */
        $(".js-choose-gene-all").click(function() {
            var genes = $(".js-search-gene-result").find(".js-gene-item");
            $.each(genes, function(idx, el) {
                 $(el).trigger("click");
            });
        });

        /*
         * 搜索基因 全部移除
         */
        $(".js-remove-gene-all").click(function() {
            var genes = $(".js-choose-gene-result").find(".js-gene-item");
            $.each(genes, function(idx, el) {
                $(el).trigger("click");
            });
        });

        /*
         * 搜索基因 单个选中
         */
        $(".js-search-gene-result").on("click", ".js-gene-item", function() {
            var gene = $(this).text();
            var idx = _.indexOf(specificGenes, gene);
            if(idx < 0) {
                $(".js-choose-gene-result").append('<p><a class="js-gene-item" href="javascript:void(0);">'+ gene +'</a></p>');
                specificGenes.push(gene);
                $(this).remove();
            } else {
                $(this).remove();
            }

        });

        /*
         * 搜索基因 单个移除
         */
        $(".js-choose-gene-result").on("click", ".js-gene-item", function() {
            var gene = $(this).text();
            _.pull(specificGenes, gene);
            $(".js-search-gene-result").append('<p><a class="js-gene-item" href="javascript:void(0);">'+ $(this).text() +'</a></p>');
            $(this).remove();
        });

        /*
         * 基因特异表达
         */
        $(".js-specific-gene").click(function() {
            secondSpecificGenes = [];
            specificGenes = [];

            specificGenes = getSpecificGenes();
            if(specificGenes.length != 0) {
                renderGenesCheckBox();

                var genes = specificGenes.join(",");
                getHeatMap(genes);

                $(".js-2nd-specific-gene").trigger("click");
            } else {
                alert("请选择基因");
            }

        });

        // 获取表达基因
        function getSpecificGenes() {
            var arr = [];
            var genes = $(".js-choose-gene-result").find(".js-gene-item");
            $.each(genes, function(idx, el) {
                arr.push($(el).text());
            });
            arr = arr.sort();
            return arr;
        }

        /*
         * 二次选择基因 Start
         */

        // 生成所选基因选框
        function renderGenesCheckBox() {
            var str = '';
            for(var i in specificGenes) {
                var gene = specificGenes[i];
                str += '<dd><label class="ga-checkbox js-checkbox-item-gene cls_'+gene+'" for="'+ gene +'"><span id="'+ gene +'"></span>'+ gene +'</label></dd>'
            }
            $(".js-checkbox-gene > dl").empty().append(str);

            var checkboxes = $(".js-checkbox-gene").find(".js-checkbox-item-gene");
            if(checkboxes.length > 5) {
                for(var i = 0; i < 5; i++) {
                    $(checkboxes[i]).trigger("click");
                }
            } else {
                for(var i = 0; i < checkboxes.length; i++) {
                    $(checkboxes[i]).trigger("click");
                }
            }
        }

        // 二次选择
        $(".js-checkbox-list").on("click", ".js-checkbox-item-gene", function() {
            var checked = $(this).hasClass("choose-tab-ac");
            var gene = $(this).text();
            if(checked) {
                $(this).removeClass("choose-tab-ac");
                _.pull(secondSpecificGenes, gene);
            } else {
                if(secondSpecificGenes.length > 4) {
                    alert("最多选5个基因");
                } else {
                    $(this).addClass("choose-tab-ac");
                    secondSpecificGenes.push(gene);
                }
            }
            render2ndCheckboxGene();
            renderChooseText();
        });

        function render2ndCheckboxGene() {
            var str = '';
            for(var i in secondSpecificGenes) {
                str += '<label class="choose-tab-ac js-2nd-choose-item"><span></span>'+ secondSpecificGenes[i] +'</label>'
            }
            $(".js-2nd-choose-gene").empty().append(str);
        }

        $(".js-checkbox-list").on("click", ".js-2nd-choose-item", function() {
            var gene = $(this).text();
            _.pull(secondSpecificGenes, gene);
            render2ndCheckboxGene();
            var cls = ".cls_" + gene;
            $(".js-2nd-choose-gene").find(cls).parents("dd").remove();
            $(".js-checkbox-gene").find(cls).removeClass("choose-tab-ac");
            renderChooseText();
        });

        function renderChooseText() {
            var len = secondSpecificGenes.length;
            $(".js-2nd-choose-text").html(len + "/5");
        }

        $(".js-btn-clear").click(function() {
            $.each( $(".js-checkbox-gene").find(".choose-tab-ac"), function(idx, el) {
                $(el).trigger("click");
            });
        });

        /*
         * 二次选择基因 End
         */

        // 二次表达（更新象限图、折线图）
        $(".js-2nd-specific-gene").click(function() {
            if(secondSpecificGenes.length != 0) {
                getBox(secondSpecificGenes.join(","));
                getLine(secondSpecificGenes.join(","));
            } else {
                alert("请勾选基因");
            }

        });


        function objKeySort(obj) {//排序的函数
            var newkey = Object.keys(obj).sort();
            //先用Object内置类的keys方法获取要排序对象的属性名，再利用Array原型上的sort方法对获取的属性名进行排序，newkey是一个数组
            var newObj = {};//创建一个新的对象，用于存放排好序的键值对
            for (var i = 0; i < newkey.length; i++) {//遍历newkey数组
                newObj[newkey[i]] = obj[newkey[i]];//向新创建的对象中按照排好的顺序依次增加键值对
            }
            return newObj;//返回排好序的新对象
        }

        function en22cn(str) {
            var name = "";
            switch (str){
                case "pod" :
                    name = "豆荚";
                    break;
                case "seed" :
                    name = "种子";
                    break;
                case "root" :
                    name = "根";
                    break;
                case "shoot" :
                    name = "芽";
                    break;
                case "leaf" :
                    name = "叶子";
                    break;
                case "seedling" :
                    name = "幼苗";
                    break;
                case "flower" :
                    name = "花";
                    break;
                case "stem" :
                    name = "茎";
                    break;
                default:
                    name = str;
            }
            return name;
        }

        function getBox(genes) {
            $.ajax({
                url: "${ctxroot}/specific/boxplot",
                type: "POST",
                data: {genes: genes},
                dataType: "json",
                success: function(res) {
                    // .. res 数据处理
                    var categories = [];
                    for(var i in res[0]){
                        var data0 = objKeySort(res[0][i]);
                        console.log('data0:', data0);
                        $.each(data0, function(idx, el){
//                            categories.push(en22cn(idx));
                            categories.push(el.chinese);
                        });
                    }
                    console.log('cate: ', categories);
                    var src = {};
                    src.data = [];
                    $.each(res, function(idx, el) {
                        var arr = [];
                        var obj = {};
                        for(var key in el) {
                            obj["name"] = key;
                            var d = objKeySort(el[key]);
                            console.log('el', d)
                            for(var key2 in d) {
                                var data1 = d[key2].data;
                                var tmp = [];
                                for(var k3 in data1) {
                                    tmp.push( data1[k3]*1 );
                                }
                                arr.push(tmp);
                            }
                        }
//                        console.log('arr: ',obj.name , JSON.stringify(arr));
                        var dd = echarts.dataTool.prepareBoxplotData(arr, {boundIQR: 'none'});
                        obj["data"] = dd.boxData;
//                        console.log('boxData', dd);
                        src.data.push(obj);
                    });
                    var sortArr = src.data;
                    function compare(propertyName) {
                        return function(object1, object2) {
                            var value1 = object1[propertyName];
                            var value2 = object2[propertyName];
                            if (value2 < value1) {
                                return 1;
                            } else if (value2 > value1) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    }
                    //使用方法
                    sortArr.sort(compare("name"));
//                    console.log(sortArr);
                    src.data = sortArr;
                    src.cate = categories;
                    renderBox(src);
                }
            });
        }

        var LineMapDt, LineCate, prevData;
        function getLine(genes) {
            $.ajax({
                url: "${ctxroot}/specific/line",
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

        var chartBox, chartLine;

        function renderBox(data) {
            var colors = ["#386cca", "#ef6062", "#ffba02", "#009cbc", "#6a6ad5"];
            var names = [];
            $.each(data.data, function(idx, el) {
                names.push(el["name"]);
                el["fillColor"] = colors[idx];
                el["tooltip"] = {
                    headerFormat: '<em>{point.key}</em><br/>'
                }
            });
            var filename = "box plot map " + names[0];

            if(chartBox) {chartBox.destroy();}
            // 箱线图
            chartBox = new Highcharts.Chart('container', {
                chart: {
                    type: 'boxplot',
                    marginTop: 80
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
                colors: colors ,
                title: {
                    text: null
                },
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
                    min: 0,
                    lineWidth: 1,
                    lineColor: "#000"
                },
                tooltip: {
                    pointFormatter: function() {
//                        console.log(this);
                        return '<span style="color:'+ this.color +'">\u25CF</span> <b>' + this.series.name + '</b><br/>' + // eslint-disable-line no-dupe-keys
                            '最大值: ' + this.high.toFixed(2) + '<br/>' +
                            '上四分位数\t: ' + this.q3.toFixed(2) + '<br/>' +
                            '中位数: ' + this.median.toFixed(2) + '<br/>' +
                            '下四分位数\t: ' + this.q1.toFixed(2) + '<br/>' +
                            '最小值: ' + this.low.toFixed(2) + '<br/>'
                    }
//                    pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {series.name}</b><br/>' + // eslint-disable-line no-dupe-keys
//                    '最大值: {point.high}<br/>' +
//                    '上四分位数\t: {point.q3}<br/>' +
//                    '中位数: {point.median}<br/>' +
//                    '下四分位数\t: {point.q1}<br/>' +
//                    '最小值: {point.low}<br/>'
                },
                series: data.data
            });
        }

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

        $("body").on("click",".cateOnToggleLineClass",function(){
            cateOnToggleLine($(this));
        });

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


        var initPage = function() {
            if(GENES) {
                specificGenes = GENES;
                var tmp = GENES.split(",");
                var str = '';
                $.each(tmp, function (idx, el) {
                    str += '<p><a class="js-gene-item" href="javascript:void(0);">' + el + '</a></p>'
                });
                $(".js-choose-gene-result").append(str);

                $(".js-specific-gene").trigger("click");

            }

            $(".js-export-line").click(function() {
                chartLine.exportChart();
            });

            $(".js-export-box").click(function() {
               chartBox.exportChart();
            });

            $(".js-export-heat").click(function() {
               chart.exportChart();
            });

            $(".js-2nd-gene-toggle").click(function() {
               if( $(".js-2nd-gene-block").hasClass("toggle") ) {
                   $(".js-2nd-gene-block").removeClass("toggle");
               } else {
                   $(".js-2nd-gene-block").addClass("toggle");
               }
            });

            $(".btn-toggle").click(function() {
                $(".js-2nd-gene-toggle").trigger("click");
            });

            $(".ga-heat-table").on("focus", ".laypage_skip", function() {
                $(this).addClass("isFocus");
            });
            $(".ga-heat-table").on("blur", ".laypage_skip", function() {
                $(this).removeClass("isFocus");
            });

            // 注册 enter 事件
            document.onkeydown = function(e) {
                var _page_skip = $('.laypage_skip');
                if(e && e.keyCode==13){ // enter 键

                    if( _page_skip.hasClass("isFocus") ) {
                        var genes = specificGenes.join(",");
                        getHeatMap(genes, _page_skip.val() * 1);
                    } else {

                    }

                }
            }

            // 修改每页显示条数
            $("body").on("change", ".lay-per-page-count-select", function() {
                pageSize = $(this).val();
                var genes = specificGenes.join(",");
                getHeatMap(genes, 1);
            });

        }();


    });

</script>
</body>
</html>