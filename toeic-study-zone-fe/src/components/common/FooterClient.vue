<template>
  <footer class="footer">
    <div class="container">
      <div class="row">
        <div class="col-md-3">
          <h5 class="footer-title">Study Zone</h5>
          <p class="footer-description">Học tập thông minh, hiệu quả.</p>
          <div class="social-links">
            <a href="#" class="social-link"
              ><i class="fab fa-facebook-f"></i
            ></a>
            <a href="#" class="social-link"><i class="fab fa-youtube"></i></a>
            <a href="#" class="social-link"><i class="fab fa-tiktok"></i></a>
          </div>
        </div>
        <div class="col-md-3">
          <h5 class="footer-title">Về Study Zone</h5>
          <ul class="footer-links">
            <li><a href="#">Giới thiệu</a></li>
            <li><a href="#">Tính năng</a></li>
            <li><a href="#">Chính sách dịch vụ</a></li>
          </ul>
        </div>
        <div class="col-md-3">
          <h5 class="footer-title">Chính sách dịch vụ</h5>
          <ul class="footer-links">
            <li><a href="#">Chính sách bảo mật</a></li>
            <li><a href="#">Điều khoản sử dụng</a></li>
            <li><a href="#">Chính sách hoàn tiền</a></li>
          </ul>
        </div>
        <div class="col-md-3">
          <h5 class="footer-title">Thông tin doanh nghiệp</h5>
          <p class="footer-description">
            Công ty TNHH Công nghệ Study Zone<br />
            Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM<br />
            Email: support@studyzone.com<br />
            Hotline: 1900 1234
          </p>
        </div>
      </div>
      <div class="footer-bottom">
        <p>© 2025 Study Zone. All rights reserved.</p>
      </div>
    </div>
  </footer>
</template>

<script setup></script>

<style lang="scss" scoped>
.footer {
  background-color: #1c2526;
  color: #fff;
  padding: 3rem 0;
  position: relative; /* Đảm bảo footer không che khuất nội dung */
}

.footer-title {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.footer-description {
  font-size: 0.9rem;
  color: #adb5bd;
}

.social-links {
  display: flex;
  gap: 1rem;
  justify-content: center;
}

.social-link {
  color: #adb5bd;
  font-size: 1.25rem;
}

.social-link:hover {
  color: #1877f2;
}

.footer-links {
  list-style: none;
  padding: 0;
}

.footer-links li {
  margin-bottom: 0.5rem;
}

.footer-links a {
  color: #adb5bd;
  font-size: 0.9rem;
  text-decoration: none;
}

.footer-links a:hover {
  color: #1877f2;
}

.footer-bottom {
  text-align: center;
  margin-top: 2rem;
  border-top: 1px solid #adb5bd;
  padding-top: 1rem;
}

.footer-bottom p {
  font-size: 0.9rem;
  color: #adb5bd;
  margin: 0;
}
</style>
