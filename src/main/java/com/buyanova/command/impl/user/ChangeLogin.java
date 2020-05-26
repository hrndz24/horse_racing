package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLogin implements Command {
    private static Logger logger = LogManager.getLogger(ChangeLogin.class);

    private UserService userService = ServiceFactory.INSTANCE.getUserService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String newLogin = request.getParameter(JSPParameter.LOGIN.getParameter());
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        try {
            userService.changeLogin(user, newLogin);
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to change login", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
