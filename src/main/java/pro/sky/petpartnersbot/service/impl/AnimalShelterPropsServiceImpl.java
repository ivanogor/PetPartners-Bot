package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;
import pro.sky.petpartnersbot.service.AnimalShelterPropsService;

/**
 * Сервис для работы со свойствами приютов для животных.
 */
@Service
@RequiredArgsConstructor
public class AnimalShelterPropsServiceImpl implements AnimalShelterPropsService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AnimalShelterPropsRepository animalShelterPropsRepository;

    @Override
    public AnimalShelterProps getUserProp(Long propId, Long animalShelterId) {
        logger.info("Was invoked getUserProp method");
        return animalShelterPropsRepository.getUserProp(propId, animalShelterId);
    }

    @Override
    public AnimalShelterProps getPetProp(Long propId, Long petId) {
        logger.info("Was invoked getPetProp method");
        return animalShelterPropsRepository.getPetProp(propId, petId);
    }

    @Override
    public AnimalShelterProps addShelterProp(AnimalShelterProps animalShelterProps) {
        logger.info("Was invoked addShelterProp method");
        return animalShelterPropsRepository.save(animalShelterProps);
    }
}