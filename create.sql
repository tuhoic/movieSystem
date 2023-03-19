CREATE DATABASE IF NOT EXISTS movie;
USE movie;

CREATE TABLE IF NOT EXISTS user (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS movie (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    director VARCHAR(255),
    cast VARCHAR(255),
    duration INT,
    genre VARCHAR(255),
    release_date DATE,
    rating FLOAT,
    poster VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS rating (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    UNIQUE (user_id, movie_id)
);

CREATE TABLE IF NOT EXISTS review (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    content TEXT,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);
