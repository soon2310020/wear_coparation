import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    category:null,
    card:0
  },
  mutations: {
    SET_ITEM(state, value) {
      state.category = value
      console.log(value)
      },
      SET_ITEM_CARD(state, value) {
        state.card = parseInt(value)
        console.log(value)
        }
  },
  actions: {
    setCategory({ commit }, payload) {
      commit("SET_ITEM", payload)
      },

      setIndexCard({ commit }, payload) {
        commit("SET_ITEM_CARD", payload)
        }
  },
  modules: {
  }
})
