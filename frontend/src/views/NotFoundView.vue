<template>
  <div class="not-found">
    <h1>404</h1>
    <p>Pagina non trovata</p>
    <router-link to="/" class="btn-primary" style="padding:10px 24px;display:inline-block;margin-top:16px">Torna alla home</router-link>
  </div>
</template>

<style scoped>
.not-found {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.not-found h1 { font-size: 72px; color: var(--primary); }
.not-found p { font-size: 18px; color: var(--gray); margin-top: 8px; }
</style>
