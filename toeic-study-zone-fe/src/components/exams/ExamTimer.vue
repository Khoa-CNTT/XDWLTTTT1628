<!-- src/components/exams/ExamTimer.vue -->
<template>
  <div class="exam-timer">
    <p>Thời gian còn lại:</p>
    <h2>{{ formattedTime }}</h2>
    <button @click="submitExam" class="submit-button">NỘP BÀI</button>
    <p class="warning">Kiểm tra lại các câu trả lời trước khi nộp bài!</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      timeRemaining: 7200, // 120 phút = 7200 giây
      timer: null, // Lưu trữ interval để đếm ngược
    };
  },
  computed: {
    formattedTime() {
      const minutes = Math.floor(this.timeRemaining / 60);
      const seconds = this.timeRemaining % 60;
      return `${minutes}:${seconds < 10 ? "0" : ""}${seconds}`;
    },
  },
  mounted() {
    // Bắt đầu đếm ngược khi component được mount
    this.startTimer();
  },
  beforeUnmount() {
    // Dọn dẹp interval khi component bị hủy
    if (this.timer) {
      clearInterval(this.timer);
    }
  },
  methods: {
    startTimer() {
      // Đếm ngược mỗi giây
      this.timer = setInterval(() => {
        if (this.timeRemaining > 0) {
          this.timeRemaining -= 1; // Giảm 1 giây
        } else {
          // Khi thời gian về 0, dừng đếm ngược và có thể tự động nộp bài
          clearInterval(this.timer);
          this.submitExam();
        }
      }, 1000); // Cập nhật mỗi 1000ms (1 giây)
    },
    submitExam() {
      // Dừng đếm ngược khi nộp bài
      if (this.timer) {
        clearInterval(this.timer);
      }
      // Logic xử lý nộp bài
      alert("Bài thi đã được nộp!");
      // Bạn có thể thêm logic để chuyển hướng hoặc gửi dữ liệu bài thi tại đây
    },
  },
};
</script>

<style scoped>
.exam-timer {
  text-align: center;
  margin-bottom: 20px;
}

.exam-timer p {
  font-size: 14px;
  margin-bottom: 8px;
  text-align: left;
  color: #333;
}

.exam-timer h2 {
  font-size: 24px;
  font-weight: bold;
  text-align: start;
  color: #333;
}

.submit-button {
  padding: 10px 20px;
  border: 1px solid #007bff; /* Viền xanh dương */
  background: #fff; /* Nền trắng */
  color: #007bff; /* Chữ màu xanh dương */
  cursor: pointer;
  font-size: 16px;
  border-radius: 5px; /* Bo góc */
  margin-top: 10px;
  width: 100%; /* Chiều rộng full */
  text-align: center; /* Căn giữa chữ */
  transition: all 0.3s; /* Hiệu ứng hover */
}

.submit-button:hover {
  background: #007bff; /* Nền xanh dương khi hover */
  color: #fff; /* Chữ chuyển sang màu trắng */
}

.warning {
  font-size: 15px !important;
  color: #ff0000 !important;
  margin-top: 10px;
  text-align: left;
}
</style>
