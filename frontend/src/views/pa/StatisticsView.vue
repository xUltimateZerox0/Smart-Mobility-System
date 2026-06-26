<template>
  <div>
    <h1>Statistiche</h1>
    <div class="card" style="margin-bottom:16px">
      <div class="form-group">
        <label for="stats-data-inizio">Data inizio</label>
        <input id="stats-data-inizio" v-model="dataInizio" type="date" />
      </div>
      <div class="form-group">
        <label for="stats-data-fine">Data fine</label>
        <input id="stats-data-fine" v-model="dataFine" type="date" />
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
        <span>Report generato con successo</span>
      </div>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>

    <div class="card" style="margin-bottom:16px;margin-top:24px">
      <h2>Analisi Stato Flotta</h2>
      <p style="font-size:13px;color:var(--gray);margin-bottom:8px">
        L'analisi automatica verifica lo stato di ogni veicolo e, se necessario, lo pone in manutenzione creando una segnalazione.
      </p>
      <div style="display:flex;gap:8px;flex-wrap:wrap">
        <button @click="loadFleet" class="btn-primary" :disabled="fleetLoading">{{ fleetLoading ? 'Caricamento...' : "Richiedi Stato Flotta" }}</button>
        <button @click="analyzeFleetAction" class="btn-warning" :disabled="analyzeLoading">{{ analyzeLoading ? 'Analisi...' : 'Analisi Stato Flotta' }}</button>
      </div>
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
            <th>ID</th><th>Tipo</th><th>Stato</th><th>Autonomia</th><th>Condizione</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="v in fleetData.veicoli" :key="v.id">
            <td>{{ v.id }}</td>
            <td>{{ v.tipo }}</td>
            <td><span class="badge" :class="badgeClass(v.stato)">{{ v.stato }}</span></td>
            <td>{{ v.autonomia }} km</td>
            <td>{{ v.condizione }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="segnalazioni.length > 0" class="segnalazioni-card" style="margin-top:16px">
      <h3>
        Segnalazioni
        <span class="segnalazioni-count">{{ segnalazioni.length }}</span>
      </h3>
      <table class="segnalazioni-table">
        <thead>
          <tr>
            <th>ID</th><th>Veicolo</th><th>Stato</th><th>Data</th><th>Motivazione</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="s in segnalazioni" :key="s.idSegnalazione">
            <td><span class="segnalazione-id">#{{ s.idSegnalazione }}</span></td>
            <td><span class="vehicle-tag">Mezzo #{{ s.idMezzo }}</span></td>
            <td>
              <span class="status-badge" :class="s.stato === 'aperta' ? 'status-aperta' : 'status-chiusa'">
                <span class="status-dot" :class="s.stato === 'aperta' ? 'dot-aperta' : 'dot-chiusa'"></span>
                {{ s.stato === 'aperta' ? 'Aperta' : 'Chiusa' }}
              </span>
            </td>
            <td>{{ s.data }}</td>
            <td class="motivazione-cell">{{ s.motivazione || '-' }}</td>
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
import { isAxiosError } from 'axios'
import * as statisticsApi from '../../api/statistics'
import { analyzeFleet } from '../../api/fleet'
import type { StatisticheResponse, FleetAnalysisResponse, SegnalazioneResponse } from '../../types'
import * as fleetApi from '../../api/fleet'

const dataInizio = ref('')
const dataFine = ref('')
const stats = ref<StatisticheResponse | null>(null)
const loading = ref(false)
const error = ref('')
const successMsg = ref('')

const fleetData = ref<FleetAnalysisResponse | null>(null)
const fleetLoading = ref(false)
const fleetError = ref('')
const segnalazioni = ref<SegnalazioneResponse[]>([])
const analyzeLoading = ref(false)
const reportGenerated = ref(false)
const reportLoading = ref(false)

async function analyze() {
  loading.value = true; error.value = ''
  try {
    const res = await statisticsApi.analyzeStatistics(dataInizio.value + 'T00:00:00', dataFine.value + 'T23:59:59')
    stats.value = res.data ?? null
  } catch (e: unknown) {
    if (isAxiosError(e)) {
      error.value = e.response?.data?.message || 'Errore'
    } else {
      error.value = 'Errore'
    }
  }
  finally { loading.value = false }
}

async function exportStats() {
  if (!dataInizio.value || !dataFine.value) {
    error.value = 'Seleziona un intervallo di date prima di esportare'
    return
  }
  error.value = ''
  try {
    if (!stats.value) {
      const res = await statisticsApi.analyzeStatistics(dataInizio.value + 'T00:00:00', dataFine.value + 'T23:59:59')
      stats.value = res.data ?? null
      if (!stats.value) { error.value = 'Nessun dato disponibile'; return }
    }
    const s = stats.value!
    const csv = [
      'Metrica,Valore',
      `Corse totali,${s.totalCorse}`,
      `Km totali,${s.totalKm.toFixed(2)}`,
      `Ricavo totale,${s.totalRicavo.toFixed(2)}`,
      `Durata media (min),${s.mediaDurata.toFixed(1)}`,
    ].join('\n')
    const url = URL.createObjectURL(new Blob([csv], { type: 'text/csv;charset=utf-8;' }))
    const a = document.createElement('a')
    a.href = url
    a.download = 'statistiche.csv'
    a.click()
    URL.revokeObjectURL(url)
  } catch (e: unknown) {
    if (isAxiosError(e)) {
      error.value = e.response?.data?.message || 'Errore durante l\'esportazione'
    } else {
      error.value = 'Errore durante l\'esportazione'
    }
  }
}

async function generateReport() {
  if (!dataInizio.value || !dataFine.value) {
    error.value = 'Seleziona un intervallo di date prima di generare il report'
    return
  }
  reportLoading.value = true; error.value = ''; reportGenerated.value = false
  try {
    if (!stats.value) {
      const res = await statisticsApi.analyzeStatistics(dataInizio.value + 'T00:00:00', dataFine.value + 'T23:59:59')
      stats.value = res.data ?? null
      if (!stats.value) { error.value = 'Nessun dato disponibile'; return }
    }
    const reportData = JSON.stringify(stats.value, null, 2)
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
  } catch (e: unknown) {
    if (isAxiosError(e)) {
      error.value = e.response?.data?.message || 'Errore durante la generazione del report'
    } else {
      error.value = 'Errore durante la generazione del report'
    }
  }
  finally { reportLoading.value = false }
}

async function loadFleet() {
  fleetLoading.value = true; fleetError.value = ''
  try {
    const res = await statisticsApi.getFleetAnalysis()
    fleetData.value = res.data ?? null
    const segRes = await fleetApi.getSegnalazioni()
    segnalazioni.value = segRes.data ?? []
  } catch (e: unknown) {
    if (isAxiosError(e)) {
      fleetError.value = e.response?.data?.message || 'Errore caricamento flotta'
    } else {
      fleetError.value = 'Errore caricamento flotta'
    }
  }
  finally { fleetLoading.value = false }
}

async function analyzeFleetAction() {
  analyzeLoading.value = true; fleetError.value = ''
  try {
    const flottaId = 1
    const res = await analyzeFleet(flottaId)
    successMsg.value = res.data ? 'Analisi completata: veicoli messi in manutenzione' : 'Analisi completata: nessun intervento necessario'
    await loadFleet()
  } catch (e: unknown) {
    if (isAxiosError(e)) {
      fleetError.value = e.response?.data?.message || 'Errore durante l\'analisi'
    } else {
      fleetError.value = 'Errore durante l\'analisi'
    }
  }
  finally { analyzeLoading.value = false }
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
.btn-warning { background: #f0ad4e; color: #333; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-size: 13px; }
.btn-warning:hover { background: #ec971f; }

.segnalazioni-card {
  background: linear-gradient(135deg, #f8faff 0%, #ffffff 100%);
  border: 1px solid #e2e8f0;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.06);
}
.segnalazioni-card h3 {
  font-size: 16px;
  color: #1e293b;
  margin-bottom: 16px;
  display: flex;
  align-items: center;
  gap: 10px;
}
.segnalazioni-count {
  background: linear-gradient(135deg, #5a5ad0, #8b5cf6);
  color: white;
  font-size: 12px;
  font-weight: 700;
  padding: 2px 10px;
  border-radius: 12px;
}
.segnalazioni-table {
  width: 100%;
  border-collapse: separate;
  border-spacing: 0;
  font-size: 13px;
}
.segnalazioni-table thead th {
  padding: 10px 12px;
  text-align: left;
  font-weight: 600;
  color: #64748b;
  font-size: 11px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
  border-bottom: 2px solid #e2e8f0;
  background: #f8fafc;
}
.segnalazioni-table thead th:first-child { border-radius: 8px 0 0 0; }
.segnalazioni-table thead th:last-child { border-radius: 0 8px 0 0; }
.segnalazioni-table tbody tr {
  transition: background 0.15s ease;
}
.segnalazioni-table tbody tr:hover {
  background: #f1f5f9;
}
.segnalazioni-table tbody td {
  padding: 10px 12px;
  border-bottom: 1px solid #f1f5f9;
  vertical-align: middle;
}
.segnalazioni-table tbody tr:last-child td {
  border-bottom: none;
}
.segnalazione-id {
  font-family: 'Courier New', monospace;
  font-weight: 600;
  color: #475569;
  font-size: 12px;
}
.vehicle-tag {
  display: inline-block;
  background: #eef2ff;
  color: #4f46e5;
  font-weight: 600;
  font-size: 12px;
  padding: 3px 10px;
  border-radius: 6px;
}
.status-badge {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  border-radius: 20px;
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.3px;
}
.status-aperta {
  background: linear-gradient(135deg, #fef3c7, #fde68a);
  color: #92400e;
  border: 1px solid #fcd34d;
  box-shadow: 0 1px 3px rgba(251, 191, 36, 0.2);
}
.status-chiusa {
  background: linear-gradient(135deg, #d1fae5, #a7f3d0);
  color: #065f46;
  border: 1px solid #6ee7b7;
  box-shadow: 0 1px 3px rgba(16, 185, 129, 0.2);
}
.status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  display: inline-block;
}
.dot-aperta {
  background: #f59e0b;
  animation: segnale-pulse 2s infinite;
}
.dot-chiusa {
  background: #10b981;
}
@keyframes segnale-pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.5; }
}
.motivazione-cell {
  color: #475569;
  max-width: 250px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
