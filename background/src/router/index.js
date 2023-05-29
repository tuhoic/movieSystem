import { createRouter, createWebHistory } from 'vue-router'
import Dashboard from "@/views/Dashboard.vue";
import Login from "@/views/Login.vue";
import UserManagement from "@/views/UserManagement.vue";
import MovieManagement from "@/views/MovieManagement.vue";
import RatingManagement from "@/views/RatingManagement.vue";
import CommentManagement from "@/views/CommentManagement.vue";

const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
        path: '/dashboard',
        name: 'Dashboard',
        component: Dashboard
    },
    {
        path: '/user-management',
        name: 'UserManagement',
        component: UserManagement
    },
    {
        path: '/movie-management',
        name: 'MovieManagement',
        component: MovieManagement
    },
    {
        path: '/rating-management',
        name: 'RatingManagement',
        component: RatingManagement
    },
    {
        path: '/comment-management',
        name: 'commentManagement',
        component: CommentManagement
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

// 在路由器中添加拦截器
router.beforeEach((to, from, next) => {
    // 判断是否需要进行登陆拦截
    if (to.meta.requiresAuth) {
        // 判断用户是否已登录
        const isLoggedIn = Boolean(localStorage.getItem('token')) // 假设登陆时将 token 存储在 localStorage 中
        if (isLoggedIn) {
            next()
        } else {
            next('/login') // 如果用户未登录，重定向到登录页面
        }
    } else {
        next()
    }
})

export default router
