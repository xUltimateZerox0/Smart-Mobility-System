<template>
  <div class="moderation-container">
    <h1>Moderazione Utenti</h1>
    <p style="color:var(--gray);margin-bottom:16px">Gestisci segnalazioni e stato utenti</p>

    <div class="two-column-layout">
      <div class="user-list-column">
        <div v-if="loadError" class="error-msg">{{ loadError }}</div>
        <div v-else-if="users.length > 0" class="user-list">
          <div v-for="u in users" :key="u.id"
            class="card user-item"
            :class="{ selected: userId === u.idUtente }"
            @click="selectUser(u.idUtente)">
            <p><strong>{{ u.nome }} {{ u.cognome }}</strong></p>
            <p class="user-email">{{ u.email }}</p>
            <p class="user-id-line">ID: {{ u.idUtente }} -
              <span class="badge" :class="statoBadgeClass(u.stato)">{{ u.stato }}</span>
            </p>
          </div>
        </div>
        <div v-else-if="usersLoading" style="color:var(--gray)">Caricamento utenti...</div>
        <div v-else style="color:var(--gray)">Nessun utente trovato</div>
      </div>

      <div v-if="selectedUser" class="detail-column card">
        <div class="detail-header">
          <h2>{{ selectedUser.nome }} {{ selectedUser.cognome }}</h2>
          <p class="user-meta">ID: {{ selectedUser.idUtente }} | {{ selectedUser.email }}</p>
          <p><span class="badge" :class="statoBadgeClass(selectedUser.stato)">{{ selectedUser.stato }}</span></p>
        </div>

        <div class="section">
          <h3>Gestione Stato</h3>
          <p class="section-note">Stato attuale: <span class="badge" :class="statoBadgeClass(selectedUser.stato)">{{ selectedUser.stato }}</span></p>
          <div class="button-row">
            <button @click="handleBlock" class="btn-danger" :disabled="selectedUser.stato !== 'attivo' || loading">Blocca</button>
            <button @click="handleUnblock" class="btn-secondary" :disabled="selectedUser.stato !== 'sospeso' || loading">Sblocca</button>
            <button @click="handleDisable" class="btn-danger" :disabled="(selectedUser.stato !== 'attivo' && selectedUser.stato !== 'sospeso') || loading">Disattiva</button>
          </div>
        </div>

        <div class="section">
          <h3>Report</h3>
          <div v-if="reportLoading" style="color:var(--gray);font-size:13px">Caricamento report...</div>
          <pre v-else-if="report" class="report-pre">{{ typeof report === 'string' ? report : JSON.stringify(report, null, 2) }}</pre>
          <p v-else style="color:var(--gray);font-size:13px">Nessun report presente</p>
          <button v-if="report" @click="handleClearReport" class="btn-warning" :disabled="loading">Cancella Report</button>
        </div>

        <div class="section">
          <h3>Azione Correttiva</h3>
          <p class="section-note">Aggiungi una nota al report. Questa operazione non modifica lo stato dell'utente.</p>
          <div class="form-row">
            <input v-model="correctiveActionText" placeholder="es. sospensione per violazione termini" />
            <button @click="applyCorrectiveAction" class="btn-primary" :disabled="actionLoading || !correctiveActionText.trim()">
              {{ actionLoading ? 'Applicazione...' : 'Aggiungi Nota' }}
            </button>
          </div>
        </div>

        <div class="section">
          <h3>Moderazione Rapida</h3>
          <button @click="toggleModerate" class="btn-secondary" :disabled="selectedUser.stato === 'disattivato' || loading">
            {{ selectedUser.stato === 'attivo' ? 'Sospendi' : selectedUser.stato === 'sospeso' ? 'Riattiva' : 'Non disponibile' }}
          </button>
          <p v-if="selectedUser.stato === 'disattivato'" class="help-text">Utente disattivato: moderazione non disponibile</p>
        </div>

        <p v-if="successMsg" class="success-msg">{{ successMsg }}</p>
        <p v-if="error" class="error-msg">{{ error }}</p>
      </div>

      <div v-else class="detail-column detail-empty">
        <p>Seleziona un utente per visualizzare i dettagli</p>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import * as adminApi from '../../api/admin'
import type { UtenteResponse } from '../../types'

const users = ref<UtenteResponse[]>([])
const userId = ref<number | null>(null)
const report = ref<any>(null)
const loading = ref(false)
const reportLoading = ref(false)
const actionLoading = ref(false)
const correctiveActionText = ref('')
const error = ref('')
const successMsg = ref('')
const loadError = ref('')
const usersLoading = ref(false)

const selectedUser = computed(() => users.value.find(u => u.idUtente === userId.value) || null)

onMounted(async () => {
  usersLoading.value = true
  try {
    const res = await adminApi.getUsers()
    users.value = res.data
  } catch (e: any) {
    if (e.code === 'ECONNABORTED') {
      loadError.value = 'Richiesta scaduta. Il server potrebbe non essere raggiungibile. Riprova.'
    } else if (!e.response) {
      loadError.value = 'Server non raggiungibile. Verifica che il backend sia in esecuzione.'
    } else {
      loadError.value = e.response?.data?.message || 'Errore nel caricamento degli utenti. Verifica i permessi di accesso.'
    }
    console.error('getUsers failed:', e)
  } finally {
    usersLoading.value = false
  }
})

