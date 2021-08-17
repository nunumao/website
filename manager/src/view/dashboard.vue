<template>
  <d1-list-page title="控制面板" :shadow="false" :background="'transparent'">    
    <div class="h-100">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="grid-content bg-purple">
            <el-card shadow="always" @click="test">
              总是显示
            </el-card>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">2</div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">3</div>
        </el-col>
        <el-col :span="6">
          <div class="grid-content bg-purple">4</div>
        </el-col>
      </el-row>
      <el-row :gutter="20" style="margin-top:20px">
        <el-col :span="12">
          <el-card shadow="always">
            <div id="line" class="line"></div>
          </el-card>
        </el-col>
        <el-col :span="12">
          <el-card shadow="always">
            <div id="pie" class="pie"></div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </d1-list-page>
</template>
<script>
import * as echarts from "echarts";
export default {
  data() {
    return {
      p1: null,
      show: false,
      list: []
    }
  },
  mounted() {
    this.init();
  },
  methods: {
    upload() {
      this.show = true
    },
    test() {
      console.log(this.list);
    },
    init() {
      echarts.dispose(document.getElementById("line"));
      let opt = {
        title: {
          text: "折线图堆叠",
        },
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["邮件营销"],
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            name: "邮件营销",
            type: "line",
            stack: "总量",
            data: [120, 132, 101, 134, 90, 230, 210],
          },
        ],
      };
      let p1 = echarts.init(document.getElementById("line"));
      p1.setOption(opt);
    },
  },
};
</script>
<style lang="scss" scoped>
.line,
.pie {
  height: 300px;
}
</style>