import { createRouter, createWebHistory } from 'vue-router'
import Home from '@/pages/TheHome.vue'
import Login from '@/pages/TheLogin.vue'
import Cart from '@/pages/TheCart.vue'
import Order from '@/pages/TheOrder.vue'

const routes = [
  { path: '/', component: Home },
  { path: '/login', component: Login },
  { path: '/cart', component: Cart },
  { path: '/order', component: Order }
]
const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
