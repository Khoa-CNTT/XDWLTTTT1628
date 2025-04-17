<template>
  <div class="p-4">
    <h4 class="fw-bold mb-4">
      <i class="bi bi-bar-chart-fill me-2"></i> Thống kê kết quả luyện thi TOEIC
    </h4>

    <!-- Bộ lọc ngày -->
    <div class="row g-3 mb-4">
      <div class="col-md-3">
        <input type="date" v-model="filters.startDate" class="form-control" />
      </div>
      <div class="col-md-3">
        <input type="date" v-model="filters.endDate" class="form-control" />
      </div>
      <div class="col-md-3">
        <button class="btn btn-primary w-100" @click="applyFilters">
          Lọc dữ liệu
        </button>
      </div>
    </div>

    <!-- Thống kê tổng quan -->
    <div class="row g-4 mb-4">
      <div class="col-md-3" v-for="box in summaryBoxes" :key="box.label">
        <div class="border rounded p-3 text-center shadow-sm h-100">
          <div :class="`text-${box.color} fw-bold mb-1`">
            <i :class="`bi ${box.icon} me-1`"></i> {{ box.label }}
          </div>
          <div class="fs-4 fw-semibold">{{ box.value }}</div>
        </div>
      </div>
    </div>

    <!-- Biểu đồ -->
    <div class="card mb-4 shadow-sm">
      <div class="card-body">
        <h6 class="fw-bold mb-3">Biểu đồ điểm trung bình theo người dùng</h6>
        <BarChart :chart-data="chartData" />
      </div>
    </div>

    <!-- Bảng dữ liệu chi tiết -->
    <div class="card shadow-sm">
      <div class="card-body">
        <h6 class="fw-bold mb-3">Danh sách bài thi</h6>
        <div class="table-responsive">
          <table class="table table-bordered table-hover align-middle">
            <thead class="table-light">
              <tr>
                <th>#</th>
                <th>Người dùng</th>
                <th>Tên bài thi</th>
                <th>Điểm</th>
                <th>Đúng</th>
                <th>Độ chính xác</th>
                <th>Trạng thái</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(test, i) in filteredTests" :key="i">
                <td>{{ i + 1 }}</td>
                <td>{{ test.user }}</td>
                <td>{{ test.testTitle }}</td>
                <td>{{ test.completed ? test.total_score : "-" }}</td>
                <td>
                  {{
                    test.completed
                      ? `${test.correct_answers}/${test.total_questions}`
                      : "-"
                  }}
                </td>
                <td>
                  {{
                    test.completed
                      ? (
                          (test.correct_answers / test.total_questions) *
                          100
                        ).toFixed(1) + "%"
                      : "-"
                  }}
                </td>
                <td>
                  <span
                    class="badge"
                    :class="
                      test.completed ? 'bg-success' : 'bg-warning text-dark'
                    "
                  >
                    {{ test.completed ? "Hoàn thành" : "Chưa hoàn thành" }}
                  </span>
                </td>
              </tr>
              <tr v-if="filteredTests.length === 0">
                <td colspan="7" class="text-center text-muted">
                  Không có dữ liệu
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import BarChart from "@/views/admin/BarChart.vue";

const filters = ref({
  startDate: "",
  endDate: "",
});

const allTests = ref([
  {
    user: "Nguyễn Văn A",
    testTitle: "TOEIC Practice Test 1",
    total_score: 720,
    correct_answers: 160,
    total_questions: 200,
    completed: true,
    date: "2025-04-10",
  },
  {
    user: "Lê Thị Ánh Ngọc",
    testTitle: "TOEIC Practice Test 2",
    total_score: 495,
    correct_answers: 98,
    total_questions: 200,
    completed: true,
    date: "2025-04-09",
  },
  {
    user: "Nguyễn Văn B",
    testTitle: "Part 2 Mini Test",
    total_score: null,
    correct_answers: 0,
    total_questions: 100,
    completed: false,
    date: "2025-04-08",
  },
]);

const filteredTests = computed(() => {
  return allTests.value.filter((test) => {
    const testDate = new Date(test.date);
    const start = filters.value.startDate
      ? new Date(filters.value.startDate)
      : null;
    const end = filters.value.endDate ? new Date(filters.value.endDate) : null;
    return (!start || testDate >= start) && (!end || testDate <= end);
  });
});

const summaryBoxes = computed(() => {
  const total = filteredTests.value.length;
  const completed = filteredTests.value.filter((t) => t.completed).length;
  const totalCorrect = filteredTests.value.reduce(
    (acc, t) => acc + (t.completed ? t.correct_answers : 0),
    0
  );
  const totalQuestions = filteredTests.value.reduce(
    (acc, t) => acc + (t.completed ? t.total_questions : 0),
    0
  );
  const avgScore = Math.round(
    filteredTests.value.reduce(
      (acc, t) => acc + (t.completed ? t.total_score || 0 : 0),
      0
    ) / (completed || 1)
  );
  return [
    {
      label: "Tổng bài thi",
      value: total,
      icon: "bi-clipboard-data",
      color: "primary",
    },
    {
      label: "Hoàn thành",
      value: completed,
      icon: "bi-check-circle-fill",
      color: "success",
    },
    {
      label: "Điểm trung bình",
      value: isNaN(avgScore) ? "-" : avgScore,
      icon: "bi-bar-chart-line",
      color: "info",
    },
    {
      label: "Độ chính xác TB",
      value: totalQuestions
        ? ((totalCorrect / totalQuestions) * 100).toFixed(1) + "%"
        : "-",
      icon: "bi-graph-up",
      color: "warning",
    },
  ];
});

const chartData = computed(() => {
  const labels = [...new Set(filteredTests.value.map((t) => t.user))];
  const scores = labels.map((user) => {
    const tests = filteredTests.value.filter(
      (t) => t.user === user && t.completed
    );
    return Math.round(
      tests.reduce((acc, t) => acc + (t.total_score || 0), 0) /
        (tests.length || 1)
    );
  });
  return {
    labels,
    datasets: [
      {
        label: "Điểm trung bình",
        data: scores,
        backgroundColor: "#0d6efd",
      },
    ],
  };
});

function applyFilters() {
  // trigger lại filteredTests
}
</script>

<style scoped>
.table th,
.table td {
  vertical-align: middle;
}
</style>
