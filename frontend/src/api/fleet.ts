import client from './client'
import type { MezzoResponse } from '../types'

export function analyzeFleet(flottaId: number) {
  return client.post<boolean>(`/fleet/${flottaId}/analyze`)
}

export function lockVehicle(id: number) {
  return client.post<boolean>(`/fleet/vehicles/${id}/lock`)
}

export function startMaintenance(flottaId: number) {
  return client.post<boolean>(`/fleet/${flottaId}/maintenance`)
}

export function getVehicleConditions(flottaId: number) {
  return client.get<MezzoResponse[]>(`/fleet/${flottaId}/conditions`)
}
