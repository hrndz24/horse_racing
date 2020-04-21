package com.buyanova.command.impl.language;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeLanguage implements Command {

    private static final String ENGLISH_LOCALE = "en";
    private static final String SPANISH_LOCALE = "es";

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter(JSPParameter.LANG.getParameter()).equals(ENGLISH_LOCALE.toUpperCase())) {
            request.getSession().setAttribute(JSPParameter.LANGUAGE.getParameter(), ENGLISH_LOCALE);
        } else {
            request.getSession().setAttribute(JSPParameter.LANGUAGE.getParameter(), SPANISH_LOCALE);
        }
        return request.getParameter(JSPParameter.JSP.getParameter());
    }
}
