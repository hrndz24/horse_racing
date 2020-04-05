package buyanova.command.impl;

import buyanova.command.Command;
import buyanova.command.JSPParameter;
import buyanova.command.JSPPath;
import buyanova.entity.User;
import buyanova.exception.ServiceException;
import buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogIn implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter(JSPParameter.LOGIN.getParameter()));
        user.setPassword(request.getParameter(JSPParameter.PASSWORD.getParameter()));
        try {
             user = UserService.INSTANCE.logIn(user);
            request.getSession().setAttribute(JSPParameter.USER_NAME.getParameter(),user.getName());
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            return JSPPath.HOME_PAGE.getPath();
        }
    }
}
