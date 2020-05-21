package com.buyanova.service;

import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;

import java.math.BigDecimal;

public interface UserService {

    void signUp(User user) throws ServiceException;

    User logIn(User user) throws ServiceException;

    void removeUser(User user) throws ServiceException;

    void changeLogin(User user, String newLogin) throws ServiceException;

    void changePassword(User user, String newPassword) throws ServiceException;

    void changeName(User user, String name) throws ServiceException;

    void changeEmail(User user, String email) throws ServiceException;

    void replenishAccount(User user, BigDecimal replenishmentSum) throws ServiceException;
}
