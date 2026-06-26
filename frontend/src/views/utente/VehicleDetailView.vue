<template>
  <div v-if="veicolo">
    <h1>{{ veicolo.tipo }} - {{ veicolo.codiceMezzo }}</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong> <span class="badge" :class="statusClass(veicolo.stato)">{{ veicolo.stato }}</span></p>
      <p><strong>Tariffa:</strong> {{ veicolo.tariffa }} €/h</p>
      <p><strong>Autonomia:</strong> {{ veicolo.autonomia }} km</p>
      <p><strong>Posizione:</strong> {{ veicolo.latitudine }}, {{ veicolo.longitudine }}</p>
      <p v-if="veicolo.tempoDisponibilita"><strong>Disponibile dalle:</strong> {{ veicolo.tempoDisponibilita }}</p>

      <div style="margin-top:16px;display:flex;gap:8px;flex-wrap:wrap">
        <button v-if="hasActiveBooking" @click="startRide" class="btn-primary" :disabled="rideLoading">
          {{ rideLoading ? 'Avvio...' : 'Avvia Corsa' }}
        </button>
        <button @click="bookVehicle" class="btn-secondary" :disabled="bookingLoading">
          {{ bookingLoading ? 'Prenoto...' : 'Prenota' }}
        </button>
      </div>
      <p v-if="!hasActiveBooking && veicolo" style="font-size:13px;color:var(--gray);margin-top:8px">
        Prenota questo veicolo per poter avviare una corsa
      </p>
      <div style="margin-top:16px">
        <h3>Calcolo Percorso</h3>
        <div class="form-group" style="display:flex;gap:8px;align-items:end">
          <div>
            <label>Destinazione</label>
            <input v-model="destinazione" placeholder="Indirizzo o coordinate" style="min-width:250px" />
          </div>
          <button @click="calculateRoute" class="btn-primary" :disabled="routeLoading">{{ routeLoading ? 'Calcolo...' : 'Calcola Percorso' }}</button>
        </div>
        <div v-if="percorso" class="card percorso-detail" style="margin-top:8px;font-size:13px">
          <p><strong>Partenza:</strong> {{ percorso.coordinatePartenza }}</p>
          <p><strong>Destinazione:</strong> {{ percorso.coordinateDestinazione }}</p>
          <p><strong>Distanza:</strong> {{ percorso.distanzaKm.toFixed(1) }} km</p>
          <p><strong>Durata stimata:</strong> {{ percorso.durataMinuti }} min</p>
          <p v-if="percorso.costoStimato > 0"><strong>Costo stimato:</strong> €{{ percorso.costoStimato.toFixed(2) }}</p>
          <p><strong>Messaggio:</strong> {{ percorso.messaggio }}</p>
        </div>
      </div>
      <p v-if="successMsg" style="color:var(--success);font-size:13px;margin-top:8px">{{ successMsg }}</p>
      <p v-if="rideError" class="error-message">{{ rideError }}</p>
    </div>
  </div>
  <p v-else-if="loading">Caricamento...</p>
  <p v-else style="color:var(--gray)">Veicolo non trovato</p>

  <div v-if="showBookingModal" class="modal-overlay" @click.self="closeModal">
    <div class="modal-content">
      <template v-if="!bookingResult">
        <h2>Prenota {{ veicolo?.tipo }}</h2>
        <div class="form-group">
          <label>Data</label>
          <input type="date" v-model="selectedDate" />
        </div>
        <div class="form-group">
          <label>Ora inizio</label>
          <input type="time" v-model="selectedTime" />
        </div>
        <div class="form-group">
          <label>Durata</label>
          <div class="duration-options">
            <label v-for="h in [1, 2, 4, 8]" :key="h" class="duration-option">
              <input type="radio" name="duration" :value="h" v-model="selectedDuration" />
              {{ h }}h
            </label>
            <label class="duration-option">
              <input type="radio" name="duration" :value="0" v-model="selectedDuration" />
              Personalizzata
            </label>
          </div>
          <div v-if="selectedDuration === 0" style="margin-top:8px">
            <input type="number" v-model.number="customHours" min="0.5" step="0.5" placeholder="Ore" style="width:100px" />
          </div>
        </div>
        <div class="booking-summary">
          <p><strong>Veicolo:</strong> {{ veicolo?.tipo }} ({{ veicolo?.codiceMezzo }})</p>
          <p><strong>Tariffa:</strong> {{ veicolo?.tariffa }} €/h</p>
          <p><strong>Costo stimato:</strong> {{ estimatedCost.toFixed(2) }} €</p>
        </div>
        <div class="modal-actions">
          <button @click="confirmBooking" class="btn-primary" :disabled="confirmLoading">
            {{ confirmLoading ? 'Prenoto...' : 'Conferma Prenotazione' }}
          </button>
          <button @click="closeModal" class="btn-secondary">Annulla</button>
        </div>
        <p v-if="bookingError" class="error-message">{{ bookingError }}</p>
      </template>
      <template v-else>
        <h2>Prenotazione Confermata</h2>
        <div class="qr-box">
          <code>{{ bookingResult.qrCode }}</code>
        </div>
        <div class="modal-actions">
          <button @click="closeModal" class="btn-primary">Chiudi</button>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as vehiclesApi from '../../api/vehicles'
import * as ridesApi from '../../api/rides'
import * as bookingsApi from '../../api/bookings'
import type { MezzoResponse, PrenotazioneResponse, PercorsoResponse } from '../../types'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()

