<template>
  <div>
    <v-carousel hide-delimiters>
      <v-carousel-item :src="require('../assets/img/home/slider4.jpg')">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Miễn phí ship cho đơn hàng trên 1.000.000 VND</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider2.jpg')">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Miễn phí ship cho đơn hàng trên 1.000.000 VND</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider3.jpg')">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Miễn phí ship cho đơn hàng trên 1.000.000 VND</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
      <v-carousel-item :src="require('../assets/img/home/slider1.jpg')">
        <v-row class="fill-height" align="center" justify="center">
          <div class="display-2 white--text pl-5 pr-5 hidden-sm-only">
            <strong>Miễn phí ship cho đơn hàng trên 1.000.000 VND</strong>
          </div>
          <br />
        </v-row>
      </v-carousel-item>
    </v-carousel>
    <div class="pl-4 pr-4 row">
      <div class="col-md-6 col-sm-6 col-xs-12">
        <v-card>
          <v-img
            :src="require('../assets/img/home/slider2.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="400px"
          >
            <h1 class="text-center font-size">Top Picks</h1>
            <div class="text-center">
              <v-btn href="/shop" class="white--text" outlined>Mua ngay</v-btn>
            </div>

            <!--            <v-expand-transition>-->
            <!--              <div-->
            <!--                v-if="hover"-->
            <!--                class="d-flex transition-fast-in-fast-out orange darken-2 v-card&#45;&#45;reveal display-3 white&#45;&#45;text"-->
            <!--                style="height: 100%;"-->
            <!--              >-->

            <!--                <h3>Top Picks</h3><br/>-->
            <!--                <h3>sdfs</h3>-->
            <!--              </div>-->
            <!--            </v-expand-transition>-->
          </v-img>
        </v-card>
        <!--        </v-hover>-->
      </div>
      <div class="col-md-6 col-sm-6 col-xs-12">
        <v-card>
          <v-img
            :src="require('../assets/img/home/slider3.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="400px"
          >
            <h1 class="text-center font-size">New Arrivals</h1>
            <div class="text-center">
              <v-btn href="/shop" class="white--text" outlined>Mua ngay</v-btn>
            </div>
          </v-img>
        </v-card>
      </div>
    </div>
    <div class="pl-4 pr-4 row">
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal2.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size"></h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text
                >Mua ngay
                <v-icon class="white--text caption"
                  >mdi-arrow-right</v-icon
                ></v-btn
              >
            </div>
          </v-img>
        </v-card>
      </div>
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal3.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size"></h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text
                >Mua ngay
                <v-icon class="white--text caption"
                  >mdi-arrow-right</v-icon
                ></v-btn
              >
            </div>
          </v-img>
        </v-card>
      </div>
      <div class="col-md-4 col-sm-4 col-xs-12">
        <v-card outlined>
          <v-img
            :src="require('../assets/img/home/deal4.jpg')"
            class="white--text align-center"
            gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
            height="300px"
          >
            <h1 class="text-center font-size"></h1>
            <div class="text-center mt-2">
              <v-btn class="white--text caption" href="/shop" text
                >Mua ngay
                <v-icon class="white--text caption"
                  >mdi-arrow-right</v-icon
                ></v-btn
              >
            </div>
          </v-img>
        </v-card>
      </div>
    </div>
    <v-container>
      <v-row no-gutters>
        <v-col :cols="12">
          <v-card-text class="" tile outlined>
            <v-card-title class="subheading">Danh mục sản phẩm</v-card-title>
            <v-divider></v-divider>
            <div class="row">
              <div
                v-for="item in items"
                :key="item.id"
                class="col-12 col-md-3 col-sm-6 col-xs-6 text-center"
              >
                <v-hover v-slot:default="{ hover }" open-delay="200">
                  <v-card :elevation="hover ? 16 : 2">
                    <v-img
                      class="white--text align-end"
                      height="200px"
                      :src="
                        'data:' +item.base64File[0].contentType + ';base64,' +item.base64File[0].content
                      "
                    >
                      <v-card-title>{{ item.name }} </v-card-title>
                    </v-img>
                    <v-card-text class="text--primary text-center">
                      <div v-html="item.description"></div>
                    </v-card-text>

                    <div class="text-center">
                      <v-btn  @click="getProduct(item.id)"  class="ma-2" outlined color="info">
                        Khám phá ngay
                      </v-btn>
                    </div>
                  </v-card>
                </v-hover>
              </div>
              <!-- <div class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                <v-hover v-slot:default="{ hover }" open-delay="200">
                  <v-card :elevation="hover ? 16 : 2">
                    <v-img
                      class="white--text align-end"
                      height="200px"
                      :src="require('../assets/img/home/deal2.jpg')"
                    >
                      <v-card-title>T-Shirt </v-card-title>
                    </v-img>

                    <v-card-text class="text--primary text-center">
                      <div>Upto 50%</div>
                      <div>Zara, Selected, Celio</div>
                    </v-card-text>

                    <div class="text-center">
                      <v-btn href="/shop" class="ma-2" outlined color="info">
                        Explore
                      </v-btn>
                    </div>
                  </v-card>
                </v-hover>
              </div>
              <div class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                <v-hover v-slot:default="{ hover }" open-delay="200">
                  <v-card :elevation="hover ? 16 : 2">
                    <v-img
                      class="white--text align-end"
                      height="200px"
                      :src="require('../assets/img/home/deal3.jpg')"
                    >
                      <v-card-title>Jeans </v-card-title>
                    </v-img>

                    <v-card-text class="text--primary text-center">
                      <div>Upto 60% + Extra 10%</div>
                      <div>Jack & Jones, Levis</div>
                    </v-card-text>

                    <div class="text-center">
                      <v-btn href="/shop" class="ma-2" outlined color="info">
                        Explore
                      </v-btn>
                    </div>
                  </v-card>
                </v-hover>
              </div>
              <div class="col-12 col-md-3 col-sm-6 col-xs-6 text-center">
                <v-hover v-slot:default="{ hover }" open-delay="200">
                  <v-card :elevation="hover ? 16 : 2">
                    <v-img
                      class="white--text align-end"
                      height="200px"
                      :src="require('../assets/img/home/deal4.jpg')"
                    >
                      <v-card-title>Shoes </v-card-title>
                    </v-img>

                    <v-card-text class="text--primary text-center">
                      <div>Upto 60% + Extra 10%</div>
                      <div>Nike, Adidas, Puma</div>
                    </v-card-text>

                    <div class="text-center">
                      <v-btn href="/shop" class="ma-2" outlined color="info">
                        Explore
                      </v-btn>
                    </div>
                  </v-card>
                </v-hover>
              </div> -->
            </div>
          </v-card-text>
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
                <p class="font-weight-thin">Trả hàng miễn phí nếu sản phẩm lỗi</p>
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
import {mapActions} from 'vuex'
export default {
  data() {
    return {
      baseURL: "http://localhost:9999/wear_shop/api",
      page: 1,
      size: 4,

      items: [
        // { title: "Click Me" },
        // { title: "Click Me" },
        // { title: "Click Me" },
        // { title: "Click Me 2" },
      ],
      activeBtn: 1,
      colors: [
        "indigo",
        "warning",
        "pink darken-2",
        // "red lighten-1",
        "deep-purple accent-4",
      ],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"],
    };
  },
  computed:{
    ...mapActions(['setCategory'])
  },

  created() {
    this.getInitCategory();
  },
  methods: {
    getProduct(id){
      this.$store.dispatch('setCategory', id);
      this.$router.push("/shop");
    },
    // async callApiFile(id) {
    //   return new Promise((resolve, reject) => {
    //     axios.get(this.baseURL + `/file/${id}`).then(
    //       (res) => {
    //         resolve(res.data);
    //       },
    //       (error) => {}
    //     );
    //     // setTimeout(()=>{
    //     // resolve("done")
    //     // },100);
    //   });
    //   return Promise.all([await axios.get(this.baseURL + `/file/${id}`)]);
    //   console.log(file);
    //   return file.data;
    // },
    // async awaitFun(content) {
    //   const promises = content.map(async (e, index) => {
    //     if (e.file != null) {
    //       let array = e.file.trim().split(" ");
    //       debugger;
    //       await axios.get(this.baseURL + `/${array[0]}`).then(
    //         (res) => {
    //           e.fileBase64 = res.data.content;
    //           e.contentType = res.data.contentType;
    //         },
    //         (error) => {
    //           console.log(error);
    //         }
    //       );
    //     }
    //   });
    //   return Promise.all(promises);
    // },
    getInitCategory() {
      axios
        .get(
          this.baseURL +
            `/front-end/categories/category?name=` +
            `&page=${this.page - 1}&size=${this.size}`
        )
         .then((response) => {
           this.items = response.data.content;
          // const awaitFun = (content) => {
          //   const promises = content.map(async (e, index) => {
          //     if (e.file != null) {
          //       let array = e.file.trim().split(' ')
          //       console.log(array)
          //       await axios.get(this.baseURL + `/${array[0]}`).then(
          //         (res) => {
          //           e.fileBase64 = res.data.content
          //           e.contentType = res.data.contentType
          //         },
          //         (error) => {
          //           console.log(error)
          //         }
          //       )
          //     }
          //   })
          //   return Promise.all(promises)
          // }
          // // this.items = ;
          // let newObj = _.cloneDeep(response.data.content)
          // awaitFun(newObj).then(() => {
          //   this.items = newObj
          // })

          this.totalElements = response.data.totalElements;
        })
        .catch((e) => {
          // this.errors.push(e);
        });
    },
  },
};
</script>
<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: 0.5;
  position: absolute;
  width: 100%;
}
.v-application .accent {
    background-color: #c7c7c7 !important;
    border-color: #eff3f2 !important;
}
</style>

