package com.buyanova.command;

import com.buyanova.command.impl.admin.*;
import com.buyanova.command.impl.bookmaker.EditOdds;
import com.buyanova.command.impl.redirect.RedirectToAddOddsPage;
import com.buyanova.command.impl.bookmaker.ShowRacesWithoutOdds;
import com.buyanova.command.impl.bookmaker.SubmitOdds;
import com.buyanova.command.impl.language.ChangeLanguage;
import com.buyanova.command.impl.redirect.*;
import com.buyanova.command.impl.user.*;

public enum CommandEnum {

    SIGN_UP(new SignUp()),

    /* user */
    VIEW_BETS(new ViewBets()), SHOW_BET(new ShowBet()), CHANGE_BET_SUM(new ChangeBetSum()),
    DELETE_BET(new DeleteBet()), DEACTIVATE_ACCOUNT(new DeleteAccount()),
    SUBMIT_BET(new SubmitBet()), MAKE_BET(new MakeBet()),

    /* bookmaker */
    PLACE_ODDS(new RedirectToAddOddsPage()), SUBMIT_ODDS(new SubmitOdds()), EDIT_ODDS(new EditOdds()),
    SHOW_RACES_WITHOUT_ODDS(new ShowRacesWithoutOdds()), REDIRECT_EDIT_ODDS(new RedirectToEditOddsPage()),

    /* common */
    LOG_IN(new LogIn()), LOG_OUT(new LogOut()), LANGUAGE(new ChangeLanguage()),
    SHOW_RACES(new ShowRaces()), SHOW_RACE(new ShowRace()), SHOW_PAST_RACES(new ShowPastRaces()),
    REPLENISH_ACCOUNT(new ReplenishAccount()), CHANGE_LOGIN(new ChangeLogin()),
    CHANGE_PASSWORD(new ChangePassword()), CHANGE_NAME(new ChangeName()), CHANGE_EMAIL(new ChangeEmail()),
    REDIRECT_SIGN_UP(new RedirectToSignUpPage()), REDIRECT_RACES(new RedirectToRacesPage()),
    REDIRECT_HOME(new RedirectToHomePage()), REDIRECT_USER(new RedirectToUserPage()),
    SHOW_PAST_RACE(new ShowPastRace()),
    NON_EXISTING_COMMAND(new NonExistingCommand()),

    /* administrator */
    SHOW_HORSES(new ShowHorses()), CHANGE_HORSES(new ChangeHorses()),
    INVALIDATE_HORSE(new InvalidateHorse()), VALIDATE_HORSE(new ValidateHorse()),
    ADD_HORSE(new AddHorse()), EDIT_HORSE(new EditHorse()),
    SHOW_USERS(new ShowUsers()), BLOCK_USER(new BlockUser()),
    SEARCH_USER(new SearchUsersByLogin()),
    UNBLOCK_USER(new UnblockUser()), SEARCH_HORSE(new SearchHorsesByName()),
    REDIRECT_ADD_RACE(new RedirectToAddRacePage()), ADD_RACE(new AddRace()),
    SHOW_RACES_WITHOUT_RESULTS(new ShowRacesWithoutResults()), SET_RESULTS(new SetRaceResults()),
    SUBMIT_WINNER(new SubmitWinner()), EDIT_RACE(new EditRace()),
    ADD_HORSE_TO_RACE(new AddHorseToRace()), REMOVE_HORSE_FROM_RACE(new RemoveHorseFromRace()),
    DELETE_RACE(new DeleteRace()),
    REDIRECT_EDIT_RACE(new RedirectToEditRacePage()), REDIRECT_EDIT_HORSE(new RedirectToEditHorsePage());

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
