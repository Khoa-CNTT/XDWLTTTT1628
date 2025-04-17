<template>
  <main class="container mt-4">
    <div class="row">
      <!-- Left Column: Test Content -->
      <div class="col-lg-8">
        <div class="test-content-wrapper">
          <!-- Test Title and Info -->
          <div class="d-flex align-items-center mb-2">
            <span class="badge text-dark me-2">#TOEIC</span>
            <h1 class="test-title mb-0">
              Practice Set TOEIC (old format) Test 1
            </h1>
          </div>

          <div class="btn-group mb-3">
            <button class="btn btn-sm btn-primary me-2">
              Thông tin đề thi
            </button>
          </div>

          <div class="test-info mb-4">
            <div class="d-flex align-items-center mb-2">
              <i class="far fa-clock me-2"></i>
              <span
                >Thời gian làm bài: 120 phút | 7 phần | 200 câu hỏi | 13 bình
                luận</span
              >
            </div>
            <div class="d-flex align-items-center mb-2">
              <i class="fas fa-users me-2"></i>
              <span><strong>33924 người đã luyện tập đề thi này</strong></span>
            </div>
            <p class="test-warning">
              Chú ý: để được quy đổi sang scaled score (ví dụ trên điểm 990 cho
              TOEIC hoặc 9.0 cho IELTS), vui lòng chọn chế độ làm FULL TEST.
            </p>
          </div>

          <!-- Practice Tabs -->
          <div class="practice-tabs mb-4">
            <ul class="nav nav-tabs border-0">
              <li class="nav-item">
                <a
                  class="nav-link"
                  :class="{ active: activeTab === 'practice' }"
                  href="#"
                  @click.prevent="activeTab = 'practice'"
                >
                  Luyện tập
                </a>
              </li>
              <li class="nav-item">
                <a
                  class="nav-link"
                  :class="{ active: activeTab === 'full-test' }"
                  href="#"
                  @click.prevent="activeTab = 'full-test'"
                >
                  Làm full test
                </a>
              </li>
            </ul>
          </div>

          <!-- Practice Section -->
          <div class="practice-section">
            <!-- Hiển thị phần "Luyện tập" -->
            <div v-if="activeTab === 'practice'">
              <div class="pro-tips mb-4">
                <p>
                  <i class="fas fa-leaf text-success me-2"></i>
                  <strong>Pro tips:</strong> Hình thức luyện tập từng phần và
                  chọn mức thời gian phù hợp sẽ giúp bạn tập trung vào giải đúng
                  các câu hỏi thay vì phải chịu áp lực hoàn thành bài thi.
                </p>
              </div>

              <div class="part-selection">
                <p class="mb-2">Chọn phần bài bạn muốn làm</p>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part1"
                    v-model="selectedParts"
                    value="Part 1"
                  />
                  <label class="form-check-label" for="part1"
                    >Part 1 (6 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part2"
                    v-model="selectedParts"
                    value="Part 2"
                  />
                  <label class="form-check-label" for="part2"
                    >Part 2 (25 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part3"
                    v-model="selectedParts"
                    value="Part 3"
                  />
                  <label class="form-check-label" for="part3"
                    >Part 3 (39 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part4"
                    v-model="selectedParts"
                    value="Part 4"
                  />
                  <label class="form-check-label" for="part4"
                    >Part 4 (30 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part5"
                    v-model="selectedParts"
                    value="Part 5"
                  />
                  <label class="form-check-label" for="part5"
                    >Part 5 (30 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-2">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part6"
                    v-model="selectedParts"
                    value="Part 6"
                  />
                  <label class="form-check-label" for="part6"
                    >Part 6 (16 câu hỏi)</label
                  >
                </div>
                <div class="form-check mb-4">
                  <input
                    class="form-check-input"
                    type="checkbox"
                    id="part7"
                    v-model="selectedParts"
                    value="Part 7"
                  />
                  <label class="form-check-label" for="part7"
                    >Part 7 (54 câu hỏi)</label
                  >
                </div>

                <div class="mb-4">
                  <label class="form-label"
                    >Giới hạn thời gian (Đề trống để làm bài không giới
                    hạn)</label
                  >
                  <select
                    class="form-select custom-time-select"
                    v-model="selectedTimeLimit"
                  >
                    <option value="">-- Chọn thời gian --</option>
                    <option
                      v-for="time in timeOptions"
                      :key="time"
                      :value="time"
                    >
                      {{ time }} phút
                    </option>
                  </select>
                </div>
                <button
                  class="btn btn-primary practice-btn"
                  @click="startPractice"
                >
                  LUYỆN TẬP
                </button>
              </div>
            </div>

            <!-- Hiển thị phần "Làm full test" -->
            <div v-if="activeTab === 'full-test'" class="full-test-section">
              <div class="full-test-notice mb-4">
                <p>
                  <i class="fas fa-exclamation-circle warning-icon"></i>
                  Sẵn sàng để bắt đầu làm full test? Để đạt được kết quả tốt
                  nhất, bạn cần dành ra 120 phút cho bài test này.
                </p>
              </div>
              <button
                class="btn btn-primary start-test-btn"
                @click="startFullTest"
              >
                BẮT ĐẦU THI
              </button>
            </div>
          </div>
        </div>

        <!-- Comments Section -->
        <div class="comments-wrapper mt-4">
          <h5 class="comment-title">Bình luận</h5>
          <Comments />
        </div>
      </div>

      <!-- Right Column: Sidebar -->
      <div class="col-lg-4" style="margin-top: 30px">
        <div class="sidebar">
          <!-- Thông tin người dùng -->
          <div class="user-info">
            <div class="avatar"></div>
            <div class="user-details">
              <h4>nguyenlongvu22122003</h4>
              <p>Ngày dự thi: 13/12/2025</p>
              <p>Ngành dự thi: TOEIC</p>
              <p>Điểm mục tiêu: 750</p>
              <button class="stats-button">
                <span class="icon">📊</span> Lịch sử bài thi
              </button>
            </div>
          </div>

          <!-- Banner quảng cáo -->
          <div class="promo-banner">
            <h3>Kiểm tra trình độ miễn phí</h3>
            <p>Nhanh chóng - Chính xác - Hiệu quả</p>
            <button class="start-button">START</button>
          </div>

          <!-- Nhóm học tập -->
          <div class="study-group">
            <h4>Trao đổi, học tập, Flashcards</h4>
            <p>StudyZone Extension</p>
            <button class="extension-button">Cài đặt ngay</button>
          </div>

          <!-- Cộng đồng -->
          <div class="community">
            <h4>Cộng đồng luyện thi TOEIC</h4>
            <p>Tham gia nhóm Facebook StudyZone</p>
            <button class="join-button">Tham gia ngay</button>
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<script>
import Comments from "./Comments.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";

export default {
  name: "TestPage",
  components: {
    Comments,
  },
  setup() {
    const router = useRouter();
    const selectedParts = ref([]); // Mảng để lưu các phần được chọn
    const selectedTimeLimit = ref(""); // Thời gian giới hạn
    const activeTab = ref("practice"); // Tab hiện tại: 'practice' hoặc 'full-test'

    // Tạo danh sách tùy chọn thời gian: 0 phút, 5 phút, ..., 135 phút
    const timeOptions = ref([]);
    for (let i = 0; i <= 135; i += 5) {
      timeOptions.value.push(i.toString());
    }

    const startPractice = () => {
      if (selectedParts.value.length === 0) {
        alert("Vui lòng chọn ít nhất một phần bạn muốn làm!");
        return;
      }

      // Chuyển hướng đến TestExam.vue với query parameters
      router.push({
        path: "/test-exam",
        query: {
          parts: selectedParts.value.join(","), // Truyền danh sách các phần được chọn
          timeLimit: selectedTimeLimit.value || "0", // 0 nghĩa là không giới hạn
        },
      });
    };

    const startFullTest = () => {
      // Chuyển hướng đến TestExam.vue với thời gian mặc định là 120 phút
      router.push({
        path: "/test-exam",
        query: {
          parts: "Part 1,Part 2,Part 3,Part 4,Part 5,Part 6,Part 7", // Bao gồm tất cả các phần
          timeLimit: "120", // Thời gian mặc định cho full test
        },
      });
    };

    return {
      selectedParts,
      selectedTimeLimit,
      activeTab,
      timeOptions,
      startPractice,
      startFullTest,
    };
  },
};
</script>

<style scoped>
/* CSS giữ nguyên */
.test-content-wrapper {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  text-align: left;
  margin: 30px 0;
}

.comments-wrapper {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  margin: 30px 0;
  text-align: left;
}

.form-select.custom-time-select {
  font-size: 0.9rem;
  padding: 0.5rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  background-color: #fff;
  color: #000;
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='none' stroke='currentColor' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'%3e%3c/polyline%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 0.5rem center;
  background-size: 1rem;
}

.custom-time-select:focus {
  border-color: #1877f2;
  box-shadow: 0 0 0 0.2rem rgba(24, 119, 242, 0.25);
  outline: none;
}

.custom-time-select option {
  background-color: #fff;
  color: #000;
  font-size: 0.9rem;
  padding: 0.5rem;
}

.custom-time-select::-webkit-scrollbar {
  width: 8px;
}

.custom-time-select::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 4px;
}

.custom-time-select::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.custom-time-select::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.custom-time-select option:hover,
.custom-time-select option:checked {
  background-color: #007bff;
  color: #fff;
}

.full-test-section {
  text-align: left;
}

.full-test-notice {
  background-color: #ffefd8;
  border: 1px solid #ffe8c8;
  border-radius: 6px;
  padding: 1rem;
  font-size: 0.9rem;
  color: #855a1f;
}

.full-test-notice p {
  margin: 0;
  display: flex;
  align-items: center;
}

.warning-icon {
  margin-right: 0.5rem;
  font-size: 1rem;
  color: #e74c3c;
}

.start-test-btn {
  background-color: #1877f2;
  border-color: #1877f2;
  text-transform: uppercase;
  font-weight: 600;
  padding: 0.75rem 1.5rem;
  letter-spacing: 0.5px;
  font-size: 0.9rem;
  border-radius: 6px;
  transition: background-color 0.2s ease;
}

.start-test-btn:hover {
  background-color: #145dbf;
}

.test-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
}

.badge {
  background-color: #484a4c33;
  font-size: 0.75rem;
  font-weight: 500;
  border-radius: 15px;
  padding: 0.35rem 0.5rem;
}

.btn-primary {
  background-color: #1877f2;
  border-color: #1877f2;
}

.test-info {
  background-color: #f8f9fa;
  border-radius: 6px;
  padding: 1rem;
  font-size: 0.9rem;
}

.test-warning {
  color: #e74c3c;
  font-size: 1rem;
  margin-top: 0.5rem;
  margin-bottom: 0;
}

.practice-tabs .nav-link {
  color: #606060;
  font-size: 0.9rem;
  padding: 0.5rem 1rem;
  margin-right: 1rem;
  border: none;
  border-bottom: 2px solid transparent;
}

.practice-tabs .nav-link.active {
  font-weight: 600;
  color: #1877f2;
  border-bottom: 2px solid #1877f2;
  background-color: transparent;
}

.pro-tips {
  background-color: #e8f5e9;
  border: 1px solid #c8e6c9;
  border-radius: 6px;
  padding: 0.75rem 1rem;
  font-size: 1rem;
  color: #2e7d32;
}

.part-selection {
  font-size: 0.9rem;
}

.form-check-input {
  border: 1px solid #333;
}

.form-check-label {
  font-size: 0.9rem;
  color: #030303c3;
}

.form-select {
  font-size: 0.9rem;
  padding: 0.5rem;
}

.practice-btn {
  text-transform: uppercase;
  font-weight: 600;
  padding: 0.5rem 1rem;
  letter-spacing: 0.5px;
}

.comment-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 1rem;
}

.sidebar {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.user-info {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-bottom: 40px;
}

.avatar {
  width: 50px;
  height: 50px;
  background-color: #e0e0e0;
  border-radius: 50%;
  flex-shrink: 0;
  margin-top: 10px;
}

.user-details {
  flex: 1;
  padding: 10px 20px;
  text-align: left;
}

.user-details h4 {
  font-size: 15px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 12px;
}

.user-details p {
  font-size: 14px;
  color: #606770;
  margin: 6px 0;
  text-align: left;
}

.stats-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 3px;
  margin-top: 20px;
  transition: all 0.3s ease;
}

.stats-button:hover {
  background-color: #145dbf;
}

.promo-banner {
  background: linear-gradient(135deg, #7794b6, #ada6e3);
  border-radius: 8px;
  padding: 15px;
  text-align: center;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.promo-banner h3 {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 8px;
}

.promo-banner p {
  font-size: 13px;
  margin-bottom: 10px;
}

.start-button {
  background-color: white;
  color: #3681dd;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-weight: 600;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.start-button:hover {
  background-color: #4fd36b;
  color: white;
  box-shadow: 0 0 10px rgba(40, 167, 69, 0.8);
}

.study-group {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.study-group h4 {
  font-size: 14px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 8px;
}

.study-group p {
  font-size: 12px;
  color: #606770;
  margin-bottom: 10px;
}

.extension-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.extension-button:hover {
  background-color: #145dbf;
}

.community {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.community h4 {
  font-size: 14px;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 8px;
}

.community p {
  font-size: 12px;
  color: #606770;
  margin-bottom: 10px;
}

.join-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 13px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.join-button:hover {
  background-color: #145dbf;
}

@media (max-width: 1024px) {
  .sidebar {
    flex-direction: row;
    flex-wrap: wrap;
    justify-content: space-between;
  }

  .user-info,
  .promo-banner,
  .study-group,
  .community {
    flex: 1 1 45%;
  }
}

@media (max-width: 768px) {
  .sidebar {
    flex-direction: column;
  }

  .user-info,
  .promo-banner,
  .study-group,
  .community {
    flex: 1 1 100%;
  }
}
</style>
