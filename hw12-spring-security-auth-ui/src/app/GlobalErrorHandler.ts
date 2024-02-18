import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AuthService } from './features/auth/services/auth.service';
import { Router } from '@angular/router';

@Injectable({
    providedIn: 'root'
})
export class GlobalErrorHandler implements ErrorHandler {

    constructor(private auth: AuthService, private router: Router) {
    }

    handleError(error: unknown) {
        console.log("=== Global error is: ", error);
        if (error instanceof HttpErrorResponse) {
            this.auth.currentUser$.subscribe(user => {
                if (user === null) {
                    this.router.navigate(['login'])
                    // location.href = '/login';
                }
            })
        }
    }
}
