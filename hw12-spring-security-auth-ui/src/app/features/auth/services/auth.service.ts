import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { User } from '../types/user';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {

    private currentUser = new BehaviorSubject<User | null>(null);
    currentUser$ = this.currentUser.asObservable();

    constructor(private http: HttpClient, private router: Router) {
    }

    login(user: User): void {
        const { username, password } = user;
        this.http.post('api/v1/login', { username, password})
            .subscribe(() => {
                sessionStorage.setItem('token', btoa(username + ':' + password));
                this.currentUser.next({ username, password });
                this.router.navigateByUrl('/');
            });
    }
}
