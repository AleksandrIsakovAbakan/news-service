CREATE SCHEMA IF NOT EXISTS postgres;

CREATE TABLE IF NOT EXISTS postgres.users
(
    id SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    reg_time TIMESTAMP NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS postgres.news
(
    id SERIAL PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    news_time TIMESTAMP NOT NULL,
    category_id INT NOT NULL,
    user_id INT NOT NULL
);

CREATE TABLE IF NOT EXISTS postgres.category
(
    id SERIAL PRIMARY KEY,
    news_category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS postgres.comments
(
    id SERIAL PRIMARY KEY,
    text VARCHAR(255) NOT NULL,
    comment_time TIMESTAMP NOT NULL,
    news_id INT NOT NULL,
    user_id INT NOT NULL
);