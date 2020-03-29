package buyanova.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    JSPPath execute(HttpServletRequest request, HttpServletResponse response);
}
