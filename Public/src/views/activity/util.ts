export interface QueryActivityListParams {
    page: number;
    limit: number;
    activityName?: string|undefined;
    userId?: number;
}

export interface Activity {
    id: number;
    teamId: number;
    activityName: string;
    startingPoint: string;
    mainContent: string;
    startTime: string;
    isCompleted: number
}

export interface Payment {
    activityId: number;
    memberId: number;
    fee: number;
    isFee: number;
}