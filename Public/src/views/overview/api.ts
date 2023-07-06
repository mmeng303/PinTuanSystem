import request from "@/api/api";

export function GetPieData() {
    return request({
        url: '/api/index/pie',
        method: 'post'
    })
}

export function GetColumnData() {
    return request({
        url: '/api/index/column',
        method: 'post'
    })
}

export function GetActivityCountApi() {
    return request({
        url: '/api/index/activityCount',
        method: 'post'
    })
}

export function GetUserCount(){
    return request({
        url: '/api/index/userCount',
        method: 'post'
    })
}