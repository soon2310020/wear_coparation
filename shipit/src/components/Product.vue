<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-md-5 col-sm-5 col-xs-12">
          <v-carousel>
            <v-carousel-item
              v-for="(image, index) in itemDetail.base64File"
              :key="index"
              :src="'data:' + image.contentType + ';base64,' + image.content"
            >
            </v-carousel-item>
          </v-carousel>
        </div>
        <div class="col-md-7 col-sm-7 col-xs-12">
          <div class="pl-6">
            <p class="display-1 mb-0">{{ itemDetail.title }}</p>
            <v-card-actions class="pa-0">
              <p class="headline font-weight-light pt-3">
                {{
                  (
                    itemDetail.price -
                    itemDetail.price * (itemDetail.discount / 100)
                  )
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND"
                }}
                <del style="" class="subtitle-1 font-weight-thin">{{
                  itemDetail.price
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ".") + " VND"
                }}</del>
              </p>
              <v-spacer></v-spacer>
            </v-card-actions>
            <p class="subtitle-1 font-weight-thin"></p>
            <p class="title">Kích cỡ</p>
            <v-radio-group v-model="itemSize" row>
              <v-radio
                v-for="(size, index) in itemDetail.size.trim().split(' ')"
                :key="index"
                :label="size"
                :value="size"
              ></v-radio>
            </v-radio-group>
            <p class="title">Số lượng</p>

            <v-text-field
              outlined
              style="width: 100px"
              v-model="quality"
              dense
            ></v-text-field>
            <v-btn
              class="primary white--text"
              outlined
              tile
              dense
              @click="addToCard(itemDetail)"
              ><v-icon>mdi-cart</v-icon>Thêm vào giỏ</v-btn
            >
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-sm-12 col-xs-12 col-md-12">
          <v-tabs>
            <v-tab>Mô tả</v-tab>
            <v-tab-item>
              <p
                class="pt-10 subtitle-1 font-weight-thin"
                v-html="itemDetail.content"
              ></p>
            </v-tab-item>
          </v-tabs>
          <v-card-text class="pa-0 pt-4" tile outlined> </v-card-text>
        </div>
      </div>
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
    idProDuct: null,
    quality: 1,
    item: 5,
    itemSize: "",
    itemDetail: {
      id: 0,
      title: "",
      price: 0,
      content: "",
      discount: 0,
      size: "",
      categoryId: null,
    },
    cardIndex : 0
  }),
  created() {
    this.idProDuct = this.$router.currentRoute.params.id;
    this.getDetail();
  },
  methods: {
    getDetail() {
      axios
        .get(this.baseURL + `/product/product?id=${this.idProDuct}`)
        .then((response) => {
          this.itemDetail = response.data;
          //  console.log(this.items)
        });
    },
    addToCard(itemToAdd) {
      debugger
      if(this.itemSize=='')
      return
      let object = { arr: [] };
      if (sessionStorage.getItem("card")) {
        object = JSON.parse(sessionStorage.getItem("card"));
        // Add the item or increase qty
        let itemInCart = object.arr.filter(
          (item) => item.id === itemToAdd.id && item.size === this.itemSize
        );
        let isItemInCart = itemInCart.length > 0;

        if (isItemInCart === false) {
          object.arr.push({
            id: itemToAdd.id,
            size: this.itemSize,
            quality: parseInt(this.quality),
          });
        } else {
          itemInCart[0].quality += parseInt(this.quality);
        }

        // itemToAdd.qty = 1;
        console.log(object.arr);
        sessionStorage.setItem("card", JSON.stringify(object));
      } else {
        object.arr.push({
          id: itemToAdd.id,
          size: this.itemSize,
          quality: parseInt(this.quality),
        });
        sessionStorage.setItem("card", JSON.stringify(object));
      }
      this.getCardIndex()
    },
    getCardIndex() {
      let index = 0;
     let object = JSON.parse(sessionStorage.getItem("card"));
      object.arr.forEach(element => {
        index +=  element.quality
      });
       this.$store.dispatch('setIndexCard', index);
      // let object = { arr: [] };
      // object = JSON.parse(sessionStorage.getItem("card"));
      // let index = 0;
      // object.arr.forEach(obj =>{
      //   index += parseInt(obj.quality)
      // })
      // this.cardIndex=index;
    },
  },
};
</script>
<style scoped>
.v-application .accent {
  background-color: #c7c7c7 !important;
  border-color: #eff3f2 !important;
}
</style>
