import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/Home.vue";
import authRoutes from "./auth";

// User
import Dashboard from "@/views/user/Dashboard.vue";
import Introduction from "@/views/user/Introduction.vue";
import TestHome from "@/views/user/TestHome.vue";
import Profile from "@/views/user/Profile.vue";
import ScheduleForm from "@/views/user/Schedule/ScheduleForm.vue";
import TestHistory from "@/views/user/TestHistory.vue";
import Test from "@/views/user/Test.vue";
import PlacementTest from "@/views/user/PlacementTest.vue";
import examsRoutes from "./exams";
import SubmitTest from "@/views/user/SubmitTest.vue";

// Admin
import DashboardAdmin from "@/views/admin/AdminDashboard.vue";
import UserManager from "@/views/admin/UserManager.vue";
import TestHistoryManager from "@/views/admin/TestHistoryManager.vue";
import Statistics from "@/views/admin/Statistics.vue";
import ScheduleManager from "@/views/admin/ScheduleManager.vue";
import Comments from "@/views/admin/CommentManager.vue";
import Ads from "@/views/admin/AdsManager.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: { layout: "default" },
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: { layout: "default", requiresAuth: true }, // Thêm requiresAuth
  },
  {
    path: "/introduction",
    name: "Introduction",
    component: Introduction,
    meta: { layout: "default" },
  },
  {
    path: "/test-home",
    name: "TestHome",
    component: TestHome,
    meta: { layout: "default" },
  },
  {
    path: "/test",
    name: "Test",
    component: Test,
    meta: { layout: "default" },
  },
  {
    path: "/profile",
    name: "Profile",
    component: Profile,
    meta: { layout: "default" },
  },
  {
    path: "/schedule-form",
    name: "ScheduleForm",
    component: ScheduleForm,
    meta: { layout: "default" },
  },
  {
    path: "/schedule-discover",
    name: "ScheduleDiscover",
    component: () => import("@/views/user/Schedule/ScheduleList.vue"),
    meta: { layout: "default" },
  },
  {
    path: "/test-history",
    name: "TestHistory",
    component: TestHistory,
    meta: { layout: "default" },
  },
  {
    path: "/placement-test",
    name: "PlacementTest",
    component: PlacementTest,
    meta: { layout: "default" },
  },
  {
    path: "/submit-test",
    name: "SubmitTest",
    component: SubmitTest,
    meta: { layout: "default" },
  },

  ...authRoutes, // Spread các route từ authRoutes
  ...examsRoutes,

  // Admin router
  {
    path: "/admin/dashboard",
    name: "DashboardAdmin",
    component: DashboardAdmin,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/test-history",
    name: "TestHistoryManager",
    component: TestHistoryManager,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/users-manager",
    name: "UserManager",
    component: UserManager,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/statistics",
    name: "Statistics",
    component: Statistics,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/comments",
    name: "Comments",
    component: Comments,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/schedule-manager",
    name: "ScheduleManager",
    component: ScheduleManager,
    meta: { layout: "admin" },
  },
  {
    path: "/admin/ads",
    name: "Ads",
    component: Ads,
    meta: { layout: "admin" },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Navigation Guard để kiểm tra trạng thái đăng nhập
// router.beforeEach((to, from, next) => {
//   const isLoggedIn = localStorage.getItem("isLoggedIn") === "true";
//   console.log("Navigating to:", to.name, "isLoggedIn:", isLoggedIn); // Debug

//   if (to.meta.requiresAuth && !isLoggedIn) {
//     // Nếu route yêu cầu đăng nhập mà chưa đăng nhập, chuyển hướng về /login
//     next("/login");
//   } else if (to.name === "Login" && isLoggedIn) {
//     // Nếu đã đăng nhập mà cố truy cập /login, chuyển hướng về /dashboard
//     next("/dashboard");
//   } else {
//     next();
//   }
// });

export default router;
