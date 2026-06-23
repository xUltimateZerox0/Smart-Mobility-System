<template>
  <div>
    <h1>Corsa in corso</h1>
    <div class="card" style="margin-top:16px">
      <p><strong>Stato:</strong> Corsa attiva</p>
      <p><strong>Costo stimato:</strong> €{{ stima.toFixed(2) }}</p>
      <div style="margin-top:16px;display:flex;gap:8px;flex-wrap:wrap">
        <button @click="endRide" class="btn-danger" :disabled="loading">Termina Corsa</button>
        <button @click="togglePause" class="btn-secondary" :disabled="loading">{{ isPaused ? 'Riprendi' : 'Sospendi' }}</button>
        <button @click="selectPayment" class="btn-primary" :disabled="loading">Metodo Pagamento</button>
      </div>
      <p v-if="error" class="error-message">{{ error }}</p>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import * as ridesApi from '../../api/rides'

const route = useRoute()
const router = useRouter()

const corsaId = Number(route.params.id)
const stima = ref(0)
const isPaused = ref(false)
const loading = ref(false)
const error = ref('')
let interval: ReturnType<typeof setInterval> | null = null

onMounted(() => {
  updateEstimate()
  interval = setInterval(updateEstimate, 30000)
})

onUnmounted(() => {
  if (interval) clearInterval(interval)
})

async function updateEstimate() {
  try {
    const res = await ridesApi.getEstimate(corsaId)
    stima.value = res.data
  } catch {}
}

async function endRide() {
  loading.value = true
  error.value = ''
  try {
    await ridesApi.endRide(corsaId)
    router.push('/utente')
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore'
  } finally {
    loading.value = false
  }
}

async function togglePause() {
  loading.value = true
  error.value = ''
  try {
    await ridesApi.pauseRide(corsaId)
    isPaused.value = !isPaused.value
  } catch (e: any) {
    error.value = e.response?.data?.message || 'Errore'
  } finally {
    loading.value = false
  }
}

async function selectPayment() {
  router.push('/utente/payments')
}
</script>

<style scoped>
.card p { margin-bottom: 8px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
