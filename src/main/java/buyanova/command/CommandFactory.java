package buyanova.command;

import buyanova.command.implementation.LogIn;
import buyanova.command.implementation.SignUp;

public enum CommandFactory {

    SIGN_UP(new SignUp()), LOG_IN(new LogIn());

    private Command command;

    CommandFactory(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }
}
