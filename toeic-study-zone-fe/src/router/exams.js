import TestExam from "@/views/user/TestExam.vue";

export default [
  {
    path: "/test/:id",
    children: [
      {
        path: "",
        name: "TestExam",
        component: TestExam,
        meta: { requiresAuth: true },
      },
    ],
  },
];
