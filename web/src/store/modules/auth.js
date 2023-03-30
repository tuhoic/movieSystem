import router from "@/router";
import axios from "axios";

const state = {
    isAuthenticated: false,
    user: null
};

const getters = {
    isAuthenticated: state => state.isAuthenticated,
    user: state => state.user
};

const actions = {
    async login({ commit }, loginForm) {
        try {
            const response = await axios.post('/user/login', loginForm);
            if (response.data.code === 200) {
                const token = response.data.data;
                localStorage.setItem('token', token); // 将token存储在本地
                axios.defaults.headers.common['Authorization'] = `Bearer ${token}`; // 设置axios默认请求头
                const userResponse = await axios.get('/user/info'); // 根据token获取用户信息
                if (userResponse.data.code === 200) {
                    const user = userResponse.data.data;
                    commit('LOGIN_USER', user);
                    localStorage.setItem('user', JSON.stringify(user));
                    await router.push("/");
                } else {
                    alert(userResponse.data.message);
                }
            } else {
                alert(response.data.message);
            }
        } catch (error) {
            console.log(error);
        }
    },

    async logout({ commit }) {
        try {
            await axios.post('/user/logout');
            commit('LOGOUT_USER');
            localStorage.removeItem('token'); // 移除本地存储的 token
            localStorage.removeItem('user');
            delete axios.defaults.headers.common['Authorization']; // 删除 axios 的默认请求头中的 token
            await router.push("/");
        } catch (error) {
            console.log(error);
        }
    },

    async register({ commit }, registerForm) {
        try {
            const response = await axios.post('/user/register', registerForm);
            if (response.data.code === 200) {
                alert("注册成功!我们将在5s内跳到登陆页面!");
                commit('LOGOUT_USER')
                await router.push("/login");
            } else {
                alert(response.data.message);
            }
        }catch (error) {
            console.log(error);
        }
    },

    async modification({ commit }, modificationForm) {
        try {
            const data = {
                "username": modificationForm.username,
                "email": modificationForm.email,
                "password": modificationForm.password,
            };
            const response = await axios.post('/user/modification', data);
            if (response.data.code === 200) {
                alert("修改密码成功!我们将在5s内跳到登陆页面!");
                commit('LOGOUT_USER')
                await router.push("/login");
            } else {
                alert(response.data.message);
            }
        }catch (error) {
            console.log(error);
        }
    }
};

const mutations = {
    LOGIN_USER(state, user) {
        state.isAuthenticated = true;
        state.user = user;
    },
    LOGOUT_USER(state) {
        state.isAuthenticated = false;
        state.user = null;
    },
    INIT_USER(state) {
        const token = localStorage.getItem('token');
        const user = localStorage.getItem('user');
        if (token && user) {
            state.isAuthenticated = true;
            state.user = JSON.parse(user);
            axios.defaults.headers.common['Authorization'] = `Bearer ${token}`; // 设置axios默认请求头
        }
    }
};


export default {
    state,
    getters,
    actions,
    mutations,
    namespaced: true
}
