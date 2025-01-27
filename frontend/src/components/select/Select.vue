<template>
  <div class="select-container">
    <label v-if="label" :for="id">{{ label }}</label>
    <select :id="id" v-model="selectedValue" @change="emitChange" @clear="emitClear">
      <option disabled value="">{{ placeholder }}</option>
      <option v-for="option in options" :key="option.value" :value="option.value">
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<script src="./Select.js"></script>
<style scoped src="./Select.css"></style>
