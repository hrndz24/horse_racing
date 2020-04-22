package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.HorseService;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AddHorseToRace implements Command {

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String[] horseIds = request.getParameterValues(JSPParameter.HORSE_ID.getParameter());
        Race race = (Race) request.getSession().getAttribute(JSPParameter.RACE.getParameter());
        try {
            for (String horseId : horseIds) {
                RaceService.INSTANCE.addHorseToRace(Integer.parseInt(horseId), race.getId());
            }
            List<Horse> horses = HorseService.INSTANCE.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.EDIT_RACE.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
