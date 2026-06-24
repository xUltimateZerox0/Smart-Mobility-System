<template>
  <div>
    <h1>Metodi di Pagamento</h1>
    <div v-if="metodi.length > 0" class="payment-list">
      <div v-for="m in metodi" :key="m.id" class="card">
        <p><strong>Carta:</strong> {{ m.numCarta }}</p>
        <p><strong>Intestatario:</strong> {{ m.intestatarioCarta }}</p>
        <p><strong>Scadenza:</strong> {{ m.dsCarta }}</p>
        <button @click="selectForRide(m.id)" class="btn-primary" style="margin-top:8px">Usa per corsa</button>
      </div>
    </div>
    <p v-else style="color:var(--gray);margin:12px 0">Nessun metodo salvato</p>
    <div class="card" style="margin-top:16px">
      <h3>Aggiungi carta</h3>
      <div class="form-group">
        <label>Numero carta</label>
        <input v-model="numCarta" type="text" />
      </div>
      <div class="form-group">
        <label>Intestatario</label>
        <input v-model="intestatario" type="text" />
      </div>
      <div class="form-group">
        <label>Scadenza</label>
        <input v-model="scadenza" type="text" placeholder="MM/AA" />
      </div>
      <div class="form-group">
        <label>CVV</label>
        <input v-model="cvv" type="text" />
      </div>
      <button @click="addCard" class="btn-primary" :disabled="saving">{{ saving ? 'Salvataggio...' : 'Salva' }}</button>
      <p v-if="cardError" class="error-message">{{ cardError }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useAuthStore } from '../../stores/auth'
import * as paymentsApi from '../../api/payments'
import * as ridesApi from '../../api/rides'
import type { MetodoPagamentoResponse } from '../../types'

const auth = useAuthStore()

const metodi = ref<MetodoPagamentoResponse[]>([])
const numCarta = ref('')
const intestatario = ref('')
const scadenza = ref('')
const cvv = ref('')
const saving = ref(false)
const cardError = ref('')

onMounted(async () => {
  try {
    const res = await paymentsApi.getSavedMethods(auth.userId!)
    metodi.value = res.data
  } catch {}
})

async function addCard() {
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
    const res = await paymentsApi.getSavedMethods(auth.userId!)
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
  try {
    await ridesApi.selectPaymentMethod(id)
    cardError.value = ''
  } catch (e: any) {
    cardError.value = e.response?.data?.message || 'Errore'
  }
}
</script>

<style scoped>
.payment-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.payment-list .card p { margin-bottom: 4px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
