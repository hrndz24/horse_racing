package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.entity.UserRole;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp implements Command {
    private static Logger logger = LogManager.getLogger(SignUp.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter(JSPParameter.LOGIN.getParameter()));
        user.setPassword(request.getParameter(JSPParameter.PASSWORD.getParameter()));
        user.setName(request.getParameter(JSPParameter.NAME.getParameter()));
        user.setEmail(request.getParameter(JSPParameter.EMAIL.getParameter()));
        user.setUserRole(UserRole.CLIENT);
        try {
            UserService.INSTANCE.signUp(user);
            request.getSession().setAttribute(JSPParameter.USER_NAME.getParameter(), user.getName());
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to sign up", e);
            return JSPPath.SIGN_UP.getPath();
        }
    }
}
