<template>
  <div>
    <h1>Prenotazioni</h1>
    <div v-if="bookings.length > 0" class="booking-list">
      <div v-for="b in bookings" :key="b.id" class="card">
        <p><strong>Veicolo:</strong> {{ b.nomeVeicolo || '#' + b.idMezzo }} <span v-if="b.tipoVeicolo" class="badge badge-info">{{ b.tipoVeicolo }}</span></p>
        <p><strong>Data:</strong> {{ b.dataInizio }}</p>
        <p v-if="b.orarioInizio"><strong>Orario inizio:</strong> {{ b.orarioInizio }}</p>
        <p><strong>Ora:</strong> {{ formatTime(b.dataInizio) }}</p>
        <p><strong>Stato:</strong> <span class="badge" :class="statusClass(b.stato)">{{ b.stato }}</span></p>
        <div v-if="b.stato === 'attiva' && b.qrCode" class="qr-box">
          <code>{{ b.qrCode }}</code>
        </div>
        <div v-if="b.stato === 'attiva'" style="margin-top:8px;display:flex;gap:8px">
          <button @click="startRide(b)" class="btn-primary">Avvia Corsa</button>
          <button @click="cancel(b.id)" class="btn-danger">Annulla</button>
        </div>
      </div>
    </div>
    <p v-else-if="!loadError" style="color:var(--gray)">Nessuna prenotazione</p>
    <p v-if="loadError" class="error-message">{{ loadError }}</p>
    <p v-if="cancelError" class="error-message">{{ cancelError }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as bookingsApi from '../../api/bookings'
import type { PrenotazioneResponse } from '../../types'

import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'

const router = useRouter()
const auth = useAuthStore()

const bookings = ref<PrenotazioneResponse[]>([])
const loadError = ref('')
const cancelError = ref('')

onMounted(async () => {
  try {
    const res = await bookingsApi.getUserBookings()
    bookings.value = res.data
  } catch (e: any) {
    loadError.value = e.response?.data?.message || 'Errore nel caricamento delle prenotazioni'
    console.error('getUserBookings failed:', e)
  }
})

function statusClass(stato: string) {
  if (stato === 'attiva') return 'badge-success'
  if (stato === 'scaduta') return 'badge-warning'
  return 'badge-danger'
}

function formatTime(dateStr: string) {
  if (!dateStr) return ''
  const d = new Date(dateStr)
  return d.toLocaleTimeString('it-IT', { hour: '2-digit', minute: '2-digit' })
}

function startRide(b: PrenotazioneResponse) {
  if (!auth.userId || !b.qrCode) return
  const qr = encodeURIComponent(b.qrCode)
  router.push(`/utente/ride/${b.idMezzo}?qrCode=${qr}`)
}

async function cancel(id: number) {
  cancelError.value = ''
  try {
    await bookingsApi.cancelBooking(id)
    bookings.value = bookings.value.filter(b => b.id !== id)
  } catch (e: any) {
    console.error('cancelBooking failed:', e)
    cancelError.value = e.response?.data?.message || 'Errore durante l\'annullamento'
  }
}
</script>

<style scoped>
.booking-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.booking-list .card p { margin-bottom: 4px; font-size: 14px; }
.qr-box {
  background: var(--bg-secondary, #f0f0f0);
  border: 2px dashed var(--border-color, #ccc);
  border-radius: 8px;
  padding: 12px 16px;
  text-align: center;
  margin: 8px 0;
}
.qr-box code {
  font-family: 'Courier New', Courier, monospace;
  font-size: 14px;
  font-weight: bold;
  letter-spacing: 1px;
  color: var(--primary, #333);
}
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
