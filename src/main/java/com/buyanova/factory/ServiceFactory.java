package com.buyanova.factory;

import com.buyanova.service.*;
import com.buyanova.service.impl.*;

public enum ServiceFactory {

    INSTANCE;

    private BetService betService = BetServiceImpl.INSTANCE;
    private HorseService horseService = HorseServiceImpl.INSTANCE;
    private OddsService oddsService = OddsServiceImpl.INSTANCE;
    private RaceService raceService = RaceServiceImpl.INSTANCE;
    private UserService userService = UserServiceImpl.INSTANCE;

    public BetService getBetService() {
        return betService;
    }

    public HorseService getHorseService() {
        return horseService;
    }

    public OddsService getOddsService() {
        return oddsService;
    }

    public RaceService getRaceService() {
        return raceService;
    }

    public UserService getUserService() {
        return userService;
    }
}
