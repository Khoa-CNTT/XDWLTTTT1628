import { createApp } from "vue";
import { createPinia } from "pinia"; // Import Pinia
import App from "./App.vue"; // App.vue
import router from "./router"; // Import router

import "bootstrap/dist/css/bootstrap.min.css"; // CSS cơ bản
import "bootstrap"; // JavaScript của Bootstrap
import "./assets/scss/main.scss"; // Import SCSS tùy chỉnh của bạn
import "./style.css"; // Giữ nguyên nếu bạn có file CSS riêng
import "@fortawesome/fontawesome-free/css/all.min.css";
// Import layouts
import AuthLayout from "./layouts/AuthLayout.vue";

const app = createApp(App);

app.component("auth-layout", AuthLayout);

app.use(createPinia()); // Tích hợp Pinia
app.use(router);
app.mount("#app");
