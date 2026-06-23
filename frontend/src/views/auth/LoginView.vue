<template>
  <div class="auth-page">
    <div class="auth-card card">
      <div class="auth-header">
        <h1>Smart Mobility</h1>
        <p>Accedi al sistema di mobilità urbana</p>
      </div>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="email">Email</label>
          <input id="email" v-model="email" type="email" placeholder="Inserisci email" required />
        </div>
        <div class="form-group">
          <label for="password">Password</label>
          <input id="password" v-model="password" type="password" placeholder="Inserisci password" required />
        </div>
        <p v-if="error" class="error-message">{{ error }}</p>
        <button type="submit" class="btn-primary btn-full" :disabled="loading">
          {{ loading ? 'Accesso in corso...' : 'Accedi' }}
        </button>
      </form>
      <div class="auth-footer">
        <p>Non hai un account? <router-link to="/register">Registrati</router-link></p>
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

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function handleLogin() {
  error.value = ''
  loading.value = true
  try {
    const data = await auth.login(email.value, password.value)
    redirectByRole(data.ruolo)
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante il login'
  } finally {
    loading.value = false
  }
}

function redirectByRole(ruolo: string) {
  if (ruolo === 'Utente') router.push('/utente')
  else if (ruolo === 'PA') router.push('/pa')
  else if (ruolo === 'Operatore') {
    const tipo = localStorage.getItem('operatore_tipo')
    if (tipo === 'OperatoreTecnico') router.push('/operatore-t')
    else router.push('/operatore-sc')
  }
  else router.push('/login')
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
