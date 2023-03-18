import axios from 'axios';

// 定义comment API的基础URL
const baseUrl = '/api/comments';

// 获取指定电影的评论列表
export const getCommentList = (movieId) => {
    return axios.get(`${baseUrl}/movie/${movieId}`).then((response) => response.data);
};

// 创建评论
export const createComment = (data) => {
    return axios.post(`${baseUrl}`, data).then((response) => response.data);
};

// 删除评论
export const deleteComment = (commentId) => {
    return axios.delete(`${baseUrl}/${commentId}`).then((response) => response.data);
};

// 修改评论
export const updateComment = (commentId) => {
    return axios.put(`${baseUrl}/${commentId}`).then((response) => response.data);
}