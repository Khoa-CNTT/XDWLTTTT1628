<template>
  <main class="container mt-4">
    <div class="row">
      <!-- Left Column: Test Content -->
      <div class="col-lg-8 left-column">
        <div class="submit-test-content-wrapper">
          <!-- Thông báo xanh phía trên -->
          <div class="alert alert-success" role="alert">
            <i class="fas fa-exclamation-circle mr-2 me-2"></i>
            <strong> Chúc mừng</strong> bạn đã <u>hoàn thành bài thi</u>! Hãy
            xem xét kết quả và <u>xem lại những phần bạn trả lời sai</u> để cải
            thiện kỹ năng. Đừng quên theo dõi tiến trình và tiếp tục luyện tập
            để đạt được mục tiêu của bạn!
          </div>

          <!-- Tiêu đề kết quả -->
          <h2 class="test-result-title">
            Kết quả thi: Practice Set TOEIC Test 10
          </h2>

          <!-- Nút thao tác -->
          <div class="action-buttons mb-3">
            <button class="btn btn-sm btn-primary me-2">Xem chi tiết</button>
            <button class="btn btn-sm btn-outline-primary">
              Xem đề thi đã làm
            </button>
          </div>

          <!-- Bảng tổng kết số liệu -->
          <div class="result-summary row mb-4">
            <div class="col-12">
              <div class="summary-grid">
                <div class="summary-card">
                  <div class="stat-icon accuracy-icon">
                    <i class="fas fa-percentage"></i>
                  </div>
                  <p class="stat-label">Độ chính xác</p>
                  <h3 class="stat-value text-success-percent">71.5%</h3>
                </div>
                <div class="summary-card accuracy-card">
                  <div class="stat-icon check-icon">
                    <i class="fas fa-check"></i>
                  </div>
                  <p class="stat-label">Kết quả làm bài</p>
                  <h3 class="stat-value">143/200</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon correct-icon">
                    <i class="fas fa-check-circle"></i>
                  </div>
                  <p class="stat-label">Trả lời đúng</p>
                  <h3 class="stat-value text-success">143</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon wrong-icon">
                    <i class="fas fa-times-circle"></i>
                  </div>
                  <p class="stat-label">Trả lời sai</p>
                  <h3 class="stat-value text-danger">57</h3>
                </div>
                <!-- Hàng dưới: Bỏ qua, Điểm, Thời gian hoàn thành -->
                <div class="summary-card">
                  <div class="stat-icon skip-icon">
                    <i class="fas fa-forward"></i>
                  </div>
                  <p class="stat-label">Bỏ qua</p>
                  <h3 class="stat-value text-secondary">0</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon score-icon">
                    <i class="fas fa-trophy"></i>
                  </div>
                  <p class="stat-label">Điểm</p>
                  <h3 class="stat-value text-primary">720</h3>
                </div>
                <div class="summary-card">
                  <div class="stat-icon time-icon">
                    <i class="fas fa-clock"></i>
                  </div>
                  <p class="stat-label">Thời gian hoàn thành</p>
                  <h3 class="stat-value">1:57:43</h3>
                </div>
              </div>
            </div>
          </div>

          <!-- Bảng điểm -->
          <div class="score-section mb-4">
            <div class="score-details row">
              <div class="col-md-6">
                <div class="score-card">
                  <h4>Listening</h4>
                  <div class="score-value">350/495</div>
                  <div class="score-progress">
                    <div class="progress">
                      <div
                        class="progress-bar bg-primary"
                        role="progressbar"
                        style="width: 71%"
                      ></div>
                    </div>
                    <span class="score-percent">Trả lời đúng: 71/100</span>
                  </div>
                </div>
              </div>

              <div class="col-md-6">
                <div class="score-card">
                  <h4>Reading</h4>
                  <div class="score-value">370/495</div>
                  <div class="score-progress">
                    <div class="progress">
                      <div
                        class="progress-bar bg-primary"
                        role="progressbar"
                        style="width: 70%"
                      ></div>
                    </div>
                    <span class="score-percent">Trả lời đúng: 70/100</span>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- Hiển thị đáp án -->
          <div class="answer-section">
            <h4>Đáp án</h4>

            <div class="answer-controls d-flex justify-content-between mb-3">
              <button class="btn btn-sm btn-sky-custom">
                Xem chi tiết đáp án
              </button>
              <div class="answer-filter d-flex align-items-center">
                <button class="btn btn-sm btn-outline-secondary">
                  Lọc đáp án
                </button>
                <input
                  type="text"
                  class="filter-input me-2"
                  placeholder="Nhập từ khóa để lọc..."
                />
              </div>
            </div>

            <!-- Tip -->
            <div class="alert alert-info" role="alert">
              <i class="fas fa-leaf me-2"></i>
              Tips: Ấn vào mũi tên trái hoặc đầu để đi đến câu hỏi của bạn chọn
              để mở flashcards và hoạt động ghi chú.
            </div>

            <!-- Đáp án chi tiết -->
            <div class="part-answers">
              <h5>Part 1</h5>

              <div class="answer-grid row">
                <div class="col-2 answer-item">
                  <div class="answer-number">1</div>
                  <div class="answer-options">
                    <span class="option selected">B</span>
                    <span class="option correct">B</span>
                    <span class="answer-mark">[34 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">2</div>
                  <div class="answer-options">
                    <span class="option selected">C</span>
                    <span class="option correct">C</span>
                    <span class="answer-mark">[30 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">3</div>
                  <div class="answer-options">
                    <span class="option selected">D</span>
                    <span class="option correct">D</span>
                    <span class="answer-mark">[29 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">4</div>
                  <div class="answer-options">
                    <span class="option selected">D</span>
                    <span class="option correct">D</span>
                    <span class="answer-mark">[34 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">5</div>
                  <div class="answer-options">
                    <span class="option selected">D</span>
                    <span class="option correct">D</span>
                    <span class="answer-mark">[29 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">6</div>
                  <div class="answer-options">
                    <span class="option selected">B</span>
                    <span class="option correct">B</span>
                    <span class="answer-mark">[30 sec]</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="part-answers">
              <h5>Part 2</h5>

              <div class="answer-grid row">
                <div class="col-2 answer-item">
                  <div class="answer-number">7</div>
                  <div class="answer-options">
                    <span class="option selected">B</span>
                    <span class="option correct">B</span>
                    <span class="answer-mark">[34 sec]</span>
                  </div>
                </div>

                <div class="col-2 answer-item">
                  <div class="answer-number">8</div>
                  <div class="answer-options">
                    <span class="option selected">C</span>
                    <span class="option correct">C</span>
                    <span class="answer-mark">[34 sec]</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Right Column: Sidebar -->
      <div class="col-lg-4 right-column">
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
import { ref } from "vue";

export default {
  name: "SubmitTest",
  setup() {
    // Dữ liệu mẫu
    const testResult = ref({
      testName: "Practice Set TOEIC Test 10",
      totalCorrect: 143,
      totalWrong: 57,
      totalSkipped: 0,
      totalScore: 720,
      accuracy: 71.5,
      timeTaken: "1:57:43",
      listening: {
        score: 350,
        maxScore: 495,
        correctPercent: 71,
      },
      reading: {
        score: 370,
        maxScore: 495,
        correctPercent: 70,
      },
    });

    return {
      testResult,
    };
  },
};
</script>

<style scoped>
.container {
  max-width: 1500px;
}

.submit-test-content-wrapper {
  background-color: #fff;
  border: 1px solid #e0e0e0;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  text-align: left;
  margin-bottom: 20px;
}

.alert {
  padding: 0.75rem 1.25rem;
}

.alert-success {
  background-color: #d8f0e2;
  border-color: #c8ead6;
  color: #1f5e39;
  font-size: 1rem;
  line-height: 1.7;
  gap: 2px;
}

u {
  text-decoration: underline;
  text-underline-offset: 2px;
}

.test-result-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #333;
  margin: 15px 0;
}

.action-buttons .btn {
  font-size: 0.85rem;
  padding: 0.25rem 0.75rem;
}

.result-summary {
  margin-top: 20px;
}

.summary-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
}

.summary-card {
  flex: 1 1 calc(20% - 15px); /* 5 cards in first row, adjusted for gap */
  background: #f8f9fa;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}

.summary-card.accuracy-card {
  flex: 1 1 calc(40% - 15px); /* Double the width for accuracy card */
}

.summary-card:nth-child(n + 5) {
  flex: 1 1 calc(33.33% - 15px); /* 3 cards in second row */
}

.summary-card:hover {
  transform: translateY(-2px);
}

.stat-icon {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 0 auto 10px;
}

.check-icon {
  background-color: #6fa8d143;
  color: #24b30e;
}

.accuracy-icon {
  background-color: #e0f7fa;
  color: #00acc1;
}

.correct-icon {
  background-color: #e8f5e9;
  color: #2e7d32;
}

.fas {
  font-size: 25px;
}

.wrong-icon {
  background-color: #ffebee;
  color: #c62828;
}

.skip-icon {
  background-color: #f5f5f5;
  color: #757575;
}

.score-icon {
  background-color: #e3f2fd;
  color: #1976d2;
}

.time-icon {
  background-color: #fff3e0;
  color: #f57c00;
}

.stat-label {
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 1.2rem;
  font-weight: 600;
  margin: 0;
}

.score-section {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 15px;
  margin: 20px 0;
}

.score-card {
  text-align: center;
  padding: 15px;
  background-color: #fff;
  border-radius: 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.score-card h4 {
  font-size: 1.1rem;
  color: #333;
  margin-bottom: 10px;
}

.score-value {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1877f2;
  margin-bottom: 10px;
}

.score-progress {
  padding: 0 15px;
}

.progress {
  height: 8px;
  margin-bottom: 5px;
}

.score-percent {
  font-size: 0.8rem;
  color: #666;
}

.table {
  font-size: 0.9rem;
}

.table th {
  background-color: #f8f9fa;
  font-weight: 600;
}

.rating {
  display: flex;
}

.rating-circle {
  width: 15px;
  height: 15px;
  border-radius: 50%;
  margin-right: 3px;
}

.fa-exclamation-circle {
  font-size: 15px;
}

.bg-success {
  background-color: #4caf50;
}

.bg-light {
  background-color: #e0e0e0;
}

.answer-controls {
  margin: 15px 0;
}

.answer-filter {
  display: flex;
  align-items: center;
  gap: 10px;
}

.filter-input {
  padding: 0.25rem 0.5rem;
  font-size: 0.85rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  width: 200px;
  outline: none;
  background-color: #fff;
}

.filter-input:focus {
  border-color: #1877f2;
  box-shadow: 0 0 5px rgba(24, 119, 242, 0.3);
  color: #030303;
}

.alert-info {
  background-color: #e3f2fd;
  border-color: #bbdefb;
  color: #0d47a1;
  font-size: 0.9rem;
}

.fa-leaf {
  font-size: 20px;
}

.answer-grid {
  margin: 10px 0 20px;
}

.answer-item {
  margin-bottom: 15px;
}

.answer-number {
  font-weight: 600;
  margin-bottom: 5px;
}

.answer-options {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}

.option {
  display: inline-block;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  border: 1px solid #ccc;
  text-align: center;
  line-height: 22px;
  font-size: 0.8rem;
  margin-right: 5px;
}

.selected {
  background-color: #e3f2fd;
  border-color: #1877f2;
  color: #1877f2;
}

.correct {
  background-color: #e8f5e9;
  border-color: #4caf50;
  color: #2e7d32;
}

.wrong {
  background-color: #ffebee;
  border-color: #ef5350;
  color: #c62828;
}

.answer-mark {
  font-size: 0.7rem;
  color: #666;
}

.sidebar {
  top: 20px;
  position: sticky;
  margin-bottom: 20px;
}

.user-info {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: flex-start;
  gap: 10px;
  margin-bottom: 15px;
}

.avatar {
  width: 50px;
  height: 50px;
  background-color: #e0e0e0;
  border-radius: 50%;
  flex-shrink: 0;
}

.user-details {
  flex: 1;
  text-align: left;
}

.user-details h4 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.user-details p {
  font-size: 0.85rem;
  color: #666;
  margin: 5px 0;
}

.stats-button {
  background-color: #1877f2;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  font-size: 0.8rem;
  margin-top: 10px;
  width: 100%;
}

.promo-banner,
.study-group,
.community {
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin-bottom: 15px;
}

.promo-banner {
  background: linear-gradient(135deg, #7794b6, #ada6e3);
  color: white;
}

.promo-banner h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin-bottom: 8px;
}

.promo-banner p {
  font-size: 0.85rem;
  margin-bottom: 10px;
}

.start-button,
.extension-button,
.join-button {
  width: 100%;
  padding: 8px;
  border-radius: 4px;
  font-size: 0.9rem;
  font-weight: 600;
  border: none;
  cursor: pointer;
  transition: all 0.3s ease;
}

.start-button {
  background-color: white;
  color: #1877f2;
}

.extension-button,
.join-button {
  background-color: #1877f2;
  color: white;
}

.study-group h4,
.community h4 {
  font-size: 0.95rem;
  font-weight: 600;
  color: #333;
  margin-bottom: 8px;
}

.study-group p,
.community p {
  font-size: 0.85rem;
  color: #666;
  margin-bottom: 10px;
}

.btn-sky-custom {
  color: #1e2022;
  background-color: #e8f2ff;
  border-color: #35509a;
}

.btn-sky-custom:hover {
  color: #fff;
  background-color: #317fe6;
}

@media (min-width: 992px) {
  .left-column {
    flex: 0 0 72%;
    max-width: 74%;
  }
  .right-column {
    flex: 0 0 28%;
    max-width: 26%;
  }
}

@media (max-width: 768px) {
  .summary-card {
    flex: 1 1 calc(50% - 15px);
  }
  .summary-card.accuracy-card {
    flex: 1 1 calc(100% - 15px);
  }
  .result-stat {
    margin-bottom: 15px;
  }

  .score-details .col-md-6 {
    margin-bottom: 15px;
  }

  .answer-filter {
    flex-direction: column;
    align-items: flex-start;
    gap: 5px;
  }

  .filter-input {
    width: 100%;
  }
}
</style>
