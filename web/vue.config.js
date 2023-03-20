const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    host: 'localhost',
    port: '8080',
    proxy: {
      '/api': {
        target: 'http://localhost:8000/api/',
        changeOrigin: true,
        pathRewrite: {  // 重写路径
          '^/api': ''  // 把/api变为空字符
        }
      }
    }
  }
})