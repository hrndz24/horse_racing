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
import java.util.List;

public class ShowRacesWithoutResults implements Command {
    private static Logger logger = LogManager.getLogger(ShowRacesWithoutResults.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Race> racesWithoutResults = RaceServiceImpl.INSTANCE.getRacesWithoutResults();
            request.getSession().setAttribute(JSPParameter.RACES_WITHOUT_RESULTS.getParameter(), racesWithoutResults);
            return JSPPath.RACES_WITHOUT_RESULTS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show races without results", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
