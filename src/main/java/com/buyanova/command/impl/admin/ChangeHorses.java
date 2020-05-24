package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.HorseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeHorses implements Command {

    private static Logger logger = LogManager.getLogger(ChangeHorses.class);
    private static final String ALL_HORSES = "ALL";
    private static final String PERFORMING_HORSES = "PERFORMING";

    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String horsesType = request.getParameter(JSPParameter.HORSES_TYPE.getParameter());
        try {
            if (horsesType.equals(ALL_HORSES)) {
                List<Horse> allHorses = horseService.getAllHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), allHorses);
            } else if (horsesType.equals(PERFORMING_HORSES)) {
                List<Horse> performingHorses = horseService.getPerformingHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), performingHorses);
            } else {
                List<Horse> nonPerformingHorses = horseService.getNonPerformingHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), nonPerformingHorses);
            }
            return request.getParameter(JSPParameter.JSP.getParameter());
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to change horses", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
