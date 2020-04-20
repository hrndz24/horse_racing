package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddRace implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Horse> performingHorses = HorseService.INSTANCE.getPerformingHorses();
            request.getSession().setAttribute(JSPParameter.PERFORMING_HORSES.getParameter(), performingHorses);
            return JSPPath.ADD_RACE.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
