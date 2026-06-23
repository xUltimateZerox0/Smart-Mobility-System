<template>
  <div>
    <h1>Moderazione Utenti</h1>
    <p style="color:var(--gray);margin-bottom:16px">Gestisci segnalazioni e stato utenti</p>
    <div class="form-group">
      <label>ID Utente</label>
      <input v-model.number="userId" type="number" />
    </div>
    <div style="display:flex;gap:8px;margin-bottom:16px">
      <button @click="loadReport" class="btn-primary" :disabled="loading">{{ loading ? 'Caricamento...' : 'Carica Report' }}</button>
      <button @click="toggleModerate" class="btn-secondary" :disabled="loading">Modera</button>
    </div>
    <div v-if="report" class="card">
      <pre style="font-size:13px;white-space:pre-wrap">{{ JSON.stringify(report, null, 2) }}</pre>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as adminApi from '../../api/admin'

const userId = ref(1)
const report = ref<any>(null)
const loading = ref(false)
const error = ref('')

async function loadReport() {
  loading.value = true; error.value = ''
  try { const res = await adminApi.getUserReport(userId.value); report.value = res.data } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}

async function toggleModerate() {
  loading.value = true; error.value = ''
  try { await adminApi.moderateUser(userId.value); report.value = { message: `Utente ${userId.value} moderato` } } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}
</script>

<style scoped>
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
