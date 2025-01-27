<template>
  <div class="recovery-view">
    <RecoveryPage />
  </div>
</template>

<script setup>
import RecoveryPage from '@/pages/recovery/RecoveryPage.vue'
</script>

<style scoped>
.recovery-view {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: var(--white);
}
</style>
