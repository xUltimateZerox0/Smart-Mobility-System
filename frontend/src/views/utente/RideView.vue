<template>
  <div>
    <h1>Corsa in corso</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong>
        <span class="badge" :class="isPaused ? 'badge-warning' : 'badge-success'">{{ isPaused ? 'Sospesa' : 'Corsa attiva' }}</span>
      </p>
      <div v-if="metodoPagamentoLabel" style="margin-bottom:8px">
        <p><strong>Metodo pagamento:</strong> <span class="badge badge-info">{{ metodoPagamentoLabel }}</span></p>
      </div>
      <div class="timer-display" :class="{ paused: isPaused }">
        <div class="timer-ring">
          <svg viewBox="0 0 100 100" class="timer-svg">
            <circle cx="50" cy="50" r="45" fill="none" stroke="var(--light-gray)" stroke-width="6"/>
            <circle cx="50" cy="50" r="45" fill="none" stroke="var(--primary)" stroke-width="6"
              :stroke-dasharray="283" :stroke-dashoffset="timerOffset"
              transform="rotate(-90 50 50)" style="transition: stroke-dashoffset 1s linear"/>
          </svg>
          <div class="timer-text">
            <div class="timer-value">{{ formattedTime }}</div>
            <div class="timer-label">durata</div>
          </div>
        </div>
        <div class="cost-display">
          <div class="cost-value">€{{ stima.toFixed(2) }}</div>
          <div class="cost-label">costo stimato</div>
          <div class="cost-rate">{{ veicoloTariffa.toFixed(2) }} €/h</div>
        </div>
      </div>

      <div v-if="!corsaAvviata && !showQrInput" style="margin-top:12px">
        <p style="font-size:13px;color:var(--gray);margin-bottom:8px">Scansiona il QR code sul veicolo per iniziare</p>
        <button @click="showQrInput = true" class="btn-primary">Scansiona QR Code</button>
      </div>

      <div v-if="showQrInput && !corsaAvviata" style="margin-top:12px" class="qr-input-section">
        <label style="font-size:13px;font-weight:600">Inserisci il QR Code del veicolo</label>
        <div style="display:flex;gap:8px;margin-top:4px">
          <input v-model="qrCode" type="text" placeholder="Es. QR-12345" style="flex:1;padding:8px;border:1px solid var(--border-color,#ccc);border-radius:6px" />
          <button @click="startRideFlow" class="btn-primary" :disabled="startLoading || !qrCode.trim()">{{ startLoading ? 'Sblocco...' : 'Avvia Corsa' }}</button>
        </div>
        <p style="font-size:12px;color:var(--gray);margin-top:4px">Il QR Code si trova sul veicolo o nella tua prenotazione attiva</p>
      </div>

      <div v-if="corsaAvviata && !timerStarted" style="margin-top:12px">
        <p style="font-size:13px;color:var(--success);margin-bottom:8px">Veicolo sbloccato! La corsa ha inizio.</p>
      </div>

      <div style="margin-top:16px;display:flex;gap:8px;flex-wrap:wrap">
        <button @click="endRide" class="btn-danger" :disabled="loading || !corsaAvviata">Termina Corsa</button>
        <button @click="togglePause" class="btn-secondary" :disabled="loading || !corsaAvviata">{{ isPaused ? 'Riprendi' : 'Sospendi' }}</button>
        <button @click="selectPayment" class="btn-primary" :disabled="loading">Metodo Pagamento</button>
      </div>
      <p v-if="successMsg" style="color:var(--success);font-size:13px;margin-top:8px">{{ successMsg }}</p>
      <p v-if="error" class="error-message">{{ error }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as ridesApi from '../../api/rides'
import * as vehiclesApi from '../../api/vehicles'
import * as paymentsApi from '../../api/payments'

const STORAGE_KEY = 'ride_state'
const ESTIMATE_INTERVAL_MS = 30000

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const vehicleId = Number(route.params.id)
if (isNaN(vehicleId)) {
  router.replace('/utente')
}
const corsaId = ref(0)
const stima = ref(0)
const veicoloTariffa = ref(0)
const isPaused = ref(false)
const corsaAvviata = ref(false)
const timerStarted = ref(false)
const loading = ref(false)
const startLoading = ref(false)
const error = ref('')
const successMsg = ref('')
const elapsedSeconds = ref(0)
const showQrInput = ref(false)
const qrCode = ref('')
const metodoPagamentoId = ref<number | null>(null)
const metodoPagamentoLabel = ref('')
let estimateInterval: ReturnType<typeof setInterval> | null = null
let timerInterval: ReturnType<typeof setInterval> | null = null

const formattedTime = computed(() => {
  const h = Math.floor(elapsedSeconds.value / 3600)
  const m = Math.floor((elapsedSeconds.value % 3600) / 60)
  const s = elapsedSeconds.value % 60
  return `${h.toString().padStart(2, '0')}:${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}`
})

const timerOffset = computed(() => {
  const maxSec = 3600
  const pct = Math.min(elapsedSeconds.value / maxSec, 1)
  return 283 * (1 - pct)
})

function persistState() {
  const state = {
    corsaId: corsaId.value,
    corsaAvviata: corsaAvviata.value,
    timerStarted: timerStarted.value,
    isPaused: isPaused.value,
    elapsedStart: Date.now() - elapsedSeconds.value * 1000,
    vehicleId: vehicleId,
  }
  localStorage.setItem(STORAGE_KEY, JSON.stringify(state))
}

function restoreState() {
  try {
    const raw = localStorage.getItem(STORAGE_KEY)
    if (!raw) return
    const state = JSON.parse(raw)
    if (state.corsaId) {
      corsaId.value = state.corsaId
      corsaAvviata.value = state.corsaAvviata
      isPaused.value = state.isPaused
      if (state.timerStarted) {
        timerStarted.value = true
        const elapsed = Math.floor((Date.now() - state.elapsedStart) / 1000)
        elapsedSeconds.value = Math.max(0, elapsed)
        startTimers(state.elapsedStart)
      }
    }
  } catch {
    localStorage.removeItem(STORAGE_KEY)
  }
}

function clearPersistedState() {
  localStorage.removeItem(STORAGE_KEY)
}

onMounted(async () => {
  const qrFromQuery = route.query.qrCode as string | undefined
  if (qrFromQuery) {
    qrCode.value = qrFromQuery
    showQrInput.value = true
  }

  const corsaFromQuery = Number(route.query.corsaId) || 0
  if (corsaFromQuery > 0) {
    corsaId.value = corsaFromQuery
    corsaAvviata.value = true
    timerStarted.value = true
    startTimers(Date.now())
    await updateEstimate()
  }

  restoreState()

  await loadVehicleTariffa()

  // If we already have an active ride, load payment method from it
  if (corsaId.value > 0) {
    await loadRidePaymentMethod()
  } else {
    await loadPaymentMethod()
  }
})

onUnmounted(() => {
  stopTimers()
})

function startTimers(startTimestamp?: number) {
  const base = startTimestamp || Date.now()
  persistState()
  timerInterval = setInterval(() => {
    if (!isPaused.value) {
      elapsedSeconds.value = Math.floor((Date.now() - base) / 1000)
    }
  }, 1000)
  if (!isPaused.value) {
    estimateInterval = setInterval(updateEstimate, ESTIMATE_INTERVAL_MS)
  }
}

function stopTimers() {
  if (estimateInterval) {
    clearInterval(estimateInterval)
    estimateInterval = null
  }
  if (timerInterval) {
    clearInterval(timerInterval)
    timerInterval = null
  }
}

async function loadVehicleTariffa() {
  try {
    const res = await vehiclesApi.getVehicleDetails(vehicleId)
    if (res.data && res.data.tariffa !== undefined) {
      veicoloTariffa.value = res.data.tariffa
    }
  } catch (e) { console.error('loadVehicleTariffa failed:', e) }
}

async function loadRidePaymentMethod() {
  if (!corsaId.value) return
  try {
    const res = await ridesApi.getActiveRide()
    if (res.data && res.data.idMetodoPagamento) {
      metodoPagamentoId.value = res.data.idMetodoPagamento
      metodoPagamentoLabel.value = res.data.metodoPagamentoLabel || ''
      return
    }
  } catch (e) { console.error('loadRidePaymentMethod failed:', e) }
  await loadPaymentMethod()
}

async function loadPaymentMethod() {
  if (!auth.userId) return
  try {
    const res = await paymentsApi.getSavedMethods()
    if (res.data && res.data.length > 0) {
      if (metodoPagamentoId.value) {
        const match = res.data.find(m => m.id === metodoPagamentoId.value)
        if (match) {
          metodoPagamentoLabel.value = `${match.numCarta.slice(-4)} - ${match.intestatarioCarta}`
          return
        }
      }
      const principale = res.data[0]
      metodoPagamentoLabel.value = `${principale.numCarta.slice(-4)} - ${principale.intestatarioCarta}`
    }
  } catch (e) { console.error('loadPaymentMethod failed:', e) }
}

async function startRideFlow() {
  if (!vehicleId) { error.value = 'ID veicolo non valido'; return }
  if (!qrCode.value.trim()) { error.value = 'QR Code obbligatorio per avviare la corsa'; return }
  if (!auth.userId) { error.value = 'Utente non autenticato'; return }

  startLoading.value = true; error.value = ''; successMsg.value = ''
  try {
    const res = await ridesApi.startRide(vehicleId, auth.userId, qrCode.value.trim())
    corsaId.value = res.data
    corsaAvviata.value = true
    startTimers()
    timerStarted.value = true
    await updateEstimate()
    await loadPaymentMethod()
    successMsg.value = 'QR Code verificato! Veicolo sbloccato. Corsa avviata.'
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante l\'avvio della corsa. Verifica il QR Code e riprova.'
    corsaAvviata.value = false
  } finally {
    startLoading.value = false
  }
}

async function updateEstimate() {
  if (!corsaId.value) return
  try {
    const res = await ridesApi.getEstimate(corsaId.value)
    stima.value = res.data.costo || 0
    veicoloTariffa.value = res.data.tariffa || 0
  } catch (e) {
    console.error('updateEstimate failed:', e)
  }
}

async function endRide() {
  if (!corsaId.value) { error.value = 'Nessuna corsa attiva'; return }
  loading.value = true; error.value = ''
  try {
    await ridesApi.endRide(corsaId.value)
    stopTimers()
    clearPersistedState()
    router.push('/utente')
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante la terminazione'
  } finally {
    loading.value = false
  }
}

async function togglePause() {
  if (!corsaId.value) { error.value = 'Nessuna corsa attiva'; return }

  if (!isPaused.value) {
    const confirmed = confirm('Sei sicuro di voler sospendere la corsa? Verranno applicate tariffe di sospensione.')
    if (!confirmed) return
  }

  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    await ridesApi.pauseRide(corsaId.value)
    isPaused.value = !isPaused.value
    if (isPaused.value) {
      if (timerInterval) {
        clearInterval(timerInterval)
        timerInterval = null
      }
      if (estimateInterval) {
        clearInterval(estimateInterval)
        estimateInterval = null
      }
    } else {
      estimateInterval = setInterval(updateEstimate, ESTIMATE_INTERVAL_MS)
      startTimers(Date.now() - elapsedSeconds.value * 1000)
      await updateEstimate()
    }
    persistState()
    successMsg.value = isPaused.value ? 'Corsa sospesa. Il costo includerà la tariffa di sospensione.' : 'Corsa ripresa'
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore'
  } finally {
    loading.value = false
  }
}

