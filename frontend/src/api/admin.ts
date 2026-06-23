import client from './client'

export function getUserReport(id: number) {
  return client.get<string>(`/admin/users/${id}/report`)
}

export function moderateUser(id: number) {
  return client.put<boolean>(`/admin/users/${id}/moderate`)
}

export function correctiveAction(id: number, azione: string) {
  return client.post<void>(`/admin/users/${id}/corrective-action`, { azione })
}
