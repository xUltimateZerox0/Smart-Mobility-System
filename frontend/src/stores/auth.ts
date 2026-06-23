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
      user.value = JSON.parse(storedUser)
      token.value = storedToken
    }
  }

  async function login(email: string, password: string) {
    const response = await authApi.login({ email, password })
    const data = response.data
    user.value = data
    token.value = data.token
    localStorage.setItem('auth_user', JSON.stringify(data))
    localStorage.setItem('auth_token', data.token)
    return data
  }

  async function register(nome: string, cognome: string, email: string, password: string, datanascita: string) {
    const response = await authApi.register({ nome, cognome, email, password, datanascita })
    const data = response.data
    user.value = data
    token.value = data.token
    localStorage.setItem('auth_user', JSON.stringify(data))
    localStorage.setItem('auth_token', data.token)
    return data
  }

  async function logout() {
    if (user.value?.email) {
      try {
        await authApi.logout(user.value.email)
      } catch {
        // proceed with local logout even if API fails
      }
    }
    user.value = null
    token.value = null
    localStorage.removeItem('auth_user')
    localStorage.removeItem('auth_token')
  }

  loadFromStorage()

  return { user, token, isLoggedIn, ruolo, userId, userEmail, login, register, logout }
})
