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
                <a
                  class="nav-link"
                  :class="{ active: activeTab === 'today' }"
                  href="#"
                  @click.prevent="activeTab = 'today'"
                  >Hôm nay cần làm</a
                >
              </li>
            </ul>

            <!-- Tab: Hôm nay cần làm -->
            <div v-if="activeTab === 'today'">
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
              <div class="text-end mt-3">
                <button
                  class="btn btn-complete px-4 py-2 rounded-pill fw-semibold"
                >
                  <i class="bi bi-check-circle-fill me-1"></i> Hoàn thành
                </button>
              </div>
            </div>

            <!-- Tab: Chỉnh sửa/Thêm mới -->
            <div v-if="activeTab === 'edit'">
              <p class="text-danger small mb-3">
                Click vào mỗi task <em>để chỉnh sửa</em> hoặc vào nút
                <strong class="text-danger">X</strong> màu đỏ để xóa.
              </p>

              <div class="row">
                <div
                  class="col-md-4 mb-4"
                  v-for="(todo, index) in editableTodos"
                  :key="index"
                >
                  <div class="border rounded shadow-sm p-3 bg-white">
                    <div
                      class="d-flex justify-content-between align-items-start mb-2"
                    >
                      <span
                        class="fw-bold px-2 py-1 rounded text-white"
                        :style="{ backgroundColor: todo.color }"
                      >
                        {{ todo.title }}
                      </span>
                      <div class="d-flex gap-2">
                        <i
                          class="bi bi-pencil-square text-primary cursor-pointer"
                        ></i>
                        <i
                          class="bi bi-trash-fill text-danger cursor-pointer"
                          @click="deleteTodo(index)"
                        ></i>
                      </div>
                    </div>
                    <div class="mb-2">
                      <button class="btn btn-sm btn-light w-100 text-start">
                        <i class="bi bi-plus-circle me-1"></i> Thêm task
                      </button>
                    </div>
                    <ul class="ps-3 mb-0">
                      <li v-for="(task, i) in todo.tasks" :key="i">
                        {{ task }}
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
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
import { ref } from "vue";
const router = useRouter();

const goToDiscover = () => {
  router.push("/schedule-discover");
};

const activeTab = ref("today");

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

const editableTodos = ref([
  {
    title: "Reading: Hàng ngày",
    color: "#0d6efd",
    tasks: [
      "Làm riêng từng part bạn muốn luyện tập (bấm thời gian 1 phút/câu)",
      "Tự chữa các câu làm sai mà không đọc giải thích",
      "Đọc giải thích chi tiết các câu không tự chữa được, hoặc xem clip bài giảng / chữa đề",
    ],
  },
  {
    title: "Listening: Hàng ngày",
    color: "#198754",
    tasks: [
      "Làm riêng từng part bạn muốn luyện tập (bấm thời gian 1 phút/câu)",
      "Tự chữa các câu làm sai mà không đọc script",
      "Nghe chép chính tả bài vừa làm, tốc độ 1.1x hoặc 1.25x",
      "Đọc transcript, giải thích hoặc xem video bài giảng nếu cần",
    ],
  },
  {
    title: "Từ vựng/ngữ pháp: Hàng ngày",
    color: "#0dcaf0",
    tasks: [
      "Học từ vựng (flashcards mỗi ngày 20-30 từ)",
      "Học và làm bài tập ngữ pháp (1-2 ngày học 1 chủ đề ngữ pháp)",
    ],
  },
]);

function addTodo() {
  editableTodos.value.push({
    title: "To-do mới",
    color: "#6c757d",
    tasks: [],
  });
}

function deleteTodo(index) {
  editableTodos.value.splice(index, 1);
}
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
.btn-complete {
  padding: 6px 18px;
  font-weight: 500;
  font-size: 14px;
  border-radius: 6px;
  background-color: #1987545e;
  border: none;
}

.btn-complete:hover {
  background-color: #1cb26ceb;
}
</style>
