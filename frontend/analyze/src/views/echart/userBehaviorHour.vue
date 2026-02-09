<template>
  <div class="chart-container">
    <div ref="chart" class="chart" :style="{ height: '500px', width: '100%' }"></div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {useStore} from "vuex";
export default {
  data() {
    return {
      chart: null,
      chartData: {
        time: [],
        totalCount: [],
        pvCount: [],
        favCount: [],
        cartCount: [],
        buyCount: []
      }
    };
  },
  mounted() {
    this.initChart();
    this.fetchData();
  },
  beforeUnmount() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    initChart() {

      this.chart = echarts.init(this.$refs.chart);

      // 设置图表响应式
      window.addEventListener('resize', () => {
        this.chart.resize();
      });
    },

    async fetchData() {
      try {
        const store = useStore();
        const response = await store.dispatch("chart/GetUserBehaviorHour"); // 调用API获取数据
        this.processData(response.data);
        this.renderChart();
      } catch (error) {
        console.error('获取数据失败:', error);
      }
    },

    processData(data) {
      // 清空现有数据
      Object.keys(this.chartData).forEach(key => this.chartData[key] = []);

      // 处理数据
      data.forEach(item => {
        this.chartData.time.push(item.time);
        this.chartData.totalCount.push(item.totalCount);
        this.chartData.pvCount.push(item.pvCount);
        this.chartData.favCount.push(item.favCount);
        this.chartData.cartCount.push(item.cartCount);
        this.chartData.buyCount.push(item.buyCount);
      });
    },

    renderChart() {
      const option = {
        title: {
          text: '用户行为小时分析'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {
          data: ['总数', '浏览量', '收藏量', '加购量', '购买量']
        },
        grid: {
          left: '1%',
          right: '1%',  // 增加右侧边距，为第二个Y轴留出空间
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: this.chartData.time,
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '总数',
            position: 'left',
            axisLine: {
              show: true,
              lineStyle: {
                color: '#5470c6'
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          // 添加第二个Y轴 - 修改部分
          {
            type: 'value',
            name: '操作量',
            position: 'right',
            show: true,  // 显式设置显示
            axisLine: {
              show: true,
              lineStyle: {
                color: '#faad14'
              }
            },
            axisLabel: {
              formatter: '{value}'
            },
            splitLine: {
              show: false
            },
            // 添加Y轴名称样式，确保可见
            nameTextStyle: {
              color: '#faad14',
              fontSize: 14
            }
          }
        ],
        series: [
          // 柱状图 - 总数
          {
            name: '总数',
            type: 'bar',
            data: this.chartData.totalCount,
            itemStyle: {
              color: '#5470c6'
            },
            emphasis: {
              focus: 'series'
            }
          },
          // 折线图 - 浏览量 (保持在左侧Y轴)
          {
            name: '浏览量',
            type: 'line',
            data: this.chartData.pvCount,
            yAxisIndex: 0,  // 左侧Y轴
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2
            },
            itemStyle: {
              color: '#ff7a45'
            }
          },
          // 折线图 - 收藏量 (移至右侧Y轴)
          {
            name: '收藏量',
            type: 'line',
            data: this.chartData.favCount,
            yAxisIndex: 1,  // 右侧Y轴
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2
            },
            itemStyle: {
              color: '#faad14'
            }
          },
          // 折线图 - 加购量 (移至右侧Y轴)
          {
            name: '加购量',
            type: 'line',
            data: this.chartData.cartCount,
            yAxisIndex: 1,  // 右侧Y轴
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2
            },
            itemStyle: {
              color: '#52c41a'
            }
          },
          // 折线图 - 购买量 (移至右侧Y轴)
          {
            name: '购买量',
            type: 'line',
            data: this.chartData.buyCount,
            yAxisIndex: 1,  // 右侧Y轴
            symbol: 'circle',
            symbolSize: 6,
            lineStyle: {
              width: 2
            },
            itemStyle: {
              color: '#1890ff'
            }
          }
        ]
      };

      this.chart.setOption(option);
    }
  }
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 100%;
  padding: 20px;
  box-sizing: border-box;
}
</style>