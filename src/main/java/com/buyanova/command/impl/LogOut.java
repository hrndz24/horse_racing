package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogOut implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return JSPPath.HOME_PAGE.getPath();
    }
}
