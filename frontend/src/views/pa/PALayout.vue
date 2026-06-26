<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="sidebar-header"><h2 class="sidebar-title">Pubblica Amministrazione</h2></div>
      <nav class="sidebar-nav">
        <router-link to="/pa" class="nav-item">Dashboard</router-link>
        <router-link to="/pa/statistics" class="nav-item">Statistiche</router-link>
        <router-link to="/pa/zones" class="nav-item">Zone</router-link>
      </nav>
      <div class="sidebar-footer">
        <button @click="handleLogout" class="btn-secondary" style="width:100%">Logout</button>
      </div>
    </aside>
    <main class="main-content"><router-view /></main>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/auth'
const router = useRouter()
const auth = useAuthStore()
async function handleLogout() { await auth.logout(); router.push('/login') }
</script>

<style scoped>
.layout { display: flex; min-height: 100vh; }
.sidebar { width: var(--sidebar-width); background: var(--dark); color: white; display: flex; flex-direction: column; position: fixed; top: 0; left: 0; bottom: 0; }
.sidebar-header { padding: 20px; border-bottom: 1px solid rgba(255,255,255,0.1); }
.sidebar-header h2 { font-size: 18px; }
.sidebar-title { color: white !important; font-weight: 700; letter-spacing: 0.5px; }
.sidebar-nav { flex: 1; padding: 12px; display: flex; flex-direction: column; gap: 4px; }
.nav-item { padding: 10px 14px; border-radius: 6px; color: rgba(255,255,255,0.7); font-size: 14px; }
.nav-item:hover, .nav-item.router-link-exact-active { background: rgba(255,255,255,0.1); color: #212529; text-decoration: none; }
.sidebar-footer { padding: 12px; border-top: 1px solid rgba(255,255,255,0.1); }
.main-content { margin-left: var(--sidebar-width); flex: 1; padding: 24px; }
</style>
