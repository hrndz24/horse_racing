package com.buyanova.service.impl;

import com.buyanova.entity.Horse;
import com.buyanova.entity.Race;
import com.buyanova.exception.RepositoryException;
import com.buyanova.exception.ServiceException;
import com.buyanova.factory.RepositoryFactory;
import com.buyanova.repository.horse.HorseRepository;
import com.buyanova.service.HorseService;
import com.buyanova.specification.impl.horse.*;
import com.buyanova.validator.HorseValidator;

import java.util.List;

public enum HorseServiceImpl implements HorseService {

    INSTANCE;

    private RepositoryFactory factory = RepositoryFactory.INSTANCE;
    private HorseRepository horseRepository = factory.getHorseRepository();

    private HorseValidator horseValidator = new HorseValidator();

    public void addHorse(Horse horse) throws ServiceException {
        if (horse == null) {
            throw new ServiceException("Null horse");
        }
        validateHorseFields(horse);
        setHorseDefaultValues(horse);
        tryAddHorseToDataSource(horse);
    }

    private void setHorseDefaultValues(Horse horse) {
        horse.setPerforming(true);
    }

    private void tryAddHorseToDataSource(Horse horse) throws ServiceException {
        try {
            horseRepository.add(horse);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to add horse due to data source problems", e);
        }
    }

    public List<Horse> getPerformingHorses() throws ServiceException {
        try {
            return horseRepository.query(new FindAllPerformingHorses());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses due to data source problems", e);
        }
    }

    public List<Horse> getNonPerformingHorses() throws ServiceException {
        try {
            return horseRepository.query(new FindAllNonPerformingHorses());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses due to data source problems", e);
        }
    }

    public List<Horse> getAllHorses() throws ServiceException {
        try {
            return horseRepository.query(new FindAllHorses());
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses due to data source problems", e);
        }
    }

    public List<Horse> getHorsesFromRace(Race race) throws ServiceException {
        if (race == null) {
            throw new ServiceException("Null race");
        }
        try {
            return horseRepository.query(new FindHorsesPerformingInRace(race.getId()));
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horses due to data source problems", e);
        }
    }

    public void updateHorse(Horse horse) throws ServiceException {
        if (horse == null) {
            throw new ServiceException("Null horse");
        }
        validateHorseFields(horse);
        tryUpdateHorseInDataSource(horse);
    }

    private void tryUpdateHorseInDataSource(Horse horse) throws ServiceException {
        try {
            horseRepository.update(horse);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to update horse due to data source problems", e);
        }
    }

    public void removeHorse(Horse horse) throws ServiceException {
        if (horse == null) {
            throw new ServiceException("Null horse");
        }
        tryRemoveHorseFromDataSource(horse);
    }

    private void tryRemoveHorseFromDataSource(Horse horse) throws ServiceException {
        try {
            horseRepository.remove(horse);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to remove horse due to data source problems", e);
        }
    }

    public Horse getHorseById(int horseId) throws ServiceException {
        try {
            return horseRepository.query(new FindHorseById(horseId)).get(0);
        } catch (RepositoryException e) {
            throw new ServiceException("Failed to get horse due to data source problems", e);
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
