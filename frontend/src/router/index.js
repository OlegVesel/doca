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

// router.beforeEach(to => {
//     // Judgment whether the target route is login, if it is a different verification permission (directly return TRUE), vice versail permissions
//     if (to.name === 'login') {
//         return true
//     } else {
//         // Judgment if the user is already logged in, note that this is a string to determine the string
//         if (localStorage.getItem('auth')) {
//             return true
//         } else {
//             return {
//                 path: '/login'
//             }
//         }
//     }
// })
router.beforeEach((to, from, next) => {
    const requiresAuth = !to.matched.some((record) => record.meta.nonRequiresAuth)
    const isLoginPage = to.matched.some((record) => record.meta.loginPage)
    const isAuthenticated = localStorage.getItem('auth')


    if (requiresAuth && !isAuthenticated) {
        next('/login')
    } else if (isLoginPage && isAuthenticated) {
        router.push('/')
    }
    next()
})

export default router
