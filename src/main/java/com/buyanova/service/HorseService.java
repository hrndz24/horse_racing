package com.buyanova.service;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.ServiceException;

import java.util.List;

public interface HorseService {

    void addHorse(Horse horse) throws ServiceException;

    List<Horse> getPerformingHorses() throws ServiceException;

    List<Horse> getNonPerformingHorses() throws ServiceException;

    List<Horse> getAllHorses() throws ServiceException;

    List<Horse> getHorsesFromRace(Race race) throws ServiceException;

    void updateHorse(Horse horse) throws ServiceException;

    void removeHorse(Horse horse) throws ServiceException;

    Horse getHorseById(int horseId) throws ServiceException;
}
