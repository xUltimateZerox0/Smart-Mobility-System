<template>
  <div>
    <h1>Moderazione Utenti</h1>
    <p style="color:var(--gray);margin-bottom:16px">Gestisci segnalazioni e stato utenti</p>
    <div v-if="users.length > 0" class="user-list" style="margin-bottom:16px">
      <div v-for="u in users" :key="u.id"
        class="card user-item"
        :class="{ selected: userId === u.idUtente }"
        @click="selectUser(u.idUtente)"
        style="cursor:pointer">
        <p><strong>{{ u.nome }} {{ u.cognome }}</strong> ({{ u.email }})</p>
        <p>ID: {{ u.idUtente }} - <span class="badge" :class="u.stato === 'attivo' ? 'badge-success' : 'badge-warning'">{{ u.stato }}</span></p>
      </div>
    </div>
    <div v-else style="color:var(--gray);margin-bottom:16px">Caricamento utenti...</div>
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
import { ref, onMounted } from 'vue'
import * as adminApi from '../../api/admin'
import type { UtenteResponse } from '../../types'

const users = ref<UtenteResponse[]>([])
const userId = ref(1)
const report = ref<any>(null)
const loading = ref(false)
const actionLoading = ref(false)
const correctiveActionText = ref('')
const error = ref('')
const successMsg = ref('')

onMounted(async () => {
  try {
    const res = await adminApi.getUsers()
    users.value = res.data
  } catch {}
})

function selectUser(id: number) {
  userId.value = id
  report.value = null
  successMsg.value = ''
  error.value = ''
}

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
.user-list { display: flex; flex-direction: column; gap: 8px; }
.user-item { padding: 10px; border: 1px solid var(--border); }
.user-item.selected { border-color: var(--primary); background: rgba(26,115,232,0.05); }
.user-item p { font-size: 13px; margin-bottom: 2px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
