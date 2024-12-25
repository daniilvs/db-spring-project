CREATE TABLE engine(
    engine_id SMALLINT PRIMARY KEY,
    name CHAR(100),
    manufacturer CHAR(100)
);

CREATE TABLE car(
    car_id SMALLINT PRIMARY KEY,
    name CHAR(100),
    FOREIGN KEY (team_id) SMALLINT
    REFERENCES team(team_id),
    FOREIGN KEY (engine_id) SMALLINT
    REFERENCES engine(engine_id)
);

CREATE TABLE track(
    track_id SMALLINT PRIMARY KEY,
    country CHAR(20),
    length FLOAT,
    since DATE,
    till DATE,
    record TIME
);

CREATE TABLE team(
    team_id SMALLINT PRIMARY KEY,
    name CHAR(100),
    location CHAR(100),
    since DATE,
    till DATE
);

CREATE TABLE format(
    format_id SMALLINT PRIMARY KEY,
    practices_quantity SMALLINT,
    sprint BOOLEAN
);

CREATE TABLE constructors_championship(
    FOREIGN KEY (team_id) SMALLINT
    REFERENCES team(team_id),
    points SMALLINT
);

CREATE TABLE drivers_championships(
    FOREIGN KEY (driver_id) SMALLINT
        REFERENCES driver(driver_id),
    points SMALLINT
);

CREATE TABLE driver(
    driver_id SMALLINT PRIMARY KEY,
    name CHAR(100),
    birthday DATE,
    country CHAR(20),
    FOREIGN KEY (team_id) SMALLINT
        REFERENCES team(team_id),
    wins SMALLINT,
    poles SMALLINT,
    championships SMALLINT,
    since DATE,
    till DATE
);

CREATE TABLE tyre(
    tyre_id SMALLINT PRIMARY KEY,
    tyre_type CHAR(15)
);

CREATE TABLE grand_prix(
    gp_id SMALLINT PRIMARY KEY,
    name CHAR(100),
    FOREIGN KEY (track_id) SMALLINT
        REFERENCES track(track_id),
    date DATE,
    FOREIGN KEY (format_id) SMALLINT
        REFERENCES format(format_id),
    since DATE,
    till DATE
);

CREATE TABLE practice(
    FOREIGN KEY (gp_id) SMALLINT
        REFERENCES grand_prix(gp_id),
    FOREIGN KEY (driver_id) SMALLINT
        REFERENCES driver(driver_id),
    place SMALLINT,
    best_time TIME,
    FOREIGN KEY (tyre_id) SMALLINT
        REFERENCES tyre(tyre_id),
    laps_completed SMALLINT,
    FOREIGN KEY (status_id) SMALLINT
        REFERENCES status(status_id)
);

CREATE TABLE qualification(
    FOREIGN KEY (gp_id) SMALLINT
        REFERENCES grand_prix(gp_id),
    FOREIGN KEY (driver_id) SMALLINT
        REFERENCES driver(driver_id),
    place SMALLINT,
    best_time TIME,
    FOREIGN KEY (tyre_id) SMALLINT
        REFERENCES tyre(tyre_id),
    FOREIGN KEY (status_id) SMALLINT
        REFERENCES status(status_id)
);

CREATE TABLE sprint(
    FOREIGN KEY (gp_id) SMALLINT
        REFERENCES grand_prix(gp_id),
    FOREIGN KEY (driver_id) SMALLINT
     REFERENCES driver(driver_id),
    place SMALLINT,
    best_time TIME,
    FOREIGN KEY (tyre_id) SMALLINT
        REFERENCES tyre(tyre_id),
    FOREIGN KEY (status_id) SMALLINT
        REFERENCES status(status_id),
    points SMALLINT
);

CREATE TABLE race(
    FOREIGN KEY (gp_id) SMALLINT
        REFERENCES grand_prix(gp_id),
    FOREIGN KEY (driver_id) SMALLINT
        REFERENCES driver(driver_id),
    place SMALLINT,
    best_time TIME,
    FOREIGN KEY (tyre_id) SMALLINT
        REFERENCES tyre(tyre_id),
    FOREIGN KEY (status_id) SMALLINT
        REFERENCES status(status_id),
    points SMALLINT
);

CREATE TABLE scoring_system(
    sc_system_id SMALLINT PRIMARY KEY,
    info CHAR(100),
    fastest_lap SMALLINT
);

CREATE TABLE sprint_points(
    FOREIGN KEY (sc_system_id) SMALLINT
        REFERENCES scoring_system(sc_system_id),
    place SMALLINT,
    points SMALLINT
);

CREATE TABLE race_points(
    FOREIGN KEY (sc_system_id) SMALLINT
        REFERENCES scoring_system(sc_system_id),
    place SMALLINT,
    points SMALLINT
);

CREATE TABLE status(
    status_id SMALLINT PRIMARY KEY,
    status_text CHAR(4),
    status_text_full CHAR(20)
);

