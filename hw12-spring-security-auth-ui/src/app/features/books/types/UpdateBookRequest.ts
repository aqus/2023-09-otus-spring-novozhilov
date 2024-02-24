export interface UpdateBookRequest {
    id: number;
    title: string;
    authorId: number;
    genresIds: number[];
}
