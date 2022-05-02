<template>
  <div id="page-wrapper">
    <div class="row">
      <div class="col-lg-12">
        <h1 class="page-header">Thống kê</h1>
      </div>
      <!-- /.col-lg-12 -->
    </div>
    <div class="row">
      <div class="col-md-3">
        <el-button type="danger" plain><p class="fa fa-bookmark"> Sản phẩm : </p>{{this.product}}</el-button>
      </div>
      <div class="col-md-3">
        <el-button type="primary" plain>
          <p class="fa fa-users"> Người dùng :</p>
          {{ this.user }}</el-button
        >
      </div>
      <div class="col-md-3">
        <el-button type="success" plain
          ><p class="fa fa-inbox"> Đơn hàng :</p>{{this.order}}</el-button
        >
      </div>
      <div class="col-md-3">
        <el-button type="warning" plain>  <p class="fa fa-file-o"> Danh mục : </p>{{this.category}}</el-button>
      </div>
    </div>
    <div class="row">
      <div class="col-md-12 col-12">
        <apexcharts
          width="600"
          type="bar"
          :options="chartOptions"
          :series="series"
        ></apexcharts>
        <div></div>
      </div>
    </div>
  </div>
  <!-- /#page-wrapper -->
</template>

<script>
import VueApexCharts from 'vue-apexcharts'
import axios from 'axios'
export default {
  name: 'DashBoard',
  created () {
    this.getOrderChart()
    this.countOrder()
    this.countProduct()
    this.countCategory()
    this.countUser()
  },
  components: {
    apexcharts: VueApexCharts
  },
  data () {
    return {
      chartOptions: {
        title: {
          text: 'Thống kê đơn hàng trong năm ' + new Date().getFullYear(),
          floating: true,
          offsetY: 335,
          align: 'center',
          style: {
            color: '#444'
          }
        },
        chart: {
          id: 'vuechart-example'
        },
        xaxis: {
          categories: [
            'Tháng 1',
            'Tháng 2',
            'Tháng 3',
            'Tháng 4',
            'Tháng 5',
            'Tháng 6',
            'Tháng 7',
            'Tháng 8',
            'Tháng 9',
            'Tháng 10',
            'Tháng 11',
            'Tháng 12'
          ]
        }
      },
      series: [
        {
          name: 'Đơn hàng',
          data: [30, 40, 45, 50, 49, 60, 70, 81]
        }
      ],
      user: 0,
      order: 0,
      product: 0,
      category: 0,

      //   options: {
      //     chart: {
      //       id: 'vuechart-example'
      //     },
      //     xaxis: {
      //       categories: [
      //         'Tháng 1',
      //         'Tháng 2',
      //         'Tháng 3',
      //         'Tháng 4',
      //         'Tháng 5',
      //         'Tháng 6',
      //         'Tháng 7',
      //         'Tháng 8',
      //         'Tháng 9',
      //         'Tháng 10',
      //         'Tháng 11',
      //         'Tháng 12'
      //       ]
      //     }
      //   },
      //   series: [
      //     {
      //       name: 'Đơn hàng',
      //       data: []
      //     }
      //   ],
      baseURL: 'http://localhost:9999/wear_shop/api'
    }
  },
  methods: {
    countOrder () {
      axios.get(this.baseURL + '/order/chart_all').then((Response) => {
        this.order = Response.data
      })
    },
    countProduct () {
      axios.get(this.baseURL + '/product/chart_all').then((Response) => {
        this.product = Response.data
      })
    },
    countCategory () {
      axios.get(this.baseURL + '/categories/chart_all').then((Response) => {
        this.category = Response.data
      })
    },
    countUser () {
      axios.get(this.baseURL + '/user/chart_all').then((Response) => {
        this.user = Response.data
      })
    },
    updateChart () {
      const max = 90
      const min = 20
      const newData = this.series[0].data.map(() => {
        return Math.floor(Math.random() * (max - min + 1)) + min
      })
      // In the same way, update the series option
      this.series = [
        {
          data: newData
        }
      ]
    },
    getOrderChart () {
      axios.get(this.baseURL + '/order/chart').then((Response) => {
        this.series = [
          {
            data: Response.data
          }
        ]
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.col-md-3 {
  width: 25%;
  height: 100px;
}
.el-button--primary.is-plain {
  width: 100%;
  height: 100%;
  font-size: 20px;
}
.el-button--success.is-plain {
  width: 100%;
  height: 100%;
    font-size: 20px;
}
.el-button--warning.is-plain {
  width: 100%;
  height: 100%;
    font-size: 20px;
}
.el-button--danger.is-plain {
  width: 100%;
  height: 100%;
    font-size: 20px;
}
</style>
