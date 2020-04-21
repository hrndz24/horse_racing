package com.buyanova.command.impl.user;

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

public class ShowRace implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {

        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        String location = request.getParameter(JSPParameter.RACE_LOCATION.getParameter());
        Race race = new Race();
        race.setId(raceId);
        try {
            List<Horse> horses = HorseService.INSTANCE.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.RACE_DATE.getParameter(), date);
            request.getSession().setAttribute(JSPParameter.RACE_ID.getParameter(), raceId);
            request.getSession().setAttribute(JSPParameter.RACE_LOCATION.getParameter(), location);
            return JSPPath.RACE.getPath();
        } catch (ServiceException e) {
            return JSPPath.RACES.getPath();
        }
    }
}
