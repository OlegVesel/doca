import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import router from "../router/index"
import authApi from "@/api/authApi";
import axios from "axios";

Vue.use(Vuex)

export default new Vuex.Store({
  //сохраняем состояния после перезагрузки страницы
  plugins: [createPersistedState({paths: ['account']})],
  state: {
    isAuthenticated: localStorage.getItem('auth'),
    account: {},
  },
  getters: {
    isAdmin(state) {
      let index = state.account.roles.findIndex(item => item === 'ROLE_ADMIN')
      return index > -1
    },

    isAuth() {
      return localStorage.getItem('auth')
    },
  },
  mutations: {
    setAuth(state, payload) {
      localStorage.setItem('auth', payload)
      state.isAuthenticated = localStorage.getItem('auth')
    },
    setToken(state, payload) {
      localStorage.setItem('token', payload)
    },

    setAccount(state, payload) {
      state.account = payload
    },

    clearAuth(state) {
      localStorage.removeItem('auth')
      localStorage.removeItem('token')
      state.isAuthenticated = localStorage.getItem('auth')
      state.account = {}
    },
  },
  actions: {
    async loginAction({commit}, account) {
      try {
        let data = await authApi.authorize(account)
        let result = data.data
        commit('setAccount', result)
        commit('setAuth', true)
        commit('setToken', result.tokenType + result.accessToken)
        axios.defaults.headers.common['token'] = localStorage.getItem('token')
        await router.push('/')
      } catch (err) {
        console.log(err)
      }
    },
    async logoutAction({commit}) {
      commit('clearAuth')
      axios.defaults.headers.common['token'] = ''
      await router.push('/login')
    },
  },
  modules: {

  }
})
