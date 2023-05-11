import { createRouter, createWebHistory } from 'vue-router'

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('../views/Home.vue')
    },
    {
        path: '/movie/:id',
        name: 'MovieDetail',
        component: () => import('../views/MovieDetail.vue')
    },
    {
        path: '/login',
        name: 'Login',
        component: () => import('../views/Login.vue')
    },
    {
        path: '/register',
        name: 'Register',
        component: () => import('../views/Register.vue')
    },
    {
        path: "/profile",
        name: 'Profile',
        component: () => import('../views/Profile.vue'),
        meta: { requiresAuth: true } // 添加需要登陆拦截的标记
    },
    {
        path: "/modification",
        name: "Modification",
        component: () => import('../views/Modification.vue'),
    },
    {
        path: '/test',
        name: 'Test',
        component: () => import('../views/Test.vue'),
    }
]

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
