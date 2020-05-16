package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.CommandEnum;

public enum CommandFactory {

    INSTANCE;

    public Command getCommandByName(String name) {
        if (name == null) {
            return CommandEnum.NON_EXISTING_COMMAND.getCommand();
        }
        String upperCaseName = name.toUpperCase();
        if (checkCommandExists(upperCaseName)) {
            return CommandEnum.valueOf(upperCaseName).getCommand();
        }
        return CommandEnum.NON_EXISTING_COMMAND.getCommand();
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
