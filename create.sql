CREATE DATABASE IF NOT EXISTS movie;
USE movie;

CREATE TABLE IF NOT EXISTS user
(
    id            INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username      VARCHAR(255) NOT NULL UNIQUE,
    password      VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    head_portrait VARCHAR(255) NOT NULL DEFAULT "default/image.jpg"
);

CREATE TABLE IF NOT EXISTS movie
(
    id                  INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title               VARCHAR(255) NOT NULL UNIQUE,
    director            VARCHAR(255),
    cast                VARCHAR(255),
    duration            INT,
    genre               VARCHAR(255),
    release_date        DATE,
    rating              FLOAT,
    cover_url           VARCHAR(255),
    full_size_cover_url VARCHAR(255),
    country             VARCHAR(255),
    writer              text
);

CREATE TABLE IF NOT EXISTS rating
(
    id       INT   NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id  INT   NOT NULL,
    movie_id INT   NOT NULL,
    rating   FLOAT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    UNIQUE (user_id, movie_id)
);

CREATE TABLE IF NOT EXISTS comment
(
    id       INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id  INT NOT NULL,
    movie_id INT NOT NULL,
    content  TEXT,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
);

CREATE TABLE `likes`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `user_id`  INT NOT NULL,
    `movie_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    KEY `idx_user_comment` (`user_id`, `movie_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `favorites`
(
    `id`       INT NOT NULL AUTO_INCREMENT,
    `user_id`  INT NOT NULL,
    `movie_id` INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id),
    PRIMARY KEY (`id`),
    KEY `idx_user_movie` (`user_id`, `movie_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE `admin`
(
    `id`          int(11)      NOT NULL AUTO_INCREMENT,
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(255) NOT NULL COMMENT '密码',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4 COMMENT ='管理员表';

CREATE TABLE userTokens
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT          NOT NULL,
    token      VARCHAR(255) NOT NULL,
    expiration DATETIME     NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE
);

CREATE TABLE adminTokens
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    admin_id    INT          NOT NULL,
    token      VARCHAR(255) NOT NULL,
    expiration DATETIME     NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (admin_id) REFERENCES admin (id) ON DELETE CASCADE
);