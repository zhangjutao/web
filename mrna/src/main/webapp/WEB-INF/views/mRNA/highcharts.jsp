<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/8/3
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script src="https://cdn.hcharts.cn/jquery/jquery-2.1.1.min.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/highcharts-more.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts/modules/heatmap.js"></script>
    <script src="https://cdn.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>

  <style>
    .open {
      color: #688be2;
      font-weight: bold;
    }
    .js-label{
      padding-left: 20px;
      height: 20px;
      line-height: 20px;
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
<%--热力图--%>
<div id="heatmap" style="height: 360px; margin: auto; min-width: 310px; max-width: 1000px"></div>
<%--相线图--%>
<div id="container" style="height: 320px; margin: auto; min-width: 310px; max-width: 1000px"></div>
<%--折线图--%>
<div id="line" style="height: 340px; margin: auto; min-width: 310px; max-width: 1000px"></div>

<script>

  $(function () {

    var chart;
    var g_cate;

    var categories = [
      {"name": "根", "level":0, values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "根1", "level":1, pname:'根', values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "根11", "level":2, pname:'根1', values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "茎", "level":0, values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "茎2", "level":1, pname:'茎', values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "茎21", "level":2, pname:'茎2', values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "花", "level":0, values: [ 1,2,3,4,5 ], state:"close"},
      {"name": "芽", "level":0, values: [ 1,2,3,4,5 ], state:"close"},
    ];
    var geneData =  [ 'Glyma7G849001','Glyma7G849002','Glyma7G849003','Glyma7G849004','Glyma7G849005' ];

    var chartData =  [[0, 0, 0], [0, 1, 19], [0, 2, 8], [0, 3, 24], [0, 4, 67],
      [1, 0, 92], [1, 1, 58], [1, 2, 78], [1, 3, 117], [1, 4, 48],
      [2, 0, 35], [2, 1, 15], [2, 2, 123], [2, 3, 64], [2, 4, 52],
      [3, 0, 72], [3, 1, 132], [3, 2, 114], [3, 3, 19], [3, 4, 16],
      [4, 0, 38], [4, 1, 5], [4, 2, 8], [4, 3, 117], [4, 4, 115],
      [5, 0, 88], [5, 1, 32], [5, 2, 12], [5, 3, 6], [5, 4, 120],
      [6, 0, 13], [6, 1, 44], [6, 2, 88], [6, 3, 98], [6, 4, 96],
      [7, 0, 31], [7, 1, 1], [7, 2, 82], [7, 3, 32], [7, 4, 30]];

    g_cate = initCategories(categories);
    renderChart(geneData, g_cate, getChartData(g_cate));

    // 初始化显示的种类数据，默认显示第一级
    function initCategories(categories){
      var d = new Array();
      for (var i = 0; i < categories.length; i++) {
        if(categories[i].level == 0){
          d.push(categories[i]);
        }
      }
      return d;
    }

    // 根据基因种类数据产生图表数据
    function getChartData(categories){
      var chartData = new Array();
      for (var i = 0; i < categories.length; i++) {
        var values = categories[i].values;
        for (var j = 0; j < values.length; j++) {
          var arr = [i,j,values[j]];
          chartData.push(arr);
        }
      }
      return chartData;
    }

    // 绘制热力图
    function renderChart(gene, cate, data) {
      if(chart) {chart.destroy();}
      // 热力图
      chart =  Highcharts.chart('heatmap', {
        chart: {
          type: 'heatmap',
          marginTop: 140,
          events: {
            render: function() {
            }
          }
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
            rotation: "0",
            y: -55,
            formatter: function() {
              // console.log(this.value["name"]);
              var name = this.value["name"];
              var level = this.value["level"];
              var state = this.value["state"];
              var _label = "";

              switch(level){
                case 0 :
                  _label += "<div class='js-label has-child "+state+" ml-20 cateOnToggleClass' >" + name + "</div><div class='js-label'>&nbsp</div><div class='js-label'>&nbsp</div>";
                  break
                case 1 :
                  _label += "<div class='js-label'>&nbsp</div><div class='js-label has-child "+state+" ml-10 cateOnToggleClass' >" + name + "</div><div class='js-label'>&nbsp</div>";
                  break
                case 2 :
                  _label += "<div class='js-label'>&nbsp</div><div class='js-label'>&nbsp</div><div class='js-label' style='margin-left:10px'>" + name + "</div>";
                  break
              }

              return _label;
            }
          }
        },
        yAxis: {
          categories: gene,
          title: null
        },
        colorAxis: {
          min: 0,
          minColor: '#ffffff',
          maxColor: '#386cca'
        },
        legend: {
          align: 'right',
          verticalAlign: 'top'
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
                  console.log(this.value, this.series.xAxis.categories[this.x]["name"], this.series.yAxis.categories[this.y]);
                }
              }
            }

          }

        },
        tooltip: {
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
            str += '    <div ><span style="display:inline-block; height:22px; width: 90px;">Cultivar :</span>AC colombe vs pagoda </div>'
            str += '    <div ><span style="display:inline-block; height:22px; width: 90px;">Phenotype :</span>High isoflavonaid content vs law isoflavonoid content. </div>'
            str += '<div style="border-top: 1px solid #DDD; margin: 10px 0;"></div>'
            str += '<div><span style="display: inline-block; background-color: #386cca; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 110px;">Log-fold change:</span> -5.5</div>'
            str += '<div><span style="display: inline-block; background-color: #386cca; width: 10px; height: 10px; margin-right: 5px;"></span><span style="display:inline-block; height:22px; width: 110px;">Adjusted p-value:</span> <math><mtext>1.5658</mtext><mo>&times;</mo><msup><mtext>10</mtext><mtext selected="true">-111</mtext></msup></math></div>'
            str += '</div>'

            return str;
            // return '<b>' + this.series.xAxis.categories[this.point.x]["name"] + '</b> sold <br><b>' +
            // this.point.value + '</b> items on <br><b>' + this.series.yAxis.categories[this.point.y] + '</b>';
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
    }

    // 种类节点点击事件
    function cateOnToggle(obj){
      if($(obj).hasClass("close")){
        openNode(obj);
      } else if($(obj).hasClass("open")){
        closeNode(obj);
      }
    }

    // 展开节点
    function openNode(obj){
      var new_cate = new Array();
      var str = $(obj).html();
      for (var i = 0; i < categories.length; i++) {
        if(categories[i].pname == str){
          var openNode = categories[i];
          var flag = true;
          for(var j = 0; j < g_cate.length; j++){
            if (g_cate[j].name == str){
              g_cate[j].state = "open";
            }
            if (getIndex(openNode.name) < getIndex(g_cate[j].name)) {
              if (flag) {
                openNode.state = "close";
                new_cate.push(openNode);
                flag = false;
              }
              new_cate.push(g_cate[j]);
            } else {
              new_cate.push(g_cate[j]);
            }

          }

          g_cate = new_cate;
          renderChart(geneData, g_cate, getChartData(g_cate));
        }
      }
    }

    // 根据节点名称获取节点位置
    function getIndex(nodeName){
      for (var i = 0; i < categories.length; i++) {
        if(categories[i].name == nodeName){
          return i;
        }
      }
    }

    // 收缩节点
    function closeNode(obj){
      var str = $(obj).html();
      for (var i = 0; i < categories.length; i++) {
        if(categories[i].pname == str){
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

          renderChart(geneData, g_cate, getChartData(g_cate));
        }
      }
    }

    $("body").on("click",".cateOnToggleClass",function(){
      cateOnToggle($(this));
    });

    $("#export").click(function() {
      chart.exportChart();
    });

    // 箱线图
    var chartBox = new Highcharts.Chart('container', {
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
        sourceHeight: 400,
        sourceWidth: 1000,
        scale: 2
      },
      colors: ["#386cca", "#ef6062", "#ffba02", "#009cbc", "#6a6ad5"] ,
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
        itemStyle: {"line-height": "10px","font-size": "12px", "font-weight": "200" , "color": "#898989"}
      },
      xAxis: {
        categories: ['根', '茎', '叶', '花', '豆荚', '种子','幼苗','芽'],
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
        lineWidth: 1,
        lineColor: "#000"
      },
      tooltip: {
        pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {series.name}</b><br/>' + // eslint-disable-line no-dupe-keys
        '最大值: {point.high}<br/>' +
        'Q2\t: {point.q3}<br/>' +
        '中位数: {point.median}<br/>' +
        'Q1\t: {point.q1}<br/>' +
        '最小值: {point.low}<br/>'
      },
      series: [{
        name: 'Glyma7G849000',
        data: [
          [760, 801, 848, 895, 965],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910],
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [714, 762, 817, 870, 918],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910]
        ],
        fillColor: "#386cca",
        tooltip: {
          headerFormat: '<em>实验号码： {point.key}</em><br/>'
        }
      },{
        name: 'Glyma7G849002',
        data: [
          [760, 801, 848, 895, 965],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910],
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [714, 762, 817, 870, 918],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910]
        ],
        fillColor: "#ef6062",
        tooltip: {
          headerFormat: '<em>实验号码： {point.key}</em><br/>'
        }
      },{
        name: 'Glyma7G849003',
        data: [
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910],
          [760, 801, 848, 895, 965],
          [714, 762, 817, 870, 918],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910]
        ],
        fillColor: "#ffba02",
        tooltip: {
          headerFormat: '<em>实验号码： {point.key}</em><br/>'
        }
      },{
        name: 'Glyma7G849004',
        data: [
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [714, 762, 817, 870, 918],
          [724, 802, 806, 871, 950],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910],
          [760, 801, 848, 895, 965],
          [834, 836, 864, 882, 910]
        ],
        fillColor: "#009cbc",
        tooltip: {
          headerFormat: '<em>实验号码： {point.key}</em><br/>'
        }
      },{
        name: 'Glyma7G849005',
        data: [
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [714, 762, 817, 870, 918],
          [724, 802, 806, 871, 950],
          [834, 836, 864, 882, 910],
          [760, 801, 848, 895, 965],
          [733, 853, 939, 980, 1080],
          [714, 762, 817, 870, 918]
        ],
        fillColor: "#6a6ad5",
        tooltip: {
          headerFormat: '<em>实验号码： {point.key}</em><br/>'
        }
      }]
    });

    /**
     * Highcharts 在 4.2.0 开始已经不依赖 jQuery 了，直接用其构造函数既可创建图表
     **/
    var chartLine = new Highcharts.Chart('line', {
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
        sourceHeight: 400,
        sourceWidth: 1000,
        scale: 2
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
        itemStyle: {"line-height": "10px","font-size": "12px", "font-weight": "200" , "color": "#898989"}
      },
      xAxis: {
        categories: ['根', '茎', '叶', '花', '豆荚', '种子','幼苗','芽'],
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
        lineWidth: 1,
        lineColor: "#000"
      },
      series: [{
        name: 'Glyma7G849000',
        data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5],
        marker: {
          enabled: false,
          symbol: "circle"
        },
        lineWidth: 1
      }, {
        name: 'Glyma7G849002',
        data: [0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1],
        marker: {
          enabled: false,
          symbol: "circle"
        },
        lineWidth: 1
      }, {
        name: 'Glyma7G849003',
        data: [0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3],
        marker: {
          enabled: false,
          symbol: "circle"
        },
        lineWidth: 1
      }, {
        name: 'Glyma7G849004',
        data: [11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8],
        marker: {
          enabled: false,
          symbol: "circle"
        },
        lineWidth: 1
      }, {
        name: 'Glyma7G849005',
        data: [13.5, 17.0, 18.6, 16.6, 14.2, 9.5, 14.5, 18.2],
        marker: {
          enabled: false,
          symbol: "circle"
        },
        lineWidth: 1
      }]
    });

  });


</script>

</body>
</html>
