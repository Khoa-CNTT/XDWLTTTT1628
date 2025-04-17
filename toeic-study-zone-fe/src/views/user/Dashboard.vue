<!-- src/views/user/Dashboard.vue -->
<template>
  <div class="dashboard-container">
    <!-- Hero Section -->
    <div class="container">
      <section class="hero-section">
        <!-- Welcome Section -->
        <div class="welcome-section">
          <h1 class="greeting">Xin chào, {{ username }}!</h1>
          <div v-if="!hasGoal" class="notification-text">
            ⏰ Bạn chưa tạo mục tiêu cho quá trình luyện thi của mình.
            <a href="#" class="action-link" @click.prevent="openStudyGoalsModal"
              >Tạo ngay</a
            >
          </div>

          <!-- Nếu đã có mục tiêu thì hiển thị -->
          <div v-else class="study-goal-box">
            <h3>Mục tiêu luyện thi hiện tại của bạn 🎯</h3>
            <ul>
              <li><strong>Môn thi:</strong> {{ userGoal.subject }}</li>
              <li><strong>Ngày dự thi:</strong> {{ userGoal.examDate }}</li>
              <li>
                <strong>Mục tiêu điểm số:</strong> {{ userGoal.targetScore }}
              </li>
            </ul>
            <a href="#" @click.prevent="openStudyGoalsModal"
              >📝 Cập nhật mục tiêu</a
            >
          </div>
        </div>

        <!-- Schedule Section -->
        <div class="schedule-section">
          <h2 class="section-title">Lịch học hôm nay</h2>
          <p class="empty-text">
            <em>
              Bạn không có lịch học hôm nay. Vui lòng vào
              <router-link to="/schedule-form" class="action-link">
                Lịch học của tôi
              </router-link>
              để xem thêm hoặc tạo mới.
            </em>
          </p>
        </div>

        <!-- Recent Results Section -->
        <div class="recent-results-section">
          <h2 class="section-title">Kết quả luyện thi mới nhất</h2>

          <div class="test-results-horizontal">
            <!-- Test result 1 -->
            <div class="test-result-card">
              <h3 class="test-title">2024 Practice Set TOEIC Test 2</h3>
              <div class="test-part">
                <span>Part 4</span>
              </div>
              <div class="test-meta">
                <p>Ngày làm bài: 15/12/2024</p>
                <p>Thời gian hoàn thành: 0:37:52</p>
                <p class="test-score">Kết quả: 18/30</p>
              </div>
              <a href="#" class="view-details">[Xem chi tiết]</a>
            </div>

            <!-- Test result 2 -->
            <div class="test-result-card">
              <h3 class="test-title">2024 Practice Set TOEIC Test 2</h3>
              <div class="test-part">
                <span>Part 2</span>
              </div>
              <div class="test-meta">
                <p>Ngày làm bài: 14/12/2024</p>
                <p>Thời gian hoàn thành: 0:07:10</p>
                <p class="test-score">Kết quả: 16/25</p>
              </div>
              <a href="#" class="view-details">[Xem chi tiết]</a>
            </div>

            <!-- Test result 3 -->
            <div class="test-result-card">
              <h3 class="test-title">2024 Practice Set TOEIC Test 2</h3>
              <div class="test-part">
                <span>Part 3</span>
              </div>
              <div class="test-meta">
                <p>Ngày làm bài: 14/12/2024</p>
                <p>Thời gian hoàn thành: 0:31:31</p>
                <p class="test-score">Kết quả: 19/30</p>
              </div>
              <a href="#" class="view-details">[Xem chi tiết]</a>
            </div>
          </div>

          <div class="view-all-bottom">
            <a href="#">Xem tất cả >></a>
          </div>
        </div>
      </section>
    </div>

    <!-- Banner section -->
    <div class="banner-section">
      <div class="level-test-banner">
        <div class="banner-content">
          <h3>Kiểm tra trình độ miễn phí</h3>
          <p>Nhanh chóng - Chính xác - Hiệu quả</p>
          <button class="start-button">START</button>
        </div>
      </div>
    </div>

    <!-- Featured courses section -->
    <div class="featured-courses-section">
      <h3 class="section-title">Khoá học online nổi bật</h3>
      <div class="row">
        <div
          class="col-md-4"
          v-for="course in featuredCourses"
          :key="course.id"
        >
          <div class="course-card">
            <div
              class="course-image"
              :style="{ backgroundImage: `url(${course.image})` }"
            ></div>
            <div class="course-content">
              <h4 class="course-title">{{ course.title }}</h4>
              <p class="course-description">{{ course.description }}</p>
              <div class="course-rating">
                <span class="rating-star">★★★★★</span>
                <span class="rating-score"
                  >{{ course.rating }} ({{ course.reviews }})</span
                >
              </div>
              <div class="course-price">
                <span class="original-price" v-if="course.hasDiscount">{{
                  course.originalPrice
                }}</span>
                <span class="sale-price">{{ course.salePrice }}</span>
                <span class="discount-tag" v-if="course.hasDiscount"
                  >-{{ course.discount }}%</span
                >
              </div>
              <button class="btn btn-outline-primary">Xem chi tiết</button>
            </div>
          </div>
        </div>
      </div>
      <div class="pagination-dots">
        <span class="dot active"></span>
        <span class="dot"></span>
        <span class="dot"></span>
      </div>
    </div>

    <!-- New tests section -->
    <div class="new-tests-section">
      <h3 class="section-title">Đề thi mới nhất</h3>
      <p class="section-subtitle">
        Nhận chứng chỉ chính thức - Chứng nhận - Hiệu quả
      </p>

      <!-- Dòng 1 - 4 cards đầu tiên -->
      <div class="row">
        <div
          class="col-md-3"
          v-for="(test, index) in newTests.slice(0, 4)"
          :key="'test1-' + index"
        >
          <div class="test-card">
            <h4 class="test-title">{{ test.title }}</h4>
            <p class="test-time">
              <span class="icon">⏰</span> {{ test.time }} |
              <span class="icon">📝</span> {{ test.participants }} |
              <span class="icon">💬</span> {{ test.comments }}
            </p>
            <p class="test-description">{{ test.description }}</p>
            <button class="btn btn-outline-primary">Chi tiết</button>
          </div>
        </div>
      </div>

      <!-- Dòng 2 - 4 cards tiếp theo -->
      <div class="row">
        <div
          class="col-md-3"
          v-for="(test, index) in newTests.slice(4, 8)"
          :key="'test2-' + index"
        >
          <div class="test-card">
            <h4 class="test-title">{{ test.title }}</h4>
            <p class="test-time">
              <span class="icon">⏰</span> {{ test.time }} |
              <span class="icon">📝</span> {{ test.participants }} |
              <span class="icon">💬</span> {{ test.comments }}
            </p>
            <p class="test-description">{{ test.description }}</p>
            <button class="btn btn-outline-primary">Chi tiết</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Promo banner -->
    <div class="promo-banner">
      <div class="promo-content">
        <h3 class="promo-title">LUYỆN ĐỀ ONLINE KHÔNG GIỚI HẠN</h3>
        <ul class="promo-features">
          <li>Kho đề phong phú: IELTS, TOEIC, HSK, TOPIK, THPT...</li>
          <li>Bảo điểm giống thi thật, đúng trình độ người dùng</li>
          <li>Tự động phát hiện điểm yếu theo điều cầu</li>
          <li>Hệ thống công cụ highlight, ghi chú, tải đề...</li>
          <li>Report đề thi ý nghĩa - Kiểm soát thời gian làm</li>
        </ul>
        <div class="promo-tag">
          <span class="discount">-30%</span>
        </div>
        <div class="promo-tag premium">
          <span>1 TỶ USER</span>
        </div>
      </div>
    </div>
    <div class="pagination-dots">
      <span class="dot active"></span>
      <span class="dot"></span>
    </div>

    <!-- Modal StudyGoals -->
    <study-goals
      :show-modal="showStudyGoalsModal"
      @close="closeStudyGoalsModal"
      @save="handleSaveGoal"
    />
  </div>
