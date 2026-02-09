<template>
  <div class="top-container">
    <a-row :gutter="16">
      <a-col :span="8">
        <a-card title="商品购买量TOP10" :bordered="false">
          <div id="buyTop10Chart" style="width: 100%; height: 300px;"></div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="商品浏览量TOP10" :bordered="false">
          <div id="pvTop10Chart" style="width: 120%; height: 300px;"></div>
        </a-card>
      </a-col>
      <a-col :span="8">
        <a-card title="商品转化率TOP10" :bordered="false">
          <div id="transTop10Chart" style="width: 100%; height: 300px;"></div>
        </a-card>
      </a-col>
    </a-row>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {useStore} from "vuex";

export default {
  name: 'TopView',
  data() {
    return {
      buyTop10Data: [],
      pvTop10Data: [],
      transTop10Data: []
    };
  },
  mounted() {
    this.loadData();
  },
  methods: {
    async loadData() {
      try {
        const store = useStore();
        const res = await store.dispatch("chart/GetTopEchartsList");

        if (res.code === 0) this.buyTop10Data = res.data;
        if (res.code === 0) this.pvTop10Data = res.data;
        if (res.code === 0) this.transTop10Data = res.data;

        this.renderCharts();
      } catch (error) {
        console.error('加载数据失败:', error);
      }
    },

    renderCharts() {
      this.renderBuyTop10Chart();
      this.renderPvTop10Chart();
      this.renderTransTop10Chart();
    },

    renderBuyTop10Chart() {
      const chart = echarts.init(document.getElementById('buyTop10Chart'));
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: '{b}<br/>购买量: {c}'
        },
        xAxis: {
          type: 'category',
          data: this.buyTop10Data.map(item => item.categoryID),
          axisLabel: {
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '购买量'
        },
        series: [{
          data: this.buyTop10Data.map(item => item.购买量),
          type: 'bar',
          itemStyle: {
            color: '#1890ff'
          }
        }]
      };
      chart.setOption(option);
    },

    renderPvTop10Chart() {
      const chart = echarts.init(document.getElementById('pvTop10Chart'));
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: '{b}<br/>浏览量: {c}'
        },
        xAxis: {
          type: 'category',
          data: this.pvTop10Data.map(item => item.categoryID),
          axisLabel: {
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '浏览量'
        },
        series: [{
          data: this.pvTop10Data.map(item => item.浏览量),
          type: 'bar',
          itemStyle: {
            color: '#52c41a'
          }
        }]
      };
      chart.setOption(option);
    },

    renderTransTop10Chart() {
      const chart = echarts.init(document.getElementById('transTop10Chart'));
      const option = {
        tooltip: {
          trigger: 'axis',
          formatter: '{b}<br/>转化率: {c}'
        },
        xAxis: {
          type: 'category',
          data: this.transTop10Data.map(item => item.categoryID),
          axisLabel: {
            rotate: 45
          }
        },
        yAxis: {
          type: 'value',
          name: '转化率(%)'
        },
        series: [{
          data: this.transTop10Data.map(item => parseFloat(item.转化率)),
          type: 'bar',
          itemStyle: {
            color: '#faad14'
          }
        }]
      };
      chart.setOption(option);
    }
  }
};
</script>

<style scoped>
.top-container {
  padding: 24px;
}

.ant-card {
  margin-bottom: 16px;
}
</style>