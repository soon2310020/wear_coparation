import Vue from 'vue'
import axios from 'axios'
import Router from 'vue-router'
import Home from '@/components/Home'
import DashBoard from '@/components/DashBoard'
import Category from '@/components/category'
import Product from '@/components/Product'
import User from '@/components/user'
import FloatCharts from '@/components/charts/FloatCharts'
import MorrisCharts from '@/components/charts/MorrisCharts'

import Forms from '@/components/Forms'
import Tables from '@/components/Tables'

import Buttons from '@/components/ui-elements/Buttons'
import Grid from '@/components/ui-elements/Grid'
import Icons from '@/components/ui-elements/Icons'
import Notifications from '@/components/ui-elements/Notifications'
import Panels from '@/components/ui-elements/Panels'
import Typography from '@/components/ui-elements/Typography'

import Blank from '@/components/pages/Blank'
import Login from '@/components/pages/Login'
import Order from '@/components/order'
import Blog from '@/components/blog' 

Vue.use(Router)

const router = new Router({
  routes: [{
    path: '/',
    name: 'Home',
    component: Home,
    children: [
      { path: '/category', name: 'category', component: Category },
      { path: '/product', name: 'product', component: Product },
      { path: '/user', name: 'user', component: User },
      { path: '/dashborad', name: 'DashBoard', component: DashBoard },

      { path: '/float', name: 'FloatCharts', component: FloatCharts },
      { path: '/morris', name: 'MorrisCharts', component: MorrisCharts },

      { path: '/tables', name: 'Tables', component: Tables },
      { path: '/forms', name: 'Forms', component: Forms },

      { path: '/buttons', name: 'Buttons', component: Buttons },
      { path: '/grid', name: 'Grid', component: Grid },
      { path: '/icons', name: 'Icons', component: Icons },
      { path: '/notifications', name: 'Notifications', component: Notifications },
      { path: '/panels', name: 'Panels', component: Panels },
      { path: '/typography', name: 'Typography', component: Typography },
      { path: '/blank', name: 'Blank', component: Blank },
      {path: '/order', name: 'Order', component: Order},
      {path: '/blog', name: 'blog', component: Blog}
    ]
  },
  { path: '/login', name: 'Login', component: Login }

  ]
})
router.beforeEach((to, from, next) => {
  console.log(to)
  const publicPages = ['/login', '/register']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = window.sessionStorage.getItem('user')
  if (loggedIn) {
    axios.defaults.headers = {
      'Access-Control-Allow-Origin': '*',
      'Content-type': 'application/json',
      'Authorization': loggedIn
    }
  }
  if (authRequired && !loggedIn) {
    let loc = window.location
    const port = loc.port ? ':' + loc.port : ''
    loc.href = `//${loc.hostname}${port}/#/login`
    next()
  } else {
    next()
  }
})
// router.replace({ path: '/product', redirect: '/product' })
export default router
