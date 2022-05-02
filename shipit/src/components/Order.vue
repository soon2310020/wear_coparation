<template>
  <div>
    <v-container>
      <p class="display-3 font-weight-light text-center pa-4">
        Nhập địa chỉ của bạn
      </p>
      <v-row>
        <v-col :cols="5" md="5" sm="12">
          <el-select
            style="width: 100%"
            v-model="province"
            placeholder="Tỉnh/TP"
            value-key="provinceId"
            @change="getDistrict()"
          >
            <el-option
              style="width: 100%"
              v-for="pro in provinces"
              :key="pro.provinceId"
              :label="pro.provinceName"
              :value="pro"
            >
            </el-option>
          </el-select>
        </v-col>
        <v-col :cols="4" md="4" sm="12">
          <el-select
            style="width: 100%"
            v-model="district"
            placeholder="Quận/Huyện"
          >
            <el-option
              style="width: 100%"
              v-for="dis in districts"
              :key="dis.districtId"
              :label="dis.districtName"
              :value="dis.districtName"
            >
            </el-option>
          </el-select>
        </v-col>
        <v-col md="3" sm="12">
          <el-input
            placeholder="Người nhận..."
            v-model="recivedName"
          ></el-input>
        </v-col>
      </v-row>
      <v-row>
        <v-col md="5" sm="12">
          <el-input
            placeholder="Địa chỉ cụ thể..."
            v-model="addressDetail"
          ></el-input>
        </v-col>
        <v-col md="4" sm="12">
          <el-input
            placeholder="Số điện thoại đặt hàng..."
            v-model="phoneNumber"
          ></el-input>
        </v-col>
        <v-col md="3" sm="12">
          <el-button @click="cancel()">Hủy</el-button>
          <el-button type="info" @click="addOrder()">Đặt hàng</el-button>
        </v-col>
      </v-row>
      <v-row>
        <v-col> </v-col>
      </v-row>
      <v-row>
        <v-col> </v-col>
      </v-row>
      <v-row>
        <v-col> </v-col>
      </v-row>
      <v-row>
        <v-col> </v-col>
      </v-row>
    </v-container>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data: () => ({
    baseURL: "http://localhost:9999/wear_shop/api",
    districts: [],
    provinces: [],
    comunues: [],
    province: null,
    district: "",
    comunue: "",
    addressDetail: "",
    phoneNumber: "",
    recivedName: "",
    userId: 7,
    order: {},
  }),
  created() {
    this.getProvince();
    this.checkLogined();
  },
  methods: {
    getProvince() {
      axios.get(this.baseURL + "/address-list").then((response) => {
        this.provinces = response.data;
      });
    },
    getDistrict() {
      this.district = "";
      if (this.province == "") return;
      axios
        .get(this.baseURL + "/address-district" + `?proIds=${this.province.provinceId}`)
        .then((response) => {
          this.districts = response.data;
        });
    },
    cancel() {
      let loc = window.location;
      const port = loc.port ? ":" + loc.port : "";
      loc.href = `//${loc.hostname}${port}/cart`;
    },
    addOrder() {
      const numberRegex = new RegExp("^0[0-9]{9,}$");
      if (this.province == null) {
        this.$toastr.e("Vui lòng chọn Tỉnh/TP");
        return;
      }
      if (this.district == "") {
        this.$toastr.e("Vui lòng chọn Quận/Huyện");
        return;
      }
      if (this.addressDetail == "" || this.addressDetail == null) {
        this.$toastr.e("Vui lòng nhập địa chỉ chi tiết");
        return;
      }
      if (this.recivedName == "") {
        this.$toastr.e("Vui lòng nhập tên người nhận");
        return;
      }
      if (this.phoneNumber == "" || this.phoneNumber == null) {
        this.$toastr.e("Vui lòng nhập số điện thoại");
        return;
      }
      if (!numberRegex.test(this.phoneNumber)) {
        this.$toastr.e("Vui lòng nhập số điện thoại đúng định dạng");
        return;
      }
      let products = { arr: [] };
      products = JSON.parse(sessionStorage.getItem("card"));

      this.order.userId = parseInt(localStorage.getItem("user_id"));
      this.order.ReceivedName = this.recivedName;
      this.order.phoneNumber = this.phoneNumber;
      this.order.address = `${this.addressDetail} ${this.district} - ${this.province.provinceName} `;
      this.order.products = products.arr;
      let loc = window.location;
      let port = "http://localhost:" + loc.port + "/order_detail/"
      this.order.url = port
      axios
        .post(this.baseURL + "/order/add_order", this.order)
        .then((response) => {
          sessionStorage.clear();
          this.$toastr.i(
            "Đặt hàng thành công check email để xem thông tin đơn hàng"
          );
          let loc = window.location;
          const port = loc.port ? ":" + loc.port : "";
          loc.href = `//${loc.hostname}${port}`;
        });
    },
    checkLogined() {
      if (localStorage.getItem("user")) {
        return;
      }
      let loc = window.location;
      const port = loc.port ? ":" + loc.port : "";
      loc.href = `//${loc.hostname}${port}`;
    },
  },
};
</script>
<style scoped>
</style>
