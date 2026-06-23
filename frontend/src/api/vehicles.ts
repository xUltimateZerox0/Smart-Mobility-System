import client from './client'
import type { MezzoResponse } from '../types'

export function getNearbyVehicles(coordinateUtente: string, raggio: number) {
  return client.post<MezzoResponse[]>('/vehicles/nearby', { coordinateUtente, raggio })
}

export function getVehicleDetails(id: number) {
  return client.get<MezzoResponse>(`/vehicles/${id}`)
}
