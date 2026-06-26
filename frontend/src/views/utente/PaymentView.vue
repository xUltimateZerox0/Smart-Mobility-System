<template>
  <div>
    <h1>Metodi di Pagamento</h1>
    <div v-if="metodi.length > 0" class="payment-list">
      <div v-for="m in metodi" :key="m.id" class="card" :class="{ 'selected-method': selectedMethodId === m.id }">
        <p><strong>Carta:</strong> {{ m.numCarta }}</p>
        <p><strong>Intestatario:</strong> {{ m.intestatarioCarta }}</p>
        <p><strong>Scadenza:</strong> {{ m.dsCarta }}</p>
        <div v-if="selectedMethodId === m.id" class="selected-badge">Selezionato per corsa</div>
        <button @click="selectForRide(m.id)" class="btn-primary" style="margin-top:8px" :disabled="useLoading || selectedMethodId === m.id">{{ useLoading ? 'Selezione...' : (selectedMethodId === m.id ? 'Già selezionato' : 'Usa per corsa') }}</button>
      </div>
      <p v-if="useSuccess" class="success-message">{{ useSuccess }}</p>
    </div>
    <p v-if="metodi.length === 0" style="color:var(--gray);margin:12px 0">Nessun metodo salvato</p>
    <div class="card" style="margin-top:16px">
      <h3>Aggiungi carta</h3>
      <div class="form-group">
        <label for="card-number">Numero carta</label>
        <input id="card-number" v-model="numCarta" type="text" />
      </div>
      <div class="form-group">
        <label for="card-holder">Intestatario</label>
        <input id="card-holder" v-model="intestatario" type="text" />
      </div>
      <div class="form-group">
        <label for="card-expiry">Scadenza</label>
        <input id="card-expiry" v-model="scadenza" type="text" placeholder="MM/AA" />
      </div>
      <div class="form-group">
        <label for="card-cvv">CVV</label>
        <input id="card-cvv" v-model="cvv" type="text" />
      </div>
      <button @click="addCard" class="btn-primary" :disabled="saving">{{ saving ? 'Salvataggio...' : 'Salva' }}</button>
      <p v-if="cardError" class="error-message">{{ cardError }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import * as paymentsApi from '../../api/payments'
import * as ridesApi from '../../api/rides'
import type { MetodoPagamentoResponse } from '../../types'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()

const metodi = ref<MetodoPagamentoResponse[]>([])
const numCarta = ref('')
const intestatario = ref('')
const scadenza = ref('')
const cvv = ref('')
const saving = ref(false)
const cardError = ref('')
const useLoading = ref(false)
const useSuccess = ref('')
const selectedMethodId = ref<number | null>(null)

const vehicleId = Number(route.query.vehicleId) || 0
const qrCode = (route.query.qrCode as string) || ''

onMounted(async () => {
  try {
    const res = await paymentsApi.getSavedMethods()
    metodi.value = res.data
  } catch (e) { console.error('getSavedMethods failed:', e) }
})

async function addCard() {
  if (!auth.userId) { cardError.value = 'Utente non autenticato'; return }
  saving.value = true
  cardError.value = ''
  try {
    await paymentsApi.addPaymentMethod({
      idUtente: auth.userId!,
      numCarta: numCarta.value,
      intestatarioCarta: intestatario.value,
      dsCarta: scadenza.value,
      cvv: cvv.value,
    })
    const res = await paymentsApi.getSavedMethods()
    metodi.value = res.data
    numCarta.value = ''
    intestatario.value = ''
    scadenza.value = ''
    cvv.value = ''
  } catch (e: any) {
    cardError.value = e.response?.data?.message || 'Errore'
  } finally {
    saving.value = false
  }
}

async function selectForRide(id: number) {
  useLoading.value = true; cardError.value = ''; useSuccess.value = ''
  try {
    await ridesApi.selectPaymentMethod(id)
    selectedMethodId.value = id
    localStorage.setItem('pending_payment_method_id', String(id))
    if (vehicleId > 0) {
      const qr = qrCode ? `?qrCode=${encodeURIComponent(qrCode)}` : ''
      router.push(`/utente/ride/${vehicleId}${qr}`)
    } else {
      router.replace('/utente/bookings')
    }
  } catch (e: any) {
    cardError.value = e.response?.data?.message || 'Errore nella selezione del metodo'
  } finally {
    useLoading.value = false
  }
}
</script>

<style scoped>
.payment-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.payment-list .card p { margin-bottom: 4px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.success-message { color: var(--success, #28a745); font-size: 13px; margin-top: 8px; }
.selected-method { border: 2px solid var(--success, #28a745) !important; background: #f0fff4; }
.selected-badge {
  display: inline-block;
  background: var(--success, #1a7a30);
  color: white;
  padding: 2px 8px;
  border-radius: 4px;
  font-size: 11px;
  margin-top: 4px;
}
</style>
