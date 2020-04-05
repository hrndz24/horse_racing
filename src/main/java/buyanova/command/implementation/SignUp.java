package buyanova.command.implementation;

import buyanova.command.Command;
import buyanova.command.JSPParameter;
import buyanova.command.JSPPath;
import buyanova.entity.User;
import buyanova.entity.UserRole;
import buyanova.exception.ServiceException;
import buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
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
            return JSPPath.SIGN_UP.getPath();
        }
    }
}
