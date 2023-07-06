<template>
    <div class="login_box">
        <n-card title="您好，请先登录!" class="card">
            <n-form label-position="top" :model="logingForm">
                <n-form-item label="用户名">
                    <n-input placeholder="请输入用户名" v-model:value="logingForm.username"></n-input>
                </n-form-item>
                <n-form-item label="密码">
                    <n-input placeholder="请输入密码" type="password" v-model:value="logingForm.password"></n-input>
                </n-form-item>
                <n-form-item>
                    <n-button type="primary" @click="loginFunc">登录</n-button>
                </n-form-item>
            </n-form>
        </n-card>
        <img src="@/assets/mybg.jpg" alt="">
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import { loginApi } from './api';
import { useMessage } from "naive-ui";
import router from '@/router/router';
const message = useMessage();

interface Login {
    username: string;
    password: string;
}
const logingForm = ref<Login>({
    username: '',
    password: ''
})

const loginFunc = async () => {
    let data = await loginApi(logingForm.value);
    if (data.code != 0) {
        message.error(data.msg);
    } else {
        message.success("登录成功");
        localStorage.setItem('token', data.data.token);
        localStorage.setItem('username', data.data.user.username);
        localStorage.setItem('name', data.data.user.name);
        localStorage.setItem('authority', data.data.user.authority);
        localStorage.setItem('id', data.data.user.id);
        router.push('/');
    }
}
</script>

<style scoped>
.login_box {
    width: 100%;
    height: 100%;
    background-color: #f5f5f5;
    position: relative;
}

.card {
    position: absolute;
    /* 靠右中 */
    top: 50%;
    transform: translateY(-50%);
    width: 30%;
    right: 10%;
    max-width: 400px;
    min-width: 300px;
    z-index: 2;
    /* 阴影 */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.login_box img {
    width: 100vw;
    height: 100vh;
    position: absolute;
    z-index: 0;
    right: 0;
    top: 0;
}
</style>