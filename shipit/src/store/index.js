import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    category:null
  },
  mutations: {
    SET_ITEM(state, value) {
      state.category = value
      console.log(value)
      }
  },
  actions: {
    setCategory({ commit }, payload) {
      commit("SET_ITEM", payload)
      }
  },
  modules: {
  }
})
