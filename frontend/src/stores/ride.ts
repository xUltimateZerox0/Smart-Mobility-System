import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import * as ridesApi from '../api/rides'

export const useRideStore = defineStore('ride', () => {
  const corsaId = ref<number>(0)
  const idMezzo = ref<number>(0)
  const dataInizio = ref<string | null>(null)
const metodoPagamentoId = ref<number | null>(null)
const metodoPagamentoLabel = ref('')
const isPaused = ref(false)
const totalePausaMillis = ref(0)
const stato = ref<string>('')
const costo = ref<number>(0)

const hasActiveRide = computed(() => corsaId.value > 0)
const orarioInizio = computed(() => dataInizio.value)

async function fetchActiveRide(): Promise<boolean> {
  try {
    const res = await ridesApi.getActiveRide()
    if (res.data && res.data.id) {
      corsaId.value = res.data.id
      idMezzo.value = res.data.idMezzo || 0
      dataInizio.value = res.data.dataInizio || null
      metodoPagamentoId.value = res.data.idMetodoPagamento || null
      metodoPagamentoLabel.value = res.data.metodoPagamentoLabel || ''
      isPaused.value = res.data.isPaused || false
      totalePausaMillis.value = res.data.totalePausaMillis || 0
      stato.value = res.data.stato || 'in_corso'
      return true
    }
    clearRide()
    return false
  } catch {
    return corsaId.value > 0
  }
}

  function setRide(id: number, mezzoId: number) {
    corsaId.value = id
    idMezzo.value = mezzoId
  }

  function setPaymentMethod(id: number, label: string) {
    metodoPagamentoId.value = id
    metodoPagamentoLabel.value = label
  }

  function setPaused(paused: boolean) {
    isPaused.value = paused
  }

  function updateCosto(costoValue: number) {
    costo.value = costoValue
  }

  function clearRide() {
    corsaId.value = 0
    idMezzo.value = 0
    dataInizio.value = null
    metodoPagamentoId.value = null
    metodoPagamentoLabel.value = ''
    isPaused.value = false
    totalePausaMillis.value = 0
    stato.value = ''
    costo.value = 0
  }

  return {
    corsaId, idMezzo, dataInizio, metodoPagamentoId, metodoPagamentoLabel,
    isPaused, totalePausaMillis, stato, costo, hasActiveRide, orarioInizio,
    fetchActiveRide, setRide, setPaymentMethod, setPaused, updateCosto, clearRide
  }
})
