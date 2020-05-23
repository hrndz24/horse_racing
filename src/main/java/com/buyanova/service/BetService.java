package com.buyanova.service;

import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;

import java.util.List;

/**
 * {@code BetService} interface is used to describe
 * business logic concerning {@code Bet} entity.
 *
 * @author Natalie
 * @see com.buyanova.service.impl.BetServiceImpl
 */
public interface BetService {

    /**
     * Adds bet to the data source.
     * <p>
     * The method will execute successfully in case bet's sum
     * is less than the user's balance, otherwise an exception is thrown.
     * <p>
     * Amount of money equal to the bet's sum is automatically
     * withdrawn from user's balance.
     *
     * @param bet bet that is to be added
     * @throws ServiceException if null bet is passed,
     *                          if bet contains invalid fields,
     *                          if a data source access error occurs
     */
    void addBet(Bet bet) throws ServiceException;

    /**
     * Removes bet from the data source and
     * returns money to its user.
     * <p>
     * Note: it checks bet exists so that to prevent
     * replenishment of user's balance.
     *
     * @param bet bet that is to be removed,
     *            should be present in the data source
     * @throws ServiceException if null or non-existent bet is passed
     *                          or a data source access error occurs
     */
    void removeBet(Bet bet) throws ServiceException;

    /**
     * Update information about the specified bet in the data source.
     * <p>
     * The method will execute successfully in case the difference between
     * new bet's sum and the old one is less than the user's balance,
     * otherwise an exception is thrown.
     * <p>
     * Note: it checks bet exists so that to prevent
     * updating user's balance.
     *
     * @param bet bet that is to be updated
     * @throws ServiceException if null or non-existent bet is passed,
     *                          if bet contains invalid fields,
     *                          if user doesn't have enough money to update bet,
     *                          if a data source access error occurs
     */
    void updateBet(Bet bet) throws ServiceException;

    /**
     * Returns list of bets made by specified user.
     *
     * @param user user whose bets are requested
     * @return list of bets made by specified user
     * @throws ServiceException if null user is passed,
     *                          or a data source access error occurs
     */
    List<Bet> getBetsByUser(User user) throws ServiceException;

    /**
     * Returns {@code Bet} object with the full information about the
     * bet from the data source.
     *
     * @param betId id of the requested bet
     * @return bet with specified id
     * @throws ServiceException if a data source access error occurs
     */
    Bet getBetById(int betId) throws ServiceException;
}
