CREATE TABLE post
(
    id          serial primary key,
    name        text,
    description text,
    visible     bool,
    city_id     int,
    created     timestamp
);