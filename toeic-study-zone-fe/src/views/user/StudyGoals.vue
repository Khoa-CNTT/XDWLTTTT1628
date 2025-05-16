<!-- src/views/user/StudyGoals.vue -->
<template>
  <div class="modal-overlay" v-if="showModal" @click.self="closeModal">
    <div class="modal-content">
      <div class="modal-header">
        <h1 class="modal-title">Tạo mục tiêu</h1>
        <button class="close-btn" @click="closeModal">×</button>
      </div>
      <div class="modal-body">
        <p class="modal-description">
          Lưu ý: Bạn chỉ được tạo 1 mục tiêu cho mỗi môn thi. Bạn có thể đổi ngày dự thi và điểm mục tiêu sau khi khởi tạo.
        </p>

        <div class="form-group">
          <label class="form-label">Chọn môn thi</label>
          <div class="subjects-grid">
            <button
              v-for="subject in subjects"
              :key="subject"
              class="subject-btn"
              :class="{ active: selectedSubject === subject }"
              @click="selectedSubject = subject"
            >
              #{{ subject }}
            </button>
          </div>
        </div>

        <div class="form-row">
          <div class="form-group">
            <label class="form-label">Ngày dự thi</label>
            <div class="input-icon-wrapper">
              <input type="date" class="form-control" v-model="examDate" />
              <span class="calendar-icon">📅</span>
            </div>
          </div>

          <div class="form-group">
            <label class="form-label">Điểm mục tiêu</label>
            <div class="score-input-wrapper" @mouseenter="showControls = true" @mouseleave="showControls = false">
              <input type="number" class="score-input" v-model.number="targetScore" min="0" />
              <div class="score-controls" v-show="showControls">
                <button class="score-btn" @click="incrementScore"><span class="arrow-up">▲</span></button>
                <button class="score-btn" @click="decrementScore"><span class="arrow-down">▼</span></button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="modal-footer">
        <button class="btn btn-primary" @click="saveGoal">Lưu</button>
        <button v-if="currentGoal && currentGoal.id" class="btn btn-danger" @click="deleteGoal">Xoá</button>
      </div>
    </div>
  </div>
</template>

<script>
import userService from "@/services/userService";

export default {
  name: "StudyGoals",
  props: {
    showModal: Boolean,
    currentGoal: Object,
  },
  data() {
    return {
      selectedSubject: "TOEIC",
      examDate: "",
      targetScore: 0,
      showControls: false,
      subjects: [
        "TOEIC",
      ],
    };
  },
  watch: {
    currentGoal: {
      immediate: true,
      handler(goal) {
        if (goal) {
          this.examDate = goal.targetDate || "";
          this.targetScore = goal.targetScore || 0;
        }
      },
    },
  },
  methods: {
    closeModal() {
      this.$emit("close");
    },
    async saveGoal() {
      if (!this.examDate || !this.targetScore) {
        alert("Vui lòng nhập đầy đủ thông tin.");
        return;
      }

      try {
        const res = await userService.getCurrentUser();
        const userId = res.data.id;
        const payload = {
          userId,
          targetScore: this.targetScore,
          targetDate: this.examDate,
        };

        if (this.currentGoal && this.currentGoal.id) {
          await userService.updateLearningGoal(this.currentGoal.id, payload);
          alert("Mục tiêu đã được cập nhật!");
        } else {
          await userService.createLearningGoal(payload);
          alert("Mục tiêu đã được tạo!");
        }

        this.$emit("save", payload);
        this.closeModal();
      } catch (error) {
        console.error("Lỗi khi lưu mục tiêu:", error);
        alert("Không thể lưu mục tiêu. Vui lòng thử lại!");
      }
    },
    async deleteGoal() {
      const confirmed = confirm("Bạn có chắc chắn muốn xoá mục tiêu này không?");
      if (!confirmed) return;

      try {
        await userService.deleteLearningGoal(this.currentGoal.id);
        alert("Mục tiêu đã được xoá!");
         this.$emit("delete");
        this.closeModal();
      } catch (error) {
        console.error("Lỗi khi xoá mục tiêu:", error);
        alert("Không thể xoá mục tiêu. Vui lòng thử lại!");
      }
    },
    incrementScore() {
      this.targetScore++;
    },
    decrementScore() {
      if (this.targetScore > 0) this.targetScore--;
    },
  },
};
</script>


<style scoped>
.form-row {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.form-row .form-group {
  flex: 1;
}

.input-icon-wrapper {
  position: relative;
}

.input-icon-wrapper input {
  width: 100%;
  padding: 8px 10px 8px 12px;

  border-radius: 4px;
  font-size: 14px;
}

.calendar-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #666;
  font-size: 16px;
  pointer-events: none;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 9999;
}

.modal-content {
  background: #fff;
  border-radius: 8px;
  width: 100%;
  max-width: 500px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 20px;
  border-bottom: 1px solid #e0e0e0;
}

.modal-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  font-size: 24px;
  color: #666;
  cursor: pointer;
}

.modal-body {
  padding: 20px;
}

.modal-description {
  font-size: 14px;
  color: #666;
  background-color: #fff3e0;
  padding: 10px;
  border-radius: 6px;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-label {
  font-size: 14px;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
  display: block;
}

.subjects-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.subject-btn {
  background: #f0f2f5;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  padding: 6px 12px;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: all 0.2s;
}

.subject-btn.active,
.subject-btn:hover {
  background: #e6f0fa;
  border-color: #1976d2;
  color: #1976d2;
}

.form-control {
  width: 100%;
  padding: 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 14px;
  color: #333;
}

.form-control:focus {
  outline: none;
  border-color: #1976d2;
  box-shadow: 0 0 0 2px rgba(25, 118, 210, 0.1);
}

.target-score-wrapper {
  position: relative;
}

.target-score-wrapper .form-control {
  padding-right: 40px;
}

.dropdown-icon {
  position: absolute;
  right: 12px;
  top: 50%;
  transform: translateY(-50%);
  color: #666;
  font-size: 12px;
  pointer-events: none;
}

.modal-footer {
  padding: 15px 20px;
  border-top: 1px solid #e0e0e0;
  text-align: right;
}

.btn {
  padding: 8px 16px;
  font-size: 16px;
  font-weight: 600;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.2s ease-in-out;
  margin-left: 10px;
}

.btn-primary {
  background-color: #1976d2;
  color: white;
}

.btn-primary:hover {
  background-color: #1565c0;
}

.btn-danger {
  background-color: #e53935;
  color: white;
}

.btn-danger:hover {
  background-color: #c62828;
}

.score-input-wrapper {
  position: relative;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  height: 36px;
  display: flex;
  align-items: center;
}

.score-input-wrapper input {
  width: 100%;
  padding: 8px 10px 8px 12px;
  border: 1px solid #e0e0e0;
  border-radius: 4px;
  font-size: 14px;
}

.score-input:focus {
  outline: 1px solid #1976d2;
}

.score-input {
  width: 100%;
  padding: 8px 30px 8px 12px;
  border: none;
  outline: none;
  background: #fff; /* hoặc dùng inherit nếu cần */
  font-size: 14px;
  color: #333; /* Đảm bảo text không bị quá mờ */
}

.score-controls {
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  display: flex;
  flex-direction: column;
  width: 24px;
}

.score-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  color: #666;
}

.score-btn:hover {
  background-color: #f0f0f0;
}

.arrow-up,
.arrow-down {
  font-size: 10px;
  line-height: 1;
}

/* Ẩn mũi tên mặc định của input number */
.score-input::-webkit-outer-spin-button,
.score-input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.score-input {
  -moz-appearance: textfield;
}
</style>
