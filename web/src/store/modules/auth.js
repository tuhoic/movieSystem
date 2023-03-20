import axios from 'axios'
import router from "@/router";

const state = {
    isAuthenticated: false,
    user: null
}

const getters = {
    isAuthenticated: state => state.isAuthenticated,
    user: state => state.user
}

const actions = {
    async login({ commit }, loginForm) {
        try {
            const response = await axios.post('/user/login', loginForm)
            console.log(loginForm.username)
            console.log(loginForm.password)
            if (response.data.code === 200) {
                const token = response.data.data
                console.log(token)
                localStorage.setItem('token', token) // 将token存储在本地
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}` // 设置axios默认请求头
                const userResponse = await axios.get('/user/info') // 根据token获取用户信息
                if (userResponse.data.code === 200) {
                    const user = userResponse.data.data
                    commit('LOGIN_USER', user)
                    localStorage.setItem('user', JSON.stringify(user))
                    await router.push("/")
                } else {
                    alert(userResponse.data.message)
                }
            } else {
                alert(response.data.message)
            }
        } catch (error) {
            console.log(error)
        }
    },

    async logout({ commit}) {
        try {
            await axios.post('/api/logout');
            commit('LOGOUT_USER', null)
            localStorage.removeItem('token'); // 移除本地存储的 token
            delete axios.defaults.headers.common['Authorization']; // 删除 axios 的默认请求头中的 token
            router.push("/")
        } catch (error) {
            console.log(error);
        }
    }
}

const mutations = {
    LOGIN_USER(state, user) {
        state.isAuthenticated = true
        state.user = user
    },
    LOGOUT_USER(state) {
        state.isAuthenticated = false
        state.user = null
    }
}

export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true
}
