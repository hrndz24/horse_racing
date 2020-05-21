package com.buyanova.command.impl.bookmaker;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.HorseServiceImpl;
import com.buyanova.service.impl.RaceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PlaceOdds implements Command {
    private static Logger logger = LogManager.getLogger(PlaceOdds.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {

        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        try {
            Race race = RaceServiceImpl.INSTANCE.getRaceById(raceId);
            List<Horse> horses = HorseServiceImpl.INSTANCE.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            return JSPPath.PLACE_ODDS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to place odds", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
