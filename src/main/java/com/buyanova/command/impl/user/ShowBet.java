package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Odds;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.BetService;
import com.buyanova.service.HorseService;
import com.buyanova.service.OddsService;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowBet implements Command {
    private static Logger logger = LogManager.getLogger(ShowBet.class);

    private BetService betService = ServiceFactory.INSTANCE.getBetService();
    private OddsService oddsService = ServiceFactory.INSTANCE.getOddsService();
    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();
    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int betId = Integer.parseInt(request.getParameter(JSPParameter.BET_ID.getParameter()));
        try {
            Bet bet = betService.getBetById(betId);
            Odds odds = oddsService.getOddsById(bet.getOddsId());
            Race race = raceService.getRaceById(odds.getRaceId());
            Horse horse = horseService.getHorseById(odds.getHorseId());
            request.getSession().setAttribute(JSPParameter.BET.getParameter(), bet);
            request.getSession().setAttribute(JSPParameter.ODDS.getParameter(), odds);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            request.getSession().setAttribute(JSPParameter.HORSE.getParameter(), horse);
            return JSPPath.BET.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show bet", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
