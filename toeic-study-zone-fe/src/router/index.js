import { createRouter, createWebHistory } from "vue-router";
import Home from "@/views/Home.vue";
import authRoutes from "./auth";
import examsRoutes from "./exams";
import authGuard from "../middleware/authGuard";
import guestGuard from "../middleware/guestGuard";
import adminGuard from "../middleware/adminGuard"; 

// User
import Dashboard from "@/views/user/Dashboard.vue";
import Introduction from "@/views/user/Introduction.vue";
import TestHome from "@/views/user/TestHome.vue";
import Profile from "@/views/user/Profile.vue";
import ScheduleForm from "@/views/user/Schedule/ScheduleForm.vue";
import TestHistory from "@/views/user/TestHistory.vue";
import Test from "@/views/user/Test.vue";
import PlacementTest from "@/views/user/PlacementTest.vue";
import SubmitTest from "@/views/user/SubmitTest.vue";

// Admin
import DashboardAdmin from "@/views/admin/AdminDashboard.vue";
import UserManager from "@/views/admin/UserManager.vue";
import TestHistoryManager from "@/views/admin/TestHistoryManager.vue";
import Statistics from "@/views/admin/Statistics.vue";
import ScheduleManager from "@/views/admin/ScheduleManager.vue";
import Comments from "@/views/admin/CommentManager.vue";
import Ads from "@/views/admin/AdsManager.vue";
import TestBank from "@/views/admin/TestBank.vue";
import TestManager from "@/views/admin/TestManager.vue";
import RoleManager from "@/views/admin/RoleManager.vue";

const routes = [
  {
    path: "/home",
    name: "Home",
    component: Home,
    meta: { layout: "default" },
  },
  {
    path: "/",
    redirect: "/home",
  },
  {
    path: "/dashboard",
    name: "Dashboard",
    component: Dashboard,
    meta: { layout: "default", requiresAuth: true },
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

  ...authRoutes,
  ...examsRoutes,

  // Admin router
  {
    path: "/admin/dashboard",
    name: "DashboardAdmin",
    component: DashboardAdmin,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true }, // Bảo vệ bởi requiresAdmin
  },
  {
    path: "/admin/test-history",
    name: "TestHistoryManager",
    component: TestHistoryManager,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/users-manager",
    name: "UserManager",
    component: UserManager,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/statistics",
    name: "Statistics",
    component: Statistics,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/comments",
    name: "Comments",
    component: Comments,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/schedule-manager",
    name: "ScheduleManager",
    component: ScheduleManager,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/ads",
    name: "Ads",
    component: Ads,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/test-bank",
    name: "TestBank",
    component: TestBank,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/test-manager",
    name: "TestManager",
    component: TestManager,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
  {
    path: "/admin/role-manager",
    name: "RoleManager",
    component: RoleManager,
    meta: { layout: "admin", requiresAuth: true, requiresAdmin: true },
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth) {
    return authGuard(to, from, next);
  }
  if (to.meta.requiresAdmin) {
    return adminGuard(to, from, next); // Kiểm tra Admin
  }
  if (to.meta.requiresGuest) {
    return guestGuard(to, from, next);
  }
  next();
});

export default router;