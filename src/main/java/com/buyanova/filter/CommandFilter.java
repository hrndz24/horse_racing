package com.buyanova.filter;


import com.buyanova.command.CommandEnum;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.User;
import com.buyanova.entity.UserRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumMap;
import java.util.EnumSet;

@WebFilter(urlPatterns = "/controller")
public class CommandFilter implements Filter {

    private EnumMap<UserRole, EnumSet<CommandEnum>> roleDependantCommands;
    private EnumSet<CommandEnum> commonCommands;
    private EnumSet<CommandEnum> guestCommands;

    @Override
    public void init(FilterConfig filterConfig) {
        roleDependantCommands = new EnumMap<>(UserRole.class);
        EnumSet<CommandEnum> bookmakerCommands = EnumSet.range(CommandEnum.PLACE_ODDS, CommandEnum.REDIRECT_EDIT_ODDS);
        EnumSet<CommandEnum> adminCommands = EnumSet.range(CommandEnum.SHOW_HORSES, CommandEnum.REDIRECT_EDIT_HORSE);
        EnumSet<CommandEnum> userCommands = EnumSet.range(CommandEnum.VIEW_BETS, CommandEnum.MAKE_BET);
        roleDependantCommands.put(UserRole.BOOKMAKER, bookmakerCommands);
        roleDependantCommands.put(UserRole.ADMINISTRATOR, adminCommands);
        roleDependantCommands.put(UserRole.CLIENT, userCommands);
        commonCommands = EnumSet.range(CommandEnum.LOG_IN, CommandEnum.NON_EXISTING_COMMAND);
        guestCommands = EnumSet.of(CommandEnum.SIGN_UP, CommandEnum.REDIRECT_HOME,
                CommandEnum.REDIRECT_SIGN_UP, CommandEnum.LOG_IN);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String command = request.getParameter(JSPParameter.COMMAND.getParameter());
        if (command != null) {
            if (!checkCommandExists(command)) {
                response.sendRedirect(request.getContextPath() + JSPPath.HOME_PAGE.getPath());
                return;
            }
            User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
            if (!checkUserRoleMatchesCommand(user, command)) {
                response.sendRedirect(request.getContextPath() + JSPPath.HOME_PAGE.getPath());
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkCommandExists(String command) {
        for (CommandEnum commandName : CommandEnum.values()) {
            if (commandName.toString().equals(command.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkUserRoleMatchesCommand(User user, String command) {
        CommandEnum commandName = CommandEnum.valueOf(command.toUpperCase());
        if (user == null) {
            return guestCommands.contains(commandName);
        } else {
            UserRole role = user.getUserRole();
            EnumSet<CommandEnum> commands = roleDependantCommands.get(role);
            return commands.contains(commandName) || commonCommands.contains(commandName);
        }
    }
}
