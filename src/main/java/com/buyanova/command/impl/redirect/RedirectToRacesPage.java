package com.buyanova.command.impl.redirect;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToRacesPage implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        if (user == null) {
            return JSPPath.HOME_PAGE.getPath();
        }
        return JSPPath.RACES.getPath();
    }
}
