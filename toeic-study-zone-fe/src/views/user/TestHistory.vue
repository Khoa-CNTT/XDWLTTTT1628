<template>
  <!-- Banner -->
  <div class="study-banner text-center py-4 mb-3">
    <div class="container">
      <div class="d-flex justify-content-between align-items-center flex-wrap">
        <h2 class="fw-bold text-dark mb-0">
          <i class="bi bi-bar-chart-fill me-2"></i> Kết quả luyện thi
        </h2>
      </div>
    </div>
  </div>
  <div class="container py-4">
    <!-- Nút chuyển trang thống kê -->
    <div class="text-center mb-4">
      <button class="btn btn-outline-primary rounded-pill">
        <i class="bi bi-bar-chart-fill me-2"></i>
        Tiếp tục luyện tập
      </button>
    </div>

    <!-- Danh sách lịch sử làm bài -->
    <div
      v-for="(test, index) in testHistories"
      :key="index"
      class="card mb-3 shadow-sm test-card"
    >
      <div class="card-body">
        <h6 class="test-title">{{ test.title }}</h6>

        <div class="row small text-muted mb-2">
          <div class="col-md-3"><strong>Ngày làm:</strong> {{ test.date }}</div>
          <div class="col-md-3">
            <strong>Kết quả:</strong> {{ test.result }}
          </div>
          <div class="col-md-3">
            <strong>Thời gian làm bài:</strong> {{ test.duration }}
          </div>
          <div class="col-md-3 text-end">
            <a :href="test.detailLink" class="text-decoration-none"
              >Xem chi tiết</a
            >
          </div>
        </div>

        <div class="d-flex flex-wrap gap-2">
          <span
            v-for="(tag, i) in test.tags"
            :key="i"
            class="badge bg-warning text-dark"
          >
            {{ tag }}
          </span>
        </div>
      </div>
    </div>

    <!-- Phân trang -->
    <div class="d-flex justify-content-center mt-4">
      <nav>
        <ul class="pagination pagination-sm mb-0">
          <li class="page-item" :class="{ disabled: currentPage === 1 }">
            <button class="page-link" @click="prevPage">«</button>
          </li>
          <li
            v-for="page in totalPages"
            :key="page"
            class="page-item"
            :class="{ active: page === currentPage }"
          >
            <button class="page-link" @click="goToPage(page)">
              {{ page }}
            </button>
          </li>
          <li
            class="page-item"
            :class="{ disabled: currentPage === totalPages }"
          >
            <button class="page-link" @click="nextPage">»</button>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const currentPage = ref(1);
const totalPages = 3;

const goToPage = (page) => {
  if (page >= 1 && page <= totalPages) currentPage.value = page;
};
const prevPage = () => {
  if (currentPage.value > 1) currentPage.value--;
};
const nextPage = () => {
  if (currentPage.value < totalPages) currentPage.value++;
};

const testHistories = ref([
  {
    title: "New Economy TOEIC Test 4",
    date: "10/04/2025",
    result: "1/6",
    duration: "00:00:16",
    tags: ["Luyện thi", "Part 1"],
    detailLink: "#",
  },
  {
    title: "2024 Practice Set TOEIC Test 8",
    date: "06/01/2025",
    result: "116/200 (Điểm: 585)",
    duration: "1:00:31",
    tags: ["Full test"],
    detailLink: "#",
  },
  {
    title: "2024 Practice Set TOEIC Test 6",
    date: "04/01/2025",
    result: "98/200 (Điểm: 495)",
    duration: "01:30:11",
    tags: ["Full test"],
    detailLink: "#",
  },
  {
    title: "2024 Practice Set TOEIC Test 4",
    date: "04/01/2025",
    result: "24/100",
    duration: "01:08:09",
    tags: ["Luyện thi", "Part 2", "Part 3"],
    detailLink: "#",
  },
  {
    title: "2024 Practice Set TOEIC Test 5",
    date: "04/01/2025",
    result: "28/46",
    duration: "00:23:31",
    tags: ["Luyện thi", "Part 3"],
    detailLink: "#",
  },
  {
    title: "2024 Practice Set TOEIC Test 5",
    date: "04/01/2025",
    result: "16/31",
    duration: "00:01:29",
    tags: ["Luyện thi", "Part 2"],
    detailLink: "#",
  },
]);
</script>

<style scoped>
.study-banner {
  background: linear-gradient(to right, #fff8dc, #eac8d3);
  border-bottom: 5px solid #eee;
}
.container {
  max-width: 850px;
}

.test-card {
  border-radius: 0.75rem;
  padding: 1.2rem;
}

.test-title {
  font-weight: bold;
  text-transform: uppercase;
  font-size: 1rem;
  margin-bottom: 0.5rem;
  color: #333;
  text-align: left;
  word-wrap: break-word;
}

.badge {
  font-size: 0.75rem;
  padding: 0.35em 0.6em;
  border-radius: 0.4rem;
}
</style>