</template>

<script>
import StudyGoals from "@/views/user/StudyGoals.vue";

export default {
  name: "Dashboard",
  components: {
    StudyGoals,
  },
  data() {
    return {
      username: "longvu2212203",
      showStudyGoalsModal: false,
      hasGoal: false,
      userGoal: null,
      newTests: [
        {
          title: "TOEIC Practice Set test 1",
          time: "120 phút",
          participants: "98,281 người thi",
          comments: "636 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 2",
          time: "120 phút",
          participants: "28,281 người thi",
          comments: "236 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 3",
          time: "120 phút",
          participants: "9,281 người thi",
          comments: "636 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 4",
          time: "120 phút",
          participants: "9,281 người thi",
          comments: "236 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 5",
          time: "120 phút",
          participants: "928,281 người thi",
          comments: "636 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 6",
          time: "120 phút",
          participants: "28,281 người thi",
          comments: "236 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 7",
          time: "120 phút",
          participants: "9,281 người thi",
          comments: "636 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
        {
          title: "TOEIC Practice Set test 8",
          time: "120 phút",
          participants: "9,281 người thi",
          comments: "236 bình luận",
          description: "2 phần thi | 200 câu hỏi",
        },
      ],
      featuredCourses: [
        {
          id: 1,
          title: "TOEIC Complete Course",
          image:
            "https://study4.com/media/courses/Course/files/2023/10/11/toeic.webp",
          description: "Lộ trình học TOEIC từ 0 đến 900+ trong 3 tháng.",
          rating: "4.9",
          reviews: "1,000+ học viên",
          originalPrice: "1,800,000đ",
          salePrice: "989,000đ",
          discount: "45",
          hasDiscount: true,
        },
        {
          id: 2,
          title: "TOEIC Listening Intensive",
          image:
            "https://study4.com/media/courses/Course/files/2023/10/11/ielts_funda.webp",
          description: "Luyện kỹ năng Listening TOEIC từ cơ bản đến nâng cao.",
          rating: "5.0",
          reviews: "800+ học viên",
          originalPrice: "999,000đ",
          salePrice: "699,000đ",
          discount: "30",
          hasDiscount: true,
        },
        {
          id: 3,
          title: "TOEIC Reading Mastery",
          image:
            "https://study4.com/media/courses/Course/files/2023/11/30/intensive_listen.webp",
          description: "Nền tảng kỹ năng Reading TOEIC để đạt điểm cao.",
          rating: "5.0",
          reviews: "600+ học viên",
          originalPrice: "999,000đ",
          salePrice: "699,000đ",
          discount: "30",
          hasDiscount: true,
        },
      ],
    };
  },
  methods: {
    openStudyGoalsModal() {
      this.showStudyGoalsModal = true;
    },
    closeStudyGoalsModal() {
      this.showStudyGoalsModal = false;
    },
    handleSaveGoal(goal) {
      console.log("Mục tiêu đã được lưu:", goal);
      this.hasGoal = true;
      this.userGoal = goal;
      this.showStudyGoalsModal = false;
    },
  },
};
</script>

<style scoped>
/* Giữ nguyên style hiện tại của Dashboard.vue */
.dashboard-container {
  max-width: 2200px;
  width: 100%;
  margin: 0 auto;
  font-family: "Inter", sans-serif;
  text-align: center;
}

.container {
  text-align: left;
  background-color: #e6f0fa;
  position: relative;
  max-width: 100%;
}

/* Hero Section */
.hero-section {
  padding: 24px 50px;

  margin-bottom: 24px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

/* Welcome Section */
.welcome-section {
  margin: 20px 0;
}

.greeting {
  font-size: 24px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 10px;
}

.notification-text {
  margin: 0;
  color: #333;
  background-color: #fff3e0;
  padding: 10px 12px;
  border-radius: 6px;
  font-size: 14px;
  display: flex;
  align-items: center;
  line-height: 1.5;
}

.action-link {
  color: #1976d2;
  text-decoration: none;
  font-weight: 600;
  margin-left: 5px;
}

.action-link:hover {
  text-decoration: underline;
}

.study-goal-box {
  background-color: #fff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.study-goal-box h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.study-goal-box ul {
  list-style: none;
  padding: 0;
  margin: 0 0 10px 0;
}

.study-goal-box li {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.study-goal-box li strong {
  color: #333;
}

/* Schedule Section */
.schedule-section {
  margin-bottom: 20px;
}

.empty-text {
  margin: 0;
  color: #666;
  font-style: italic;
  font-size: 14px;
  line-height: 1.5;
}

/* Recent Results Section */
.recent-results-section {
  margin-top: 30px;
  background-color: #e6f0fa;
}

.section-title {
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 15px;
}

.test-results-horizontal {
  display: flex;
  gap: 20px;
  overflow-x: auto;
  padding-bottom: 15px;
  margin-bottom: 15px;
}

.test-result-card {
  min-width: 280px;
  padding: 15px;
  border-radius: 8px;
  background-color: #fff;
  border: 1px solid #e0e0e0;
  box-shadow: 0 4px 0 0 rgba(143, 156, 173, 0.2);
  flex-shrink: 0;
}

.test-title {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
  margin-bottom: 12px;
}

.test-part {
  margin-bottom: 12px;
}

.test-part span {
  color: #fff;
  font-size: 13px;
  background-color: #f5b255;
  border-radius: 0.35rem;
  padding: 0.25em 0.4em;
  font-weight: 700;
  line-height: 1;
}

.test-meta p {
  font-size: 14px;
  color: #030303;
  margin: 6px 0;
}

.test-score {
  font-weight: 500;
  color: #030303;
}

.view-details {
  display: inline-block;
  color: #1976d2;
  text-decoration: none;
  font-size: 13px;
  margin-top: 12px;
}

.view-details:hover {
  text-decoration: underline;
}

.view-all-bottom {
  text-align: right;
}

.view-all-bottom a {
  color: #1976d2;
  text-decoration: none;
  font-size: 14px;
}

.view-all-bottom a:hover {
  text-decoration: underline;
}

/* Rest of your existing styles (unchanged) */
.banner-section {
  margin-bottom: 24px;
  padding: 0 60px;
}

.level-test-banner {
  width: 100%;
  height: 180px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: #4a90e2;
  background-image: linear-gradient(135deg, #5a99e2, #796aee);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.banner-content {
  text-align: center;
  color: white;
}

.banner-content h3 {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 8px;
}

.start-button {
  background-color: white;
  color: #3681dd;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  font-weight: 700;
  margin-top: 16px;
  cursor: pointer;
}

.start-button:hover {
  background-color: #4fd36b;
  color: white;
  box-shadow: 0 0 15px rgba(40, 167, 69, 0.8);
}

.new-tests-section {
  padding: 0 60px;
}

.course-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  margin-bottom: 1.5rem;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
}

.course-image {
  width: 100%;
  height: 150px;
  background-size: contain;
  background-position: center;
  background-repeat: no-repeat;
}

.course-content {
  padding: 1rem;
}

.course-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 0.5rem;
}

.course-description {
  font-size: 0.9rem;
  color: #606770;
  margin-bottom: 0.5rem;
  min-height: 40px;
}

.course-rating {
  display: flex;
  align-items: center;
  margin-bottom: 0.75rem;
}

.rating-star {
  color: #f1c40f;
  margin-right: 0.5rem;
}

.rating-score {
  font-size: 0.9rem;
  color: #606770;
}

.course-price {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 1rem;
  font-weight: 600;
  color: #e74c3c;
  margin-bottom: 0.75rem;
}

.original-price {
  text-decoration: line-through;
  color: #606770;
  font-weight: normal;
}

.sale-price {
  color: #e74c3c;
}

.discount-tag {
  background-color: #e74c3c;
  color: white;
  padding: 0.25rem 0.5rem;
  border-radius: 4px;
  font-size: 0.8rem;
  font-weight: 500;
}

.btn-outline-primary {
  width: 100%;
  border-color: #1877f2;
  color: #1877f2;

  &:hover {
    background-color: #1877f2;
    color: #fff;
  }
}

.pagination-dots {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  margin-bottom: 20px;
}

.dot {
  width: 8px;
  height: 8px;
  background-color: #d1d9e6;
  border-radius: 50%;
  margin: 0 4px;
  cursor: pointer;
}

.dot.active {
  background-color: #0066cc;
}

.test-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 1.8rem 1.5rem;
  text-align: left;
  margin-bottom: 1.5rem;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
}

.featured-courses-section {
  padding: 0 60px;
}

.row {
  padding: 20px;
}

.test-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 0.5rem;
}

