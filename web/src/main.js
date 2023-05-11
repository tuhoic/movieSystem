import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "@/router";
import store from "@/store";
import axios from "axios";
import '@fortawesome/fontawesome-free/css/all.css'

axios.defaults.baseURL = 'http://localhost:8000'

const app = createApp(App)
app.config.globalProperties.$axios = axios

store.commit('auth/INIT_USER')

app.use(ElementPlus)
app.use(router)
app.use(store)


app.mount('#app')
