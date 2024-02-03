import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../pages/HomeView.vue'
import LoginPage from "@/pages/LoginPage";

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'home', component: HomeView  },
  { path: '/login', name: 'login', component: LoginPage, meta: { loginPage: true, nonRequiresAuth: true } }
]

const router = new VueRouter({
  mode: "history",
  routes
})

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
