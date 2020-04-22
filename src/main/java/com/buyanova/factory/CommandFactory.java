package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.impl.admin.*;
import com.buyanova.command.impl.bookmaker.PlaceOdds;
import com.buyanova.command.impl.bookmaker.SubmitOdds;
import com.buyanova.command.impl.language.ChangeLanguage;
import com.buyanova.command.impl.redirect.*;
import com.buyanova.command.impl.user.*;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn()), LOG_OUT(new LogOut()),
    VIEW_BETS(new ViewBets()),
    REPLENISH_ACCOUNT(new ReplenishAccount()), CHANGE_LOGIN(new ChangeLogin()),
    CHANGE_PASSWORD(new ChangePassword()), CHANGE_NAME(new ChangeName()), CHANGE_EMAIL(new ChangeEmail()),

    LANGUAGE(new ChangeLanguage()),

    SHOW_RACES(new ShowRaces()), SHOW_RACE(new ShowRace()),
    REDIRECT_ADD_RACE(new RedirectToAddRacePage()), ADD_RACE(new AddRace()),
    SHOW_RACES_WITHOUT_RESULTS(new ShowRacesWithoutResults()), SET_RESULTS(new SetRaceResults()),
    SUBMIT_WINNER(new SubmitWinner()), EDIT_RACE(new EditRace()),
    ADD_HORSE_TO_RACE(new AddHorseToRace()), REMOVE_HORSE_FROM_RACE(new RemoveHorseFromRace()),
    DELETE_RACE(new DeleteRace()),

    SUBMIT_BET(new SubmitBet()), MAKE_BET(new MakeBet()),

    PLACE_ODDS(new PlaceOdds()), SUBMIT_ODDS(new SubmitOdds()),

    REDIRECT_SIGN_UP(new RedirectToSignUpPage()), REDIRECT_RACES(new RedirectToRacesPage()),
    REDIRECT_HOME(new RedirectToHomePage()), REDIRECT_USER(new RedirectToUserPage()),
    REDIRECT_EDIT_RACE(new RedirectToEditRacePage());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
