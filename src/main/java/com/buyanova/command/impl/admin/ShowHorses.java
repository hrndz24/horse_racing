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

public class ShowHorses implements Command {
    private static Logger logger = LogManager.getLogger(ShowHorses.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Horse> horses = HorseServiceImpl.INSTANCE.getPerformingHorses();
            request.getSession().setAttribute(JSPParameter.HORSES.getParameter(), horses);
            return JSPPath.HORSES.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show horses", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}