import axios from 'axios';

// 定义order API的基础URL
const baseUrl = '/api/orders';

// 获取当前用户的订单列表
export const getOrderList = () => {
    return axios.get(`${baseUrl}`).then((response) => response.data);
};

// 获取指定订单详情
export const getOrderDetail = (orderId) => {
    return axios.get(`${baseUrl}/${orderId}`).then((response) => response.data);
};

// 创建订单
export const createOrder = (data) => {
    return axios.post(`${baseUrl}`, data).then((response) => response.data);
};

// 取消订单
export const cancelOrder = (orderId) => {
    return axios.delete(`${baseUrl}/${orderId}`).then((response) => response.data);
};
