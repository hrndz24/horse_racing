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

public class AddHorse implements Command {

    private static Logger logger = LogManager.getLogger(AddHorse.class);

    private HorseService horseService = ServiceFactory.INSTANCE.getHorseService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Horse horse = new Horse();
        horse.setName(request.getParameter(JSPParameter.HORSE_NAME.getParameter()));
        horse.setBreed(request.getParameter(JSPParameter.HORSE_BREED.getParameter()));
        horse.setAge(Integer.parseInt(request.getParameter(JSPParameter.HORSE_AGE.getParameter())));
        horse.setRacesWonNumber(Integer.parseInt(request.getParameter(JSPParameter.HORSE_WON_RACES.getParameter())));
        horse.setRacesLostNumber(Integer.parseInt(request.getParameter(JSPParameter.HORSE_LOST_RACES.getParameter())));
        try {
            horseService.addHorse(horse);
            List<Horse> horses = horseService.getPerformingHorses();
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.HORSES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to add horse", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
