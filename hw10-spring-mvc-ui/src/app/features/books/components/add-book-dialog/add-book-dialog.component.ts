import {Component} from "@angular/core";
import {
    MatDialogActions,
    MatDialogClose,
    MatDialogContent, MatDialogRef,
    MatDialogTitle
} from "@angular/material/dialog";
import {MatButtonModule} from "@angular/material/button";
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {CreateBookRequest} from "../../types/CreateBookRequest";

@Component({
    selector: 'add-book-dialog',
    templateUrl: './add-book-dialog.component.html',
    styleUrls: ['./add-book-dialog.component.scss'],
    standalone: true,
    imports: [MatDialogTitle, MatDialogContent, MatDialogActions, MatDialogClose, MatButtonModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule],
})
export class AddBookDialogComponent {

    addForm = this.fb.group({
        title: [undefined, Validators.required],
        authorId: [undefined, Validators.required],
        genresIds: [undefined, Validators.required]
    });

    constructor(
        private dialogRef: MatDialogRef<AddBookDialogComponent>,
        private fb: FormBuilder) {
    }

    onCancel() {
        this.dialogRef.close();
    }

    submit() {
        if (this.addForm.invalid) {
            return;
        }

        this.dialogRef.close(this.getBookFromFormValue());
    }

    private getBookFromFormValue() {
        const genresIds = () => {
            const value = this.addForm.get('genresIds')?.value;

            if (!value) {
                return [];
            }

            if (Array.isArray(value)) {
                return value;
            }

            if (typeof value == 'string') {
                return (value as string).split(",").map(Number);
            }

            return value;
        }

        return Object.assign(this.addForm.getRawValue(), {
            genresIds: genresIds()
        });
    }
}
