import {UpdateBookRequest} from "./UpdateBookRequest";

export type CreateBookRequest = Omit<UpdateBookRequest, 'id'>;
