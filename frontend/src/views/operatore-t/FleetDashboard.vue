<template>
  <div>
    <h1>Gestione Flotta</h1>
    <p style="color:var(--gray);margin-bottom:16px">Monitoraggio e gestione dei veicoli</p>
    <div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">
      <button @click="loadConditions" class="btn-primary" :disabled="loading">{{ loading ? 'Caricamento...' : 'Stato Flotta' }}</button>
    </div>
    <div class="form-group" style="display:flex;gap:8px;align-items:end;margin-bottom:16px">
      <div>
        <label>ID Flotta</label>
        <input v-model.number="fleetId" type="number" placeholder="ID flotta" />
      </div>
      <div>
        <label>ID Veicolo</label>
        <input v-model.number="lockVehicleId" type="number" placeholder="ID veicolo" />
      </div>
      <button @click="lockVehicle" class="btn-danger" :disabled="lockLoading">{{ lockLoading ? 'Blocco...' : 'Blocca Mezzo' }}</button>
      <button @click="unlockVehicle" class="btn-secondary" :disabled="unlockLoading">{{ unlockLoading ? 'Sblocco...' : 'Sblocca Mezzo' }}</button>
    </div>
    <div v-if="conditions.length > 0" class="card" style="margin-bottom:12px">
      <h3>Condizioni Mezzi</h3>
      <div v-for="m in conditions" :key="m.id" style="font-size:13px;padding:8px 0;border-bottom:1px solid var(--border)">
        <div style="display:flex;justify-content:space-between;align-items:center;flex-wrap:wrap;gap:8px">
          <span>ID {{ m.id }} - {{ m.tipo }} - {{ m.stato }} - Autonomia: {{ m.autonomia }}km</span>
          <div style="display:flex;gap:4px;flex-wrap:wrap">
            <button @click="lockSpecificVehicle(m.id)" class="btn-danger btn-sm">Blocca</button>
            <button @click="unlockSpecificVehicle(m.id)" class="btn-secondary btn-sm">Sblocca</button>
          </div>
        </div>
      </div>
    </div>
    <div v-if="fleetData" class="card">
      <pre style="font-size:13px;white-space:pre-wrap">{{ JSON.stringify(fleetData, null, 2) }}</pre>
    </div>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import * as fleetApi from '../../api/fleet'
import type { FleetAnalysisResponse, MezzoResponse } from '../../types'

const fleetData = ref<FleetAnalysisResponse | { message: string } | null>(null)
const conditions = ref<MezzoResponse[]>([])
// TODO: fleetId should come from user profile when multi-fleet support is active
const fleetId = ref(1)
const loading = ref(false)
const lockLoading = ref(false)
const unlockLoading = ref(false)
const lockVehicleId = ref(1)
const error = ref('')

async function loadConditions() {
  loading.value = true; error.value = ''
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

async function lockVehicle() {
  lockLoading.value = true; error.value = ''
  try { await fleetApi.lockVehicle(lockVehicleId.value); fleetData.value = { message: `Mezzo ${lockVehicleId.value} bloccato` } } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
  finally { lockLoading.value = false }
}

async function unlockVehicle() {
  unlockLoading.value = true; error.value = ''
  try { await fleetApi.unlockVehicle(lockVehicleId.value); fleetData.value = { message: `Mezzo ${lockVehicleId.value} sbloccato` } } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
  finally { unlockLoading.value = false }
}

async function lockSpecificVehicle(id: number) {
  error.value = ''
  try { await fleetApi.lockVehicle(id); fleetData.value = { message: `Mezzo ${id} bloccato` }; await loadConditions() } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
}

async function unlockSpecificVehicle(id: number) {
  error.value = ''
  try { await fleetApi.unlockVehicle(id); fleetData.value = { message: `Mezzo ${id} sbloccato` }; await loadConditions() } catch (e: unknown) {
    if (e && typeof e === 'object' && 'response' in e) {
      const axiosErr = e as { response: { data?: { message?: string } } }
      error.value = axiosErr.response.data?.message || 'Errore'
    } else { error.value = 'Errore sconosciuto' }
  }
}

</script>

<style scoped>
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
.btn-warning { background: #f0ad4e; color: #fff; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; font-size: 13px; }
.btn-warning:hover { background: #ec971f; }
.btn-sm { padding: 4px 8px; font-size: 12px; }
</style>
