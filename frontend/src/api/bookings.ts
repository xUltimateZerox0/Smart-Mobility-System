import client from './client'
import type { PrenotazioneResponse } from '../types'

export function createBooking({ idMezzo, idUtente, orarioInizio }: { idMezzo: number; idUtente: number; orarioInizio?: string }) {
  return client.post<PrenotazioneResponse>('/bookings', { idMezzo, idUtente, orarioInizio })
}

export function getBookings() {
  return client.get<PrenotazioneResponse[]>('/bookings')
}

export function getUserBookings() {
  return client.get<PrenotazioneResponse[]>(`/bookings/user/me`)
}

export function cancelBooking(id: number) {
  return client.delete<boolean>(`/bookings/${id}`)
}

export function handleTimeout() {
  return client.post<void>('/bookings/timeout')
}

export function notifyExpiry(id: number) {
  return client.post<void>(`/bookings/${id}/notify-expiry`)
}

export function getQRCode(id: number) {
  return client.get<string>(`/bookings/${id}/qrcode`)
}
