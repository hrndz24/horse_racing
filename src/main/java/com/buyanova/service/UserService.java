package com.buyanova.service;

import com.buyanova.entity.User;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.specification.impl.user.FindUserByLogin;
import com.buyanova.specification.impl.user.FindUserByLoginAndPassword;
import com.buyanova.validator.UserValidator;

import java.math.BigDecimal;
import java.util.List;

public enum UserService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();

    private UserValidator userValidator = new UserValidator();

    public User signUp(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        validateUserFields(user);
        checkLoginIsUnique(user.getLogin());

        user.setActive(true);
        user.setBalance(new BigDecimal(0));
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        try {
            userRepository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public User logIn(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        validateUserLogInCredentials(user);
        user.setPassword(String.valueOf(user.getPassword().hashCode()));
        try {
            List<User> users = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            if (users.isEmpty() || !users.get(0).isActive()) {
                throw new ServiceException("User not found");
            }
            return users.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void removeUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        try {
            userRepository.remove(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void changeLogin(User user, String newLogin) throws ServiceException {
        if (user == null || newLogin == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidLogin(newLogin)) {
            throw new ServiceException("Invalid user login");
        }
        checkLoginIsUnique(newLogin);
        user.setLogin(newLogin);
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void changePassword(User user, String newPassword) throws ServiceException {
        if (user == null || newPassword == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidPassword(newPassword)) {
            throw new ServiceException("Invalid user password");
        }
        try {
            user.setPassword(String.valueOf(user.getPassword().hashCode()));
            List<User> users = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            if (users.isEmpty()) {
                throw new ServiceException("User with such login and password does not exist");
            }
            user.setPassword(String.valueOf(newPassword.hashCode()));
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void changeName(User user, String name) throws ServiceException {
        if (user == null || name == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidName(name)) {
            throw new ServiceException("Invalid user name");
        }

        user.setName(name);
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void changeEmail(User user, String email) throws ServiceException {
        if (user == null || email == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidEmail(email)) {
            throw new ServiceException("Invalid user email");
        }

        user.setEmail(email);
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void replenishAccount(User user, BigDecimal replenishmentSum) throws ServiceException {
        if (user == null || replenishmentSum == null) {
            throw new ServiceException("Null user parameter");
        }
        if (replenishmentSum.doubleValue() <= 0) {
            throw new ServiceException("Negative replenishment sum");
        }
        user.setBalance(user.getBalance().add(replenishmentSum));
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void checkLoginIsUnique(String login) throws ServiceException {
        try {
            List<User> users = userRepository.query(new FindUserByLogin(login));
            if (!users.isEmpty()) {
                throw new ServiceException("Login is already taken");
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
        if (user.getUserRole() == null) {
            throw new ServiceException("Null user role");
        }
    }

    private void validateUserLogInCredentials(User user) throws ServiceException {
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
