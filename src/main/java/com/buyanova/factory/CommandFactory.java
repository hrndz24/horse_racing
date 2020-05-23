package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.CommandEnum;

/**
 * {@code CommandFactory} class uses factory pattern to decide
 * what {@code Command} implementations should be provided to
 * the controller layer.
 *
 * @author Natalie
 * @see com.buyanova.command.Command
 * @see com.buyanova.command.CommandEnum
 * @see com.buyanova.controller.MainServlet
 */
public enum CommandFactory {

    INSTANCE;

    /**
     * Returns {@code Command} implementation associated
     * with the provided name.
     * <p>
     * In case of invalid name {@code NonExistingCommand}
     * is returned.
     *
     * @param name string representation of requested command
     * @return {@code Command} implementation that is associated
     *         with provided name in {@code CommandEnum} class
     *         or {@code NonExistingCommand} if the name matches nothing
     */
    public Command getCommandByName(String name) {
        if (name == null) {
            return CommandEnum.NON_EXISTING_COMMAND.getCommand();
        }
        String upperCaseName = name.toUpperCase();
        if (checkCommandExists(upperCaseName)) {
            return CommandEnum.valueOf(upperCaseName).getCommand();
        } else {
            return CommandEnum.NON_EXISTING_COMMAND.getCommand();
        }
    }

    private boolean checkCommandExists(String name) {
        for (CommandEnum commandName : CommandEnum.values()) {
            if (commandName.toString().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
