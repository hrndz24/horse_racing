package buyanova.service;

import buyanova.entity.User;
import buyanova.exception.RepositoryException;
import buyanova.exception.ServiceException;
import buyanova.factory.RepositoryFactory;
import buyanova.repository.user.UserRepository;
import buyanova.specification.sql.implementation.user.FindUserByLogin;
import buyanova.specification.sql.implementation.user.FindUserByLoginAndPassword;
import buyanova.validator.UserValidator;

import java.math.BigDecimal;
import java.util.List;

public enum UserService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();

    private UserValidator userValidator = new UserValidator();

    public User signUp(User user) throws ServiceException {
        validateUserFields(user);
        checkLoginIsUnique(user);

        user.setActive(true);
        user.setBalance(new BigDecimal(0));
        user.setPassword(String.valueOf(user.getPassword().hashCode())); // TODO: 29.03.2020 make normal hashing
        try {
            userRepository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public User logIn(User user) throws ServiceException {
        validateUserLogInCredentials(user);
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        // TODO: 29.03.2020 make normal hashing
        try {
            List<User> users = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            return users.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void removeUser(User user) throws ServiceException {
        validateUserFields(user);
        try {
            userRepository.remove(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void updateUser(User user) throws ServiceException {
        validateUserFields(user);
        checkLoginIsUnique(user);
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkLoginIsUnique(User user) throws ServiceException {
        try {
            List<User> users = userRepository.query(new FindUserByLogin(user.getLogin()));
            if (!users.isEmpty()) {
                if (users.get(0).getId() != user.getId()) {
                    throw new ServiceException("Login already taken");
                }
            }
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateUserFields(User user) throws ServiceException {
        validateUserLogInCredentials(user);

        if (user.getName() == null) {
            throw new ServiceException("Null user name");
        }
        if (!userValidator.isValidName(user.getName())) {
            throw new ServiceException("Invalid user name");
        }

        if (user.getEmail() == null) {
            throw new ServiceException("Null user email");
        }
        if (!userValidator.isValidEmail(user.getEmail())) {
            throw new ServiceException("Invalid user email");
        }
        if(user.getUserRole()==null){
            throw new ServiceException("Null user role");
        }
    }

    private void validateUserLogInCredentials(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }

        if (user.getLogin() == null) {
            throw new ServiceException("Null user login");
        }
        if (!userValidator.isValidLogin(user.getLogin())) {
            throw new ServiceException("Invalid user login");
        }

        if (user.getPassword() == null) {
            throw new ServiceException("Null user password");
        }
        if (!userValidator.isValidPassword(user.getPassword())) {
            throw new ServiceException("Invalid user password");
        }
    }
}
