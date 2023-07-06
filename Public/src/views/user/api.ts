import request from "@/api/api";
import { QueryUserData, QueryUserListResponse } from "./util";
import { UserInfo } from "@/util";


export function QueryUserListApi(queryUserData: QueryUserData): Promise<QueryUserListResponse> {
    return request({
        url: "/api/user/allUsers",
        method: "post",
        data: queryUserData
    })
}

export function AddUserApi(userInfo: UserInfo): Promise<any> {
    return request({
        url: "/api/user/saveOrUpdateUser",
        method: "post",
        data: userInfo
    })
}