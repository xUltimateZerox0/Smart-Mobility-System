import client from './client'
import type { MetodoPagamentoResponse } from '../types'

export function processPayment(idUtente: number, idMetodoPagamento: number, costo: number) {
  return client.post<boolean>('/payments/process', { idUtente, idMetodoPagamento, costo })
}

export function addPaymentMethod(data: {
  idUtente: number
  numCarta: string
  dsCarta: string
  cvv: string
  intestatarioCarta: string
}) {
  return client.post<boolean>('/payments/methods', data)
}

export function getSavedMethods(idUtente: number) {
  return client.get<MetodoPagamentoResponse[]>('/payments/methods', { params: { idUtente } })
}
