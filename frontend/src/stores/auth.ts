import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { AuthResponse } from '../types'
import * as authApi from '../api/auth'

export const useAuthStore = defineStore('auth', () => {
  const user = ref<AuthResponse | null>(null)
  const token = ref<string | null>(null)

  const isLoggedIn = computed(() => !!token.value)
  const ruolo = computed(() => user.value?.ruolo || null)
  const userId = computed(() => user.value?.idUtente || null)
  const userEmail = computed(() => user.value?.email || null)

  function loadFromStorage() {
    const storedUser = localStorage.getItem('auth_user')
    const storedToken = localStorage.getItem('auth_token')
    if (storedUser && storedToken) {
      const parsed: AuthResponse = JSON.parse(storedUser)
      user.value = parsed
      token.value = storedToken
      syncOperatoreTipo(parsed)
    }
  }

  function syncOperatoreTipo(data: AuthResponse | null) {
    if (data?.tipo) {
      localStorage.setItem('operatore_tipo', data.tipo)
    } else {
      localStorage.removeItem('operatore_tipo')
    }
  }

  async function login(email: string, password: string) {
    const response = await authApi.login({ email, password })
    const data = response.data
    user.value = data
    token.value = data.token
    localStorage.setItem('auth_user', JSON.stringify(data))
    localStorage.setItem('auth_token', data.token)
    syncOperatoreTipo(data)
    return data
  }

  async function register(nome: string, cognome: string, email: string, password: string, datanascita: string) {
    const response = await authApi.register({ nome, cognome, email, password, datanascita })
    const data = response.data
    user.value = data
    token.value = data.token
    localStorage.setItem('auth_user', JSON.stringify(data))
    localStorage.setItem('auth_token', data.token)
    syncOperatoreTipo(data)
    return data
  }

  async function logout() {
    if (user.value?.email) {
      try {
        await authApi.logout(user.value.email)
      } catch (e) {
        console.error('Logout API failed:', e)
      }
    }
    user.value = null
    token.value = null
    localStorage.removeItem('auth_user')
    localStorage.removeItem('auth_token')
    localStorage.removeItem('operatore_tipo')
  }

  loadFromStorage()

  return { user, token, isLoggedIn, ruolo, userId, userEmail, login, register, logout }
})
