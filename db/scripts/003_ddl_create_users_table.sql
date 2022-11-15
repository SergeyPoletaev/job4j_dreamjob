CREATE TABLE users
(
    id    serial primary key,
    name  text,
    email text,
    CONSTRAINT email_unique UNIQUE (email)
);