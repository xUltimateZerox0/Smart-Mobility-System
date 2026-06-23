<template>
  <div>
    <h1>Gestione Flotta</h1>
    <p style="color:var(--gray);margin-bottom:16px">Monitoraggio e gestione dei veicoli</p>
    <div style="display:flex;gap:8px;margin-bottom:16px">
      <button @click="analyzeFleet" class="btn-primary" :disabled="analyzing">{{ analyzing ? 'Analisi...' : 'Analisi Flotta' }}</button>
      <button @click="maintenanceMode" class="btn-secondary" :disabled="maintLoading">{{ maintLoading ? 'Impostazione...' : 'Manutenzione' }}</button>
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
const analyzing = ref(false)
const maintLoading = ref(false)
const error = ref('')

async function analyzeFleet() {
  analyzing.value = true; error.value = ''
  try { const res = await fleetApi.analyzeFleet(1); fleetData.value = res.data } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { analyzing.value = false }
}

async function maintenanceMode() {
  maintLoading.value = true; error.value = ''
  try { await fleetApi.startMaintenance(1); fleetData.value = { message: 'Manutenzione impostata' } } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
  finally { maintLoading.value = false }
}
</script>

<style scoped>
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
