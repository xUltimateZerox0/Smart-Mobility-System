<template>
  <div v-if="veicolo">
    <h1>{{ veicolo.tipo }} - {{ veicolo.codiceMezzo }}</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong> <span class="badge" :class="statusClass(veicolo.stato)">{{ veicolo.stato }}</span></p>
      <p><strong>Tariffa:</strong> {{ veicolo.tariffa }} €/h</p>
      <p><strong>Autonomia:</strong> {{ veicolo.autonomia }} km</p>
      <p><strong>Posizione:</strong> {{ veicolo.latitudine }}, {{ veicolo.longitudine }}</p>
      <div style="margin-top:16px;display:flex;gap:8px">
        <button @click="startRide" class="btn-primary" :disabled="rideLoading">
          {{ rideLoading ? 'Avvio...' : 'Avvia Corsa' }}
        </button>
        <button @click="bookVehicle" class="btn-secondary" :disabled="bookingLoading">
          {{ bookingLoading ? 'Prenoto...' : 'Prenota' }}
        </button>
      </div>
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
const rideError = ref('')

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

async function startRide() {
  rideError.value = ''
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
  rideError.value = ''
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
