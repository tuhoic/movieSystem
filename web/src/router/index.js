import { createRouter, createWebHistory } from 'vue-router'
import Home from '../components/HomePage.vue'
import Login from '../components/LoginPage.vue'
import Register from '../components/RegisterPage.vue'
import MovieList from '../components/MovieList.vue'
import AdminMovie from '../components/AdminMovie.vue'
import CommentList from '../components/CommentList.vue'
import OrderList from '../components/OrderList.vue'
import UserMovie from '../components/UserMovie.vue'

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/movies', component: MovieList },
    { path: '/admin/movies', component: AdminMovie },
    { path: '/admin/comments', component: CommentList },
    { path: '/admin/orders', component: OrderList },
    { path: '/user/movies', component: UserMovie },
]

const router = createRouter({
    history: createWebHistory(),
    routes,
})

export default router
