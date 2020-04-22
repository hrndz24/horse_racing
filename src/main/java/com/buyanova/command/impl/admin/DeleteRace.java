package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteRace implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        try {
            RaceService.INSTANCE.removeRace(race);
            List<Race> races = RaceService.INSTANCE.getUpcomingRaces();
            request.getSession().setAttribute(JSPParameter.RACES.getParameter(), races);
            return JSPPath.RACES.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
