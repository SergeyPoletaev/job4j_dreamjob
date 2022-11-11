CREATE TABLE candidate
(
    id          serial primary key,
    name        text,
    description text,
    visible     bool,
    city_id     int,
    photo       bytea,
    created     timestamp
);