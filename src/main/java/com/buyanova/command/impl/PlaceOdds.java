package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class PlaceOdds implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        Race race = new Race();
        race.setId(raceId);
        try {
            List<Horse> horses = HorseService.INSTANCE.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.RACE_DATE.getParameter(), date);
            request.getSession().setAttribute(JSPParameter.RACE_ID.getParameter(), raceId);
            return JSPPath.PLACE_ODDS.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
