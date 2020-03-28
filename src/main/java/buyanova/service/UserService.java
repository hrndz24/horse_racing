package buyanova.service;

import buyanova.entity.User;
import buyanova.factory.RepositoryFactory;
import buyanova.repository.user.UserRepository;

public enum UserService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();

    public User signUp(User user){
        /*
        validate user stuff
        hash password
        pass to repository
         */
        return null;
    }

    public User logIn(User user){
        /*
        validate user stuff
        hash password
        query user by password and login in repository
         */
        return null;
    }
}
