package com.buyanova.command.impl.redirect;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class RedirectToAddRacePage implements Command {
    private static Logger logger = LogManager.getLogger(RedirectToAddRacePage.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Horse> performingHorses = HorseService.INSTANCE.getPerformingHorses();
            request.getSession().setAttribute(JSPParameter.PERFORMING_HORSES.getParameter(), performingHorses);
            return JSPPath.ADD_RACE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to redirect to add race page", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
