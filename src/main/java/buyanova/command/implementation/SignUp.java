package buyanova.command.implementation;

import buyanova.command.Command;
import buyanova.command.JSPPath;
import buyanova.entity.User;
import buyanova.entity.UserRole;
import buyanova.exception.ServiceException;
import buyanova.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp implements Command {
    @Override
    public JSPPath execute(HttpServletRequest request, HttpServletResponse response) {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setUserRole(UserRole.CLIENT);
        try {
            user = UserService.INSTANCE.signUp(user);
            request.getSession().setAttribute("userName",user.getName());
            return JSPPath.USER_PAGE;
        } catch (ServiceException e) {
            return JSPPath.SIGN_UP;
        }
    }
}
