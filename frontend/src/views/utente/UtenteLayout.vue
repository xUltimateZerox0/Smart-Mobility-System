<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2 class="sidebar-title">Smart Mobility</h2>
      </div>
      <nav class="sidebar-nav">
        <router-link to="/utente" class="nav-item">Dashboard</router-link>
        <router-link v-if="!rideStore.hasActiveRide" to="/utente/vehicles" class="nav-item">Veicoli</router-link>
        <router-link to="/utente/bookings" class="nav-item">Prenotazioni</router-link>
        <router-link to="/utente/payments" class="nav-item">Pagamenti</router-link>
        <router-link v-if="rideStore.hasActiveRide" :to="`/utente/ride/${rideStore.idMezzo}?corsaId=${rideStore.corsaId}`" class="nav-item nav-ride">
          <span class="ride-indicator"></span>
          Corsa Attiva
        </router-link>
      </nav>
      <div class="sidebar-footer">
        <button @click="handleLogout" class="btn-secondary" style="width:100%">Logout</button>
      </div>
    </aside>
    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
import { useRideStore } from '../../stores/ride'

const router = useRouter()
const auth = useAuthStore()
const rideStore = useRideStore()

let pollInterval: ReturnType<typeof setInterval> | null = null

onMounted(async () => {
  await rideStore.fetchActiveRide()
  pollInterval = setInterval(() => rideStore.fetchActiveRide(), 15000)
})

onUnmounted(() => {
  if (pollInterval) {
    clearInterval(pollInterval)
    pollInterval = null
  }
})

async function handleLogout() {
  await auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.layout { display: flex; min-height: 100vh; }
.sidebar {
  width: var(--sidebar-width);
  background: var(--dark);
  color: white;
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
}
.sidebar-header { padding: 20px; border-bottom: 1px solid rgba(255,255,255,0.1); }
.sidebar-header h2 { font-size: 18px; }
.sidebar-title { color: white !important; font-weight: 700; letter-spacing: 0.5px; }
.sidebar-nav { flex: 1; padding: 12px; display: flex; flex-direction: column; gap: 4px; }
.nav-item {
  padding: 10px 14px;
  border-radius: 6px;
  color: rgba(255,255,255,0.7);
  font-size: 14px;
  transition: all 0.2s;
}
.nav-item:hover, .nav-item.router-link-exact-active {
  background: rgba(255,255,255,0.1);
  color: white;
  text-decoration: none;
}
.nav-ride {
  background: rgba(40, 167, 69, 0.2);
  color: #28a745;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}
.nav-ride:hover { background: rgba(40, 167, 69, 0.3); }
.ride-indicator {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: #28a745;
  display: inline-block;
  animation: pulse 2s infinite;
}
@keyframes pulse {
  0%, 100% { opacity: 1; }
  50% { opacity: 0.4; }
}
.sidebar-footer { padding: 12px; border-top: 1px solid rgba(255,255,255,0.1); }
.main-content { margin-left: var(--sidebar-width); flex: 1; padding: 24px; }
</style>
