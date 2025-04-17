<template>
  <div class="introduction-page">
    <!-- Hero Section -->
    <section class="hero-section py-5 bg-light">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-6">
            <h1 class="display-4 fw-bold mb-4">Giới thiệu về Study Zone</h1>
            <p class="lead mb-4">
              Nền tảng học tập trực tuyến hàng đầu, giúp bạn chinh phục mọi mục
              tiêu học tập với phương pháp hiện đại và hiệu quả.
            </p>
            <router-link
              to="/register"
              class="btn btn-primary btn-lg px-4 me-2"
            >
              Đăng ký ngay
            </router-link>
          </div>
          <div class="col-lg-6">
            <img
              src="@/assets/images/banner-introductions.png"
              alt="Study Zone Platform"
              class="img-fluid rounded shadow"
            />
          </div>
        </div>
      </div>
    </section>

    <!-- About Us Section -->
    <section class="about-section py-5">
      <div class="container">
        <div class="row justify-content-center mb-5">
          <div class="col-lg-8 text-center">
            <h2 class="fw-bold mb-3">Về chúng tôi</h2>
            <p class="text-muted">
              Study Zone được thành lập với sứ mệnh mang lại trải nghiệm học tập
              chất lượng cao, giúp học viên tiếp cận kiến thức một cách dễ dàng
              và hiệu quả nhất.
            </p>
          </div>
        </div>
        <div class="row g-4">
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body text-center p-4">
                <div
                  class="icon-box bg-primary bg-opacity-10 text-primary rounded-circle mx-auto mb-4"
                >
                  <i class="bi bi-people-fill fs-3"></i>
                </div>
                <h5 class="fw-bold mb-3">Đội ngũ chuyên gia</h5>
                <p class="text-muted">
                  Giảng viên giàu kinh nghiệm, chuyên môn cao từ các trường đại
                  học hàng đầu.
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body text-center p-4">
                <div
                  class="icon-box bg-primary bg-opacity-10 text-primary rounded-circle mx-auto mb-4"
                >
                  <i class="bi bi-book-half fs-3"></i>
                </div>
                <h5 class="fw-bold mb-3">Phương pháp hiện đại</h5>
                <p class="text-muted">
                  Áp dụng phương pháp học tập tiên tiến, cá nhân hóa lộ trình
                  cho từng học viên.
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body text-center p-4">
                <div
                  class="icon-box bg-primary bg-opacity-10 text-primary rounded-circle mx-auto mb-4"
                >
                  <i class="bi bi-award fs-3"></i>
                </div>
                <h5 class="fw-bold mb-3">Cam kết chất lượng</h5>
                <p class="text-muted">
                  Đảm bảo đầu ra cho học viên với hệ thống đánh giá và hỗ trợ
                  liên tục.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Features Section -->
    <!-- Features Section -->
    <section class="features-section py-5 bg-light">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-lg-6 order-lg-2">
            <h2 class="fw-bold mb-5">Tính năng nổi bật</h2>
            <div class="feature-list">
              <div class="feature-item d-flex mb-4">
                <div
                  class="feature-icon bg-primary text-white rounded-circle me-4"
                >
                  <i class="bi bi-check-lg fs-4"></i>
                </div>
                <div class="feature-text">
                  <h5 class="fw-bold">Học mọi lúc, mọi nơi</h5>
                  <p class="text-muted mb-0">
                    Truy cập kho bài giảng và tài liệu 24/7 trên mọi thiết bị.
                  </p>
                </div>
              </div>
              <div class="feature-item d-flex mb-4">
                <div
                  class="feature-icon bg-primary text-white rounded-circle me-4"
                >
                  <i class="bi bi-check-lg fs-4"></i>
                </div>
                <div class="feature-text">
                  <h5 class="fw-bold">Luyện đề đa dạng</h5>
                  <p class="text-muted mb-0">
                    Ngân hàng đề thi phong phú với đầy đủ các môn học và cấp độ.
                  </p>
                </div>
              </div>
              <div class="feature-item d-flex mb-4">
                <div
                  class="feature-icon bg-primary text-white rounded-circle me-4"
                >
                  <i class="bi bi-check-lg fs-4"></i>
                </div>
                <div class="feature-text">
                  <h5 class="fw-bold">Theo dõi tiến độ</h5>
                  <p class="text-muted mb-0">
                    Hệ thống báo cáo chi tiết giúp bạn đánh giá sự tiến bộ của
                    bản thân.
                  </p>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-6 order-lg-1">
            <img
              src="@/assets/images/banner-feature.png"
              alt="Study Zone Features"
              class="img-fluid rounded shadow"
            />
          </div>
        </div>
      </div>
    </section>

    <!-- Testimonials Section -->
    <section class="testimonials-section py-5">
      <div class="container">
        <div class="row justify-content-center mb-5">
          <div class="col-lg-8 text-center">
            <h2 class="fw-bold mb-3">Học viên nói gì về chúng tôi</h2>
            <p class="text-muted">
              Hàng nghìn học viên đã tin tưởng và đồng hành cùng Study Zone
            </p>
          </div>
        </div>
        <div class="row g-4">
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body p-4">
                <div class="d-flex align-items-center mb-3">
                  <img
                    src="@/assets/images/user-avatar.png"
                    alt="Student"
                    class="rounded-circle me-3 testimonial-avatar"
                  />
                  <div>
                    <h6 class="fw-bold mb-0">Nguyễn Văn A</h6>
                    <small class="text-muted">Học viên Tiếng Anh</small>
                  </div>
                </div>
                <p class="mb-0">
                  "Study Zone giúp mình hệ thống kiến thức rõ ràng, phương pháp
                  dạy dễ hiểu. Mình đã cải thiện điểm số từ 5 lên 8 chỉ sau 3
                  tháng."
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body p-4">
                <div class="d-flex align-items-center mb-3">
                  <img
                    src="@/assets/images/user-avatar.png"
                    alt="Student"
                    class="rounded-circle me-3 testimonial-avatar"
                  />
                  <div>
                    <h6 class="fw-bold mb-0">Trần Thị B</h6>
                    <small class="text-muted">Học viên Tiếng Anh</small>
                  </div>
                </div>
                <p class="mb-0">
                  "Mình rất thích cách giảng dạy sinh động của các thầy cô. Ngân
                  hàng đề thi phong phú giúp mình tự tin hơn trước mỗi kỳ thi."
                </p>
              </div>
            </div>
          </div>
          <div class="col-md-4">
            <div class="card h-100 border-0 shadow-sm">
              <div class="card-body p-4">
                <div class="d-flex align-items-center mb-3">
                  <img
                    src="@/assets/images/user-avatar.png"
                    alt="Student"
                    class="rounded-circle me-3 testimonial-avatar"
                  />
                  <div>
                    <h6 class="fw-bold mb-0">Lê Văn C</h6>
                    <small class="text-muted">Học viên Luyện thi ĐH</small>
                  </div>
                </div>
                <p class="mb-0">
                  "Lộ trình học cá nhân hóa phù hợp với năng lực của mình. Nhờ
                  đó mình đã đạt 27 điểm trong kỳ thi THPT Quốc gia vừa qua."
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section py-5 bg-primary text-white mb-5">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-lg-8 text-center">
            <h2 class="fw-bold mb-4">
              Sẵn sàng bắt đầu hành trình học tập của bạn?
            </h2>
            <p class="lead mb-4">
              Đăng ký ngay để trải nghiệm hệ thống học tập thông minh và hiệu
              quả
            </p>
            <router-link
              to="/register"
              class="btn btn-light btn-lg px-5 fw-bold"
            >
              Đăng ký miễn phí
            </router-link>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
// Bạn có thể thêm các logic cần thiết ở đây
</script>

<style lang="scss" scoped>
.introduction-page {
  padding-top: 0;
  margin-top: 0;
}

.hero-section {
  padding-top: 5rem;
  padding-bottom: 5rem;
}

.icon-box {
  width: 60px;
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.feature-icon {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.cta-section {
  background: linear-gradient(135deg, #1877f2, #0d5dbd);
}

.card {
  transition: transform 0.3s ease;

  &:hover {
    transform: translateY(-5px);
  }
}

.btn-primary {
  background-color: #1877f2;
  border-color: #1877f2;

  &:hover {
    background-color: #0d5dbd;
    border-color: #0d5dbd;
  }
}

.btn-outline-primary {
  color: #1877f2;
  border-color: #1877f2;

  &:hover {
    background-color: #1877f2;
    color: white;
  }
}

.testimonial-avatar {
  width: 50px;
  height: 50px;
  object-fit: cover;
}

.feature-list {
  display: flex;
  flex-direction: column;
}

.feature-item {
  align-items: flex-start;
}

.feature-text {
  flex: 1;
}
</style>
