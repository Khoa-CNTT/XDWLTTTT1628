<template>
  <!-- Banner-->
  <div class="study-banner py-5 mb-3">
    <div class="container">
      <div class="text-start">
        <h2 class="fw-bold text-dark mb-0">
          <i class="bi bi-clipboard-check-fill me-2"></i>
          Kiểm tra đầu vào miễn phí
        </h2>
      </div>
    </div>
  </div>
  <!-- Nội dung chọn bài -->
  <div class="container py-5" style="max-width: 700px">
    <div class="vstack gap-4">
      <!-- Option duy nhất -->
      <label class="exam-option border rounded px-4 py-4 active">
        <div class="form-check d-flex align-items-start">
          <input
            class="form-check-input mt-1 me-3"
            type="radio"
            name="exam"
            value="full-test"
            v-model="selectedOption"
            checked
          />
          <div class="text-start">
            <strong class="d-block fs-5"
              >TOEIC Full Test (Listening + Reading)</strong
            >
            <small class="text-muted d-block"
              >Thời gian làm bài: 02:00:00</small
            >
            <small class="text-muted"
              >Bài thi gồm 2 phần - Bài Nghe (100 câu) và Bài Đọc (100
              câu)</small
            >
          </div>
        </div>
      </label>

      <!-- Nút bắt đầu -->
      <div class="text-center mt-3">
        <button class="btn btn-primary px-5 py-2" :disabled="!selectedOption">
          Bắt đầu làm bài
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
const selectedOption = ref("full-test");
</script>

<style scoped>
.study-banner {
  background: linear-gradient(to right, #fff8dc, #eac8d3);
  border-bottom: 5px solid #eee;
}

.exam-option {
  transition: border 0.3s ease;
  background-color: white;
}

.exam-option.active {
  border: 2px solid #007bff;
  background-color: #f8f9ff;
}

.form-check-input:checked {
  background-color: #007bff;
  border-color: #007bff;
}
</style>
