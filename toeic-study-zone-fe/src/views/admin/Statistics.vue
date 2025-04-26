<template>
  <div class="p-4">
    <h4 class="fw-bold mb-4">
      <i class="bi bi-bar-chart-fill me-2"></i> Thống kê người dùng làm bài
      nhiều nhất
    </h4>

    <!-- Bộ lọc theo tháng/năm -->
    <div class="row g-3 mb-4">
      <div class="col-md-3">
        <select v-model="selectedMonth" class="form-select">
          <option disabled value="">Chọn tháng</option>
          <option v-for="m in 12" :key="m" :value="m">
            {{ m < 10 ? "0" + m : m }}
          </option>
        </select>
      </div>
      <div class="col-md-3">
        <select v-model="selectedYear" class="form-select">
          <option disabled value="">Chọn năm</option>
          <option v-for="y in years" :key="y" :value="y">{{ y }}</option>
        </select>
      </div>
      <div class="col-md-3">
        <button class="btn btn-primary w-100" @click="applyFilters">
          Lọc dữ liệu
        </button>
      </div>
    </div>

    <!-- Biểu đồ -->
    <div class="card shadow-sm">
      <div class="card-body">
        <h6 class="fw-bold mb-3">Biểu đồ người dùng làm bài nhiều nhất</h6>
        <BarChart :chart-data="chartData" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import BarChart from "@/views/admin/BarChart.vue";

const selectedMonth = ref("");
const selectedYear = ref("");
const years = [2023, 2024, 2025];

const allTests = ref([
  { user: "Nguyễn Văn A", date: "2025-04-10" },
  { user: "Nguyễn Văn B", date: "2025-04-10" },
  { user: "Lê Thị Ánh Ngọc", date: "2025-04-05" },
  { user: "Nguyễn Văn A", date: "2025-04-12" },
  { user: "Nguyễn Văn C", date: "2025-03-15" },
  { user: "Nguyễn Văn A", date: "2025-04-19" },
  { user: "Nguyễn Văn B", date: "2025-04-25" },
  { user: "Lê Thị Ánh Ngọc", date: "2025-04-28" },
  { user: "Nguyễn Văn D", date: "2025-04-08" },
  { user: "Nguyễn Văn A", date: "2025-04-30" },
]);

const filteredTests = ref([]);

function applyFilters() {
  if (!selectedMonth.value || !selectedYear.value) return;

  filteredTests.value = allTests.value.filter((test) => {
    const d = new Date(test.date);
    const month = d.getMonth() + 1;
    const year = d.getFullYear();
    return (
      month === Number(selectedMonth.value) &&
      year === Number(selectedYear.value)
    );
  });
}

const chartData = computed(() => {
  const userCounts = {};

  filteredTests.value.forEach((t) => {
    userCounts[t.user] = (userCounts[t.user] || 0) + 1;
  });

  const sortedUsers = Object.entries(userCounts)
    .sort((a, b) => b[1] - a[1])
    .slice(0, 5);

  const labels = sortedUsers.map(([user]) => user);
  const data = sortedUsers.map(([_, count]) => count);

  return {
    labels,
    datasets: [
      {
        label: "Số lần làm bài",
        data,
        backgroundColor: "#0d6efd",
      },
    ],
  };
});
</script>

<style scoped>
select.form-select {
  font-size: 15px;
}
</style>
