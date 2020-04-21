package com.buyanova.command.impl.bookmaker;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Odds;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.OddsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitOdds implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        User bookmaker = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        int bookmakerId = bookmaker.getId();
        String[] horsesIds = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
        String[] oddsInFavourArray = request.getParameterValues(JSPParameter.ODDS_IN_FAVOUR.getParameter());
        String[] oddsAgainstArray = request.getParameterValues(JSPParameter.ODDS_AGAINST.getParameter());

        Odds odds;
        for (int i = 0; i < horsesIds.length; i++) {
            odds = new Odds();
            odds.setRaceId(raceId);
            odds.setBookmakerId(bookmakerId);
            odds.setHorseId(Integer.parseInt(horsesIds[i]));
            odds.setOddsInFavour(Integer.parseInt(oddsInFavourArray[i]));
            odds.setOddsAgainst(Integer.parseInt(oddsAgainstArray[i]));
            try {
                OddsService.INSTANCE.addOdds(odds);
            } catch (ServiceException e) {
                request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
                return JSPPath.ERROR_PAGE.getPath();
            }
        }
        return JSPPath.RACES.getPath();
    }
}
