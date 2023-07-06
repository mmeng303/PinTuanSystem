import axios from "axios";
import router from "@/router/router";
const request = axios.create({});
// window上挂在message
import { useMessage } from 'naive-ui'
const msg = useMessage()
// @ts-ignore
window.$message = msg
request.interceptors.request.use(
    (config) => {
        // 如果有token，就携带token
        const token = localStorage.getItem("token");
        if (token) {
            config.headers.token = token;
        }
        return config;
    }
)
request.interceptors.response.use(
    (response) => {
        
        if (response.data === "") {
            router.push({
                replace: true,
                path: "/login"
            })
            return
        }
        return response.data;
    },
    (error) => {
        // 状态码为401重定向到登录页面清除token
        if (error.response.status == 401) {
            router.push({
                replace: true,
                path: "/login"
            })
            return Promise.reject(error);
        }
        // 403权限不够
        if (error.response.status == 403) {
            return Promise.reject(error);
        }
        // 500也退出到登录页面
        if (error.response.status == 500) {
        }
        return Promise.reject(error);
    }
)
export default request;