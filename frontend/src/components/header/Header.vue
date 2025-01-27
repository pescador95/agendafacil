<template>
  <div class="header">
    <div>
      <Title :title="pageTitle" />
    </div>
    <div class="header-buttons">
      <Select id="organizacaoId" ref="organizacaoSelect" :options="organizacaoOptions"
        @change="handleOrganizacaoChange" />
      <LogoutButton />
    </div>
  </div>
</template>

<script src="./Header.js"></script>
<style src="./Header.css"></style>
