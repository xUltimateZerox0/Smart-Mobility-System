<template>
  <div>
    <h1>Dashboard Utente</h1>
    <p style="color:var(--gray);margin-bottom:24px">Benvenuto, {{ auth.userEmail }}</p>
    <div class="dashboard-grid">
      <div class="card">
        <h3>Veicoli nelle vicinanze</h3>
        <p>Cerca veicoli disponibili vicino a te</p>
        <router-link to="/utente/vehicles" class="btn-primary" style="display:inline-block;margin-top:12px">Cerca</router-link>
      </div>
      <div class="card">
        <h3>Prenotazioni</h3>
        <p>Gestisci le tue prenotazioni attive</p>
        <router-link to="/utente/bookings" class="btn-primary" style="display:inline-block;margin-top:12px">Vedi</router-link>
      </div>
      <div class="card">
        <h3>Pagamenti</h3>
        <p>Gestisci i tuoi metodi di pagamento</p>
        <router-link to="/utente/payments" class="btn-primary" style="display:inline-block;margin-top:12px">Gestisci</router-link>
      </div>
    </div>

    <h2 style="margin-top:32px;margin-bottom:16px">Prenotazioni Attive</h2>
    <div v-if="loadingBookings" class="loading">Caricamento prenotazioni...</div>
    <div v-else-if="activeBookings.length === 0" class="card no-bookings">
      <p>Nessuna prenotazione attiva</p>
    </div>
    <div v-else class="bookings-grid">
      <div v-for="b in activeBookings" :key="b.id" class="booking-card card">
        <h3>{{ b.nomeVeicolo || 'Veicolo #' + b.idMezzo }}</h3>
        <p><strong>Data:</strong> {{ formatDate(b.dataInizio) }}</p>
        <p><strong>Ora:</strong> {{ formatTime(b.dataInizio) }} - {{ formatTime(b.dataFine) }}</p>
        <div v-if="b.qrCode" class="qr-section">
          <code class="qr-text">{{ b.qrCode }}</code>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as bookingsApi from '../../api/bookings'
import * as ridesApi from '../../api/rides'
import type { PrenotazioneResponse } from '../../types'

const router = useRouter()
const auth = useAuthStore()

const activeBookings = ref<PrenotazioneResponse[]>([])
const loadingBookings = ref(false)

onMounted(async () => {
  if (!auth.userId) { return }

  try {
    const activeRideRes = await ridesApi.getActiveRide()
    if (activeRideRes.data && activeRideRes.data.id) {
      router.push(`/utente/ride/${activeRideRes.data.id}`)
      return
    }
  } catch (e) { console.error('getActiveRide failed:', e) }

  loadingBookings.value = true
  try {
    const res = await bookingsApi.getUserBookings()
    activeBookings.value = res.data.filter(b => b.stato === 'attiva')
  } catch (e) { console.error('getUserBookings failed:', e); activeBookings.value = [] }
  finally { loadingBookings.value = false }
})

function formatDate(iso: string) {
  return new Date(iso).toLocaleDateString('it-IT')
}
function formatTime(iso: string) {
  return new Date(iso).toLocaleTimeString('it-IT', { hour: '2-digit', minute: '2-digit' })
}
</script>

<style scoped>
.dashboard-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
.dashboard-grid .card h3 { margin-bottom: 8px; font-size: 16px; }
.dashboard-grid .card p { font-size: 13px; color: var(--gray); }
.bookings-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
.booking-card h3 { margin-bottom: 8px; font-size: 15px; }
.booking-card p { font-size: 13px; color: var(--gray); margin-bottom: 4px; }
.no-bookings p { text-align: center; color: var(--gray); padding: 24px 0; }
.loading { font-size: 13px; color: var(--gray); }
.qr-section { margin-top: 8px; }
.qr-text { font-family: 'Courier New', monospace; font-size: 13px; font-weight: bold; color: var(--primary); padding: 8px; background: #f0f0f0; border-radius: 4px; display: inline-block; }
</style>
