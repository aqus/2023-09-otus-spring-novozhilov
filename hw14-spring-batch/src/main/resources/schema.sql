create table authors (
    id bigserial,
    full_name varchar(255),
    imported bool DEFAULT false,
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    imported bool DEFAULT false,
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    imported bool DEFAULT false,
    primary key (id)
);

create table comments (
    id bigserial,
    text varchar(400),
    book_id bigint references books (id) on delete cascade,
    imported bool DEFAULT false,
    primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table if not exists users (
   id bigserial PRIMARY KEY,
   username VARCHAR(100) UNIQUE NOT NULL,
   password VARCHAR(100) NOT NULL,
   authority VARCHAR(100) NOT NULL,
   last_login  TIMESTAMP NOT NULL,
   active BOOLEAN NOT NULL
);

create table if not exists batches (
    id bigserial PRIMARY KEY,
    class_name varchar (400),
    import_link varchar(400),
    export_link varchar(400),
    imported boolean DEFAULT false
);
