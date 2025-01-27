<template>
  <button :class="`btn--${type}`" :disabled="disabled" class="btn" @click="handleClick">
    <slot />
  </button>
</template>

<script src="./Button.js"></script>
<style scoped src="./Button.css"></style>
