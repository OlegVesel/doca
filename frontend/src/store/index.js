import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import router from "../router/index"
import authApi from "@/api/authApi";
import cardApi from "@/api/cardApi";
import axios from "axios";

Vue.use(Vuex)

export default new Vuex.Store({
    //сохраняем состояния после перезагрузки страницы
    plugins: [createPersistedState({paths: ['account']})],
    state: {
        isAuthenticated: localStorage.getItem('auth'),
        account: {},
        cards:[],
    },
    getters: {
        isAdmin(state) {
            let index = state.account.roles?.findIndex(item => item === 'ROLE_ADMIN')
            return index > -1
        },

        isAuth() {
            return localStorage.getItem('auth')
        },
        getAccount(state) {
            return state.account
        },
        getCards(state){
            return state.cards
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
        setCards(state, cards){
            state.cards = cards
        },
        addCardToList(state, card){
            state.cards.push(card)
        },
        deleteCardFromList(state, card){
            let index = state.cards.findIndex(c => c.id === card.id);
            if (index > -1)
            state.cards = [...state.cards.splice(0, index),
                ...state.cards.splice(index + 1)]
        },
        replaceCardInList(state, card){
            let index = state.cards.findIndex(c => c.id === card.id);
            if (index > -1){
                Vue.set(state.cards, index, card)
            }
        },
        clearState(state){
            state.cards = []
        }
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
            commit('clearState')
            axios.defaults.headers.common['token'] = ''
            await router.push('/login')
        },
        async getCardsFromServer({commit}) {
            try {
                let response = await cardApi.getCards()
                commit('setCards', response.data)
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        },
        async saveCard({commit}, card){
            try{
                let response = await cardApi.saveCard(card)
                commit('addCardToList', response.data)
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        }
    },
    modules: {}
})
