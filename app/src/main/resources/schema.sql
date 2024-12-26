DROP TABLE if exists engine cascade;
DROP TABLE if exists car;
DROP TABLE if exists track;
DROP TABLE if exists team;
DROP TABLE if exists format;
DROP TABLE if exists constructors_championship;
DROP TABLE if exists drivers_championships;
DROP TABLE if exists engine;
DROP TABLE if exists engine;
DROP TABLE if exists engine;
DROP TABLE if exists engine;
DROP TABLE if exists engine;

CREATE TABLE engine(
    engine_id SMALLINT PRIMARY KEY,
    name CHAR(100) not null,
    manufacturer CHAR(100) not null
);

CREATE TABLE car(
    car_id SMALLINT PRIMARY KEY,
    name CHAR(100) not null,
    team_id smallint REFERENCES team(team_id),
    engine_id SMALLINT REFERENCES engine(engine_id)
);

CREATE TABLE track(
    track_id SMALLINT PRIMARY KEY,
    country CHAR(20) not null,
    length FLOAT not null check (length > 0),
    since DATE not null,
    till DATE not null,
    record TIME not null
);

CREATE TABLE team(
    team_id SMALLINT PRIMARY KEY,
    name CHAR(100) unique not null,
    location CHAR(100) not null,
    since DATE not null,
    till DATE not null
);

CREATE TABLE format(
    format_id SMALLINT PRIMARY KEY,
    practices_quantity SMALLINT not null,
    sprint BOOLEAN not null
);

CREATE TABLE constructors_championship(
    team_id SMALLINT REFERENCES team(team_id),
    points SMALLINT not null
);

CREATE TABLE drivers_championships(
    driver_id SMALLINT REFERENCES driver(driver_id),
    points SMALLINT not null
);

CREATE TABLE driver(
    driver_id SMALLINT PRIMARY KEY,
    name CHAR(100) not null,
    birthday DATE not null,
    country CHAR(20) not null,
    team_id SMALLINT REFERENCES team(team_id),
    wins SMALLINT not null,
    poles SMALLINT not null,
    championships SMALLINT not null,
    since DATE not null,
    till DATE
);

CREATE TABLE tyre(
    tyre_id SMALLINT PRIMARY KEY,
    tyre_type CHAR(15) unique not null
);

CREATE TABLE grand_prix(
    gp_id SMALLINT PRIMARY KEY,
    name CHAR(100) unique not null,
    track_id SMALLINT REFERENCES track(track_id),
    date DATE not null,
    format_id SMALLINT REFERENCES format(format_id),
    since DATE not null,
    till DATE not null
);

CREATE TABLE practice(
    gp_id SMALLINT REFERENCES grand_prix(gp_id),
    driver_id SMALLINT REFERENCES driver(driver_id),
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre(tyre_id),
    laps_completed SMALLINT not null,
    status_id SMALLINT REFERENCES status(status_id)
);

CREATE TABLE qualification(
    gp_id SMALLINT REFERENCES grand_prix(gp_id),
    driver_id SMALLINT REFERENCES driver(driver_id),
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre(tyre_id),
    status_id SMALLINT REFERENCES status(status_id)
);

CREATE TABLE sprint(
    gp_id SMALLINT REFERENCES grand_prix(gp_id),
    driver_id SMALLINT REFERENCES driver(driver_id),
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre(tyre_id),
    status_id SMALLINT REFERENCES status(status_id),
    points SMALLINT not null
);

CREATE TABLE race(
    gp_id SMALLINT REFERENCES grand_prix(gp_id),
    driver_id SMALLINT REFERENCES driver(driver_id),
    place SMALLINT not null,
    best_time TIME not null,
    tyre_id SMALLINT REFERENCES tyre(tyre_id),
    status_id SMALLINT REFERENCES status(status_id),
    points SMALLINT not null
);

CREATE TABLE scoring_system(
    sc_system_id SMALLINT PRIMARY KEY,
    info CHAR(100) not null,
    fastest_lap SMALLINT not null
);

CREATE TABLE sprint_points(
    sc_system_id SMALLINT REFERENCES scoring_system(sc_system_id),
    place SMALLINT not null,
    points SMALLINT not null
);

CREATE TABLE race_points(
    sc_system_id SMALLINT REFERENCES scoring_system(sc_system_id),
    place SMALLINT not null,
    points SMALLINT not null
);

CREATE TABLE status(
    status_id SMALLINT PRIMARY KEY,
    status_text CHAR(4) not null,
    status_text_full CHAR(20) not null
);

