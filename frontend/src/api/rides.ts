import client from './client'
import type { CorsaResponse, PercorsoResponse, StimaCorsaResponse } from '../types'

export function getActiveRide() {
  return client.get<CorsaResponse>(`/rides/active`)
}

export function startRide(idMezzo: number, idUtente: number, qrCode: string) {
  return client.post<number>('/rides/start', { idMezzo, idUtente, qrCode })
}

export function endRide(id: number) {
  return client.post<CorsaResponse>(`/rides/${id}/end`)
}

export function getEstimate(id: number) {
  return client.get<StimaCorsaResponse>(`/rides/${id}/estimate`)
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
