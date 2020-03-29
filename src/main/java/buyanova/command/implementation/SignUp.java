package buyanova.command.implementation;

import buyanova.command.Command;
import buyanova.command.JSPPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignUp implements Command {
    @Override
    public JSPPath execute(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
