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

public class RedirectToEditHorsePage implements Command {
    private static Logger logger = LogManager.getLogger(RedirectToEditHorsePage.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        try {
            Horse horse = HorseService.INSTANCE.getHorseById(horseId);
            request.getSession().setAttribute(JSPParameter.HORSE.getParameter(), horse);
            return JSPPath.EDIT_HORSE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to redirect to edit horse page", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
