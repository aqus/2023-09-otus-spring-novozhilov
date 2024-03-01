insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into comments(text, book_id)
values ('BookComment_1', 1), ('BookComment_2', 2), ('BookComment_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into users(username, password, authority, last_login, active)
values ('user', '$2a$12$Q59DuP9OJUFAnYSzfsrVpusLVr/vFxQiA1JyhNfXvM3xaXUmqbT6i', 'ROLE_USER', CURRENT_TIMESTAMP, TRUE),
('admin', '$2a$12$lDVgFDU1jtOxOp./uq.Vkug0NmVHrX5szfbw6Lz4WVbs35npAluZC', 'ROLE_ADMIN', CURRENT_TIMESTAMP, TRUE);
