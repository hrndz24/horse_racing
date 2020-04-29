package com.buyanova.command.impl.redirect;

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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RedirectToEditOddsPage implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        try {
            Race race = RaceService.INSTANCE.getRaceById(raceId);
            List<Horse> horses = HorseService.INSTANCE.getHorsesFromRace(race);
            List<Odds> odds = OddsService.INSTANCE.getOddsForRace(raceId);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.ODDS.getParameter(), odds);
            return JSPPath.EDIT_ODDS.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
