package com.buyanova.command.impl.redirect;

import com.buyanova.command.Command;
import com.buyanova.command.JSPPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToSignUpPage implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return JSPPath.SIGN_UP.getPath();
    }
}
