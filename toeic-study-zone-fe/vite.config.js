import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@use "bootstrap/scss/bootstrap" as *;`,
        includePaths: ["node_modules", "src/assets/scss/bootstrap-custom"],
      },
    },
  },
  // server: {
  //   port: 5173, // Đảm bảo frontend chạy trên cổng 5173 (mặc định của Vite)
  //   proxy: {
  //     '/api': {
  //       target: 'http://localhost:8080', // Chuyển tiếp request tới backend
  //       changeOrigin: true,
  //       secure: false,
  //     },
  //   },
  // },
});