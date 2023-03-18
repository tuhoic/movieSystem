import axios from 'axios';

// 定义auth API的基础URL
const baseUrl = '/api/user';

// 用户注册
export const register = (data) => {
    return axios.post(`${baseUrl}/register`, data).then((response) => response.data);
};

// 用户登录
export const login = (username, password) => {
    console.log(username);
    console.log(password);
    return axios.post(`${baseUrl}/login`, username, password).then((response) => response.data);
};

// 获取当前登录用户信息
export const getCurrentUser = () => {
    return axios.get(`${baseUrl}/user`).then((response) => response.data);
};

// 更新当前登录用户信息
export const updateCurrentUser = (data) => {
    return axios.put(`${baseUrl}/user`, data).then((response) => response.data);
};

// 更新当前登录用户密码
export const updatePassword = (data) => {
    return axios.put(`${baseUrl}/password`, data).then((response) => response.data);
};
