<template>
  <section class="sidebar">
    <div class="logo">
      <div class="logo-container">
        <img alt="Logo" src="../../assets/logo.png" />
      </div>
    </div>
    <div>
      <ul>
        <router-link
          v-slot="{ route, href, navigate, isActive, isExactActive }"
          custom
          to="/agendamento"
        >
          <li :class="[isExactActive ? 'active-li' : 'default-li']">Agendamentos</li>
        </router-link>
        <router-link
          v-slot="{ route, href, navigate, isActive, isExactActive }"
          custom
          to="/clientes"
        >
          <li :class="[isExactActive ? 'active-li' : 'default-li']">Clientes</li>
        </router-link>
        <router-link
          v-slot="{ route, href, navigate, isActive, isExactActive }"
          custom
          to="/organizacoes"
        >
          <li :class="[isExactActive ? 'active-li' : 'default-li']">Organizações</li>
        </router-link>
      </ul>
    </div>
  </section>
</template>

<script src="./Sidebar.js"></script>
<style src="./Sidebar.css"></style>
