import client from './client'
import type { UtenteResponse } from '../types'

export function getUsers() {
  return client.get<UtenteResponse[]>('/admin/users')
}

export function getUserReport(id: number) {
  return client.get<string>(`/admin/users/${id}/report`)
}

export function moderateUser(id: number) {
  return client.put<boolean>(`/admin/users/${id}/moderate`)
}

export function correctiveAction(id: number, azione: string) {
  return client.post<void>(`/admin/users/${id}/corrective-action`, { azione })
}

export function blockUser(id: number) {
  return client.post<void>(`/admin/users/${id}/block`)
}

export function unblockUser(id: number) {
  return client.post<void>(`/admin/users/${id}/unblock`)
}

export function disableUser(id: number) {
  return client.post<void>(`/admin/users/${id}/disable`)
}

export function clearUserReport(id: number) {
  return client.delete<void>(`/admin/users/${id}/report`)
}
