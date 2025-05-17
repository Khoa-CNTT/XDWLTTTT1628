<template>
  <div class="admin-dashboard">
    <!-- Header -->
    <nav class="admin-header">
      <div class="logo">
        <img :src="logo" alt="Logo" />
        <span class="brand-text">STUDY ZONE</span>
      </div>
      <div class="admin-name">Admin</div>
    </nav>

    <!-- Page Title -->
    <div class="page-title">
      <h1>Admin Dashboard</h1>
      <p>Dashboard</p>
    </div>

    <!-- Tổng quan -->
    <div class="cards">
      <div class="card" v-for="item in summaryItems" :key="item.title">
        <i :class="item.icon"></i>
        <div>
          <h2>{{ item.value }}</h2>
          <p>{{ item.title }}</p>
        </div>
      </div>
    </div>

    <!-- Biểu đồ -->
    <div class="chart-section">
      <div class="chart-card">
        <h3>Top 5 bài thi được làm nhiều nhất</h3>
        <BarChart :chart-data="topTestChart" />
      </div>
      <div class="chart-card">
        <h3>Tỷ lệ người dùng hoàn thành mục tiêu</h3>
        <p>
          Tổng: {{ goalStats.totalGoals }} | Đã hoàn thành:
          {{ goalStats.completedGoals }} | Tỷ lệ:
          {{ goalStats.completionRate.toFixed(1) }}%
        </p>
        <progress
          class="form-range w-100"
          :value="goalStats.completionRate"
          max="100"
        ></progress>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import logo from "@/assets/images/logo-study-zone.png";
import BarChart from "@/views/admin/BarChart.vue";
import statisticsService from "@/services/statisticsService";

const summary = ref({});
const topTests = ref([]);
const goalStats = ref({
  totalGoals: 0,
  completedGoals: 0,
  completionRate: 0,
});

const summaryItems = computed(() => [
  { title: "Người dùng", value: summary.value.totalUsers, icon: "fas fa-user" },
  {
    title: "Bài thi",
    value: summary.value.totalTests,
    icon: "fas fa-clipboard-list",
  },
  {
    title: "Đã nộp",
    value: summary.value.completedTests,
    icon: "fas fa-chart-bar",
  },
  {
    title: "Điểm TB",
    value: summary.value.averageScore,
    icon: "fas fa-check-circle",
  },
  {
    title: "Bình luận",
    value: summary.value.totalComments,
    icon: "fas fa-comment",
  },
]);

const topTestChart = computed(() => {
  const labels = topTests.value.map((t) => t.testTitle);
  const data = topTests.value.map((t) => t.totalAttempts);
  return {
    labels,
    datasets: [
      {
        label: "Số lượt làm",
        data,
        backgroundColor: "#1877f2",
      },
    ],
  };
});

onMounted(async () => {
  try {
    const [s, t, g] = await Promise.all([
      statisticsService.getSummary(),
      statisticsService.getTopTests(),
      statisticsService.getGoalCompletion(),
    ]);
    summary.value = s.data;
    topTests.value = t.data;
    goalStats.value = g.data;
  } catch (err) {
    console.error("Lỗi tải dashboard:", err);
  }
});
</script>

<style scoped>
.admin-dashboard {
  padding: 2rem;
  background-color: #f9fafb;
  font-family: "Segoe UI", sans-serif;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  margin-bottom: 2rem;
}

.logo {
  display: flex;
  align-items: center;
}
.logo img {
  height: 48px;
  margin-right: 10px;
}
.brand-text {
  font-size: 1.6rem;
  font-weight: bold;
  color: #1877f2;
  text-transform: uppercase;
}
.admin-name {
  font-size: 1.2rem;
  font-weight: 600;
}
.page-title h1 {
  font-size: 2.2rem;
  margin-bottom: 0.25rem;
}
.page-title p {
  color: #777;
  margin-bottom: 2rem;
}

.cards {
  display: flex;
  gap: 1.5rem;
  flex-wrap: wrap;
  margin-bottom: 2rem;
}
.card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  flex: 1 1 200px;
  display: flex;
  align-items: center;
  gap: 1rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}
.card i {
  font-size: 2rem;
  color: #1877f2;
}
.card h2 {
  font-size: 1.6rem;
  margin: 0;
}
.card p {
  color: #555;
  margin: 0;
}

.chart-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin-top: 2rem;
}
.chart-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
}
</style>
