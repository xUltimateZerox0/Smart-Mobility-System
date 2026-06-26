<template>
  <div class="auth-page">
    <div class="auth-card card">
      <div class="auth-header">
        <h1>Registrazione</h1>
        <p>Crea un nuovo account</p>
      </div>
      <form @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="nome">Nome</label>
          <input id="nome" v-model="nome" type="text" required />
        </div>
        <div class="form-group">
          <label for="cognome">Cognome</label>
          <input id="cognome" v-model="cognome" type="text" required />
        </div>
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" v-model="email" type="email" required />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input id="password" v-model="password" type="password" required />
        </div>
        <div class="form-group">
          <label for="datanascita">Data di nascita</label>
          <input id="datanascita" v-model="datanascita" type="date" required />
        </div>
        <p v-if="error" class="error-message">{{ error }}</p>
        <button type="submit" class="btn-primary btn-full" :disabled="loading">
          {{ loading ? 'Registrazione in corso...' : 'Registrati' }}
        </button>
      </form>
      <div class="auth-footer">
        <p>Hai già un account? <router-link to="/login">Accedi</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const nome = ref('')
const cognome = ref('')
const email = ref('')
const password = ref('')
const datanascita = ref('')
const error = ref('')
const loading = ref(false)

async function handleRegister() {
  error.value = ''
  loading.value = true
  try {
    await auth.register(nome.value, cognome.value, email.value, password.value, datanascita.value)
    router.push('/utente')
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante la registrazione'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #1a73e8 0%, #1557b0 100%);
}
.auth-card {
  width: 400px;
  max-width: 90vw;
}
.auth-header {
  text-align: center;
  margin-bottom: 28px;
}
.auth-header h1 {
  font-size: 24px;
  margin-bottom: 6px;
}
.auth-header p {
  color: var(--gray);
  font-size: 14px;
}
.btn-full {
  width: 100%;
  margin-top: 8px;
}
.error-message {
  color: var(--danger);
  font-size: 13px;
  margin-bottom: 8px;
}
.auth-footer {
  text-align: center;
  margin-top: 20px;
  font-size: 13px;
  color: var(--gray);
}
</style>
