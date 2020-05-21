package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeName implements Command {
    private static Logger logger = LogManager.getLogger(ChangeName.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter(JSPParameter.NAME.getParameter());
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        try {
            UserServiceImpl.INSTANCE.changeName(user, name);
            request.getSession().setAttribute(JSPParameter.USER_NAME.getParameter(), user.getName());
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to change name", e);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
