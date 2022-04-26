// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import vuetify from '@/plugins/vuetify' // path to vuetify export
import App from './App'
import router from './router'
// import 'material-design-icons-iconfont/dist/material-design-icons.css'
import axios from "axios";
import store from './store'
axios.defaults.headers = {
  "Access-Control-Allow-Origin": "*",
  "Content-type": "application/json",
  // "Authorization": user,
};
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  vuetify,
  components: { App },
  template: '<App/>'
})
