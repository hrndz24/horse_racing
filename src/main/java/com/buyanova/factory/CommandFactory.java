package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.impl.admin.*;
import com.buyanova.command.impl.bookmaker.PlaceOdds;
import com.buyanova.command.impl.bookmaker.SubmitOdds;
import com.buyanova.command.impl.redirect.RedirectToHomePage;
import com.buyanova.command.impl.redirect.RedirectToRacesPage;
import com.buyanova.command.impl.redirect.RedirectToSignUpPage;
import com.buyanova.command.impl.redirect.RedirectToUserPage;
import com.buyanova.command.impl.user.*;
import com.buyanova.command.impl.language.ChangeLanguage;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn()), LOG_OUT(new LogOut()),
    VIEW_BETS(new ViewBets()),
    REPLENISH_ACCOUNT(new ReplenishAccount()),

    LANGUAGE(new ChangeLanguage()),
    SHOW_RACES(new ShowRaces()), SHOW_RACE(new ShowRace()),
    ADD_RACE(new AddRace()), SUBMIT_RACE(new SubmitRace()),
    SHOW_RACES_WITHOUT_RESULTS(new ShowRacesWithoutResults()), SET_RESULTS(new SetRaceResults()),
    SUBMIT_WINNER(new SubmitWinner()),

    MAKE_BET(new MakeBet()), SUBMIT_BET(new SubmitBet()),

    PLACE_ODDS(new PlaceOdds()), SUBMIT_ODDS(new SubmitOdds()),

    REDIRECT_SIGN_UP(new RedirectToSignUpPage()), REDIRECT_RACES(new RedirectToRacesPage()),
    REDIRECT_HOME(new RedirectToHomePage()), REDIRECT_USER(new RedirectToUserPage());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
