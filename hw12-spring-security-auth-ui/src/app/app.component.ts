import { Component, ErrorHandler } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterLink, RouterLinkActive, RouterOutlet} from '@angular/router';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import { GlobalErrorHandler } from './GlobalErrorHandler';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpLoadingInterceptor } from './HttpLoadingInterceptor';

@Component({
    selector: 'app-root',
    standalone: true,
    imports: [
        CommonModule,
        RouterOutlet,
        MatToolbarModule,
        MatButtonModule,
        RouterLink,
        RouterLinkActive
    ],
    templateUrl: './app.component.html',
    styleUrl: './app.component.scss',
})
export class AppComponent {

    title = 'books-library';
}
