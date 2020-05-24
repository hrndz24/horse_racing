package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteRace implements Command {
    private static Logger logger = LogManager.getLogger(DeleteRace.class);

    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        try {
            raceService.removeRace(race);
            List<Race> races = raceService.getUpcomingRaces();
            request.getSession().setAttribute(JSPParameter.RACES.getParameter(), races);
            return JSPPath.RACES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to delete race", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
