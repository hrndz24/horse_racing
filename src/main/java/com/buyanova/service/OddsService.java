package com.buyanova.service;

import com.buyanova.entity.Odds;
import com.buyanova.exception.ServiceException;

import java.util.List;

public interface OddsService {

    void addOdds(Odds odds) throws ServiceException;

    void updateOdds(Odds odds) throws ServiceException;

    List<Odds> getOddsForRace(int raceId) throws ServiceException;

    Odds getOddsForHorseInRace(int horseId, int raceId) throws ServiceException;

    Odds getOddsById(int oddsId) throws ServiceException;
}
