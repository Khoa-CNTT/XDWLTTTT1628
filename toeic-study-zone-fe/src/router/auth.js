import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

const authRoutes = [
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: { layout: "auth", requiresGuest: true },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: { layout: "auth", requiresGuest: true },
  },
];

export default authRoutes;