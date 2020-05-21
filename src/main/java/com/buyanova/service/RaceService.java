package com.buyanova.service;

import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;

import java.util.List;

public interface RaceService {

    void addRace(Race race) throws ServiceException;

    void setRaceResults(Race race) throws ServiceException;

    void updateRace(Race race) throws ServiceException;

    void removeRace(Race race) throws ServiceException;

    List<Race> getRacesWithoutResults() throws ServiceException;

    void addHorseToRace(int horseId, int raceId) throws ServiceException;

    void removeHorseFromRace(int horseId, int raceId) throws ServiceException;

    List<Race> getUpcomingRaces() throws ServiceException;

    List<Race> getUpcomingRacesWithoutOdds() throws ServiceException;

    List<Race> getPastRaces() throws ServiceException;

    Race getRaceById(int raceId) throws ServiceException;
}
