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
import java.util.Locale;

public class EditRace implements Command {
    private static Logger logger = LogManager.getLogger(EditRace.class);
    private static final String DATE_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            Race race = updateRaceFields(request);
            raceService.updateRace(race);
            return JSPPath.EDIT_RACE.getPath();
        } catch (ParseException | ServiceException e) {
            logger.warn("Failed to execute command to edit race", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private Race updateRaceFields(HttpServletRequest request) throws ParseException {
        String date = request.getParameter(JSPParameter.RACE_DATE.getParameter());
        Date raceDate = new SimpleDateFormat(DATE_PATTERN, Locale.ENGLISH).parse(date);
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        race.setLocation(request.getParameter(JSPParameter.RACE_LOCATION.getParameter()));
        race.setDistance(Integer.parseInt(request.getParameter(JSPParameter.RACE_DISTANCE.getParameter())));
        race.setPrizeMoney(new BigDecimal(request.getParameter(JSPParameter.RACE_PRIZE_MONEY.getParameter())));
        race.setDate(raceDate);
        return race;
    }
}
