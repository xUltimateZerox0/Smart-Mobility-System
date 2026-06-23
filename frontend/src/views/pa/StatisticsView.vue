<template>
  <div>
    <h1>Statistiche</h1>
    <div class="card" style="margin-bottom:16px">
      <div class="form-group">
        <label>Data inizio</label>
        <input v-model="dataInizio" type="date" />
      </div>
      <div class="form-group">
        <label>Data fine</label>
        <input v-model="dataFine" type="date" />
      </div>
      <button @click="analyze" class="btn-primary" :disabled="loading">{{ loading ? 'Analisi...' : 'Analizza' }}</button>
    </div>
    <div v-if="stats" class="card">
      <p><strong>Corse totali:</strong> {{ stats.totalCorse }}</p>
      <p><strong>Km totali:</strong> {{ stats.totalKm.toFixed(2) }}</p>
      <p><strong>Ricavo totale:</strong> €{{ stats.totalRicavo.toFixed(2) }}</p>
      <p><strong>Durata media:</strong> {{ stats.mediaDurata.toFixed(1) }} min</p>
      <button @click="exportStats" class="btn-secondary" style="margin-top:12px">Esporta CSV</button>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as statisticsApi from '../../api/statistics'
import type { StatisticheResponse } from '../../types'

const dataInizio = ref('')
const dataFine = ref('')
const stats = ref<StatisticheResponse | null>(null)
const loading = ref(false)
const error = ref('')

async function analyze() {
  loading.value = true; error.value = ''
  try {
    const res = await statisticsApi.analyzeStatistics(dataInizio.value + 'T00:00:00', dataFine.value + 'T23:59:59')
    stats.value = res.data
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}

async function exportStats() {
  error.value = ''
  try {
    await statisticsApi.exportStatistics([])
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
}
</script>

<style scoped>
.card p { margin-bottom: 6px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
