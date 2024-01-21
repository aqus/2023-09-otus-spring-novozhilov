import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class GenresService {

    constructor(private httpClient: HttpClient) {
    }

    fetchGenres() {
        return this.httpClient.get('/api/v1/genres');
    }
}
