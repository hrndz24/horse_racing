package com.buyanova.service.impl;

import com.buyanova.entity.User;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.user.UserRepository;
import com.buyanova.service.UserService;
import com.buyanova.specification.impl.user.*;
import com.buyanova.util.PasswordEncryptor;
import com.buyanova.validator.UserValidator;

import java.math.BigDecimal;
import java.util.List;

public enum UserServiceImpl implements UserService {
    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private UserRepository userRepository = factory.getUserRepository();

    private UserValidator userValidator = new UserValidator();

    public void signUp(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        validateUserFields(user);
        checkLoginIsUnique(user.getLogin());
        setDefaultUserValues(user);
        encryptUserPassword(user);
        tryAddUserToDataSource(user);
    }

    private void setDefaultUserValues(User user) {
        user.setActive(true);
        user.setBalance(BigDecimal.ZERO);
    }

    private void encryptUserPassword(User user) {
        String unencryptedPassword = user.getPassword();
        String encryptedPassword = PasswordEncryptor.INSTANCE.encryptPassword(unencryptedPassword);
        user.setPassword(encryptedPassword);
    }

    private void tryAddUserToDataSource(User user) throws ServiceException {
        try {
            userRepository.add(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to sign up due to data source problems", e);
        }
    }

    public User logIn(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        validateUserLogInCredentials(user);
        encryptUserPassword(user);
        return getFirstUserFoundByLoginAndPasswordIfExists(user);
    }

    private User getFirstUserFoundByLoginAndPasswordIfExists(User user) throws ServiceException {
        try {
            List<User> users = userRepository.query(new FindUserByLoginAndPassword(user.getLogin(), user.getPassword()));
            if (users.isEmpty() || !users.get(0).isActive())
                throw new ServiceException("User not found");
            return users.get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to find user due to data source problems", e);
        }
    }

    public void removeUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        tryRemoveUserFromDataSource(user);
    }

    private void tryRemoveUserFromDataSource(User user) throws ServiceException {
        try {
            userRepository.remove(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to remove user due to data source problems", e);
        }
    }

    public void unblockUser(User user) throws ServiceException {
        if (user == null) {
            throw new ServiceException("Null user");
        }
        tryUnblockUserInDataSource(user);
    }

    private void tryUnblockUserInDataSource(User user) throws ServiceException {
        try {
            userRepository.unblock(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to unblock user due to data source problems", e);
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
        tryUpdateUserInDataSource(user);
    }

    public void changePassword(User user, String newPassword) throws ServiceException {
        if (user == null || newPassword == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidPassword(newPassword)) {
            throw new ServiceException("Invalid user password");
        }
        encryptUserPassword(user);
        getFirstUserFoundByLoginAndPasswordIfExists(user);
        user.setPassword(newPassword);
        encryptUserPassword(user);
        tryUpdateUserInDataSource(user);
    }

    public void changeName(User user, String name) throws ServiceException {
        if (user == null || name == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidName(name)) {
            throw new ServiceException("Invalid user name");
        }
        user.setName(name);
        tryUpdateUserInDataSource(user);
    }

    public void changeEmail(User user, String email) throws ServiceException {
        if (user == null || email == null) {
            throw new ServiceException("Null user parameter");
        }
        if (!userValidator.isValidEmail(email)) {
            throw new ServiceException("Invalid user email");
        }
        user.setEmail(email);
        tryUpdateUserInDataSource(user);
    }

    public void replenishAccount(User user, BigDecimal replenishmentSum) throws ServiceException {
        if (user == null || replenishmentSum == null) {
            throw new ServiceException("Null user parameter");
        }
        if (replenishmentSum.doubleValue() <= 0) {
            throw new ServiceException("Negative replenishment sum");
        }
        user.setBalance(user.getBalance().add(replenishmentSum));
        tryUpdateUserInDataSource(user);
    }

    private void tryUpdateUserInDataSource(User user) throws ServiceException {
        try {
            userRepository.update(user);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to change information due to data source problems", e);
        }
    }

    private void checkLoginIsUnique(String login) throws ServiceException {
        try {
            List<User> users = userRepository.query(new FindUserByLogin(login));
            if (!users.isEmpty())
                throw new ServiceException("Login is already taken");
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get user due to data source problems", e);
        }
    }

    public List<User> getUsersSubList(int indexFrom, int size) throws ServiceException {
        if (indexFrom < 0 || size < 0) {
            throw new ServiceException("Invalid index from or size number");
        }
        try {
            return userRepository.query(new FindUsersSortedByLoginWithLimitAndOffset(indexFrom, size));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get users due to data source problems", e);
        }
    }

    @Override
    public int getUsersTotalNumber() throws ServiceException {
        try {
            return userRepository.getNumberOfRecords();
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get number of users due to data source problems", e);
        }
    }

    @Override
    public List<User> getUsersWhoseLoginMatchString(String pattern) throws ServiceException {
        if (pattern == null) {
            throw new ServiceException("Null pattern");
        }
        try {
            return userRepository.query(new FindUsersWhoseLoginStartsWithPattern(pattern));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get users due to data source problems", e);
        }
    }

    @Override
    public User getUserById(int userId) throws ServiceException {
        try {
            return userRepository.query(new FindUserById(userId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get user due to data source problems", e);
        }
    }

    private void validateUserFields(User user) throws ServiceException {
        validateUserLogInCredentials(user);

        String name = user.getName();
        if (name == null || !userValidator.isValidName(name)) {
            throw new ServiceException("Invalid user name");
        }
        String email = user.getEmail();
        if (email == null || !userValidator.isValidEmail(email)) {
            throw new ServiceException("Invalid user email");
        }

        if (user.getUserRole() == null) {
            throw new ServiceException("Null user role");
        }
    }

    private void validateUserLogInCredentials(User user) throws ServiceException {
        String login = user.getLogin();
        if (login == null || !userValidator.isValidLogin(login)) {
            throw new ServiceException("Invalid user login");
        }
        String password = user.getPassword();
        if (password == null || !userValidator.isValidPassword(password)) {
            throw new ServiceException("Invalid user password");
        }
    }
}
