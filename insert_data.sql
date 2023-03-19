INSERT INTO user (username, password, email)
VALUES ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com'),
       ('user3', 'password3', 'user3@example.com');

INSERT INTO movie (title, director, cast, duration, genre, release_date, rating, cover_url, full_size_cover_url,
                   country, writer)
VALUES ('The Shawshank Redemption', 'Frank Darabont', 'Tim Robbins, Morgan Freeman, Bob Gunton', 142, 'Drama',
        '1994-09-23', 9.3, 'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.jpg',
        'https://img3.doubanio.com/view/photo/l/public/p480747492.webp', 'USA', 'Stephen King'),
       ('The Godfather', 'Francis Ford Coppola', 'Marlon Brando, Al Pacino, James Caan', 175, 'Crime, Drama',
        '1972-03-24', 9.2, 'https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2190556185.jpg',
        'https://img9.doubanio.com/view/photo/l/public/p2190556185.webp', 'USA', 'Mario Puzo'),
       ('The Dark Knight', 'Christopher Nolan', 'Christian Bale, Heath Ledger, Aaron Eckhart', 152,
        'Action, Crime, Drama', '2008-07-18', 9.0,
        'https://img3.doubanio.com/view/photo/s_ratio_poster/public/p462657443.jpg',
        'https://img3.doubanio.com/view/photo/l/public/p462657443.webp', 'USA, UK',
        'Christopher Nolan, Jonathan Nolan');

INSERT INTO rating (user_id, movie_id, rating)
VALUES (1, 1, 9.0),
       (1, 2, 9.5),
       (2, 2, 9.0),
       (2, 3, 8.5),
       (3, 1, 8.0),
       (3, 3, 9.0);

INSERT INTO comment (user_id, movie_id, content)
VALUES (1, 1, 'One of the best movies I have ever seen.'),
       (1, 2, 'Marlon Brando was amazing in this movie.'),
       (2, 1, 'The story is very touching.'),
       (2, 3, 'Heath Ledger was phenomenal as the Joker.'),
       (3, 2, 'I highly recommend this movie.'),
       (3, 3, 'The plot is very well-written.');
