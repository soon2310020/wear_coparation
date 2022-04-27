<template>
  <div>
    <v-container>
      <p class="display-3 font-weight-light text-center pa-4">
        Giỏ hàng của bạn
      </p>
      <v-row>
        <v-col :cols="12" md="9" sm="12">
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-center">Tên sản phẩm</th>
                  <th class="text-center">Giá</th>
                  <th class="text-center">Số lượng</th>
                  <th class="text-center">Tổng tiền</th>
                  <th class="text-center"></th>
                </tr>
              </thead>

              <tbody v-if="products !== null">
                <tr :key="index" v-for="(pro, index) in products">
                  <td>
                    <v-list-item key="1">
                      <v-list-item-avatar>
                        <v-img
                          :src="
                            'data:' +
                            pro.file.contentType +
                            ';base64,' +
                            pro.file.content
                          "
                        ></v-img>
                      </v-list-item-avatar>

                      <v-list-item-content>
                        <v-list-item-title>{{ pro.name }}</v-list-item-title>
                        <v-list-item-subtitle>{{
                          pro.size
                        }}</v-list-item-subtitle>
                      </v-list-item-content>
                    </v-list-item>
                  </td>
                  <td>{{ pro.price.toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND" }}</td>
                  <td>
                    <v-text-field
                      class="pt-10"
                      label="Outlined"
                      style="width: 80px"
                      single-line
                      outlined
                      :min =1
                      v-model="pro.quantity"
                      type="number"
                    ></v-text-field>
                  </td>
                  <td>{{ pro.totalPrice.toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND" }}</td>
                  <td><a @click="productRemove(index)">X</a></td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </v-col>
        <v-col :cols="12" md="3" sm="12" style="background-color: lightgray">
          <p class="headline">Chi tiết đơn đặt hàng</p>
          <p class="overline">Đơn hàng trên 1.000.000 VND sẽ được freeShip</p>
          <v-simple-table>
            <template v-slot:default>
              <tbody>
                <tr>
                  <td>Tổng tiền</td>
                  <td class="text-right" style="width: 50px">{{total !=null ? total.toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND":0}}</td>
                </tr>
                <tr>
                  <td>Phí ship</td>
                  <td class="text-right" style="width: 50px">{{feeShip != null ? feeShip.toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND":0}}</td>
                </tr>
                <tr>
                  <td>Số tiền thanh toán</td>
                  <td class="text-right" style="width: 50px"><b>{{totalPay !=null ? totalPay.toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND":0}}</b></td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
          <div class="text-center">
            <v-btn
              class="secondary white--text mt-5"
              outlined
              @click="updateStore()"
              >Cập nhật</v-btn
            >
            <v-btn class="primary white--text mt-5" outlined>Đặt hàng</v-btn>
          </div>
        </v-col>
      </v-row>
    </v-container>
    <v-card class="accent">
      <v-container>
        <v-row no-gutters>
          <v-col class="col-12 col-md-4 col-sm-12">
            <v-row>
              <v-col class="col-12 col-sm-3 pr-4" align="right">
                <v-icon class="display-2">mdi-truck</v-icon>
              </v-col>
              <v-col class="col-12 col-sm-9 pr-4">
                <h3 class="font-weight-light">Miễn phí ship</h3>
                <p class="font-weight-thin">Cho đơn hàng trên 1.000.000 VND</p>
              </v-col>
            </v-row>
          </v-col>
          <v-col class="col-12 col-md-4 col-sm-12">
            <v-row>
              <v-col class="col-12 col-sm-3 pr-4" align="right">
                <v-icon class="display-2">mdi-cash-usd</v-icon>
              </v-col>
              <v-col class="col-12 col-sm-9 pr-4">
                <h3 class="font-weight-light">Bảo hành 1 tháng</h3>
                <p class="font-weight-thin">
                  Trả hàng miễn phí nếu sản phẩm lỗi
                </p>
              </v-col>
            </v-row>
          </v-col>
          <v-col class="col-12 col-md-4 col-sm-12">
            <v-row>
              <v-col class="col-12 col-sm-3 pr-4" align="right">
                <v-icon class="display-2">mdi-headset</v-icon>
              </v-col>
              <v-col class="col-12 col-sm-9 pr-4">
                <h3 class="font-weight-light">0865279193</h3>
                <p class="font-weight-thin">Gọi hotline để được hỗ trợ</p>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-container>
    </v-card>
  </div>
</template>
<script>
import axios from "axios";
export default {
  data: () => ({
    baseURL: "http://localhost:9999/wear_shop/api/front-end",
    products: [
      // {
      //   id: null,
      //   size: "",
      //   price: 0,
      //   quantity: 0,
      //   totalPrice: 0,
      //   file: {
      //     id: 0,
      //     name: "",
      //     extendsion: "",
      //     url: "",
      //     content: "",
      //     contentType: "",
      //     filePath: "",
      //   },
      //   name: "",
      // },
    ],
    total :0,
    feeShip:0,
    totalPay:0
  }),
  created() {
    this.getCardDetail();
  },
  methods: {
    getCardDetail() {
      let object = { arr: [] };
      object = JSON.parse(sessionStorage.getItem("card"));
      if (object !== null) {
        axios
          .post(this.baseURL + `/product/order`, object.arr)
          .then((response) => {
            this.products = response.data.product;
            this.total=response.data.total
            this.feeShip=response.data.shipFee
            this.totalPay = this.total+this.feeShip
            console.log(this.products);
          });
      }
    },
    productRemove(index) {
      this.products.splice(index, 1);
    },
    updateStore() {
      let object = { arr: [] };
      if (this.products.length > 0) {
        this.products.forEach((p) => {
          object.arr.push({
            id: p.id,
            size: p.size,
            quality: parseInt(p.quantity),
          });
        });
        sessionStorage.setItem("card", JSON.stringify(object));
      } else {
        sessionStorage.clear();
      }
      this.getCardDetail();
      this.getCardIndex();
    },
    getCardIndex() {
      let index = 0;
      let object = JSON.parse(sessionStorage.getItem("card"));
      if(object !==null)
      {
      object.arr.forEach((element) => {
        index += element.quality;
      });
       this.$store.dispatch("setIndexCard", index);
       return
      }
       window.history.go()


      // let object = { arr: [] };
      // object = JSON.parse(sessionStorage.getItem("card"));
      // let index = 0;
      // object.arr.forEach(obj =>{
      //   index += parseInt(obj.quality)
      // })
      // this.cardIndex=index;
    },
    changeValue(a)
    {
console.log(a)
    }
  },
};
</script>
<style scoped>
.v-application .accent {
  background-color: #c7c7c7 !important;
  border-color: #eff3f2 !important;
}
</style>

