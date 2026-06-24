<template>
  <div v-if="veicolo">
    <h1>{{ veicolo.tipo }} - {{ veicolo.codiceMezzo }}</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong> <span class="badge" :class="statusClass(veicolo.stato)">{{ veicolo.stato }}</span></p>
      <p><strong>Tariffa:</strong> {{ veicolo.tariffa }} €/h</p>
      <p><strong>Autonomia:</strong> {{ veicolo.autonomia }} km</p>
      <p><strong>Posizione:</strong> {{ veicolo.latitudine }}, {{ veicolo.longitudine }}</p>
      <p v-if="disponibile !== null"><strong>Disponibile:</strong> {{ disponibile ? 'Sì' : 'No' }}</p>
      <div style="margin-top:16px;display:flex;gap:8px;flex-wrap:wrap">
        <button @click="startRide" class="btn-primary" :disabled="rideLoading">
          {{ rideLoading ? 'Avvio...' : 'Avvia Corsa' }}
        </button>
        <button @click="bookVehicle" class="btn-secondary" :disabled="bookingLoading">
          {{ bookingLoading ? 'Prenoto...' : 'Prenota' }}
        </button>
        <button @click="checkAvailability" class="btn-secondary" :disabled="checkLoading">
          {{ checkLoading ? 'Verifico...' : 'Verifica Disponibilità' }}
        </button>
      </div>
      <div style="margin-top:16px">
        <h3>Calcolo Percorso</h3>
        <div class="form-group" style="display:flex;gap:8px;align-items:end">
          <div>
            <label>Destinazione</label>
            <input v-model="destinazione" placeholder="Indirizzo o coordinate" style="min-width:250px" />
          </div>
          <button @click="calculateRoute" class="btn-primary" :disabled="routeLoading">{{ routeLoading ? 'Calcolo...' : 'Calcola Percorso' }}</button>
        </div>
        <div v-if="percorso" class="card" style="margin-top:8px;font-size:13px">
          <p><strong>Distanza:</strong> {{ percorso.distanza }}</p>
          <p><strong>Durata:</strong> {{ percorso.durata }}</p>
          <p><strong>Messaggio:</strong> {{ percorso.messaggio }}</p>
        </div>
      </div>
      <p v-if="successMsg" style="color:var(--success);font-size:13px;margin-top:8px">{{ successMsg }}</p>
      <p v-if="rideError" class="error-message">{{ rideError }}</p>
    </div>
  </div>
  <p v-else-if="loading">Caricamento...</p>
  <p v-else style="color:var(--gray)">Veicolo non trovato</p>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as vehiclesApi from '../../api/vehicles'
import * as ridesApi from '../../api/rides'
import * as bookingsApi from '../../api/bookings'
import type { MezzoResponse } from '../../types'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const veicolo = ref<MezzoResponse | null>(null)
const loading = ref(true)
const rideLoading = ref(false)
const bookingLoading = ref(false)
const checkLoading = ref(false)
const routeLoading = ref(false)
const rideError = ref('')
const successMsg = ref('')
const disponibile = ref<boolean | null>(null)
const destinazione = ref('')
const percorso = ref<any>(null)

onMounted(async () => {
  try {
    const res = await vehiclesApi.getVehicleDetails(Number(route.params.id))
    veicolo.value = res.data
  } catch {
    veicolo.value = null
  } finally {
    loading.value = false
  }
})

function statusClass(stato: string) {
  if (stato === 'disponibile') return 'badge-success'
  if (stato === 'in_uso' || stato === 'prenotato') return 'badge-info'
  return 'badge-warning'
}

async function checkAvailability() {
  checkLoading.value = true; rideError.value = ''; successMsg.value = ''
  try {
    const res = await ridesApi.checkAvailability(veicolo.value!.id)
    disponibile.value = res.data
    successMsg.value = res.data ? 'Il mezzo è disponibile' : 'Il mezzo non è disponibile'
  } catch (e: any) { rideError.value = e.response?.data?.message || 'Errore' }
  finally { checkLoading.value = false }
}

async function calculateRoute() {
  if (!destinazione.value.trim()) { rideError.value = 'Inserisci una destinazione'; return }
  routeLoading.value = true; rideError.value = ''; successMsg.value = ''
  try {
    const coord = `${veicolo.value!.latitudine},${veicolo.value!.longitudine}`
    const res = await ridesApi.calculateRoute(coord, destinazione.value)
    percorso.value = res.data
    successMsg.value = 'Percorso calcolato'
  } catch (e: any) { rideError.value = e.response?.data?.message || 'Errore' }
  finally { routeLoading.value = false }
}

async function startRide() {
  rideError.value = ''; successMsg.value = ''
  rideLoading.value = true
  try {
    await ridesApi.startRide(veicolo.value!.id, auth.userId!)
    router.push(`/utente/ride/${veicolo.value!.id}`)
  } catch (e: any) {
    rideError.value = e.response?.data?.message || 'Errore'
  } finally {
    rideLoading.value = false
  }
}

async function bookVehicle() {
  rideError.value = ''; successMsg.value = ''
  bookingLoading.value = true
  try {
    await bookingsApi.createBooking(veicolo.value!.id, auth.userId!)
    router.push('/utente/bookings')
  } catch (e: any) {
    rideError.value = e.response?.data?.message || 'Errore'
  } finally {
    bookingLoading.value = false
  }
}
</script>

<style scoped>
.card p { margin-bottom: 8px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
