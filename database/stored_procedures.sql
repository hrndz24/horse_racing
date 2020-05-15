USE horse_racing;

delimiter //

CREATE PROCEDURE usp_SetRaceResults(in race_id int, in horse_winner_id int)
BEGIN
    DECLARE done INT;
    DECLARE wonSum int;
    DECLARE lostSum int;
    DECLARE newBalance int;
    DECLARE oldBalance int;
    DECLARE betSum decimal(11, 2);
    DECLARE userId int;
    DECLARE userBalance decimal(11, 2);
    DECLARE bookmakerId int;
    DECLARE oddsInFavour int;
    DECLARE oddsAgainst int;
    DECLARE won_bets_cursor CURSOR FOR SELECT bet_sum, bets.user_id, user_balance, odds.bookmaker_id,
                                odds_in_favour FROM users JOIN bets ON users.user_id = bets.user_id
                                JOIN odds ON bets.odds_id = odds.odds_id JOIN races ON odds.race_id = races.race_id
                                WHERE odds.horse_id = horse_winner_id AND races.race_id = race_id;
    DECLARE lost_bets_cursor CURSOR FOR SELECT bet_sum, bets.user_id, user_balance, odds.bookmaker_id,
                                odds_against FROM users JOIN bets ON users.user_id = bets.user_id
                                JOIN odds ON bets.odds_id = odds.odds_id JOIN races ON odds.race_id = races.race_id
                                WHERE odds.horse_id != horse_winner_id AND races.race_id = race_id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    START TRANSACTION;
    UPDATE races SET races.horse_winner_id = horse_winner_id WHERE races.race_id = race_id;

    OPEN won_bets_cursor;
    SET done = 0;
    REPEAT
        FETCH won_bets_cursor INTO betSum, userId, userBalance, bookmakerId, oddsInFavour;
        IF NOT done THEN
            SET wonSum = betSum * oddsInFavour;
            SET newBalance = userBalance + wonSum;
            UPDATE users SET user_balance = newBalance WHERE user_id = userId;
            SET oldBalance = (SELECT user_balance FROM users WHERE user_id = bookmakerId);
            SET newBalance = oldBalance - wonSum;
            UPDATE users SET user_balance = newBalance WHERE user_id = bookmakerId;
        END IF;
    UNTIL done END REPEAT;
    CLOSE won_bets_cursor;

    OPEN lost_bets_cursor;
    SET done = 0;
    REPEAT
        FETCH lost_bets_cursor INTO betSum, userId, userBalance, bookmakerId, oddsAgainst;
        IF NOT done THEN
            SET lostSum = betSum * (oddsAgainst-1);
            SET newBalance = userBalance - lostSum;
            UPDATE users SET user_balance = newBalance WHERE user_id = userId;
            SET oldBalance = (SELECT user_balance FROM users WHERE user_id = bookmakerId);
            SET newBalance = oldBalance + lostSum;
            UPDATE users SET user_balance = newBalance WHERE user_id = bookmakerId;
        END IF;
    UNTIL done END REPEAT;
    CLOSE lost_bets_cursor;
    COMMIT;
END//

CREATE PROCEDURE usp_MakeBet(IN user_id INT, IN bet_sum DECIMAL(11,2), IN odds_id INT, OUT bet_id INT)
BEGIN
    DECLARE oldBalance DECIMAL(11, 2);
    START TRANSACTION;
    INSERT INTO bets(user_id, bet_sum, odds_id) VALUES(user_id, bet_sum, odds_id);
    SET bet_id = LAST_INSERT_ID();
    SET oldBalance = (SELECT user_balance FROM users WHERE users.user_id = user_id);
    UPDATE users SET user_balance = (oldBalance-bet_sum) WHERE users.user_id = user_id;
    SELECT bet_id;
    COMMIT;
END//

CREATE PROCEDURE usp_DeleteBet(IN bet_id INT, IN user_id INT)
BEGIN
    DECLARE oldBalance DECIMAL(11, 2);
    DECLARE betSum DECIMAL(11, 2);
    START TRANSACTION;
    SET betSum = (SELECT bet_sum FROM bets WHERE bets.bet_id = bet_id);
    DELETE FROM bets WHERE bets.bet_id = bet_id;
    SET oldBalance = (SELECT user_balance FROM users WHERE users.user_id = user_id);
    UPDATE users SET user_balance = (oldBalance+betSum) WHERE users.user_id = user_id;
    COMMIT;
END//

CREATE PROCEDURE usp_UpdateBetSum(IN bet_id INT, IN new_bet_sum DECIMAL(11, 2), IN user_id INT)
BEGIN
    DECLARE oldBalance DECIMAL(11, 2);
    DECLARE oldBetSum DECIMAL(11, 2);
    START TRANSACTION;
    SET oldBetSum = (SELECT bet_sum FROM bets WHERE bets.bet_id = bet_id);
    UPDATE bets SET bets.bet_sum = new_bet_sum WHERE bets.bet_id = bet_id;
    SET oldBalance = (SELECT user_balance FROM users WHERE users.user_id = user_id);
    UPDATE users SET user_balance = (oldBalance-(new_bet_sum - oldBetSum)) WHERE users.user_id = user_id;
    COMMIT;
END//