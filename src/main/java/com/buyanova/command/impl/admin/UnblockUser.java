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

public class UnblockUser implements Command {
    private static Logger logger = LogManager.getLogger(UnblockUser.class);

    private UserService userService = ServiceFactory.INSTANCE.getUserService();

    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int userId = Integer.parseInt(request.getParameter(JSPParameter.USER_ID.getParameter()));
        int pageNumber = (Integer) request.getSession().getAttribute(JSPParameter.CURRENT_PAGE.getParameter());
        try {
            unblockUser(userId);
            setUsersSessionAttribute(request, pageNumber);
            return JSPPath.USERS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to block user", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private void unblockUser(int userId) throws ServiceException {
        User user = userService.getUserById(userId);
        user.setActive(true);
        userService.unblockUser(user);
    }

    private void setUsersSessionAttribute(HttpServletRequest request, int pageNumber) throws ServiceException {
        int indexFrom = (pageNumber - 1) * RECORDS_PER_PAGE;
        List<User> usersToShowOnPage = userService.getUsersSubList(indexFrom, RECORDS_PER_PAGE);
        request.getSession().setAttribute(JSPParameter.USERS.getParameter(), usersToShowOnPage);
    }
}
