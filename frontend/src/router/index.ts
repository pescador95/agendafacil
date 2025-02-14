import { createRouter, createWebHistory } from 'vue-router'
import { getAuthData } from '@/utils/contextHelpers'
import LoginPage from '@/pages/login/LoginPage.vue'
import ForgotPage from '@/pages/forgot/ForgotPage.vue'
import RecoveryPage from '@/pages/recovery/RecoveryPage.vue'
import AgendamentoPage from '@/pages/agendamento/AgendamentoPage.vue'

const routes = [
  {
    path: '/auth',
    name: 'Auth',
    component: LoginPage,
    meta: { requiresAuth: false },
  },
  {
    path: '/forgotPassword',
    name: 'Forgot',
    component: ForgotPage,
    meta: { requiresAuth: false },
  },
  {
    path: '/recoverPassword',
    name: 'Recovery',
    component: RecoveryPage,
    meta: { requiresAuth: false },
  },
  {
    path: '/agendamento',
    name: 'Agendamento',
    component: AgendamentoPage,
    meta: {
      requiresAuth: true,
      roles: ['usuario', 'admin'],
    },
  },

  {
    path: '/:catchAll(.*)',
    redirect: () => {

      const authData = getAuthData()
      if (authData.accessToken) {
        return '/agendamento'
      }
      return '/auth'
    },
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  const authData = getAuthData()
  const token = authData.accessToken

  if (token && to.name === 'Auth') {
    return next({ path: '/agendamento' })
  }

  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!token) {
      return next({ path: '/' })
    }

    if (
      to.matched.some(
        (record) =>
           !record.meta.roles // && !record.meta.roles.some((role) => authData.group.includes(role))
      ) // TODO: check if the user has the required role
    ) {
      return next({ path: '/unauthorized' })
    }
  }

  next()
})

export default router
