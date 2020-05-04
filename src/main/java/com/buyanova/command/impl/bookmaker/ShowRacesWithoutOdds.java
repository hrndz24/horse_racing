package com.buyanova.command.impl.bookmaker;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRacesWithoutOdds implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Race> racesWithoutOdds = RaceService.INSTANCE.getUpcomingRacesWithoutOdds();
            request.getSession().setAttribute(JSPParameter.RACES_WITHOUT_ODDS.getParameter(), racesWithoutOdds);
            return JSPPath.RACES_WITHOUT_ODDS.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}