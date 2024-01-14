import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class CommentsService {

    constructor(private httpClient: HttpClient) {
    }

    fetchComments(bookId: number) {
        return this.httpClient.get(`/api/v1/comments/${bookId}`);
    }

    deleteComment(id: number) {
        return this.httpClient.delete(`/api/v1/comments/${id}`)
    }
}
