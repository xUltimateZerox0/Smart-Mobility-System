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
