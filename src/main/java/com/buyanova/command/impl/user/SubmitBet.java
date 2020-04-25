package com.buyanova.command.impl.user;

import com.buyanova.command.Command;
import com.buyanova.command.JSPParameter;
import com.buyanova.command.JSPPath;
import com.buyanova.entity.Bet;
import com.buyanova.entity.User;
import com.buyanova.exception.ServiceException;
import com.buyanova.service.BetService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

public class SubmitBet implements Command {
    @Override
    public String getJSP(HttpServletRequest request, HttpServletResponse response) {
        int oddsId = Integer.parseInt(request.getParameter(JSPParameter.ODDS_ID.getParameter()));
        User user = (User) request.getSession().getAttribute(JSPParameter.USER.getParameter());
        int userId = user.getId();
        BigDecimal sum = new BigDecimal(request.getParameter(JSPParameter.BET_SUM.getParameter()));

        Bet bet = new Bet();
        bet.setOddsId(oddsId);
        bet.setSum(sum);
        bet.setUserId(userId);

        try {
            BetService.INSTANCE.addBet(bet);
            user.setBalance(user.getBalance().subtract(bet.getSum()));
            return JSPPath.USER_PAGE.getPath();
        } catch (ServiceException e) {
            return JSPPath.MAKE_BET.getPath();
        }
    }
}
