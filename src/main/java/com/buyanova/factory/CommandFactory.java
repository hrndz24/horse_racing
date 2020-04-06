package com.buyanova.factory;

import com.buyanova.command.Command;
import com.buyanova.command.impl.ChangeLanguage;
import com.buyanova.command.impl.LogIn;
import com.buyanova.command.impl.LogOut;
import com.buyanova.command.impl.SignUp;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn()), LOG_OUT(new LogOut()),
    LANGUAGE(new ChangeLanguage());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
