<template>
  <div>
    <h1>Ricerca Veicoli</h1>
    <div v-if="rangeNotification" class="range-notification">
      <span>Ricerca in corso entro <strong>{{ rangeNotification }} km</strong> dal tuo punto di riferimento</span>
    </div>
    <div class="card" style="margin-bottom:20px">
      <div class="form-group">
        <label for="lat">Latitudine</label>
        <input id="lat" v-model="latitudine" type="number" step="any" />
      </div>
      <div class="form-group">
        <label for="lon">Longitudine</label>
        <input id="lon" v-model="longitudine" type="number" step="any" />
      </div>
      <button @click="searchVehicles" class="btn-primary" :disabled="loading">
        {{ loading ? 'Ricerca...' : (expanded ? 'Cerca entro 5 km' : 'Cerca veicoli') }}
      </button>
    </div>

    <div v-if="showExpandPrompt" class="card expand-prompt" style="margin-bottom:16px">
      <p style="margin-bottom:12px;font-size:14px">Nessun veicolo trovato entro 2 km dal punto indicato.</p>
      <p style="margin-bottom:12px;font-size:13px;color:var(--gray)">Vuoi espandere la ricerca a 5 km?</p>
      <div style="display:flex;gap:8px">
        <button @click="expandTo5km" class="btn-primary">Sì, espandi a 5 km</button>
        <button @click="stopSearch" class="btn-secondary">No, ferma la ricerca</button>
      </div>
    </div>

    <div v-if="searched && veicoli.length > 0" class="range-indicator">
      Ricerca completata entro <strong>{{ expanded ? 5 : 2 }} km</strong> - {{ veicoli.length }} veicolo/i trovato/i
    </div>

    <div v-if="veicoli.length > 0" class="vehicle-grid">
      <div v-for="v in veicoli" :key="v.id" class="card">
        <h3>{{ v.tipo }} - {{ v.codiceMezzo }}</h3>
        <p>Stato: <span class="badge" :class="statusClass(v.stato)">{{ v.stato }}</span></p>
        <p>Tariffa: {{ v.tariffa }} €/h</p>
        <router-link :to="`/utente/vehicles/${v.id}`" class="btn-primary" style="display:inline-block;margin-top:8px">Dettagli</router-link>
      </div>
    </div>

    <div v-if="veicoli.length === 0 && searched && !showExpandPrompt && !error" class="empty-result">
      <p>Nessun veicolo trovato entro {{ expanded ? 5 : 2 }} km dal punto indicato.</p>
    </div>

    <div v-if="error" class="error-message" style="margin-top:12px">
      <strong>Errore durante la ricerca:</strong> {{ error }}
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as vehiclesApi from '../../api/vehicles'
import * as ridesApi from '../../api/rides'
import type { MezzoResponse } from '../../types'

const router = useRouter()
const auth = useAuthStore()

onMounted(async () => {
  if (!auth.userId) return
  try {
    const res = await ridesApi.getActiveRide()
    if (res.data && res.data.id && res.data.idMezzo) {
      router.push(`/utente/ride/${res.data.idMezzo}?corsaId=${res.data.id}`)
    }
  } catch (e) { console.error('getActiveRide failed:', e) }
})

const latitudine = ref(45.4642)
const longitudine = ref(9.1900)
const veicoli = ref<MezzoResponse[]>([])
const loading = ref(false)
const searched = ref(false)
const expanded = ref(false)
const showExpandPrompt = ref(false)
const rangeNotification = ref('')
const error = ref('')

function statusClass(stato: string) {
  if (stato === 'disponibile') return 'badge-success'
  if (stato === 'in_uso' || stato === 'prenotato') return 'badge-info'
  return 'badge-warning'
}

async function searchVehicles() {
  const range = expanded.value ? 5 : 2
  rangeNotification.value = String(range)
  loading.value = true
  searched.value = true
  error.value = ''
  try {
    const res = await vehiclesApi.getNearbyVehicles(`${latitudine.value},${longitudine.value}`, range)
    veicoli.value = res.data
    if (veicoli.value.length === 0 && !expanded.value) {
      showExpandPrompt.value = true
    } else {
      showExpandPrompt.value = false
    }
  } catch (e: any) {
    error.value = e.response?.data?.message || e.response?.data?.error || 'Errore durante la ricerca'
    veicoli.value = []
    if (!expanded.value) {
      showExpandPrompt.value = true
    }
  } finally {
    loading.value = false
  }
}

function expandTo5km() {
  expanded.value = true
  showExpandPrompt.value = false
  rangeNotification.value = '5'
  searchVehicles()
}

function stopSearch() {
  showExpandPrompt.value = false
  expanded.value = false
}
</script>

<style scoped>
.vehicle-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 16px; }
.vehicle-grid .card h3 { font-size: 15px; margin-bottom: 6px; }
.vehicle-grid .card p { font-size: 13px; margin-bottom: 4px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.range-notification {
  background: var(--primary);
  color: white;
  padding: 10px 16px;
  border-radius: 8px;
  margin-bottom: 16px;
  font-size: 14px;
  text-align: center;
  animation: fadeIn 0.3s ease-in;
}
@keyframes fadeIn {
  from { opacity: 0; transform: translateY(-10px); }
  to { opacity: 1; transform: translateY(0); }
}
.range-indicator {
  background: var(--bg-secondary, #e8f4fd);
  color: var(--primary);
  padding: 8px 14px;
  border-radius: 6px;
  margin-bottom: 16px;
  font-size: 13px;
  border-left: 3px solid var(--primary);
}
.expand-prompt {
  border: 2px dashed var(--warning, #ffc107);
  background: #fffde7;
  padding: 16px;
  border-radius: 8px;
}
</style>
