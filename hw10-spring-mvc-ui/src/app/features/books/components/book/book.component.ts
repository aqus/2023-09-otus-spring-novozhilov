import {Component, OnDestroy, OnInit} from '@angular/core';
import {BooksService} from "../../services/books.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {BookDto} from "../../types/BookDto";
import {CommentDto} from "../../../comments/types/CommentDto";
import {CommentsService} from "../../../comments/services/comments.service";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";
import {UpdateBookDialogComponent} from "../update-book-dialog/update-book-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";

@Component({
    selector: 'app-book',
    standalone: true,
    imports: [
        MatButtonModule,
        MatIconModule,
        ReactiveFormsModule,
        MatInputModule
    ],
    templateUrl: './book.component.html',
    styleUrl: './book.component.scss'
})
export class BookComponent implements OnInit, OnDestroy {

    private routeSub: Subscription | undefined;

    private bookId: number | undefined;

    book: BookDto | undefined;

    comments: CommentDto[] = [];

    addCommentForm = this.fb.group({
        text: ['', Validators.minLength(4)]
    });

    constructor(private booksService: BooksService,
                private commentsService: CommentsService,
                private dialog: MatDialog,
                private route: ActivatedRoute,
                private fb: FormBuilder) {
    }

    ngOnInit(): void {
        this.routeSub = this.route.params.subscribe(params => {
            this.bookId = params['id'];
            this.fetchBook(params['id']);
        });
    }

    fetchBook(bookId: number) {
        this.booksService.findById(bookId)
            .subscribe(result => {
                this.book = result as BookDto;
                this.fetchComments(bookId);
            });
    }

    fetchComments(bookId: number) {
        this.commentsService.fetchComments(bookId)
            .subscribe(result => {
                this.comments = result as CommentDto[];
            })
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
                        this.book = result as BookDto;
                    });
            });
    }

    addComment() {
        // implement it
        // add new modal
    }

    deleteComment(comment: CommentDto) {
        this.commentsService.deleteComment(comment.id).subscribe();
        this.fetchComments(this.book!.id);
    }

    commentSubmit() {
        if (this.addCommentForm.invalid) {
            return;
        }

        const text = this.addCommentForm.get('text')!.value!;
        this.commentsService.addComment({
            bookId: this.book!.id,
            text
        })
            .subscribe(() => {
                this.fetchComments(this.book!.id);
            });
    }

    ngOnDestroy(): void {
        this.routeSub?.unsubscribe();
    }
}
