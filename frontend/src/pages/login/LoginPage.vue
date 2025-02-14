<template>
  <div class="container">
    <div class="login-page">
      <div class="login-left">
        <img alt="Agenda Fácil" src="@/assets/logo.png" />
      </div>
      <div class="login-right">
        <h1>Login</h1>
        <InputField id="user" v-model="user" class="input-field input-field--full-width" placeholder="Usuário" />
        <InputField id="password" v-model="password" class="input-field input-field--full-width" placeholder="Senha"
          type="password" />

        <div class="sigin-button">
          <Button :disabled="verifyForm(user, password)" class="btn btn--primary " @click="handleLogin()">Entrar
          </Button>
          <div class="forgot-button">
            <a class="forgot-password" href="/forgotPassword">Esqueceu a senha?</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./LoginPage.js"></script>
<style src="./LoginPage.css"></style>
