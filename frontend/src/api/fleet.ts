import client from './client'
import type { MezzoResponse, SegnalazioneResponse } from '../types'

export function analyzeFleet(flottaId: number) {
  return client.post<boolean>(`/fleet/${flottaId}/analyze`)
}

export function lockVehicle(id: number) {
  return client.post<boolean>(`/fleet/vehicles/${id}/lock`)
}

export function unlockVehicle(id: number) {
  return client.post<boolean>(`/fleet/vehicles/${id}/unlock`)
}

export function startVehicleMaintenance(id: number) {
  return client.post<boolean>(`/fleet/vehicles/${id}/maintenance`)
}

export function startMaintenance(flottaId: number) {
  return client.post<boolean>(`/fleet/${flottaId}/maintenance`)
}

export function getVehicleConditions(flottaId: number) {
  return client.get<MezzoResponse[]>(`/fleet/${flottaId}/conditions`)
}

export function getSegnalazioni(stato?: string) {
  const url = stato ? `/fleet/segnalazioni/${stato}` : '/fleet/segnalazioni'
  return client.get<SegnalazioneResponse[]>(url)
}
