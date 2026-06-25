<template>
  <div>
    <h1>Ricerca Veicoli</h1>
    <div class="card" style="margin-bottom:20px">
      <div class="form-group">
        <label for="lat">Latitudine</label>
        <input id="lat" v-model="latitudine" type="number" step="any" />
      </div>
      <div class="form-group">
        <label for="lon">Longitudine</label>
        <input id="lon" v-model="longitudine" type="number" step="any" />
      </div>
      <div class="form-group">
        <label for="raggio">Raggio (km)</label>
        <input id="raggio" v-model="raggio" type="number" />
      </div>
      <button @click="searchVehicles" class="btn-primary" :disabled="loading">
        {{ loading ? 'Ricerca...' : 'Cerca' }}
      </button>
    </div>
    <div v-if="veicoli.length > 0" class="vehicle-grid">
      <div v-for="v in veicoli" :key="v.id" class="card">
        <h3>{{ v.tipo }} - {{ v.codiceMezzo }}</h3>
        <p>Stato: <span class="badge" :class="statusClass(v.stato)">{{ v.stato }}</span></p>
        <p>Tariffa: {{ v.tariffa }} €/h</p>
        <router-link :to="`/utente/vehicles/${v.id}`" class="btn-primary" style="display:inline-block;margin-top:8px">Dettagli</router-link>
      </div>
    </div>
    <p v-if="veicoli.length === 0 && searched" style="color:var(--gray)">Nessun veicolo trovato</p>
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
    if (res.data && res.data.id) {
      router.push(`/utente/ride/${res.data.id}`)
    }
  } catch (e) { console.error('getActiveRide failed:', e) }
})

const latitudine = ref(45.4642)
const longitudine = ref(9.1900)
const raggio = ref(5)
const veicoli = ref<MezzoResponse[]>([])
const loading = ref(false)
const searched = ref(false)

function statusClass(stato: string) {
  if (stato === 'disponibile') return 'badge-success'
  if (stato === 'in_uso' || stato === 'prenotato') return 'badge-info'
  return 'badge-warning'
}

async function searchVehicles() {
  loading.value = true
  searched.value = true
  try {
    const res = await vehiclesApi.getNearbyVehicles(`${latitudine.value},${longitudine.value}`, raggio.value)
    veicoli.value = res.data
  } catch (e) { console.error('searchVehicles failed:', e)
    veicoli.value = []
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.vehicle-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(260px, 1fr)); gap: 16px; }
.vehicle-grid .card h3 { font-size: 15px; margin-bottom: 6px; }
.vehicle-grid .card p { font-size: 13px; margin-bottom: 4px; }
</style>
