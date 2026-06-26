import client from './client'
import type { StatisticheResponse, CorsaResponse, FleetAnalysisResponse } from '../types'

export function analyzeStatistics(dataInizio: string, dataFine: string) {
  return client.post<StatisticheResponse>('/statistics/analyze', { dataInizio, dataFine })
}

export function exportStatistics(corse: CorsaResponse[]) {
  return client.post<Blob>('/statistics/export', { corse }, { responseType: 'blob' })
}

export function getFleetAnalysis() {
  return client.get<FleetAnalysisResponse>('/statistics/fleet')
}
