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
        component: () => import('../views/Profile.vue')
    },
    {
        path: "/modification",
        name: "Modification",
        component: () => import('../views/Modification.vue')
    },
    {
        path: '/recommendations',
        name: 'Recommendations',
        component: () => import('../views/Recommendations.vue'),
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

export default router
