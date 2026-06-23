import client from './client'
import type { PrenotazioneResponse } from '../types'

export function createBooking(idMezzo: number, idUtente: number) {
  return client.post<void>('/bookings', { idMezzo, idUtente })
}

export function getBookings() {
  return client.get<PrenotazioneResponse[]>('/bookings')
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
