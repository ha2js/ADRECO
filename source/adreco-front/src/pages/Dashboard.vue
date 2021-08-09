<template>
  <div>

    <div class="row">
      <div class="col-12">
        <card id="contents-size" type="chart">
          <template slot="header">
            <div class="row">
              <div class="col-sm-9 text-left">
                <h5 class="card-category">{{$t('dashboard.monthlyTrendEng')}}</h5>
                <h4 class="card-title">{{this.currentProduct.productName}} 상품 월별 추이</h4>
              </div>
              <div class="col-sm-3">
                <div class="btn-group btn-group-toggle float-right"
                     data-toggle="buttons">
                  <label v-for="(option, index) in chartCategories"
                         :key="option"
                         class="btn btn-sm btn-primary btn-simple"
                         :class="{active: bigLineChart.activeIndex === index}"
                         :id="index">
                    <input type="radio"
                           @click="initBigChart(index)"
                           name="options" autocomplete="off"
                           :checked="bigLineChart.activeIndex === index">
                    {{option}}
                  </label>
                </div>
              </div>
            </div>
          </template>
          <div class="chart-area">
            <line-chart style="height: 37vh"
                        ref="bigChart"
                        chart-id="big-line-chart"
                        :chart-data="bigLineChart.chartData"
                        :gradient-colors="bigLineChart.gradientColors"
                        :gradient-stops="bigLineChart.gradientStops"
                        :extra-options="bigLineChart.extraOptions">
            </line-chart>
          </div>
        </card>
      </div>
    </div>
    <div class="row">
      <div class="col-6">
        <card type="card">
          <template slot="header">
            <h5 class="card-category">{{$t('dashboard.productTop3Eng')}}</h5>
            <h4 class="card-title"><i class="tim-icons icon-bell-55 text-primary "></i> {{this.currentProduct.keyword}} 카테고리의 Top3 상품</h4>
          </template>
          <div class="table-responsive">
            <base-table :data="this.categoryTop3"
                        :columns="this.columns">
            </base-table>
          </div>
        </card>
      </div>
      <div class="col-6">
        <card type="chart">
          <template slot="header">
            <h5 class="card-category">{{$t('dashboard.ageGroupsViewerEng')}}</h5>
            <h4 class="card-title"><i class="tim-icons icon-tv-2 text-info "></i> {{this.currentProduct.keyword}} 카테고리의 연령대별 시청률</h4>
          </template>
          <div class="chart-area">
            <bar-chart style="height: 100%"
                       chart-id="blue-bar-chart"
                       ref="barChart"
                       :chart-data="blueBarChart.chartData"
                       :gradient-stops="blueBarChart.gradientStops"
                       :extra-options="blueBarChart.extraOptions">
            </bar-chart>
          </div>
        </card>
      </div>
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
  import axios from "axios";
  import { BaseTable } from "@/components";

  export default {
    components: {
      LineChart,
      BarChart,
      TaskList,
      UserTable,
      BaseTable
    },
    data() {
      return {
        mapData: {},
        currentProduct: "",
        categoryTop3: [{rank:1}, {rank:2}, {rank:3}],
        chartCategories: [
            "평균 시청률",
            "평균 시청시간"
        ],
        columns : [
          "Rank",
          "Product-Name"
        ],
        bigLineChart: {
          allData: [
          ],
          activeIndex: 0,
          chartData: {
            datasets: [{}],
            labels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'],
          },
          extraOptions: chartConfigs.purpleChartOptions,
          gradientColors: config.colors.primaryGradient,
          gradientStops: [1, 0.4, 0],
          categories: []
        },
        blueBarChart: {
          extraOptions: chartConfigs.barChartOptions,
          chartData: {
            labels: ['20대 남자', '20대 여자', '30대 남자', '30대 여자', '40대 남자', '40대 여자', '50대 남자', '50대 여자', '60대 남자', '60대 여자'],
            datasets: [{
              label: "시청률",
              fill: true,
              borderColor: config.colors.info,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              data: [],
            }]
          },
          gradientColors: config.colors.primaryGradient,
          gradientStops: [1, 0.4, 0],
        }
      }
    },
    methods: {
      initBigChart(index) {
        let chartData = {
          datasets: [{
            fill: true,
            borderColor: config.colors.primary,
            borderWidth: 2,
            borderDash: [],
            borderDashOffset: 0.0,
            pointBackgroundColor: config.colors.primary,
            pointBorderColor: 'rgba(255,255,255,0)',
            pointHoverBackgroundColor: config.colors.primary,
            pointBorderWidth: 20,
            pointHoverRadius: 4,
            pointHoverBorderWidth: 15,
            pointRadius: 4,
            data: this.bigLineChart.allData[index],
          }],
          labels: ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC']
        }
        this.$refs.bigChart.updateGradients(chartData);
        this.bigLineChart.chartData = chartData;
        this.bigLineChart.activeIndex = index;
      },

      initBlueBarChart() {
        let chartData = {
          datasets: [{
            label: "시청률",
              fill: true,
              borderColor: config.colors.info,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              data: this.blueBarChart.chartData.datasets.data
          }],
          labels: this.blueBarChart.chartData.labels
        }
        this.$refs.barChart.updateGradients(chartData);
        this.blueBarChart.chartData = chartData;
      },

      async changeAdview() {
        await axios.get("/api/admin/getDashBoardInfo")
                .then((res) => {
                  this.mapData = res.data.data;
                })
                .catch((error) => {
                    console.log(error);
                })
      }
    },
    created() {
      this.changeAdview();
      setInterval(this.changeAdview, 2000);
    },
    watch: {
      mapData: function() {
          this.currentProduct = this.mapData.currentProduct;
          this.categoryTop3 = [];
          this.bigLineChart.allData = this.mapData.bigLineChartData;
          this.blueBarChart.chartData.datasets.data = this.mapData.blueBarChartData[0];
          this.blueBarChart.chartData.labels = this.mapData.barChartColumns;

          this.mapData.categoryTop3.forEach((element,index) => {
            const param = {
              "rank": index+1,
              "product-name": element.productName
            }
            this.categoryTop3.push(param);
          });
          this.initBigChart(this.bigLineChart.activeIndex);
          this.initBlueBarChart();
      }
    },
    mounted() {
      this.i18n = this.$i18n;
    }
  }
</script>
<style lang="scss" scoped>

  #contents-size {
    width:100%;
    height:50vh;
  }

</style>
