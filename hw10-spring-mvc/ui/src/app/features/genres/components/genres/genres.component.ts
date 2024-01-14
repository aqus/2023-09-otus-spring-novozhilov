import {Component, OnInit} from '@angular/core';
import {GenresService} from "../../services/genres.service";
import {GenreDto} from "../../types/GenreDto";

@Component({
    standalone: true,
    selector: 'app-genres',
    templateUrl: './genres.component.html',
    styleUrls: ['./genres.component.scss']
})
export class GenresComponent implements OnInit {

    genres: GenreDto[] = [];

    constructor(private genresService: GenresService) {
    }

    ngOnInit(): void {
        this.genresService.fetchGenres()
            .subscribe(result => this.genres = result as GenreDto[]);
    }

}
