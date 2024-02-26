import {Component, Inject} from "@angular/core";
import {
    MAT_DIALOG_DATA,
    MatDialogActions,
    MatDialogClose,
    MatDialogContent, MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import {BookDto} from "../../types/BookDto";
import {MatButtonModule} from "@angular/material/button";
import {UpdateBookRequest} from "../../types/UpdateBookRequest";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";

export interface DialogData {
    book: BookDto
}

@Component({
    selector: 'update-book-dialog',
    templateUrl: './update-book-dialog.component.html',
    styleUrls: ['./update-book-dialog.component.scss'],
    standalone: true,
    imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule],
})
export class UpdateBookDialogComponent {

    updatedBook: UpdateBookRequest;

    updateForm: FormGroup;

    constructor(
        private dialogRef: MatDialogRef<UpdateBookDialogComponent>,
        private fb: FormBuilder,
        @Inject(MAT_DIALOG_DATA) public data: DialogData) {

        this.updatedBook = {
            id: data.book.id,
            title: data.book.title,
            authorId: data.book.authorDto.id,
            genresIds: data.book.genreDtos.map(g => g.id)
        };

        this.updateForm = this.fb.group({
            title: [this.updatedBook.title, Validators.required],
            authorId: [this.updatedBook.authorId, Validators.required],
            genresIds: [this.updatedBook.genresIds, Validators.required]
        });
    }

    onCancel() {
        this.dialogRef.close();
    }

    submit() {
        if (this.updateForm.invalid) {
            return;
        }

        this.updatedBook = this.getUpdatedBookFromFormValue()
        this.dialogRef.close(this.updatedBook);
    }

    private getUpdatedBookFromFormValue() {
        const genresIds = () => {
            const value = this.updateForm.get('genresIds')?.value;

            if (!value) {
                return [];
            }

            if (Array.isArray(value)) {
                return value
            }

            if (typeof value == 'string') {
                return (value as string).split(",").map(String);
            }

            return value;
        }

        return Object.assign(this.updatedBook, this.updateForm.getRawValue(), {
            genresIds: genresIds()
        });
    }
}
