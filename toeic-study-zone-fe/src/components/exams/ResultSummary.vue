<template>
  <div class="result-summary-modal">
    <!-- Tiêu đề pop-up -->
    <div class="modal-header">
      <h5>Đáp án chi tiết #{{ question.questionNumber }}</h5>
      <button class="close-btn" @click="$emit('close')">
        <i class="fas fa-times"></i>
      </button>
    </div>

    <!-- Nội dung pop-up -->
    <div class="modal-body">
      <!-- Tiêu đề bài thi -->
      <p class="test-title">
        Practice Set 2023 TOEIC Test 10 <br />
        <small>#[Part {{ question.part }}] Tranh tách nguồn và vật</small>
      </p>

      <!-- File audio -->
      <div class="audio-player" v-if="question.audio">
        <audio controls :src="question.audio">
          Your browser does not support the audio element.
        </audio>
      </div>

      <!-- Hình ảnh -->
      <div class="question-image" v-if="question.image">
        <img :src="question.image" alt="Question Image" />
      </div>

      <!-- Nội dung câu hỏi -->
      <div class="question-text" v-if="question.questionText">
        <h6>Câu hỏi</h6>
        <!-- Chỉ hiển thị câu hỏi cụ thể (questionText) -->
        <p class="question-specific">
          {{ question.questionText }}
        </p>
      </div>

      <!-- Các lựa chọn đáp án -->
      <div class="answer-options">
        <div
          class="option"
          v-for="(option, index) in question.options"
          :key="index"
          :class="{
            correct: option.value === question.correctAnswer,
            selected: option.value === question.selectedAnswer,
            wrong:
              option.value === question.selectedAnswer &&
              option.value !== question.correctAnswer,
          }"
        >
          <input
            type="radio"
            :id="'option-' + index"
            :value="option.value"
            disabled
            :checked="option.value === question.selectedAnswer"
          />
          <label :for="'option-' + index"
            >{{ option.value }}. {{ option.text }}</label
          >
        </div>
      </div>

      <!-- Transcript -->
      <div class="transcript-section" v-if="question.transcript">
        <a href="#" class="transcript-toggle" @click.prevent="toggleTranscript"
          >{{ showTranscript ? "Ẩn Transcript" : "Hiển Transcript" }}
          <i
            :class="
              showTranscript ? 'fas fa-chevron-up' : 'fas fa-chevron-down'
            "
            class="toggle-icon"
          ></i>
        </a>
        <div v-if="showTranscript" class="transcript-content">
          <p>{{ question.transcript }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  name: "ResultSummary",
  props: {
    question: {
      type: Object,
      required: true,
    },
  },
  setup() {
    const showTranscript = ref(false);

    const toggleTranscript = () => {
      showTranscript.value = !showTranscript.value;
    };

    return {
      showTranscript,
      toggleTranscript,
    };
  },
};
</script>

<style scoped>
.result-summary-modal {
  background: #fff;
  border-radius: 8px;
  max-width: 600px;
  width: 100%;
  margin: 0 auto;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  position: relative;
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border-bottom: 1px solid #e0e0e0;
  background-color: #f8f9fa;
}

.modal-header h5 {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.2rem;
  cursor: pointer;
  color: #666;
}

.close-btn:hover {
  color: #333;
}

.modal-body {
  padding: 20px;
  max-height: 80vh;
  overflow-y: auto;
}

.test-title {
  font-size: 1rem;
  color: #333;
  margin-bottom: 15px;
}

.test-title small {
  font-size: 0.85rem;
  color: #666;
}

.audio-player {
  margin-bottom: 20px;
}

.audio-player audio {
  width: 100%;
  outline: none;
}

.question-image img {
  width: 100%;
  max-width: 500px;
  height: auto;
  border-radius: 8px;
  margin-bottom: 20px;
  border: 1px solid #ddd;
}

.question-text {
  margin-bottom: 20px;
}

.question-text h6 {
  font-size: 1rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.question-specific {
  font-size: 0.95rem;
  color: #333;
  font-weight: 500;
  margin: 0;
  text-align: left;
}

.answer-options {
  margin-bottom: 20px;
}

.option {
  display: flex;
  align-items: center;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 5px;
  background-color: #f8f9fa;
}

.option.correct {
  background-color: #e8f5e9;
  border: 1px solid #4caf50;
}

.option.selected.wrong {
  background-color: #ffebee;
  border: 1px solid #ef5350;
}

/* Tùy chỉnh nút radio */
.option input[type="radio"] {
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  width: 16px;
  height: 16px;
  border: 2px solid #ccc; /* Màu viền mặc định */
  border-radius: 50%;
  margin-right: 10px;
  position: relative;
  cursor: not-allowed; /* Vì nút bị disabled */
}

/* Khi nút radio được checked */
.option input[type="radio"]:checked {
  border-color: #4caf50; /* Màu viền khi checked (mặc định là màu của đáp án đúng) */
  background-color: #4caf50; /* Màu nền khi checked */
}

/* Tạo dấu chấm bên trong khi checked */
.option input[type="radio"]:checked::after {
  content: "";
  width: 8px;
  height: 8px;
  background-color: #fff; /* Màu trắng cho dấu chấm */
  border-radius: 50%;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

/* Khi đáp án sai và được chọn */
.option.selected.wrong input[type="radio"]:checked {
  border-color: #ef5350; /* Màu viền đỏ khi sai */
  background-color: #ef5350; /* Màu nền đỏ khi sai */
}

/* Khi nút radio bị disabled */
.option input[type="radio"]:disabled {
  opacity: 0.6; /* Làm mờ nhẹ để biểu thị trạng thái disabled */
}

.option label {
  margin: 0;
  font-size: 0.95rem;
}

.transcript-section {
  margin-bottom: 20px;
}

.transcript-toggle {
  font-size: 0.95rem;
  color: #1877f2;
  text-decoration: none;
  display: flex;
  align-items: center;
  gap: 5px;
}

.transcript-toggle:hover {
  text-decoration: underline;
}

.toggle-icon {
  font-size: 0.8rem;
}

.transcript-content {
  margin-top: 10px;
  padding: 10px;
  background-color: #f1f8ff;
  border-radius: 5px;
  border: 1px solid #bbdefb;
  text-align: left;
}

.transcript-content p {
  margin: 0;
  font-size: 0.9rem;
  color: #333;
}
</style>
