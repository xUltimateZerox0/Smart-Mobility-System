<template>
  <div>
    <h1>Prenotazioni</h1>
    <div v-if="bookings.length > 0" class="booking-list">
      <div v-for="b in bookings" :key="b.id" class="card">
        <p><strong>Veicolo:</strong> {{ b.nomeVeicolo || '#' + b.idMezzo }} <span v-if="b.tipoVeicolo" class="badge badge-info">{{ b.tipoVeicolo }}</span></p>
        <p><strong>Data:</strong> {{ b.dataInizio }}</p>
        <p><strong>Stato:</strong> <span class="badge" :class="statusClass(b.stato)">{{ b.stato }}</span></p>
        <button v-if="b.stato === 'attiva'" @click="cancel(b.id)" class="btn-danger" style="margin-top:8px">Annulla</button>
      </div>
    </div>
    <p v-else style="color:var(--gray)">Nessuna prenotazione</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth'
import * as bookingsApi from '../../api/bookings'
import type { PrenotazioneResponse } from '../../types'

const auth = useAuthStore()
const bookings = ref<PrenotazioneResponse[]>([])

onMounted(async () => {
  try {
    const res = await bookingsApi.getUserBookings(auth.userId!)
    bookings.value = res.data
  } catch {}
})

function statusClass(stato: string) {
  if (stato === 'attiva') return 'badge-success'
  if (stato === 'scaduta') return 'badge-warning'
  return 'badge-danger'
}

async function cancel(id: number) {
  try {
    await bookingsApi.cancelBooking(id)
    bookings.value = bookings.value.filter(b => b.id !== id)
  } catch {}
}
</script>

<style scoped>
.booking-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.booking-list .card p { margin-bottom: 4px; font-size: 14px; }
</style>
