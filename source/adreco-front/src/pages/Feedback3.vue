<template>
<div>
  <div class="fb2Select">
    <select id="category-option" @change="onSelectedCategory">
      <option v-for="category in categoryList" :key="category" :value="category">{{category}}</option>
    </select>

    <select id="product-option" @change="onSelectedProduct">
      <option v-for="product in productByCategory" :key="product.productName" :value="product.productName">{{product.productName}}</option>
    </select>

  <button id="submit-btn">확인</button>
  </div>
  <div class="fb2Chart">
      <div id="chartFloat" class="col-lg-4">
        <card id="cardWidth" type="chart">
          <template slot="header">
            <h5 class="card-category">Average Viewing Time By Advertisement</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 남성</h3>
          </template>
          <div class="chart-area" >
            <bar-chart style="height: 35vh"
                chart-id="blue-bar-chart"
                ref="barChart"
                :chart-data="blueBarChart.chartData"
                :gradient-stops="blueBarChart.gradientStops"
                :extra-options="blueBarChart.extraOptions">
            </bar-chart>
          </div>
        </card>
      </div> 

      <div id="chartFloat" class="col-lg-4" style="margin-left:00px;">
        <card id="cardWidth" type="chart">
          <template slot="header">
            <h5 class="card-category">Average Viewing Time By Advertisement</h5>
            <h3 class="card-title"><i class="tim-icons icon-single-02"></i> 여성</h3>
          </template>
          <div class="chart-area">
            <bar-chart style="height: 35vh"
                chart-id="red-bar-chart"
                ref="barChart"
                :chart-data="redBarChart.chartData"
                :gradient-stops="redBarChart.gradientStops"
                :extra-options="redBarChart.extraOptions">
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
  import axios from 'axios';

  export default {
    components: {
      LineChart,
      BarChart,
      TaskList,
      UserTable
    },
    data() {
      return {
        categoryList: [],
        productList: [],
        selectedCategory: "",
        selectedProduct: "",
        blueBarChart: {
          extraOptions: chartConfigs.barChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "시청 시간(초)",
              fill: true,
              borderColor: config.colors.info,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              data: [3, 15, 10, 13, 20, 2],
            }]
          }
        },
        redBarChart: {
          extraOptions: chartConfigs.barChartOptions,
          chartData: {
            labels: ['10대', '20대', '30대', '40대', '50대','60대'],
            datasets: [{
              label: "시청 시간(초)",
              fill: true,
              borderColor: config.colors.danger,
              borderWidth: 2,
              borderDash: [],
              borderDashOffset: 0.0,
              data: [7, 18, 24, 15, 18, 5],
            }]
          }
        },
    
      }
    },
    mounted() {
      axios.get("/api/admin/getFeedbackData")
          .then((res) => {
            const mapData = res.data.data;
            
            this.categoryList = mapData.categoryList;
            this.productList = mapData.productList;

            this.selectedCategory = this.categoryList[0];
            this.selectedProduct = this.productList[0].productName;
          })
          .catch(() => {
            
          })
    },
    computed: {
      productByCategory: function() {
        return this.productList.filter(product => product.keyword == this.selectedCategory);
      }
    },
    methods: {
      onSelectedCategory(e) {
        this.selectedCategory = e.target.value;
        this.selectedProduct = this.productByCategory[0].productName;
      },
      onSelectedProduct(e) {
        this.selectedProduct = e.target.value;
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
    margin-right: 10px;
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
