package buyanova.controller;

import buyanova.command.Command;
import buyanova.command.JSPParameter;
import buyanova.factory.CommandFactory;
import buyanova.command.JSPPath;
import buyanova.exception.ConnectionPoolException;
import buyanova.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
public class MainServlet extends HttpServlet {

    private Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool.INSTANCE.init();
        } catch (ConnectionPoolException e) {
            logger.fatal(e);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.INSTANCE.destroyPool();
        } catch (ConnectionPoolException e) {
            logger.fatal(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandFactory.valueOf(request.getParameter(JSPParameter.COMMAND.getParameter()).toUpperCase()).getCommand();
        JSPPath jsp = command.execute(request, response);
        request.getRequestDispatcher(jsp.getPath()).forward(request, response);
    }
}
