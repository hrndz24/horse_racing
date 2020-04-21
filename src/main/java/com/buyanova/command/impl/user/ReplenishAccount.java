package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class ReplenishAccount implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        BigDecimal sum = new BigDecimal(request.getParameter(JSPParameter.SUM.getParameter()));
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        try {
            UserService.INSTANCE.replenishAccount(user, sum);
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
