<template>
  <div>
    <h1>Gestione Zone</h1>
    <div v-if="zone.length > 0" class="zone-list">
      <div v-for="z in zone" :key="z.id" class="card">
        <h3>{{ z.tipoRestrizione }}</h3>
        <p><strong>Restrizione:</strong> {{ z.tipoRestrizione }}</p>
        <p><strong>Note:</strong> {{ z.noteRestrizione }}</p>
        <div class="form-group" style="margin-top:8px">
          <label>Tipo restrizione</label>
          <select v-model="editTipo[z.id]">
            <option value="">-- Seleziona --</option>
            <option value="divieto_parcheggio">Divieto Parcheggio</option>
            <option value="ZTL">ZTL</option>
            <option value="limite_velocita">Limite Velocità</option>
          </select>
        </div>
        <div class="form-group" style="margin-top:8px">
          <label>Coordinate zona</label>
          <input v-model="editZona[z.id]" placeholder="es. 45.4640,9.1900,45.4660,9.1950" />
        </div>
        <div class="form-group" style="margin-top:8px">
          <label>Note</label>
          <input v-model="editNote[z.id]" :placeholder="'Nuova nota per ' + z.tipoRestrizione" />
        </div>
        <div style="display:flex;gap:8px;margin-top:4px">
          <button @click="checkAndUpdate(z)" class="btn-primary">Verifica Conflitti e Aggiorna</button>
        </div>
        <div v-if="pendingOverwrite[z.id]" class="card" style="margin-top:8px;padding:8px;background:#fff8e1;border-color:#f0ad4e">
          <p style="font-size:13px;color:#856404;margin-bottom:8px">Rilevato conflitto con zona esistente. Confermi la sovrascrittura?</p>
          <div style="display:flex;gap:8px">
            <button @click="confirmOverwrite(z)" class="btn-danger" style="padding:4px 12px;font-size:13px">Conferma Sovrascrittura</button>
            <button @click="cancelOverwrite(z.id)" class="btn-secondary" style="padding:4px 12px;font-size:13px">Annulla</button>
          </div>
        </div>
        <p v-if="conflictMsg[z.id] && !pendingOverwrite[z.id]" :style="{ color: conflictResolved[z.id] ? 'var(--success)' : 'var(--warning)', fontSize: '13px', marginTop: '4px' }">
          {{ conflictMsg[z.id] }}
        </p>
      </div>
    </div>
    <p v-else style="color:var(--gray)">Nessuna zona configurata</p>
    <p v-if="error" class="error-message">{{ error }}</p>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import * as zonesApi from '../../api/zones'
import type { ZonaGeograficaResponse } from '../../types'

const zone = ref<ZonaGeograficaResponse[]>([])
const editNote = reactive<Record<number, string>>({})
const editTipo = reactive<Record<number, string>>({})
const editZona = reactive<Record<number, string>>({})
const conflictMsg = reactive<Record<number, string>>({})
const conflictResolved = reactive<Record<number, boolean>>({})
const pendingOverwrite = reactive<Record<number, boolean>>({})
const pendingData = reactive<Record<number, { tipo: string; note: string; zona: string }>>({})
const error = ref('')

onMounted(async () => {
  try {
    const res = await zonesApi.getZones()
    zone.value = res.data
    res.data.forEach(z => {
      editTipo[z.id] = z.tipoRestrizione || ''
      editZona[z.id] = z.zona || ''
      editNote[z.id] = z.noteRestrizione || ''
    })
  } catch (e) { console.error('getZones failed:', e) }
})

async function checkAndUpdate(z: ZonaGeograficaResponse) {
  error.value = ''
  const id = z.id
  const tipo = editTipo[id] || z.tipoRestrizione || ''
  const zona = editZona[id] || z.zona || ''
  const note = editNote[id] || z.noteRestrizione || ''
  try {
    const conflictRes = await zonesApi.checkConflict(zona)
    if (conflictRes.data) {
      conflictMsg[id] = 'Attenzione: rilevato conflitto con zona esistente.'
      conflictResolved[id] = false
      pendingOverwrite[id] = true
      pendingData[id] = { tipo, note, zona }
      return
    }
    await performUpdate(id, tipo, note, zona)
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
}

async function confirmOverwrite(z: ZonaGeograficaResponse) {
  const id = z.id
  const data = pendingData[id]
  if (!data) return
  try {
    await performUpdate(id, data.tipo, data.note, data.zona)
    pendingOverwrite[id] = false
    delete pendingData[id]
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
}

function cancelOverwrite(id: number) {
  pendingOverwrite[id] = false
  delete pendingData[id]
  conflictMsg[id] = 'Operazione annullata'
  conflictResolved[id] = true
}

async function performUpdate(id: number, tipo: string, note: string, zona: string) {
  await zonesApi.updateZone(id, { tipoRestrizione: tipo, noteRestrizione: note, zona })
  const res = await zonesApi.getZones()
  zone.value = res.data
  conflictMsg[id] = 'Zona aggiornata con successo'
  conflictResolved[id] = true
}
</script>

<style scoped>
.zone-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.zone-list .card h3 { margin-bottom: 6px; }
.zone-list .card p { font-size: 13px; margin-bottom: 4px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
