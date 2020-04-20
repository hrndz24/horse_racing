package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class SubmitWinner implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        int raceId = (Integer) request.getSession().getAttribute(JSPParameter.RACE_ID.getParameter());

        try {
            Race race =RaceService.INSTANCE.getRaceById(raceId);
            race.setHorseWinnerId(horseId);
            RaceService.INSTANCE.setRaceResults(race);
            List<Race> racesWithoutResults = RaceService.INSTANCE.getRacesWithoutResults();
            request.getSession().setAttribute(JSPParameter.RACES_WITHOUT_RESULTS.getParameter(), racesWithoutResults);
            return JSPPath.RACES_WITHOUT_RESULTS.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
