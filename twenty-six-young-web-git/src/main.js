// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
import router from './router'
import Axios from 'axios'
import store from './store/index.js'
import Vuex from 'vuex'
import $ from 'jquery'
import '../static/UEditor/ueditor.config.js'
import '../static/UEditor/ueditor.all.min.js'
import '../static/UEditor/lang/zh-cn/zh-cn.js'
import '../static/UEditor/ueditor.parse.min.js'

Vue.use(Antd)
Vue.use(Vuex)
Vue.prototype.$ = $
Vue.config.productionTip = false
Vue.prototype.$axios = Axios
Vue.prototype.HOST = '/26young'

Axios.interceptors.response.use((response) => {
  return response.data
}, function (error) {
  return Promise.reject(error)
})

// request interceptor
Axios.interceptors.request.use(config => {
  return config
}, (error) => {
  return Promise.reject(error)
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 这里判断用户是否登录，我例子中是验证本地存储是否有token
    if (!sessionStorage.getItem('loginStatus')) {
      console.log('failed')
      next({
        name: 'Login',
        query: { redirect: to.fullPath }
      })
    } else {
      console.log('success')
      next()
    }
  } else {
    next() // 确保一定要调用 next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
