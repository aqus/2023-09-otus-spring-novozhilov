import { Routes } from '@angular/router';
import {BooksComponent} from "./features/books/components/books/books.component";
import {AuthorsComponent} from "./features/authors/components/authors/authors.component";
import {GenresComponent} from "./features/genres/components/genres/genres.component";
import {BookComponent} from "./features/books/components/book/book.component";

export const routes: Routes = [
    {
        path: '',
        component: BooksComponent
    },
    {
        path: '',
        component: BooksComponent
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
