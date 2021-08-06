<template>
<div>
  <div class="fb2Select">
    <select id="category-option" v-model="category">
      <option disabled value="">선택하세요</option>
      <option>test1</option>
      <option>test2</option>
      <option>test3</option>
    </select>
    {{category}}

    <select id="product-option" v-model="product">
      <option disabled value="">선택하세요</option>
      <option>test1</option>
      <option>test2</option>
      <option>test3</option>
    </select>
    {{product}}
  <button id="submit-btn">확인</button>
  </div>
  <div class="fb2Chart">
      <div id="chartFloat" class="col-lg-4" :class="{'text-right': isRTL}"  >
        <card id="cardWidth" type="chart">
          <template slot="header">
            <h5 class="card-category">Viewer Ratings By Advertisement</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 남성</h3>
          </template>
          <div class="chart-area" >
            <line-chart style="height: 35vh"
                        chart-id="blue-line-chart"
                        :chart-data="blueLineChart.chartData"
                        :gradient-stops="blueLineChart.gradientStops"
                        :extra-options="blueLineChart.extraOptions">
            </line-chart>
          </div>
        </card>
      </div> 

      <div id="chartFloat" class="col-lg-4" :class="{'text-right': isRTL}" style="margin-left:00px;">
        <card id="cardWidth" type="chart">
          <template slot="header">
            <h5 class="card-category">Viewer Ratings By Advertisement</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 여성</h3>
          </template>
          <div class="chart-area">
            <line-chart style="height: 35vh;"
                        chart-id="red-line-chart"
                        :chart-data="redLineChart.chartData"
                        :gradient-stops="redLineChart.gradientStops"
                        :extra-options="redLineChart.extraOptions">
            </line-chart>
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

  export default {
    components: {
      LineChart,
      BarChart,
      TaskList,
      UserTable
    },
    data() {
      return {
        blueLineChart: {
          extraOptions: chartConfigs.greenChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "광고별 시청률",
              fill: true,
              borderColor: config.colors.info,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              pointBackgroundColor: config.colors.info,
              pointBorderColor: 'rgba(255,255,255,0)',
              pointHoverBackgroundColor: config.colors.info,
              pointBorderWidth: 20,
              pointHoverRadius: 4,
              pointHoverBorderWidth: 15,
              pointRadius: 4,
              data: [90, 50, 83, 40, 30,10],
            }]
          }
        },
        redLineChart: {
          extraOptions: chartConfigs.greenChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "광고별 시청률",
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
          }
        },
        category: '',
        product: '',
      }
    }
  };
</script>
<style lang="scss" scoped>
  .fb2Select {
    text-align:center;
    margin-top:10vh;
  }
  #category-option, #product-option {
    background-color:rgb(39,41,61);
    color:rgb(255,255,255);
    border: 1px solid rgb(39,41,61);
  }
  #category-option {
    width:15%;
    height:50px;
  }
  #product-option {
    width:50%;
    height:50px;
  }
  .fb2Chart {
    margin:0 auto;
    position:absolute;
    width:100%;
    left:25%;
    margin-top:10vh;
  } 
  #chartFloat {
    float:left;
  }
  #cardWidth {
    height:50vh;
  }
  #submit-btn {
    margin-left: 10px;
    background-color:rgb(39,41,61);
    color:rgb(255,255,255);
    border: 1px solid rgb(89,91,111);
    border-radius: 20px;
    width:5%;
    height:50px;
  }
</style>
