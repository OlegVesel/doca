import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate"
import router from "../router/index"
import authApi from "@/api/authApi";
import cardApi from "@/api/cardApi";
import documentApi from "@/api/documentApi";
import dictionaryApi from "@/api/dictionaryApi";
import userApi from "@/api/userApi";
import axios from "axios";

Vue.use(Vuex)

export default new Vuex.Store({
    //сохраняем состояния после перезагрузки страницы
    plugins: [createPersistedState({paths: ['account']})],
    state: {
        isAuthenticated: localStorage.getItem('auth'),
        account: {
            roles:[]
        },
        cards:[],
        typeDocs:[],
        users: [],
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
        getTypeDocs(state){
            return state.typeDocs
        },
        getUsers(state){
            return state.users
        }
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
            localStorage.setItem('login', payload.login)
        },

        clearAuth(state) {
            localStorage.removeItem('auth')
            localStorage.removeItem('token')
            localStorage.removeItem('login')
            state.isAuthenticated = localStorage.getItem('auth')
            state.account = {}
        },
        setCards(state, cards){
            state.cards = cards
        },
        addCardToList(state, card){
            state.cards.push(card)
        },
        deleteCardFromList(state, id){
            let index = state.cards.findIndex(c => c.id === id);
            if (index > -1)
            state.cards = [...state.cards.slice(0, index),
                ...state.cards.slice(index + 1)]
        },
        replaceCardInList(state, card){
            let index = state.cards.findIndex(c => c.id === card.id);
            if (index > -1){
                Vue.set(state.cards, index, card)
            }
        },
        clearState(state){
            state.cards = []
        },
        setTypeDocs(state, typeDocs){
            state.typeDocs = typeDocs
        },
        setUsers(state, list){
            state.users = list
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
                    await router.push('login')
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
        },
        async deleteCardFromServer({commit}, id){
            try {
                let response = await cardApi.deleteCard(id)
                if (response.status === 200){
                    commit('deleteCardFromList', id)
                }
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        },
        async getTypeDocsFromServer({commit}){
            try {
                let response = await dictionaryApi.getAllTypeDocs()
                if (response.status === 200){
                    commit('setTypeDocs', response.data)
                }
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        },
        async deleteFileFromCard({commit}, id){
            try {
                let { status } = await documentApi.softDeleteDocument(id)
                if (status === 200){
                    console.log('success')
                } else
                    commit('')
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        },
        async hardDeleteFileFromCard({commit}, id){
            try {
                let { status } = await documentApi.hardDeleteDocument(id)
                if (status === 200){
                    console.log('success')
                } else
                    commit('')
            } catch (err){
                if (err.request.status === 401)
                    await router.push("/login")
            }
        },
        async getUsersAction({commit}){
            try {
                let { status, data } = await userApi.getAllUsers()
                if (status === 200){
                    commit('setUsers', data)
                }
            } catch (err){
                if (err.request.status === 401){
                    await router.push("login")
                }
            }
        }
    },
    modules: {}
})
