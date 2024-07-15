package pro.sky.petpartnersbot.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.petpartnersbot.repository.AnimalShelterPropsRepository;

@Service
@RequiredArgsConstructor
public class AnimalShelterPropsServiceImpl {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final AnimalShelterPropsRepository animalShelterPropsRepository;

    private final Long volunteerChatPropId = 5L;

    public String getVolunteerChat(Long animalShelterId){
        logger.info("Was invoked getVolunteerChat method");
        return animalShelterPropsRepository.getVolunteerChatId(volunteerChatPropId,animalShelterId);
    }
}
