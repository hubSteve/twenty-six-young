import Vue from 'vue'
import Vuex from 'vuex'
import app from './app'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    app
  },
  state: {
    // 存储数据
    msg: '获取到了'
  },
  mutations: {
    // 数据处理方法
  },
  actions: {
  },
  getters
})
