<!-- src/components/exams/ReadingPassage.vue -->
<template>
  <div class="reading-passage">
    <!-- Hiển thị hình ảnh nếu có -->
    <img
      v-if="image"
      :src="image"
      alt="Passage Image"
      class="passage-image"
      @error="onImageError"
    />
    <!-- Hiển thị nội dung đoạn văn nếu không có hình ảnh -->
    <div v-else-if="content" class="passage-content">
      <pre>{{ content }}</pre>
    </div>
    <!-- Thông báo lỗi nếu không có cả image lẫn content -->
    <div v-else class="error-message">
      No content or image available for this passage.
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "",
    },
    content: {
      type: String,
      default: "",
    },
    image: {
      type: String,
      default: null,
    },
  },
  methods: {
    onImageError() {
      console.error(`Failed to load image: ${this.image}`);
    },
  },
};
</script>

<style scoped>
.reading-passage {
  margin-bottom: 20px;
}

.passage-content {
  font-size: 14px;
  line-height: 1.6;
  white-space: pre-wrap;
}

.passage-content pre {
  font-family: Arial, sans-serif;
  margin: 0;
}

.passage-image {
  width: 100%;
  max-width: 650px;
  height: auto;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  object-fit: contain;
}

.error-message {
  color: red;
  font-style: italic;
}
</style>
