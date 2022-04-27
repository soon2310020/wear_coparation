import Vue from 'vue'
import axios from 'axios'
import Router from 'vue-router'
import Home from '@/components/Home'
import Shop from '@/components/Shop'
import Product from '@/components/Product'
import Blog from '@/components/Blog'
import Post from '@/components/Post'
import Cart from '@/components/Cart'
import Layout from '@/components/Layout'
import Login from '@/components/Login'
import Register from '@/components/Register'
Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      component: Layout,
      children: [
        {
          path: '/',
          component: Home,
          name: 'Home'
        },
        {
          path: '/shop',
          component: Shop,
          name: 'Shop'
        },
        {
          path: '/product/:id',
          component: Product,
          name: 'Product'
        },
        {
          path: '/blog',
          component: Blog,
          name: 'Blog'
        },
        {
          path: '/post',
          component: Post,
          name: 'Post'
        },
        {
          path: '/cart',
          component: Cart,
          name: 'Cart'
        }
      ]

    },
    {
      path: '/login',
      component: Login

    },
    {
      path: '/register',
      component: Register

    }
  ],
  mode: 'history'
}
)
router.beforeEach((to, from, next) => {
  console.log(to)
  const publicPages = ['/login','/Home','/shop','/product','/cart','/blog','/register','/']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')
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
    loc.href = `//${loc.hostname}${port}/login`
    next()
  } else {
    next()
  }
})
export default router
