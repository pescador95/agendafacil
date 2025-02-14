<template>
  <div class="container">
    <div class="forgot-page">
      <div class="forgot-left">
        <img alt="Agenda Fácil" src="@/assets/logo.png" />
      </div>
      <div class="forgot-right">
        <h1>Esqueceu a senha?</h1>
        <h2>
          Informe o seu e-mail ou login de acesso cadastrado para enviarmos um link para a
          recuperação da senha.
        </h2>
        <InputField id="email" v-model="email" class="input-field input-field--full-width" placeholder="E-mail" />
        <Button :disabled="!email" class="btn btn--primary " @click="handleSubmmit()">Enviar
        </Button>
        <div class="forgot-button">
          <a class="forgot-password" href="/">Deseja fazer login?</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./ForgotPage.js"></script>
<style src="./ForgotPage.css"></style>
