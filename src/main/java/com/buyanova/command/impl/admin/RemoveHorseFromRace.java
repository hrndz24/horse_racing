package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.HorseService;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RemoveHorseFromRace implements Command {
    private static Logger logger = LogManager.getLogger(RemoveHorseFromRace.class);

    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();
    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        try {
            raceService.removeHorseFromRace(horseId, race.getId());
            List<Horse> horses = horseService.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.EDIT_RACE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to remove horse from race", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