.test-time {
  font-size: 0.9rem;
  color: #030303;
  margin-bottom: 0.5rem;
  display: flex;
  gap: 0.4rem;
  flex-wrap: wrap;
}

.test-time .icon {
  font-size: 1rem;
}

.test-description {
  font-size: 0.9rem;
  color: #030303;
  margin-bottom: 0.75rem;
}

.btn-outline-primary {
  width: 100%;
  border-color: #1877f2;
  color: #1877f2;

  &:hover {
    background-color: #1877f2;
    color: #fff;
  }
}

.promo-banner {
  background-color: #f8f9fa;
  border-radius: 8px;
  padding: 0 60px;
  margin-bottom: 18px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  overflow: hidden;
  position: relative;
  height: 300px;
}

.promo-content {
  flex: 1;
  padding: 24px;
  position: relative;
  background: linear-gradient(135deg, #0a2647, #144272);
  color: white;
}

.promo-title {
  font-size: 24px;
  font-weight: 700;
  color: white;
  margin-bottom: 16px;
}

.promo-features {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.promo-features li {
  font-size: 14px;
  color: #eaeaea;
  margin-bottom: 10px;
  position: relative;
}

.promo-features li:before {
  content: "•";
  left: -20px;
  color: #f0b14c;
  font-size: 24px;
  position: relative;
}

.promo-tag {
  position: absolute;
  top: 16px;
  right: 16px;
  background-color: #e94560;
  color: white;
  padding: 8px 16px;
  border-radius: 50%;
  font-weight: 700;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 50px;
  height: 50px;
}

.promo-tag.premium {
  background-color: #593dfb;
  right: 90px;
  font-size: 12px;
}

@media (max-width: 1024px) {
  .courses-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .tests-grid {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media (max-width: 768px) {
  .courses-grid {
    grid-template-columns: 1fr;
  }

  .tests-grid {
    grid-template-columns: 1fr;
  }

  .promo-banner {
    flex-direction: column;
    height: auto;
  }

  .promo-image {
    height: 200px;
  }
}
</style>
