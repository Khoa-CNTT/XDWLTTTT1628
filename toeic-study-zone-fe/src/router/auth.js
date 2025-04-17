import Login from "../views/auth/Login.vue";
import Register from "../views/auth/Register.vue";

const authRoutes = [
  {
    path: "/login",
    name: "Login",
    component: Login,
    meta: { layout: "auth" },
  },
  {
    path: "/register",
    name: "Register",
    component: Register,
    meta: { layout: "auth" },
  },
];

export default authRoutes;
