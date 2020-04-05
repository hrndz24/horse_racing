package buyanova.factory;

import buyanova.command.Command;
import buyanova.command.impl.ChangeLanguage;
import buyanova.command.impl.LogIn;
import buyanova.command.impl.SignUp;

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
