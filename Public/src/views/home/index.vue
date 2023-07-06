<template>
    <div class="home">
        <nav>
            <div class="nav_title">
                管理中心
            </div>
            <div class="nav_icon" @click="switchLedtCollapse">
                <ChevronRight class="icon" v-if="leftCollapse"></ChevronRight>
                <ChevronLeft class="icon" v-else></ChevronLeft>
            </div>
            <div class="nav_user">
                <n-dropdown trigger="hover" :options="options" @select="handleSelect">
                    <n-gradient-text :size="24" type="success">
                        {{ username }}
                    </n-gradient-text>
                </n-dropdown>
            </div>
        </nav>
        <div class="main">
            <n-menu :collapsed="leftCollapse" :value="leftMenuValue"
                :class="{ left_menu: !leftCollapse, left_menu2: leftCollapse }" :options="menuOptions"
                @update:value="handleUpdateValue">
            </n-menu>
            <main>
                <router-view v-slot="{ Component }">
                    <KeepAlive>
                        <component :is="Component" :key="$route.name" v-if="$route.meta.keepAlive" />
                    </KeepAlive>
                    <component :is="Component" :key="$route.name" v-if="!$route.meta.keepAlive" />
                </router-view>
            </main>
        </div>
    </div>
</template>

<script setup lang="ts">
import router from '@/router/router'
import { h, onMounted, ref } from "vue"
import { RouterLink } from 'vue-router';
import { NIcon } from 'naive-ui'
const authority = localStorage.getItem('authority') ? Number(localStorage.getItem('authority')) : 0
const leftCollapse = ref(false)
const username = localStorage.getItem('username')
const leftMenuValue = ref('')
import { UserAvatar as UserIcon, ChevronRight, ChevronLeft } from "@vicons/carbon"
onMounted(() => {
    leftMenuValue.value = router.currentRoute.value.path;
})
const options = [
    {
        label: '退出登录',
        key: 'logout'
    }
]
const renderIcon = (icon: any) => {
    return () => h(NIcon, null, {
        default: () => h(icon)
    })
}
const menuOptions = [
    {
        label: () => h(RouterLink, { to: '/overview' }, {
            default: () => '数据概览'
        }),
        icon: renderIcon(UserIcon),
        show: authority == 1,
        key: '/overview'
    },
    {
        label: () => h(RouterLink, { to: '/user' }, {
            default: () => '用户管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 1,
        key: '/user'
    },
    {
        label: () => h(RouterLink, { to: '/team' }, {
            default: () => '团队管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 1,
        key: '/team'
    },
    {
        label: () => h(RouterLink, { to: '/userTeam' }, {
            default: () => '团队管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 2,
        key: '/userTeam'
    },
    {
        label: () => h(RouterLink, { to: '/accepted' }, {
            default: () => '邀请管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 2,
        key: '/accepted'
    },
    {
        label: () => h(RouterLink, { to: '/activity' }, {
            default: () => '活动管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 1,
        key: '/activity'
    },
    {
        label: () => h(RouterLink, { to: '/cost' }, {
            default: () => '团费管理'
        })
        ,
        icon: renderIcon(UserIcon),
        show: authority == 1,
        key: '/cost'
    }
]
const handleUpdateValue = (value: string) => {
    leftMenuValue.value = value;
}
const switchLedtCollapse = () => {
    leftCollapse.value = !leftCollapse.value;
}
const handleSelect = (command: string) => {
    console.log(command);

    switch (command) {
        case 'logout':
            localStorage.clear();
            router.push('/login');
            break;
    }
}
</script>

<style scoped>
.home {
    display: flex;
    flex-direction: column;
    height: 100vh;
}

.main {
    display: flex;
    height: 100%;
}

.top_menu {
    border-bottom: none;
}

.title {
    font-size: 18px;
    padding: 0 1.4rem;
    color: white;
}

.left_menu {
    width: 10rem;
}

.left_menu2 {
    width: 3rem;
}

main {
    padding: 1rem;
    width: 100%;
    height: 100%;
}

.home nav {
    width: 100vw;
    height: 60px;
    background-color: #434b52;
    display: flex;
    align-items: center;
}

.home nav>div {
    letter-spacing: 2px;
    font-size: 1.1rem;
    cursor: pointer;
    transition: all .3s;
    color: white;
}

.nav_title {
    width: 10rem;
    text-align: center;
    height: 60px;
    line-height: 60px;
    font-weight: bold;
}

.nav_icon {
    width: 4rem;
    display: flex;
    align-items: center;
    justify-content: center;
    height: 60px;
}

.nav_icon .icon {
    width: 2rem;
    height: 2rem;
    color: white;
}

.home nav>div:hover {
    background-color: #31373c;
}

.nav_user {
    margin-left: auto;
    height: 60px;
    padding: 0 2rem;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
}

.drop {
    color: white;
    width: 100%;
    height: 60px;
    line-height: 60px;
}

.drop:focus {
    outline: none;
}

.drop /deep/ .el-dropdown-link:focus {
    outline: none;
}
</style>