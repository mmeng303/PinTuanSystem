import request from "@/api/api";
import { Activity, Payment, QueryActivityListParams } from "./util";


export function QueryActivityList(params: QueryActivityListParams): Promise<any> {
    return request({
        url: '/api/activity/list',
        method: 'post',
        data: params
    })
}

export function AddActivity(params: Activity): Promise<any> {
    return request({
        url: '/api/activity/release',
        method: 'post',
        data: params
    })
}

export function GetActivityDetail(id: number): Promise<any> {
    return request({
        url: '/api/activity/' + id,
        method: 'get',
    })
}

export function PaymentApi(pay: Payment): Promise<any> {
    return request({
        url: '/api/fee/payment',
        method: 'post',
        data: pay
    })
}

export function SettlementApi(id: number): Promise<any> {
    return request({
        url: '/api/activity/completed/' + id,
        method: 'get',
    })
}