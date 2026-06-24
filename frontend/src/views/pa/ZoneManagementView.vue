<template>
  <div>
    <h1>Gestione Zone</h1>
    <div v-if="zone.length > 0" class="zone-list">
      <div v-for="z in zone" :key="z.id" class="card">
        <h3>{{ z.nome }}</h3>
        <p><strong>Restrizione:</strong> {{ z.tipoRestrizione }}</p>
        <p><strong>Note:</strong> {{ z.noteRestrizione }}</p>
        <div class="form-group" style="margin-top:8px">
          <input v-model="editNote[z.id]" :placeholder="'Nuova nota per ' + z.nome" />
        </div>
        <div style="display:flex;gap:8px;margin-top:4px">
          <button @click="checkAndUpdate(z)" class="btn-primary">Verifica Conflitti e Aggiorna</button>
        </div>
        <p v-if="conflictMsg[z.id]" :style="{ color: conflictResolved[z.id] ? 'var(--success)' : 'var(--warning)', fontSize: '13px', marginTop: '4px' }">
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
const conflictMsg = reactive<Record<number, string>>({})
const conflictResolved = reactive<Record<number, boolean>>({})
const error = ref('')

onMounted(async () => {
  try { const res = await zonesApi.getZones(); zone.value = res.data } catch {}
})

async function checkAndUpdate(z: ZonaGeograficaResponse) {
  error.value = ''
  const id = z.id
  try {
    const conflictRes = await zonesApi.checkConflict('')
    if (conflictRes.data) {
      conflictMsg[id] = 'Attenzione: rilevato conflitto con zona esistente. Sovrascrittura in corso...'
      conflictResolved[id] = false
    }
    await zonesApi.updateZone(id, { tipoRestrizione: z.tipoRestrizione, noteRestrizione: editNote[id] || z.noteRestrizione || '', zona: '' })
    const res = await zonesApi.getZones()
    zone.value = res.data
    conflictMsg[id] = 'Zona aggiornata con successo'
    conflictResolved[id] = true
  } catch (e: any) { error.value = e.response?.data?.message || 'Errore' }
}
</script>

<style scoped>
.zone-list { display: flex; flex-direction: column; gap: 12px; margin-top: 16px; }
.zone-list .card h3 { margin-bottom: 6px; }
.zone-list .card p { font-size: 13px; margin-bottom: 4px; }
.error-message { color: var(--danger); font-size: 13px; margin-top: 8px; }
</style>
