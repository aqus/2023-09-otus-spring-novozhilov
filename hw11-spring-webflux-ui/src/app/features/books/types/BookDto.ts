import {AuthorDto} from "../../authors/types/AuthorDto";
import {GenreDto} from "../../genres/types/GenreDto";

export interface BookDto {
    id: number;
    title: string;
    authorDto: AuthorDto;
    genreDtos: GenreDto[];
}
