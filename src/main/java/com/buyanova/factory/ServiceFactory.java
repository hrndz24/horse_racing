package com.buyanova.factory;

import com.buyanova.service.*;
import com.buyanova.service.impl.*;

/**
 * {@code ServiceFactory} class uses factory pattern to decide
 * what classes that implement business logic should be provided to
 * layers above the business logic layer.
 *
 * @author Natalie
 * @see com.buyanova.service.BetService
 * @see com.buyanova.service.HorseService
 * @see com.buyanova.service.OddsService
 * @see com.buyanova.service.RaceService
 * @see com.buyanova.service.UserService
 */
public enum ServiceFactory {

    INSTANCE;

    public BetService getBetService() {
        return BetServiceImpl.INSTANCE;
    }

    public HorseService getHorseService() {
        return HorseServiceImpl.INSTANCE;
    }

    public OddsService getOddsService() {
        return OddsServiceImpl.INSTANCE;
    }

    public RaceService getRaceService() {
        return RaceServiceImpl.INSTANCE;
    }

    public UserService getUserService() {
        return UserServiceImpl.INSTANCE;
    }
}
