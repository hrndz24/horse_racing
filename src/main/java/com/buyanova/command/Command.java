package com.buyanova.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String getJSP(HttpServletRequest request, HttpServletResponse response);
}
