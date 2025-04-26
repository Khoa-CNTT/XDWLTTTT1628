<template>
  <label class="answer-option">
    <input
      type="radio"
      :value="option.value"
      :name="name"
      @change="$emit('click', option.value)"
    />
    <span>{{ option.value }}. {{ option.text }}</span>
  </label>
</template>

<script>
export default {
  props: {
    option: Object, // Thay đổi từ String thành Object để nhận { value, text }
    name: String,
  },
};
</script>

<style scoped>
.answer-option {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  position: relative;
  width: 100%;
}

.answer-option input[type="radio"] {
  appearance: none;
  -webkit-appearance: none;
  -moz-appearance: none;
  width: 24px; /* Tăng kích thước giống Hình 1 */
  height: 24px;
  cursor: pointer;
  position: relative;
  border: 1px solid #666;
  border-radius: 50%;
  background-color: #fff;
}

.answer-option input[type="radio"]:checked {
  background-color: #fff;
  border: 1px solid #666;
}

.answer-option input[type="radio"]:checked::after {
  content: "";
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 16px; /* Tăng kích thước điểm bên trong */
  height: 16px;
  background-color: #0075ff;
  border-radius: 50%;
}

.answer-option span {
  font-size: 16px;
  font-weight: normal; /* Không cần đậm giống Hình 1 */
  color: #333;
}
</style>
