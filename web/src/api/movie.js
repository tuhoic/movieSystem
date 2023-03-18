import axios from 'axios';

// 定义movie API的基础URL
const baseUrl = '/api/movie';

// 获取电影列表
export const getMovieList = (params) => {
    return axios.get(baseUrl, { params }).then((response) => response.data);
};

// 获取单个电影信息
export const getMovieDetail = (id) => {
    return axios.get(`${baseUrl}/${id}`).then((response) => response.data);
};

// 创建电影信息
export const createMovie = (data) => {
    return axios.post(baseUrl, data).then((response) => response.data);
};

// 更新电影信息
export const updateMovie = (id, data) => {
    return axios.put(`${baseUrl}/${id}`, data).then((response) => response.data);
};

// 删除电影信息
export const deleteMovie = (id) => {
    return axios.delete(`${baseUrl}/${id}`).then((response) => response.data);
};

// 搜索电影
export const searchMovie = (name) => {
    return axios.get(`${baseUrl}/search/${name}`).then((response) => response.data);
};
