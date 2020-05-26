package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.ServiceFactory;
import com.buyanova.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteBet implements Command {
    private static Logger logger = LogManager.getLogger(DeleteBet.class);

    private BetService betService = ServiceFactory.INSTANCE.getBetService();

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        Bet bet = (Bet) request.getSession().getAttribute(JSPParameter.BET.getParameter());
        try {
            betService.removeBet(bet);
            updateSessionAttributes(request, bet);
            return JSPPath.USER_BETS.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to delete bet", e);
            request.setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }

    private void updateSessionAttributes(HttpServletRequest request, Bet bet) throws ServiceException {
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        user.setBalance(user.getBalance().add(bet.getSum()));
        List<Bet> bets = betService.getBetsByUser(user);
        request.getSession().setAttribute(JSPParameter.BETS.getParameter(), bets);
    }
}
