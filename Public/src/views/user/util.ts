export interface QueryUserData {
    limit: number;
    name: string | undefined;
    page: number;
}

export interface QueryUserListResponse {
    code: number;
    data:any;
}
