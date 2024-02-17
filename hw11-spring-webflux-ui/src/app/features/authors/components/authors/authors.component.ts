import {Component, OnInit} from '@angular/core';
import {AuthorDto} from "../../types/AuthorDto";
import {AuthorsService} from "../../services/authors.service";

@Component({
    standalone: true,
    selector: 'app-authors',
    templateUrl: './authors.component.html',
    styleUrls: ['./authors.component.scss']
})
export class AuthorsComponent implements OnInit {

    authors: AuthorDto[] = [];

    constructor(private authorsService: AuthorsService) {
    }

    ngOnInit(): void {
        this.authorsService.fetchAuthors()
            .subscribe(result=> this.authors = result as AuthorDto[])
    }

}
