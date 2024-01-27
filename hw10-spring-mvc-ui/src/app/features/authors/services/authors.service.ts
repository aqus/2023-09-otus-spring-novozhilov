import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class AuthorsService {

    constructor(private httpClient: HttpClient) {
    }

    fetchAuthors() {
        return this.httpClient.get('api/v1/authors');
    }
}
