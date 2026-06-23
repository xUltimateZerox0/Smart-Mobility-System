import client from './client'
import type { PercorsoResponse } from '../types'

export function startRide(idMezzo: number, idUtente: number) {
  return client.post<void>('/rides/start', { idMezzo, idUtente })
}

export function endRide(id: number) {
  return client.post<void>(`/rides/${id}/end`)
}

export function getEstimate(id: number) {
  return client.get<number>(`/rides/${id}/estimate`)
}

export function pauseRide(id: number) {
  return client.post<boolean>(`/rides/${id}/pause`)
}

export function unlockVehicle(qrCode: string) {
  return client.post<boolean>('/rides/unlock', { qrCode })
}

export function calculateRoute(coordinateUtente: string, destinazione: string) {
  return client.post<PercorsoResponse>('/rides/route', { coordinateUtente, destinazione })
}

export function selectPaymentMethod(idMetodoPagamento: number) {
  return client.post<void>('/rides/payment-method', { idMetodoPagamento })
}

export function checkAvailability(id: number, info?: string) {
  const url = info ? `/rides/${id}/availability/${info}` : `/rides/${id}/availability`
  return client.get<boolean>(url)
}
