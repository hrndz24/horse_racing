package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.Odds;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.impl.BetServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class SubmitBet implements Command {
    private static Logger logger = LogManager.getLogger(SubmitBet.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int oddsId = ((Odds) request.getSession().getAttribute(JSPParameter.ODDS.getParameter())).getId();
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        int userId = user.getId();
        BigDecimal sum = new BigDecimal(request.getParameter(JSPParameter.BET_SUM.getParameter()));

        Bet bet = new Bet();
        bet.setOddsId(oddsId);
        bet.setSum(sum);
        bet.setUserId(userId);

        try {
            BetServiceImpl.INSTANCE.addBet(bet);
            user.setBalance(user.getBalance().subtract(bet.getSum()));
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to submit bet", e);
            return JSPPath.MAKE_BET.getPath();
        }
    }
}
