<template>
  <component :is="layout">
    <router-view />
  </component>
</template>

<script setup>
import { computed } from "vue";
import { useRoute } from "vue-router";

// Import các layout
import DefaultLayout from "@/layouts/DefaultLayout.vue";
import AuthLayout from "@/layouts/AuthLayout.vue";
import AdminLayout from "@/layouts/AdminLayout.vue";

// Đăng ký các layout
const layouts = {
  "default-layout": DefaultLayout,
  "auth-layout": AuthLayout,
  "admin-layout": AdminLayout,
};

// Lấy route hiện tại
const route = useRoute();

// Tính toán layout dựa trên meta.layout của route
const defaultLayout = "default";
const layout = computed(() => {
  const layoutName = route.meta.layout || defaultLayout;
  return layouts[`${layoutName}-layout`] || DefaultLayout;
});
</script>

<style lang="scss">
#app {
  height: 100vh !important;
  width: 100vw !important;
  max-width: none !important;
  margin: 0 !important;
  padding: 0 !important;
  display: flex;
  flex-direction: column;
  overflow: auto !important;
}
</style>
