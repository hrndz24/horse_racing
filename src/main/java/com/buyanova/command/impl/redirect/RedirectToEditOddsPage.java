package com.buyanova.command.impl.redirect;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Odds;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.HorseService;
import com.buyanova.service.OddsService;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RedirectToEditOddsPage implements Command {
    private static Logger logger = LogManager.getLogger(RedirectToEditOddsPage.class);

    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();
    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();
    private OddsService oddsService = ServiceFactory.INSTANCE.getOddsService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        try {
            Race race = raceService.getRaceById(raceId);
            List<Horse> horses = horseService.getHorsesFromRace(race);
            List<Odds> odds = oddsService.getOddsForRace(raceId);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.ODDS.getParameter(), odds);
            return JSPPath.EDIT_ODDS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to redirect to edit odds page", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
