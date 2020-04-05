package buyanova.factory;

import buyanova.command.Command;
import buyanova.command.implementation.ChangeLanguage;
import buyanova.command.implementation.LogIn;
import buyanova.command.implementation.SignUp;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn()),
    LANGUAGE(new ChangeLanguage());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
