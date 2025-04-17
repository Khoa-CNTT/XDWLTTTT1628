<template>
  <div>
    <!--Banner -->
    <div class="study-banner text-center py-4 mb-3">
      <div class="container">
        <div
          class="d-flex justify-content-between align-items-center flex-wrap"
        >
          <h2 class="fw-bold text-dark">
            <i class="bi bi-calendar-week-fill me-2"></i> Lịch học
          </h2>
        </div>

        <!-- Tabs căn trái -->
        <div class="d-flex justify-content-start gap-2 mt-3">
          <router-link
            to="/schedule-form"
            class="btn custom-btn rounded-pill px-4 py-1 fw-semibold"
            :class="
              $route.path === '/schedule' ? 'btn-light' : 'btn-outline-light'
            "
          >
            Lịch học của tôi
          </router-link>
          <router-link
            to="/schedule-discover"
            class="btn rounded-pill px-4 py-1 fw-semibold"
            :class="
              $route.path === '/schedule-discover'
                ? 'btn-primary'
                : 'btn-outline-primary'
            "
          >
            Khám phá
          </router-link>
        </div>
      </div>
    </div>

    <!-- Nội dung -->
    <div class="container py-4">
      <div class="row">
        <!-- Main Content -->
        <div class="col-lg-9">
          <div
            v-for="(plan, index) in studyPlans"
            :key="index"
            class="card p-4 shadow-sm mb-4"
          >
            <div class="d-flex justify-content-between align-items-start mb-2">
              <div>
                <h5 class="fw-bold mb-1">
                  {{ plan.title }}
                  <i class="bi bi-pencil-square text-secondary"></i>
                </h5>
                <p class="mb-1"><strong>Khóa học:</strong> {{ plan.course }}</p>
              </div>
              <span class="badge bg-success">Active</span>
            </div>

            <ul class="small ps-3 mb-3 text-start">
              <li v-for="(line, i) in plan.description" :key="i">{{ line }}</li>
            </ul>

            <div class="row">
              <div
                class="col-md-4 mb-3"
                v-for="(box, i) in plan.tasks"
                :key="i"
              >
                <div class="border rounded p-3 h-100">
                  <h6 :class="box.colorClass" class="fw-bold mb-2">
                    {{ box.title }}
                  </h6>
                  <ul class="list-unstyled mb-0 text-start">
                    <li
                      v-for="(item, j) in box.items"
                      :key="j"
                      class="form-check d-flex align-items-start"
                    >
                      <input
                        class="form-check-input mt-1 me-2"
                        type="checkbox"
                        :id="`task-${index}-${i}-${j}`"
                      />
                      <label
                        class="form-check-label small"
                        :for="`task-${index}-${i}-${j}`"
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
                src="https://via.placeholder.com/300x150?text=Ad+1"
                class="img-fluid rounded shadow-sm"
              />
            </div>
            <div class="mb-3">
              <img
                src="https://via.placeholder.com/300x150?text=Ad+2"
                class="img-fluid rounded shadow-sm"
              />
            </div>
            <div>
              <img
                src="https://via.placeholder.com/300x150?text=Ad+3"
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
const studyPlans = [
  {
    title: "Complete TOEIC 650+",
    course: "Complete TOEIC",
    description: [
      "Dành cho các bạn học viên target 650+",
      "Chỉ luyện 1 part cho đến khi đạt 70-80%, rồi mới chuyển part khác",
      "Listening: part 1 → part 2 → part 3 → part 4",
      "Reading: part 5 → part 6 → part 7",
      "Chú ý không luyện nhiều part cùng lúc",
    ],
    tasks: [
      {
        title: "Reading: Hàng ngày",
        colorClass: "text-primary",
        items: [
          "Làm riêng từng part luyện tập (1 phút/câu)",
          "Tự chữa các câu sai mà không đọc giải thích",
          "Đọc giải thích hoặc xem clip giảng",
        ],
      },
      {
        title: "Listening: Hàng ngày",
        colorClass: "text-success",
        items: [
          "Làm riêng từng part luyện tập (1 phút/câu)",
          "Tự chữa câu sai không cần đọc script",
          "Nghe chép chính tả + đọc transcript",
        ],
      },
      {
        title: "Từ vựng/ngữ pháp: Hàng ngày",
        colorClass: "text-info",
        items: [
          "Học từ vựng flashcard mỗi ngày 20-30 từ",
          "Làm bài tập ngữ pháp 1 chủ đề/ngày",
        ],
      },
    ],
  },
];
</script>

<style scoped>
.custom-btn {
  color: black;
  border: 1px solid rgb(198, 189, 189);
}

.study-banner {
  background: linear-gradient(to right, #fff8dc, #eac8d3);
  border-bottom: 1px solid #eee;
}

.card {
  border-radius: 1rem;
}
</style>
