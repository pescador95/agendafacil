<template>
  <div class="container">
    <div class="recovery-page">
      <div class="recovery-left">
        <img alt="Agenda Fácil" src="@/assets/logo.png" />
      </div>
      <div class="recovery-right">
        <h1>Digite a sua nova senha</h1>
        <InputField id="password" v-model="password" class="input-field input-field--full-width" placeholder="Senha"
          type="password" />
        <InputField id="confirmpassword" v-model="confirmpassword" class="input-field input-field--full-width"
          placeholder="Confirmar Senha" type="password" />
        <Button :disabled="verifyPass(password, confirmpassword)" class="btn btn--primary "
          @click="handleSubmmit()">ALTERAR
        </Button>
        <div class="recover-button">
          <a class="recovery-password" href="/">Deseja fazer login?</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script src="./RecoveryPage.js"></script>
<style src="./RecoveryPage.css"></style>
