import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import LoginPage from "@/pages/LoginPage";
import DocumentList from "@/pages/documents/DocumentList";

Vue.use(VueRouter)

const routes = [
  { path: '/', name: 'home', component: HomePage  },
  { path: '/users/:id/documents', name: 'documentList', component: DocumentList  },
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
