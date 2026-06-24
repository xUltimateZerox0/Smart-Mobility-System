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
      <div style="margin-top:12px;display:flex;gap:8px;flex-wrap:wrap">
        <button @click="exportStats" class="btn-secondary">Esporta CSV</button>
        <button @click="generateReport" class="btn-success" :disabled="reportLoading">{{ reportLoading ? 'Generazione...' : 'Genera Report' }}</button>
      </div>
      <div v-if="reportGenerated" class="report-success">
        <span>✅ Report generato con successo</span>
      </div>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>

    <div class="card" style="margin-bottom:16px;margin-top:24px">
      <h2>Analisi Stato Flotta</h2>
      <button @click="loadFleet" class="btn-primary" :disabled="fleetLoading">{{ fleetLoading ? 'Caricamento...' : 'Carica Dati Flotta' }}</button>
    </div>

    <div v-if="fleetData" class="fleet-summary">
      <div class="summary-card"><span class="badge green">Disponibili</span><strong>{{ fleetData.statistiche.disponibile }}</strong></div>
      <div class="summary-card"><span class="badge blue">Prenotati</span><strong>{{ fleetData.statistiche.prenotato }}</strong></div>
      <div class="summary-card"><span class="badge yellow">In Uso</span><strong>{{ fleetData.statistiche.in_uso }}</strong></div>
      <div class="summary-card"><span class="badge red">Manutenzione</span><strong>{{ fleetData.statistiche.manutenzione }}</strong></div>
      <div class="summary-card"><span class="badge gray">Bloccati</span><strong>{{ fleetData.statistiche.bloccato }}</strong></div>
      <div class="summary-card"><span class="badge orange">Sospesi</span><strong>{{ fleetData.statistiche.sospeso }}</strong></div>
    </div>

    <div v-if="fleetData" class="card" style="margin-top:16px;overflow-x:auto">
      <table class="fleet-table">
        <thead>
          <tr>
            <th>ID</th><th>Tipo</th><th>Stato</th><th>Autonomia</th><th>Condizione</th><th>Azioni</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="v in fleetData.veicoli" :key="v.id">
            <td>{{ v.id }}</td>
            <td>{{ v.tipo }}</td>
            <td><span class="badge" :class="badgeClass(v.stato)">{{ v.stato }}</span></td>
            <td>{{ v.autonomia }} km</td>
            <td>{{ v.condizione }}</td>
            <td class="actions">
              <button @click="lockVehicleAction(v.id)" class="btn-small">Lock</button>
              <button @click="unlockVehicleAction(v.id)" class="btn-small">Unlock</button>
              <button @click="maintenanceAction(v.id)" class="btn-small">Manutenzione</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <p v-if="successMsg" class="success-message">{{ successMsg }}</p>
    <p v-if="fleetError" class="error-message">{{ fleetError }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as statisticsApi from '../../api/statistics'
import { lockVehicle, unlockVehicle, startVehicleMaintenance } from '../../api/fleet'
import type { StatisticheResponse, FleetAnalysisResponse } from '../../types'

const dataInizio = ref('')
const dataFine = ref('')
const stats = ref<StatisticheResponse | null>(null)
const loading = ref(false)
const error = ref('')
const successMsg = ref('')

const fleetData = ref<FleetAnalysisResponse | null>(null)
const fleetLoading = ref(false)
const fleetError = ref('')
const reportGenerated = ref(false)
const reportLoading = ref(false)

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
    const res = await statisticsApi.exportStatistics([])
    const url = URL.createObjectURL(new Blob([res.data], { type: 'text/csv' }))
    const a = document.createElement('a')
    a.href = url
    a.download = 'statistiche.csv'
    a.click()
    URL.revokeObjectURL(url)
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
}

async function generateReport() {
  if (!dataInizio.value || !dataFine.value) {
    error.value = 'Seleziona un intervallo di date prima di generare il report'
    return
  }
  reportLoading.value = true; error.value = ''; reportGenerated.value = false
  try {
    const res = await statisticsApi.analyzeStatistics(dataInizio.value + 'T00:00:00', dataFine.value + 'T23:59:59')
    stats.value = res.data
    const reportData = JSON.stringify(res.data, null, 2)
    const blob = new Blob([reportData], { type: 'application/json' })
    const url = URL.createObjectURL(blob)
    const a = document.createElement('a')
    a.href = url
    const now = new Date()
    a.download = `report-statistiche-${now.toISOString().slice(0, 10)}.json`
    a.click()
    URL.revokeObjectURL(url)
    reportGenerated.value = true
    successMsg.value = 'Report generato e scaricato con successo'
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore durante la generazione del report' }
  finally { reportLoading.value = false }
}

async function loadFleet() {
  fleetLoading.value = true; fleetError.value = ''
  try {
    const res = await statisticsApi.getFleetAnalysis()
    fleetData.value = res.data
  } catch (e: any) { fleetError.value = e.response?.data?.message || 'Errore caricamento flotta' }
  finally { fleetLoading.value = false }
}

async function lockVehicleAction(id: number) {
  try { await lockVehicle(id); await loadFleet() }
  catch (e: any) { fleetError.value = e.response?.data?.message || 'Errore' }
}

async function unlockVehicleAction(id: number) {
  try { await unlockVehicle(id); await loadFleet() }
  catch (e: any) { fleetError.value = e.response?.data?.message || 'Errore' }
}

async function maintenanceAction(id: number) {
  try { await startVehicleMaintenance(id); await loadFleet() }
  catch (e: any) { fleetError.value = e.response?.data?.message || 'Errore' }
}

function badgeClass(stato: string): string {
  const map: Record<string, string> = {
    disponibile: 'green',
    prenotato: 'blue',
    in_uso: 'yellow',
    manutenzione: 'red',
    bloccato: 'gray',
    sospeso: 'orange',
  }
  return map[stato] || 'gray'
}
</script>

<style scoped>
.card p { margin-bottom: 6px; font-size: 14px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.success-message { color: var(--secondary); font-size: 13px; margin-top: 8px; }
.report-success {
  margin-top: 10px;
  padding: 8px 12px;
  background: var(--secondary-light);
  border-radius: var(--radius-sm);
  font-size: 13px;
  color: #065f46;
}
.fleet-summary { display: flex; flex-wrap: wrap; gap: 12px; margin-bottom: 16px; }
.summary-card { display: flex; align-items: center; gap: 8px; padding: 12px 16px; border-radius: 8px; background: var(--bg-secondary, #f5f5f5); min-width: 140px; }
.badge { display: inline-block; padding: 2px 10px; border-radius: 12px; font-size: 12px; font-weight: 600; color: #fff; }
.badge.green { background: #28a745; }
.badge.blue { background: #007bff; }
.badge.yellow { background: #ffc107; color: #333; }
.badge.red { background: #dc3545; }
.badge.gray { background: #6c757d; }
.badge.orange { background: #fd7e14; }
.fleet-table { width: 100%; border-collapse: collapse; font-size: 13px; }
.fleet-table th, .fleet-table td { padding: 8px 12px; text-align: left; border-bottom: 1px solid var(--border, #e0e0e0); }
.fleet-table th { font-weight: 600; color: var(--gray); }
.actions { display: flex; gap: 6px; }
.btn-small { padding: 4px 10px; font-size: 12px; border: 1px solid var(--border, #ddd); border-radius: 4px; background: var(--bg-secondary, #f9f9f9); cursor: pointer; }
.btn-small:hover { background: var(--bg-hover, #e9e9e9); }
</style>
