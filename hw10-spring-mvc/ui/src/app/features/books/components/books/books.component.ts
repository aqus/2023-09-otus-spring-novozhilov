import {ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {BooksService} from "../../services/books.service";
import {HttpClientModule} from "@angular/common/http";
import {BookDto} from "../../types/BookDto";
import {MatTableModule} from "@angular/material/table";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {MatDialog} from "@angular/material/dialog";
import {UpdateBookDialogComponent} from "../update-book-dialog/update-book-dialog.component";
import {RouterLink} from "@angular/router";
import {AddBookDialogComponent} from "../add-book-dialog/add-book-dialog.component";
import {CreateBookRequest} from "../../types/CreateBookRequest";

@Component({
    selector: 'app-books',
    standalone: true,
    imports: [
        HttpClientModule,
        MatTableModule,
        MatIconModule,
        MatButtonModule,
        RouterLink
    ],
    templateUrl: './books.component.html',
    styleUrls: ['./books.component.scss'],
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class BooksComponent implements OnInit {

    displayedColumns: string[] = ['id', 'title', 'fullName', 'genres', 'actions'];
    books: BookDto[] = [];

    constructor(private booksService: BooksService,
                private ref: ChangeDetectorRef,
                private dialog: MatDialog) {
    }

    ngOnInit(): void {
        this.fetchBooks();
    }

    fetchBooks() {
        this.booksService.fetchBooks()
            .subscribe((result) => {
                this.books = result as BookDto[];
                this.ref.markForCheck();
            });
    }

    openUpdateDialog(book: BookDto) {
        const dialogRef = this.dialog.open(UpdateBookDialogComponent, {
            data: {book},
        });

        dialogRef.afterClosed()
            .subscribe(result => {
                if (!result) {
                    return;
                }

                const updatedBook = result as BookDto;
                this.booksService.updateBook(updatedBook)
                    .subscribe(result => {
                        this.fetchBooks();
                    });
            });
    }

    openAddDialog() {
        const dialogRef = this.dialog.open(AddBookDialogComponent, {
            data: null,
        });

        dialogRef.afterClosed()
            .subscribe(result => {
                if (!result) {
                    return;
                }
                this.booksService.addBook(result as CreateBookRequest)
                    .subscribe(result => {
                        this.fetchBooks();
                    });
            });
    }

    deleteBook(bookId: number) {
        this.booksService.deleteBook(bookId)
            .subscribe(() => {
                this.fetchBooks();
            });
    }
}
