DROP TABLE if exists engine cascade;
DROP TABLE if exists car;
DROP TABLE if exists track cascade;
DROP TABLE if exists team cascade;
DROP TABLE if exists format cascade;
DROP TABLE if exists constructors_championship;
DROP TABLE if exists drivers_championships;
DROP TABLE if exists driver cascade;
DROP TABLE if exists tyre cascade;
DROP TABLE if exists grand_prix cascade;
DROP TABLE if exists practice;
DROP TABLE if exists qualification;
DROP TABLE if exists sprint;
DROP TABLE if exists race;
DROP TABLE if exists scoring_system cascade;
DROP TABLE if exists sprint_points cascade;
DROP TABLE if exists race_points cascade;
DROP TABLE if exists status cascade;


CREATE TABLE status(
    id bigserial primary key,
    status_text CHAR(4) not null,
    status_text_full CHAR(20) not null
);

CREATE TABLE engine(
    id bigserial primary key,
    manufacturer CHAR(100) not null
);

CREATE TABLE team(
    id bigserial primary key,
    name CHAR(100) unique not null,
    location CHAR(100) not null,
    since int check (since > 0),
    till int check (till > 0)
);

CREATE TABLE car(
    id bigserial primary key,
    name CHAR(100) not null,
    team_id smallint REFERENCES team,
    engine_id SMALLINT REFERENCES engine
);

CREATE TABLE track(
    id bigserial primary key,
    country CHAR(20) not null,
    length FLOAT not null check (length > 0),
    since DATE not null,
    till DATE not null,
    record TIME not null
);

CREATE TABLE format(
    id bigserial primary key,
    practices_quantity SMALLINT not null,
    sprint BOOLEAN not null
);

CREATE TABLE constructors_championship(
    id SMALLINT REFERENCES team,
    points SMALLINT not null
);

CREATE TABLE driver(
    id bigserial primary key,
    name CHAR(100) not null,
    birthday DATE not null,
    country CHAR(20) not null,
    team_id SMALLINT REFERENCES team,
    wins SMALLINT not null,
    poles SMALLINT not null,
    championships SMALLINT not null,
    since DATE not null,
    till DATE
);

CREATE TABLE drivers_championships(
    id SMALLINT REFERENCES driver,
    points SMALLINT not null
);

CREATE TABLE tyre(
    id bigserial primary key,
    tyre_type CHAR(15) unique not null
);

CREATE TABLE grand_prix(
    id bigserial primary key,
    name CHAR(100) unique not null,
    track_id SMALLINT REFERENCES track,
    date DATE not null,
    format_id SMALLINT REFERENCES format,
    since DATE not null,
    till DATE not null
);

CREATE TABLE practice(
    gp_id SMALLINT REFERENCES grand_prix,
    driver_id SMALLINT REFERENCES driver,
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre,
    laps_completed SMALLINT not null,
    status_id SMALLINT REFERENCES status
);

CREATE TABLE qualification(
    gp_id SMALLINT REFERENCES grand_prix,
    driver_id SMALLINT REFERENCES driver,
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre,
    status_id SMALLINT REFERENCES status
);

CREATE TABLE sprint(
    gp_id SMALLINT REFERENCES grand_prix,
    driver_id SMALLINT REFERENCES driver,
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre,
    status_id SMALLINT REFERENCES status,
    points SMALLINT not null
);

CREATE TABLE race(
    gp_id SMALLINT REFERENCES grand_prix,
    driver_id SMALLINT REFERENCES driver,
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre,
    status_id SMALLINT REFERENCES status,
    points SMALLINT not null
);

CREATE TABLE scoring_system(
    id bigserial primary key,
    info CHAR(100) not null,
    fastest_lap SMALLINT not null
);

CREATE TABLE sprint_points(
    sc_system_id SMALLINT REFERENCES scoring_system,
    place SMALLINT not null,
    points SMALLINT not null
);

CREATE TABLE race_points(
    sc_system_id SMALLINT REFERENCES scoring_system,
    place SMALLINT not null,
    points SMALLINT not null
);

