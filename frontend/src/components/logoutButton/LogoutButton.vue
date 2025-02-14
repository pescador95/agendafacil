<template>
  <div class="logout">
    <button @click="handleLogout()">
      {{ userLogin }} <img alt="Logout Icon" class="logout-icon" src="@/assets/icons/logout.svg" />
    </button>
  </div>
</template>

<script src="./LogoutButton.js"></script>
<style src="./LogoutButton.css"></style>
