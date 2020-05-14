package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Odds;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
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

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int betId = Integer.parseInt(request.getParameter(JSPParameter.BET_ID.getParameter()));
        try {
            Bet bet = BetService.INSTANCE.getBetById(betId);
            Odds odds = OddsService.INSTANCE.getOddsById(bet.getOddsId());
            Race race = RaceService.INSTANCE.getRaceById(odds.getRaceId());
            Horse horse = HorseService.INSTANCE.getHorseById(odds.getHorseId());
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
