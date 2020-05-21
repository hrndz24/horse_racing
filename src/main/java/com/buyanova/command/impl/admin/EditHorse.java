package com.buyanova.command.impl.admin;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Horse;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.HorseServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class EditHorse implements Command {
    private static Logger logger = LogManager.getLogger(EditHorse.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int horseId = Integer.parseInt(request.getParameter(JSPParameter.HORSE_ID.getParameter()));
        try {
            Horse horse = HorseServiceImpl.INSTANCE.getHorseById(horseId);
            horse.setBreed(request.getParameter(JSPParameter.HORSE_BREED.getParameter()));
            horse.setName(request.getParameter(JSPParameter.HORSE_NAME.getParameter()));
            horse.setAge(Integer.parseInt(request.getParameter(JSPParameter.HORSE_AGE.getParameter())));
            horse.setRacesWonNumber(Integer.parseInt(request.getParameter(JSPParameter.HORSE_WON_RACES.getParameter())));
            horse.setRacesLostNumber(Integer.parseInt(request.getParameter(JSPParameter.HORSE_LOST_RACES.getParameter())));
            HorseServiceImpl.INSTANCE.updateHorse(horse);
            List<Horse> horses = HorseServiceImpl.INSTANCE.getPerformingHorses();
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.HORSES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to edit horse", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
