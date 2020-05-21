package com.buyanova.service;

import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;

import java.util.List;

public interface BetService {

    void addBet(Bet bet) throws ServiceException;

    void removeBet(Bet bet) throws ServiceException;

    void updateBet(Bet bet) throws ServiceException;

    List<Bet> getBetsByUser(User user) throws ServiceException;

    Bet getBetById(int betId) throws ServiceException;
}
