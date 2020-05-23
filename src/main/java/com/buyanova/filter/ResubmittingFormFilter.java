package com.buyanova.filter;

import com.buyanova.command.CommandEnum;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.EnumSet;

@WebFilter(urlPatterns = "/controller")
public class ResubmittingFormFilter implements Filter {

    private EnumSet<CommandEnum> nonRepeatedCommands = EnumSet.of(CommandEnum.SIGN_UP, CommandEnum.CHANGE_BET_SUM,
            CommandEnum.CHANGE_NAME, CommandEnum.CHANGE_EMAIL, CommandEnum.CHANGE_LOGIN, CommandEnum.CHANGE_PASSWORD,
            CommandEnum.SUBMIT_BET, CommandEnum.SUBMIT_ODDS, CommandEnum.SUBMIT_WINNER, CommandEnum.EDIT_ODDS,
            CommandEnum.LOG_IN, CommandEnum.REPLENISH_ACCOUNT, CommandEnum.INVALIDATE_HORSE, CommandEnum.VALIDATE_HORSE,
            CommandEnum.ADD_HORSE, CommandEnum.EDIT_HORSE, CommandEnum.ADD_RACE, CommandEnum.EDIT_RACE,
            CommandEnum.ADD_HORSE_TO_RACE, CommandEnum.REMOVE_HORSE_FROM_RACE, CommandEnum.DELETE_RACE,
            CommandEnum.DELETE_BET);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        Object previousHash = session.getAttribute(JSPParameter.PREVIOUS_REQUEST_HASH.getParameter());
        int currentHash = calculateRequestHash(request);

        if (previousHash != null && (Integer) previousHash == currentHash) {
            String command = request.getParameter(JSPParameter.COMMAND.getParameter());
            if (checkCommandCannotBeResubmitted(command)) {
                response.sendRedirect(request.getContextPath() + JSPPath.RESUBMIT_REDIRECT.getPath());
                return;
            }
        } else {
            previousHash = currentHash;
            session.setAttribute(JSPParameter.PREVIOUS_REQUEST_HASH.getParameter(), previousHash);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private int calculateRequestHash(HttpServletRequest request) {
        int hash = 0;
        for (String[] value : request.getParameterMap().values()) {
            hash += Arrays.toString(value).hashCode();
        }
        return hash;
    }

    private boolean checkCommandCannotBeResubmitted(String command) {
        CommandEnum commandName = CommandEnum.valueOf(command.toUpperCase());
        return nonRepeatedCommands.contains(commandName);
    }
}
