<template>
  <div class="hello">
    <h2>Essential Links</h2>
    <div id="main" style="width: 1104px;height:464px;"></div>
  </div>
</template>

<script>
import echarts from "echarts";
import { getWebkitJson  } from "@/api/index"; //页面调用的事件方法

export default {
  name: 'HelloWorld',
  data () {
    return {
      chart: null,
    }
  },
  mounted(){
    //画图
    this.drawGrapWebkit();
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods:{
    drawGrapWebkit(){
      this.chart = echarts.init(document.getElementById('main'));
      this.chart.showLoading();

      getWebkitJson().then(webkitDep => {
        // let webkitDep = res;
        this.chart.hideLoading();
        let option = {
          legend: {
              data: ['HTMLElement', 'WebGL', 'SVG', 'CSS', 'Other']
          },
          series: [{
              type: 'graph',
              layout: 'force',
              animation: false,
              label: {
                  normal: {
                      position: 'right',
                      formatter: '{b}'
                  }
              },
              draggable: true,
              data: webkitDep.nodes.map(function (node, idx) {
                  node.id = idx;
                  return node;
              }),
              categories: webkitDep.categories,
              force: {
                  // initLayout: 'circular'
                  // repulsion: 20,
                  edgeLength: 5,
                  repulsion: 20,
                  gravity: 0.2
              },
              edges: webkitDep.links
          }]
        };

        this.chart.setOption(option);
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
</style>
