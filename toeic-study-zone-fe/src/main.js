import { createApp } from "vue";
import pinia from "./store"; 
import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "./assets/scss/main.scss";
import "./style.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

import AuthLayout from "./layouts/AuthLayout.vue";

const app = createApp(App);

app.component("auth-layout", AuthLayout);

app.use(pinia); 
app.use(router);
app.mount("#app");