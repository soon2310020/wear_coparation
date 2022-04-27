<template>
  <div>
    <v-container>
      <div class="row">
        <div class="col-md-3 col-sm-3 col-xs-12">
          <v-card outlined>
            <v-card-title>Lọc sản phẩm</v-card-title>
            <v-divider></v-divider>
            <template>
              <v-card-title>Danh mục sản phẩm</v-card-title>
              <v-row>
                <v-col cols="12" sm="12">
                <el-select
                  v-model="fromFilter.categoryIds"
                  multiple
                  filterable
                  remote
                  reserve-keyword
                  placeholder="Nhập vào tên danh mục..."
                >
                  <el-option
                    v-for="item in listCategory"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
                </v-col>
              </v-row>
            </template>
            <v-divider></v-divider>
            <v-card-title>Giá</v-card-title>
            <v-range-slider
              v-model="range"
              :max="max"
              :min="min"
              :height="10"
              class="align-center"
              dense
            ></v-range-slider>
            <v-row class="pa-2" dense>
              <v-col cols="12" sm="5">
                <v-text-field
                  :value="range[0]"
                  label="Min"
                  outlined
                  dense
                  @change="$set(range, 0, $event)"
                ></v-text-field>
              </v-col>
              <v-col cols="12" sm="2">
                <p class="pt-2 text-center">TO</p>
              </v-col>
              <v-col cols="12" sm="5">
                <v-text-field
                  :value="range[1]"
                  label="Max"
                  outlined
                  dense
                  @change="$set(range, 1, $event)"
                ></v-text-field>
              </v-col>
            </v-row>

            <v-divider></v-divider>
            <v-row class="pa-2" dense>
              <v-col cols="6" sm="5">
                <el-button icon="el-icon-search" circle @click="onClickFilter()"></el-button>
              </v-col>
            </v-row>
          </v-card>
        </div>
        <div class="col-md-9 col-sm-9 col-xs-12">
          <!-- <v-breadcrumbs class="pb-0" :items="breadcrums"></v-breadcrumbs>

          <v-row dense>
            <v-col cols="12" sm="8" class="pl-6 pt-6">
              <small>Showing 1-12 of 200 products</small>
            </v-col>
            <v-col cols="12" sm="4">
              <v-select class="pa-0" v-model="select" :items="options" style="margin-bottom: -20px;" outlined dense></v-select>
            </v-col>
          </v-row> -->

          <v-divider></v-divider>

          <div class="row text-center">
            <div
              class="col-md-3 col-sm-6 col-xs-12"
              :key="pro.id"
              v-for="pro in products"
            >
              <v-hover v-slot:default="{ hover }">
                <v-card class="mx-auto" color="grey lighten-4" max-width="600">
                  <v-img
                    class="white--text align-end"
                    :aspect-ratio="16 / 9"
                    height="200px"
                    :src="
                      'data:' +
                      pro.base64File[0].contentType +
                      ';base64,' +
                      pro.base64File[0].content
                    "
                  >
                    <v-card-title>{{ pro.title }} </v-card-title>
                    <v-expand-transition>
                      <div
                        v-if="hover"
                        class="
                          d-flex
                          transition-fast-in-fast-out
                          white
                          darken-2
                          v-card--reveal
                          display-3
                          white--text
                        "
                        style="height: 100%"
                      >
                        <v-btn
                          v-if="hover"
                          @click="getProductDetail(pro.id)"
                          class=""
                          outlined
                          >Xem chi tiết</v-btn
                        >
                      </div>
                    </v-expand-transition>
                  </v-img>
                  <v-card-text class="text--primary">
                    <div>
                      <a href="/product" style="text-decoration: none">{{
                        pro.name
                      }}</a>
                    </div>
                    <div>{{ NumberToString1(pro.price) }}</div>
                  </v-card-text>
                </v-card>
              </v-hover>
            </div>
          </div>
          <div class="text-center mt-12">
            <el-pagination layout="prev, pager, next" :total="totalElement">
            </el-pagination>
          </div>
        </div>
      </div>
    </v-container>
  </div>
</template>
<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.8;
  position: absolute;
  width: 100%;
}
</style>
<script>
import axios from "axios";
export default {
  created() {
    console.log(this.$store.state.category);
    if (this.$store.state.category != null) {
      this.categoryIds = this.$store.state.category;
    }
    this.getListCategory();
    this.getAllProduct();
  },
  methods: {
    onClickFilter(){
      let query = {
        categoryIds:this.fromFilter.categoryIds,
        fromPrice:this.range[0],
        toPrice:this.range[1],
        name:null
      }
      console.log(query);
      this.getProduct(query)
    },
    getListCategory(){
       axios
        .get(
          this.baseURL +
            `/categories/category?name=` +
            `&page=${this.page - 1}&size=${this.size}`
        )
         .then((response) => {
           this.listCategory = response.data.content;

        })
        .catch((e) => {
          // this.errors.push(e);
        });
    },
    getProductDetail(id) {
      this.$router.push(`/product/${id}`);
    },
    getAllProduct(){
      let query = {
        categoryIds: this.categoryIds !=null ? this.categoryIds : null
      };
      this.getProduct(query)
    },
    getProduct(query) {
      axios
        .post(
          this.baseURL +
            `/product/search-product?page=0&size=999`,
          query
        )
        .then(
          (response) => {
            this.products = response.data.content;
            this.totalElement = response.data.totalElements;
          },
          (error) => {}
        );
    },

    NumberToString1(value) {
      if (value == null || value === "") {
      } else {
        let val = (value / 1).toFixed().replace(".", ",");
        let string = val.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
        return string;
      }
    },
  },
  craete() {
    // axios.get('')
  },
  data: () => ({
    baseURL: "http://localhost:9999/wear_shop/api/front-end",
    page: 1,
    size: 8,
    categoryIds:null,
    range: [0, 5000000],
    totalElement: 0,
    select: "Popularity",
    listCategory:[],
    fromFilter:{
      categoryIds:null
    },
    min: 0,
    max: 5000000,
    items: [
      {
        id: 2,
        name: "Shoes",
        children: [
          { id: 2, name: "Casuals" },
          { id: 3, name: "Formals" },
          { id: 4, name: "Sneakers" },
        ],
      },
      {
        id: 1,
        name: "Clothing",
        children: [
          { id: 5, name: "Shirts" },
          { id: 6, name: "Tops" },
          { id: 7, name: "Tunics" },
          { id: 8, name: "Bodysuit" },
        ],
      },
    ],
    products: [],
  }),
};
</script>
<style scoped>
.el-pager {
  padding: 0 !important;
}
.el-select {
    width: 100% !important;
    padding: 10px !important;
}
.v-application ol, .v-application ul {
        padding-left: 0px !important;
}
</style>
