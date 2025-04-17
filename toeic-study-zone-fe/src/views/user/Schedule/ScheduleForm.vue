<template>
  <div>
    <!-- Banner -->
    <div class="study-banner text-center py-4 mb-3">
      <div class="container">
        <div
          class="d-flex justify-content-between align-items-center flex-wrap"
        >
          <h2 class="fw-bold text-dark">
            <i class="bi bi-calendar-week-fill me-2"></i> Lịch học
          </h2>
        </div>

        <!-- Tabs -->
        <div class="d-flex justify-content-start gap-2 mt-3">
          <button class="btn btn-light rounded-pill px-4 py-1 fw-semibold">
            Lịch học của tôi
          </button>
          <button
            class="btn btn-outline-primary rounded-pill px-4 py-1 fw-semibold"
            @click="goToDiscover"
          >
            Khám phá
          </button>
        </div>
      </div>
    </div>

    <!-- Nội dung -->
    <div class="container py-4">
      <div class="row">
        <!-- Cột chính -->
        <div class="col-lg-9">
          <div class="card p-4 shadow-sm">
            <!-- Header -->
            <div class="d-flex justify-content-between align-items-start mb-3">
              <div>
                <h5 class="fw-bold mb-1">
                  Complete TOEIC 650+
                  <i class="bi bi-pencil-square text-secondary"></i>
                </h5>
                <p class="mb-1"><strong>Khóa học:</strong> Complete TOEIC</p>
              </div>
              <span class="badge bg-success">Active</span>
            </div>

            <!-- Mô tả -->
            <ul class="small ps-3 mb-3 text-start">
              <li>Dành cho các bạn học viên target 650+</li>
              <li>
                Chỉ luyện 1 part cho đến khi đạt 70-80%, rồi mới chuyển part
                khác
              </li>
              <li>Listening: part 1 → part 2 → part 3 → part 4</li>
              <li>Reading: part 5 → part 6 → part 7</li>
              <li>Chú ý không luyện nhiều part cùng lúc</li>
            </ul>

            <!-- Tabs nhỏ -->
            <ul class="nav nav-tabs mb-3">
              <li class="nav-item">
                <a class="nav-link active" href="#">Hôm nay cần làm</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Theo tuần</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Chỉnh sửa/Thêm mới</a>
              </li>
            </ul>

            <!-- Checklist -->
            <div class="row">
              <div class="col-md-4 mb-3" v-for="(task, i) in tasks" :key="i">
                <div class="border rounded p-3 h-100">
                  <h6 :class="task.colorClass" class="fw-bold mb-2">
                    {{ task.title }}
                  </h6>
                  <ul class="list-unstyled mb-0 text-start">
                    <li
                      v-for="(item, j) in task.items"
                      :key="j"
                      class="form-check text-start"
                    >
                      <input
                        class="form-check-input"
                        type="checkbox"
                        :id="`task-${i}-${j}`"
                      />
                      <label
                        class="form-check-label small text-dark"
                        :for="`task-${i}-${j}`"
                      >
                        {{ item }}
                      </label>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div class="text-end">
              <button class="btn btn-outline-primary mt-3">
                Sử dụng lịch học này
              </button>
            </div>
          </div>
        </div>

        <!-- Sidebar quảng cáo -->
        <div class="col-lg-3">
          <div class="sticky-top" style="top: 100px">
            <div class="mb-3">
              <img
                src="https://via.placeholder.com/300x150?text=Quang+cao+1"
                class="img-fluid rounded shadow-sm"
              />
            </div>
            <div class="mb-3">
              <img
                src="https://via.placeholder.com/300x150?text=Quang+cao+2"
                class="img-fluid rounded shadow-sm"
              />
            </div>
            <div>
              <img
                src="https://via.placeholder.com/300x150?text=Quang+cao+3"
                class="img-fluid rounded shadow-sm"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

const goToDiscover = () => {
  router.push("/schedule-discover");
};

const tasks = [
  {
    title: "Reading: Hàng ngày",
    colorClass: "text-primary",
    items: [
      "Làm riêng từng part bạn muốn luyện tập (1 phút/câu)",
      "Tự chữa các câu làm sai mà không đọc giải thích",
      "Đọc giải thích chi tiết hoặc xem clip bài giảng",
    ],
  },
  {
    title: "Listening: Hàng ngày",
    colorClass: "text-success",
    items: [
      "Làm riêng từng part luyện tập (1 phút/câu)",
      "Tự chữa câu sai mà không đọc script",
      "Nghe chép chính tả, đọc transcript, xem video nếu cần",
    ],
  },
  {
    title: "Từ vựng/ngữ pháp: Hàng ngày",
    colorClass: "text-info",
    items: [
      "Học từ vựng (flashcards 20-30 từ/ngày)",
      "Làm bài tập ngữ pháp (1-2 ngày học 1 chủ đề)",
    ],
  },
];
</script>

<style scoped>
.study-banner {
  background: linear-gradient(to right, #fff8dc, #eac8d3);
  border-bottom: 1px solid #eee;
}
.card {
  border-radius: 1rem;
}
.nav-tabs .nav-link.active {
  font-weight: bold;
  border-color: transparent transparent #0d6efd;
}
</style>
