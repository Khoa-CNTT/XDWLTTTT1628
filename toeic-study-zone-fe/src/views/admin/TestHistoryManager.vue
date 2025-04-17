<template>
  <div class="p-4">
    <h4 class="fw-bold mb-3">
      <i class="bi bi-person-lines-fill me-2"></i> Quản trị - Lịch sử bài thi
      người dùng
    </h4>

    <!-- Bộ lọc và tìm kiếm -->
    <div class="row mb-4">
      <div class="col-md-4">
        <input
          type="text"
          v-model="search"
          class="form-control"
          placeholder="Tìm theo người dùng..."
        />
      </div>
    </div>

    <!-- Bảng dữ liệu người dùng -->
    <div class="table-responsive">
      <table class="table table-hover table-bordered align-middle">
        <thead class="table-light">
          <tr>
            <th>#</th>
            <th>Người dùng</th>
            <th>Số bài thi</th>
            <th>Tổng điểm cao nhất</th>
            <th>Thao tác</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(group, i) in filteredUsers" :key="group.user">
            <td>{{ i + 1 }}</td>
            <td>{{ group.user }}</td>
            <td>{{ group.tests.length }}</td>
            <td>{{ group.maxScore }}</td>
            <td>
              <button
                class="btn btn-sm btn-outline-info"
                @click="viewUserDetails(group.userId, group.user)"
              >
                Chi tiết
              </button>
            </td>
          </tr>
          <tr v-if="filteredUsers.length === 0">
            <td colspan="5" class="text-center text-muted">
              Không có dữ liệu phù hợp
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Modal Chi tiết -->
    <div
      v-if="showDetailModal"
      class="modal-backdrop d-flex align-items-center justify-content-center"
    >
      <div
        class="modal-content bg-white rounded shadow p-4"
        style="max-width: 1000px; width: 100%"
      >
        <div class="d-flex justify-content-between align-items-center mb-4">
          <h5 class="fw-bold mb-0">Chi tiết bài thi: {{ selectedUserName }}</h5>
          <button class="btn-close" @click="showDetailModal = false"></button>
        </div>

        <div v-for="(test, i) in userTests" :key="i" class="mb-5">
          <h6 class="fw-bold mb-3 text-primary">
            Bài {{ i + 1 }}: {{ test.testTitle }}
          </h6>

          <!-- Kết quả tổng quan -->
          <div class="row g-3 mb-3">
            <div class="col-md-3">
              <div class="border rounded p-3 bg-light">
                <div class="fw-semibold mb-2">✔ Kết quả làm bài</div>
                <div class="fw-bold">
                  {{ test.correct_answers }}/{{ test.total_questions || 200 }}
                </div>
                <hr />
                <div class="mb-2">🎯 Độ chính xác</div>
                <div class="fw-bold">
                  {{
                    (
                      (test.correct_answers / (test.total_questions || 200)) *
                      100
                    ).toFixed(1)
                  }}%
                </div>
                <hr />
                <div class="mb-2">⏱ Thời gian hoàn thành</div>
                <div class="fw-bold">{{ test.duration || "00:00:00" }}</div>
              </div>
            </div>

            <div
              class="col-md-2"
              v-for="(box, j) in [
                {
                  label: 'Trả lời đúng',
                  value: test.correct_answers,
                  color: 'success',
                },
                {
                  label: 'Trả lời sai',
                  value:
                    (test.total_questions || 200) -
                    test.correct_answers -
                    (test.skipped || 0),
                  color: 'danger',
                },
                {
                  label: 'Bỏ qua',
                  value: test.skipped || 0,
                  color: 'secondary',
                },
                {
                  label: 'Điểm',
                  value: test.total_score || 0,
                  color: 'primary',
                },
              ]"
              :key="j"
            >
              <div
                class="text-center border rounded p-3 h-100 d-flex flex-column justify-content-center align-items-center"
              >
                <div :class="`text-${box.color} fw-bold`">{{ box.label }}</div>
                <div class="fw-semibold">{{ box.value }} câu hỏi</div>
              </div>
            </div>
          </div>

          <!-- Listening & Reading -->
          <div class="row g-4">
            <div class="col-md-6">
              <div class="border rounded p-3 text-center">
                <div class="fw-bold text-primary">Listening</div>
                <div class="fs-5 fw-semibold">
                  {{ test.listening_score || 0 }}/495
                </div>
                <div class="text-muted small">
                  Trả lời đúng: {{ test.correct_listening || 0 }}/100
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="border rounded p-3 text-center">
                <div class="fw-bold text-primary">Reading</div>
                <div class="fs-5 fw-semibold">
                  {{ test.reading_score || 0 }}/495
                </div>
                <div class="text-muted small">
                  Trả lời đúng: {{ test.correct_reading || 0 }}/100
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";

const search = ref("");
const showDetailModal = ref(false);
const selectedUserId = ref(null);
const selectedUserName = ref("");
const userTests = ref([]);

const rawData = ref([
  {
    userId: 2,
    user: "Lê Thị Ánh Ngọc",
    testTitle: "TOEIC Practice Test 3",
    total_score: 495,
    correct_answers: 98,
    total_questions: 200,
    skipped: 1,
    duration: "00:13:01",
    listening_score: 300,
    reading_score: 195,
    correct_listening: 58,
    correct_reading: 40,
    completed: true,
  },
  {
    userId: 2,
    user: "Lê Thị Ánh Ngọc",
    testTitle: "TOEIC Practice Test 4",
    total_score: 690,
    correct_answers: 150,
    total_questions: 200,
    skipped: 0,
    duration: "01:30:00",
    listening_score: 360,
    reading_score: 330,
    correct_listening: 75,
    correct_reading: 75,
    completed: true,
  },
]);

const groupedUsers = computed(() => {
  const map = new Map();
  for (const item of rawData.value) {
    if (!map.has(item.userId)) {
      map.set(item.userId, {
        userId: item.userId,
        user: item.user,
        tests: [],
        maxScore: 0,
      });
    }
    const group = map.get(item.userId);
    group.tests.push(item);
    if (item.total_score && item.total_score > group.maxScore) {
      group.maxScore = item.total_score;
    }
  }
  return Array.from(map.values());
});

const filteredUsers = computed(() => {
  return groupedUsers.value.filter((group) =>
    group.user.toLowerCase().includes(search.value.toLowerCase())
  );
});

function viewUserDetails(userId, userName) {
  selectedUserId.value = userId;
  selectedUserName.value = userName;
  userTests.value = rawData.value.filter((t) => t.userId === userId);
  showDetailModal.value = true;
}
</script>

<style scoped>
.table th,
.table td {
  vertical-align: middle;
}
.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1050;
}
.modal-content {
  max-height: 90vh;
  overflow-y: auto;
}
</style>
