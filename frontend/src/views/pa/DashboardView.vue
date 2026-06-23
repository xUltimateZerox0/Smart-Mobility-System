<template>
  <div>
    <h1>Dashboard PA</h1>
    <p style="color:var(--gray);margin-bottom:24px">Monitoraggio della mobilità urbana</p>
    <div class="dashboard-grid">
      <div class="card">
        <h3>Statistiche</h3>
        <p>Analisi delle tratte e reportistica</p>
        <router-link to="/pa/statistics" class="btn-primary" style="display:inline-block;margin-top:12px">Vedi</router-link>
      </div>
      <div class="card">
        <h3>Gestione Zone</h3>
        <p>Configura restrizioni geografiche</p>
        <router-link to="/pa/zones" class="btn-primary" style="display:inline-block;margin-top:12px">Gestisci</router-link>
      </div>
    </div>
  </div>
</template>

<style scoped>
.dashboard-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 16px; }
.dashboard-grid .card h3 { margin-bottom: 8px; font-size: 16px; }
.dashboard-grid .card p { font-size: 13px; color: var(--gray); }
</style>
