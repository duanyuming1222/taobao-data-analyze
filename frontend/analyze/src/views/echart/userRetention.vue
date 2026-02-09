<template>
  <div class="charts-container">
    <div class="chart-row">
      <div class="chart-item">
        <div ref="directBuyChartRef" style="width: 100%; height: 300px;"></div>
      </div>
      <div class="chart-item">
        <div ref="favBuyChartRef" style="width: 100%; height: 300px;"></div>
      </div>
    </div>
    <div class="chart-row">
      <div class="chart-item">
        <div ref="cartBuyChartRef" style="width: 100%; height: 300px;"></div>
      </div>
      <div class="chart-item">
        <div ref="bothBuyChartRef" style="width: 100%; height: 300px;"></div>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, ref, onUnmounted } from 'vue';
import * as echarts from 'echarts';
import { useStore } from 'vuex';

export default {
  name: 'UserRetentionChart',
  setup() {
    const directBuyChartRef = ref(null);
    const favBuyChartRef = ref(null);
    const cartBuyChartRef = ref(null);
    const bothBuyChartRef = ref(null);

    const chartInstances = ref({
      directBuy: null,
      favBuy: null,
      cartBuy: null,
      bothBuy: null
    });

    const store = useStore();

    const initCharts = async () => {
      try {
        // 获取四个不同的数据结果
        const [clickResult, directBuyResult, favBuyResult, cartBuyResult, bothBuyResult] = await Promise.all([
          store.dispatch('chart/GetClickUsers'),
          store.dispatch('chart/GetDirectBuyUsers'),
          store.dispatch('chart/GetFavBuyUsers'),
          store.dispatch('chart/GetCartBuyUsers'),
          store.dispatch('chart/GetBothBuyUsers')
        ]);

        // 提取数据
        const clickUsers = clickResult.data[0].点击;
        const directByResultBuy = directBuyResult.data[0].购买;

        const favBuyResultBuy = favBuyResult.data[0].购买;
        const favBuyResultFav = favBuyResult.data[0].收藏;

        const cartBuyResultBuy = cartBuyResult.data[0].购买;
        const cartBuyResultAdd = cartBuyResult.data[0].加购物车;

        const bothBuyResultBuy = bothBuyResult.data[0].购买;
        const bothBuyResultBuyBoth = bothBuyResult.data[0].收藏且加购物车;

        console.log(clickResult, directBuyResult, favBuyResult, cartBuyResult, bothBuyResult);

        // 创建四个漏斗图的配置
        const chartOptions = {
          directBuy: createDirectBuyOption('直接购买转化漏斗', clickUsers, directByResultBuy, '#fac858'),
          favBuy: createFavBuyOption('收藏购买转化漏斗', clickUsers, favBuyResultFav, favBuyResultBuy, '#ee6666'),
          cartBuy: createCartBuyOption('加购购买转化漏斗', clickUsers, cartBuyResultAdd, cartBuyResultBuy, '#73c0de'),
          bothBuy: createBothBuyOption('收藏加购购买转化漏斗', clickUsers, bothBuyResultBuyBoth, bothBuyResultBuy, '#3ba272')
        };

        // 初始化四个图表
        if (directBuyChartRef.value) {
          chartInstances.value.directBuy = echarts.init(directBuyChartRef.value);
          chartInstances.value.directBuy.setOption(chartOptions.directBuy);
        }

        if (favBuyChartRef.value) {
          chartInstances.value.favBuy = echarts.init(favBuyChartRef.value);
          chartInstances.value.favBuy.setOption(chartOptions.favBuy);
        }

        if (cartBuyChartRef.value) {
          chartInstances.value.cartBuy = echarts.init(cartBuyChartRef.value);
          chartInstances.value.cartBuy.setOption(chartOptions.cartBuy);
        }

        if (bothBuyChartRef.value) {
          chartInstances.value.bothBuy = echarts.init(bothBuyChartRef.value);
          chartInstances.value.bothBuy.setOption(chartOptions.bothBuy);
        }

      } catch (error) {
        console.error('获取漏斗图数据失败:', error);
      }
    };

    // 创建漏斗图配置的辅助函数
    // 创建漏斗图配置的辅助函数
    const createDirectBuyOption = (title, totalUsers, convertedUsers, color) => {
      return {
        title: {
          text: title,
          left: 'center',
          textStyle: {
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '用户数量',
            type: 'funnel',
            left: '10%',
            top: 50,
            bottom: 30,
            width: '80%',
            min: 0,
            max: totalUsers,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside',
              formatter: '{c}'
            },
            labelLine: {
              length: 10,
              lineStyle: {
                width: 1,
                type: 'solid'
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              label: {
                fontSize: 14
              }
            },
            data: [
              { value: totalUsers, name: '点击用户', itemStyle: { color: '#5470c6' } },
              { value: convertedUsers, name: '购买用户', itemStyle: { color: color } }
            ]
          }
        ]
      };
    };

    const createFavBuyOption = (title, totalUsers, favUsers, buyUsers, color) => {
      return {
        title: {
          text: title,
          left: 'center',
          textStyle: {
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '用户数量',
            type: 'funnel',
            left: '10%',
            top: 50,
            bottom: 30,
            width: '80%',
            min: 0,
            max: totalUsers,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside',
              formatter: '{c}'
            },
            labelLine: {
              length: 10,
              lineStyle: {
                width: 1,
                type: 'solid'
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              label: {
                fontSize: 14
              }
            },
            data: [
              { value: totalUsers, name: '点击用户', itemStyle: { color: '#5470c6' } },
              { value: favUsers, name: '收藏用户', itemStyle: { color: '#91cc75' } },
              { value: buyUsers, name: '购买用户', itemStyle: { color: color } }
            ]
          }
        ]
      };
    };

    const createCartBuyOption = (title, totalUsers, cartUsers, buyUsers, color) => {
      return {
        title: {
          text: title,
          left: 'center',
          textStyle: {
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '用户数量',
            type: 'funnel',
            left: '10%',
            top: 50,
            bottom: 30,
            width: '80%',
            min: 0,
            max: totalUsers,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside',
              formatter: '{c}'
            },
            labelLine: {
              length: 10,
              lineStyle: {
                width: 1,
                type: 'solid'
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              label: {
                fontSize: 14
              }
            },
            data: [
              { value: totalUsers, name: '点击用户', itemStyle: { color: '#5470c6' } },
              { value: cartUsers, name: '加购物车', itemStyle: { color: '#fac858' } },
              { value: buyUsers, name: '购买用户', itemStyle: { color: color } }
            ]
          }
        ]
      };
    };

    const createBothBuyOption = (title, totalUsers, bothUsers, buyUsers, color) => {
      return {
        title: {
          text: title,
          left: 'center',
          textStyle: {
            fontSize: 14,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        series: [
          {
            name: '用户数量',
            type: 'funnel',
            left: '10%',
            top: 50,
            bottom: 30,
            width: '80%',
            min: 0,
            max: totalUsers,
            minSize: '0%',
            maxSize: '100%',
            sort: 'descending',
            gap: 2,
            label: {
              show: true,
              position: 'inside',
              formatter: '{c}'
            },
            labelLine: {
              length: 10,
              lineStyle: {
                width: 1,
                type: 'solid'
              }
            },
            itemStyle: {
              borderColor: '#fff',
              borderWidth: 1
            },
            emphasis: {
              label: {
                fontSize: 14
              }
            },
            data: [
              { value: totalUsers, name: '点击用户', itemStyle: { color: '#5470c6' } },
              { value: bothUsers, name: '收藏且加购物车', itemStyle: { color: '#73c0de' } },
              { value: buyUsers, name: '购买用户', itemStyle: { color: color } }
            ]
          }
        ]
      };
    };

    const handleResize = () => {
      Object.values(chartInstances.value).forEach(instance => {
        if (instance) {
          instance.resize();
        }
      });
    };

    onMounted(() => {
      initCharts();
      window.addEventListener('resize', handleResize);
    });

    onUnmounted(() => {
      Object.values(chartInstances.value).forEach(instance => {
        if (instance) {
          instance.dispose();
        }
      });
      window.removeEventListener('resize', handleResize);
    });

    return {
      directBuyChartRef,
      favBuyChartRef,
      cartBuyChartRef,
      bothBuyChartRef
    };
  }
};
</script>

<style scoped>
.charts-container {
  padding: 20px;
  background: #fff;
  border-radius: 4px;
  margin: 20px;
}

.chart-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.chart-item {
  flex: 1;
  margin: 0 10px;
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 10px;
}
</style>