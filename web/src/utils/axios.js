import axios from 'axios'

const instance = axios.create({
    baseURL: 'http://localhost:8000' // 配置请求的基础路径
})

// 请求拦截器
instance.interceptors.request.use(config => {
    // 修改图片请求路径
    if (config.url.endsWith('.jpg') || config.url.endsWith('.png')) {
        config.url = 'http://localhost:8000/static/images' + config.url
    }
    return config
}, error => {
    return Promise.reject(error)
})

export default instance
