import {TestBed} from '@angular/core/testing';

import {BooksService} from './books.service';

describe('BooksServiceService', () => {
    let service: BooksService;

    beforeEach(() => {
        TestBed.configureTestingModule({});
        service = TestBed.inject(BooksService);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
