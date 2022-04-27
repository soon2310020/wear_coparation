// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import App from './App'
import router from './router'
// import 'material-design-icons-iconfont/dist/material-design-icons.css'
import axios from "axios";
import store from './store'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/vi'
import VueToastr from "vue-toastr";

import jwt_decode from "jwt-decode";
axios.defaults.headers = {
  "Access-Control-Allow-Origin": "*",
  "Content-type": "application/json",
  // "Authorization": user,
};
// use plugin
Vue.use(VueToastr, {
  /* OverWrite Plugin Options if you need */
});
Vue.config.productionTip = false
Vue.use(Element, { locale, size: "medium" })
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  components: { App },
  template: '<App/>'
})
