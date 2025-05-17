<template>
  <div class="home-page">
    <!-- Hero Section -->
    <section class="hero-section">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-6">
            <h1 class="hero-title">Luyện đề TOEIC online không giới hạn</h1>
            <p class="hero-description">
              Đề thi TOEIC sát với thực tế, hỗ trợ bạn đạt điểm cao!
            </p>
            <div class="hero-stats">
              <div class="stat-item">
                <span class="stat-number">2000+</span>
                <span class="stat-label">Đề thi TOEIC</span>
              </div>
              <div class="stat-item">
                <span class="stat-number">500K+</span>
                <span class="stat-label">Học viên</span>
              </div>
            </div>
            <button class="btn btn-primary">Bắt đầu ngay</button>
          </div>
          <div class="col-md-6">
            <img :src="heroImage" alt="Hero Illustration" class="hero-image" />
          </div>
        </div>
      </div>
    </section>

    <!-- Courses Section -->
    <section class="courses-section">
      <div class="container">
        <h2 class="section-title">Khóa học TOEIC nổi bật</h2>
        <div class="row">
          <div class="col-md-4" v-for="course in courses" :key="course.id">
            <div class="course-card">
              <img
                :src="course.image"
                alt="Course Image"
                class="course-image"
              />
              <div class="course-content">
                <h3 class="course-title">{{ course.title }}</h3>
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
      </div>
    </section>

    <!-- Khoá học -->
    <section class="tests-section">
      <div class="container">
        <h2 class="section-title">Đề thi mới nhất</h2>
        <p class="section-subtitle">
          Nhận chứng chỉ chính thức - Chứng nhận - Hiệu quả
        </p>
        <div class="row">
          <div class="col-md-3" v-for="test in tests.slice(0, 8)" :key="test.id">
            <div class="test-card">
              <h3 class="test-title">{{ test.title }}</h3>
              <p class="test-time">
                <span class="icon">⏰</span> {{ test.timeLimit }} phút |
                <span class="icon">📝</span> {{ test.participants }} người thi |
                <span class="icon">💬</span> {{ test.comments }} bình luận
              </p>
              <p class="test-description">
                {{ test.totalQuestions }} câu hỏi | {{ test.description }}
              </p>
              <button class="btn btn-outline-primary" @click="goToTestDetail(test.id)">
                Chi tiết
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Practice Section -->
    <section class="practice-section">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-6">
            <h2 class="section-title">Luyện đề TOEIC không giới hạn</h2>
            <ul class="practice-list">
              <li>Giao diện giống thi thật, sát đề TOEIC chính thức.</li>
              <li>Tự chọn phần thi: Listening hoặc Reading.</li>
              <li>Chấm điểm ngay, highlight câu sai, giải thích chi tiết.</li>
              <li>Hỗ trợ cải thiện điểm số từng ngày, từng bước một.</li>
            </ul>
            <button class="btn btn-primary">Luyện ngay</button>
          </div>
          <div class="col-md-6">
            <img
              :src="practiceImage"
              alt="Practice Illustration"
              class="practice-image"
            />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import examService from "@/services/examService";
import heroImage from "@/assets/images/hero-illustration.png";
import practiceImage from "@/assets/images/banner-main.png";

const router = useRouter();

const courses = [
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
      "https://study4.com/media/courses/Course/files/2023/11/30/intensive_listen.webp",
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
      "https://study4.com/media/courses/Course/files/2023/10/11/ielts_funda.webp",
    description: "Nền tảng kỹ năng Reading TOEIC để đạt điểm cao.",
    rating: "5.0",
    reviews: "600+ học viên",
    originalPrice: "999,000đ",
    salePrice: "699,000đ",
    discount: "30",
    hasDiscount: true,
  },
];

const tests = ref([]);

const loadLatestTests = async () => {
  try {
    const res = await examService.getLatestTests();
    tests.value = res.data.map((test) => ({
      id: test.id,
      title: test.title,
      description: test.description,
      timeLimit: test.timeLimit,
      totalQuestions: test.totalQuestions,
      participants: (test.participantsCount ?? 0).toLocaleString(),
      comments: (test.commentsCount ?? 0).toLocaleString(),
    }));
  } catch (err) {
    console.error("Lỗi khi tải đề thi mới:", err);
  }
};

const goToTestDetail = (id) => {
  router.push({ name: "Test", params: { id } });
};

onMounted(() => {
  loadLatestTests();
});
</script>

<style lang="scss" scoped>
.home-page {
  font-family: "Roboto", sans-serif;
  min-height: 100vh;
}

/* Hero Section */
.hero-section {
  background-color: #e6f0fa;
  padding: 4rem 0;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: #1c2526;
  margin-bottom: 1rem;
}

.hero-description {
  font-size: 1.2rem;
  color: #606770;
  margin-bottom: 1.5rem;
}

.hero-stats {
  display: flex;
  gap: 2rem;
  margin-bottom: 2rem;
}

.stat-item {
  text-align: center;
}

.stat-number {
  display: block;
  font-size: 1.5rem;
  font-weight: 700;
  color: #1877f2;
}

.stat-label {
  font-size: 0.9rem;
  color: #606770;
}

.hero-image {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  display: block;
}

/* Section Title */
.section-title {
  font-size: 1.75rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 1rem;
  text-align: center;
}

.section-subtitle {
  font-size: 1rem;
  color: #606770;
  margin-bottom: 1.5rem;
  text-align: center;
}

/* Courses Section */
.courses-section {
  padding: 3rem 0;
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
    filter: brightness(105%);
  }
}

.course-image {
  width: 100%;
  height: 150px;
  object-fit: cover;
  display: block;
}

.course-content {
  padding: 1rem;
}

.course-title {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 0.5rem;
}

.course-description {
  font-size: 0.9rem;
  color: #606770;
  display: flex;
  margin-bottom: 0.5rem;
  margin-left: 20px;
}

.course-rating {
  display: flex;
  align-items: center;
  margin-bottom: 0.75rem;
  margin-left: 20px;
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
  margin-left: 20px;

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
}

/* Tests Section */
.tests-section {
  background-color: #f8f9fa;
  padding: 3rem 0;
}

.test-card {
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  padding: 1.5rem;
  text-align: left;
  margin-bottom: 1.5rem;
  transition: all 0.3s ease;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    filter: brightness(105%);
  }
}

.test-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: #1c2526;
  margin-bottom: 0.5rem;
}

.test-time {
  font-size: 0.9rem;
  color: #606770;
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  gap: 0.3rem;
  flex-wrap: wrap;
}

.test-time .icon {
  font-size: 1rem;
}

.icon-wrapper {
  display: inline-flex;
  align-items: center;
  gap: 0.3rem;
}

.test-description {
  font-size: 0.9rem;
  color: #606770;
  margin-bottom: 0.75rem;
}

.tag {
  background-color: #e6f0fa;
  color: #1877f2;
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
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

/* Practice Section */
.practice-section {
  padding: 3rem 0;
}

.practice-list {
  list-style: none;
  padding: 0;
  margin-bottom: 1.5rem;
}

.practice-list li {
  font-size: 1rem;
  color: #606770;
  margin-bottom: 0.75rem;
  position: relative;
  padding-left: 1.5rem;
}

.practice-list li::before {
  content: "✔";
  color: #1877f2;
  position: absolute;
  left: 0;
  font-size: 1rem;
}

.practice-image {
  width: 100%;
  max-width: 400px;
  margin: 0 auto;
  display: block;
}
</style>
