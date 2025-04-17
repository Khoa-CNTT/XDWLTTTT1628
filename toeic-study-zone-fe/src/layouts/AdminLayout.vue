<template>
  <div class="admin-layout">
    <NavbarAdmin />
    <div class="content">
      <HeaderAdmin />
      <router-view />
    </div>
  </div>
</template>

<script setup>
import NavbarAdmin from "@/components/common/NavbarAdmin.vue";
import HeaderAdmin from "@/components/common/HeaderAdmin.vue";
</script>

<style scoped>
.admin-layout {
  display: flex;
}

.content {
  margin-left: 250px; /* đúng bằng chiều ngang sidebar */
  flex-grow: 1;
}
</style>