function statoBadgeClass(stato: string): string {
  if (stato === 'attivo') return 'badge-success'
  if (stato === 'sospeso') return 'badge-warning'
  if (stato === 'disattivato') return 'badge-danger'
  return ''
}

function updateUserStato(id: number, newStato: string) {
  const u = users.value.find(u => u.idUtente === id)
  if (u) u.stato = newStato
}

function selectUser(id: number) {
  userId.value = id
  report.value = null
  successMsg.value = ''
  error.value = ''
  loadReport()
}

async function loadReport() {
  if (!userId.value) return
  reportLoading.value = true
  error.value = ''
  try {
    const res = await adminApi.getUserReport(userId.value)
    report.value = res.data
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore nel caricamento del report'
  } finally {
    reportLoading.value = false
  }
}

async function handleBlock() {
  if (!userId.value) return
  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.blockUser(userId.value)
    updateUserStato(userId.value, 'sospeso')
    successMsg.value = 'Utente bloccato con successo'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante il blocco' }
  finally { loading.value = false }
}

async function handleUnblock() {
  if (!userId.value) return
  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.unblockUser(userId.value)
    updateUserStato(userId.value, 'attivo')
    successMsg.value = 'Utente sbloccato con successo'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante lo sblocco' }
  finally { loading.value = false }
}

async function handleDisable() {
  if (!userId.value) return
  const confirmed = confirm('ATTENZIONE: Questa azione è DEFINITIVA e non può essere annullata. L\'account verrà disattivato permanentemente. Sei sicuro di voler procedere?')
  if (!confirmed) return
  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.disableUser(userId.value)
    updateUserStato(userId.value, 'disattivato')
    successMsg.value = 'Utente disattivato con successo'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante la disattivazione' }
  finally { loading.value = false }
}

async function handleClearReport() {
  if (!userId.value) return
  if (!confirm('Sei sicuro di voler cancellare il report?')) return
  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.clearUserReport(userId.value)
    report.value = null
    successMsg.value = 'Report cancellato con successo'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante la cancellazione del report' }
  finally { loading.value = false }
}

async function toggleModerate() {
  if (!userId.value) return
  loading.value = true; error.value = ''; successMsg.value = ''
  const currentStato = selectedUser.value?.stato
  const newStato = currentStato === 'attivo' ? 'sospeso' : 'attivo'
  try {
    await adminApi.moderateUser(userId.value)
    updateUserStato(userId.value, newStato)
    successMsg.value = currentStato === 'attivo' ? 'Utente sospeso' : 'Utente riattivato'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante la moderazione' }
  finally { loading.value = false }
}

async function applyCorrectiveAction() {
  if (!userId.value || !correctiveActionText.value.trim()) return
  actionLoading.value = true; error.value = ''; successMsg.value = ''
  try {
    await adminApi.correctiveAction(userId.value, correctiveActionText.value)
    successMsg.value = 'Nota aggiunta al report'
    correctiveActionText.value = ''
    await loadReport()
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { actionLoading.value = false }
}
</script>

<style scoped>
.moderation-container { max-width: 1200px; }

.two-column-layout {
  display: flex;
  gap: 20px;
  align-items: flex-start;
}

.user-list-column {
  width: 320px;
  flex-shrink: 0;
}

.user-list {
  display: flex;
  flex-direction: column;
  gap: 6px;
  max-height: 70vh;
  overflow-y: auto;
}

.user-item {
  padding: 10px 12px;
  border: 1px solid var(--light-gray);
  cursor: pointer;
  transition: border-color 0.15s;
}

.user-item:hover {
  border-color: var(--primary);
}

.user-item.selected {
  border-color: var(--primary);
  background: rgba(26,115,232,0.05);
}

.user-item p { font-size: 13px; margin-bottom: 2px; line-height: 1.4; }
.user-email { color: var(--gray); font-size: 12px !important; }
.user-id-line { font-size: 12px !important; color: var(--gray); }

.detail-column {
  flex: 1;
  min-width: 0;
}

.detail-empty {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: 200px;
  color: var(--gray);
}

.detail-header {
  padding-bottom: 16px;
  border-bottom: 1px solid var(--light-gray);
  margin-bottom: 16px;
}

.detail-header h2 { font-size: 20px; margin-bottom: 4px; }
.user-meta { color: var(--gray); font-size: 13px; margin-bottom: 8px; }

.section {
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid var(--light-gray);
}

.section:last-of-type { border-bottom: none; }

.section h3 {
  font-size: 15px;
  margin-bottom: 8px;
  color: var(--dark);
}

.section-note {
  font-size: 12px;
  color: var(--gray);
  margin-bottom: 10px;
}

.button-row {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.form-row {
  display: flex;
  gap: 8px;
  align-items: flex-end;
}

.form-row input { flex: 1; }

.report-pre {
  font-size: 13px;
  white-space: pre-wrap;
  word-break: break-word;
  background: #1e1e1e;
  color: #d4d4d4;
  padding: 12px;
  border-radius: 6px;
  margin-bottom: 10px;
  max-height: 200px;
  overflow-y: auto;
}

.success-msg { color: var(--secondary); font-size: 13px; margin-top: 8px; }
.error-msg { color: var(--danger); font-size: 13px; margin-top: 8px; }
.help-text { color: var(--gray); font-size: 12px; margin-top: 6px; }

.btn-warning {
  background: var(--warning);
  color: #333;
}

.btn-warning:hover:not(:disabled) {
  background: #e5a800;
}
</style>
