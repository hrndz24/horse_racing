package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ChangeHorses implements Command {

    private static final String ALL_HORSES = "ALL";
    private static final String PERFORMING_HORSES = "PERFORMING";

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String horsesType = request.getParameter(JSPParameter.HORSES_TYPE.getParameter());
        try {
            if (horsesType.equals(ALL_HORSES)) {
                List<Horse> allHorses = HorseService.INSTANCE.getAllHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), allHorses);
            } else if (horsesType.equals(PERFORMING_HORSES)) {
                List<Horse> performingHorses = HorseService.INSTANCE.getPerformingHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), performingHorses);
            } else {
                List<Horse> nonPerformingHorses = HorseService.INSTANCE.getNonPerformingHorses();
                request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), nonPerformingHorses);
            }
            return request.getParameter(JSPParameter.JSP.getParameter());
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}