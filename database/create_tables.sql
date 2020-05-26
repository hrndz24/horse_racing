CREATE DATABASE horse_racing;
USE horse_racing;

CREATE TABLE user_roles
(
    role_id   INT PRIMARY KEY AUTO_INCREMENT,
    role_name VARCHAR(20)
);

CREATE TABLE users
(
    user_id        INT PRIMARY KEY AUTO_INCREMENT,
    user_name      VARCHAR(50),
    user_log_in    VARCHAR(30) UNIQUE,
    user_password  VARCHAR(128),
    user_email     VARCHAR(30),
    user_role_id   INT,
    user_is_active BOOL,
    user_balance   DECIMAL(11, 2),
    FOREIGN KEY (user_role_id) REFERENCES user_roles (role_id)
);

CREATE TABLE horses
(
    horse_id         INT PRIMARY KEY AUTO_INCREMENT,
    horse_name       VARCHAR(50),
    horse_breed      VARCHAR(50),
    horse_age        INT,
    is_performing    BOOL,
    races_won_number INT,
    races_lost       INT
);

CREATE TABLE race_horses
(
    race_id  INT,
    horse_id INT,
    PRIMARY KEY (race_id, horse_id),
    FOREIGN KEY (race_id) REFERENCES races (race_id),
    FOREIGN KEY (horse_id) REFERENCES horses (horse_id)
);

CREATE TABLE races
(
    race_id          INT PRIMARY KEY AUTO_INCREMENT,
    race_prize_money DECIMAL(11, 2),
    horse_winner_id  INT,
    race_date        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    race_distance    INT,
    race_location    VARCHAR(100),
    FOREIGN KEY (horse_winner_id) REFERENCES horses (horse_id)
);

CREATE TABLE odds
(
    odds_id        INT PRIMARY KEY AUTO_INCREMENT,
    race_id        INT,
    bookmaker_id   INT,
    horse_id       INT,
    odds_in_favour INT,
    odds_against   INT,
    FOREIGN KEY (race_id) REFERENCES races (race_id) ON DELETE CASCADE,
    FOREIGN KEY (bookmaker_id) REFERENCES users (user_id),
    FOREIGN KEY (horse_id) REFERENCES horses (horse_id)
);

create table bets
(
    bet_id  INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    bet_sum DECIMAL(11, 2),
    odds_id INT,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE CASCADE,
    FOREIGN KEY (odds_id) REFERENCES odds (odds_id) ON DELETE CASCADE
);