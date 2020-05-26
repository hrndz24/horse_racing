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

public class DeleteAccount implements Command {
    private static Logger logger = LogManager.getLogger(DeleteAccount.class);

    private UserService userService = ServiceFactory.INSTANCE.getUserService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        try {
            userService.removeUser(user);
            request.getSession().invalidate();
            return JSPPath.HOME_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to delete account", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
