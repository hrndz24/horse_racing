package com.buyanova.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command interface uses command pattern to handle
 * http requests at controller layer presented as
 * {@code Servlet} class.
 *
 * @author Natalie
 * @see com.buyanova.controller.MainServlet
 * @see com.buyanova.command.CommandEnum
 */
public interface Command {

    /**
     * Returns path of the JSP in the project that
     * should handle processed request.
     *
     * @param request  client http request
     * @param response server http response
     * @return JSP path for further handling request
     */
    String getJSP(HttpServletRequest request, HttpServletResponse response);
}
