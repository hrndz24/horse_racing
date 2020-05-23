package com.buyanova.controller;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.exception.ConnectionPoolException;
import com.buyanova.factory.CommandFactory;
import com.buyanova.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * {@code MainServlet} class represents project's
 * controller layer and is used to process users'
 * http requests.
 * <p>
 * It should be the only {@code Servlet} class used
 * in the project.
 * <p>
 * Note: the class is loaded at the same time as
 * the servlet container starts.
 *
 * @author Natalie
 */
@WebServlet(urlPatterns = "/controller", loadOnStartup = 0)
public class MainServlet extends HttpServlet {

    private static Logger logger = LogManager.getLogger(MainServlet.class);

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(JSPParameter.COMMAND.getParameter());
        Command command = CommandFactory.INSTANCE.getCommandByName(commandName);
        String jsp = command.getJSP(request, response);
        request.getRequestDispatcher(jsp).forward(request, response);
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
}
