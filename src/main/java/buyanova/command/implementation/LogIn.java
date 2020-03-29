package buyanova.command.implementation;

import buyanova.command.Command;
import buyanova.command.JSPPath;
import buyanova.entity.User;
import buyanova.exception.ServiceException;
import buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogIn implements Command {
    @Override
    public JSPPath execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        try {
             user = UserService.INSTANCE.logIn(user);
            request.getSession().setAttribute("userName",user.getName());
            return JSPPath.USER_PAGE;
        } catch (ServiceException e) {
            return JSPPath.SIGN_UP;
        }
    }
}
