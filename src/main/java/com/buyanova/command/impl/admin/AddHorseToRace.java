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

public class AddHorseToRace implements Command {

    private static Logger logger = LogManager.getLogger(AddHorseToRace.class);

    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();
    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        try {
            addHorsesToRace(request, race);
            List<Horse> horses = horseService.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.EDIT_RACE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to add horse to race", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private void addHorsesToRace(HttpServletRequest request, Race race) throws ServiceException {
        String[] horseIds = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
        for (String horseId : horseIds) {
            raceService.addHorseToRace(Integer.parseInt(horseId), race.getId());
        }
    }
}
