import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {BookDto} from "../types/BookDto";

@Injectable({
    providedIn: 'root'
})
export class BooksService {

    constructor(private httpClient: HttpClient) {
    }

    fetchBooks() {
        return this.httpClient.get("/api/v1/books");
    }

    findById(id: number) {
        return this.httpClient.get(`/api/v1/books/${id}`)
    }

    addBook(book: BookDto) {
        return this.httpClient.post("/api/v1/books", book);
    }

    updateBook(book: BookDto) {
        return this.httpClient.put("/api/v1/books", book);
    }

    deleteBook(id: number) {
        return this.httpClient.delete(`/api/v1/books/${id}`)
    }
}
