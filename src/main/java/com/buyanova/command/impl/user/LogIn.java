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

public class LogIn implements Command {
    private static Logger logger = LogManager.getLogger(LogIn.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter(JSPParameter.LOGIN.getParameter()));
        user.setPassword(request.getParameter(JSPParameter.PASSWORD.getParameter()));
        try {
            user = UserServiceImpl.INSTANCE.logIn(user);
            request.getSession().setAttribute(JSPParameter.USER_NAME.getParameter(), user.getName());
            request.getSession().setAttribute(JSPParameter.USER.getParameter(), user);
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to log in", e);
            return JSPPath.HOME_PAGE.getPath();
        }
    }
}
