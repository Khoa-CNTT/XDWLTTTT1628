<template>
  <div class="auth-layout">
    <slot />
  </div>
</template>

<style lang="scss" scoped>
.auth-layout {
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center; /* Đổi từ center sang flex-start */
  background-color: #f0f2f5;
}
</style>
