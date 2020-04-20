package com.buyanova.command.impl.redirect;

import com.buyanova.command.Command;
import com.buyanova.command.JSPPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToUserPage implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        return JSPPath.USER_PAGE.getPath();
    }
}
