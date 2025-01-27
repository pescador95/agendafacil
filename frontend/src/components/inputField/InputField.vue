<template>
  <div class="input-field">
    <label v-if="label" :for="id">{{ label }}</label>
    <input :id="id" :value="props.modelValue" v-bind="$attrs" @input="updateValue($event.target.value)" />
  </div>
</template>

<script src="./InputField.js"></script>
<style src="./InputField.css"></style>
