<template>
  <div>
    <a-card class="version-info">
      <h1>电商用户行为分析大屏</h1>
      <a-skeleton :loading="loading" active :paragraph="{ rows: 4 }" />

      <a-card v-if="!loading" style="margin-bottom: 10px; min-width: 370px">
        <a-row>
          <a-col :span="12">
            <a-statistic
                title="系统已启动"
                :value="
                timerObject.days +
                '天' +
                timerObject.hours +
                '时' +
                timerObject.minutes +
                '分' +
                timerObject.seconds +
                '秒'
              "
                style="margin-right: 50px"
            />
          </a-col>
          <a-col :span="12">
            <a-statistic
                title="启动时间"
                :precision="2"
                :value="systemStartupTime"
            />
          </a-col>
        </a-row>
      </a-card>

      <a-list
          :grid="{ gutter: 16, xs: 1, sm: 2, md: 4, lg: 4, xl: 6, xxl: 3 }"
          :data-source="systemData"
          v-if="!loading"
      >
        <template #renderItem="{ item }">
          <a-list-item style="min-width: 120px">
            <a-card
                bodyStyle="overflow:hidden;text-overflow: ellipsis;white-space: nowrap;"
                :title="item.title"
                :hoverable="true"
            >{{ item.content }}</a-card
            >
          </a-list-item>
        </template>
      </a-list>
      <a-tag color="cyan">推荐分析</a-tag>
      <a-list item-layout="horizontal" :data-source="chartList">
        <template #renderItem="{ item }">
          <a-list-item>
            <a-list-item-meta :description="item.chartDes">
              <template #title>
                <a @click="view(item)">{{ item.name }}</a>
                <a-tag
                    v-if="store.getters.depId == item.depId"
                    color="green"
                    style="margin-left: 5px"
                >{{ item.depName }}</a-tag
                >
                <a-tag v-else style="margin-left: 5px">{{
                    item.depName
                  }}</a-tag>
                <span style="color: grey; font-size: 12px"
                >{{ item.username }} 发布于
                  {{
                    formatDate(new Date(item.createTime), "yyyy-MM-dd hh:mm:ss")
                  }}</span
                >
              </template>
              <template #avatar>
                <a-avatar :src="item.avatar" />
              </template>
            </a-list-item-meta>
          </a-list-item>
        </template>
      </a-list>
      <a-tag color="cyan">图表分析</a-tag>
      <div class="chart-container">
        <div
            v-for="(item, index) in echartsList"
            :key="index"
            class="chart-item"
            @click="view(item)"
        >
          <div class="chart-title">{{ item.name }}</div>
          <div
              ref="chartRefs"
              :data-index="index"
              class="chart-canvas"
              style="width: 100%; height: 300px;"
          ></div>
        </div>
      </div>
    </a-card>
  </div>
</template>

<script>
import { reactive, toRefs, defineComponent, onMounted, nextTick } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { formatDate } from "@/utils/utils";
import * as echarts from 'echarts'; // 添加ECharts导入

