import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import LoginPage from "@/pages/LoginPage";
import DocumentList from "@/pages/documents/DocumentList";


Vue.use(VueRouter)

const routes = [
    {path: '/', name: 'home', component: HomePage},
    {path: '/documents', name: 'documentList', component: DocumentList},
    {path: '/login', name: 'login', component: LoginPage, meta: {loginPage: true, nonRequiresAuth: true}}
]

const router = new VueRouter({
    mode: "history",
    routes
})

router.beforeEach(async (to, from, next) => {
    const isAuthenticated = localStorage.getItem('auth')
    if (to.name !== 'login' && !isAuthenticated) {
        next({ name: 'login' })
    }
    else next()
})

export default router
