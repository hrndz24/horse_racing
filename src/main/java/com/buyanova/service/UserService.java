package com.buyanova.service;

import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;

import java.math.BigDecimal;

/**
 * {@code UserService} interface is used to describe
 * business logic concerning {@code User} entity.
 *
 * @author Natalie
 * @see com.buyanova.service.impl.UserServiceImpl
 */
public interface UserService {

    /**
     * Adds new user to the data source.
     * <p>
     * By default user's initial balance is set to be zero.
     * <p>
     * Note: user's password should be encrypted by
     * all implementations of this method.
     *
     * @param user user that is to be added
     * @throws ServiceException if null user is passed,
     *                          if user contains invalid fields,
     *                          if login is not unique,
     *                          if a data source access error occurs
     */
    void signUp(User user) throws ServiceException;

    /**
     * Allows user to use the application if their login and
     * password match the ones in the data source,
     * otherwise an exception is thrown.
     * <p>
     * Note: user's password should be encrypted by
     * all implementations of this method.
     *
     * @param user user whose login and password should be used to log in
     * @return user with the information from the data source
     * @throws ServiceException if null user is passed,
     *                          if user contains invalid fields,
     *                          if user with such login and password doesn't exist
     *                          if a data source access error occurs
     */
    User logIn(User user) throws ServiceException;

    /**
     * Removes user from the list of active users.
     *
     * @param user user that is to be removed
     * @throws ServiceException if null user is passed,
     *                          if a data source access error occurs
     */
    void removeUser(User user) throws ServiceException;

    /**
     * Updates user's login in the data source.
     *
     * @param user     user whose login is to be updated
     * @param newLogin new user's login
     * @throws ServiceException if null user or login is passed,
     *                          if invalid login is passed,
     *                          if new login is not unique,
     *                          if a data source access error occurs
     */
    void changeLogin(User user, String newLogin) throws ServiceException;

    /**
     * Updates user's password in the data source.
     * <p>
     * Note: user's password should be encrypted by
     * all implementations of this method.
     *
     * @param user        user whose password is to be updated
     * @param newPassword new user's password
     * @throws ServiceException if null user or password is passed,
     *                          if invalid password is passed,
     *                          if user with their login and old password is not found,
     *                          if a data source access error occurs
     */
    void changePassword(User user, String newPassword) throws ServiceException;

    /**
     * Updates user's name in the data source.
     *
     * @param user user whose name is to be updated
     * @param name new user's name
     * @throws ServiceException if null user or name is passed,
     *                          if invalid name is passed,
     *                          if a data source access error occurs
     */
    void changeName(User user, String name) throws ServiceException;

    /**
     * Updates user's email in the data source.
     *
     * @param user  user whose email is to be updated
     * @param email new user's email
     * @throws ServiceException if null user or email is passed,
     *                          if invalid email is passed,
     *                          if a data source access error occurs
     */
    void changeEmail(User user, String email) throws ServiceException;

    /**
     * Updates user's balance by the specified sum in the data source.
     * <p>
     * Note: the sum should always be positive.
     *
     * @param user             user whose balance is to be updated
     * @param replenishmentSum amount of money to add to user's balance
     * @throws ServiceException if null user or sum is passed,
     *                          if non-positive sum is passed,
     *                          if a data source access error occurs
     */
    void replenishAccount(User user, BigDecimal replenishmentSum) throws ServiceException;
}
