<template></template>

<script>
import Toastify from 'toastify-js'

export default {
  methods: {
    success(message) {
      Toastify({
        text: `${message}`,
        duration: 3000,
        close: true,
        gravity: 'top',
        position: 'right',
        style: {
          background: 'linear-gradient(to right, #00b09b, #96c93d)',
        },
        stopOnFocus: true,
      }).showToast()
    },
    error(message) {
      Toastify({
        text: `${message}`,
        duration: 3000,
        close: true,
        gravity: 'top',
        position: 'right',
        style: {
          background: 'linear-gradient(to right, #ff5f6d, #ffc371)',
        },
        stopOnFocus: true,
      }).showToast()
    },
    info(message) {
      Toastify({
        text: `${message}`,
        duration: 3000,
        close: true,
        gravity: 'top',
        position: 'right',
        style: {
          background: 'linear-gradient(to right, #3498db, #2980b9)',
        },
        stopOnFocus: true,
      }).showToast()
    },
  },
}
</script>

<style>
@import 'toastify-js/src/toastify.css';
</style>
