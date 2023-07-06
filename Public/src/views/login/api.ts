import request from "@/api/api"

export function loginApi(loginForm: any): Promise<any> {
    return request({
        url: "/api/user/login",
        method: "post",
        data: loginForm
    })
}