async function selectPayment() {
  router.push('/utente/payments')
}
</script>

<style scoped>
.card p { margin-bottom: 8px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.timer-display {
  display: flex;
  align-items: center;
  gap: 24px;
  margin: 16px 0;
  padding: 16px;
  background: linear-gradient(135deg, var(--bg), #fff);
  border-radius: 12px;
  border: 1px solid var(--light-gray);
}
.timer-display.paused {
  background: linear-gradient(135deg, #fef7e0, #fff);
  border-color: var(--warning);
}
.timer-ring {
  position: relative;
  width: 100px;
  height: 100px;
  flex-shrink: 0;
}
.timer-svg { width: 100%; height: 100%; }
.timer-text {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.timer-value {
  font-size: 18px;
  font-weight: 700;
  font-variant-numeric: tabular-nums;
  color: var(--dark);
}
.timer-label {
  font-size: 10px;
  color: var(--gray);
  text-transform: uppercase;
  letter-spacing: 1px;
}
.paused .timer-value { color: var(--warning); }
.cost-display {
  text-align: center;
}
.cost-value {
  font-size: 28px;
  font-weight: 800;
  color: var(--primary);
  font-variant-numeric: tabular-nums;
}
.cost-label {
  font-size: 10px;
  color: var(--gray);
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-top: 2px;
}
.cost-rate {
  font-size: 12px;
  color: var(--gray);
  margin-top: 4px;
}
</style>
