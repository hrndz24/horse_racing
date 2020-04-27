package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeEmail implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter(JSPParameter.EMAIL.getParameter());
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        try {
            UserService.INSTANCE.changeEmail(user, email);
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}