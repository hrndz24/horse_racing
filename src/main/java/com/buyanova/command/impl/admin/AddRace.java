package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddRace implements Command {

    private static Logger logger = LogManager.getLogger(AddRace.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'hh:mm";

    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            Race race = buildRace(request);
            raceService.addRace(race);
            addHorsesToRace(request, race);
            List<Race> races = raceService.getUpcomingRaces();
            request.getSession().setAttribute(JSPParameter.RACES.getParameter(), races);
            return JSPPath.RACES.getPath();
        } catch (ParseException | ServiceException e) {
            logger.warn("Failed to execute command to add race", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private Race buildRace(HttpServletRequest request) throws ParseException {
        Race race = new Race();
        race.setLocation(request.getParameter(JSPParameter.RACE_LOCATION.getParameter()));
        race.setDistance(Integer.parseInt(request.getParameter(JSPParameter.RACE_DISTANCE.getParameter())));
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        Date raceDate = new SimpleDateFormat(DATE_PATTERN).parse(date);
        race.setDate(raceDate);
        race.setPrizeMoney(new BigDecimal(request.getParameter(JSPParameter.RACE_PRIZE_MONEY.getParameter())));
        return race;
    }

    private void addHorsesToRace(HttpServletRequest request, Race race) throws ServiceException {
        String[] horses = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
        for (String horse : horses) {
            raceService.addHorseToRace(Integer.parseInt(horse), race.getId());
        }
    }
}
