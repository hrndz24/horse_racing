package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.RaceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRace implements Command {

    private static Logger logger = LogManager.getLogger(AddRace.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm aa";

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String location = request.getParameter(JSPParameter.RACE_LOCATION.getParameter());
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        int distance = Integer.parseInt(request.getParameter(JSPParameter.RACE_DISTANCE.getParameter()));
        BigDecimal prizeMoney = new BigDecimal(request.getParameter(JSPParameter.RACE_PRIZE_MONEY.getParameter()));
        try {
            Date raceDate = new SimpleDateFormat(DATE_PATTERN).parse(date);
            Race race = new Race();
            race.setLocation(location);
            race.setDistance(distance);
            race.setDate(raceDate);
            race.setPrizeMoney(prizeMoney);
            RaceServiceImpl.INSTANCE.addRace(race);
            String[] horses = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
            for (String horse : horses) {
                RaceServiceImpl.INSTANCE.addHorseToRace(Integer.parseInt(horse), race.getId());
            }
            return JSPPath.RACES.getPath();
        } catch (ParseException | ServiceException e) {
            logger.warn("Failed to execute command to add race", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
