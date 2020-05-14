package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Odds;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;
import com.buyanova.service.OddsService;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakeBet implements Command {
    private static Logger logger = LogManager.getLogger(MakeBet.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        int raceId = ((Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter())).getId();

        try {
            Horse horse = HorseService.INSTANCE.getHorseById(horseId);
            Race race = RaceService.INSTANCE.getRaceById(raceId);
            Odds odds = OddsService.INSTANCE.getOddsForHorseInRace(horseId, raceId);
            request.getSession().setAttribute(JSPParameter.HORSE.getParameter(), horse);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            request.getSession().setAttribute(JSPParameter.ODDS.getParameter(), odds);
            return JSPPath.MAKE_BET.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to make a bet", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
