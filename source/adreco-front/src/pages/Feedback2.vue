<template>
  <div>
    <!--
      <div class="col-lg-4" :class="{'text-right': isRTL}" >
        <card type="chart">
          <template slot="header">
            <h5 class="card-category">{{$t('dashboard.completedTasks')}}광고별 시청률</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 남성</h3>
          </template>
          <div class="chart-area">
            <line-chart style="height: 100%"
                        chart-id="green-line-chart"
                        :chart-data="greenLineChart.chartData"
                        :gradient-stops="greenLineChart.gradientStops"
                        :extra-options="greenLineChart.extraOptions">
            </line-chart>
          </div>
        </card>
      </div> 
      -->
      <div class="col-lg-4" :class="{'text-right': isRTL}">
        <card type="chart">
          <template slot="header">
            <h5 class="card-category"><!--{{$t('dashboard.completedTasks')}}-->광고별 시청률</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 여성</h3>
          </template>
          <div class="chart-area">
            <line-chart style="height: 100%"
                        chart-id="red-line-chart"
                        :chart-data="redLineChart.chartData"
                        :gradient-stops="redLineChart.gradientStops"
                        :extra-options="redLineChart.extraOptions">
            </line-chart>
          </div>
        </card>
      </div>
  </div>
</template>
<script>
  import LineChart from '@/components/Charts/LineChart';
  import BarChart from '@/components/Charts/BarChart';
  import * as chartConfigs from '@/components/Charts/config';
  import TaskList from './Dashboard/TaskList';
  import UserTable from './Dashboard/UserTable';
  import config from '@/config';

  export default {
    components: {
      LineChart,
      BarChart,
      TaskList,
      UserTable
    },
    data() {
      return {
        redLineChart: {
          extraOptions: chartConfigs.greenChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "제품 관심도",
              fill: true,
              borderColor: config.colors.danger,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              pointBackgroundColor: config.colors.danger,
              pointBorderColor: 'rgba(255,255,255,0)',
              pointHoverBackgroundColor: config.colors.danger,
              pointBorderWidth: 20,
              pointHoverRadius: 4,
              pointHoverBorderWidth: 15,
              pointRadius: 4,
              data: [80, 90, 77, 40, 10,30],
            }]
          },
          gradientColors: ['rgba(66,134,121,0.15)', 'rgba(66,134,121,0.0)', 'rgba(66,134,121,0)'],
          gradientStops: [1, 0.4, 0],
        },
        blueLineChart: {
          extraOptions: chartConfigs.greenChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "제품 관심도",
              fill: true,
              borderColor: config.colors.danger,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              pointBackgroundColor: config.colors.danger,
              pointBorderColor: 'rgba(255,255,255,0)',
              pointHoverBackgroundColor: config.colors.danger,
              pointBorderWidth: 20,
              pointHoverRadius: 4,
              pointHoverBorderWidth: 15,
              pointRadius: 4,
              data: [80, 90, 77, 40, 10,30],
            }]
          },
          gradientColors: ['rgba(66,134,121,0.15)', 'rgba(66,134,121,0.0)', 'rgba(66,134,121,0)'],
          gradientStops: [1, 0.4, 0],
        }
      }
    },
  };
</script>
<style>
</style>
