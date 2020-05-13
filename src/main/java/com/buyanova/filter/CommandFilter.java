package com.buyanova.filter;


import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.entity.UserRole;
import com.buyanova.factory.CommandFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;

@WebFilter(urlPatterns = "/controller")
public class CommandFilter implements Filter {

    private EnumMap<UserRole, EnumSet<CommandFactory>> roleDependantCommands;
    EnumSet<CommandFactory> commonCommands = EnumSet.range(CommandFactory.LOG_IN, CommandFactory.REDIRECT_USER);

    @Override
    public void init(FilterConfig filterConfig) {
        roleDependantCommands = new EnumMap<>(UserRole.class);
        EnumSet<CommandFactory> bookmakerCommands = EnumSet.range(CommandFactory.PLACE_ODDS, CommandFactory.REDIRECT_EDIT_ODDS);
        EnumSet<CommandFactory> adminCommands = EnumSet.range(CommandFactory.SHOW_HORSES, CommandFactory.REDIRECT_EDIT_HORSE);
        EnumSet<CommandFactory> userCommands = EnumSet.range(CommandFactory.VIEW_BETS, CommandFactory.MAKE_BET);
        roleDependantCommands.put(UserRole.BOOKMAKER, bookmakerCommands);
        roleDependantCommands.put(UserRole.ADMINISTRATOR, adminCommands);
        roleDependantCommands.put(UserRole.CLIENT, userCommands);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String command = request.getParameter(JSPParameter.COMMAND.getParameter());
        if (command != null) {
            User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
            if (user != null) {
                UserRole role = user.getUserRole();
                EnumSet<CommandFactory> commands = roleDependantCommands.get(role);
                if (!(commands.contains(CommandFactory.valueOf(command.toUpperCase())) ||
                        commonCommands.contains(CommandFactory.valueOf(command.toUpperCase())))) {
                    response.sendRedirect(request.getContextPath() + JSPPath.HOME_PAGE.getPath());
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
