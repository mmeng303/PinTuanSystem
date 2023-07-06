export interface PayData {
    memberId: number;
    page: number;
    limit: number;
}

export interface tourFareData {
    aaFare: number;
    activityFee: number;
    activityId: number;
    activityName: string;
    additionalFee: any;
    id: number;
    memberId: number;
    realTimeAaFee: number;
    realTimeCostFee: number;
}