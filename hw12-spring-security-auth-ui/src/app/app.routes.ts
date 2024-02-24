import { Routes } from '@angular/router';
import {BooksComponent} from "./features/books/components/books/books.component";
import {AuthorsComponent} from "./features/authors/components/authors/authors.component";
import {GenresComponent} from "./features/genres/components/genres/genres.component";
import {BookComponent} from "./features/books/components/book/book.component";
import { LoginPageComponent } from './features/auth/components/login-page/login-page.component';

export const routes: Routes = [
    {
        path: '',
        component: BooksComponent
    },
    {
        path: 'login',
        component: LoginPageComponent
    },
    {
        path: 'authors',
        component: AuthorsComponent
    },
    {
        path: 'genres',
        component: GenresComponent
    },
    {
        path: 'books/:id',
        component: BookComponent
    }
];
