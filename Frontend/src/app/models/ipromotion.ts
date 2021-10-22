export interface IPromotion {
    id: number;
    name: string;
    gender: string;
    status: string;
    minAge: number;
    maxAge: number;
    endTimeEpoch: Date;
    startTimeEpoch: Date;
}
