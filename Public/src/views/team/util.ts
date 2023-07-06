export interface QueryTeamListData {
    limit: number;
    page: number;
    teamId?: number;
    teamName?: string;
}

export interface QueryTeamListResponse {
    code: number;
    data: any;
    msg: string;
}

export interface Team {
    creationTime: number;
    isDelete: number;
    leaderId: number;
    teamId: number;
    teamName: string;
    updateTime: number;
}