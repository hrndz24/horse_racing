package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.HorseServiceImpl;
import com.buyanova.service.impl.RaceServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowRace implements Command {
    private static Logger logger = LogManager.getLogger(ShowRace.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int raceId = Integer.parseInt(request.getParameter(JSPParameter.RACE_ID.getParameter()));
        try {
            Race race = RaceServiceImpl.INSTANCE.getRaceById(raceId);
            List<Horse> horses = HorseServiceImpl.INSTANCE.getHorsesFromRace(race);
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            request.getSession().setAttribute(JSPParameter.RACE.getParameter(), race);
            return JSPPath.RACE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show race", e);
            return JSPPath.RACES.getPath();
        }
    }
}
