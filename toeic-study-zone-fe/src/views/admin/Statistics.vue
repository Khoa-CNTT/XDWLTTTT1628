<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-6">📊 Thống kê</h2>

    <div class="grid grid-cols-1 md:grid-cols-5 gap-4 mb-6">
      <div
        v-for="item in summaryItems"
        :key="item.title"
        class="rounded-2xl p-4 shadow text-center bg-white border border-gray-200"
      >
        <div class="text-2xl font-bold text-blue-600">{{ item.value }}</div>
        <div class="text-sm text-gray-500">{{ item.title }}</div>
      </div>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
      <div class="card shadow-sm p-4 bg-white rounded-xl">
        <h5 class="text-lg font-semibold mb-4">📌 Top 5 bài thi được làm nhiều nhất</h5>
        <BarChart :chart-data="topTestChart" />
      </div>

      <div class="card shadow-sm p-4 bg-white rounded-xl">
        <h5 class="text-lg font-semibold mb-4">🎯 Tỷ lệ hoàn thành mục tiêu</h5>
        <div class="text-sm text-gray-600 mb-2">
          Tổng: {{ goalStats.totalGoals }} | Hoàn thành: {{ goalStats.completedGoals }} |
          Tỷ lệ: {{ goalStats.completionRate.toFixed(1) }}%
        </div>
        <div class="w-full h-4 bg-gray-200 rounded-full">
          <div
            class="h-4 bg-blue-500 rounded-full"
            :style="{ width: goalStats.completionRate + '%' }"
          ></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import BarChart from '@/views/admin/BarChart.vue';
import statisticsService from '@/services/statisticsService';

const summary = ref({});
const topTests = ref([]);
const goalStats = ref({
  totalGoals: 0,
  completedGoals: 0,
  completionRate: 0,
});
const summaryItems = computed(() => [
  { title: 'Người dùng', value: summary.value.totalUsers },
  { title: 'Bài thi', value: summary.value.totalTests },
  { title: 'Đã nộp', value: summary.value.completedTests },
  { title: 'Điểm TB', value: summary.value.averageScore },
  { title: 'Bình luận', value: summary.value.totalComments },
]);


const topTestChart = computed(() => {
  const labels = topTests.value.map(t => t.testTitle);
  const data = topTests.value.map(t => t.totalAttempts);
  return {
    labels,
    datasets: [
      {
        label: 'Số lượt làm',
        data,
        backgroundColor: '#3b82f6',
        borderRadius: 5,
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
    console.error('Lỗi thống kê:', err);
  }
});
</script>

<style scoped>
.card {
  border: 1px solid #e5e7eb;
}
</style>
