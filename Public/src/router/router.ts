import { createRouter, createWebHistory } from "vue-router"


interface RouteConfig {
    path: string;
    name: string;
    component: any;
    meta?: any;
    children?: any;
}
const routes: Array<RouteConfig> = [
    {
        path: "/login",
        name: "login",
        component: () => import("@/views/login/index.vue"),
        meta: {
            title: "登录",
            keepAlive: false
        }
    },
    {
        path: "/",
        name: "home",
        component: () => import("@/views/home/index.vue"),
        meta: {
            title: "首页",
            keepAlive: false
        },
        children: [
            {
                path: "/user",
                name: "user",
                component: () => import("@/views/user/user.vue"),
                meta: {
                    title: "用户管理",
                    keepAlive: false
                },
            },
            {
                // team
                path: "/team",
                name: "team",
                component: () => import("@/views/team/index.vue"),
                meta: {
                    title: "团队管理",
                    keepAlive: false
                },
            },
            {
                // activity
                path: "/activity",
                name: "activity",
                component: () => import("@/views/activity/index.vue"),
                meta: {
                    title: "活动",
                    keepAlive: false
                },
            },
            {
                // cost
                path: "/cost",
                name: "cost",
                component: () => import("@/views/cost/index.vue"),
                meta: {
                    title: "费用",
                    keepAlive: false
                },
            },
            {
                // overview
                path: "/overview",
                name: "overview",
                component: () => import("@/views/overview/index.vue"),
                meta: {
                    title: "概览",
                    keepAlive: false
                },
            },
            {
                // accepted
                path: "/accepted",
                name: "accepted",
                component: () => import("@/views/accepted/index.vue"),
                meta: {
                    title: "管理邀请",
                    keepAlive: false
                },
            },
            {
                // userTeam
                path: "/userTeam",
                name: "userTeam",
                component: () => import("@/views/userTeam/index.vue"),
                meta: {
                    title: "团队管理",
                    keepAlive: false
                },
            }
        ]
    },

]
const router = createRouter({
    history: createWebHistory(),
    routes
})
router.beforeEach((to, _from, next) => {
    // 如果是登录页面，直接放行
    document.title = to.meta.title as string;
    if (to.path == "/login") {
        next();
        return;
    }
    // 如果不是登录页面，判断是否有token
    const token = localStorage.getItem("token");
    if (token) {
        if (to.path == "/") {
            let authority = localStorage.getItem("authority");
            if (authority == "1") {
                next("/user");
            } else {
                next("/userTeam")
            }
            return;
        }
        next();
        return;
    }
    // 如果没有token，跳转到登录页面
    next("/login");
})


export default router