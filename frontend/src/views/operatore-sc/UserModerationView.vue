<template>
  <div>
    <h1>Moderazione Utenti</h1>
    <p style="color:var(--gray);margin-bottom:16px">Gestisci segnalazioni e stato utenti</p>
    <div class="form-group">
      <label>ID Utente</label>
      <input v-model.number="userId" type="number" />
    </div>
    <div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">
      <button @click="loadReport" class="btn-primary" :disabled="loading">{{ loading ? 'Caricamento...' : 'Carica Report' }}</button>
      <button @click="toggleModerate" class="btn-secondary" :disabled="loading">Modera</button>
    </div>
    <div class="form-group" style="display:flex;gap:8px;align-items:end;margin-bottom:16px">
      <div>
        <label>Azione Correttiva</label>
        <input v-model="correctiveActionText" placeholder="es. sospensione per violazione termini" style="min-width:300px" />
      </div>
      <button @click="applyCorrectiveAction" class="btn-danger" :disabled="actionLoading">{{ actionLoading ? 'Applicazione...' : 'Applica Azione' }}</button>
    </div>
    <div v-if="report" class="card">
      <pre style="font-size:13px;white-space:pre-wrap">{{ typeof report === 'string' ? report : JSON.stringify(report, null, 2) }}</pre>
    </div>
    <p v-if="successMsg" style="color:var(--success);font-size:13px;margin-top:8px">{{ successMsg }}</p>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as adminApi from '../../api/admin'

const userId = ref(1)
const report = ref<any>(null)
const loading = ref(false)
const actionLoading = ref(false)
const correctiveActionText = ref('')
const error = ref('')
const successMsg = ref('')

async function loadReport() {
  loading.value = true; error.value = ''; successMsg.value = ''
  try { const res = await adminApi.getUserReport(userId.value); report.value = res.data } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}

async function toggleModerate() {
  loading.value = true; error.value = ''; successMsg.value = ''
  try { await adminApi.moderateUser(userId.value); report.value = `Utente ${userId.value} moderato`; successMsg.value = 'Moderazione applicata' } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}

async function applyCorrectiveAction() {
  if (!correctiveActionText.value.trim()) { error.value = 'Inserisci un testo per l\'azione correttiva'; return }
  actionLoading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.correctiveAction(userId.value, correctiveActionText.value)
    successMsg.value = 'Azione correttiva applicata'
    correctiveActionText.value = ''
    await loadReport()
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { actionLoading.value = false }
}
</script>

<style scoped>
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
