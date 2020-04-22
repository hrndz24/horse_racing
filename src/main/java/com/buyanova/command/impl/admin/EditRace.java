package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditRace implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        String location = request.getParameter(JSPParameter.RACE_LOCATION.getParameter());
        int distance = Integer.parseInt(request.getParameter(JSPParameter.RACE_DISTANCE.getParameter()));
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        BigDecimal prizeMoney = new BigDecimal(request.getParameter(JSPParameter.RACE_PRIZE_MONEY.getParameter()));

        try {
            Date raceDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(date);
            race.setLocation(location);
            race.setDistance(distance);
            race.setDate(raceDate);
            race.setPrizeMoney(prizeMoney);
            RaceService.INSTANCE.updateRace(race);
            return JSPPath.EDIT_RACE.getPath();
        } catch (ParseException | ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
