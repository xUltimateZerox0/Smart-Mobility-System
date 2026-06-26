<template>
  <div>
    <h1>Gestione Flotta</h1>
    <p style="color:var(--gray);margin-bottom:16px">Monitoraggio e gestione dei veicoli</p>
    <div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">
      <button @click="loadConditions" class="btn-primary" :disabled="loading">{{ loading ? 'Caricamento...' : 'Richiedi Stato Flotta' }}</button>
    </div>

    <div v-if="conditions.length > 0" class="card" style="margin-bottom:12px">
      <h3>Condizioni Mezzi - Flotta {{ fleetId }}</h3>
      <table class="fleet-table">
        <thead>
          <tr>
            <th>ID</th><th>Tipo</th><th>Stato</th><th>Autonomia</th><th>Condizione</th><th>Azioni</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="m in conditions" :key="m.id">
            <td>{{ m.id }}</td>
            <td>{{ m.tipo }}</td>
            <td><span class="badge" :class="statusBadge(m.stato)">{{ m.stato }}</span></td>
            <td>{{ m.autonomia }} km</td>
            <td>{{ m.condizione || '-' }}</td>
            <td class="actions">
              <button v-if="m.stato !== 'bloccato'" @click="lockSpecificVehicle(m.id)" class="btn-small btn-danger">Blocca</button>
              <button v-if="m.stato === 'bloccato'" @click="unlockSpecificVehicle(m.id)" class="btn-small btn-secondary">Sblocca</button>

            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <div v-if="error" class="error-message">{{ error }}</div>
    <div v-if="successMsg" class="success-message">{{ successMsg }}</div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as fleetApi from '../../api/fleet'
import type { MezzoResponse } from '../../types'

const conditions = ref<MezzoResponse[]>([])
const fleetId = ref(1)
const loading = ref(false)
const error = ref('')
const successMsg = ref('')

async function loadConditions() {
  loading.value = true; error.value = ''; successMsg.value = ''
  try {
    const res = await fleetApi.getVehicleConditions(fleetId.value)
    conditions.value = res.data
  } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else {
      error.value = 'Errore sconosciuto'
    }
  }
  finally { loading.value = false }
}

async function lockSpecificVehicle(id: number) {
  error.value = ''; successMsg.value = ''
  try {
    await fleetApi.lockVehicle(id)
    successMsg.value = `Veicolo ${id} bloccato con successo`
    await loadConditions()
  } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
}

async function unlockSpecificVehicle(id: number) {
  error.value = ''; successMsg.value = ''
  try {
    await fleetApi.unlockVehicle(id)
    successMsg.value = `Veicolo ${id} sbloccato con successo`
    await loadConditions()
  } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
}

function statusBadge(stato: string): string {
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
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.success-message { color: var(--success, #28a745); font-size: 13px; margin-top: 8px; }
.btn-warning { background: #f0ad4e; color: #fff; border: none; padding: 4px 8px; border-radius: 4px; cursor: pointer; font-size: 12px; }
.btn-warning:hover { background: #ec971f; }
.btn-small { padding: 4px 8px; font-size: 12px; border-radius: 4px; cursor: pointer; border: none; }
.btn-danger { background: #dc3545; color: #fff; }
.btn-danger:hover { background: #c82333; }
.btn-secondary { background: #6c757d; color: #fff; }
.btn-secondary:hover { background: #5a6268; }
.badge { display: inline-block; padding: 2px 8px; border-radius: 10px; font-size: 11px; font-weight: 600; color: #fff; }
.badge.green { background: #28a745; }
.badge.blue { background: #007bff; }
.badge.yellow { background: #ffc107; color: #333; }
.badge.red { background: #dc3545; }
.badge.gray { background: #6c757d; }
.badge.orange { background: #fd7e14; }
.actions { display: flex; gap: 4px; }
.fleet-table { width: 100%; border-collapse: collapse; font-size: 13px; }
.fleet-table th, .fleet-table td { padding: 8px 10px; text-align: left; border-bottom: 1px solid var(--border, #e0e0e0); }
.fleet-table th { font-weight: 600; color: var(--gray); }
</style>
