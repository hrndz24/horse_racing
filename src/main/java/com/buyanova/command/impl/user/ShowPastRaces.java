package com.buyanova.command.impl.user;

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
import java.util.List;

public class ShowPastRaces implements Command {
    private static Logger logger = LogManager.getLogger(ShowPastRaces.class);

    private RaceService raceService = ServiceFactory.INSTANCE.getRaceService();

    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int RECORDS_PER_PAGE = 4;

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageNumber = identifyPageNumber(request);
            int racesQuantity = raceService.getPastRacesTotalNumber();
            int pageQuantity = (int) Math.ceil(racesQuantity * 1.0 / RECORDS_PER_PAGE);
            int indexFrom = (pageNumber - 1) * RECORDS_PER_PAGE;
            List<Race> racesToShowOnPage = raceService.getPastRacesSubList(indexFrom, RECORDS_PER_PAGE);
            request.getSession().setAttribute(JSPParameter.PAST_RACES.getParameter(), racesToShowOnPage);
            request.getSession().setAttribute(JSPParameter.PAGE_QUANTITY.getParameter(), pageQuantity);
            request.getSession().setAttribute(JSPParameter.CURRENT_PAGE.getParameter(), pageNumber);
            return JSPPath.PAST_RACES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show past races", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private int identifyPageNumber(HttpServletRequest request) {
        if (request.getParameter(JSPParameter.PAGE_NUMBER.getParameter()) != null) {
            return Integer.parseInt(request.getParameter(JSPParameter.PAGE_NUMBER.getParameter()));
        } else {
            return FIRST_PAGE_NUMBER;
        }
    }
}
