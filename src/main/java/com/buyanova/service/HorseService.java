package com.buyanova.service;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.specification.impl.horse.FindHorseById;
import com.buyanova.specification.impl.horse.FindHorsesPerformingInRace;
import com.buyanova.validator.HorseValidator;

import java.util.List;

public enum HorseService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private HorseRepository horseRepository = factory.getHorseRepository();

    private HorseValidator horseValidator = new HorseValidator();

    public void addHorse(Horse horse) throws ServiceException {
        if (horse == null) {
            throw new ServiceException("Null horse");
        }
        validateHorseFields(horse);
        horse.setPerforming(true);
        try {
            horseRepository.add(horse);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Horse> getHorsesFromRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        try {
            return horseRepository.query(new FindHorsesPerformingInRace(race.getId()));
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void removeHorse(Horse horse) throws ServiceException {
        if (horse == null) {
            throw new ServiceException("Null horse");
        }
        try {
            horseRepository.remove(horse);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public Horse getHorseById(int horseId) throws ServiceException {
        try {
            return horseRepository.query(new FindHorseById(horseId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    private void validateHorseFields(Horse horse) throws ServiceException {
        if (horse.getName() == null) {
            throw new ServiceException("Null horse name");
        }
        if (horse.getBreed() == null) {
            throw new ServiceException("Null horse breed");
        }
        if (!horseValidator.isAgePositive(horse.getAge())) {
            throw new ServiceException("Negative horse age");
        }
        if (!horseValidator.isRacesNumberPositive(horse.getRacesLostNumber())) {
            throw new ServiceException("Negative number of lost races");
        }
        if (!horseValidator.isRacesNumberPositive(horse.getRacesWonNumber())) {
            throw new ServiceException("Negative number of won races");
        }
    }
}