const veicolo = ref<MezzoResponse | null>(null)
const loading = ref(true)
const rideLoading = ref(false)
const bookingLoading = ref(false)
const routeLoading = ref(false)
const rideError = ref('')
const successMsg = ref('')
const destinazione = ref('')
const percorso = ref<PercorsoResponse | null>(null)
const hasActiveBooking = ref(false)

const showBookingModal = ref(false)
const selectedDate = ref('')
const selectedTime = ref('')
const selectedDuration = ref(1)
const customHours = ref(1)
const bookingResult = ref<{ id: number; qrCode: string } | null>(null)
const bookingError = ref('')
const confirmLoading = ref(false)

const effectiveDuration = computed(() => {
  if (selectedDuration.value === 0) return Math.max(customHours.value, 0) || 0
  return selectedDuration.value
})
const estimatedCost = computed(() => (veicolo.value?.tariffa ?? 0) * effectiveDuration.value)

function setDefaultDateTime() {
  const now = new Date()
  now.setMinutes(now.getMinutes() + 15)
  selectedDate.value = now.toISOString().split('T')[0]
  selectedTime.value = now.toTimeString().slice(0, 5)
}

onMounted(async () => {
  if (!auth.userId) { return }
  try {
    const vehicleId = Number(route.params.id)
    if (isNaN(vehicleId)) { loading.value = false; return }

    const [vehicleRes, activeRideRes] = await Promise.all([
      vehiclesApi.getVehicleDetails(vehicleId),
      ridesApi.getActiveRide().catch(() => ({ data: null }))
    ])
    veicolo.value = vehicleRes.data

    if (activeRideRes.data && activeRideRes.data.id) {
      router.push(`/utente/ride/${activeRideRes.data.idMezzo}?corsaId=${activeRideRes.data.id}`)
      return
    }

    const bookingsRes = await bookingsApi.getUserBookings()
    hasActiveBooking.value = bookingsRes.data.some(
      (b: PrenotazioneResponse) =>
        (b.idVeicolo === vehicleId || b.idMezzo === vehicleId) &&
        b.stato === 'attiva'
    )
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

async function calculateRoute() {
  if (!destinazione.value.trim()) { rideError.value = 'Inserisci una destinazione'; return }
  if (!veicolo.value) { rideError.value = 'Veicolo non disponibile'; return }
  routeLoading.value = true; rideError.value = ''; successMsg.value = ''
  try {
    const coord = `${veicolo.value.latitudine},${veicolo.value.longitudine}`
    const res = await ridesApi.calculateRoute(coord, destinazione.value)
    percorso.value = res.data
    successMsg.value = 'Percorso calcolato'
  } catch (e: any) { rideError.value = e.response?.data?.message || 'Errore' }
  finally { routeLoading.value = false }
}

function startRide() {
  if (!veicolo.value) { rideError.value = 'Veicolo non disponibile'; return }
  rideError.value = ''; successMsg.value = ''
  const qr = bookingResult.value?.qrCode ? `?qrCode=${encodeURIComponent(bookingResult.value.qrCode)}` : ''
  router.push(`/utente/ride/${veicolo.value.id}${qr}`)
}

function bookVehicle() {
  rideError.value = ''; successMsg.value = ''
  bookingResult.value = null
  bookingError.value = ''
  setDefaultDateTime()
  showBookingModal.value = true
}

async function confirmBooking() {
  if (!auth.userId || !veicolo.value) { bookingError.value = 'Dati utente o veicolo non disponibili'; return }
  confirmLoading.value = true
  bookingError.value = ''
  try {
    const orarioInizio = `${selectedDate.value}T${selectedTime.value}`
    const res = await bookingsApi.createBooking({
      idMezzo: veicolo.value.id,
      idUtente: auth.userId!,
      orarioInizio
    })
    const bookingId = res.data.id
    const qrCode = res.data.qrCode || `QR-${veicolo.value!.id}-${bookingId}-${Date.now()}`
    bookingResult.value = { id: bookingId, qrCode }
  } catch (e: any) {
    bookingError.value = e.response?.data?.message || 'Errore durante la prenotazione'
  } finally {
    confirmLoading.value = false
  }
}

function closeModal() {
  showBookingModal.value = false
  if (bookingResult.value) {
    hasActiveBooking.value = true
  }
  bookingResult.value = null
  bookingError.value = ''
}
</script>

<style scoped>
.card p { margin-bottom: 8px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.modal-overlay {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}
.modal-content {
  background: var(--card-bg, #fff);
  border-radius: 12px;
  padding: 24px;
  min-width: 360px;
  max-width: 480px;
  box-shadow: 0 8px 32px rgba(0,0,0,0.2);
}
.duration-options {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
  margin-top: 4px;
}
.duration-option {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 14px;
}
.booking-summary {
  background: var(--bg-secondary, #f5f5f5);
  padding: 12px;
  border-radius: 8px;
  margin: 12px 0;
  font-size: 14px;
}
.booking-summary p {
  margin-bottom: 4px;
}
.modal-actions {
  display: flex;
  gap: 8px;
  margin-top: 16px;
}
.qr-box {
  background: var(--bg-secondary, #f0f0f0);
  border: 2px dashed var(--border-color, #ccc);
  border-radius: 8px;
  padding: 20px;
  text-align: center;
  margin: 16px 0;
}
.qr-box code {
  font-family: 'Courier New', Courier, monospace;
  font-size: 16px;
  font-weight: bold;
  letter-spacing: 2px;
  color: var(--primary, #333);
}

.percorso-detail p {
  margin-bottom: 4px;
  font-size: 13px;
}
</style>
