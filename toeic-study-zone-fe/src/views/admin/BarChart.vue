<template>
  <Bar :data="chartData" :options="options" />
</template>

<script setup>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

defineProps({
  chartData: Object,
});

const options = {
  responsive: true,
  plugins: {
    legend: {
      position: "bottom",
    },
  },
};
</script>

<style scoped>
canvas {
  max-height: 400px;
}
</style>
