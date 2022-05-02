<template>
  <v-app id="inspire">
    <v-app-bar
      :clipped-left="$vuetify.breakpoint.lgAndUp"
      app
      color="primary"
      dark
    >
      <!--      <v-app-bar-nav-icon @click.stop="drawer = !drawer" />-->
      <v-toolbar-title style="width: 350px">
        <a href="/" class="white--text" style="text-decoration: none"
          ><v-icon>mdi-truck</v-icon>&nbsp;Wear Shop</a
        >
      </v-toolbar-title>
      <v-text-field
        flat
        solo-inverted
        hide-details
        prepend-inner-icon="mdi-magnify"
        label="Nhập tên sản phẩm..."
        class="hidden-sm-and-down pl-10 ml-4"
      />
      <v-spacer />

      <v-btn v-on="on" href="/cart" icon>
        <v-badge
          :content="getCountCard()"
          :value="getCountCard()"
          color="green"
          overlap
        >
          <v-icon>mdi-cart</v-icon>
        </v-badge>
      </v-btn>
        <v-btn icon>
        <v-icon>mdi-account-circle</v-icon>
      </v-btn>

       <a v-if="this.username != ''" @click="handleLogout()" class="white--text" style="text-decoration: none"
          >&nbsp; {{this.username}} | Đăng xuất</a
        >
        <a v-if="this.username == ''" href="/login" class="white--text" style="text-decoration: none"
          >&nbsp;Đăng nhập</a
        >

    </v-app-bar>
    <v-content>
      <v-bottom-navigation :value="activeBtn" color="primary" horizontal>
        <a href="/" class="v-btn">
          <span>Trang chủ</span>
        </a>
        <!-- <v-menu open-on-hover offset-y>
          <template v-slot:activator="{ on }">
            <v-btn v-on="on">
              <span>Sản phẩm</span>
            </v-btn>
          </template>
          <v-card class="mx-auto" max-width="344" outlined>
            <v-list-item
              v-for="(item, index) in items"
              :key="index"
              @click=""
              href="/shop"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-card>
        </v-menu> -->
        <a href="/shop" class="v-btn">
          <span>Sản phẩm</span>
        </a>
        <v-btn href="/blog">
          <span>Blog</span>
        </v-btn>
      </v-bottom-navigation>
    </v-content>
    <router-view />
    <v-footer :padless="true">
      <v-card flat tile width="100%" class="secondary white--text text-center">
        <v-card-text>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-home</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-email</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-calendar</v-icon>
          </v-btn>
          <v-btn class="mx-4 white--text" icon>
            <v-icon size="24px">mdi-delete</v-icon>
          </v-btn>
        </v-card-text>

        <v-card-text class="white--text pt-0">
          Thành lập từ năm 2013, đến nay hệ thống cửa hàng của Wear shop đang là
          địa chỉ “One stop fashion shop” cung ứng cho nam giới mọi nhu cầu về
          thời trang với tất cả các loại trang phục, phụ kiện. Phong cách tối
          giản đặc trưng của Wear shop mang đến sự gần gũi, đa dụng và đủ sức
          tạo nên dấu ấn riêng cho người mặc
        </v-card-text>

        <v-divider></v-divider>

        <v-card-text class="white--text">
          {{ new Date().getFullYear() }} — <strong>Wear Shop</strong>
        </v-card-text>
      </v-card>
    </v-footer>
  </v-app>
</template>
<script>
import jwt_decode from "jwt-decode";
import axios from "axios";
export default {
  created() {
    this.getUserDetail()
  },
  methods: {
    getCountCard() {
      let card = this.$store.state.card;
      let object = JSON.parse(sessionStorage.getItem("card"));
      if (card == 0) {
        if (object) {
          let index = 0;
          object.arr.forEach((element) => {
            index += element.quality;
          });
          return index;
        } else return 0;
      } else {
        return this.$store.state.card;
      }
    },
    handleLogout()
    {
      localStorage.clear()
      window.history.go()
    },
    getUserDetail()
    {
      if(localStorage.getItem("user")==null)
      return
      let arr = []
      arr = localStorage.getItem("user").trim().split(" ")

      let jwt = arr[1]
      let decoded = jwt_decode(jwt);
      if(decoded.sub==null)
      {
        localStorage.clear()
      }

       axios
        .get(this.baseURL + `/user/${decoded.sub}`)
        .then((response) => {
          localStorage.setItem("user_id",response.data.id)
          console.log(response)
          this.username = response.data.username


        }).catch ((error) =>
          {

 debugger
            console.log(error)

          if(error.response.status == 401||)
          {
            localStorage.clear()
          }
        } )

    }
  },
  data() {
    return {
      activeBtn: 1,
        baseURL: 'http://localhost:9999/wear_shop/api',
        username:''
    };
  },
};
</script>
<style scoped>
.v-application .primary {
  background-color: #121111 !important;
  border-color: #2d3753 !important;
}
.v-application .secondary {
  background-color: #090909 !important;
  border-color: #2d3753 !important;
}
</style>
