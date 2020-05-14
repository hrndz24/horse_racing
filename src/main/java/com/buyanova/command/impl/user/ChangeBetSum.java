package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.BetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class ChangeBetSum implements Command {
    private static Logger logger = LogManager.getLogger(ChangeBetSum.class);

    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        BigDecimal sum = new BigDecimal(request.getParameter(JSPParameter.SUM.getParameter()));
        Bet bet = (Bet) request.getSession().getAttribute(JSPParameter.BET.getParameter());
        BigDecimal oldSum = bet.getSum();
        bet.setSum(sum);
        try {
            BetService.INSTANCE.updateBetSum(bet);
            User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
            user.setBalance(user.getBalance().subtract(bet.getSum().subtract(oldSum)));
            return JSPPath.BET.getPath();
        } catch (ServiceException e) {
            logger.warn("Failed to execute command to change bet sum", e);
            bet.setSum(oldSum);
            request.getSession().setAttribute(JSPParameter.ERROR_MESSAGE.getParameter(), e.getMessage());
            return JSPPath.ERROR_PAGE.getPath();
        }
    }
}
