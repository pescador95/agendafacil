<template>
  <div v-if="show" class="modal-overlay">
    <div class="modal-content">
      <slot name="header"></slot>
      <slot name="body"></slot>
      <slot name="footer"></slot>
    </div>
  </div>
</template>

<script src="./Modal.js" />
<style src="./Modal.css" />
