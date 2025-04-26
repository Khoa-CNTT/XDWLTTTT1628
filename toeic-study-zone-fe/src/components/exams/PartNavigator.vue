<template>
  <div class="part-navigator">
    <ExamTimer :time-limit="timeLimit" />
    <h3>Điều hướng câu hỏi</h3>
    <div class="question-grid">
      <!-- Nhóm câu hỏi theo từng Part -->
      <div v-for="part in selectedParts" :key="part" class="part-section">
        <h4>Part {{ part }}</h4>
        <div class="part-questions">
          <button
            v-for="question in questionsByPart[part]"
            :key="question.id"
            :class="{
              answered: examStore.answers[question.id],
              active: question.id === currentQuestionId,
            }"
            @click="$emit('navigate', question.id)"
          >
            {{ question.id }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useExamStore } from "@/store/useExamStore";
import ExamTimer from "@/components/exams/ExamTimer.vue";

export default {
  props: {
    questions: Array,
    currentQuestionId: Number,
    timeLimit: Number, // Nhận timeLimit từ TestExam.vue
  },
  setup() {
    const examStore = useExamStore();
    return { examStore };
  },
  components: {
    ExamTimer,
  },
  computed: {
    selectedParts() {
      // Lấy danh sách các phần từ questions, đảm bảo chỉ hiển thị các phần có trong dữ liệu
      return [...new Set(this.questions.map((q) => q.part))].sort();
    },
    questionsByPart() {
      const grouped = {};
      this.selectedParts.forEach((part) => {
        grouped[part] = this.questions.filter((q) => q.part === part);
      });
      return grouped;
    },
  },
};
</script>

<style scoped>
.part-navigator {
  width: 100%;
  padding: 20px;
  border: 1px solid #ddd;
  background: #ffffff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.part-navigator h3 {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.part-section {
  margin-bottom: 15px;
}

.part-section h4 {
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
  text-align: left;
}

.part-questions {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  gap: 4px;
}

.part-questions button {
  padding: 4px;
  border: 1px solid #080808;
  background: #fff;
  cursor: pointer;
  font-size: 12px;
  border-radius: 3px;
  transition: all 0.3s;
  text-align: center;
  color: #000000;
}

.part-questions button.answered {
  background: #d3f9d8;
  border-color: #28a745;
  color: #000000;
}

.part-questions button.active {
  background: #007bff;
  border-color: #007bff;
  color: white;
}
</style>
