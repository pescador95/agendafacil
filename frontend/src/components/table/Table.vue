<template>
  <table class="table">
    <thead>
      <tr>
        <th v-for="(column, index) in columns" :key="index">{{ column }}</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(row, rowIndex) in data" :key="rowIndex">
        <td v-for="(column, colIndex) in columns" :key="colIndex">{{ row[column] }}</td>
      </tr>
    </tbody>
  </table>
</template>

<script src="./Table.js"></script>
<style src="./Table.css"></style>
