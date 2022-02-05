export interface CustomPageEvent {
    pageIndex?: number;
    previousPageIndex?: number;
    pageSize?: number;
    length?: number;
    sort?: string;
    search?: string;
    type?: string;
}