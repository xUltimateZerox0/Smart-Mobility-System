export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  nome: string
  cognome: string
  email: string
  password: string
  datanascita: string
}

export interface AuthResponse {
  token: string
  email: string
  ruolo: string
  idUtente: number
  tipo?: string
}

export interface MezzoResponse {
  id: number
  tipo: string
  stato: string
  latitudine: number
  longitudine: number
  autonomia: number
  tariffa: number
  codiceMezzo: string
  tempoDisponibilita?: string
}

export interface PrenotazioneResponse {
  id: number
  idUtente: number
  idMezzo: number
  dataInizio: string
  dataFine: string
  stato: string
  nomeVeicolo?: string
  tipoVeicolo?: string
}

export interface CorsaResponse {
  id: number
  idUtente: number
  idMezzo: number
  dataInizio: string
  dataFine: string
  costo: number
  distanza: number
  stato: string
}

export interface MetodoPagamentoResponse {
  id: number
  numCarta: string
  intestatarioCarta: string
  dsCarta: string
}

export interface StatisticheResponse {
  totalCorse: number
  totalKm: number
  totalRicavo: number
  mediaDurata: number
  dettagli: unknown
}

export interface ZonaGeograficaResponse {
  id: number
  nome: string
  tipoRestrizione: string
  noteRestrizione: string
  zona: string
}

export interface PercorsoResponse {
  percorso: string
  messaggio: string
}

export interface UtenteResponse {
  id: number
  idUtente: number
  nome: string
  cognome: string
  email: string
  stato: string
}

export interface ErrorResponse {
  status: number
  message: string
  timestamp: number
}

export const RuoloAttore = {
  Utente: 'Utente',
  Operatore: 'Operatore',
  PA: 'PA',
} as const
export type RuoloAttore = (typeof RuoloAttore)[keyof typeof RuoloAttore]

export const StatoMezzo = {
  Disponibile: 'disponibile',
  Prenotato: 'prenotato',
  InUso: 'in_uso',
  Sospeso: 'sospeso',
  Bloccato: 'bloccato',
  Manutenzione: 'manutenzione',
} as const
export type StatoMezzo = (typeof StatoMezzo)[keyof typeof StatoMezzo]

export const StatoPrenotazione = {
  Attiva: 'attiva',
  Scaduta: 'scaduta',
  Annullata: 'annullata',
  Completata: 'completata',
} as const
export type StatoPrenotazione = (typeof StatoPrenotazione)[keyof typeof StatoPrenotazione]
