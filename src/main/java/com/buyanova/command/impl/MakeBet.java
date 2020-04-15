package com.buyanova.command.impl;

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

public class MakeBet implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        int raceId = (Integer) request.getSession().getAttribute(JSPParameter.RACE_ID.getParameter());

        try {
            Horse horse = HorseService.INSTANCE.getHorseById(horseId);
            Race race = RaceService.INSTANCE.getRaceById(raceId);
            Odds odds = OddsService.INSTANCE.getOddsForHorseInRace(horseId, raceId);
            request.getSession().setAttribute(JSPParameter.HORSE.getParameter(), horse);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            request.getSession().setAttribute(JSPParameter.ODDS.getParameter(), odds);
            return JSPPath.MAKE_BET.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
