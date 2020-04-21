package com.buyanova.command.impl;

import com.buyanova.command.Command;
import com.buyanova.command.JSPPath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditUser implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        return JSPPath.EDIT_USER.getPath();
    }
}
