<!-- src/components/exams/QuestionBlock.vue -->
<template>
  <div class="question-block">
    <!-- Hiển thị đoạn văn hoặc hình ảnh nếu có -->
    <ReadingPassage v-if="passage || image" :content="passage" :image="image" />

    <!-- File nghe (nếu có) -->
    <ListeningPlayer v-if="audio" :audio-source="audio" />

    <!-- Nội dung câu hỏi -->
    <div class="question-text">
      <span class="question-number">{{ questionId }}</span>
      <span class="question-content">{{ content }}</span>
    </div>

    <!-- Lựa chọn đáp án -->
    <div class="options">
      <AnswerOption
        v-for="option in options"
        :key="option.value"
        :option="option"
        :name="`question-${questionId}`"
        @click="selectOption(option.value)"
      />
    </div>

    <!-- Đường gạch dưới (nếu không phải câu hỏi cuối) -->
    <div v-if="!isLastQuestion" class="question-divider"></div>
  </div>
</template>

<script>
import ListeningPlayer from "./ListeningPlayer.vue";
import AnswerOption from "./AnswerOption.vue";
import ReadingPassage from "./ReadingPassage.vue";

export default {
  components: { ListeningPlayer, AnswerOption, ReadingPassage },
  props: {
    questionId: Number,
    image: String,
    audio: String,
    content: String,
    options: Array,
    isLastQuestion: Boolean,
    passage: String,
  },
  methods: {
    selectOption(optionValue) {
      this.$emit("select-option", this.questionId, optionValue);
    },
  },
};
</script>

<style scoped>
/* Giữ nguyên style hiện tại */
.question-block {
  margin-bottom: 1rem;
  padding-bottom: 1rem;
}
.question-image {
  width: 100%;
  max-width: 650px;
  height: auto;
  object-fit: contain;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
.question-text {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 10px;
}
.question-number {
  border-radius: 50%;
  background-color: #e8f2ff;
  color: #030303;
  width: 35px;
  height: 35px;
  line-height: 35px;
  font-size: 16px;
  font-weight: 600;
  text-align: center;
  display: inline-block;
}
.question-content {
  font-size: 14px;
  font-weight: bold;
  color: #333;
}
.question-block .options {
  display: flex;
  flex-direction: column;
  gap: 10px;
  align-items: flex-start;
}
.question-divider {
  margin-top: 10px;
  border-bottom: 2px solid #ddd;
}
</style>
