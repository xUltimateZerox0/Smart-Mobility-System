<template>
  <div>
    <h1>Gestione Flotta</h1>
    <p style="color:var(--gray);margin-bottom:16px">Monitoraggio e gestione dei veicoli</p>
    <div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">
      <button @click="loadConditions" class="btn-primary" :disabled="loading">{{ loading ? 'Caricamento...' : 'Stato Flotta' }}</button>
      <button @click="analyzeFleet" class="btn-primary" :disabled="analyzing">{{ analyzing ? 'Analisi...' : 'Analisi Flotta' }}</button>
      <button @click="maintenanceMode" class="btn-secondary" :disabled="maintLoading">{{ maintLoading ? 'Impostazione...' : 'Manutenzione' }}</button>
    </div>
    <div class="form-group" style="display:flex;gap:8px;align-items:end;margin-bottom:16px">
      <div>
        <label>ID Veicolo da bloccare</label>
        <input v-model.number="lockVehicleId" type="number" placeholder="ID veicolo" />
      </div>
      <button @click="lockVehicle" class="btn-danger" :disabled="lockLoading">{{ lockLoading ? 'Blocco...' : 'Blocca Mezzo' }}</button>
    </div>
    <div v-if="conditions.length > 0" class="card" style="margin-bottom:12px">
      <h3>Condizioni Mezzi</h3>
      <div v-for="m in conditions" :key="m.id" style="font-size:13px;padding:4px 0;border-bottom:1px solid var(--border)">
        ID {{ m.id }} - {{ m.tipo }} - {{ m.stato }} - Autonomia: {{ m.autonomia }}km
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

const fleetData = ref<any>(null)
const conditions = ref<any[]>([])
const fleetId = 1
const loading = ref(false)
const analyzing = ref(false)
const maintLoading = ref(false)
const lockLoading = ref(false)
const lockVehicleId = ref(1)
const error = ref('')

async function loadConditions() {
  loading.value = true; error.value = ''
  try {
    const res = await fleetApi.getVehicleConditions(fleetId)
    conditions.value = res.data
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { loading.value = false }
}

async function analyzeFleet() {
  analyzing.value = true; error.value = ''
  try { const res = await fleetApi.analyzeFleet(fleetId); fleetData.value = res.data } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { analyzing.value = false }
}

async function maintenanceMode() {
  maintLoading.value = true; error.value = ''
  try { await fleetApi.startMaintenance(fleetId); fleetData.value = { message: 'Manutenzione impostata' } } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { maintLoading.value = false }
}

async function lockVehicle() {
  lockLoading.value = true; error.value = ''
  try { await fleetApi.lockVehicle(lockVehicleId.value); fleetData.value = { message: `Mezzo ${lockVehicleId.value} bloccato` } } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { lockLoading.value = false }
}
</script>

<style scoped>
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
