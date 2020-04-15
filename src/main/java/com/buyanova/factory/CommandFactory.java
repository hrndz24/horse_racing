package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.impl.*;
import com.buyanova.command.impl.redirect.RedirectToHomePage;
import com.buyanova.command.impl.redirect.RedirectToRacesPage;
import com.buyanova.command.impl.redirect.RedirectToSignUpPage;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn()), LOG_OUT(new LogOut()),
    LANGUAGE(new ChangeLanguage()), SHOW_RACES(new ShowRaces()), SHOW_RACE(new ShowRace()),
    MAKE_BET(new MakeBet()), SUBMIT_BET(new SubmitBet()),
    PLACE_ODDS(new PlaceOdds()), SUBMIT_ODDS(new SubmitOdds()),

    REDIRECT_SIGN_UP(new RedirectToSignUpPage()), REDIRECT_RACES(new RedirectToRacesPage()),
    REDIRECT_HOME(new RedirectToHomePage());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
