package com.buyanova.command.impl.bookmaker;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Odds;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.OddsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditOdds implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int raceId = ((Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter())).getId();
        String[] horsesIds = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
        String[] oddsInFavourArray = request.getParameterValues(JSPParameter.ODDS_IN_FAVOUR.getParameter());
        String[] oddsAgainstArray = request.getParameterValues(JSPParameter.ODDS_AGAINST.getParameter());

        Odds odds;
        for (int i = 0; i < horsesIds.length; i++) {
            try {
                odds = OddsService.INSTANCE.getOddsForHorseInRace(Integer.parseInt(horsesIds[i]), raceId);
                odds.setOddsInFavour(Integer.parseInt(oddsInFavourArray[i]));
                odds.setOddsAgainst(Integer.parseInt(oddsAgainstArray[i]));

                OddsService.INSTANCE.changeOddsNumbers(odds);
            } catch (ServiceException e) {
                request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
                return JSPPath.ERROR_PAGE.getPath();
            }
        }
        return JSPPath.RACES.getPath();
    }
}
