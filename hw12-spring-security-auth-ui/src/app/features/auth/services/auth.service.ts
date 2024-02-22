import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../types/user';
import { tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private router: Router) {
    }

    login(user: User) {
        const { username, password } = user;
        return this.http.post('api/v1/authenticate', { username, password }, {
            responseType: 'text'
        }).pipe(
            tap(token => {
                sessionStorage.setItem('username', username);
                const fullToken = 'Bearer ' + token;
                sessionStorage.setItem('token', fullToken);
                return token;
            })
        );
    }
}