export default defineComponent({
  setup() {
    const state = reactive({
      chartList: [],
      systemData: [],
      echartsList: [],
      reData: [],
      loading: true,
      systemStartupTime: null,
      timerObject: {
        days: "",
        hours: "",
        minutes: "",
        seconds: "",
      },
      chartInstances: [], // 存储图表实例
    });

    const store = useStore();
    // const chartRefs = []; // 图表DOM引用

    const handleGetIndexInfo = async () => {
      const indexInfoResult = await store.dispatch("index/GetIndexInfo");
      state.systemData = indexInfoResult.data;

      // 处理数据
      for (let index in state.systemData) {
        if (state.systemData[index].title == "系统启动时间戳") {
          state.systemStartupTime = getLocalTime(
              state.systemData[index].content
          );
          leftTimer(state.systemData[index].content);
          setInterval(leftTimer, 1000, state.systemData[index].content);
        }
      }

      state.loading = false;
    };

    handleGetIndexInfo();

    // API 获取推荐图表列表
    const handleGetRecChartList = async () => {
      const recResult = await store.dispatch("chart/GetRecChartList");
      state.chartList = recResult.data;
    };

    handleGetRecChartList();



    const handleGetEchartsList = async () => {
      try {
        const allResult = await store.dispatch("chart/GetEchartsList");
        console.log(allResult.data);
        state.echartsList = allResult.data;

        // 数据获取后渲染图表
        nextTick(() => {
          renderAllCharts();
        });
      } catch (error) {
        console.error('获取所有图表列表失败:', error);
      }
    };
    handleGetEchartsList();

    // 渲染所有图表
    const renderAllCharts = () => {
      // 先销毁之前的图表实例
      destroyAllCharts();

      state.echartsList.forEach((item, index) => {
        const chartDom = document.querySelector(`.chart-canvas[data-index="${index}"]`);
        if (chartDom) {
          const chartInstance = echarts.init(chartDom);
          state.chartInstances[index] = chartInstance;

          // 创建柱状图配置
          const option = {
            title: {
              text: '商品购买量TOP10',
              left: 'center'
            },
            tooltip: {
              trigger: 'axis',
              formatter: function(params) {
                return `商品ID: ${params[0].name}<br/>购买量: ${params[0].value}`;
              }
            },
            xAxis: {
              type: 'category',
              data: state.echartsList.map(item => item.categoryID), // 商品ID作为x轴
              axisLabel: {
                rotate: 45 // 如果ID太长可以旋转显示
              }
            },
            yAxis: {
              type: 'value',
              name: '购买量'
            },
            series: [{
              type: 'bar',
              data: state.echartsList.map(item => parseInt(item.购买量)), // 购买量作为y轴
              itemStyle: {
                color: '#5470c6'
              }
            }]
          };

          chartInstance.setOption(option);
        }
      });
    };

    // 销毁所有图表实例
    const destroyAllCharts = () => {
      state.chartInstances.forEach(instance => {
        if (instance) {
          instance.dispose();
        }
      });
      state.chartInstances = [];
    };

    // 添加窗口大小变化时的自适应
    const handleResize = () => {
      state.chartInstances.forEach(instance => {
        if (instance) {
          instance.resize();
        }
      });
    };
    onMounted(() => {
      window.addEventListener('resize', handleResize);
    });

    // 组件卸载时清理
    const cleanup = () => {
      destroyAllCharts();
      window.removeEventListener('resize', handleResize);
    };

    const router = useRouter();

    const view = (record) => {
      if (router.hasRoute("analyze-view")) {
        console.log("跳转key：" + "analyze-view");
        router.push({ name: "analyze-view", params: record });
      } else {
        router.push({ name: "error-403" });
      }
    };

    // 计时组件
    const leftTimer = (nS) => {
      var leftTime = new Date(parseInt(nS)) - new Date(); //计算剩余的毫秒数
      var days = parseInt(leftTime / 1000 / 60 / 60 / 24, 10); //计算剩余的天数
      var hours = parseInt((leftTime / 1000 / 60 / 60) % 24, 10); //计算剩余的小时
      var minutes = parseInt((leftTime / 1000 / 60) % 60, 10); //计算剩余的分钟
      var seconds = parseInt((leftTime / 1000) % 60, 10); // 计算剩余的秒数
      if (days < 0) days = -days;
      if (hours < 0) hours = -hours;
      if (minutes < 0) minutes = -minutes;
      if (seconds < 0) seconds = -seconds;
      let timerObject = {
        days: days,
        hours: hours,
        minutes: minutes,
        seconds: seconds,
      };
      state.timerObject = timerObject;
    };

    // 时间戳转日期 例：2022/2/27 10:22:55
    const getLocalTime = (nS) => {
      return new Date(parseInt(nS))
          .toLocaleString()
          .replace(/年|月/g, "-")
          .replace(/日/g, " ");
    };

    return {
      ...toRefs(state),
      view,
      store,
      formatDate,
      cleanup,
    };
  },
  beforeUnmount() {
    if (this.timer) {
      clearInterval(this.timer);
    }
    this.cleanup();
  },
});
</script>
<style lang="less" scoped>
.version-info {
  text-align: center;
  margin-bottom: 24px;

  h1 {
    color: #1f2f3d;
    margin-bottom: 8px;
    font-size: 28px;
  }
}
.chart-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(400px, 1fr));
  gap: 16px;
  margin-top: 16px;
}

.chart-item {
  border: 1px solid #e8e8e8;
  border-radius: 4px;
  padding: 12px;
  cursor: pointer;
  transition: box-shadow 0.3s;

  &:hover {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.12);
  }
}

.chart-title {
  font-weight: bold;
  margin-bottom: 8px;
  text-align: center;
}

.chart-info {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 8px;
  font-size: 12px;

  .chart-author {
    color: #666;
  }

  .chart-time {
    color: #999;
    margin-left: auto;
  }
}
</style>