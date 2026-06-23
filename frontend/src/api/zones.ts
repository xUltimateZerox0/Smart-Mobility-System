import client from './client'
import type { ZonaGeograficaResponse } from '../types'

export function updateZone(id: number, data: { tipoRestrizione: string; noteRestrizione?: string; zona: string }) {
  return client.put<void>(`/zones/${id}`, data)
}

export function checkConflict(zona: string) {
  return client.post<boolean>('/zones/conflict-check', { zona })
}

export function getZones() {
  return client.get<ZonaGeograficaResponse[]>('/zones')
}
