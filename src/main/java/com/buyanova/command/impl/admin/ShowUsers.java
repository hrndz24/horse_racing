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

public class ShowUsers implements Command {

    private static Logger logger = LogManager.getLogger(ShowUsers.class);

    private UserService userService = ServiceFactory.INSTANCE.getUserService();

    private static final int FIRST_PAGE_NUMBER = 1;
    private static final int RECORDS_PER_PAGE = 5;

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        try {
            int pageNumber = identifyPageNumber(request);
            int indexFrom = (pageNumber - 1) * RECORDS_PER_PAGE;
            List<User> usersToShowOnPage = userService.getUsersSubList(indexFrom, RECORDS_PER_PAGE);
            int usersNumber = userService.getUsersTotalNumber();
            int pageQuantity = (int) Math.ceil(usersNumber * 1.0 / RECORDS_PER_PAGE);
            request.getSession().setAttribute(JSPParameter.USERS.getParameter(), usersToShowOnPage);
            request.getSession().setAttribute(JSPParameter.PAGE_QUANTITY.getParameter(), pageQuantity);
            request.getSession().setAttribute(JSPParameter.CURRENT_PAGE.getParameter(), pageNumber);
            return JSPPath.USERS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to show users", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private int identifyPageNumber(HttpServletRequest request) {
        if (request.getParameter(JSPParameter.PAGE_NUMBER.getParameter()) != null) {
            return Integer.parseInt(request.getParameter(JSPParameter.PAGE_NUMBER.getParameter()));
        } else {
            return FIRST_PAGE_NUMBER;
        }
    }
}
