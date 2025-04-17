<template>
  <header class="admin-header">
    <div class="title">TRANG QUẢN TRỊ</div>
    <div class="right">
      <i class="fas fa-bell"></i>
      <i class="fas fa-user-circle"></i>
    </div>
  </header>
</template>

<script setup>
// header đơn giản, không cần logic
</script>

<style scoped>
.admin-header {
  height: 60px;
  background-color: #ffffff;
  border-bottom: 1px solid #ddd;
  padding: 0 20px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: sticky;
  top: 0;
  z-index: 10;
}

.title {
  font-size: 1.2rem;
  font-weight: 600;
  color: #1f2d3d;
}

.right i {
  font-size: 1.2rem;
  margin-left: 20px;
  cursor: pointer;
  color: #555;
}

.right i:hover {
  color: #000;
}
</style>
