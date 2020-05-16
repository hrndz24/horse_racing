package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowPastRaces implements Command {
    private static Logger logger = LogManager.getLogger(ShowPastRaces.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Race> pastRaces = RaceService.INSTANCE.getPastRaces();
            request.getSession().setAttribute(JSPParameter.PAST_RACES.getParameter(), pastRaces);
            return JSPPath.PAST_RACES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show past races", e);
            return JSPPath.USER_PAGE.getPath();
        }
    }
}
