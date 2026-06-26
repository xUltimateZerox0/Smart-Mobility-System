import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/auth/LoginView.vue'),
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/auth/RegisterView.vue'),
  },
  {
    path: '/utente',
    component: () => import('../views/utente/UtenteLayout.vue'),
    meta: { requiresAuth: true, ruolo: 'Utente' },
    children: [
      { path: '', name: 'UtenteDashboard', component: () => import('../views/utente/DashboardView.vue') },
      { path: 'vehicles', name: 'VehicleSearch', component: () => import('../views/utente/VehicleSearchView.vue') },
      { path: 'vehicles/:id', name: 'VehicleDetail', component: () => import('../views/utente/VehicleDetailView.vue') },
      { path: 'ride/:id', name: 'Ride', component: () => import('../views/utente/RideView.vue') },
      { path: 'bookings', name: 'Bookings', component: () => import('../views/utente/BookingView.vue') },
      { path: 'payments', name: 'Payments', component: () => import('../views/utente/PaymentView.vue') },
    ],
  },
  {
    path: '/operatore-t',
    component: () => import('../views/operatore-t/OperatoreTLayout.vue'),
    meta: { requiresAuth: true, ruolo: 'Operatore' },
    children: [
      { path: '', name: 'FleetDashboard', component: () => import('../views/operatore-t/FleetDashboard.vue') },
    ],
  },
  {
    path: '/operatore-sc',
    component: () => import('../views/operatore-sc/OperatoreSCLayout.vue'),
    meta: { requiresAuth: true, ruolo: 'Operatore' },
    children: [
      { path: '', name: 'UserModeration', component: () => import('../views/operatore-sc/UserModerationView.vue') },
    ],
  },
  {
    path: '/pa',
    component: () => import('../views/pa/PALayout.vue'),
    meta: { requiresAuth: true, ruolo: 'PA' },
    children: [
      { path: '', name: 'PADashboard', component: () => import('../views/pa/DashboardView.vue') },
      { path: 'statistics', name: 'Statistics', component: () => import('../views/pa/StatisticsView.vue') },
      { path: 'zones', name: 'ZoneManagement', component: () => import('../views/pa/ZoneManagementView.vue') },
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFoundView.vue'),
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

router.beforeEach((to, _from, next) => {
  const auth = useAuthStore()
  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    next({ name: 'Login' })
    return
  }
  if (to.meta.ruolo && auth.ruolo !== to.meta.ruolo) {
    if (auth.ruolo === 'Utente') next({ name: 'UtenteDashboard' })
    else if (auth.ruolo === 'PA') next({ name: 'PADashboard' })
    else if (auth.ruolo === 'Operatore') {
      const tipo = localStorage.getItem('operatore_tipo')
      if (tipo === 'OperatoreTecnico') next({ name: 'FleetDashboard' })
      else next({ name: 'UserModeration' })
    }
    else next({ name: 'Login' })
    return
  }
  next()
})

export default router
