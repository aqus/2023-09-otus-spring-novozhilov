import { ApplicationConfig, ErrorHandler } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideAnimations } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch } from "@angular/common/http";
import { GlobalErrorHandler } from './GlobalErrorHandler';
import { HttpLoadingInterceptor } from './HttpLoadingInterceptor';

export const appConfig: ApplicationConfig = {
    providers: [
        provideRouter(routes),
        provideClientHydration(),
        provideAnimations(),
        provideHttpClient(withFetch()),
        {
            provide: ErrorHandler,
            useClass: GlobalErrorHandler
        }, {
            provide: HTTP_INTERCEPTORS,
            useClass: HttpLoadingInterceptor,
            multi: true,
        }
    ]
};
