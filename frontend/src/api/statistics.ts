import client from './client'
import type { StatisticheResponse, CorsaResponse } from '../types'

export function analyzeStatistics(dataInizio: string, dataFine: string) {
  return client.post<StatisticheResponse>('/statistics/analyze', { dataInizio, dataFine })
}

export function exportStatistics(corse: CorsaResponse[]) {
  return client.post('/statistics/export', { corse }, { responseType: 'blob' })
}
