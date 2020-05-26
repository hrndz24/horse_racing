package com.buyanova.command.impl.admin;

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
import java.util.List;

public class SearchUsersByLogin implements Command {

    private static Logger logger = LogManager.getLogger(SearchUsersByLogin.class);

    private UserService userService = ServiceFactory.INSTANCE.getUserService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String loginPattern = request.getParameter(JSPParameter.SEARCH.getParameter());
        try {
            List<User> users = userService.getUsersWhoseLoginMatchString(loginPattern);
            request.getSession().setAttribute(JSPParameter.USERS.getParameter(), users);
            return JSPPath.USERS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to search users", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
