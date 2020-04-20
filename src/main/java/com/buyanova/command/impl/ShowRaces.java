package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.RaceService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRaces implements Command {

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {

        try {
            List<Race> races = RaceService.INSTANCE.getUpcomingRaces();
            request.getSession().setAttribute(JSPParameter.RACES.getParameter(), races);
            return JSPPath.RACES.getPath();
        } catch (ServiceException e) {
            return JSPPath.HOME_PAGE.getPath();
        }
    }
}
