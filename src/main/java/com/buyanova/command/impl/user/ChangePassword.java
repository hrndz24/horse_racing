package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePassword implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String oldPassword = request.getParameter(JSPParameter.OLD_PASSWORD.getParameter());
        String newPassword = request.getParameter(JSPParameter.NEW_PASSWORD.getParameter());
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        user.setPassword(oldPassword);
        try {
            UserService.INSTANCE.changePassword(user, newPassword);
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}