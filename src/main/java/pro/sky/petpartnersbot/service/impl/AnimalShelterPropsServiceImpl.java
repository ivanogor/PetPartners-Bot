package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.entity.AnimalShelterProps;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;

@Service
@RequiredArgsConstructor
public class AnimalShelterPropsServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AnimalShelterPropsRepository animalShelterPropsRepository;

    public AnimalShelterProps getUserProp(Long propId,Long animalShelterId){
        logger.info("Was invoked getVolunteerChat method");
        return animalShelterPropsRepository.getUserProp(propId,animalShelterId);
    }

    public void addShelterProp(AnimalShelterProps animalShelterProps){
        animalShelterPropsRepository.save(animalShelterProps);
    }
}
