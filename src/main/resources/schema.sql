CREATE TABLE IF NOT EXISTS users (
       id INTEGER AUTO_INCREMENT PRIMARY KEY,
       email VARCHAR(255),
       login VARCHAR(255),
       name VARCHAR(255),
       birthday DATE,
       friend_status_id INTEGER
);

CREATE TABLE IF NOT EXISTS genre (
       id INTEGER AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS mpa (
       id INTEGER AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS film (
       id INTEGER AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255),
       description TEXT,
       mpa_id INTEGER,
       release_date DATE,
       duration VARCHAR(50),
       FOREIGN KEY (mpa_id) REFERENCES mpa(id)
);

create table IF NOT EXISTS GENRE_FILM (
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "GENRE_FILM_pk"
        primary key (FILM_ID, GENRE_ID),
    constraint "GENRE_FILM_FILM_FILM_ID_fk"
        foreign key (FILM_ID) references FILM (ID),
    constraint "GENRE_FILM_GENRE_ID_fk"
        foreign key (GENRE_ID) references GENRE (ID)
);

create table IF NOT EXISTS GENRE_FILM
(
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint "GENRE_FILM_pk"
        primary key (FILM_ID, GENRE_ID),
    constraint "GENRE_FILM_FILM_FILM_ID_fk"
        foreign key (FILM_ID) references FILM (ID),
    constraint "GENRE_FILM_GENRE_ID_fk"
        foreign key (GENRE_ID) references GENRE (ID)
);

CREATE TABLE IF NOT EXISTS friend (
       id INTEGER PRIMARY KEY,
       user_id_1 INTEGER,
       user_id_2 INTEGER,
       status_id INTEGER,
       FOREIGN KEY (user_id_1) REFERENCES users(id),
       FOREIGN KEY (user_id_2) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS "like" (
       user_id INTEGER,
       film_id INTEGER,
       PRIMARY KEY (user_id, film_id),
       FOREIGN KEY (user_id) REFERENCES users(id),
       FOREIGN KEY (film_id) REFERENCES film(id)
);
