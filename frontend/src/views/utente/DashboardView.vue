<template>
  <div>
    <h1>Dashboard Utente</h1>
    <p style="color:var(--gray);margin-bottom:24px">Benvenuto, {{ auth.userEmail }}</p>
    <div class="dashboard-grid">
      <div class="card">
        <h3>Veicoli nelle vicinanze</h3>
        <p>Cerca veicoli disponibili vicino a te</p>
        <router-link to="/utente/vehicles" class="btn-primary" style="display:inline-block;margin-top:12px">Cerca</router-link>
      </div>
      <div class="card">
        <h3>Prenotazioni</h3>
        <p>Gestisci le tue prenotazioni attive</p>
        <router-link to="/utente/bookings" class="btn-primary" style="display:inline-block;margin-top:12px">Vedi</router-link>
      </div>
      <div class="card">
        <h3>Pagamenti</h3>
        <p>Gestisci i tuoi metodi di pagamento</p>
        <router-link to="/utente/payments" class="btn-primary" style="display:inline-block;margin-top:12px">Gestisci</router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useAuthStore } from '../../stores/auth'
const auth = useAuthStore()
</script>

<style scoped>
.dashboard-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
.dashboard-grid .card h3 { margin-bottom: 8px; font-size: 16px; }
.dashboard-grid .card p { font-size: 13px; color: var(--gray); }
</style>
