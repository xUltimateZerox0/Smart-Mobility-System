<template>
  <div>
    <h1>Corsa in corso</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong>
        <span class="badge" :class="isPaused ? 'badge-warning' : 'badge-success'">{{ isPaused ? 'Sospesa' : 'Corsa attiva' }}</span>
      </p>
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
      <div style="margin-top:12px" v-if="!corsaAvviata">
        <p style="font-size:13px;color:var(--gray);margin-bottom:8px">{{ startLoading ? 'Sblocco veicolo in corso...' : 'Sblocca il veicolo per iniziare la corsa' }}</p>
        <button @click="startRideFlow" class="btn-primary" :disabled="startLoading">{{ startLoading ? 'Sblocco...' : 'Avvia Corsa e Sblocca' }}</button>
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

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const vehicleId = Number(route.params.id)
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
let estimateInterval: ReturnType<typeof setInterval> | null = null
let timerInterval: ReturnType<typeof setInterval> | null = null
let startTime: number = 0

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

onMounted(async () => {
  await loadVehicleTariffa()
})

onUnmounted(() => {
  stopTimers()
})

function startTimers() {
  startTime = Date.now()
  timerInterval = setInterval(() => {
    if (!isPaused.value) {
      elapsedSeconds.value = Math.floor((Date.now() - startTime) / 1000)
    }
  }, 1000)
  estimateInterval = setInterval(updateEstimate, 30000)
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
  } catch {}
}

async function startRideFlow() {
  if (!vehicleId) { error.value = 'ID veicolo non valido'; return }
  startLoading.value = true; error.value = ''; successMsg.value = ''
  try {
    const res = await ridesApi.startRide(vehicleId, auth.userId!)
    corsaId.value = res.data
    corsaAvviata.value = true
    await ridesApi.unlockVehicle(vehicleId.toString())
    startTimers()
    timerStarted.value = true
    await updateEstimate()
    successMsg.value = 'Veicolo sbloccato! Corsa avviata.'
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore durante l\'avvio della corsa'
  } finally {
    startLoading.value = false
  }
}

async function updateEstimate() {
  if (!corsaId.value) return
  try {
    const res = await ridesApi.getEstimate(corsaId.value)
    if (typeof res.data === 'object' && res.data !== null) {
      stima.value = res.data.costo || 0
      veicoloTariffa.value = res.data.tariffa || 0
    } else {
      stima.value = res.data || 0
    }
  } catch {}
}

async function endRide() {
  if (!corsaId.value) { error.value = 'Nessuna corsa attiva'; return }
  loading.value = true; error.value = ''
  try {
    await ridesApi.endRide(corsaId.value)
    stopTimers()
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
      if (timerInterval) clearInterval(timerInterval)
    } else {
      startTime = Date.now() - elapsedSeconds.value * 1000
      timerInterval = setInterval(() => {
        elapsedSeconds.value = Math.floor((Date.now() - startTime) / 1000)
      }, 1000)
      await updateEstimate()
    }
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
